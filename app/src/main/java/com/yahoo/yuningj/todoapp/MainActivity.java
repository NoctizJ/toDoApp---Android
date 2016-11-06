package com.yahoo.yuningj.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> toDoItems;
    ArrayAdapter<String> toDoAdapter;
    ListView lvItems;
    EditText toDoEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.toDoListView);
        lvItems.setAdapter(toDoAdapter);
        toDoEdit = (EditText)findViewById(R.id.toDoEdit);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoItems.remove(position);
                toDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void populateArrayItems() {
        readItems();
        //toDoItems = new ArrayList<String>();
        toDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
    }

    public void onAddItem(View view) {
        toDoAdapter.add(toDoEdit.getText().toString());
        toDoEdit.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "toDo.txt");
        try {
            toDoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "toDo.txt");
        try {
            FileUtils.writeLines(file, toDoItems);
        } catch (IOException e) {

        }
    }
}
