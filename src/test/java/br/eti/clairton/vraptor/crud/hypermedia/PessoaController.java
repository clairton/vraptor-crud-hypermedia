package br.eti.clairton.vraptor.crud.hypermedia;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithRoot;
import br.com.caelum.vraptor.view.Results;

@Controller
public class PessoaController {

	private final Result result;

	private static final Map<Integer, Pessoa> pessoas = new HashMap<Integer, Pessoa>() {
		private static final long serialVersionUID = 1L;
		{
			put(1, new Pessoa());
		}
	};

	@Deprecated
	protected PessoaController() {
		this(null);
	}

	@Inject
	public PessoaController(Result result) {
		super();
		this.result = result;
	}

	/**
	 * Mostra os recursos. Parametros para filtagem são mandados na URL.
	 */
	@Get("pessoa")
	public void index() {
		result.use(Results.json()).from(pessoas, "pessoas").serialize();
	}

	@Get("pessoa/{id}")
	public void show(final Integer id) {
		result.use(Results.json()).from(new Pessoa()).serialize();
	}

	@Put("pessoa/{id}")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void update(final Integer id, final Pessoa pessoa) {
		pessoas.put(id, pessoa);
		result.use(Results.json()).from(pessoa).serialize();
	}

	@Post("pessoa")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void create(final Pessoa pessoa) {
		final Integer id = Long.valueOf(new Date().getTime()).intValue();
		pessoa.setId(id);
		pessoas.put(id, pessoa);
		result.use(Results.json()).from(pessoa).serialize();
	}
}
