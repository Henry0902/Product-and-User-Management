package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.model.search.ProductGroupSearch;
import com.project.repository.ProductGroupReponsitory;
import com.project.repository.ProductModuleReponsitory;
import com.project.repository.UserGroupPermissionRepository;
import com.project.table.ProductGroup;
import com.project.table.ProductModule;
import com.project.table.UserGroupPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class NhomSanPhamController extends BaseController{
    @Autowired
    ProductGroupReponsitory productGroupRepository;
    @Autowired
    ProductModuleReponsitory productModuleRepository;
    @Autowired
    UserGroupPermissionRepository userGroupPermissionRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/nhom-san-pham")
    public String nhomsanPham (Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {

        handlingGet(allParams, model);

        forwartParams(allParams, model);
        return "nhomsanpham/nhomsanpham";
    }

    private void handlingGet(Map<String, String> allParams, Model model) {
        Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
        // clear all param if reset
        if (allParams.get("reset") != null) {
            allParams.clear();
        }

        ProductGroupSearch productGroupSearch = objectMapper.convertValue(allParams, ProductGroupSearch.class);
        productGroupSearch.normalize();

        Page<ProductGroup> productGroups = productGroupRepository.selectParams(
                productGroupSearch.getS_pname(),
                productGroupSearch.getS_status(),
                getPageable(allParams, paginate));

        model.addAttribute("currentPage", paginate.getPage());
        model.addAttribute("totalPage", productGroups.getTotalPages());
        model.addAttribute("totalElement", productGroups.getTotalElements());
        model.addAttribute("productGroups", productGroups.getContent());
    }

    @GetMapping(value = "/nhom-san-pham/them-moi")
    public String getNhomsanPhamThemMoi (Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {

        Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

        model.addAttribute("productModules", productModules);
        model.addAttribute("productGroup", new ProductGroup());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "nhomsanpham/addnhomsanpham";
    }

    @PostMapping(value = "/nhom-san-pham/them-moi")
    public String postNhomsanPhamThemMoi (Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                            @RequestParam Map<String, String> allParams, @ModelAttribute("productGroup") ProductGroup productGroup,
                                            @RequestParam(value = "permissions", required = false) List<Long> permissions) {

        try {
            productGroup.setCreateTime(new Date());
            productGroup.setCreateBy(getUserName(req));
            checkErrorMessage(productGroup);

            productGroupRepository.save(productGroup);

            if(permissions != null) {
                ArrayList<UserGroupPermission> listUserGroupPermission = new ArrayList<>();
                for (Long moduleId : permissions) {
                    UserGroupPermission userGroupPermission = new UserGroupPermission();
                    userGroupPermission.setGroupId(productGroup.getId());
                    userGroupPermission.setModuleId(moduleId);
                    listUserGroupPermission.add(userGroupPermission);

                }
                userGroupPermissionRepository.saveAll(listUserGroupPermission);
            }
            redirectAttributes.addFlashAttribute("success", "Thêm thành công");

            return "redirect:/nhom-san-pham";
        } catch (Exception e) {
            e.printStackTrace();
            Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

            model.addAttribute("productModules", productModules);
            forwartParams(allParams, model);
            model.addAttribute("name", "Thêm mới");
            model.addAttribute("error", e.getMessage());
        }
        return "nhomsanpham/addnhomsanpham";
    }

    @GetMapping(value = "/nhom-san-pham/sua")
    public String getNhomsanPhamSua (Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception("Sửa thất bại");
            }
            Optional<ProductGroup> productGroup = productGroupRepository.findById(Long.valueOf(allParams.get("id")));
            if (!productGroup.isPresent()) {
                throw new Exception("Không tồn tại bản ghi");
            }
            Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

            List<UserGroupPermission> userGroupPermissions = userGroupPermissionRepository.findByGroupId(productGroup.get().getId());

            model.addAttribute("userGroupPermissions", new Gson().toJson(userGroupPermissions));
            model.addAttribute("productModules", productModules);
            model.addAttribute("productGroup", productGroup.get());
            model.addAttribute("name", "Sửa");
            forwartParams(allParams, model);
            return "nhomsanpham/addnhomsanpham";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/nhom-san-pham";
        }
    }

    @PostMapping(value = "/nhom-san-pham/sua")
    public String postNhomsanPhamSua (Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                        @RequestParam Map<String, String> allParams, @ModelAttribute("productGroup") ProductGroup productGroup,
                                        @RequestParam(value = "permissions", required = false) List<Long> permissions) {

        try {
            System.out.println(permissions);
            checkErrorMessage(productGroup);

            ProductGroup productGroupDb = productGroupRepository.findById(Long.valueOf(allParams.get("id"))).get();

            updateObjectToObject(productGroupDb, productGroup);

            productGroupRepository.save(productGroupDb);

            if(permissions != null) {
                userGroupPermissionRepository.deleteByGroupId(productGroup.getId());
                ArrayList<UserGroupPermission> listUserGroupPermission = new ArrayList<>();
                for (Long moduleId : permissions) {
                    UserGroupPermission userGroupPermission = new UserGroupPermission();
                    userGroupPermission.setGroupId(productGroup.getId());
                    userGroupPermission.setModuleId(moduleId);
                    listUserGroupPermission.add(userGroupPermission);

                }
                userGroupPermissionRepository.saveAll(listUserGroupPermission);
            }

            redirectAttributes.addFlashAttribute("success", "Sửa thành công");

            return "redirect:/nhom-san-pham";
        } catch (Exception e) {
            e.printStackTrace();
            Iterable<ProductModule> productModules = productModuleRepository.selectAll(Contains.TT_MODULE_HOATDONG);

            model.addAttribute("productModules", productModules);
            forwartParams(allParams, model);
            model.addAttribute("name", "Sửa");
            model.addAttribute("error", e.getMessage());
        }
        return "nhomsanpham/addnhomsanpham";
    }

    @RequestMapping(value = "/nhom-san-pham/xoa", method = { RequestMethod.GET })
    public String delete(Model model, @RequestParam Map<String, String> allParams,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {
        if (!StringUtils.isEmpty(allParams.get("id"))) {
            ProductGroup checkNd = productGroupRepository
                    .findById(Long.valueOf(allParams.get("id"))).get();
            if (checkNd != null) {
                productGroupRepository.delete(checkNd);
            }
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } else {
            redirectAttributes.addFlashAttribute("error", "Xóa thất bại");
        }

        return "redirect:/nhom-san-pham?" + queryStringBuilder(allParams);
    }

    @GetMapping("/api/product-group")
    @ResponseBody
    public long nhomSanPhamValid(@RequestParam("name") String name, @RequestParam("id") Long id) {
        return productGroupRepository.countUpdate(name, id);
    }
}
