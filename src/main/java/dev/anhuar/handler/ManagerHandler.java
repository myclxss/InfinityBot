package dev.anhuar.handler;

/*
 * ========================================================
 * InfinityBot - ManagerHandler.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 17/11/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import dev.anhuar.manager.SetupManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ManagerHandler {

    private final Map<Class<?>, Object> managers = new HashMap<>();

    private final SetupManager setupManager;

    public ManagerHandler() {

        this.setupManager = new SetupManager();

        // Registrarlos en el mapa
        registerManager(SetupManager.class, setupManager);
    }

    private <T> void registerManager(Class<T> managerClass, T manager) {
        managers.put(managerClass, manager);
    }

    @SuppressWarnings("unchecked")
    public <T> T getManager(Class<T> managerClass) {
        return (T) managers.get(managerClass);
    }
}