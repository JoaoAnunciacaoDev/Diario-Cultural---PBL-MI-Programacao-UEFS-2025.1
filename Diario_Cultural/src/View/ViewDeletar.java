package View;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.SerieController;

import Model.*;

import Service.Servicos;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela interface de deleção de mídias no sistema.
 * Permite a exclusão segura de diferentes tipos de mídia (Livros, Filmes e Séries).
 */
public class ViewDeletar {

    /**
     * Processa a deleção de uma mídia selecionada pelo usuário.
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void processarRemocao(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {

        deletarMidia(ViewLista.selecionarLista(livroController, filmeController, serieController, scanner), scanner);

    }

    /**
     * Realiza a deleção de uma mídia específica após busca e confirmação.
     * Ao finalizar cada alteração é chamado o método para persistir os dados
     *
     * @param lista lista de mídias disponíveis
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void deletarMidia(List<? extends Midia> lista, Scanner scanner) {

        if (lista.isEmpty()) {

            System.out.println("\nNenhuma mídia cadastrada.");
            return;

        }

        List<? extends Midia> resultadosBusca = ViewBusca.buscarPorTitulo(lista, scanner);

        if (resultadosBusca.isEmpty()) return;

        int indiceMidia = ViewLista.selecionarMidia(resultadosBusca);

        Midia midiaParaRemover = resultadosBusca.get(indiceMidia);

        if (midiaParaRemover.getTipoMidia().equals("Série")) {

            System.out.println("\nDesejar remover:");
            System.out.println("[1] Série");
            System.out.println("[2] Temporada");

            System.out.print("\nEscolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("2")) {

                removerTemporada((Serie) midiaParaRemover, scanner);
                Servicos.salvandoSeries();
                return;

            }

        }

        System.out.print("\nTem certeza que deseja deletar a mídia " + midiaParaRemover.getTitulo() + "? (sim / não): ");
        boolean confirmacao = confirmarDelecao(midiaParaRemover, scanner);

        if (confirmacao) {

            if (midiaParaRemover.getTipoMidia().equals("Livro")) {

                LivroController.removerMidia((Livro) midiaParaRemover);
                Servicos.salvandoLivros();

            } else if (midiaParaRemover.getTipoMidia().equals("Filme")) {

                FilmeController.removerMidia((Filme) midiaParaRemover);
                Servicos.salvandoFilmes();

            } else {

                SerieController.removerMidia((Serie) midiaParaRemover);
                Servicos.salvandoSeries();

            }

            System.out.println("\nRemovida com sucesso!");

        } else {

            System.out.println("\nRemoção cancelada.");

        }

    }

    /**
     * Solicita confirmação do usuário antes de deletar uma mídia.
     *
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void removerTemporada(Serie serie, Scanner scanner) {

        if (serie.getTemporadas().isEmpty()) {

            System.out.println("\nEssa série não possui temporadas cadastradas.");
            return;

        }

        System.out.println("\n--- Temporadas da Série ---");

        for (int i = 0; i < serie.getTemporadas().size(); i++) {

            System.out.printf("[%d] Temporada %d\n", i + 1, serie.getTemporadas().get(i).getNumeroTemporada());

        }

        System.out.print("Escolha a temporada a remover: ");

        int escolha = -1;

        while (escolha < 1 || escolha > serie.getTemporadas().size()) {

            escolha = (int) Servicos.getValidarEntrada("int");

        }

        boolean confirmacao = confirmarDelecao(serie.getTemporadas().get(escolha - 1), scanner);

        if (confirmacao) {

            serie.getTemporadas().remove(escolha - 1);
            System.out.println("\nTemporada removida com sucesso!");

        } else {

            System.out.println("\nRemoção cancelada.");

        }

    }

    /**
     * Solicita confirmação do usuário antes de deletar uma mídia.
     *
     * @param midia mídia a ser deletada
     * @param scanner objeto para leitura de entrada do usuário
     * @return true se o usuário confirmar a deleção, false caso contrário
     */
    private static boolean confirmarDelecao(Midia midia, Scanner scanner) {
        System.out.println("\n=== Confirmação de Deleção ===");
        System.out.println("Você está prestes a deletar a seguinte mídia:");
        System.out.println("Tipo: " + midia.getTipoMidia());
        System.out.println("Título: " + midia.getTitulo());
        System.out.println("Ano: " + midia.getAnoLancamento());

        while (true) {

            System.out.print("\nConfirma a deleção? (Sim/Não): ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (Servicos.normalizarTitulo(resposta).equalsIgnoreCase("Sim")) return true;
            if (Servicos.normalizarTitulo(resposta).equalsIgnoreCase("Não")) return false;

            System.out.println("Por favor, responda apenas Sim ou Não.");

        }
    }

}
