package com.mycompany.demglazforms.forms;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;

public class RedactTovar extends javax.swing.JDialog {

    private JSONObject product;
    private boolean dataChanged = false;
    private boolean isAddMode;
    private String currentImagePath = ""; // Добавляем поле для хранения полного пути

    public RedactTovar(java.awt.Frame parent, boolean modal, JSONObject product, boolean isAddMode) {
        super(parent, modal);
        this.product = product;
        this.isAddMode = isAddMode;
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());
        setLocationRelativeTo(parent);

        if (isAddMode) {
            this.setTitle("ООО «Обувь» - Добавление товара");
            jButton17.setText("Добавить");
        }

        loadProductData();
        loadComboBoxData();
    }

    private void loadComboBoxData() {
        loadCategories();
        loadManufacturers();
        loadSuppliers();
        loadUnits();
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
                        jComboBox1.removeAllItems();
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

    private void loadManufacturers() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/manufacturers/all")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray manufacturers = new JSONArray(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        jComboBox2.removeAllItems();
                        for (int i = 0; i < manufacturers.length(); i++) {
                            JSONObject manufacturer = manufacturers.getJSONObject(i);
                            jComboBox2.addItem(manufacturer.getString("name"));
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
                        jComboBox3.removeAllItems();
                        for (int i = 0; i < suppliers.length(); i++) {
                            JSONObject supplier = suppliers.getJSONObject(i);
                            jComboBox3.addItem(supplier.getString("name"));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadUnits() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/api/units/all")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray units = new JSONArray(responseBody);

                    SwingUtilities.invokeLater(() -> {
                        jComboBox4.removeAllItems();
                        for (int i = 0; i < units.length(); i++) {
                            JSONObject unit = units.getJSONObject(i);
                            jComboBox4.addItem(unit.getString("name"));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean validateData() {
        if (jTextField1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите название товара");
            return false;
        }
        try {
            Double.parseDouble(jTextField3.getText());
            Integer.parseInt(jTextField4.getText());
            Integer.parseInt(jTextField5.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Проверьте числовые поля");
            return false;
        }
        return true;
    }

    private void loadProductData() {
        if (product != null && !isAddMode) {
            jTextField1.setText(product.optString("name", ""));
            jTextField2.setText(product.optString("description", ""));
            jTextField3.setText(String.valueOf(product.optDouble("price", 0)));
            jTextField4.setText(String.valueOf(product.optInt("stockquantity", 0)));
            jTextField5.setText(String.valueOf(product.optInt("discount", 0)));

            // Загружаем путь из базы
            String imagePath = product.optString("image", "");
            currentImagePath = imagePath; // Сохраняем полный путь
            jTextField6.setText(imagePath);

            // Загружаем и показываем изображение в jLabel2
            loadImageToLabel(imagePath);
        } else {
            // Для нового товара очищаем поле пути
            currentImagePath = "";
            jTextField6.setText("");
            setDefaultImage();
        }
    }

    private void loadImageToLabel(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            setDefaultImage();
            return;
        }

        try {
            jLabel2.setText("");
            BufferedImage image = null;

            File file = new File(imagePath);
            if (file.exists() && file.length() > 0) {
                image = ImageIO.read(file);
            } else {
                // Пробуем загрузить из папки images
                File imageFile = new File("images/" + imagePath);
                if (imageFile.exists() && imageFile.length() > 0) {
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

            if (image != null && image.getWidth() > 0 && image.getHeight() > 0) {
                // Получаем размеры jLabel2
                int labelWidth = jLabel2.getWidth() > 0 ? jLabel2.getWidth() : 150;
                int labelHeight = jLabel2.getHeight() > 0 ? jLabel2.getHeight() : 150;

                Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                jLabel2.setIcon(new ImageIcon(scaledImage));
            } else {
                setDefaultImage();
            }
        } catch (Exception e) {
            System.out.println("Ошибка загрузки изображения: " + e.getMessage());
            setDefaultImage();
        }
    }

    private void setDefaultImage() {
        try {
            // Загружаем заглушку из ресурсов
            java.net.URL defaultImageUrl = getClass().getResource("/img/picture.png");
            if (defaultImageUrl != null) {
                ImageIcon defaultIcon = new ImageIcon(defaultImageUrl);
                int labelWidth = jLabel2.getWidth() > 0 ? jLabel2.getWidth() : 150;
                int labelHeight = jLabel2.getHeight() > 0 ? jLabel2.getHeight() : 150;

                Image scaledImage = defaultIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                jLabel2.setIcon(new ImageIcon(scaledImage));
            } else {
                jLabel2.setText("Нет изображения");
                jLabel2.setIcon(null);
            }
        } catch (Exception e) {
            jLabel2.setText("Нет изображения");
            jLabel2.setIcon(null);
        }
    }

    private void saveProduct() {
        if (!validateData()) {
            return;
        }

        new Thread(() -> {
            try {
                // Используем сохраненный полный путь к изображению
                String imagePath = currentImagePath;
                System.out.println("Сохраняем товар с изображением: " + imagePath);

                OkHttpClient client = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder()
                        .add("name", jTextField1.getText().trim())
                        .add("description", jTextField2.getText().trim())
                        .add("price", jTextField3.getText().trim())
                        .add("stockquantity", jTextField4.getText().trim())
                        .add("discount", jTextField5.getText().trim())
                        .add("image", imagePath) // Сохраняем полный путь
                        .add("idcategory", "1") // Временно фиксированные значения
                        .add("idmanufacturer", "1")
                        .add("idsupplier", "1")
                        .add("idunit", "1");

                if (!isAddMode) {
                    formBody.add("idproducts", String.valueOf(product.getInt("idproducts")));
                }

                RequestBody body = formBody.build();
                String url = isAddMode ? "http://localhost:8080/api/products/add"
                        : "http://localhost:8080/api/products/update";

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                SwingUtilities.invokeLater(() -> {
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            if (responseBody.equals("true")) {
                                dataChanged = true;
                                JOptionPane.showMessageDialog(this,
                                        isAddMode ? "Товар добавлен" : "Товар обновлен");
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Ошибка сохранения");
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

    public boolean isDataChanged() {
        return dataChanged;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
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
        jLabel25 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(127, 255, 0));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 250, 154));
        jLabel1.setText("ООО «Обувь» - Редактирование товара");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel25.setBackground(new java.awt.Color(127, 255, 0));
        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 250, 154));
        jLabel25.setText("Категория");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel26.setBackground(new java.awt.Color(127, 255, 0));
        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 250, 154));
        jLabel26.setText("Компания");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel30.setBackground(new java.awt.Color(127, 255, 0));
        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 250, 154));
        jLabel30.setText("Поставщик");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel32.setBackground(new java.awt.Color(127, 255, 0));
        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 250, 154));
        jLabel32.setText("Ед. измерения");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel27.setBackground(new java.awt.Color(127, 255, 0));
        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 250, 154));
        jLabel27.setText("Название");

        jTextField1.setBackground(new java.awt.Color(127, 255, 0));
        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel33.setBackground(new java.awt.Color(127, 255, 0));
        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 250, 154));
        jLabel33.setText("Описание");

        jTextField2.setBackground(new java.awt.Color(127, 255, 0));
        jTextField2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel34.setBackground(new java.awt.Color(127, 255, 0));
        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 250, 154));
        jLabel34.setText("Цена");

        jTextField3.setBackground(new java.awt.Color(127, 255, 0));
        jTextField3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel35.setBackground(new java.awt.Color(127, 255, 0));
        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 250, 154));
        jLabel35.setText("Количество");

        jTextField4.setBackground(new java.awt.Color(127, 255, 0));
        jTextField4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel36.setBackground(new java.awt.Color(127, 255, 0));
        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 250, 154));
        jLabel36.setText("Скидка");

        jTextField5.setBackground(new java.awt.Color(127, 255, 0));
        jTextField5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton17.setBackground(new java.awt.Color(0, 250, 154));
        jButton17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Редактировать");
        jButton17.setBorderPainted(false);
        jButton17.setDefaultCapable(false);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton18.setBackground(new java.awt.Color(0, 250, 154));
        jButton18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Выбрать изображение");
        jButton18.setBorderPainted(false);
        jButton18.setDefaultCapable(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton17)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel27))
                                .addGap(10, 10, 10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36))
                                .addGap(30, 30, 30)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, 150, Short.MAX_VALUE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(153, 153, 153))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(jLabel36))
                        .addGap(18, 18, 18)
                        .addComponent(jButton17)
                        .addGap(54, 54, 54))))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        if (validateData()) {
            saveProduct();
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите изображение товара");

        // Фильтр для изображений
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Изображения", "jpg", "jpeg", "png", "gif", "bmp"
        ));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            try {
                // Проверяем, что файл существует и не пустой
                if (selectedFile.exists() && selectedFile.length() > 0) {
                    // Загружаем изображение для предпросмотра
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage();

                    // Проверяем размеры изображения
                    if (image.getWidth(null) > 0 && image.getHeight(null) > 0) {
                        // Масштабируем для предпросмотра
                        int previewWidth = 150;
                        int previewHeight = 150;

                        Image scaledImage = image.getScaledInstance(previewWidth, previewHeight, Image.SCALE_SMOOTH);
                        jLabel2.setIcon(new ImageIcon(scaledImage));

                        // Сохраняем ПОЛНЫЙ ПУТЬ к изображению
                        currentImagePath = imagePath;
                        jTextField6.setText(imagePath); // Показываем путь в текстовом поле
                        System.out.println("Изображение выбрано, полный путь: " + currentImagePath);
                    } else {
                        JOptionPane.showMessageDialog(this, "Выбранный файл не является корректным изображением");
                        setDefaultImage();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Выбранный файл пуст или недоступен");
                    setDefaultImage();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки изображения: " + e.getMessage());
                setDefaultImage();
            }
        }

    }//GEN-LAST:event_jButton18ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RedactTovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RedactTovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RedactTovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RedactTovar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
