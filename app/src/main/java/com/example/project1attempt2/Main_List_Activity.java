package com.example.project1attempt2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main_List_Activity extends AppCompatActivity {

    private String task;
    private  int month;
    private  int year;
    private  int day;
    private  int hour;
    private  int minute;
    private int repeat;

    private String fileID ="";
    private boolean selected;



    RecyclerView recyclerView;
    static RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;


    FirebaseDatabase database;
    DatabaseReference myRef;
    List<RowData> rowDataList;
    RowData row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__list_);

        database = FirebaseDatabase.getInstance();

       // addToList();
        String key = database.getReference().push().getKey();

            myRef  = FirebaseDatabase.getInstance().getReference().child("/users").child("files").child(key);

            myRef  = FirebaseDatabase.getInstance().getReference().child("/users").child("files").child(fileID);





        recyclerView = (RecyclerView)findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        rowDataList = new ArrayList<>();



//        rowDataList.add(new RowData(task,fileID,month,year,day,hour,minute,repeat,selected));
        mAdapter = new CustomRecyclerAdapter(this, rowDataList);
        recyclerView.setAdapter(mAdapter);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              for(DataSnapshot data : dataSnapshot.getChildren()){


                  rowDataList.add(data.getValue(RowData.class));

              }


                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });







        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               launchActivity();

            }
        });






    }
private void addToList(){
    Intent intentRecovery =  getIntent();

    task = intentRecovery.getExtras().getString("task");
    fileID = intentRecovery.getExtras().getString("FileID");
    month = intentRecovery.getExtras().getInt("month");
    year = intentRecovery.getExtras().getInt("year");
    day = intentRecovery.getExtras().getInt("day");
    hour = intentRecovery.getExtras().getInt("hour");
    repeat = intentRecovery.getExtras().getInt("repeat");
    selected = intentRecovery.getExtras().getBoolean("selected");

    row = new RowData(task,fileID,month,year,day,hour,minute,repeat,selected);
   // rowDataList.add(row);
}

    public void launchActivity() {

        Intent intent = new Intent(this, new_task_Activity.class);
        intent.putExtra("voiceInput", "");
        startActivity(intent);

    }



    }




