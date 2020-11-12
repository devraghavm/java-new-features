package com.raghav.java14.concurrent.synchronize;

public class RaghavSynchronizedBlocks {
    private int count = 0;
    private static int staticCount = 0;

    void performSynchronizedTask() {
        synchronized (this) {
            setCount(getCount() + 1);
        }
    }

    static void performStaticSyncTask() {
        synchronized (RaghavSynchronizedBlocks.class) {
            setStaticCount(getStaticCount() + 1);
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    static int getStaticCount() {
        return staticCount;
    }

    private static void setStaticCount(int staticCount) {
        RaghavSynchronizedBlocks.staticCount = staticCount;
    }
}
