package com.softmaticbd.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.softmaticbd.R;
import com.softmaticbd.adapter.NoteAdapter;
import com.softmaticbd.entity.NoteEntity;
import com.softmaticbd.service.ItemClickListener;
import com.softmaticbd.viewmodel.NoteViewModel;

import java.util.List;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;
    private RecyclerView noteRecycler;
    private NoteAdapter noteAdapter;
    private FloatingActionButton btnAddNote;
    private ItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
//        getSupportActionBar().hide();

        noteRecycler = findViewById(R.id.rvNoteId);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnAddNote.setOnClickListener(this);
        setTitle(Html.fromHtml("<font color='#05F842'>Keep Note</font>"));
        noteRecycler.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter();
        noteRecycler.setAdapter(noteAdapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                noteAdapter.setNotes(noteEntities);
            }
        });

        onDeleteNote();
        noteAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(NoteEntity note) {
                //Toast.makeText(NoteActivity.this,"Note CLick",Toast.LENGTH_SHORT).show();
                setItemClickListener(note);
            }
        });
    }


    private void onDeleteNote() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(NoteActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecycler);
    }

    private void setItemClickListener(NoteEntity note) {
        Intent intent = new Intent(NoteActivity.this, AddEditNoteActivity.class);
        intent.putExtra(AddEditNoteActivity.EXTRA_ID,note.getId());
        intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
        intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
        intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
        startActivityForResult(intent,EDIT_NOTE_REQUEST);
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddNote) {
            Intent intent = new Intent(getApplicationContext(), AddEditNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            NoteEntity note = new NoteEntity(title, desc, priority);
            noteViewModel.insert(note);
            Toast.makeText(NoteActivity.this, "New Note Added", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);
            if (id == -1){
                Toast.makeText(NoteActivity.this, "Note Can`t Update.. !! ", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            NoteEntity note = new NoteEntity(title, desc, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(NoteActivity.this,"Note Updated",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(NoteActivity.this, "Note Not Save.. !! ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuDelAllNote:
                AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("Are You Really Delete All Note.. ??");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteViewModel.deleteAllNote();
                        Toast.makeText(NoteActivity.this, "All Note Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NoteActivity.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}