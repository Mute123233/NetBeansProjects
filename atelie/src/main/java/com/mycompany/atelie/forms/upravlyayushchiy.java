/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.atelie.forms;

import com.formdev.flatlaf.FlatLightLaf;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author 8280@stud.pgt.su
 */
public class upravlyayushchiy extends javax.swing.JFrame {

    private Integer currentUpravId;
    private JTable table1;
    private DefaultTableModel tableModel;

    /**
     * Creates new form upravlyayushchiy
     */
    public upravlyayushchiy() {
        FlatLightLaf.setup();
        initComponents();
        ImageIcon icon = new ImageIcon("src/main/resources/img/icon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public upravlyayushchiy(Integer upravId) {
        this.currentUpravId = upravId;
        initComponents();
    }
    private static final String BASE_URL = "http://localhost:8080/atelie/";

// Продажи текущий месяц
    private void loadSalesCurrentMonth() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "salesCurrentMonth";
            System.out.println("Запрос: " + url);

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Ответ код: " + response.code());
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Ответ: " + responseBody);
                    JSONArray jsonArray = new JSONArray(responseBody);
                    displayDataInTable(jsonArray);
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage());
        }
    }

// Продажи за период - с правильным кодированием URL
    private void loadSalesByPeriod() {
        String startDate = "";
        String endDate = "";

        if (jFormattedTextField6 != null) {
            startDate = jFormattedTextField6.getText().trim();
        }
        if (jFormattedTextField5 != null) {
            endDate = jFormattedTextField5.getText().trim();
        }

        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните даты начала и конца периода");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();

            // Правильное формирование URL с кодированием
            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "salesByPeriod").newBuilder();
            urlBuilder.addQueryParameter("startDate", startDate);
            urlBuilder.addQueryParameter("endDate", endDate);
            String url = urlBuilder.build().toString();

            System.out.println("Запрос продаж за период: " + url);

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Ответ код: " + response.code());
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Ответ body: " + responseBody);
                    JSONArray jsonArray = new JSONArray(responseBody);
                    System.out.println("Количество элементов: " + jsonArray.length());
                    displayDataInTable(jsonArray);
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                    System.out.println("Тело ошибки: " + response.body().string());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage());
        }
    }
// Количество заказов текущий месяц

    private void loadOrdersCountCurrentMonth() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "ordersCountCurrentMonth";
            System.out.println("Запрос: " + url);

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Ответ код: " + response.code());
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Ответ: " + responseBody);

                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String message = jsonResponse.getString("message");
                    int count = jsonResponse.getInt("count");

                    // Выводим в таблицу
                    Vector<String> headers = new Vector<>();
                    headers.add("Период");
                    headers.add("Количество заказов");

                    DefaultTableModel model = new DefaultTableModel();
                    model.setColumnIdentifiers(headers);

                    Vector<Object> row = new Vector<>();
                    row.add("Текущий месяц");
                    row.add(count);
                    model.addRow(row);

                    jTable1.setModel(model);

                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage());
        }
    }

// Количество заказов за период
    private void loadOrdersCountByPeriod() {
        String startDate = "";
        String endDate = "";

        if (jFormattedTextField6 != null) {
            startDate = jFormattedTextField6.getText().trim();
        }
        if (jFormattedTextField5 != null) {
            endDate = jFormattedTextField5.getText().trim();
        }

        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните даты начала и конца периода");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "ordersCountByPeriod").newBuilder();
            urlBuilder.addQueryParameter("startDate", startDate);
            urlBuilder.addQueryParameter("endDate", endDate);
            String url = urlBuilder.build().toString();

            System.out.println("Запрос количества заказов за период: " + url);

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Ответ код: " + response.code());
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Ответ body: " + responseBody);

                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String message = jsonResponse.getString("message");
                    int count = jsonResponse.getInt("count");
                    String period = jsonResponse.getString("period");

                    // Выводим в таблицу
                    Vector<String> headers = new Vector<>();
                    headers.add("Период");
                    headers.add("Количество заказов");

                    DefaultTableModel model = new DefaultTableModel();
                    model.setColumnIdentifiers(headers);

                    Vector<Object> row = new Vector<>();
                    row.add(period);
                    row.add(count);
                    model.addRow(row);

                    jTable1.setModel(model);

                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage());
        }
    }

// Все заказы
    private void loadAllOrders() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allOrders";
            System.out.println("Запрос: " + url);

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Ответ код: " + response.code());
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Ответ: " + responseBody);
                    JSONArray jsonArray = new JSONArray(responseBody);
                    displayDataInTable(jsonArray);
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage());
        }
    }

// Остатки материалов
    private void loadMaterialsStock() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "materialsStock";
            System.out.println("Запрос: " + url);

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Ответ код: " + response.code());
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Ответ: " + responseBody);
                    JSONArray jsonArray = new JSONArray(responseBody);
                    displayDataInTable(jsonArray);
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка сервера: " + response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных: " + e.getMessage());
        }
    }
// Метод для отображения данных в таблице

    private void displayDataInTable(JSONArray jsonArray) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Очищаем таблицу

        if (jsonArray.length() > 0) {
            // Получаем заголовки из первого элемента
            JSONObject firstItem = jsonArray.getJSONObject(0);
            Vector<String> headers = new Vector<>();

            // Получаем все ключи из первого объекта
            Iterator<String> keys = firstItem.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                headers.add(key);
            }

            // Устанавливаем заголовки
            model.setColumnIdentifiers(headers);

            // Заполняем данные
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                Vector<Object> row = new Vector<>();

                for (String header : headers) {
                    Object value = item.get(header);
                    if (value instanceof JSONObject) {
                        row.add(value.toString());
                    } else {
                        row.add(value);
                    }
                }

                model.addRow(row);
            }
        } else {
            // Если данных нет
            Vector<String> headers = new Vector<>();
            headers.add("Сообщение");
            model.setColumnIdentifiers(headers);

            Vector<Object> row = new Vector<>();
            row.add("Нет данных для отображения");
            model.addRow(row);
        }

        // Автоподбор ширины столбцов
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Панель - управляющий");

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

        jButton14.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton14.setForeground(new java.awt.Color(163, 27, 24));
        jButton14.setText("Вывести продажи текущий месяц");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton15.setForeground(new java.awt.Color(163, 27, 24));
        jButton15.setText("Вывести продажи за период");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(163, 27, 24));
        jLabel17.setText("Период");

        jButton16.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton16.setForeground(new java.awt.Color(163, 27, 24));
        jButton16.setText("Вывести заказы текущий месяц");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton17.setForeground(new java.awt.Color(163, 27, 24));
        jButton17.setText("Вывести заказы за период");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton18.setForeground(new java.awt.Color(163, 27, 24));
        jButton18.setText("Вывести все заказы");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton19.setForeground(new java.awt.Color(163, 27, 24));
        jButton19.setText("Вывести остатки материалов и фурнитуры");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        jFormattedTextField6.setForeground(new java.awt.Color(163, 27, 24));
        try {
            jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField6.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jFormattedTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField6ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(163, 27, 24));
        jLabel9.setText("-");

        jFormattedTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        jFormattedTextField5.setForeground(new java.awt.Color(163, 27, 24));
        try {
            jFormattedTextField5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField5.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton14)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton15)
                    .addComponent(jButton16)
                    .addComponent(jButton17)
                    .addComponent(jButton18)
                    .addComponent(jButton19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(20, 20, 20)
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton19)))
                .addContainerGap(64, Short.MAX_VALUE))
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

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        loadSalesCurrentMonth();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        loadSalesByPeriod();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        loadOrdersCountCurrentMonth();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        loadOrdersCountByPeriod();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        loadAllOrders();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        loadMaterialsStock();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jFormattedTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField6ActionPerformed

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
            java.util.logging.Logger.getLogger(upravlyayushchiy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(upravlyayushchiy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(upravlyayushchiy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(upravlyayushchiy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new upravlyayushchiy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
