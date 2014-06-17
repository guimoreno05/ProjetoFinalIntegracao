package com.ufg.enviamensagem.modelo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ufg.enviamensagem.bean.Mensagem;

public class MensagemDAO extends SQLiteOpenHelper{
	
	private static final int VERSAO = 1;
	private static final String TABELA = "Mensagem";
	private static final String DATABASE = "EnviaMensagem";
	
	private static final String TAG = "CADASTRO_MENSAGENS";
	
	public MensagemDAO(Context context){
		super(context, DATABASE, null, VERSAO);
	}
			
	public MensagemDAO(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE " + TABELA + "( id INTEGER PRIMARY KEY, corpo TEXT, dataRecebimento NUMERIC)";
		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public List<Mensagem> listar(){
		
		List<Mensagem> lista = new ArrayList<Mensagem>();
		
		String sql = "Select * from " + TABELA + " order by dataRecebimento ";
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Mensagem msg = new Mensagem();
				
				msg.setId(cursor.getLong(0));
				msg.setCorpo(cursor.getString(1));
				msg.setTimeDataRecebimento(cursor.getLong(2));
				
				lista.add(msg);
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		Log.i(TAG, "Lista Mensagens");
		return lista;
	}
	
	public Mensagem consultar(long id){
		
		String sql = "Select * from " + TABELA + " where id = " + id;
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Mensagem msg = new Mensagem();
				
				msg.setId(cursor.getLong(0));
				msg.setCorpo(cursor.getString(1));
				msg.setTimeDataRecebimento(cursor.getLong(2));
				
				return msg;
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		return null;
	}
	
	public void cadastrar(Mensagem msg){
		msg.setTimeDataRecebimento(new Date().getTime());
		
		ContentValues values = new ContentValues();
		
		values.put("corpo", msg.getCorpo());
		values.put("dataRecebimento", msg.getTimeDataRecebimento());
		
		getWritableDatabase().insert(TABELA, null, values);
		Log.i(TAG, "Mensagem Armazenada: " + msg.getCorpo());
	}
}
