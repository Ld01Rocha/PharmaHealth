package com.example.pharmahealthmobile;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicamentos")
public class Medicamento {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String nome;
    private String dosagem;
    private String horario; // Formato "HH:mm"
    private boolean ativo;
    private long dataCriacao;

    // Construtor vazio (obrigatório para Room)
    public Medicamento() {
        this.ativo = true;
        this.dataCriacao = System.currentTimeMillis();
    }

    // Construtor com parâmetros
    public Medicamento(String nome, String dosagem, String horario) {
        this.nome = nome;
        this.dosagem = dosagem;
        this.horario = horario;
        this.ativo = true;
        this.dataCriacao = System.currentTimeMillis();
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(long dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}