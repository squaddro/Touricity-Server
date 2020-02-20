package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.InsertionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IPath;
import com.squadro.touricity.message.types.data.Path;
import com.squadro.touricity.message.types.data.Stop;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class InsertNewPathQuery extends InsertionQuery {

    private final Path path;
    private final String newUUID;

    public InsertNewPathQuery(String newUUID, Path path) {
        this.newUUID = newUUID;
        this.path = path;
    }

    @Override
    public String getQuery() {
        return "INSERT INTO DB_path(path_id,path_type,vertices) VALUES('" + newUUID + "'," + path.getPath_type() + "," + vertexArrayToByteArray(path.getVertices()) + ")";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        return false;
    }

    private static byte[] vertexArrayToByteArray(IPath.PathVertex[] vertices) {
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(vertices);
            return out.toByteArray();
        }catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }
}
