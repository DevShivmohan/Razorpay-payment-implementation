package org.shiv.razorpay.model;

import lombok.Builder;

@Builder
public class OrderResponse {
    private String orderId;
    private int amount;
    private int amountPaid;
    private int amountDue;
    private String currency;
    private String receipt;
    private String entity;
    private String status;
    private int attempts;
}
