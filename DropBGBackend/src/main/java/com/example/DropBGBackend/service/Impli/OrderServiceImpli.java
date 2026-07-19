package com.example.DropBGBackend.service.Impli;
import com.example.DropBGBackend.model.OrderEntity;
import com.example.DropBGBackend.repository.OrderRepository;
import com.example.DropBGBackend.service.OrderService;
import com.example.DropBGBackend.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpli implements OrderService {

    private final RazorpayService razorpayService;
    private final OrderRepository orderRepository;

    private record PlanDetails(String name,int credits,int amount){
    }

    private static final Map<String,PlanDetails> PLAN_DETAILS = Map.of(
            "Basic", new PlanDetails("Basic",100,499),
            "Premium", new PlanDetails("Premium",250,899),
            "Ultimate", new PlanDetails("Ultimate",1000,1499)
    );
    @Override
    public Order createOrder(String planId, String clerkId) throws RazorpayException {
        PlanDetails planDetails = PLAN_DETAILS.get(planId);
        if (planDetails == null){
            throw new IllegalArgumentException("Plan id " + planId + " doesn't exist");
        }
        try{
            Order razorpayOrder = razorpayService.createdOrder(planDetails.amount(),"INR");
            OrderEntity newOrder = OrderEntity.builder()
                    .clerkId(clerkId)
                    .plan(planDetails.name())
                    .credits(planDetails.credits())
                    .amount(planDetails.amount())
                    .orderId(razorpayOrder.get("id"))
                    .build();
            log.info("Order created in DB which has id:{}",newOrder.getId());
            log.info("Order: {}",newOrder);
            if (orderRepository.findByOrderId(razorpayOrder.get("id")).isPresent()) {
                log.error("Order already exists in DB");
                throw new RazorpayException("Duplicate order");
            }
            orderRepository.save(newOrder);
            return razorpayOrder;
        } catch (RazorpayException e) {
            throw new RazorpayException("Error while creating the payment: "+e);
        }
    }
}
