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
	public final static String DATABASE_ACCOUNT = "DB_ACCOUNT";
	public final static String DATABASE_ACCOUNT_ACCOUNT_ID = "Account_Id";
	public final static String DATABASE_USER = "DB_USER";
	public final static String DATABASE_USER_ACCOUNT_ID = "Account_Id";
	public final static String DATABASE_USER_USERNAME = "Username";
	public final static String DATABASE_USER_PASSWORD = "User_Password";
	public final static String DATABASE_SESSION = "DB_SESSION";
	public final static String DATABASE_SESSION_ACCOUNT_ID = "Account_Id";
	public final static String DATABASE_SESSION_SESSION_ID = "Session_Id";
	public final static String DATABASE_ROUTE = "DB_ROUTE";
	public final static String DATABASE_ROUTE_CREATOR = "Creator";
	public final static String DATABASE_ROUTE_ROUTE_ID = "Route_Id";
	public final static String DATABASE_ENTRY = "DB_ENTRY";
	public final static String DATABASE_ENTRY_ROUTE_ID = "Route_Id";
	public final static String DATABASE_ENTRY_STOP_ID = "Stop_Id";
	public final static String DATABASE_ENTRY_PATH_ID = "Path_Id";
	public final static String DATABASE_ENTRY_ENTRY_ID = "Entry_Id";
	public final static String DATABASE_ENTRY_EXPENSE = "Expense";
	public final static String DATABASE_ENTRY_DURATION = "Duration";
	public final static String DATABASE_ENTRY_COMMENT_DESCRIPTION = "Comment_desc";
	public final static String DATABASE_PATH = "DB_PATH";
	public final static String DATABASE_PATH_PATH_ID = "Path_Id";
	public final static String DATABASE_PATH_PATH_TYPE = "Path_Type";
	public final static String DATABASE_PATH_VERTICES = "Vertices";
	public final static String DATABASE_STOP = "DB_STOP";
	public final static String DATABASE_STOP_LOCATION_ID = "Location_Id";
	public final static String DATABASE_STOP_STOP_ID = "Stop_Id";
	public final static String DATABASE_LOCATION = "DB_LOCATION";
	public final static String DATABASE_LOCATION_LOCATION_ID = "Location_Id";
	public final static String DATABASE_LOCATION_LATITUDE = "Latitude";
	public final static String DATABASE_LOCATION_LONGITUDE = "Longitude";
	public final static String DATABASE_LIKE = "DB_LIKE";
	public final static String DATABASE_LIKE_ACCOUNT_ID = "Account_Id";
	public final static String DATABASE_LIKE_LIKE_ID = "Like_Id";
	public final static String DATABASE_LIKE_SCORE = "Score";
	public final static String DATABASE_COMMENT = "DB_COMMENT";
	public final static String DATABASE_COMMENT_ACCOUNT_ID = "Account_Id";
	public final static String DATABASE_COMMENT_COMMENT_ID = "Comment_Id";
	public final static String DATABASE_COMMENT_COMMENT_DESCRIPTION = "Comment_desc";
	public final static String DATABASE_LOCATION_LIKE = "DB_LOCATION_LIKE";
	public final static String DATABASE_LOCATION_LIKE_LOCATION_ID = "Location_Id";
	public final static String DATABASE_LOCATION_LIKE_LIKE_ID = "Like_Id";
	public final static String DATABASE_LOCATION_COMMENT = "DB_LOCATION_COMMENT";
	public final static String DATABASE_LOCATION_COMMENT_LOCATION_ID = "Location_Id";
	public final static String DATABASE_LOCATION_COMMENT_COMMENT_ID = "Comment_Id";
	public final static String DATABASE_ROUTE_LIKE = "DB_ROUTE_LIKE";
	public final static String DATABASE_ROUTE_LIKE_ROUTE_ID = "Route_Id";
	public final static String DATABASE_ROUTE_LIKE_LIKE_ID = "Like_Id";
	public final static String DATABASE_ROUTE_COMMENT = "DB_ROUTE_COMMENT";
	public final static String DATABASE_ROUTE_COMMENT_ROUTE_ID = "Route_Id";
	public final static String DATABASE_ROUTE_COMMENT_COMMENT_ID = "Comment_Id";
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
