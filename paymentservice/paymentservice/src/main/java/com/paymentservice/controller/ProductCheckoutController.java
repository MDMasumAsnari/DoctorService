package com.paymentservice.controller;

import com.paymentservice.client.BookingClient;
import com.paymentservice.dto.BookingConfirmation;
import com.paymentservice.dto.ProductRequest;
import com.paymentservice.dto.StripeResponse;
import com.paymentservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/v1")
public class ProductCheckoutController {

    private StripeService stripeService;

     private BookingClient bookingClient;

    public ProductCheckoutController(StripeService stripeService, BookingClient bookingClient) {
        this.stripeService = stripeService;
        this.bookingClient = bookingClient;
    }


    // http://localhost:8084/product/v1/checkout

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }


    @GetMapping("/success")
    public ResponseEntity<String> handleSuccess(@RequestParam("session_id") String sessionId,  @RequestParam("bookingId") long bookingId) {
        Stripe.apiKey = "sk_test_51SmEzrHse2hHVesu6Mr2MgGkOySs3u0r0WZHdV49fEJK9hlJc0PQ0XMsMRCx3BBE9E4WYsm54spheKt059x3KsvQ00Yzbq6VEb";

        try {
            Session session = Session.retrieve(sessionId);
            String paymentStatus = session.getPaymentStatus();

            if ("paid".equalsIgnoreCase(paymentStatus)) {
                //Update Booking Details
                BookingConfirmation confirm = bookingClient.getBookingsById(bookingId);
                 confirm.setStatus(true);
                 bookingClient.confirmBooking(confirm);
                return ResponseEntity.ok("Payment successful");
            } else {
                System.out.println("❌ Payment not completed: false");
                return ResponseEntity.status(400).body("Payment not completed");
            }

        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Stripe error occurred");
        }
    }


    @GetMapping("/cancel")
    public ResponseEntity<String> handleCancel() {
        System.out.println("❌ Payment cancelled: false");
        return ResponseEntity.ok("Payment cancelled");
    }

}
