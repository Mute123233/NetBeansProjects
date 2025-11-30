
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mavenproject1.mavenproject1.controller;

import com.mavenproject1.mavenproject1.entity.Abonent;
import com.mavenproject1.mavenproject1.entity.AbonentServices;
import com.mavenproject1.mavenproject1.entity.ApplicationType;
import com.mavenproject1.mavenproject1.entity.Applications;
import com.mavenproject1.mavenproject1.entity.Equipment;
import com.mavenproject1.mavenproject1.entity.HomePhone;
import com.mavenproject1.mavenproject1.entity.Internet;
import com.mavenproject1.mavenproject1.entity.Operator;
import com.mavenproject1.mavenproject1.entity.Remont;
import com.mavenproject1.mavenproject1.entity.Services;
import com.mavenproject1.mavenproject1.entity.Status;
import com.mavenproject1.mavenproject1.entity.Supervisor;
import com.mavenproject1.mavenproject1.entity.Technician;
import com.mavenproject1.mavenproject1.entity.Users;
import com.mavenproject1.mavenproject1.repository.AbonentInterface;
import com.mavenproject1.mavenproject1.repository.AbonentServicesInterface;
import com.mavenproject1.mavenproject1.repository.ApplicationInterface;
import com.mavenproject1.mavenproject1.repository.ApplicationTypeInterface;
import com.mavenproject1.mavenproject1.repository.EquipmentInterface;
import com.mavenproject1.mavenproject1.repository.HomePhoneInterface;
import com.mavenproject1.mavenproject1.repository.InternetInterface;
import com.mavenproject1.mavenproject1.repository.OperatorInterface;
import com.mavenproject1.mavenproject1.repository.RemontInterface;
import com.mavenproject1.mavenproject1.repository.ServicesInterface;
import com.mavenproject1.mavenproject1.repository.StatusInterface;
import com.mavenproject1.mavenproject1.repository.SupervisorInterface;
import com.mavenproject1.mavenproject1.repository.TechnicianInterface;
import com.mavenproject1.mavenproject1.repository.UsersRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 8277
 */
@RestController
@RequestMapping("/mavenproject1")
public class MainController {

    @Autowired
    private UsersRepository UsersRepository;
    @Autowired
    private AbonentInterface AbonentInterface;
    @Autowired
    private AbonentServicesInterface AbonentServicesInterface;
    @Autowired
    private ApplicationInterface ApplicationInterface;
    @Autowired
    private ApplicationTypeInterface ApplicationTypeInterface;
    @Autowired
    private EquipmentInterface EquipmentInterface;
    @Autowired
    private HomePhoneInterface HomePhoneInterface;
    @Autowired
    private InternetInterface InternetInterface;
    @Autowired
    private OperatorInterface OperatorInterface;
    @Autowired
    private RemontInterface RemontInterface;
    @Autowired
    private ServicesInterface ServicesInterface;
    @Autowired
    private StatusInterface StatusInterface;
    @Autowired
    private SupervisorInterface SupervisorInterface;
    @Autowired
    private TechnicianInterface TechnicianInterface;

    @GetMapping("/tests")
    public String test1() {
        return "working fine";
    }

    @PostMapping("/updateAbonentForOperator")
    public @ResponseBody
    String updateAbonentForOperator(
            @RequestParam(name = "idAbonent") String idAbonent,
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic", required = false) String Patronymic,
            @RequestParam(name = "DownloadDate") String DownloadDate,
            @RequestParam(name = "AvailabilityBlocker") Integer AvailabilityBlocker,
            @RequestParam(name = "Debt", required = false) String Debt,
            @RequestParam(name = "Notes", required = false) String Notes,
            @RequestParam(name = "operatorLogin") String operatorLogin) {

        try {
            Abonent abonent = AbonentInterface.findById(Integer.parseInt(idAbonent))
                    .orElseThrow(() -> new RuntimeException("Абонент не найден"));

            abonent.setSurname(Surname);
            abonent.setName(Name);
            abonent.setPatronymic(Patronymic != null ? Patronymic : "");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(DownloadDate);
            abonent.setDownloadDate(date);

            abonent.setAvailabilityBlocker(AvailabilityBlocker != null ? AvailabilityBlocker : 0);
            abonent.setDebt(Debt != null ? Debt : "0");
            abonent.setNotes(Notes != null ? Notes : "");

            // Находим оператора по логину и убеждаемся, что абонент остается у того же оператора
            Users operatorUser = UsersRepository.findByLogin(operatorLogin);
            if (operatorUser == null) {
                return "ERROR: Operator not found";
            }

            Optional<Operator> operatorOpt = OperatorInterface.findByIdUser(operatorUser);
            if (!operatorOpt.isPresent()) {
                return "ERROR: Operator role not found";
            }

            Operator operator = operatorOpt.get();

            // Проверяем, принадлежит ли абонент этому оператору
            if (!abonent.getIdOperator().getIdOperator().equals(operator.getIdOperator())) {
                return "ERROR: Abonent does not belong to this operator";
            }

            AbonentInterface.save(abonent);
            return "SUCCESS";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR: " + ex.getMessage();
        }
    }

    @PostMapping("/addAbonentForOperator")
    public @ResponseBody
    String addAbonentForOperator(
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic", required = false) String Patronymic,
            @RequestParam(name = "DownloadDate") String DownloadDate,
            @RequestParam(name = "AvailabilityBlocker") Integer AvailabilityBlocker,
            @RequestParam(name = "Debt", required = false) String Debt,
            @RequestParam(name = "Notes", required = false) String Notes,
            @RequestParam(name = "operatorLogin") String operatorLogin) {

        try {
            Abonent abonent = new Abonent();
            abonent.setSurname(Surname);
            abonent.setName(Name);
            abonent.setPatronymic(Patronymic != null ? Patronymic : "");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(DownloadDate);
            abonent.setDownloadDate(date);

            abonent.setAvailabilityBlocker(AvailabilityBlocker != null ? AvailabilityBlocker : 0);
            abonent.setDebt(Debt != null ? Debt : "0");
            abonent.setNotes(Notes != null ? Notes : "");

            // Находим оператора по логину
            Users operatorUser = UsersRepository.findByLogin(operatorLogin);
            if (operatorUser == null) {
                return "ERROR: Operator not found";
            }

            Optional<Operator> operatorOpt = OperatorInterface.findByIdUser(operatorUser);
            if (!operatorOpt.isPresent()) {
                return "ERROR: Operator role not found";
            }

            Operator operator = operatorOpt.get();
            abonent.setIdOperator(operator);

            AbonentInterface.save(abonent);
            return "SUCCESS";

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "ERROR: Invalid date format";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR: " + ex.getMessage();
        }
    }
// SQL запросы для оператора

    @GetMapping("/operatorQueries")
    public @ResponseBody
    Map<String, Object> getOperatorQueries() {
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. Запрос из одной таблицы: Все абоненты с операторами
            List<Object[]> singleTable = AbonentInterface.findAllAbonentsWithOperator();
            result.put("singleTable", formatAbonentsWithOperators(singleTable));

            // 2. Запрос из двух таблиц: Абоненты с услугами
            List<Object[]> twoTables1 = AbonentServicesInterface.findAbonentsWithServices();
            result.put("twoTables1", formatAbonentsWithServices(twoTables1));

            // 3. Запрос из двух таблиц: Заявки с типами
            List<Object[]> twoTables2 = ApplicationInterface.findApplicationsWithTypes();
            result.put("twoTables2", formatApplicationsWithTypes(twoTables2));

            // 4. Запрос из двух таблиц: Статистика по блокировкам
            List<Object[]> twoTables3 = AbonentInterface.findBlockedStatistics();
            result.put("twoTables3", formatBlockedStatistics(twoTables3));

            // 5. Запрос из трех таблиц: Полная информация об абонентах
            List<Object[]> threeTables = AbonentInterface.findFullAbonentInfo();
            result.put("threeTables", formatFullAbonentInfo(threeTables));

        } catch (Exception e) {
            result.put("error", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/adminQueries")
    public @ResponseBody
    Map<String, Object> getAdminQueries() {
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. Запрос из одной таблицы: Все пользователи
            List<Object[]> singleTable = UsersRepository.findAllUsersBasic();
            result.put("singleTable", formatUsersBasic(singleTable));

            // 2. Запрос из двух таблиц: Пользователи с ролями
            List<Object[]> twoTables1 = UsersRepository.findUsersWithRoles();
            result.put("twoTables1", formatUsersWithRoles(twoTables1));

            // 3. Запрос из двух таблиц: Статистика по статусам
            List<Object[]> twoTables2 = UsersRepository.findUserStatusStatistics();
            result.put("twoTables2", formatUserStatusStatistics(twoTables2));

            // 4. Запрос из двух таблиц: Операторы и абоненты
            List<Object[]> twoTables3 = OperatorInterface.findOperatorsWithAbonentCount();
            result.put("twoTables3", formatOperatorsWithAbonentCount(twoTables3));

            // 5. Запрос из трех таблиц: Полная иерархия
            List<Object[]> threeTables = UsersRepository.findFullUserHierarchy();
            result.put("threeTables", formatFullUserHierarchy(threeTables));

        } catch (Exception e) {
            result.put("error", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

// Методы форматирования для оператора
    private List<Map<String, Object>> formatAbonentsWithOperators(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", row[0]);
            item.put("abonent", row[1] + " " + row[2] + " " + row[3]);
            item.put("downloadDate", row[4]);
            item.put("blocked", row[5].equals(1) ? "Да" : "Нет");
            item.put("debt", row[6]);
            item.put("operator", row[7] + " " + row[8]);
            result.add(item);
        }
        return result;
    }

    private List<Map<String, Object>> formatAbonentsWithServices(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("abonent", row[0] + " " + row[1] + " " + row[2]);
            item.put("service", row[3]);
            item.put("startDate", row[4]);
            result.add(item);
        }
        return result;
    }

    private List<Map<String, Object>> formatApplicationsWithTypes(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", row[0]);
            item.put("abonent", row[1] + " " + row[2] + " " + row[3]);
            item.put("type", row[4]);
            item.put("date", row[5]);
            result.add(item);
        }
        return result;
    }

  // В методах форматирования убедитесь, что все названия на русском
private List<Map<String, Object>> formatBlockedStatistics(List<Object[]> data) {
    List<Map<String, Object>> result = new ArrayList<>();
    for (Object[] row : data) {
        Map<String, Object> item = new HashMap<>();
        String status = row[0].equals(1) ? "Заблокированы" : "Активны";
        item.put("Статус", status);
        item.put("Количество", row[1]);
        item.put("Общая задолженность", row[2] + " руб.");
        result.add(item);
    }
    return result;
}

    private List<Map<String, Object>> formatFullAbonentInfo(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("abonent", row[0] + " " + row[1] + " " + row[2]);
            item.put("service", row[3]);
            item.put("operator", row[4]);
            result.add(item);
        }
        return result;
    }

// Методы форматирования для администратора
    private List<Map<String, Object>> formatUsersBasic(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", row[0]);
            item.put("user", row[1] + " " + row[2] + " " + row[3]);
            item.put("login", row[4]);
            item.put("phone", row[5]);
            item.put("status", row[6]);
            result.add(item);
        }
        return result;
    }

    private List<Map<String, Object>> formatUsersWithRoles(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("user", row[0] + " " + row[1]);
            item.put("login", row[2]);
            item.put("role", row[3]);
            result.add(item);
        }
        return result;
    }

    private List<Map<String, Object>> formatUserStatusStatistics(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("status", row[0]);
            item.put("count", row[1]);
            result.add(item);
        }
        return result;
    }

    private List<Map<String, Object>> formatOperatorsWithAbonentCount(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("operator", row[0] + " " + row[1]);
            item.put("abonentCount", row[2]);
            result.add(item);
        }
        return result;
    }

    private List<Map<String, Object>> formatFullUserHierarchy(List<Object[]> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> item = new HashMap<>();
            item.put("user", row[0] + " " + row[1]);
            item.put("login", row[2]);
            item.put("phone", row[3]);
            item.put("status", row[4]);
            item.put("role", row[5]);
            item.put("abonentCount", row[6]);
            result.add(item);
        }
        return result;
    }

    @GetMapping("/getOperatorAbonents")
    public @ResponseBody
    List<Map<String, Object>> getOperatorAbonents(@RequestParam(name = "operatorLogin") String operatorLogin) {
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("=== ПОИСК АБОНЕНТОВ ДЛЯ ОПЕРАТОРА ===");
            System.out.println("Логин оператора: " + operatorLogin);

            // Находим оператора по логину
            Users operatorUser = UsersRepository.findByLogin(operatorLogin);
            if (operatorUser == null) {
                System.out.println("❌ ОПЕРАТОР НЕ НАЙДЕН: " + operatorLogin);
                return result;
            }
            System.out.println("✅ Найден пользователь: " + operatorUser.getSurname() + " " + operatorUser.getName());

            // Находим ID оператора
            Optional<Operator> operatorOpt = OperatorInterface.findByIdUser(operatorUser);
            if (!operatorOpt.isPresent()) {
                System.out.println("❌ РОЛЬ ОПЕРАТОРА НЕ НАЙДЕНА для пользователя: " + operatorLogin);
                return result;
            }

            Operator operator = operatorOpt.get();
            System.out.println("✅ Найден оператор ID: " + operator.getIdOperator());

            // Получаем всех абонентов этого оператора
            List<Abonent> abonents = AbonentInterface.findByIdOperator(operator);
            System.out.println("📊 Найдено абонентов в базе: " + abonents.size());

            for (Abonent abonent : abonents) {
                Map<String, Object> abonentData = new HashMap<>();
                abonentData.put("idAbonent", abonent.getIdAbonent());
                abonentData.put("surname", abonent.getSurname());
                abonentData.put("name", abonent.getName());
                abonentData.put("patronymic", abonent.getPatronymic());

                if (abonent.getDownloadDate() != null) {
                    abonentData.put("downloadDate", format.format(abonent.getDownloadDate()));
                } else {
                    abonentData.put("downloadDate", "Не указана");
                }

                abonentData.put("availabilityBlocker", abonent.getAvailabilityBlocker());
                abonentData.put("debt", abonent.getDebt() != null ? abonent.getDebt() : "0");
                abonentData.put("notes", abonent.getNotes() != null ? abonent.getNotes() : "");

                // ФИО оператора
                String operatorFIO = operatorUser.getSurname() + " "
                        + operatorUser.getName() + " "
                        + operatorUser.getPatronymic();
                abonentData.put("operatorFIO", operatorFIO);
                abonentData.put("operatorLogin", operatorLogin);

                result.add(abonentData);
                System.out.println("✅ Добавлен абонент: " + abonent.getSurname() + " " + abonent.getName());
            }

            System.out.println("=== ВСЕГО АБОНЕНТОВ В ОТВЕТЕ: " + result.size() + " ===");
            return result;

        } catch (Exception e) {
            System.out.println("❌ ОШИБКА: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @PostMapping("/updateAbonentWorking")
    public @ResponseBody
    String updateAbonentWorking(
            @RequestParam(name = "idAbonent") String idAbonent,
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic", required = false) String Patronymic,
            @RequestParam(name = "DownloadDate") String DownloadDate,
            @RequestParam(name = "AvailabilityBlocker") Integer AvailabilityBlocker,
            @RequestParam(name = "Debt", required = false) String Debt,
            @RequestParam(name = "Notes", required = false) String Notes,
            @RequestParam(name = "idOperator") String idOperator) {

        try {
            Abonent abonent = AbonentInterface.findById(Integer.parseInt(idAbonent))
                    .orElseThrow(() -> new RuntimeException("Абонент не найден"));

            abonent.setSurname(Surname);
            abonent.setName(Name);
            abonent.setPatronymic(Patronymic != null ? Patronymic : "");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(DownloadDate);
            abonent.setDownloadDate(date);

            abonent.setAvailabilityBlocker(AvailabilityBlocker != null ? AvailabilityBlocker : 0);
            abonent.setDebt(Debt != null ? Debt : "0");
            abonent.setNotes(Notes != null ? Notes : "");

            // Находим оператора
            try {
                Operator operator = OperatorInterface.findById(Integer.parseInt(idOperator))
                        .orElseThrow(() -> new RuntimeException("Оператор не найден"));
                abonent.setIdOperator(operator);
            } catch (Exception e) {
                // Если оператор не найден, используем первого доступного
                Optional<Operator> firstOperator = OperatorInterface.findAll().stream().findFirst();
                if (firstOperator.isPresent()) {
                    abonent.setIdOperator(firstOperator.get());
                }
            }

            AbonentInterface.save(abonent);
            return "SUCCESS";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR: " + ex.getMessage();
        }
    }

    @GetMapping("/testAbonents")
    public @ResponseBody
    List<Map<String, Object>> getTestAbonents() {
        List<Map<String, Object>> result = new ArrayList<>();

        // Тестовые данные
        Map<String, Object> abonent1 = new HashMap<>();
        abonent1.put("idAbonent", 1);
        abonent1.put("surname", "Иванов");
        abonent1.put("name", "Иван");
        abonent1.put("patronymic", "Иванович");
        abonent1.put("downloadDate", "2024-01-15");
        abonent1.put("availabilityBlocker", 0);
        abonent1.put("debt", "500");
        abonent1.put("notes", "Тестовый абонент");
        abonent1.put("operatorFIO", "Не назначен");
        result.add(abonent1);

        Map<String, Object> abonent2 = new HashMap<>();
        abonent2.put("idAbonent", 2);
        abonent2.put("surname", "Петров");
        abonent2.put("name", "Петр");
        abonent2.put("patronymic", "Петрович");
        abonent2.put("downloadDate", "2024-02-20");
        abonent2.put("availabilityBlocker", 1);
        abonent2.put("debt", "0");
        abonent2.put("notes", "Заблокирован");
        abonent2.put("operatorFIO", "Федоров Д.И.");
        result.add(abonent2);

        return result;
    }

// Метод для удаления пользователя
    @PostMapping("/deleteUserById")
    public @ResponseBody
    String deleteUserById(@RequestParam(name = "userId") Integer userId) {
        try {
            // Сначала удаляем роли пользователя
            Users user = UsersRepository.findById(userId).orElse(null);
            if (user != null) {
                OperatorInterface.deleteByIdUser(user);
                SupervisorInterface.deleteByIdUser(user);
                TechnicianInterface.deleteByIdUser(user);
            }

            // Затем удаляем самого пользователя
            UsersRepository.deleteById(userId);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    @PostMapping("/updateUserProfile")
    public @ResponseBody
    String updateUserProfile(
            @RequestParam(name = "currentLogin") String currentLogin,
            @RequestParam(name = "surname", required = false) String surname,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "patronymic", required = false) String patronymic,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "newLogin", required = false) String newLogin) {

        try {
            // Находим пользователя по текущему логину
            Users user = UsersRepository.findByLogin(currentLogin);
            if (user == null) {
                return "USER_NOT_FOUND";
            }

            // Обновляем основные данные
            if (surname != null && !surname.trim().isEmpty()) {
                user.setSurname(surname.trim());
            }
            if (name != null && !name.trim().isEmpty()) {
                user.setName(name.trim());
            }
            if (patronymic != null) {
                user.setPatronymic(patronymic.trim());
            }
            if (phone != null) {
                user.setPhone(phone.trim());
            }

            // Обновляем логин, если указан новый
            if (newLogin != null && !newLogin.trim().isEmpty() && !newLogin.equals(currentLogin)) {
                // Проверяем, не занят ли новый логин другим пользователем
                Users existingUser = UsersRepository.findByLogin(newLogin);
                if (existingUser != null && !existingUser.getIdUser().equals(user.getIdUser())) {
                    return "LOGIN_ALREADY_EXISTS";
                }
                user.setLogin(newLogin.trim());
            }

            UsersRepository.save(user);
            return "SUCCESS";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }
// Метод для обновления пользователя

    @PostMapping("/updateUserAdmin")
    public @ResponseBody
    String updateUserAdmin(
            @RequestParam(name = "idUser") Integer idUser,
            @RequestParam(name = "surname") String surname,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "patronymic", required = false) String patronymic,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "snils", required = false) String snils,
            @RequestParam(name = "passportData", required = false) String passportData,
            @RequestParam(name = "birthDate") String birthDate,
            @RequestParam(name = "employmentDate") String employmentDate,
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "role") String role) {

        try {
            Users user = UsersRepository.findById(idUser).orElse(null);
            if (user == null) {
                return "USER_NOT_FOUND";
            }

            // Обновляем основные данные
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic != null ? patronymic : "");
            user.setPhone(phone != null ? phone : "");
            user.setSnils(snils != null ? snils : "");
            user.setPassportData(passportData != null ? passportData : "");
            user.setLogin(login);

            // Обновляем пароль только если он указан
            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }

            user.setStatus(status);

            // Обновляем даты
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDateParsed = format.parse(birthDate);
            user.setBirthDate(birthDateParsed);

            Date employmentDateParsed = format.parse(employmentDate);
            user.setEmploymentDate(employmentDateParsed);

            UsersRepository.save(user);

            // Обновляем роль
            updateUserRole(user, role);

            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

// Вспомогательный метод для обновления роли
    private void updateUserRole(Users user, String role) {
        // Удаляем все текущие роли
        OperatorInterface.deleteByIdUser(user);
        SupervisorInterface.deleteByIdUser(user);
        TechnicianInterface.deleteByIdUser(user);

        // Назначаем новую роль
        switch (role) {
            case "Оператор":
                Operator operator = new Operator();
                operator.setIdUser(user);
                OperatorInterface.save(operator);
                break;
            case "Супервизор":
                Supervisor supervisor = new Supervisor();
                supervisor.setIdUser(user);
                SupervisorInterface.save(supervisor);
                break;
            case "Технический специалист":
                Technician technician = new Technician();
                technician.setIdUser(user);
                TechnicianInterface.save(technician);
                break;
            // Для "Нет роли" ничего не делаем
        }
    }

    @PostMapping("/addUserrrr")
    public @ResponseBody
    String addUserrrr(
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic", required = false) String Patronymic,
            @RequestParam(name = "BirthDate") String BirthDate,
            @RequestParam(name = "EmploymentDate") String EmploymentDate,
            @RequestParam(name = "Login") String Login,
            @RequestParam(name = "Password") String Password,
            @RequestParam(name = "PassportData", required = false) String PassportData,
            @RequestParam(name = "SNILS", required = false) String SNILS,
            @RequestParam(name = "Phone", required = false) String Phone,
            @RequestParam(name = "PhotoEquipment", required = false) String PhotoEquipment,
            @RequestParam(name = "role", required = false) String role) {

        try {
            // Проверяем, не существует ли уже пользователь с таким логином
            if (UsersRepository.findByLogin(Login) != null) {
                return "USER_EXISTS";
            }

            Users users = new Users();
            users.setSurname(Surname);
            users.setName(Name);
            users.setPatronymic(Patronymic != null ? Patronymic : "");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(BirthDate);
            users.setBirthDate(date);

            Date date2 = format.parse(EmploymentDate);
            users.setEmploymentDate(date2);

            users.setLogin(Login);
            users.setPassword(Password);
            users.setPassportData(PassportData != null ? PassportData : "");
            users.setSnils(SNILS != null ? SNILS : "");
            users.setPhone(Phone != null ? Phone : "");
            users.setPhotoEquipment(PhotoEquipment != null ? PhotoEquipment : "");

            // Установка статуса "Подтвержден"
            users.setStatus("Подтвержден");

            // Генерируем случайный код (5 цифр)
            Random random = new Random();
            String code = String.format("%05d", random.nextInt(100000));
            users.setCode(code);

            UsersRepository.save(users);

            // НАЗНАЧАЕМ РОЛЬ СРАЗУ ПОСЛЕ СОХРАНЕНИЯ ПОЛЬЗОВАТЕЛЯ
            if (role != null && !role.isEmpty() && !"Нет роли".equals(role)) {
                assignRoleToUser(users, role);
            }

            System.out.println("Пользователь зарегистрирован: " + Login + ", код: " + code + ", роль: " + role);
            return "SUCCESS";

        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Ошибка парсинга даты", ex);
            return "DATE_ERROR";
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Ошибка регистрации", ex);
            ex.printStackTrace();
            return "SERVER_ERROR: " + ex.getMessage();
        }
    }

    // Вспомогательный метод для назначения роли
    private void assignRoleToUser(Users user, String role) {
        try {
            switch (role) {
                case "Оператор":
                    Operator operator = new Operator();
                    operator.setIdUser(user);
                    OperatorInterface.save(operator);
                    break;
                case "Супервизор":
                    Supervisor supervisor = new Supervisor();
                    supervisor.setIdUser(user);
                    SupervisorInterface.save(supervisor);
                    break;
                case "Технический специалист":
                    Technician technician = new Technician();
                    technician.setIdUser(user);
                    TechnicianInterface.save(technician);
                    break;
            }
            System.out.println("Роль '" + role + "' назначена пользователю: " + user.getLogin());
        } catch (Exception e) {
            System.err.println("Ошибка назначения роли: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Остальные методы контроллера остаются без изменений...
    @PostMapping("/addAbonentWorking")
    public @ResponseBody
    String addAbonentWorking(
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic", required = false) String Patronymic,
            @RequestParam(name = "DownloadDate") String DownloadDate,
            @RequestParam(name = "AvailabilityBlocker") Integer AvailabilityBlocker,
            @RequestParam(name = "Debt", required = false) String Debt,
            @RequestParam(name = "Notes", required = false) String Notes,
            @RequestParam(name = "idOperator") String idOperator) {

        try {
            Abonent abonent = new Abonent();
            abonent.setSurname(Surname);
            abonent.setName(Name);
            abonent.setPatronymic(Patronymic != null ? Patronymic : "");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(DownloadDate);
            abonent.setDownloadDate(date);

            abonent.setAvailabilityBlocker(AvailabilityBlocker != null ? AvailabilityBlocker : 0);
            abonent.setDebt(Debt != null ? Debt : "0");
            abonent.setNotes(Notes != null ? Notes : "");

            // Находим оператора
            try {
                Operator operator = OperatorInterface.findById(Integer.parseInt(idOperator))
                        .orElseThrow(() -> new RuntimeException("Оператор не найден"));
                abonent.setIdOperator(operator);
            } catch (Exception e) {
                // Если оператор не найден, используем первого доступного
                Optional<Operator> firstOperator = OperatorInterface.findAll().stream().findFirst();
                if (firstOperator.isPresent()) {
                    abonent.setIdOperator(firstOperator.get());
                } else {
                    return "ERROR: No operators available";
                }
            }

            AbonentInterface.save(abonent);
            return "SUCCESS";

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "ERROR: Invalid date format";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR: " + ex.getMessage();
        }
    }

    @GetMapping("/getAllUsers")
    public @ResponseBody
    List<Map<String, Object>> getAllUsers() {
        try {
            List<Users> allUsers = UsersRepository.findAll();
            List<Map<String, Object>> usersWithRoles = new ArrayList<>();

            for (Users user : allUsers) {
                Map<String, Object> userData = new HashMap<>();
                userData.put("idUser", user.getIdUser());
                userData.put("surname", user.getSurname());
                userData.put("name", user.getName());
                userData.put("patronymic", user.getPatronymic());
                userData.put("login", user.getLogin());
                userData.put("phone", user.getPhone());
                userData.put("snils", user.getSnils());
                userData.put("passportData", user.getPassportData());
                userData.put("birthDate", user.getBirthDate());
                userData.put("employmentDate", user.getEmploymentDate());
                userData.put("status", user.getStatus());

                // Определяем роль
                String role = "Нет роли";
                if (OperatorInterface.existsByIdUser(user)) {
                    role = "Оператор";
                } else if (SupervisorInterface.existsByIdUser(user)) {
                    role = "Супервизор";
                } else if (TechnicianInterface.existsByIdUser(user)) {
                    role = "Технический специалист";
                }
                userData.put("role", role);

                usersWithRoles.add(userData);
            }

            return usersWithRoles;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @PostMapping("/getUserRole")
    public @ResponseBody
    String getUserRole(@RequestParam(name = "Login") String Login) {
        try {
            Users user = UsersRepository.findByLogin(Login);
            if (user == null) {
                return "USER_NOT_FOUND";
            }

            // Проверяем роли пользователя
            if (OperatorInterface.findByIdUser(user).isPresent()) {
                return "Оператор";
            } else if (SupervisorInterface.findByIdUser(user).isPresent()) {
                return "Супервизор";
            } else if (TechnicianInterface.findByIdUser(user).isPresent()) {
                return "Технический специалист";
            } else {
                return "NO_ROLE"; // Пользователь без роли
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @GetMapping("/getUsersWithoutRole")
    public @ResponseBody
    List<Map<String, Object>> getUsersWithoutRole() {
        try {
            List<Users> allUsers = UsersRepository.findAll();
            List<Map<String, Object>> usersWithoutRole = new ArrayList<>();

            for (Users user : allUsers) {
                // Проверяем, есть ли у пользователя роль
                boolean hasRole = OperatorInterface.existsByIdUser(user)
                        || SupervisorInterface.existsByIdUser(user)
                        || TechnicianInterface.existsByIdUser(user);

                if (!hasRole && "Подтвержден".equals(user.getStatus())) {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("idUser", user.getIdUser());
                    userData.put("surname", user.getSurname());
                    userData.put("name", user.getName());
                    userData.put("patronymic", user.getPatronymic());
                    userData.put("login", user.getLogin());
                    usersWithoutRole.add(userData);
                }
            }

            return usersWithoutRole;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @PostMapping("/assignRole")
    public @ResponseBody
    String assignRole(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "role") String role) {

        try {
            Users user = UsersRepository.findById(userId).orElse(null);
            if (user == null) {
                return "USER_NOT_FOUND";
            }

            // Удаляем существующие роли
            if (OperatorInterface.existsByIdUser(user)) {
                OperatorInterface.deleteByIdUser(user);
            }
            if (SupervisorInterface.existsByIdUser(user)) {
                SupervisorInterface.deleteByIdUser(user);
            }
            if (TechnicianInterface.existsByIdUser(user)) {
                TechnicianInterface.deleteByIdUser(user);
            }

            // Назначаем новую роль
            switch (role) {
                case "Оператор":
                    Operator operator = new Operator();
                    operator.setIdUser(user);
                    OperatorInterface.save(operator);
                    break;
                case "Супервизор":
                    Supervisor supervisor = new Supervisor();
                    supervisor.setIdUser(user);
                    SupervisorInterface.save(supervisor);
                    break;
                case "Технический специалист":
                    Technician technician = new Technician();
                    technician.setIdUser(user);
                    TechnicianInterface.save(technician);
                    break;
                default:
                    return "INVALID_ROLE";
            }

            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

// Метод для получения всех пользователей с их ролями
    @GetMapping("/getAllUsersWithRoles")
    public @ResponseBody
    List<Map<String, Object>> getAllUsersWithRoles() {
        try {
            List<Users> allUsers = (List<Users>) UsersRepository.findAll();
            List<Map<String, Object>> usersWithRoles = new ArrayList<>();

            for (Users user : allUsers) {
                Map<String, Object> userData = new HashMap<>();
                userData.put("idUser", user.getIdUser());
                userData.put("surname", user.getSurname());
                userData.put("name", user.getName());
                userData.put("patronymic", user.getPatronymic());
                userData.put("login", user.getLogin());
                userData.put("status", user.getStatus());

                // Определяем роль
                String role = "";
                if (OperatorInterface.findByIdUser(user).isPresent()) {
                    role = "Оператор";
                } else if (SupervisorInterface.findByIdUser(user).isPresent()) {
                    role = "Супервизор";
                } else if (TechnicianInterface.findByIdUser(user).isPresent()) {
                    role = "Технический специалист";
                } else {
                    role = "Нет роли";
                }
                userData.put("role", role);

                usersWithRoles.add(userData);
            }

            return usersWithRoles;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/all")
    public @ResponseBody
    Iterable<Users> allUsers() {
        return UsersRepository.findAll();
    }

    @GetMapping(path = "/registrationForm")
    public ModelAndView home() {
        return new ModelAndView("registrationForm"); // Убедитесь, что ваше представление называется formregistration.html
    }

    @PostMapping("/getAutarization")
    public @ResponseBody
    boolean getAutarization(@RequestParam(name = "Login") String Login, @RequestParam(name = "Password") String Password) {
        boolean temp = false;
        for (Users users : UsersRepository.findAll()) {
            if (users.getLogin().equals(Login) && users.getPassword().equals(Password)) {
                temp = true;
            }
        }
        return temp;
    }

    @PostMapping("/addUser")
    public @ResponseBody
    boolean addUser(
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic") String Patronymic,
            @RequestParam(name = "BirthDate") String BirthDate,
            @RequestParam(name = "EmploymentDate") String EmploymentDate,
            @RequestParam(name = "Login") String Login,
            @RequestParam(name = "Password") String Password,
            @RequestParam(name = "PassportData") String PassportData,
            @RequestParam(name = "SNILS") String SNILS,
            @RequestParam(name = "Phone") String Phone,
            @RequestParam(name = "PhotoEquipment") String PhotoEquipment,
            @RequestParam(name = "role", required = false) String role) {

        Users users = new Users();
        users.setSurname(Surname);
        users.setName(Name);
        users.setPatronymic(Patronymic);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(BirthDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        users.setBirthDate(date);
        Date date2 = null;
        try {
            date2 = format.parse(EmploymentDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        users.setEmploymentDate(date2);
        users.setLogin(Login);
        users.setPassword(Password);
        users.setPassportData(PassportData);
        users.setSnils(SNILS);
        users.setPhone(Phone);
        users.setPhotoEquipment(PhotoEquipment);

        // Генерация 5-значного кода
        String code = generateCode();
        users.setCode(code);

        // Установка статуса "Не подтвержден"
        users.setStatus("Не подтвержден");

        UsersRepository.save(users);

        if (role != null && !role.isEmpty()) {
            int userId = users.getIdUser();
            if ("Оператор".equals(role)) {
                Operator operator = new Operator();
                operator.setIdUser(users);
                OperatorInterface.save(operator);
            } else if ("Супервизор".equals(role)) {
                Supervisor supervisor = new Supervisor();
                supervisor.setIdUser(users);
                SupervisorInterface.save(supervisor);
            } else if ("Технический специалист".equals(role)) {
                Technician technician = new Technician();
                technician.setIdUser(users);
                TechnicianInterface.save(technician);
            }
        }
        return true;
    }

    @PostMapping("/addUserrr")
    public @ResponseBody
    boolean addUserrr(
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic") String Patronymic,
            @RequestParam(name = "BirthDate") String BirthDate,
            @RequestParam(name = "EmploymentDate") String EmploymentDate,
            @RequestParam(name = "Login") String Login,
            @RequestParam(name = "Password") String Password,
            @RequestParam(name = "PassportData") String PassportData,
            @RequestParam(name = "SNILS") String SNILS,
            @RequestParam(name = "Phone") String Phone,
            @RequestParam(name = "PhotoEquipment") String PhotoEquipment,
            @RequestParam(name = "role", required = false) String role) {

        Users users = new Users();
        users.setSurname(Surname);
        users.setName(Name);
        users.setPatronymic(Patronymic);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(BirthDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        users.setBirthDate(date);
        Date date2 = null;
        try {
            date2 = format.parse(EmploymentDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        users.setEmploymentDate(date2);
        users.setLogin(Login);
        users.setPassword(Password);
        users.setPassportData(PassportData);
        users.setSnils(SNILS);
        users.setPhone(Phone);
        users.setPhotoEquipment(PhotoEquipment);

        // Установка статуса "Подтвержден"
        users.setStatus("Подтвержден");

        // Установка code в null
        users.setCode(null);

        UsersRepository.save(users);

        if (role != null && !role.isEmpty()) {
            int userId = users.getIdUser();
            if ("Оператор".equals(role)) {
                Operator operator = new Operator();
                operator.setIdUser(users);
                OperatorInterface.save(operator);
            } else if ("Супервизор".equals(role)) {
                Supervisor supervisor = new Supervisor();
                supervisor.setIdUser(users);
                SupervisorInterface.save(supervisor);
            } else if ("Технический специалист".equals(role)) {
                Technician technician = new Technician();
                technician.setIdUser(users);
                TechnicianInterface.save(technician);
            }
        }
        return true;
    }

    @PostMapping("/addUserr")
    public @ResponseBody
    boolean addUserr(
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic") String Patronymic,
            @RequestParam(name = "BirthDate") String BirthDate,
            @RequestParam(name = "EmploymentDate") String EmploymentDate,
            @RequestParam(name = "Login") String Login,
            @RequestParam(name = "Password") String Password,
            @RequestParam(name = "PassportData") String PassportData,
            @RequestParam(name = "SNILS") String SNILS,
            @RequestParam(name = "Phone") String Phone,
            @RequestParam(name = "PhotoEquipment") MultipartFile photoEquipment,
            @RequestParam(name = "role", required = false) String role) {

        // Создание объекта пользователя
        Users users = new Users();
        users.setSurname(Surname);
        users.setName(Name);
        users.setPatronymic(Patronymic);

        // Обработка даты рождения
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(BirthDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Обработайте ошибку
        }
        users.setBirthDate(date);

        // Обработка даты трудоустройства
        Date employmentDate;
        try {
            employmentDate = format.parse(EmploymentDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Обработайте ошибку
        }
        users.setEmploymentDate(employmentDate);

        // Установка других свойств
        users.setLogin(Login);
        users.setPassword(Password);
        users.setPassportData(PassportData);
        users.setSnils(SNILS);
        users.setPhone(Phone);

        // Получение имени файла без его сохранения
        if (photoEquipment != null && !photoEquipment.isEmpty()) {
            // Получение имени файла
            String filename = photoEquipment.getOriginalFilename();
            users.setPhotoEquipment(filename); // Сохраните имя файла в базе данных, если это нужно
        } else {
            users.setPhotoEquipment(null); // Обработка случая, когда файл отсутствует
        }

        // Генерация 5-значного кода
        String code = generateCode();
        users.setCode(code);
        users.setStatus("Не подтвержден");

        // Сохранение пользователя в базе данных
        UsersRepository.save(users);

        if (role != null && !role.isEmpty()) {
            // Логика для работы с ролью
        }

        return true; // Вернуть true при успешном добавлении
    }

    private String generateCode() {
        Random random = new Random();
        int code = 10000 + random.nextInt(90000); // Генерируем число от 10000 до 99999
        return String.valueOf(code);
    }

    @PostMapping("/deleteUser")
    public @ResponseBody
    boolean deleteUser(@RequestParam(name = "idUser") String idUser) {
        UsersRepository.deleteById(Integer.parseInt(idUser));
        return true;
    }

    @PostMapping("/updateUser")
    public @ResponseBody
    boolean updateUser(
            @RequestParam(name = "idUser") String idUser,
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic") String Patronymic,
            @RequestParam(name = "BirthDate") String BirthDate,
            @RequestParam(name = "EmploymentDate") String EmploymentDate,
            @RequestParam(name = "Login") String Login,
            @RequestParam(name = "Password") String Password,
            @RequestParam(name = "PassportData") String PassportData,
            @RequestParam(name = "PhotoEquipment", required = false) String PhotoEquipment,
            @RequestParam(name = "role") String role,
            @RequestParam(name = "oldRole") String oldRole,
            @RequestParam(name = "status") String status
    ) {
        try {
            Users user = UsersRepository.findById(Integer.parseInt(idUser)).orElse(null);
            if (user == null) {
                return false;
            }
            user.setSurname(Surname);
            user.setName(Name);
            user.setPatronymic(Patronymic);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse(BirthDate);
            } catch (ParseException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            user.setBirthDate(date);

            try {
                date = format.parse(EmploymentDate);
            } catch (ParseException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            user.setEmploymentDate(date);
            user.setLogin(Login);
            user.setPassword(Password);
            user.setPassportData(PassportData);

            if (PhotoEquipment != null) {
                user.setPhotoEquipment(PhotoEquipment);
            }

            user.setStatus(status);

            UsersRepository.save(user);

            String currentRole = getRole(Integer.parseInt(idUser));

            if (!role.equals(currentRole)) {
                deleteAllRoles(Integer.parseInt(idUser));

                if (role != null && !role.isEmpty()) {
                    if ("Оператор".equals(role)) {
                        Operator operator = new Operator();
                        operator.setIdUser(user);
                        OperatorInterface.save(operator);
                    } else if ("Супервизор".equals(role)) {
                        Supervisor supervisor = new Supervisor();
                        supervisor.setIdUser(user);
                        SupervisorInterface.save(supervisor);
                    } else if ("Технический специалист".equals(role)) {
                        Technician technician = new Technician();
                        technician.setIdUser(user);
                        TechnicianInterface.save(technician);
                    }
                }
            }

            return true;
        } catch (NumberFormatException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Метод для получения текущей роли пользователя
    @GetMapping("/getRole")
    public String getRole(@RequestParam(name = "idUser") int idUser) {
        if (OperatorInterface.findByIdUser_IdUser(idUser).isPresent()) {
            return "Оператор";
        } else if (SupervisorInterface.findByIdUser_IdUser(idUser).isPresent()) {
            return "Супервизор";
        } else if (TechnicianInterface.findByIdUser_IdUser(idUser).isPresent()) {
            return "Технический специалист";
        }
        return "";
    }

    // Метод для удаления всех ролей пользователя
    private void deleteAllRoles(int idUser) {
        Optional<Supervisor> supervisorOptional = SupervisorInterface.findByIdUser_IdUser(idUser);
        supervisorOptional.ifPresent(supervisor -> deleteSupervisor(String.valueOf(supervisor.getIdSupervisor())));

        Optional<Technician> technicianOptional = TechnicianInterface.findByIdUser_IdUser(idUser);
        technicianOptional.ifPresent(technician -> deleteTechnician(String.valueOf(technician.getIdTechnician())));

        Optional<Operator> operatorOptional = OperatorInterface.findByIdUser_IdUser(idUser);
        operatorOptional.ifPresent(operator -> deleteOperator(String.valueOf(operator.getIdOperator())));
    }

    // Добавьте методы для удаления ролей (deleteSupervisor, deleteTechnician, deleteOperator)
    // в соответствии с вашими репозиториями и сервисами.
    @PostMapping("/addAbonent")
    public @ResponseBody
    boolean addAbonent(@RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic") String Patronymic,
            @RequestParam(name = "DownloadDate") String DownloadDate,
            @RequestParam(name = "AvailabilityBlocker") Integer AvailabilityBlocker,
            @RequestParam(name = "Debt") String Debt,
            @RequestParam(name = "Notes") String Notes,
            @RequestParam(name = "idOperator") String idOperator) {

        Abonent abonent = new Abonent();
        abonent.setSurname(Surname);
        abonent.setName(Name);
        abonent.setPatronymic(Patronymic);

        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(DownloadDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        abonent.setDownloadDate(date);
        abonent.setAvailabilityBlocker(AvailabilityBlocker);
        abonent.setDebt(Debt);
        abonent.setNotes(Notes);
        Operator operator = new Operator(Integer.parseInt(idOperator));
        abonent.setIdOperator(operator);

        AbonentInterface.save(abonent);
        return true;
    }

    @PostMapping("/deleteAbonent")
    public @ResponseBody
    boolean deleteAbonent(@RequestParam(name = "idAbonent") String idAbonent) {
        AbonentInterface.deleteById(Integer.parseInt(idAbonent));
        return true;
    }

    @PostMapping("/updateAbonent")
    public @ResponseBody
    boolean updateAbonent(@RequestParam(name = "idAbonent") String idAbonent,
            @RequestParam(name = "Surname") String Surname,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Patronymic") String Patronymic,
            @RequestParam(name = "DownloadDate") String DownloadDate,
            @RequestParam(name = "AvailabilityBlocker") Integer AvailabilityBlocker,
            @RequestParam(name = "Debt") String Debt,
            @RequestParam(name = "Notes") String Notes,
            @RequestParam(name = "idOperator") String idOperator) {

        Abonent abonent = AbonentInterface.findById(Integer.parseInt(idAbonent)).get();
        abonent.setSurname(Surname);
        abonent.setName(Name);
        abonent.setPatronymic(Patronymic);

        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(DownloadDate);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        abonent.setDownloadDate(date);
        abonent.setAvailabilityBlocker(AvailabilityBlocker);
        abonent.setDebt(Debt);
        abonent.setNotes(Notes);
        Operator operator = new Operator(Integer.parseInt(idOperator));
        abonent.setIdOperator(operator);

        AbonentInterface.save(abonent);
        return true;
    }

    @PostMapping("/addService")
    public @ResponseBody
    boolean addService(@RequestParam(name = "Name") String Name,
            @RequestParam(name = "Notes") String Notes) {

        Services service = new Services();
        service.setName(Name);
        service.setNotes(Notes);

        ServicesInterface.save(service);
        return true;
    }

    @PostMapping("/deleteService")
    public @ResponseBody
    boolean deleteService(@RequestParam(name = "idServices") String idServices) {
        ServicesInterface.deleteById(Integer.parseInt(idServices));
        return true;
    }

    @PostMapping("/updateService")
    public @ResponseBody
    boolean updateService(@RequestParam(name = "idServices") String idServices,
            @RequestParam(name = "Name") String Name,
            @RequestParam(name = "Notes") String Notes) {

        Services service = ServicesInterface.findById(Integer.parseInt(idServices)).get();
        service.setName(Name);
        service.setNotes(Notes);

        ServicesInterface.save(service);
        return true;
    }

    @PostMapping("/addApplicationType")
    public boolean addApplicationType(
            @RequestParam String name,
            @RequestParam String notes) {
        ApplicationType type = new ApplicationType();
        type.setName(name);
        type.setNotes(notes);
        ApplicationTypeInterface.save(type);
        return true;
    }

    @GetMapping("/allApplicationType")
    public @ResponseBody
    Iterable<ApplicationType> allApplicationType() {
        return ApplicationTypeInterface.findAll();
    }

    @PostMapping("/deleteApplicationType")
    public boolean deleteApplicationType(@RequestParam String id) {
        ApplicationTypeInterface.deleteById(Integer.parseInt(id));
        return true;
    }

    @PostMapping("/updateApplicationType")
    public boolean updateApplicationType(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String notes) {
        ApplicationType type = ApplicationTypeInterface.findById(Integer.parseInt(id)).get();
        type.setName(name);
        type.setNotes(notes);
        ApplicationTypeInterface.save(type);
        return true;
    }

    @PostMapping("/addAbonentService")
    public @ResponseBody
    boolean addAbonentService(
            @RequestParam(name = "idAbonent") String idAbonent,
            @RequestParam(name = "idServices") String idServices,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {

        // Проверка идентификаторов
        int abonentId;
        int serviceId;

        try {
            abonentId = Integer.parseInt(idAbonent);
            serviceId = Integer.parseInt(idServices);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID format");
        }

        AbonentServices service = new AbonentServices();
        service.setIdAbonent(new Abonent(abonentId));
        service.setIdServices(new Services(serviceId));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            service.setStartDate(format.parse(startDate));
            service.setEndDate(format.parse(endDate));
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Или выбросьте исключение, если это необходимо
        }

        AbonentServicesInterface.save(service);
        return true;
    }

    @GetMapping("/allApplicationss")
    public @ResponseBody
    List<Map<String, Object>> getAllApplicationss() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> result = new ArrayList<>();

        for (Applications application : ApplicationInterface.findAll()) {
            Map<String, Object> applicationData = new HashMap<>();
            applicationData.put("idApplication", application.getIdApplication());

            // Получаем ФИО абонента
            if (application.getIdAbonent() != null) {
                String abonentFIO = application.getIdAbonent().getSurname() + " "
                        + application.getIdAbonent().getName() + " "
                        + application.getIdAbonent().getPatronymic();
                applicationData.put("abonentFIO", abonentFIO);
            } else {
                applicationData.put("abonentFIO", "Абонент не найден");
            }

            // Получаем название типа заявки
            if (application.getIdApplicationType() != null) {
                applicationData.put("applicationTypeName", application.getIdApplicationType().getName());
            } else {
                applicationData.put("applicationTypeName", "Тип заявки не найден");
            }

            // Получаем название статуса
            if (application.getIdStatus() != null) {
                applicationData.put("statusName", application.getIdStatus().getName());
            } else {
                applicationData.put("statusName", "Статус не найден");
            }

            if (application.getApplicationsDate() != null) {
                applicationData.put("applicationsDate", format.format(application.getApplicationsDate()));
            } else {
                applicationData.put("applicationsDate", null);
            }
            applicationData.put("note", application.getNote());

            result.add(applicationData);
        }

        return result;
    }

    @GetMapping("/allApplicationTypesForComboBox")
    public @ResponseBody
    List<Map<String, Object>> getAllApplicationTypesForComboBox() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (ApplicationType applicationType : ApplicationTypeInterface.findAll()) {
            Map<String, Object> applicationTypeData = new HashMap<>();
            applicationTypeData.put("idApplicationType", applicationType.getIdApplicationType());
            applicationTypeData.put("applicationTypeName", applicationType.getName());
            result.add(applicationTypeData);
        }

        return result;
    }

    @GetMapping("/allStatusesForComboBox")
    public @ResponseBody
    List<Map<String, Object>> getAllStatusesForComboBox() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Status status : StatusInterface.findAll()) {
            Map<String, Object> statusData = new HashMap<>();
            statusData.put("idStatus", status.getIdStatus());
            statusData.put("statusName", status.getName());
            result.add(statusData);
        }

        return result;
    }

    @PostMapping("/addApplicationn")
    public @ResponseBody
    boolean addApplicationn(
            @RequestParam("idAbonent") int idAbonent,
            @RequestParam("idApplicationType") int idApplicationType,
            @RequestParam("applicationsDate") String applicationsDate,
            @RequestParam("note") String note,
            @RequestParam("idStatus") int idStatus) {

        try {
            Applications application = new Applications();

            // Получаем объекты по ID
            Abonent abonent = AbonentInterface.findById(idAbonent).orElse(null);
            ApplicationType applicationType = ApplicationTypeInterface.findById(idApplicationType).orElse(null);
            Status status = StatusInterface.findById(idStatus).orElse(null);

            if (abonent == null || applicationType == null || status == null) {
                return false; // Не удалось найти связанные объекты
            }

            application.setIdAbonent(abonent);
            application.setIdApplicationType(applicationType);
            application.setIdStatus(status);

            // Устанавливаем дату
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(applicationsDate);
            application.setApplicationsDate(date);

            // Устанавливаем примечание
            application.setNote(note);

            ApplicationInterface.save(application);
            return true;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/updateApplicationn")
    public @ResponseBody
    boolean updateApplicationn(
            @RequestParam("idApplication") int idApplication,
            @RequestParam("idAbonent") int idAbonent,
            @RequestParam("idApplicationType") int idApplicationType,
            @RequestParam("applicationsDate") String applicationsDate,
            @RequestParam("note") String note,
            @RequestParam("idStatus") int idStatus) {
        try {
            Applications application = ApplicationInterface.findById(idApplication).orElse(null);

            if (application == null) {
                return false;
            }

            // Получаем объекты по ID
            Abonent abonent = AbonentInterface.findById(idAbonent).orElse(null);
            ApplicationType applicationType = ApplicationTypeInterface.findById(idApplicationType).orElse(null);
            Status status = StatusInterface.findById(idStatus).orElse(null);

            if (abonent == null || applicationType == null || status == null) {
                return false; // Не удалось найти связанные объекты
            }

            application.setIdAbonent(abonent);
            application.setIdApplicationType(applicationType);
            application.setIdStatus(status);

            // Устанавливаем дату
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(applicationsDate);
            application.setApplicationsDate(date);

            // Устанавливаем примечание
            application.setNote(note);

            ApplicationInterface.save(application);
            return true;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/deleteApplicationn")
    public @ResponseBody
    boolean deleteApplicationn(@RequestParam("idApplication") int idApplication) {
        try {
            ApplicationInterface.deleteById(idApplication);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/allAbonentServices")
    public @ResponseBody
    Iterable<AbonentServices> allAbonentServices() {
        return AbonentServicesInterface.findAll();
    }

    @GetMapping("/allAbonents")
    public @ResponseBody
    Iterable<Abonent> allAbonents() {
        return AbonentInterface.findAll();
    }

    @GetMapping("/allAbonentsForComboBox")
    public @ResponseBody
    List<Map<String, Object>> getAllAbonentsForComboBox() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Abonent abonent : AbonentInterface.findAll()) {
            Map<String, Object> abonentData = new HashMap<>();
            String abonentFIO = abonent.getSurname() + " "
                    + abonent.getName() + " "
                    + abonent.getPatronymic();
            abonentData.put("idAbonent", abonent.getIdAbonent());
            abonentData.put("abonentFIO", abonentFIO);
            result.add(abonentData);
        }

        return result;
    }

    @GetMapping("/allServicesForComboBox")
    public @ResponseBody
    List<Map<String, Object>> getAllServicesForComboBox() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Services service : ServicesInterface.findAll()) {
            Map<String, Object> serviceData = new HashMap<>();
            serviceData.put("idServices", service.getIdServices());
            serviceData.put("serviceName", service.getName());
            result.add(serviceData);
        }

        return result;
    }

    @GetMapping("/allRemontss")
    public @ResponseBody
    List<Map<String, Object>> getAllRemontss() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Remont remont : RemontInterface.findAll()) {
            Map<String, Object> remontData = new HashMap<>();
            remontData.put("idRemont", remont.getIdRemont());

            // Получаем название оборудования
            if (remont.getIdEquipment() != null) {
                remontData.put("equipmentName", remont.getIdEquipment().getName());
            } else {
                remontData.put("equipmentName", "Оборудование не найдено");
            }

            // Получаем ФИО техника
            if (remont.getIdTechnician() != null) {
                String technicianFIO = remont.getIdTechnician().getIdUser().getSurname() + " "
                        + remont.getIdTechnician().getIdUser().getName() + " "
                        + remont.getIdTechnician().getIdUser().getPatronymic();
                remontData.put("technicianFIO", technicianFIO);
            } else {
                remontData.put("technicianFIO", "Техник не найден");
            }

            // Получаем название услуги
            if (remont.getIdServices() != null) {
                remontData.put("serviceName", remont.getIdServices().getName());
            } else {
                remontData.put("serviceName", "Услуга не найдена");
            }

            remontData.put("type", remont.getType());
            result.add(remontData);
        }

        return result;
    }

    @PostMapping("/deleteServicee")
    public @ResponseBody
    String deleteServicee(@RequestParam("idServices") int idServices) {
        ServicesInterface.deleteById(idServices);
        return "true";
    }

    @PostMapping("/updateServicee")
    public @ResponseBody
    String updateServicee(@RequestParam("idServices") int idServices,
            @RequestParam("name") String name,
            @RequestParam("notes") String notes) {
        Services service = ServicesInterface.findById(idServices).orElse(null);
        if (service != null) {
            service.setName(name);
            service.setNotes(notes);
            ServicesInterface.save(service);
            return "true";
        }
        return "false";
    }

    @PostMapping("/addServicee")
    public @ResponseBody
    String addServicee(@RequestParam("name") String name,
            @RequestParam("notes") String notes) {
        Services service = new Services();
        service.setName(name);
        service.setNotes(notes);
        ServicesInterface.save(service);
        return "true";
    }

    @GetMapping("/allServicess")
    public @ResponseBody
    List<Map<String, Object>> getAllServicess() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Services service : ServicesInterface.findAll()) {
            Map<String, Object> serviceData = new HashMap<>();
            serviceData.put("idServices", service.getIdServices());
            serviceData.put("name", service.getName());
            serviceData.put("notes", service.getNotes());
            result.add(serviceData);
        }

        return result;
    }

    @PostMapping("/deleteApplicationTypee")
    public @ResponseBody
    String deleteApplicationTypee(@RequestParam("idApplicationType") int idApplicationType) {
        ApplicationTypeInterface.deleteById(idApplicationType);
        return "true";
    }

    @GetMapping("/allEquipmentForComboBox")
    public @ResponseBody
    List<Map<String, Object>> getAllEquipmentForComboBox() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Equipment equipment : EquipmentInterface.findAll()) {
            Map<String, Object> equipmentData = new HashMap<>();
            equipmentData.put("idEquipment", equipment.getIdEquipment());
            equipmentData.put("equipmentName", equipment.getName());
            result.add(equipmentData);
        }

        return result;
    }

    @PostMapping("/updateApplicationTypee")
    public @ResponseBody
    String updateApplicationTypee(@RequestParam("idApplicationType") int idApplicationType,
            @RequestParam("name") String name,
            @RequestParam("notes") String notes) {
        ApplicationType appType = ApplicationTypeInterface.findById(idApplicationType).orElse(null);
        if (appType != null) {
            appType.setName(name);
            appType.setNotes(notes);
            ApplicationTypeInterface.save(appType);
            return "true";
        }
        return "false";
    }

    @PostMapping("/addApplicationTypee")
    public @ResponseBody
    String addApplicationTypee(@RequestParam("name") String name,
            @RequestParam("notes") String notes) {
        ApplicationType appType = new ApplicationType();
        appType.setName(name);
        appType.setNotes(notes);
        ApplicationTypeInterface.save(appType);
        return "true";
    }

    @GetMapping("/allApplicationTypess")
    public @ResponseBody
    List<Map<String, Object>> getAllApplicationTypess() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (ApplicationType appType : ApplicationTypeInterface.findAll()) {
            Map<String, Object> appTypeData = new HashMap<>();
            appTypeData.put("idApplicationType", appType.getIdApplicationType());
            appTypeData.put("name", appType.getName());
            appTypeData.put("notes", appType.getNotes());
            result.add(appTypeData);
        }

        return result;
    }

    @GetMapping("/allTechniciansForComboBox")
    public @ResponseBody
    List<Map<String, Object>> getAllTechniciansForComboBox() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Technician technician : TechnicianInterface.findAll()) {
            Map<String, Object> technicianData = new HashMap<>();
            String technicianFIO = technician.getIdUser().getSurname() + " "
                    + technician.getIdUser().getName() + " "
                    + technician.getIdUser().getPatronymic();
            technicianData.put("idTechnician", technician.getIdTechnician());
            technicianData.put("technicianFIO", technicianFIO);
            result.add(technicianData);
        }

        return result;
    }

    @PostMapping("/addRemontt")
    public @ResponseBody
    boolean addRemontt(
            @RequestParam("idEquipment") int idEquipment,
            @RequestParam("idServices") int idServices,
            @RequestParam("idTechnician") int idTechnician,
            @RequestParam("type") String type) {

        try {
            Remont remont = new Remont();

            // Получаем объекты по ID
            Equipment equipment = EquipmentInterface.findById(idEquipment).orElse(null);
            Services service = ServicesInterface.findById(idServices).orElse(null);
            Technician technician = TechnicianInterface.findById(idTechnician).orElse(null);

            if (equipment == null || service == null || technician == null) {
                return false; // Не удалось найти связанные объекты
            }

            remont.setIdEquipment(equipment);
            remont.setIdServices(service);
            remont.setIdTechnician(technician);
            remont.setType(type);

            RemontInterface.save(remont);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/updateRemontt")
    public @ResponseBody
    boolean updateRemontt(
            @RequestParam("idRemont") int idRemont,
            @RequestParam("idEquipment") int idEquipment,
            @RequestParam("idServices") int idServices,
            @RequestParam("idTechnician") int idTechnician,
            @RequestParam("type") String type) {

        try {
            Remont remont = RemontInterface.findById(idRemont).orElse(null);

            if (remont == null) {
                return false;
            }

            // Получаем объекты по ID
            Equipment equipment = EquipmentInterface.findById(idEquipment).orElse(null);
            Services service = ServicesInterface.findById(idServices).orElse(null);
            Technician technician = TechnicianInterface.findById(idTechnician).orElse(null);

            if (equipment == null || service == null || technician == null) {
                return false; // Не удалось найти связанные объекты
            }

            remont.setIdEquipment(equipment);
            remont.setIdServices(service);
            remont.setIdTechnician(technician);
            remont.setType(type);

            RemontInterface.save(remont);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/deleteRemontt")
    public @ResponseBody
    boolean deleteRemontt(@RequestParam("idRemont") int idRemont) {
        try {
            RemontInterface.deleteById(idRemont);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/allEquipmentt")
    public @ResponseBody
    List<Map<String, Object>> getAllEquipmentt() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Equipment equipment : EquipmentInterface.findAll()) {
            Map<String, Object> equipmentData = new HashMap<>();
            equipmentData.put("idEquipment", equipment.getIdEquipment());
            equipmentData.put("name", equipment.getName());
            equipmentData.put("photo", equipment.getPhoto());
            result.add(equipmentData);
        }

        return result;
    }

    @PostMapping("/addEquipmentt")
    public @ResponseBody
    boolean addEquipmentt(
            @RequestParam("name") String name,
            @RequestParam("photo") String photo) {

        try {
            Equipment equipment = new Equipment();
            equipment.setName(name);
            equipment.setPhoto(photo);

            EquipmentInterface.save(equipment);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/updateEquipmentt")
    public @ResponseBody
    boolean updateEquipmentt(
            @RequestParam("idEquipment") int idEquipment,
            @RequestParam("name") String name,
            @RequestParam("photo") String photo) {

        try {
            Equipment equipment = EquipmentInterface.findById(idEquipment).orElse(null);

            if (equipment == null) {
                return false;
            }

            equipment.setName(name);
            equipment.setPhoto(photo);

            EquipmentInterface.save(equipment);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/deleteEquipmentt")
    public @ResponseBody
    boolean deleteEquipmentt(@RequestParam("idEquipment") int idEquipment) {
        try {
            EquipmentInterface.deleteById(idEquipment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/allAbonentServicess")
    public @ResponseBody
    List<Map<String, Object>> getAllAbonentServices() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> result = new ArrayList<>();

        for (AbonentServices abonentServices : AbonentServicesInterface.findAll()) {
            Map<String, Object> abonentServicesData = new HashMap<>();
            abonentServicesData.put("idAbonentServices", abonentServices.getIdAbonentServices());

            // Получаем ФИО абонента
            if (abonentServices.getIdAbonent() != null) {
                String abonentFIO = abonentServices.getIdAbonent().getSurname() + " "
                        + abonentServices.getIdAbonent().getName() + " "
                        + abonentServices.getIdAbonent().getPatronymic();
                abonentServicesData.put("abonentFIO", abonentFIO);
            } else {
                abonentServicesData.put("abonentFIO", "Абонент не найден"); // Или какое-то другое значение по умолчанию
            }

            // Получаем название услуги
            if (abonentServices.getIdServices() != null) {
                abonentServicesData.put("serviceName", abonentServices.getIdServices().getName());
            } else {
                abonentServicesData.put("serviceName", "Услуга не найдена"); // Или какое-то другое значение по умолчанию
            }
            if (abonentServices.getStartDate() != null) {
                abonentServicesData.put("startDate", format.format(abonentServices.getStartDate()));
            } else {
                abonentServicesData.put("startDate", null);
            }
            if (abonentServices.getEndDate() != null) {
                abonentServicesData.put("endDate", format.format(abonentServices.getEndDate()));
            } else {
                abonentServicesData.put("endDate", null);
            }

            result.add(abonentServicesData);
        }

        return result;
    }

    @GetMapping("/allOperatorss")
    public @ResponseBody
    List<Map<String, Object>> getAllOperators() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Operator operator : OperatorInterface.findAll()) {
            Map<String, Object> operatorData = new HashMap<>();
            String operatorFIO = operator.getIdUser().getSurname() + " "
                    + operator.getIdUser().getName() + " "
                    + operator.getIdUser().getPatronymic();
            operatorData.put("idOperator", operator.getIdOperator());
            operatorData.put("operatorFIO", operatorFIO);
            result.add(operatorData);
        }

        return result;
    }

    @GetMapping("/allService")
    public @ResponseBody
    Iterable<Services> allService() {
        return ServicesInterface.findAll();
    }

    @GetMapping("/abonentsWithDebt")
    public @ResponseBody
    List<Object[]> getAbonentsWithDebt() {
        List<Object[]> result = new ArrayList<>();

        Iterable<Abonent> abonents = AbonentInterface.findAll();

        for (Abonent abonent : abonents) {
            Object[] row = new Object[2]; // ФИО и задолженность
            row[0] = abonent.getSurname() + " " + abonent.getName() + " " + abonent.getPatronymic(); // ФИО
            row[1] = abonent.getDebt(); // Задолженность

            result.add(row);
        }

        return result;
    }

    @GetMapping("/internetAbonents")
    public @ResponseBody
    List<Abonent> getInternetAbonents() {
        return AbonentInterface.findAbonentsByInternetService();
    }

    @GetMapping("/allAbonentss")
    public @ResponseBody
    List<Map<String, Object>> getAllAbonents() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> result = new ArrayList<>();

        for (Abonent abonent : AbonentInterface.findAll()) {
            Map<String, Object> abonentData = new HashMap<>();
            abonentData.put("idAbonent", abonent.getIdAbonent());
            abonentData.put("surname", abonent.getSurname());
            abonentData.put("name", abonent.getName());
            abonentData.put("patronymic", abonent.getPatronymic());
            if (abonent.getDownloadDate() != null) {
                abonentData.put("downloadDate", format.format(abonent.getDownloadDate()));
            } else {
                abonentData.put("downloadDate", null);
            }
            abonentData.put("availabilityBlocker", abonent.getAvailabilityBlocker());
            abonentData.put("debt", abonent.getDebt());
            abonentData.put("notes", abonent.getNotes());

            // Получаем ФИО оператора
            if (abonent.getIdOperator() != null) {
                String operatorFIO = abonent.getIdOperator().getIdUser().getSurname() + " "
                        + abonent.getIdOperator().getIdUser().getName() + " "
                        + abonent.getIdOperator().getIdUser().getPatronymic();
                abonentData.put("operatorFIO", operatorFIO);
            } else {
                abonentData.put("operatorFIO", "Не назначен"); // Или какое-то другое значение по умолчанию
            }

            result.add(abonentData);
        }

        return result;
    }

    @GetMapping("/blockerCount")
    public @ResponseBody
    long getBlockerCount() {
        return AbonentInterface.countByAvailabilityBlocker(1);
    }

    @GetMapping("/abonentsCountByService")
    public @ResponseBody
    List<Object[]> getAbonentsCountByService() {
        return ServicesInterface.countAbonentsByService();
    }

    @GetMapping("/abonentsWithServicesAndDate")
    public @ResponseBody
    List<Object[]> getAbonentsWithServicesAndDate() {
        List<Object[]> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Iterable<Abonent> abonents = AbonentInterface.findAll();

        for (Abonent abonent : abonents) {
            // Собираем услуги абонента
            StringBuilder services = new StringBuilder();
            abonent.getAbonentServicesCollection().forEach(abonentService -> {
                if (abonentService.getIdServices() != null) {
                    services.append(abonentService.getIdServices().getName()).append(", ");
                }
            });

            // Убираем последнюю запятую и пробел, если есть услуги
            if (services.length() > 0) {
                services.delete(services.length() - 2, services.length());
            } else {
                services.append("No Services");
            }

            //Собираем даты начала установки услуг
            StringBuilder dates = new StringBuilder();
            abonent.getAbonentServicesCollection().forEach(abonentService -> {
                if (abonentService.getStartDate() != null) {
                    dates.append(dateFormat.format(abonentService.getStartDate())).append(", ");
                }
            });
            if (dates.length() > 0) {
                dates.delete(dates.length() - 2, dates.length());
            } else {
                dates.append("No Date");
            }

            Object[] row = new Object[3]; // ФИО, услуги, дата установки
            row[0] = abonent.getSurname() + " " + abonent.getName() + " " + abonent.getPatronymic(); // ФИО
            row[1] = services.toString(); // Услуги
            row[2] = dates.toString(); // Дата установки

            result.add(row);
        }

        return result;
    }

    @GetMapping("/abonentsByService")
    public @ResponseBody
    List<Object[]> getAbonentsByService(@RequestParam("serviceId") Integer serviceId) {
        List<Object[]> result = new ArrayList<>();

        // Находим все AbonentServices, связанные с указанным serviceId
        List<AbonentServices> abonentServicesList = AbonentServicesInterface.findByIdServices_IdServices(serviceId); // Используем ваш интерфейс

        // Для каждой записи AbonentServices извлекаем информацию об абоненте
        for (AbonentServices abonentService : abonentServicesList) {
            Abonent abonent = abonentService.getIdAbonent();

            if (abonent != null) {
                Object[] row = new Object[4];  // Уменьшено до 4, т.к. название услуги не нужно
                row[0] = abonent.getIdAbonent();
                row[1] = abonent.getSurname();
                row[2] = abonent.getName();
                row[3] = abonent.getPatronymic();

                result.add(row);
            }
        }

        return result;
    }

    @GetMapping("/allServices")
    public @ResponseBody
    Iterable<Services> getAllServices() {
        return ServicesInterface.findAll();
    }

    @GetMapping("/abonentsWithServices")
    public @ResponseBody
    List<Object[]> getAbonentsWithServices() {
        List<Object[]> result = new ArrayList<>();

        // Получаем всех абонентов
        Iterable<Abonent> abonents = AbonentInterface.findAll();

        // Для каждого абонента
        for (Abonent abonent : abonents) {
            // Получаем все AbonentServices, связанные с этим абонентом
            List<AbonentServices> abonentServices = new ArrayList<>(abonent.getAbonentServicesCollection());

            // Если у абонента есть услуги
            if (!abonentServices.isEmpty()) {
                // Для каждой услуги абонента
                for (AbonentServices abonentService : abonentServices) {
                    // Получаем название услуги
                    Services service = abonentService.getIdServices();
                    String serviceName = (service != null) ? service.getName() : "N/A";

                    // Создаем массив объектов для хранения информации об абоненте и услуге
                    Object[] row = new Object[5];
                    row[0] = abonent.getIdAbonent();
                    row[1] = abonent.getSurname();
                    row[2] = abonent.getName();
                    row[3] = abonent.getPatronymic();
                    row[4] = serviceName; // Название услуги

                    result.add(row);
                }
            } else {
                // Если у абонента нет услуг, добавляем информацию об абоненте с пометкой "No Services"
                Object[] row = new Object[5];
                row[0] = abonent.getIdAbonent();
                row[1] = abonent.getSurname();
                row[2] = abonent.getName();
                row[3] = abonent.getPatronymic();
                row[4] = "No Services";

                result.add(row);
            }
        }

        return result;
    }

    @PostMapping("/updateAbonentService")
    public @ResponseBody
    boolean updateAbonentService(@RequestParam(name = "idAbonentServices") String idAbonentServices,
            @RequestParam(name = "idServices") String idServices,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate) {
        AbonentServices service = AbonentServicesInterface.findById(Integer.parseInt(idAbonentServices)).get();
        service.setIdServices(new Services(Integer.parseInt(idServices)));
        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            service.setStartDate(format.parse(startDate));
            service.setEndDate(format.parse(endDate));
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AbonentServicesInterface.save(service);
        return true;
    }

    @PostMapping("/deleteAbonentService")
    public @ResponseBody
    boolean deleteAbonentService(@RequestParam(name = "idAbonentServices") String idAbonentServices) {
        AbonentServicesInterface.deleteById(Integer.parseInt(idAbonentServices));
        return true;
    }

    @PostMapping("/addApplication")
    public @ResponseBody
    boolean addApplication(@RequestParam(name = "idAbonent") String idAbonent,
            @RequestParam(name = "idApplicationType") String idApplicationType,
            @RequestParam(name = "applicationsDate") String applicationsDate,
            @RequestParam(name = "note") String note) {
        Applications application = new Applications();
        application.setIdAbonent(new Abonent(Integer.parseInt(idAbonent)));
        application.setIdApplicationType(new ApplicationType(Integer.parseInt(idApplicationType)));

        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            application.setApplicationsDate(format.parse(applicationsDate));
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        application.setNote(note);
        ApplicationInterface.save(application);
        return true;
    }

    @GetMapping("/allApplications")
    public @ResponseBody
    Iterable<Applications> allApplications() {
        return ApplicationInterface.findAll();
    }

    @PostMapping("/updateApplication")
    public @ResponseBody
    boolean updateApplication(@RequestParam(name = "idApplication") String idApplication,
            @RequestParam(name = "idAbonent") String idAbonent,
            @RequestParam(name = "idApplicationType") String idApplicationType,
            @RequestParam(name = "applicationsDate") String applicationsDate,
            @RequestParam(name = "note") String note) {
        Applications application = ApplicationInterface.findById(Integer.parseInt(idApplication)).get();
        application.setIdAbonent(new Abonent(Integer.parseInt(idAbonent)));
        application.setIdApplicationType(new ApplicationType(Integer.parseInt(idApplicationType)));

        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            application.setApplicationsDate(format.parse(applicationsDate));
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        application.setNote(note);
        ApplicationInterface.save(application);
        return true;
    }

    @PostMapping("/deleteApplication")
    public @ResponseBody
    boolean deleteApplication(@RequestParam(name = "idApplication") String idApplication) {
        ApplicationInterface.deleteById(Integer.parseInt(idApplication));
        return true;
    }

    @PostMapping("/addEquipment")
    public @ResponseBody
    boolean addEquipment(@RequestParam(name = "name") String name,
            @RequestParam(name = "photo") String photo) {
        Equipment equipment = new Equipment();
        equipment.setName(name);
        equipment.setPhoto(photo);

        EquipmentInterface.save(equipment);
        return true;
    }

    @GetMapping("/allEquipment")
    public @ResponseBody
    Iterable<Equipment> allEquipment() {
        return EquipmentInterface.findAll();
    }

    @PostMapping("/updateEquipment")
    public @ResponseBody
    boolean updateEquipment(@RequestParam(name = "idEquipment") String idEquipment,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "photo") String photo) {
        Equipment equipment = EquipmentInterface.findById(Integer.parseInt(idEquipment)).get();
        equipment.setName(name);
        equipment.setPhoto(photo);

        EquipmentInterface.save(equipment);
        return true;
    }

    @PostMapping("/deleteEquipment")
    public @ResponseBody
    boolean deleteEquipment(@RequestParam(name = "idEquipment") String idEquipment) {
        EquipmentInterface.deleteById(Integer.parseInt(idEquipment));
        return true;
    }

    @PostMapping("/addHomePhone")
    public @ResponseBody
    boolean addHomePhone(@RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "idServices") String idServices) {
        HomePhone homePhone = new HomePhone();
        homePhone.setName(name);
        homePhone.setType(type);
        homePhone.setIdServices(new Services(Integer.parseInt(idServices)));

        HomePhoneInterface.save(homePhone);
        return true;
    }

    @GetMapping("/allHomePhones")
    public @ResponseBody
    Iterable<HomePhone> allHomePhones() {
        return HomePhoneInterface.findAll();
    }

    @PostMapping("/updateHomePhone")
    public @ResponseBody
    boolean updateHomePhone(@RequestParam(name = "idHomePhone") String idHomePhone,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type) {
        HomePhone homePhone = HomePhoneInterface.findById(Integer.parseInt(idHomePhone)).get();
        homePhone.setName(name);
        homePhone.setType(type);

        HomePhoneInterface.save(homePhone);
        return true;
    }

    @PostMapping("/deleteHomePhone")
    public @ResponseBody
    boolean deleteHomePhone(@RequestParam(name = "idHomePhone") String idHomePhone) {
        HomePhoneInterface.deleteById(Integer.parseInt(idHomePhone));
        return true;
    }

    @PostMapping("/addInternet")
    public @ResponseBody
    boolean addInternet(@RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "idServices") String idServices) {
        Internet internet = new Internet();
        internet.setName(name);
        internet.setType(type);
        internet.setIdServices(new Services(Integer.parseInt(idServices)));

        InternetInterface.save(internet);
        return true;
    }

    @GetMapping("/allStatusess")
    public @ResponseBody
    List<Map<String, Object>> getAllStatusess() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Status status : StatusInterface.findAll()) {
            Map<String, Object> statusData = new HashMap<>();
            statusData.put("idStatus", status.getIdStatus());
            statusData.put("name", status.getName());
            result.add(statusData);
        }

        return result;
    }

    @PostMapping("/addStatuss")
    public @ResponseBody
    String addStatuss(@RequestParam("name") String name) {
        Status status = new Status();
        status.setName(name);
        StatusInterface.save(status);
        return "true";
    }

    @PostMapping("/updateStatuss")
    public @ResponseBody
    String updateStatuss(@RequestParam("idStatus") int idStatus,
            @RequestParam("name") String name) {
        Status status = StatusInterface.findById(idStatus).orElse(null);
        if (status != null) {
            status.setName(name);
            StatusInterface.save(status);
            return "true";
        }
        return "false";
    }

    @GetMapping("/allInternetss")
    public @ResponseBody
    List<Map<String, Object>> getAllInternetss() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Internet internet : InternetInterface.findAll()) {
            Map<String, Object> internetData = new HashMap<>();
            internetData.put("idInternet", internet.getIdInternet());
            internetData.put("name", internet.getName());
            internetData.put("type", internet.getType());
            if (internet.getIdServices() != null) {
                internetData.put("serviceName", internet.getIdServices().getName()); // Название услуги
                internetData.put("idServices", internet.getIdServices().getIdServices()); // ID услуги
            } else {
                internetData.put("serviceName", "Услуга не найдена");
                internetData.put("idServices", null);
            }
            result.add(internetData);
        }

        return result;
    }

    @PostMapping("/addInternett")
    public @ResponseBody
    String addInternett(@RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("serviceId") int serviceId) {
        Services service = ServicesInterface.findById(serviceId).orElse(null);
        if (service == null) {
            return "false";
        }
        Internet internet = new Internet();
        internet.setName(name);
        internet.setType(type);
        internet.setIdServices(service);
        InternetInterface.save(internet);
        return "true";
    }

    @PostMapping("/updateInternett")
    public @ResponseBody
    String updateInternett(@RequestParam("idInternet") int idInternet,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("serviceId") int serviceId) {
        Internet internet = InternetInterface.findById(idInternet).orElse(null);
        if (internet == null) {
            return "false";
        }
        Services service = ServicesInterface.findById(serviceId).orElse(null);
        if (service == null) {
            return "false";
        }
        internet.setName(name);
        internet.setType(type);
        internet.setIdServices(service);
        InternetInterface.save(internet);
        return "true";
    }

    @PostMapping("/deleteInternett")
    public @ResponseBody
    String deleteInternett(@RequestParam("idInternet") int idInternet) {
        InternetInterface.deleteById(idInternet);
        return "true";
    }

    @PostMapping("/updateHomePhonee")
    public @ResponseBody
    String updateHomePhonee(@RequestParam("idHomePhone") int idHomePhone,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("serviceId") int serviceId) {
        HomePhone homePhone = HomePhoneInterface.findById(idHomePhone).orElse(null);
        if (homePhone == null) {
            return "false";
        }
        Services service = ServicesInterface.findById(serviceId).orElse(null);
        if (service == null) {
            return "false";
        }
        homePhone.setName(name);
        homePhone.setType(type);
        homePhone.setIdServices(service);
        HomePhoneInterface.save(homePhone);
        return "true";
    }

    @PostMapping("/deleteHomePhonee")
    public @ResponseBody
    String deleteHomePhonee(@RequestParam("idHomePhone") int idHomePhone) {
        HomePhoneInterface.deleteById(idHomePhone);
        return "true";
    }

    @PostMapping("/addHomePhonee")
    public @ResponseBody
    String addHomePhonee(@RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("serviceId") int serviceId) {
        Services service = ServicesInterface.findById(serviceId).orElse(null);
        if (service == null) {
            return "false";
        }
        HomePhone homePhone = new HomePhone();
        homePhone.setName(name);
        homePhone.setType(type);
        homePhone.setIdServices(service);
        HomePhoneInterface.save(homePhone);
        return "true";
    }

    @GetMapping("/allHomePhoness")
    public @ResponseBody
    List<Map<String, Object>> getAllHomePhoness() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (HomePhone homePhone : HomePhoneInterface.findAll()) {
            Map<String, Object> homePhoneData = new HashMap<>();
            homePhoneData.put("idHomePhone", homePhone.getIdHomePhone());
            homePhoneData.put("name", homePhone.getName());
            homePhoneData.put("type", homePhone.getType());
            if (homePhone.getIdServices() != null) {
                homePhoneData.put("serviceName", homePhone.getIdServices().getName()); // Название услуги
                homePhoneData.put("idServices", homePhone.getIdServices().getIdServices()); // ID услуги
            } else {
                homePhoneData.put("serviceName", "Услуга не найдена");
                homePhoneData.put("idServices", null);
            }
            result.add(homePhoneData);
        }

        return result;
    }

    @PostMapping("/deleteStatuss")
    public @ResponseBody
    String deleteStatuss(@RequestParam("idStatus") int idStatus) {
        StatusInterface.deleteById(idStatus);
        return "true";
    }

    @GetMapping("/allInternet")
    public @ResponseBody
    Iterable<Internet> allInternet() {
        return InternetInterface.findAll();
    }

    @PostMapping("/updateInternet")
    public @ResponseBody
    boolean updateInternet(@RequestParam(name = "idInternet") String idInternet,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type) {
        Internet internet = InternetInterface.findById(Integer.parseInt(idInternet)).get();
        internet.setName(name);
        internet.setType(type);

        InternetInterface.save(internet);
        return true;
    }

    @PostMapping("/deleteInternet")
    public @ResponseBody
    boolean deleteInternet(@RequestParam(name = "idInternet") String idInternet) {
        InternetInterface.deleteById(Integer.parseInt(idInternet));
        return true;
    }

    @PostMapping("/addOperator")
    public @ResponseBody
    boolean addOperator(@RequestParam(name = "idUser") String idUser) {
        Operator operator = new Operator();
        operator.setIdUser(new Users(Integer.parseInt(idUser)));
        OperatorInterface.save(operator);
        return true;
    }

    @GetMapping("/allOperators")
    public @ResponseBody
    List<Integer> allOperators() {
        List<Operator> operators = (List<Operator>) OperatorInterface.findAll();
        return operators.stream()
                .map(Operator::getIdOperator)
                .collect(Collectors.toList());
    }

    @PostMapping("/updateOperator")
    public @ResponseBody
    boolean updateOperator(@RequestParam(name = "idOperator") String idOperator,
            @RequestParam(name = "idUser ") String idUser) {
        Operator operator = OperatorInterface.findById(Integer.parseInt(idOperator)).get();
        operator.setIdUser(new Users(Integer.parseInt(idUser)));

        OperatorInterface.save(operator);
        return true;
    }

    @PostMapping("/deleteOperator")
    public @ResponseBody
    boolean deleteOperator(@RequestParam(name = "idOperator") String idOperator) {
        OperatorInterface.deleteById(Integer.parseInt(idOperator));
        return true;
    }

    @PostMapping("/addSupervisor")
    public @ResponseBody
    boolean addSupervisor(@RequestParam(name = "idUser") String idUser) {
        Supervisor supervisor = new Supervisor();
        supervisor.setIdUser(new Users(Integer.parseInt(idUser)));
        SupervisorInterface.save(supervisor);
        return true;
    }

    @GetMapping("/allSupervisors")
    public @ResponseBody
    List allSupervisors() {
        List list = new ArrayList();
        for (Supervisor t : SupervisorInterface.findAll()) {
            list.add(t.getIdUser());
        }
        return list;
    }

    @PostMapping("/updateSupervisor")
    public @ResponseBody
    boolean updateSupervisor(@RequestParam(name = "idSupervisor") String idSupervisor,
            @RequestParam(name = "idUser ") String idUser) {
        Supervisor supervisor = SupervisorInterface.findById(Integer.parseInt(idSupervisor)).get();
        supervisor.setIdUser(new Users(Integer.parseInt(idUser)));

        SupervisorInterface.save(supervisor);
        return true;
    }

    @PostMapping("/deleteSupervisor")
    public @ResponseBody
    boolean deleteSupervisor(@RequestParam(name = "idSupervisor") String idSupervisor) {
        SupervisorInterface.deleteById(Integer.parseInt(idSupervisor));
        return true;
    }

    @PostMapping("/addTechnician")
    public @ResponseBody
    boolean addTechnician(@RequestParam(name = "idUser") String idUser) {
        Technician technician = new Technician();
        technician.setIdUser(new Users(Integer.parseInt(idUser)));
        TechnicianInterface.save(technician);
        return true;
    }

    @GetMapping("/allTechnicians")
    public @ResponseBody
    List allTechnicians() {
        List list = new ArrayList();
        for (Technician t : TechnicianInterface.findAll()) {
            list.add(t.getIdUser());
        }
        return list;
    }

    @PostMapping("/updateTechnician")
    public @ResponseBody
    boolean updateTechnician(@RequestParam(name = "idTechnician") String idTechnician,
            @RequestParam(name = "idUser ") String idUser) {
        Technician technician = TechnicianInterface.findById(Integer.parseInt(idTechnician)).get();
        technician.setIdUser(new Users(Integer.parseInt(idUser)));

        TechnicianInterface.save(technician);
        return true;
    }

    @PostMapping("/deleteTechnician")
    public @ResponseBody
    boolean deleteTechnician(@RequestParam(name = "idTechnician") String idTechnician) {
        TechnicianInterface.deleteById(Integer.parseInt(idTechnician));
        return true;
    }

    @PostMapping("/addStatus")
    public @ResponseBody
    boolean addStatus(@RequestParam(name = "name") String name) {
        Status status = new Status();
        status.setName(name);
        StatusInterface.save(status);
        return true;
    }

    @GetMapping("/allStatuses")
    public @ResponseBody
    Iterable<Status> allStatuses() {
        return StatusInterface.findAll();
    }

    @PostMapping("/updateStatus")
    public @ResponseBody
    boolean updateStatus(@RequestParam(name = "idStatus") String idStatus,
            @RequestParam(name = "name") String name) {
        Status status = StatusInterface.findById(Integer.parseInt(idStatus)).orElse(null);
        if (status != null) {
            status.setName(name);
            StatusInterface.save(status);
            return true;
        }
        return false;
    }

    @PostMapping("/addRemont")
    public @ResponseBody
    boolean addRemont(@RequestParam(name = "Type") String Type,
            @RequestParam(name = "idEquipment") String idEquipment,
            @RequestParam(name = "idServices") String idServices,
            @RequestParam(name = "idTechnician") String idTechnician) {
        Remont remont = new Remont();
        remont.setType(Type);
        remont.setIdEquipment(new Equipment(Integer.parseInt(idEquipment)));
        remont.setIdServices(new Services(Integer.parseInt(idServices)));
        remont.setIdTechnician(new Technician(Integer.parseInt(idTechnician)));

        RemontInterface.save(remont);
        return true;
    }

    @GetMapping("/allRemonts")
    public @ResponseBody
    Iterable<Remont> allRemonts() {
        return RemontInterface.findAll();
    }

    @PostMapping("/updateRemont")
    public @ResponseBody
    boolean updateRemont(@RequestParam(name = "idRemont") String idRemont,
            @RequestParam(name = "Type") String Type,
            @RequestParam(name = "idEquipment") String idEquipment,
            @RequestParam(name = "idServices") String idServices,
            @RequestParam(name = "idTechnician") String idTechnician) {
        Remont remont = RemontInterface.findById(Integer.parseInt(idRemont)).orElse(null);
        if (remont != null) {
            remont.setType(Type);
            remont.setIdEquipment(new Equipment(Integer.parseInt(idEquipment)));
            remont.setIdServices(new Services(Integer.parseInt(idServices)));
            remont.setIdTechnician(new Technician(Integer.parseInt(idTechnician)));
            RemontInterface.save(remont);
            return true;
        }
        return false;
    }

    @PostMapping("/deleteRemont")
    public @ResponseBody
    boolean deleteRemont(@RequestParam(name = "idRemont") String idRemont) {
        if (RemontInterface.existsById(Integer.parseInt(idRemont))) {
            RemontInterface.deleteById(Integer.parseInt(idRemont));
            return true;
        }
        return false;
    }

    @PostMapping("/getAuthorization2")
    public String getAuthorization2(@RequestParam String Login, @RequestParam String Password) {
        Optional<Users> usersOptional = UsersRepository.findByLoginAndPassword(Login, Password);

        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            System.out.println("Пользователь найден: " + users.getIdUser());

            if ("Подтвержден".equals(users.getStatus())) {
                if (!users.getSupervisorCollection().isEmpty()) {
                    return "Supervision";
                } else if (!users.getTechnicianCollection().isEmpty()) {
                    return "Technician";
                } else if (!users.getOperatorCollection().isEmpty()) {
                    return "Operator";
                }
            } else {
                System.out.println("Пользователь не подтвержден");
                return "Пользователь не подтвержден";
            }
        } else {
            System.out.println("Пользователь не найден или пароль неверный");
            return "Неправильный логин или пароль";
        }
        return null;
    }
}
