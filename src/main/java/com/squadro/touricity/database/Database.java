package com.squadro.touricity.database;

import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.IPipelinedQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.*;
import com.squadro.touricity.message.types.data.enumeration.PathType;
import com.squadro.touricity.message.types.data.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

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

	private boolean executeQuery(ISingleQuery query){
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

	public static Route getRouteInfo(String route_id) {

		final AtomicInteger pathsLength = new AtomicInteger(0);
		final AtomicInteger stopsLength = new AtomicInteger(0);

		final AtomicReference<String> id = new AtomicReference<String>();
		final AtomicReference<String> creator = new AtomicReference<String>();
		final AtomicReference<String> city_id = new AtomicReference<String>();
		final AtomicReferenceArray<IEntry> stops  = new AtomicReferenceArray<IEntry>(50);
		final AtomicReference<String> title = new AtomicReference<String>();
		final AtomicReferenceArray<IEntry> paths  = new AtomicReferenceArray<IEntry>(50);
		final AtomicInteger privacy = new AtomicInteger();
		final AtomicReferenceArray<IEntry> entries = new AtomicReferenceArray<IEntry>(100);

		id.set(route_id);

		getInstance().execute(new PipelinedQuery(){

			protected void PrepareQueue(Queue<ISingleQuery> queue) {

				queue.add(new SelectionQuery() {

					public String getQuery() {
						String routeIdQuery = "SELECT * FROM DB_ROUTE WHERE ROUTE_ID = " + id.get();
						return routeIdQuery;
					}

					public boolean onResult(QueryResult result) throws SQLException {

						ResultSet rs = result.getResultSet();

						creator.set(rs.getString(ROUTE_CREATOR));
						title.set(rs.getString(ROUTE_TITLE));
						city_id.set(rs.getString(CITY_CITY_ID));
						privacy.set(rs.getInt(ROUTE_PRIVACY));

						return true;
					}
				});

				//stops ->
				queue.add(new SelectionQuery() {
					public String getQuery() {
						String stopQuery = "SELECT EXPENSE,DURATION,COMMENT_DESC,STOP_ID,POINTER FROM ENTRY WHERE ROUTE_ID = " + id.get() + "AND STOP_ID IS NOT NULL ORDER BY POINTER ASC";
						return stopQuery;
					}

					public boolean onResult(QueryResult result) throws SQLException {
						ResultSet rs = result.getResultSet();
						Stop tmpStop;

						while(rs.next()){
							tmpStop = new Stop();
							tmpStop.setStop_id(rs.getString(STOP_STOP_ID));
							tmpStop.setExpense(rs.getInt(ENTRY_EXPENSE));
							tmpStop.setDuration(rs.getInt(ENTRY_DURATION));
							tmpStop.setComment(rs.getString(ENTRY_COMMENT_DESCRIPTION));
							tmpStop.setIndex(rs.getInt("POINTER"));

							stops.set(stopsLength.get() , tmpStop);
							stopsLength.set(stopsLength.get() + 1);
							tmpStop = null;
						}
						return true;
					}
				});

				final AtomicInteger stopCounter = new AtomicInteger(0);
				while(stopCounter.get() < stopsLength.get()){

					queue.add(new SelectionQuery() {
						public String getQuery() {
							String stop_locationIdQuery = "SELECT LOCATION_ID FROM DB_STOP WHERE STOP_ID = " + ((Stop) stops.get(stopCounter.get())).getStop_id();
							return stop_locationIdQuery;
						}
						public boolean onResult(QueryResult result) throws SQLException {
							ResultSet rs = result.getResultSet();
							((Stop)(stops.get(stopCounter.get()))).setLocation_id(rs.getString(STOP_LOCATION_ID));
							return true;
						}
					});
					stopCounter.set(stopCounter.get()+1);
				}
				//////paths ->
				queue.add(new SelectionQuery() {
					public String getQuery() {
						String pathQuery = "SELECT EXPENSE,DURATION,COMMENT_DESC,PATH_ID,POINTER FROM ENTRY WHERE ROUTE_ID = " + id.get() + "AND PATH_ID IS NOT NULL ORDER BY POINTER ASC";
						return pathQuery;
					}

					public boolean onResult(QueryResult result) throws SQLException {
						ResultSet rs = result.getResultSet();

						Path tmpPath;

						while(rs.next()){
							tmpPath = new Path();
							tmpPath.setPath_id(rs.getString(PATH_PATH_ID));
							tmpPath.setExpense(rs.getInt(ENTRY_EXPENSE));
							tmpPath.setDuration(rs.getInt(ENTRY_DURATION));
							tmpPath.setComment(rs.getString(ENTRY_COMMENT_DESCRIPTION));
							tmpPath.setIndex(rs.getInt("POINTER"));

							paths.set(pathsLength.get() , tmpPath);
							tmpPath = null;
						}
						return true;
					}
				});

				final AtomicInteger pathCounter = new AtomicInteger(0);
				while(pathCounter.get() < pathsLength.get()){

					queue.add(new SelectionQuery() {
						public String getQuery() {
							String pathType_VerticesQuery = "SELECT PATH_TYPE, VERTICES FROM DB_PATH WHERE PATH_ID = " + ((Path) paths.get(pathCounter.get())).getPath_id();
							return pathType_VerticesQuery;
						}
						public boolean onResult(QueryResult result) throws SQLException {

							ResultSet rs = result.getResultSet();
							((Path)(paths.get(pathCounter.get()))).setPath_type(PathType.values()[rs.getInt(PATH_PATH_TYPE)]);
							((Path)(paths.get(pathCounter.get()))).setVertices(byteArrayToVerticesArray(rs.getBytes(PATH_VERTICES)));
							return true;
						}
					});
					pathCounter.set(pathCounter.get()+1);
				}
			}
		});

		if(stops.get(0).getIndex() == 0){
			for(int i = 0; i < stopsLength.get() + pathsLength.get() ; i++){
				int j = 0; //counter for stops.
				int k = 0; //counter for paths.
				if(i % 2 == 0){
					entries.set(i, stops.get(j));
					j++;
				}
				else{
					entries.set(i,paths.get(k));
					k++;
				}
			}
		}
		else if(paths.get(0).getIndex() == 0){

			for(int i = 0; i < stopsLength.get() + pathsLength.get() ; i++){
				int j = 0; //counter for paths.
				int k = 0; //counter for stops.

				if(i % 2 == 0){
					entries.set(i, paths.get(j));
					j++;
				}
				else{
					entries.set(i,stops.get(k));
					k++;
				}
			}
		}
		IEntry[] entries2return = new IEntry[entries.length()];

		for(int i = 0; i < stopsLength.get() + pathsLength.get() ; i++){
			entries2return[i] = entries.get(i);
		}

		Route route = new Route(creator.get(), id.get(), entries2return, city_id.get(), title.get(), privacy.get());
		return route;
	}

	private static IPath.PathVertex[] byteArrayToVerticesArray(byte[] bytes) {
		ByteArrayInputStream in;
		ObjectInputStream is = null;
		IPath.PathVertex[] arr = null;
		try{
			in = new ByteArrayInputStream(bytes);
			is = new ObjectInputStream(in);
			arr = (IPath.PathVertex[]) is.readObject();
		}catch (Exception e){
			e.getStackTrace();
		}
		return arr;
	}
	
}
