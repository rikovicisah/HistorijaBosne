package com.example.irhad.historijabosne;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {

    TextView txtHighScore;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtHighScore = findViewById(R.id.txtHighScore);

        sharedPreferences = getSharedPreferences("userScore", Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            txtHighScore.setText("Najbolji rezultat :\n" +
                    sharedPreferences.getString("ime", "dd") + "\n" +
                    sharedPreferences.getInt("rezultat", 0));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
