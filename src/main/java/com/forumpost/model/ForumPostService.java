package com.forumpost.model;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forumreply.model.ForumReplyVO;
import com.forumreport.model.ForumReportVO;


@Service("forumPostService")
public class ForumPostService {


		@Autowired
		ForumPostRepository repository;

		public void addForumPost(ForumPostVO forumPostVO) {
			repository.save(forumPostVO);
		}

		public void updateForumPost(ForumPostVO forumPostVO) {
			repository.save(forumPostVO);
		}

		public void deleteForumPost(Integer fpNum) {
			if (repository.existsById(fpNum))
				repository.deleteByFpStat(fpNum);
		}

		public ForumPostVO getOneForumPost(Integer fpNum) {
			Optional<ForumPostVO> optional = repository.findById(fpNum);
//			return optional.get();
			return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		}

		public List<ForumPostVO> getAll() {
			return repository.findAll();
		}
		
		public Set<ForumReplyVO> getForumReplyByfrFpnum(Integer frFpNum){
			return getOneForumPost(frFpNum).getForumReply();
		}
		
		public Set<ForumReportVO> getForumReportByfrpFpNum(Integer frpFpNum){
			return getOneForumPost(frpFpNum).getForumReport();
		}
}
