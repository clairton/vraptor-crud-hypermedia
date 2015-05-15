package br.eti.clairton.vraptor.crud.hypermedia;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.eti.clairton.repository.Model;

/**
 * Representa uma Aplicação.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Entity
public class Aplicacao extends Model {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 250)
	private String nome;

	/**
	 * Construtor padrão.
	 */
	public Aplicacao() {
	}

	public Aplicacao(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
