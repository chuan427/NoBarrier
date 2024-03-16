package com.newsmodel;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	public List<NewsVO> getAllAndHandleStatus() {
	    // 获取所有新闻
	    List<NewsVO> allNews = repository.findAll();
	    
	    // 获取今日时间七天前的日期
	    long millisInDay = 1000 * 60 * 60 * 24;
	    long sevenDaysInMillis = 7 * millisInDay;
	    long sevenDaysAgoMillis = System.currentTimeMillis() - sevenDaysInMillis;
	    Date sevenDaysAgo = new Date(sevenDaysAgoMillis);
	    
	    // 使用Lambda表达式筛选出日期小于今日时间七天以上的新闻，并将其状态设置为0
	    List<NewsVO> updatedNews = allNews.stream()
	           .filter(news -> {
	               if (news.getNewsDate().before(sevenDaysAgo)) {
	                   news.setNewsIsValid(0);
	                   return false; // 不返回这些新闻
	               } else {
	                   return true; // 返回未受影响的新闻
	               }
	           })
	           .collect(Collectors.toList());
	    
	    // 将更新后的新闻保存到数据库
	    repository.saveAll(updatedNews);
	    
	    // 返回状态为1的新闻列表
	    List<NewsVO> validNews = updatedNews.stream()
	           .filter(news -> news.getNewsIsValid() == 1)
	           .collect(Collectors.toList());
	    
	    return validNews;
	}

}
