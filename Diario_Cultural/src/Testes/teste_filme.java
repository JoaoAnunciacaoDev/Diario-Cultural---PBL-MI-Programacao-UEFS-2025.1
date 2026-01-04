package Testes;

import Model.Filme;
import Model.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Classe de teste para {@link Filme} usando JUnit 5.
 * Testa tanto as funcionalidades específicas de Filme quanto
 * aquelas herdadas de {@link Model.Audiovisual} e {@link Model.Midia}.
 *
 * @see Filme
 * @see Model.Audiovisual
 * @see Model.Midia
 */
public class teste_filme {

    private Filme filme;
    private final String TITULO = "Interestelar";
    private final List<String> GENEROS = Arrays.asList("Ficção Científica", "Drama", "Aventura");
    private final int ANO = 2014;
    private final int DURACAO = 169;
    private final String DIRECAO = "Christopher Nolan";
    private final String ROTEIRO = "Jonathan Nolan, Christopher Nolan";
    private final String TITULO_ORIGINAL = "Interstellar";
    private final List<String> ONDE_ASSISTIR = Arrays.asList("Netflix", "Prime Video", "HBO Max");
    private final Map<String, List<String>> ELENCO = new HashMap<>() {{
        put("Protagonistas", Arrays.asList("Matthew McConaughey", "Anne Hathaway"));
        put("Coadjuvantes", Arrays.asList("Jessica Chastain", "Michael Caine"));
    }};

    /**
     * Configuração inicial executada antes de cada teste.
     * Cria uma nova instância de Filme com dados do "Interestelar".
     */
    @BeforeEach
    public void setUp() {
        filme = new Filme(TITULO, GENEROS, ANO, DURACAO, DIRECAO, ROTEIRO,
                ELENCO, TITULO_ORIGINAL, ONDE_ASSISTIR);
    }

    /**
     * Verifica se o construtor inicializa corretamente todos os atributos,
     * incluindo os herdados de Audiovisual e Midia.
     */
    @Test
    public void testConstrutorFilme() {
        assertAll("Construtor do Filme",
                // Atributos específicos de Filme
                () -> assertEquals(DURACAO, filme.getDuracao()),
                () -> assertEquals(DIRECAO, filme.getDirecao()),
                () -> assertEquals(ROTEIRO, filme.getRoteiro()),

                // Atributos herdados de Audiovisual
                () -> assertEquals(TITULO_ORIGINAL, filme.getTituloOriginal()),
                () -> assertEquals(ONDE_ASSISTIR, filme.getOndeAssistir()),
                () -> assertEquals(ELENCO, filme.getElenco()),

                // Atributos herdados de Midia
                () -> assertEquals(TITULO, filme.getTitulo()),
                () -> assertEquals(GENEROS, filme.getGeneros()),
                () -> assertEquals(ANO, filme.getAnoLancamento()),
                () -> assertFalse(filme.getJa_Consumiu())
        );
    }

    /**
     * Verifica se a alteração da duração do filme funciona corretamente.
     */
    @Test
    public void testSetDuracao() {
        int novaDuracao = 170;
        filme.setDuracao(novaDuracao);
        assertEquals(novaDuracao, filme.getDuracao());
    }

    /**
     * Verifica se a alteração do diretor funciona corretamente.
     */
    @Test
    public void testSetDirecao() {
        String novaDirecao = "C. Nolan";
        filme.setDirecao(novaDirecao);
        assertEquals(novaDirecao, filme.getDirecao());
    }

    /**
     * Verifica se a alteração do roteiro funciona corretamente.
     */
    @Test
    public void testSetRoteiro() {
        String novoRoteiro = "Irmãos Nolan";
        filme.setRoteiro(novoRoteiro);
        assertEquals(novoRoteiro, filme.getRoteiro());
    }

    /**
     * Verifica se o método getTipoMidia retorna corretamente o tipo "Filme".
     */
    @Test
    public void testGetTipoMidia() {
        assertEquals("Filme", filme.getTipoMidia());
    }

    /**
     * Verifica se a adição de avaliação funciona corretamente.
     */
    @Test
    public void testSetAvaliacao() {
        Avaliacao avaliacao = new Avaliacao(5.0f, "Obra-prima do cinema!", "25/07/2015");
        filme.setAvaliacao(avaliacao);
        assertTrue(filme.getAvaliacoes().contains(avaliacao));
    }

    /**
     * Verifica se a alteração do elenco funciona corretamente.
     */
    @Test
    public void testSetElenco() {
        Map<String, List<String>> novoElenco = new HashMap<>();
        novoElenco.put("Protagonista", List.of("Matthew McConaughey"));
        filme.setElenco(novoElenco);
        assertEquals(novoElenco, filme.getElenco());
    }

    /**
     * Verifica se a alteração das plataformas de streaming funciona corretamente.
     */
    @Test
    public void testSetOndeAssistir() {
        List<String> novasPlataformas = Arrays.asList("Netflix", "Prime Video");
        filme.setOndeAssistir(novasPlataformas);
        assertEquals(novasPlataformas, filme.getOndeAssistir());
    }

    /**
     * Verifica se o método toString retorna uma string contendo
     * todas as informações relevantes do filme.
     */
    @Test
    public void testToString() {
        String resultado = filme.toString();

        assertAll("Verificação do toString",
                // Informações da classe Midia
                () -> assertTrue(resultado.contains(TITULO)),
                () -> assertTrue(resultado.contains(String.valueOf(ANO))),
                () -> assertTrue(resultado.contains("Ficção Científica")),

                // Informações específicas de Filme
                () -> assertTrue(resultado.contains(String.valueOf(DURACAO))),
                () -> assertTrue(resultado.contains(DIRECAO)),
                () -> assertTrue(resultado.contains(ROTEIRO)),
                () -> assertTrue(resultado.contains(TITULO_ORIGINAL)),
                () -> assertTrue(resultado.contains("Netflix")),
                () -> assertTrue(resultado.contains("Matthew McConaughey"))
        );
    }
}