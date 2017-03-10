package com.youjuke.swissgearlibrary.rxbus.finder;

import com.youjuke.swissgearlibrary.rxbus.entity.EventType;
import com.youjuke.swissgearlibrary.rxbus.entity.SubscriberEvent;
import com.youjuke.swissgearlibrary.rxbus.entity.ProducerEvent;

import java.util.Map;
import java.util.Set;

/**
 * Finds producer and subscriber methods.
 */
public interface Finder {

    Map<EventType, ProducerEvent> findAllProducers(Object listener);

    Map<EventType, Set<SubscriberEvent>> findAllSubscribers(Object listener);


    Finder ANNOTATED = new Finder() {
        @Override
        public Map<EventType, ProducerEvent> findAllProducers(Object listener) {
            return AnnotatedFinder.findAllProducers(listener);
        }

        @Override
        public Map<EventType, Set<SubscriberEvent>> findAllSubscribers(Object listener) {
            return AnnotatedFinder.findAllSubscribers(listener);
        }
    };
}
