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

import dev.anhuar.InfinityBot;
import dev.anhuar.interfaces.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class SetupCommand implements ICommand {

    private final InfinityBot bot = InfinityBot.getInstance();

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String subCommand = event.getSubcommandName();

        if (subCommand == null) {
            event.reply("Please provide a valid subcommand.").setEphemeral(true).queue();
            return;
        }

        switch (subCommand.toLowerCase()) {
            case "register" -> {
                bot.getManagerHandler().getSetupManager().registerSetup(event);
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

    @Override
    public CommandData buildCommandData() {
        return Commands.slash(getName().toLowerCase(), getDescription())
                .addSubcommands(
                        new SubcommandData("register", "Setup the registration embed and components")
                );
    }
}