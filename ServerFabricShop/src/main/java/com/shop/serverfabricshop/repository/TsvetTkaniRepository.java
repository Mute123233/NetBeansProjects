/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.shop.serverfabricshop.repository;
import com.shop.serverfabricshop.entity.Tsvettkani;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author 8279@stud.pgt.su
 */
@Repository
public interface TsvetTkaniRepository extends CrudRepository<Tsvettkani, Integer>{
    
}
