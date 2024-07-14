package com.example.erp.controller;

import com.example.erp.entity.Invoice;
import com.example.erp.service.EbmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EbmsController {

    @Autowired
    private EbmsService ebmsService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password, Model model) {
        String token = ebmsService.authenticate(username, password);
        model.addAttribute("token", token);
        return "dashboard";
    }

    @PostMapping("/invoice")
    public String postInvoice(@RequestBody Invoice invoice, Model model) {
        String response = ebmsService.postInvoice(invoice);
        model.addAttribute("response", response);
        return "invoice";
    }
}
