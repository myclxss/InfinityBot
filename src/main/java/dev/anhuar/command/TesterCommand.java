package dev.anhuar.command;

/*
 * ========================================================
 * InfinityBot - TesterCommand.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 07/11/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import dev.anhuar.interfaces.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TesterCommand implements ICommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String subCommand = event.getSubcommandName();

        if (subCommand == null) {
            event.reply("Please provide a valid subcommand.").setEphemeral(true).queue();
            return;
        }

        switch (subCommand.toLowerCase()) {
            case "add" -> {

            }
            case "remove" -> {

            }
            default -> {

            }
        }

    }

    @Override
    public String getName() {
        return "tester";
    }

    @Override
    public String getDescription() {
        return "Todos los comandos respecto a la organización para los tester";
    }
}