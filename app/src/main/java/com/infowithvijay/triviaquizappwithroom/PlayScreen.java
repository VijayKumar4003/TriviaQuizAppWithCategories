package com.infowithvijay.triviaquizappwithroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.infowithvijay.triviaquizappwithroom.MusicController.StopSound;

public class PlayScreen extends AppCompatActivity {

    private static Context context;

    Button btPlayQuiz,btSettings;

    long backPressed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        btPlayQuiz = findViewById(R.id.btPlayQuiz);
        btSettings = findViewById(R.id.btsettings);


        context = getApplicationContext();
        MusicController.currentActivity = this;


        if (SettingPreference.getMusicEnableDisable(context)){
            try {
                MusicController.playSound();
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }



        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent settingIntent = new Intent(PlayScreen.this,Settings.class);
                startActivity(settingIntent);
                finish();

            }
        });



        btPlayQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playquizIntent = new Intent(PlayScreen.this,CategoryActivity.class);
                startActivity(playquizIntent);
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @Override
    public void onBackPressed() {

        StopSound();

        if (backPressed + 2000 > System.currentTimeMillis()){

           new AlertDialog.Builder(this)
                   .setTitle("Do you want to Exit")
                   .setNegativeButton("No",null)
                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           setResult(RESULT_OK,new Intent().putExtra("Exit",true));
                           finish();
                       }
                   }).create().show();


        }else {

            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }
        backPressed = System.currentTimeMillis();
    }
}
