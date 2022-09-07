package com.advancia.bookBatis.dao;

import java.util.List;

import com.advancia.bookBatis.model.Author;
import com.advancia.bookBatis.model.Book;
import com.advancia.bookBatis.model.Genre;

public interface Dao {
	List<Author> findAllAuthors();

	List<Genre> findAllGenres();

	List<Book> findAllBooks();

	Author selectAuthorById(Integer id);

	Genre selectGenreById(Integer id);

	Book selectBookById(Integer id);

	Integer insertAuthor(Author author);

	Integer insertGenre(Genre genre);

	Integer insertBook(Book book);

	Integer updAuthor(Author author);

	Integer updGenre(Genre genre);

	Integer updBook(Book book);

	Integer delAuthor(Integer id);

	Integer delGenre(Integer id);

	Integer delBook(Integer id);
}
