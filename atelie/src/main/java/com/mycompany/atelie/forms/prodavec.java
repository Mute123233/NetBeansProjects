/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.atelie.forms;

import com.formdev.flatlaf.FlatLightLaf;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author 8280@stud.pgt.su
 */
public class prodavec extends javax.swing.JFrame {

    private Map<String, Integer> tsvetTkaniMap = new HashMap<>();
    private Map<String, Integer> rastsvetkaMap = new HashMap<>();
    private Map<String, Integer> tsvetFurnituryMap = new HashMap<>();
    private Map<String, Integer> edinitsaMap = new HashMap<>();
    private Map<String, Integer> furnituraMap = new HashMap<>();
    private Map<String, Integer> tkanMap = new HashMap<>();
    private Map<String, Integer> razmerMap = new HashMap<>();
    private Map<String, Integer> klientMap = new HashMap<>();
    private Map<String, Integer> izdelieMap = new HashMap<>();
    private Map<String, Integer> statusMap = new HashMap<>();
    private Map<String, Integer> shveyaMap = new HashMap<>();
    private Map<Integer, String> tsvetTkaniCache = new HashMap<>();
    private Map<Integer, String> tsvetFurnituryCache = new HashMap<>();
    private Map<Integer, String> edinitsaCache = new HashMap<>();
    private Map<Integer, String> rastsvetkaCache = new HashMap<>();
    private Map<Integer, String> furnituraCache = new HashMap<>();
    private Map<Integer, String> tkanCache = new HashMap<>();
    private Map<Integer, String> razmerCache = new HashMap<>();
    private Map<Integer, String> klientCache = new HashMap<>();
    private Map<Integer, String> izdelieCache = new HashMap<>();
    private Map<Integer, String> statusCache = new HashMap<>();
    private Map<Integer, String> shveyaCache = new HashMap<>();
    private Integer currentProdavecId;
    private static final String BASE_URL = "http://localhost:8080/atelie/";

    /**
     * Creates new form prodavec
     */
    public prodavec() {

        FlatLightLaf.setup();
        initComponents();
        ImageIcon icon = new ImageIcon("src/main/resources/img/icon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initializeComponents();
        fillComboBoxesFromCaches();
        loadAllCaches();
        loadTsvetFurnituryCache();
        loadInitialData();
    }

    private void loadAllCaches() {
        loadTsvetTkaniCache();
        loadTsvetFurnituryCache();
        loadEdinitsaCache();
        loadRastsvetkaCache();
        loadFurnituraCache();
        loadTkanCache();
        loadRazmerCache();
        loadKlientCache();
        loadIzdelieCache();
        loadStatusCache();
        loadShveyaCache();
    }

    private void debugJsonStructure(String endpoint) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + endpoint;
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    if (jsonArray.length() > 0) {
                        JSONObject firstItem = jsonArray.getJSONObject(0);
                        System.out.println("=== Структура JSON для " + endpoint + " ===");
                        System.out.println("Ключи: " + firstItem.keySet());
                        System.out.println("Первый элемент: " + firstItem.toString(2));
                        System.out.println("=================================");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка отладки структуры: " + e.getMessage());
        }
    }

    private void debugCaches() {
        System.out.println("=== ОТЛАДКА КЭШЕЙ ===");
        System.out.println("Цвета ткани: " + tsvetTkaniCache);
        System.out.println("Цвета фурнитуры: " + tsvetFurnituryCache);
        System.out.println("Единицы измерения: " + edinitsaCache);
        System.out.println("Расцветки: " + rastsvetkaCache);
        System.out.println("Фурнитура: " + furnituraCache);
        System.out.println("Ткани: " + tkanCache);
        System.out.println("Размеры: " + razmerCache);
        System.out.println("Клиенты: " + klientCache);
        System.out.println("Изделия: " + izdelieCache);
        System.out.println("Статусы: " + statusCache);
        System.out.println("Швеи: " + shveyaCache);
        System.out.println("====================");
    }

    public prodavec(Integer prodavecId) {
        this();
        this.currentProdavecId = prodavecId;
    }

    private void initializeComponents() {
        // Настройка таблиц
        setupTables();

        // Загрузка кэшей
        loadAllCaches();

        // Загрузка данных в комбобоксы
        loadComboBoxData();

        // ДОБАВИТЬ ЭТУ СТРОКУ - загрузка скидок
        loadSkidkaComboBox();

        // Загрузка данных
        loadInitialData();
    }

    private void fillComboBoxesFromCaches() {
        fillTsvetTkaniComboBox();
        fillTsvetFurnituryComboBox();
        fillEdinitsaComboBox();
        fillRastsvetkaComboBox();
        fillFurnituraComboBox();
        fillTkanComboBox();
        fillRazmerComboBox();
        fillKlientComboBox();
        fillIzdelieComboBox();
        fillStatusComboBox();
        fillShveyaComboBox();
    }
// Вместо старых методов используйте новые:

    private void allTkan() {
        loadDataForTable("allTkanForTable", jTable1);
    }

    private void allFurnitura() {
        loadDataForTable("allFurnituraForTable", jTable2);
    }

    private void allProdazhafurnitury() {
        loadDataForTable("allProdazhafurnituryForTable", jTable3);
    }

    private void allProdazhatkani() {
        loadDataForTable("allProdazhatkaniForTable", jTable4);
    }

    private void allZakaznaposhiv() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allZakaznaposhivForTable";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
                    model.setRowCount(0);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject zakaz = jsonArray.getJSONObject(i);
                        Vector<Object> row = new Vector<>();

                        int id = getIntValue(zakaz, "id");
                        row.add(id);
                        row.add(getStringValue(zakaz, "Data"));

                        int klientId = getIntValue(zakaz, "idKlient");
                        String klientStr = getKlientNameById(klientId);
                        row.add(klientStr);

                        int izdelieId = getIntValue(zakaz, "idIzdelie");
                        String izdelieName = getIzdelieNameById(izdelieId);
                        row.add(izdelieName);

                        int statusId = getIntValue(zakaz, "idStatusRaboty");
                        String statusName = getStatusNameById(statusId);
                        row.add(statusName);

                        int shveyaId = getIntValue(zakaz, "idShveya");
                        String shveyaStr = getShveyaNameById(shveyaId);
                        row.add(shveyaStr);

                        BigDecimal tsena = getBigDecimalValue(zakaz, "TsenaRabotyShvei");
                        row.add(tsena);

                        // СКИДКА - исправленный код
                        int skidkaId = getIntValue(zakaz, "idSkidka");
                        String skidkaProcent = getSkidkaProcentById(skidkaId);
                        row.add(skidkaProcent);

                        model.addRow(row);
                    }
                    System.out.println("Загружено заказов: " + model.getRowCount());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки заказов: " + e.getMessage());
            e.printStackTrace();
            addTestDataToTable5();
        }
    }

    private void loadSkidkaComboBox() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allSkidka";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox21.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject skidka = jsonArray.getJSONObject(i);
                        int procent = getIntValue(skidka, "procent");
                        jComboBox21.addItem(procent + "%");
                    }

                    // Add default "0%" if no discounts found
                    if (jComboBox21.getItemCount() == 0) {
                        jComboBox21.addItem("0%");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки скидок: " + e.getMessage());
            // Add default values
            jComboBox21.addItem("0%");
            jComboBox21.addItem("5%");
            jComboBox21.addItem("10%");
            jComboBox21.addItem("15%");
            jComboBox21.addItem("20%");
        }
    }

    private int getTsvetTkaniIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : tsvetTkaniCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Цвет ткани '" + name + "' не найден");
    }

    private int getRastsvetkaIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : rastsvetkaCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Расцветка '" + name + "' не найдена");
    }

    private int getTsvetFurnituryIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : tsvetFurnituryCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Цвет фурнитуры '" + name + "' не найден");
    }

    private int getEdinitsaIzmereniyaIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : edinitsaCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Единица измерения '" + name + "' не найдена");
    }
// Общий метод для загрузки данных в таблицы

    private void loadDataForTable(String endpoint, JTable table) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + endpoint;
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);

                    // Обработка данных для каждой таблицы
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        Vector<Object> row = new Vector<>();

                        // Добавление данных в строку в зависимости от таблицы
                        addTableRowData(table, row, item);

                        model.addRow(row);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки данных для " + endpoint + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

// Метод для добавления данных в строку таблицы
    private void addTableRowData(JTable table, Vector<Object> row, JSONObject item) {
        String tableName = table.getName();

        if (table == jTable1) { // Ткани
            row.add(getIntValue(item, "id"));
            row.add(getStringValue(item, "artikul"));
            row.add(getStringValue(item, "nazvanie"));
            row.add(getStringValue(item, "kategoriya"));

            int tsvetId = getIntValue(item, "idTsvet");
            row.add(getTsvetTkaniNameById(tsvetId));

            int rastsvetkaId = getIntValue(item, "idRastsvetka");
            row.add(getRastsvetkaNameById(rastsvetkaId));

            row.add(getBooleanValue(item, "novinka") ? "Да" : "Нет");

        } else if (table == jTable2) { // Фурнитура
            row.add(getIntValue(item, "id"));
            row.add(getStringValue(item, "artikul"));
            row.add(getStringValue(item, "nazvanie"));

            int tsvetId = getIntValue(item, "idTsvet");
            row.add(getTsvetFurnituryNameById(tsvetId));

            int edinitsaId = getIntValue(item, "idEdIzmereniya");
            row.add(getEdinitsaNameById(edinitsaId));

            row.add(getBooleanValue(item, "novinka") ? "Да" : "Нет");

        } else if (table == jTable3) { // Продажи фурнитуры
            row.add(getIntValue(item, "id"));
            row.add(getStringValue(item, "data"));

            int furnituraId = getIntValue(item, "idFurnitura");
            row.add(getFurnituraNameById(furnituraId));

            row.add(getIntValue(item, "Kolichestvo"));
            row.add(getBigDecimalValue(item, "tsenaZaYedinitsu"));

        } else if (table == jTable4) { // Продажи ткани
            row.add(getIntValue(item, "id"));
            row.add(getStringValue(item, "data"));

            int tkanId = getIntValue(item, "idTkan");
            row.add(getTkanNameById(tkanId));

            row.add(getIntValue(item, "Kolichestvo"));
            row.add(getBigDecimalValue(item, "tsenaZaRulon"));

            int razmerId = getIntValue(item, "idRazmer");
            row.add(getRazmerStringById(razmerId));

        } else if (table == jTable5) { // Заказы
            row.add(getIntValue(item, "id"));
            row.add(getStringValue(item, "Data"));

            int klientId = getIntValue(item, "idKlient");
            row.add(getKlientNameById(klientId));

            int izdelieId = getIntValue(item, "idIzdelie");
            row.add(getIzdelieNameById(izdelieId));

            int statusId = getIntValue(item, "idStatusRaboty");
            row.add(getStatusNameById(statusId));

            int shveyaId = getIntValue(item, "idShveya");
            row.add(getShveyaNameById(shveyaId));

            row.add(getBigDecimalValue(item, "TsenaRabotyShvei"));
        }
    }

    private String getTsvetTkaniNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = tsvetTkaniCache.getOrDefault(id, "Цвет " + id);
        if (name.equals("Цвет " + id)) {
            System.out.println("Цвет ткани не найден в кэше. ID: " + id + ", Кэш: " + tsvetTkaniCache);
        }
        return name;
    }

    private String getTsvetFurnituryNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = tsvetFurnituryCache.getOrDefault(id, "Цвет " + id);
        if (name.equals("Цвет " + id)) {
            System.out.println("Цвет фурнитуры не найден в кэше. ID: " + id + ", Кэш: " + tsvetFurnituryCache);
        }
        return name;
    }

    private String getEdinitsaNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = edinitsaCache.getOrDefault(id, "Ед. " + id);
        if (name.equals("Ед. " + id)) {
            System.out.println("Единица измерения не найдена в кэше. ID: " + id + ", Кэш: " + edinitsaCache);
        }
        return name;
    }

    private String getRastsvetkaNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = rastsvetkaCache.getOrDefault(id, "Расцветка " + id);
        if (name.equals("Расцветка " + id)) {
            System.out.println("Расцветка не найдена в кэше. ID: " + id + ", Кэш: " + rastsvetkaCache);
        }
        return name;
    }

    private String getFurnituraNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = furnituraCache.getOrDefault(id, "Фурнитура " + id);
        if (name.equals("Фурнитура " + id)) {
            System.out.println("Фурнитура не найдена в кэше. ID: " + id + ", Кэш: " + furnituraCache);
        }
        return name;
    }

    private String getTkanNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = tkanCache.getOrDefault(id, "Ткань " + id);
        if (name.equals("Ткань " + id)) {
            System.out.println("Ткань не найдена в кэше. ID: " + id + ", Кэш: " + tkanCache);
        }
        return name;
    }

    private String getRazmerStringById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = razmerCache.getOrDefault(id, "Размер " + id);
        if (name.equals("Размер " + id)) {
            System.out.println("Размер не найден в кэше. ID: " + id + ", Кэш: " + razmerCache);
        }
        return name;
    }

    private String getKlientNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = klientCache.getOrDefault(id, "Клиент " + id);
        if (name.equals("Клиент " + id)) {
            System.out.println("Клиент не найден в кэше. ID: " + id + ", Кэш: " + klientCache);
        }
        return name;
    }

    private String getIzdelieNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = izdelieCache.getOrDefault(id, "Изделие " + id);
        if (name.equals("Изделие " + id)) {
            System.out.println("Изделие не найдено в кэше. ID: " + id + ", Кэш: " + izdelieCache);
        }
        return name;
    }

    private String getStatusNameById(int id) {
        if (id == 0) {
            return "Неизвестно";
        }
        String name = statusCache.getOrDefault(id, "Статус " + id);
        if (name.equals("Статус " + id)) {
            System.out.println("Статус не найден в кэше. ID: " + id + ", Кэш: " + statusCache);
        }
        return name;
    }

    private String getShveyaNameById(int id) {
        if (id == 0) {
            return "Не назначена";
        }
        String name = shveyaCache.getOrDefault(id, "Швея " + id);
        if (name.equals("Швея " + id)) {
            System.out.println("Швея не найдена в кэше. ID: " + id + ", Кэш: " + shveyaCache);
        }
        return name;
    }

    private void fillTsvetTkaniComboBox() {
        jComboBox1.removeAllItems();
        for (String nazvanie : tsvetTkaniCache.values()) {
            jComboBox1.addItem(nazvanie);
        }
    }

    private void fillTsvetFurnituryComboBox() {
        jComboBox14.removeAllItems();
        for (String nazvanie : tsvetFurnituryCache.values()) {
            jComboBox14.addItem(nazvanie);
        }
    }

    private void fillEdinitsaComboBox() {
        jComboBox13.removeAllItems();
        for (String nazvanie : edinitsaCache.values()) {
            jComboBox13.addItem(nazvanie);
        }
    }

    private void fillRastsvetkaComboBox() {
        jComboBox2.removeAllItems();
        for (String nazvanie : rastsvetkaCache.values()) {
            jComboBox2.addItem(nazvanie);
        }
    }

    private void fillFurnituraComboBox() {
        jComboBox3.removeAllItems();
        for (String nazvanie : furnituraCache.values()) {
            jComboBox3.addItem(nazvanie);
        }
    }

    private void fillTkanComboBox() {
        jComboBox15.removeAllItems();
        for (String nazvanie : tkanCache.values()) {
            jComboBox15.addItem(nazvanie);
        }
    }

    private void fillRazmerComboBox() {
        jComboBox4.removeAllItems();
        jComboBox16.removeAllItems();
        for (String razmerStr : razmerCache.values()) {
            jComboBox4.addItem(razmerStr);
            jComboBox16.addItem(razmerStr);
        }
    }

    private void fillKlientComboBox() {
        jComboBox18.removeAllItems();
        for (String klientStr : klientCache.values()) {
            jComboBox18.addItem(klientStr);
        }
    }

    private void fillIzdelieComboBox() {
        jComboBox20.removeAllItems();
        for (String nazvanie : izdelieCache.values()) {
            jComboBox20.addItem(nazvanie);
        }
    }

    private void fillStatusComboBox() {
        jComboBox17.removeAllItems();
        for (String nazvanie : statusCache.values()) {
            jComboBox17.addItem(nazvanie);
        }
    }

    private void fillShveyaComboBox() {
        jComboBox19.removeAllItems();
        for (String shveyaStr : shveyaCache.values()) {
            jComboBox19.addItem(shveyaStr);
        }
    }

    private void refreshAllCaches() {
        loadAllCaches();
        fillComboBoxesFromCaches();
    }

    private void refreshTkanCaches() {
        loadTsvetTkaniCache();
        loadRastsvetkaCache();
        loadTkanCache();
        fillTsvetTkaniComboBox();
        fillRastsvetkaComboBox();
        fillTkanComboBox();
    }

    private void refreshFurnituraCaches() {
        loadTsvetFurnituryCache();
        loadEdinitsaCache();
        loadFurnituraCache();
        fillTsvetFurnituryComboBox();
        fillEdinitsaComboBox();
        fillFurnituraComboBox();
    }

    private void refreshProdazhaCaches() {
        loadFurnituraCache();
        loadTkanCache();
        loadRazmerCache();
        fillFurnituraComboBox();
        fillTkanComboBox();
        fillRazmerComboBox();
    }

    private void refreshZakazCaches() {
        loadKlientCache();
        loadIzdelieCache();
        loadStatusCache();
        loadShveyaCache();
        fillKlientComboBox();
        fillIzdelieComboBox();
        fillStatusComboBox();
        fillShveyaComboBox();
    }

    private void loadTsvetTkaniCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTsvettkani";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    tsvetTkaniCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String nazvanie = item.getString("nazvanie");
                        tsvetTkaniCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено цветов ткани: " + tsvetTkaniCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша цветов ткани: " + e.getMessage());
        }
    }

    private int getFurnituraIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : furnituraCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Фурнитура '" + name + "' не найдена");
    }

    private int getTkanIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : tkanCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Ткань '" + name + "' не найдена");
    }

    private int getRazmerIdByString(String razmerStr) throws Exception {
        for (Map.Entry<Integer, String> entry : razmerCache.entrySet()) {
            if (entry.getValue().equals(razmerStr)) {
                return entry.getKey();
            }
        }
        throw new Exception("Размер '" + razmerStr + "' не найден");
    }

    private int getKlientIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : klientCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Клиент '" + name + "' не найден");
    }

    private int getIzdelieIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : izdelieCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Изделие '" + name + "' не найдено");
    }

    private int getStatusRabotyIdByName(String name) throws Exception {
        for (Map.Entry<Integer, String> entry : statusCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Статус '" + name + "' не найден");
    }

    private int getShveyaIdByName(String name) throws Exception {
        // Если имя в формате "Швея X", извлекаем ID
        if (name.startsWith("Швея ")) {
            try {
                String idStr = name.substring(5).trim();
                return Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                throw new Exception("Неверный формат ID швеи: " + name);
            }
        }

        // Ищем в кэше
        for (Map.Entry<Integer, String> entry : shveyaCache.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Швея '" + name + "' не найдена");
    }
// Аналогичные методы для других сущностей...

    private void loadTsvetFurnituryCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTsvetfurnitury";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    tsvetFurnituryCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String nazvanie = item.getString("nazvanie");
                        tsvetFurnituryCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено цветов фурнитуры: " + tsvetFurnituryCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша цветов фурнитуры: " + e.getMessage());
        }
    }

    private void loadEdinitsaCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allEdinitsyizmereniya";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    edinitsaCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String nazvanie = item.getString("nazvanie");
                        edinitsaCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено единиц измерения: " + edinitsaCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша единиц измерения: " + e.getMessage());
        }
    }

    private void loadRastsvetkaCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allRastsvetka";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    rastsvetkaCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String nazvanie = item.getString("nazvanie");
                        rastsvetkaCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено расцветок: " + rastsvetkaCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша расцветок: " + e.getMessage());
        }
    }

    private void loadFurnituraCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allFurnitura";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    furnituraCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String nazvanie = item.getString("nazvanie");
                        furnituraCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено фурнитуры: " + furnituraCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша фурнитуры: " + e.getMessage());
        }
    }

    private void loadTkanCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTkan";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    tkanCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String nazvanie = item.getString("nazvanie");
                        tkanCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено тканей: " + tkanCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша тканей: " + e.getMessage());
        }
    }

    private void loadRazmerCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allRazmer";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    razmerCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");

                        // Используем правильные названия полей из JSON
                        int shirina = item.has("shirina") ? item.getInt("shirina")
                                : item.has("Shirina") ? item.getInt("Shirina") : 0;
                        int dlina = item.has("dlina") ? item.getInt("dlina")
                                : item.has("Dlina") ? item.getInt("Dlina") : 0;

                        String razmerStr = shirina + "x" + dlina;
                        razmerCache.put(id, razmerStr);
                    }
                    System.out.println("Загружено размеров: " + razmerCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша размеров: " + e.getMessage());
            // Добавляем значения по умолчанию при ошибке
            razmerCache.put(1, "150x100");
            razmerCache.put(2, "140x100");
        }
    }

    private void loadKlientCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allKlient";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    klientCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");

                        // Используем правильные названия полей из JSON
                        String familiya = item.has("familiya") ? item.getString("familiya")
                                : item.has("Familiya") ? item.getString("Familiya") : "";
                        String imya = item.has("imya") ? item.getString("imya")
                                : item.has("Imya") ? item.getString("Imya") : "";

                        String klientStr = familiya + " " + imya;
                        klientCache.put(id, klientStr);
                    }
                    System.out.println("Загружено клиентов: " + klientCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша клиентов: " + e.getMessage());
            // Добавляем значения по умолчанию при ошибке
            klientCache.put(1, "Волков Андрей");
            klientCache.put(2, "Зайцева Ирина");
        }
    }

    private void loadIzdelieCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allIzdelie";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    izdelieCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");

                        // Используем правильные названия полей из JSON
                        String nazvanie = item.has("nazvanie") ? item.getString("nazvanie")
                                : item.has("Nazvanie") ? item.getString("Nazvanie") : "";

                        izdelieCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено изделий: " + izdelieCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша изделий: " + e.getMessage());
            // Добавляем значения по умолчанию при ошибке
            izdelieCache.put(1, "Платье");
            izdelieCache.put(2, "Брюки");
        }
    }

    private void loadStatusCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allStatusraboty";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    statusCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String nazvanie = item.getString("nazvanie");
                        statusCache.put(id, nazvanie);
                    }
                    System.out.println("Загружено статусов: " + statusCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша статусов: " + e.getMessage());
        }
    }

    private void loadShveyaCache() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allShveya";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    shveyaCache.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        shveyaCache.put(id, "Швея " + id);
                    }
                    System.out.println("Загружено швей: " + shveyaCache.size());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки кэша швей: " + e.getMessage());
        }
    }

    private void testTsvetFurnituryData() {
        System.out.println("=== ТЕСТИРОВАНИЕ ДАННЫХ ЦВЕТОВ ФУРНИТУРЫ ===");

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTsvetfurnitury";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    System.out.println("Все цвета фурнитуры в базе:");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        System.out.println("ID: " + item.getInt("id") + ", Название: " + item.getString("nazvanie"));
                    }
                } else {
                    System.out.println("Ошибка при получении данных: " + response.code());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка тестирования: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getPolzovatelNameById(int id) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allPolzovateli";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int itemId = item.getInt("id");
                        if (itemId == id) {
                            String familiya = item.getString("familiya");
                            String imya = item.getString("imya");
                            String otchestvo = item.optString("otchestvo", "");
                            return familiya + " " + imya + (otchestvo.isEmpty() ? "" : " " + otchestvo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения пользователя ID " + id + ": " + e.getMessage());
        }
        return null;
    }

    private void setupTables() {
        // Таблица тканей
        Vector<String> tkanHeaders = new Vector<>();
        tkanHeaders.add("Номер");
        tkanHeaders.add("Артикул");
        tkanHeaders.add("Название");
        tkanHeaders.add("Категория");
        tkanHeaders.add("Цвет");
        tkanHeaders.add("Расцветка");
        tkanHeaders.add("Новинка");
        DefaultTableModel tkanModel = new DefaultTableModel(tkanHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tkanModel);

        // Таблица фурнитуры
        Vector<String> furnituraHeaders = new Vector<>();
        furnituraHeaders.add("Номер");
        furnituraHeaders.add("Артикул");
        furnituraHeaders.add("Название");
        furnituraHeaders.add("Цвет");
        furnituraHeaders.add("Единица");
        furnituraHeaders.add("Новинка");
        DefaultTableModel furnituraModel = new DefaultTableModel(furnituraHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable2.setModel(furnituraModel);

        // Таблица продаж фурнитуры
        Vector<String> prodazhaFurnituraHeaders = new Vector<>();
        prodazhaFurnituraHeaders.add("Номер");
        prodazhaFurnituraHeaders.add("Дата");
        prodazhaFurnituraHeaders.add("Фурнитура");
        prodazhaFurnituraHeaders.add("Количество");
        prodazhaFurnituraHeaders.add("Цена за единицу");
        DefaultTableModel prodazhaFurnituraModel = new DefaultTableModel(prodazhaFurnituraHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable3.setModel(prodazhaFurnituraModel);

        // Таблица продаж ткани
        Vector<String> prodazhaTkanHeaders = new Vector<>();
        prodazhaTkanHeaders.add("Номер");
        prodazhaTkanHeaders.add("Дата");
        prodazhaTkanHeaders.add("Ткань");
        prodazhaTkanHeaders.add("Количество");
        prodazhaTkanHeaders.add("Цена за рулон");
        prodazhaTkanHeaders.add("Размер");
        DefaultTableModel prodazhaTkanModel = new DefaultTableModel(prodazhaTkanHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable4.setModel(prodazhaTkanModel);

        Vector<String> zakazHeaders = new Vector<>();
        zakazHeaders.add("Номер");
        zakazHeaders.add("Дата");
        zakazHeaders.add("Клиент");
        zakazHeaders.add("Изделие");
        zakazHeaders.add("Статус");
        zakazHeaders.add("Швея");
        zakazHeaders.add("Цена работы");
        zakazHeaders.add("Скидка"); // ДОБАВЛЯЕМ СТОЛБЕЦ ДЛЯ СКИДКИ

        DefaultTableModel zakazModel = new DefaultTableModel(zakazHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable5.setModel(zakazModel);
    }

    private void loadComboBoxData() {
        loadSkidkaComboBox();
        // Загрузка цветов ткани
        loadTsvetTkani();
        // Загрузка расцветок
        loadRastsvetka();
        // Загрузка цветов фурнитуры
        loadTsvetFurnitury();
        // Загрузка единиц измерения
        loadEdinitsyIzmereniya();
        // Загрузка размеров
        loadRazmer();
        // Загрузка статусов работы
        loadStatusRaboty();
        // Загрузка клиентов
        loadKlient();
        // Загрузка швей
        loadShveya();
        // Загрузка изделий
        loadIzdelie();
        // Загрузка скидок
        loadSkidka();
    }

    private void loadInitialData() {
        allTkan();
        allFurnitura();
        allProdazhafurnitury();
        allProdazhatkani();
        allZakaznaposhiv();
    }

    private void loadTsvetTkani() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTsvettkani";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox1.removeAllItems();
                    tsvetTkaniMap.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String nazvanie = item.getString("nazvanie");
                        int id = item.getInt("id");

                        jComboBox1.addItem(nazvanie);
                        tsvetTkaniMap.put(nazvanie, id);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки цветов ткани: " + e.getMessage());
        }
    }

    private void loadRastsvetka() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allRastsvetka";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox2.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String nazvanie = item.has("nazvanie") ? item.getString("nazvanie") : "Расцветка " + (i + 1);
                        jComboBox2.addItem(nazvanie);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки расцветок: " + e.getMessage());
            jComboBox2.addItem("Однотонная");
        }
    }

    private void loadTsvetFurnitury() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTsvetfurnitury";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox14.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String nazvanie = item.has("nazvanie") ? item.getString("nazvanie") : "Цвет " + (i + 1);
                        jComboBox14.addItem(nazvanie);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки цветов фурнитуры: " + e.getMessage());
            jComboBox14.addItem("Золото");
        }
    }

    private void loadEdinitsyIzmereniya() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allEdinitsyizmereniya";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox13.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String nazvanie = item.has("nazvanie") ? item.getString("nazvanie") : "Ед. " + (i + 1);
                        jComboBox13.addItem(nazvanie);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки единиц измерения: " + e.getMessage());
            jComboBox13.addItem("шт");
        }
    }

    private void loadRazmer() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allRazmer";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox4.removeAllItems();
                    jComboBox16.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        // Используем правильные названия полей из вашей базы
                        int shirina = item.has("Shirina") ? item.getInt("Shirina") : 0;
                        int dlina = item.has("Dlina") ? item.getInt("Dlina") : 0;
                        String razmer = shirina + "x" + dlina;
                        jComboBox4.addItem(razmer);
                        jComboBox16.addItem(razmer);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки размеров: " + e.getMessage());
            // Добавляем значения по умолчанию
            jComboBox4.addItem("150x100");
            jComboBox16.addItem("150x100");
        }
    }

    private void loadStatusRaboty() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allStatusraboty";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox17.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String nazvanie = item.has("nazvanie") ? item.getString("nazvanie") : "Статус " + (i + 1);
                        jComboBox17.addItem(nazvanie);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки статусов работы: " + e.getMessage());
            jComboBox17.addItem("Принят");
        }
    }

    private void loadKlient() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allKlient";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox18.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        // Используем правильные названия полей
                        String familiya = item.has("Familiya") ? item.getString("Familiya") : "";
                        String imya = item.has("Imya") ? item.getString("Imya") : "";
                        String klient = familiya + " " + imya;
                        jComboBox18.addItem(klient);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки клиентов: " + e.getMessage());
            // Добавляем значения по умолчанию
            jComboBox18.addItem("Волков Андрей");
        }
    }

    private void loadShveya() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allShveya";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox19.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        jComboBox19.addItem("Швея " + item.getInt("id"));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки швей: " + e.getMessage());
            jComboBox19.addItem("Швея 1");
        }
    }

    private void loadIzdelie() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allIzdelie";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    jComboBox20.removeAllItems();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        // Используем правильные названия полей
                        String nazvanie = item.has("Nazvanie") ? item.getString("Nazvanie") : "Изделие " + (i + 1);
                        jComboBox20.addItem(nazvanie);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки изделий: " + e.getMessage());
            // Добавляем значения по умолчанию
            jComboBox20.addItem("Платье");
        }
    }

    private void loadSkidka() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allSkidka";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    // Здесь можно добавить комбобокс для скидок если нужно
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Вспомогательный метод для получения названия цвета ткани из JSON объекта
    private String getTsvetTkaniNameFromObject(JSONObject tkan) {
        try {
            // Сначала пробуем получить как вложенный объект
            if (tkan.has("idTsvet") && tkan.get("idTsvet") instanceof JSONObject) {
                JSONObject tsvetObj = tkan.getJSONObject("idTsvet");
                if (tsvetObj.has("nazvanie")) {
                    return tsvetObj.getString("nazvanie");
                }
            }

            // Пробуем получить как числовой ID
            if (tkan.has("idTsvet")) {
                int tsvetId = tkan.getInt("idTsvet");
                return getTsvetTkaniNameById(tsvetId);
            }

            // Альтернативные ключи
            String[] possibleKeys = {"tsvet", "color", "id_tsvet", "tsvet_id", "idTsvet"};
            for (String key : possibleKeys) {
                if (tkan.has(key)) {
                    if (tkan.get(key) instanceof JSONObject) {
                        JSONObject tsvetObj = tkan.getJSONObject(key);
                        return tsvetObj.getString("nazvanie");
                    } else if (tkan.get(key) instanceof Number) {
                        int tsvetId = tkan.getInt(key);
                        return getTsvetTkaniNameById(tsvetId);
                    } else if (tkan.get(key) instanceof String) {
                        // Если цвет уже представлен как строка
                        return tkan.getString(key);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения цвета ткани: " + e.getMessage());
        }
        return "Неизвестно";
    }

// Вспомогательный метод для получения названия расцветки из JSON объекта
    private String getRastsvetkaNameFromObject(JSONObject tkan) {
        try {
            // Сначала пробуем получить как вложенный объект
            if (tkan.has("idRastsvetka") && tkan.get("idRastsvetka") instanceof JSONObject) {
                JSONObject rastsvetkaObj = tkan.getJSONObject("idRastsvetka");
                if (rastsvetkaObj.has("nazvanie")) {
                    return rastsvetkaObj.getString("nazvanie");
                }
            }

            // Пробуем получить как числовой ID
            if (tkan.has("idRastsvetka")) {
                int rastsvetkaId = tkan.getInt("idRastsvetka");
                return getRastsvetkaNameById(rastsvetkaId);
            }

            // Альтернативные ключи
            String[] possibleKeys = {"rastsvetka", "pattern", "id_rastsvetka", "rastsvetka_id"};
            for (String key : possibleKeys) {
                if (tkan.has(key)) {
                    if (tkan.get(key) instanceof JSONObject) {
                        JSONObject rastsvetkaObj = tkan.getJSONObject(key);
                        return rastsvetkaObj.getString("nazvanie");
                    } else if (tkan.get(key) instanceof Number) {
                        int rastsvetkaId = tkan.getInt(key);
                        return getRastsvetkaNameById(rastsvetkaId);
                    } else if (tkan.get(key) instanceof String) {
                        // Если расцветка уже представлена как строка
                        return tkan.getString(key);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения расцветки: " + e.getMessage());
        }
        return "Неизвестно";
    }

// Вспомогательный метод для получения названия цвета фурнитуры из JSON объекта
    private String getTsvetFurnituryNameFromObject(JSONObject furnitura) {
        try {
            // Сначала пробуем получить как вложенный объект
            if (furnitura.has("idTsvet") && furnitura.get("idTsvet") instanceof JSONObject) {
                JSONObject tsvetObj = furnitura.getJSONObject("idTsvet");
                if (tsvetObj.has("nazvanie")) {
                    return tsvetObj.getString("nazvanie");
                }
            }

            // Пробуем получить как числовой ID
            if (furnitura.has("idTsvet")) {
                int tsvetId = furnitura.getInt("idTsvet");
                return getTsvetFurnituryNameById(tsvetId);
            }

            // Альтернативные ключи
            String[] possibleKeys = {"tsvet", "color", "id_tsvet", "tsvet_id"};
            for (String key : possibleKeys) {
                if (furnitura.has(key)) {
                    if (furnitura.get(key) instanceof JSONObject) {
                        JSONObject tsvetObj = furnitura.getJSONObject(key);
                        return tsvetObj.getString("nazvanie");
                    } else if (furnitura.get(key) instanceof Number) {
                        int tsvetId = furnitura.getInt(key);
                        return getTsvetFurnituryNameById(tsvetId);
                    } else if (furnitura.get(key) instanceof String) {
                        // Если цвет уже представлен как строка
                        return furnitura.getString(key);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения цвета фурнитуры: " + e.getMessage());
        }
        return "Неизвестно";
    }

// Вспомогательный метод для получения названия единицы измерения из JSON объекта
    private String getEdinitsaNameFromObject(JSONObject furnitura) {
        try {
            // Сначала пробуем получить как вложенный объект
            if (furnitura.has("idEdIzmereniya") && furnitura.get("idEdIzmereniya") instanceof JSONObject) {
                JSONObject edinitsaObj = furnitura.getJSONObject("idEdIzmereniya");
                if (edinitsaObj.has("nazvanie")) {
                    return edinitsaObj.getString("nazvanie");
                }
            }

            // Пробуем получить как числовой ID
            if (furnitura.has("idEdIzmereniya")) {
                int edinitsaId = furnitura.getInt("idEdIzmereniya");
                return getEdinitsaNameById(edinitsaId);
            }

            // Альтернативные ключи
            String[] possibleKeys = {"edIzmereniya", "unit", "id_ed_izmereniya", "ed_izmereniya_id"};
            for (String key : possibleKeys) {
                if (furnitura.has(key)) {
                    if (furnitura.get(key) instanceof JSONObject) {
                        JSONObject edinitsaObj = furnitura.getJSONObject(key);
                        return edinitsaObj.getString("nazvanie");
                    } else if (furnitura.get(key) instanceof Number) {
                        int edinitsaId = furnitura.getInt(key);
                        return getEdinitsaNameById(edinitsaId);
                    } else if (furnitura.get(key) instanceof String) {
                        // Если единица измерения уже представлена как строка
                        return furnitura.getString(key);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения единицы измерения: " + e.getMessage());
        }
        return "Неизвестно";
    }

    private void debugJsonStructure(String url, String endpointName) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(BASE_URL + url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    if (jsonArray.length() > 0) {
                        JSONObject firstItem = jsonArray.getJSONObject(0);
                        System.out.println("=== Структура JSON для " + endpointName + " ===");
                        System.out.println("Первый элемент: " + firstItem.toString());
                        System.out.println("Ключи: " + firstItem.keySet());
                        System.out.println("=================================");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка отладки структуры " + endpointName + ": " + e.getMessage());
        }
    }

    private int getIntValue(JSONObject obj, String key) {
        try {
            // Пробуем разные варианты написания ключей
            String[] possibleKeys = {
                key.toLowerCase(),
                key,
                "id" + key.substring(0, 1).toUpperCase() + key.substring(1),
                key.toUpperCase(),
                camelCaseToSnakeCase(key)
            };

            for (String possibleKey : possibleKeys) {
                if (obj.has(possibleKey) && !obj.isNull(possibleKey)) {
                    Object value = obj.get(possibleKey);
                    if (value instanceof Number) {
                        int intValue = ((Number) value).intValue();
                        System.out.println("Найдено значение " + key + " (вариант " + possibleKey + "): " + intValue);
                        return intValue;
                    }
                }
            }

            System.out.println("Ключ " + key + " не найден в объекте. Доступные ключи: " + obj.keySet());
        } catch (Exception e) {
            System.err.println("Ошибка чтения числа " + key + ": " + e.getMessage());
        }
        return 0;
    }

    private String getStringValue(JSONObject obj, String key) {
        try {
            // Пробуем разные варианты написания ключей
            String[] possibleKeys = {
                key.toLowerCase(),
                key,
                key.toUpperCase(),
                camelCaseToSnakeCase(key)
            };

            for (String possibleKey : possibleKeys) {
                if (obj.has(possibleKey) && !obj.isNull(possibleKey)) {
                    Object value = obj.get(possibleKey);
                    if (value instanceof String) {
                        return (String) value;
                    } else {
                        return value.toString();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения строки " + key + ": " + e.getMessage());
        }
        return "";
    }

// Вспомогательный метод для преобразования camelCase в snake_case
    private String camelCaseToSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    private boolean getBooleanValue(JSONObject obj, String key) {
        try {
            // Сначала пробуем в нижнем регистре
            if (obj.has(key.toLowerCase()) && !obj.isNull(key.toLowerCase())) {
                return obj.getBoolean(key.toLowerCase());
            }
            // Потом в верхнем регистре
            if (obj.has(key) && !obj.isNull(key)) {
                return obj.getBoolean(key);
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения boolean " + key + ": " + e.getMessage());
        }
        return false;
    }

    private BigDecimal getBigDecimalValue(JSONObject obj, String key) {
        try {
            // Пробуем разные варианты написания ключей
            String[] possibleKeys = {
                key.toLowerCase(),
                key,
                key.toUpperCase(),
                camelCaseToSnakeCase(key)
            };

            for (String possibleKey : possibleKeys) {
                if (obj.has(possibleKey) && !obj.isNull(possibleKey)) {
                    Object value = obj.get(possibleKey);
                    System.out.println("Найдено значение " + key + " (вариант " + possibleKey + "): " + value); // Отладка

                    if (value instanceof Number) {
                        return BigDecimal.valueOf(((Number) value).doubleValue());
                    } else if (value instanceof String) {
                        try {
                            return new BigDecimal((String) value);
                        } catch (NumberFormatException e) {
                            return BigDecimal.ZERO;
                        }
                    } else if (value instanceof BigDecimal) {
                        return (BigDecimal) value;
                    }
                }
            }

            System.out.println("Ключ " + key + " не найден в объекте. Доступные ключи: " + obj.keySet());
        } catch (Exception e) {
            System.err.println("Ошибка чтения BigDecimal " + key + ": " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal parseBigDecimal(Object value) {
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        } else if (value instanceof String) {
            try {
                return new BigDecimal((String) value);
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    private Map<Integer, String> loadTsvetTkaniMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTsvettkani";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки цветов ткани: " + e.getMessage());
            // Добавляем значения по умолчанию
            map.put(1, "Красный");
            map.put(2, "Синий");
            map.put(3, "Зеленый");
        }
        return map;
    }

    private Map<Integer, String> loadRastsvetkaMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allRastsvetka";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки расцветок: " + e.getMessage());
            map.put(1, "Однотонная");
            map.put(2, "Полоска");
        }
        return map;
    }

    private Map<Integer, String> loadTsvetFurnituryMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTsvetfurnitury";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки цветов фурнитуры: " + e.getMessage());
            map.put(1, "Золото");
            map.put(2, "Серебро");
        }
        return map;
    }

    private Map<Integer, String> loadEdinitsyIzmereniyaMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allEdinitsyizmereniya";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки единиц измерения: " + e.getMessage());
            map.put(1, "шт");
            map.put(2, "м");
        }
        return map;
    }

    private Map<Integer, String> loadFurnituraMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allFurnitura";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки фурнитуры: " + e.getMessage());
            map.put(1, "Пуговицы");
            map.put(2, "Молния");
        }
        return map;
    }

    private Map<Integer, String> loadTkanMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allTkan";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки тканей: " + e.getMessage());
            map.put(1, "Сатин");
            map.put(2, "Шифон");
        }
        return map;
    }

    private Map<Integer, String> loadRazmerMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allRazmer";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        int shirina = getIntValue(item, "Shirina");
                        int dlina = getIntValue(item, "Dlina");
                        String razmerStr = shirina + "x" + dlina;
                        map.put(id, razmerStr);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки размеров: " + e.getMessage());
            map.put(1, "150x100");
            map.put(2, "140x100");
        }
        return map;
    }

    private Map<Integer, String> loadKlientMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allKlient";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String familiya = getStringValue(item, "Familiya");
                        String imya = getStringValue(item, "Imya");
                        String klientStr = familiya + " " + imya;
                        map.put(id, klientStr);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки клиентов: " + e.getMessage());
            map.put(1, "Волков Андрей");
            map.put(2, "Зайцева Ирина");
        }
        return map;
    }

    private Map<Integer, String> loadIzdelieMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allIzdelie";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "Nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки изделий: " + e.getMessage());
            map.put(1, "Платье");
            map.put(2, "Брюки");
        }
        return map;
    }

    private Map<Integer, String> loadStatusRabotyMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allStatusraboty";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        String name = getStringValue(item, "nazvanie");
                        map.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки статусов: " + e.getMessage());
            map.put(1, "Принят");
            map.put(2, "В работе");
        }
        return map;
    }

    private Map<Integer, String> loadShveyaMap() {
        Map<Integer, String> map = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allShveya";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = getIntValue(item, "id");
                        map.put(id, "Швея " + id);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки швей: " + e.getMessage());
            map.put(1, "Швея 1");
            map.put(2, "Швея 2");
        }
        return map;
    }

    private void addTestDataToTable1() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{1, "T001", "Сатин", "Натуральная", "Красный", "Однотонная", "Да"});
        model.addRow(new Object[]{2, "T002", "Шифон", "Вечерняя", "Синий", "Полоска", "Нет"});
    }

    private void addTestDataToTable2() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.addRow(new Object[]{1, "F001", "Пуговицы", "Золото", "шт", "Да"});
        model.addRow(new Object[]{2, "F002", "Молния", "Серебро", "м", "Нет"});
    }

    private void addTestDataToTable3() {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.addRow(new Object[]{1, "2024-01-15", "Пуговицы", 10, 50.00});
        model.addRow(new Object[]{2, "2024-01-16", "Молния", 5, 120.00});
    }

    private void addTestDataToTable4() {
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.addRow(new Object[]{1, "2024-01-15", "Сатин", 2, 2500.00, "150x100"});
        model.addRow(new Object[]{2, "2024-01-16", "Шифон", 1, 1800.00, "140x100"});
    }

    private void addTestDataToTable5() {
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        model.addRow(new Object[]{1, "2024-01-15", "Волков Андрей", "Платье", "Принят", "Швея 1", 1500.00});
        model.addRow(new Object[]{2, "2024-01-16", "Зайцева Ирина", "Брюки", "В работе", "Швея 2", 1200.00});
    }

    // Вспомогательный метод для безопасного получения значений из таблицы
    private String getValueAsString(JTable table, int row, int column) {
        Object value = table.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void clearTkanForm() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jCheckBox1.setSelected(false);
    }

    private void clearFurnituraForm() {
        jTextField12.setText("");
        jTextField13.setText("");
        jComboBox14.setSelectedIndex(0);
        jComboBox13.setSelectedIndex(0);
        jCheckBox2.setSelected(false);
    }

    private void clearProdazhaTkanForm() {
        jTextField4.setText(""); // дата
        jTextField5.setText(""); // количество
        jTextField14.setText(""); // цена
        jComboBox15.setSelectedIndex(0); // ткань
        jComboBox4.setSelectedIndex(0); // размер
    }

    private void clearProdazhaFurnituraForm() {
        jTextField4.setText(""); // дата
        jTextField5.setText(""); // количество
        jTextField14.setText(""); // цена
        jComboBox3.setSelectedIndex(0); // фурнитура
    }

    private void clearZakazForm() {
        jTextField15.setText("");
        jTextField16.setText("");
        jComboBox17.setSelectedIndex(0);
        jComboBox18.setSelectedIndex(0);
        jComboBox19.setSelectedIndex(0);
        jComboBox20.setSelectedIndex(0);
        jComboBox21.setSelectedIndex(0); // Очищаем скидку
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jComboBox13 = new javax.swing.JComboBox<>();
        jComboBox14 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox15 = new javax.swing.JComboBox<>();
        jComboBox16 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jComboBox17 = new javax.swing.JComboBox<>();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jComboBox18 = new javax.swing.JComboBox<>();
        jComboBox19 = new javax.swing.JComboBox<>();
        jComboBox20 = new javax.swing.JComboBox<>();
        jComboBox21 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Панель - продавец");

        jTabbedPane1.setBackground(new java.awt.Color(163, 27, 24));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(163, 27, 24));
        jLabel2.setText("Артикул");

        jTextField1.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(163, 27, 24));
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(163, 27, 24));
        jLabel3.setText("Название");

        jTextField2.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(163, 27, 24));
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(163, 27, 24));
        jLabel4.setText("Категория");

        jTextField3.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(163, 27, 24));
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(163, 27, 24));
        jLabel6.setText("Цвет");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(163, 27, 24));
        jLabel7.setText("Расцветка");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(163, 27, 24));
        jLabel8.setText("Новинка");

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

        jComboBox1.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox2.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jCheckBox1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 267, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
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
                            .addComponent(jLabel6)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jCheckBox1))
                        .addGap(166, 166, 166))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))))
                .addContainerGap(344, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("                  Ткань                ", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(163, 27, 24));
        jLabel5.setText("Артикул");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(163, 27, 24));
        jLabel9.setText("Название");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(163, 27, 24));
        jLabel11.setText("Цвет");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(163, 27, 24));
        jLabel12.setText("Единица");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(163, 27, 24));
        jLabel13.setText("Новинка");

        jScrollPane2.setForeground(new java.awt.Color(163, 27, 24));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jCheckBox2.setForeground(new java.awt.Color(163, 27, 24));

        jButton16.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton16.setForeground(new java.awt.Color(163, 27, 24));
        jButton16.setText("Добавить");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton17.setForeground(new java.awt.Color(163, 27, 24));
        jButton17.setText("Изменить");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton18.setForeground(new java.awt.Color(163, 27, 24));
        jButton18.setText("Удалить");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jTextField12.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(163, 27, 24));
        jTextField12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField13.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(163, 27, 24));
        jTextField13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox13.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox13.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox14.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox14.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton16)
                        .addGap(18, 18, 18)
                        .addComponent(jButton17)
                        .addGap(18, 18, 18)
                        .addComponent(jButton18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField13))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(23, 23, 23)
                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(21, 21, 21)
                                .addComponent(jCheckBox2))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(63, 63, 63))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox2)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16)
                    .addComponent(jButton17)
                    .addComponent(jButton18))
                .addContainerGap(336, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("               Фурнитура               ", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(914, 400));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(163, 27, 24));
        jLabel10.setText("Дата");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(163, 27, 24));
        jLabel14.setText("Количество");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(163, 27, 24));
        jLabel15.setText("Фурнитура");

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(163, 27, 24));
        jLabel16.setText("Цена за единицу");

        jScrollPane3.setForeground(new java.awt.Color(163, 27, 24));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jScrollPane4.setForeground(new java.awt.Color(163, 27, 24));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(163, 27, 24));
        jLabel19.setText("Ткань");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(163, 27, 24));
        jLabel20.setText("Ширина");

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(163, 27, 24));
        jLabel21.setText("Длина");

        jButton19.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton19.setForeground(new java.awt.Color(163, 27, 24));
        jButton19.setText("Добавить");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton20.setForeground(new java.awt.Color(163, 27, 24));
        jButton20.setText("Изменить");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton21.setForeground(new java.awt.Color(163, 27, 24));
        jButton21.setText("Удалить");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton22.setForeground(new java.awt.Color(163, 27, 24));
        jButton22.setText("Добавить");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton23.setForeground(new java.awt.Color(163, 27, 24));
        jButton23.setText("Изменить");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton24.setForeground(new java.awt.Color(163, 27, 24));
        jButton24.setText("Удалить");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(163, 27, 24));
        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField5.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(163, 27, 24));
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField14.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(163, 27, 24));
        jTextField14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox3.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox4.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox4.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox15.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox15.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox16.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox16.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel14))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 95, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(66, 66, 66)
                                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(27, 27, 27)
                                .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(75, 75, 75))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton19)
                                .addGap(18, 18, 18)
                                .addComponent(jButton20)
                                .addGap(18, 18, 18)
                                .addComponent(jButton21))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton22)
                                .addGap(18, 18, 18)
                                .addComponent(jButton23)
                                .addGap(18, 18, 18)
                                .addComponent(jButton24)))
                        .addGap(22, 22, 22))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(11, 11, 11)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton19)
                            .addComponent(jButton20)
                            .addComponent(jButton21)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton22)
                            .addComponent(jButton23)
                            .addComponent(jButton24))))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("               Продажа фурнитуры и ткани              ", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(163, 27, 24));
        jLabel17.setText("Дата");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(163, 27, 24));
        jLabel18.setText("Статус работы");

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(163, 27, 24));
        jLabel22.setText("Клиент");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(163, 27, 24));
        jLabel23.setText("Швея");

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(163, 27, 24));
        jLabel24.setText("Изделие");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(163, 27, 24));
        jLabel25.setText("Цена");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(163, 27, 24));
        jLabel26.setText("Скидка");

        jScrollPane5.setForeground(new java.awt.Color(163, 27, 24));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jTextField15.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(163, 27, 24));
        jTextField15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jTextField16.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(163, 27, 24));
        jTextField16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox17.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox17.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

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
        jButton27.setText("Удалить");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jComboBox18.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox18.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox19.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox19.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox20.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox20.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jComboBox21.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jComboBox21.setForeground(new java.awt.Color(163, 27, 24));
        jComboBox21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addGap(57, 57, 57))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton25)
                        .addGap(18, 18, 18)
                        .addComponent(jButton26)
                        .addGap(18, 18, 18)
                        .addComponent(jButton27)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25)
                    .addComponent(jButton26)
                    .addComponent(jButton27))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("                 Заказ                ", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        jTextField1.setText(getValueAsString(jTable1, selectedRow, 1)); // Артикул
        jTextField2.setText(getValueAsString(jTable1, selectedRow, 2)); // Название
        jTextField3.setText(getValueAsString(jTable1, selectedRow, 3)); // Категория

        String tsvet = getValueAsString(jTable1, selectedRow, 4);
        jComboBox1.setSelectedItem(tsvet);

        String rastsvetka = getValueAsString(jTable1, selectedRow, 5);
        jComboBox2.setSelectedItem(rastsvetka);

        jCheckBox1.setSelected(getValueAsString(jTable1, selectedRow, 6).equals("Да"));
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addTkan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deleteTkan();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        jTextField12.setText(getValueAsString(jTable2, selectedRow, 1)); // Артикул
        jTextField13.setText(getValueAsString(jTable2, selectedRow, 2)); // Название

        String tsvet = getValueAsString(jTable2, selectedRow, 3);
        jComboBox14.setSelectedItem(tsvet);

        String edinitsa = getValueAsString(jTable2, selectedRow, 4);
        jComboBox13.setSelectedItem(edinitsa);

        jCheckBox2.setSelected(getValueAsString(jTable2, selectedRow, 5).equals("Да"));
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int selectedRow = jTable3.getSelectedRow(); // ИСПРАВЛЕНО: было jTable4
        if (selectedRow == -1) {
            return;
        }

        try {
            String prodazhaId = getValueAsString(jTable3, selectedRow, 0); // ИСПРАВЛЕНО: было jTable4

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allProdazhafurnituryForTable";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject prodazha = jsonArray.getJSONObject(i);
                        if (prodazha.getInt("id") == Integer.parseInt(prodazhaId)) {
                            // Заполняем поля для фурнитуры
                            jTextField4.setText(getStringValue(prodazha, "data"));
                            jTextField5.setText(String.valueOf(getIntValue(prodazha, "Kolichestvo")));
                            jTextField14.setText(getBigDecimalValue(prodazha, "tsenaZaYedinitsu").toString());

                            // Устанавливаем фурнитуру в комбобокс
                            int furnituraId = getIntValue(prodazha, "idFurnitura");
                            String furnituraName = getFurnituraNameById(furnituraId);
                            setComboBoxValue(jComboBox3, furnituraName);

                            // Очищаем поля для ткани
                            clearProdazhaTkanFields();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных продажи фурнитуры: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        int selectedRow = jTable4.getSelectedRow(); // ИСПРАВЛЕНО: было jTable3
        if (selectedRow == -1) {
            return;
        }

        try {
            String prodazhaId = getValueAsString(jTable4, selectedRow, 0); // ИСПРАВЛЕНО: было jTable3

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allProdazhatkaniForTable";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject prodazha = jsonArray.getJSONObject(i);
                        if (prodazha.getInt("id") == Integer.parseInt(prodazhaId)) {
                            // Заполняем поля для ткани
                            jTextField4.setText(getStringValue(prodazha, "data"));
                            jTextField5.setText(String.valueOf(getIntValue(prodazha, "Kolichestvo")));
                            jTextField14.setText(getBigDecimalValue(prodazha, "tsenaZaRulon").toString());

                            // Устанавливаем ткань в комбобокс
                            int tkanId = getIntValue(prodazha, "idTkan");
                            String tkanName = getTkanNameById(tkanId);
                            setComboBoxValue(jComboBox15, tkanName);

                            // Устанавливаем размер в комбобокс
                            int razmerId = getIntValue(prodazha, "idRazmer");
                            String razmerStr = getRazmerStringById(razmerId);
                            setComboBoxValue(jComboBox4, razmerStr);

                            // Очищаем поля для фурнитуры
                            clearProdazhaFurnituraFields();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных продажи ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTable4MouseClicked
    private void clearProdazhaFurnituraFields() {
        // Очищаем только поля, специфичные для фурнитуры
        jComboBox3.setSelectedIndex(0);
        // jTextField4, jTextField5, jTextField14 не очищаем, т.к. они общие
    }

    private void clearProdazhaTkanFields() {
        // Очищаем только поля, специфичные для ткани
        jComboBox15.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox16.setSelectedIndex(0);
        // jTextField4, jTextField5, jTextField14 не очищаем, т.к. они общие
    }
    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        int selectedRow = jTable5.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        try {
            String zakazId = getValueAsString(jTable5, selectedRow, 0);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allZakaznaposhivForTable";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject zakaz = jsonArray.getJSONObject(i);
                        if (zakaz.getInt("id") == Integer.parseInt(zakazId)) {
                            // Заполняем поля данными
                            jTextField15.setText(getStringValue(zakaz, "Data"));

                            // Цена работы
                            BigDecimal tsenaRaboty = getBigDecimalValue(zakaz, "TsenaRabotyShvei");
                            jTextField16.setText(tsenaRaboty != null ? tsenaRaboty.toString() : "0.00");

                            // Устанавливаем клиента в комбобокс
                            int klientId = getIntValue(zakaz, "idKlient");
                            String klientStr = getKlientNameById(klientId);
                            setComboBoxValue(jComboBox18, klientStr);

                            // Устанавливаем изделие в комбобокс
                            int izdelieId = getIntValue(zakaz, "idIzdelie");
                            String izdelieName = getIzdelieNameById(izdelieId);
                            setComboBoxValue(jComboBox20, izdelieName);

                            // Устанавливаем статус в комбобокс
                            int statusId = getIntValue(zakaz, "idStatusRaboty");
                            String statusName = getStatusNameById(statusId);
                            setComboBoxValue(jComboBox17, statusName);

                            // Устанавливаем швею в комбобокс
                            int shveyaId = getIntValue(zakaz, "idShveya");
                            String shveyaStr = getShveyaNameById(shveyaId);
                            setComboBoxValue(jComboBox19, shveyaStr);

                            // Устанавливаем скидку в комбобокс (берем из данных JSON)
                            int skidkaId = getIntValue(zakaz, "idSkidka");
                            String skidkaProcent = getSkidkaProcentById(skidkaId);
                            setComboBoxValue(jComboBox21, skidkaProcent);

                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных заказа: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTable5MouseClicked

    private String getSkidkaProcentById(int skidkaId) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allSkidka";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject skidka = jsonArray.getJSONObject(i);
                        if (getIntValue(skidka, "id") == skidkaId) {
                            int procent = getIntValue(skidka, "procent");
                            return procent + "%";
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка получения скидки: " + e.getMessage());
        }
        return "0%"; // default value
    }

    private void setComboBoxValue(JComboBox<String> comboBox, String value) {
        if (value == null || value.isEmpty()) {
            if (comboBox.getItemCount() > 0) {
                comboBox.setSelectedIndex(0);
            }
            return;
        }

        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(value)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }

        // Если значение не найдено, устанавливаем первое
        if (comboBox.getItemCount() > 0) {
            comboBox.setSelectedIndex(0);
        }
    }

    private String getFurnituraNameFromComboBox(int furnituraId) {
        // Предполагаем, что в комбобоксе jComboBox3 уже загружены названия фурнитуры
        // и они соответствуют ID в том же порядке
        if (jComboBox3.getItemCount() > furnituraId - 1 && furnituraId > 0) {
            return jComboBox3.getItemAt(furnituraId - 1);
        }
        return "Неизвестно";
    }

    private String getTkanNameFromComboBox(int tkanId) {
        // Предполагаем, что в комбобоксе jComboBox15 уже загружены названия тканей
        if (jComboBox15.getItemCount() > tkanId - 1 && tkanId > 0) {
            return jComboBox15.getItemAt(tkanId - 1);
        }
        return "Неизвестно";
    }

    private String getRazmerStringFromComboBox(int razmerId) {
        // Предполагаем, что в комбобоксе jComboBox4 уже загружены размеры
        if (jComboBox4.getItemCount() > razmerId - 1 && razmerId > 0) {
            return jComboBox4.getItemAt(razmerId - 1);
        }
        return "Неизвестно";
    }

    private String getKlientNameFromComboBox(int klientId) {
        // Предполагаем, что в комбобоксе jComboBox18 уже загружены клиенты
        if (jComboBox18.getItemCount() > klientId - 1 && klientId > 0) {
            return jComboBox18.getItemAt(klientId - 1);
        }
        return "Неизвестно";
    }

    private String getIzdelieNameFromComboBox(int izdelieId) {
        // Предполагаем, что в комбобоксе jComboBox20 уже загружены изделия
        if (jComboBox20.getItemCount() > izdelieId - 1 && izdelieId > 0) {
            return jComboBox20.getItemAt(izdelieId - 1);
        }
        return "Неизвестно";
    }

    private String getStatusNameFromComboBox(int statusId) {
        // Предполагаем, что в комбобоксе jComboBox17 уже загружены статусы
        if (jComboBox17.getItemCount() > statusId - 1 && statusId > 0) {
            return jComboBox17.getItemAt(statusId - 1);
        }
        return "Неизвестно";
    }

    private String getShveyaNameFromComboBox(int shveyaId) {
        // Предполагаем, что в комбобоксе jComboBox19 уже загружены швеи
        if (jComboBox19.getItemCount() > shveyaId - 1 && shveyaId > 0) {
            return jComboBox19.getItemAt(shveyaId - 1);
        }
        return "Неизвестно";
    }


    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        addFurnitura();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        deleteFurnitura();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        addProdazhaFurnitury();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        deleteProdazhaFurnitury();
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        addProdazhaTkani();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        deleteProdazhaTkani();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        addZakaz();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        deleteZakaz();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        updateProdazhaFurnitury();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        updateProdazhaTkani();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        updateZakaz();
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updateTkan();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        updateFurnitura();
    }//GEN-LAST:event_jButton17ActionPerformed
    private void updateFurnitura() {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите фурнитуру для изменения");
            return;
        }

        String furnituraId = getValueAsString(jTable2, selectedRow, 0);
        String artikul = jTextField12.getText().trim();
        String nazvanie = jTextField13.getText().trim();
        String tsvetName = (String) jComboBox14.getSelectedItem();
        String edinitsaName = (String) jComboBox13.getSelectedItem();
        boolean novinka = jCheckBox2.isSelected();

        if (artikul.isEmpty() || nazvanie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Артикул и Название");
            return;
        }

        try {
            int tsvetId = getTsvetFurnituryIdByName(tsvetName);
            int edinitsaId = getEdinitsaIzmereniyaIdByName(edinitsaName);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updateFurnitura";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("id", furnituraId)
                    .add("artikul", artikul)
                    .add("nazvanie", nazvanie)
                    .add("idTsvet", String.valueOf(tsvetId))
                    .add("novinka", String.valueOf(novinka))
                    .add("idEdIzmereniya", String.valueOf(edinitsaId));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    if (jsonResponse.getBoolean("success")) {
                        JOptionPane.showMessageDialog(this, "Фурнитура успешно обновлена");
                        clearFurnituraForm();
                        refreshFurnituraCaches();
                        allFurnitura();
                    } else {
                        JOptionPane.showMessageDialog(this, "Ошибка обновления фурнитуры: " + jsonResponse.getString("message"));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка обновления фурнитуры: HTTP " + response.code());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления фурнитуры: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateTkan() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите ткань для изменения");
            return;
        }

        String tkanId = getValueAsString(jTable1, selectedRow, 0);
        String artikul = jTextField1.getText().trim();
        String nazvanie = jTextField2.getText().trim();
        String kategoriya = jTextField3.getText().trim();
        String tsvetName = (String) jComboBox1.getSelectedItem();
        String rastsvetkaName = (String) jComboBox2.getSelectedItem();
        boolean novinka = jCheckBox1.isSelected();

        if (artikul.isEmpty() || nazvanie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Артикул и Название");
            return;
        }

        try {
            int tsvetId = getTsvetTkaniIdByName(tsvetName);
            int rastsvetkaId = getRastsvetkaIdByName(rastsvetkaName);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updateTkan";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("id", tkanId)
                    .add("artikul", artikul)
                    .add("nazvanie", nazvanie)
                    .add("kategoriya", kategoriya)
                    .add("idTsvet", String.valueOf(tsvetId))
                    .add("idRastsvetka", String.valueOf(rastsvetkaId))
                    .add("novinka", String.valueOf(novinka));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    if (jsonResponse.getBoolean("success")) {
                        JOptionPane.showMessageDialog(this, "Ткань успешно обновлена");
                        clearTkanForm();
                        refreshTkanCaches();
                        allTkan();
                    } else {
                        JOptionPane.showMessageDialog(this, "Ошибка обновления ткани: " + jsonResponse.getString("message"));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка обновления ткани: HTTP " + response.code());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateZakaz() {
        int selectedRow = jTable5.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите заказ для изменения");
            return;
        }

        String zakazId = getValueAsString(jTable5, selectedRow, 0);
        String data = jTextField15.getText().trim();
        String tsenaRabotyStr = jTextField16.getText().trim();
        String klientName = (String) jComboBox18.getSelectedItem();
        String izdelieName = (String) jComboBox20.getSelectedItem();
        String statusName = (String) jComboBox17.getSelectedItem();
        String shveyaName = (String) jComboBox19.getSelectedItem();
        String skidkaProcentStr = (String) jComboBox21.getSelectedItem();

        if (data.isEmpty() || tsenaRabotyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Дата и Цена работы");
            return;
        }

        try {
            BigDecimal tsenaRaboty = new BigDecimal(tsenaRabotyStr);
            int klientId = getKlientIdByName(klientName);
            int izdelieId = getIzdelieIdByName(izdelieName);
            int statusId = getStatusRabotyIdByName(statusName);
            int shveyaId = getShveyaIdByName(shveyaName);
            int skidkaId = getSkidkaIdByProcent(skidkaProcentStr);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updateZakaznaposhiv";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("id", zakazId)
                    .add("idKlient", String.valueOf(klientId))
                    .add("idStatusRaboty", String.valueOf(statusId))
                    .add("Data", data)
                    .add("idShveya", String.valueOf(shveyaId))
                    .add("idIzdelie", String.valueOf(izdelieId))
                    .add("TsenaRabotyShvei", tsenaRaboty.toString())
                    .add("idSkidka", String.valueOf(skidkaId));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Заказ успешно обновлен");
                    clearZakazForm();
                    allZakaznaposhiv();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка обновления заказа");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления заказа: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateProdazhaTkani() {
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите продажу для изменения");
            return;
        }

        String prodazhaId = getValueAsString(jTable4, selectedRow, 0);
        String kolichestvoStr = jTextField5.getText().trim(); // количество
        String tsenaStr = jTextField14.getText().trim(); // цена
        String tkanName = (String) jComboBox15.getSelectedItem();
        String razmerStr = (String) jComboBox4.getSelectedItem();

        if (kolichestvoStr.isEmpty() || tsenaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните количество и цену");
            return;
        }

        try {
            // ИСПРАВЛЕНИЕ: используем правильные типы данных
            int kolichestvo = Integer.parseInt(kolichestvoStr); // количество - целое число
            BigDecimal tsena = new BigDecimal(tsenaStr); // цена - BigDecimal с десятичными знаками

            int tkanId = getTkanIdByName(tkanName);
            int razmerId = getRazmerIdByString(razmerStr);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updateProdazhatkani";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("id", prodazhaId)
                    .add("idProdavets", String.valueOf(currentProdavecId))
                    .add("data", currentDate)
                    .add("idTkan", String.valueOf(tkanId))
                    .add("Kolichestvo", String.valueOf(kolichestvo))
                    .add("tsenaZaRulon", tsena.toString()) // ИСПРАВЛЕНИЕ: передаем как строку BigDecimal
                    .add("idRazmer", String.valueOf(razmerId));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Продажа ткани успешно обновлена");
                    clearProdazhaTkanForm();
                    allProdazhatkani();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка обновления продажи ткани");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ошибка формата чисел:\n- Количество должно быть целым числом\n- Цена может содержать десятичные знаки");
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления продажи ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateProdazhaFurnitury() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите продажу для изменения");
            return;
        }

        String prodazhaId = getValueAsString(jTable3, selectedRow, 0);
        String data = jTextField4.getText().trim();
        String kolichestvoStr = jTextField5.getText().trim();
        String tsenaStr = jTextField14.getText().trim();
        String furnituraName = (String) jComboBox3.getSelectedItem();

        if (data.isEmpty() || kolichestvoStr.isEmpty() || tsenaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните все обязательные поля");
            return;
        }

        try {
            int kolichestvo = Integer.parseInt(kolichestvoStr);
            BigDecimal tsena = new BigDecimal(tsenaStr);
            int furnituraId = getFurnituraIdByName(furnituraName);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "updateProdazhafurnitury";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("id", prodazhaId)
                    .add("idProdavets", String.valueOf(currentProdavecId))
                    .add("data", data)
                    .add("idFurnitura", String.valueOf(furnituraId))
                    .add("Kolichestvo", String.valueOf(kolichestvo))
                    .add("tsenaZaYedinitsu", tsena.toString());

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Продажа фурнитуры успешно обновлена");
                    clearProdazhaFurnituraForm();
                    allProdazhafurnitury();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка обновления продажи фурнитуры");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка обновления продажи фурнитуры: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addTkan() {
        String artikul = jTextField1.getText().trim();
        String nazvanie = jTextField2.getText().trim();
        String kategoriya = jTextField3.getText().trim();
        String tsvet = (String) jComboBox1.getSelectedItem();
        String rastsvetka = (String) jComboBox2.getSelectedItem();
        boolean novinka = jCheckBox1.isSelected();

        if (artikul.isEmpty() || nazvanie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Артикул и Название");
            return;
        }

        try {
            int tsvetId = getTsvetTkaniIdByName(tsvet);
            int rastsvetkaId = getRastsvetkaIdByName(rastsvetka);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addTkan";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("artikul", artikul)
                    .add("nazvanie", nazvanie)
                    .add("kategoriya", kategoriya)
                    .add("idTsvet", String.valueOf(tsvetId))
                    .add("idRastsvetka", String.valueOf(rastsvetkaId))
                    .add("novinka", String.valueOf(novinka));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Ткань успешно добавлена");
                    clearTkanForm();
                    refreshTkanCaches();
                    allTkan();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка добавления ткани");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addFurnitura() {
        String artikul = jTextField12.getText().trim();
        String nazvanie = jTextField13.getText().trim();
        String tsvet = (String) jComboBox14.getSelectedItem();
        String edinitsa = (String) jComboBox13.getSelectedItem();
        boolean novinka = jCheckBox2.isSelected();

        if (artikul.isEmpty() || nazvanie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Артикул и Название");
            return;
        }

        try {
            int tsvetId = getTsvetFurnituryIdByName(tsvet);
            int edinitsaId = getEdinitsaIzmereniyaIdByName(edinitsa);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addFurnitura";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("artikul", artikul)
                    .add("nazvanie", nazvanie)
                    .add("idTsvet", String.valueOf(tsvetId))
                    .add("novinka", String.valueOf(novinka))
                    .add("idEdIzmereniya", String.valueOf(edinitsaId));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Фурнитура успешно добавлена");
                    clearFurnituraForm();
                    refreshFurnituraCaches();
                    allFurnitura();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка добавления фурнитуры");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления фурнитуры: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addProdazhaFurnitury() {
        String data = jTextField4.getText().trim();
        String kolichestvoStr = jTextField5.getText().trim();
        String tsenaStr = jTextField14.getText().trim();
        String furnituraName = (String) jComboBox3.getSelectedItem();

        if (data.isEmpty() || kolichestvoStr.isEmpty() || tsenaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните все обязательные поля");
            return;
        }

        try {
            int kolichestvo = Integer.parseInt(kolichestvoStr);
            BigDecimal tsena = new BigDecimal(tsenaStr);
            int furnituraId = getFurnituraIdByName(furnituraName);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addProdazhafurnitury";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("idProdavets", String.valueOf(currentProdavecId))
                    .add("data", data)
                    .add("idFurnitura", String.valueOf(furnituraId))
                    .add("Kolichestvo", String.valueOf(kolichestvo))
                    .add("tsenaZaYedinitsu", tsena.toString());

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Продажа фурнитуры успешно добавлена");
                    clearProdazhaFurnituraForm();
                    allProdazhafurnitury();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка добавления продажи фурнитуры");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления продажи фурнитуры: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addProdazhaTkani() {
        String data = jTextField4.getText().trim(); // дата
        String kolichestvoStr = jTextField5.getText().trim(); // количество
        String tsenaStr = jTextField14.getText().trim(); // цена
        String tkanName = (String) jComboBox15.getSelectedItem();
        String razmerStr = (String) jComboBox4.getSelectedItem();

        if (data.isEmpty() || kolichestvoStr.isEmpty() || tsenaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните все обязательные поля");
            return;
        }

        try {
            // ИСПРАВЛЕНИЕ: правильные типы данных
            int kolichestvo = Integer.parseInt(kolichestvoStr); // количество - целое число
            BigDecimal tsena = new BigDecimal(tsenaStr); // цена - BigDecimal

            int tkanId = getTkanIdByName(tkanName);
            int razmerId = getRazmerIdByString(razmerStr);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addProdazhatkani";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("idProdavets", String.valueOf(currentProdavecId))
                    .add("data", data)
                    .add("idTkan", String.valueOf(tkanId))
                    .add("Kolichestvo", String.valueOf(kolichestvo))
                    .add("tsenaZaRulon", tsena.toString()) // ИСПРАВЛЕНИЕ: передаем как строку BigDecimal
                    .add("idRazmer", String.valueOf(razmerId));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Продажа ткани успешно добавлена");
                    clearProdazhaTkanForm();
                    allProdazhatkani();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка добавления продажи ткани");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ошибка формата чисел:\n- Количество должно быть целым числом\n- Цена может содержать десятичные знаки");
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления продажи ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addZakaz() {
        String data = jTextField15.getText().trim();
        String tsenaRabotyStr = jTextField16.getText().trim();
        String klientName = (String) jComboBox18.getSelectedItem();
        String izdelieName = (String) jComboBox20.getSelectedItem();
        String statusName = (String) jComboBox17.getSelectedItem();
        String shveyaName = (String) jComboBox19.getSelectedItem();
        String skidkaProcentStr = (String) jComboBox21.getSelectedItem();

        if (data.isEmpty() || tsenaRabotyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Заполните обязательные поля: Дата и Цена работы");
            return;
        }

        try {
            BigDecimal tsenaRaboty = new BigDecimal(tsenaRabotyStr);
            int klientId = getKlientIdByName(klientName);
            int izdelieId = getIzdelieIdByName(izdelieName);
            int statusId = getStatusRabotyIdByName(statusName);
            int shveyaId = getShveyaIdByName(shveyaName);

            // Получаем ID скидки из процента
            int skidkaId = getSkidkaIdByProcent(skidkaProcentStr);

            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "addZakaznaposhiv";

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("idKlient", String.valueOf(klientId))
                    .add("idStatusRaboty", String.valueOf(statusId))
                    .add("Data", data)
                    .add("idShveya", String.valueOf(shveyaId))
                    .add("idIzdelie", String.valueOf(izdelieId))
                    .add("TsenaRabotyShvei", tsenaRaboty.toString())
                    .add("idSkidka", String.valueOf(skidkaId));

            RequestBody formBody = formBuilder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Заказ успешно добавлен");
                    clearZakazForm();
                    allZakaznaposhiv();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка добавления заказа");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка добавления заказа: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int getSkidkaIdByProcent(String skidkaProcentStr) throws Exception {
        // Убираем символ % и преобразуем в число
        String procentStr = skidkaProcentStr.replace("%", "").trim();
        int procent = Integer.parseInt(procentStr);

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "allSkidka";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseBody);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject skidka = jsonArray.getJSONObject(i);
                        if (getIntValue(skidka, "procent") == procent) {
                            return getIntValue(skidka, "id");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка поиска скидки: " + e.getMessage());
        }

        // Если не нашли, возвращаем скидку 0%
        return 1; // предполагая, что ID=1 соответствует 0%
    }

    private void deleteTkan() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите ткань для удаления");
            return;
        }

        String idTkan = getValueAsString(jTable1, selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить ткань?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "deleteTkan";

            RequestBody formBody = new FormBody.Builder()
                    .add("id", idTkan)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Ткань успешно удалена");
                    clearTkanForm();
                    allTkan(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка удаления ткани");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteFurnitura() {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите фурнитуру для удаления");
            return;
        }

        String idFurnitura = getValueAsString(jTable2, selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить фурнитуру?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "deleteFurnitura";

            RequestBody formBody = new FormBody.Builder()
                    .add("id", idFurnitura)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Фурнитура успешно удалена");
                    clearFurnituraForm();
                    allFurnitura(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка удаления фурнитуры");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления фурнитуры: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteProdazhaFurnitury() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите продажу для удаления");
            return;
        }

        String idProdazha = getValueAsString(jTable3, selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить продажу?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "deleteProdazhafurnitury";

            RequestBody formBody = new FormBody.Builder()
                    .add("id", idProdazha)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Продажа успешно удалена");
                    clearProdazhaFurnituraForm();
                    allProdazhafurnitury(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка удаления продажи");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления продажи: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteProdazhaTkani() {
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите продажу для удаления");
            return;
        }

        String idProdazha = getValueAsString(jTable4, selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить продажу?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "deleteProdazhatkani";

            RequestBody formBody = new FormBody.Builder()
                    .add("id", idProdazha)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Продажа ткани успешно удалена");
                    clearProdazhaTkanForm();
                    allProdazhatkani(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка удаления продажи ткани");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления продажи ткани: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteZakaz() {
        int selectedRow = jTable5.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите заказ для удаления");
            return;
        }

        String idZakaz = getValueAsString(jTable5, selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить заказ?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "deleteZakaznaposhiv";

            RequestBody formBody = new FormBody.Builder()
                    .add("id", idZakaz)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(this, "Заказ успешно удален");
                    clearZakazForm();
                    allZakaznaposhiv(); // Обновляем таблицу
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка удаления заказа");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка удаления заказа: " + e.getMessage());
            e.printStackTrace();
        }
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
            java.util.logging.Logger.getLogger(prodavec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(prodavec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(prodavec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(prodavec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new prodavec().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox21;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
