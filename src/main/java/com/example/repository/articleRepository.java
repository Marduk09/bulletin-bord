package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

/**
 * articlesテーブルを操作するレポジトリ.
 * 
 * @author takara.miyazaki
 *
 */
@Repository
public class articleRepository {

	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 記事を全件検索する.
	 * 
	 * @return 全記事のリスト(コメントリストは空)
	 */
	public List<Article> findAll(){
		String sql = "SELECT id, name, content FROM articles;";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		
		return articleList;
	}
	
	/**
	 * 記事をテーブルに追加する.
	 * 
	 * @param article 追加する記事
	 */
	public void Insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles (name, content) VALUES(:name, :content)";
		template.update(sql, param);
	}
	
	/**
	 * 指定されたIDの記事を削除する.
	 * 
	 * @param id 削除する記事ID
	 */
	public void deleteById(int id) {
		String sql = "DLETE FROM articles WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
