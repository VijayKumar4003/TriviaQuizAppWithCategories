package com.infowithvijay.triviaquizappwithroom;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsRepository {

    private QuestionsDao mQuestionDao;
    private LiveData<List<Questions>> mAllQuestions;


    public QuestionsRepository(Application application){

        QuestionsRoomDatabase db = QuestionsRoomDatabase.getInstance(application);
        mQuestionDao = db.wordDao();
        mAllQuestions = mQuestionDao.getAllQuestions();


    }

//    public LiveData<List<Questions>> getmAllQuestions(){
//        return mAllQuestions;
//    }

    public LiveData<List<Questions>> getQuestions(String category){

        mAllQuestions = mQuestionDao.getQuestionsByCategory(category);
        return mAllQuestions;
    }

}
