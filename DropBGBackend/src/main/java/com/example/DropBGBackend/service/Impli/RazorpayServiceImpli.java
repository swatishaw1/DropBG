package com.example.DropBGBackend.service.Impli;

import com.example.DropBGBackend.dto.UserDTO;
import com.example.DropBGBackend.model.OrderEntity;
import com.example.DropBGBackend.repository.OrderRepository;
import com.example.DropBGBackend.service.RazorpayService;
import com.example.DropBGBackend.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RazorpayServiceImpli implements RazorpayService {

    private final UserService userService;
    @Value("${Test_API_Key}")
    private String razorpayKey;
    @Value("${Test_Key_Secret}")
    private String razorpaySecretKey;
    private final OrderRepository orderRepository;

    @Override
    public Order createdOrder(Integer amount, String currency) throws RazorpayException {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKey,razorpaySecretKey);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",amount*100);
            orderRequest.put("currency",currency);
            orderRequest.put("receipt","order_rcptid_"+System.currentTimeMillis());
            orderRequest.put("payment_capture",1);
            return razorpayClient.orders.create(orderRequest);
        }catch (RazorpayException e){
            log.error(e.getMessage());
            throw new RazorpayException("Razorpay Error: "+e.getMessage());
        }
    }

    @Override
    public Map<String, Object> verifyPayment(String razorpayOrderId) throws RazorpayException {
        Map<String, Object> returnValue = new HashMap<>();
        try{
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKey,razorpaySecretKey);
            Order orderInfo = razorpayClient.orders.fetch(razorpayOrderId);
            if (orderInfo.get("status").toString().equalsIgnoreCase("paid") &&
                    orderInfo.get("amount").toString().equalsIgnoreCase(orderInfo.get("amount_paid").toString())){
                OrderEntity existingOrder = orderRepository.findByOrderId(razorpayOrderId)
                        .orElseThrow(() -> new RazorpayException("Order Not Found"));
                if (existingOrder.getPayment()){
                    returnValue.put("success",false);
                    returnValue.put("message","Payment Failed");
                    return returnValue;
                }
                //Updating Credits
                UserDTO userDTO = userService.getUserByClerkId(existingOrder.getClerkId());
                userDTO.setCredits(userDTO.getCredits()+existingOrder.getCredits());
                userService.saveUser(userDTO);

                existingOrder.setPayment(true);
                orderRepository.save(existingOrder);
                returnValue.put("success",true);
                returnValue.put("message","Payment Successful and Credits Added");
                log.info("Payment Successful and Credits Added");
                log.info("Order: {}",existingOrder);
                return returnValue;
            }
        }catch (RazorpayException e){
            log.error("Verification problem: {}",e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error While Varifying the Payment");
        }
        return returnValue;
    }
}
