package com.example.DropBGBackend.Service.Impli;
import com.example.DropBGBackend.DTO.UserDTO;
import com.example.DropBGBackend.Entity.OrderEntity;
import com.example.DropBGBackend.Repository.OrderRepository;
import com.example.DropBGBackend.Service.OrderService;
import com.example.DropBGBackend.Service.RazorpayService;
import com.example.DropBGBackend.Service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderServiceImpli implements OrderService {

    private final RazorpayService razorpayService;
    private final OrderRepository orderRepository;
    private final UserService userService;

    private static final Map<String,PlanDetails> PLAN_DETAILS = Map.of(
            "Basic", new PlanDetails("Basic",100,499),
            "Premium", new PlanDetails("Premium",250,899),
            "Ultimate", new PlanDetails("Ultimate",1000,1499)
    );

    private record PlanDetails(String name,int credits,int amount){
    }
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
            System.out.println("New Order created ");
            System.out.println("Order in DB: ");
            System.out.println(newOrder);
            if (orderRepository.findByOrderId(razorpayOrder.get("id")).isPresent()) {
                throw new RazorpayException("Duplicate order");
            }
            orderRepository.save(newOrder);
            return razorpayOrder;
        } catch (RazorpayException e) {
            throw new RazorpayException("Error while creating the payment: "+e);
        }
    }
}
