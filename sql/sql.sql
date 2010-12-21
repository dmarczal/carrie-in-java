create database carrie;

create table users (
	id INT NOT NULL AUTO_INCREMENT,
	email varchar(30) not null unique key,
	created_at timestamp,
	PRIMARY KEY (id)
);

create table mistakes (
	id INT NOT NULL AUTO_INCREMENT,
	object longblob,
	exercise varchar(200),
	learningObject varchar(200),
	description text,
	answer varchar(400),
	correctAnswer varchar(400),
	title varchar(300),
	created_at timestamp,
	user_id integer,
	
	PRIMARY KEY (id),
	
	FOREIGN KEY (user_id) REFERENCES users(id)
             ON DELETE SET NULL         
);

