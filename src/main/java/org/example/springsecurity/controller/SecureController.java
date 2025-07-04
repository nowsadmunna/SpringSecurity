package org.example.springsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @GetMapping("/admin")
    public String admin(HttpServletRequest req) {
        if (!"ADMIN".equals(req.getAttribute("role"))) return "Access Denied";
        return "Welcome Admin " + req.getAttribute("username");
    }

    @GetMapping("/store")
    public String store(HttpServletRequest req) {
        if (!"STORE_MANAGER".equals(req.getAttribute("role"))) return "Access Denied";
        return "Welcome Store Manager";
    }

    @GetMapping("/production")
    public String production(HttpServletRequest req) {
        if (!"PRODUCTION_OFFICER".equals(req.getAttribute("role"))) return "Access Denied";
        return "Welcome Production Officer";
    }

    @GetMapping("/sales")
    public String sales(HttpServletRequest req) {
        if (!"SALES_OFFICER".equals(req.getAttribute("role"))) return "Access Denied";
        return "Welcome Sales Officer";
    }
}