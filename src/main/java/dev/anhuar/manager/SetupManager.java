package dev.anhuar.manager;

/*
 * ========================================================
 * InfinityBot - SetupManager.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 17/11/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class SetupManager {

    public void registerSetup(SlashCommandInteractionEvent event) {

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("📜 Evaluation Testing Waitlist & Roles")
                .setDescription(
                        "**Step 1: Register Your Profile**\n" +
                                "Click the `Register / Update Profile` button to set your in-game details.\n\n" +
                                "**Step 2: Select Your Tier**\n" +
                                "Choose the tier you want to test from the dropdown menu below.\n\n" +
                                "**Step 3: Join the Waitlist**\n" +
                                "Once registered, you'll receive the corresponding waitlist role for your selected tier."
                )
                .setColor(Color.decode("#5865F2"))
                .setFooter("CTL Tierlist", event.getJDA().getSelfUser().getAvatarUrl());

        // Menú de selección de tiers
        StringSelectMenu tierMenu = StringSelectMenu.create("tier-select")
                .setPlaceholder("Select a tier to get the waitlist role")
                .addOption("Netherite", "netherite", "Top tier - Netherite", Emoji.fromUnicode("⚫"))
                .addOption("Potion", "potion", "High tier - Potion", Emoji.fromUnicode("🧪"))
                .addOption("Sword", "sword", "Mid tier - Sword", Emoji.fromUnicode("⚔️"))
                .addOption("Crystal", "crystal", "Entry tier - Crystal", Emoji.fromUnicode("💎"))
                .addOption("UHC", "uhc", "Basic tier - UHC", Emoji.fromUnicode("🎮"))
                .build();

        // Botón de registro
        Button registerButton = Button.primary("register-profile", "Register / Update Profile").withEmoji(Emoji.fromUnicode("📝"));

        event.replyEmbeds(embed.build()).addComponents(ActionRow.of(registerButton), ActionRow.of(tierMenu)).queue();
    }

}