package com.example.pharmahealthmobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AcaoBotaoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("ACAO_TOMADO".equals(intent.getAction())) {
            String nome = intent.getStringExtra("NOME_MEDICAMENTO");
            Toast.makeText(context, "Medicamento " + nome + " registrado!", Toast.LENGTH_SHORT).show();
            // Aqui você chamará o viewModel futuramente para salvar no histórico
        }
    }
}