package com.ufg.enviamensagem;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class GCM {
	
	public static void ativa(Context context) {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
			GCMRegistrar.register(context, Constantes.SENDER_ID);
			String id = GCMRegistrar.getRegistrationId(context);
			Log.i(Constantes.TAG, "Servi�o GCM ativado. ID " + id);
		} else {
			Log.i(Constantes.TAG, "O servi�o GCM j� est� ativo. ID: " + regId);
		}
	}

	public static void desativa(Context context) {
		GCMRegistrar.unregister(context);
		Log.i(Constantes.TAG, "Servi�o GCM desativado.");
	}

	public static boolean isAtivo(Context context) {
		return GCMRegistrar.isRegistered(context);
	}
}
