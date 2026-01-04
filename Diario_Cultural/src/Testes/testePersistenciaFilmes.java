package Testes;

import Controller.FilmeController;
import Model.Filme;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para verificar a persistência de dados de filmes no sistema.
 * Testa operações como cadastro, salvamento e carregamento de filmes
 * armazenados em um arquivo de persistência.
 *
 * @see FilmeController
 * @see Filme
 */
class testePersistenciaFilmes {

    private FilmeController controller;
    private static final String CAMINHO_ARQUIVO = "src/arquivos/filmes.dat";

    /**
     * Configura o ambiente antes de cada execução de teste.
     * Inicializa o controlador de filmes e garante que não haja dados residuais 
     * no arquivo de persistência.
     */
    @BeforeEach
    void setUp() {
        controller = new FilmeController();
        limparArquivoPersistencia();
    }

    /**
     * Limpa o ambiente de teste após cada execução de teste.
     * Remove quaisquer dados persistidos e reseta a lista de filmes gerenciada.
     */
    @AfterEach
    void tearDown() {
        limparArquivoPersistencia();
        FilmeController.limparFilmes();
    }

    /**
     * Método utilitário para excluir o arquivo de persistência de filmes,
     * garantindo um estado limpo para os testes.
     */
    private void limparArquivoPersistencia() {
        File file = new File(CAMINHO_ARQUIVO);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Testa o fluxo completo de persistência: cadastro de um filme, salvamento 
     * em arquivo e carregamento dos dados de volta para a aplicação.
     * Verifica se os dados persistidos são carregados exatamente como foram armazenados.
     */
    @Test
    void testeCadastrarFilmeESalvarECarregar() {

        List<String> generos = Arrays.asList("Drama", "Sci-Fi");
        List<String> plataformas = Arrays.asList("Netflix", "Amazon Prime");
        Map<String, List<String>> elenco = new HashMap<>();
        elenco.put("Protagonistas", Arrays.asList("Ator 1", "Ator 2"));
        elenco.put("Coadjuvantes", Collections.singletonList("Ator 3"));

        controller.cadastrarFilme("Interestelar", generos, 2014, 169, 
                "Christopher Nolan", "Jonathan Nolan", elenco, 
                "Interstellar", plataformas);

        File file = new File(CAMINHO_ARQUIVO);
        assertTrue(file.exists(), "Arquivo de persistência de filmes não foi criado");

        FilmeController.limparFilmes();
        boolean carregado = FilmeController.carregarFilmes();
        assertTrue(carregado, "O carregamento do arquivo de filmes falhou");
        List<Filme> filmesCarregados = FilmeController.getFilmes();
        assertEquals(1, filmesCarregados.size());

        Filme filme = filmesCarregados.getFirst();
        assertAll("Verificação dos atributos do filme carregado",
                () -> assertEquals("Interestelar", filme.getTitulo()),
                () -> assertEquals(generos, filme.getGeneros()),
                () -> assertEquals(2014, filme.getAnoLancamento()),
                () -> assertEquals(169, filme.getDuracao()),
                () -> assertEquals("Christopher Nolan", filme.getDirecao()),
                () -> assertEquals("Jonathan Nolan", filme.getRoteiro()),
                () -> assertEquals(elenco, filme.getElenco()),
                () -> assertEquals("Interstellar", filme.getTituloOriginal()),
                () -> assertEquals(plataformas, filme.getOndeAssistir())
        );
    }
}