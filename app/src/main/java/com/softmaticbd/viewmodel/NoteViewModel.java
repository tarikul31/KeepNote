package com.softmaticbd.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.softmaticbd.entity.NoteEntity;
import com.softmaticbd.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(NoteEntity note){
        repository.insert(note);
    }

    public void update(NoteEntity note){
        repository.update(note);
    }

    public void delete(NoteEntity note){
        repository.delete(note);
    }

    public void deleteAllNote(){
        repository.deleteAllNote();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }
}
