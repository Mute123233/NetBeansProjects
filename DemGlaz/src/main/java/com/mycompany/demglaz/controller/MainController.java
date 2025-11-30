package com.mycompany.demglaz.controller;

import com.mycompany.demglaz.entity.*;
import com.mycompany.demglaz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UsersRepository UsersRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ManufacturersRepository manufacturersRepository;
    @Autowired
    private SuppliersRepository suppliersRepository;
    @Autowired
    private UnitsRepository unitsRepository;
    @Autowired
    private OrderstatusesRepository orderstatusesRepository;
    @Autowired
    private PickuppointsRepository pickuppointsRepository;
    @Autowired
    private UsersRepository usersRepository;

    // ========== ТЕСТ ==========
    @GetMapping("/test")
    public String test() {
        return "API работает!";
    }

// ========== АВТОРИЗАЦИЯ ==========
    @PostMapping("/auth/login")
    public @ResponseBody
    boolean login(@RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {
        for (Users user : usersRepository.findAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/auth/getUserById")
    public @ResponseBody
    Users getUserById(@RequestParam(name = "userId") int userId) {
        return usersRepository.findById(userId).orElse(null);
    }

    @GetMapping("/auth/getUserId")
    public @ResponseBody
    Integer getUserId(@RequestParam(name = "username") String username) {
        for (Users user : usersRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                return user.getIdusers();
            }
        }
        return 0;
    }

    @GetMapping("/auth/getUserId1")
    public @ResponseBody
    Integer getUserId1(@RequestParam(name = "username") String username) {
        for (Users user : usersRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                return user.getIdusers();
            }
        }
        return 0;
    }

    @GetMapping("/auth/getRole")
    public @ResponseBody
    String getRole(@RequestParam(name = "username") String username) {
        for (Users user : usersRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                return user.getRole();
            }
        }
        return "Гость";
    }

    // ========== ПОЛУЧЕНИЕ ДАННЫХ ПОЛЬЗОВАТЕЛЯ ==========
    @GetMapping("/auth/getUser")
    public @ResponseBody
    Users getUser(@RequestParam(name = "username") String username) {
        for (Users user : usersRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // ========== ТОВАРЫ ==========
    @GetMapping("/products/all")
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    @PostMapping("/products/add")
    public boolean addProduct(
            @RequestParam String name,
            @RequestParam int idcategory,
            @RequestParam String description,
            @RequestParam int idmanufacturer,
            @RequestParam int idsupplier,
            @RequestParam double price,
            @RequestParam int idunit,
            @RequestParam int stockquantity,
            @RequestParam int discount,
            @RequestParam String image) {

        try {
            Products product = new Products();
            product.setName(name);
            product.setIdcategory(categoriesRepository.findById(idcategory).orElse(null));
            product.setDescription(description);
            product.setIdmanufacturer(manufacturersRepository.findById(idmanufacturer).orElse(null));
            product.setIdsupplier(suppliersRepository.findById(idsupplier).orElse(null));
            product.setPrice(price);
            product.setIdunit(unitsRepository.findById(idunit).orElse(null));
            product.setStockquantity(stockquantity);
            product.setDiscount(discount);
            product.setImage(image);

            productsRepository.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/products/update")
    public boolean updateProduct(
            @RequestParam int idproducts,
            @RequestParam String name,
            @RequestParam int idcategory,
            @RequestParam String description,
            @RequestParam int idmanufacturer,
            @RequestParam int idsupplier,
            @RequestParam double price,
            @RequestParam int idunit,
            @RequestParam int stockquantity,
            @RequestParam int discount,
            @RequestParam String image) {

        try {
            Products product = productsRepository.findById(idproducts).orElse(null);
            if (product == null) {
                return false;
            }

            product.setName(name);
            product.setIdcategory(categoriesRepository.findById(idcategory).orElse(null));
            product.setDescription(description);
            product.setIdmanufacturer(manufacturersRepository.findById(idmanufacturer).orElse(null));
            product.setIdsupplier(suppliersRepository.findById(idsupplier).orElse(null));
            product.setPrice(price);
            product.setIdunit(unitsRepository.findById(idunit).orElse(null));
            product.setStockquantity(stockquantity);
            product.setDiscount(discount);
            product.setImage(image);

            productsRepository.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/products/delete")
    public boolean deleteProduct(@RequestParam int idproducts) {
        try {
            productsRepository.deleteById(idproducts);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== ЗАКАЗЫ ==========
    @GetMapping("/orders/all")
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @PostMapping("/orders/add")
    public boolean addOrder(
            @RequestParam String ordernumber,
            @RequestParam String orderdate,
            @RequestParam String deliverydate,
            @RequestParam int idstatus,
            @RequestParam int idpickuppoint,
            @RequestParam int iduser) {

        try {
            Orders order = new Orders();
            order.setOrdernumber(ordernumber);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            order.setOrderdate(format.parse(orderdate));
            if (deliverydate != null && !deliverydate.isEmpty()) {
                order.setDeliverydate(format.parse(deliverydate));
            }

            order.setIdstatus(orderstatusesRepository.findById(idstatus).orElse(null));
            order.setIdpickuppoint(pickuppointsRepository.findById(idpickuppoint).orElse(null));
            order.setIduser(usersRepository.findById(iduser).orElse(null));

            ordersRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/orders/update")
    public boolean updateOrder(
            @RequestParam int idorders,
            @RequestParam String ordernumber,
            @RequestParam String orderdate,
            @RequestParam String deliverydate,
            @RequestParam int idstatus,
            @RequestParam int idpickuppoint,
            @RequestParam int iduser) {

        try {
            Orders order = ordersRepository.findById(idorders).orElse(null);
            if (order == null) {
                return false;
            }

            order.setOrdernumber(ordernumber);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            order.setOrderdate(format.parse(orderdate));
            if (deliverydate != null && !deliverydate.isEmpty()) {
                order.setDeliverydate(format.parse(deliverydate));
            } else {
                order.setDeliverydate(null);
            }

            order.setIdstatus(orderstatusesRepository.findById(idstatus).orElse(null));
            order.setIdpickuppoint(pickuppointsRepository.findById(idpickuppoint).orElse(null));
            order.setIduser(usersRepository.findById(iduser).orElse(null));

            ordersRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/orders/delete")
    public boolean deleteOrder(@RequestParam int idorders) {
        try {
            ordersRepository.deleteById(idorders);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== ДАННЫЕ ДЛЯ КОМБОБОКСОВ ==========
    @GetMapping("/categories/all")
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @GetMapping("/manufacturers/all")
    public List<Manufacturers> getAllManufacturers() {
        return manufacturersRepository.findAll();
    }

    @GetMapping("/suppliers/all")
    public List<Suppliers> getAllSuppliers() {
        return suppliersRepository.findAll();
    }

    @GetMapping("/units/all")
    public List<Units> getAllUnits() {
        return unitsRepository.findAll();
    }

    @GetMapping("/statuses/all")
    public List<Orderstatuses> getAllStatuses() {
        return orderstatusesRepository.findAll();
    }

    @GetMapping("/pickuppoints/all")
    public List<Pickuppoints> getAllPickupPoints() {
        return pickuppointsRepository.findAll();
    }

    @GetMapping("/users/all")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // ========== ПОИСК ПО ID ==========
    @GetMapping("/categories/{id}")
    public Categories getCategoryById(@PathVariable int id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    @GetMapping("/manufacturers/{id}")
    public Manufacturers getManufacturerById(@PathVariable int id) {
        return manufacturersRepository.findById(id).orElse(null);
    }

    @GetMapping("/suppliers/{id}")
    public Suppliers getSupplierById(@PathVariable int id) {
        return suppliersRepository.findById(id).orElse(null);
    }

    @GetMapping("/units/{id}")
    public Units getUnitById(@PathVariable int id) {
        return unitsRepository.findById(id).orElse(null);
    }

    @GetMapping("/statuses/{id}")
    public Orderstatuses getStatusById(@PathVariable int id) {
        return orderstatusesRepository.findById(id).orElse(null);
    }

    @GetMapping("/pickuppoints/{id}")
    public Pickuppoints getPickupPointById(@PathVariable int id) {
        return pickuppointsRepository.findById(id).orElse(null);
    }

}
