package com.forumreply.model;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forumreport.model.ForumReportVO;


@Service("forumReplyService")
public class ForumReplyService {


		@Autowired
		ForumReplyRepository repository;

		public void addForumReply(ForumReplyVO forumReplyVO) {
			repository.save(forumReplyVO);
		}

		public void updateForumReply(ForumReplyVO forumReplyVO) {
			repository.save(forumReplyVO);
		}

		public void deleteForumReply(Integer frNum) {
			if (repository.existsById(frNum))
				repository.deleteByFrStat(frNum);
		}	
	

		public ForumReplyVO getOneForumReply(Integer frNum) {
			Optional<ForumReplyVO> optional = repository.findById(frNum);
//			return optional.get();
			return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		}

		public List<ForumReplyVO> getAll() {
			return repository.findAll();
		}
		
		public Set<ForumReportVO> getForumReportByfrpFpNum(Integer frpFrNum){
			return getOneForumReply(frpFrNum).getForumReport();
		}
	

}
