package com.example.pharmahealthmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MedicamentoAdapter extends ListAdapter<Medicamento, MedicamentoAdapter.MedicamentoViewHolder> {

    private OnMedicamentoClickListener listener;

    public interface OnMedicamentoClickListener {
        void onTomadoClick(Medicamento medicamento);
        void onPerdeuClick(Medicamento medicamento);
    }

    public MedicamentoAdapter(OnMedicamentoClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Medicamento> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Medicamento>() {
                @Override
                public boolean areItemsTheSame(@NonNull Medicamento oldItem, @NonNull Medicamento newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Medicamento oldItem, @NonNull Medicamento newItem) {
                    return oldItem.getNome().equals(newItem.getNome()) &&
                            oldItem.getDosagem().equals(newItem.getDosagem()) &&
                            oldItem.getHorario().equals(newItem.getHorario());
                }
            };

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemmedicamento, parent, false);
        return new MedicamentoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        Medicamento medicamento = getItem(position);
        holder.bind(medicamento);
    }

    class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHorario;
        private TextView tvNome;
        private TextView tvDosagem;
        private Button btnTomado;
        private Button btnPerdeu;

        public MedicamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHorario = itemView.findViewById(R.id.tv_horario_item);
            tvNome = itemView.findViewById(R.id.tv_nome_medicamento_item);
            tvDosagem = itemView.findViewById(R.id.tv_dosagem_item);
            btnTomado = itemView.findViewById(R.id.btn_tomado);
            btnPerdeu = itemView.findViewById(R.id.btn_perdeu);
        }

        public void bind(Medicamento medicamento) {
            tvHorario.setText(medicamento.getHorario());
            tvNome.setText(medicamento.getNome());
            tvDosagem.setText(medicamento.getDosagem());

            btnTomado.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTomadoClick(medicamento);
                }
            });

            btnPerdeu.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPerdeuClick(medicamento);
                }
            });
        }
    }
}