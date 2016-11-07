package com.yahoo.yuningj.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    String note;
    EditText editText;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText = (EditText)findViewById(R.id.editText);
        note = getIntent().getStringExtra("note");
        editText.setText(note);
    }

    public void onSaveItem(View view) {
        String editedNote = editText.getText().toString();
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("editedNote", editedNote);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }
}
