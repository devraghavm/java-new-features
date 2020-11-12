package com.raghav.java14.concurrent.threadfactory;

public class Demo {
    public void execute() {
        RaghavThreadFactory factory = new RaghavThreadFactory("RaghavThreadFactory");
        for (int i = 0; i < 10; i++) {
            Thread t = factory.newThread(new Task());
            t.start();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.execute();
    }
}
