/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.demglazforms.forms;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
public class Guest extends javax.swing.JFrame {

    private List<NewJPanelf_1> productPanels = new ArrayList<>();
    private JSONArray originalProducts = new JSONArray();
    private int userId;

    public Guest() {
        this(0);
    }

    public Guest(int userId) {
        this.userId = userId;
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());
        setupBackButton();
        loadUserInfo();
        loadProductsFromAPI();
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
            setTitle("Гостевая панель");
        } else {
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
                            setTitle("Гостевая панель - " + fio.trim());
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

            // Используем NewJPanelf_1 вместо NewJPanelf
            NewJPanelf_1 jp = new NewJPanelf_1();
            jp.setProductData(product);

            // Заполняем информацию о товаре
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

            // Применяем подсветку в зависимости от скидки
            applyProductHighlighting(jp, product);

            // Загружаем изображение
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

    private void applyProductHighlighting(NewJPanelf_1 jp, JSONObject product) {
        int discount = product.optInt("discount", 0);
        int stock = product.optInt("stockquantity", 0);

        jp.setBackground(Color.WHITE);
        jp.setOpaque(true);

        // Подсветка в зависимости от скидки
        if (discount > 15) {
            jp.setBackground(new Color(0x2E8B57)); // Зеленый для больших скидок
        } else if (discount > 0) {
            jp.setBackground(new Color(0x90EE90)); // Светло-зеленый для маленьких скидок
        }

        // Подсветка для отсутствующего товара
        if (stock == 0) {
            jp.setBackground(new Color(173, 216, 230)); // Голубой для отсутствующего товара
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(127, 255, 0));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 250, 154));
        jLabel1.setText("ООО «Обувь» - Гость");

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
                .addGap(398, 398, 398)
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

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(0, 250, 154));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1331, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1333, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );

        jTabbedPane1.addTab("Товары", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1345, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the FlatLaf look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Guest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Guest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
