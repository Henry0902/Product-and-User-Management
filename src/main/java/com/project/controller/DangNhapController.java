package com.project.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.project.repository.ProductInfoRepository;
import com.project.repository.ProductModuleReponsitory;
import com.project.table.ProductInfo;
import com.project.table.ProductModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.common.CommonUtils;
import com.project.exception.ErrorException;
import com.project.repository.UserInfoRepository;
import com.project.repository.UserModuleRepository;
import com.project.table.UserInfo;
import com.project.table.UserModule;

@Controller
public class DangNhapController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DangNhapController.class);
	
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserModuleRepository userModuleRepository;
    @Autowired
    ProductModuleReponsitory productModuleRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;

    @GetMapping(value = "/login")
    public String get(Model model) {
        return "login";
    }

    @PostMapping(value = "/login")
    public String get(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
        try {
            UserInfo userInfo = userInfoRepository.findByUsername(getStringParams(allParams, "username"));

            if (userInfo == null) throw new ErrorException("Tên đăng nhập không đúng");
            if (!userInfo.getPassword().equals(CommonUtils.getMD5(getStringParams(allParams, "password"))))
                throw new ErrorException("Mật khẩu không đúng");

            List<UserModule> userModules = userModuleRepository.selectGroupId(userInfo.getGroupId());

            System.out.println(userModules.size());
            
            req.getSession().setAttribute("userModuleMenus", userModules);
            req.getSession().setAttribute("username", userInfo.getUsername());
            req.getSession().setAttribute("fullName", userInfo.getFullName());
            req.getSession().setAttribute("email", userInfo.getEmail());
            req.getSession().setAttribute("userId", userInfo.getId());

            String username = userInfo.getUsername().toLowerCase();
            if ("admin".equals(username) || "supper_admin".equals(username)) {
                return "redirect:/";
            } else {
                return "redirect:/home-shopping";
            }
        } catch (ErrorException e) {
			model.addAttribute("message", e.getMessage());
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			LOGGER.error("ErrorException", e);
		}
		return "login";
	}
}
