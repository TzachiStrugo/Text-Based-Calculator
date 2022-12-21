package org.tzachi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class CalculatorManager {

    Map<String, Integer> calcVariablesMap = new LinkedHashMap<>();

    public int calculate(String input) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException("Expression can not be empty.");
        }

        Map<String, Integer> expressionVariablesMap = new LinkedHashMap<>(calcVariablesMap);
        Map<String, Integer> postIncrementVariablesMap = new HashMap<>();

        Expression expression = new Expression(input, expressionVariablesMap, postIncrementVariablesMap);
        int result = expression.evaluate();

        expressionVariablesMap.put(expression.getAssignedVariable(), result);
        expressionVariablesMap.putAll(postIncrementVariablesMap);

        calcVariablesMap = expressionVariablesMap;
        return result;
    }

    public String getResult() {

        StringBuilder builder = new StringBuilder("(");
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(calcVariablesMap.entrySet());

        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<String, Integer> entry = entries.get(i);
            // Example format: (k1=v1,k2=v2)
            builder.append(entry.getKey()).append("=").append(entry.getValue());
            if (i < entries.size() - 1) {
                builder.append(",");
            } else {
                builder.append(")");
            }
        }
        return builder.toString();
    }
}
