package com.trading.stock_simulator.market;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PriceStreamService {
    CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter(3600000L);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> emitters.remove(sseEmitter));
        sseEmitter.onError(e -> emitters.remove(sseEmitter));
        emitters.add(sseEmitter);
        return sseEmitter;
    }

    @TransactionalEventListener
    public void onPricesUpdated(PricesUpdatedEvent pricesUpdatedEvent) {
        for (SseEmitter emitter: emitters) {
            try {
                emitter.send(SseEmitter.event().name("prices").data(pricesUpdatedEvent.assets()));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
