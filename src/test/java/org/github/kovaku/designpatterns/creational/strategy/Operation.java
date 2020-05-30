package org.github.kovaku.designpatterns.creational.strategy;

public interface Operation {

    Double calculate(Double x, Double y);

    String getSymbol();
}
