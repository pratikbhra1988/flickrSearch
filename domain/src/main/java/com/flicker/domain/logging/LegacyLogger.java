package com.flicker.domain.logging;

/**
 * Created by Pratik Behera on 2019-09-03.
 */

public class LegacyLogger {
    public interface InjectableInstance {
        void debug(String message);

        void error(String message, Throwable exception);
    }

    public static InjectableInstance injected = new InjectableInstance() {
        @Override
        public void debug(String message) {
            /* do nothing by default.
             * we expect this implementation to be overridden from outside the library. */
        }

        @Override
        public void error(String message, Throwable exception) {
            /* do nothing by default.
             * we expect this implementation to be overridden from outside the library. */
        }
    };

    public static void debug(String message) {
        injected.debug(message);
    }

    public static void error(String message) {
        injected.error(message, null);
    }

    public static void error(String message, Throwable error) {
        injected.error(message, error);
    }
}