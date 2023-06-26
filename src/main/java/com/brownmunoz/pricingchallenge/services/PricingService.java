package com.brownmunoz.pricingchallenge.services;

import com.brownmunoz.pricingchallenge.data.Transaction;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

@Service
public class PricingService {

    private static BigDecimal FIFTY = new BigDecimal(50);
    private static BigDecimal HUNDRED = new BigDecimal(100);


    /**
     * Get number of points
     * @param price
     * @return number of points awarded
     */
    public static Integer getPointsForPrice(BigDecimal price) {
        double points = 0.0;
        if (price.compareTo(HUNDRED) > 0) { // > 100
            points = 50.0 + Math.floor(price.doubleValue() - 100.0) * 2.0;
        } else if (price.compareTo(FIFTY) > 0) { // 50 < p < 100 )
            points = Math.floor(price.doubleValue() - 50.0);
        }

        return (int) points;
    }

    /**
     * Cumulative Points for a list of prices.
     * @param prices
     * @return
     */
    public Integer getPointsForPriceList(List<BigDecimal> prices) {
        return prices.stream().mapToInt(PricingService::getPointsForPrice).sum();
    }

    /**
     * Cumulative points for each YearMonth. Note that the customer is ignored. The assumption is that
     * the customer has already been aggregated.
     * @param pricePairs
     * @return
     */
    public Map<YearMonth, Integer> getMonthlyPointsReport(List<Transaction> pricePairs) {
        Map<YearMonth, List<BigDecimal>> monthlyPrices = new HashMap<>();
        Map<YearMonth, Integer> monthlyPoints = new HashMap<>();

        for (Transaction transaction : pricePairs ) {
            monthlyPrices.computeIfAbsent(
                    YearMonth.from(transaction.getDate()),
                    p -> new ArrayList()
                    ).add(transaction.getPrice());
        }

        for (Map.Entry<YearMonth, List<BigDecimal>> entry : monthlyPrices.entrySet()) {
            monthlyPoints.put(entry.getKey(), getPointsForPriceList(entry.getValue()));
        }

        return monthlyPoints;
    }

    /**
     * parses CSV list to aggregate transactions by customer.
     *
     * @param csvlist
     * @return
     * @throws IOException
     * @throws CsvException
     */
    public Map<String, List<Transaction>> getCustomerTransactions(String csvlist) throws IOException, CsvException {
        Map<String, List<Transaction>> customers = new HashMap<>();
        // collect transactions by customer
        try (CSVReader reader = new CSVReader(new StringReader(csvlist))) {
            for (String[] eachLine : reader.readAll()) {

                if (eachLine.length <= 1) {
                    continue;
                }

                // In a real app, I would handle exceptions in an elegant way
                String customer = eachLine[0];

                LocalDate date = LocalDate.parse(eachLine[1].trim());
                BigDecimal price = new BigDecimal(eachLine[2].trim());

                customers.computeIfAbsent(customer, p -> new ArrayList()).add(new Transaction(customer, date, price));
            }
        }

        return customers;
    }
}
