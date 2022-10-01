package com.Infotech;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public ArrayList<NotesModel> arrayList;
    public Context context;

    public NoteAdapter(ArrayList<NotesModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ;
        if(arrayList.get(position).status.equals("1")){
            holder.tvnotes.setText(arrayList.get(position).subject);
            holder.tvnotes.setPaintFlags(holder.tvnotes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.tvnotes.setText(arrayList.get(position).subject);
        }

//        String s = "<b>Bolded text</b>, <i>italic text</i>, even <u>underlined</u>!";
//        TextView tv = (TextView)findViewById(R.id.THE_TEXTVIEW_ID);
//        tv.setText(Html.fromHtml(s));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase  database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = database.getReference();
                if(holder.checkBox.isChecked()){
                    mDatabaseRef.child("NOTES").child(arrayList.get(position).subject).child("status").setValue("1");
                    notifyDataSetChanged();
                }else {
                    mDatabaseRef.child("NOTES").child(arrayList.get(position).subject).child("status").setValue("0");
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvnotes;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemview){
            super(itemview);
            tvnotes = itemview.findViewById(R.id.tvnotes);
            checkBox = itemview.findViewById(R.id.checkBox);
        }
    }
}
