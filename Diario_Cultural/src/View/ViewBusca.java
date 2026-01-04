package View;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.MidiaController;
import Controller.SerieController;

import Model.Filme;
import Model.Livro;
import Model.Midia;
import Model.Serie;
import Service.Servicos;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por gerenciar as operações de busca de mídias no sistema.
 * Permite realizar buscas por diferentes critérios como título, gênero, ano,
 * além de critérios específicos para cada tipo de mídia.
 */
public class ViewBusca {

    /**
     * Processa a operação de busca principal, apresentando as opções disponíveis
     * e direcionando para o método de busca específico escolhido pelo usuário.
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void processarBusca(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {

        List<? extends Midia> midiaLista = ViewLista.selecionarLista(livroController, filmeController, serieController, scanner);

        if (midiaLista.isEmpty()) return;

        System.out.println("\n[1] Buscar por Título");
        System.out.println("[2] Buscar por Gênero");
        System.out.println("[3] Buscar por Ano de Lançamento");

        if (midiaLista.getFirst() instanceof Livro) {

            System.out.println("[4] Buscar por Autor");
            System.out.println("[5] Buscar por ISBN");

        } else if (midiaLista.getFirst() instanceof Filme) {

            System.out.println("[4] Buscar por Diretor");
            System.out.println("[5] Buscar por Ator no Elenco");

        }

        System.out.print("\nEscolha uma opção: ");

        String opcao = scanner.nextLine();

        switch (opcao) {

            case "1" -> {

                List<? extends Midia> resultadosBusca = ViewBusca.buscarPorTitulo(midiaLista, scanner);

                if (resultadosBusca.isEmpty()) return;

                String resposta = "não";

                resposta = serieVerificacao(scanner, resultadosBusca, resposta);

                if (! resposta.equalsIgnoreCase("sim")) resultadosBusca.forEach(System.out::println);

            }

            case "2" -> {

                List<? extends Midia> resultadosBusca = ViewBusca.buscarPorGenero(midiaLista, scanner);

                if (resultadosBusca.isEmpty()) return;

                String resposta = "não";

                resposta = serieVerificacao(scanner, resultadosBusca, resposta);

                if (! resposta.equalsIgnoreCase("sim")) resultadosBusca.forEach(System.out::println);

            }

            case "3" -> {

                List<? extends Midia> resultadosBusca = ViewBusca.buscarPorAnoLancamento(midiaLista);

                if (resultadosBusca.isEmpty()) return;

                String resposta = "não";

                resposta = serieVerificacao(scanner, resultadosBusca, resposta);

                if (! resposta.equalsIgnoreCase("sim")) resultadosBusca.forEach(System.out::println);

            }

            case "4" -> {

                List<? extends Midia> resultadosBusca;

                if (!midiaLista.isEmpty() && midiaLista.getFirst() instanceof Livro) {

                    resultadosBusca = ViewBusca.buscarPorAutor((List<Livro>) midiaLista, scanner);

                } else if (!midiaLista.isEmpty() && midiaLista.getFirst() instanceof Filme) {

                    resultadosBusca = ViewBusca.buscarPorDiretor((List<Filme>) midiaLista, scanner);

                } else {

                    System.out.println("\nOpção inválida. Voltando ao menu principal...");
                    break;

                }

                resultadosBusca.forEach(System.out::println);

            }

            case "5" -> {

                List<? extends Midia> resultadosBusca;

                if (!midiaLista.isEmpty() && midiaLista.getFirst() instanceof Livro) {

                    resultadosBusca = ViewBusca.buscarPorISBN((List<Livro>) midiaLista, scanner);

                } else if (!midiaLista.isEmpty() && midiaLista.getFirst() instanceof Filme) {

                    resultadosBusca = ViewBusca.buscarPorAtor((List<Filme>) midiaLista, scanner);

                } else {

                    System.out.println("\nOpção inválida. Voltando ao menu principal...");
                    break;

                }

                if (resultadosBusca.isEmpty()) return;

                resultadosBusca.forEach(System.out::println);

            }

            default -> System.out.println("\nOpção inválida. Voltando ao menu principal...");

        }

    }

    /**
     * Verifica se a mídia é uma série e permite visualizar detalhes das temporadas.
     *
     * @param scanner objeto para leitura de entrada do usuário
     * @param resultadosBusca lista de mídias encontradas na busca
     * @param resposta resposta inicial do usuário
     * @return resposta atualizada do usuário
     */
    private static String serieVerificacao(Scanner scanner, List<? extends Midia> resultadosBusca, String resposta) {

        if (resultadosBusca.getFirst() instanceof Serie) {

            System.out.println("\nDeseja visualizar detalhes das temporadas? (sim / não) ");
            resposta = scanner.nextLine();

        }

        if (resposta.equalsIgnoreCase("sim")) {

            for (Midia midia : resultadosBusca) {

                interagirComSerie((Serie) midia);

            }

        }

        return resposta;

    }

    /**
     * Realiza busca por título em uma lista de mídias.
     *
     * @param midiaLista lista de mídias a serem pesquisadas
     * @param scanner objeto para leitura de entrada do usuário
     * @return lista de mídias que correspondem ao título buscado
     */
    public static List<? extends Midia> buscarPorTitulo(List<? extends Midia> midiaLista, Scanner scanner) {

        System.out.print("\nInsira o título da Mídia que deseja buscar: ");
        String titulo = scanner.nextLine();

        List<? extends Midia> resultados_da_busca = MidiaController.busca_titulo(titulo, midiaLista);

        if (resultados_da_busca.isEmpty()) System.out.println("\nNenhum item encontrado para a busca.");

        return resultados_da_busca;

    }

    /**
     * Realiza busca por gênero em uma lista de mídias.
     *
     * @param midiaLista lista de mídias a serem pesquisadas
     * @param scanner objeto para leitura de entrada do usuário
     * @return lista de mídias que correspondem ao gênero buscado
     */
    public static List<? extends Midia> buscarPorGenero(List<? extends Midia> midiaLista, Scanner scanner) {

        System.out.print("Digite o gênero: ");
        String genero = scanner.nextLine();

        List<? extends Midia> resultados_da_busca = MidiaController.busca_genero(genero, midiaLista);

        if (resultados_da_busca.isEmpty()) System.out.println("\nNenhum item encontrado para a busca.");

        return resultados_da_busca;

    }

    /**
     * Realiza busca por ano de lançamento em uma lista de mídias.
     *
     * @param midiaLista lista de mídias a serem pesquisadas
     * @return lista de mídias que correspondem ao ano buscado
     */
    public static List<? extends Midia> buscarPorAnoLancamento(List<? extends Midia> midiaLista) {

        System.out.print("Digite o ano de lançamento: ");
        int ano = (int) Servicos.getValidarEntrada("int");

        List<? extends Midia> resultados_da_busca = MidiaController.busca_ano(ano, midiaLista);

        if (resultados_da_busca.isEmpty()) System.out.println("\nNenhum item encontrado para a busca.");

        return resultados_da_busca;

    }

    /**
     * Realiza busca por autor em uma lista de livros.
     *
     * @param midiaLista lista de livros a serem pesquisados
     * @param scanner objeto para leitura de entrada do usuário
     * @return lista de livros que correspondem ao autor buscado
     */
    public static List<Livro> buscarPorAutor(List<Livro> midiaLista, Scanner scanner) {

        System.out.print("Digite o nome do Autor: ");
        String autor = scanner.nextLine();

        List<Livro> resultados_da_busca = LivroController.busca_autor(autor, midiaLista);

        if (resultados_da_busca.isEmpty()) {
            System.out.println("\nNenhum item encontrado para a busca.");
        }

        return resultados_da_busca;

    }

    /**
     * Realiza busca por diretor em uma lista de filmes.
     *
     * @param midiaLista lista de filmes a serem pesquisados
     * @param scanner objeto para leitura de entrada do usuário
     * @return lista de filmes que correspondem ao diretor buscado
     */
    public static List<Filme> buscarPorDiretor(List<Filme> midiaLista, Scanner scanner) {

        System.out.print("Digite o nome do Diretor: ");
        String diretor = scanner.nextLine();

        List<Filme> resultados_da_busca = FilmeController.busca_diretor(diretor, midiaLista);

        if (resultados_da_busca.isEmpty()) {
            System.out.println("\nNenhum item encontrado para a busca.");
        }

        return resultados_da_busca;

    }

    /**
     * Realiza busca por ISBN em uma lista de livros.
     *
     * @param midiaLista lista de livros a serem pesquisados
     * @param scanner objeto para leitura de entrada do usuário
     * @return lista de livros que correspondem ao ISBN buscado
     */
    public static List<Livro> buscarPorISBN(List<Livro> midiaLista, Scanner scanner) {

        System.out.print("\nInsira o ISBN da Mídia que deseja buscar: ");
        String isbn = scanner.nextLine();

        List<Livro> resultados_da_busca = LivroController.busca_isbn(isbn, midiaLista);

        if (resultados_da_busca.isEmpty()) {
            System.out.println("\nNenhum item encontrado para a busca.");
        }

        return resultados_da_busca;

    }

    /**
     * Realiza busca por ator/função no elenco em uma lista de filmes.
     *
     * @param midiaLista lista de filmes a serem pesquisados
     * @param scanner objeto para leitura de entrada do usuário
     * @return lista de filmes que correspondem ao ator/função buscado
     */
    public static List<Filme> buscarPorAtor(List<Filme> midiaLista, Scanner scanner) {

        System.out.print("Digite a função: ");
        String funcao = scanner.nextLine();

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        List<Filme> resultados_da_busca = FilmeController.busca_elenco(funcao, nome, midiaLista);

        if (resultados_da_busca.isEmpty()) {
            System.out.println("\nNenhum item encontrado para a busca.");
        }

        return resultados_da_busca;

    }

    /**
     * Exibe detalhes interativos de uma série, incluindo informações sobre
     * temporadas, elenco e avaliações.
     *
     * @param serie objeto Serie a ser detalhado
     */
    public static void interagirComSerie(Serie serie) {

        if (serie.getTemporadas().isEmpty()) {
            System.out.println("Essa série não possui temporadas.");
            return;
        }

        System.out.println(serie);

        for (int i = 0; i < serie.getTemporadas().size(); i++) {

            var temporada = serie.getTemporadas().get(i);

            System.out.printf("""
                    Temporada %d:
                      Elenco:
                    """, temporada.getNumeroTemporada());

            if (temporada.getElenco().isEmpty()) {

                System.out.println("    Nenhum ator cadastrado.");

            } else {

                for (var entry : temporada.getElenco().entrySet()) {

                    String nomes = String.join(", ", entry.getValue());
                    System.out.printf("    %s: %s\n", entry.getKey(), nomes);

                }

            }

            var avaliacoes = temporada.getAvaliacoes();

            if (avaliacoes.isEmpty()) {

                System.out.println("  Nenhuma avaliação registrada.");

            } else {

                System.out.println("  Avaliações:");

                for (var avaliacao : avaliacoes) {

                    System.out.println(avaliacao);

                }

            }

        }

    }

}
