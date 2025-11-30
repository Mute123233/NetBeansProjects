/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping("/index")
    public ModelAndView getPageIndex() {
        return new ModelAndView("index");
    }

    @GetMapping("/cabinet")
    public ModelAndView getPageCabinet() {
        return new ModelAndView("cabinet");
    }

    @GetMapping("/novelties")
    public ModelAndView getPageNovelties() {
        return new ModelAndView("novelties");
    }
    
    @GetMapping("/order-status")
    public ModelAndView getPageOrderStatus() {
        return new ModelAndView("order-status");
    }
    
    @GetMapping("/login")
    public ModelAndView getPageLogin() {
        return new ModelAndView("login");
    }
}
