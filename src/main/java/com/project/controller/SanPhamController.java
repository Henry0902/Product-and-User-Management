package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.CommonUtils;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.exception.ErrorException;
import com.project.model.dto.ProductInfoDto;
import com.project.model.search.ProductSearch;
import com.project.repository.*;
import com.project.table.Cart;
import com.project.table.CartItem;
import com.project.table.ProductGroup;
import com.project.table.ProductInfo;
import com.project.utils.ConvertDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Date.parse;

@Controller
public class SanPhamController extends BaseController{
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img/product";
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductInfoDtoReponsitory productInfoDtoRepository;
    @Autowired
    ProductGroupReponsitory productGroupRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private CartRepository cartRepository;

    @GetMapping(value = "/san-pham")
    public String sanPham(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
        handlingGet(allParams, model, req);
        forwartParams(allParams, model);
        return "sanpham/sanpham";
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

    @GetMapping(value = "/san-pham/them-moi")
    public String getsanPhamThemMoi(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) throws JsonProcessingException {
        Iterable<ProductGroup> productGroups = productGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

        model.addAttribute("productGroups", productGroups);
        model.addAttribute("productInfo", new ProductInfo());
        model.addAttribute("name", "Thêm mới");
        forwartParams(allParams, model);
        return "sanpham/addsanpham";
    }

    @PostMapping(value = "/san-pham/them-moi")
    public String postsanPhamThemMoi(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                     @ModelAttribute("productInfo") ProductInfo productInfo,
                                     @RequestParam("productImage") MultipartFile file,
                                     @RequestParam ("imgName") String imgName) {

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


            String imageUUID;
            if (!file.isEmpty()) {
                imageUUID = file.getOriginalFilename();
                Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
                Files.write(fileNameAndPath, file.getBytes());
            } else {
                imageUUID = imgName;
            }
            productInfo.setImageName(imageUUID);


            productInfoRepository.save(productInfo);

            redirectAttributes.addFlashAttribute("success", "Thêm thành công");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/san-pham";
    }

    @GetMapping(value = "/san-pham/sua")
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
            return "redirect:/san-pham";
        }
    }

    @PostMapping(value = "/san-pham/sua")
    public String postsanphamSua(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                   @RequestParam Map<String, String> allParams,
                                 @ModelAttribute("ProductInfo") ProductInfo productInfo,
                                 @RequestParam("productImage") MultipartFile file) {

        try {
            checkErrorMessage(productInfo);

            ProductInfo productInfoDb = productInfoRepository.findById(Long.valueOf(allParams.get("id"))).get();

//            if (!StringUtils.isEmpty(productInfo.getProductOrigin()) && !productInfoDb.getProductOrigin().equals(CommonUtils.getMD5(productInfo.getProductOrigin()))) {
//                productInfo.setProductOrigin(CommonUtils.getMD5(productInfo.getProductOrigin()));
//            }

            updateObjectToObject(productInfoDb, productInfo);

            // Xử lý file upload
            if (!file.isEmpty()) {
                String uploadDirectory = "src/main/resources/static/img/product";
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = Paths.get(uploadDirectory, fileName);

                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());

                productInfoDb.setImageName(fileName);
            }

            productInfoRepository.save(productInfoDb);

            redirectAttributes.addFlashAttribute("success", "Sửa thành công");
        } catch (ErrorException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống");
            e.printStackTrace();
        }
        return "redirect:/san-pham";
    }

    @RequestMapping(value = "/san-pham/xoa", method = {RequestMethod.GET})
    public String delete(Model model, @RequestParam Map<String, String> allParams,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {

        if (!StringUtils.isEmpty(allParams.get("id"))) {
            Long productId = Long.valueOf(allParams.get("id"));
            ProductInfo product = productInfoRepository.findById(productId).orElse(null);

            if (product != null) {
                // Kiểm tra xem sản phẩm có trong giỏ hàng không
                boolean isInCart = checkIfProductInCart(productId);

                if (!isInCart) {
                    productInfoRepository.delete(product);
                    redirectAttributes.addFlashAttribute("success", "Xóa thành công");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Không thể xóa sản phẩm vì nó đang có trong giỏ hàng");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Xóa thất bại");
        }


        return "redirect:/san-pham?" + queryStringBuilder(allParams);
    }

    private boolean checkIfProductInCart(Long productId) {

        List<CartItem> cartItems = cartItemRepository.findAll(); // Tùy thuộc vào cách bạn lấy dữ liệu từ giỏ hàng
        return cartItems.stream().anyMatch(item -> item.getProductInfo().getId().equals(productId));
    }


    @GetMapping("/api/product-info")
    @ResponseBody
    public long sanphamValid(@RequestParam("name") String name) {
        return productInfoRepository.countByProductName(name);
    }
}
