package com.store.validation.otp;

import org.springframework.stereotype.Component;

import java.util.SplittableRandom;
import java.util.stream.IntStream;

@Component
public class OTPGeneratorIml implements OTPGenarator{
    @Override
    public String generateOTP(int otpLength) {
        SplittableRandom splittableRandom = new SplittableRandom();
        StringBuilder stringBuilder = new StringBuilder();
        IntStream stream = splittableRandom.ints(otpLength, 0, 10);// stream of random inst
        stream.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
