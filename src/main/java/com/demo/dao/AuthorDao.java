package com.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.dao.mybatis.IAuthorMapper;
import com.demo.entity.Author;

@Repository("/authordao")
public class AuthorDao {
	
	@Autowired
	private IAuthorMapper authorMapper;
	
	public List<Author> getAllUser() throws Exception{
		List<Author> authors = null;
		authors = authorMapper.getAllUser();
		return authors;
	}
	
	public Author getAuthorById(Integer authorid) throws Exception{
		Author author1 = new Author();
		author1 = authorMapper.getAuthorById(authorid);
		return author1;
	}
	
	public boolean DelAuthor(Author author) throws Exception{
		return authorMapper.DelAuthor(author);
	}

}
