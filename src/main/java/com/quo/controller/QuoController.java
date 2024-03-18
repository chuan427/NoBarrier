package com.quo.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.order.model.OrderService;
import com.quo.model.QuoService;
import com.quo.model.QuoVO;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserService;
import com.user.model.UserVO;


@Controller
@RequestMapping("/userinformation")
public class QuoController {

	@Autowired
	QuoService quoSvc;
	
	@Autowired
	UserService userSvc;
	
	@Autowired
	ReqOrderService reqOrderSvc;

	@Autowired
	OrderService orderSvc;
	
	@GetMapping("/addQuotation")
	public String addQuo(ModelMap model, HttpServletRequest request,@RequestParam("reqNum") String reqNum) {
		HttpSession session = request.getSession();
	    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
		ReqOrderVO reqVO = reqOrderSvc.getOneReqOrder(Integer.parseInt(reqNum));
		
		QuoVO quoVO = new QuoVO();
		quoVO.setQuoProdname(reqVO.getReqProdname());
		quoVO.setQuoUnitname(reqVO.getReqUnitname());
		quoVO.setQuoProdqty(reqVO.getReqProdqty());
		quoVO.setReqOrderVO(reqVO);
		quoVO.setUserVO(userVO);
		
		System.out.println(reqVO.getReqProdname());
		
		model.addAttribute("quoVO", quoVO);
		
		return "front-end/userinformation/addQuotation";
	}
	
	//========================新增報價單=====================================
	@PostMapping("insertQuo")
	public String insertQuo(@Valid QuoVO quoVO, BindingResult result, ModelMap model) throws IOException {
	    
	    
	    
	    if (result.hasErrors()) {
	        return "front-end/userinformation/addQuotation";
	    }

	    // 這裡加入您的邏輯來處理quoVO和userVO...
	    quoSvc.addQuo(quoVO);

	    List<QuoVO> list = quoSvc.getAll();
	    model.addAttribute("quoListData", list);
	    model.addAttribute("success", "- (新增成功)");
	    return "redirect:/userinformation/userpage";
	}



	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(QuoVO quoVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(quoVO, "quoVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}