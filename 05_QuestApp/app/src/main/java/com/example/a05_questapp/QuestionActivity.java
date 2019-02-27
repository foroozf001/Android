package com.example.a05_questapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;
    private int questionNumber;
    private String[] items;
    private String correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtons = new RadioButton[3];
        radioButtons[0] = findViewById(R.id.radioButton1);
        radioButtons[1] = findViewById(R.id.radioButton2);
        radioButtons[2] = findViewById(R.id.radioButton3);
        questionNumber = 1;
        populateQuestion(questionNumber);
    }

    public void populateQuestion(int questionNumber){
        //   populate question
        String question = "question" + questionNumber;
        int holderint1 = getResources().getIdentifier(question, "string",
                this.getPackageName()); // You had used "name"

        String questionScreen = getResources().getString(holderint1);
        textViewQuestion.setText(questionScreen);

        // populate answer
        String answer = "answer" + questionNumber;
        int holderint = getResources().getIdentifier(answer, "array",
                this.getPackageName()); // You had used "name"
        items = getResources().getStringArray(holderint);
        radioButtons[0].setText(items[1]);
        radioButtons[1].setText(items[2]);
        radioButtons[2].setText(items[3]);
        correctAnswer = items[0];
    }
}
