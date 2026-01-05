package com.example.DropBGBackend.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

import java.util.Map;

public interface RazorpayService {
    Order createdOrder(Double amount, String currency) throws RazorpayException;
    Map<String,Object> verifyPayment(String paymentId) throws RazorpayException;
}
