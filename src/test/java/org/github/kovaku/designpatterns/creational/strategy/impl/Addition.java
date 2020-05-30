package org.github.kovaku.designpatterns.creational.strategy.impl;

import org.github.kovaku.designpatterns.creational.strategy.Operation;

public class Addition implements Operation {

    @Override
    public Double calculate(Double x, Double y) {
        return x + y;
    }

    @Override
    public String getSymbol() {
        return "+";
    }
}
