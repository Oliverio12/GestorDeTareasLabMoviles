package com.android.labmovilesg4;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar;
    public static List<Tareas> lstTareas = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final int REQUEST_CODE_EDIT_TASK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgregar = findViewById(R.id.btnAgregar);
        recyclerView = findViewById(R.id.recycleview);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(lstTareas, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tareas tarea, int position) {
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                intent.putExtra("titulo", tarea.getTitulo());
                intent.putExtra("descripcion", tarea.getDescripcion());
                intent.putExtra("fecha", tarea.getFecha());
                intent.putExtra("hora", tarea.getHora());
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_EDIT_TASK);
            }
        });
        recyclerView.setAdapter(myAdapter);

        // Aquí agregamos el ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                lstTareas.remove(position);

                myAdapter.notifyItemRemoved(position);

                Toast.makeText(MainActivity.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    RectF background = new RectF(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    c.drawRect(background, paint);

                    paint.setColor(Color.WHITE);
                    paint.setTextSize(48);
                    paint.setTextAlign(Paint.Align.CENTER);
                    float textY = itemView.getTop() + ((itemView.getHeight() + paint.getTextSize()) / 2) - 10;
                    c.drawText("Eliminando", background.centerX(), textY, paint);

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_TASK && resultCode == RESULT_OK && data != null) {
            String nuevoTitulo = data.getStringExtra("titulo");
            String nuevaDescripcion = data.getStringExtra("descripcion");
            String nuevaFecha = data.getStringExtra("fecha");
            String nuevaHora = data.getStringExtra("hora");
            int position = data.getIntExtra("position", -1);

            if (position != -1) {
                Tareas tarea = lstTareas.get(position);
                tarea.setTitulo(nuevoTitulo);
                tarea.setDescripcion(nuevaDescripcion);
                tarea.setFecha(nuevaFecha);
                tarea.setHora(nuevaHora);

                myAdapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.notifyDataSetChanged();
    }
}
