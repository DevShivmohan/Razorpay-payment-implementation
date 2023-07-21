package org.shiv.razorpay.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    public static final String AUTHORIZATION="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGl2QGdtYWlsLmNvbSIsInVzZXJfdHlwZSI6IlZlbmRvciIsInRva2VuX3R5cGUiOiJBY2Nlc3NfVG9rZW4iLCJleHAiOjE2ODY1OTA1MjYsImlhdCI6MTY4NjU2ODkyNiwidXNlcm5hbWUiOiJzaGl2QGdtYWlsLmNvbSJ9.k_ujY3t3cIyFHHIf_DbRY6FShTuho4dqN-uWkYMelOU";
    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient("rzp_test_IbeKrCqc7m9rbf","AAR5oEbGx8Wu5CUf7FI4k536");
    }
}
