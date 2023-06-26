package com.brownmunoz.pricingchallenge.services;

import com.brownmunoz.pricingchallenge.data.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PricingServiceTest {
    PricingService pricingServiceUnderTest = new PricingService();

    @Test
    void testGetPointsForPrice() {
        assertEquals(0, pricingServiceUnderTest.getPointsForPrice (new BigDecimal(49)));
        assertEquals(0, pricingServiceUnderTest.getPointsForPrice (new BigDecimal(50)));
        assertEquals(50, pricingServiceUnderTest.getPointsForPrice (new BigDecimal(100)));
        assertEquals(52, pricingServiceUnderTest.getPointsForPrice (new BigDecimal(101)));

        assertEquals(52, pricingServiceUnderTest.getPointsForPrice(BigDecimal.valueOf(101.23)));
        assertEquals(50, pricingServiceUnderTest.getPointsForPrice(BigDecimal.valueOf(100.78)));
        assertEquals(1,1);
    }

    @Test
    void testGetPointsForPriceList() {
        List<BigDecimal>  priceList = new ArrayList<>();
        priceList.add (new BigDecimal(100)); // 50
        priceList.add (new BigDecimal(101)); // 52
        priceList.add (BigDecimal.valueOf(101.23)); // 52

        assertEquals(154, pricingServiceUnderTest.getPointsForPriceList(priceList));
    }

    @Test
    void testGetMonthlyPriceReport() {
        List<Transaction> input = new ArrayList<>();
        input.add (new Transaction("bob", LocalDate.of(2023, 1, 23), BigDecimal.valueOf(49)));
        input.add (new Transaction("bob", LocalDate.of(2023, 2, 23), BigDecimal.valueOf(103))); // 56

        input.add (new Transaction("bob", LocalDate.of(2023, 1, 4), BigDecimal.valueOf(52)));
        input.add (new Transaction("bob", LocalDate.of(2023, 2, 6), BigDecimal.valueOf(53))); // 3

        Map<YearMonth, Integer> monthlyPointsReport = pricingServiceUnderTest.getMonthlyPointsReport(input);

        assertEquals (2, monthlyPointsReport.get(YearMonth.parse("2023-01")));
        assertEquals (59, monthlyPointsReport.get(YearMonth.parse("2023-02")));
    }
}
