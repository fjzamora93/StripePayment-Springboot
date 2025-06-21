package org.stopmultas.controller;

import com.stripe.exception.StripeException;

import org.stopmultas.model.PaymentRequest;
import org.stopmultas.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class StripeController {

    private final StripeService stripeService;

    @Autowired
    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(
        @RequestBody PaymentRequest paymentRequest
        ) {
        try {
            String clientSecret = stripeService.createPaymentIntent(paymentRequest);
            return ResponseEntity.ok(Map.of("clientSecret", clientSecret));
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<Map<String, String>> confirmPayment(@RequestBody Map<String, Object> request) {
        try {
            String result = stripeService.confirmPayment(request);
            return ResponseEntity.ok(Map.of("status", result));
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
