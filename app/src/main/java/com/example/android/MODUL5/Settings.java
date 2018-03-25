package com.example.android.MODUL5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    private TextView warna;
    int colorid;
    AlertDialog.Builder alert;
    SharedPreferences.Editor sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");


        alert = new AlertDialog.Builder(this); //alert dialog baru

        //inisiasi shared preference
        SharedPreferences sharedP = getApplicationContext().getSharedPreferences("Preferences", 0);
        sharedpref = sharedP.edit();
        colorid = sharedP.getInt("Colourground", R.color.white);
        warna = findViewById(R.id.shapecolor);    //mengakses text view pada layout
        warna.setText(getShapeColor(colorid)); //set color dengan color id yang terpilih
    }

    //Apabila button back di klik
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Settings.this, MainActivity.class); //intent baru dari settings ke MainActivity
        startActivity(intent);
        finish();
    }

    //ketika menu di klik, method dijalankan
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.onBackPressed(); //menjalankan method onbackpressed
        }
        return true;
    }

    //String warna untuk warna yang dapat digunakan di settings
    public String getShapeColor(int i){
        if (i==R.color.red){
            return "Red";
        }else if (i==R.color.green){
            return "Green";
        }else if (i==R.color.blue){
            return "Blue";
        }else{
            return "Default";
        }
    }

    //id dari warna yang akan digunakan
    public int getColorid(int i){
        if (i==R.color.red){
            return R.id.red;
        }else if (i==R.color.green){
            return R.id.green;
        }else if (i==R.color.blue){
            return R.id.blue;
        }else{
            return R.id.white;
        }
    }

    public void pilihWarna(View view) {

        alert.setTitle("Shape Color"); //set title alert dialog menjadi Shape Color
        View view1 = getLayoutInflater().inflate(R.layout.setting_color, null); //membuat view baru
        alert.setView(view1); //menampilkan view yang telah dibuat

        //akses radiogroup apda layout
        final RadioGroup radG = view1.findViewById(R.id.radio_color);
        radG.check(getColorid(colorid));


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {//ketika klik Ok pada alert dialog
                //mendapatkan id radio button yang di pilih
                int a = radG.getCheckedRadioButtonId();
                switch (a){
                    case R.id.red:
                        colorid = R.color.red;
                        break;
                    case R.id.green:
                        colorid = R.color.green;
                        break;
                    case R.id.blue:
                        colorid = R.color.blue;
                        break;
                    case R.id.white:
                        colorid = R.color.white;
                        break;
                }

                warna.setText(getShapeColor(colorid)); //set shape color menjadi color id yang dipilih
                sharedpref.putInt("Colourground", colorid);
                sharedpref.commit();
            }
        });

        //ketika cancel diklik pada alertdialog
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.create().show();  //buat dan tampilkan alert dialog
    }


}