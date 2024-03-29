package com.forumpost.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.forumreply.model.ForumReplyVO;
import com.forumreport.model.ForumReportVO;
import com.user.model.UserVO;

@Service("forumPostService")
public class ForumPostService {

	@Autowired
	ForumPostRepository repository;
	
	public void addForumPost(ForumPostVO ForumPostVO ,UserVO loggingInUser) {        
		ForumPostVO.setUserVO(loggingInUser); 
		repository.save(ForumPostVO);
	}

	
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
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<ForumPostVO> getAll() {
		return repository.findAll();
	}

	public Set<ForumReplyVO> getForumReplyByfrFpnum(Integer frFpNum) {
		return getOneForumPost(frFpNum).getForumReply();
	}

	public Set<ForumReportVO> getForumReportByfrpFpNum(Integer frpFpNum) {
		return getOneForumPost(frpFpNum).getForumReport();
	}

	
    public ForumPostVO getLatestPost() {
        Optional<ForumPostVO> latestPost = repository.findLatestPost();
        System.out.println(latestPost);
        return latestPost.orElse(null);  // 如果沒有找到，返回null
    }
    
    public Page<ForumPostVO> findByComNameOrFpTitleContaining(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByComNameOrFpTitleContaining(searchTerm, pageable);
    }
     
    
    public List<ForumPostVO> getAllForumPostsSortedByFpTime() {
        return repository.findAllByOrderByFpTimeDesc();
    }
    
}
