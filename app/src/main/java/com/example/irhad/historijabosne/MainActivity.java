package com.example.irhad.historijabosne;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView slika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slika = findViewById(R.id.slika);
        if(isInternetOn()) {
            String url = "https://i.pinimg.com/564x/1c/3d/ef/1c3def5861319923bb46cd0037922412.jpg";
            Picasso.with(this).load(url).into(slika);
            Animation animation = AnimationUtils.loadAnimation(this,R.anim.my_animation);
            slika.startAnimation(animation);
            timer();

        }else{
            TextView txtIspisInternet = new TextView(getApplicationContext());
            txtIspisInternet.setText("Kako bi nastavili sa kvizom potrebno je ukljuciti internet");
            txtIspisInternet.setTextSize(20f);
            txtIspisInternet.setTextColor(Color.parseColor("#ff0000"));
            ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
            constraintLayout.addView(txtIspisInternet);
            timerShutdown();
        }


    }

    //provjeriti da li je ukljucen internet
    private boolean isInternetOn(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void timer(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent options = new Intent(MainActivity.this, Options.class);
                startActivity(options);
                finish();
            }
        }, 2000);
    }

    private void timerShutdown(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 5000);
    }
}
