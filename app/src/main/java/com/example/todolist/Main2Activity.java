package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    ImageView background;
    TextView textView;
    Calendar c;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        background = (ImageView) findViewById(R.id.background);
        String url = "https://upload.wikimedia.org/wikipedia/commons/9/99/Black_square.jpg";
        Picasso.with(this).load(url).fit().into(background);

        textView = (TextView) findViewById(R.id.textView);

        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = month + 1;

        textView.setText(day + "/" + month + "/" + year);

        CalendarView cv = (CalendarView) findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               textView.setText(dayOfMonth + "/" + month + "/" + year);

           }
        });

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatePickerDialog datePickerDialog = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                textView.setText(dayOfMonth + "/" + month + "/" + year);

                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });

    }

    public void todo(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}

