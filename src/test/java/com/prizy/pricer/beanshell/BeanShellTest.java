package com.prizy.pricer.beanshell;

import bsh.EvalError;
import bsh.Interpreter;
import com.prizy.pricer.rule.ProductIdealPriceRule;
import com.prizy.pricer.rule.ProductIdealPriceRuleImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BeanShellTest {

	
    public static void main(String[] args) throws IOException, EvalError {

        Interpreter interpreter = new Interpreter();


        List<BigDecimal> priceList = new ArrayList<>();
        priceList.add(new BigDecimal(32.0));
        priceList.add(new BigDecimal(32.0));
        priceList.add(new BigDecimal(342.0));
        priceList.add(new BigDecimal(35.0));
        priceList.add(new BigDecimal(62.0));
        priceList.add(new BigDecimal(37.0));

        interpreter.set("priceList", priceList);

        ProductIdealPriceRule idealPriceRule = new ProductIdealPriceRuleImpl();
        interpreter.set("idealPriceRule", idealPriceRule);

        Object result = interpreter.source("ProductIdealPriceRule-rule.bsh");

        System.out.println("result " + result);

    }
}
