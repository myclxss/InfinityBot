package dev.anhuar.handler;

/*
 * ========================================================
 * InfinityBot - CommandHandler.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 27/10/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import dev.anhuar.command.RankCommand;
import dev.anhuar.interfaces.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler extends ListenerAdapter {

    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandHandler() {

        registerCommand(new RankCommand());

    }

    public void registerCommand(ICommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public List<CommandData> getCommandData() {
        List<CommandData> commandData = new ArrayList<>();
        for (ICommand command : commands.values()) {
            commandData.add(command.getCommandData());
        }
        return commandData;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName().toLowerCase();
        ICommand command = commands.get(commandName);

        if (command != null) {
            if (command.requiresPermissions()) {
                List<Permission> requiredPermissions = command.getRequiredPermissions();
                if (!requiredPermissions.isEmpty() && event.getMember() != null &&
                        requiredPermissions.stream().anyMatch(perm -> !event.getMember().hasPermission(perm))) {
                    event.reply("No cuentas con los permisos necesarios.").setEphemeral(true).queue();
                    return;
                }
            }
            command.execute(event);
        }
    }
}