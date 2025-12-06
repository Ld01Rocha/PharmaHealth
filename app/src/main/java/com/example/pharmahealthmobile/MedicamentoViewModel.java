package com.example.pharmahealthmobile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MedicamentoViewModel extends AndroidViewModel {

    private MedicamentoRepository repository;
    private LiveData<List<Medicamento>> todosMedicamentos;
    private LiveData<List<HistoricoMedicamento>> historico;

    public MedicamentoViewModel(@NonNull Application application) {
        super(application);
        repository = new MedicamentoRepository(application);
        todosMedicamentos = repository.getTodosAtivos();
        historico = repository.getHistorico();
    }

    // Getters para LiveData
    public LiveData<List<Medicamento>> getTodosMedicamentos() {
        return todosMedicamentos;
    }

    public LiveData<List<HistoricoMedicamento>> getHistorico() {
        return historico;
    }

    // Operações CRUD
    public void inserir(Medicamento medicamento, MedicamentoRepository.OnInsertCallback callback) {
        repository.inserir(medicamento, callback);
    }

    public void atualizar(Medicamento medicamento) {
        repository.atualizar(medicamento);
    }

    public void desativar(long id) {
        repository.desativar(id);
    }

    // Histórico
    public void registrarTomada(long medicamentoId, String nomeMedicamento) {
        repository.registrarTomada(medicamentoId, nomeMedicamento);
    }

    public void registrarPerdido(long medicamentoId, String nomeMedicamento) {
        repository.registrarPerdido(medicamentoId, nomeMedicamento);
    }

    // Estatísticas
    public void obterEstatisticas(MedicamentoRepository.OnEstatisticasCallback callback) {
        repository.obterEstatisticasSemana(callback);
    }
}