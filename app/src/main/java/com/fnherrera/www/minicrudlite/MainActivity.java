package com.fnherrera.www.minicrudlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLInput;

public class MainActivity extends AppCompatActivity {
    Button v_btn_guardar,v_btn_borrar,v_btn_update,v_btn_buscar;
    EditText v_et_id,v_et_nombre,v_et_telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v_btn_guardar = (Button)findViewById(R.id.btn_guardar);
        v_btn_borrar = (Button)findViewById(R.id.btn_borrar);
        v_btn_buscar = (Button)findViewById(R.id.btn_buscar);
        v_btn_update = (Button)findViewById(R.id.btn_update);

        v_et_id = (EditText)findViewById(R.id.et_id);
        v_et_nombre  = (EditText)findViewById(R.id.et_nombre);
        v_et_telefono = (EditText)findViewById(R.id.et_telefono);


        final AyudaBD ayudabd = new AyudaBD(getApplicationContext());

        //Insertar datos
        v_btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = ayudabd.getWritableDatabase();

                ContentValues valores = new ContentValues();
                valores.put(AyudaBD.DatosTabla.COLUMNA_ID,v_et_id.getText().toString());
                valores.put(AyudaBD.DatosTabla.COLUMNA_NOMBRES,v_et_nombre.getText().toString());
                valores.put(AyudaBD.DatosTabla.COLUMNA_TELEFONOS,v_et_telefono.getText().toString());

                Long IdGuardado = db.insert(AyudaBD.DatosTabla.NOMBRE_TABLA, AyudaBD.DatosTabla.COLUMNA_ID,valores);

                Toast.makeText(getApplicationContext(),"Se guardo correctamente el dato: "+ IdGuardado,Toast.LENGTH_LONG).show();

            }
        });

        v_btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = ayudabd.getWritableDatabase();

                String selection = AyudaBD.DatosTabla.COLUMNA_ID + "=?";

                String[] selectionArgs = {v_et_id.getText().toString()};
                try {
                    db.delete(AyudaBD.DatosTabla.NOMBRE_TABLA,selection,selectionArgs);

                    v_et_nombre.setText(null);
                    v_et_telefono.setText(null);
                    v_et_id.setText(null);
                    Toast.makeText(getApplicationContext(),"Se eliminó correctamente el registro",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Ocurrio un error, por favor intenta con otro ID",Toast.LENGTH_LONG).show();

                }

            }
        });

        v_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = ayudabd.getReadableDatabase();

                ContentValues valores = new ContentValues();

                valores.put (AyudaBD.DatosTabla.COLUMNA_NOMBRES,v_et_nombre.getText().toString());
                valores.put(AyudaBD.DatosTabla.COLUMNA_TELEFONOS,v_et_telefono.getText().toString());

                String selection = AyudaBD.DatosTabla.COLUMNA_ID + "=?";
                String[] selectionArgs = {v_et_id.getText().toString()};

                int count = db.update(
                        AyudaBD.DatosTabla.NOMBRE_TABLA,
                        valores,
                        selection,
                        selectionArgs
                );

                Toast.makeText(getApplicationContext(),"El registro con el id n° "+v_et_id.getText().toString() + " fue correctamente actualizado",Toast.LENGTH_LONG).show();
            }
        });

        v_btn_buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db = ayudabd.getReadableDatabase();
                try {
                    //Definiendo la projection


                    //Filtrando los resultados

                    // String selection = AyudaBD.DatosTabla.COLUMNA_ID + "=?";   //Donde seleccionaré segun lo que busque
                    String[] selectionArgs = {v_et_id.getText().toString()};    //Que valor busco

                    String[] projection = {
                            AyudaBD.DatosTabla.COLUMNA_NOMBRES,
                            AyudaBD.DatosTabla.COLUMNA_TELEFONOS
                    };

                    String sortOrder = null;

                    Cursor c = db.query(
                            AyudaBD.DatosTabla.NOMBRE_TABLA,
                            projection,
                            AyudaBD.DatosTabla.COLUMNA_ID + "=?",
                            selectionArgs,
                            null,
                            null,
                            null
                    );

                    c.moveToFirst();


                    v_et_nombre.setText(c.getString(0));
                    v_et_telefono.setText(c.getString(1));

                }catch (Exception e ){
                    Toast.makeText(getApplicationContext(),"Ocurrio un error, por favor pruebe con otro ID",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
