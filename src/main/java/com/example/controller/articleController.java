package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("/bord")
public class articleController {
	
	@Autowired
	private ArticleRepository repository;
	
	@RequestMapping("")
	public String index(Model model) {
		
		List<Article> articleList = repository.findAll();
		model.addAttribute("articleList", articleList);
		
		return "bulletin-bord";
	}

}
