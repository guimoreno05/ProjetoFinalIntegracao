package com.ufg.enviamensagem;

import java.io.UnsupportedEncodingException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.ufg.enviamensagem.R;
import com.ufg.enviamensagem.activity.*;
import com.ufg.enviamensagem.bean.Mensagem;
import com.ufg.enviamensagem.modelo.dao.MensagemDAO;

public class GCMIntentService extends GCMBaseIntentService {
	@Override
	protected void onRegistered(Context context, String regId) {
		Log.i(Constantes.TAG, "GCM ativado.");
		String mensagem = "ID de registro no GCM: " + regId;
		Log.i(Constantes.TAG, mensagem);
	}
	@Override
	protected void onError(Context context, String errorMessage) {
		Log.e(Constantes.TAG, "Erro: " + errorMessage);
	}
	
	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(Constantes.TAG, "GCM Desativado.");
	}
	
	@Override
	protected void onMessage(Context context, Intent intent) {
		String mensagem = intent.getExtras().getString("mensagem");
		try {
			 if(mensagem.getBytes("UTF-8").length/2048 <= 2){
				 Log.i(Constantes.TAG, "Mensagem recebida: " + mensagem);	
					if (mensagem != null && !"".equals(mensagem)){
						MensagemDAO msgDao = new MensagemDAO(context);
						msgDao.cadastrar(new Mensagem(mensagem));
						mostraNotificacao("Nova mensagem recebida", mensagem, context);
					}
			 	}
			}catch (UnsupportedEncodingException e) {
 		}
  	}
	
	public void mostraNotificacao(String titulo, String mensagem, Context context) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(titulo)
		        .setContentText(mensagem);
		
		Intent resultIntent = new Intent(context, EnviaMensagemPrincipal.class);
		resultIntent.putExtra("mensagem_recebida", mensagem);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(EnviaMensagemPrincipal.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		Notification notification = mBuilder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_ALL;
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

	
}