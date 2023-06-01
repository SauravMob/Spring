package com.rest.RestApi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.RestApi.entities.Book;
import com.rest.RestApi.service.BookService;

@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;

//	@RequestMapping(value="/books", method = RequestMethod.GET)
	@GetMapping("/books")
	public List<Book> getBooks() {
		return this.bookService.getAllBooks();
	}
	
	@GetMapping("/books/{id}")
	public Book getBook(@PathVariable("id") int id) {
		return bookService.getBookById(id);
	}
	
	@PostMapping("/books")
	public Book addBook(@RequestBody Book book) {
		Book b = this.bookService.addBook(book);
		return b;
	}
	
	@DeleteMapping("/books/{id}")
	public List<Book> deleteBook(@PathVariable("id") int id) {
		List<Book> b = this.bookService.deleteBook(id);
		return b;
	}
	
	@PutMapping("/books/{id}")
	public Book updateBook(@RequestBody Book book, @PathVariable("id") int id) {
		Book b = this.bookService.updateBook(book, id);
		return b;
	}
}
