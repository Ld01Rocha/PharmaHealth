package com.example.pharmahealthmobile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedicamentoDao {

    // CREATE
    @Insert
    long inserir(Medicamento medicamento);

    // READ
    @Query("SELECT * FROM medicamentos WHERE ativo = 1 ORDER BY horario ASC")
    LiveData<List<Medicamento>> listarTodosAtivos();

    @Query("SELECT * FROM medicamentos WHERE id = :id")
    Medicamento buscarPorId(long id);

    // UPDATE
    @Update
    void atualizar(Medicamento medicamento);

    // DELETE (soft delete - apenas desativa)
    @Query("UPDATE medicamentos SET ativo = 0 WHERE id = :id")
    void desativar(long id);

    // DELETE (hard delete - remove permanentemente)
    @Delete
    void deletar(Medicamento medicamento);
}