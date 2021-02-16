package com.cos.blog.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BoardController {
	// Git Local Modify 35	@GetMapping({"","/"})
	public String index( ) {
		// /WEB-INF/views/index.jsp
		return "index";
	}    
}
