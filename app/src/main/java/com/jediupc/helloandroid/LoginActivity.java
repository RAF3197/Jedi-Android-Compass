package com.jediupc.helloandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity{

    public static final String USERNAME = "username";
    public static final String KEY_SETTINGS = "settings";
    Button bLogin;
    EditText mUser;
    TextView pass;
    TextView mTextView;
    CheckBox passVisible,remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences(KEY_SETTINGS,MODE_PRIVATE);

        if (sp.contains(USERNAME)) {
            Log.d("LoginActivity","Already have username");
            startContentActivity();
            return;
        }

        bLogin = findViewById(R.id.button);
        mUser = findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);
        passVisible = findViewById(R.id.checkBox);
        pass = findViewById(R.id.editText2);
        remember =  findViewById(R.id.checkBox2);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity", "Click :)");
                Log.d("LoginActivity",mUser.getText().toString());
                Log.d("LoginActivity",pass.getText().toString());

                if(remember.isChecked()) {
                    SharedPreferences sp = getSharedPreferences(KEY_SETTINGS,MODE_PRIVATE);
                    sp.edit().putString(USERNAME,mUser.getText().toString()).apply();
                }

                startContentActivity();
            }
        });
        passVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) { pass.setTransformationMethod(null); }
                else if (!b) { pass.setTransformationMethod(new PasswordTransformationMethod()); }
            }
        });
    }

    private void startContentActivity() {
        Intent i = new Intent(LoginActivity.this, ListActivity.class);
        startActivity(i);
        finish();
    }
}