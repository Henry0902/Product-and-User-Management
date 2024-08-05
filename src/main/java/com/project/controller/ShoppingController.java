package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.exception.ErrorException;
import com.project.model.dto.ProductInfoDto;
import com.project.model.search.ProductSearch;
import com.project.repository.ProductGroupReponsitory;
import com.project.repository.ProductInfoDtoReponsitory;
import com.project.repository.ProductInfoRepository;

import com.project.table.ProductGroup;
import com.project.table.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
public class ShoppingController extends BaseController {
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductInfoDtoReponsitory productInfoDtoRepository;
    @Autowired
    ProductGroupReponsitory productGroupRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/shopping")
    public String sanPham(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
        handlingGet(allParams, model, req);
        forwartParams(allParams, model);
        return "cart/shopping";
    }

    private void handlingGet(Map<String, String> allParams, Model model, HttpServletRequest req) {
        Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
        // clear all param if reset
        if (allParams.get("reset") != null) {
            allParams.clear();
        }

        ProductSearch productSearch = objectMapper.convertValue(allParams, ProductSearch.class);
        productSearch.normalize();

        Page<ProductInfoDto> productInfos = productInfoDtoRepository.selectParams(
                productSearch.getS_pname(),
                productSearch.getS_porigin(),
                productSearch.getS_status(),
                getPageable(allParams, paginate));

        model.addAttribute("currentPage", paginate.getPage());
        model.addAttribute("totalPage", productInfos.getTotalPages());
        model.addAttribute("totalElement", productInfos.getTotalElements());
        model.addAttribute("productInfos", productInfos.getContent());
    }

    @GetMapping(value = "/shopping/them-moi")
    public String getsanPhamThemMoi(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) throws JsonProcessingException {
        Iterable<ProductGroup> productGroups = productGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

        model.addAttribute("productGroups", productGroups);
        model.addAttribute("productInfo", new ProductInfo());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "cart/addcart";
    }

    @PostMapping(value = "/shopping/them-moi")
    public String postsanPhamThemMoi(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                     @RequestParam Map<String, String> allParams, @ModelAttribute("productInfo") ProductInfo productInfo) {
        try {
            checkErrorMessage(productInfo);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if (productInfo.getGroupId() == 0) throw new Exception("Chọn nhóm cho sản phẩm");
            ProductInfo checkProductName = productInfoRepository.findByProductName(productInfo.getProductName());
            if (checkProductName != null) throw new Exception("Tên sản phẩm đã tồn tại");

            productInfo.setCreateBy(getProductName(req));
            productInfo.setPrice(productInfo.getPrice());
            productInfo.setCreateTime(new Date());
            productInfo.setProductOrigin(productInfo.getProductOrigin());
            productInfo.setProductDate(productInfo.getProductDate());

            productInfoRepository.save(productInfo);

            redirectAttributes.addFlashAttribute("success", "Thêm thành công");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/shopping";
    }

    @GetMapping(value = "/shopping/sua")
    public String getsanPhamSua(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception("Sửa thất bại");
            }
            Optional<ProductInfo> productInfo = productInfoRepository.findById(Long.valueOf(allParams.get("id")));
            if (!productInfo.isPresent()) {
                throw new Exception("Không tồn tại bản ghi");
            }

            Iterable<ProductGroup> productGroups = productGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

            model.addAttribute("productGroups", productGroups);
            model.addAttribute("productInfo", productInfo.get());
            model.addAttribute("name", "Sửa");
            forwartParams(allParams, model);
            return "sanpham/addsanpham";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/shopping";
        }
    }

    @PostMapping(value = "/shopping/sua")
    public String postsanphamSua(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                 @RequestParam Map<String, String> allParams, @ModelAttribute("ProductInfo") ProductInfo productInfo) {

        try {
            checkErrorMessage(productInfo);

            ProductInfo productInfoDb = productInfoRepository.findById(Long.valueOf(allParams.get("id"))).get();

//            if (!StringUtils.isEmpty(productInfo.getProductOrigin()) && !productInfoDb.getProductOrigin().equals(CommonUtils.getMD5(productInfo.getProductOrigin()))) {
//                productInfo.setProductOrigin(CommonUtils.getMD5(productInfo.getProductOrigin()));
//            }

            updateObjectToObject(productInfoDb, productInfo);

            productInfoRepository.save(productInfoDb);

            redirectAttributes.addFlashAttribute("success", "Sửa thành công");
        } catch (ErrorException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống");
            e.printStackTrace();
        }
        return "redirect:/shopping";
    }

    @RequestMapping(value = "/shopping/xoa", method = {RequestMethod.GET})
    public String delete(Model model, @RequestParam Map<String, String> allParams,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {
        if (!StringUtils.isEmpty(allParams.get("id"))) {
            ProductInfo checkNd = productInfoRepository
                    .findById(Long.valueOf(allParams.get("id"))).get();
            if (checkNd != null) {
                productInfoRepository.delete(checkNd);
            }
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } else {
            redirectAttributes.addFlashAttribute("error", "Xóa thất bại");
        }

        return "redirect:/shopping?" + queryStringBuilder(allParams);
    }

}