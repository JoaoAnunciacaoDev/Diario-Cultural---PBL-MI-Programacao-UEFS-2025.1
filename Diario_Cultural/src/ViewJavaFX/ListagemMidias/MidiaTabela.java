package ViewJavaFX.ListagemMidias;

import Controller.MidiaController;
import Model.Midia;

/**
 * Representa uma entrada de mídia para uso em listas.
 *
 * Esta classe serve para armazenar informações básicas de uma mídia,
 * facilitando a exibição e manipulação de dados em interfaces gráficas de listagem.
 *
 * Contém informações resumidas como título, tipo de mídia, nota e ano de lançamento,
 * além de manter uma referência para o objeto de mídia original e seu controlador.
 */
public class MidiaTabela {

    /** Mídia original associada a esta entrada de tabela */
    private Midia midiaOriginal;

    /** Controlador responsável pela mídia */
    private MidiaController controller;

    /** Título da mídia */
    private final String titulo;

    /** Tipo da mídia (ex: Livro, Filme, Série) */
    private final String tipoMidia;

    /** Nota ou avaliação da mídia */
    private final float nota;

    /** Ano de lançamento ou publicação da mídia */
    private final int anoLancamento;

    /**
     * Construtor para criar uma entrada de mídia para tabela.
     *
     * @param titulo Título da mídia
     * @param tipoMidia Tipo da mídia (Livro, Filme, Série)
     * @param nota Nota ou avaliação da mídia
     * @param ano Ano de lançamento ou publicação
     * @param midiaOriginal Objeto de mídia original
     * @param controller Controlador associado à mídia
     */
    public MidiaTabela(String titulo, String tipoMidia, float nota, int ano, Midia midiaOriginal, MidiaController controller) {
        this.titulo = titulo;
        this.tipoMidia = tipoMidia;
        this.nota = nota;
        this.anoLancamento = ano;
        this.midiaOriginal = midiaOriginal;
        this.controller = controller;
    }

    /**
     * Obtém o título da mídia.
     *
     * @return Título da mídia
     */
    public String getTitulo() { return titulo; }

    /**
     * Obtém o tipo da mídia.
     *
     * @return Tipo da mídia (Livro, Filme, Série)
     */
    public String getTipoMidia() { return tipoMidia; }

    /**
     * Obtém a nota da mídia.
     *
     * @return Nota ou avaliação da mídia
     */
    public float getNota() { return nota; }

    /**
     * Obtém o ano de lançamento da mídia.
     *
     * @return Ano de lançamento ou publicação
     */
    public int getAnoLancamento() { return anoLancamento; }

    /**
     * Obtém a mídia original associada a esta entrada de tabela.
     *
     * @return Objeto Midia original
     */
    public Midia getMidiaOriginal() { return midiaOriginal; }

    /**
     * Obtém o controlador associado à mídia.
     *
     * @return Controlador da mídia
     */
    public MidiaController getMidiaController() { return controller; }
}
