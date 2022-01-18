package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTeste {

	private LocacaoService teste = new LocacaoService();

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		teste = new LocacaoService();
	}

	@Test
	public void testLocacao() throws Exception {

		// cenario
		Usuario user = new Usuario("Maria");
		List<Filme> filme =  Arrays.asList(new Filme("Senhor dos Aneis", 2, 15.0));
		

		// acao
		Locacao acao;
		acao = teste.alugarFilme(user, filme);

		// verificacao usando metodo error. No caso anterior no primeiro erro para, este
		// rastreia todos os error
		error.checkThat(acao.getValor(), is(15.0));
		error.checkThat(isMesmaData(acao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(acao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}

	// Como a excecao é esperada não da erro. Ela n é capaz de verificar a msg de
	// erro, mas é a mais facil de fazer
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacaoElegante() throws Exception {
		// cenario
		Usuario user = new Usuario("Maria");
		List<Filme> filme =  Arrays.asList(new Filme("Senhor dos Aneis", 0, 15.0));
		
		// acao
		teste.alugarFilme(user, filme);
	}

	@Test
	public void testeLocacaoVazioRobusta() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filme =  Arrays.asList(new Filme("Senhor dos Aneis", 2, 15.0));
		
		// acao
		try {
			teste.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
	}
}

	@Test
	public void testFilmeVazioNova() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario user = new Usuario("Maria");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// acao
		teste.alugarFilme(user, null);
	}

	// tenho todo o controle, n da erro pois a exception foi capiturada
	/*
	 * @Test public void testLocacaoRobusta(){ //cenario Usuario user = new
	 * Usuario("Maria"); Filme filme = new Filme("Senhor dos Aneis", 0, 15.0);
	 * LocacaoService teste = new LocacaoService();
	 * 
	 * //acao try { teste.alugarFilme(user, filme); } catch (Exception e) {
	 * assertThat(e.getMessage(), is("Filme sem estoque")); } }
	 */
	/*
	 * @Test public void testLocacaoNova() throws Exception { //cenario
	 * LocacaoService teste = new LocacaoService(); Usuario user = new
	 * Usuario("Maria"); Filme filme = new Filme("Senhor dos Aneis", 0, 15.0);
	 * 
	 * exception.expect(Exception.class);
	 * exception.expectMessage("Filme sem estoque");
	 * 
	 * //acao teste.alugarFilme(user, filme); }
	 */
}