package dev.anhuar.handler;

/*
 * ========================================================
 * InfinityBot - MongoHandler.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 27/10/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import dev.anhuar.InfinityBot;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
@Getter
public class MongoHandler {

    private final InfinityBot bot = InfinityBot.getInstance();
    private final MongoClient mongoClient;
    private final MongoCollection<Document> players;

    public MongoHandler(InfinityBot bot) {
        String uri = bot.getSetting().getString("MONGO.URI", "mongodb://localhost:27017");
        String databaseName = bot.getSetting().getString("MONGO.DATABASE", "Pulpy");
        String collectionName = bot.getSetting().getString("MONGO.COLLECTION", "users");

        this.mongoClient = MongoClients.create(uri);
        this.players = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        log.info("Connected to MongoDB");
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();

            log.info("Disconnected from MongoDB");
        }
    }
}