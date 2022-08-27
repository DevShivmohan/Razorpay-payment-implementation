package org.shiv.razorpay.controller;

import com.razorpay.RazorpayException;
import org.shiv.razorpay.exception.GenericException;
import org.shiv.razorpay.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/order",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generateOrder(@RequestBody Map<String,Object> reqBody) throws RazorpayException {
        return ResponseEntity.status(HttpStatus.OK).body(razorpayService.generateOrder(reqBody));
    }

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentSave(@RequestBody Map<String,Object> reqBody) throws RazorpayException, GenericException {
        return ResponseEntity.status(HttpStatus.OK).body(razorpayService.afterPaymentCaptured(reqBody));
    }
}
