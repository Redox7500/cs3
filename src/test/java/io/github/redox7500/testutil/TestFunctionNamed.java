package io.github.redox7500.testutil;

public record TestFunctionNamed<T>(String name, TestFunction<T> function) {}
