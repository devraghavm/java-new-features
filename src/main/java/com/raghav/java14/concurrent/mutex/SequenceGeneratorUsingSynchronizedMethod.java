package com.raghav.java14.concurrent.mutex;

public class SequenceGeneratorUsingSynchronizedMethod extends SequenceGenerator {
    @Override
    public synchronized int getNextSequence() {
        return super.getNextSequence();
    }
}
