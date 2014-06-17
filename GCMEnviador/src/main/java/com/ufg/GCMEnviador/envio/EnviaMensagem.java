package com.ufg.GCMEnviador.envio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class EnviaMensagem {

	public static void main(String[] args) {

		System.out.println("Local do Arquivo de Configuração:");

		Scanner scanner = new Scanner(System.in);
		String caminho = scanner.nextLine();
		List<String> listaDispositivos = new ArrayList<String>();

		String apiKey = "";
		try {
			FileReader arquivo = new FileReader(caminho);
			BufferedReader lerArquivo = new BufferedReader(arquivo);
			apiKey = lerArquivo.readLine();
			System.out.println("APIKEY: " + apiKey);
			String idDispositivo = lerArquivo.readLine();
			while(idDispositivo != null && !idDispositivo.equals("")){
				listaDispositivos.add(idDispositivo);
				idDispositivo = lerArquivo.readLine();
			}
			
			lerArquivo.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		boolean continuar = true;
		while(continuar){
			System.out.println("Escolha:");
			System.out.println("1 - Enviar Mensagem");
			System.out.println("2 - Sair");
			
			String valorMenu = scanner.nextLine();
			
			if(valorMenu.equals("2"))
				continuar = false;
			else if(valorMenu.equals("1")){
			
				System.out.println("Mensagem a enviar:");
				String msg = scanner.nextLine();
				
				if(msg != null && !msg.equals("")){
					Sender sender = new Sender(apiKey);
					Message message = new Message.Builder().collapseKey("1").timeToLive(3)
							.delayWhileIdle(true).addData("mensagem", msg).build();
		
					Result result = null;
		
					try {
						for(String idDispositivo : listaDispositivos){
							result = sender.send(message, idDispositivo, 1);
							if (result != null) {
								System.out.println("Mensagem enviada!");
							}
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
