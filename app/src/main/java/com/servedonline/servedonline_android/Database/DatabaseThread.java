package com.servedonline.servedonline_android.Database;

import android.content.Context;
import android.os.Handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseThread extends Thread {


    /**
     * The amount of time the database thread will sleep before polling for new Database Requests
     */
    private final int THREAD_SLEEP_INTERVAL = 100;

    private Handler handler;
    private DatabaseManager databaseManager;
    private BlockingQueue<DatabaseRequest> queue = new LinkedBlockingQueue<>();

    /**
     * Create a new DatabaseThread
     * @param context       Context to create DatabaseManager with
     * @param handler       Handler in which to process callbacks on. Make sure this is on the right thread that you want to receive callbacks.
     */
    public DatabaseThread(Context context, Handler handler) {
        super();
        this.handler = handler;
        databaseManager = new DatabaseManager(context);
    }

    @Override
    public void run() {
        while (true) {
            final DatabaseRequest request;
            // Poll for new requests, process each one in order
            if ((request = queue.poll()) != null) {
                final Object out = request.getRunnable().run();

                if (request.getOnDatabaseRequestComplete() != null) {
                    // Handle callbacks on the thread which was designated in the constructor
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (request != null) {
                                request.getOnDatabaseRequestComplete().onRequestComplete(out);
                            }
                        }
                    });
                }
            } else {
                try {
                    // Sleep the thread to prevent CPU thrashing
                    Thread.sleep(THREAD_SLEEP_INTERVAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns the current Database Manager instance
     * @return      DatabaseManager
     */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     * Adds a Database Request to the queue
     * @param request           DatabaseRequest object
     */
    public void enqueueDatabaseRequest(DatabaseRequest request) {
        queue.add(request);
    }

    /**
     * Convenience method for enqueueDatabaseRequest(DatabaseRequest), wraps params into a DatabaseRequest object
     * @param request           Database Runnable
     * @param callback          Callback method
     */
    public void enqueueDatabaseRequest(DatabaseRunnable request, OnDatabaseRequestComplete callback) {
        enqueueDatabaseRequest(new DatabaseRequest(request, callback));
    }


    /**
     * Class which contains a queue-able Database request, complete with Runnable and Callback
     */
    public static class DatabaseRequest {
        private DatabaseRunnable runnable;
        private OnDatabaseRequestComplete onDatabaseRequestComplete;

        public DatabaseRequest(DatabaseRunnable runnable, OnDatabaseRequestComplete onDatabaseRequestComplete) {
            this.runnable = runnable;
            this.onDatabaseRequestComplete = onDatabaseRequestComplete;
        }

        public DatabaseRunnable getRunnable() {
            return runnable;
        }

        public OnDatabaseRequestComplete getOnDatabaseRequestComplete() {
            return onDatabaseRequestComplete;
        }

    }

    /**
     * Generic Runnable Interface. Unlike a standard Runnable, this Runnable allows and expects a return value.
     *
     * DatabaseRunnables are an integral part of the Threaded Database paradigm, in which data can be processed
     * on the Database thread, returned on the Database thread and then that output is piped into the callback
     * object on the appropriate thread.
     *
     * @param <T>       Expected return type, default will be Object
     */
    public interface DatabaseRunnable<T> {
        T run();
    }

    /**
     * Callback class for Database Requests performed via the DatabaseThread. Ensure that the Generics of this
     * class align with the output of DatabaseRunnable object passed in concert with this class.
     * @param <T>       Expected Input type, default will be Object
     */
    public static abstract class OnDatabaseRequestComplete<T> {
        public abstract void onRequestComplete(T returnValue);
    }
}
