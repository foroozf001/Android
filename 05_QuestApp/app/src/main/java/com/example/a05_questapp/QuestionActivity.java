package com.example.a05_questapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class QuestionActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;
    private int questionNumber = 1;
    private String[] items;
    private String correctAnswer;
    private Button checkAnswerButton;
    RadioButton answer;
    public static final String questionKey = "questionKey";

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
        populateQuestion(questionNumber);
        checkAnswerButton = findViewById(R.id.buttonCheckAnswer);

        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Snackbar.make(view, getApplicationContext().getString(R.string.choose_answer), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    answer = findViewById(selectedId);
                    if (answer.getText().equals(correctAnswer)) {
                        Intent intent = new Intent(QuestionActivity.this, LocationActivity.class);
                        intent.putExtra(questionKey, questionNumber);
                        startActivityForResult(intent,1234);
                    } else {
                        Snackbar.make(view, getApplicationContext().getString(R.string.try_again), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            }
        });
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check if the result code is the right one
        if (resultCode == Activity.RESULT_OK) {
            //Check if the request code is correct
            if (requestCode == 1234) {
                questionNumber = data.getIntExtra(LocationActivity.clueKey,-1);
                populateQuestion(questionNumber);
                radioGroup.clearCheck();
            }
        }
    }
}
