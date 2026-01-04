package Controller;

import Model.Livro;
import Service.Servicos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador específico para gerenciamento de livros, herdando de MidiaController.
 * Fornece funcionalidades para cadastro, busca e manipulação de livros no sistema.
 */
public class LivroController extends MidiaController {

    /** Lista estática que armazena todos os livros cadastrados no sistema */
    private static List<Livro> livros = new ArrayList<>();

    private static final String DIRETORIO = "src/arquivos";
    private static final String ARQUIVO = DIRETORIO + "/livros.dat";

    /**
     * Construtor para um novo controlador de livros.
     * Este construtor cria a estrutura de diretórios necessária para o armazenamento persistente
     * de dados das séries, garantindo que o diretório especificado em {@link #DIRETORIO} exista.
     *
     * <p>Ao instanciar um novo LivroController:</p>
     * <ul>
     *     <li>Verifica a existência do diretório de armazenamento</li>
     *     <li>Cria o diretório se ele não existir</li>
     * </ul>
     *
     * @see #criarDiretorioSeNaoExiste()
     */
    public LivroController() {
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
     * Cadastra um novo livro no sistema.
     *
     * @param titulo título do livro
     * @param autor nome do autor
     * @param editora nome da editora
     * @param generos lista de gêneros do livro
     * @param ano_de_publicacao ano em que o livro foi publicado
     * @param isbn código ISBN do livro
     * @param possui indica se o usuário possui um exemplar físico do livro
     */
    public void cadastrarLivro(String titulo, String autor, String editora, List<String> generos, int ano_de_publicacao, String isbn, boolean possui) {
        livros.add(new Livro(titulo, generos, ano_de_publicacao, autor, isbn, possui, editora));
        salvarLivros();
    }

    /**
     * Busca livros por autor (busca exata, ignorando case e acentuação).
     *
     * @param autor nome do autor a ser buscado
     * @param lista lista de livros onde realizar a busca
     * @return lista de livros do autor especificado
     */
    public static List<Livro> busca_autor(String autor, List<Livro> lista) {

        List<Livro> resultados_da_busca =  new ArrayList<>();

        for (Livro midia : lista) {
            if (Servicos.normalizarTitulo(midia.getAutor()).equalsIgnoreCase(Servicos.normalizarTitulo(autor))) {
                resultados_da_busca.add(midia);
            }
        }

        return resultados_da_busca;

    }

    /**
     * Busca livros por ISBN (busca exata, ignorando case).
     *
     * @param isbn código ISBN a ser buscado
     * @param lista lista de livros onde realizar a busca
     * @return lista de livros com o ISBN especificado
     */
    public static List<Livro> busca_isbn(String isbn, List<Livro> lista) {

        List<Livro> resultados_da_busca =  new ArrayList<>();

        for (Livro midia : lista) {
            if (Servicos.normalizarTitulo(midia.getIsbn()).equalsIgnoreCase(Servicos.normalizarTitulo(isbn))) {
                resultados_da_busca.add(midia);
            }
        }

        return resultados_da_busca;

    }

    /**
     * Retorna uma lista completa de livros cadastrados no sistema
     *
     * @return lista completa de livros cadastrados no sistema
     */
    public static List<Livro> getLivros() {
        return livros;
    }

    /**
     * Remove todas os livros cadastrados no sistema.
     * Este método limpa completamente a lista de livros, removendo todos os registros
     * da memória. É útil principalmente para fins de teste ou quando é necessário
     * reinicializar o sistema.
     *
     * <p>Importante: Esta operação não pode ser desfeita e todos os dados serão perdidos.</p>
     *
     * @see #livros
     */
    public static void limparLivros() { livros.clear(); }

    /**
     * Remove um livro específico do sistema.
     *
     * @param midia livro a ser removido
     */
    public static void removerMidia(Livro midia) {
        livros.remove(midia);
    }

    /**
     * Retorna o nome do autor do livro
     *
     * @param livro retorna o autor
     */
    public static String getAutor(Livro livro) { return livro.getAutor(); }

    /**
     * Retorna o nome da editora do livro
     *
     * @param livro retorna a editora
     */
    public static String getEditora(Livro livro) { return livro.getEditora(); }

    /**
     * Retorna o ISBN do livro
     *
     * @param livro retorna o ISBN
     */
    public static String getISBN(Livro livro) { return livro.getIsbn(); }

    /**
     * Retorna se possui exemplar ou não
     *
     * @param livro
     */
    public static boolean getPossuiExemplar(Livro livro) { return livro.getPossuiExemplar(); }

    /**
     * Define um novo autor para o livro.
     *
     * @param livro livro a ser modificado
     * @param novoAutor novo nome do autor
     */
    public static void setAutor(Livro livro, String novoAutor) { livro.setAutor(novoAutor); }

    /**
     * Define uma nova editora para o livro.
     *
     * @param livro livro a ser modificado
     * @param novaEditora nova editora
     */
    public static void setEditora(Livro livro, String novaEditora) { livro.setEditora(novaEditora); }

    /**
     * Define um novo ISBN para o livro.
     *
     * @param livro livro a ser modificado
     * @param novoISBN novo código ISBN
     */
    public static void setIsbn(Livro livro, String novoISBN) { livro.setIsbn(novoISBN); }

    /**
     * Atualiza o status de posse do exemplar físico.
     *
     * @param livro livro a ser modificado
     * @param novaResposta novo status de posse (true se possui, false caso contrário)
     */
    public static void setPossuiExemplar(Livro livro, boolean novaResposta) { livro.setPossuiExemplar(novaResposta); }

    /**
     * Salva todas os livros cadastrados no sistema em um arquivo.
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
    public static void salvarLivros() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(livros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega os livros previamente salvas do arquivo para o sistema.
     * Este método deserializa a lista de livros armazenada no arquivo binário
     * especificado por {@link #ARQUIVO}.
     *
     * <p>O método utiliza um {@link ObjectInputStream} para realizar a deserialização
     * dos dados.</p>
     *
     * @return {@code true} se os livros foram carregadas com sucesso,
     *         {@code false} se ocorreu algum erro durante o carregamento
     * @see #ARQUIVO
     * @see java.io.ObjectInputStream
     */
    public static boolean carregarLivros() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            livros = (List<Livro>) ois.readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
