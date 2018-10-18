package com.demo.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.entity.Book;

public interface IBookMapper {

	public List<Book> getAllBook() throws Exception;
	
	public boolean AddBook(Book book) throws Exception;

	public boolean DelOneBook(Book book) throws Exception;

	public boolean AddMoreBook(@Param("books")List<Book> books) throws Exception;
	
	public Book getBook(Book book) throws Exception;
}
