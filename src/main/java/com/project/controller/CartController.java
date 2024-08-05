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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller

public class CartController extends BaseController{

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

    @GetMapping(value = "/cart/{userId}")
    public String sanPham(Model model, HttpServletRequest req,
                          @PathVariable Long userId,
                          @RequestParam Map<String, String> allParams) {
        handlingGet(allParams, model, req);
        Cart cart = cartRepository.findByUserInfoId(userId);
        if (cart != null) {
            model.addAttribute("cartItems", cart.getItems());
            model.addAttribute("totalPrice", cart.getTotalPrice());
        } else {
            model.addAttribute("cartItems", null);
            model.addAttribute("totalPrice", 0);
        }
        forwartParams(allParams, model);
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


    @PostMapping("/cart/add-to-cart/{userId}")
    public String addToCart(@PathVariable Long userId,
                            @RequestParam Long productId,
                            @RequestParam int quantity, HttpSession session) {

        Cart cart = cartRepository.findByUserInfoId(userId);
        if (cart == null) {
            cart = new Cart();
            UserInfo userInfo = userInfoRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            cart.setUserInfo(userInfo);
            cartRepository.save(cart);
        }

        ProductInfo productInfo = productInfoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductInfoId(cart.getId(), productId);
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

        return "redirect:/cart/" + userId;
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
            return "redirect:/cart/" + userId; // Chuyển hướng về trang giỏ hàng của người dùng
        }
    }
    redirectAttributes.addFlashAttribute("error", "Xóa thất bại");
    return "redirect:/cart?" + queryStringBuilder(allParams);
}
    @RequestMapping(value = "/cart/update-so-luong", method = {RequestMethod.GET})
    public String updateSoLuong(Model model,
                         @RequestParam Map<String, String> allParams,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {


        if (!StringUtils.isEmpty(allParams.get("id"))) {
            CartItem checkNd = cartItemRepository
                    .findById(Long.valueOf(allParams.get("id"))).get();

            if (checkNd != null) {
                String dau = allParams.get("dau");
                if (dau.equals("cong")) {
                    checkNd.setQuantity(checkNd.getQuantity() + 1);
                    cartItemRepository.save(checkNd);
                    Optional<Cart> cart = cartRepository.findById(checkNd.getCart().getId());
                    cart.get().setTotalPrice(cart.get().getTotalPrice() + checkNd.getPrice());
                    cartRepository.save(cart.get());
                } else {
                    checkNd.setQuantity(checkNd.getQuantity() - 1);
                    cartItemRepository.save(checkNd);
                    Optional<Cart> cart = cartRepository.findById(checkNd.getCart().getId());
                    cart.get().setTotalPrice(cart.get().getTotalPrice() - checkNd.getPrice());
                    cartRepository.save(cart.get());
                }
                Long userId = checkNd.getCart().getUserInfo().getId(); // Lấy userId từ CartItem

                redirectAttributes.addFlashAttribute("success", "Tăng số lượng thành công");
                return "redirect:/cart/" + userId; // Chuyển hướng về trang giỏ hàng của người dùng
            }
        }
        redirectAttributes.addFlashAttribute("error", "Xóa thất bại");
        return "redirect:/cart?" + queryStringBuilder(allParams);
    }



}
