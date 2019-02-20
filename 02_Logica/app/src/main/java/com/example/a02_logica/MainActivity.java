package com.example.a02_logica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Button mSubmit;
    private EditText R1;
    private EditText R2;
    private EditText R3;
    private EditText R4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        R1 = findViewById(R.id.editText2);
        R2 = findViewById(R.id.editText3);
        R3 = findViewById(R.id.editText4);
        R4 = findViewById(R.id.editText5);

        mSubmit = findViewById(R.id.buttonSubmit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                CheckInput();
            }
        });
    }

    private boolean CheckInput() {

        if(R1.getText().toString().toUpperCase().equals("T")
        && R2.getText().toString().toUpperCase().equals("F")
        && R3.getText().toString().toUpperCase().equals("F")
        && R4.getText().toString().toUpperCase().equals("F")) {
            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(MainActivity.this, "Probeer opnieuw", Toast.LENGTH_SHORT).show();
        return false;
    }
}
