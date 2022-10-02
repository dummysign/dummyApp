package com.Infotech;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public ArrayList<NotesModel> arrayList;
    public Context context;
    private int TAB = -1;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.checkBox.setChecked(false);
        if(arrayList.get(position).status.equals("1")){
            holder.tvnotes.setText(arrayList.get(position).subject);
            holder.tvnotes.setPaintFlags(holder.tvnotes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.tvnotes.setText(arrayList.get(position).subject);
        }
        holder.tvdescription.setText(arrayList.get(position).description);
        String s = "<b>"+arrayList.get(position).subject+"</b>"+"<i>"+arrayList.get(position).subject+"</i>"+"<u>"+arrayList.get(position).subject+"</u>!";
        holder.tvdescription.setText(Html.fromHtml(s,Html.FROM_HTML_MODE_COMPACT));


//        if(TAB==position){
//            holder.checkBox.setChecked(true);
//        }else {
//            holder.checkBox.setChecked(false);
//        }

//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean isChecked =  holder.checkBox.isChecked();
                FirebaseDatabase  database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = database.getReference();
                if(isChecked){
                    mDatabaseRef.child("NOTES").child(arrayList.get(position).subject).child("status").setValue("1");
                    //checkBox clicked and checked
                }else{
                    //checkBox clicked and unchecked
                    mDatabaseRef.child("NOTES").child(arrayList.get(position).subject).child("status").setValue("0");
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.ShowDetails(context,arrayList.get(position).getImage(),arrayList.get(position).subject,arrayList.get(position).description,arrayList.get(position).date);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvnotes,tvdescription;
        CheckBox checkBox;
        ImageView upload;
        public ViewHolder(@NonNull View itemview){
            super(itemview);
            tvdescription = itemview.findViewById(R.id.tvdescription);
            tvnotes = itemview.findViewById(R.id.tvnotes);
            upload = itemview.findViewById(R.id.upload);
            checkBox = itemview.findViewById(R.id.checkBox);
        }
    }

}
