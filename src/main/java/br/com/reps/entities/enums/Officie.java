package br.com.reps.entities.enums;

public enum Officie {

	PRESIDENTE("Presidente"),
	VICE_PRESIDENTE("Vice-Presidente"),
	SECRETARIA("Secretaria"),
	TESOURARIA("Tesouraria"),
	POLITICA_ESTUDANTIL("Politica estudantil e assistência ao aluno"),
	INTEGRACAO_E_MOBILIZACAO_ESTUDANTIL("Integração e mobilização estudantil"),
	COMUNICACAO_SOCIAL_E_IMPRENSA("Comunicação e imprensa"),
	CULTURA_E_ARTE("Cultura, artes e relações socioambientais"),
	ESPORTES_E_LAZER("Esportes e lazer");
	
	private String nome;
	
	private Officie(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
