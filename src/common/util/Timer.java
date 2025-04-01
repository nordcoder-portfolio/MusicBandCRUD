package common.util;

public class Timer {
    long startTime;
    long timeout;

    public Timer(long timeout) {
        this.startTime = System.currentTimeMillis();
        this.timeout = timeout;
    }

    public boolean timeIsOut() {
        return System.currentTimeMillis() - startTime > timeout;
    }
}
