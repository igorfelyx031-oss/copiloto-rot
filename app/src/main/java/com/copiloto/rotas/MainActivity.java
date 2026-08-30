package com.copiloto.rotas;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout tela = new FrameLayout(this);
        tela.setBackgroundColor(Color.rgb(11, 18, 32));

        TextView botao = new TextView(this);

        botao.setText("🟠\nCOPILOTO");
        botao.setTextColor(Color.WHITE);
        botao.setTextSize(14);
        botao.setGravity(Gravity.CENTER);
        botao.setPadding(18, 12, 18, 12);

        GradientDrawable fundo = new GradientDrawable();
        fundo.setColor(Color.rgb(234, 88, 12));
        fundo.setCornerRadius(100);
        botao.setBackground(fundo);

        FrameLayout.LayoutParams tamanho =
                new FrameLayout.LayoutParams(
                        130,
                        70
                );

        tamanho.gravity = Gravity.TOP | Gravity.END;
        tamanho.setMargins(0, 60, 15, 0);

        tela.addView(botao, tamanho);

        botao.setOnClickListener(v -> {
            botao.setText("🟢\nATIVO");
        });

        setContentView(tela);
    }
}
