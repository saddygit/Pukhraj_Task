package com.prizy.pricer.dto;


import com.prizy.pricer.domain.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPriceDetail {
    private Product product;
    private BigDecimal idealPrice;
    private BigDecimal averagePrice;
    
    private BigDecimal lowestPrice;
    private BigDecimal highestPrice;
}
