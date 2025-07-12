package org.stopmultas.controller;

import com.stripe.exception.StripeException;

import org.stopmultas.model.PaymentConfirmationRequest;
import org.stopmultas.model.PaymentConfirmationResponse;
import org.stopmultas.model.PaymentResponse;
import org.stopmultas.model.PriceUpdateRequest;
import org.stopmultas.model.PriceUpdateResponse;
import org.stopmultas.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
public class StripeController {

    private final StripeService stripeService;

    @Autowired
    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/create-payment-intent")
    public ResponseEntity<PaymentResponse> createPaymentIntent() {
        try {
            String clientSecret = stripeService.createPaymentIntent();
            return ResponseEntity.ok(new PaymentResponse(clientSecret, null));
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(
                new PaymentResponse(null, e.getMessage())
                );
        }
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<PaymentConfirmationResponse> confirmPayment(@RequestBody PaymentConfirmationRequest request) {
        try {
            String status = stripeService.confirmPayment(request);
            return ResponseEntity.ok(new PaymentConfirmationResponse(
                status,
                null
            ));
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(new PaymentConfirmationResponse(
                 null,
                e.getMessage()
            ));
        }
    }

    @GetMapping("/pricing")
    public ResponseEntity<Long> getPricing() {
        return ResponseEntity.ok(stripeService.getPricing());
    }

    @PostMapping("/pricing")
    public ResponseEntity<PriceUpdateResponse> updatePricing(@RequestBody PriceUpdateRequest priceUpdateRequest) {
        System.out.println("🔍 priceUpdateRequest: " + priceUpdateRequest);
        return ResponseEntity.ok(stripeService.updatePricing(priceUpdateRequest));
    }
}
