# Getting Started
# Description
Simple exercise to learn Spring Batch transformation on files.
## Configuration
### Database
Add custom database in application.yml with the following tables:
```
create table book(
    id serial primary key,
    title varchar(255) not null,
);

create table author(
    id serial primary key,
    firstname varchar(255) not null,
    lastname varchar(255) not null
);

create table r_book_author(
	book_id int references book(id),
	author_id int references author(id)
)

```
insert records in the tables and start the application with the following command:
```
mvn clean install -DskipTests
mvn spring-boot:run
```
or via Docker with
```angular2html
docker compose up -d
```