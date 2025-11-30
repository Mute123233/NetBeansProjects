package com.mycompany.demglazforms.forms;

import com.mycompany.demglazforms.forms.NewJPanelZ;
import com.mycompany.demglazforms.forms.NewJPanelf;
import com.mycompany.demglazforms.forms.RedactTovar;
import com.mycompany.demglazforms.forms.RedactZakaz;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Admin extends javax.swing.JFrame {

    private int userId;
    private List<NewJPanelf> productPanels = new ArrayList<>();
    private List<NewJPanelZ> orderPanels = new ArrayList<>();
    private JSONArray originalProducts = new JSONArray();
    private JSONArray originalOrders = new JSONArray();
    // Старый конструктор без параметров (для совместимости)

    public Admin() {
        this(0); // Вызываем новый конструктор с ID по умолчанию
    }

    public Admin(int userId) {
        this.userId = userId;
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());
        setupBackButton();
        loadUserInfo();
        initializeComboBoxes();
        setupEventListeners();
        loadProductsFromAPI();
        loadOrdersFromAPI();
    }

    private void setupBackButton() {
        jButton2.addActionListener(e -> goBackToAuthorization());
    }

    private void goBackToAuthorization() {
        this.dispose(); // Закрываем текущую форму
        Autorization authForm = new Autorization();
        authForm.setVisible(true);
    }

    private void loadUserInfo() {
        if (userId == 0) {
            jLabel2.setText("Гость");
            return;
        }

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/auth/getUserById?userId=" + userId)
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject user = new JSONObject(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        String lastName = user.optString("lastname", "");
                        String firstName = user.optString("firstname", "");
                        String middleName = user.optString("middlename", "");
                        String fio = lastName + " " + firstName + " " + middleName;

                        jLabel2.setText(fio.trim());
                        setTitle("Панель администратора - " + fio.trim());
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    jLabel2.setText("Гость");
                });
            }
        }).start();
    }

    private void initializeComboBoxes() {
        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        jComboBox3.removeAllItems();
        jComboBox4.removeAllItems();
        jComboBox5.removeAllItems();

        jComboBox1.addItem("Все категории");
        jComboBox2.addItem("По названию");
        jComboBox2.addItem("По цене (возр.)");
        jComboBox2.addItem("По цене (убыв.)");
        jComboBox2.addItem("По количеству");
        jComboBox2.addItem("По скидке");
        jComboBox3.addItem("Все статусы");
        jComboBox4.addItem("Все поставщики");
        jComboBox5.addItem("По номеру заказа");
        jComboBox5.addItem("По дате заказа");
        jComboBox5.addItem("По пользователю");

        loadComboBoxData();
    }

    private void setupEventListeners() {
        // Поиск товаров
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterProducts();
            }
        });

        // Фильтры товаров
        jComboBox1.addActionListener(e -> filterProducts());
        jComboBox4.addActionListener(e -> filterProducts());

        // Сортировка товаров
        jComboBox2.addActionListener(e -> sortProducts());

        // Поиск заказов
        jTextField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterOrders();
            }
        });

        // Сортировка заказов
        jComboBox5.addActionListener(e -> sortOrders());
    }

    private void loadComboBoxData() {
        loadCategories();
        loadSuppliers();
        loadStatuses();
    }

    private void loadCategories() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/categories/all")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray categories = new JSONArray(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject category = categories.getJSONObject(i);
                            jComboBox1.addItem(category.getString("name"));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadSuppliers() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/suppliers/all")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray suppliers = new JSONArray(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        for (int i = 0; i < suppliers.length(); i++) {
                            JSONObject supplier = suppliers.getJSONObject(i);
                            jComboBox4.addItem(supplier.getString("name"));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadStatuses() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/statuses/all")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray statuses = new JSONArray(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        for (int i = 0; i < statuses.length(); i++) {
                            JSONObject status = statuses.getJSONObject(i);
                            jComboBox3.addItem(status.getString("name"));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ========== МЕТОДЫ ДЛЯ ТОВАРОВ ==========
    public void loadProductsFromAPI() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/products/all")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    originalProducts = new JSONArray(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        displayProducts();
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Ошибка загрузки товаров: " + response.code());
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
                });
            }
        }).start();
    }

    private void displayProducts() {
        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS));
        jPanel3.removeAll();
        productPanels.clear();

        for (int i = 0; i < originalProducts.length(); i++) {
            JSONObject product = originalProducts.getJSONObject(i);
            NewJPanelf jp = new NewJPanelf();
            jp.setAdminForm(this);
            jp.setProductData(product);

            String categoryName = "Без категории";
            if (product.has("idcategory") && !product.isNull("idcategory")) {
                JSONObject category = product.getJSONObject("idcategory");
                categoryName = category.optString("name", "Без категории");
            }

            String productName = product.optString("name", "Без названия");
            jp.getLabel1().setText(categoryName + " | " + productName);
            jp.getLabel2().setText("Описание: " + product.optString("description", "Нет описания"));

            String manufacturerName = "Не указан";
            if (product.has("idmanufacturer") && !product.isNull("idmanufacturer")) {
                JSONObject manufacturer = product.getJSONObject("idmanufacturer");
                manufacturerName = manufacturer.optString("name", "Не указан");
            }
            jp.getLabel3().setText("Производитель: " + manufacturerName);

            String supplierName = "Не указан";
            if (product.has("idsupplier") && !product.isNull("idsupplier")) {
                JSONObject supplier = product.getJSONObject("idsupplier");
                supplierName = supplier.optString("name", "Не указан");
            }
            jp.getLabel4().setText("Поставщик: " + supplierName);

            double price = product.optDouble("price", 0);
            int discount = product.optInt("discount", 0);

            if (discount > 0) {
                double finalPrice = price * (100 - discount) / 100;
                String priceText = "<html><strike><font color='red'>" + price + " руб.</font></strike> "
                        + "<font color='black'>" + String.format("%.2f", finalPrice) + " руб.</font></html>";
                jp.getLabel5().setText(priceText);
            } else {
                jp.getLabel5().setText("Цена: " + price + " руб.");
            }

            String unitName = "шт.";
            if (product.has("idunit") && !product.isNull("idunit")) {
                JSONObject unit = product.getJSONObject("idunit");
                unitName = unit.optString("name", "шт.");
            }
            jp.getLabel6().setText("Единица измерения: " + unitName);

            int stock = product.optInt("stockquantity", 0);
            jp.getLabel7().setText("Количество: " + stock);
            jp.getLabel8().setText("Скидка: " + discount + "%");

            applyProductHighlighting(jp, product);

            // Берем путь к изображению напрямую из JSON продукта
            String imagePath = product.optString("image", "");
            System.out.println("Загружаем товар: " + productName + ", путь к изображению: '" + imagePath + "'");

            loadProductImage(jp, imagePath);

            jPanel3.add(jp);
            jPanel3.add(Box.createVerticalStrut(10));
            productPanels.add(jp);
        }

        jPanel3.revalidate();
        jPanel3.repaint();
    }

    private void applyProductHighlighting(NewJPanelf jp, JSONObject product) {
        int discount = product.optInt("discount", 0);
        int stock = product.optInt("stockquantity", 0);

        jp.setBackground(Color.WHITE);
        jp.setOpaque(true);

        if (discount > 15) {
            jp.setBackground(new Color(0x2E8B57));
        }
        if (stock == 0) {
            jp.setBackground(new Color(173, 216, 230));
        }
    }

    private void loadProductImage(NewJPanelf jp, String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            setDefaultProductImage(jp);
            return;
        }

        try {
            jp.getLabel9().setText("");
            BufferedImage image = null;

            File file = new File(imagePath);
            if (file.exists()) {
                image = ImageIO.read(file);
            } else {
                // Пробуем загрузить из папки images
                File imageFile = new File("images/" + imagePath);
                if (imageFile.exists()) {
                    image = ImageIO.read(imageFile);
                } else {
                    // Пробуем загрузить как ресурс
                    java.net.URL imageUrl = getClass().getResource("/images/" + imagePath);
                    if (imageUrl != null) {
                        image = ImageIO.read(imageUrl);
                    } else {
                        // Пробуем загрузить из папки img
                        imageUrl = getClass().getResource("/img/" + imagePath);
                        if (imageUrl != null) {
                            image = ImageIO.read(imageUrl);
                        }
                    }
                }
            }

            if (image != null) {
                Image scaledImage = image.getScaledInstance(130, 100, Image.SCALE_SMOOTH);
                jp.getLabel9().setIcon(new ImageIcon(scaledImage));
            } else {
                setDefaultProductImage(jp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            setDefaultProductImage(jp);
        } catch (IOException e) {
            e.printStackTrace();
            setDefaultProductImage(jp);
        } catch (Exception e) {
            e.printStackTrace();
            setDefaultProductImage(jp);
        }
    }

    private void setDefaultProductImage(NewJPanelf jp) {
        try {
            // Загружаем заглушку из ресурсов
            java.net.URL imageUrl = getClass().getResource("/img/picture.png");
            if (imageUrl != null) {
                BufferedImage image = ImageIO.read(imageUrl);
                Image scaledImage = image.getScaledInstance(130, 100, Image.SCALE_SMOOTH);
                jp.getLabel9().setIcon(new ImageIcon(scaledImage));
            } else {
                jp.getLabel9().setText("Нет фото");
            }
        } catch (Exception e) {
            jp.getLabel9().setText("Нет фото");
        }
    }

    // ========== МЕТОДЫ ДЛЯ ЗАКАЗОВ ==========
    public void loadOrdersFromAPI() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/orders/all")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    originalOrders = new JSONArray(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        displayOrders();
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Ошибка загрузки заказов: " + response.code());
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
                });
            }
        }).start();
    }

    private void displayOrders() {
        jPanel4.setLayout(new BoxLayout(jPanel4, BoxLayout.Y_AXIS));
        jPanel4.removeAll();
        orderPanels.clear();

        for (int i = 0; i < originalOrders.length(); i++) {
            JSONObject order = originalOrders.getJSONObject(i);
            NewJPanelZ jp = new NewJPanelZ();
            jp.setAdminForm(this);
            jp.setOrderData(order);

            String orderNumber = order.optString("ordernumber", "Без номера");
            jp.getLabel1().setText("Номер заказа: " + orderNumber);

            String orderDate = order.optString("orderdate", "Не указана");
            jp.getLabel2().setText("Дата заказа: " + formatDate(orderDate));

            String deliveryDate = order.optString("deliverydate", "Не указана");
            jp.getLabel3().setText("Дата доставки: " + formatDate(deliveryDate));

            String statusName = "Не указан";
            if (order.has("idstatus") && !order.isNull("idstatus")) {
                JSONObject status = order.getJSONObject("idstatus");
                statusName = status.optString("name", "Не указан");
            }
            jp.getLabel4().setText("Статус: " + statusName);

            String address = "Не указан";
            if (order.has("idpickuppoint") && !order.isNull("idpickuppoint")) {
                JSONObject pickupPoint = order.getJSONObject("idpickuppoint");
                String city = pickupPoint.optString("city", "");
                String street = pickupPoint.optString("street", "");
                String houseNumber = pickupPoint.optString("housenumber", "");
                address = city + ", " + street + ", " + houseNumber;
            }
            jp.getLabel5().setText("Адрес: " + address);

            String userName = "Не указан";
            if (order.has("iduser") && !order.isNull("iduser")) {
                JSONObject user = order.getJSONObject("iduser");
                String lastName = user.optString("lastname", "");
                String firstName = user.optString("firstname", "");
                String middleName = user.optString("middlename", "");
                userName = lastName + " " + firstName + " " + middleName;
            }
            jp.getLabel6().setText("Пользователь: " + userName.trim());

            jPanel4.add(jp);
            jPanel4.add(Box.createVerticalStrut(10));
            orderPanels.add(jp);
        }

        jPanel4.revalidate();
        jPanel4.repaint();
    }

    private String formatDate(String dateTime) {
        if (dateTime == null || dateTime.equals("null") || dateTime.isEmpty()) {
            return "Не указана";
        }
        if (dateTime.contains("T")) {
            return dateTime.split("T")[0];
        }
        if (dateTime.contains(" ")) {
            return dateTime.split(" ")[0];
        }
        return dateTime;
    }

    // ========== КНОПКИ ДЕЙСТВИЙ ==========
    public void addProduct() {
        System.out.println("Кнопка добавления товара нажата!");
        JSONObject newProduct = new JSONObject();
        newProduct.put("idproducts", 0);
        RedactTovar dialog = new RedactTovar(this, true, newProduct, true);
        dialog.setVisible(true);
        if (dialog.isDataChanged()) {
            loadProductsFromAPI();
        }
    }

    public void addOrder() {
        System.out.println("Кнопка добавления заказа нажата!");
        JSONObject newOrder = new JSONObject();
        newOrder.put("idorders", 0);
        RedactZakaz dialog = new RedactZakaz(this, true, newOrder, true);
        dialog.setVisible(true);
        if (dialog.isDataChanged()) {
            loadOrdersFromAPI();
        }
    }

    public void editProduct(NewJPanelf productPanel) {
        if (productPanel.getProductData() != null) {
            RedactTovar dialog = new RedactTovar(this, true, productPanel.getProductData(), false);
            dialog.setVisible(true);
            if (dialog.isDataChanged()) {
                loadProductsFromAPI();
            }
        }
    }

    public void editOrder(NewJPanelZ orderPanel) {
        if (orderPanel.getOrderData() != null) {
            RedactZakaz dialog = new RedactZakaz(this, true, orderPanel.getOrderData(), false);
            dialog.setVisible(true);
            if (dialog.isDataChanged()) {
                loadOrdersFromAPI();
            }
        }
    }

    public void deleteProduct(NewJPanelf productPanel) {
        if (productPanel.getProductData() != null) {
            int productId = productPanel.getProductData().getInt("idproducts");
            String productName = productPanel.getProductData().getString("name");

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Удалить товар: " + productName + "?",
                    "Подтверждение удаления",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteProductFromAPI(productId);
            }
        }
    }

    public void deleteOrder(NewJPanelZ orderPanel) {
        if (orderPanel.getOrderData() != null) {
            int orderId = orderPanel.getOrderData().getInt("idorders");
            String orderNumber = orderPanel.getOrderData().getString("ordernumber");

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Удалить заказ: " + orderNumber + "?",
                    "Подтверждение удаления",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteOrderFromAPI(orderId);
            }
        }
    }

    private void deleteProductFromAPI(int productId) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("idproducts", String.valueOf(productId))
                        .build();

                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/products/delete")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                SwingUtilities.invokeLater(() -> {
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            if (responseBody.equals("true")) {
                                JOptionPane.showMessageDialog(this, "Товар удален");
                                loadProductsFromAPI();
                            } else {
                                JOptionPane.showMessageDialog(this, "Ошибка удаления");
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                    }
                });
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Ошибка сети: " + e.getMessage());
                });
            }
        }).start();
    }

    private void deleteOrderFromAPI(int orderId) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("idorders", String.valueOf(orderId))
                        .build();

                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/orders/delete")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                SwingUtilities.invokeLater(() -> {
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            if (responseBody.equals("true")) {
                                JOptionPane.showMessageDialog(this, "Заказ удален");
                                loadOrdersFromAPI();
                            } else {
                                JOptionPane.showMessageDialog(this, "Ошибка удаления");
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                    }
                });
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Ошибка сети: " + e.getMessage());
                });
            }
        }).start();
    }

    // ========== ПОИСК И ФИЛЬТРАЦИЯ ТОВАРОВ ==========
    public void filterProducts() {
        String searchText = jTextField1.getText().toLowerCase();
        String selectedCategory = jComboBox1.getSelectedItem().toString();
        String selectedSupplier = jComboBox4.getSelectedItem().toString();

        jPanel3.removeAll();

        for (NewJPanelf panel : productPanels) {
            // Собираем ВСЕ данные карточки для поиска
            String panelText = (panel.getLabel1().getText() + " "
                    + // Категория + название
                    panel.getLabel2().getText() + " "
                    + // Описание
                    panel.getLabel3().getText() + " "
                    + // Производитель
                    panel.getLabel4().getText() + " "
                    + // Поставщик
                    panel.getLabel5().getText() + " "
                    + // Цена
                    panel.getLabel6().getText() + " "
                    + // Единица измерения
                    panel.getLabel7().getText() + " "
                    + // Количество
                    panel.getLabel8().getText() // Скидка
                    ).toLowerCase();

            boolean matchesSearch = searchText.isEmpty() || panelText.contains(searchText);
            boolean matchesCategory = selectedCategory.equals("Все категории")
                    || panel.getLabel1().getText().contains(selectedCategory);
            boolean matchesSupplier = selectedSupplier.equals("Все поставщики")
                    || panel.getLabel4().getText().contains(selectedSupplier);

            if (matchesSearch && matchesCategory && matchesSupplier) {
                jPanel3.add(panel);
                jPanel3.add(Box.createVerticalStrut(10));
            }
        }

        jPanel3.revalidate();
        jPanel3.repaint();
    }

    // ========== ПОИСК И СОРТИРОВКА ЗАКАЗОВ ==========
    public void filterOrders() {
        String searchText = jTextField2.getText().toLowerCase();
        String selectedStatus = jComboBox3.getSelectedItem().toString();

        jPanel4.removeAll();

        for (NewJPanelZ panel : orderPanels) {
            // Собираем ВСЕ данные карточки заказа для поиска
            String panelText = (panel.getLabel1().getText() + " "
                    + // Номер заказа
                    panel.getLabel2().getText() + " "
                    + // Дата заказа
                    panel.getLabel3().getText() + " "
                    + // Дата доставки
                    panel.getLabel4().getText() + " "
                    + // Статус
                    panel.getLabel5().getText() + " "
                    + // Адрес
                    panel.getLabel6().getText() // Пользователь
                    ).toLowerCase();

            boolean matchesSearch = searchText.isEmpty() || panelText.contains(searchText);
            boolean matchesStatus = selectedStatus.equals("Все статусы")
                    || panel.getLabel4().getText().contains(selectedStatus);

            if (matchesSearch && matchesStatus) {
                jPanel4.add(panel);
                jPanel4.add(Box.createVerticalStrut(10));
            }
        }

        jPanel4.revalidate();
        jPanel4.repaint();
    }

    public void sortOrders() {
        String sortType = jComboBox5.getSelectedItem().toString();

        orderPanels.sort((p1, p2) -> {
            try {
                switch (sortType) {
                    case "По номеру заказа":
                        return extractOrderNumber(p1.getLabel1()).compareTo(extractOrderNumber(p2.getLabel1()));
                    case "По дате заказа":
                        return extractDate(p1.getLabel2()).compareTo(extractDate(p2.getLabel2()));
                    case "По пользователю":
                        return extractUserName(p1.getLabel6()).compareTo(extractUserName(p2.getLabel6()));
                    default:
                        return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        });

        refreshOrdersDisplay();
    }

    private String extractOrderNumber(JLabel label) {
        return label.getText().replace("Номер заказа: ", "").trim();
    }

    private String extractDate(JLabel label) {
        return label.getText().replace("Дата заказа: ", "").trim();
    }

    private String extractUserName(JLabel label) {
        return label.getText().replace("Пользователь: ", "").trim();
    }

    private void refreshOrdersDisplay() {
        jPanel4.removeAll();
        for (NewJPanelZ panel : orderPanels) {
            jPanel4.add(panel);
            jPanel4.add(Box.createVerticalStrut(10));
        }
        jPanel4.revalidate();
        jPanel4.repaint();
    }

    // ========== СОРТИРОВКА ТОВАРОВ ==========
    public void sortProducts() {
        String sortType = jComboBox2.getSelectedItem().toString();

        productPanels.sort((p1, p2) -> {
            try {
                switch (sortType) {
                    case "По названию":
                        return p1.getLabel1().getText().compareTo(p2.getLabel1().getText());
                    case "По цене (возр.)":
                        return Double.compare(getPriceFromLabel(p1.getLabel5()), getPriceFromLabel(p2.getLabel5()));
                    case "По цене (убыв.)":
                        return Double.compare(getPriceFromLabel(p2.getLabel5()), getPriceFromLabel(p1.getLabel5()));
                    case "По количеству":
                        return Integer.compare(getStockFromLabel(p1.getLabel7()), getStockFromLabel(p2.getLabel7()));
                    case "По скидке":
                        return Integer.compare(getDiscountFromLabel(p1.getLabel8()), getDiscountFromLabel(p2.getLabel8()));
                    default:
                        return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        });

        refreshProductsDisplay();
    }

    private double getPriceFromLabel(JLabel label) {
        try {
            String text = label.getText();
            if (text.contains("<html>")) {
                String[] parts = text.split(">");
                String priceStr = parts[parts.length - 2].replace(" руб.</font", "").trim();
                return Double.parseDouble(priceStr);
            } else {
                return Double.parseDouble(text.replace("Цена: ", "").replace(" руб.", "").trim());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    private int getStockFromLabel(JLabel label) {
        try {
            return Integer.parseInt(label.getText().replace("Количество: ", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private int getDiscountFromLabel(JLabel label) {
        try {
            return Integer.parseInt(label.getText().replace("Скидка: ", "").replace("%", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private String copyImageToProject(String sourcePath) {
        try {
            File sourceFile = new File(sourcePath);
            String fileName = sourceFile.getName();

            // Папка для изображений в проекте
            File projectImagesDir = new File("src/main/resources/images/");
            if (!projectImagesDir.exists()) {
                projectImagesDir.mkdirs();
            }

            File destFile = new File(projectImagesDir, fileName);

            // Копируем файл
            java.nio.file.Files.copy(
                    sourceFile.toPath(),
                    destFile.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            // Возвращаем относительный путь
            return "images/" + fileName;

        } catch (Exception e) {
            System.out.println("Ошибка копирования изображения: " + e.getMessage());
            return sourcePath; // Возвращаем оригинальный путь если не удалось скопировать
        }
    }

    private void refreshProductsDisplay() {
        jPanel3.removeAll();
        for (NewJPanelf panel : productPanels) {
            jPanel3.add(panel);
            jPanel3.add(Box.createVerticalStrut(10));
        }
        jPanel3.revalidate();
        jPanel3.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton18 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton24 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(127, 255, 0));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 250, 154));
        jLabel1.setText("ООО «Обувь» - Панель администратора");

        jButton2.setBackground(new java.awt.Color(0, 250, 154));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Назад");
        jButton2.setBorderPainted(false);
        jButton2.setDefaultCapable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("ФИО");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(539, 539, 539)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2)
                    .addComponent(jLabel2))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jTabbedPane2.setForeground(new java.awt.Color(0, 250, 154));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setBackground(new java.awt.Color(127, 255, 0));
        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 250, 154));
        jLabel23.setText("Поиск");

        jTextField1.setBackground(new java.awt.Color(127, 255, 0));
        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setBackground(new java.awt.Color(127, 255, 0));
        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 250, 154));
        jLabel24.setText("Фильтр");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jButton18.setBackground(new java.awt.Color(0, 250, 154));
        jButton18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Добавить");
        jButton18.setBorderPainted(false);
        jButton18.setDefaultCapable(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel25.setBackground(new java.awt.Color(127, 255, 0));
        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 250, 154));
        jLabel25.setText("Категория");

        jLabel31.setBackground(new java.awt.Color(127, 255, 0));
        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 250, 154));
        jLabel31.setText("Сортировка");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1306, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel3);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel31))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, 150, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1318, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel23))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(451, 451, 451))))
        );

        jTabbedPane2.addTab("Товары", jPanel8);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setBackground(new java.awt.Color(127, 255, 0));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 250, 154));
        jLabel5.setText("Поиск");

        jTextField2.setBackground(new java.awt.Color(127, 255, 0));
        jTextField2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setBackground(new java.awt.Color(127, 255, 0));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 250, 154));
        jLabel6.setText("Фильтр");

        jLabel7.setBackground(new java.awt.Color(127, 255, 0));
        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 250, 154));
        jLabel7.setText("Сортировка");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jButton24.setBackground(new java.awt.Color(0, 250, 154));
        jButton24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setText("Добавить");
        jButton24.setBorderPainted(false);
        jButton24.setDefaultCapable(false);
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1296, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel4);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox3, 0, 178, Short.MAX_VALUE)
                            .addComponent(jTextField2))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton24)
                        .addGap(487, 487, 487))))
        );

        jTabbedPane2.addTab("Заказы", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        addProduct();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        addOrder();
    }//GEN-LAST:event_jButton24ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the FlatLaf look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Admin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton24;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
