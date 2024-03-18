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
	public String addQuo(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
	    
	    if (userVO == null) {
	        return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
	    }
		QuoVO quoVO = new QuoVO();
//		quoVO.setUserVO(userVO);
		model.addAttribute("quoVO", quoVO);
		model.addAttribute("comName", userVO.getComName()); // 將公司名稱添加到模型中
		return "front-end/userinformation/addQuotation";
	}
	
	//========================新增報價單=====================================
	@PostMapping("insertQuo")
	public String insertQuo(HttpServletRequest request, @Valid QuoVO quoVO, BindingResult result, ModelMap model, @RequestParam("reqNum") Integer reqNum) throws IOException {
	    UserVO userVO = (UserVO) request.getSession().getAttribute("loggingInUser");
	    
	    // 检查reqNum参数是否为空
	    if (reqNum == null) {
	        // 如果reqNum参数为空，返回错误页面或进行其他处理
	        return "error-page"; // 替换为您的错误页面路径
	    }
	    
	    // 使用reqNum来获取ReqOrderVO实例
	    ReqOrderVO reqOrderVO = reqOrderSvc.getOneReqOrder(reqNum);
	    
	    if (result.hasErrors()) {
	        return "front-end/userinformation/addQuotation";
	    }

	    // 这里加入您的逻辑来处理quoVO和userVO...
	    quoSvc.addQuo(quoVO, userVO, reqOrderVO);

	    List<QuoVO> list = quoSvc.getOneStatQuotation(userVO);
	    model.addAttribute("quoListData", list);
	    model.addAttribute("success", "- (新增成功)");
	    return "front-end/userinformation/userpage";
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