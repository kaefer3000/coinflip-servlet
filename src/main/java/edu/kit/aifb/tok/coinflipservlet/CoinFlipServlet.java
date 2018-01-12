package edu.kit.aifb.tok.coinflipservlet;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;

@Path("")
public class CoinFlipServlet {

	private static final String VOCAB_BASE_URI_STRING = "https://kaefer3000.github.io/coinflip-servlet/vocab#";

	private static final Resource COINFLIP = new Resource(VOCAB_BASE_URI_STRING + "Coin");
	private static final Resource RESULT = new Resource(VOCAB_BASE_URI_STRING + "shows");
	private static final Resource HEADS = new Resource(VOCAB_BASE_URI_STRING + "Heads");
	private static final Resource TAILS = new Resource(VOCAB_BASE_URI_STRING + "Tails");

	@GET
	public Response getTime(@Context UriInfo uriinfo) {

		Resource nir = new Resource(uriinfo.getAbsolutePath().toString() + "#it");

		Node[][] triples = new Node[][] { new Node[] { nir, RDF.TYPE, COINFLIP },
				new Node[] { nir, RESULT, Math.random() < 0.5 ? HEADS : TAILS } };

		return Response.ok(new GenericEntity<Iterable<Node[]>>(Arrays.asList(triples)) {
		}).build();

	}
}
