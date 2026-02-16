package com.example.DropBGBackend.Controller;

import com.example.DropBGBackend.DTO.RazorpayOrderDTO;
import com.example.DropBGBackend.Response.DropBGResponse;
import com.example.DropBGBackend.Service.OrderService;
import com.example.DropBGBackend.Service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final RazorpayService razorpayService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestParam String planId,Authentication authentication) throws RazorpayException {
        Map<String,Object> map = new HashMap<>();
        DropBGResponse response = null;
        if (authentication == null || authentication.getName() == null) {
            response = DropBGResponse.builder()
                    .statusCode(HttpStatus.FORBIDDEN)
                    .data("User doesn't have the permission to access the information")
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        try {
            Order order = orderService.createOrder(planId,authentication.getName());// clerkId is the authentication.getName in DB
            System.out.println("Razorpay version: ");
            System.out.println(order);
            if (!Objects.equals(order.get("status").toString(), "paid")) {
                System.out.println("Payment Not Done Yet");
            }
            RazorpayOrderDTO responseDTO = convertToDTO(order);
            response = DropBGResponse.builder()
                    .success(true)
                    .data(responseDTO)
                    .statusCode(HttpStatus.CREATED)
                    .build();
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            response = DropBGResponse.builder()
                    .success(false)
                    .data(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private RazorpayOrderDTO convertToDTO(Order order) {
        return RazorpayOrderDTO.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .status(order.get("status"))
                .created_at(order.get("created_at"))
                .receipt(order.get("receipt"))
                .build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOrder(@RequestBody Map<String,Object> request) throws RazorpayException {
        try {
            String razorpayOrderId = request.get("razorpay_order_id").toString();
            Map<String,Object> returnValue = razorpayService.verifyPayment(razorpayOrderId);
            return ResponseEntity.ok(returnValue);
        } catch (RazorpayException e) {
            Map<String,Object> errorResponse = new HashMap<>();
            errorResponse.put("status",false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
