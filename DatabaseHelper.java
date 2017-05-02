package com.example.peva.obd2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Peva on 21/04/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbName = "Statistiche";
    static final String Tabella = "Tabella";
    static final String tabID = "tabID";
    static final String maxSpeed = "maxSpeed";

    public DatabaseHelper(Context context) {
        super(context, dbName, null,1);
    }

    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE `Tabella` (`tabID` INTEGER PRIMARY KEY AUTOINCREMENT,`maxSpeed` INTEGER);");
        db.execSQL("CREATE TABLE `Comando` (`ID_Comando` INTEGER PRIMARY KEY AUTOINCREMENT,`Nome` VARCHAR(15), `Descrizione` VARCHAR(90));");
        db.execSQL("CREATE TABLE `Valore` (ID_Valore INTEGER PRIMARY KEY AUTOINCREMENT,\n " +
        "\t`Nome`\tvarchar(15),\n" +
                "\t`ID_Lettura`\tINTEGER,\n" +
                "\t`Valore`\tINTEGER,\n" +
            //    "\tPRIMARY KEY(ID_Valore),\n" +
                "\tFOREIGN KEY(`Nome`) REFERENCES Comando('Nome'),\n" +
                "\tFOREIGN KEY(`ID_Lettura`) REFERENCES Lettura('ID_Lettura')\n" +
                ");");
        db.execSQL("CREATE TABLE `Lettura` (`ID_Lettura` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "`DataOra` DATETIME );");  //DATETIME - format: YYYY-MM-DD HH:MI:SS

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS "+Tabella);
        onCreate(db);
    }
    public void updateSpeed(String speed){

        SQLiteDatabase db = this.getWritableDatabase();
       //// ContentValues  cv=new ContentValues();
       // cv.put(maxSpeed, speed);
        // updating row
        db.execSQL("UPDATE Tabella SET maxSpeed="+speed +" WHERE tabID='1';");
    }
    public int selectSpeed()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        int lol=0;
        Cursor cur=db.rawQuery("SELECT "+maxSpeed+ " from "+Tabella,new String [] {});
        if (cur.moveToFirst()){
            do{
                lol = cur.getInt(cur.getColumnIndex("maxSpeed"));
                // do what ever you want here
            }while(cur.moveToNext());
        }
        cur.close();
       // db.execSQL("UPDATE Tabella SET maxSpeed="+speed +" WHERE tabID='1';");


        return lol;
    }
    public String[] lol2(){
        String lol[]={"","",""};
        String Query ="SELECT * FROM Valore";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                lol[0]=cursor.getString(cursor.getColumnIndex("ID_Comando"));
                lol[1]=cursor.getString(cursor.getColumnIndex("ID_Lettura"));
                lol[2]=cursor.getString(cursor.getColumnIndex("Valore"));
break;
            } while (cursor.moveToNext());
        }
        return lol;
    }
    public void InsertGiri(String Value,int Y,int M,int D,int H,int Min,int S){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO 'Comando' (Nome,Descrizione) VALUES('giri','i giri motore al minuto');");
        db.execSQL("INSERT INTO 'Lettura'(DataOra) VALUES ('"+Y+"-"+M+"-"+D+" "+H+":"+Min+":"+S+"');");
        db.execSQL("INSERT INTO 'Valore' (Valore,Nome,ID_Lettura) " + "VALUES("+Value+",'giri',(select max(ID_Lettura) from Lettura));");
        //SELECT ID_Comando from Comando WHERE ID_Valore=ID_Comando
    }
    public void InsertTempMotore(String Value,int Y,int M,int D,int H,int Min,int S){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO 'Comando' (Nome,Descrizione) VALUES('engineTemp','Temperatura del motore');");
        db.execSQL("INSERT INTO 'Lettura'(DataOra) VALUES ('"+Y+"-"+M+"-"+D+" "+H+":"+Min+":"+S+"');");
        db.execSQL("INSERT INTO 'Valore' (Valore,Nome,ID_Lettura) " + "VALUES("+Value+",'engineTemp',(select max(ID_Lettura) from Lettura));");
    }
    public void InsertFarfalle(String Value,int Y,int M,int D,int H,int Min,int S){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO 'Comando' (Nome,Descrizione) VALUES('throttle','Posizione della valvola a farfalla');");
        db.execSQL("INSERT INTO 'Lettura' (DataOra) VALUES ('"+Y+"-"+M+"-"+D+" "+H+":"+Min+":"+S+"');");
        db.execSQL("INSERT INTO 'Valore' (Valore,Nome,ID_Lettura) " + "VALUES("+Value+",'throttle',(select max(ID_Lettura) from Lettura));");
    }
    public void InsertSpeed(String Value,int Y,int M,int D,int H,int Min,int S){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO 'Comando' (Nome,Descrizione) VALUES('speed','Velocità in Km/h');");
        db.execSQL("INSERT INTO 'Lettura' (DataOra) VALUES ('"+Y+"-"+M+"-"+D+" "+H+":"+Min+":"+S+"');");
        db.execSQL("INSERT INTO 'Valore' (Valore,Nome,ID_Lettura) " + "VALUES("+Value+",'speed',(select max(ID_Lettura) from Lettura));");
    }

    public String[] selectThrottleGraph(){
        List<String> noIndex = new ArrayList<String>();
        int i=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String Query = "SELECT Valore FROM Valore  " +
                "where Nome='throttle'";
        cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.moveToFirst()) {
            for(i=0;i<cursor.getCount();i++) {

                //[y]
                    if(i<cursor.getCount()) {
if(cursor != null && cursor.getString(cursor.getColumnIndex("Valore")) != null) {

    noIndex.add( cursor.getString(cursor.getColumnIndex("Valore")));
}
                    cursor.moveToNext();
                }
            }

        }
        String Vet[]=new String[noIndex.size()];
        for(i=0;i<noIndex.size();i++)
        Vet[i]=noIndex.get(i);
        return Vet;
    }

    public String[] selectRPMGraph(){
        List<String> noIndex = new ArrayList<String>();
        int i=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String Query = "SELECT Valore FROM Valore  " +
                "where Nome='giri'";
        cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.moveToFirst()) {
            for(i=0;i<cursor.getCount();i++) {

                //[y]
                if(i<cursor.getCount()) {
                    if(cursor != null && cursor.getString(cursor.getColumnIndex("Valore")) != null) {

                        noIndex.add( cursor.getString(cursor.getColumnIndex("Valore")));
                    }
                    cursor.moveToNext();
                }
            }

        }
        String Vet[]=new String[noIndex.size()];
        for(i=0;i<noIndex.size();i++)
            Vet[i]=noIndex.get(i);
        return Vet;
    }
    public String[] selectCoolantGraph(){
        List<String> noIndex = new ArrayList<String>();
        int i=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String Query = "SELECT Valore FROM Valore  " +
                "where Nome='engineTemp'";
        cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.moveToFirst()) {
            for(i=0;i<cursor.getCount();i++) {

                //[y]
                if(i<cursor.getCount()) {
                    if(cursor != null && cursor.getString(cursor.getColumnIndex("Valore")) != null) {

                        noIndex.add( cursor.getString(cursor.getColumnIndex("Valore")));
                    }
                    cursor.moveToNext();
                }
            }

        }
        String Vet[]=new String[noIndex.size()];
        for(i=0;i<noIndex.size();i++)
            Vet[i]=noIndex.get(i);
        return Vet;
    }
    public String[] selectSpeedGraph(){
        List<String> noIndex = new ArrayList<String>();
        int i=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String Query = "SELECT Valore FROM Valore  " +
                "where Nome='speed'";
        cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.moveToFirst()) {
            for(i=0;i<cursor.getCount();i++) {

                //[y]
                if(i<cursor.getCount()) {
                    if(cursor != null && cursor.getString(cursor.getColumnIndex("Valore")) != null) {

                        noIndex.add( cursor.getString(cursor.getColumnIndex("Valore")));
                    }
                    cursor.moveToNext();
                }
            }

        }
        String Vet[]=new String[noIndex.size()];
        for(i=0;i<noIndex.size();i++)
            Vet[i]=noIndex.get(i);
        return Vet;
    }
    public String[] selectAll(){
        List<String> noIndex = new ArrayList<String>();
        int i=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String Query = "SELECT Valore FROM Valore  " ;
        cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.moveToFirst()) {
            for(i=0;i<cursor.getCount();i++) {

                //[y]
                if(i<cursor.getCount()) {
                    if(cursor != null && cursor.getString(cursor.getColumnIndex("Valore")) != null) {

                        noIndex.add( cursor.getString(cursor.getColumnIndex("Valore")));
                    }
                    cursor.moveToNext();
                }
            }

        }
        String Vet[]=new String[noIndex.size()];
        for(i=0;i<noIndex.size();i++)
            Vet[i]=noIndex.get(i);
        return Vet;
    }

    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Tabella");
        db.execSQL("delete from Comando");
        db.execSQL("delete from Valore");
        db.execSQL("delete from Lettura");
    }
}