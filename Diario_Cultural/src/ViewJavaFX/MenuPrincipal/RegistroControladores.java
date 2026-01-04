package ViewJavaFX.MenuPrincipal;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.SerieController;

/**
 * Classe utilitária responsável por gerenciar e fornecer acesso centralizado
 * aos controladores das diferentes mídias no sistema.
 *
 * <p>Esta classe implementa um padrão de registro singleton-like para
 * controladores de livros, filmes e séries, permitindo acesso global
 * e inicialização única dos controladores.</p>
 *
 * @see LivroController
 * @see FilmeController
 * @see SerieController
 */
public class RegistroControladores {

    /**
     * Instância estática do controlador de livros.
     * Mantém o estado global dos livros no sistema.
     */
    private static LivroController livroController = new LivroController();

    /**
     * Instância estática do controlador de filmes.
     * Mantém o estado global dos filmes no sistema.
     */
    private static FilmeController filmeController = new FilmeController();

    /**
     * Instância estática do controlador de séries.
     * Mantém o estado global das séries no sistema.
     */
    private static SerieController serieController = new SerieController();

    /**
     * Inicializa ou reinicializa todos os controladores do sistema.
     *
     * <p>Este método garante que cada controlador seja uma nova instância,
     * útil para redefinir o estado dos controladores quando necessário.</p>
     */
    public static void initialize() {
        livroController = new LivroController();
        filmeController = new FilmeController();
        serieController = new SerieController();
    }

    /**
     * Obtém a instância atual do controlador de livros.
     *
     * @return Instância do controlador de livros
     */
    public static LivroController getLivroController() {
        return livroController;
    }

    /**
     * Obtém a instância atual do controlador de filmes.
     *
     * @return Instância do controlador de filmes
     */
    public static FilmeController getFilmeController() {
        return filmeController;
    }

    /**
     * Obtém a instância atual do controlador de séries.
     *
     * @return Instância do controlador de séries
     */
    public static SerieController getSerieController() {
        return serieController;
    }

}
