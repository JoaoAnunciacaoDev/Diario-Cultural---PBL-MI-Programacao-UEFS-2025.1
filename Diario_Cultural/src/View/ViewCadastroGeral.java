package View;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.SerieController;

import java.util.Scanner;

/**
 * Classe responsável por gerenciar a interface de cadastro de diferentes tipos de mídia.
 * Oferece um menu unificado para cadastro de livros, filmes e séries, direcionando
 * para as views específicas de cada tipo de mídia.
 *
 * <p>Esta classe atua como um ponto central de distribuição para as diferentes
 * operações de cadastro, seguindo o padrão de design Facade para simplificar
 * a interface de cadastro para o usuário.</p>
 */
public class ViewCadastroGeral {

    /**
     * Processa o cadastro de uma nova mídia baseado na escolha do usuário.
     * Este método apresenta as opções de cadastro disponíveis e direciona
     * para a view específica de acordo com a escolha do usuário.
     *
     * @param livroController controlador para operações com livros
     * @param filmeController controlador para operações com filmes
     * @param serieController controlador para operações com séries
     * @param scanner objeto Scanner para leitura de entrada do usuário
     */
    public static void processarCadastro(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {

        System.out.println("\n[1] Cadastrar Livro");
        System.out.println("[2] Cadastrar Filme");
        System.out.println("[3] Cadastrar Série");
        System.out.print("\nEscolha uma opção: ");

        String opcao = scanner.nextLine();

        switch (opcao) {

            case "1" -> ViewCadastroLivro.cadastrarLivro(livroController, scanner);

            case "2" -> ViewCadastroFilme.cadastrarFilme(filmeController, scanner);

            case "3" -> {

                System.out.println("\n[1] Cadastrar Série");
                System.out.println("[2] Cadastrar Temporada");
                System.out.print("\nEscolha uma opção: ");

                opcao = scanner.nextLine();

                switch (opcao) {

                    case "1" -> ViewCadastroSerie.cadastrarSerie(serieController, scanner);

                    case "2" -> ViewCadastroSerie.cadastrarTemporada(serieController, scanner);

                    default -> System.out.println("\nOpção inválida. Voltando ao menu principal...");

                }

            }

        }
    }
}
