package br.com.reps.entities.enums;

public enum PostType {
	NOTICE("Central de avisos"),
	TRANSPARENCY("Central da transparência");
	
	private String nome;
	
	private PostType(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
