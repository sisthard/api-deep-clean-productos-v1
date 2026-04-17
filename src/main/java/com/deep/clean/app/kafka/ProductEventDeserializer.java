package com.deep.clean.app.kafka;

import com.deep.clean.app.events.ProductEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ProductEventDeserializer extends ObjectMapperDeserializer<ProductEvent> {
    public ProductEventDeserializer() {
        super(ProductEvent.class);
    }
}
