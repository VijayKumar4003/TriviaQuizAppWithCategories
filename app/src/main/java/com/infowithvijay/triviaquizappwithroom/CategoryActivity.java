package com.infowithvijay.triviaquizappwithroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{


    Button btJava,btKotlin,btDart,btFlutter,btJavaScript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btJava = findViewById(R.id.bt_Java);
        btKotlin = findViewById(R.id.bt_Kotlin);
        btDart = findViewById(R.id.bt_Dart);
        btFlutter = findViewById(R.id.bt_Flutter);
        btJavaScript = findViewById(R.id.bt_JavaScript);

        btJava.setOnClickListener(this);
        btKotlin.setOnClickListener(this);
        btDart.setOnClickListener(this);
        btFlutter.setOnClickListener(this);
        btJavaScript.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.bt_Java:

                Intent javaIntent = new Intent(CategoryActivity.this,QuizActivity.class);
                javaIntent.putExtra("Category","Java");
                startActivity(javaIntent);
                finish();
                break;

            case R.id.bt_Dart:

                Intent dartIntent = new Intent(CategoryActivity.this,QuizActivity.class);
                dartIntent.putExtra("Category","Dart");
                startActivity(dartIntent);
                finish();
                break;

            case R.id.bt_Kotlin:

                Intent kotlinIntent = new Intent(CategoryActivity.this,QuizActivity.class);
                kotlinIntent.putExtra("Category","Kotlin");
                startActivity(kotlinIntent);
                finish();
                break;

            case R.id.bt_Flutter:

                Intent flutterIntent = new Intent(CategoryActivity.this,QuizActivity.class);
                flutterIntent.putExtra("Category","Flutter");
                startActivity(flutterIntent);
                finish();
                break;

            case R.id.bt_JavaScript:

                Intent javaScriptIntent = new Intent(CategoryActivity.this,QuizActivity.class);
                javaScriptIntent.putExtra("Category","JavaScript");
                startActivity(javaScriptIntent);
                finish();
                break;



        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent intent = new Intent(CategoryActivity.this,PlayScreen.class);
            startActivity(intent);
            finish();

    }
}