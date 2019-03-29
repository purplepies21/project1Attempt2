package com.example.project1attempt2;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.AudioSource.VOICE_RECOGNITION;

public class ButtonFragment extends Fragment {

    View view;
    ArrayList<String> voiceArr;


    private  FloatingActionButton addButton;

    private  static ImageButton buttonSR;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.my_button_view,container,false);
        voiceArr = new ArrayList<String>();
        buttonSR = view.findViewById(R.id.speechButton);
        addButton = view.findViewById(R.id.addFAB);
        buttonSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), " Activating Speech Recognition", Toast.LENGTH_LONG).show();

                openSR();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                launchActivity();
            }
        });

        return view;}

    public void launchActivity(String voice1) {

        Intent intent = new Intent(getContext(), new_task_Activity.class);

        intent.putExtra("voiceInput", voice1);
        startActivity(intent);

    }
    public void launchActivity() {

        Intent intent = new Intent(getContext(), new_task_Activity.class);
        intent.putExtra("voiceInput", "");
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == VOICE_RECOGNITION){
            Log.i("SpeechDemo", "## INFO 02: RequestCode VOICE_RECOGNITION = " + requestCode);
            if (resultCode == RESULT_OK) {
                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                for (int i = 0; i < results.size(); i++) {
                    final String result = results.get(i);
                    Log.i("SpeechDemo", "## INFO 05: Result: " + result );
                    voiceArr.add(result);
                    Toast.makeText(view.getContext(), ""+ voiceArr.get(0), Toast.LENGTH_SHORT).show();

                }
                launchActivity(voiceArr.get(0));

            }

        }    }

    public void openSR(){
        voiceArr.clear();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Just speak normally into your phone");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        try {
            startActivityForResult(intent, VOICE_RECOGNITION);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }








}
