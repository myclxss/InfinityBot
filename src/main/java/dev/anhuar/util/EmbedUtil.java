package dev.anhuar.util;

/*
 * ========================================================
 * InfinityBot - EmbedUtil.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 27/10/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class EmbedUtil {

    public static EmbedBuilder complete(String title, String description, String color) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(Color.decode(color));
    }

    public static EmbedBuilder description(String title, String color, String... lines) {
        String description = String.join("\n", lines);
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(Color.decode(color));
    }
}