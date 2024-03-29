package org.shiv.razorpay.service;

import com.razorpay.RazorpayException;
import org.shiv.razorpay.exception.GenericException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.Map;

public interface RazorpayService {
    ResponseEntity<?> generateOrder(Map<String,Object> requestBody) throws RazorpayException;
    ResponseEntity<?> afterPaymentCaptured(Map<String,Object> responseBody) throws RazorpayException, GenericException;

    ResponseEntity<?> refundPayment(String paymentId) throws RazorpayException;
    ResponseEntity<?> generatePaymentLink(Map<String,Object> responseBody) throws RazorpayException, UnknownHostException;
    ResponseEntity<?> handleCallbackOfPaymentLink(Map<String,String> callBackRequest);


}
