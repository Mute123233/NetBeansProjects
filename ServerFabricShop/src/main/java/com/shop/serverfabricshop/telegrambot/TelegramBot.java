/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shop.serverfabricshop.telegrambot;

import com.shop.serverfabricshop.repository.EdinitsylzmerenyaRepository;
import com.shop.serverfabricshop.repository.FurnituraRepository;
import com.shop.serverfabricshop.repository.IzdelieRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author 8279@stud.pgt.su
 */
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    @Autowired
    private EdinitsylzmerenyaRepository edinitsylzmerenyaRepository;
    @Autowired
    private FurnituraRepository furnituraRepository;
    @Autowired
    private IzdelieRepository izdelieRepository;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    int clientid;
    int userid;
    boolean logged;
    boolean logging;
    boolean ordering;
    boolean dishselect;
    int totalcost;
    boolean cooking;
    boolean updating;
    int ingredientNum;
    boolean ingredientChosen;
    boolean changing;
    boolean scorring;
    boolean add;
    boolean delete;
    ArrayList<Integer> orderList = new ArrayList();

    @Override
    public void onUpdateReceived(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        if (logged == false && ordering == false && scorring == false) {

        }
    }
}
