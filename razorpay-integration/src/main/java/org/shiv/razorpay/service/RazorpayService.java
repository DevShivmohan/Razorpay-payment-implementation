package org.shiv.razorpay.service;

import com.razorpay.RazorpayException;
import org.shiv.razorpay.exception.GenericException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RazorpayService {
    ResponseEntity<?> generateOrder(Map<String,Object> requestBody) throws RazorpayException;
    ResponseEntity<?> afterPaymentCaptured(Map<String,Object> responseBody) throws RazorpayException, GenericException;
}
