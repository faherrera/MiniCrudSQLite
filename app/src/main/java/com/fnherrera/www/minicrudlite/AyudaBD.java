package com.fnherrera.www.minicrudlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by fnhde on 31/1/2017.
 */

public class AyudaBD extends SQLiteOpenHelper{

    public static class DatosTabla implements BaseColumns {
        //Esquema BD
        public static final String NOMBRE_TABLA = "Directorio";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NOMBRES = "nombres";
        public static final String COLUMNA_TELEFONOS = "telefono";

        //Creacion de BD.
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String CREAR_TABLA_1 =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA+ " (" +
                        DatosTabla.COLUMNA_ID+ " INTEGER PRIMARY KEY," +
                        DatosTabla.COLUMNA_NOMBRES+ TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_TELEFONOS+ TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DatosTabla.NOMBRE_TABLA;

    }

    public  static final int DATABASE_VERSION = 1;
    public static  final String DATABASE_NAME  = "MiBaseDeDatos.db";

    public AyudaBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatosTabla.CREAR_TABLA_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatosTabla.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
