package Testes;

import Controller.FilmeController;
import Model.Filme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para {@link FilmeController}.
 * Implementa testes para todas as funcionalidades específicas do controlador de filmes,
 * incluindo cadastro, busca e manipulação de dados do elenco.
 *
 * @see FilmeController
 * @see Filme
 */
class teste_filmeController {

    private FilmeController controller;
    private Filme filme1, filme2;
    private final String DIRETOR1 = "Christopher Nolan";
    private final String DIRETOR2 = "Martin Scorsese";
    private final String ATOR1 = "Leonardo DiCaprio";
    private final Map<String, List<String>> ELENCO1 = new HashMap<>();
    private final Map<String, List<String>> ELENCO2 = new HashMap<>();

    /**
     * Configura o ambiente de teste antes de cada caso de teste.
     * Inicializa o controller e cria filmes de exemplo para os testes.
     */
    @BeforeEach
    void setUp() {
        controller = new FilmeController();

        // Configurando elencos
        ELENCO1.put("Protagonista", Arrays.asList(ATOR1, "Joseph Gordon-Levitt"));
        ELENCO1.put("Coadjuvante", Arrays.asList("Ellen Page", "Tom Hardy"));

        ELENCO2.put("Protagonista", Arrays.asList(ATOR1, "Robert De Niro"));
        ELENCO2.put("Coadjuvante", Arrays.asList("Margot Robbie", "Joe Pesci"));

        // Cadastrando filmes de teste
        controller.cadastrarFilme(
                "A Origem",
                Arrays.asList("Ficção Científica", "Ação"),
                2010,
                148,
                DIRETOR1,
                "Christopher Nolan",
                ELENCO1,
                "Inception",
                Arrays.asList("Netflix", "Prime Video")
        );

        controller.cadastrarFilme(
                "O Lobo de Wall Street",
                Arrays.asList("Drama", "Biografia"),
                2013,
                180,
                DIRETOR2,
                "Terence Winter",
                ELENCO2,
                "The Wolf of Wall Street",
                Arrays.asList("Netflix")
        );

        filme1 = controller.getFilmes().get(0);
        filme2 = controller.getFilmes().get(1);
    }

    /**
     * Testa as operações de cadastro de filmes.
     */
    @Nested
    class CadastroTests {
        /**
         * Testa o cadastro de um novo filme.
         */
        @Test
        void testCadastrarFilme() {
            int tamanhoInicial = controller.getFilmes().size();
            Map<String, List<String>> elencoNovo = new HashMap<>();
            elencoNovo.put("Protagonista", List.of("Ator Teste"));

            controller.cadastrarFilme(
                    "Filme Teste",
                    List.of("Ação"),
                    2024,
                    120,
                    "Diretor Teste",
                    "Roteirista Teste",
                    elencoNovo,
                    "Test Movie",
                    List.of("Netflix")
            );

            assertEquals(tamanhoInicial + 1, controller.getFilmes().size());
            Filme novoFilme = controller.getFilmes().getLast();

            assertAll("Verificação do novo filme",
                    () -> assertEquals("Filme Teste", novoFilme.getTitulo()),
                    () -> assertEquals(120, novoFilme.getDuracao()),
                    () -> assertEquals("Diretor Teste", novoFilme.getDirecao()),
                    () -> assertEquals("Test Movie", novoFilme.getTituloOriginal())
            );
        }
    }

    /**
     * Agrupa testes relacionados às funcionalidades de busca.
     */
    @Nested
    class BuscaTests {
        /**
         * Testa a busca por diretor.
         */
        @Test
        void testBuscaDiretor() {
            List<Filme> resultado = FilmeController.busca_diretor(DIRETOR1, controller.getFilmes());
            assertEquals(1, resultado.size());
            assertEquals(DIRETOR1, resultado.get(0).getDirecao());
        }

        /**
         * Testa a busca por diretor com acentuação diferente.
         */
        @Test
        void testBuscaDiretorComAcentuacao() {
            List<Filme> resultado = FilmeController.busca_diretor("christopher nolán", controller.getFilmes());
            assertEquals(1, resultado.size());
            assertEquals(DIRETOR1, resultado.get(0).getDirecao());
        }

        /**
         * Testa a busca por membro do elenco.
         */
        @Test
        void testBuscaElenco() {
            List<Filme> resultado = FilmeController.busca_elenco("Protagonista", ATOR1, controller.getFilmes());
            assertEquals(2, resultado.size());
            assertTrue(resultado.stream()
                    .allMatch(f -> f.getElenco().get("Protagonista").contains(ATOR1)));
        }
    }

    /**
     * Agrupa testes relacionados às operações de modificação de filmes.
     */
    @Nested
    class ModificacaoTests {
        /**
         * Testa a alteração da duração de um filme.
         */
        @Test
        void testSetDuracao() {
            int novaDuracao = 150;
            FilmeController.setDuracao(filme1, novaDuracao);
            assertEquals(novaDuracao, filme1.getDuracao());
        }

        /**
         * Testa a alteração do diretor de um filme.
         */
        @Test
        void testSetDirecao() {
            String novaDirecao = "Novo Diretor";
            FilmeController.setDirecao(filme1, novaDirecao);
            assertEquals(novaDirecao, filme1.getDirecao());
        }

        /**
         * Testa a alteração do roteirista de um filme.
         */
        @Test
        void testSetRoteiro() {
            String novoRoteiro = "Novo Roteirista";
            FilmeController.setRoteiro(filme1, novoRoteiro);
            assertEquals(novoRoteiro, filme1.getRoteiro());
        }

        /**
         * Testa a alteração do título original de um filme.
         */
        @Test
        void testSetTituloOriginal() {
            String novoTitulo = "New Original Title";
            FilmeController.setTituloOriginal(filme1, novoTitulo);
            assertEquals(novoTitulo, filme1.getTituloOriginal());
        }
    }

    /**
     * Testa as operações de consulta de dados.
     */
    @Nested
    class ConsultaTests {
        /**
         * Testa a obtenção do elenco de um filme.
         */
        @Test
        void testGetElenco() {
            Map<String, List<String>> elenco = FilmeController.getElenco(filme1);
            assertAll("Verificação do elenco",
                    () -> assertEquals(ELENCO1.size(), elenco.size()),
                    () -> assertTrue(elenco.get("Protagonista").contains(ATOR1))
            );
        }

        /**
         * Testa a obtenção das plataformas onde assistir.
         */
        @Test
        void testGetOndeAssistir() {
            List<String> plataformas = FilmeController.getOndeAssistir(filme1);
            assertTrue(plataformas.contains("Netflix"));
            assertTrue(plataformas.contains("Prime Video"));
        }
    }

    /**
     * Testa a remoção de filmes do sistema.
     */
    @Nested
    class RemocaoTests {
        /**
         * Testa a remoção de um filme específico.
         */
        @Test
        void testRemoverFilme() {
            int tamanhoInicial = controller.getFilmes().size();
            FilmeController.removerMidia(filme1);

            assertAll("Verificação da remoção",
                    () -> assertEquals(tamanhoInicial - 1, controller.getFilmes().size()),
                    () -> assertFalse(controller.getFilmes().contains(filme1))
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
            List<? extends Model.Midia> resultado = FilmeController.busca_titulo(
                    "A Origem",
                    controller.getFilmes()
            );
            assertEquals(1, resultado.size());
            assertEquals("A Origem", resultado.get(0).getTitulo());
        }

        /**
         * Testa a busca por gênero herdada.
         */
        @Test
        void testBuscaGenero() {
            List<? extends Model.Midia> resultado = FilmeController.busca_genero(
                    "Drama",
                    controller.getFilmes()
            );
            assertTrue(resultado.stream()
                    .anyMatch(f -> f.getGeneros().contains("Drama")));
        }
    }
}