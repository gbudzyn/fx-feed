package org.gb.fx.calculator;

public interface Calculator<INPUT, OUTPUT> {
    public OUTPUT calculate(INPUT input);
}
