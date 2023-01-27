package br.com.reps.entities.enums;

public enum Officie {

	PRESIDENTE("Presidente"),
	VICE_PRESIDENTE("Vice-Presidente"),
	SECRETARIA("Secretaria"),
	TESOURARIA("Tesouraria"),
	POLITICA_ESTUDANTIL("Diretoria de Politica estudantil e assistência ao aluno"),
	INTEGRACAO_E_MOBILIZACAO_ESTUDANTIL("Diretoria de Integração e mobilização estudantil"),
	COMUNICACAO_SOCIAL_E_IMPRENSA("Diretoria de Comunicação e imprensa"),
	CULTURA_E_ARTE("Diretoria de Cultura, artes e relações socioambientais"),
	ESPORTES_E_LAZER("Diretoria de Esportes e lazer");
	
	private String nome;
	
	private Officie(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
