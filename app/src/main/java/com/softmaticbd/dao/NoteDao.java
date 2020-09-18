package com.softmaticbd.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.softmaticbd.entity.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(NoteEntity entity);

    @Update
    void update(NoteEntity entity);

    @Delete
    void delete(NoteEntity entity);

    @Query("DELETE FROM note_table")
    void deleteAllNote();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<NoteEntity>> getAllNotes();


}
