package com.prizy.pricer.beanshell;


import bsh.EvalError;
import bsh.Interpreter;
import com.prizy.pricer.rule.ProductIdealPriceRule;
import com.prizy.pricer.rule.ProductIdealPriceRuleImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class uses  BeanShell to interpret dynamic rules
 */
@Slf4j
public class DynamicRuleInterpreter {
	

    public static BigDecimal interpretProductIdealPriceRule(List<BigDecimal> priceList){
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String ruleDirectory = bundle.getString("rule.directory");
        String ruleFilePath = ruleDirectory + File.separator + ProductIdealPriceRule.class.getSimpleName() + "-rule.bsh";

        ProductIdealPriceRule idealPriceRule = new ProductIdealPriceRuleImpl();
        Interpreter interpreter = new Interpreter();

        try {
            interpreter.set("priceList", priceList);
            interpreter.set("idealPriceRule", idealPriceRule);
            Object result = interpreter.source(ruleFilePath);
            return (BigDecimal)result;
        } catch (IOException | EvalError e) {
            log.error("error while interpreting", e);
        }
        return null;
    }

}
