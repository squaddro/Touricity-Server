package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IEntry;
import com.squadro.touricity.message.types.data.IPath;
import com.squadro.touricity.message.types.data.Path;
import com.squadro.touricity.message.types.data.Stop;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class InsertNewEntryQuery extends InsertionQuery {

    private final String route_id;
    private final AtomicReference<IEntry> entry = new AtomicReference<>();
    private boolean isStop;

    public InsertNewEntryQuery(String route_id, IEntry entry) {
        this.route_id = route_id;
        if(entry instanceof Stop){
            if(((Stop) entry).getStop_id() == null){
                Stop stop = new Stop(((Stop) entry).getLocation_id(), null, entry.getExpense(), entry.getDuration(), entry.getComment(), entry.getIndex());
                this.entry.set(stop);
                isStop = true;
            }
            else{
                DoesStopExists doesStopExists = new DoesStopExists(((Stop) entry).getStop_id());
                doesStopExists.execute();

                if(doesStopExists.getDoesStopExists()){
                    Stop stop = new Stop(((Stop) entry).getLocation_id(), ((Stop) entry).getStop_id(), entry.getExpense(), entry.getDuration(), entry.getComment(), entry.getIndex());
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

                InsertNewStopQuery insertNewStopQuery = new InsertNewStopQuery(newUUID, stop);
                insertNewStopQuery.execute();
                return "INSERT INTO db_entry(route_id,stop_id,path_id,entry_id,expense,duration,comment_desc,pointer) VALUES(" +"'"+ route_id + "','" + newUUID + "'," + "NULL" + ",'"
                        + UUID.randomUUID().toString() + "'," + stop.getExpense() + "," + stop.getDuration() + ",'" + stop.getComment() + "'," + stop.getIndex() + ")";
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

                InsertNewPathQuery insertNewPathQuery = new InsertNewPathQuery(newUUID, path);
                insertNewPathQuery.execute();
                return "INSERT INTO DB_entry(route_id,stop_id,path_id,entry_id,expense,duration,comment_desc,pointer) VALUES(" + "'" + route_id + "'," + "NULL" + ",'" + newUUID + "','"
                        + UUID.randomUUID().toString() + "'," + path.getExpense() + "," + path.getDuration() + ",'" + path.getComment() + "'," + path.getIndex() + ")";
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
        return false;
    }
    public IEntry getEntry(){
        return entry.get();
    }
}
