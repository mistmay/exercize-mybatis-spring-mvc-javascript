package com.advancia.bookBatis.model;

import java.util.List;

public class Book {
	private Integer id;
	private String title;
	private Author author;
	private List<Genre> genres;

	public Book() {
	}

	public Book(String title, Author author, List<Genre> genres) {
		this.title = title;
		this.author = author;
		this.genres = genres;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
}
