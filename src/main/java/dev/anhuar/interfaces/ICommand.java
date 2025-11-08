package dev.anhuar.interfaces;

/*
 * ========================================================
 * InfinityBot - ICommand.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 07/11/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.List;

public interface ICommand {

    void execute(SlashCommandInteractionEvent event);

    String getName();

    String getDescription();

    default List<Permission> getRequiredPermissions() {
        return List.of();
    }

    default boolean requiresPermissions() {
        return !getRequiredPermissions().isEmpty();
    }

    default List<String> getAliases() {
        return List.of();
    }

    default CommandData getCommandData() {
        return buildCommandData();
    }

    default CommandData buildCommandData() {
        return Commands.slash(getName().toLowerCase(), getDescription());
    }
}