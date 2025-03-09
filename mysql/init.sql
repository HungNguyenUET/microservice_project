-- Drop the database if it already exists
DROP DATABASE IF EXISTS microservice;
-- Create database
CREATE DATABASE IF NOT EXISTS microservice;
USE microservice;

-- Create table department
DROP TABLE IF EXISTS department;
CREATE TABLE IF NOT EXISTS department (
    id 						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name 					NVARCHAR(100) NOT NULL UNIQUE KEY,
    total_member			INT	UNSIGNED,
    type					ENUM('DEV','TEST','SCRUM_MASTER','PM') NOT NULL,
    created_at      		DATETIME DEFAULT NOW()
);

-- create table: account
DROP TABLE IF EXISTS account;
CREATE TABLE account(
    id						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username				VARCHAR(50) NOT NULL UNIQUE KEY,
    firstname				VARCHAR(50),
    lastname				VARCHAR(50),
    department_id 			INT UNSIGNED NOT NULL,
    FOREIGN KEY(department_id) REFERENCES department(id)
);

-- Create table users
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id bigint NOT NULL AUTO_INCREMENT,
    email varchar(50) NOT NULL,
    password varchar(120) NOT NULL,
    username varchar(20) NOT NULL,
    firstname varchar(20),
    lastname varchar(20),
    access_token varchar(255),
    refresh_token varchar(255),
    role ENUM('USER','ADMIN','MANAGER') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (username),
    UNIQUE KEY (email)
 );

  -- Create table refreshtoken
 DROP TABLE IF EXISTS refreshtoken;
 CREATE TABLE refreshtoken (
   id bigint NOT NULL,
   expiry_date datetime(6) NOT NULL,
   token varchar(255) NOT NULL,
   user_id bigint DEFAULT NULL,
   PRIMARY KEY (id),
   UNIQUE KEY (token),
   UNIQUE KEY (user_id),
   CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id)
 );

 -- Create table refreshtoken_seq
 DROP TABLE IF EXISTS refreshtoken_seq;
 CREATE TABLE refreshtoken_seq (
   next_val bigint DEFAULT NULL
 );

 -- Create table roles
 DROP TABLE IF EXISTS roles;
 CREATE TABLE roles (
   id int NOT NULL AUTO_INCREMENT,
   name enum('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER') DEFAULT NULL,
   PRIMARY KEY (id)
 );


 -- Create table user_roles
 DROP TABLE IF EXISTS user_roles;
 CREATE TABLE user_roles (
   user_id bigint NOT NULL,
   role_id int NOT NULL,
   PRIMARY KEY (user_id, role_id),
   KEY (role_id),
   CONSTRAINT FOREIGN KEY (role_id) REFERENCES roles (id),
   CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id)
 );

 -- =============================================
 -- INSERT DATA
 -- =============================================
 -- Add data department
 INSERT INTO department(	name, 		total_member, 	type, 		    created_at)
 VALUES
 						(N'Marketing'	, 		1,		'DEV', 			'2020-03-05'),
 						(N'Sale'		, 		2,		'TEST', 		'2020-03-05'),
 						(N'Bảo vệ'		, 		3,		'SCRUM_MASTER', '2020-03-07'),
 						(N'Nhân sự'		, 		4,		'PM', 			'2020-03-08'),
 						(N'Kỹ thuật'	, 		5,		'DEV', 			'2020-03-10'),
 						(N'Tài chính'	, 		6,		'SCRUM_MASTER',  NOW()		),
 						(N'Phó giám đốc', 		7,		'PM', 			 NOW()		),
 						(N'Giám đốc'	, 		8,		'TEST', 		'2020-04-07'),
 						(N'Thư kí'		, 		9,		'PM', 			'2020-04-07'),
 						(N'Bán hàng'	, 		1,		'DEV', 			'2020-04-09');

 -- Add data account
 INSERT INTO account(	username		,	department_id	)
 VALUES 			(	'dangblack'		,   	'5'			),
 					(	'quanganh'		,		'1'			),
                    (	'vanchien'		,		'1'			),
                    (	'cocoduongqua'	,		'1'			),
                    (	'doccocaubai'	,   	'2'			),
                    (	'khabanh'		,   	'2'			),
                    (	'huanhoahong'	,   	'2'			),
                    (	'tungnui'		,   	'8'			),
                    (	'duongghuu'		,   	'9'			);

 -- Add data refreshtoken_seq
 INSERT INTO refreshtoken_seq VALUES (1);

 -- Add data roles
 INSERT INTO roles VALUES (1,'ROLE_USER'),(2,'ROLE_MODERATOR'),(3,'ROLE_ADMIN');