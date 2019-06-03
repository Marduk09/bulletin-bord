package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/bord")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	/**
	 * 記事一覧を表示
	 * @param model リクエストスコープ
	 * @return 記事一覧画面
	 */
	@RequestMapping("")
	public String index(Model model) {
		
		List<Article> articleList = articleRepository.findAll();
		for(Article article : articleList) {
			article.setCommentList(commentRepository.findByArticleId(article.getId()));
		}
		model.addAttribute("articleList", articleList);
		
		return "bulletin-bord";
	}
	
	/**
	 * 記事を投稿.
	 * @param form 記事のフォーム
	 * @param model リクエストスコープ
	 * @return 記事一覧画面へのリダイレクト
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.Insert(article);
		
		return "redirect:/bord";
	}
	
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		System.out.println(form);
		System.out.println(comment);
		commentRepository.insert(comment);
		
		return "redirect:/bord";
	}
	

}
