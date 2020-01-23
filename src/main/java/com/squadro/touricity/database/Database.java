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
	private final static String DATABASE_ACCOUNT = "DB_ACCOUNT";
	private final static String DATABASE_ACCOUNT_ACCOUNT_ID = "Account_Id";
	private final static String DATABASE_USER = "DB_USER";
	private final static String DATABASE_USER_ACCOUNT_ID = "Account_Id";
	private final static String DATABASE_USER_USERNAME = "Username";
	private final static String DATABASE_USER_PASSWORD = "User_Password";
	private final static String DATABASE_SESSION = "DB_SESSION";
	private final static String DATABASE_SESSION_ACCOUNT_ID = "Account_Id";
	private final static String DATABASE_SESSION_SESSION_ID = "Session_Id";
	private final static String DATABASE_ROUTE = "DB_ROUTE";
	private final static String DATABASE_ROUTE_CREATOR = "Creator";
	private final static String DATABASE_ROUTE_ROUTE_ID = "Route_Id";
	private final static String DATABASE_ENTRY = "DB_ENTRY";
	private final static String DATABASE_ENTRY_ROUTE_ID = "Route_Id";
	private final static String DATABASE_ENTRY_STOP_ID = "Stop_Id";
	private final static String DATABASE_ENTRY_PATH_ID = "Path_Id";
	private final static String DATABASE_ENTRY_ENTRY_ID = "Entry_Id";
	private final static String DATABASE_ENTRY_EXPENSE = "Expense";
	private final static String DATABASE_ENTRY_DURATION = "Duration";
	private final static String DATABASE_ENTRY_COMMENT_DESCRIPTION = "Comment_desc";
	private final static String DATABASE_PATH = "DB_PATH";
	private final static String DATABASE_PATH_PATH_ID = "Path_Id";
	private final static String DATABASE_PATH_PATH_TYPE = "Path_Type";
	private final static String DATABASE_PATH_VERTICES = "Vertices";
	private final static String DATABASE_STOP = "DB_STOP";
	private final static String DATABASE_STOP_LOCATION_ID = "Location_Id";
	private final static String DATABASE_STOP_STOP_ID = "Stop_Id";
	private final static String DATABASE_LOCATION = "DB_LOCATION";
	private final static String DATABASE_LOCATION_LOCATION_ID = "Location_Id";
	private final static String DATABASE_LOCATION_LATITUDE = "Latitude";
	private final static String DATABASE_LOCATION_LONGITUDE = "Longitude";
	private final static String DATABASE_LIKE = "DB_LIKE";
	private final static String DATABASE_LIKE_ACCOUNT_ID = "Account_Id";
	private final static String DATABASE_LIKE_LIKE_ID = "Like_Id";
	private final static String DATABASE_LIKE_SCORE = "Score";
	private final static String DATABASE_COMMENT = "DB_COMMENT";
	private final static String DATABASE_COMMENT_ACCOUNT_ID = "Account_Id";
	private final static String DATABASE_COMMENT_COMMENT_ID = "Comment_Id";
	private final static String DATABASE_COMMENT_COMMENT_DESCRIPTION = "Comment_desc";
	private final static String DATABASE_LOCATION_LIKE = "DB_LOCATION_LIKE";
	private final static String DATABASE_LOCATION_LIKE_LOCATION_ID = "Location_Id";
	private final static String DATABASE_LOCATION_LIKE_LIKE_ID = "Like_Id";
	private final static String DATABASE_LOCATION_COMMENT = "DB_LOCATION_COMMENT";
	private final static String DATABASE_LOCATION_COMMENT_LOCATION_ID = "Location_Id";
	private final static String DATABASE_LOCATION_COMMENT_COMMENT_ID = "Comment_Id";
	private final static String DATABASE_ROUTE_LIKE = "DB_ROUTE_LIKE";
	private final static String DATABASE_ROUTE_LIKE_ROUTE_ID = "Route_Id";
	private final static String DATABASE_ROUTE_LIKE_LIKE_ID = "Like_Id";
	private final static String DATABASE_ROUTE_COMMENT = "DB_ROUTE_COMMENT";
	private final static String DATABASE_ROUTE_COMMENT_ROUTE_ID = "Route_Id";
	private final static String DATABASE_ROUTE_COMMENT_COMMENT_ID = "Comment_Id";
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
