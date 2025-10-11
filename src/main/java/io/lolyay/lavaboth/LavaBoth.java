package io.lolyay.lavaboth;

import io.lolyay.eventbus.EventBus;


public abstract class LavaBoth {
    public static final EventBus eventBus = new EventBus();
    public static boolean debug = false;
    public static String defaultRemoteCipheringServer = "https://cipher.kikkia.dev/";
}
