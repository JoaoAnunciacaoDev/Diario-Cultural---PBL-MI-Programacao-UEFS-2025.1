package View;

import Controller.*;

import java.util.Scanner;

/**
 * Classe responsável por gerenciar o menu principal do sistema Diário Cultural.
 * Esta classe implementa a interface com o usuário através de um menu baseado em console,
 * oferecendo opções para gerenciamento de diferentes tipos de mídias.
 *
 * <p>O menu principal oferece as seguintes funcionalidades:</p>
 * <ul>
 *     <li>Cadastro de novas mídias</li>
 *     <li>Listagem de mídias existentes</li>
 *     <li>Atualização de mídias</li>
 *     <li>Remoção de mídias</li>
 *     <li>Avaliação de mídias</li>
 *     <li>Busca de mídias</li>
 * </ul>
 */

public class MenuPrincipal {

    private final Scanner scanner;
    private final LivroController livroController;
    private final FilmeController filmeController;
    private final SerieController serieController;

    /**
     * Construtor que inicializa o menu principal com as dependências necessárias.
     *
     * @param scanner Scanner para entrada de dados do usuário
     * @param livroController Controlador para operações com livros
     * @param filmeController Controlador para operações com filmes
     * @param serieController Controlador para operações com séries
     */
    public MenuPrincipal(Scanner scanner, LivroController livroController, FilmeController filmeController,
                         SerieController serieController) {

        this.scanner = scanner;
        this.livroController = livroController;
        this.filmeController = filmeController;
        this.serieController = serieController;

    }

    /**
     * Inicia o menu principal e mantém o sistema em execução até que
     * o usuário escolha sair.
     */
    public void iniciar() {
        boolean rodando = true;

        while (rodando) {

            rodando = exibirMenuPrincipal();

        }
    }

    /**
     * Exibe as opções do menu principal e aguarda a entrada do usuário.
     *
     * @return boolean indicando se o sistema deve continuar executando
     */
    private boolean exibirMenuPrincipal() {

        System.out.println("\n--- Diário Cultural ---");
        System.out.println("\n[1] Cadastrar Mídia");
        System.out.println("[2] Listar Mídias");
        System.out.println("[3] Atualizar Mídias");
        System.out.println("[4] Deletar Mídias");
        System.out.println("[5] Adicionar Avaliação");
        System.out.println("[6] Visualizar Avaliações");
        System.out.println("[7] Sobreescrever Avaliação");
        System.out.println("[8] Deletar Avaliação");
        System.out.println("[9] Buscar Mídia");
        System.out.println("[10] Sair");
        System.out.print("\nEscolha uma opção: ");

        String opcao = scanner.nextLine();
        return processarOpcaoMenuPrincipal(opcao);

    }

    /**
     * Processa a opção escolhida pelo usuário no menu principal.
     * Este método gerencia todas as operações disponíveis no menu, incluindo:
     * <ul>
     *     <li>Cadastro de novas mídias</li>
     *     <li>Listagem de mídias</li>
     *     <li>Atualização de mídias</li>
     *     <li>Remoção de mídias</li>
     *     <li>Gerenciamento de avaliações</li>
     *     <li>Busca de mídias</li>
     * </ul>
     *
     * @param opcao String contendo a opção escolhida pelo usuário
     * @return boolean indicando se o sistema deve continuar executando
     */
    private boolean processarOpcaoMenuPrincipal(String opcao) {

        return switch (opcao) {

            case "1" -> {

                ViewCadastroGeral.processarCadastro(livroController, filmeController, serieController, scanner);
                yield true;

            }
            case "2" -> {

                ViewLista.processarListagem(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "3" -> {

                ViewAtualizar.processarAtualizacao(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "4" -> {

                ViewDeletar.processarRemocao(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "5" -> {

                ViewAvaliacao.processarAvaliacao(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "6" -> {

                ViewAvaliacao.visualizarAvaliacoes(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "7" -> {

                ViewAvaliacao.sobreescreverAvaliacao(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "8" -> {

                ViewAvaliacao.deletarAvaliacao(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "9" -> {

                ViewBusca.processarBusca(livroController, filmeController, serieController, scanner);
                yield true;

            }

            case "10" -> {

                System.out.println("\nAté a próxima!");
                yield false;

            }


            default -> {

                System.out.println("\nOpção inválida. Tente novamente.");
                yield true;

            }

        };

    }

}