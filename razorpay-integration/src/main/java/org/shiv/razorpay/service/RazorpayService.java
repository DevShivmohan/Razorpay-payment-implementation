package org.shiv.razorpay.service;

import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RazorpayService {
    ResponseEntity<?> generateOrder(Map<String,Object> requestBody) throws RazorpayException;
}
