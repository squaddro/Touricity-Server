package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.ByteArrays;
import com.squadro.touricity.database.Database;
import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.query.locationQueries.InsertNewLocationQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IEntry;
import com.squadro.touricity.message.types.data.IPath;
import com.squadro.touricity.message.types.data.Path;
import com.squadro.touricity.message.types.data.Stop;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class InsertNewEntryQuery extends InsertionQuery {

    private final String route_id;
    private final int pointer;
    private final AtomicReference<IEntry> entry = new AtomicReference<>();
    private boolean isStop;
    private String entryQuery = "";

    public InsertNewEntryQuery(String route_id, IEntry entry,int pointer) {
        this.route_id = route_id;
        this.pointer = pointer;
        if(entry instanceof Stop){
            if(((Stop) entry).getStop_id() == null){
                Stop stop = new Stop(((Stop) entry).getLocation(), null, entry.getExpense(), entry.getDuration(), entry.getComment(), entry.getIndex());
                this.entry.set(stop);
                isStop = true;
            }
            else{
                DoesStopExists doesStopExists = new DoesStopExists(((Stop) entry).getStop_id());
                doesStopExists.execute();

                if(doesStopExists.getDoesStopExists()){
                    Stop stop = new Stop(((Stop) entry).getLocation(), ((Stop) entry).getStop_id(), entry.getExpense(), entry.getDuration(), entry.getComment(), entry.getIndex());
                    this.entry.set(stop);
                    isStop = true;
                }
                else{
                    //TODO: throw an error.
                }
            }

        }else if(entry instanceof Path){
            if(((Path) entry).getPath_id() == null){
                Path path = new Path(null, ((Path) entry).getPath_type(), ((Path) entry).getVertices(), entry.getDuration(), entry.getExpense(), entry.getComment(), entry.getIndex());
                this.entry.set(path);
                isStop = false;
            }
            else{
                DoesPathExists doesPathExists = new DoesPathExists(((Path) entry).getPath_id());
                doesPathExists.execute();

                if(doesPathExists.getDoesPathExists()){
                    Path path = new Path(((Path) entry).getPath_id(), ((Path) entry).getPath_type(), ((Path) entry).getVertices(), entry.getDuration(), entry.getExpense(), entry.getComment(), entry.getIndex());
                    this.entry.set(path);
                    isStop = false;
                }
                else{
                    //TODO: throw an error.
                }
            }
        }
    }

    @Override
    public String getQuery() {
        if(isStop == true){
            Stop stop = (Stop) this.entry.get();
            if(stop.getStop_id() == null){
                String newUUID = UUID.randomUUID().toString();
                ((Stop) this.entry.get()).setStop_id(newUUID);
                InsertNewLocationQuery locationQuery = new InsertNewLocationQuery(stop.getLocation());
                Database.execute(locationQuery);
                entryQuery = "INSERT INTO db_entry(route_id,stop_id,path_id,entry_id,expense,duration,comment_desc,pointer) VALUES(" +"'"+ route_id + "','" + newUUID + "'," + "NULL" + ",'"
                        + UUID.randomUUID().toString() + "'," + stop.getExpense() + "," + stop.getDuration() + ",'" + stop.getComment() + "'," + pointer+ ")";
                return "INSERT INTO db_stop(location_id, stop_id) VALUES(" + "'" + stop.getLocation().getLocation_id() + "','" + newUUID + "')";
            }
            else{
                DoesStopExists doesStopExists = new DoesStopExists(((Stop) entry.get()).getStop_id());
                doesStopExists.execute();
                if(doesStopExists.getDoesStopExists()){
                    UpdateStopQuery updateStopQuery = new UpdateStopQuery(stop);
                    updateStopQuery.execute();
                }
            }
        }
        else{
            Path path = (Path) this.entry.get();
            if(path.getPath_id() == null){
                String newUUID = UUID.randomUUID().toString();
                ((Path) this.entry.get()).setPath_id(newUUID);
                entryQuery = "INSERT INTO DB_entry(route_id,stop_id,path_id,entry_id,expense,duration,comment_desc,pointer) VALUES(" + "'" + route_id + "'," + "NULL" + ",'" + newUUID + "','"
                        + UUID.randomUUID().toString() + "'," + path.getExpense() + "," + path.getDuration() + ",'" + path.getComment() + "'," + pointer + ")";
                return "INSERT INTO db_path(path_id,path_type,vertices) VALUES('" + newUUID + "'," + path.getPath_typeAsInt() + ", '" + ByteArrays.Encoders.encodePathVertexArray(path.getVertices()) + "')";
            }
            else{
                DoesPathExists doesPathExists = new DoesPathExists(((Path) entry.get()).getPath_id());
                doesPathExists.execute();

                if(doesPathExists.getDoesPathExists()){
                    UpdatePathQuery updatePathQuery = new UpdatePathQuery(path);
                    updatePathQuery.execute();
                }
            }
        }
        return null;
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {

        if(!entryQuery.equals("")){
            if(isStop){
                InsertNewStopQuery insertNewStopQuery = new InsertNewStopQuery(entryQuery);
                insertNewStopQuery.execute();
            }
            else{
                InsertNewPathQuery insertNewPathQuery = new InsertNewPathQuery(entryQuery);
                insertNewPathQuery.execute();
            }
        }


        return false;
    }
    public IEntry getEntry(){
        return entry.get();
    }
}
