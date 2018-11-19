package com.example.irhad.historijabosne;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irhad.historijabosne.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    TextView txtPitanja;
    public TextView txtTimer;
    Button btn1,btn2,btn3,btn4;
    int score = 0;
    int brojTacnih = 0;
    int questionsTotal = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    Question question;
    public MediaPlayer mediaPlayer;
    private Set<Integer> listaPitanja;
    private Random random;
    private static final int MAXNUMBER_QUESTIONS = 40;
    int brPitanja = 0;
    Animation animation;
    CountDownTimer countDownTimer;
    private ArrayList<Button> listaButtona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtPitanja = findViewById(R.id.txtPitanja);
        txtTimer = findViewById(R.id.txtTimer);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.klik);
        listaPitanja = new HashSet<>();
        random = new Random();

        listaButtona = new ArrayList<>();
        listaButtona.add(btn1);
        listaButtona.add(btn2);
        listaButtona.add(btn3);
        listaButtona.add(btn4);

        animation = AnimationUtils.loadAnimation(this,R.anim.my_animation);


        if(isInternetOn()) {
            buttonsEnabledDisabled(true);
            reset();
            updateQuestion();
            timer();
        }
        else {
            buttonsEnabledDisabled(false);
            Toast.makeText(getApplicationContext(),
                    "Kako bi nastavili sa kvizom potrebno je ukljuciti internet",
                    Toast.LENGTH_LONG).show();
            txtPitanja.setText("Kako bi nastavili sa kvizom potrebno je ukljuciti internet");
        }

    }

    //provjeriti da li je ukljucen internet
    private boolean isInternetOn(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void updateQuestion(){
        if(!isInternetOn()){
            buttonsEnabledDisabled(false);
            Toast.makeText(getApplicationContext(),
                    "Kako bi nastavili sa kvizom potrebno je ukljuciti internet",
                    Toast.LENGTH_LONG).show();
            txtPitanja.setText("Kako bi nastavili sa kvizom potrebno je ukljuciti internet");
        }else {
            randomGenerate();
            resetButton();
            if (questionsTotal < MAXNUMBER_QUESTIONS) {
                myRef = database.getReference().child("Questions").child(String.valueOf(questionsTotal));
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        question = dataSnapshot.getValue(Question.class);

                        txtPitanja.setText(question.getQuestion());

                        btn1.startAnimation(animation);
                        btn2.startAnimation(animation);
                        btn3.startAnimation(animation);
                        btn4.startAnimation(animation);

                        btn1.setText(question.getOption1());
                        btn2.setText(question.getOption2());
                        btn3.setText(question.getOption3());
                        btn4.setText(question.getOption4());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                openScoreActivity();
            }
        }

    }

    public void onClickButton(View view){
        switch (Integer.parseInt(view.getTag().toString())){
            case 1:
                provjeraPitanja(((Button)view).getText().toString(), view);
                break;
            case 2:
                provjeraPitanja(((Button)view).getText().toString(), view);
                break;
            case 3:
                provjeraPitanja(((Button)view).getText().toString(), view);
                break;
            case 4:
                provjeraPitanja(((Button)view).getText().toString(), view);
                break;
        }
        updateQuestion();
    }//onClickButton

    private void timer(){
        countDownTimer = new CountDownTimer(61000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                int t = ((int)millisUntilFinished/1000);
                if(t < 10) {
                    txtTimer.setText("00:0" + ((int) millisUntilFinished / 1000));
                    txtTimer.setTextColor(getResources().getColor(R.color.crvena));
                }else{
                    txtTimer.setText("00:" + ((int) millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                openScoreActivity();
            }
        }.start();
    }

    private void provjeraPitanja(String odg, View view){
        if(question.getAnswer().equals(odg)) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.klik);
            ((Button)view).setBackgroundColor(getResources().getColor(R.color.zelena));
            score += 10;
            brojTacnih++;
        }else{
            ((Button)view).setBackgroundColor(getResources().getColor(R.color.crvena));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
            score -= 5;
        }
        mediaPlayer.start();

    }

    private void openScoreActivity(){
        countDownTimer.cancel();
        Intent scores = new Intent(QuizActivity.this, ScoreActivity.class);
        scores.putExtra("total", brPitanja);
        scores.putExtra("score", (score < 0)? 0 : score);
        scores.putExtra("tacni", brojTacnih);
        startActivity(scores);
    }

    private void reset(){
        questionsTotal = 0;
        brPitanja = 0;
        score = brojTacnih = 0;
        listaPitanja.clear();
        txtTimer.setTextColor(getResources().getColor(R.color.plava));
        resetButton();
    }
    private void resetButton(){
        btn1.setBackgroundResource(R.drawable.button_background);
        btn2.setBackgroundResource(R.drawable.button_background);
        btn3.setBackgroundResource(R.drawable.button_background);
        btn4.setBackgroundResource(R.drawable.button_background);
    }

    private void randomGenerate(){
        questionsTotal = random.nextInt(MAXNUMBER_QUESTIONS) + 1;
        if(!listaPitanja.add(questionsTotal))
            randomGenerate();
        else brPitanja++;
    }

    private void buttonsEnabledDisabled(boolean enabled){
        for (Button b :
                listaButtona) {
            b.setEnabled(enabled);
        }
    }



}
