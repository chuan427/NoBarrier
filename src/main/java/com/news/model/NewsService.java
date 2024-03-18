package com.news.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("newsService")
public class NewsService {

	@Autowired
		NewsRepository repository;

	public void addNews(NewsVO newsVO) {
		repository.save(newsVO);
	}

	public void updateNews(NewsVO newsVO) {
		repository.save(newsVO);
	}

	public void deleteNews(Integer newsNum) {
		if (repository.existsById(newsNum))
			repository.deleteById(newsNum);
//		    repository.deleteById(newsNum);
	}

	public NewsVO getOneNews(Integer newsNum) {
		Optional<NewsVO> optional = repository.findById(newsNum);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<NewsVO> getAll() {
		return repository.findAll();
	}

}
