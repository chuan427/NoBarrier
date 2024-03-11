package com.forumreport.model;

import java.util.List;
import java.util.Optional;

//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("forumReportService")
public class ForumReportService {

	@Autowired
	private ForumReportRepository repository;

	public void addForumReport(ForumReportVO forumReportVO) {
		repository.save(forumReportVO);
	}

	public void updateForumReport(ForumReportVO forumReportVO) {
		repository.save(forumReportVO);
	}

	public void deleteForumReport(Integer frpNum) {
		if (repository.existsById(frpNum));
//			repository.deleteByFrpIsValid(frpNum);
	}

	public ForumReportVO getOneForumReport(Integer frpNum) {
		Optional<ForumReportVO> optional = repository.findById(frpNum);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	public List<ForumReportVO> getAll() {
		return repository.findAll();
	}

}
