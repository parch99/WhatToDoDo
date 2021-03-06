package com.example.whattododo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapterForHomeNotes extends RecyclerView.Adapter<MyAdapterForHomeNotes.MyViewHolder> {

    Context context2;
    RealmResults<NoteForHome> notesList2;

    public MyAdapterForHomeNotes(Context context2, RealmResults<NoteForHome> notesList2) {
        this.context2 = context2;
        this.notesList2 = notesList2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context2).inflate(R.layout.item_view4home,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterForHomeNotes.MyViewHolder holder, int position) {
        NoteForHome note = notesList2.get(position);
        holder.titleOutput.setText(note.getTitle());

        String formatedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formatedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context2,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            //delete the note
                            Realm realm2 = Realm.getDefaultInstance();
                            realm2.beginTransaction();
                            note.deleteFromRealm();
                            realm2.commitTransaction();
                            Toast.makeText(context2,"Note deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}