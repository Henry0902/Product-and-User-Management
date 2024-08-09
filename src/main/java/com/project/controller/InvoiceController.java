package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.Paginate;
import com.project.exception.ResourceNotFoundException;
import com.project.model.dto.ProductInfoDto;
import com.project.model.search.ProductSearch;
import com.project.model.search.UserSearch;
import com.project.repository.*;
import com.project.table.Cart;
import com.project.table.CartItem;
import com.project.table.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller

public class InvoiceController extends BaseController{
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


    @GetMapping(value = "/invoice")
    public String getinvoice(Model model, HttpServletRequest req,
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
        return "cart/invoice";
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




}
