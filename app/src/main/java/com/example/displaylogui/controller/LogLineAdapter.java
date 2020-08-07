package com.example.displaylogui.controller;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.displaylogui.R;
import com.example.displaylogui.model.LogLine;

import java.util.List;

public class LogLineAdapter extends ArrayAdapter<LogLine> {

    private List<LogLine> logLineList;
    private Activity activity;

    public LogLineAdapter(Activity context, List<LogLine> list) {

        super(context, R.layout.log_line_item_row, list);
        this.logLineList = list;
        this.activity = context;
    }

    @Override
    public void add(LogLine line) {
        super.add(line);
        logLineList.add(line);
        notifyDataSetChanged();
        //Log.d("DisplayLogUIActivity", "Log Line Adapter");
    }


    @Override
    public LogLine getItem(int position) {
        return logLineList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.log_line_item_row, parent, false);
        }

        LogLine logLine = logLineList.get(position);
        TextView logLineTxt = convertView.findViewById(R.id.log_line_txt);


        logLineTxt.setText(logLine.getLine());
        return convertView;
    }



}
