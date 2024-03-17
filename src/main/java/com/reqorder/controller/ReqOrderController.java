package com.reqorder.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;
import com.order.model.OrderService;
import com.order.model.OrderVO;
import com.reqorder.model.ReqOrderService;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserService;
import com.user.model.UserVO;

@Controller
@RequestMapping("/userinformation")
public class ReqOrderController {

    @Autowired
    ReqOrderService reqOrderSvc;

    @Autowired
    UserService userSvc;

    @Autowired
    IndustryService industrySvc;
    
    @Autowired
   	OrderService orderSvc; 
    
    @GetMapping("/addReqOrder")
    public String addReqOrder(ModelMap model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
	    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
	    
	    if (userVO == null) {
	        return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
	    }

	    List<ReqOrderVO> list = reqOrderSvc.getAllReqOrderExceptMe(userVO.getUserId());
	    
        ReqOrderVO reqOrderVO = new ReqOrderVO();
        model.addAttribute("reqOrderVO", reqOrderVO);
        model.addAttribute("comName", userVO.getComName()); // 將公司名稱添加到模型中
        return "front-end/userinformation/addReqOrder";
    }

    
  //=======================新增需求單=================================
    @PostMapping("insertreq")
    public String insert(HttpServletRequest request,@Valid ReqOrderVO reqOrderVO, BindingResult result, ModelMap model,
            @RequestParam("reqProdimage") MultipartFile[] parts) throws IOException {

        result = removeFieldError(reqOrderVO, result, "reqProdimage");
        
        UserVO userVO = (UserVO)request.getSession().getAttribute("loggingInUser");
        
        IndustryVO industryVO =reqOrderVO.getIndustryVO();
        
//        IndustryVO industryVO = (IndustryVO)request.getSession().getAttribute("loggingInUser");
        
        if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "關於我們圖片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				reqOrderVO.setReqProdimage(buf);
			}
		}

        if (result.hasErrors() || parts.length == 0) {
            return "front-end/userinformation/addReqOrder";
        }

        reqOrderSvc.addReqOrder(reqOrderVO, userVO, industryVO);
        
        List<ReqOrderVO> list = reqOrderSvc.getAllReqOrderExceptMe(userVO.getUserId());
        model.addAttribute("reqOrderListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/userinformation/userpage?successMessage=addsuccess";

//        return "redirect:/userinformation/userpage";
    }

    //=======================按下忽略改變需求單狀態=================================
    @PostMapping("/userpage/complete")
    public String complete(@RequestParam(name = "reqNum", required = false) String reqNum, ModelMap model) {
        if (reqNum == null) {
            return "errorPage"; // 如果 reqNum 為空，返回一個錯誤頁面
        }

        // 根據 reqNum 從數據庫中獲取相應的 ReqOrderVO 對象
        ReqOrderVO reqOrderVO = reqOrderSvc.getOneReqOrder(Integer.valueOf(reqNum));

        // 將 reqIsValid 設置為 1，表示已完成需求
        int valid = 0;
        reqOrderVO.setReqIsValid(valid);

        // 更新數據庫中的 ReqOrderVO 對象
        reqOrderSvc.updateReqOrder(reqOrderVO);

        // 添加成功消息到模型中
        model.addAttribute("success", "- (完成需求)");

        // 返回到 userpage 頁面
        return "redirect:/userinformation/userpage";
    }


    @ModelAttribute("userListData")
    protected List<UserVO> referenceListData() {
        return userSvc.getAll();
    }

    @ModelAttribute("industryListData")
    protected List<IndustryVO> referenceListData1() {
        return industrySvc.getAll();
    }

    @ModelAttribute("orderListData")
	protected List<OrderVO> referenceListOrderData(){
	List<OrderVO> list = orderSvc.getAll();
	return list;
	}
    
    public BindingResult removeFieldError(ReqOrderVO reqOrderVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
        result = new BeanPropertyBindingResult(reqOrderVO, "reqOrderVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }
}
