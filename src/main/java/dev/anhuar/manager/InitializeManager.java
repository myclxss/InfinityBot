package dev.anhuar.manager;

/*
 * ========================================================
 * InfinityBot - InitializeManager.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 17/11/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import dev.anhuar.InfinityBot;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.List;

@Getter
@Slf4j
public class InitializeManager {

    private final InfinityBot bot = InfinityBot.getInstance();
    private JDA jda;

    public void start() {
        String token = bot.getSetting().getString("DISCORD.TOKEN");
        List<String> servers = bot.getSetting().getStringList("DISCORD.SERVER-ID");

        if (token == null || token.isEmpty()) {
            log.error(bot.getMessage().getString("ERROR.TOKEN"));
            return;
        }

        if (servers == null || servers.isEmpty()) {
            log.error(bot.getMessage().getString("ERROR.SERVER-ID"));
            return;
        }

        try {
            log.info("Iniciando bot de Discord...");

            JDABuilder jdaBuilder = JDABuilder.createDefault(token)
                    .enableIntents(
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_PRESENCES
                    )
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setActivity(Activity.playing("HOla"));

            jda = jdaBuilder.build();

            bot.setJda(jda);

            jda.addEventListener(bot.getCommandHandler());
            jda.addEventListener(bot.getListenerHandler());

            jda.awaitReady();

            for (String serverId : servers) {
                var guild = jda.getGuildById(serverId);
                if (guild != null) {
                    guild.updateCommands().addCommands(bot.getCommandHandler().getCommandData()).queue();
                } else {
                    log.warn("No se encontró el servidor con ID: {}", serverId);
                }
            }

            log.info("Bot iniciado correctamente");

        } catch (InterruptedException e) {
            log.error("Error al inicializar JDA: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error inesperado al inicializar el bot: {}", e.getMessage(), e);
        }
    }
}