package com.infowithvijay.triviaquizappwithroom;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Questions.class},version = 5)
public abstract class QuestionsRoomDatabase extends RoomDatabase {


    private static QuestionsRoomDatabase INSTANCE;


    public abstract QuestionsDao wordDao();



    public static synchronized QuestionsRoomDatabase getInstance(final Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    QuestionsRoomDatabase.class,"questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();
        }

        return INSTANCE;
    }

    private static Callback RoomDBCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(INSTANCE).execute();

        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{


        private QuestionsDao wordDao;


        private PopulateDbAsyncTask(QuestionsRoomDatabase db)
        {
            wordDao = db.wordDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {


            wordDao.insert(new Questions(" Category : Java : API 21 is for what ?","Lollipop","Nought","Oreo","Android","Lollipop","Java"));
            wordDao.insert(new Questions(" Category : Java : PC full is ? ","Lollipop","Personal Computer","Oreo","Android","Personal Computer","Java"));
            wordDao.insert(new Questions(" Category : Java : Firefox is what ?","Virus","Nought","Browser","Android","Browser","Java"));
            wordDao.insert(new Questions(" Category : Java : API 25 is for what ?","Lollipop","Nought","Oreo","Android","Nought","Java"));
            wordDao.insert(new Questions("Category : Java : Which of the following is a chat engine?","Google Bol","Yahoo Talk","Rediif Messenger","None of these","None of these","Java"));
            wordDao.insert(new Questions("Category : Java : Which of the following is an input device?","Plotter","Printer","Monitor","Scanner","Scanner","Java"));
            wordDao.insert(new Questions("Category : Java : HTML is used to create -","Operating System","High Level Program","Web-Server","Web Page","Web Page","Java"));
            wordDao.insert(new Questions(" Category : Java : API 25 is for what ?","Lollipop","Nought","Oreo","Android","Nought","Java"));
            wordDao.insert(new Questions("Category : Java : Which of the following is a chat engine?","Google Bol","Yahoo Talk","Rediif Messenger","None of these","None of these","Java"));
            wordDao.insert(new Questions("Category : Java : Which of the following is an input device?","Plotter","Printer","Monitor","Scanner","Scanner","Java"));
            wordDao.insert(new Questions("Category : Java : HTML is used to create -","Operating System","High Level Program","Web-Server","Web Page","Web Page","Java"));



            wordDao.insert(new Questions("Category : Dart : Which is the fastest memory in computer","RAM","ROM","Cache","Hard Drive","Cache","Dart"));
            wordDao.insert(new Questions("Category : Dart : What is the name for a webpage address? ","Directory","Protocol","URL","Domain","URL","Dart"));
            wordDao.insert(new Questions("Category : Dart : Which of the following is not an input device?","Microphone","Keyboard","Mozilla firefox","Mouse","Mozilla firefox","Dart"));
            wordDao.insert(new Questions("Category : Dart : Which is the fastest memory in computer","RAM","ROM","Cache","Hard Drive","Cache","Dart"));
            wordDao.insert(new Questions("Category : Dart : What is the name for a webpage address? ","Directory","Protocol","URL","Domain","URL","Dart"));
            wordDao.insert(new Questions("Category : Dart : Which of the following is not an input device?","Microphone","Keyboard","Mozilla firefox","Mouse","Mozilla firefox","Dart"));
            wordDao.insert(new Questions("Category : Dart : Which is the fastest memory in computer","RAM","ROM","Cache","Hard Drive","Cache","Dart"));
            wordDao.insert(new Questions("Category : Dart : What is the name for a webpage address? ","Directory","Protocol","URL","Domain","URL","Dart"));
            wordDao.insert(new Questions("Category : Dart : Which of the following is not an input device?","Microphone","Keyboard","Mozilla firefox","Mouse","Mozilla firefox","Dart"));

            return null;
        }
    }


}
