package com.example.vamsikrishnag.mcassign3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        Bundle bundle = getIntent().getExtras();
        String acc = bundle.getString("accuracy");
        TextView kcrossval = (TextView)findViewById(R.id.kcrossval);
        kcrossval.setText(acc);
    }
}
