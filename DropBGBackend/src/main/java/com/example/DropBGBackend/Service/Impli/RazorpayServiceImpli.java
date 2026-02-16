package com.example.DropBGBackend.Service.Impli;

import com.example.DropBGBackend.DTO.UserDTO;
import com.example.DropBGBackend.Entity.OrderEntity;
import com.example.DropBGBackend.Repository.OrderRepository;
import com.example.DropBGBackend.Service.RazorpayService;
import com.example.DropBGBackend.Service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RazorpayServiceImpli implements RazorpayService {

    private final UserService userService;
    @Value("${Test_API_Key}")
    private String razorpayKeyId;
    @Value("${Test_Key_Secret}")
    private String razorpaySecretKey;
    private final OrderRepository orderRepository;

    @Override
    public Order createdOrder(Integer amount, String currency) throws RazorpayException {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpaySecretKey);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",amount*100);
            orderRequest.put("currency",currency);
            orderRequest.put("receipt","order_rcptid_"+System.currentTimeMillis());
            orderRequest.put("payment_capture",1);
            return razorpayClient.orders.create(orderRequest);
        }catch (RazorpayException e){
            e.printStackTrace();
            throw new RazorpayException("Razorpay Error: "+e.getMessage());
        }
    }

    @Override
    public Map<String, Object> verifyPayment(String razorpayOrderId) throws RazorpayException {
        Map<String, Object> returnValue = new HashMap<>();
        try{
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpaySecretKey);
            Order orderInfo = razorpayClient.orders.fetch(razorpayOrderId);
            if (orderInfo.get("status").toString().equalsIgnoreCase("paid")){
                OrderEntity existingOrder = orderRepository.findByOrderId(razorpayOrderId)
                        .orElseThrow(() -> new RazorpayException("Order Not Found"));
                if (existingOrder.getPayment()){
                    returnValue.put("success",false);
                    returnValue.put("message","Payment Failed");
                    return returnValue;
                }
                UserDTO userDTO = userService.getUserByClerkId(existingOrder.getClerkId());
                userDTO.setCredits(userDTO.getCredits()+existingOrder.getCredits());
                userService.saveUser(userDTO);
                existingOrder.setPayment(true);
                orderRepository.save(existingOrder);
                returnValue.put("success",true);
                returnValue.put("message","Payment Successfull and Credits Added");
                System.out.println("Order Status: ");
                System.out.println(orderInfo);
                return returnValue;
            }
        }catch (RazorpayException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error While Varifying the Payment");
        }
        return returnValue;
    }
}
