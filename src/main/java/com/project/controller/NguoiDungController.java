package com.project.controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.CommonUtils;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.exception.ErrorException;
import com.project.model.dto.UserInfoDto;
import com.project.model.search.UserSearch;
import com.project.repository.UserGroupRepository;
import com.project.repository.UserInfoDtoRepository;
import com.project.repository.UserInfoRepository;
import com.project.table.UserGroup;
import com.project.table.UserInfo;

@Controller
public class NguoiDungController extends BaseController {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserInfoDtoRepository userInfoDtoRepository;
    @Autowired
    UserGroupRepository userGroupRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/nguoi-dung")
    public String nguoiDung(Model model, HttpServletRequest req,
                            @RequestParam Map<String, String> allParams) {
        handlingGet(allParams, model, req);
        forwartParams(allParams, model);
        return "nguoidung/nguoidung";
    }

    private void handlingGet(Map<String, String> allParams, Model model, HttpServletRequest req) {
        Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
        // clear all param if reset
        if (allParams.get("reset") != null) {
            allParams.clear();
        }

        UserSearch userSearch = objectMapper.convertValue(allParams, UserSearch.class);
        userSearch.normalize();

        Page<UserInfoDto> userInfos = userInfoDtoRepository.selectParams(
                userSearch.getS_uname(),
                userSearch.getS_fname(),
                userSearch.getS_email(),
                userSearch.getS_status(),
                getPageable(allParams, paginate));

        model.addAttribute("currentPage", paginate.getPage());
        model.addAttribute("totalPage", userInfos.getTotalPages());
        model.addAttribute("totalElement", userInfos.getTotalElements());
        model.addAttribute("userInfos", userInfos.getContent());
    }

    @GetMapping(value = "/nguoi-dung/them-moi")
    public String getnguoiDungThemMoi(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) throws JsonProcessingException {
        Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

        model.addAttribute("userGroups", userGroups);
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "nguoidung/addnguoidung";
    }

    @PostMapping(value = "/nguoi-dung/them-moi")
    public String postnguoiDungThemMoi(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                       @RequestParam Map<String, String> allParams,
                                       @ModelAttribute("userInfo") UserInfo userInfo) {
        try {
            checkErrorMessage(userInfo);

            if (userInfo.getGroupId() == 0) throw new Exception("Chọn nhóm cho người dùng");
            UserInfo checkUserName = userInfoRepository.findByUsername(userInfo.getUsername());
            if (checkUserName != null) throw new Exception("Tên đăng nhập đã tồn tại");

            userInfo.setCreateBy(getUserName(req));
            userInfo.setCreateTime(new Date());
            userInfo.setPassword(CommonUtils.getMD5(userInfo.getPassword()));

            userInfoRepository.save(userInfo);

            redirectAttributes.addFlashAttribute("success", "Thêm thành công");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/nguoi-dung";
    }

    @GetMapping(value = "/nguoi-dung/sua")
    public String getnguoiDungSua(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception("Sửa thất bại");
            }
            Optional<UserInfo> userInfo = userInfoRepository.findById(Long.valueOf(allParams.get("id")));
            if (!userInfo.isPresent()) {
                throw new Exception("Không tồn tại bản ghi");
            }

            Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

            model.addAttribute("userGroups", userGroups);
            model.addAttribute("userInfo", userInfo.get());
            model.addAttribute("name", "Sửa");
            forwartParams(allParams, model);
            return "nguoidung/addnguoidung";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/nguoi-dung";
        }
    }

    @PostMapping(value = "/nguoi-dung/sua")
    public String postnguoiDungSua(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                   @RequestParam Map<String, String> allParams, @ModelAttribute("UserInfo") UserInfo userInfo) {

        try {
            checkErrorMessage(userInfo);

            UserInfo userInfoDb = userInfoRepository.findById(Long.valueOf(allParams.get("id"))).get();

            if (!StringUtils.isEmpty(userInfo.getPassword()) && !userInfoDb.getPassword().equals(CommonUtils.getMD5(userInfo.getPassword()))) {
                userInfo.setPassword(CommonUtils.getMD5(userInfo.getPassword()));
            }

            updateObjectToObject(userInfoDb, userInfo);

            userInfoRepository.save(userInfoDb);

            redirectAttributes.addFlashAttribute("success", "Sửa thành công");
        } catch (ErrorException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống");
            e.printStackTrace();
        }
        return "redirect:/nguoi-dung";
    }

    @RequestMapping(value = "/nguoi-dung/xoa", method = {RequestMethod.GET})
    public String delete(Model model, @RequestParam Map<String, String> allParams,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {
        if (!StringUtils.isEmpty(allParams.get("id"))) {
            UserInfo checkNd = userInfoRepository
                    .findById(Long.valueOf(allParams.get("id"))).get();
            if (checkNd != null) {
                userInfoRepository.delete(checkNd);
            }
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } else {
            redirectAttributes.addFlashAttribute("error", "Xóa thất bại");
        }

        return "redirect:/nguoi-dung?" + queryStringBuilder(allParams);
    }
    @GetMapping("/api/user-info")
    @ResponseBody
    public long nguoiDungValid(@RequestParam("name") String name) {
        return userInfoRepository.countByUsername(name);
    }
}
