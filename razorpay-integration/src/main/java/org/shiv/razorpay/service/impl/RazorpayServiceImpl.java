package org.shiv.razorpay.service.impl;

import com.razorpay.PaymentLink;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Override
    public ResponseEntity<?> generatePaymentLink(Map<String, Object> requestBody) throws RazorpayException {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",(Long.parseLong((String) requestBody.get("amount"))*100));
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",true);
        paymentLinkRequest.put("first_min_partial_amount",100); // Rs 1 minimum to pay
        paymentLinkRequest.put("expire_by", TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() + (16 * 60 * 1000)));
        paymentLinkRequest.put("reference_id",UUID.randomUUID().toString());
        paymentLinkRequest.put("description","Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name", requestBody.get("name"));
        customer.put("contact",requestBody.get("mobile"));
        customer.put("email",requestBody.get("email"));
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Learning");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","http://localhost:8090/razorpay/callback");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        log.info(payment.toString());
        return ResponseEntity.ok(payment.get("short_url"));
    }

    @Override
    public ResponseEntity<?> handleCallbackOfPaymentLink(String paymentId,String paymentLinkId,String paymentLinkRefId,String paymentLinkStatus,String razorpaySignature) {
        Map<String,Object> response=new HashMap<>();
        response.put("paymentId",paymentId);
        response.put("paymentLinkId",paymentLinkId);
        response.put("paymentLinkRefId",paymentLinkRefId);
        response.put("paymentLinkStatus",paymentLinkStatus);
        response.put("razorpaySignature",razorpaySignature);
        response.put("message","Payment captured successfully on our server");
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }
}
