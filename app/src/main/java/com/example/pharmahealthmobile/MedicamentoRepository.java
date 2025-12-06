package com.example.pharmahealthmobile;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MedicamentoRepository {

    private MedicamentoDao medicamentoDao;
    private HistoricoDao historicoDao;
    private LiveData<List<Medicamento>> todosAtivos;
    private LiveData<List<HistoricoMedicamento>> historico;

    // Executor para operações assíncronas
    private ExecutorService executorService;

    public MedicamentoRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        medicamentoDao = database.medicamentoDao();
        historicoDao = database.historicoDao();
        todosAtivos = medicamentoDao.listarTodosAtivos();
        historico = historicoDao.listarHistorico();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Getter para LiveData
    public LiveData<List<Medicamento>> getTodosAtivos() {
        return todosAtivos;
    }

    public LiveData<List<HistoricoMedicamento>> getHistorico() {
        return historico;
    }

    // CRUD - Medicamentos
    public void inserir(Medicamento medicamento, OnInsertCallback callback) {
        executorService.execute(() -> {
            long id = medicamentoDao.inserir(medicamento);
            if (callback != null) {
                callback.onInserted(id);
            }
        });
    }

    public void atualizar(Medicamento medicamento) {
        executorService.execute(() -> medicamentoDao.atualizar(medicamento));
    }

    public void desativar(long id) {
        executorService.execute(() -> medicamentoDao.desativar(id));
    }

    // Histórico
    public void registrarTomada(long medicamentoId, String nomeMedicamento) {
        executorService.execute(() -> {
            HistoricoMedicamento registro = new HistoricoMedicamento(
                    medicamentoId,
                    nomeMedicamento,
                    "TOMADO"
            );
            historicoDao.inserir(registro);
        });
    }

    public void registrarPerdido(long medicamentoId, String nomeMedicamento) {
        executorService.execute(() -> {
            HistoricoMedicamento registro = new HistoricoMedicamento(
                    medicamentoId,
                    nomeMedicamento,
                    "PERDIDO"
            );
            historicoDao.inserir(registro);
        });
    }

    public void obterEstatisticasSemana(OnEstatisticasCallback callback) {
        executorService.execute(() -> {
            long umaSemanaAtras = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L);
            int tomados = historicoDao.contarTomados(umaSemanaAtras);
            int perdidos = historicoDao.contarPerdidos(umaSemanaAtras);

            if (callback != null) {
                callback.onEstatisticas(tomados, perdidos);
            }
        });
    }

    // Interfaces para callbacks
    public interface OnInsertCallback {
        void onInserted(long id);
    }

    public interface OnEstatisticasCallback {
        void onEstatisticas(int tomados, int perdidos);
    }
}