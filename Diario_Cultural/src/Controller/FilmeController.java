package Controller;

import Model.Filme;
import Service.Servicos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controlador específico para gerenciamento de filmes, herdando de MidiaController.
 * Fornece funcionalidades para cadastro, busca e manipulação de filmes no sistema.
 */
public class FilmeController extends MidiaController {

    /** Lista estática que armazena todos os filmes cadastrados no sistema */
    private static List<Filme> filmes = new ArrayList<>();

    private static final String DIRETORIO = "src/arquivos";
    private static final String ARQUIVO = DIRETORIO + "/filmes.dat";

    /**
     * Construtor para um novo controlador de filmes.
     * Este construtor cria a estrutura de diretórios necessária para o armazenamento persistente
     * de dados das séries, garantindo que o diretório especificado em {@link #DIRETORIO} exista.
     *
     * <p>Ao instanciar um novo FilmeController:</p>
     * <ul>
     *     <li>Verifica a existência do diretório de armazenamento</li>
     *     <li>Cria o diretório se ele não existir</li>
     * </ul>
     *
     * @see #criarDiretorioSeNaoExiste()
     */
    public FilmeController() {
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
     * @see File#mkdirs()
     */
    private void criarDiretorioSeNaoExiste() {
        File diretorio = new File(DIRETORIO);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    /**
     * Cadastra um novo filme no sistema.
     *
     * @param titulo título em português do filme
     * @param generos lista de gêneros do filme
     * @param ano_de_lancamento ano em que o filme foi lançado
     * @param duracao duração do filme em minutos
     * @param direcao nome do(s) diretor(es)
     * @param roteiro nome do(s) roteirista(s)
     * @param elenco mapa com categorias e respectivos membros do elenco
     * @param titulo_original título original do filme
     * @param onde_assistir lista de plataformas onde o filme está disponível
     */
    public void cadastrarFilme(String titulo, List<String> generos, int ano_de_lancamento, int duracao, String direcao, String roteiro, Map<String, List<String>> elenco, String titulo_original, List<String> onde_assistir) {
        filmes.add(new Filme(titulo, generos, ano_de_lancamento, duracao, direcao, roteiro, elenco, titulo_original, onde_assistir));
        salvarFilmes();
    }

    /**
     * Busca filmes por diretor (busca exata, ignorando case e acentuação).
     *
     * @param diretor nome do diretor a ser buscado
     * @param lista lista de filmes onde realizar a busca
     * @return lista de filmes do diretor especificado
     */
    public static List<Filme> busca_diretor(String diretor, List<Filme> lista) {

        List<Filme> resultados_da_busca =  new ArrayList<>();

        for (Filme midia : lista) {
            if (Servicos.normalizarTitulo(midia.getDirecao()).equalsIgnoreCase(Servicos.normalizarTitulo(diretor))) {
                resultados_da_busca.add(midia);
            }
        }

        return resultados_da_busca;

    }

    /**
     * Busca filmes por membro do elenco e sua função.
     * (busca exata, ignorando case e acentuação)
     *
     * @param funcao categoria/função no filme (ex: "Protagonista", "Coadjuvante")
     * @param nome nome do membro do elenco
     * @param lista lista de filmes onde realizar a busca
     * @return lista de filmes onde a pessoa exerceu a função especificada
     */
    public static List<Filme> busca_elenco(String funcao, String nome, List<Filme> lista) {

        List<Filme> resultados_da_busca = new ArrayList<>();

        String funcaoBusca = Servicos.normalizarTitulo(funcao);
        String nomeBusca = Servicos.normalizarTitulo(nome);

        for (Filme filme : lista) {

            Map<String, List<String>> elenco = filme.getElenco();

            for (Map.Entry<String, List<String>> entry : elenco.entrySet()) {

                String funcaoAtual = Servicos.normalizarTitulo(entry.getKey());

                if (funcaoAtual.equalsIgnoreCase(funcaoBusca)) {

                    for (String pessoa : entry.getValue()) {

                        if (Servicos.normalizarTitulo(pessoa).equalsIgnoreCase(nomeBusca)) {

                            resultados_da_busca.add(filme);
                            break;

                        }
                    }
                }
            }
        }

        return resultados_da_busca;
    }

    /**
     * Retorna uma lista completa de filmes cadastrados no sistema
     *
     * @return lista completa de filmes cadastrados no sistema
     */
    public static List<Filme> getFilmes() {
        return filmes;
    }

    /**
     * Retorna a duração do filme em minutos
     *
     * @return duração do filme em minutos
     */
    public static int getDuracao(Filme filme) { return filme.getDuracao(); }

    /**
     * Remove todas os filmes cadastradas no sistema.
     * Este método limpa completamente a lista de filmes, removendo todos os registros
     * da memória. É útil principalmente para fins de teste ou quando é necessário
     * reinicializar o sistema.
     *
     * <p>Importante: Esta operação não pode ser desfeita e todos os dados serão perdidos.</p>
     *
     * @see #filmes
     */
    public static void limparFilmes() { filmes.clear(); }

    /**
     * Remove um filme específico do sistema.
     *
     * @param midia filme a ser removido
     */
    public static void removerMidia(Filme midia) {
        filmes.remove(midia);
    }

    /**
     * Define uma nova duração para o filme.
     *
     * @param filme filme a ser modificado
     * @param duracao nova duração em minutos
     */
    public static void setDuracao(Filme filme, int duracao) { filme.setDuracao(duracao); }

    /**
     * Define um novo diretor para o filme.
     *
     * @param filme filme a ser modificado
     * @param direcao novo nome do diretor
     */
    public static void setDirecao(Filme filme, String direcao) { filme.setDirecao(direcao); }

    /**
     * Retorna o diretor do filme.
     *
     * @param filme filme que será consultado
     */
    public static String getDirecao(Filme filme) { return filme.getDirecao(); }

    /**
     * Define um novo roteirista para o filme.
     *
     * @param filme filme a ser modificado
     * @param roteiro novo nome do roteirista
     */
    public static void setRoteiro(Filme filme, String roteiro) { filme.setRoteiro(roteiro); }

    /**
     * Retorna o roteirista do filme.
     *
     * @param filme filme a ser retornado o roteirista
     */
    public static String getRoteiro(Filme filme) { return filme.getRoteiro(); }

    /**
     * Define um novo título original para o filme.
     *
     * @param filme filme a ser modificado
     * @param novoTitulo novo título original
     */
    public static void setTituloOriginal(Filme filme, String novoTitulo) { filme.setTituloOriginal(novoTitulo); }

    /**
     * Retorna o título original do filme.
     *
     * @param filme filme a ser verificado
     */
    public static String getTituloOriginal(Filme filme) { return filme.getTituloOriginal(); }

    /**
     * Retorna um mapa com as categorias e respectivos membros do elenco de um filme
     *
     * @param filme filme do qual obter o elenco
     * @return mapa com categorias e respectivos membros do elenco
     */
    public static Map<String, List<String>> getElenco(Filme filme) { return filme.getElenco(); }

    /**
     * Retorna lista de plataformas onde o filme está disponível
     *
     * @param filme filme do qual obter as plataformas
     * @return lista de plataformas onde o filme está disponível
     */
    public static List<String> getOndeAssistir(Filme filme) { return filme.getOndeAssistir(); }

    /**
     * Adiciona novas plataformas de streaming à lista existente de um filme.
     * Este método permite expandir as opções de onde o filme pode ser assistido
     * sem substituir as plataformas já cadastradas.
     *
     * @param filme filme ao qual serão adicionadas as novas plataformas
     * @param ondeAssistir lista de novas plataformas de streaming a serem adicionadas
     */
    public static void addOndeAssistir(Filme filme, List<String> ondeAssistir) { filme.addOndeAssistir(ondeAssistir); }

    /**
     * Define uma nova lista completa de plataformas de streaming para um filme.
     * Este método substitui todas as plataformas existentes pelas novas informadas.
     * Útil quando é necessário atualizar completamente as informações de disponibilidade.
     *
     * @param filme filme a ter sua lista de plataformas atualizada
     * @param ondeAssistir nova lista de plataformas que substituirá a atual
     */
    public static void setOndeAssistir(Filme filme, List<String> ondeAssistir) { filme.setOndeAssistir(ondeAssistir); }

    /**
     * Remove uma plataforma específica da lista de locais onde o filme pode ser assistido.
     * A comparação do nome da plataforma é feita ignorando maiúsculas/minúsculas e acentuação.
     *
     * @param filme filme do qual a plataforma será removida
     * @param ondeAssistir nome da plataforma a ser removida
     */
    public static void removerOndeAssistir(Filme filme, String ondeAssistir) { filme.removerOndeAssistir(ondeAssistir); }

    /**
     * Salva todas os filmes cadastrados no sistema em um arquivo.
     * Este método serializa a lista de filmes e a armazena em um arquivo binário
     * no diretório especificado por {@link #ARQUIVO}.
     *
     * <p>O método utiliza um {@link ObjectOutputStream} para realizar a serialização
     * dos dados. Em caso de erro durante a operação de salvamento, o stack trace
     * do erro será impresso no console.</p>
     *
     * @see #ARQUIVO
     * @see ObjectOutputStream
     */
    public static void salvarFilmes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(filmes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega os filmes previamente salvas do arquivo para o sistema.
     * Este método deserializa a lista de séries armazenada no arquivo binário
     * especificado por {@link #ARQUIVO}.
     *
     * <p>O método utiliza um {@link ObjectInputStream} para realizar a deserialização
     * dos dados.</p>
     *
     * @return {@code true} se os filmes foram carregadas com sucesso,
     *         {@code false} se ocorreu algum erro durante o carregamento
     * @see #ARQUIVO
     * @see ObjectInputStream
     */
    public static boolean carregarFilmes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            filmes = (List<Filme>) ois.readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}