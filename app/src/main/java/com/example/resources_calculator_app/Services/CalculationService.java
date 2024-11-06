package com.example.resources_calculator_app.Services;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculationService {
    private String content;

    private final String nextSymbol;
    private final String digitRegex = "\\d";
    private final String lastNumberRegex = "(.*?)(-?[0-9]*\\.?[0-9]+)$";
    private final String operationsRegex = "[*/+\\-.]";

    public CalculationService(String content, String nextSymbol) {
        this.content = content;
        this.nextSymbol = nextSymbol;
    }

    public void calculateExpression() {
        if (nextSymbol.equals("C")) {
            content = "";
            return;
        }

        if (contentContainsEqual()) {
            return;
        }

        double result;

        if (nextSymbol.matches(digitRegex)) {
            if (content.equals("0")) {
                content = "";
            }

            content += nextSymbol;
        } else if (content.isEmpty()) {
            return;
        } else if (nextSymbol.equals("=")) {
            if (!deleteOperationIfExists())
                return;

            if (!content.contains("/0")) {
                result = calculate();

                content += "=" + convertDoubleToString(result);
            } else {
                content = "0";
            }
        } else if (nextSymbol.equals("X")) {
            deleteOperationIfExists();

            content += "*";
        } else if (nextSymbol.equals("%")) {
            deleteOperationIfExists();

            String lastNumber = getLastContentNumber();

            if (!lastNumber.equals("empty")) {
                double percentNumber = Double.parseDouble(lastNumber);
                percentNumber /= 100.0;

                String buffContent = "";

                if (content.isEmpty()) {
                    result = percentNumber;
                } else {
                    buffContent = content;
                    deleteOperationIfExists();

                    result = calculate();
                    result *= percentNumber;
                }
                content = buffContent + convertDoubleToString(result);
            }
        } else if (nextSymbol.equals("+/-")) {
            String lastNumber = getLastContentNumber();

            deleteOperationIfExists();

            if (lastNumber.equals("empty")) {
                return;
            } else if (lastNumber.contains("-")) {
                lastNumber = lastNumber.substring(1, lastNumber.length());

                content += "+" + lastNumber;
            } else {
                content += "-" + lastNumber;
            }
        } else if (nextSymbol.equals(".")) {
            String lastNumber = getLastContentNumber();

            if (lastNumber.equals("empty")) {
                return;
            } else if (!lastNumber.contains(nextSymbol)) {
                content += lastNumber + nextSymbol;
            }
        } else if (nextSymbol.matches(operationsRegex)) {
            deleteOperationIfExists();

            content += nextSymbol;
        } else {
            content = "";
        }
    }

    public String getContent() {
        return content;
    }

    private double calculate() {
        Expression expression = new ExpressionBuilder(content).build();
        return expression.evaluate();
    }

    private boolean contentContainsEqual() {
        if (content.contains("=")) {
            int equalIndex = content.indexOf('=');
            content = content
                    .substring(equalIndex + 1, content.length());
            return true;
        }

        return false;
    }

    private boolean deleteOperationIfExists() {
        if (content.isEmpty())
            return false;

        String lastSymbol = content.substring(content.length() - 1);

        if (lastSymbol.matches(operationsRegex)) {
            content = content.substring(0, content.length() - 1);
        }

        return true;
    }

    private String getLastContentNumber() {
        Pattern pattern = Pattern.compile(lastNumberRegex);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            content = matcher.group(1);

            return matcher.group(2);
        } else {
            return "empty";
        }
    }

    private String convertDoubleToString(double value) {
        if (value % 1 == 0) {
            return String.valueOf(Math.round(value));
        }
        return String.valueOf(value);
    }
}
