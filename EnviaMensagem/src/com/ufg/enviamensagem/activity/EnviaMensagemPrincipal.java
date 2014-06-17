package com.ufg.enviamensagem.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ufg.enviamensagem.GCM;
import com.ufg.enviamensagem.R;
import com.ufg.enviamensagem.VisualizaMensagem;
import com.ufg.enviamensagem.bean.Mensagem;
import com.ufg.enviamensagem.modelo.dao.MensagemDAO;

public class EnviaMensagemPrincipal extends ActionBarActivity {

	ListView lvListagem;
	
	private List<Mensagem> msgs;
	private ArrayAdapter<Mensagem> adapter;
	private int adapterLayout = android.R.layout.simple_list_item_1;
	
	private static boolean primeiraExecucao = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_ativa_gcm);
		
		GCM.ativa(getApplicationContext());
		
		if (GCM.isAtivo(getApplicationContext())) {
			Toast.makeText(getApplicationContext(), "Recebimento de mensagens ativado!", Toast.LENGTH_LONG).show();
			
			carregarLista();
		}else{
			Toast.makeText(getApplicationContext(), "Recebimento de mensagens não ativo!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void carregarLista(){
		MensagemDAO msgDao = new MensagemDAO(this);
		msgs = msgDao.listar();
		msgDao.close();
		
		lvListagem = (ListView) findViewById(R.id.lvListagem);
		this.adapter = new ArrayAdapter<Mensagem>(this, adapterLayout, msgs);
		this.lvListagem.setAdapter(adapter);
		lvListagem.setOnItemClickListener(new OnItemClickListener(){
	        @Override
	        public void onItemClick(AdapterView<?> Parent, View view, int position,
	                long id) {
	        	
	        	Mensagem msgItem = (Mensagem)lvListagem.getItemAtPosition((int)id);
	        	
	        	Intent intent = new Intent(EnviaMensagemPrincipal.this, VisualizaMensagem.class);
	        	Bundle sendBundle = new Bundle();
	            sendBundle.putLong("idMensagem", msgItem.getId());
	            intent.putExtras(sendBundle);
	            
	        	EnviaMensagemPrincipal.this.startActivity(intent);
	        }});
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		setContentView(R.layout.activity_ativa_gcm);
		
		lvListagem = (ListView) findViewById(R.id.lvListagem);
		carregarLista();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		if(!primeiraExecucao){
			primeiraExecucao = false;
			finish();
		}
	}
}
