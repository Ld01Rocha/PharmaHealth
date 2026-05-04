package com.example.pharmahealthmobile;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class AlertaReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "PHARMA_ALERT_CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent) {
        long medicamentoId = intent.getLongExtra("MEDICAMENTO_ID", -1);
        String nomeMedicamento = intent.getStringExtra("NOME_MEDICAMENTO");

        if (nomeMedicamento == null || medicamentoId == -1) return;

        createNotificationChannel(context);

        // Ação ao clicar no corpo da notificação (Abre o App)
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, (int) medicamentoId, mainIntent, PendingIntent.FLAG_IMMUTABLE);

        // --- NOVO: Ação para o botão "TOMADO" ---
        Intent tomadoIntent = new Intent(context, AcaoBotaoReceiver.class);
        tomadoIntent.setAction("ACAO_TOMADO");
        tomadoIntent.putExtra("MEDICAMENTO_ID", medicamentoId);
        tomadoIntent.putExtra("NOME_MEDICAMENTO", nomeMedicamento);

        PendingIntent tomadoPendingIntent = PendingIntent.getBroadcast(
                context,
                (int) medicamentoId,
                tomadoIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Construindo a notificação com o botão
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("💊 Hora do Remédio!")
                .setContentText("Tomar: " + nomeMedicamento)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(mainPendingIntent)
                .setAutoCancel(true)
                // Adiciona o botão na notificação
                .addAction(R.drawable.ic_launcher_foreground, "MARCAR COMO TOMADO", tomadoPendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify((int) medicamentoId, builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Alertas", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }
}