/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.shop.serverfabricshop;

import com.shop.serverfabricshop.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author 8279@stud.pgt.su
 */
@SpringBootApplication()
@ComponentScan (basePackageClasses=MainController.class)
public class ServerFabricShop {

    public static void main(String[] args) {
        SpringApplication.run(ServerFabricShop.class, args);
    }
}
