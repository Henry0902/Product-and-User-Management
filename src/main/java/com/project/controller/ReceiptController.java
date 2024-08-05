package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.Paginate;
import com.project.model.dto.ProductInfoDto;
import com.project.model.search.ProductSearch;
import com.project.repository.*;
import com.project.table.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ReceiptController extends BaseController{

}
