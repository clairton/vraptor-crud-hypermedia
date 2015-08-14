package br.eti.clairton.vraptor.crud.hypermedia;

import br.eti.clairton.gson.hypermedia.Hypermediable;
import br.eti.clairton.vraptor.crud.serializer.Resourceable;

public interface HypermediableResourceable<T> extends Resourceable<T>, Hypermediable<T> {

}
