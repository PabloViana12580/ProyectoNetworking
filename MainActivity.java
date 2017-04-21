package com.example.familia.ratingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    RatingBar ratingBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "stars: " + (int)ratingBar.getNumStars(),Toast.LENGTH_LONG).show();

            }
        });}
        public int getStars (int stars){
            int salida = 0 ;
            int presalida = 0;
            double starsnumber;
            starsnumber = (int)ratingBar.getNumStars();
            if (starsnumber == 1){
                presalida = 1;
            } else if (starsnumber == 2) {
                presalida = 2;
            } else if (starsnumber == 3) {
                presalida = 3;
            }
            else if (starsnumber == 4){
                presalida = 4;
            }
            else if (starsnumber == 5) {
                presalida = 5;
            }
            salida = presalida + salida;
            return salida;
        }
    }
