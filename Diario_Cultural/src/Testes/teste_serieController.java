package Testes;

import Controller.SerieController;
import Model.Serie;
import Model.Temporada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para {@link SerieController}.
 * Implementa testes para todas as funcionalidades específicas do controlador de séries,
 * incluindo cadastro de séries e temporadas, manipulação de dados e consultas.
 *
 * @author Seu Nome
 * @version 1.0
 * @see SerieController
 * @see Serie
 * @see Temporada
 */
class teste_serieController {

    private SerieController controller;
    private Serie serie1, serie2;
    private final Map<String, List<String>> ELENCO1 = new HashMap<>();
    private final Map<String, List<String>> ELENCO2 = new HashMap<>();
    private final String TITULO1 = "Breaking Bad";
    private final String TITULO2 = "The Crown";

    /**
     * Configura o ambiente de teste antes de cada caso de teste.
     * Inicializa o controller e cria séries de exemplo para os testes.
     */
    @BeforeEach
    void setUp() {
        controller = new SerieController();

        // Configurando elencos
        ELENCO1.put("Protagonista", Arrays.asList("Bryan Cranston", "Aaron Paul"));
        ELENCO1.put("Coadjuvante", Arrays.asList("Bob Odenkirk", "Giancarlo Esposito"));

        ELENCO2.put("Protagonista", Arrays.asList("Claire Foy", "Olivia Colman"));
        ELENCO2.put("Coadjuvante", Arrays.asList("Matt Smith", "Tobias Menzies"));

        // Cadastrando séries de teste
        controller.cadastrarSerie(
                TITULO1,
                Arrays.asList("Drama", "Crime"),
                2008,
                2013,
                ELENCO1,
                "Breaking Bad",
                Arrays.asList("Netflix")
        );

        controller.cadastrarSerie(
                TITULO2,
                Arrays.asList("Drama", "História"),
                2016,
                2023,
                ELENCO2,
                "The Crown",
                Arrays.asList("Netflix")
        );

        serie1 = controller.getSeries().get(0);
        serie2 = controller.getSeries().get(1);
    }

    /**
     * Testa as operações de cadastro de séries e temporadas.
     */
    @Nested
    class CadastroTests {
        /**
         * Testa o cadastro de uma nova série.
         */
        @Test
        void testCadastrarSerie() {
            int tamanhoInicial = controller.getSeries().size();
            Map<String, List<String>> elencoNovo = new HashMap<>();
            elencoNovo.put("Protagonista", List.of("Ator Teste"));

            controller.cadastrarSerie(
                    "Série Teste",
                    List.of("Drama"),
                    2024,
                    2024,
                    elencoNovo,
                    "Test Series",
                    List.of("Netflix")
            );

            assertEquals(tamanhoInicial + 1, controller.getSeries().size());
            Serie novaSerie = controller.getSeries().getLast();

            assertAll("Verificação da nova série",
                    () -> assertEquals("Série Teste", novaSerie.getTitulo()),
                    () -> assertEquals(2024, novaSerie.getAnoLancamento()),
                    () -> assertEquals("Test Series", novaSerie.getTituloOriginal())
            );
        }

        /**
         * Testa o cadastro de uma nova temporada.
         */
        @Test
        void testCadastrarTemporada() {
            Map<String, List<String>> elencoTemporada = new HashMap<>(ELENCO1);

            controller.cadastrarTemporada(
                    "Temporada 1",
                    Arrays.asList("Drama", "Crime"),
                    2008,
                    elencoTemporada,
                    "Season 1",
                    Arrays.asList("Netflix"),
                    1,
                    13,
                    serie1
            );

            assertAll("Verificação da nova temporada",
                    () -> assertFalse(serie1.getTemporadas().isEmpty()),
                    () -> assertEquals(1, serie1.getTemporadas().get(0).getNumeroTemporada()),
                    () -> assertEquals(13, serie1.getTemporadas().get(0).getQuantidadeEpisodios())
            );
        }

        /**
         * Testa a ordenação automática das temporadas após cadastro.
         */
        @Test
        void testOrdenacaoTemporadas() {
            // Cadastra temporadas fora de ordem
            controller.cadastrarTemporada(
                    "Temporada 2", List.of("Drama"), 2009, ELENCO1,
                    "Season 2", List.of("Netflix"), 2, 13, serie1
            );

            controller.cadastrarTemporada(
                    "Temporada 1", List.of("Drama"), 2008, ELENCO1,
                    "Season 1", List.of("Netflix"), 1, 13, serie1
            );

            List<Temporada> temporadas = serie1.getTemporadas();
            assertEquals(1, temporadas.get(0).getNumeroTemporada());
            assertEquals(2, temporadas.get(1).getNumeroTemporada());
        }
    }

    /**
     * Testa as operações de consulta de dados.
     */
    @Nested
    class ConsultaTests {
        /**
         * Testa a obtenção do elenco de uma série.
         */
        @Test
        void testGetElenco() {
            Map<String, List<String>> elenco = SerieController.getElenco(serie1);
            assertAll("Verificação do elenco",
                    () -> assertEquals(ELENCO1.size(), elenco.size()),
                    () -> assertTrue(elenco.get("Protagonista").contains("Bryan Cranston"))
            );
        }

        /**
         * Testa a obtenção das plataformas onde assistir.
         */
        @Test
        void testGetOndeAssistir() {
            List<String> plataformas = SerieController.getOndeAssistir(serie1);
            assertTrue(plataformas.contains("Netflix"));
        }
    }

    /**
     * Testa as operações de modificação de séries.
     */
    @Nested
    class ModificacaoTests {
        /**
         * Testa a alteração do título original de uma série.
         */
        @Test
        void testSetTituloOriginal() {
            String novoTitulo = "New Original Title";
            SerieController.setTituloOriginal(serie1, novoTitulo);
            assertEquals(novoTitulo, serie1.getTituloOriginal());
        }
    }

    /**
     * Testa a remoção de séries do sistema.
     */
    @Nested
    class RemocaoTests {
        /**
         * Testa a remoção de uma série específica.
         */
        @Test
        void testRemoverSerie() {
            int tamanhoInicial = controller.getSeries().size();
            SerieController.removerMidia(serie1);

            assertAll("Verificação da remoção",
                    () -> assertEquals(tamanhoInicial - 1, controller.getSeries().size()),
                    () -> assertFalse(controller.getSeries().contains(serie1))
            );
        }
    }

    /**
     * Testa funcionalidades herdadas de MidiaController.
     */
    @Nested
    class HerancaTests {
        /**
         * Testa a busca por título herdada.
         */
        @Test
        void testBuscaTitulo() {
            List<? extends Model.Midia> resultado = SerieController.busca_titulo(
                    TITULO1,
                    controller.getSeries()
            );
            assertEquals(1, resultado.size());
            assertEquals(TITULO1, resultado.get(0).getTitulo());
        }

        /**
         * Testa a busca por gênero herdada.
         */
        @Test
        void testBuscaGenero() {
            List<? extends Model.Midia> resultado = SerieController.busca_genero(
                    "Drama",
                    controller.getSeries()
            );
            assertTrue(resultado.stream()
                    .allMatch(s -> s.getGeneros().contains("Drama")));
        }
    }
}