package com.reqorder.controller;

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
@RequestMapping("/reqorder")
public class ReqOrderController {

    @Autowired
    ReqOrderService reqOrderSvc;

    @Autowired
    UserService userSvc;

    @Autowired
    IndustryService industrySvc;

    @GetMapping("addReqOrder")
    public String addReqOrder(ModelMap model) {
        ReqOrderVO reqOrderVO = new ReqOrderVO();
        model.addAttribute("reqOrderVO", reqOrderVO);
        model.addAttribute("reqProdimage", new MultipartFile[1]); // 添加一個空的MultipartFile陣列
        return "front-end/userinformation/addReqOrder";
    }


    @GetMapping("/reqorder_page")
    public String reqOrderList(Model model) {
        List<ReqOrderVO> list = reqOrderSvc.getAll();
        model.addAttribute("reqOrderListData", list);
        return "redirect:/userinformation/reqorder_page";
    }

    @PostMapping("insert")
    public String insert(@Valid ReqOrderVO reqOrderVO, BindingResult result, ModelMap model,
            @ModelAttribute("reqProdimage") MultipartFile[] parts) throws IOException {
        
        model.addAttribute("reqOrderVO", reqOrderVO);

        if (result.hasErrors() || parts[0].isEmpty()) {
            return "front-end/userinformation/addReqOrder";
        }

        reqOrderSvc.addReqOrder(reqOrderVO);

        List<ReqOrderVO> list = reqOrderSvc.getAll();
        model.addAttribute("reqOrderListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/userinformation/reqorder_page";
    }


    @ModelAttribute("userListData")
    protected List<UserVO> referenceListData() {
        List<UserVO> list = userSvc.getAll();
        return list;
    }

    @ModelAttribute("industryListData")
    protected List<IndustryVO> referenceListData1() {
        List<IndustryVO> list = industrySvc.getAll();
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
