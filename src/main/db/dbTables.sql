DROP TABLE IF EXISTS DB_ACCOUNT;
DROP TABLE IF EXISTS DB_USER;
DROP TABLE IF EXISTS DB_SESSION;
DROP TABLE IF EXISTS DB_ROUTE;
DROP TABLE IF EXISTS DB_ENTRY;
DROP TABLE IF EXISTS DB_STOP;
DROP TABLE IF EXISTS DB_PATH;
DROP TABLE IF EXISTS DB_LOCATION;
DROP TABLE IF EXISTS DB_LIKE;
DROP TABLE IF EXISTS DB_COMMENT;
DROP TABLE IF EXISTS DB_CITY;
DROP TABLE IF EXISTS DB_SUGGESTED;

CREATE TABLE DB_ACCOUNT (
       Account_Id char(36)
);

CREATE TABLE DB_USER (
       Account_Id char(36),
       Username varchar(20),
       User_Password varchar(200)
);

CREATE TABLE DB_SESSION (
       Account_Id char(36),
       Session_Id char(36)
);

CREATE TABLE DB_ROUTE (
       Creator char(36),
       Route_Id char(36),
       City_Id char(36),
       Title varchar(70),
       Route_Desc varchar(1000),
       Privacy integer
);

CREATE TABLE DB_ENTRY (
       Route_Id char(36),
       Stop_Id char(36),
       Path_Id char(36),
       Entry_Id char(36),
       Expense integer,
       Duration integer,
       Comment_desc varchar(1000),
       Pointer integer
);
   
CREATE TABLE DB_PATH (
       Path_Id char(36),
       Path_Type integer,
       Vertices text
);

CREATE TABLE DB_STOP (
       Location_Id text,
       Stop_Id char(36)
);

CREATE TABLE DB_LOCATION (
       Location_Id text,
       City_Id char(36),
       Latitude double precision,
       Longitude double precision
);

CREATE TABLE DB_LIKE (
       Account_Id char(36),
       Like_Id char(36),
       Score integer
);

CREATE TABLE DB_COMMENT (
       Account_Id char(36),
       Comment_Id char(36),
       Comment_desc varchar(1000)
);

CREATE TABLE DB_CITY (
        City_Id char(36),
        City_Name varchar(100)
);

CREATE TABLE DB_LOCATION_LIKE (
       Location_Id text,
       Like_Id char(36)
);

CREATE TABLE DB_LOCATION_COMMENT (
       Location_Id text,
       Comment_Id char(36)
);

CREATE TABLE DB_ROUTE_LIKE (
       Route_Id char(36),
       Like_Id char(36)
);

CREATE TABLE DB_ROUTE_COMMENT (
       Route_Id char(36),
       Comment_Id char(36)
);

CREATE TABLE DB_SUGGESTED (
suggested_id char(36)
);

ALTER TABLE DB_ACCOUNT
ADD CONSTRAINT ACCOUNT_PK
PRIMARY KEY (Account_Id);

ALTER TABLE DB_SESSION 
ADD CONSTRAINT SESSION_PK 
PRIMARY KEY (Session_Id);
	 
ALTER TABLE DB_ROUTE 
ADD CONSTRAINT ROUTE_PK 
PRIMARY KEY (Route_Id);
	 
ALTER TABLE DB_ENTRY
ADD CONSTRAINT ENTRY_PK 
PRIMARY KEY (Entry_Id);
	 
ALTER TABLE DB_PATH
ADD CONSTRAINT PATH_PK 
PRIMARY KEY (Path_Id);

ALTER TABLE DB_STOP 
ADD CONSTRAINT STOP_PK 
PRIMARY KEY (Stop_Id);
	 
ALTER TABLE DB_LOCATION
ADD CONSTRAINT LOCATION_PK 
PRIMARY KEY (Location_Id);
	
ALTER TABLE DB_LIKE 
ADD CONSTRAINT LIKE_PK 
PRIMARY KEY (Like_Id);
	 
ALTER TABLE DB_COMMENT
ADD CONSTRAINT COMMENT_PK 
PRIMARY KEY (Comment_Id);

ALTER TABLE DB_CITY
ADD CONSTRAINT CITY_PK
PRIMARY KEY (City_Id);
	 
ALTER TABLE DB_USER 
ADD CONSTRAINT Account_Id_FK
FOREIGN KEY (Account_Id)
REFERENCES DB_ACCOUNT(Account_Id)
ON DELETE CASCADE;

ALTER TABLE DB_SESSION 
ADD CONSTRAINT Session_FK
FOREIGN KEY (Account_Id)
REFERENCES DB_ACCOUNT(Account_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ROUTE 
ADD CONSTRAINT User_Id_FK
FOREIGN KEY (Creator)
REFERENCES DB_ACCOUNT(Account_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ROUTE
ADD CONSTRAINT Route_City_Id_FK
FOREIGN KEY (City_Id)
REFERENCES DB_CITY(City_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ENTRY
ADD CONSTRAINT Route_Id_FK
FOREIGN KEY (Route_Id)
REFERENCES DB_ROUTE(Route_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ENTRY
ADD CONSTRAINT Stop_Id_FK
FOREIGN KEY (Stop_Id)
REFERENCES DB_STOP(Stop_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ENTRY
ADD CONSTRAINT Path_Id_FK
FOREIGN KEY (Path_Id)
REFERENCES DB_PATH(Path_Id)
ON DELETE CASCADE;

ALTER TABLE DB_STOP 
ADD CONSTRAINT Location_Id_FK
FOREIGN KEY (Location_Id)
REFERENCES DB_LOCATION(Location_Id)
ON DELETE CASCADE;

ALTER TABLE DB_LIKE 
ADD CONSTRAINT Account_Id_Like_FK
FOREIGN KEY (Account_Id)
REFERENCES DB_ACCOUNT(Account_Id)
ON DELETE CASCADE;

ALTER TABLE DB_COMMENT 
ADD CONSTRAINT Acc_Id_Comm_FK
FOREIGN KEY (Account_Id)
REFERENCES DB_ACCOUNT(Account_Id)
ON DELETE CASCADE;

ALTER TABLE DB_LOCATION_LIKE
ADD CONSTRAINT Loc_Id_Like_FK
FOREIGN KEY (Location_Id)
REFERENCES DB_LOCATION(Location_Id)
ON DELETE CASCADE;

ALTER TABLE DB_LOCATION_LIKE
ADD CONSTRAINT Like_Id_Loc_FK
FOREIGN KEY (Like_Id)
REFERENCES DB_LIKE(Like_Id)
ON DELETE CASCADE;

ALTER TABLE DB_LOCATION_COMMENT
ADD CONSTRAINT Loc_Id_Comm_FK
FOREIGN KEY (Location_Id)
REFERENCES DB_LOCATION(Location_Id)
ON DELETE CASCADE;

ALTER TABLE DB_LOCATION_COMMENT
ADD CONSTRAINT Comm_Id_Loc_FK
FOREIGN KEY (Comment_Id)
REFERENCES DB_COMMENT(Comment_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ROUTE_LIKE
ADD CONSTRAINT Route_Id_Like_FK
FOREIGN KEY (Route_Id)
REFERENCES DB_ROUTE(Route_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ROUTE_LIKE
ADD CONSTRAINT Like_Id_Route_FK
FOREIGN KEY (Like_Id)
REFERENCES DB_LIKE(Like_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ROUTE_COMMENT
ADD CONSTRAINT Route_Id_Comm_FK
FOREIGN KEY (Route_Id)
REFERENCES DB_ROUTE(Route_Id)
ON DELETE CASCADE;

ALTER TABLE DB_ROUTE_COMMENT
ADD CONSTRAINT Comm_Id_Route_FK
FOREIGN KEY (Comment_Id)
REFERENCES DB_COMMENT(Comment_Id)
ON DELETE CASCADE;