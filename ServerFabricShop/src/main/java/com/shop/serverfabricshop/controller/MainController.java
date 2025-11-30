/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.shop.serverfabricshop.controller;

import com.shop.serverfabricshop.entity.Edinitsyizmereniya;
import com.shop.serverfabricshop.entity.Furnitura;
import com.shop.serverfabricshop.entity.Izdelie;
import com.shop.serverfabricshop.entity.Klient;
import com.shop.serverfabricshop.entity.Polzovateli;
import com.shop.serverfabricshop.entity.Postavkafurnitury;
import com.shop.serverfabricshop.entity.Postavkatkani;
import com.shop.serverfabricshop.entity.Prodavec;
import com.shop.serverfabricshop.entity.Prodazhafurnitury;
import com.shop.serverfabricshop.entity.Prodazhatkani;
import com.shop.serverfabricshop.entity.Rastsvetka;
import com.shop.serverfabricshop.entity.Razmer;
import com.shop.serverfabricshop.entity.Shveya;
import com.shop.serverfabricshop.entity.Skidka;
import com.shop.serverfabricshop.entity.Sostav;
import com.shop.serverfabricshop.entity.Sostavprocent;
import com.shop.serverfabricshop.entity.Statusraboty;
import com.shop.serverfabricshop.entity.Tkan;
import com.shop.serverfabricshop.entity.Tsvetfurnitury;
import com.shop.serverfabricshop.entity.Tsvettkani;
import com.shop.serverfabricshop.entity.Upravlyayushchiy;
import com.shop.serverfabricshop.entity.Zakazfurnitura;
import com.shop.serverfabricshop.entity.Zakaznaposhiv;
import com.shop.serverfabricshop.entity.Zakaztkan;
import com.shop.serverfabricshop.repository.EdinitsylzmerenyaRepository;
import com.shop.serverfabricshop.repository.FurnituraRepository;
import com.shop.serverfabricshop.repository.IzdelieRepository;
import com.shop.serverfabricshop.repository.KlientRepository;
import com.shop.serverfabricshop.repository.PolzovateliRepository;
import com.shop.serverfabricshop.repository.PostavkaFurnituryRepository;
import com.shop.serverfabricshop.repository.PostavkaTkaniRepository;
import com.shop.serverfabricshop.repository.ProdavecRepository;
import com.shop.serverfabricshop.repository.ProdazhaFurnituryRepository;
import com.shop.serverfabricshop.repository.ProdazhaTkaniRepository;
import com.shop.serverfabricshop.repository.RastsvetkaRepository;
import com.shop.serverfabricshop.repository.RazmerRepository;

import com.shop.serverfabricshop.repository.ShveyaRepository;
import com.shop.serverfabricshop.repository.SkidkaRepository;
import com.shop.serverfabricshop.repository.SostavRepository;
import com.shop.serverfabricshop.repository.SostavprocentRepository;
import com.shop.serverfabricshop.repository.StatusrabotyRepository;
import com.shop.serverfabricshop.repository.TkanRepository;
import com.shop.serverfabricshop.repository.TsvetFurnituryRepository;
import com.shop.serverfabricshop.repository.TsvetTkaniRepository;
import com.shop.serverfabricshop.repository.UpravlyayushchiyRepository;
import com.shop.serverfabricshop.repository.ZakazFurnituraRepository;
import com.shop.serverfabricshop.repository.ZakazNaPoshivRepository;
import com.shop.serverfabricshop.repository.ZakaztkanRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 8280@stud.pgt.su
 */
@RestController
@RequestMapping("/atelie")
public class MainController {

    @Autowired
    private EdinitsylzmerenyaRepository EdinitsyIzmerenyaRepository;
    @Autowired
    private FurnituraRepository FurnituraRepository;
    @Autowired
    private IzdelieRepository IzdelieRepository;
    @Autowired
    private KlientRepository KlientRepository;
    @Autowired
    private PolzovateliRepository PolzovatelRepository;
    @Autowired
    private PostavkaFurnituryRepository PostavkaFurnituryRepository;
    @Autowired
    private PostavkaTkaniRepository PostavkaTkaniRepository;
    @Autowired
    private ProdavecRepository ProdavecRepository;
    @Autowired
    private ProdazhaFurnituryRepository ProdazhaFurnituryRepository;
    @Autowired
    private ProdazhaTkaniRepository ProdazhaTkaniRepository;
    @Autowired
    private RastsvetkaRepository RastsvetkaRepository;
    @Autowired
    private RazmerRepository RazmerRepository;
    @Autowired
    private ShveyaRepository ShveyaRepository;
    @Autowired
    private SkidkaRepository SkidkaRepository;
    @Autowired
    private SostavRepository SostavRepository;
    @Autowired
    private SostavprocentRepository SostavprocentRepository;
    @Autowired
    private StatusrabotyRepository StatusrabotyRepository;
    @Autowired
    private TkanRepository TkanRepository;
    @Autowired
    private TsvetFurnituryRepository TsvetFurnituryRepository;
    @Autowired
    private TsvetTkaniRepository TsvetTkaniRepository;
    @Autowired
    private UpravlyayushchiyRepository UpravlyayushchiyRepository;
    @Autowired
    private ZakazFurnituraRepository ZakazFurnituraRepository;
    @Autowired
    private ZakazNaPoshivRepository ZakazNaPoshivRepository;
    @Autowired
    private ZakaztkanRepository ZakaztkanRepository;

    @PostMapping("/getAuthorization")
    public @ResponseBody
    boolean getAuthorization(@RequestParam(name = "Login") String Login,
            @RequestParam(name = "Password") String Password) {
        return PolzovatelRepository.findByLoginAndParol(Login, Password).isPresent();
    }

    @PostMapping("/getUserRole")
    public @ResponseBody
    String getUserRole(@RequestParam String login, @RequestParam String password) {
        Optional<Polzovateli> user = PolzovatelRepository.findByLoginAndParol(login, password);

        if (user.isPresent()) {
            Polzovateli userObj = user.get();

            if (!ShveyaRepository.findByPolzovatelid(userObj).isEmpty()) {
                return "shveya";
            } else if (!ProdavecRepository.findByPolzovatelid(userObj).isEmpty()) {
                return "prodavec";
            } else if (!UpravlyayushchiyRepository.findByPolzovatelid(userObj).isEmpty()) {
                return "upravlyayushchiy";
            }
        }
        return "";
    }

    @PostMapping("/updateTkan")
    public @ResponseBody
    Map<String, Object> updateTkan(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "artikul") String artikul,
            @RequestParam(name = "nazvanie") String nazvanie,
            @RequestParam(name = "kategoriya") String kategoriya,
            @RequestParam(name = "idTsvet") String idTsvet,
            @RequestParam(name = "idRastsvetka") String idRastsvetka,
            @RequestParam(name = "novinka") String novinka) {

        Map<String, Object> response = new HashMap<>();

        try {
            Tkan tkan = TkanRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new RuntimeException("Ткань не найдена"));

            tkan.setArtikul(artikul);
            tkan.setNazvanie(nazvanie);
            tkan.setKategoriya(kategoriya);

            // Получаем объекты по внешним ключам
            Tsvettkani tsvet = TsvetTkaniRepository.findById(Integer.parseInt(idTsvet))
                    .orElseThrow(() -> new RuntimeException("Цвет ткани не найден"));
            tkan.setIdTsvet(tsvet);

            Rastsvetka rastsvetka = RastsvetkaRepository.findById(Integer.parseInt(idRastsvetka))
                    .orElseThrow(() -> new RuntimeException("Расцветка не найдена"));
            tkan.setIdRastsvetka(rastsvetka);

            tkan.setNovinka(Boolean.parseBoolean(novinka));

            TkanRepository.save(tkan);

            response.put("success", true);
            response.put("message", "Ткань успешно обновлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка обновления ткани: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/updateFurnitura")
    public @ResponseBody
    Map<String, Object> updateFurnitura(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "artikul") String artikul,
            @RequestParam(name = "nazvanie") String nazvanie,
            @RequestParam(name = "idTsvet") String idTsvet,
            @RequestParam(name = "novinka") String novinka,
            @RequestParam(name = "idEdIzmereniya") String idEdIzmereniya) {

        Map<String, Object> response = new HashMap<>();

        try {
            Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new RuntimeException("Фурнитура не найдена"));

            furnitura.setArtikul(artikul);
            furnitura.setNazvanie(nazvanie);

            // Получаем объекты по внешним ключам
            Tsvetfurnitury tsvet = TsvetFurnituryRepository.findById(Integer.parseInt(idTsvet))
                    .orElseThrow(() -> new RuntimeException("Цвет фурнитуры не найден"));
            furnitura.setIdTsvet(tsvet);

            furnitura.setNovinka(Boolean.parseBoolean(novinka));

            Edinitsyizmereniya edIzmereniya = EdinitsyIzmerenyaRepository.findById(Integer.parseInt(idEdIzmereniya))
                    .orElseThrow(() -> new RuntimeException("Единица измерения не найдена"));
            furnitura.setIdEdIzmereniya(edIzmereniya);

            FurnituraRepository.save(furnitura);

            response.put("success", true);
            response.put("message", "Фурнитура успешно обновлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка обновления фурнитуры: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/updateProdazhafurnitury")
    public @ResponseBody
    Map<String, Object> updateProdazhafurnitury(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "idProdavets") String idProdavets,
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idFurnitura") String idFurnitura,
            @RequestParam(name = "Kolichestvo") String Kolichestvo,
            @RequestParam(name = "tsenaZaYedinitsu") String tsenaZaYedinitsu) {

        Map<String, Object> response = new HashMap<>();

        try {
            Prodazhafurnitury prodazha = ProdazhaFurnituryRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new RuntimeException("Продажа не найдена"));

            // Получаем объекты по внешним ключам
            Prodavec prodavets = ProdavecRepository.findById(Integer.parseInt(idProdavets))
                    .orElseThrow(() -> new RuntimeException("Продавец не найден"));
            prodazha.setIdProdavets(prodavets);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataStr);
            prodazha.setData(data);

            Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(idFurnitura))
                    .orElseThrow(() -> new RuntimeException("Фурнитура не найдена"));
            prodazha.setIdFurnitura(furnitura);

            prodazha.setKolichestvo(Integer.parseInt(Kolichestvo));
            prodazha.setTsenaZaYedinitsu(new BigDecimal(tsenaZaYedinitsu));

            ProdazhaFurnituryRepository.save(prodazha);

            response.put("success", true);
            response.put("message", "Продажа фурнитуры успешно обновлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка обновления продажи фурнитуры: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/updateProdazhatkani")
    public @ResponseBody
    Map<String, Object> updateProdazhatkani(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "idProdavets") String idProdavets,
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idTkan") String idTkan,
            @RequestParam(name = "Kolichestvo") String Kolichestvo,
            @RequestParam(name = "tsenaZaRulon") String tsenaZaRulon,
            @RequestParam(name = "idRazmer") String idRazmer) {

        Map<String, Object> response = new HashMap<>();

        try {
            Prodazhatkani prodazha = ProdazhaTkaniRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new RuntimeException("Продажа не найдена"));

            // Получаем объекты по внешним ключам
            Prodavec prodavets = ProdavecRepository.findById(Integer.parseInt(idProdavets))
                    .orElseThrow(() -> new RuntimeException("Продавец не найден"));
            prodazha.setIdProdavets(prodavets);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataStr);
            prodazha.setData(data);

            Tkan tkan = TkanRepository.findById(Integer.parseInt(idTkan))
                    .orElseThrow(() -> new RuntimeException("Ткань не найдена"));
            prodazha.setIdTkan(tkan);

            prodazha.setKolichestvo(Integer.parseInt(Kolichestvo));
            prodazha.setTsenaZaRulon(new BigDecimal(tsenaZaRulon));

            Razmer razmer = RazmerRepository.findById(Integer.parseInt(idRazmer))
                    .orElseThrow(() -> new RuntimeException("Размер не найден"));
            prodazha.setIdRazmer(razmer);

            ProdazhaTkaniRepository.save(prodazha);

            response.put("success", true);
            response.put("message", "Продажа ткани успешно обновлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка обновления продажи ткани: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/updateZakaznaposhiv")
    public @ResponseBody
    Map<String, Object> updateZakaznaposhiv(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "idKlient") String idKlient,
            @RequestParam(name = "idStatusRaboty") String idStatusRaboty,
            @RequestParam(name = "Data") String dataStr,
            @RequestParam(name = "idShveya") String idShveya,
            @RequestParam(name = "idIzdelie") String idIzdelie,
            @RequestParam(name = "TsenaRabotyShvei") String TsenaRabotyShvei,
            @RequestParam(name = "idSkidka") String idSkidka) {

        Map<String, Object> response = new HashMap<>();

        try {
            Zakaznaposhiv zakaz = ZakazNaPoshivRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new RuntimeException("Заказ не найден"));

            // Получаем объекты по внешним ключам
            Klient klient = KlientRepository.findById(Integer.parseInt(idKlient))
                    .orElseThrow(() -> new RuntimeException("Клиент не найден"));
            zakaz.setIdKlient(klient);

            Statusraboty status = StatusrabotyRepository.findById(Integer.parseInt(idStatusRaboty))
                    .orElseThrow(() -> new RuntimeException("Статус не найден"));
            zakaz.setIdStatusRaboty(status);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataStr);
            zakaz.setData(data);

            Shveya shveya = ShveyaRepository.findById(Integer.parseInt(idShveya))
                    .orElseThrow(() -> new RuntimeException("Швея не найдена"));
            zakaz.setIdShveya(shveya);

            Izdelie izdelie = IzdelieRepository.findById(Integer.parseInt(idIzdelie))
                    .orElseThrow(() -> new RuntimeException("Изделие не найдено"));
            zakaz.setIdIzdelie(izdelie);

            zakaz.setTsenaRabotyShvei(new BigDecimal(TsenaRabotyShvei));

            Skidka skidka = SkidkaRepository.findById(Integer.parseInt(idSkidka))
                    .orElseThrow(() -> new RuntimeException("Скидка не найдена"));
            zakaz.setIdSkidka(skidka);

            ZakazNaPoshivRepository.save(zakaz);

            response.put("success", true);
            response.put("message", "Заказ успешно обновлен");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка обновления заказа: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/isShveya")
    public @ResponseBody
    boolean isShveya(@RequestParam String login) {
        Optional<Polzovateli> user = PolzovatelRepository.findByLogin(login);
        return user.isPresent() && !ShveyaRepository.findByPolzovatelid(user.get()).isEmpty();
    }

    @PostMapping("/isProdavec")
    public @ResponseBody
    boolean isProdavec(@RequestParam String login) {
        Optional<Polzovateli> user = PolzovatelRepository.findByLogin(login);
        return user.isPresent() && !ProdavecRepository.findByPolzovatelid(user.get()).isEmpty();
    }

    @PostMapping("/isUpravlyayushchiy")
    public @ResponseBody
    boolean isUpravlyayushchiy(@RequestParam String login) {
        Optional<Polzovateli> user = PolzovatelRepository.findByLogin(login);
        return user.isPresent() && !UpravlyayushchiyRepository.findByPolzovatelid(user.get()).isEmpty();
    }
// Получение ID швеи по логину

    // Получение ID швеи по логину
    @PostMapping("/getShveyaIdByLogin")
    public @ResponseBody
    Map<String, Object> getShveyaIdByLogin1(@RequestParam(name = "login") String login) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Polzovateli> userOpt = PolzovatelRepository.findByLogin(login);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Пользователь не найден");
                return response;
            }

            Polzovateli user = userOpt.get();
            Optional<Shveya> shveyaOpt = ShveyaRepository.findByPolzovatelid_Id(user.getId());

            if (shveyaOpt.isPresent()) {
                response.put("success", true);
                response.put("shveyaId", shveyaOpt.get().getId());
                response.put("message", "ID швеи найден");
            } else {
                response.put("success", false);
                response.put("message", "Пользователь не является швеей");
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка поиска швеи: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/KolichestvoZakazovZaPeriod")
    public @ResponseBody
    Map<String, List<String>> KolichestvoZakazovZaPeriod(
            @RequestParam(name = "startDate") String startDateStr,
            @RequestParam(name = "endDate") String endDateStr) {

        Map<String, List<String>> result = new HashMap<>();
        List<String> zakazyNaPoshiv = new ArrayList<>();
        List<String> zakazyTkani = new ArrayList<>();
        List<String> zakazyFurnitura = new ArrayList<>();
        List<String> obshayaStatistika = new ArrayList<>();

        try {
            // Парсим даты
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Добавляем время к конечной дате
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            endCal.set(Calendar.HOUR_OF_DAY, 23);
            endCal.set(Calendar.MINUTE, 59);
            endCal.set(Calendar.SECOND, 59);
            endCal.set(Calendar.MILLISECOND, 999);
            Date endDateWithTime = endCal.getTime();

            // Счетчики для общей статистики
            int totalPoshiv = 0;
            int totalTkani = 0;
            int totalFurnitura = 0;
            Set<Object> uniqueZakazIds = new HashSet<>(); // Используем Object вместо Long
            Map<String, Integer> statusStats = new HashMap<>();

            // Обрабатываем заказы на пошив
            for (Zakaznaposhiv zakaz : ZakazNaPoshivRepository.findAll()) {
                Date zakazData = zakaz.getData();

                if ((zakazData.after(startDate) || zakazData.equals(startDate))
                        && (zakazData.before(endDateWithTime) || zakazData.equals(endDateWithTime))) {

                    totalPoshiv++;
                    uniqueZakazIds.add(zakaz.getId()); // Просто добавляем ID как Object

                    // Статистика по статусам
                    String status = zakaz.getIdStatusRaboty().getNazvanie();
                    statusStats.put(status, statusStats.getOrDefault(status, 0) + 1);

                    String klient = zakaz.getIdKlient().getFamiliya() + " "
                            + zakaz.getIdKlient().getImya() + " "
                            + (zakaz.getIdKlient().getOtchestvo() != null ? zakaz.getIdKlient().getOtchestvo() : "");
                    String izdelie = zakaz.getIdIzdelie().getNazvanie();
                    BigDecimal tsenaRaboty = zakaz.getTsenaRabotyShvei();
                    String shveya = zakaz.getIdShveya() != null
                            ? zakaz.getIdShveya().getPolzovatelid().getFamiliya() + " "
                            + zakaz.getIdShveya().getPolzovatelid().getImya() : "Не назначена";

                    String info = String.format("Заказ №%d от %s: Клиент: %s, Изделие: %s, Статус: %s, Цена: %.2f руб., Швея: %s",
                            zakaz.getId(),
                            dateFormat.format(zakazData),
                            klient.trim(),
                            izdelie,
                            status,
                            tsenaRaboty,
                            shveya);

                    zakazyNaPoshiv.add(info);
                }
            }

            // Обрабатываем заказы тканей
            for (Zakaztkan zakazTkan : ZakaztkanRepository.findAll()) {
                Date zakazData = zakazTkan.getIdZakaz().getData();

                if ((zakazData.after(startDate) || zakazData.equals(startDate))
                        && (zakazData.before(endDateWithTime) || zakazData.equals(endDateWithTime))) {

                    totalTkani++;
                    uniqueZakazIds.add(zakazTkan.getIdZakaz().getId()); // Просто добавляем ID как Object

                    String tkanNazvanie = zakazTkan.getIdTkan().getNazvanie();
                    BigDecimal kolichestvo = zakazTkan.getKolichestvoTkaniM();
                    String zakazInfo = "Заказ №" + zakazTkan.getIdZakaz().getId();

                    String info = String.format("%s: Ткань: %s, Количество: %.2f м, Дата заказа: %s",
                            zakazInfo,
                            tkanNazvanie,
                            kolichestvo,
                            dateFormat.format(zakazData));

                    zakazyTkani.add(info);
                }
            }

            // Обрабатываем заказы фурнитуры
            for (Zakazfurnitura zakazFurnitura : ZakazFurnituraRepository.findAll()) {
                Date zakazData = zakazFurnitura.getIdZakaz().getData();

                if ((zakazData.after(startDate) || zakazData.equals(startDate))
                        && (zakazData.before(endDateWithTime) || zakazData.equals(endDateWithTime))) {

                    totalFurnitura++;
                    uniqueZakazIds.add(zakazFurnitura.getIdZakaz().getId()); // Просто добавляем ID как Object

                    String furnituraNazvanie = zakazFurnitura.getIdFurnitura().getNazvanie();
                    Integer kolichestvo = zakazFurnitura.getKolichestvo();
                    String zakazInfo = "Заказ №" + zakazFurnitura.getIdZakaz().getId();

                    String info = String.format("%s: Фурнитура: %s, Количество: %d шт, Дата заказа: %s",
                            zakazInfo,
                            furnituraNazvanie,
                            kolichestvo,
                            dateFormat.format(zakazData));

                    zakazyFurnitura.add(info);
                }
            }

            // Формируем общую статистику
            obshayaStatistika.add("=== ОБЩАЯ СТАТИСТИКА ЗА ПЕРИОД ===");
            obshayaStatistika.add("Период: с " + startDateStr + " по " + endDateStr);
            obshayaStatistika.add("Всего уникальных заказов: " + uniqueZakazIds.size());
            obshayaStatistika.add("Заказов на пошив: " + totalPoshiv);
            obshayaStatistika.add("Записей тканей: " + totalTkani);
            obshayaStatistika.add("Записей фурнитуры: " + totalFurnitura);

            // Добавляем статистику по статусам
            if (!statusStats.isEmpty()) {
                obshayaStatistika.add("--- Статистика по статусам ---");
                for (Map.Entry<String, Integer> entry : statusStats.entrySet()) {
                    obshayaStatistika.add("Статус '" + entry.getKey() + "': " + entry.getValue() + " заказов");
                }
            }

            // Добавляем сообщения если данных нет
            if (zakazyNaPoshiv.isEmpty()) {
                zakazyNaPoshiv.add("Заказы на пошив за период с " + startDateStr + " по " + endDateStr + " не найдены");
            }

            if (zakazyTkani.isEmpty()) {
                zakazyTkani.add("Заказы тканей за период с " + startDateStr + " по " + endDateStr + " не найдены");
            }

            if (zakazyFurnitura.isEmpty()) {
                zakazyFurnitura.add("Заказы фурнитуры за период с " + startDateStr + " по " + endDateStr + " не найдены");
            }

        } catch (ParseException e) {
            String errorMsg = "Ошибка формата даты. Используйте формат: ГГГГ-ММ-ДД";
            zakazyNaPoshiv.add(errorMsg);
            zakazyTkani.add(errorMsg);
            zakazyFurnitura.add(errorMsg);
            obshayaStatistika.add(errorMsg);
        } catch (Exception e) {
            String errorMsg = "Ошибка при обработке данных: " + e.getMessage();
            zakazyNaPoshiv.add(errorMsg);
            zakazyTkani.add(errorMsg);
            zakazyFurnitura.add(errorMsg);
            obshayaStatistika.add(errorMsg);
        }

        result.put("zakazyNaPoshiv", zakazyNaPoshiv);
        result.put("zakazyTkani", zakazyTkani);
        result.put("zakazyFurnitura", zakazyFurnitura);
        result.put("obshayaStatistika", obshayaStatistika);

        return result;
    }

    @PostMapping("/ZakazyZaMesiac")
    public @ResponseBody
    List<String> ZakazyZaMesiac(
            @RequestParam(name = "month") int month, // Принимаем номер месяца (1-12)
            @RequestParam(name = "year", required = false) Integer year) {  // Год опционально

        List<String> zakazy = new ArrayList<>();

        // Если год не указан, используем текущий год
        if (year == null) {
            Calendar currentCalendar = Calendar.getInstance();
            year = currentCalendar.get(Calendar.YEAR);
        }

        // Преобразуем номер месяца (1-12) в формат Calendar (0-11)
        int inputMonth = month - 1;

        for (Zakaznaposhiv zakaz : ZakazNaPoshivRepository.findAll()) {
            Date zakazData = zakaz.getData();
            Calendar zakazCalendar = Calendar.getInstance();
            zakazCalendar.setTime(zakazData);
            int zakazMonth = zakazCalendar.get(Calendar.MONTH);
            int zakazYear = zakazCalendar.get(Calendar.YEAR);

            // Сравниваем месяц и год
            if (zakazMonth == inputMonth && zakazYear == year) {
                String klient = zakaz.getIdKlient().getFamiliya() + " "
                        + zakaz.getIdKlient().getImya() + " "
                        + (zakaz.getIdKlient().getOtchestvo() != null ? zakaz.getIdKlient().getOtchestvo() : "");
                String izdelie = zakaz.getIdIzdelie().getNazvanie();
                String status = zakaz.getIdStatusRaboty().getNazvanie();
                BigDecimal tsenaRaboty = zakaz.getTsenaRabotyShvei();
                String shveya = zakaz.getIdShveya() != null
                        ? zakaz.getIdShveya().getPolzovatelid().getFamiliya() + " "
                        + zakaz.getIdShveya().getPolzovatelid().getImya() : "Не назначена";

                // Получаем информацию о тканях для этого заказа
                List<String> tkaniInfo = new ArrayList<>();
                for (Zakaztkan zakazTkan : ZakaztkanRepository.findByIdZakaz(zakaz)) {
                    String tkanNazvanie = zakazTkan.getIdTkan().getNazvanie();
                    BigDecimal kolichestvo = zakazTkan.getKolichestvoTkaniM();
                    tkaniInfo.add(tkanNazvanie + " (" + kolichestvo + " м)");
                }

                // Получаем информацию о фурнитуре для этого заказа
                List<String> furnituraInfo = new ArrayList<>();
                for (Zakazfurnitura zakazFurnitura : ZakazFurnituraRepository.findByIdZakaz(zakaz)) {
                    String furnituraNazvanie = zakazFurnitura.getIdFurnitura().getNazvanie();
                    Integer kolichestvo = zakazFurnitura.getKolichestvo();
                    furnituraInfo.add(furnituraNazvanie + " (" + kolichestvo + " шт)");
                }

                String info = String.format("Заказ №%d от %s: Клиент: %s, Изделие: %s, Статус: %s, Цена работы: %.2f руб., Швея: %s, Ткани: %s, Фурнитура: %s",
                        zakaz.getId(),
                        new SimpleDateFormat("yyyy-MM-dd").format(zakazData),
                        klient.trim(),
                        izdelie,
                        status,
                        tsenaRaboty,
                        shveya,
                        tkaniInfo.isEmpty() ? "нет" : String.join(", ", tkaniInfo),
                        furnituraInfo.isEmpty() ? "нет" : String.join(", ", furnituraInfo));

                zakazy.add(info);
            }
        }

        // Добавляем общую статистику
        if (!zakazy.isEmpty()) {
            int totalZakazy = zakazy.size();
            String monthName = getMonthName(month);
            zakazy.add(0, "=== ОБЩАЯ СТАТИСТИКА ЗА " + monthName.toUpperCase() + " " + year + " ===");
            zakazy.add(1, "Всего заказов: " + totalZakazy);

            // Статистика по статусам
            Map<String, Integer> statusStats = new HashMap<>();
            for (Zakaznaposhiv zakaz : ZakazNaPoshivRepository.findAll()) {
                Date zakazData = zakaz.getData();
                Calendar zakazCalendar = Calendar.getInstance();
                zakazCalendar.setTime(zakazData);
                int zakazMonth = zakazCalendar.get(Calendar.MONTH);
                int zakazYear = zakazCalendar.get(Calendar.YEAR);

                if (zakazMonth == inputMonth && zakazYear == year) {
                    String status = zakaz.getIdStatusRaboty().getNazvanie();
                    statusStats.put(status, statusStats.getOrDefault(status, 0) + 1);
                }
            }

            for (Map.Entry<String, Integer> entry : statusStats.entrySet()) {
                zakazy.add("Статус '" + entry.getKey() + "': " + entry.getValue() + " заказов");
            }

        } else {
            String monthName = getMonthName(month);
            zakazy.add("Заказы за " + monthName + " " + year + " года не найдены");
        }

        return zakazy;
    }

    @PostMapping("/VseZakazy")
    public @ResponseBody
    List<String> VseZakazy() {
        List<String> vseZakazy = new ArrayList<>();

        for (Zakaznaposhiv zakaz : ZakazNaPoshivRepository.findAll()) {
            // Основная информация о заказе
            String osnovnayaInfo = String.format("Заказ №%d от %s: ",
                    zakaz.getId(),
                    zakaz.getData().toString());

            // Информация о клиенте
            String klientInfo = "";
            if (zakaz.getIdKlient() != null) {
                Klient klient = zakaz.getIdKlient();
                klientInfo = String.format("Клиент: %s %s %s, ",
                        klient.getFamiliya(),
                        klient.getImya(),
                        klient.getOtchestvo() != null ? klient.getOtchestvo() : "");
            }

            // Информация об изделии
            String izdelieInfo = "";
            if (zakaz.getIdIzdelie() != null) {
                Izdelie izdelie = zakaz.getIdIzdelie();
                izdelieInfo = String.format("Изделие: %s (%.2f руб), ",
                        izdelie.getNazvanie(),
                        izdelie.getTsena());
            }

            // Информация о статусе
            String statusInfo = "";
            if (zakaz.getIdStatusRaboty() != null) {
                statusInfo = String.format("Статус: %s, ",
                        zakaz.getIdStatusRaboty().getNazvanie());
            }

            // Информация о швее
            String shveyaInfo = "";
            if (zakaz.getIdShveya() != null && zakaz.getIdShveya().getPolzovatelid() != null) {
                Polzovateli shveya = zakaz.getIdShveya().getPolzovatelid();
                shveyaInfo = String.format("Швея: %s %s, ",
                        shveya.getFamiliya(),
                        shveya.getImya());
            }

            // Собираем ткани для этого заказа
            List<String> tkaniList = new ArrayList<>();
            for (Zakaztkan zakazTkan : ZakaztkanRepository.findByIdZakaz(zakaz)) {
                if (zakazTkan.getIdTkan() != null) {
                    Tkan tkan = zakazTkan.getIdTkan();
                    tkaniList.add(String.format("%s (%.2f м)",
                            tkan.getNazvanie(),
                            zakazTkan.getKolichestvoTkaniM()));
                }
            }
            String tkaniInfo = tkaniList.isEmpty() ? ""
                    : String.format("Ткани: %s, ", String.join("; ", tkaniList));

            // Собираем фурнитуру для этого заказа
            List<String> furnituraList = new ArrayList<>();
            for (Zakazfurnitura zakazFurnitura : ZakazFurnituraRepository.findByIdZakaz(zakaz)) {
                if (zakazFurnitura.getIdFurnitura() != null) {
                    Furnitura furnitura = zakazFurnitura.getIdFurnitura();
                    furnituraList.add(String.format("%s (%d шт)",
                            furnitura.getNazvanie(),
                            zakazFurnitura.getKolichestvo()));
                }
            }
            String furnituraInfo = furnituraList.isEmpty() ? ""
                    : String.format("Фурнитура: %s, ", String.join("; ", furnituraList));

            // Информация о стоимости и скидке
            String stoimostInfo = "";
            if (zakaz.getTsenaRabotyShvei() != null) {
                BigDecimal osnovnayaStoimost = zakaz.getTsenaRabotyShvei();
                BigDecimal skidkaProcent = BigDecimal.ZERO;

                if (zakaz.getIdSkidka() != null) {
                    skidkaProcent = BigDecimal.valueOf(zakaz.getIdSkidka().getProcent());
                }

                BigDecimal skidkaSumma = osnovnayaStoimost.multiply(skidkaProcent)
                        .divide(BigDecimal.valueOf(100));
                BigDecimal itogovayaStoimost = osnovnayaStoimost.subtract(skidkaSumma);

                stoimostInfo = String.format("Стоимость работы: %.2f руб, Скидка: %.0f%%, Итого: %.2f руб",
                        osnovnayaStoimost,
                        skidkaProcent,
                        itogovayaStoimost);
            }

            // Формируем полную строку заказа
            String polnayaInfo = osnovnayaInfo + klientInfo + izdelieInfo + statusInfo
                    + shveyaInfo + tkaniInfo + furnituraInfo + stoimostInfo;

            vseZakazy.add(polnayaInfo);
        }

        return vseZakazy;
    }

    @PostMapping("/ProdazhiZaPeriod")
    public @ResponseBody
    Map<String, List<String>> ProdazhiZaPeriod(
            @RequestParam(name = "startDate") String startDateStr,
            @RequestParam(name = "endDate") String endDateStr) {

        Map<String, List<String>> result = new HashMap<>();
        List<String> prodazhaTkani = new ArrayList<>();
        List<String> prodazhafurnitury = new ArrayList<>();

        try {
            // Парсим даты
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Добавляем время к конечной дате
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            endCal.set(Calendar.HOUR_OF_DAY, 23);
            endCal.set(Calendar.MINUTE, 59);
            endCal.set(Calendar.SECOND, 59);
            endCal.set(Calendar.MILLISECOND, 999);
            Date endDateWithTime = endCal.getTime();

            // Обрабатываем продажи ткани
            for (Prodazhatkani prodazha : ProdazhaTkaniRepository.findAll()) {
                Date prodazhaData = prodazha.getData();

                if ((prodazhaData.after(startDate) || prodazhaData.equals(startDate))
                        && (prodazhaData.before(endDateWithTime) || prodazhaData.equals(endDateWithTime))) {

                    String tkan = prodazha.getIdTkan().getNazvanie();
                    Integer count = prodazha.getKolichestvo();
                    BigDecimal tsena = prodazha.getTsenaZaRulon();
                    BigDecimal total = tsena.multiply(BigDecimal.valueOf(count));

                    String info = String.format("Ткань: %s, Количество: %d рулонов, Цена за рулон: %.2f, Сумма: %.2f, Дата: %s",
                            tkan, count, tsena, total, dateFormat.format(prodazhaData));
                    prodazhaTkani.add(info);
                }
            }

            // Обрабатываем продажи фурнитуры
            for (Prodazhafurnitury prodazha : ProdazhaFurnituryRepository.findAll()) {
                Date prodazhaData = prodazha.getData();

                if ((prodazhaData.after(startDate) || prodazhaData.equals(startDate))
                        && (prodazhaData.before(endDateWithTime) || prodazhaData.equals(endDateWithTime))) {

                    String furnitura = prodazha.getIdFurnitura().getNazvanie();
                    Integer count = prodazha.getKolichestvo();
                    BigDecimal tsena = prodazha.getTsenaZaYedinitsu();
                    BigDecimal total = tsena.multiply(BigDecimal.valueOf(count));

                    String info = String.format("Мебель: %s, Количество: %d, Цена: %.2f, Сумма: %.2f, Дата: %s",
                            furnitura, count, tsena, total, dateFormat.format(prodazhaData));
                    prodazhafurnitury.add(info);
                }
            }

            // Добавляем сообщения если данных нет
            if (prodazhaTkani.isEmpty()) {
                prodazhaTkani.add("Продажи ткани за период с " + startDateStr + " по " + endDateStr + " не найдены");
            }

            if (prodazhafurnitury.isEmpty()) {
                prodazhafurnitury.add("Продажи фурнитуры за период с " + startDateStr + " по " + endDateStr + " не найдены");
            }

        } catch (ParseException e) {
            prodazhaTkani.add("Ошибка формата даты. Используйте формат: ГГГГ-ММ-ДД");
            prodazhafurnitury.add("Ошибка формата даты. Используйте формат: ГГГГ-ММ-ДД");
        } catch (Exception e) {
            prodazhaTkani.add("Ошибка при обработке данных: " + e.getMessage());
            prodazhafurnitury.add("Ошибка при обработке данных: " + e.getMessage());
        }

        result.put("tkani", prodazhaTkani);
        result.put("furnitura", prodazhafurnitury);

        return result;
    }

    @PostMapping("/ProdazhatkaniZaMesiac")
    public @ResponseBody
    List<String> ProdazhatkaniZaMesiac(
            @RequestParam(name = "month") int month, // Принимаем номер месяца (1-12)
            @RequestParam(name = "year", required = false) Integer year) {  // Год опционально

        List<String> prodazhaTkani = new ArrayList<>();

        // Если год не указан, используем текущий год
        if (year == null) {
            Calendar currentCalendar = Calendar.getInstance();
            year = currentCalendar.get(Calendar.YEAR);
        }

        // Преобразуем номер месяца (1-12) в формат Calendar (0-11)
        int inputMonth = month - 1;

        for (Prodazhatkani prodazha : ProdazhaTkaniRepository.findAll()) {
            Date prodazhaData = prodazha.getData();
            Calendar prodazhaCalendar = Calendar.getInstance();
            prodazhaCalendar.setTime(prodazhaData);
            int prodazhaMonth = prodazhaCalendar.get(Calendar.MONTH);
            int prodazhaYear = prodazhaCalendar.get(Calendar.YEAR);

            // Сравниваем месяц и год
            if (prodazhaMonth == inputMonth && prodazhaYear == year) {
                String tkan = prodazha.getIdTkan().getNazvanie();
                Integer count = prodazha.getKolichestvo();
                BigDecimal tsena = prodazha.getTsenaZaRulon();
                BigDecimal total = tsena.multiply(BigDecimal.valueOf(count));

                String info = String.format("Ткань: %s, Количество: %d рулонов, Цена за рулон: %.2f, Сумма: %.2f",
                        tkan, count, tsena, total);
                prodazhaTkani.add(info);
            }
        }

        if (prodazhaTkani.isEmpty()) {
            String monthName = getMonthName(month);
            prodazhaTkani.add("Продажи ткани за " + monthName + " " + year + " года не найдены");
        }

        return prodazhaTkani;
    }

    @PostMapping("/ProdazhafurnituryZaMesiac")
    public @ResponseBody
    List<String> ProdazhafurnituryZaMesiac(
            @RequestParam(name = "month") int month, // Принимаем номер месяца (1-12)
            @RequestParam(name = "year", required = false) Integer year) {  // Год опционально

        List<String> prodazhafurnitury = new ArrayList<>();

        // Если год не указан, используем текущий год
        if (year == null) {
            Calendar currentCalendar = Calendar.getInstance();
            year = currentCalendar.get(Calendar.YEAR);
        }

        // Преобразуем номер месяца (1-12) в формат Calendar (0-11)
        int inputMonth = month - 1;

        for (Prodazhafurnitury prodazha : ProdazhaFurnituryRepository.findAll()) {
            Date prodazhaData = prodazha.getData();
            Calendar prodazhaCalendar = Calendar.getInstance();
            prodazhaCalendar.setTime(prodazhaData);
            int prodazhaMonth = prodazhaCalendar.get(Calendar.MONTH);
            int prodazhaYear = prodazhaCalendar.get(Calendar.YEAR);

            // Сравниваем месяц и год
            if (prodazhaMonth == inputMonth && prodazhaYear == year) {
                String furnitura = prodazha.getIdFurnitura().getNazvanie();
                Integer count = prodazha.getKolichestvo();
                BigDecimal tsena = prodazha.getTsenaZaYedinitsu();
                BigDecimal total = tsena.multiply(BigDecimal.valueOf(count));

                String info = String.format("Мебель: %s, Количество: %d, Цена: %.2f, Сумма: %.2f",
                        furnitura, count, tsena, total);
                prodazhafurnitury.add(info);
            }
        }

        if (prodazhafurnitury.isEmpty()) {
            String monthName = getMonthName(month);
            prodazhafurnitury.add("Продажи за " + monthName + " " + year + " года не найдены");
        }

        return prodazhafurnitury;
    }

// Вспомогательный метод для получения названия месяца
    private String getMonthName(int month) {
        String[] monthNames = {
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };
        return monthNames[month - 1];
    }

    @GetMapping("/allFurnituraNovinki")
    public @ResponseBody
    List<Furnitura> allFurnituraNovinki() {
        List<Furnitura> allFurnitura = (List<Furnitura>) FurnituraRepository.findAll();
        List<Furnitura> result = new ArrayList<>();
        for (Furnitura furnitura : allFurnitura) {
            if (furnitura.getNovinka() != null && furnitura.getNovinka() == true) {
                result.add(furnitura);
            }
        }
        return result;
    }
// Получение ID продавца по логину

    @PostMapping("/getProdavecIdByLogin")
    public @ResponseBody
    Map<String, Object> getProdavecIdByLogin(@RequestParam(name = "login") String login) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Polzovateli> userOpt = PolzovatelRepository.findByLogin(login);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Пользователь не найден");
                return response;
            }

            Polzovateli user = userOpt.get();
            Optional<Prodavec> prodavecOpt = ProdavecRepository.findByPolzovatelid_Id(user.getId());

            if (prodavecOpt.isPresent()) {
                response.put("success", true);
                response.put("prodavecId", prodavecOpt.get().getId());
                response.put("message", "ID продавца найден");
            } else {
                response.put("success", false);
                response.put("message", "Пользователь не является продавцом");
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка поиска продавца: " + e.getMessage());
        }

        return response;
    }

// Получение ID управляющего по логину
    @PostMapping("/getUpravlyayushchiyIdByLogin")
    public @ResponseBody
    Map<String, Object> getUpravlyayushchiyIdByLogin(@RequestParam(name = "login") String login) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Polzovateli> userOpt = PolzovatelRepository.findByLogin(login);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Пользователь не найден");
                return response;
            }

            Polzovateli user = userOpt.get();
            Optional<Upravlyayushchiy> upravOpt = UpravlyayushchiyRepository.findByPolzovatelid_Id(user.getId());

            if (upravOpt.isPresent()) {
                response.put("success", true);
                response.put("upravlyayushchiyId", upravOpt.get().getId());
                response.put("message", "ID управляющего найден");
            } else {
                response.put("success", false);
                response.put("message", "Пользователь не является управляющим");
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка поиска управляющего: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/allTkanNovinki")
    public @ResponseBody
    List<Tkan> allTkanNovinki() {
        List<Tkan> allTkan = (List<Tkan>) TkanRepository.findAll();
        List<Tkan> result = new ArrayList<>();
        for (Tkan tkan : allTkan) {
            if (tkan.getNovinka() != null && tkan.getNovinka() == true) {
                result.add(tkan);
            }
        }
        return result;
    }

    @GetMapping("/allKlient")
    public @ResponseBody
    List<Map<String, Object>> allKlient() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Klient klient : KlientRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", klient.getId());
            item.put("Familiya", klient.getFamiliya());
            item.put("Imya", klient.getImya());
            item.put("Otchestvo", klient.getOtchestvo());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addKlient")
    public @ResponseBody
    boolean addKlient(@RequestParam(name = "Familiya") String Familiya,
            @RequestParam(name = "Imya") String Imya,
            @RequestParam(name = "Otchestvo") String Otchestvo,
            @RequestParam(name = "Nomertelefona") String Nomertelefona,
            @RequestParam(name = "bonusi") String bonusi) {
        Klient klient = new Klient();
        klient.setFamiliya(Familiya);
        klient.setImya(Imya);
        klient.setOtchestvo(Otchestvo);
        klient.setNomertelefona(Nomertelefona);
        klient.setBonusi(Integer.parseInt(bonusi));
        KlientRepository.save(klient);
        return true;
    }

    @PostMapping("/deleteKlient")
    public @ResponseBody
    boolean deleteKlient(@RequestParam(name = "idKlient") String idKlient) {
        KlientRepository.deleteById(Integer.parseInt(idKlient));
        return true;
    }

    @PostMapping("/updateKlient")
    public @ResponseBody
    boolean updateKlient(@RequestParam(name = "Familiya") String Familiya,
            @RequestParam(name = "Imya") String Imya,
            @RequestParam(name = "Otchestvo") String Otchestvo,
            @RequestParam(name = "Nomertelefona") String Nomertelefona,
            @RequestParam(name = "bonusi") String bonusi,
            @RequestParam(name = "idKlient") String idKlient) {
        Klient klient = KlientRepository.findById(Integer.parseInt(idKlient)).get();
        klient.setFamiliya(Familiya);
        klient.setImya(Imya);
        klient.setOtchestvo(Otchestvo);
        klient.setNomertelefona(Nomertelefona);
        klient.setBonusi(Integer.parseInt(bonusi));
        KlientRepository.save(klient);
        return true;
    }

    @GetMapping("/allPolzovateli")
    public List<Polzovateli> getAllPolzovateli() {
        return PolzovatelRepository.findAll();
    }

    @GetMapping("/allPolzovateliWithRoles")
    public List<Map<String, Object>> allPolzovateliWithRoles() {
        List<Polzovateli> users = PolzovatelRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Polzovateli user : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("familiya", user.getFamiliya());
            userMap.put("imya", user.getImya());
            userMap.put("otchestvo", user.getOtchestvo() != null ? user.getOtchestvo() : "");
            userMap.put("pasport", user.getPasport() != null ? user.getPasport() : "");

            // Форматируем даты для отображения
            if (user.getDatarozhdeniya() != null) {
                userMap.put("datarozhdeniya_display", outputFormat.format(user.getDatarozhdeniya()));
                userMap.put("datarozhdeniya", inputFormat.format(user.getDatarozhdeniya()));
            } else {
                userMap.put("datarozhdeniya_display", "");
                userMap.put("datarozhdeniya", "");
            }

            userMap.put("nomertelefona", user.getNomertelefona() != null ? user.getNomertelefona() : "");

            if (user.getDatatrudoustroystva() != null) {
                userMap.put("datatrudoustroystva_display", outputFormat.format(user.getDatatrudoustroystva()));
                userMap.put("datatrudoustroystva", inputFormat.format(user.getDatatrudoustroystva()));
            } else {
                userMap.put("datatrudoustroystva_display", "");
                userMap.put("datatrudoustroystva", "");
            }

            userMap.put("login", user.getLogin());
            userMap.put("parol", user.getParol());
            userMap.put("foto", user.getFoto() != null ? user.getFoto() : "");

            // Определяем должность
            String dolzhnost = determineUserRole(user.getId());
            userMap.put("dolzhnost", dolzhnost);

            result.add(userMap);
        }

        return result;
    }

    @PostMapping("/ZakazInfo")
    public @ResponseBody
    List<ZakazInfoDto> ZakazInfo(@RequestParam(name = "id") String id) {
        List<ZakazInfoDto> resultList = new ArrayList<>();
        for (Zakaznaposhiv zakaz : ZakazNaPoshivRepository.findAll()) {
            ZakazInfoDto dto = new ZakazInfoDto();
            // ФИО клиента
            if (zakaz.getIdKlient() != null) {
                String fio = zakaz.getIdKlient().getFamiliya() + " "
                        + zakaz.getIdKlient().getImya() + " "
                        + zakaz.getIdKlient().getOtchestvo();
                dto.setFioKlienta(fio);
            }
            // Изделие клиента
            if (zakaz.getIdIzdelie() != null) {
                String izdelie = zakaz.getIdIzdelie().getNazvanie() + " "
                        + zakaz.getIdIzdelie().getOpisanie();
                dto.setIzdelieKlienta(izdelie);
            }
            // Цена заказа
            if (zakaz.getIdIzdelie() != null && zakaz.getIdIzdelie().getTsena() != null
                    && zakaz.getTsenaRabotyShvei() != null) {
                BigDecimal tsena = zakaz.getIdIzdelie().getTsena().add(zakaz.getTsenaRabotyShvei());
                dto.setTsenaZakaza(tsena.toString());
            }
            // Дата заказа
            if (zakaz.getData() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dataString = dateFormat.format(zakaz.getData());
                dto.setDataZakaza(dataString);
            }
            // Текщий статус заказа
            if (zakaz.getIdStatusRaboty() != null) {
                String status = zakaz.getIdStatusRaboty().getNazvanie();
                dto.setStatusZakaza(status);
            }
            resultList.add(dto);
        }
        return resultList;
    }

    public static class ZakazInfoDto {

        private String fioKlienta;
        private String izdelieKlienta;
        private String tsenaZakaza;
        private String dataZakaza;
        private String statuszakaza;

        // геттеры и сеттеры
        public String getFioKlienta() {
            return fioKlienta;
        }

        public void setFioKlienta(String fioKlienta) {
            this.fioKlienta = fioKlienta;
        }

        public String getIzdelieKlienta() {
            return izdelieKlienta;
        }

        public void setIzdelieKlienta(String izdelieKlienta) {
            this.izdelieKlienta = izdelieKlienta;
        }

        public String getTsenaZakaza() {
            return tsenaZakaza;
        }

        public void setTsenaZakaza(String tsenaZakaza) {
            this.tsenaZakaza = tsenaZakaza;
        }

        public String getDataZakaza() {
            return dataZakaza;
        }

        public void setDataZakaza(String dataZakaza) {
            this.dataZakaza = dataZakaza;
        }

        public String getStatusZakaza() {
            return statuszakaza;
        }

        public void setStatusZakaza(String statuszakaza) {
            this.statuszakaza = statuszakaza; // ИСПРАВЛЕНО: было this.statuszakaza = fioKlienta;
        }
    }

    // Продажи текущий месяц
    @GetMapping("/salesCurrentMonth")
    public List<Map<String, Object>> salesCurrentMonth() {
        List<Map<String, Object>> result = new ArrayList<>();

        // Продажи фурнитуры
        List<Prodazhafurnitury> furnituraSales = (List<Prodazhafurnitury>) ProdazhaFurnituryRepository.findAll();
        for (Prodazhafurnitury sale : furnituraSales) {
            if (isCurrentMonth(sale.getData())) {
                Map<String, Object> saleMap = new HashMap<>();
                saleMap.put("Дата", sale.getData());
                saleMap.put("Тип", "Фурнитура");
                saleMap.put("Продукт", sale.getIdFurnitura().getNazvanie());
                saleMap.put("Количество", sale.getKolichestvo());
                saleMap.put("Цена", sale.getTsenaZaYedinitsu());
                saleMap.put("Общая стоимость", sale.getKolichestvo() * sale.getTsenaZaYedinitsu().doubleValue());
                saleMap.put("Продавец", getSellerName(sale.getIdProdavets().getId()));
                result.add(saleMap);
            }
        }

        // Продажи тканей
        List<Prodazhatkani> tkaniSales = (List<Prodazhatkani>) ProdazhaTkaniRepository.findAll();
        for (Prodazhatkani sale : tkaniSales) {
            if (isCurrentMonth(sale.getData())) {
                Map<String, Object> saleMap = new HashMap<>();
                saleMap.put("Дата", sale.getData());
                saleMap.put("Тип", "Ткань");
                saleMap.put("Продукт", sale.getIdTkan().getNazvanie());
                saleMap.put("Количество", sale.getKolichestvo());
                saleMap.put("Цена", sale.getTsenaZaRulon());
                saleMap.put("Общая стоимость", sale.getKolichestvo() * sale.getTsenaZaRulon().doubleValue());
                saleMap.put("Продавец", getSellerName(sale.getIdProdavets().getId()));
                result.add(saleMap);
            }
        }

        return result;
    }

// Продажи за период
    @GetMapping("/salesByPeriod")
    public List<Map<String, Object>> salesByPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        List<Map<String, Object>> result = new ArrayList<>();

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);

            // Продажи фурнитуры за период
            List<Prodazhafurnitury> furnituraSales = (List<Prodazhafurnitury>) ProdazhaFurnituryRepository.findAll();
            for (Prodazhafurnitury sale : furnituraSales) {
                if (isDateInPeriod(sale.getData(), start, end)) {
                    Map<String, Object> saleMap = new HashMap<>();
                    saleMap.put("Дата", sale.getData());
                    saleMap.put("Тип", "Фурнитура");
                    saleMap.put("Продукт", sale.getIdFurnitura().getNazvanie());
                    saleMap.put("Количество", sale.getKolichestvo());
                    saleMap.put("Цена", sale.getTsenaZaYedinitsu());
                    saleMap.put("Общая стоимость", sale.getKolichestvo() * sale.getTsenaZaYedinitsu().doubleValue());
                    saleMap.put("Продавец", getSellerName(sale.getIdProdavets().getId()));
                    result.add(saleMap);
                }
            }

            // Продажи тканей за период
            List<Prodazhatkani> tkaniSales = (List<Prodazhatkani>) ProdazhaTkaniRepository.findAll();
            for (Prodazhatkani sale : tkaniSales) {
                if (isDateInPeriod(sale.getData(), start, end)) {
                    Map<String, Object> saleMap = new HashMap<>();
                    saleMap.put("Дата", sale.getData());
                    saleMap.put("Тип", "Ткань");
                    saleMap.put("Продукт", sale.getIdTkan().getNazvanie());
                    saleMap.put("Количество", sale.getKolichestvo());
                    saleMap.put("Цена", sale.getTsenaZaRulon());
                    saleMap.put("Общая стоимость", sale.getKolichestvo() * sale.getTsenaZaRulon().doubleValue());
                    saleMap.put("Продавец", getSellerName(sale.getIdProdavets().getId()));
                    result.add(saleMap);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

// Все заказы - ИСПРАВЛЕННЫЙ
    @GetMapping("/allOrders")
    public List<Map<String, Object>> allOrders() {
        List<Map<String, Object>> result = new ArrayList<>();

        try {
            List<Zakaznaposhiv> orders = (List<Zakaznaposhiv>) ZakazNaPoshivRepository.findAll();
            System.out.println("Найдено заказов: " + orders.size());

            for (Zakaznaposhiv order : orders) {
                try {
                    Map<String, Object> orderMap = new HashMap<>();
                    orderMap.put("Номер Заказа", order.getId());
                    orderMap.put("Дата заказа", order.getData());

                    // Безопасное получение клиента
                    if (order.getIdKlient() != null) {
                        orderMap.put("Клиент",
                                safeString(order.getIdKlient().getFamiliya()) + " "
                                + safeString(order.getIdKlient().getImya()));
                    } else {
                        orderMap.put("client_name", "Не указан");
                    }

                    // Безопасное получение изделия
                    if (order.getIdIzdelie() != null) {
                        orderMap.put("Название", safeString(order.getIdIzdelie().getNazvanie()));
                        orderMap.put("Цена продукта", order.getIdIzdelie().getTsena());
                    } else {
                        orderMap.put("product_name", "Не указано");
                        orderMap.put("product_price", 0);
                    }

                    orderMap.put("Стоимость работы", order.getTsenaRabotyShvei() != null ? order.getTsenaRabotyShvei() : 0);

                    // Безопасное получение скидки
                    if (order.getIdSkidka() != null) {
                        orderMap.put("Скидка", order.getIdSkidka().getProcent());
                    } else {
                        orderMap.put("discount_percent", 0);
                    }

                    orderMap.put("Общая стоимость", calculateTotalPrice(order));

                    // Безопасное получение статуса
                    if (order.getIdStatusRaboty() != null) {
                        orderMap.put("Статус", safeString(order.getIdStatusRaboty().getNazvanie()));
                    } else {
                        orderMap.put("status", "Не указан");
                    }

                    orderMap.put("Швея", getSeamstressName(order.getIdShveya() != null ? order.getIdShveya().getId() : null));

                    result.add(orderMap);
                } catch (Exception e) {
                    System.err.println("Ошибка обработки заказа " + order.getId() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка в allOrders: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

// Количество заказов текущий месяц
    @GetMapping("/ordersCountCurrentMonth")
    public Map<String, Object> ordersCountCurrentMonth() {
        Map<String, Object> result = new HashMap<>();
        int count = 0;

        List<Zakaznaposhiv> orders = (List<Zakaznaposhiv>) ZakazNaPoshivRepository.findAll();
        for (Zakaznaposhiv order : orders) {
            if (order.getData() != null && isCurrentMonth(order.getData())) {
                count++;
            }
        }

        result.put("period", "Текущий месяц");
        result.put("count", count);
        result.put("message", "Количество заказов за текущий месяц: " + count);

        return result;
    }

// Количество заказов за период
    @GetMapping("/ordersCountByPeriod")
    public Map<String, Object> ordersCountByPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        Map<String, Object> result = new HashMap<>();
        int count = 0;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);

            List<Zakaznaposhiv> orders = (List<Zakaznaposhiv>) ZakazNaPoshivRepository.findAll();
            for (Zakaznaposhiv order : orders) {
                if (order.getData() != null && isDateInPeriod(order.getData(), start, end)) {
                    count++;
                }
            }

            result.put("period", startDate + " - " + endDate);
            result.put("count", count);
            result.put("message", "Количество заказов за период: " + count);

        } catch (ParseException e) {
            result.put("error", "Неверный формат даты");
        }

        return result;
    }

// Вспомогательный метод для безопасного получения строк
    private String safeString(String value) {
        return value != null ? value : "";
    }

// Исправленный calculateTotalPrice
    private Double calculateTotalPrice(Zakaznaposhiv order) {
        if (order == null) {
            return 0.0;
        }

        double productPrice = 0;
        double workPrice = 0;
        double discount = 0;

        if (order.getIdIzdelie() != null && order.getIdIzdelie().getTsena() != null) {
            productPrice = order.getIdIzdelie().getTsena().doubleValue();
        }

        if (order.getTsenaRabotyShvei() != null) {
            workPrice = order.getTsenaRabotyShvei().doubleValue();
        }

        if (order.getIdSkidka() != null) {
            discount = order.getIdSkidka().getProcent();
        }

        return (productPrice + workPrice) * (1 - discount / 100);
    }
// Остатки материалов и фурнитуры

    @GetMapping("/materialsStock")
    public List<Map<String, Object>> materialsStock() {
        List<Map<String, Object>> result = new ArrayList<>();

        // Остатки тканей
        List<Tkan> fabrics = (List<Tkan>) TkanRepository.findAll();
        for (Tkan fabric : fabrics) {
            Map<String, Object> fabricMap = new HashMap<>();
            fabricMap.put("Артикул", fabric.getArtikul());
            fabricMap.put("Название", fabric.getNazvanie());
            fabricMap.put("Тип", "Ткань");
            fabricMap.put("Количество", calculateFabricStock(fabric.getId()));
            fabricMap.put("Единица Измерения", "рулон");
            result.add(fabricMap);
        }

        // Остатки фурнитуры
        List<Furnitura> furnitures = (List<Furnitura>) FurnituraRepository.findAll();
        for (Furnitura furniture : furnitures) {
            Map<String, Object> furnitureMap = new HashMap<>();
            furnitureMap.put("Артикул", furniture.getArtikul());
            furnitureMap.put("Название", furniture.getNazvanie());
            furnitureMap.put("Тип", "Фурнитура");
            furnitureMap.put("Количество", calculateFurnitureStock(furniture.getId()));
            furnitureMap.put("Единица Измерения", getUnitName(furniture.getIdEdIzmereniya()));
            result.add(furnitureMap);
        }

        return result;
    }

// Вспомогательные методы
    private boolean isCurrentMonth(Date date) {
        if (date == null) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        cal.setTime(date);
        int dateMonth = cal.get(Calendar.MONTH);
        int dateYear = cal.get(Calendar.YEAR);

        return dateMonth == currentMonth && dateYear == currentYear;
    }

    private boolean isDateInPeriod(Date date, Date start, Date end) {
        if (date == null) {
            return false;
        }
        return !date.before(start) && !date.after(end);
    }

    private String getSellerName(Integer sellerId) {
        if (sellerId == null) {
            return "";
        }

        Optional<Prodavec> seller = ProdavecRepository.findById(sellerId);
        if (seller.isPresent()) {
            Polzovateli user = seller.get().getPolzovatelid();
            return user.getFamiliya() + " " + user.getImya();
        }
        return "";
    }

    private String getSeamstressName(Integer seamstressId) {
        if (seamstressId == null) {
            return "";
        }

        Optional<Shveya> seamstress = ShveyaRepository.findById(seamstressId);
        if (seamstress.isPresent()) {
            Polzovateli user = seamstress.get().getPolzovatelid();
            return user.getFamiliya() + " " + user.getImya();
        }
        return "";
    }

    private Integer calculateFabricStock(Integer fabricId) {
        if (fabricId == null) {
            return 0;
        }

        List<Postavkatkani> supplies = PostavkaTkaniRepository.findByIdTkanId(fabricId);
        List<Prodazhatkani> sales = ProdazhaTkaniRepository.findByIdTkanId(fabricId);

        int totalSupply = 0;
        int totalSales = 0;

        if (supplies != null) {
            for (Postavkatkani supply : supplies) {
                if (supply.getKolichestvo() != null) {
                    totalSupply += supply.getKolichestvo();
                }
            }
        }

        if (sales != null) {
            for (Prodazhatkani sale : sales) {
                if (sale.getKolichestvo() != null) {
                    totalSales += sale.getKolichestvo();
                }
            }
        }

        return totalSupply - totalSales;
    }

    private Integer calculateFurnitureStock(Integer furnitureId) {
        if (furnitureId == null) {
            return 0;
        }

        List<Postavkafurnitury> supplies = PostavkaFurnituryRepository.findByIdFurnituraId(furnitureId);
        List<Prodazhafurnitury> sales = ProdazhaFurnituryRepository.findByIdFurnituraId(furnitureId);

        int totalSupply = 0;
        int totalSales = 0;

        if (supplies != null) {
            for (Postavkafurnitury supply : supplies) {
                if (supply.getKolichestvo() != null) {
                    totalSupply += supply.getKolichestvo();
                }
            }
        }

        if (sales != null) {
            for (Prodazhafurnitury sale : sales) {
                if (sale.getKolichestvo() != null) {
                    totalSales += sale.getKolichestvo();
                }
            }
        }

        return totalSupply - totalSales;
    }

    private String getUnitName(Edinitsyizmereniya unit) {
        if (unit == null) {
            return "шт";
        }
        return unit.getNazvanie() != null ? unit.getNazvanie() : "шт";
    }

    @PostMapping("/addPolzovatel")
    public Map<String, Object> addPolzovatel(
            @RequestParam String familiya,
            @RequestParam String imya,
            @RequestParam(required = false) String otchestvo,
            @RequestParam String login,
            @RequestParam String parol,
            @RequestParam(required = false) String nomertelefona,
            @RequestParam(required = false) String pasport,
            @RequestParam(required = false) String datarozhdeniya,
            @RequestParam(required = false) String datatrudoustroystva,
            @RequestParam(required = false) String foto,
            @RequestParam String dolzhnost) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Проверяем обязательные поля
            if (familiya == null || familiya.trim().isEmpty()
                    || imya == null || imya.trim().isEmpty()
                    || login == null || login.trim().isEmpty()
                    || parol == null || parol.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Заполните обязательные поля: Фамилия, Имя, Логин, Пароль");
                return response;
            }

            // Проверяем уникальность логина
            Optional<Polzovateli> existingUser = PolzovatelRepository.findByLogin(login.trim());
            if (existingUser.isPresent()) {
                response.put("success", false);
                response.put("message", "Пользователь с таким логином уже существует");
                return response;
            }

            Polzovateli polzovatel = new Polzovateli();
            polzovatel.setFamiliya(familiya.trim());
            polzovatel.setImya(imya.trim());
            polzovatel.setOtchestvo(otchestvo != null ? otchestvo.trim() : null);
            polzovatel.setLogin(login.trim());
            polzovatel.setParol(parol.trim());
            polzovatel.setNomertelefona(nomertelefona != null ? nomertelefona.trim() : null);
            polzovatel.setPasport(pasport != null ? pasport.trim() : null);

            // Обработка дат
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (datarozhdeniya != null && !datarozhdeniya.trim().isEmpty()) {
                try {
                    polzovatel.setDatarozhdeniya(format.parse(datarozhdeniya.trim()));
                } catch (ParseException e) {
                    response.put("success", false);
                    response.put("message", "Неверный формат даты рождения");
                    return response;
                }
            }

            if (datatrudoustroystva != null && !datatrudoustroystva.trim().isEmpty()) {
                try {
                    polzovatel.setDatatrudoustroystva(format.parse(datatrudoustroystva.trim()));
                } catch (ParseException e) {
                    response.put("success", false);
                    response.put("message", "Неверный формат даты трудоустройства");
                    return response;
                }
            }

            polzovatel.setFoto(foto != null ? foto.trim() : null);

            // Сохраняем пользователя
            Polzovateli savedUser = PolzovatelRepository.save(polzovatel);

            // Создаем роль если выбрана (не "Отсутствует")
            if (!"Отсутствует".equals(dolzhnost)) {
                boolean roleCreated = createUserRole(savedUser.getId(), dolzhnost);
                if (!roleCreated) {
                    PolzovatelRepository.delete(savedUser);
                    response.put("success", false);
                    response.put("message", "Ошибка при создании должности");
                    return response;
                }
            }

            response.put("success", true);
            response.put("message", "Пользователь успешно добавлен");
            response.put("id", savedUser.getId());

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка сервера: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("/updatePolzovatel")
    public Map<String, Object> updatePolzovatel(
            @RequestParam String idPolzovatel,
            @RequestParam String familiya,
            @RequestParam String imya,
            @RequestParam(required = false) String otchestvo,
            @RequestParam String login,
            @RequestParam(required = false) String parol,
            @RequestParam(required = false) String nomertelefona,
            @RequestParam(required = false) String pasport,
            @RequestParam(required = false) String datarozhdeniya,
            @RequestParam(required = false) String datatrudoustroystva,
            @RequestParam(required = false) String foto,
            @RequestParam String dolzhnost) {

        Map<String, Object> response = new HashMap<>();

        try {
            Integer userId = Integer.parseInt(idPolzovatel);
            Polzovateli polzovatel = PolzovatelRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            // Проверяем уникальность логина для других пользователей
            Optional<Polzovateli> existingUser = PolzovatelRepository.findByLoginAndIdNot(login.trim(), userId);
            if (existingUser.isPresent()) {
                response.put("success", false);
                response.put("message", "Пользователь с таким логином уже существует");
                return response;
            }

            // Обновляем основные данные
            polzovatel.setFamiliya(familiya.trim());
            polzovatel.setImya(imya.trim());
            polzovatel.setOtchestvo(otchestvo != null ? otchestvo.trim() : null);
            polzovatel.setLogin(login.trim());
            polzovatel.setNomertelefona(nomertelefona != null ? nomertelefona.trim() : null);
            polzovatel.setPasport(pasport != null ? pasport.trim() : null);

            // Обновляем пароль только если предоставлен
            if (parol != null && !parol.trim().isEmpty()) {
                polzovatel.setParol(parol.trim());
            }

            // Обработка дат
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (datarozhdeniya != null && !datarozhdeniya.trim().isEmpty()) {
                try {
                    polzovatel.setDatarozhdeniya(format.parse(datarozhdeniya.trim()));
                } catch (ParseException e) {
                    response.put("success", false);
                    response.put("message", "Неверный формат даты рождения");
                    return response;
                }
            } else {
                polzovatel.setDatarozhdeniya(null);
            }

            if (datatrudoustroystva != null && !datatrudoustroystva.trim().isEmpty()) {
                try {
                    polzovatel.setDatatrudoustroystva(format.parse(datatrudoustroystva.trim()));
                } catch (ParseException e) {
                    response.put("success", false);
                    response.put("message", "Неверный формат даты трудоустройства");
                    return response;
                }
            } else {
                polzovatel.setDatatrudoustroystva(null);
            }

            polzovatel.setFoto(foto != null ? foto.trim() : null);

            // Сохраняем обновленного пользователя
            PolzovatelRepository.save(polzovatel);

            // ВАЖНО: Полностью пересоздаем роли
            updateUserRoles(userId, dolzhnost);

            response.put("success", true);
            response.put("message", "Пользователь успешно обновлен");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка сервера: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    private void updateUserRoles(Integer userId, String newDolzhnost) {
        System.out.println("=== ОБНОВЛЕНИЕ РОЛЕЙ ДЛЯ ПОЛЬЗОВАТЕЛЯ " + userId + " ===");
        System.out.println("Новая роль: " + newDolzhnost);

        try {
            // 1. УДАЛЯЕМ ВСЕ СУЩЕСТВУЮЩИЕ РОЛИ
            System.out.println("Удаляем все существующие роли...");

            ProdavecRepository.deleteByUserId(userId);
            System.out.println("Удалено из продавцов");

            ShveyaRepository.deleteByUserId(userId);
            System.out.println("Удалено из швей");

            UpravlyayushchiyRepository.deleteByUserId(userId);
            System.out.println("Удалено из управляющих");

            // 2. ДОБАВЛЯЕМ НОВУЮ РОЛЬ (если не "Отсутствует")
            if (!"Отсутствует".equals(newDolzhnost)) {
                System.out.println("Добавляем новую роль: " + newDolzhnost);
                createUserRole(userId, newDolzhnost);
            } else {
                System.out.println("Роль установлена как 'Отсутствует'");
            }

            System.out.println("=== ОБНОВЛЕНИЕ РОЛЕЙ ЗАВЕРШЕНО ===");

        } catch (Exception e) {
            System.err.println("ОШИБКА при обновлении ролей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @PostMapping("/deletePolzovatel")
    public Map<String, Object> deletePolzovatel(@RequestParam String idPolzovatel) {
        Map<String, Object> response = new HashMap<>();

        try {
            Integer userId = Integer.parseInt(idPolzovatel);

            // Удаляем связанные записи в таблицах ролей
            deleteUserRoles(userId);

            // Удаляем пользователя
            PolzovatelRepository.deleteById(userId);

            response.put("success", true);
            response.put("message", "Пользователь успешно удален");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка при удалении: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }
// ДОБАВИТЬ ЭТОТ МЕТОД В КОНТРОЛЛЕР

    @PostMapping("/addRole")
    public @ResponseBody
    Map<String, Object> addRole(
            @RequestParam(name = "idPolzovatel") String idPolzovatel,
            @RequestParam(name = "role") String role) {

        Map<String, Object> response = new HashMap<>();

        try {
            Integer userId = Integer.parseInt(idPolzovatel);
            boolean success = createUserRole(userId, role);

            response.put("success", success);
            response.put("message", success ? "Роль успешно добавлена" : "Ошибка добавления роли");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка: " + e.getMessage());
        }

        return response;
    }

    // Вспомогательные методы
    private String determineUserRole(Integer userId) {
        if (ProdavecRepository.existsByPolzovatelid(userId)) {
            return "Продавец";
        } else if (ShveyaRepository.existsByPolzovatelid(userId)) {
            return "Швея";
        } else if (UpravlyayushchiyRepository.existsByPolzovatelid(userId)) {
            return "Управляющий";
        } else {
            return "Отсутствует";
        }
    }

    private boolean createUserRole(Integer userId, String dolzhnost) {
        try {
            Polzovateli user = PolzovatelRepository.findById(userId).orElse(null);
            if (user == null) {
                System.err.println("Пользователь не найден: " + userId);
                return false;
            }

            System.out.println("Создаем роль '" + dolzhnost + "' для пользователя " + userId);

            switch (dolzhnost.toLowerCase()) {
                case "продавец":
                    if (!ProdavecRepository.existsByPolzovatelid(userId)) {
                        Prodavec prodavec = new Prodavec();
                        prodavec.setPolzovatelid(user);
                        ProdavecRepository.save(prodavec);
                        System.out.println("Создан продавец");
                    }
                    break;
                case "швея":
                    if (!ShveyaRepository.existsByPolzovatelid(userId)) {
                        Shveya shveya = new Shveya();
                        shveya.setPolzovatelid(user);
                        ShveyaRepository.save(shveya);
                        System.out.println("Создана швея");
                    }
                    break;
                case "управляющий":
                    if (!UpravlyayushchiyRepository.existsByPolzovatelid(userId)) {
                        Upravlyayushchiy uprav = new Upravlyayushchiy();
                        uprav.setPolzovatelid(user);
                        UpravlyayushchiyRepository.save(uprav);
                        System.out.println("Создан управляющий");
                    }
                    break;
                default:
                    System.err.println("Неизвестная роль: " + dolzhnost);
                    return false;
            }
            return true;
        } catch (Exception e) {
            System.err.println("ОШИБКА создания роли: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void updateUserRole(Integer userId, String newDolzhnost) {
        try {
            System.out.println("Обновление роли для пользователя " + userId + " на: " + newDolzhnost);

            // Удаляем ВСЕ существующие роли
            deleteUserRoles(userId);

            // Создаем новую роль только если выбрана не "Отсутствует"
            if (!"Отсутствует".equals(newDolzhnost)) {
                boolean roleCreated = createUserRole(userId, newDolzhnost);
                System.out.println("Новая роль создана: " + roleCreated + " для пользователя: " + userId);
            } else {
                System.out.println("Роль установлена как 'Отсутствует' для пользователя: " + userId);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении роли: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/checkUserRoles")
    public Map<String, Boolean> checkUserRoles(@RequestParam Integer userId) {
        Map<String, Boolean> roles = new HashMap<>();
        roles.put("isProdavec", ProdavecRepository.existsByPolzovatelid(userId));
        roles.put("isShveya", ShveyaRepository.existsByPolzovatelid(userId));
        roles.put("isUpravlyayushchiy", UpravlyayushchiyRepository.existsByPolzovatelid(userId));
        return roles;
    }

    private void deleteUserRoles(Integer userId) {
        try {
            System.out.println("Удаление ролей для пользователя: " + userId);

            // Удаляем из всех таблиц ролей
            ProdavecRepository.deleteByPolzovatelidId(userId);
            ShveyaRepository.deleteByPolzovatelidId(userId);
            UpravlyayushchiyRepository.deleteByPolzovatelidId(userId);

            System.out.println("Роли успешно удалены для пользователя: " + userId);

        } catch (Exception e) {
            System.err.println("Ошибка при удалении ролей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/getUserPhoto")
    public String getUserPhoto(@RequestParam int id) {
        Optional<Polzovateli> user = PolzovatelRepository.findById(id);
        return user.map(Polzovateli::getFoto).orElse("");
    }

    @GetMapping("/getRole")
    public @ResponseBody
    String getRole(@RequestParam(name = "idPolzovatel") String idPolzovatel) {
        int id = Integer.parseInt(idPolzovatel);

        // Проверяем, является ли пользователь продавцом
        if (ProdavecRepository.existsByPolzovatelid(id)) {
            return "Продавец";
        } // Проверяем, является ли пользователем швеей
        else if (ShveyaRepository.existsByPolzovatelid(id)) {
            return "Швея";
        } // Проверяем, является ли пользователем управляющим
        else if (UpravlyayushchiyRepository.existsByPolzovatelid(id)) {
            return "Управляющий";
        } else {
            return "Пользователь";
        }
    }

// Получить ID последнего добавленного пользователя
    @GetMapping("/getLastUserId")
    public @ResponseBody
    int getLastUserId() {
        List<Polzovateli> users = (List<Polzovateli>) PolzovatelRepository.findAll();
        if (users.isEmpty()) {
            return -1;
        }
        return users.get(users.size() - 1).getId();
    }

// Проверить наличие пользователя в таблице
    @GetMapping("/checkUserInTable")
    public @ResponseBody
    boolean checkUserInTable(@RequestParam(name = "userId") int userId,
            @RequestParam(name = "table") String tableName) {
        switch (tableName.toLowerCase()) {
            case "prodavec":
                return ProdavecRepository.existsByPolzovatelid(userId);
            case "shveya":
                return ShveyaRepository.existsByPolzovatelid(userId);
            case "upravlyayushchiy":
                return UpravlyayushchiyRepository.existsByPolzovatelid(userId);
            default:
                return false;
        }
    }
// Получение ID швеи по логину

    @GetMapping("/getShveyaIdByLogin")
    public @ResponseBody
    Map<String, Object> getShveyaIdByLogin(@RequestParam(name = "login") String login) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("Поиск швеи по логину: " + login);

            // Находим пользователя по логину
            Optional<Polzovateli> userOpt = PolzovatelRepository.findByLogin(login);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Пользователь не найден");
                return response;
            }

            Polzovateli user = userOpt.get();
            System.out.println("Найден пользователь ID: " + user.getId());

            // Проверяем, является ли пользователь швеей
            Optional<Shveya> shveyaOpt = ShveyaRepository.findByPolzovatelid_Id(user.getId());
            if (!shveyaOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Пользователь не является швеей");
                return response;
            }

            Shveya shveya = shveyaOpt.get();
            System.out.println("Найдена швея ID: " + shveya.getId());

            response.put("success", true);
            response.put("shveyaId", shveya.getId());
            response.put("message", "ID швеи найден");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка поиска швеи: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/shveyaOrders")
    public @ResponseBody
    List<Map<String, Object>> getShveyaOrders(@RequestParam(name = "shveyaId") String shveyaId) {
        List<Map<String, Object>> result = new ArrayList<>();

        try {
            System.out.println("Запрос заказов для швеи ID: " + shveyaId);
            Integer id = Integer.parseInt(shveyaId);
            List<Zakaznaposhiv> orders = ZakazNaPoshivRepository.findByIdShveya_Id(id);

            System.out.println("Найдено заказов в БД: " + orders.size());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            for (Zakaznaposhiv order : orders) {
                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("id", order.getId());
                orderMap.put("nazvanieIzdelie", order.getIdIzdelie().getNazvanie());
                orderMap.put("klient", order.getIdKlient().getFamiliya() + " " + order.getIdKlient().getImya());
                orderMap.put("data", dateFormat.format(order.getData()));
                orderMap.put("status", order.getIdStatusRaboty().getNazvanie());
                orderMap.put("statusId", order.getIdStatusRaboty().getId());
                orderMap.put("tsenaRaboty", order.getTsenaRabotyShvei());

                System.out.println("Добавляем заказ: " + orderMap);
                result.add(orderMap);
            }

        } catch (Exception e) {
            System.out.println("Ошибка в shveyaOrders: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
// Получение всех статусов

    @GetMapping("/allStatusraboty")
    public @ResponseBody
    List<Map<String, Object>> allStatusraboty() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Statusraboty status : StatusrabotyRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", status.getId());
            item.put("nazvanie", status.getNazvanie());
            result.add(item);
        }
        return result;
    }

// Обновление статуса заказа
    @PostMapping("/updateOrderStatus")
    public @ResponseBody
    Map<String, Object> updateOrderStatus(
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "statusId") String statusId) {

        Map<String, Object> response = new HashMap<>();

        try {
            Zakaznaposhiv order = ZakazNaPoshivRepository.findById(Integer.parseInt(orderId)).get();
            Statusraboty newStatus = StatusrabotyRepository.findById(Integer.parseInt(statusId)).get();

            order.setIdStatusRaboty(newStatus);
            ZakazNaPoshivRepository.save(order);

            response.put("success", true);
            response.put("message", "Статус заказа успешно обновлен");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка обновления статуса: " + e.getMessage());
        }

        return response;
    }

// Получение материалов заказа
    @GetMapping("/orderMaterials")
    public @ResponseBody
    Map<String, Object> getOrderMaterials(@RequestParam(name = "orderId") String orderId) {
        Map<String, Object> result = new HashMap<>();

        try {
            Integer id = Integer.parseInt(orderId);

            List<Zakaztkan> tkani = ZakaztkanRepository.findByIdZakaz_Id(id);
            List<Zakazfurnitura> furnitura = ZakazFurnituraRepository.findByIdZakaz_Id(id);

            result.put("tkani", tkani);
            result.put("furnitura", furnitura);
            result.put("success", true);

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Ошибка загрузки материалов: " + e.getMessage());
        }

        return result;
    }

// Добавление фурнитуры к заказу
    @PostMapping("/addFurnituraToOrder")
    public @ResponseBody
    Map<String, Object> addFurnituraToOrder(
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "furnituraId") String furnituraId,
            @RequestParam(name = "kolichestvo") String kolichestvo) {

        Map<String, Object> response = new HashMap<>();

        try {
            Zakazfurnitura zakazFurnitura = new Zakazfurnitura();

            Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(furnituraId)).get();
            zakazFurnitura.setIdFurnitura(furnitura);

            Zakaznaposhiv zakaz = ZakazNaPoshivRepository.findById(Integer.parseInt(orderId)).get();
            zakazFurnitura.setIdZakaz(zakaz);

            zakazFurnitura.setKolichestvo(Integer.parseInt(kolichestvo));

            ZakazFurnituraRepository.save(zakazFurnitura);

            response.put("success", true);
            response.put("message", "Фурнитура успешно добавлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка добавления фурнитуры: " + e.getMessage());
        }

        return response;
    }

// Добавление ткани к заказу
    @PostMapping("/addTkanToOrder")
    public @ResponseBody
    Map<String, Object> addTkanToOrder(
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "tkanId") String tkanId,
            @RequestParam(name = "kolichestvo") String kolichestvo) {

        Map<String, Object> response = new HashMap<>();

        try {
            Zakaztkan zakazTkan = new Zakaztkan();

            Tkan tkan = TkanRepository.findById(Integer.parseInt(tkanId)).get();
            zakazTkan.setIdTkan(tkan);

            Zakaznaposhiv zakaz = ZakazNaPoshivRepository.findById(Integer.parseInt(orderId)).get();
            zakazTkan.setIdZakaz(zakaz);

            zakazTkan.setKolichestvoTkaniM(new BigDecimal(kolichestvo));

            ZakaztkanRepository.save(zakazTkan);

            response.put("success", true);
            response.put("message", "Ткань успешно добавлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка добавления ткани: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/allEdinitsyizmereniya")
    public @ResponseBody
    List<Map<String, Object>> allEdinitsyizmereniya() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Edinitsyizmereniya edinitsa : EdinitsyIzmerenyaRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", edinitsa.getId());
            item.put("nazvanie", edinitsa.getNazvanie());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addEdinitsaIzmereniya")
    public @ResponseBody
    boolean addEdinitsaIzmereniya(@RequestParam(name = "nazvanie") String nazvanie) {
        Edinitsyizmereniya edinitsa = new Edinitsyizmereniya();
        edinitsa.setNazvanie(nazvanie);
        EdinitsyIzmerenyaRepository.save(edinitsa);
        return true;
    }

    @PostMapping("/deleteEdinitsaIzmereniya")
    public @ResponseBody
    boolean deleteEdinitsaIzmereniya(@RequestParam(name = "idEdinitsa") String idEdinitsa) {
        EdinitsyIzmerenyaRepository.deleteById(Integer.parseInt(idEdinitsa));
        return true;
    }

    @PostMapping("/updateEdinitsaIzmereniya")
    public @ResponseBody
    boolean updateEdinitsaIzmereniya(@RequestParam(name = "nazvanie") String nazvanie,
            @RequestParam(name = "idEdinitsa") String idEdinitsa) {
        Edinitsyizmereniya edinitsa = EdinitsyIzmerenyaRepository.findById(Integer.parseInt(idEdinitsa)).get();
        edinitsa.setNazvanie(nazvanie);
        EdinitsyIzmerenyaRepository.save(edinitsa);
        return true;
    }

    @GetMapping("/allPostavkafurnitury")
    public @ResponseBody
    List<Postavkafurnitury> allPostavkafurnitury() {
        return (List<Postavkafurnitury>) PostavkaFurnituryRepository.findAll();
    }

    @PostMapping("/addPostavkafurnitury")
    public @ResponseBody
    boolean addPostavkafurnitury(
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idFurnitura") String idFurnitura,
            @RequestParam(name = "kolichestvo") String kolichestvo,
            @RequestParam(name = "tsenaZaYedinitsu") String tsenaZaYedinitsu) {

        Postavkafurnitury postavka = new Postavkafurnitury();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = format.parse(dataStr);

        } catch (ParseException ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        postavka.setData(data);

        Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(idFurnitura)).get();
        postavka.setIdFurnitura(furnitura);

        postavka.setKolichestvo(Integer.parseInt(kolichestvo));
        postavka.setTsenaZaYedinitsu(new BigDecimal(tsenaZaYedinitsu));

        PostavkaFurnituryRepository.save(postavka);
        return true;
    }

    @PostMapping("/updatePostavkafurnitury")
    public @ResponseBody
    boolean updatePostavkafurnitury(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idFurnitura") String idFurnitura,
            @RequestParam(name = "kolichestvo") String kolichestvo,
            @RequestParam(name = "tsenaZaYedinitsu") String tsenaZaYedinitsu) {

        Postavkafurnitury postavka = PostavkaFurnituryRepository.findById(Integer.parseInt(id)).get();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = format.parse(dataStr);

        } catch (ParseException ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        postavka.setData(data);

        Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(idFurnitura)).get();
        postavka.setIdFurnitura(furnitura);

        postavka.setKolichestvo(Integer.parseInt(kolichestvo));
        postavka.setTsenaZaYedinitsu(new BigDecimal(tsenaZaYedinitsu));

        PostavkaFurnituryRepository.save(postavka);
        return true;
    }

    @PostMapping("/deletePostavkafurnitury")
    public @ResponseBody
    boolean deletePostavkafurnitury(@RequestParam(name = "id") String id) {
        PostavkaFurnituryRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allFurnitura")
    public @ResponseBody
    List<Furnitura> allFurnitura() {
        return (List<Furnitura>) FurnituraRepository.findAll();
    }

    @PostMapping("/addFurnitura")
    public @ResponseBody
    Map<String, Object> addFurnitura(
            @RequestParam(name = "artikul") String artikul,
            @RequestParam(name = "nazvanie") String nazvanie,
            @RequestParam(name = "idTsvet") String idTsvet,
            @RequestParam(name = "novinka") String novinka,
            @RequestParam(name = "idEdIzmereniya") String idEdIzmereniya) {

        Map<String, Object> response = new HashMap<>();

        try {
            Furnitura furnitura = new Furnitura();
            furnitura.setArtikul(artikul);
            furnitura.setNazvanie(nazvanie);

            // Получаем объекты по внешним ключам
            Tsvetfurnitury tsvet = TsvetFurnituryRepository.findById(Integer.parseInt(idTsvet))
                    .orElseThrow(() -> new RuntimeException("Цвет фурнитуры не найден"));
            furnitura.setIdTsvet(tsvet);

            furnitura.setNovinka(Boolean.parseBoolean(novinka));

            Edinitsyizmereniya edIzmereniya = EdinitsyIzmerenyaRepository.findById(Integer.parseInt(idEdIzmereniya))
                    .orElseThrow(() -> new RuntimeException("Единица измерения не найдена"));
            furnitura.setIdEdIzmereniya(edIzmereniya);

            Furnitura savedFurnitura = FurnituraRepository.save(furnitura);

            response.put("success", true);
            response.put("message", "Фурнитура успешно добавлена");
            response.put("id", savedFurnitura.getId());

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка добавления фурнитуры: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/deleteFurnitura")
    public @ResponseBody
    Map<String, Object> deleteFurnitura(@RequestParam(name = "id") String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            FurnituraRepository.deleteById(Integer.parseInt(id));
            response.put("success", true);
            response.put("message", "Фурнитура успешно удалена");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка удаления фурнитуры: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/allIzdelie")
    public @ResponseBody
    List<Map<String, Object>> allIzdelie() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Izdelie izdelie : IzdelieRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", izdelie.getId());
            item.put("Nazvanie", izdelie.getNazvanie());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addIzdelie")
    public @ResponseBody
    boolean addIzdelie(
            @RequestParam(name = "Nazvanie") String Nazvanie,
            @RequestParam(name = "Opisanie") String Opisanie,
            @RequestParam(name = "Tsena") String Tsena) {

        Izdelie izdelie = new Izdelie();
        izdelie.setNazvanie(Nazvanie);
        izdelie.setOpisanie(Opisanie);
        izdelie.setTsena(new BigDecimal(Tsena));

        IzdelieRepository.save(izdelie);
        return true;
    }

    @PostMapping("/updateIzdelie")
    public @ResponseBody
    boolean updateIzdelie(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "Nazvanie") String Nazvanie,
            @RequestParam(name = "Opisanie") String Opisanie,
            @RequestParam(name = "Tsena") String Tsena) {

        Izdelie izdelie = IzdelieRepository.findById(Integer.parseInt(id)).get();
        izdelie.setNazvanie(Nazvanie);
        izdelie.setOpisanie(Opisanie);
        izdelie.setTsena(new BigDecimal(Tsena));

        IzdelieRepository.save(izdelie);
        return true;
    }

    @PostMapping("/deleteIzdelie")
    public @ResponseBody
    boolean deleteIzdelie(@RequestParam(name = "id") String id) {
        IzdelieRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allPostavkatkani")
    public @ResponseBody
    List<Postavkatkani> allPostavkatkani() {
        return (List<Postavkatkani>) PostavkaTkaniRepository.findAll();
    }

    @PostMapping("/addPostavkatkani")
    public @ResponseBody
    boolean addPostavkatkani(
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idTkan") String idTkan,
            @RequestParam(name = "Kolichestvo") String Kolichestvo,
            @RequestParam(name = "tsenaZaRulon") String tsenaZaRulon,
            @RequestParam(name = "idRazmer") String idRazmer) {

        Postavkatkani postavka = new Postavkatkani();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = format.parse(dataStr);

        } catch (ParseException ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        postavka.setData(data);

        // Получаем объекты по внешним ключам
        Tkan tkan = TkanRepository.findById(Integer.parseInt(idTkan)).get();
        postavka.setIdTkan(tkan);

        postavka.setKolichestvo(Integer.parseInt(Kolichestvo));
        postavka.setTsenaZaRulon(new BigDecimal(tsenaZaRulon));

        Razmer razmer = RazmerRepository.findById(Integer.parseInt(idRazmer)).get();
        postavka.setIdRazmer(razmer);

        PostavkaTkaniRepository.save(postavka);
        return true;
    }

    @PostMapping("/updatePostavkatkani")
    public @ResponseBody
    boolean updatePostavkatkani(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idTkan") String idTkan,
            @RequestParam(name = "Kolichestvo") String Kolichestvo,
            @RequestParam(name = "tsenaZaRulon") String tsenaZaRulon,
            @RequestParam(name = "idRazmer") String idRazmer) {

        Postavkatkani postavka = PostavkaTkaniRepository.findById(Integer.parseInt(id)).get();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = format.parse(dataStr);

        } catch (ParseException ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        postavka.setData(data);

        // Получаем объекты по внешним ключам
        Tkan tkan = TkanRepository.findById(Integer.parseInt(idTkan)).get();
        postavka.setIdTkan(tkan);

        postavka.setKolichestvo(Integer.parseInt(Kolichestvo));
        postavka.setTsenaZaRulon(new BigDecimal(tsenaZaRulon));

        Razmer razmer = RazmerRepository.findById(Integer.parseInt(idRazmer)).get();
        postavka.setIdRazmer(razmer);

        PostavkaTkaniRepository.save(postavka);
        return true;
    }

    @PostMapping("/deletePostavkatkani")
    public @ResponseBody
    boolean deletePostavkatkani(@RequestParam(name = "id") String id) {
        PostavkaTkaniRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allProdazhafurnitury")
    public @ResponseBody
    List<Prodazhafurnitury> allProdazhafurnitury() {
        return (List<Prodazhafurnitury>) ProdazhaFurnituryRepository.findAll();
    }

    @PostMapping("/addProdazhafurnitury")
    public @ResponseBody
    Map<String, Object> addProdazhafurnitury(
            @RequestParam(name = "idProdavets") String idProdavets,
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idFurnitura") String idFurnitura,
            @RequestParam(name = "Kolichestvo") String Kolichestvo,
            @RequestParam(name = "tsenaZaYedinitsu") String tsenaZaYedinitsu) {

        Map<String, Object> response = new HashMap<>();

        try {
            Prodazhafurnitury prodazha = new Prodazhafurnitury();

            // Получаем объекты по внешним ключам
            Prodavec prodavets = ProdavecRepository.findById(Integer.parseInt(idProdavets))
                    .orElseThrow(() -> new RuntimeException("Продавец не найден"));
            prodazha.setIdProdavets(prodavets);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataStr);
            prodazha.setData(data);

            Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(idFurnitura))
                    .orElseThrow(() -> new RuntimeException("Фурнитура не найдена"));
            prodazha.setIdFurnitura(furnitura);

            prodazha.setKolichestvo(Integer.parseInt(Kolichestvo));
            prodazha.setTsenaZaYedinitsu(new BigDecimal(tsenaZaYedinitsu));

            ProdazhaFurnituryRepository.save(prodazha);

            response.put("success", true);
            response.put("message", "Продажа фурнитуры успешно добавлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка добавления продажи фурнитуры: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/deleteProdazhafurnitury")
    public @ResponseBody
    Map<String, Object> deleteProdazhafurnitury(@RequestParam(name = "id") String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            ProdazhaFurnituryRepository.deleteById(Integer.parseInt(id));
            response.put("success", true);
            response.put("message", "Продажа фурнитуры успешно удалена");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка удаления продажи фурнитуры: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/addProdazhatkani")
    public @ResponseBody
    Map<String, Object> addProdazhatkani(
            @RequestParam(name = "idProdavets") String idProdavets,
            @RequestParam(name = "data") String dataStr,
            @RequestParam(name = "idTkan") String idTkan,
            @RequestParam(name = "Kolichestvo") String Kolichestvo,
            @RequestParam(name = "tsenaZaRulon") String tsenaZaRulon,
            @RequestParam(name = "idRazmer") String idRazmer) {

        Map<String, Object> response = new HashMap<>();

        try {
            Prodazhatkani prodazha = new Prodazhatkani();

            // Получаем объекты по внешним ключам
            Prodavec prodavets = ProdavecRepository.findById(Integer.parseInt(idProdavets))
                    .orElseThrow(() -> new RuntimeException("Продавец не найден"));
            prodazha.setIdProdavets(prodavets);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataStr);
            prodazha.setData(data);

            Tkan tkan = TkanRepository.findById(Integer.parseInt(idTkan))
                    .orElseThrow(() -> new RuntimeException("Ткань не найдена"));
            prodazha.setIdTkan(tkan);

            prodazha.setKolichestvo(Integer.parseInt(Kolichestvo));
            prodazha.setTsenaZaRulon(new BigDecimal(tsenaZaRulon));

            Razmer razmer = RazmerRepository.findById(Integer.parseInt(idRazmer))
                    .orElseThrow(() -> new RuntimeException("Размер не найден"));
            prodazha.setIdRazmer(razmer);

            ProdazhaTkaniRepository.save(prodazha);

            response.put("success", true);
            response.put("message", "Продажа ткани успешно добавлена");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка добавления продажи ткани: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/deleteProdazhatkani")
    public @ResponseBody
    Map<String, Object> deleteProdazhatkani(@RequestParam(name = "id") String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            ProdazhaTkaniRepository.deleteById(Integer.parseInt(id));
            response.put("success", true);
            response.put("message", "Продажа ткани успешно удалена");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка удаления продажи ткани: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/allProdazhatkani")
    public @ResponseBody
    List<Prodazhatkani> allProdazhatkani() {
        return (List<Prodazhatkani>) ProdazhaTkaniRepository.findAll();
    }

    @GetMapping("/allRastsvetka")
    public @ResponseBody
    List<Map<String, Object>> allRastsvetka() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Rastsvetka rastsvetka : RastsvetkaRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", rastsvetka.getId());
            item.put("nazvanie", rastsvetka.getNazvanie());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addRastsvetka")
    public @ResponseBody
    boolean addRastsvetka(@RequestParam(name = "nazvanie") String nazvanie) {
        Rastsvetka rastsvetka = new Rastsvetka();
        rastsvetka.setNazvanie(nazvanie);
        RastsvetkaRepository.save(rastsvetka);
        return true;
    }

    @PostMapping("/updateRastsvetka")
    public @ResponseBody
    boolean updateRastsvetka(@RequestParam(name = "id") String id,
            @RequestParam(name = "nazvanie") String nazvanie) {
        Rastsvetka rastsvetka = RastsvetkaRepository.findById(Integer.parseInt(id)).get();
        rastsvetka.setNazvanie(nazvanie);
        RastsvetkaRepository.save(rastsvetka);
        return true;
    }

    @PostMapping("/deleteRastsvetka")
    public @ResponseBody
    boolean deleteRastsvetka(@RequestParam(name = "id") String id) {
        RastsvetkaRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allRazmer")
    public @ResponseBody
    List<Map<String, Object>> allRazmer() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Razmer razmer : RazmerRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", razmer.getId());
            item.put("Shirina", razmer.getShirina());
            item.put("Dlina", razmer.getDlina());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addRazmer")
    public @ResponseBody
    boolean addRazmer(@RequestParam(name = "Shirina") String Shirina,
            @RequestParam(name = "Dlina") String Dlina) {
        Razmer razmer = new Razmer();
        razmer.setShirina(Integer.parseInt(Shirina));
        razmer.setDlina(Integer.parseInt(Dlina));
        RazmerRepository.save(razmer);
        return true;
    }

    @PostMapping("/updateRazmer")
    public @ResponseBody
    boolean updateRazmer(@RequestParam(name = "id") String id,
            @RequestParam(name = "Shirina") String Shirina,
            @RequestParam(name = "Dlina") String Dlina) {
        Razmer razmer = RazmerRepository.findById(Integer.parseInt(id)).get();
        razmer.setShirina(Integer.parseInt(Shirina));
        razmer.setDlina(Integer.parseInt(Dlina));
        RazmerRepository.save(razmer);
        return true;
    }

    @PostMapping("/deleteRazmer")
    public @ResponseBody
    boolean deleteRazmer(@RequestParam(name = "id") String id) {
        RazmerRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @PostMapping("/addStatusraboty")
    public @ResponseBody
    boolean addStatusraboty(@RequestParam(name = "nazvanie") String nazvanie) {
        Statusraboty status = new Statusraboty();
        status.setNazvanie(nazvanie);
        StatusrabotyRepository.save(status);
        return true;
    }

    @PostMapping("/updateStatusraboty")
    public @ResponseBody
    boolean updateStatusraboty(@RequestParam(name = "id") String id,
            @RequestParam(name = "nazvanie") String nazvanie) {
        Statusraboty status = StatusrabotyRepository.findById(Integer.parseInt(id)).get();
        status.setNazvanie(nazvanie);
        StatusrabotyRepository.save(status);
        return true;
    }

    @PostMapping("/deleteStatusraboty")
    public @ResponseBody
    boolean deleteStatusraboty(@RequestParam(name = "id") String id) {
        StatusrabotyRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allTkan")
    public @ResponseBody
    List<Tkan> allTkan() {
        return (List<Tkan>) TkanRepository.findAll();
    }

    @PostMapping("/addTkan")
    public @ResponseBody
    Map<String, Object> addTkan(
            @RequestParam(name = "artikul") String artikul,
            @RequestParam(name = "nazvanie") String nazvanie,
            @RequestParam(name = "kategoriya") String kategoriya,
            @RequestParam(name = "idTsvet") String idTsvet,
            @RequestParam(name = "idRastsvetka") String idRastsvetka,
            @RequestParam(name = "novinka") String novinka) {

        Map<String, Object> response = new HashMap<>();

        try {
            Tkan tkan = new Tkan();
            tkan.setArtikul(artikul);
            tkan.setNazvanie(nazvanie);
            tkan.setKategoriya(kategoriya);

            // Получаем объекты по внешним ключам
            Tsvettkani tsvet = TsvetTkaniRepository.findById(Integer.parseInt(idTsvet))
                    .orElseThrow(() -> new RuntimeException("Цвет ткани не найден"));
            tkan.setIdTsvet(tsvet);

            Rastsvetka rastsvetka = RastsvetkaRepository.findById(Integer.parseInt(idRastsvetka))
                    .orElseThrow(() -> new RuntimeException("Расцветка не найдена"));
            tkan.setIdRastsvetka(rastsvetka);

            tkan.setNovinka(Boolean.parseBoolean(novinka));

            Tkan savedTkan = TkanRepository.save(tkan);

            response.put("success", true);
            response.put("message", "Ткань успешно добавлена");
            response.put("id", savedTkan.getId());

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка добавления ткани: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/deleteTkan")
    public @ResponseBody
    Map<String, Object> deleteTkan(@RequestParam(name = "id") String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            TkanRepository.deleteById(Integer.parseInt(id));
            response.put("success", true);
            response.put("message", "Ткань успешно удалена");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка удаления ткани: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/allTsvetfurnitury")
    public @ResponseBody
    List<Map<String, Object>> allTsvetfurnitury() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Tsvetfurnitury tsvet : TsvetFurnituryRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", tsvet.getId());
            item.put("nazvanie", tsvet.getNazvanie());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addTsvetfurnitury")
    public @ResponseBody
    boolean addTsvetfurnitury(@RequestParam(name = "nazvanie") String nazvanie
    ) {
        Tsvetfurnitury tsvet = new Tsvetfurnitury();
        tsvet.setNazvanie(nazvanie);
        TsvetFurnituryRepository.save(tsvet);
        return true;
    }

    @PostMapping("/updateTsvetfurnitury")
    public @ResponseBody
    boolean updateTsvetfurnitury(@RequestParam(name = "id") String id,
            @RequestParam(name = "nazvanie") String nazvanie
    ) {
        Tsvetfurnitury tsvet = TsvetFurnituryRepository.findById(Integer.parseInt(id)).get();
        tsvet.setNazvanie(nazvanie);
        TsvetFurnituryRepository.save(tsvet);
        return true;
    }

    @PostMapping("/deleteTsvetfurnitury")
    public @ResponseBody
    boolean deleteTsvetfurnitury(@RequestParam(name = "id") String id
    ) {
        TsvetFurnituryRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allTsvettkani")
    public @ResponseBody
    List<Map<String, Object>> allTsvettkani() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Tsvettkani tsvet : TsvetTkaniRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", tsvet.getId());
            item.put("nazvanie", tsvet.getNazvanie());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addTsvettkani")
    public @ResponseBody
    boolean addTsvettkani(@RequestParam(name = "nazvanie") String nazvanie
    ) {
        Tsvettkani tsvet = new Tsvettkani();
        tsvet.setNazvanie(nazvanie);
        TsvetTkaniRepository.save(tsvet);
        return true;
    }

    @PostMapping("/updateTsvettkani")
    public @ResponseBody
    boolean updateTsvettkani(@RequestParam(name = "id") String id,
            @RequestParam(name = "nazvanie") String nazvanie
    ) {
        Tsvettkani tsvet = TsvetTkaniRepository.findById(Integer.parseInt(id)).get();
        tsvet.setNazvanie(nazvanie);
        TsvetTkaniRepository.save(tsvet);
        return true;
    }

    @PostMapping("/deleteTsvettkani")
    public @ResponseBody
    boolean deleteTsvettkani(@RequestParam(name = "id") String id
    ) {
        TsvetTkaniRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allZakazfurnitura")
    public @ResponseBody
    List<Zakazfurnitura> allZakazfurnitura() {
        return (List<Zakazfurnitura>) ZakazFurnituraRepository.findAll();
    }

    @PostMapping("/addZakazfurnitura")
    public @ResponseBody
    boolean addZakazfurnitura(@RequestParam(name = "idFurnitura") String idFurnitura,
            @RequestParam(name = "idZakaz") String idZakaz,
            @RequestParam(name = "kolichestvo") String kolichestvo
    ) {

        Zakazfurnitura zakazFurnitura = new Zakazfurnitura();

        // Получаем объекты по внешним ключам
        Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(idFurnitura)).get();
        zakazFurnitura.setIdFurnitura(furnitura);

        Zakaznaposhiv zakaz = ZakazNaPoshivRepository.findById(Integer.parseInt(idZakaz)).get();
        zakazFurnitura.setIdZakaz(zakaz);

        zakazFurnitura.setKolichestvo(Integer.parseInt(kolichestvo));

        ZakazFurnituraRepository.save(zakazFurnitura);
        return true;
    }

    @PostMapping("/updateZakazfurnitura")
    public @ResponseBody
    boolean updateZakazfurnitura(@RequestParam(name = "id") String id,
            @RequestParam(name = "idFurnitura") String idFurnitura,
            @RequestParam(name = "idZakaz") String idZakaz,
            @RequestParam(name = "kolichestvo") String kolichestvo
    ) {

        Zakazfurnitura zakazFurnitura = ZakazFurnituraRepository.findById(Integer.parseInt(id)).get();

        // Получаем объекты по внешним ключам
        Furnitura furnitura = FurnituraRepository.findById(Integer.parseInt(idFurnitura)).get();
        zakazFurnitura.setIdFurnitura(furnitura);

        Zakaznaposhiv zakaz = ZakazNaPoshivRepository.findById(Integer.parseInt(idZakaz)).get();
        zakazFurnitura.setIdZakaz(zakaz);

        zakazFurnitura.setKolichestvo(Integer.parseInt(kolichestvo));

        ZakazFurnituraRepository.save(zakazFurnitura);
        return true;
    }

    @PostMapping("/deleteZakazfurnitura")
    public @ResponseBody
    boolean deleteZakazfurnitura(@RequestParam(name = "id") String id
    ) {
        ZakazFurnituraRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allZakaznaposhiv")
    public @ResponseBody
    List<Zakaznaposhiv> allZakaznaposhiv() {
        return (List<Zakaznaposhiv>) ZakazNaPoshivRepository.findAll();
    }

    @PostMapping("/addZakaznaposhiv")
    public @ResponseBody
    Map<String, Object> addZakaznaposhiv(
            @RequestParam(name = "idKlient") String idKlient,
            @RequestParam(name = "idStatusRaboty") String idStatusRaboty,
            @RequestParam(name = "Data") String dataStr,
            @RequestParam(name = "idShveya") String idShveya,
            @RequestParam(name = "idIzdelie") String idIzdelie,
            @RequestParam(name = "TsenaRabotyShvei") String TsenaRabotyShvei,
            @RequestParam(name = "idSkidka") String idSkidka
    ) {

        Map<String, Object> response = new HashMap<>();

        try {
            Zakaznaposhiv zakaz = new Zakaznaposhiv();

            // Получаем объекты по внешним ключам
            Klient klient = KlientRepository.findById(Integer.parseInt(idKlient))
                    .orElseThrow(() -> new RuntimeException("Клиент не найден"));
            zakaz.setIdKlient(klient);

            Statusraboty status = StatusrabotyRepository.findById(Integer.parseInt(idStatusRaboty))
                    .orElseThrow(() -> new RuntimeException("Статус не найден"));
            zakaz.setIdStatusRaboty(status);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date data = format.parse(dataStr);
            zakaz.setData(data);

            Shveya shveya = ShveyaRepository.findById(Integer.parseInt(idShveya))
                    .orElseThrow(() -> new RuntimeException("Швея не найдена"));
            zakaz.setIdShveya(shveya);

            Izdelie izdelie = IzdelieRepository.findById(Integer.parseInt(idIzdelie))
                    .orElseThrow(() -> new RuntimeException("Изделие не найдено"));
            zakaz.setIdIzdelie(izdelie);

            zakaz.setTsenaRabotyShvei(new BigDecimal(TsenaRabotyShvei));

            Skidka skidka = SkidkaRepository.findById(Integer.parseInt(idSkidka))
                    .orElseThrow(() -> new RuntimeException("Скидка не найдена"));
            zakaz.setIdSkidka(skidka);

            ZakazNaPoshivRepository.save(zakaz);

            response.put("success", true);
            response.put("message", "Заказ успешно добавлен");

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка добавления заказа: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/deleteZakaznaposhiv")
    public @ResponseBody
    Map<String, Object> deleteZakaznaposhiv(@RequestParam(name = "id") String id
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            ZakazNaPoshivRepository.deleteById(Integer.parseInt(id));
            response.put("success", true);
            response.put("message", "Заказ успешно удален");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка удаления заказа: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/allZakaztkan")
    public @ResponseBody
    List<Zakaztkan> allZakaztkan() {
        return (List<Zakaztkan>) ZakaztkanRepository.findAll();
    }

    @PostMapping("/addZakaztkan")
    public @ResponseBody
    boolean addZakaztkan(@RequestParam(name = "idTkan") String idTkan,
            @RequestParam(name = "idZakaz") String idZakaz,
            @RequestParam(name = "kolichestvoTkaniM") String kolichestvoTkaniM
    ) {

        Zakaztkan zakaztkan = new Zakaztkan();

        // Получаем объекты по внешним ключам
        Tkan tkan = TkanRepository.findById(Integer.parseInt(idTkan)).get();
        zakaztkan.setIdTkan(tkan);

        Zakaznaposhiv zakaz = ZakazNaPoshivRepository.findById(Integer.parseInt(idZakaz)).get();
        zakaztkan.setIdZakaz(zakaz);

        zakaztkan.setKolichestvoTkaniM(new BigDecimal(kolichestvoTkaniM));

        ZakaztkanRepository.save(zakaztkan);
        return true;
    }

    @PostMapping("/updateZakaztkan")
    public @ResponseBody
    boolean updateZakaztkan(@RequestParam(name = "id") String id,
            @RequestParam(name = "idTkan") String idTkan,
            @RequestParam(name = "idZakaz") String idZakaz,
            @RequestParam(name = "kolichestvoTkaniM") String kolichestvoTkaniM
    ) {

        Zakaztkan zakaztkan = ZakaztkanRepository.findById(Integer.parseInt(id)).get();

        // Получаем объекты по внешним ключам
        Tkan tkan = TkanRepository.findById(Integer.parseInt(idTkan)).get();
        zakaztkan.setIdTkan(tkan);

        Zakaznaposhiv zakaz = ZakazNaPoshivRepository.findById(Integer.parseInt(idZakaz)).get();
        zakaztkan.setIdZakaz(zakaz);

        zakaztkan.setKolichestvoTkaniM(new BigDecimal(kolichestvoTkaniM));

        ZakaztkanRepository.save(zakaztkan);
        return true;
    }

    @PostMapping("/deleteZakaztkan")
    public @ResponseBody
    boolean deleteZakaztkan(@RequestParam(name = "id") String id
    ) {
        ZakaztkanRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allProdavec")
    public @ResponseBody
    List<Prodavec> allProdavec() {
        return (List<Prodavec>) ProdavecRepository.findAll();
    }

    @PostMapping("/addProdavec")
    public @ResponseBody
    boolean addProdavec(@RequestParam(name = "polzovatelid") String polzovatelid
    ) {
        Prodavec prodavec = new Prodavec();

        // Получаем объект по внешнему ключу
        Polzovateli polzovatel = PolzovatelRepository.findById(Integer.parseInt(polzovatelid)).get();
        prodavec.setPolzovatelid(polzovatel);

        ProdavecRepository.save(prodavec);
        return true;
    }

    @PostMapping("/updateProdavec")
    public @ResponseBody
    boolean updateProdavec(@RequestParam(name = "id") String id,
            @RequestParam(name = "polzovatelid") String polzovatelid
    ) {

        Prodavec prodavec = ProdavecRepository.findById(Integer.parseInt(id)).get();

        // Получаем объект по внешнему ключу
        Polzovateli polzovatel = PolzovatelRepository.findById(Integer.parseInt(polzovatelid)).get();
        prodavec.setPolzovatelid(polzovatel);

        ProdavecRepository.save(prodavec);
        return true;
    }

    @PostMapping("/deleteProdavec")
    public @ResponseBody
    boolean deleteProdavec(@RequestParam(name = "id") String id
    ) {
        ProdavecRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allShveya")
    public @ResponseBody
    List<Map<String, Object>> allShveya() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Shveya shveya : ShveyaRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", shveya.getId());
            // Можно добавить информацию о пользователе если нужно
            result.add(item);
        }
        return result;
    }

    @PostMapping("/addShveya")
    public @ResponseBody
    boolean addShveya(@RequestParam(name = "polzovatelid") String polzovatelid
    ) {
        Shveya shveya = new Shveya();

        // Получаем объект по внешнему ключу
        Polzovateli polzovatel = PolzovatelRepository.findById(Integer.parseInt(polzovatelid)).get();
        shveya.setPolzovatelid(polzovatel);

        ShveyaRepository.save(shveya);
        return true;
    }

    @PostMapping("/updateShveya")
    public @ResponseBody
    boolean updateShveya(@RequestParam(name = "id") String id,
            @RequestParam(name = "polzovatelid") String polzovatelid
    ) {

        Shveya shveya = ShveyaRepository.findById(Integer.parseInt(id)).get();

        // Получаем объект по внешнему ключу
        Polzovateli polzovatel = PolzovatelRepository.findById(Integer.parseInt(polzovatelid)).get();
        shveya.setPolzovatelid(polzovatel);

        ShveyaRepository.save(shveya);
        return true;
    }

    @PostMapping("/deleteShveya")
    public @ResponseBody
    boolean deleteShveya(@RequestParam(name = "id") String id
    ) {
        ShveyaRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allSkidka")
    public @ResponseBody
    List<Skidka> allSkidka() {
        return (List<Skidka>) SkidkaRepository.findAll();
    }

    @PostMapping("/addSkidka")
    public @ResponseBody
    boolean addSkidka(@RequestParam(name = "procent") String procent
    ) {
        Skidka skidka = new Skidka();
        skidka.setProcent(Integer.parseInt(procent));
        SkidkaRepository.save(skidka);
        return true;
    }

    @PostMapping("/updateSkidka")
    public @ResponseBody
    boolean updateSkidka(@RequestParam(name = "id") String id,
            @RequestParam(name = "procent") String procent
    ) {
        Skidka skidka = SkidkaRepository.findById(Integer.parseInt(id)).get();
        skidka.setProcent(Integer.parseInt(procent));
        SkidkaRepository.save(skidka);
        return true;
    }

    @PostMapping("/deleteSkidka")
    public @ResponseBody
    boolean deleteSkidka(@RequestParam(name = "id") String id
    ) {
        SkidkaRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allSostav")
    public @ResponseBody
    List<Sostav> allSostav() {
        return (List<Sostav>) SostavRepository.findAll();
    }

    @PostMapping("/addSostav")
    public @ResponseBody
    boolean addSostav(@RequestParam(name = "nazvanie") String nazvanie
    ) {
        Sostav sostav = new Sostav();
        sostav.setNazvanie(nazvanie);
        SostavRepository.save(sostav);
        return true;
    }

    @PostMapping("/updateSostav")
    public @ResponseBody
    boolean updateSostav(@RequestParam(name = "id") String id,
            @RequestParam(name = "nazvanie") String nazvanie
    ) {
        Sostav sostav = SostavRepository.findById(Integer.parseInt(id)).get();
        sostav.setNazvanie(nazvanie);
        SostavRepository.save(sostav);
        return true;
    }

    @PostMapping("/deleteSostav")
    public @ResponseBody
    boolean deleteSostav(@RequestParam(name = "id") String id
    ) {
        SostavRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allSostavprocent")
    public @ResponseBody
    List<Sostavprocent> allSostavprocent() {
        return (List<Sostavprocent>) SostavprocentRepository.findAll();
    }

    @PostMapping("/addSostavprocent")
    public @ResponseBody
    boolean addSostavprocent(@RequestParam(name = "tkanyid") String tkanyid,
            @RequestParam(name = "sostavid") String sostavid,
            @RequestParam(name = "procent") String procent
    ) {

        Sostavprocent sostavprocent = new Sostavprocent();

        // Получаем объекты по внешним ключам
        Tkan tkan = TkanRepository.findById(Integer.parseInt(tkanyid)).get();
        sostavprocent.setTkanyid(tkan);

        Sostav sostav = SostavRepository.findById(Integer.parseInt(sostavid)).get();
        sostavprocent.setSostavid(sostav);

        sostavprocent.setProcent(Integer.parseInt(procent));

        SostavprocentRepository.save(sostavprocent);
        return true;
    }

    @PostMapping("/updateSostavprocent")
    public @ResponseBody
    boolean updateSostavprocent(@RequestParam(name = "id") String id,
            @RequestParam(name = "tkanyid") String tkanyid,
            @RequestParam(name = "sostavid") String sostavid,
            @RequestParam(name = "procent") String procent
    ) {

        Sostavprocent sostavprocent = SostavprocentRepository.findById(Integer.parseInt(id)).get();

        // Получаем объекты по внешним ключам
        Tkan tkan = TkanRepository.findById(Integer.parseInt(tkanyid)).get();
        sostavprocent.setTkanyid(tkan);

        Sostav sostav = SostavRepository.findById(Integer.parseInt(sostavid)).get();
        sostavprocent.setSostavid(sostav);

        sostavprocent.setProcent(Integer.parseInt(procent));

        SostavprocentRepository.save(sostavprocent);
        return true;
    }

    @PostMapping("/deleteSostavprocent")
    public @ResponseBody
    boolean deleteSostavprocent(@RequestParam(name = "id") String id
    ) {
        SostavprocentRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @GetMapping("/allUpravlyayushchiy")
    public @ResponseBody
    List<Upravlyayushchiy> allUpravlyayushchiy() {
        return (List<Upravlyayushchiy>) UpravlyayushchiyRepository.findAll();
    }

    @PostMapping("/addUpravlyayushchiy")
    public @ResponseBody
    boolean addUpravlyayushchiy(@RequestParam(name = "polzovatelid") String polzovatelid
    ) {
        Upravlyayushchiy upravlyayushchiy = new Upravlyayushchiy();

        // Получаем объект по внешнему ключу
        Polzovateli polzovatel = PolzovatelRepository.findById(Integer.parseInt(polzovatelid)).get();
        upravlyayushchiy.setPolzovatelid(polzovatel);

        UpravlyayushchiyRepository.save(upravlyayushchiy);
        return true;
    }
// Исправленные методы для получения данных с правильной структурой JSON

    @GetMapping("/allTkanForTable")
    public @ResponseBody
    List<Map<String, Object>> allTkanForTable() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Tkan tkan : TkanRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", tkan.getId());
            item.put("artikul", tkan.getArtikul());
            item.put("nazvanie", tkan.getNazvanie());
            item.put("kategoriya", tkan.getKategoriya());
            item.put("idTsvet", tkan.getIdTsvet() != null ? tkan.getIdTsvet().getId() : null);
            item.put("idRastsvetka", tkan.getIdRastsvetka() != null ? tkan.getIdRastsvetka().getId() : null);
            item.put("novinka", tkan.getNovinka());
            result.add(item);
        }

        return result;
    }

    @GetMapping("/allFurnituraForTable")
    public @ResponseBody
    List<Map<String, Object>> allFurnituraForTable() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Furnitura furnitura : FurnituraRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", furnitura.getId());
            item.put("artikul", furnitura.getArtikul());
            item.put("nazvanie", furnitura.getNazvanie());
            item.put("idTsvet", furnitura.getIdTsvet() != null ? furnitura.getIdTsvet().getId() : null);
            item.put("idEdIzmereniya", furnitura.getIdEdIzmereniya() != null ? furnitura.getIdEdIzmereniya().getId() : null);
            item.put("novinka", furnitura.getNovinka());
            result.add(item);
        }

        return result;
    }

    @GetMapping("/allProdazhafurnituryForTable")
    public @ResponseBody
    List<Map<String, Object>> allProdazhafurnituryForTable() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Prodazhafurnitury prodazha : ProdazhaFurnituryRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", prodazha.getId());
            item.put("data", prodazha.getData());
            item.put("idFurnitura", prodazha.getIdFurnitura() != null ? prodazha.getIdFurnitura().getId() : null);
            item.put("Kolichestvo", prodazha.getKolichestvo());
            item.put("tsenaZaYedinitsu", prodazha.getTsenaZaYedinitsu());
            result.add(item);
        }

        return result;
    }

    @GetMapping("/allProdazhatkaniForTable")
    public @ResponseBody
    List<Map<String, Object>> allProdazhatkaniForTable() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Prodazhatkani prodazha : ProdazhaTkaniRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", prodazha.getId());
            item.put("data", prodazha.getData());
            item.put("idTkan", prodazha.getIdTkan() != null ? prodazha.getIdTkan().getId() : null);
            item.put("Kolichestvo", prodazha.getKolichestvo());
            item.put("tsenaZaRulon", prodazha.getTsenaZaRulon());
            item.put("idRazmer", prodazha.getIdRazmer() != null ? prodazha.getIdRazmer().getId() : null);
            result.add(item);
        }

        return result;
    }

    @GetMapping("/allZakaznaposhivForTable")
    public @ResponseBody
    List<Map<String, Object>> allZakaznaposhivForTable() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Zakaznaposhiv zakaz : ZakazNaPoshivRepository.findAll()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", zakaz.getId());
            item.put("Data", zakaz.getData());
            item.put("idKlient", zakaz.getIdKlient() != null ? zakaz.getIdKlient().getId() : null);
            item.put("idIzdelie", zakaz.getIdIzdelie() != null ? zakaz.getIdIzdelie().getId() : null);
            item.put("idStatusRaboty", zakaz.getIdStatusRaboty() != null ? zakaz.getIdStatusRaboty().getId() : null);
            item.put("idShveya", zakaz.getIdShveya() != null ? zakaz.getIdShveya().getId() : null);
            item.put("TsenaRabotyShvei", zakaz.getTsenaRabotyShvei());
            item.put("idSkidka", zakaz.getIdSkidka() != null ? zakaz.getIdSkidka().getId() : 1); // 1 = скидка 0% по умолчанию
            result.add(item);
        }

        return result;
    }

    @PostMapping("/updateUpravlyayushchiy")
    public @ResponseBody
    boolean updateUpravlyayushchiy(@RequestParam(name = "id") String id,
            @RequestParam(name = "polzovatelid") String polzovatelid
    ) {

        Upravlyayushchiy upravlyayushchiy = UpravlyayushchiyRepository.findById(Integer.parseInt(id)).get();

        // Получаем объект по внешнему ключу
        Polzovateli polzovatel = PolzovatelRepository.findById(Integer.parseInt(polzovatelid)).get();
        upravlyayushchiy.setPolzovatelid(polzovatel);

        UpravlyayushchiyRepository.save(upravlyayushchiy);
        return true;
    }

    @PostMapping("/deleteUpravlyayushchiy")
    public @ResponseBody
    boolean deleteUpravlyayushchiy(@RequestParam(name = "id") String id
    ) {
        UpravlyayushchiyRepository.deleteById(Integer.parseInt(id));
        return true;
    }
}
