/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.controller;

import com.shop.serverfabricshop.ServerFabricShop;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * @author 8279@stud.pgt.su
 */
public class Initializer extends SpringBootServletInitializer{
    @Override
   protected SpringApplicationBuilder configure (SpringApplicationBuilder application){
       return application.sources(ServerFabricShop.class);
   }
}
