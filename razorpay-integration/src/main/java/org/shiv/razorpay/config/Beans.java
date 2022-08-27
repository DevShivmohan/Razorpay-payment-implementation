package org.shiv.razorpay.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient("rzp_test_LUKaL2EYvgMhdH","nTFd5vbghcD5FtfEAMu18xLS");
    }
}
