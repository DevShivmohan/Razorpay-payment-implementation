package org.shiv.razorpay.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient("rzp_test_IbeKrCqc7m9rbf","AAR5oEbGx8Wu5CUf7FI4k536");
    }
}
