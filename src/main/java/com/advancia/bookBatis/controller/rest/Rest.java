package com.advancia.bookBatis.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advancia.bookBatis.dao.Dao;
import com.advancia.bookBatis.model.Author;
import com.advancia.bookBatis.model.Book;
import com.advancia.bookBatis.model.Genre;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Rest {
	@Resource
	Dao dao;

	@GetMapping("/authors")
	public ResponseEntity<List<Author>> getAllAuthors() {
		try {
			List<Author> authors = new ArrayList<Author>();
			dao.findAllAuthors().forEach(authors::add);
			return new ResponseEntity<>(authors, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/genres")
	public ResponseEntity<List<Genre>> getAllGenres() {
		try {
			List<Genre> genres = new ArrayList<Genre>();
			dao.findAllGenres().forEach(genres::add);
			return new ResponseEntity<>(genres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		try {
			List<Book> books = new ArrayList<Book>();
			dao.findAllBooks().forEach(books::add);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/authors/{id}")
	public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id) {
		Author authorData = dao.selectAuthorById(id);
		if (authorData != null) {
			return new ResponseEntity<>(authorData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/genres/{id}")
	public ResponseEntity<Genre> getGenreById(@PathVariable("id") Integer id) {
		Genre genreData = dao.selectGenreById(id);
		if (genreData != null) {
			return new ResponseEntity<>(genreData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id) {
		Book bookData = dao.selectBookById(id);
		if (bookData != null) {
			return new ResponseEntity<>(bookData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/authors")
	public ResponseEntity<String> createAuthor(@RequestBody Author author) {
		try {
			Author _author = new Author(author.getName(), author.getSurname());
			Integer row = dao.insertAuthor(_author);
			if (row > 0) {
				return new ResponseEntity<>(_author.getName() + " " + _author.getSurname() + " Inserted Successfully",
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/genres")
	public ResponseEntity<String> createGenre(@RequestBody Genre genre) {
		try {
			Genre _genre = new Genre(genre.getName());
			Integer row = dao.insertGenre(_genre);
			if (row > 0) {
				return new ResponseEntity<>(_genre.getName() + " Inserted Successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/books")
	public ResponseEntity<String> createBook(@RequestBody Book book) {
		try {
			Book _book = new Book(book.getTitle(), book.getAuthor(), book.getGenres());
			Integer row = dao.insertBook(_book);
			if (row != 0) {
				return new ResponseEntity<>(_book.getTitle() + " Inserted Successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/authors/{id}")
	public ResponseEntity<String> updateAuthor(@PathVariable("id") Integer id, @RequestBody Author author) {
		try {
			Author _author = dao.selectAuthorById(id);
			if (_author != null) {
				_author.setName(author.getName());
				_author.setSurname(author.getSurname());
				Integer row = dao.updAuthor(_author);
				if (row > 0) {
					return new ResponseEntity<>("Update success", HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/genres/{id}")
	public ResponseEntity<String> updateGenre(@PathVariable("id") Integer id, @RequestBody Genre genre) {
		try {
			Genre _genre = dao.selectGenreById(id);
			if (_genre != null) {
				_genre.setName(genre.getName());
				Integer row = dao.updGenre(_genre);
				if (row > 0) {
					return new ResponseEntity<>("Update success", HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<String> updateBook(@PathVariable("id") Integer id, @RequestBody Book book) {
		try {
			Book _book = dao.selectBookById(id);
			Author _author = dao.selectAuthorById(book.getAuthor().getId());
			List<Genre> _genres = new ArrayList<>();
			boolean check = true;
			for (Genre currentGenre : book.getGenres()) {
				Genre gen = dao.selectGenreById(currentGenre.getId());
				if (gen != null) {
					_genres.add(gen);
				} else {
					check = false;
				}
			}
			if (_book != null && _author != null && check) {
				_book.setTitle(book.getTitle());
				_book.setAuthor(_author);
				_book.setGenres(_genres);
				Integer row = dao.updBook(_book);
				if (row != 0) {
					return new ResponseEntity<>("Update success", HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/authors/{id}")
	public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") Integer id) {
		try {
			Integer row = dao.delAuthor(id);
			if (row > 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/genres/{id}")
	public ResponseEntity<HttpStatus> deleteGenre(@PathVariable("id") Integer id) {
		try {
			Integer row = dao.delGenre(id);
			if (row > 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Integer id) {
		try {
			Integer row = dao.delBook(id);
			if (row != 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
