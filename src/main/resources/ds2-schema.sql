create table address (
	id identity primary key,
	address1 varchar(255),
	address2 varchar(255),
	city varchar(255),
	state varchar(255),
	zip varchar(255),
	customer_id bigint not null
);