package Controller;

import Model.Audiovisual;
import Model.Serie;
import Model.Temporada;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Controlador específico para gerenciamento de séries e temporadas, herdando de MidiaController.
 * Fornece funcionalidades para cadastro e manipulação de séries e suas temporadas.
 */
public class SerieController extends MidiaController {

    /**
     * Lista estática que armazena todas as séries cadastradas no sistema
     */
    private static List<Serie> series = new ArrayList<>();

    private static final String DIRETORIO = "src/arquivos";
    private static final String ARQUIVO = DIRETORIO + "/series.dat";

    /**
     * Construtor de um novo controlador de séries.
     * Este construtor cria a estrutura de diretórios necessária para o armazenamento persistente
     * de dados das séries, garantindo que o diretório especificado em {@link #DIRETORIO} exista.
     *
     * <p>Ao instanciar um novo SerieController:</p>
     * <ul>
     *     <li>Verifica a existência do diretório de armazenamento</li>
     *     <li>Cria o diretório se ele não existir</li>
     * </ul>
     *
     * @see #criarDiretorioSeNaoExiste()
     */
    public SerieController() {
        criarDiretorioSeNaoExiste();
    }

    /**
     * Cria o diretório de armazenamento de arquivos se ele não existir.
     * Este método é responsável por garantir que a estrutura de diretórios necessária
     * para o armazenamento persistente dos dados esteja disponível.
     *
     * <p>O método verifica se o diretório especificado em {@link #DIRETORIO} existe e,
     * caso não exista, cria toda a estrutura de diretórios necessária.</p>
     *
     * <p>Este método é chamado durante a inicialização do controlador para garantir
     * que o sistema possa salvar e carregar dados corretamente.</p>
     *
     * @see #DIRETORIO
     * @see java.io.File#mkdirs()
     */
    private void criarDiretorioSeNaoExiste() {
        File diretorio = new File(DIRETORIO);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    /**
     * Cadastra uma nova série no sistema.
     *
     * @param titulo              título em português da série
     * @param generos             lista de gêneros da série
     * @param ano_de_lancamento   ano em que a série começou
     * @param ano_de_encerramento ano em que a série terminou (ou atual se ainda em exibição)
     * @param elenco              mapa com categorias e respectivos membros do elenco principal
     * @param titulo_original     título original da série
     * @param onde_assistir       lista de plataformas onde a série está disponível
     */
    public void cadastrarSerie(String titulo, List<String> generos, int ano_de_lancamento, int ano_de_encerramento, Map<String, List<String>> elenco, String titulo_original, List<String> onde_assistir) {

        series.add(new Serie(titulo, generos, ano_de_lancamento, ano_de_encerramento, elenco, titulo_original, onde_assistir));
        salvarSeries();

    }

    /**
     * Cadastra uma nova temporada para uma série existente.
     * Após o cadastro, as temporadas são reordenadas automaticamente.
     *
     * @param titulo               título específico da temporada
     * @param generos              gêneros específicos da temporada
     * @param ano_de_lancamento    ano de lançamento da temporada
     * @param elenco_temporada     elenco específico da temporada
     * @param titulo_original      título original da temporada
     * @param onde_assistir        plataformas onde a temporada está disponível
     * @param numero_temporada     número/ordem da temporada na série
     * @param quantidade_episodios quantidade de episódios na temporada
     * @param serie_selecionada    série à qual a temporada pertence
     */
    public void cadastrarTemporada(String titulo, List<String> generos, int ano_de_lancamento, Map<String, List<String>> elenco_temporada,
                                   String titulo_original, List<String> onde_assistir, int numero_temporada, int quantidade_episodios, Serie serie_selecionada) {

        serie_selecionada.addTemporada(new Temporada(titulo, generos, ano_de_lancamento, elenco_temporada,
                titulo_original, onde_assistir, numero_temporada, quantidade_episodios));

        serie_selecionada.ordernarTemporadas();
    }

    /**
     * Retorna uma lista completa de séries cadastradas no sistema
     *
     * @return lista completa de séries cadastradas no sistema
     */
    public static List<Serie> getSeries() {
        return series;
    }

    /**
     * Remove todas as séries cadastradas no sistema.
     * Este método limpa completamente a lista de séries, removendo todos os registros
     * da memória. É útil principalmente para fins de teste ou quando é necessário
     * reinicializar o sistema.
     *
     * @see #series
     */
    public static void limparSeries() { series.clear(); }

    /**
     * Remove uma série específica do sistema.
     *
     * @param midia série a ser removida
     */
    public static void removerMidia(Serie midia) {
        series.remove(midia);
    }

    /**
     * Retorna um mapa com categorias e respectivos membros do elenco
     *
     * @param serie série da qual obter o elenco
     * @return mapa com categorias e respectivos membros do elenco
     */
    public static Map<String, List<String>> getElenco(Audiovisual serie) {
        return serie.getElenco();
    }

    /**
     * Retorna lista de plataformas onde a série está disponível
     *
     * @param serie série da qual obter as plataformas
     * @return lista de plataformas onde a série está disponível
     */
    public static List<String> getOndeAssistir(Audiovisual serie) {
        return serie.getOndeAssistir();
    }

    /**
     * Retorna informação sobre o ano de encerramento da série, retorna 'em exibição' caso ainda não tenha sido encerrada.
     *
     * @param serie série da qual obter o ano de encerramento.
     * @return ano de encerramento da série.
     */
    public static String getAnoEncerramento(Serie serie) { return serie.getAnoEncerramento() == 0 ? "Em exibição" : Integer.toString(serie.getAnoEncerramento()); }

    /**
     * Define um novo título original para a série.
     *
     * @param serie      série a ser modificada
     * @param novoTitulo novo título original
     */
    public static void setTituloOriginal(Serie serie, String novoTitulo) {
        serie.setTituloOriginal(novoTitulo);
    }

    /**
     * Retorna o título original da série.
     *
     * @param serie série a ser consultada
     */
    public static String getTituloOriginal(Serie serie) { return serie.getTituloOriginal(); }

    /**
     * Define o ano de encerramento da série.
     *
     * @param serie série a ser modificada.
     */
    public static void setAnoEncerramento(Serie serie, int anoEncerramento) { serie.setAnoEncerramento(anoEncerramento); }

    /**
     * Retorna a lista de temporadas associadas a uma série específica.
     * Este método fornece acesso à coleção completa de temporadas de uma série,
     * mantendo a ordem em que foram adicionadas.
     *
     * @param serie a série da qual se deseja obter as temporadas
     * @return lista contendo todas as temporadas da série
     * @see Model.Temporada
     * @see Model.Serie
     */
    public static List<Temporada> getTemporadas(Serie serie) {
        return serie.getTemporadas();
    }

    /**
     * Adiciona novas plataformas de streaming à lista existente de uma série.
     * Este método permite expandir as opções de onde a série pode ser assistido
     * sem substituir as plataformas já cadastradas.
     *
     * @param serie serie ao qual serão adicionadas as novas plataformas
     * @param ondeAssistir lista de novas plataformas de streaming a serem adicionadas
     */
    public static void addOndeAssistir(Serie serie, List<String> ondeAssistir) { serie.addOndeAssistir(ondeAssistir); }

    /**
     * Define uma nova lista completa de plataformas de streaming para uma série.
     * Este método substitui todas as plataformas existentes pelas novas informadas.
     * Útil quando é necessário atualizar completamente as informações de disponibilidade.
     *
     * @param serie serie a ter sua lista de plataformas atualizada
     * @param ondeAssistir nova lista de plataformas que substituirá a atual
     */
    public static void setOndeAssistir(Serie serie, List<String> ondeAssistir) { serie.setOndeAssistir(ondeAssistir); }

    /**
     * Remove uma plataforma específica da lista de locais onde a série pode ser assistido.
     * A comparação do nome da plataforma é feita ignorando maiúsculas/minúsculas e acentuação.
     *
     * @param serie serie do qual a plataforma será removida
     * @param ondeAssistir nome da plataforma a ser removida
     */
    public static void removerOndeAssistir(Serie serie, String ondeAssistir) { serie.removerOndeAssistir(ondeAssistir); }

    /**
     * Salva todas as séries cadastradas no sistema em um arquivo.
     * Este método serializa a lista de séries e a armazena em um arquivo binário
     * no diretório especificado por {@link #ARQUIVO}.
     *
     * <p>O método utiliza um {@link ObjectOutputStream} para realizar a serialização
     * dos dados. Em caso de erro durante a operação de salvamento, o stack trace
     * do erro será impresso no console.</p>
     *
     * @see #ARQUIVO
     * @see java.io.ObjectOutputStream
     */
    public static void salvarSeries() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega as séries previamente salvas do arquivo para o sistema.
     * Este método deserializa a lista de séries armazenada no arquivo binário
     * especificado por {@link #ARQUIVO}.
     *
     * <p>O método utiliza um {@link ObjectInputStream} para realizar a deserialização
     * dos dados.</p>
     *
     * @return {@code true} se as séries foram carregadas com sucesso,
     *         {@code false} se ocorreu algum erro durante o carregamento
     * @see #ARQUIVO
     * @see java.io.ObjectInputStream
     */
    public static boolean carregarSeries() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            series = (List<Serie>) ois.readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
