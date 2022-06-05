package com.yopox;

public class Chrono {
    private boolean running = false;
    private boolean forward = true;
    private float count = 0f;

    public Chrono() {
    }

    public Chrono(Long remaining) {
        this.forward = false;
        count = remaining;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void update(float elapsed) {
        if (!running) return;
        if (forward) {
            count += elapsed;
        } else {
            count -= elapsed;
        }
    }

    @Override
    public String toString() {
        final int seconds = (int) Math.floor(count);
        final int minutes = (int) Math.floor(seconds / 60f);
        final int displaySeconds = seconds - minutes * 60;
        final String m = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        final String s = displaySeconds < 10 ? "0" + displaySeconds : String.valueOf(displaySeconds);
        return m + ":" + s;
    }
}
