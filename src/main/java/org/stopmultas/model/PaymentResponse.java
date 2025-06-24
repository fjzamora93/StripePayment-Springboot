package org.stopmultas.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
        private String clientSecret;
        private String error;
}
