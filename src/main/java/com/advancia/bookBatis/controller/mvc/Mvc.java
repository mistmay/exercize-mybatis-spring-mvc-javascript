package com.advancia.bookBatis.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Mvc {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndexPage(ModelMap model) {
		return "index.html";
	}
}
