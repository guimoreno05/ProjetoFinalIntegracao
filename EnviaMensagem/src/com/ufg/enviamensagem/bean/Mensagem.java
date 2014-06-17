package com.ufg.enviamensagem.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mensagem {

	Long id;
	String corpo;
	Long timeDataRecebimento;
	
	public Mensagem(){}
	
	public Mensagem(String corpo){
		this.corpo = corpo;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public Long getTimeDataRecebimento() {
		return timeDataRecebimento;
	}
	public void setTimeDataRecebimento(Long timeDataRecebimento) {
		this.timeDataRecebimento = timeDataRecebimento;
	}
	
	public String getDataFormatada(){
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date data = new Date(timeDataRecebimento);
		
		return dataFormatada.format(data);
	}
	@Override
	public String toString() {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		Date data = new Date(timeDataRecebimento);
		
		String corpoReduzido = corpo.length() > 100 ? corpo.substring(0, 99) + "..." : corpo;
		
		return dataFormatada.format(data) + " - " + corpoReduzido;
	}
}
