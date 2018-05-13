package com.prizy.pricer.service;


import com.prizy.pricer.beanshell.DynamicRuleInterpreter;
import com.prizy.pricer.dto.ProductPriceDetail;
import com.prizy.pricer.rule.ProductIdealPriceRule;
import com.prizy.pricer.rule.ProductIdealPriceRuleImpl;
import com.prizy.pricer.service.repository.ProductRepository;
import com.prizy.pricer.service.repository.ProductSurveyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
 
public class IdealPriceServiceImpl implements IdealPriceService {

    @Autowired
    ProductSurveyRepository loaderRepository;

    @Autowired
    ProductRepository productRepository;


    @Override
    public ProductPriceDetail getProductPriceDetail(String barcode) {
        //ProductIdealPriceRule rule = new RuleFactory<ProductIdealPriceRule>().getRule(ProductIdealPriceRule.class);

        ProductIdealPriceRule rule = new ProductIdealPriceRuleImpl();
        List<BigDecimal> priceList = loaderRepository.getPrice(barcode);

        List<BigDecimal> clonedList = new ArrayList<>(priceList);

        BigDecimal idealPrice = DynamicRuleInterpreter.interpretProductIdealPriceRule(clonedList);

        Collections.sort(priceList);

        ProductPriceDetail detail = new ProductPriceDetail();

        detail.setProduct(productRepository.findByBarcode(barcode));

        detail.setIdealPrice(idealPrice);
        detail.setAveragePrice(rule.calculateAveragePrice(priceList));
        detail.setLowestPrice(rule.calculateLowestPrice(priceList));
        detail.setHighestPrice(rule.calculateHighestPrice(priceList));

        return detail;
    }
}
