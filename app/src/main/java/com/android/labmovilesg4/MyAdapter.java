package com.android.labmovilesg4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Tareas> tareasList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Tareas tarea, int position);
    }

    public MyAdapter(List<Tareas> tareasList, OnItemClickListener listener) {
        this.tareasList = tareasList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tareas tarea = tareasList.get(position);

        holder.txtTitulo.setText(tarea.getTitulo());
        holder.txtDescripcion.setText(tarea.getDescripcion());
        holder.txtFecha.setText(tarea.getFecha());
        holder.txtHora.setText(tarea.getHora());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(tarea, position);  // Notificar el clic y la posición
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareasList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescripcion, txtFecha, txtHora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.tvTitulo);
            txtDescripcion = itemView.findViewById(R.id.tvDescripcion);
            txtFecha = itemView.findViewById(R.id.tvFecha);
            txtHora = itemView.findViewById(R.id.tvHora);
        }
    }
}
