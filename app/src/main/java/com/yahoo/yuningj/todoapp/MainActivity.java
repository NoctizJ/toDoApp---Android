package com.yahoo.yuningj.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private final int REQUEST_CODE = 20;
    ArrayList<String> toDoItems;
    ArrayAdapter<String> toDoAdapter;
    ListView lvItems;
    EditText toDoEdit;
    int editPosition;
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
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onClick(i);
                editPosition = i;
            }
        });
}

    public void onClick(int position) {
        Intent i = new Intent(MainActivity.this, EditActivity.class);
        String note = new String(toDoItems.get(position).toString());
        i.putExtra("note", note);
        startActivityForResult(i, REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("editedNote");
            // Toast the name to display temporarily on screen
            toDoItems.set(editPosition, name);
            toDoAdapter.notifyDataSetChanged();
            writeItems();
        }
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
