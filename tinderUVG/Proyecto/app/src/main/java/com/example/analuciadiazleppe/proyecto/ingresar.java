package com.example.analuciadiazleppe.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



/**
 * Created by Emilio on 4/6/2017.
 */

public class ingresar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingresar);

        final EditText etName = (EditText) findViewById(R.id.etUsuario);
        final EditText etClave = (EditText) findViewById(R.id.etClave);
        final Button bIngresar = (Button) findViewById(R.id.bIngresar);
        final Button bNuevo = (Button) findViewById(R.id.bNuevo);
        bNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bNuevo = new Intent(ingresar.this,crearnuevousuario.class);
                ingresar.this.startActivity(bNuevo);
            }
        });
        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = ((EditText)findViewById(R.id.etName)).getText().toString();
                String Clave =((EditText)findViewById(R.id.etClave)).getText().toString();
                if (Name.equals("Pyre")&& Clave.equals("2017")){
                Intent bIngresar = new Intent(ingresar.this,proyecto.class);
                ingresar.this.startActivity(bIngresar);}

            }
        });
    }
}

