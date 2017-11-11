package com.example.java_oglen.jsonuserloginregister;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity

{
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        txt=(TextView)  findViewById(R.id.textView);
    }
}
