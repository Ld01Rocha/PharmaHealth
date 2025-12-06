package com.example.pharmahealthmobile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoricoDao {

    @Insert
    void inserir(HistoricoMedicamento historico);

    @Query("SELECT * FROM historico_medicamentos ORDER BY timestamp DESC LIMIT 50")
    LiveData<List<HistoricoMedicamento>> listarHistorico();

    @Query("SELECT * FROM historico_medicamentos WHERE medicamentoId = :medicamentoId ORDER BY timestamp DESC")
    LiveData<List<HistoricoMedicamento>> buscarPorMedicamento(long medicamentoId);

    // Estatísticas
    @Query("SELECT COUNT(*) FROM historico_medicamentos WHERE status = 'TOMADO' AND timestamp >= :dataInicio")
    int contarTomados(long dataInicio);

    @Query("SELECT COUNT(*) FROM historico_medicamentos WHERE status = 'PERDIDO' AND timestamp >= :dataInicio")
    int contarPerdidos(long dataInicio);
}