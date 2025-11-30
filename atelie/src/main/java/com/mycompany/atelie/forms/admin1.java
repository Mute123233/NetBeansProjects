/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.atelie.forms;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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

public class admin1 extends javax.swing.JFrame {

    private static final String BASE_URL = "http://localhost:8080/atelie/";
    private String imagePath;

    public admin1() {
        FlatLightLaf.setup();
        initComponents();
        ImageIcon icon = new ImageIcon("src/main/resources/img/icon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeComponents();

        // Проверяем соединение и загружаем данные
        if (testServerConnection()) {
            allPolzovateli();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Сервер не доступен! Убедитесь, что ServerFabricShop запущен на localhost:8080",
                    "Ошибка соединения",
                    JOptionPane.ERROR_MESSAGE);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private boolean testServerConnection() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allPolzovateliWithRoles";
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                return response.isSuccessful();
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void initializeComponents() {
        // Инициализация комбобокса с должностями
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Отсутствует");
        jComboBox1.addItem("Продавец");
        jComboBox1.addItem("Швея");
        jComboBox1.addItem("Управляющий");

        // Настройка таблицы
        setupTable();
    }

    private void setupTable() {
        Vector<String> headers = new Vector<>();
        headers.add("Номер");
        headers.add("Фамилия");
        headers.add("Имя");
        headers.add("Отчество");
        headers.add("Паспорт");
        headers.add("Дата рождения");
        headers.add("Телефон");
        headers.add("Дата трудоустройства");
        headers.add("Логин");
        headers.add("Пароль");
        headers.add("Должность");

        DefaultTableModel model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(model);
    }

    public void allPolzovateli() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allPolzovateliWithRoles";
            System.out.println("Запрос всех пользователей: " + url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Ошибка сервера: " + response.code());
                    return;
                }

                String responseBody = response.body().string();
                System.out.println("Получен ответ: " + responseBody);

                JSONArray jsonArray = new JSONArray(responseBody);

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject user = jsonArray.getJSONObject(i);
                    Vector<Object> row = new Vector<>();

                    row.add(user.getInt("id"));
                    row.add(user.getString("familiya"));
                    row.add(user.getString("imya"));
                    row.add(user.optString("otchestvo", ""));
                    row.add(user.optString("pasport", ""));
                    row.add(user.optString("datarozhdeniya_display", ""));
                    row.add(user.optString("nomertelefona", ""));
                    row.add(user.optString("datatrudoustroystva_display", ""));
                    row.add(user.getString("login"));
                    row.add(user.getString("parol"));
                    row.add(user.getString("dolzhnost"));

                    model.addRow(row);
                }

                System.out.println("Успешно загружено пользователей: " + jsonArray.length());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addPolzovatel() {
        // Получаем данные из полей формы
        String familiya = jTextField1.getText().trim();
        String imya = jTextField2.getText().trim();
        String otchestvo = jTextField3.getText().trim();
        String pasport = jTextField5.getText().trim();
        String datarozhdeniya = jFormattedTextField6.getText().trim();
        String telefon = jTextField7.getText().trim();
        String datatrudoustroystva = jFormattedTextField7.getText().trim();
        String login = jTextField8.getText().trim();
        String parol = jTextField9.getText().trim();
        String foto = imagePath != null ? imagePath : "";
        String dolznost = (String) jComboBox1.getSelectedItem();

        // Валидация данных
        if (familiya.isEmpty() || imya.isEmpty() || login.isEmpty() || parol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Фамилия, Имя, Логин, Пароль");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addPolzovatel";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("familiya", familiya)
                    .add("imya", imya)
                    .add("otchestvo", otchestvo)
                    .add("login", login)
                    .add("parol", parol)
                    .add("nomertelefona", telefon)
                    .add("pasport", pasport)
                    .add("datarozhdeniya", datarozhdeniya)
                    .add("datatrudoustroystva", datatrudoustroystva)
                    .add("foto", foto)
                    .add("dolzhnost", dolznost);

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                System.out.println("Ответ от сервера: " + responseBody);

                JSONObject jsonResponse = new JSONObject(responseBody);

                boolean success = jsonResponse.getBoolean("success");
                String message = jsonResponse.getString("message");

                if (success) {
                    JOptionPane.showMessageDialog(this, message);
                    clearForm();
                    allPolzovateli(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateUserData(String idPolzovatel) {
        // Получаем данные из полей формы
        String familiya = jTextField1.getText().trim();
        String imya = jTextField2.getText().trim();
        String otchestvo = jTextField3.getText().trim();
        String pasport = jTextField5.getText().trim();
        String datarozhdeniya = jFormattedTextField6.getText().trim();
        String telefon = jTextField7.getText().trim();
        String datatrudoustroystva = jFormattedTextField7.getText().trim();
        String login = jTextField8.getText().trim();
        String parol = jTextField9.getText().trim();
        String foto = imagePath != null ? imagePath : "";
        String dolznost = (String) jComboBox1.getSelectedItem();

        // Валидация
        if (familiya.isEmpty() || imya.isEmpty() || login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Фамилия, Имя, Логин");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updatePolzovatel";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("idPolzovatel", idPolzovatel)
                    .add("familiya", familiya)
                    .add("imya", imya)
                    .add("otchestvo", otchestvo)
                    .add("login", login)
                    .add("parol", parol.isEmpty() ? "" : parol)
                    .add("nomertelefona", telefon)
                    .add("pasport", pasport)
                    .add("datarozhdeniya", datarozhdeniya)
                    .add("datatrudoustroystva", datatrudoustroystva)
                    .add("foto", foto)
                    .add("dolzhnost", dolznost);

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                System.out.println("Ответ от сервера: " + responseBody);

                JSONObject jsonResponse = new JSONObject(responseBody);

                boolean success = jsonResponse.getBoolean("success");
                String message = jsonResponse.getString("message");

                if (success) {
                    JOptionPane.showMessageDialog(this, message);
                    allPolzovateli(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateUserWithRole(String idPolzovatel, String newDolznost) {
        // Получаем данные из полей формы
        String familiya = jTextField1.getText().trim();
        String imya = jTextField2.getText().trim();
        String otchestvo = jTextField3.getText().trim();
        String pasport = jTextField5.getText().trim();
        String datarozhdeniya = jFormattedTextField6.getText().trim();
        String telefon = jTextField7.getText().trim();
        String datatrudoustroystva = jFormattedTextField7.getText().trim();
        String login = jTextField8.getText().trim();
        String parol = jTextField9.getText().trim();
        String foto = imagePath != null ? imagePath : "";

        // Валидация
        if (familiya.isEmpty() || imya.isEmpty() || login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Фамилия, Имя, Логин");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();

            // Сначала обновляем данные пользователя
            String updateUrl = BASE_URL + "updatePolzovatel";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("idPolzovatel", idPolzovatel)
                    .add("familiya", familiya)
                    .add("imya", imya)
                    .add("otchestvo", otchestvo)
                    .add("login", login)
                    .add("parol", parol.isEmpty() ? "" : parol)
                    .add("nomertelefona", telefon)
                    .add("pasport", pasport)
                    .add("datarozhdeniya", datarozhdeniya)
                    .add("datatrudoustroystva", datatrudoustroystva)
                    .add("foto", foto)
                    .add("dolzhnost", newDolznost);

            RequestBody formBody = formBuilder.build();

            Request updateRequest = new Request.Builder()
                    .url(updateUrl)
                    .post(formBody)
                    .build();

            try (Response updateResponse = client.newCall(updateRequest).execute()) {
                String responseBody = updateResponse.body().string();
                System.out.println("Ответ от сервера при обновлении: " + responseBody);

                JSONObject jsonResponse = new JSONObject(responseBody);

                boolean success = jsonResponse.getBoolean("success");
                String message = jsonResponse.getString("message");

                if (success) {
                    JOptionPane.showMessageDialog(this, "Данные и должность успешно обновлены");
                    allPolzovateli(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updatePolzovatel() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите пользователя для изменения");
            return;
        }

        String idPolzovatel = jTable1.getValueAt(selectedRow, 0).toString();
        String currentDolznost = getValueAsString(selectedRow, 10); // Текущая должность из таблицы
        String newDolznost = (String) jComboBox1.getSelectedItem();
        String familiya = jTextField1.getText().trim();
        String imya = jTextField2.getText().trim();
        String otchestvo = jTextField3.getText().trim();
        String pasport = jTextField5.getText().trim();
        String datarozhdeniya = jFormattedTextField6.getText().trim();
        String telefon = jTextField7.getText().trim();
        String datatrudoustroystva = jFormattedTextField7.getText().trim();
        String login = jTextField8.getText().trim();
        String parol = jTextField9.getText().trim();
        String foto = imagePath != null ? imagePath : "";
        if (currentDolznost.equals(newDolznost)) {
            updateUserData(idPolzovatel);
        } else {
            // Если должность изменилась - обновляем и данные и роль
            updateUserWithRole(idPolzovatel, newDolznost);
        }

        // Валидация
        if (familiya.isEmpty() || imya.isEmpty() || login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Фамилия, Имя, Логин");
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updatePolzovatel";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("idPolzovatel", idPolzovatel)
                    .add("familiya", familiya)
                    .add("imya", imya)
                    .add("otchestvo", otchestvo)
                    .add("login", login)
                    .add("parol", parol.isEmpty() ? "" : parol)
                    .add("nomertelefona", telefon)
                    .add("pasport", pasport)
                    .add("datarozhdeniya", datarozhdeniya)
                    .add("datatrudoustroystva", datatrudoustroystva)
                    .add("foto", foto)
                    .add("dolzhnost", newDolznost);

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                System.out.println("Ответ от сервера: " + responseBody);

                JSONObject jsonResponse = new JSONObject(responseBody);

                boolean success = jsonResponse.getBoolean("success");
                String message = jsonResponse.getString("message");

                if (success) {
                    JOptionPane.showMessageDialog(this, message);
                    allPolzovateli(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления: " + e.getMessage());
            e.printStackTrace();
        }
    }

// Метод удаления пользователя - ИСПРАВЛЕННЫЙ
    private void deletePolzovatel() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите пользователя для удаления");
            return;
        }

        String idPolzovatel = jTable1.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить пользователя?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "deletePolzovatel";
            System.out.println("Удаление пользователя: " + url);

            RequestBody formBody = new FormBody.Builder()
                    .add("idPolzovatel", idPolzovatel)
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
                    clearForm();
                    allPolzovateli();
                } else {
                    JOptionPane.showMessageDialog(this, message);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Изображения", "jpg", "jpeg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePath = selectedFile.getAbsolutePath();

            // Отображаем изображение в jLabel111
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                Image scaledImage = image.getScaledInstance(jLabel111.getWidth(), jLabel111.getHeight(), Image.SCALE_SMOOTH);
                jLabel111.setIcon(new ImageIcon(scaledImage));
                jLabel111.setText(""); // Убираем текст
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки изображения");
                jLabel111.setText("Ошибка загрузки");
            }
        }
    }

    // Метод очистки формы
    private void clearForm() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField5.setText("");
        jFormattedTextField6.setText("");
        jTextField7.setText("");
        jFormattedTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jComboBox1.setSelectedIndex(0);
        imagePath = null;
        jLabel111.setIcon(null);
        jLabel111.setText("Фото");
    }

    // Метод форматирования даты для отображения
    private String formatDateForDisplay(String dateStr) {
        if (dateStr == null || dateStr.isEmpty() || "null".equals(dateStr)) {
            return "";
        }

        try {
            // Предполагаем, что дата приходит в формате "yyyy-MM-dd"
            DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat displayFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = sourceFormat.parse(dateStr);
            return displayFormat.format(date);
        } catch (Exception e) {
            // Если не удалось распарсить, возвращаем как есть
            return dateStr;
        }
    }

    // Вспомогательный метод для безопасного получения значений из таблицы
    private String getValueAsString(int row, int column) {
        Object value = jTable1.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    // Метод загрузки фото пользователя - ПРЯМАЯ ССЫЛКА
    private void loadUserPhoto(int userId) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = "http://localhost:8080/atelie/getUserPhoto?id=" + userId;
            System.out.println("Загрузка фото: " + url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String photoPath = response.body().string();
                    if (photoPath != null && !photoPath.trim().isEmpty() && !"null".equals(photoPath)) {
                        File photoFile = new File(photoPath);
                        if (photoFile.exists()) {
                            BufferedImage image = ImageIO.read(photoFile);
                            Image scaledImage = image.getScaledInstance(
                                    jLabel111.getWidth(),
                                    jLabel111.getHeight(),
                                    Image.SCALE_SMOOTH
                            );
                            jLabel111.setIcon(new ImageIcon(scaledImage));
                            jLabel111.setText("");
                            imagePath = photoPath;
                            return;
                        }
                    }
                }
            }

            // Если фото не найдено
            jLabel111.setIcon(null);
            jLabel111.setText("Нет фото");
            imagePath = null;

        } catch (IOException e) {
            jLabel111.setIcon(null);
            jLabel111.setText("Ошибка загрузки");
            imagePath = null;
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
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jLabel111 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Администратор");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(163, 27, 24));
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(163, 27, 24));
        jLabel2.setText("Имя");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(163, 27, 24));
        jLabel3.setText("Фамилия");

        jTextField2.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(163, 27, 24));
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField3.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(163, 27, 24));
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(163, 27, 24));
        jLabel4.setText("Отчество");

        jTextField5.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(163, 27, 24));
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField7.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(163, 27, 24));
        jTextField7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField8.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(163, 27, 24));
        jTextField8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField9.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(163, 27, 24));
        jTextField9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(163, 27, 24));
        jLabel10.setText("Пароль");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(163, 27, 24));
        jLabel9.setText("Логин");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(163, 27, 24));
        jLabel11.setText("Дата трудоустройства");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(163, 27, 24));
        jLabel8.setText("Телефон");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(163, 27, 24));
        jLabel7.setText("Дата рождения");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(163, 27, 24));
        jLabel6.setText("Паспортные данные");

        jTable1.setForeground(new java.awt.Color(163, 27, 24));
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

        jButton1.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(163, 27, 24));
        jButton1.setText("Добавить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(163, 27, 24));
        jButton2.setText("Изменить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(163, 27, 24));
        jButton3.setText("Удалить");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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

        jFormattedTextField7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        jFormattedTextField7.setForeground(new java.awt.Color(163, 27, 24));
        try {
            jFormattedTextField7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField7.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jFormattedTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField7ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton4.setForeground(new java.awt.Color(163, 27, 24));
        jButton4.setText("Добавить изображение");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(163, 27, 24));
        jLabel12.setText("Фото");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(163, 27, 24));
        jLabel13.setText("Должность");

        jComboBox1.setEditable(true);
        jComboBox1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jComboBox1.setLightWeightPopupEnabled(false);
        jComboBox1.setRequestFocusEnabled(true);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10)
                            .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, 0, 233, Short.MAX_VALUE))
                            .addComponent(jTextField9)
                            .addComponent(jTextField8)
                            .addComponent(jTextField7)
                            .addComponent(jTextField5)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jFormattedTextField6)
                            .addComponent(jFormattedTextField7)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 57, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jFormattedTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jFormattedTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(0, 0, Short.MAX_VALUE))
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
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        try {
            // Заполняем текстовые поля
            jTextField1.setText(getValueAsString(selectedRow, 1)); // Фамилия
            jTextField2.setText(getValueAsString(selectedRow, 2)); // Имя
            jTextField3.setText(getValueAsString(selectedRow, 3)); // Отчество
            jTextField5.setText(getValueAsString(selectedRow, 4)); // Паспорт
            jTextField7.setText(getValueAsString(selectedRow, 6)); // Телефон
            jTextField8.setText(getValueAsString(selectedRow, 8)); // Логин
            jTextField9.setText(getValueAsString(selectedRow, 9)); // Пароль

            // Обрабатываем даты (преобразуем из формата таблицы в формат для полей)
            String birthDateStr = getValueAsString(selectedRow, 5);
            String employmentDateStr = getValueAsString(selectedRow, 7);

            if (!birthDateStr.isEmpty()) {
                try {
                    DateFormat displayFormat = new SimpleDateFormat("dd.MM.yyyy");
                    DateFormat fieldFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthDate = displayFormat.parse(birthDateStr);
                    jFormattedTextField6.setText(fieldFormat.format(birthDate));
                } catch (Exception e) {
                    jFormattedTextField6.setText(birthDateStr);
                }
            }

            if (!employmentDateStr.isEmpty()) {
                try {
                    DateFormat displayFormat = new SimpleDateFormat("dd.MM.yyyy");
                    DateFormat fieldFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date employmentDate = displayFormat.parse(employmentDateStr);
                    jFormattedTextField7.setText(fieldFormat.format(employmentDate));
                } catch (Exception e) {
                    jFormattedTextField7.setText(employmentDateStr);
                }
            }

            // Устанавливаем должность
            String dolznost = getValueAsString(selectedRow, 10);
            jComboBox1.setSelectedItem(dolznost);

            // Загружаем фото пользователя
            int userId = Integer.parseInt(getValueAsString(selectedRow, 0));
            loadUserPhoto(userId);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при загрузке данных: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTable1MouseClicked
    // Вспомогательный метод для безопасного получения значений из таблицы


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addPolzovatel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deletePolzovatel();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jFormattedTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField6ActionPerformed

    private void jFormattedTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        loadImage();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updatePolzovatel();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(admin1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
// Методы для проверки принадлежности пользователя к должности

}
