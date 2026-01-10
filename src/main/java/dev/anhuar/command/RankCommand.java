package dev.anhuar.command;

/*
 * ========================================================
 * InfinityBot - RankCommand.java
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

public class RankCommand implements ICommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String subCommand = event.getSubcommandName();

        if (subCommand == null) {
            event.reply("Please provide a valid subcommand.").setEphemeral(true).queue();
            return;
        }

        switch (subCommand.toLowerCase()) {
            case "add" -> {
                event.reply("Rank Command Subcommand 1 executed.").setEphemeral(true).queue();
            }
            case "remove" -> {
                event.reply("Rank Command Subcommand 2 executed.").setEphemeral(true).queue();
            }
            default -> {
                event.reply("Debes usar /rank <add|remove>").setEphemeral(true).queue();
            }
        }

    }

    @Override
    public String getName() {
        return "rank";
    }

    @Override
    public String getDescription() {
        return "Coloca un rango de la tier a un usuario.";
    }
}