package com.example.project1attempt2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class new_task_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    String taskText = "";
    String timeText = "";
    String fileID = "";
    String dateText="";
    EditText taskEditText;
    private EditText calendarEditText;
    String voice;
    int repeat = 0;
    Spinner spin;
    private EditText timeEditText;

    FirebaseDatabase database;
    DatabaseReference myRef;
    //Time and date passed to list
    private int aYear, aMonth, aDay, aHour, aMinute;

    //Current Time
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        spin = findViewById(R.id.repeatSpinner);
//        spin.setOnItemSelectedListener(ne);
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){

            try{
                actionBar.setDisplayHomeAsUpEnabled(true);

            }catch ( Exception e){
                Log.d("toolbar Crash", "Crashed while setting up toolbar");

            }

        }
        calendarEditText = (EditText) findViewById(R.id.dateEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        ImageView calendarButton = (ImageView) findViewById(R.id.dateImageView);
        ImageView clearCalendarButton = (ImageView) findViewById(R.id.cleardateImageView);
        ImageView clearTimeButton = (ImageView) findViewById(R.id.clearTimeImageView);
        ImageView timeButton = (ImageView) findViewById(R.id.timeImageView);
        taskEditText = (EditText)findViewById(R.id.taskEditText);
        Intent intentRecovery = getIntent();

        voice = intentRecovery.getExtras().getString("voiceInput");
        voiceSplitter(voice);
   //     taskEditText.setText(taskText);
       // voiceSplitter(voice);
       // taskEditText.setText(voice);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });
        clearCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarEditText.setText("");
            }
        });
        clearTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeEditText.setText("");
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                aHour = hourOfDay;
                                aMinute=minute;
                                if(hourOfDay >=12){
                                timeEditText.setText(hourOfDay-12 +   ":" + minute+" PM");
                                }else{timeEditText.setText(hourOfDay +   ":" + minute+" AM");}
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.DATE, dayOfMonth);
        calSet.set(Calendar.MONTH, month);
        calSet.set(Calendar.YEAR, year);
        aDay = dayOfMonth;
        aMonth = month;
        aYear = year;
        long time_val = calSet.getTimeInMillis();

        String formatted_date = (new SimpleDateFormat("EE, MMM dd   yyyy").format(time_val));



        calendarEditText.setText(formatted_date);
    }
void voiceSplitter(String voice){
        taskText = "";
        String[] words = voice.split("\\s+");
        int wordCount = 0;


    for(int i=0; i< words.length;i++){

        if(words[i].equals("Monday") ||words[i].equals("Tuesday") ||words[i].equals("Wednesday") ||words[i].equals("Thursday") ||words[i].equals("Friday") ||words[i].equals("Saturday") ||words[i].equals("Sunday") ){
            break;

        }

        if(words[i].equals("January") ||words[i].equals("February")||words[i].equals("March")||words[i].equals("April")||words[i].equals("May")||words[i].equals("June")||words[i].equals("July") ||words[i].equals("August")||words[i].equals("September")||words[i].equals("October")||words[i].equals("November")||words[i].equals("December") )
        {
            break;

        }
        taskText = (taskText+ " "+words[i]);
        wordCount++;

    }

    for(int i = wordCount; i< words.length;i++){

        if(words[i].equals("at"))
        {
            break;

        }
        dateText = (dateText+ " "+words[i]);
        wordCount++;
    }

    for(int i = wordCount; i< words.length;i++){

        if(words[i].equals("at")){
            i++;
        }
        if(words[i].equals("am"))
        {

            break;

        }else if(words[i].equals("pm")){

            break;

        }
        timeText = timeText + words[i];


    }




    taskEditText.setText(taskText);
    calendarEditText.setText(dateText);
    timeEditText.setText(timeText);
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return true;
            }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {



            taskText = taskEditText.getText().toString();
            String key = database.getReference().push().getKey();

            if(fileID.equals("") ) {
                fileID = key;
                myRef  = FirebaseDatabase.getInstance().getReference().child("/users").child("files");
            }else{
                myRef  = FirebaseDatabase.getInstance().getReference().child("/users").child("files");


            }
            Intent intent= new Intent(getApplicationContext(), Main_List_Activity.class);
            intent.putExtra("task", taskText);
            intent.putExtra("fileID", fileID);
            intent.putExtra("month", aMonth);
            intent.putExtra("year", aYear);
            intent.putExtra("day", aDay);
            intent.putExtra("hour", aHour);
            intent.putExtra("minute", aMinute);
            intent.putExtra("repeat", repeat);
            intent.putExtra("selected", false);
            RowData row = new RowData(taskText, fileID, aMonth,aYear, aDay, aHour, aMinute,repeat,false);

            myRef.push().setValue(row);

            startActivity(intent);
            finish();

       //   MainActivity.updateItem(taskText,fileID,aMonth, aYear, aDay, aHour, aMinute, repeat, false);

            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
