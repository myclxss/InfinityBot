package dev.anhuar;

/*
 * ========================================================
 * InfinityBot - InfinityBot.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 27/10/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import dev.anhuar.handler.CommandHandler;
import dev.anhuar.handler.ListenerHandler;
import dev.anhuar.handler.MongoHandler;
import dev.anhuar.manager.InitializeManager;
import dev.anhuar.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;

@Getter
public class InfinityBot {

    @Getter
    private static InfinityBot instance;

    @Getter
    @Setter
    private JDA jda;

    @Getter
    ConfigUtil setting, message;

    private InitializeManager initializeManager;

    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private MongoHandler mongoHandler;

    public static void main(String[] args) {
        new InfinityBot().onEnable();
    }

    public void onEnable() {

        instance = this;

        setting = new ConfigUtil("settings");
        message = new ConfigUtil("messages");

        commandHandler = new CommandHandler();
        listenerHandler = new ListenerHandler();
        mongoHandler = new MongoHandler(this);

        initializeManager = new InitializeManager();
        initializeManager.start();

    }

    public void onDisable() {

    }

}