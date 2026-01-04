package Testes;

import Model.Temporada;
import Model.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Classe de teste para {@link Temporada} usando JUnit 5.
 * Testa as funcionalidades específicas de Temporada e
 * as funcionalidades herdadas de {@link Model.Audiovisual} e {@link Model.Midia}.
 */
public class teste_temporada {
    private Temporada temporada;
    private final String TITULO = "Breaking Bad - Temporada 1";
    private final List<String> GENEROS = Arrays.asList("Drama", "Crime", "Suspense");
    private final int ANO_LANCAMENTO = 2008;
    private final String TITULO_ORIGINAL = "Breaking Bad - Season 1";
    private final List<String> ONDE_ASSISTIR = Arrays.asList("Netflix", "Prime Video");
    private final Map<String, List<String>> ELENCO = new HashMap<>() {{
        put("Protagonistas", Arrays.asList("Bryan Cranston", "Aaron Paul"));
        put("Coadjuvantes", Arrays.asList("Bob Odenkirk", "Giancarlo Esposito"));
    }};
    private final int NUMERO_TEMPORADA = 1;
    private final int QUANTIDADE_EPISODIOS = 7;

    /**
     * Configuração inicial executada antes de cada teste.
     */
    @BeforeEach
    public void setUp() {
        temporada = new Temporada(
                TITULO,
                GENEROS,
                ANO_LANCAMENTO,
                ELENCO,
                TITULO_ORIGINAL,
                ONDE_ASSISTIR,
                NUMERO_TEMPORADA,
                QUANTIDADE_EPISODIOS
        );
    }

    /**
     * Testa se o construtor inicializa corretamente todos os atributos,
     * incluindo os herdados.
     */
    @Test
    public void testConstrutorTemporada() {
        assertAll("Construtor da Temporada",
                // Atributos específicos de Temporada
                () -> assertEquals(NUMERO_TEMPORADA, temporada.getNumeroTemporada()),
                () -> assertEquals(QUANTIDADE_EPISODIOS, temporada.getQuantidadeEpisodios()),

                // Atributos herdados de Audiovisual
                () -> assertEquals(TITULO_ORIGINAL, temporada.getTituloOriginal()),
                () -> assertEquals(ONDE_ASSISTIR, temporada.getOndeAssistir()),
                () -> assertEquals(ELENCO, temporada.getElenco()),
                () -> assertTrue(temporada.getAvaliacoes().isEmpty()),

                // Atributos herdados de Midia
                () -> assertEquals(TITULO, temporada.getTitulo()),
                () -> assertEquals(GENEROS, temporada.getGeneros()),
                () -> assertEquals(ANO_LANCAMENTO, temporada.getAnoLancamento()),
                () -> assertFalse(temporada.getJa_Consumiu())
        );
    }

    /**
     * Testa a alteração do número da temporada.
     */
    @Test
    public void testSetNumeroTemporada() {
        int novoNumero = 2;
        temporada.setNumeroTemporada(novoNumero);
        assertEquals(novoNumero, temporada.getNumeroTemporada());
    }

    /**
     * Testa a alteração da quantidade de episódios.
     */
    @Test
    public void testSetQuantidadeEpisodios() {
        int novaQuantidade = 13;
        temporada.setQuantidadeEpisodios(novaQuantidade);
        assertEquals(novaQuantidade, temporada.getQuantidadeEpisodios());
    }

    /**
     * Testa a adição e recuperação de avaliações (funcionalidade herdada).
     */
    @Test
    public void testAvaliacoes() {
        Avaliacao avaliacao = new Avaliacao(4.5f, "Excelente temporada!", "25/07/2021");
        temporada.setAvaliacao(avaliacao);

        assertAll("Avaliações",
                () -> assertEquals(1, temporada.getAvaliacoes().size()),
                () -> assertEquals(avaliacao, temporada.getAvaliacoes().getLast()),
                () -> assertEquals(4.5, temporada.getAvaliacoes().getLast().getNota()),
                () -> assertEquals("Excelente temporada!", temporada.getAvaliacoes().getLast().getAvaliacao())
        );
    }

    /**
     * Testa a atualização do elenco (funcionalidade herdada).
     */
    @Test
    public void testSetElenco() {
        Map<String, List<String>> novoElenco = new HashMap<>();
        novoElenco.put("Protagonista", List.of("Bryan Cranston"));

        temporada.setElenco(novoElenco);
        assertEquals(novoElenco, temporada.getElenco());
    }

    /**
     * Testa a atualização das plataformas de streaming (funcionalidade herdada).
     */
    @Test
    public void testSetOndeAssistir() {
        List<String> novasPlataformas = Arrays.asList("Netflix", "HBO Max");
        temporada.setOndeAssistir(novasPlataformas);
        assertEquals(novasPlataformas, temporada.getOndeAssistir());
    }

    /**
     * Testa o método toString.
     */
    @Test
    public void testToString() {
        temporada.setAvaliacao(new Avaliacao(4.5f, "Excelente!", "25/07/2021"));
        String resultado = temporada.toString();

        assertAll("Verificação do toString",
                () -> assertTrue(resultado.contains("Temporada " + NUMERO_TEMPORADA)),
                () -> assertTrue(resultado.contains(QUANTIDADE_EPISODIOS + " episódios")),
                () -> assertTrue(resultado.contains("4.5")),
                () -> assertTrue(resultado.contains("Excelente!"))
        );
    }

    /**
     * Testa a funcionalidade de marcar como consumido (herdada de Midia).
     */
    @Test
    public void testMarcarComoConsumido() {
        temporada.setJaConsumiu(true);
        assertTrue(temporada.getJa_Consumiu());
    }
}