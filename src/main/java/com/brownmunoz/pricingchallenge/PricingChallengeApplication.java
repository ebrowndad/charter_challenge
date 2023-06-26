package com.brownmunoz.pricingchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( scanBasePackages = {"com.brownmunoz.pricingchallenge"})
public class PricingChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingChallengeApplication.class, args);
    }

}
