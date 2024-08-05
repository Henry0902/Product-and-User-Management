package com.project.controller;

import com.project.table.Receipt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class InvoiceController extends BaseController{
    @GetMapping(value = "/invoice")
    public String showInvoice(HttpSession session, Model model) {
        // Lấy hóa đơn từ session
        Receipt receipt = (Receipt) session.getAttribute("receipt");
        if (receipt == null) {
            return "redirect:/cart"; // Chuyển hướng nếu không có hóa đơn
        }
        model.addAttribute("receipt", receipt);
        return "cart/invoice";
    }
}
