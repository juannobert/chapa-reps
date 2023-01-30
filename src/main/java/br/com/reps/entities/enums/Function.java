package br.com.reps.entities.enums;

public enum Function {
	ESTUDANTE("Estudante"),
	PROFESSOR("Professor"),
	SERVIDOR_ADMINISTRATIVO("Servidor Administrativo"),
	OUTRO("Outro");

	private String nome;
	
	private Function(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
