package com.advancia.bookBatis.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Genre {
	private Integer id;
	private String name;

	@JsonIgnore
	private List<Book> books;
	
	public Genre() {}
	
	public Genre(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
