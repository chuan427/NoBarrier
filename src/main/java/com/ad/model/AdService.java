package com.ad.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addday.AdDate;
import com.advertisements.model.AdvertisementsRepository;
import com.advertisements.model.AdvertisementsVO;
import com.user.model.UserRepository;
import com.user.model.UserVO;


@Service("adService")
public class AdService {

	@Autowired
	AdRepository repository;

	@Autowired
	AdvertisementsRepository advertisementsrepository;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
    HttpServletRequest request;
	
	

	public void addEmp(AdVO adVO) {
		repository.save(adVO);
	}

	public void updateEmp(AdVO adVO) {
		repository.save(adVO);
	}

	public void deleteEmp(Integer adOrdernum) {
		if (repository.existsById(adOrdernum))
			repository.deleteByAd_ordernum(adOrdernum);
//		    repository.deleteById(empno);
	}

	public AdVO getOneEmp(Integer adOrdernum) {
		Optional<AdVO> optional = repository.findById(adOrdernum);
//		return optional.get();
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<AdVO> getAll() {
		return repository.findAll();
	}
	

	public void addAdAndAdvertisements(AdDate addate) {
        AdVO advo;
        advo = new AdVO();
        advo.setAdPrice(addate.getAdPriceadd());
        advo.setAdImage(addate.getAdImageadd());
        advo.setAdDuration(addate.getAdvertisements().size());
        
        Integer userId = 2;

        
//        Integer userId = (Integer) request.getSession().getAttribute("userId");

            UserVO userVO = userrepository.findById(userId).orElse(null);
            advo.setUserVO(userVO); // 设置用户信息到广告对象中

        
        List<AdvertisementsVO> preparedToSaveEmployees = addate.getAdvertisements();
        preparedToSaveEmployees.forEach(advertisements -> advertisements.setAdVO(advo));
        advo.setAdvertisements(preparedToSaveEmployees);
		repository.save(advo);
		
	}


}
