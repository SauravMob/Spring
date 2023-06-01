package com.rest.RestApi.dao;

import org.springframework.data.repository.CrudRepository;

import com.rest.RestApi.entities.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

	public Book findById(int id);
}
