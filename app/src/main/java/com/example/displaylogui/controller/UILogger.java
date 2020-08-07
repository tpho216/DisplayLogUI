package com.example.displaylogui.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.displaylogui.model.LogLine;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class UILogger {

    private static final String TAG = "UILogger";
    private static boolean ON = false;

    /* be careful - static context leakage*/
    private static LogLineAdapter logLineAdapter;
    private static UILogger mInstance;

    private Context context;
    private LogCatTask logCatTask = new LogCatTask();


    /*Call this when you start your application, possibly in the onCreate of the Application class*/
    public static void init(Context _context) {
        mInstance = new UILogger(_context);
        if (mInstance.context != null) {
            Log.i(TAG, "init: UILogger is now ON");
            ON = true;
        } else {
            Log.w(TAG, "init: you didn't activate the logger due to null context");
        }
    }

    public UILogger(Context _context) {
        mInstance = this;
        mInstance.context = _context;
    }
    
    private static void showLogCatLogsInView(LogLineAdapter adapter) {
        if (adapter != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            }
            logLineAdapter = adapter;
            mInstance.logCatTask.execute();
        }
    }

    public static void startLogcatTask(LogLineAdapter adapter) {
        logLineAdapter = adapter;
        mInstance.logCatTask = new UILogger.LogCatTask();
        showLogCatLogsInView(logLineAdapter);
    }

    public static void stopLogcatTask() {
        mInstance.logCatTask.cancel(true);
    }

    public static class LogCatTask extends AsyncTask<Void, String, Void> {
        public static final String TAG = "LogCatTask";

        AtomicBoolean run = new AtomicBoolean(true);

        @Override
        protected void onProgressUpdate(String... values) {
            try {
                if (logLineAdapter != null) {

                    logLineAdapter.add(new LogLine(values[0]));

                }
            } catch (Exception e) {
                Log.e(TAG, "onProgressUpdate: ignore: ", e);
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Runtime.getRuntime().exec("logcat -c");
                Process process = Runtime.getRuntime().exec("logcat DisplayLogUIApplication:I *:S");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder log = new StringBuilder();
                String line;
                while (run.get()) {
                    line = bufferedReader.readLine();
                    if (line != null) {
                        log.append(line);
                        publishProgress(log.toString());
                    }
                    Thread.sleep(100);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}