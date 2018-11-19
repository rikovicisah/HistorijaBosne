package com.example.irhad.historijabosne;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    TextView txtBodova;
    TextView txtTacnih;
    TextView txtBrPitanja;
    Button btnSpasi;
    EditText txtUnosIme;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txtBodova = findViewById(R.id.txtBodova);
        txtTacnih = findViewById(R.id.txtTacnih);
        txtBrPitanja = findViewById(R.id.txtBrPitanja);
        btnSpasi = findViewById(R.id.btnSpasi);
        txtUnosIme = findViewById(R.id.txtUnosIme);

        sharedPreferences = getSharedPreferences("userScore", Context.MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        txtBodova.setText("Broj bodova: " + this.getIntent().getExtras().getInt("score"));
        txtTacnih.setText("Tacnih odgovora: " + this.getIntent().getExtras().getInt("tacni"));
        txtBrPitanja.setText("Ukupno pitanja:" + this.getIntent().getExtras().getInt("total"));
    }

    public void onClickBtnSpasi(View view){
        if(!txtUnosIme.getText().toString().isEmpty()) {
            if(sharedPreferences != null){
                if(this.getIntent().getExtras().getInt("score") >
                        sharedPreferences.getInt("rezultat", 0)){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("rezultat", this.getIntent().getExtras().getInt("score"));
                    editor.putString("ime", txtUnosIme.getText().toString());
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Spaseno", Toast.LENGTH_SHORT).show();
                }
                txtUnosIme.setText("");
            }

        }else
            Toast.makeText(getApplicationContext(),
                    "Unesite ime ukoliko zelite spasiti rezultat",
                    Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
