package Testes;

import Controller.LivroController;
import Model.Livro;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para verificar a persistência de dados de livros no sistema.
 * Testa o ciclo completo de persistência, incluindo o cadastro, salvamento
 * em arquivo e carregamento dos dados de volta para a aplicação.
 *
 * @see LivroController
 * @see Livro
 */
class testePersistenciaLivros {

    private LivroController controller;
    private static final String CAMINHO_ARQUIVO = "src/arquivos/livros.dat";

    /**
     * Configura o ambiente de teste antes de cada execução de teste.
     * Inicializa o controlador de livros e garante que não há dados residuais
     * no arquivo de persistência.
     */
    @BeforeEach
    void setUp() {
        controller = new LivroController();
        limparArquivoPersistencia();
    }

    /**
     * Limpa o ambiente de teste após cada execução de teste.
     * Remove quaisquer dados persistidos no arquivo de persistência.
     */
    @AfterEach
    void tearDown() {
        limparArquivoPersistencia();
    }

    /**
     * Método utilitário para excluir o arquivo de persistência de livros,
     * garantindo um estado limpo para os testes subsequentes.
     */
    private void limparArquivoPersistencia() {
        File file = new File(CAMINHO_ARQUIVO);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Testa o ciclo completo de persistência de livros.
     * Verifica:
     * <ul>
     *     <li>Se o arquivo de persistência é criado corretamente após o cadastro.</li>
     *     <li>Se os dados podem ser carregados corretamente após a limpeza da memória.</li>
     *     <li>Se os dados carregados do arquivo são consistentes com os dados cadastrados.</li>
     * </ul>
     */
    @Test
    void testeCadastrarLivroESalvarECarregar() {
        List<String> generos = Arrays.asList("Ficção", "Aventura");
        controller.cadastrarLivro("O Hobbit", "J.R.R. Tolkien", "HarperCollins", generos, 1937, "0123456789", true);

        File file = new File(CAMINHO_ARQUIVO);
        assertTrue(file.exists(), "Arquivo de persistência não foi criado");

        LivroController.limparLivros();
        boolean carregado = LivroController.carregarLivros();
        assertTrue(carregado, "O carregamento do arquivo falhou");

        List<Livro> livrosCarregados = LivroController.getLivros();
        assertEquals(1, livrosCarregados.size());

        Livro livro = livrosCarregados.get(0);
        assertAll("Verificação dos dados do livro carregado",
                () -> assertEquals("O Hobbit", livro.getTitulo()),
                () -> assertEquals("J.R.R. Tolkien", livro.getAutor()),
                () -> assertEquals("HarperCollins", livro.getEditora()),
                () -> assertEquals(generos, livro.getGeneros()),
                () -> assertEquals(1937, livro.getAnoLancamento()),
                () -> assertEquals("0123456789", livro.getIsbn()),
                () -> assertTrue(livro.getPossuiExemplar())
        );
    }
}