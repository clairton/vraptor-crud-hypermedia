package br.eti.clairton.vraptor.crud.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

	@NotNull
	@OneToMany(mappedBy = "aplicacao", cascade = CascadeType.ALL)
	private Collection<Recurso> recursos = new HashSet<Recurso>();

	/**
	 * Construtor padrão.
	 */
	public Aplicacao() {
	}

	public Aplicacao(final String nome) {
		super();
		this.nome = nome;
	}

	public Aplicacao(final String nome, final Recurso recurso) {
		this(nome);
		this.recursos.add(recurso);
	}

	public String getNome() {
		return nome;
	}
}
