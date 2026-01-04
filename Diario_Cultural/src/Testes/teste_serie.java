package Testes;

import Model.Serie;
import Model.Temporada;
import Model.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Classe de teste para {@link Serie} usando JUnit 5.
 * Testa tanto as funcionalidades específicas de Serie quanto
 * aquelas herdadas de {@link Model.Audiovisual} e {@link Model.Midia}.
 *
 * @see Serie
 * @see Model.Audiovisual
 * @see Model.Midia
 */
public class teste_serie {

    private Serie serie;
    private final String TITULO = "Breaking Bad";
    private final List<String> GENEROS = Arrays.asList("Drama", "Crime", "Suspense");
    private final int ANO_LANCAMENTO = 2008;
    private final int ANO_ENCERRAMENTO = 2013;
    private final String TITULO_ORIGINAL = "Breaking Bad";
    private final List<String> ONDE_ASSISTIR = Arrays.asList("Netflix", "Prime Video");
    private final Map<String, List<String>> ELENCO = new HashMap<>() {{
        put("Protagonistas", Arrays.asList("Bryan Cranston", "Aaron Paul"));
        put("Coadjuvantes", Arrays.asList("Bob Odenkirk", "Giancarlo Esposito"));
    }};

    /**
     * Configuração inicial executada antes de cada teste.
     * Cria uma nova instância de Serie com dados de "Breaking Bad".
     */
    @BeforeEach
    public void setUp() {
        serie = new Serie(TITULO, GENEROS, ANO_LANCAMENTO, ANO_ENCERRAMENTO,
                ELENCO, TITULO_ORIGINAL, ONDE_ASSISTIR);
    }

    /**
     * Cria uma temporada com os dados necessários para teste.
     *
     * @param numeroTemp número da temporada
     * @param qtdEpisodios quantidade de episódios
     * @return uma nova instância de Temporada
     */
    private Temporada criarTemporada(int numeroTemp, int qtdEpisodios) {
        String tituloTemp = TITULO + " - Temporada " + numeroTemp;
        Map<String, List<String>> elencoTemp = new HashMap<>(ELENCO);
        return new Temporada(
                tituloTemp,           // título
                GENEROS,             // gêneros
                ANO_LANCAMENTO + numeroTemp - 1, // ano de lançamento
                elencoTemp,          // elenco
                TITULO_ORIGINAL + " Season " + numeroTemp, // título original
                ONDE_ASSISTIR,       // onde assistir
                numeroTemp,          // número da temporada
                qtdEpisodios         // quantidade de episódios
        );
    }

    /**
     * Verifica se o construtor inicializa corretamente todos os atributos.
     */
    @Test
    public void testConstrutorSerie() {
        assertAll("Construtor da Serie",
                // Atributos específicos de Serie
                () -> assertEquals(ANO_ENCERRAMENTO, serie.getAnoEncerramento()),
                () -> assertEquals(0.0f, serie.getNota()),
                () -> assertTrue(serie.getTemporadas().isEmpty()),

                // Atributos herdados
                () -> assertEquals(TITULO, serie.getTitulo()),
                () -> assertEquals(GENEROS, serie.getGeneros()),
                () -> assertEquals(ANO_LANCAMENTO, serie.getAnoLancamento()),
                () -> assertEquals(TITULO_ORIGINAL, serie.getTituloOriginal()),
                () -> assertEquals(ONDE_ASSISTIR, serie.getOndeAssistir()),
                () -> assertEquals(ELENCO, serie.getElenco())
        );
    }

    /**
     * Verifica se a adição de temporadas funciona corretamente.
     */
    @Test
    public void testAddTemporada() {
        Temporada temporada1 = criarTemporada(1, 7);
        Temporada temporada2 = criarTemporada(2, 13);

        serie.addTemporada(temporada1);
        serie.addTemporada(temporada2);

        assertAll("Adição de Temporadas",
                () -> assertEquals(2, serie.getTemporadas().size()),
                () -> assertTrue(serie.getTemporadas().contains(temporada1)),
                () -> assertTrue(serie.getTemporadas().contains(temporada2))
        );
    }

    /**
     * Verifica se o cálculo da nota média da série funciona corretamente.
     */
    @Test
    public void testSetNota() {
        Temporada temp1 = criarTemporada(1, 7);
        temp1.setAvaliacao(new Avaliacao(4.5f, "Excelente início!", "25/07/2020"));

        Temporada temp2 = criarTemporada(2, 13);
        temp2.setAvaliacao(new Avaliacao(5.0f, "Melhor ainda!", "25/07/2020"));

        serie.addTemporada(temp1);
        serie.addTemporada(temp2);

        serie.setNota();
        assertEquals(4.75f, serie.getNota(), 0.01);
    }

    /**
     * Verifica se a ordenação das temporadas funciona corretamente.
     */
    @Test
    public void testOrdernarTemporadas() {
        Temporada temp2 = criarTemporada(2, 13);
        Temporada temp1 = criarTemporada(1, 7);
        Temporada temp3 = criarTemporada(3, 13);

        serie.addTemporada(temp2);
        serie.addTemporada(temp1);
        serie.addTemporada(temp3);

        serie.ordernarTemporadas();

        assertAll("Ordenação de Temporadas",
                () -> assertEquals(1, serie.getTemporadas().get(0).getNumeroTemporada()),
                () -> assertEquals(2, serie.getTemporadas().get(1).getNumeroTemporada()),
                () -> assertEquals(3, serie.getTemporadas().get(2).getNumeroTemporada())
        );
    }

    /**
     * Verifica se o método getTipoMidia retorna corretamente o tipo "Série".
     */
    @Test
    public void testGetTipoMidia() {
        assertEquals("Série", serie.getTipoMidia());
    }

    /**
     * Verifica se o método toString retorna uma string contendo
     * todas as informações relevantes da série.
     */
    @Test
    public void testToString() {
        Temporada temp1 = criarTemporada(1, 7);
        temp1.setAvaliacao(new Avaliacao(4.5f, "Excelente!", "25/07/2020"));
        serie.addTemporada(temp1);
        serie.setNota();

        String resultado = serie.toString();

        assertAll("Verificação do toString",
                // Informações básicas
                () -> assertTrue(resultado.contains(TITULO)),
                () -> assertTrue(resultado.contains(String.valueOf(ANO_LANCAMENTO))),
                () -> assertTrue(resultado.contains("Drama")),

                // Informações específicas de Serie
                () -> assertTrue(resultado.contains("Encerrada em " + ANO_ENCERRAMENTO)),
                () -> assertTrue(resultado.contains("4.5")),
                () -> assertTrue(resultado.contains("Temporada 1")),
                () -> assertTrue(resultado.contains("7 episódios"))
        );
    }
}