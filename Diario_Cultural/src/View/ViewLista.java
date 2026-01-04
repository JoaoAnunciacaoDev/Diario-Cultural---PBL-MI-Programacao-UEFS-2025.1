package View;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.MidiaController;
import Controller.SerieController;

import Model.*;
import Service.Servicos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela interface de listagem de mídias do sistema.
 * Fornece funcionalidades para listar, ordenar e filtrar diferentes tipos de mídias.
 */

public class ViewLista {

    /**
     * Processa as opções de listagem de mídias disponíveis no sistema.
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void processarListagem(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {

        System.out.println("\n[1] Listar todas as mídias");
        System.out.println("[2] Listar mídia específica");
        System.out.print("\nEscolha uma opção: ");

        String opcao = scanner.nextLine();

        switch (opcao) {

            case "1" -> listarTodasMidias(livroController.getLivros(), filmeController.getFilmes(), serieController.getSeries(), scanner);

            case "2" -> {

                List<? extends Midia> midiaLista = selecionarLista(livroController, filmeController, serieController, scanner);
                escolhaOrdenacao(midiaLista, scanner);

            }

            default -> System.out.println("\nOpção inválida. Voltando ao menu principal...");

        }

    }

    /**
     * Apresenta opções de ordenação para uma lista de mídias.
     *
     * @param midiaLista lista de mídias a ser ordenada
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void escolhaOrdenacao(List<? extends Midia> midiaLista, Scanner scanner) {

        System.out.println("\n--- Ordenar Lista ---");
        System.out.println("\n[1] Ordenar por bem avaliados");
        System.out.println("[2] Ordenar por mal avaliados");
        System.out.print("\nEscolha uma opção: ");

        String opcao = scanner.nextLine();

        switch (opcao) {

            case "1" -> {
                midiaLista = MidiaController.ordenarBemAvaliado(midiaLista);
            }

            case "2" -> {
                midiaLista = MidiaController.ordenarMalAvaliado(midiaLista);
            }

            default -> {
                System.out.println("\nOpção inválida. Voltando ao menu principal...");
                return;
            }

        }

        escolhaFiltragem(midiaLista, scanner);

    }

    /**
     * Apresenta opções de filtragem para uma lista de mídias.
     *
     * @param midiaLista lista de mídias a ser filtrada
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void escolhaFiltragem(List<? extends Midia> midiaLista, Scanner scanner) {

        System.out.println("\n--- Filtragem Lista ---");
        System.out.println("\n[1] Filtrar por Gênero");
        System.out.println("[2] Filtrar por Ano de lançamento");
        System.out.println("[3] Sem Filtragem");
        System.out.print("\nEscolha uma opção: ");

        String opcao = scanner.nextLine();

        switch (opcao) {

            case "1" -> {

                System.out.print("\nGênero para Filtrar: ");
                String genero = scanner.nextLine();

                listarIndividual(MidiaController.filtrarPorGenero(midiaLista, genero));

            }

            case "2" -> {

                System.out.print("\nAno para Filtrar: ");
                int anoLancamento = (int) Servicos.getValidarEntrada("int");
                listarIndividual(MidiaController.filtrarPorAno(midiaLista, anoLancamento));

            }

            case "3" -> listarIndividual(midiaLista);

            default -> System.out.println("\nOpção inválida. Voltando ao menu principal...");

        }

    }

    /**
     * Permite ao usuário selecionar qual tipo de mídia deseja listar.
     *
     * @param livros controlador de livros
     * @param filmes controlador de filmes
     * @param series controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     * @return lista de mídias do tipo selecionado
     */
    public static List<? extends Midia> selecionarLista(LivroController livros, FilmeController filmes, SerieController series, Scanner scanner) {

        System.out.println("\nSelecione o tipo de mídia:");
        System.out.println("\n[1] Livro");
        System.out.println("[2] Filme");
        System.out.println("[3] Série");
        System.out.print("Escolha uma opção: ");

        String opcao = scanner.nextLine();

        return switch (opcao) {

            case "1" -> livros.getLivros();

            case "2" -> filmes.getFilmes();

            case "3" -> series.getSeries();

            default -> {

                System.out.println("\nOpção inválida.");
                yield Collections.emptyList();

            }

        };
    }

    /**
     * Lista os detalhes de cada mídia em uma lista específica.
     * Exibe informações como título, ano de lançamento, nota e,
     * no caso de séries, o número de temporadas.
     *
     * @param midiaLista lista de mídias a ser exibida
     */
    public static void listarIndividual(List<? extends Midia> midiaLista) {

        if (midiaLista.isEmpty()) {
            System.out.println("\nNenhuma mídia cadastrada.");
            return;
        }

        if (Servicos.verificaListaHomogenea(midiaLista)) {
            System.out.println("\n--- Listagem de " + midiaLista.getFirst().getTipoMidia() + "---");
        } else {
            System.out.println("\n--- Listagem ---");
        }


        int contador = 0;

        for (Midia midia : midiaLista) {

            contador += 1;

            System.out.print("[" + contador + "]" + " " + midia.getTitulo() + " - " + midia.getAnoLancamento() + " Nota: " );

            boolean temAvaliacao = !midia.getAvaliacoes().isEmpty();

            if (temAvaliacao) {

                System.out.print(midia.getAvaliacoes().getLast().getNota());

            } else {

                if (midia instanceof Serie) {
                    System.out.print(((Serie) midia).getNota());
                } else {
                    System.out.print("0.0");
                }


            }

            if (midia instanceof Serie) {

                System.out.print(" Temporadas: " + ((Serie) midia).getTemporadas().size());

            }

            System.out.println();

        }

    }

    /**
     * Lista todas as mídias cadastradas no sistema, separadas por tipo.
     *
     * @param livros lista de livros cadastrados
     * @param filmes lista de filmes cadastrados
     * @param series lista de séries cadastradas
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void listarTodasMidias (List<Livro> livros, List<Filme> filmes, List<Serie> series, Scanner scanner) {

        if (livros.isEmpty() && filmes.isEmpty() && series.isEmpty()) {

            System.out.println("\nNenhuma mídia cadastrada.");
            return;

        }

        List<Midia> todasMidias = new ArrayList<>();

        if (!livros.isEmpty()) todasMidias.addAll(livros);
        if (!filmes.isEmpty()) todasMidias.addAll(filmes);
        if (!series.isEmpty()) todasMidias.addAll(series);

        escolhaOrdenacao(todasMidias, scanner);

    }

    /**
     * Permite ao usuário selecionar uma mídia específica de uma lista de resultados.
     *
     * @param resultadosDaBusca lista de mídias encontradas
     * @return índice da mídia selecionada ou -1 se nenhuma mídia for encontrada/selecionada
     */
    public static int selecionarMidia(List<? extends Midia> resultadosDaBusca) {

        if (resultadosDaBusca.isEmpty()) {

            System.out.println("Nenhuma mídia encontrada.");
            return -1;

        }

        if (resultadosDaBusca.size() == 1) { return 0; }

        System.out.println("Foram encontradas múltiplas mídias:\n");

        for (int i = 0; i < resultadosDaBusca.size(); i++) {

            Midia m = resultadosDaBusca.get(i);
            System.out.printf("[%d] %s (%s)\n", i + 1, m.getTitulo(), m.getAnoLancamento());

        }

        System.out.print("\nEscolha o número da mídia desejada: ");

        int escolha = -1;

        while (escolha < 1 || escolha > resultadosDaBusca.size()) {

            escolha = (int) Servicos.getValidarEntrada("int");

        }

        return (escolha - 1);

    }

}
