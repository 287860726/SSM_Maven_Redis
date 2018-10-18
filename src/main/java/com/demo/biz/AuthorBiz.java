package com.demo.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.AuthorDao;
import com.demo.entity.Author;

@Service("/authorbiz")
public class AuthorBiz {

	@Autowired
	private AuthorDao authordao;

	public List<Author> getAllUser() throws Exception {
		List<Author> authors = null;
		authors = authordao.getAllUser();
		return authors;
	}
	
	public Author getAuthorById(Integer authorid) throws Exception {
		return authordao.getAuthorById(authorid);
	}
}
