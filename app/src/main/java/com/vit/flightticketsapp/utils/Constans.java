package com.vit.flightticketsapp.utils;

import java.io.IOException;
import java.net.SocketException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

public class Constans {

    public static void handlingErrorRxJava() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if ((e instanceof IOException) || (e instanceof SocketException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // that's likely a bug in the application
//                Thread.currentThread().getUncaughtExceptionHandler()
//                        .handleException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
//                Thread.currentThread().getUncaughtExceptionHandler()
//                        .handleException(Thread.currentThread(), e);
                return;
            }
//            Log.w("Undeliverable exception received, not sure what to do", e);
        });
    }
}
