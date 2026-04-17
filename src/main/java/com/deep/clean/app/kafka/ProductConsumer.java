package com.deep.clean.app.kafka;

import com.deep.clean.app.events.ProductEvent;
import com.deep.clean.app.entity.ProductLogEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

@ApplicationScoped
public class ProductConsumer {

    private static final Logger LOG = Logger.getLogger(ProductConsumer.class);

    @Incoming("product-in")
    @Transactional
    public void consumeProductEvent(ProductEvent event) {
        LOG.infof("==> Kafka Consumer: Procesando evento para el producto SKU: %s", event.getSku());

        // Guardamos en la bitácora de la base de datos
        ProductLogEntity logEntry = ProductLogEntity.builder()
                .productId(event.getId())
                .sku(event.getSku())
                .action("EVENT_RECEIVED")
                .description("Evento de producto procesado desde Kafka: " + event.getName())
                .timestamp(LocalDateTime.now())
                .build();

        logEntry.persist();

        LOG.infof("==> Kafka Consumer: Log persistido con éxito para SKU: %s", event.getSku());
    }
}