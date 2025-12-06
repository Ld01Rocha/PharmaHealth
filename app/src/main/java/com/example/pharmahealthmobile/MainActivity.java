package com.example.pharmahealthmobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem; // <-- 1. IMPORTANTE! Importe esta classe

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seu código original:
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alertas);

        // 2. HABILITA A SETA DE VOLTAR
        // Verifica se a ActionBar existe para evitar erros
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Alertas");
        }
    }

    // 3. LIDA COM O CLIQUE NA SETA DE VOLTAR (Up Button)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verifica se o item clicado é o botão de 'Home' (o ícone de voltar)
        if (item.getItemId() == android.R.id.home) {
            // A função finish() encerra a Activity atual (Alertas)
            // e a Activity anterior na pilha (MainActivity) é retomada.
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}