package com.ufg.enviamensagem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ufg.enviamensagem.R;
import com.ufg.enviamensagem.bean.Mensagem;
import com.ufg.enviamensagem.modelo.dao.MensagemDAO;

public class VisualizaMensagem extends Activity {
	
	TextView textViewMensagem;
	TextView textViewData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualiza_msg);
		
		Bundle receiveBundle = this.getIntent().getExtras();
		final long idMensagem = receiveBundle.getLong("idMensagem");
		
		MensagemDAO msgDao = new MensagemDAO(this);
		Mensagem consultada = msgDao.consultar(idMensagem);
		 
		textViewMensagem = (TextView)findViewById(R.id.textViewMensagem);
		textViewMensagem.setText(consultada.getCorpo());
		
		textViewData = (TextView)findViewById(R.id.textViewData);
		textViewData.setText(consultada.getDataFormatada());
	}
}
