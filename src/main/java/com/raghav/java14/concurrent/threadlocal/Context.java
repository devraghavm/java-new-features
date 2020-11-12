package com.raghav.java14.concurrent.threadlocal;

public class Context {
    private final String userName;

    Context(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Context{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
