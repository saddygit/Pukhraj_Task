package com.prizy.pricer.rule.factory;


import com.prizy.pricer.compiler.RuleCompiler;
import com.prizy.pricer.exception.NoRuleClassFoundException;
import com.prizy.pricer.rule.Rule;

public class RuleFactory<T extends Rule> {
    public T getRule(Class<T> ruleClazz){
        if(ruleClazz == null){
            throw new IllegalArgumentException("Rule class can't be null");
        }
        
        Object dynamicClass = RuleCompiler.getDynamicClass(ruleClazz);

        if(ruleClazz.getClass().isAssignableFrom(dynamicClass.getClass())){
            return (T) dynamicClass;
        }
        throw new NoRuleClassFoundException("Not Rule Found " + ruleClazz.getClass().getName());
    }
}
