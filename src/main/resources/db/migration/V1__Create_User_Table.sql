create table user
(
	id bigint not null,
	nickname varchar(255) not null,
	password varchar(32) default null null,
	salt varchar(10) default null null,
	head varchar(128) default null null,
	register_time datetime default null null,
	last_login_date datetime default null null,
	login_count int default 0 null,
	constraint user_pk
		primary key (id)
);

