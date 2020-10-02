package com.workingsafe.safetyapp.videoquiz;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.workingsafe.safetyapp.R;

import java.util.ArrayList;
import java.util.List;


class TriviaQuizHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TQuiz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 13;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Video
    private static final String VIDEO ="VIDEO";
    //Image
    private static final String IMAGE="IMAGE";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , second column-question ,third column -video link, fourth column -option A, fifth column -option B , sixth column -option C , seventh column -option D , eighth column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), "+ VIDEO + " VARCHAR(255), " + IMAGE + " INTEGER, " +OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    TriviaQuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<TriviaQuestion> arraylist = new ArrayList<>();
        arraylist.add(new TriviaQuestion(1,"Identify the type of harassment","android.resource://com.workingsafe.safetyapp/"+ R.raw.scenario_1,1, "Quid Pro Quo Harassment", "Physical Harassment", "Hostile Work Environment Harassment", "Verbal Harassment", "Physical Harassment"));
        arraylist.add(new TriviaQuestion(2,"Identify the type of harassment", "android.resource://com.workingsafe.safetyapp/"+ R.raw.scenario_2,2,"Quid Pro Quo Harassment", "Physical Harassment", "Hostile Work Environment Harassment", "Verbal Harassment", "Quid Pro Quo Harassment"));
        arraylist.add(new TriviaQuestion(3,"Identify the type of harassment", "null", R.drawable.scenario_6,"Quid Pro Quo Harassment", "Physical Harassment", "Visual Harassment", "Verbal Harassment", "Visual Harassment"));
        arraylist.add(new TriviaQuestion(4,"Identify the type of harassment", "android.resource://com.workingsafe.safetyapp/"+ R.raw.scenario_3,3,"Quid Pro Quo Harassment", "Physical Harassment", "Hostile Work Environment Harassment", "Verbal Harassment", "Quid Pro Quo Harassment"));
        arraylist.add(new TriviaQuestion(5,"Identify the type of harassment", "android.resource://com.workingsafe.safetyapp/"+ R.raw.scenario_4,4,"Quid Pro Quo Harassment", "Physical Harassment", "Hostile Work Environment Harassment", "Verbal Harassment", "Hostile Work Environment Harassment"));
        arraylist.add(new TriviaQuestion(6,"Identify the type of harassment", "android.resource://com.workingsafe.safetyapp/"+ R.raw.scenario_5,5,"Quid Pro Quo Harassment", "Physical Harassment", "Hostile Work Environment Harassment", "Verbal Harassment", "Hostile Work Environment Harassment"));
        arraylist.add(new TriviaQuestion(7,"Identify the type of harassment", "null",R.drawable.scenario_7,"Visual Harassment", "Physical Harassment", "Hostile Work Environment Harassment", "Verbal Harassment", "Verbal Harassment"));

        this.addAllQuestions(arraylist);

    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.execSQL(DROP_TABLE);
    }


    private void addAllQuestions(ArrayList<TriviaQuestion> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TriviaQuestion question : allQuestions) {
                values.put(UID,question.getId());
                values.put(QUESTION, question.getQuestion());
                values.put(VIDEO,question.getVideo());
                values.put(IMAGE, question.getImage());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<TriviaQuestion> getAllOfTheQuestions() {

        List<TriviaQuestion> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION,VIDEO,IMAGE, OPTA, OPTB, OPTC, OPTD, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            TriviaQuestion question = new TriviaQuestion();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setVideo(cursor.getString(2));
            question.setImage(cursor.getInt(3));
            question.setOptA(cursor.getString(4));
            question.setOptB(cursor.getString(5));
            question.setOptC(cursor.getString(6));
            question.setOptD(cursor.getString(7));
            question.setAnswer(cursor.getString(8));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }
}
