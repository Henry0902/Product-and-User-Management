package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.Paginate;
import com.project.model.dto.OrderDto;
import com.project.model.dto.UserInfoDto;
import com.project.model.search.OrderSearch;
import com.project.repository.CartRepository;
import com.project.repository.ListOrderRepository;
import com.project.repository.OrderDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ListOrderController extends BaseController{


    @Autowired
    private OrderDtoRepository orderDtoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping(value = "/list-order")
    public String listorder(Model model, HttpServletRequest req,
                            @RequestParam Map<String, String> allParams) {
        handlingGet(allParams, model, req);
        forwartParams(allParams, model);
        return "cart/listorder";
    }

    private void handlingGet(Map<String, String> allParams, Model model, HttpServletRequest req) {
        Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
        // clear all param if reset
        if (allParams.get("reset") != null) {
            allParams.clear();
        }

        OrderSearch orderSearch = objectMapper.convertValue(allParams, OrderSearch.class);
        orderSearch.normalize();

        Page<OrderDto> orderDtos = orderDtoRepository.selectParams(
                orderSearch.getS_firstname(),
                orderSearch.getS_lastname(),
                orderSearch.getS_phone(),
                orderSearch.getS_status(),
                getPageable(allParams, paginate));

        model.addAttribute("currentPage", paginate.getPage());
        model.addAttribute("totalPage", orderDtos.getTotalPages());
        model.addAttribute("totalElement", orderDtos.getTotalElements());
        model.addAttribute("orderDtos", orderDtos.getContent());
    }


}
