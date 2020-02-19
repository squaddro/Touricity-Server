package com.squadro.touricity.database.query.routeQueries;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.IPath;
import com.squadro.touricity.message.types.data.Path;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerticesSelectionFromPathId extends SelectionQuery {

    private final String path_id;
    private List<IPath.PathVertex> vertices;

    public VerticesSelectionFromPathId(String pathId) {
        this.path_id = pathId;
        vertices = new ArrayList<>();
    }

    @Override
    public String getQuery() {
        return "SELECT vertices FROM path WHERE path_id = '" + path_id + "'";
    }

    @Override
    public boolean onResult(QueryResult result) throws SQLException {
        ResultSet rs = result.getResultSet();
        vertices = Arrays.asList(byteArrayToVerticesArray(rs.getBytes("vertices")));
        return false;
    }

    private static IPath.PathVertex[] byteArrayToVerticesArray(byte[] bytes) {
        ByteArrayInputStream in;
        ObjectInputStream is = null;
        IPath.PathVertex[] arr = null;
        try {
            in = new ByteArrayInputStream(bytes);
            is = new ObjectInputStream(in);
            arr = (IPath.PathVertex[]) is.readObject();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return arr;
    }
    public IPath.PathVertex[] getVertices(){
        return (IPath.PathVertex[]) vertices.toArray();
    }

}
