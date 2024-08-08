package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.Paginate;
import com.project.exception.ResourceNotFoundException;
import com.project.model.dto.ProductInfoDto;
import com.project.model.dto.UserInfoDto;
import com.project.model.search.ProductSearch;
import com.project.model.search.UserSearch;
import com.project.repository.*;
import com.project.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class CheckoutController extends BaseController{
    @Autowired
    private ProductInfoDtoReponsitory productInfoDtoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private CartItemRepository cartItemRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping(value = "/checkout")
    public String getCheckout(Model model, HttpServletRequest req,
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
        model.addAttribute("cartId", cart.getId());
        model.addAttribute("userId", cart.getUserInfo().getId());
        return "cart/checkout";
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

        UserSearch userSearch = objectMapper.convertValue(allParams, UserSearch.class);
        userSearch.normalize();

        model.addAttribute("currentPage", paginate.getPage());
        model.addAttribute("totalPage", productInfos.getTotalPages());
        model.addAttribute("totalElement", productInfos.getTotalElements());
        model.addAttribute("productInfos", productInfos.getContent());
    }

    @PostMapping(value = "/checkout")
    public String checkout(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                           @RequestParam Map<String, String> allParams,
                           @ModelAttribute("checkout") Checkout checkout) {
        try {
            // Set receipt details
            checkout.setFirstName(req.getParameter("firstName"));
            checkout.setLastName(req.getParameter("lastName"));
            checkout.setAddress(req.getParameter("address"));
            checkout.setPhone(req.getParameter("phone"));
            checkout.setDate(new Timestamp(new Date().getTime()));
            checkout.setStatus("Pending");
            checkout.setUserInfo(userInfoRepository.findById(Long.valueOf(allParams.get("userId"))).orElseThrow(() -> new ResourceNotFoundException("User not found")));
            checkout.setCart(cartRepository.findById(Long.valueOf(allParams.get("cartId"))).orElseThrow(() -> new ResourceNotFoundException("Cart not found")));
            checkoutRepository.save(checkout);

            List<CartItem> cartItems = cartItemRepository.findByCartId(Long.valueOf(allParams.get("cartId")));
            for (CartItem cartItem : cartItems) {
                cartItem.setCheckout(checkout);
                cartItemRepository.save(cartItem);
                cartItemRepository.deleteById(cartItem.getId());

            }

            Cart cart = cartRepository.findById(Long.valueOf(allParams.get("cartId"))).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
            cart.setTotalPrice(0);
            cartRepository.save(cart);

            // Add success message
            redirectAttributes.addFlashAttribute("successMessage", "Checkout successful!");

            return "redirect:/list-order"; // or wherever you want to redirect after successful checkout
        } catch (Exception e) {
            // Handle any exceptions here
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred during checkout.");

            return "redirect:/checkout?userId=" + allParams.get("userId"); // or wherever you want to redirect in case of error
        }
    }


}
