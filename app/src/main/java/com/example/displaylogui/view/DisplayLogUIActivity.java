package com.example.displaylogui.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.displaylogui.controller.UILogger;
import com.example.displaylogui.controller.LogLineAdapter;
import com.example.displaylogui.R;
import com.example.displaylogui.model.LogLine;


import java.util.ArrayList;
import java.util.List;


public class DisplayLogUIActivity extends ListActivity {

    private LogLineAdapter logLineAdapter;
    private List<LogLine> logLineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // add some customization to the log messages

        logLineList = new ArrayList<LogLine>();
        logLineAdapter = new LogLineAdapter(this, logLineList);

        setListAdapter(logLineAdapter);
        UILogger.startLogcatTask(logLineAdapter);
        Log.i("DisplayLogUIApplication", "cmon");
    }


    public void onDestroy() {
        super.onDestroy();

    }
}