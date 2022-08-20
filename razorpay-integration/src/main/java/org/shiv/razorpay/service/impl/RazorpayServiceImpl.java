package org.shiv.razorpay.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.shiv.razorpay.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class RazorpayServiceImpl implements RazorpayService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Override
    public ResponseEntity<?> generateOrder(Map<String, Object> requestBody) throws RazorpayException {
        JSONObject orderObject=new JSONObject();
        orderObject.put("amount",requestBody.get("amount"));
        orderObject.put("currency","INR");
        orderObject.put("receipt", UUID.randomUUID().toString());
        Order order=razorpayClient.orders.create(orderObject);
        System.out.println(order);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @Override
    public ResponseEntity<?> afterPaymentCaptured(Map<String, Object> responseBody) throws RazorpayException {
        var data= razorpayClient.payments.fetch(responseBody.get("p_id").toString());
        System.out.println("Payments-"+data);
        return ResponseEntity.status(HttpStatus.OK).body("Payment successfully captured on server");
    }
}
