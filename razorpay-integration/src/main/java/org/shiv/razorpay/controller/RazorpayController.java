package org.shiv.razorpay.controller;

import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.shiv.razorpay.exception.GenericException;
import org.shiv.razorpay.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/razorpay")
@Slf4j
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

    /**
     * Note:- This part for test level , in production this will not be able to requested via client
     * it will automatically schedule when a user payment success but failed to capture on our server
     * @param paymentId
     * @return
     * @throws RazorpayException
     * @throws GenericException
     */
    @PostMapping(value = "/refund")
    public ResponseEntity<?> paymentSave(@RequestBody String paymentId) throws RazorpayException, GenericException {
        log.info("Shiv id-"+paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(razorpayService.refundPayment(paymentId));
    }


    /**
     * Generate a payment link with callback url and 16 minutes link expiry
     * @param req
     * @return
     * @throws RazorpayException
     */
    @PostMapping(value = "/link")
    public ResponseEntity<?> generatePaymentLink(@RequestBody Map<String,Object> req) throws RazorpayException {
        log.info("Shiv id-"+req.toString());
        return razorpayService.generatePaymentLink(req);
    }

    @GetMapping(value = "/callback/{auth}")
    public ResponseEntity<?> handleCallbackOfPaymentLink(@PathVariable("auth") String authorization,@RequestParam Map<String,String> callbackRequest) throws RazorpayException {
        log.info("Authorization-"+authorization);
        callbackRequest.put("authorization",authorization);
        return razorpayService.handleCallbackOfPaymentLink(callbackRequest);
    }
}
