package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    final List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView ListView = findViewById(R.id.listView);
        final TextAdapter adapter = new TextAdapter();

        /** load saved info**/
        readInfo();

        adapter.setData(list);
        ListView.setAdapter(adapter);
        /** Item Button: Click on the button can decide whether to delete or not**/
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int index, long id) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(index);
                                adapter.setData(list);
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
        });

        /** New Task Button: once clicked can create a new task**/
        final Button newTaskButton = findViewById(R.id.NewTaskButton);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskInput = new EditText(MainActivity.this);
                taskInput.setSingleLine();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add a new Task")
                        .setMessage("What is your new task")
                        .setView(taskInput)
                        .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.add(taskInput.getText().toString());
                                adapter.setData(list);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
    }

    /** everytime the app is paused, information will be saved**/
    @Override
    protected void onPause() {
        super.onPause();
        SaveInfo();
    }

    /** to save info once entered**/
    private void SaveInfo() {
        try{
            File file = new File(this.getFilesDir(), "saved");

            FileOutputStream fOut = new FileOutputStream(file);
            BufferedWriter bufferw = new BufferedWriter(new OutputStreamWriter(fOut));

            for (int i  = 0; i < list.size(); i++) {
                bufferw.write(list.get(i));
                bufferw.newLine();
            }
            bufferw.close();
            fOut.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** read saved info**/
    private void readInfo() {
        File file = new File(this.getFilesDir(), "saved");
        if (file == null) {
            return;
        }
        try{
            FileInputStream fInput = new FileInputStream(file);
            BufferedReader bufferr = new BufferedReader(new InputStreamReader(fInput));
            String line = bufferr.readLine();
            while(line != null) {
                list.add(line);
                line = bufferr.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    class TextAdapter extends BaseAdapter {

        List<String> TextList = new ArrayList<>();

        void setData(List<String> mList) {
            TextList.clear();
            TextList.addAll(mList);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return TextList.size();
        }
        @Override
        public Object getItem(int index) {
            return null;
        }
        @Override
        public long getItemId(int index) {
            return 0;
        }
        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.task, parent, false);
            TextView TextView = rowView.findViewById(R.id.task);
            TextView.setText(TextList.get(index));
            return rowView;
        }
    }
}
