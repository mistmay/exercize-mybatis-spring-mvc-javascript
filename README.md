# Exercize MyBatis, Spring Boot MVC, Javascript Oracle

based on database 

``CREATE TABLE author (
id_author number(6) NOT NULL,
name varchar2(50) NOT NULL,
surname varchar2(50) NOT NULL,
PRIMARY KEY (id_author));

CREATE TABLE genre (
id_genre number(6) NOT NULL,
name varchar2(50) NOT NULL,
PRIMARY KEY (id_genre));

CREATE TABLE book (
id_book number(6) NOT NULL,
id_author number(6) NOT NULL,
title varchar2(50) NOT NULL,
PRIMARY KEY (id_book),
CONSTRAINT fk_author FOREIGN KEY (id_author) REFERENCES author (id_author));

CREATE TABLE book_genre (
id_book number(6) NOT NULL,
id_genre number(6) NOT NULL,
PRIMARY KEY (id_book, id_genre),
CONSTRAINT fk_book FOREIGN KEY (id_book) REFERENCES book (id_book), 
CONSTRAINT fk_genre FOREIGN KEY (id_genre) REFERENCES genre (id_genre));

CREATE SEQUENCE author_seq
START WITH     1
INCREMENT BY   1;

CREATE SEQUENCE genre_seq
START WITH     1
INCREMENT BY   1;

CREATE SEQUENCE book_seq
START WITH     1
INCREMENT BY   1;``