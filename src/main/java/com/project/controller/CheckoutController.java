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
import java.util.Map;

@Controller

public class CheckoutController extends BaseController{
    @Autowired
    private ProductInfoDtoReponsitory productInfoDtoRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping(value = "/checkout/{userId}")
    public String getCheckout(Model model, HttpServletRequest req,
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
                           @ModelAttribute("receipt") Receipt receipt, HttpSession session) {
        try {
            // Retrieve cart items from session
            HashMap<Long, CartItem> cartItems = (HashMap<Long, CartItem>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new HashMap<>();
            }

            // Set receipt details
            receipt.setReceiptFirstName(req.getParameter("firstName"));
            receipt.setReceiptLastName(req.getParameter("lastName"));
            receipt.setReceiptAddress(req.getParameter("address"));
            receipt.setReceiptPhone(req.getParameter("phone"));
            receipt.setReceiptDate(new Timestamp(new Date().getTime()));
            receipt.setReceiptStatus(true);
            receiptRepository.save(receipt);

            // Update each CartItem to associate it with the receipt and save it
            for (Map.Entry<Long, CartItem> entry : cartItems.entrySet()) {
                CartItem cartItem = entry.getValue();
                cartItem.setReceipt(receipt);
                cartItemRepository.save(cartItem);
            }

            // Clear the cart in the session after successful processing
            cartItems.clear();
            session.setAttribute("cartItems", cartItems);
            session.setAttribute("totalPrice", 0);

            // Add success message
            redirectAttributes.addFlashAttribute("successMessage", "Checkout successful!");

            return "redirect:/invoice/"; // or wherever you want to redirect after successful checkout
        } catch (Exception e) {
            // Handle any exceptions here
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred during checkout.");

            return "redirect:/checkout/{userId}"; // or wherever you want to redirect in case of error
        }
    }


}
