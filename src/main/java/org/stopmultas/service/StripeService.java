package org.stopmultas.service;

import com.google.api.client.util.Value;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.stopmultas.model.PaymentConfirmationRequest;
import org.stopmultas.model.PaymentRequest;

import jakarta.annotation.PostConstruct;


@Service
public class StripeService {

    @Value("${stripe.secret-key.test}")
    private String testSecretKey;

    @Value("${stripe.secret-key.live}")
    private String liveSecretKey;

    @Value("${stripe.mode}")
    private String stripeMode;

    private String activeSecretKey;

    @PostConstruct
    public void init() {
        if ("live".equalsIgnoreCase(stripeMode)) {
            activeSecretKey = liveSecretKey;
        } else {
            activeSecretKey = testSecretKey;
        }

        Stripe.apiKey = activeSecretKey;
    }


    /**Crea el intento de pago, es decir, la pasarela que verá el usuario para poder realizar el pago*/
    // TODO : El PaymentIntent no está vinculado a ningún Customer de Stripe.
    // TODO: Manejar idempotencia para evitar cargos duplicados
    // TODO Meter logs con el intento de creación
    // TODO: El PaymentIntent se crea en Stripe, pero no se guarda su ID o metadata en una base de datos local.
    // TODO: No hay control de excepciones personalizado. El método lanza directamente StripeException, pero no hay lógica para distinguir el tipo de error.
    public String createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {

        // OBTENEMOS EL USUARIO DESDE EL CONTEXTO (Ahora podemos asociar el pago al usuario)
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Long amount = paymentRequest.getAmount();
        String currency = "EUR";

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getClientSecret();
    }

    /** 
     * La confirmación es opcional: 
     * -Si el pago ha salido bien, este bloque de código se ejecutará después de realizar el pago (con un mensaje de éxito o error) 
     * - Desde Flutter solo recibiremos el String con el id del pago, y dentro del service, obtenemos el status (aunque podríamos obtener más datos)
     * 
     * POSIBLES STATUS:
        -requires_payment_method	El intento de pago no tiene un método de pago válido.
        -requires_confirmation	    El intento de pago necesita ser confirmado (usualmente automático).
        -requires_action	        Se necesita una acción adicional del cliente (como autenticación 3D Secure).
        -processing	                El pago está en proceso, aún no se completó.
        -requires_capture	        El pago fue autorizado pero necesita ser capturado manualmente (opcional).
        -canceled	                El intento fue cancelado (por ti o por Stripe).
        -succeeded	                🎉 ¡Pago realizado con éxito!
     * */
    public String confirmPayment(PaymentConfirmationRequest data) throws StripeException {
        String paymentIntentId = data.getPaymentIntentId();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
    
        System.out.println("🔍 [Stripe] Confirmación de pago recibida:");
        System.out.println("  - ID del PaymentIntent: " + paymentIntent.getId());
        System.out.println("  - Estado: " + paymentIntent.getStatus());
        System.out.println("  - Monto: " + paymentIntent.getAmount());
        System.out.println("  - Moneda: " + paymentIntent.getCurrency());
        System.out.println("  - Método de pago: " + paymentIntent.getPaymentMethod());
        System.out.println("  - Cliente: " + paymentIntent.getCustomer());
    
        return paymentIntent.getStatus();
    }



    // TODO: COMPLEMENTAR CON UN WEBHOOK PARA AUTOMATIZAR LA CONFIRMACIÓN DE LOS PAGOS_
    // TODO: UN WEBHOOK PUEDE PREVENIR QUE EL CLIENTE CIERRE LA APLICACIÓN O SITUACIONES SIMIALRES
    
}