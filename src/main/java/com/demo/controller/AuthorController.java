package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.biz.AuthorBiz;
import com.demo.entity.Author;

@Controller
@RequestMapping("/AuthorController")
public class AuthorController {

	@Autowired
	private AuthorBiz userbiz;

	@RequestMapping("/getAllUser")
	public String getAllUser(ModelMap mm) {
		List<Author> authors = null;
		try {
			authors = userbiz.getAllUser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		mm.addAttribute("authors", authors);
		return "test";
	}
}
