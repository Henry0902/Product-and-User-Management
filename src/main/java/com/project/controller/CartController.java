package com.project.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.Contains;
import com.project.common.Paginate;
import com.project.exception.ErrorException;
import com.project.exception.ResourceNotFoundException;
import com.project.model.dto.ProductInfoDto;
import com.project.model.search.ProductSearch;
import com.project.repository.*;
import com.project.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller

public class CartController extends BaseController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductInfoDtoReponsitory productInfoDtoRepository;
    @Autowired
    ProductGroupReponsitory productGroupRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/cart")
    public String sanPham(Model model, HttpServletRequest req,
                          @RequestParam Map<String, String> allParams) {
        handlingGet(allParams, model, req);
        Cart cart = cartRepository.findByUserInfoId(Long.valueOf(allParams.get("userId")));
        if (cart != null) {
            model.addAttribute("cartItems", cart.getItems());
            model.addAttribute("totalPrice", cart.getTotalPrice());
        } else {
            model.addAttribute("cartItems", null);
            model.addAttribute("totalPrice", 0);
        }
        forwartParams(allParams, model);
        model.addAttribute("userId", cart.getUserInfo().getId());
        return "cart/cart";
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

    @PostMapping("/cart")
    public String createCart(@RequestParam Map<String, String> allParams) {


        UserInfo userInfo = userInfoRepository.findById(Long.valueOf(allParams.get("userId")))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserInfoId(Long.valueOf(allParams.get("userId")));
        if (cart == null) {
            cart = new Cart();
            cart.setUserInfo(userInfo);
            cartRepository.save(cart);
        }

        return "redirect:/cart?userId=" + allParams.get("userId");
    }

    @PostMapping("/cart/add-to-cart")
    public String addToCart(@RequestParam Map<String, String> allParams,
                            @RequestParam int quantity,
                            RedirectAttributes redirectAttributes, HttpServletRequest req) {

        Cart cart = cartRepository.findByUserInfoId(Long.valueOf(allParams.get("userId")));
        if (cart == null) {
            cart = new Cart();
            UserInfo userInfo = userInfoRepository.findById(Long.valueOf(allParams.get("userId")))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            cart.setUserInfo(userInfo);
            cartRepository.save(cart);
        }

        ProductInfo productInfo = productInfoRepository.findById(Long.valueOf(allParams.get("productId")))
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductInfoId(cart.getId(), productInfo.getId());
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductInfo(productInfo);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(productInfo.getPrice());
            cartItemRepository.save(cartItem);
        }

        recalculateTotalPrice(cart);
        cartRepository.save(cart);
        redirectAttributes.addFlashAttribute("message", "Thêm vào giỏ hàng thành công");

        return "redirect:/home-shopping?" + queryStringBuilder(allParams);
    }

    private void recalculateTotalPrice(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }

    @RequestMapping(value = "/cart/xoa", method = {RequestMethod.GET})
    public String delete(Model model,
                         @RequestParam Map<String, String> allParams,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {


        if (!StringUtils.isEmpty(allParams.get("id"))) {
            CartItem checkNd = cartItemRepository
                    .findById(Long.valueOf(allParams.get("id"))).get();
            if (checkNd != null) {
                cartItemRepository.delete(checkNd);
                Long userId = checkNd.getCart().getUserInfo().getId(); // Lấy userId từ CartItem
                Optional<Cart> cart = cartRepository.findById(checkNd.getCart().getId());


                cart.get().setTotalPrice(cart.get().getTotalPrice() - checkNd.getPrice() * checkNd.getQuantity());

                cartRepository.save(cart.get());
                redirectAttributes.addFlashAttribute("success", "Xóa thành công");
                return "redirect:/cart?userId=" + allParams.get("userId"); // Chuyển hướng về trang giỏ hàng của người dùng
            }
        }
        redirectAttributes.addFlashAttribute("error", "Xóa thất bại");
        return "redirect:/cart?" + queryStringBuilder(allParams);
    }


    @PostMapping(value = "/cart/update-quantity")
    public String updateSoLuong(Model model,
                                @RequestParam Map<String, String> allParams,
                                RedirectAttributes redirectAttributes, HttpServletRequest req) {

        List<Long> cartItemIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        // Giả sử rằng allParams chứa các cặp giá trị như: cartItemId-1, quantity-1, ...
        // Do đó, chúng ta có thể trích xuất các giá trị dựa trên tên tham số
        for (String key : allParams.keySet()) {
            if (key.startsWith("cartItemId-")) {
                Long cartItemId = Long.valueOf(allParams.get(key));
                cartItemIds.add(cartItemId);
            } else if (key.startsWith("quantity-")) {
                Integer quantity = Integer.valueOf(allParams.get(key));
                quantities.add(quantity);
            }
        }

        // Kiểm tra tính hợp lệ của dữ liệu
//        if (cartItemIds.size() != quantities.size()) {
//            redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ.");
//            return "redirect:/cart?userId=" + allParams.get("userId");
//        }

        // Cập nhật số lượng cho từng CartItem
        for (int i = 0; i < cartItemIds.size(); i++) {
            Long cartItemId = cartItemIds.get(i);
            Integer quantity = quantities.get(i);

            CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);
            }
        }

        // Tính toán lại tổng giá và lưu Cart
        Cart cart = cartRepository.findByUserInfoId(Long.valueOf(allParams.get("userId")));
        recalculateTotalPrice(cart);
        cartRepository.save(cart);

        redirectAttributes.addFlashAttribute("success", "Cập nhật số lượng thành công");
        return "redirect:/checkout?userId=" + allParams.get("userId");
    }

}