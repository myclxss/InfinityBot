package dev.anhuar.command;

/*
 * ========================================================
 * InfinityBot - SetupCommand.java
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

public class SetupCommand implements ICommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String subCommand = event.getSubcommandName();

        if (subCommand == null) {
            event.reply("Please provide a valid subcommand.").setEphemeral(true).queue();
            return;
        }

        switch (subCommand.toLowerCase()) {
            case "" -> {
                event.reply("").setEphemeral(true).queue();
            }
            default -> {

            }
        }

    }

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "Realiza el setup de los embeds y canales para el bot";
    }
}