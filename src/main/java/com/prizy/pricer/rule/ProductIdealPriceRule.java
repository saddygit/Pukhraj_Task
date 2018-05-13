package com.prizy.pricer.rule;


import java.math.BigDecimal;
import java.util.List;

public interface ProductIdealPriceRule extends Rule {
    //BigDecimal calculateProductIdealPrice(List<BigDecimal> priceList);
    BigDecimal calculateAveragePrice(List<BigDecimal> priceList);
    BigDecimal calculateLowestPrice(List<BigDecimal> priceList);
    BigDecimal calculateHighestPrice(List<BigDecimal> priceList);
    
}
