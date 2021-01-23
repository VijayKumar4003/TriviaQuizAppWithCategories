package com.infowithvijay.triviaquizappwithroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    Button buttonA,buttonB,buttonC,buttonD;
    TextView questionText,txtTotalQuestion,timeText,txtCoin,txtCorrect,txtWrong;

    private QuestionsViewModel questionsViewModelob;

    List<Questions> list;

    Questions currentQuestion;

    int qid = 1;

    int sizeOfQuiz = 3;

    Handler handler = new Handler();
    Handler handler2 = new Handler();

    AnimationDrawable anim;


    private static final long COUNTDOWN_IN_MIILS = 32000;

    private CountDownTimer countDownTimer;

    private long timeLeftInMillis;

    long savedTime = 0;

    TimerDialog timerDialog;

    int correct = 0;
    int wrong = 0;
    int coins = 0;

    Animation shakeAnimation;

    Animation correctAnsAnimation;

    int MUSIC_FLAG = 0;
    PlaySound playSound;

    String categoryValue="";

    long backPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.txtTriviaQuestion);
        txtTotalQuestion = findViewById(R.id.txtTotalQuestions);
        txtCoin = findViewById(R.id.txtCoin);
        timeText = findViewById(R.id.txtTimer);
        txtCorrect = findViewById(R.id.txtCorrect);
        txtWrong = findViewById(R.id.txtWrong);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);


        Intent categoryIntent = getIntent();
        categoryValue = categoryIntent.getStringExtra("Category");


        timerDialog = new TimerDialog(this);

        txtTotalQuestion.setText(qid + "/" + sizeOfQuiz);

        txtCorrect.setText(String.valueOf(correct));
        txtWrong.setText(String.valueOf(wrong));
        txtCoin.setText(String.valueOf(correct));

        playSound = new PlaySound(this);

        shakeAnimation = AnimationUtils.loadAnimation(this,R.anim.incorrect_animation);
        shakeAnimation.setRepeatCount(3);

        correctAnsAnimation = AnimationUtils.loadAnimation(this,R.anim.right_ans_animation);
        correctAnsAnimation.setRepeatCount(3);


        questionsViewModelob = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        questionsViewModelob.getAllQuestionsByCategory(categoryValue).observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {

                fetchQuestions(questions);

            }
        });

    }

    private void fetchQuestions(List<Questions> questions){

        list = questions;

        Collections.shuffle(list);

        currentQuestion = list.get(qid);

        updateQueAnsOptions();


    }

    private void updateQueAnsOptions() {

        buttonA.setBackgroundResource(R.drawable.default_button_bg);
        buttonB.setBackgroundResource(R.drawable.default_button_bg);
        buttonC.setBackgroundResource(R.drawable.default_button_bg);
        buttonD.setBackgroundResource(R.drawable.default_button_bg);

        questionText.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOptA());
        buttonB.setText(currentQuestion.getOptB());
        buttonC.setText(currentQuestion.getOptC());
        buttonD.setText(currentQuestion.getOptD());

        timeLeftInMillis = COUNTDOWN_IN_MIILS;

        startCountDownTimer();


    }

    private void SetNewQuestion(){

        qid++;

        txtTotalQuestion.setText(qid + "/" + sizeOfQuiz);

        currentQuestion = list.get(qid);

        enableButtons();

        updateQueAnsOptions();

    }


    private void correctAns(int correct){
        txtCorrect.setText(String.valueOf(correct));
    }


    private void wrongAns(int wrong){
        txtWrong.setText(String.valueOf(wrong));
    }


    public void buttonA(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonA.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonA.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())){

                    buttonA.setBackgroundResource(R.drawable.correct_button_bg);
                    buttonA.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);

                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);

                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {

                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonA.setBackgroundResource(R.drawable.wrong_button_bg);
                    buttonA.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);
                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){
                                buttonB.setBackgroundResource(R.drawable.correct_button_bg);
                            }else if(currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
                                buttonC.setBackgroundResource(R.drawable.correct_button_bg);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.correct_button_bg);
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {

                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);



    }

    public void buttonB(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonB.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonB.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){

                    buttonB.setBackgroundResource(R.drawable.correct_button_bg);
                    buttonB.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);
                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {
                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonB.setBackgroundResource(R.drawable.wrong_button_bg);
                    buttonB.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);
                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())){
                                buttonA.setBackgroundResource(R.drawable.correct_button_bg);
                            }else if(currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
                                buttonC.setBackgroundResource(R.drawable.correct_button_bg);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.correct_button_bg);
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {
                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);


    }

    public void buttonC(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonC.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonC.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptC().equals(currentQuestion.getAnswer())){

                    buttonC.setBackgroundResource(R.drawable.correct_button_bg);
                    buttonC.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);
                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {
                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonC.setBackgroundResource(R.drawable.wrong_button_bg);
                    buttonC.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);
                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){
                                buttonB.setBackgroundResource(R.drawable.correct_button_bg);
                            }else if(currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {
                                buttonA.setBackgroundResource(R.drawable.correct_button_bg);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.correct_button_bg);
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {
                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);



    }

    public void buttonD(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonD.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonD.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptD().equals(currentQuestion.getAnswer())){

                    buttonD.setBackgroundResource(R.drawable.correct_button_bg);
                    buttonD.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);
                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {
                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonD.setBackgroundResource(R.drawable.wrong_button_bg);
                    buttonD.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);

                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);

                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){
                                buttonB.setBackgroundResource(R.drawable.correct_button_bg);
                            }else if(currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
                                buttonC.setBackgroundResource(R.drawable.correct_button_bg);
                            }else {
                                buttonA.setBackgroundResource(R.drawable.correct_button_bg);
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {
                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }


    @Override
    public void onBackPressed() {


        if (countDownTimer!=null){
            countDownTimer.cancel();
            if (backPressed + 2000 > System.currentTimeMillis()){
                Intent intent = new Intent(QuizActivity.this,CategoryActivity.class);
                startActivity(intent);
                finish();
            }else {

                Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

            }
            backPressed = System.currentTimeMillis();
        }



    }


    private void disableButtons(){

        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

    }

    private void enableButtons(){
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }


    private void finalQuizData(){

        Intent resultData = new Intent(QuizActivity.this,Result.class);
        resultData.putExtra(Constants.COINS,coins);
        resultData.putExtra(Constants.TOTAL_QUES,sizeOfQuiz);
        resultData.putExtra(Constants.CORRECT,correct);
        resultData.putExtra(Constants.WRONG,wrong);
        resultData.putExtra("Category",categoryValue);
        startActivity(resultData);
        finish();

    }



    private void startCountDownTimer(){

        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUnitilFinished) {
                timeLeftInMillis = millisUnitilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeLeftInMillis = 0;
                updateCountDownText();

            }
        }.start();



    }



    private void updateCountDownText(){

        int seconds = (int) (timeLeftInMillis/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d",seconds);

        savedTime = Long.parseLong(timeFormatted);

        timeText.setText(timeFormatted);

        if (timeLeftInMillis < 10000){

            timeText.setTextColor(ContextCompat.getColor(this,R.color.red));
        }else {
            timeText.setTextColor(ContextCompat.getColor(this,R.color.white));
        }

        if (timeLeftInMillis == 0){

            Toast.makeText(this, "Times Up!", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    timerDialog.timerDialog();

                }
            },1000);

        }


    }


}
