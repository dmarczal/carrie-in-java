DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS mistakes;
DROP TABLE IF EXISTS retroactions;
DROP TABLE IF EXISTS hits;

create table users (
	id BIGINT NOT NULL AUTO_INCREMENT,
	email varchar(30) not null unique key,
	created_at timestamp,
	updated_at timestamp,
	PRIMARY KEY (id)
);

create table mistakes (
	id BIGINT NOT NULL AUTO_INCREMENT,
	object longblob,
	exercise varchar(200),
	oa varchar(200),
	description text,
	answer varchar(400),
	correct_answer varchar(400),
	title varchar(300),
	created_at timestamp,
	updated_at timestamp,
	user_id  BIGINT NOT NULL,
	
	cell varchar(200),
	
	PRIMARY KEY (id),
	
	FOREIGN KEY (user_id) REFERENCES users(id)
             ON DELETE SET NULL         
);


create table retroactions (
	id BIGINT NOT NULL AUTO_INCREMENT,
	created_at timestamp,
	updated_at timestamp,
	mistake_id  BIGINT NOT NULL,
	
	PRIMARY KEY (id),
             
    FOREIGN KEY (mistake_id) REFERENCES mistakes(id)
             ON DELETE SET NULL         
);

create table hits (
	id BIGINT NOT NULL AUTO_INCREMENT,
	created_at timestamp,
	updated_at timestamp,
	exercise varchar(200),
	cell varchar(200),
	oa varchar(200),
	answer varchar(400),
	correct_answer varchar(400),
	
	user_id  BIGINT NOT NULL,
	
	PRIMARY KEY (id),
              
    FOREIGN KEY (user_id) REFERENCES users(id)
             ON DELETE SET NULL
);

