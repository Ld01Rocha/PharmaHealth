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
            // Recebe os dados agendados do AlarmManager
            long medicamentoId = intent.getLongExtra("MEDICAMENTO_ID", -1);
            String nomeMedicamento = intent.getStringExtra("NOME_MEDICAMENTO");

            if (nomeMedicamento == null || medicamentoId == -1) {
                return;
            }

            // 1. Criar o canal de notificação (necessário para Android 8.0/Oreo e superiores)
            createNotificationChannel(context);

            // 2. Configurar a ação ao clicar na notificação (volta para a tela principal)
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    (int) medicamentoId, // Usa o ID como código para unicidade
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            // 3. Construir a Notificação
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground) // TROQUE PELO SEU ÍCONE
                    .setContentTitle("💊 Hora do Medicamento!")
                    .setContentText("Lembrete: Administrar " + nomeMedicamento + " agora.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            // 4. Disparar a notificação
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify((int) medicamentoId, builder.build());
            }
        }

        private void createNotificationChannel(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Alertas de Medicamento";
                String description = "Notificações para lembretes de horários de medicamentos.";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }
        }

}
