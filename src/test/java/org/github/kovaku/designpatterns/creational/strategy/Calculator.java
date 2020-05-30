package org.github.kovaku.designpatterns.creational.strategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Calculator {

    private final Map<String, Operation> operations;

    public Calculator(List<Operation> operations) {
        this.operations = operations.stream()
            .collect(Collectors.toMap(Operation::getSymbol, Function.identity()));
    }

    public Double doCalculation(Double x, Double y, String operationSymbol) {
        Optional<Operation> operation = Optional.ofNullable(operations.get(operationSymbol));
        return operation
            .orElseThrow(UnsupportedOperationException::new)
            .calculate(x, y);
    }

}
