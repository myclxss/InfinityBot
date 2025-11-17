package dev.anhuar.handler;

/*
 * ========================================================
 * InfinityBot - ListenerHandler.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 27/10/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import dev.anhuar.interfaces.IListener;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListenerHandler extends ListenerAdapter {

    private final List<IListener<?>> listeners = new ArrayList<>();

    public ListenerHandler() {

//        registerListener(new JoinDefaultRole());
    }

    public void registerListener(IListener<?> listener) {
        listeners.add(listener);
    }

    @Override
    public void onGenericEvent(@NotNull GenericEvent genericEvent) {

        if (genericEvent instanceof Event event) {

            for (IListener<?> listener : this.listeners) {
                if (listener.getEventType().isInstance(event)) {
                    @SuppressWarnings("unchecked")
                    IListener<Event> typedListener = (IListener<Event>) listener;
                    typedListener.onEvent(event);
                }
            }
        }
    }
}