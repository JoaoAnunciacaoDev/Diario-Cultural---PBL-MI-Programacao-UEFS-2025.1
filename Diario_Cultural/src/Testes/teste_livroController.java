package Testes;

import Controller.LivroController;
import Model.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para {@link LivroController}.
 * Implementa testes para todas as funcionalidades específicas do controlador de livros,
 * incluindo cadastro, busca e manipulação de dados.
 *
 * @see LivroController
 * @see Livro
 */
class teste_livroController {

    private LivroController controller;
    private Livro livro1, livro2, livro3;
    private final String AUTOR1 = "George R. R. Martin";
    private final String AUTOR2 = "J. R. R. Tolkien";
    private final String ISBN1 = "978-0553103540";
    private final String ISBN2 = "978-0261102219";

    /**
     * Configura o ambiente de teste antes de cada caso de teste.
     * Inicializa o controller e cria livros de exemplo para os testes.
     */
    @BeforeEach
    void setUp() {

        controller = new LivroController();

        // Criando livros de teste
        controller.cadastrarLivro(
                "A Game of Thrones",
                AUTOR1,
                "Bantam Books",
                Arrays.asList("Fantasia", "Aventura"),
                1996,
                ISBN1,
                true
        );

        controller.cadastrarLivro(
                "O Senhor dos Anéis",
                AUTOR2,
                "Allen & Unwin",
                Arrays.asList("Fantasia", "Aventura"),
                1954,
                ISBN2,
                false
        );

        controller.cadastrarLivro(
                "A Clash of Kings",
                AUTOR1,
                "Bantam Books",
                Arrays.asList("Fantasia", "Aventura"),
                1998,
                "978-0553108033",
                true
        );

        livro1 = controller.getLivros().get(0);
        livro2 = controller.getLivros().get(1);
        livro3 = controller.getLivros().get(2);
    }

    /**
     * Testa as operações de cadastro de livros.
     */
    @Nested
    class CadastroTests {
        /**
         * Testa o cadastro de um novo livro.
         */
        @Test
        void testCadastrarLivro() {
            int tamanhoInicial = controller.getLivros().size();
            controller.cadastrarLivro(
                    "Novo Livro",
                    "Novo Autor",
                    "Nova Editora",
                    Arrays.asList("Ficção"),
                    2024,
                    "123-456",
                    true
            );

            assertEquals(tamanhoInicial + 1, controller.getLivros().size());
            Livro novoLivro = controller.getLivros().getLast();

            assertAll("Verificação do novo livro",
                    () -> assertEquals("Novo Livro", novoLivro.getTitulo()),
                    () -> assertEquals("Novo Autor", novoLivro.getAutor()),
                    () -> assertEquals("Nova Editora", novoLivro.getEditora()),
                    () -> assertEquals("123-456", novoLivro.getIsbn()),
                    () -> assertTrue(novoLivro.getPossuiExemplar())
            );
        }
    }

    /**
     * Agrupa testes relacionados às funcionalidades de busca.
     */
    @Nested
    class BuscaTests {
        /**
         * Testa a busca por autor.
         */
        @Test
        void testBuscaAutor() {
            List<Livro> resultado = LivroController.busca_autor(AUTOR1, controller.getLivros());
            assertEquals(2, resultado.size());
            assertTrue(resultado.stream().allMatch(l -> l.getAutor().equals(AUTOR1)));
        }

        /**
         * Testa a busca por autor com acentuação diferente.
         */
        @Test
        void testBuscaAutorComAcentuacao() {
            List<Livro> resultado = LivroController.busca_autor("george r. r. martín", controller.getLivros());
            assertEquals(2, resultado.size());
            assertTrue(resultado.stream().allMatch(l -> l.getAutor().equals(AUTOR1)));
        }

        /**
         * Testa a busca por ISBN.
         */
        @Test
        void testBuscaISBN() {
            List<Livro> resultado = LivroController.busca_isbn(ISBN1, controller.getLivros());
            assertEquals(1, resultado.size());
            assertEquals(ISBN1, resultado.get(0).getIsbn());
        }
    }

    /**
     * Agrupa testes relacionados às operações de modificação de livros.
     */
    @Nested
    class ModificacaoTests {
        /**
         * Testa a alteração do autor de um livro.
         */
        @Test
        void testSetAutor() {
            String novoAutor = "Novo Autor";
            LivroController.setAutor(livro1, novoAutor);
            assertEquals(novoAutor, livro1.getAutor());
        }

        /**
         * Testa a alteração da editora de um livro.
         */
        @Test
        void testSetEditora() {
            String novaEditora = "Nova Editora";
            LivroController.setEditora(livro1, novaEditora);
            assertEquals(novaEditora, livro1.getEditora());
        }

        /**
         * Testa a alteração do ISBN de um livro.
         */
        @Test
        void testSetIsbn() {
            String novoISBN = "999-999";
            LivroController.setIsbn(livro1, novoISBN);
            assertEquals(novoISBN, livro1.getIsbn());
        }

        /**
         * Testa a alteração do status de posse do exemplar.
         */
        @Test
        void testSetPossuiExemplar() {
            boolean novoStatus = !livro1.getPossuiExemplar();
            LivroController.setPossuiExemplar(livro1, novoStatus);
            assertEquals(novoStatus, livro1.getPossuiExemplar());
        }
    }

    /**
     * Testa a remoção de livros do sistema.
     */
    @Nested
    class RemocaoTests {
        /**
         * Testa a remoção de um livro específico.
         */
        @Test
        void testRemoverLivro() {
            int tamanhoInicial = controller.getLivros().size();
            LivroController.removerMidia(livro1);

            assertAll("Verificação da remoção",
                    () -> assertEquals(tamanhoInicial - 1, controller.getLivros().size()),
                    () -> assertFalse(controller.getLivros().contains(livro1))
            );
        }
    }

    /**
     * Testa funcionalidades herdadas de MidiaController.
     */
    @Nested
    class HerancaTests {
        /**
         * Testa a busca por título herdada de MidiaController.
         */
        @Test
        void testBuscaTitulo() {
            List<? extends Model.Midia> resultado = LivroController.busca_titulo(
                    "A Game of Thrones",
                    controller.getLivros()
            );
            assertEquals(1, resultado.size());
            assertEquals("A Game of Thrones", resultado.get(0).getTitulo());
        }

        /**
         * Testa a busca por gênero herdada de MidiaController.
         */
        @Test
        void testBuscaGenero() {
            List<? extends Model.Midia> resultado = LivroController.busca_genero(
                    "Fantasia",
                    controller.getLivros()
            );
            assertTrue(resultado.size() > 0);
            assertTrue(resultado.stream()
                    .allMatch(l -> l.getGeneros().contains("Fantasia")));
        }
    }
}