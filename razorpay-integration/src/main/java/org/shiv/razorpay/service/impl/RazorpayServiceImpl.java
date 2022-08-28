package org.shiv.razorpay.service.impl;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.shiv.razorpay.exception.GenericException;
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
        orderObject.put("amount",Integer.parseInt(""+requestBody.get("amount"))*100);
        orderObject.put("currency","INR");
        orderObject.put("receipt", UUID.randomUUID().toString());
        var order=razorpayClient.orders.create(orderObject);
        requestBody.clear();
        requestBody.put("order_id",order.get("id"));
        requestBody.put("amount",order.get("amount"));
        requestBody.put("amount_paid",order.get("amount_paid"));
        requestBody.put("amount_due",order.get("amount_due"));
        requestBody.put("currency",order.get("currency"));
        requestBody.put("receipt",order.get("receipt"));
        requestBody.put("entity",order.get("entity"));
        requestBody.put("status",order.get("status"));
        requestBody.put("attempts",order.get("attempts"));
        log.info(requestBody.toString());
        return ResponseEntity.status(HttpStatus.OK).body(requestBody);
    }

    @Override
    public ResponseEntity<?> afterPaymentCaptured(Map<String, Object> responseBody) throws RazorpayException, GenericException {
        var paymentData= razorpayClient.payments.fetch(responseBody.get("p_id").toString());
        if(!paymentData.get("order_id").equals(responseBody.get("o_id")))
            throw new GenericException(HttpStatus.EXPECTATION_FAILED.value(), "Your payment not successful");
        log.info("Payments-"+responseBody);
        responseBody.clear();
        responseBody.put("status","Payment successfully captured on server");
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    /**
     * this happend on server side automatically not for request able
     * @param paymentId this is id of which payment will be to return
     * @return
     * @throws RazorpayException
     */
    @Override
    public ResponseEntity<?> refundPayment(String paymentId) throws RazorpayException {
        var refund= razorpayClient.payments.refund(paymentId,new JSONObject().put("speed","normal"));
        log.info(refund.toString());
        return ResponseEntity.ok().body(refund);
    }
}
