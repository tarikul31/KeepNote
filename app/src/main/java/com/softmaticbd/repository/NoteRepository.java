package com.softmaticbd.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.softmaticbd.dao.NoteDao;
import com.softmaticbd.database.NoteDatabase;
import com.softmaticbd.entity.NoteEntity;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(NoteEntity note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(NoteEntity note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(NoteEntity note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNote(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NoteEntity, Void,Void>{

        private NoteDao noteDao;
        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.insert(noteEntities[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<NoteEntity, Void,Void>{

        private NoteDao noteDao;
        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.update(noteEntities[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteEntity, Void,Void>{

        private NoteDao noteDao;
        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.delete(noteEntities[0]);
            return null;
        }
    }
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void,Void>{

        private NoteDao noteDao;
        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNote();
            return null;
        }
    }

}
