package org.stopmultas.model;
import lombok.Data;

@Data
public class PaymentRequest {
        private Long amount;
        private String currency;
        // getters y setters
}
 