package com.ad.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

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
	

	public void addAdAndAdvertisements(AdDate addate,UserVO userVO) {
        AdVO advo;
        advo = new AdVO();
        advo.setAdPrice(addate.getAdPriceadd());
        advo.setAdImage(addate.getAdImageadd());
        advo.setAdDuration(addate.getAdvertisements().size());
        
//        Integer userId = 2;

        
//        Integer userId = (Integer) request.getSession().getAttribute("userId");

//            UserVO userVO = userrepository.findById(userId).orElse(null);
            advo.setUserVO(userVO); // 设置用户信息到广告对象中

        
        List<AdvertisementsVO> preparedToSaveEmployees = addate.getAdvertisements();
        preparedToSaveEmployees.forEach(advertisements -> advertisements.setAdVO(advo));
        advo.setAdvertisements(preparedToSaveEmployees);
		repository.save(advo);
		
	}
	
//	public  boolean hasValidAdOrder(UserVO userVo) {
//        List<AdVO> userAds = repository.findByUserVO(userVo);
//        for (AdVO ad : userAds) {
//            // 检查广告订单是否有效
//            if (ad.getAdOrdernum() != null) {
//                // 获取广告订单的截止日期
//            	List<AdvertisementsVO> advertisements = ad.getAdvertisements();
//            	LocalDate currentDate = LocalDate.now();
//            	for (AdvertisementsVO adv : advertisements) {
//            	    // 获取广告信息的截止日期
//            	    Date endDate = adv.getAdsDays();
//            	    // 转换为 LocalDate
//            	    LocalDate endDateLocal = ((java.sql.Date) endDate).toLocalDate();
//            	    // 获取当前日期的年月日部分
//            	    
////            	    System.out.println(currentDate);
//            	    // 检查截止日期的年月日部分是否在当前日期之后
//            	    if (endDateLocal.isEqual(currentDate)) {
//            	        return true; // 存在有效的广告订单
//            	    }
//            	}
//
//            }
//        }
//        return false; // 不存在有效的广告订单
//    }

	public boolean hasValidAdOrder(UserVO userVo) {
	    List<AdVO> userAds = repository.findByUserVO(userVo);
	    LocalDate currentDate = LocalDate.now();
	    return userAds.stream()
	            .filter(ad -> ad.getAdOrdernum() != null)
	            .flatMap(ad -> ad.getAdvertisements().stream())
	            .map(AdvertisementsVO::getAdsDays)
	            .map(endDate -> ((java.sql.Date) endDate).toLocalDate())
	            .anyMatch(endDateLocal -> endDateLocal.isEqual(currentDate));
	}

}
