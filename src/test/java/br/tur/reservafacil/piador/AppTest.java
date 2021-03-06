package br.tur.reservafacil.piador;

import br.tur.reservafacil.piador.domain.PostagemDomain;
import br.tur.reservafacil.piador.domain.PostagemDomainImpl;
import br.tur.reservafacil.piador.pio.Pio;
import br.tur.reservafacil.piador.pio.PioRepository;
import br.tur.reservafacil.piador.pio.PioRepositoryDefaultImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class AppTest {

    public static final String USUARIO_PRE_DEFINIDO = "@danielle.miranda";

    private PostagemDomain domain;
    private PioRepository  pioRepository;

    @Before
    public void init() {
        pioRepository = new PioRepositoryDefaultImpl();
        ((PioRepositoryDefaultImpl) pioRepository).initRepos();
	this.domain = new PostagemDomainImpl(pioRepository);
    }

    @Test
    public void deveriaEnviarUmPost() throws Exception {
	// Given
	Pio pio = new Pio("@chrisreichel", "Hello World. Este é meu pio");
	domain.fazPostagem(pio);
	// When
	final List<Pio> pios = domain.listarPosts("@chrisreichel");
	// Then
	assertNotNull(pios);
    }

    @Test(expected = Exception.class)
    public void deveLancarExceptionSeRepoIndisponivel() {
	this.domain = new PostagemDomainImpl(null);
	domain.fazPostagem(new Pio("@nadison", "Hello World. Este é meu pio"));
    }

    @Test(expected = Exception.class)
    public void deveLancarExceptionSeNaoAdicionarPostagem() {
	domain.fazPostagem(null);
    }

    @Test
    public void deveriaListarPost() throws Exception {
	List<Pio> pios = domain.listarPosts(USUARIO_PRE_DEFINIDO);
	assertNotNull(pios);
    }

}
