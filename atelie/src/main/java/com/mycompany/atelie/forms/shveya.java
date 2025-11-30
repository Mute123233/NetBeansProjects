/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.atelie.forms;

import com.formdev.flatlaf.FlatLightLaf;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author 8280@stud.pgt.su
 */
public class shveya extends javax.swing.JFrame {

    private static final String BASE_URL = "http://localhost:8080/atelie/";
    private Integer currentShveyaId;

    /**
     * Creates new form shveya
     */
    public shveya() {

        FlatLightLaf.setup();
        initComponents();
        ImageIcon icon = new ImageIcon("src/main/resources/img/icon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    public shveya(Integer shveyaId) {
        this.currentShveyaId = shveyaId;
        initComponents();
        initTable();
        loadOrders();
        loadStatuses();
        loadFurnitura();
        loadTkani();
    }

    private void initTable() {
        // Создаем модель таблицы с колонками
        String[] columnNames = {"Номер", "Изделие", "Клиент", "Дата", "Статус", "Цена работы"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(model);

        // Добавляем обработчик клика по таблице
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
    }

// Обработчик клика по таблице
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            // Получаем статус выбранного заказа из таблицы
            String currentStatus = jTable1.getValueAt(row, 4).toString();
            System.out.println("Выбран заказ со статусом: " + currentStatus);

            // Устанавливаем этот статус в комбобокс
            setSelectedStatus(currentStatus);
        }
    }

// Метод для установки выбранного статуса в комбобокс
    private void setSelectedStatus(String status) {
        for (int i = 0; i < jComboBox20.getItemCount(); i++) {
            String item = jComboBox20.getItemAt(i);
            if (item.equals(status)) {
                jComboBox20.setSelectedIndex(i);
                System.out.println("Установлен статус в комбобокс: " + status);
                return;
            }
        }
        // Если статус не найден, сбрасываем выбор
        jComboBox20.setSelectedIndex(-1);
        System.out.println("Статус не найден в комбобоксе: " + status);
    }

    private void loadOrders() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "shveyaOrders?shveyaId=" + currentShveyaId;
            System.out.println("Запрос заказов: " + url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Ошибка сервера: " + response.code());
                    return;
                }

                String responseBody = response.body().string();
                System.out.println("Ответ сервера: " + responseBody);

                JSONArray jsonArray = new JSONArray(responseBody);
                System.out.println("Найдено заказов: " + jsonArray.length());

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject order = jsonArray.getJSONObject(i);
                    System.out.println("Заказ " + i + ": " + order.toString());

                    Vector<Object> row = new Vector<>();

                    row.add(order.getInt("id"));
                    row.add(order.getString("nazvanieIzdelie"));
                    row.add(order.getString("klient"));
                    row.add(order.getString("data"));
                    row.add(order.getString("status"));
                    row.add(order.getBigDecimal("tsenaRaboty"));

                    model.addRow(row);
                }

                System.out.println("Загружено строк в таблицу: " + model.getRowCount());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки заказов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadStatuses() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allStatusraboty";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox20.removeAllItems();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject status = jsonArray.getJSONObject(i);
                        // Просто добавляем название статуса в комбобокс
                        jComboBox20.addItem(status.getString("nazvanie"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFurnitura() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allFurnitura";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox18.removeAllItems();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject furnitura = jsonArray.getJSONObject(i);
                        // Просто добавляем название фурнитуры в комбобокс
                        jComboBox18.addItem(furnitura.getString("nazvanie"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTkani() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTkan";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox19.removeAllItems();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tkan = jsonArray.getJSONObject(i);
                        // Просто добавляем название ткани в комбобокс
                        jComboBox19.addItem(tkan.getString("nazvanie"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jComboBox18 = new javax.swing.JComboBox<>();
        jComboBox19 = new javax.swing.JComboBox<>();
        jComboBox20 = new javax.swing.JComboBox<>();
        refreshButtonActionPerformed = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Панель - швея");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setForeground(new java.awt.Color(163, 27, 24));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(163, 27, 24));
        jLabel3.setText("Заказы");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(163, 27, 24));
        jLabel6.setText("Статус");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(163, 27, 24));
        jLabel26.setText("Ткань");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(163, 27, 24));
        jLabel23.setText("Фурнитура");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(163, 27, 24));
        jLabel17.setText("Количество(м)");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(163, 27, 24));
        jLabel18.setText("Количество(м)");

        jButton25.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton25.setForeground(new java.awt.Color(163, 27, 24));
        jButton25.setText("Добавить");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton26.setForeground(new java.awt.Color(163, 27, 24));
        jButton26.setText("Изменить");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton27.setForeground(new java.awt.Color(163, 27, 24));
        jButton27.setText("Добавить");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jTextField15.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(163, 27, 24));
        jTextField15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField16.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(163, 27, 24));
        jTextField16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox18.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox18.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox19.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox19.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox20.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox20.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        refreshButtonActionPerformed.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        refreshButtonActionPerformed.setForeground(new java.awt.Color(163, 27, 24));
        refreshButtonActionPerformed.setText("Обновить");
        refreshButtonActionPerformed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton25, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel26))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(jButton26))
                            .addComponent(refreshButtonActionPerformed, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton26)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(jButton27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshButtonActionPerformed)
                        .addGap(31, 31, 31))))
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

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите заказ для добавления фурнитуры");
            return;
        }

        String selectedFurnitura = (String) jComboBox18.getSelectedItem();
        if (selectedFurnitura == null || selectedFurnitura.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Выберите фурнитуру");
            return;
        }

        String quantity = jTextField15.getText().trim();
        if (quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите количество");
            return;
        }

        String orderId = jTable1.getValueAt(selectedRow, 0).toString();

        // Получаем ID фурнитуры по названию
        Integer furnituraId = getFurnituraIdByName(selectedFurnitura);
        if (furnituraId == null) {
            JOptionPane.showMessageDialog(this, "Фурнитура не найдена");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addFurnituraToOrder";

            RequestBody formBody = new FormBody.Builder()
                    .add("orderId", orderId)
                    .add("furnituraId", String.valueOf(furnituraId))
                    .add("kolichestvo", quantity)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);

                boolean success = jsonResponse.getBoolean("success");
                String message = jsonResponse.getString("message");

                if (success) {
                    JOptionPane.showMessageDialog(this, message);
                    jTextField15.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления фурнитуры: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите заказ для добавления ткани");
            return;
        }

        String selectedTkan = (String) jComboBox19.getSelectedItem();
        if (selectedTkan == null || selectedTkan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Выберите ткань");
            return;
        }

        String quantity = jTextField16.getText().trim();
        if (quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите количество");
            return;
        }

        String orderId = jTable1.getValueAt(selectedRow, 0).toString();

        // Получаем ID ткани по названию
        Integer tkanId = getTkanIdByName(selectedTkan);
        if (tkanId == null) {
            JOptionPane.showMessageDialog(this, "Ткань не найдена");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addTkanToOrder";

            RequestBody formBody = new FormBody.Builder()
                    .add("orderId", orderId)
                    .add("tkanId", String.valueOf(tkanId))
                    .add("kolichestvo", quantity)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);

                boolean success = jsonResponse.getBoolean("success");
                String message = jsonResponse.getString("message");

                if (success) {
                    JOptionPane.showMessageDialog(this, message);
                    jTextField16.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите заказ для изменения статуса");
            return;
        }

        String selectedStatus = (String) jComboBox20.getSelectedItem();
        if (selectedStatus == null || selectedStatus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Выберите новый статус");
            return;
        }

        String orderId = jTable1.getValueAt(selectedRow, 0).toString();

        // Получаем ID статуса по названию
        Integer statusId = getStatusIdByName(selectedStatus);
        if (statusId == null) {
            JOptionPane.showMessageDialog(this, "Статус не найден");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updateOrderStatus";

            RequestBody formBody = new FormBody.Builder()
                    .add("orderId", orderId)
                    .add("statusId", String.valueOf(statusId))
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);

                boolean success = jsonResponse.getBoolean("success");
                String message = jsonResponse.getString("message");

                if (success) {
                    JOptionPane.showMessageDialog(this, message);
                    loadOrders();
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления статуса: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void refreshButtonActionPerformedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformedActionPerformed
        loadOrders();
    }//GEN-LAST:event_refreshButtonActionPerformedActionPerformed
// Вспомогательные методы для получения ID по названию

    private Integer getStatusIdByName(String statusName) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allStatusraboty";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject status = jsonArray.getJSONObject(i);
                        if (status.getString("nazvanie").equals(statusName)) {
                            return status.getInt("id");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer getFurnituraIdByName(String furnituraName) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allFurnitura";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject furnitura = jsonArray.getJSONObject(i);
                        if (furnitura.getString("nazvanie").equals(furnituraName)) {
                            return furnitura.getInt("id");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer getTkanIdByName(String tkanName) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTkan";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tkan = jsonArray.getJSONObject(i);
                        if (tkan.getString("nazvanie").equals(tkanName)) {
                            return tkan.getInt("id");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
            java.util.logging.Logger.getLogger(shveya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(shveya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(shveya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(shveya.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new shveya().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JButton refreshButtonActionPerformed;
    // End of variables declaration//GEN-END:variables
}
