package com.youjuke.swissgearlibrary.rxbus;


import com.youjuke.swissgearlibrary.rxbus.thread.ThreadEnforcer;

/**
 * Instance of {@link Bus}.
 * Simply use {@link #get()} to get the instance of {@link Bus}
 * https://github.com/AndroidKnife/RxBus
 */
public class RxBus {

    /**
     * Instance of {@link Bus}
     */
    private static Bus sBus;

    /**
     * Get the instance of {@link Bus}
     *
     * @return
     */
    public static synchronized Bus get() {
        if (sBus == null) {
            sBus = new Bus(ThreadEnforcer.ANY);
        }
        return sBus;
    }
}