package com.example.pharmahealthmobile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "historico_medicamentos")
public class HistoricoMedicamento {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long medicamentoId;
    private String nomeMedicamento;
    private long timestamp;
    private String status; // "TOMADO" ou "PERDIDO"

    // Construtor vazio
    public HistoricoMedicamento() {
        this.timestamp = System.currentTimeMillis();
    }

    // Construtor com parâmetros
    public HistoricoMedicamento(long medicamentoId, String nomeMedicamento, String status) {
        this.medicamentoId = medicamentoId;
        this.nomeMedicamento = nomeMedicamento;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}