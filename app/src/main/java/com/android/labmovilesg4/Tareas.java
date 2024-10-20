package com.android.labmovilesg4;

import androidx.annotation.NonNull;

public class Tareas {

    String titulo;
    String descripcion;
    String fecha;
    String hora;
    public Tareas(){

    }
    public Tareas(String descripcion, String fecha, String hora, String titulo) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.titulo = titulo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
