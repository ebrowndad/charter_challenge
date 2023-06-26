package com.brownmunoz.pricingchallenge.web;

import com.brownmunoz.pricingchallenge.data.Transaction;
import com.brownmunoz.pricingchallenge.services.PricingService;
import com.opencsv.exceptions.CsvException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PricingController {

    private final PricingService pricingService;

    PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }
    @GetMapping("/")
    public String getInstructions() {
        return """
                Usage: send a CSV file to
                    POST /calculate_points
                    
                Returns a JSON object with month and points.
                                
                Example (run from scripts directory):
                    curl -X POST -H "Content-Type: text/csv" --data-binary @test2.csv http://localhost:8080/price
                    (--data-binary ensures that Curl keep newlines).
                    
                See README.md for more information.
                """;
    }

    @PostMapping(
            path = "/prices",
            consumes = {"text/csv"}
            )
    public Map<String, Map<YearMonth, Integer>> getPrices(@RequestBody String csvlist) throws IOException, CsvException {

        Map<String, Map<YearMonth, Integer>> finalReport = new HashMap<>();

        for (Map.Entry<String, List<Transaction>> customerTransaction :
                pricingService.getCustomerTransactions(csvlist).entrySet()) {

            String customer = customerTransaction.getKey();
            Map<YearMonth, Integer> transactionListForcustomer =
                    pricingService.getMonthlyPointsReport(customerTransaction.getValue());

            finalReport.put(customer, transactionListForcustomer);
        }

        return finalReport;
    }
}
