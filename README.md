# Razorpay-payment-implementation
Razorpay payment implementation


**For Refund generation within a certain time period** - https://razorpay.com/docs/payments/optimizer/capture-refund-settings


**Payment link callbacks contains these parts in URL with GET request**

```
https://example-callback-url.com/?razorpay_payment_id=pay_Fc8mUeDrEKf08Y&razorpay_payment_link_id=plink_Fc8lXILABzQL7M&
razorpay_payment_link_reference_id=TSsd1989&
razorpay_payment_link_status=partially_paid&razorpay_signature=b0ea302006d9c3da504510c9be482a647d5196b265f5a82aeb272888dcbee70e
```

**Validating IFSC code open API**

GET - `https://ifsc.razorpay.com/<ifsc>`
GET - `https://ifsc.razorpay.com/sbin0009981`
