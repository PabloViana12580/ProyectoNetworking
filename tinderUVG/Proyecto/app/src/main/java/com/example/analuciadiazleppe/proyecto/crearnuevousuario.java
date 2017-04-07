package com.example.analuciadiazleppe.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Emilio on 4/6/2017.
 */

public class crearnuevousuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearnuevousuario);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etClave = (EditText) findViewById(R.id.etClave);
        final Button bIngresar = (Button) findViewById(R.id.bIngresar);

        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = ((EditText)findViewById(R.id.etName)).getText().toString();
                String Clave =((EditText)findViewById(R.id.etClave)).getText().toString();
                Intent bIngresar = new Intent(crearnuevousuario.this,ingresar.class);
                crearnuevousuario.this.startActivity(bIngresar);

            }
        });




    }
}