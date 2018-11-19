package com.example.irhad.historijabosne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Options extends AppCompatActivity {

    Button btnKviz;
    Button btnHighScore;
    Button btnCredits;
    Intent intent;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnKviz = findViewById(R.id.btnKviz);
        btnHighScore = findViewById(R.id.btnHighScore);
        btnCredits = findViewById(R.id.btnCredits);
        animation = AnimationUtils.loadAnimation(this,R.anim.my_animation);

        btnKviz.startAnimation(animation);
        btnHighScore.startAnimation(animation);
        btnCredits.startAnimation(animation);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        btnKviz.startAnimation(animation);
        btnHighScore.startAnimation(animation);
        btnCredits.startAnimation(animation);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void btnOptions(View view){
        switch (Integer.parseInt(view.getTag().toString())){
            case 1:
                intent = new Intent(Options.this, QuizActivity.class);
                break;
            case 2:
                intent = new Intent(Options.this, HighScore.class);
                break;
            case 3:
                intent = new Intent(Options.this, Credits.class);
                break;
        }
        startActivity(intent);
    }

    public void exit(View view){
        System.exit(0);
    }


}
