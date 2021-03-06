package br.eti.clairton.vraptor.crud.hypermedia;

import javax.inject.Inject;
import javax.servlet.ServletRequest;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Language;
import br.eti.clairton.repository.Repository;
import br.eti.clairton.repository.http.QueryParser;
import br.eti.clairton.vraptor.crud.controller.CrudController;
import br.eti.clairton.vraptor.crud.model.Aplicacao;

@Controller
public class AplicacaoController extends CrudController<Aplicacao> {
	@Deprecated
	protected AplicacaoController() {
		this(null, null, null, null, null);
	}

	@Inject
	public AplicacaoController(final Repository repository,
			final Result result, @Language final Inflector inflector,
			final ServletRequest request, final QueryParser queryParser) {
		super(Aplicacao.class, repository, result, inflector, request,
				queryParser);
	}
}