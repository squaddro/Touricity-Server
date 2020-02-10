package com.squadro.touricity.database;

import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.pipeline.IPipelinedQuery;
import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.data.*;
import com.squadro.touricity.message.types.data.enumeration.PathType;
import com.squadro.touricity.session.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
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
		if(instance == null) {
			synchronized (Database.class) {
				if(instance==null)
					instance = new Database();
			}
		}
		return instance;
	}

	private boolean executeQuery(ISingleQuery query) throws IOException, ClassNotFoundException {
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

	public static void execute(ISingleQuery query) throws IOException, ClassNotFoundException {
		getInstance().executeQuery(query);
	}

	public static void execute(IPipelinedQuery query) throws IOException, ClassNotFoundException {
		Queue<ISingleQuery> queue = query.getQueries();
		while(!queue.isEmpty()) {
			if(!getInstance().executeQuery(queue.poll()))
				break;
		}
	}

	public static boolean checkConnection() throws IOException, ClassNotFoundException {
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

	public static Route getRouteInfo(String route_id) throws IOException, ClassNotFoundException {

		final AtomicReference<String> id = null;
		final AtomicReference<String> creator = null;
		final AtomicReference<String> city_id = null;
		final AtomicReference<String> title = null;
		final AtomicReferenceArray<IEntry> stops  = new AtomicReferenceArray<IEntry>(50);
		final AtomicReferenceArray<IEntry> paths  = new AtomicReferenceArray<IEntry>(50);
		final AtomicReferenceArray<IEntry> entries = new AtomicReferenceArray<IEntry>(100);
		final AtomicInteger privacy = null;

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
						city_id.set(rs.getString(CITY_CITY_ID));
						title.set(rs.getString(ROUTE_TITLE));
						privacy.set(rs.getInt(ROUTE_PRIVACY));

						return true;
					}
				});

				//stops ->

				queue.add(new SelectionQuery() {
					public String getQuery() {
						String stopQuery = "SELECT EXPENSE,DURATION,COMMENT_DESC,STOP_ID,INDEX FROM ENTRY WHERE ROUTE_ID = " + id.get() + "AND STOP_ID IS NOT NULL ORDER BY INDEX ASC";
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
							tmpStop.setIndex(rs.getInt("index"));

							stops.set(stops.length() , tmpStop);
							tmpStop = null;
						}

						return true;
					}
				});

				final AtomicInteger stopCounter = null;
				stopCounter.set(0);
				while(stopCounter.get() < stops.length()){

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
						String pathQuery = "SELECT EXPENSE,DURATION,COMMENT_DESC,PATH_ID,INDEX FROM ENTRY WHERE ROUTE_ID = " + id.get() + "AND PATH_ID IS NOT NULL ORDER BY INDEX ASC";
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
							tmpPath.setIndex(rs.getInt("index"));

							paths.set(paths.length() , tmpPath);
							tmpPath = null;
						}

						return true;
					}
				});

				final AtomicInteger pathCounter = null;
				pathCounter.set(0);
				while(pathCounter.get() < paths.length()){

					queue.add(new SelectionQuery() {
						public String getQuery() {
							String pathType_VerticesQuery = "SELECT PATH_TYPE, VERTICES FROM DB_PATH WHERE PATH_ID = " + ((Path) paths.get(pathCounter.get())).getPath_id();
							return pathType_VerticesQuery;
						}

						public boolean onResult(QueryResult result) throws SQLException, IOException, ClassNotFoundException {

							ResultSet rs = result.getResultSet();
							((Path)(paths.get(pathCounter.get()))).setPath_type(PathType.values()[rs.getInt(PATH_PATH_TYPE)]);
							((Path)(paths.get(pathCounter.get()))).setVertices(byteArrayToVerticesArray(rs.getBytes(PATH_VERTICES)));  //PathType.values()[rs.getInt(PATH_PATH_TYPE)]
							return true;
						}
					});
					pathCounter.set(pathCounter.get()+1);
				}
			}
		});

		if(stops.get(0).getIndex() == 0){

			for(int i = 0; i <stopsLength + pathsLength ; i++){
				int j = 0; //counter for stops.
				int k = 0; //counter for paths.

				if(i%2 == 0){
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

			for(int i = 0; i <stopsLength + pathsLength ; i++){
				int j = 0; //counter for paths.
				int k = 0; //counter for stops.

				if(i%2 == 0){
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

		for(int i = 0; i<stops.length(); i++){
			entries2return[i] = entries.get(i);
		}

		Route route = new Route(creator.get(), id.get(), entries2return, city_id.get(), title.get(), privacy.get());

		//TODO: atomic arraylerin initializationlarını kontrol et.

		return route;
	}

	private static IPath.PathVertex[] byteArrayToVerticesArray(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		ObjectInputStream is = new ObjectInputStream(in);
		return (IPath.PathVertex[]) is.readObject();
	}





}
