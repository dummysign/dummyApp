package com.Infotech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabbtn;
    public static RecyclerView recyclerview;
    String Subject,Description,value;
    ArrayList<NotesModel> chatList= new ArrayList<NotesModel>();
    public static NoteAdapter noteAdapter;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabbtn = findViewById(R.id.fabbtn);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getStringExtra("UUID");
            Log.e("UUID","UUID"+value);
        }
        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDailog(MainActivity.this,value);
            }
        });
        readMessages("subject");
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                NotesModel deletedCourse = chatList.get(viewHolder.getAdapterPosition());

                int position = viewHolder.getAdapterPosition();

                chatList.remove(viewHolder.getAdapterPosition());

                noteAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                Snackbar.make(recyclerview, deletedCourse.subject, Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chatList.add(position, deletedCourse);

                        noteAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerview);

    }

    private void UpdateDailog(MainActivity mainActivity,String uuid) {
        final Dialog dialog = new Dialog(mainActivity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.add_layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.windowAnimations = R.style.DialogAnimation;
        TextView TvCancel = dialog.findViewById(R.id.TvCancel);
        TextView TvSubmit = dialog.findViewById(R.id.TvSubmit);
        EditText etSubject = dialog.findViewById(R.id.etSubject);
        EditText etDescription = dialog.findViewById(R.id.etDescription);
        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subject = etSubject.getText().toString().trim();
                Description = etDescription.getText().toString().trim();
                if(!TextUtils.isEmpty(Subject)){
                    if(!TextUtils.isEmpty(Description)){
                        sendmessage(mainActivity,Subject,Description,uuid);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getApplicationContext(), "Enter The Subject", Toast.LENGTH_SHORT).show();
                    }
                    
                }else {
                    Toast.makeText(getApplicationContext(), "Enter The Description", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.getWindow().setLayout((int) (Utils.getScreenWidth(mainActivity) * 1), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void sendmessage(MainActivity mainActivity, String subject, String description, String value) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Utils.getUserLang(mainActivity);
        String value1 = Settings.UUID;
        Log.e("LLLL","LLLLL "+value1);
        HashMap<String, Object> hashMap = new HashMap<>();
        if(value == null){
            hashMap.put("UUID",value1);
        }
        hashMap.put("UUID",value);
        hashMap.put("subject",subject);
        hashMap.put("description",description);
        hashMap.put("status","0");
        hashMap.put("timestamp",timestamp);
        databaseReference.child("NOTES").child(subject).setValue(hashMap);
        DatabaseReference mReferenceCpf = FirebaseDatabase.getInstance().getReference("NOTES");
        mReferenceCpf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                readMessages(subject);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void readMessages(String subject) {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("NOTES");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                   chatList.add(new NotesModel(dataSnapshot1.child("subject").getValue().toString(),dataSnapshot1.child("description").getValue().toString(),dataSnapshot1.child("timestamp").getValue().toString(),dataSnapshot1.child("status").getValue().toString()));
                }
                Collections.sort(chatList, new Comparator<NotesModel>() {
                    @Override
                    public int compare(NotesModel item1, NotesModel item2) {
                        return item1.getDate().compareToIgnoreCase(item2.getDate());

                    }
                });
                Collections.reverse(chatList);
                noteAdapter = new NoteAdapter(chatList,MainActivity.this);
                recyclerview.setAdapter(noteAdapter);
                noteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}