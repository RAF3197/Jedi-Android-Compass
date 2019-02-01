package com.jediupc.helloandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    Button mButton;
    EditText mTextEdit;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mButton = findViewById(R.id.button);
        mTextEdit = findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity","Click :)");
                mTextView.setText(mTextEdit.getText());
            }
        });
    }
}