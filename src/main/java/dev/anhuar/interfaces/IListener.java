package dev.anhuar.interfaces;

import net.dv8tion.jda.api.events.Event;

public interface IListener<T extends Event> {

    void onEvent(T event);

    Class<T> getEventType();

}