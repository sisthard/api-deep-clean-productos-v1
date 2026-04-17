package com.deep.clean.app.kafka;

import com.deep.clean.app.events.ProductEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

@ApplicationScoped
public class ProductProducer {

    private static final Logger LOG = Logger.getLogger(ProductProducer.class);

    @Inject
    @Channel("product-out")
    Emitter<ProductEvent> eventEmitter;

    public void sendProductEvent(Long id, String sku, String name, String status) {
        ProductEvent event = ProductEvent.builder()
                .id(id)
                .sku(sku)
                .name(name)
                .status(status)
                .timestamp(LocalDateTime.now().toString())
                .build();

        LOG.infof("Emitiendo evento Kafka para producto: %s", sku);
        eventEmitter.send(event);
    }
}