package org.stopmultas.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
public class PaymentConfirmationResponse {
    private String status;
    private String error;

}
