package com.example.android.MODUL5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDo extends AppCompatActivity {
    private EditText ToDo, Description, Priority;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        setTitle("Add To Do"); //Titlenya menjadi Add To Do

        ToDo = (EditText) findViewById(R.id.edt_Todo);
        Description = (EditText) findViewById(R.id.edt_Desc);
        Priority = (EditText) findViewById(R.id.edt_Priority);
        db = new Database(this);
    }

    //Apabil back di klik
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AddToDo.this, MainActivity.class); //Inent ke MainActivity
        startActivity(intent);
        this.finish();
    }

    //Apabila to do, description, dan priority diisi(atau tidak diisi) maka akan keluar toast seperti di bawah dan akan dipindahkan ke MainActivity
    public void addTodo(View view) {
        if (db.inputdata(new AddData(ToDo.getText().toString(), Description.getText().toString(), Priority.getText().toString()))){

            Toast.makeText(this, "To Do List Added!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddToDo.this, MainActivity.class));
            this.finish();

        }else {

            Toast.makeText(this, "List can't be empty", Toast.LENGTH_SHORT).show();
            ToDo.setText(null); //set semua edit text menjadi kosong
            Description.setText(null);
            Priority.setText(null);
        }
    }

}
