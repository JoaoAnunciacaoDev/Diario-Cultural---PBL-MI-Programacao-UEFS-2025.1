package View;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.SerieController;

import Testes.CadastroPreDefinido;

import java.util.Scanner;

/**
 * Classe principal do sistema do Diário Cultural.
 * Esta classe é responsável por inicializar o sistema, criar as instâncias
 * necessárias dos controllers e iniciar o menu principal.
 *
 * O sistema permite o gerenciamento de diferentes tipos de mídias:
 * <ul>
 *     <li>Livros</li>
 *     <li>Filmes</li>
 *     <li>Séries</li>
 * </ul>
 *
 * @author João Victor Anunciação da Silva
 * @version 1.0
 * @since 21/03/2025
 */

public class Main {

    /**
     * Método principal que inicializa e executa o sistema.
     * Este método realiza as seguintes operações:
     * <ol>
     *     <li>Inicializa o scanner para entrada de dados</li>
     *     <li>Cria as instâncias dos controllers necessários</li>
     *     <li>Carrega dados predefinidos para teste (Para facilitar os testes)</li>
     *     <li>Inicia o menu principal do sistema</li>
     * </ol>
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        final LivroController livroController = new LivroController();
        final FilmeController filmeController = new FilmeController();
        final SerieController serieController = new SerieController();

        boolean livrosCarregados = livroController.carregarLivros();
        boolean filmesCarregados = filmeController.carregarFilmes();
        boolean seriesCarregados = serieController.carregarSeries();

        if (!livrosCarregados && !filmesCarregados && !seriesCarregados) {
            CadastroPreDefinido.cadastrarMidiasPadrao(livroController, filmeController, serieController);
        }

        MenuPrincipal menu = new MenuPrincipal(scanner, livroController, filmeController, serieController);
        menu.iniciar();

        scanner.close();

    }

}
