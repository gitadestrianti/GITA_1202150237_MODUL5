package com.example.android.MODUL5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database dtBase;
    private RecyclerView rcView;
    private Adapter adapter;
    private ArrayList<AddData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("To Do List");  //Titlenya To Do List

        rcView = findViewById(R.id.rec_view); //mengakses recyclerview yang idnya rec_view

        data_list = new ArrayList<>();    //Array list baru
        dtBase = new Database(this); //database baru
        dtBase.readdata(data_list);

        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);


        adapter = new Adapter(this,data_list, color); //adapter baru

        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        rcView.setAdapter(adapter); //inisiasi adapter untuk recycler view
        hapusdata(); //method hapusdata dijalankan
    }

    //Untuk menghapus item yang telah dibuat di to do list
    public void hapusdata(){
        //Touch helper baru
        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);

                //Apabila item digeser ke kiri
                if(direction==ItemTouchHelper.LEFT){

                    if(dtBase.removedata(current.getTodo())){ //Remove item yang dipilih
                        adapter.deleteData(position);
                        Snackbar.make(findViewById(R.id.coordinator), "List has been deleted", 2000).show(); //membuat snack bar
                        //pemberitahuan bahwa item telah dihapus. waktunya 2S
                    }
                }
            }
        };

        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rcView);
    }

    //Waktu menu pada activity di buat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Apabila item dipiih, method ini dijalankan
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId(); //mendapatkan id dari item yang dipilih

        //Apabila yang dipilih adalah menu settings
        if (id==R.id.action_settings){

            Intent intent = new Intent(MainActivity.this, Settings.class); //Intent ke activity settings
            startActivity(intent);
            finish();
        }
        return true;
    }

    //ketika button add di klik, method dijalankan
    public void addlist(View view) {
        Intent intent = new Intent(MainActivity.this, AddToDo.class);
        startActivity(intent);
    }
}
