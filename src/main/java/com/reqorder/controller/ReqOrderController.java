package com.reqorder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.industry.model.IndustryService;
import com.industry.model.IndustryVO;
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
    
    
    @GetMapping("/addReqOrder")
    public String addReqOrder(ModelMap model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
	    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
	    
	    if (userVO == null) {
	        return "redirect:/login"; // 如果使用者未登入，將其重定向到登入頁面
	    }

	    List<ReqOrderVO> list = reqOrderSvc.getOneStatReqOrder(userVO);
	    
        ReqOrderVO reqOrderVO = new ReqOrderVO();
        model.addAttribute("reqOrderVO", reqOrderVO);
        model.addAttribute("comName", userVO.getComName()); // 將公司名稱添加到模型中
        return "front-end/userinformation/addReqOrder";
    }
    
//	@GetMapping("/userpage")
//    public List<ReqOrderVO> getOrdersByUserId(HttpSession session) {
//		
//	    UserVO userVO = (UserVO) session.getAttribute("loggingInUser");
//        return reqOrderSvc.getReqOrderByUserId(userVO.getUserId());
//    }


    @PostMapping("insertreq")
    public String insert(HttpServletRequest request,@Valid ReqOrderVO reqOrderVO, BindingResult result, ModelMap model,
            @RequestParam("reqProdimage") MultipartFile[] parts) throws IOException {

        result = removeFieldError(reqOrderVO, result, "reqProdimage");
        
        UserVO userVO = (UserVO)request.getSession().getAttribute("loggingInUser");
        
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

        reqOrderSvc.addReqOrder(reqOrderVO, userVO);
        
//        List<ReqOrderVO> list = reqOrderSvc.getAll();
//        model.addAttribute("reqOrderListData", list);
        
        List<ReqOrderVO> list = reqOrderSvc.getOneStatReqOrder(userVO);
        model.addAttribute("reqOrderListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/userinformation/userpage";
    }

//    @PostMapping("getOne_For_Update")
//    public String getOne_For_Update(@RequestParam("reqNum") String reqNum, ModelMap model) {
//        ReqOrderVO reqOrderVO = reqOrderSvc.getOneReqOrder(Integer.valueOf(reqNum));
//        model.addAttribute("reqOrderVO", reqOrderVO);
//        return "front-end/userinformation/updateReqOrder";
//    }
//
//    @PostMapping("update")
//    public String update(@Valid ReqOrderVO reqOrderVO, BindingResult result, ModelMap model,
//            @RequestParam("reqProdimage") MultipartFile[] parts) throws IOException {
//
//        result = removeFieldError(reqOrderVO, result, "reqProdimage");
//
//        if (parts.length == 0) {
//            byte[] reqProdimage = reqOrderSvc.getOneReqOrder(reqOrderVO.getReqNum()).getReqProdimage();
//            reqOrderVO.setReqProdimage(reqProdimage);
//        } else {
//            for (MultipartFile multipartFile : parts) {
//                byte[] reqProdimage = multipartFile.getBytes();
//                reqOrderVO.setReqProdimage(reqProdimage);
//            }
//        }
//
//        if (result.hasErrors()) {
//            return "front-end/userinformation/updateReqOrder";
//        }
//
//        reqOrderSvc.updateReqOrder(reqOrderVO);
//        model.addAttribute("success", "- (修改成功)");
//        reqOrderVO = reqOrderSvc.getOneReqOrder(Integer.valueOf(reqOrderVO.getReqNum()));
//        model.addAttribute("reqOrderVO", reqOrderVO);
//        return "front-end/userinformation/req_userpage";
//    }

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
