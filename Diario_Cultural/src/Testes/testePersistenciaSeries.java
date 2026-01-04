package Testes;

import Controller.SerieController;
import Model.Serie;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste responsável por validar a persistência de séries no sistema.
 * Testa o fluxo completo, incluindo cadastro, salvamento, carregamento e validação
 * dos dados armazenados em um arquivo de persistência.
 *
 * @see SerieController
 * @see Serie
 */
class testePersistenciaSeries {

    private SerieController controller;
    private static final String CAMINHO_ARQUIVO = "src/arquivos/series.dat";

    /**
     * Configuração inicial antes de cada caso de teste.
     * Inicializa o controlador de séries e limpa quaisquer dados persistidos anteriormente.
     */
    @BeforeEach
    void setUp() {
        controller = new SerieController();
        limparArquivoPersistencia();
    }

    /**
     * Limpa o ambiente após cada execução de teste.
     * Certifica-se de que os dados persistidos e os registros em memória sejam removidos.
     */
    @AfterEach
    void tearDown() {
        limparArquivoPersistencia();
        SerieController.limparSeries();
    }

    /**
     * Método utilitário para excluir o arquivo de persistência de séries.
     * Garante que os testes sejam executados em um estado limpo.
     */
    private void limparArquivoPersistencia() {
        File file = new File(CAMINHO_ARQUIVO);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Testa o ciclo completo de persistência de séries:
     * <ul>
     *     <li>Cadastra uma nova série no sistema.</li>
     *     <li>Valida que o arquivo de persistência é criado corretamente.</li>
     *     <li>Carrega os dados da série armazenados no arquivo.</li>
     *     <li>Verifica se os dados carregados correspondem aos dados cadastrados.</li>
     * </ul>
     */
    @Test
    void testeCadastrarSerieESalvarECarregar() {

        List<String> generos = Arrays.asList("Drama", "Fantasia");
        List<String> plataformas = Arrays.asList("HBO Max");
        Map<String, List<String>> elenco = new HashMap<>();
        elenco.put("Protagonistas", Arrays.asList("Ator X", "Ator Y"));
        elenco.put("Coadjuvantes", Collections.singletonList("Ator Z"));

        controller.cadastrarSerie("Game of Thrones", generos, 2011, 2019, elenco, "Game of Thrones", plataformas);

        File file = new File(CAMINHO_ARQUIVO);
        assertTrue(file.exists(), "Arquivo de persistência de séries não foi criado");

        SerieController.limparSeries();
        boolean carregado = SerieController.carregarSeries();
        assertTrue(carregado, "O carregamento do arquivo de séries falhou");

        List<Serie> seriesCarregadas = SerieController.getSeries();
        assertEquals(1, seriesCarregadas.size());

        Serie serie = seriesCarregadas.get(0);
        assertAll("Verificação dos dados da série carregada",
                () -> assertEquals("Game of Thrones", serie.getTitulo()),
                () -> assertEquals(generos, serie.getGeneros()),
                () -> assertEquals(2011, serie.getAnoLancamento()),
                () -> assertEquals(2019, serie.getAnoEncerramento()),
                () -> assertEquals(elenco, serie.getElenco()),
                () -> assertEquals("Game of Thrones", serie.getTituloOriginal()),
                () -> assertEquals(plataformas, serie.getOndeAssistir())
        );
    }
}