package com.deep.clean.app.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEvent {
    private Long id;
    private String sku;
    private String name;
    private String status;
    private String timestamp;
}
