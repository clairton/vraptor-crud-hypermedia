package br.eti.clairton.vraptor.crud.hypermedia;

import br.eti.clairton.repository.Model;

public class Pessoa extends Model {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;

	public Pessoa() {
		this(1, "Maria");
	}

	public Pessoa(final Integer id, final String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	@Override
	public Long getId() {
		return Long.valueOf(id);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}