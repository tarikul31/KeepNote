package com.softmaticbd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.softmaticbd.R;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.softmaticbd.activity.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.softmaticbd.activity.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.softmaticbd.activity.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.softmaticbd.activity.EXTRA_PRIORITY";

    public static final String REQUIRED = "Input Field Required";

    private EditText etNoteTitle, etNoteDesc;
    private NumberPicker npPriority;
    private ImageView ivCloseNote, ivSaveNote;
    private String title, desc;
    private int priority;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteDesc = findViewById(R.id.etNoteDesc);
        npPriority = findViewById(R.id.npPriority);

        npPriority.setMinValue(1);
        npPriority.setMaxValue(10);
        getExtraIntentData();

    }

    private void getExtraIntentData() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(Html.fromHtml("<font color='#05F842'>Update Note</font>"));
            etNoteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etNoteDesc.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            npPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle(Html.fromHtml("<font color='#05F842'>Add Note</font>"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSaveMenu:
                SaveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void SaveNote() {
        title = etNoteTitle.getText().toString().trim();
        desc = etNoteDesc.getText().toString().trim();
        priority = npPriority.getValue();
        if (title.isEmpty()) {
            etNoteTitle.setError(REQUIRED);
            etNoteTitle.requestFocus();
            return;
        }
        if (desc.isEmpty()) {
            etNoteDesc.setError(REQUIRED);
            etNoteDesc.requestFocus();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, desc);
        data.putExtra(EXTRA_PRIORITY, priority);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

}