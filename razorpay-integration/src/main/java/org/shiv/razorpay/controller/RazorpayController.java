package org.shiv.razorpay.controller;

import com.razorpay.RazorpayException;
import org.shiv.razorpay.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/razorpay")
public class RazorpayController {

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping(value = "/order")
    public ResponseEntity<?> generateOrder(@RequestBody Map<String,Object> reqBody) throws RazorpayException {
        return ResponseEntity.status(HttpStatus.OK).body(razorpayService.generateOrder(reqBody).toString());
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> paymentSave(@RequestBody Map<String,Object> reqBody) throws RazorpayException {
        return ResponseEntity.status(HttpStatus.OK).body(razorpayService.afterPaymentCaptured(reqBody));
    }
}
