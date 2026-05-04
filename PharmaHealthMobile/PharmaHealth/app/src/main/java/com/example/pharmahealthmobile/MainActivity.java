package com.example.pharmahealthmobile;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNome, etDosagem, etHorario;
    private MedicamentoViewModel viewModel;
    private MedicamentoAdapter adapter; // Adicionado para gerenciar a lista

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

        // 3. CONFIGURAÇÃO DA LISTA (RecyclerView)
        configurarRecyclerView();

        // 4. Listeners de clique
        etHorario.setOnClickListener(v -> abrirRelogio());
        btnSalvar.setOnClickListener(v -> salvarEPresentarAlerta());
    }

    private void configurarRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_medicamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa o adapter com a lógica de clique nos botões de ação
        adapter = new MedicamentoAdapter(new MedicamentoAdapter.OnMedicamentoClickListener() {
            @Override
            public void onTomadoClick(Medicamento medicamento) {
                // Registra no histórico como TOMADO usando seu HistoricoDao
                viewModel.registrarTomada(medicamento.getId(), medicamento.getNome());
                Toast.makeText(MainActivity.this, "Registro: " + medicamento.getNome() + " tomado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPerdeuClick(Medicamento medicamento) {
                // Registra no histórico como PERDIDO
                viewModel.registrarPerdido(medicamento.getId(), medicamento.getNome());
                Toast.makeText(MainActivity.this, "Aviso: " + medicamento.getNome() + " marcado como perdido.", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        // OBSERVAR DADOS: Sempre que o banco mudar, a lista atualiza sozinha
        viewModel.getTodosMedicamentos().observe(this, lista -> {
            if (lista != null) {
                adapter.submitList(lista);
            }
        });
    }

    private void abrirRelogio() {
        Calendar calendar = Calendar.getInstance();
        int horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoAtual = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    String horarioFormatado = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    etHorario.setText(horarioFormatado);
                }, horaAtual, minutoAtual, true);

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

        Medicamento novo = new Medicamento(nome, dosagem, horario);

        // Salva no banco via Repository/DAO
        viewModel.inserir(novo, idGerado -> {
            // Agenda o alarme no sistema usando seu AlertaManager
            AlertaManager.agendarAlerta(
                    getApplicationContext(),
                    idGerado,
                    nome,
                    horario
            );

            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "✅ " + nome + " agendado para as " + horario, Toast.LENGTH_LONG).show();
                limparCampos();
            });
        });
    }

    private void limparCampos() {
        etNome.setText("");
        etDosagem.setText("");
        etHorario.setText("");
        etNome.requestFocus();
    }
}