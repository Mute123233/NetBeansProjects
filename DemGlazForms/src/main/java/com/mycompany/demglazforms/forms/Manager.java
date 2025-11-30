/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.demglazforms.forms;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author 8277@stud.pgt.su
 */
public class Manager extends javax.swing.JFrame {

    private List<NewJPanelf_1> productPanels = new ArrayList<>();
    private List<NewJPanelZ_1> orderPanels = new ArrayList<>();
    private JSONArray originalProducts = new JSONArray();
    private JSONArray originalOrders = new JSONArray();
    private int userId;

    public Manager() {
        this(0);
    }

    public Manager(int userId) {
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
                        setTitle("Панель менеджера - " + fio.trim());
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
        jComboBox12.removeAllItems();
        jComboBox13.removeAllItems();
        jComboBox14.removeAllItems();
        jComboBox15.removeAllItems();
        jComboBox16.removeAllItems();

        jComboBox12.addItem("Все категории");
        jComboBox13.addItem("По названию");
        jComboBox13.addItem("По цене (возр.)");
        jComboBox13.addItem("По цене (убыв.)");
        jComboBox13.addItem("По количеству");
        jComboBox13.addItem("По скидке");
        jComboBox14.addItem("Все статусы");
        jComboBox15.addItem("Все поставщики");
        jComboBox16.addItem("По номеру заказа");
        jComboBox16.addItem("По дате заказа");
        jComboBox16.addItem("По пользователю");

        loadComboBoxData();
    }

    private void setupEventListeners() {
        // Поиск товаров
        jTextField6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterProducts();
            }
        });

        // Фильтры товаров
        jComboBox12.addActionListener(e -> filterProducts());
        jComboBox15.addActionListener(e -> filterProducts());

        // Сортировка товаров
        jComboBox13.addActionListener(e -> sortProducts());

        // Поиск заказов
        jTextField7.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterOrders();
            }
        });

        // Фильтр статусов заказов
        jComboBox14.addActionListener(e -> filterOrders());

        // Сортировка заказов
        jComboBox16.addActionListener(e -> sortOrders());
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
                            jComboBox12.addItem(category.getString("name"));
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
                            jComboBox15.addItem(supplier.getString("name"));
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
                            jComboBox14.addItem(status.getString("name"));
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void displayProducts() {
        jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.Y_AXIS));
        jPanel5.removeAll();
        productPanels.clear();

        for (int i = 0; i < originalProducts.length(); i++) {
            JSONObject product = originalProducts.getJSONObject(i);
            NewJPanelf_1 jp = new NewJPanelf_1();
            jp.setProductData(product);

            // Заполняем информацию о товаре (как в предыдущем коде)
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

            String imagePath = product.optString("image", "");
            loadProductImage(jp, imagePath);

            jPanel5.add(jp);
            jPanel5.add(Box.createVerticalStrut(10));
            productPanels.add(jp);
        }

        jPanel5.revalidate();
        jPanel5.repaint();
    }

    // ========== ПОИСК И ФИЛЬТРАЦИЯ ТОВАРОВ ==========
    public void filterProducts() {
        String searchText = jTextField6.getText().toLowerCase();
        String selectedCategory = jComboBox12.getSelectedItem().toString();
        String selectedSupplier = jComboBox15.getSelectedItem().toString();

        jPanel5.removeAll();

        for (NewJPanelf_1 panel : productPanels) {
            String panelText = (panel.getLabel1().getText() + " "
                    + panel.getLabel2().getText() + " "
                    + panel.getLabel3().getText() + " "
                    + panel.getLabel4().getText() + " "
                    + panel.getLabel5().getText() + " "
                    + panel.getLabel6().getText() + " "
                    + panel.getLabel7().getText() + " "
                    + panel.getLabel8().getText()).toLowerCase();

            boolean matchesSearch = searchText.isEmpty() || panelText.contains(searchText);
            boolean matchesCategory = selectedCategory.equals("Все категории")
                    || panel.getLabel1().getText().contains(selectedCategory);
            boolean matchesSupplier = selectedSupplier.equals("Все поставщики")
                    || panel.getLabel4().getText().contains(selectedSupplier);

            if (matchesSearch && matchesCategory && matchesSupplier) {
                jPanel5.add(panel);
                jPanel5.add(Box.createVerticalStrut(10));
            }
        }

        jPanel5.revalidate();
        jPanel5.repaint();
    }

    public void sortProducts() {
        String sortType = jComboBox13.getSelectedItem().toString();

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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void displayOrders() {
        jPanel7.setLayout(new BoxLayout(jPanel7, BoxLayout.Y_AXIS));
        jPanel7.removeAll();
        orderPanels.clear();

        for (int i = 0; i < originalOrders.length(); i++) {
            JSONObject order = originalOrders.getJSONObject(i);
            NewJPanelZ_1 jp = new NewJPanelZ_1();
            jp.setOrderData(order);

            // Заполняем информацию о заказе (как в предыдущем коде)
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

            jPanel7.add(jp);
            jPanel7.add(Box.createVerticalStrut(10));
            orderPanels.add(jp);
        }

        jPanel7.revalidate();
        jPanel7.repaint();
    }

    // ========== ПОИСК И ФИЛЬТРАЦИЯ ЗАКАЗОВ ==========
    public void filterOrders() {
        String searchText = jTextField7.getText().toLowerCase();
        String selectedStatus = jComboBox14.getSelectedItem().toString();

        jPanel7.removeAll();

        for (NewJPanelZ_1 panel : orderPanels) {
            String panelText = (panel.getLabel1().getText() + " "
                    + panel.getLabel2().getText() + " "
                    + panel.getLabel3().getText() + " "
                    + panel.getLabel4().getText() + " "
                    + panel.getLabel5().getText() + " "
                    + panel.getLabel6().getText()).toLowerCase();

            boolean matchesSearch = searchText.isEmpty() || panelText.contains(searchText);
            boolean matchesStatus = selectedStatus.equals("Все статусы")
                    || panel.getLabel4().getText().contains(selectedStatus);

            if (matchesSearch && matchesStatus) {
                jPanel7.add(panel);
                jPanel7.add(Box.createVerticalStrut(10));
            }
        }

        jPanel7.revalidate();
        jPanel7.repaint();
    }

    public void sortOrders() {
        String sortType = jComboBox16.getSelectedItem().toString();

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

    // ========== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ==========
    private void applyProductHighlighting(NewJPanelf_1 jp, JSONObject product) {
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

    private void loadProductImage(NewJPanelf_1 jp, String imagePath) {
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
        } catch (Exception e) {
            e.printStackTrace();
            setDefaultProductImage(jp);
        }
    }

    private void setDefaultProductImage(NewJPanelf_1 jp) {
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

    private String extractOrderNumber(JLabel label) {
        return label.getText().replace("Номер заказа: ", "").trim();
    }

    private String extractDate(JLabel label) {
        return label.getText().replace("Дата заказа: ", "").trim();
    }

    private String extractUserName(JLabel label) {
        return label.getText().replace("Пользователь: ", "").trim();
    }

    private void refreshProductsDisplay() {
        jPanel5.removeAll();
        for (NewJPanelf_1 panel : productPanels) {
            jPanel5.add(panel);
            jPanel5.add(Box.createVerticalStrut(10));
        }
        jPanel5.revalidate();
        jPanel5.repaint();
    }

    private void refreshOrdersDisplay() {
        jPanel7.removeAll();
        for (NewJPanelZ_1 panel : orderPanels) {
            jPanel7.add(panel);
            jPanel7.add(Box.createVerticalStrut(10));
        }
        jPanel7.revalidate();
        jPanel7.repaint();
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
        jTextField6 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jComboBox16 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1595, 664));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(127, 255, 0));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 250, 154));
        jLabel1.setText("ООО «Обувь» - Панель менеджера");

        jButton2.setBackground(new java.awt.Color(0, 250, 154));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Назад");
        jButton2.setBorderPainted(false);
        jButton2.setDefaultCapable(false);

        jLabel2.setText("ФИО");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(511, 511, 511)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 574, Short.MAX_VALUE)
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

        jTextField6.setBackground(new java.awt.Color(127, 255, 0));
        jTextField6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setBackground(new java.awt.Color(127, 255, 0));
        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 250, 154));
        jLabel24.setText("Фильтр");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel31.setBackground(new java.awt.Color(127, 255, 0));
        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 250, 154));
        jLabel31.setText("Сортировка");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1203, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel5);

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel33.setBackground(new java.awt.Color(127, 255, 0));
        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 250, 154));
        jLabel33.setText("Поставщик");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(jLabel31)
                    .addComponent(jLabel33))
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField6)
                    .addComponent(jComboBox12, 0, 170, Short.MAX_VALUE)
                    .addComponent(jComboBox13, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox15, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel23))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(431, 431, 431))))
        );

        jTabbedPane2.addTab("Товары", jPanel8);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setPreferredSize(new java.awt.Dimension(1583, 576));

        jLabel25.setBackground(new java.awt.Color(127, 255, 0));
        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 250, 154));
        jLabel25.setText("Поиск");

        jTextField7.setBackground(new java.awt.Color(127, 255, 0));
        jTextField7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel26.setBackground(new java.awt.Color(127, 255, 0));
        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 250, 154));
        jLabel26.setText("Фильтр");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel32.setBackground(new java.awt.Color(127, 255, 0));
        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 250, 154));
        jLabel32.setText("Сортировка");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1223, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel7);

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(29, 29, 29)
                        .addComponent(jComboBox16, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel32))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(470, 470, 470))))
        );

        jTabbedPane2.addTab("Заказы", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the FlatLaf look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Manager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
