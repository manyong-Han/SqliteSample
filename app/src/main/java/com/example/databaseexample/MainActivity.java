package com.example.databaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;
    EditText edit_name, edit_tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        try{
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e){
            db = dbHelper.getReadableDatabase();
        }
        edit_name = (EditText) findViewById(R.id.id_edit_text);
        edit_tel = (EditText) findViewById(R.id.tel_edit_text);
    }

    public void insert(View view) {
        String name = edit_name.getText().toString();
        String tel = edit_tel.getText().toString();

        db.execSQL("INSERT INTO contacts VALUES (NULL,'" + name + "','" + tel + "');");
        Toast.makeText(MainActivity.this, "추가 성공", Toast.LENGTH_SHORT).show();

        edit_name.setText("");
        edit_tel.setText("");
    }

    public void search(View view){
        String name = edit_name.getText().toString();
        Cursor cursor;
        cursor = db.rawQuery("SELECT name, tel FROM contacts WHERE name= '" + name + "';",null);

        while (cursor.moveToNext()){
            String tel = cursor.getString(1);
            edit_tel.setText(tel);
        }
    }
}
