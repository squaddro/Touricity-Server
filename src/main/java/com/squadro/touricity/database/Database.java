package com.squadro.touricity.database;

import com.squadro.touricity.database.query.pipeline.IPipelinedQuery;
import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.result.QueryResult;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.Queue;

public class Database {

	private static Database instance;
	private final static String ENVIRONMENT_DATABASE_URL = "DATABASE_URL";

	private String databaseUrl;

	private Database() {
		String databaseUrl = System.getenv(ENVIRONMENT_DATABASE_URL);
	}

	private Connection connect() throws SQLException {
		return DriverManager.getConnection(databaseUrl);
	}

	public static Database getInstance() {
		if(instance == null) {
			synchronized (Database.class) {
				if(instance==null)
					instance = new Database();
			}
		}
		return instance;
	}

	private boolean executeQuery(ISingleQuery query) {
		String queryStr = query.getQuery();
		try {
			Connection conn = connect();
			PreparedStatement stmt = conn.prepareStatement(queryStr);

			ResultSet resultSet = null;
			boolean successful = false;

			switch (query.getExecutionType()) {
				case DELETE:
				case INSERT:
				case UPDATE:
					successful = stmt.executeUpdate() != 0;
					break;
				case SELECT:
					resultSet = stmt.executeQuery();
					successful = resultSet.next();
					break;
			}

			boolean doContinue = query.onResult(new QueryResult(successful, resultSet));
			conn.close();
			return doContinue;
		} catch (SQLException e) {
			e.printStackTrace();
			query.onError("Query: " + queryStr + " Error: " + e.toString());
			return false;
		}
	}

	public static void execute(ISingleQuery query) {
		getInstance().executeQuery(query);
	}

	public static void execute(IPipelinedQuery query) {
		Queue<ISingleQuery> queue = query.getQueries();
		while(!queue.isEmpty()) {
			if(!getInstance().executeQuery(queue.poll()))
				break;
		}
	}
}
