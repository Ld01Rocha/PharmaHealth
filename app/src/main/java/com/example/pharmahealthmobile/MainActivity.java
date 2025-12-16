package com.example.pharmahealthmobile;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNome, etDosagem, etHorario;
    private MedicamentoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar o ViewModel
        viewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);

        // 2. Referenciar os componentes do XML
        etNome = findViewById(R.id.et_nome_medicamento);
        etDosagem = findViewById(R.id.et_dosagem);
        etHorario = findViewById(R.id.et_horario);
        MaterialButton btnSalvar = findViewById(R.id.btn_salvar_medicamento);

        // --- INTEGRAÇÃO PASSO 3: Configurar o TimePicker ao clicar no campo horário ---
        etHorario.setOnClickListener(v -> abrirRelogio());

        // 3. Configurar o clique do botão Salvar
        btnSalvar.setOnClickListener(v -> {
            salvarEPresentarAlerta();
        });
    }

    // Método para abrir o seletor de horas (TimePicker)
    private void abrirRelogio() {
        Calendar calendar = Calendar.getInstance();
        int horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoAtual = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    // Formata para garantir o padrão "HH:mm" (ex: 09:05)
                    String horarioFormatado = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    etHorario.setText(horarioFormatado);
                }, horaAtual, minutoAtual, true); // 'true' para formato 24h

        timePickerDialog.show();
    }

    private void salvarEPresentarAlerta() {
        String nome = etNome.getText().toString().trim();
        String dosagem = etDosagem.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();

        if (nome.isEmpty() || dosagem.isEmpty() || horario.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar o objeto medicamento
        Medicamento novo = new Medicamento(nome, dosagem, horario);

        // --- INTEGRAÇÃO PASSO 2: SALVAR NO BANCO E AGENDAR ALERTA ---
        viewModel.inserir(novo, idGerado -> {

            // Chama o AlertaManager usando o ID que o banco acabou de criar
            AlertaManager.agendarAlerta(
                    getApplicationContext(),
                    idGerado,
                    nome,
                    horario
            );

            // Feedback visual na UI (Precisa rodar na MainThread)
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "✅ Salvo: Alerta agendado para " + horario, Toast.LENGTH_LONG).show();

                // Limpar campos após sucesso
                etNome.setText("");
                etDosagem.setText("");
                etHorario.setText("");
                etNome.requestFocus(); // Volta o foco para o primeiro campo
            });
        });
    }
}