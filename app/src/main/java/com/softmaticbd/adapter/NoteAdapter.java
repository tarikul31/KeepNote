package com.softmaticbd.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softmaticbd.R;
import com.softmaticbd.activity.NoteActivity;
import com.softmaticbd.entity.NoteEntity;
import com.softmaticbd.service.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private List<NoteEntity> noteList = new ArrayList<>();
    private ItemClickListener listener;

//    public NoteAdapter() {
//    }
//
//    public NoteAdapter(Context context, List<NoteEntity> noteList) {
//        this.context = context;
//        this.noteList = noteList;
//    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity currentNote = noteList.get(position);
        holder.tvNoteTitle.setText(currentNote.getTitle());
        holder.tvNoteDesc.setText(currentNote.getDescription());
        holder.tvNotePriority.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public NoteEntity getNoteAt(int position) {
        return noteList.get(position);
    }
    public void setNotes(List<NoteEntity> notes) {
        this.noteList = notes;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNotePriority, tvNoteTitle, tvNoteDesc;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotePriority = itemView.findViewById(R.id.tvNotePriority);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteDesc = itemView.findViewById(R.id.tvNoteDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Log.d("LOG","Position : " + position);
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(noteList.get(position));
                    }
                }
            });
        }
    }
    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
