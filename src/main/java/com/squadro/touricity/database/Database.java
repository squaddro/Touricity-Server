package com.squadro.touricity.database;

import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.filterQueries.RouteIdSelectionFromCity;
import com.squadro.touricity.database.query.filterQueries.RouteIdSelectionFromCostAndDuration;
import com.squadro.touricity.database.query.filterQueries.RouteIdSelectionFromLike;
import com.squadro.touricity.database.query.filterQueries.RouteIdSelectionFromTransportation;
import com.squadro.touricity.database.query.likeQueries.GetLikeInfoQuery;
import com.squadro.touricity.database.query.locationQueries.GetLocationInfoQuery;
import com.squadro.touricity.database.query.locationQueries.InsertNewLocationQuery;
import com.squadro.touricity.database.query.pipeline.IPipelinedQuery;
import com.squadro.touricity.database.query.pipeline.PipelinedQuery;
import com.squadro.touricity.database.query.routeQueries.*;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.Status;
import com.squadro.touricity.message.types.data.*;
import com.squadro.touricity.message.types.data.enumeration.PathType;
import com.squadro.touricity.message.types.data.enumeration.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.*;
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
	public final static String ENTRY_POINTER = "Pointer";
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

	public static IMessage getRouteIdsFromFilter(Filter filter) {

		final int expense = filter.getExpense();
		final String city_name = filter.getCity_name();
		final int duration = filter.getDuration();
		final int score = (int) filter.getScore();
		final int path_type = filter.getPath_type();

		RouteIdSelectionFromCity selectionFromCity = new RouteIdSelectionFromCity(city_name);
		RouteIdSelectionFromCostAndDuration selectionFromCostAndDuration = new RouteIdSelectionFromCostAndDuration(expense, duration);
		RouteIdSelectionFromTransportation selectionFromTransportation = new RouteIdSelectionFromTransportation(path_type);
		RouteIdSelectionFromLike selectionFromLike = new RouteIdSelectionFromLike(score);

		selectionFromCity.execute();
		selectionFromCostAndDuration.execute();
		selectionFromLike.execute();
		selectionFromTransportation.execute();

		HashSet<String> routeIds = new HashSet<String>(selectionFromCity.getList());
		routeIds.retainAll(selectionFromCostAndDuration.getList());
		routeIds.retainAll(selectionFromLike.getList());
		routeIds.retainAll(selectionFromTransportation.getList());

		List<Route> routeList = new ArrayList<>();
		Iterator<String> iterator = routeIds.iterator();
		while (iterator.hasNext()) {
			routeList.add(getRouteInfo(iterator.next()));
		}
		return new FilterResult(routeList);
	}

	public static IMessage deleteRoute(RouteId routeId) {
		DeleteRouteQuery deletingRouteQuery = new DeleteRouteQuery(routeId);
		deletingRouteQuery.execute();
		if(deletingRouteQuery.isSuccessfull()) {
			return Status.build(StatusCode.DELETE_ROUTE_SUCCESSFULL);
		}
		else
			return Status.build(StatusCode.DELETE_ROUTE_FAIL);
	}

	public static Location getLocationInfo(String location_id){
		GetLocationInfoQuery locationInfoQuery = new GetLocationInfoQuery(location_id);
		locationInfoQuery.execute();
		return locationInfoQuery.getLocation();
	}

	public static Like getLikeInfo(String like_id){
		GetLikeInfoQuery likeInfoQuery = new GetLikeInfoQuery(like_id);
		likeInfoQuery.execute();
		return likeInfoQuery.getLike();
	}

	public static Route insertRoute(Route route) {
		InsertNewRouteQuery insertNewRouteQuery = new InsertNewRouteQuery(route.getRoute_id(), route.getCreator(), route.getEntries(), route.getCity_id(), route.getTitle(), route.getPrivacy());
		insertNewRouteQuery.execute();

		return insertNewRouteQuery.getRoute();
	}

	public static Route getRouteInfo(String route_id){

		final AtomicReference<String> id = new AtomicReference<>(route_id);
		final AtomicReference<String> creator = new AtomicReference<String>();
		final AtomicReference<String> city_id = new AtomicReference<String>();
		final List<IEntry> stops = Collections.synchronizedList(new ArrayList<>());
		final List<IEntry> paths = Collections.synchronizedList(new ArrayList<>());
		List<IEntry> entries = new ArrayList<>();
		final AtomicReference<String> title = new AtomicReference<String>();
		final AtomicInteger privacy = new AtomicInteger();

		RouteInstancesSelectionFromRouteId routeInstancesSelectionFromRouteId = new RouteInstancesSelectionFromRouteId(route_id);
		routeInstancesSelectionFromRouteId.execute();

		creator.set(routeInstancesSelectionFromRouteId.getRoute().getCreator());
		title.set(routeInstancesSelectionFromRouteId.getRoute().getTitle());
		city_id.set(routeInstancesSelectionFromRouteId.getRoute().getCity_id());
		privacy.set(routeInstancesSelectionFromRouteId.getRoute().getPrivacy());

		StopListSelectionFromRouteId stopListSelectionFromRouteId = new StopListSelectionFromRouteId(route_id);
		PathListSelectionFromRouteId pathListSelectionFromRouteId = new PathListSelectionFromRouteId(route_id);

		stopListSelectionFromRouteId.execute();
		pathListSelectionFromRouteId.execute();

		stops.addAll(stopListSelectionFromRouteId.getList());
		paths.addAll(pathListSelectionFromRouteId.getList());

		entries = combineSortedStopsAndPaths(stops, paths);
		IEntry[] entriesArr = entries.toArray(new IEntry[entries.size()]);
		
		return new Route(creator.get(), id.get(), entriesArr, city_id.get(), title.get(), privacy.get());
	}

	private static List<IEntry> combineSortedStopsAndPaths(List<IEntry> stops, List<IEntry> paths) {
		List<IEntry> entries = new ArrayList<>();

		if(stops.size() > 0 && stops.get(0).getIndex() == 0){
			for(int i=0; i < stops.size() + paths.size() ; i++){
				int j = 0; //counter for stops.
				int k = 0; //counter for paths.
				if (i % 2 == 0) {
					entries.add(stops.get(j));
					j++;
				} else {
					entries.add(paths.get(k));
					k++;
				}
			}
		}
		else if (paths.size() > 0 && paths.get(0).getIndex() == 0) {
			for (int i = 0; i < stops.size() + paths.size() ; i++) {
				int j = 0; //counter for paths.
				int k = 0; //counter for stops.
				if (i % 2 == 0) {
					entries.add(paths.get(j));
					j++;
				} else {
					entries.add(stops.get(k));
					k++;
				}
			}
		}
		else
			entries = null;

		return entries;
	}
}
