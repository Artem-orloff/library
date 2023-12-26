CREATE TABLE IF NOT EXISTS author
(
    author_id BIGSERIAL PRIMARY KEY ,
    full_name  VARCHAR(50) NOT NULL ,
    birth VARCHAR(50) NOT NULL ,
    biography VARCHAR  NOT NULL

    );

CREATE TABLE IF NOT EXISTS book
(
    book_id BIGSERIAL PRIMARY KEY ,
    name  VARCHAR(50) NOT NULL ,
    publication VARCHAR(50) NOT NULL ,
    genre VARCHAR(50)  NOT NULL ,
    author_id INTEGER ,
    FOREIGN KEY (author_id) REFERENCES author (author_id)

    );

CREATE TABLE IF NOT EXISTS users
(
    user_id BIGSERIAL PRIMARY KEY ,
    full_name  VARCHAR(50) NOT NULL ,
    birth VARCHAR(50) NOT NULL ,
    type VARCHAR(8)  NOT NULL ,
    book_id INTEGER ,
    FOREIGN KEY (book_id) REFERENCES book (book_id),
    username varchar(100) NOT NULL ,
    enabled varchar(100) not null,
    password varchar(100) not null
    );
