package com.squadro.touricity.database;

import com.squadro.touricity.database.query.ISingleQuery;
import com.squadro.touricity.database.query.SelectionQuery;
import com.squadro.touricity.database.query.commentQueries.InsertNewCommentPipeline;
import com.squadro.touricity.database.query.commentQueries.SelectCommentFromCommentID;
import com.squadro.touricity.database.query.commentQueries.SelectCommentIDFromRouteID;
import com.squadro.touricity.database.query.commentQueries.SelectUsernameFromAccountId;
import com.squadro.touricity.database.query.filterQueries.RouteIdSelectionFromCity;
import com.squadro.touricity.database.query.filterQueries.RouteIdSelectionFromCostAndDuration;
import com.squadro.touricity.database.query.filterQueries.RouteIdSelectionFromTransportation;
import com.squadro.touricity.database.query.likeQueries.GetLikeInfoQuery;
import com.squadro.touricity.database.query.likeQueries.InsertNewLikePipeline;
import com.squadro.touricity.database.query.locationQueries.GetLocationInfoQuery;
import com.squadro.touricity.database.query.locationQueries.RouteSuggestionQuery;
import com.squadro.touricity.database.query.locationQueries.StopSuggestionQuery;
import com.squadro.touricity.database.query.pipeline.IPipelinedQuery;
import com.squadro.touricity.database.query.routeQueries.*;
import com.squadro.touricity.database.query.userQueries.*;
import com.squadro.touricity.database.result.QueryResult;
import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.Status;
import com.squadro.touricity.message.types.data.Suggestion;
import com.squadro.touricity.message.types.data.*;
import com.squadro.touricity.message.types.data.enumeration.PathType;
import com.squadro.touricity.message.types.data.enumeration.StatusCode;
import com.squadro.touricity.session.CreateAccountQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
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
		Connection conn = null;
		try {
			conn = connect();
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
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
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
		int path_type = filter.getPath_type();

		List<Integer> typeList = new ArrayList<>();
		if (path_type >= 4) {
			typeList.add(PathType.BUS.getValue());
			path_type -= 4;
		}
		if (path_type >= 2) {
			typeList.add(PathType.DRIVING.getValue());
			path_type -= 2;
		}
		if (path_type >= 1) {
			typeList.add(PathType.WALKING.getValue());
		}


		RouteIdSelectionFromCity selectionFromCity = new RouteIdSelectionFromCity(city_name);
		RouteIdSelectionFromCostAndDuration selectionFromCostAndDuration = new RouteIdSelectionFromCostAndDuration(expense, duration);
		RouteIdSelectionFromTransportation selectionFromTransportation = new RouteIdSelectionFromTransportation(typeList);
//		RouteIdSelectionFromLike selectionFromLike = new RouteIdSelectionFromLike(score);

		CountDownLatch countDownLatch = new CountDownLatch(3);
		new Thread(() -> {
			selectionFromCity.execute();
			countDownLatch.countDown();
		}).start();

		new Thread(() -> {
			selectionFromCostAndDuration.execute();
			countDownLatch.countDown();
		}).start();
/*
		new Thread(() -> {
			selectionFromLike.execute();
			countDownLatch.countDown();
		}).start();
*/
		new Thread(() -> {
			selectionFromTransportation.execute();
			countDownLatch.countDown();
		}).start();

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		HashSet<String> routeIds = new HashSet<String>(selectionFromCity.getList());
		routeIds.retainAll(selectionFromCostAndDuration.getList());
		//	routeIds.retainAll(selectionFromLike.getList());
		routeIds.retainAll(selectionFromTransportation.getList());

		List<String> routeIdList = new ArrayList<>(routeIds);
		List<RouteLike> routeList = new ArrayList<>();
		CountDownLatch countDownLatch2 = new CountDownLatch(routeIds.size());

		ExecutorService executor = Executors.newFixedThreadPool(16);
		for (String s : routeIdList) {
			executor.execute(new Thread(() -> {
				while (!checkConnection()) {
				}
				routeList.add(getRouteLikeInfo(s));
				countDownLatch2.countDown();
			}));
		}
		try {
			countDownLatch2.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new FilterResult(routeList);
	}

	public static IMessage deleteRoute(RouteId routeId) {
		DeleteRouteQuery deletingRouteQuery = new DeleteRouteQuery(routeId);
		deletingRouteQuery.execute();
		if (deletingRouteQuery.isSuccessfull()) {
			return Status.build(StatusCode.DELETE_ROUTE_SUCCESSFULL);
		} else
			return Status.build(StatusCode.DELETE_ROUTE_FAIL);
	}

	public static Location getLocationInfo(String location_id) {
		GetLocationInfoQuery locationInfoQuery = new GetLocationInfoQuery(location_id);
		locationInfoQuery.execute();
		return locationInfoQuery.getLocation();
	}

	public static Like getLikeInfo(String like_id) {
		GetLikeInfoQuery likeInfoQuery = new GetLikeInfoQuery(like_id);
		likeInfoQuery.execute();
		return likeInfoQuery.getLike();
	}

	public static IMessage insertComment(CommentRegister commentRegister){
		InsertNewCommentPipeline commentPipeline = new InsertNewCommentPipeline(commentRegister);
		Database.execute(commentPipeline);
		if(commentPipeline.isSuccessfull){
			postCommentToFcm(commentRegister);
			return Status.build(StatusCode.COMMENT_SUCCESSFUL);
		}
		return Status.build(StatusCode.COMMENT_REJECT);
	}

	public static CommentRegisterList getComment(RouteId routeId){
		SelectCommentIDFromRouteID selectCommentIDFromRouteID = new SelectCommentIDFromRouteID(routeId.getRoute_id());
		selectCommentIDFromRouteID.execute();
		List<CommentRegister> commentRegisterList = new ArrayList<CommentRegister>();
		if(selectCommentIDFromRouteID.isSuccessfull){
			ArrayList<String> list = (ArrayList<String>) selectCommentIDFromRouteID.getList();
			for(String s : list){
				SelectCommentFromCommentID selectCommentFromCommentID = new SelectCommentFromCommentID(s);
				selectCommentFromCommentID.execute();
				CommentRegister commentRegister = selectCommentFromCommentID.getCommentRegister();
				SelectUsernameFromAccountId selectUsernameFromAccountId = new SelectUsernameFromAccountId(commentRegister.getUsername(), commentRegister);
				selectUsernameFromAccountId.execute();
				commentRegisterList.add(selectCommentFromCommentID.getCommentRegister());
			}
		}
		CommentRegisterList list = new CommentRegisterList(commentRegisterList);
		return list;
	}

	public static IMessage insertLike(LikeRegister likeRegister){
		InsertNewLikePipeline likePipeline = new InsertNewLikePipeline(likeRegister);
		Database.execute(likePipeline);
		if(likePipeline.isSuccessfull){
			postLikeToFcm(likeRegister);
			return Status.build(StatusCode.LIKE_SUCCESSFUL);
		}
		return Status.build(StatusCode.LIKE_REJECT);
	}

	public static Suggestion suggestStops(BoundPoints boundPoints) {
		StopSuggestionQuery stopSuggestionQuery = new StopSuggestionQuery(boundPoints);
		stopSuggestionQuery.execute();
		return new Suggestion(stopSuggestionQuery.getLocationList());
	}

	public static RouteSuggestion suggestRoutes(Dummy dummy) {
		RouteSuggestionQuery routeSuggestionQuery = new RouteSuggestionQuery();
		routeSuggestionQuery.execute();
		ArrayList<String> ids = routeSuggestionQuery.getRouteIdList();

		RouteSuggestion routeSuggestion = new RouteSuggestion();

		for (String s:ids) {
			routeSuggestion.getRouteList().add(getRouteInfo(s));
		}
		return routeSuggestion;
	}


	public static IMessage signUp(String cookie, Credential userInfo) {
		String user = userInfo.getUser_name();
		String token = userInfo.getToken();

		if (user == null || token == null) {
			return Status.build(StatusCode.SIGNUP_REJECT);
		}

		UserCheckQuery userCheckQuery = new UserCheckQuery(user);
		userCheckQuery.execute();
		if (userCheckQuery.getDoesUserExists()) {
			return Status.build(StatusCode.SIGNUP_REJECT_USERNAME);
		}

		String newAccoutId = UUID.randomUUID().toString();
		CreateAccountQuery createAccountQuery = new CreateAccountQuery(newAccoutId);
		createAccountQuery.execute();

		CreateNewUserQuery createNewUserQuery = new CreateNewUserQuery(newAccoutId, user, token);
		createNewUserQuery.execute();

		/*LoginPipeline loginPipeline = new LoginPipeline(cookie, user, token);
		Database.execute(loginPipeline);*/

		return Status.build(StatusCode.SIGNUP_SUCCESSFUL);
	}

	public static IMessage signIn(String cookie, Credential userInfo) {
		String account_id = null;
		String user = userInfo.getUser_name();
		String token = userInfo.getToken();

		if (user == null || token == null) {
			return Status.build(StatusCode.SIGNIN_REJECT);
		}

		LoginQuery loginQuery = new LoginQuery(user, token);
		Database.execute(loginQuery);
		if (loginQuery.isSuccesfull)
			return Status.build(StatusCode.SIGNIN_SUCCESSFUL);
		else
			return Status.build(StatusCode.SIGNIN_REJECT);
	}

	public static void postLikeToFcm(LikeRegister likeRegister) {
		String user_name = likeRegister.getUsername();
		String route_id = likeRegister.getRouteId().getRoute_id();
		CreatorSelectionFromRouteId creatorSelectionFromRouteId = new CreatorSelectionFromRouteId(route_id);
		creatorSelectionFromRouteId.execute();
		String account_id = creatorSelectionFromRouteId.getCreator();
		GetTokenFromAccountId getTokenFromAccountId = new GetTokenFromAccountId(account_id);
		getTokenFromAccountId.execute();
		String token = getTokenFromAccountId.getUserPassword();

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String key = "AAAAKO3rtsw:APA91bEnHnMHXGg54aiQSfd6Vq7iUMCNrewtd4oQGhvXxv-wWLfy21vGKXj4N0Vg9Nrt2Q6CscKVHPtcOB-WARJ6Q_vgW1Va2xiyCSeFWQKIkFGtWuvjaGR0fBYC4wa2ZJ7VgWZEyDvt";
		headers.add("Authorization", "key=" + key);

		JSONObject obj = new JSONObject();
		obj.put("to", token);

		JSONObject innerObj = new JSONObject();
		innerObj.put("title", "Touricity");
		innerObj.put("body",user_name + " liked your route!");
		obj.put("notification", innerObj);

		String url = "https://fcm.googleapis.com/fcm/send";

		HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
		restTemplate.postForObject(url, request, String.class);
	}

	public static void postCommentToFcm(CommentRegister commentRegister) {
		String user_name = commentRegister.getUsername();
		String route_id = commentRegister.getRouteId().getRoute_id();
		CreatorSelectionFromRouteId creatorSelectionFromRouteId = new CreatorSelectionFromRouteId(route_id);
		creatorSelectionFromRouteId.execute();
		String account_id = creatorSelectionFromRouteId.getCreator();
		GetTokenFromAccountId getTokenFromAccountId = new GetTokenFromAccountId(account_id);
		getTokenFromAccountId.execute();
		String token = getTokenFromAccountId.getUserPassword();

		String comment = commentRegister.getComment().getCommentDesc();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String key = "AAAAKO3rtsw:APA91bEnHnMHXGg54aiQSfd6Vq7iUMCNrewtd4oQGhvXxv-wWLfy21vGKXj4N0Vg9Nrt2Q6CscKVHPtcOB-WARJ6Q_vgW1Va2xiyCSeFWQKIkFGtWuvjaGR0fBYC4wa2ZJ7VgWZEyDvt";
		headers.add("Authorization", "key=" + key);

		JSONObject obj = new JSONObject();
		obj.put("to", token);

		JSONObject innerObj = new JSONObject();
		innerObj.put("title", "Touricity");
		innerObj.put("body",user_name + " commented : " + comment);
		obj.put("notification", innerObj);

		String url = "https://fcm.googleapis.com/fcm/send";

		HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
		restTemplate.postForObject(url, request, String.class);
	}
	public static Route insertRoute(RouteRegister routeRegister) {
		InsertNewRouteQuery insertNewRouteQuery = new InsertNewRouteQuery(routeRegister.getRoute(), routeRegister.getUsername());
		insertNewRouteQuery.execute();
		Route route = insertNewRouteQuery.getRoute();

		InsertDefaultLikeQuery insertDefaultLikeQuery = new InsertDefaultLikeQuery(route.getCreator(),route.getRoute_id());
		Database.execute(insertDefaultLikeQuery);
		return route;
	}

	public static RouteLike getRouteLikeInfo(String route_id) {

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

		LikeIdSelectionFromRouteId likeIdSelectionFromRouteId = new LikeIdSelectionFromRouteId(id.get());
		Database.execute(likeIdSelectionFromRouteId);
		List<String> like_id = likeIdSelectionFromRouteId.getList();
		int likeNum = like_id.size();
		int likeSum = 0;
		for(String s : like_id){
			ScoreSelectionFromLikeId scoreSelectionFromLikeId = new ScoreSelectionFromLikeId(s);
			scoreSelectionFromLikeId.execute();
			likeSum = likeSum + scoreSelectionFromLikeId.getScore();
		}

		double likeScore = 0;
		if(likeNum!=0){
			likeScore = likeSum / likeNum;
		}

		Route route = new Route(creator.get(), id.get(), entriesArr, city_id.get(), title.get(), privacy.get());
		RouteLike routeLike = new RouteLike(route, likeScore);
		return routeLike;
	}

	public static Route getRouteInfo(String route_id) {

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

		int j = 0; //counter for stops.
		int k = 0; //counter for paths.
		if (stops.size() > 0 && stops.get(0).getIndex() == 0) {
			for (int i = 0; i < stops.size() + paths.size(); i++) {
				if (i % 2 == 0) {
					entries.add(stops.get(j));
					j++;
				} else {
					entries.add(paths.get(k));
					k++;
				}
			}
		} else if (paths.size() > 0 && paths.get(0).getIndex() == 0) {
			for (int i = 0; i < stops.size() + paths.size(); i++) {
				if (i % 2 == 0) {
					entries.add(paths.get(j));
					j++;
				} else {
					entries.add(stops.get(k));
					k++;
				}
			}
		} else
			entries = null;

		return entries;
	}
}