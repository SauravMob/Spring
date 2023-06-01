package com.rest.RestApi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.RestApi.dao.BookRepository;
import com.rest.RestApi.entities.Book;

@Component
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;	
	
	public List<Book> getAllBooks() {
		List<Book> list = (List<Book>) this.bookRepository.findAll();
		return list;
	}
	
	public Book getBookById(int id) {
		Book book = null;
		book = this.bookRepository.findById(id);
		return book;
	}
	
	public Book addBook(Book b) {
		Book result = bookRepository.save(b);
		return result;
	}
	
	public List<Book> deleteBook(int id) {
		bookRepository.deleteById(id);
		List<Book> list = (List<Book>) this.bookRepository.findAll();
		return list;
	}
	
	public Book updateBook(Book book, int id) {
		book.setId(id);
		bookRepository.save(book);
		Book book1 = this.bookRepository.findById(id);
		return book1;
	}
	
//	private static List<Book> list = new ArrayList<>();
//	
//	static{
//		list.add(new Book(2, "Theory of Everything", "Stephen Hawking"));
//		list.add(new Book(3, "2 states", "Chetan Bhagat"));
//		list.add(new Book(4, "Ae dill hai Mushkil", "Aishwarya"));
//	}
	
//	//Get all books
//	public List<Book> getAllBooks() {
//		return list;
//	}
//	
//	//Get single book by id
//	public Book getBookById(int id) {
//		Book book = null;
//		book = list.stream().filter(e->e.getId()==id).findFirst().get();
//		return book;
//	}
//	
//	//Post a book
//	public Book addBook(Book b) {
//		list.add(b);
//		return b;
//	}
//	
//	public List<Book> deleteBook(int id) {
//		System.out.println("ID:" + id);
//		List<Book> b = list.stream().filter(book->book.getId()!=id).collect(Collectors.toList());
//		return b;
//	}
//	
//	public List<Book> updateBook(Book book, int id) {
//		list = list.stream().map(b->{
//			if (b.getId() == id) {
//				b.setTitle(book.getTitle());
//				b.setAuthor(book.getAuthor());
//			}
//			return b;
//		}).collect(Collectors.toList());
//		return list;
//	}
}
