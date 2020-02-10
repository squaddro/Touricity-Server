package com.squadro.touricity.database;

import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.IPipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Database {

	private static Database instance;
	private static final Logger logger = LoggerFactory.getLogger(Database.class);

	private final static String ENVIRONMENT_DATABASE_URL = "DB_URL";

	public final static String ACCOUNT = "DB_ACCOUNT";
	public final static String ACCOUNT_ACCOUNT_ID = "Account_Id";
	public final static String USER = "DB_USER";
	public final static String USER_ACCOUNT_ID = "Account_Id";
	public final static String USER_USERNAME = "Username";
	public final static String USER_PASSWORD = "User_Password";
	public final static String SESSION = "DB_SESSION";
	public final static String SESSION_ACCOUNT_ID = "Account_Id";
	public final static String SESSION_SESSION_ID = "Session_Id";
	public final static String ROUTE = "DB_ROUTE";
	public final static String ROUTE_CREATOR = "Creator";
	public final static String ROUTE_ROUTE_ID = "Route_Id";
	public final static String ROUTE_CITY_ID = "City_Id";
	public final static String ROUTE_TITLE = "Title";
	public final static String ROUTE_DESC = "Route_Desc";
	public final static String ROUTE_PRIVACY = "Privacy";
	public final static String ENTRY = "DB_ENTRY";
	public final static String ENTRY_ROUTE_ID = "Route_Id";
	public final static String ENTRY_STOP_ID = "Stop_Id";
	public final static String ENTRY_PATH_ID = "Path_Id";
	public final static String ENTRY_ENTRY_ID = "Entry_Id";
	public final static String ENTRY_EXPENSE = "Expense";
	public final static String ENTRY_DURATION = "Duration";
	public final static String ENTRY_COMMENT_DESCRIPTION = "Comment_desc";
	public final static String PATH = "DB_PATH";
	public final static String PATH_PATH_ID = "Path_Id";
	public final static String PATH_PATH_TYPE = "Path_Type";
	public final static String PATH_VERTICES = "Vertices";
	public final static String STOP = "DB_STOP";
	public final static String STOP_LOCATION_ID = "Location_Id";
	public final static String STOP_STOP_ID = "Stop_Id";
	public final static String LOCATION = "DB_LOCATION";
	public final static String LOCATION_CITY_ID = "City_Id";
	public final static String LOCATION_LOCATION_ID = "Location_Id";
	public final static String LOCATION_LATITUDE = "Latitude";
	public final static String LOCATION_LONGITUDE = "Longitude";
	public final static String LIKE = "DB_LIKE";
	public final static String LIKE_ACCOUNT_ID = "Account_Id";
	public final static String LIKE_LIKE_ID = "Like_Id";
	public final static String LIKE_SCORE = "Score";
	public final static String COMMENT = "DB_COMMENT";
	public final static String COMMENT_ACCOUNT_ID = "Account_Id";
	public final static String COMMENT_COMMENT_ID = "Comment_Id";
	public final static String COMMENT_COMMENT_DESCRIPTION = "Comment_desc";
	public final static String CITY = "DB_CITY";
	public final static String CITY_CITY_ID = "City_Id";
	public final static String CITY_CITY_NAME = "City_Name";
	public final static String LOCATION_LIKE = "DB_LOCATION_LIKE";
	public final static String LOCATION_LIKE_LOCATION_ID = "Location_Id";
	public final static String LOCATION_LIKE_LIKE_ID = "Like_Id";
	public final static String LOCATION_COMMENT = "DB_LOCATION_COMMENT";
	public final static String LOCATION_COMMENT_LOCATION_ID = "Location_Id";
	public final static String LOCATION_COMMENT_COMMENT_ID = "Comment_Id";
	public final static String ROUTE_LIKE = "DB_ROUTE_LIKE";
	public final static String ROUTE_LIKE_ROUTE_ID = "Route_Id";
	public final static String ROUTE_LIKE_LIKE_ID = "Like_Id";
	public final static String ROUTE_COMMENT = "DB_ROUTE_COMMENT";
	public final static String ROUTE_COMMENT_ROUTE_ID = "Route_Id";
	public final static String ROUTE_COMMENT_COMMENT_ID = "Comment_Id";

	private String databaseUrl;

	private Database() {
		databaseUrl = System.getenv(ENVIRONMENT_DATABASE_URL);
	}

	private Connection connect() throws SQLException {
		return DriverManager.getConnection(databaseUrl);
	}

	public static Database getInstance() {
		if (instance == null) {
			synchronized (Database.class) {
				if (instance == null)
					instance = new Database();
			}
		}
		return instance;
	}

	private boolean executeQuery(ISingleQuery query) {
		String queryStr = query.getQuery();
		logger.info("Execute: " + queryStr);
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
			logger.info("Query error: " + e.toString());
			return false;
		}
	}

	public static void execute(ISingleQuery query) {
		getInstance().executeQuery(query);
	}

	public static void execute(IPipelinedQuery query) {
		Queue<ISingleQuery> queue = query.getQueries();
		while (!queue.isEmpty()) {
			if (!getInstance().executeQuery(queue.poll()))
				break;
		}
	}

	public static boolean checkConnection() {
		final AtomicBoolean connectionCheck = new AtomicBoolean();

		getInstance().execute(new SelectionQuery() {
			public String getQuery() {
				return "SELECT * FROM PG_TABLES";
			}

			public boolean onResult(QueryResult result) {
				logger.info("Connected to the database!");
				connectionCheck.set(true);
				return false;
			}

			@Override
			public void onError(String e) {
				logger.error("Couldn't connect to the database!");
				connectionCheck.set(false);
			}
		});

		return connectionCheck.get();
	}

	public static String value(String content) {
		return "'" + content + "'";
	}

	public static List<String> getRouteIdsFromFilter(Filter filter) {

		final int expense = filter.getExpense();
		final String city_name = filter.getCity_name();
		final int duration = filter.getDuration();
		final int score = (int) filter.getScore();
		final int path_type = filter.getPath_type();

		HashSet<String> routeIds = new HashSet<String>(getRouteIdsFromCity(city_name));
		routeIds.retainAll(getRouteIdsFromCostAndDuration(expense, duration));
		routeIds.retainAll(getRouteIdsFromLike(score));
		routeIds.retainAll(getRouteIdsFromTransportation(path_type));
		return new ArrayList<String>(routeIds);
	}

	private static List<String> getRouteIdsFromTransportation(final int transportation) {
		final List<String> list = new ArrayList<String>();
		getInstance().execute(new SelectionQuery() {
			public String getQuery() {
				return "SELECT route_id" +
						"FROM DB_ENTRY " +
						"INNER JOIN DB_ROUTE ON DB_ENTRY.route_id = DB_ROUTE.route_id" +
						"INNER JOIN DB_PATH ON DB_ENTRY.path_id = DB_PATH.path_id" +
						"WHERE DB_PATH.path_type = " + transportation;
			}

			public boolean onResult(QueryResult result) throws SQLException {
				while (result.getResultSet().next()) {
					list.add(result.getResultSet().getString("route_id"));
				}
				return false;
			}
		});
		return list;
	}

	private static List<String> getRouteIdsFromLike(final int minRate) {
		final List<String> list = new ArrayList<String>();
		getInstance().execute(new SelectionQuery() {
			public String getQuery() {
				return "SELECT route_id" +
						"FROM DB_ROUTE_LIKE " +
						"INNER JOIN DB_ROUTE ON DB_ROUTE.route_id = DB_ROUTE_LIKE.route_id" +
						"INNER JOIN DB_LIKE ON DB_ROUTE_LIKE.like_id = DB_LIKE.like_id" +
						"WHERE DB_LIKE.score >= " + minRate;
			}

			public boolean onResult(QueryResult result) throws SQLException {
				while (result.getResultSet().next()) {
					list.add(result.getResultSet().getString("route_id"));
				}
				return false;
			}
		});
		return list;
	}

	private static List<String> getRouteIdsFromCostAndDuration(final int averageCost, final int duration) {
		final List<String> list = new ArrayList<String>();
		getInstance().execute(new SelectionQuery() {
			public String getQuery() {
				return "SELECT route_id,SUM(expense) AS totalExpense, SUM(duration) AS totalDuration " +
						"FROM DB_ROUTE INNER JOIN DB_ENTRY ON DB_ROUTE.route_id = DB_ENTRY.route_id" +
						"HAVING totalExpense <= " + averageCost + "AND totalDuration <= " + duration;
			}

			public boolean onResult(QueryResult result) throws SQLException {
				while (result.getResultSet().next()) {
					list.add(result.getResultSet().getString("route_id"));
				}
				return false;
			}
		});
		return list;
	}

	private static List<String> getRouteIdsFromCity(final String city) {
		final List<String> list = new ArrayList<String>();
		getInstance().execute(new SelectionQuery() {
			public String getQuery() {
				String cityQuery = "SELECT city_id FROM DB_CITY WHERE " + "city_name" + "=" + city.toUpperCase();
				return "SELECT route_id FROM DB_ROUTE WHERE " + "(" + cityQuery + ") AND privacy = 0";
			}

			public boolean onResult(QueryResult result) throws SQLException {
				while (result.getResultSet().next()) {
					list.add(result.getResultSet().getString("route_id"));
				}
				return false;
			}
		});
		return list;
	}
}
