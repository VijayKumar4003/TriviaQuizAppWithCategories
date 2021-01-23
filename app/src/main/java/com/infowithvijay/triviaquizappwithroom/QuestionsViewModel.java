package com.infowithvijay.triviaquizappwithroom;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsViewModel extends AndroidViewModel {

    private QuestionsRepository mRepository;

    private LiveData<List<Questions>> mAllQuestions;



//    public QuestionsViewModel(Application application) {
//        super(application);
//
//        mRepository = new QuestionsRepository(application);
//        mAllQuestions = mRepository.getmAllQuestions();
//    }
//
//    LiveData<List<Questions>> getAllQuestions(){
//        return mAllQuestions;
//    }


    public QuestionsViewModel(Application application){
        super(application);
        mRepository = new QuestionsRepository(application);

    }


    LiveData<List<Questions>> getAllQuestionsByCategory(String category){
        mAllQuestions = mRepository.getQuestions(category);
        return mAllQuestions;
    }

}
