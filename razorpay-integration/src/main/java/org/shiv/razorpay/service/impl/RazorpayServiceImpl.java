package org.shiv.razorpay.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.shiv.razorpay.service.RazorpayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class RazorpayServiceImpl implements RazorpayService {

    @Override
    public ResponseEntity<?> generateOrder(Map<String, Object> requestBody) throws RazorpayException {
        RazorpayClient razorpayClient=new RazorpayClient("rzp_test_7042XNF8NBeP5W","azGfwQDpzvNUf0p47fCYdrMM");
        JSONObject orderObject=new JSONObject();
        orderObject.put("amount",requestBody.get("amount"));
        orderObject.put("currency","INR");
        orderObject.put("receipt", UUID.randomUUID().toString());
        Order order=razorpayClient.orders.create(orderObject);
        System.out.println(order);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
