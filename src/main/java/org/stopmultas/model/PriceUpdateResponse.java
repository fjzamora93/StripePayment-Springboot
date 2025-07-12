package org.stopmultas.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceUpdateResponse {
        private boolean success;
        private long price;
        private String message;
}
