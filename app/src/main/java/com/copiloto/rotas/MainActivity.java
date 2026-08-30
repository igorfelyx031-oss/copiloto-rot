package com.copiloto.rotas;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView texto = new TextView(this);
        texto.setText("Copiloto de Rotas");
        texto.setTextSize(24);
        texto.setTextColor(Color.WHITE);
        texto.setPadding(40, 80, 40, 40);

        setContentView(texto);
    }
}
