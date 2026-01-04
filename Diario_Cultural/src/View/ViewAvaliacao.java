package View;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.MidiaController;
import Controller.SerieController;

import Model.*;

import Service.Servicos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por gerenciar a avaliação de mídias.
 * Permite aos usuários avaliar diferentes tipos de mídia (Livros, Filmes e Séries),
 * incluindo notas, comentários e datas de consumo.
 */
public class ViewAvaliacao {

    /**
     * Processa a avaliação de uma mídia direcionando para o método de adição de avaliação.
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void processarAvaliacao(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {

        AdicionarAvaliacao(livroController, filmeController, serieController, scanner);

    }

    /**
     * Permite ao usuário avaliar uma mídia específica.
     * O processo inclui:
     * - Seleção da mídia
     * - Verificação se já foi consumida
     * - Atribuição de nota (1 a 5 estrelas)
     * - Adição de comentário
     * - Para séries, permite avaliar temporadas específicas
     *
     * Ao finalizar cada alteração é chamado o método para persistir os dados
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void AdicionarAvaliacao(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {

        List<? extends Midia> midia = ViewLista.selecionarLista(livroController, filmeController, serieController, scanner);

        int temporada;

        if (midia.isEmpty()) return;

        List<? extends Midia> resultados_da_busca = ViewBusca.buscarPorTitulo(midia, scanner);

        if (resultados_da_busca.isEmpty()) {

            System.out.println("Nenhuma mídia encontrada.");
            return;

        }

        int indice_midia = ViewLista.selecionarMidia(resultados_da_busca);
        if (indice_midia < 0) return;

        Midia midiaSelecionada = resultados_da_busca.get(indice_midia);

        if (!midiaSelecionada.getJa_Consumiu()) {

            System.out.print("Já consumiu essa obra? (sim / não) ");
            String resposta = scanner.nextLine();
            boolean consumiu = resposta.equalsIgnoreCase("sim");
            midiaSelecionada.setJaConsumiu(consumiu);

            if (!consumiu) {

                System.out.print("Não pode avaliar uma obra que não consumiu.\n");
                return;

            }

        }

        float nota = 0.0f;
        System.out.print("Nota (1 a 5 estrelas): ");

        while (nota < 1.0 || nota > 5.0) {

            nota = (float) Servicos.getValidarEntrada("float");

            if (nota < 1.0 || nota > 5.0) System.out.print("\nEntre 1 a 5 estrelas: ");

        }


        System.out.print("Avaliação: ");
        String comentario = scanner.nextLine();

        if (midiaSelecionada instanceof Serie serie) {

            System.out.print("Número da temporada avaliada: ");
            temporada = (int) Servicos.getValidarEntrada("int");

            for (Temporada temp : serie.getTemporadas()) {

                if (temp.getNumeroTemporada() == temporada) {

                    temp.setAvaliacao(configurandoAvaliacao(nota, comentario, scanner));
                    serie.setNota();
                    System.out.printf("Avaliação adicionada com sucesso para \"%s\"!\n", midiaSelecionada.getTitulo());
                    Servicos.salvandoSeries();
                    return;

                }

            }

            System.out.println("Temporada não encontrada.");

        } else {

            midiaSelecionada.setAvaliacao(configurandoAvaliacao(nota, comentario, scanner));
            System.out.printf("Avaliação adicionada com sucesso para \"%s\"!\n", midiaSelecionada.getTitulo());

            if (midiaSelecionada.getTipoMidia().equals("Filme")) {
                Servicos.salvandoFilmes();
            } else {
                Servicos.salvandoLivros();
            }

        }

    }

    /**
     * Configura uma nova avaliação com nota, comentário e data de consumo.
     * Realiza validações para garantir que:
     * - A data de consumo seja válida
     * - A data não seja futura
     * - O formato da data esteja correto (DD/MM/AAAA)
     *
     * @param nota valor numérico da avaliação (1 a 5 estrelas)
     * @param avaliacaoEscrita comentário textual da avaliação
     * @param scanner objeto para leitura de entrada do usuário
     * @return objeto Avaliacao configurado com os dados fornecidos
     */
    public static Avaliacao configurandoAvaliacao(float nota, String avaliacaoEscrita, Scanner scanner) {
        boolean rodando_codigo = true;

        LocalDateTime hora_agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = hora_agora.format(formatter);

        Avaliacao avaliacao = new Avaliacao(nota, avaliacaoEscrita, dataFormatada);

        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (rodando_codigo) {

            try {

                System.out.print("Digite a data de consumo (DD/MM/AAAA): ");
                String input = scanner.nextLine();
                LocalDate dataConsumo = LocalDate.parse(input, formatter);

                if (dataConsumo.isAfter(LocalDate.now())) {

                    System.out.println("Erro: Data não pode ser futura! Tente novamente.");

                } else {

                    rodando_codigo = false;
                    avaliacao.setDataConsumo(dataConsumo);
                    return avaliacao;

                }

            } catch (DateTimeParseException e) {

                System.out.println("Formato inválido! Use DD/MM/AAAA.");

            }
        }

        return avaliacao;

    }

    /**
     * Exibe todas as avaliações de uma mídia específica selecionada pelo usuário.
     * Mostra:
     * - Título da mídia
     * - Média geral das avaliações
     * - Total de avaliações recebidas
     * - Lista detalhada de todas as avaliações
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void visualizarAvaliacoes(LivroController livroController,
                                            FilmeController filmeController,
                                            SerieController serieController,
                                            Scanner scanner) {

        Midia midia = getMidia(livroController, filmeController, serieController, scanner);
        if (midia == null) return;

        System.out.println("\n=== Avaliações de " + MidiaController.getTitulo(midia) + " ===");

        List<Avaliacao> todasAvaliacoes = new ArrayList<>(List.of());
        todasAvaliacoes.addAll(MidiaController.getAvaliacoes(midia));

        if (MidiaController.getAvaliacoes(midia).isEmpty()) {

            if (!(midia instanceof Serie)) {

                System.out.println("\nEsta mídia ainda não possui avaliações.");
                return;

            } else {

                for (Temporada temporada : SerieController.getTemporadas((Serie) midia)) {

                    todasAvaliacoes.addAll(SerieController.getAvaliacoes(temporada));

                }

                if (todasAvaliacoes.isEmpty()) {

                    System.out.println("\nEsta mídia ainda não possui avaliações.");
                    return;

                }

            }

        }

        if (!(midia instanceof Serie)) {
            System.out.println("Total de avaliações: " + MidiaController.getAvaliacoes(midia).size());
        } else {

            int contador = 0;

            for (Temporada temporada : SerieController.getTemporadas((Serie) midia)) {

                contador += temporada.getAvaliacoes().size();

            }

            System.out.println("Total de avaliações: " + contador);

        }

        System.out.println("\nTodas as avaliações:");

        for (Avaliacao avaliacao : todasAvaliacoes) {

            System.out.println(avaliacao);

        }

    }

    /**
     * Retorna uma mídia específica.
     * O processo inclui:
     * <ul>
     *     <li>Seleção do tipo de mídia (Livro, Filme ou Série)</li>
     *     <li>Busca por título na lista selecionada</li>
     *     <li>Seleção da mídia específica dos resultados da busca</li>
     * </ul>
     *
     * @param livroController controlador para gerenciamento de livros
     * @param filmeController controlador para gerenciamento de filmes
     * @param serieController controlador para gerenciamento de séries
     * @param scanner objeto para leitura de entrada do usuário
     * @return a mídia selecionada ou null se:
     *         <ul>
     *             <li>Nenhuma mídia estiver cadastrada</li>
     *             <li>Nenhum resultado for encontrado na busca</li>
     *             <li>O usuário cancelar a operação</li>
     *         </ul>
     * @see Model.Midia
     * @see ViewLista#selecionarLista
     * @see ViewBusca#buscarPorTitulo
     * @see ViewLista#selecionarMidia
     */
    private static Midia getMidia(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {
        List<? extends Midia> lista = ViewLista.selecionarLista(
                livroController, filmeController, serieController, scanner
        );

        if (lista.isEmpty()) {

            System.out.println("\nNenhuma mídia cadastrada.");
            return null;

        }

        List<? extends Midia> resultadosBusca = ViewBusca.buscarPorTitulo(lista, scanner);

        if (resultadosBusca.isEmpty()) return null;

        int indiceMidia = ViewLista.selecionarMidia(resultadosBusca);

        if (indiceMidia == -1) {

            System.out.println("\nOperação cancelada.");
            return null;

        }

        return resultadosBusca.get(indiceMidia);
    }

    /**
     * Permite ao usuário selecionar uma avaliação específica de uma lista.
     *
     * @param avaliacoes lista de avaliações disponíveis
     * @return índice da avaliação selecionada ou -1 se cancelado
     */
    private static int selecionarAvaliacao(List<Avaliacao> avaliacoes) {
        if (avaliacoes.isEmpty()) {
            System.out.println("Não há avaliações disponíveis.");
            return -1;
        }

        System.out.println("\n=== Avaliações Disponíveis ===");
        for (int i = 0; i < avaliacoes.size(); i++) {
            Avaliacao avaliacao = avaliacoes.get(i);
            System.out.printf("[%d] Nota: %.1f - Data: %s\n",
                    i + 1,
                    avaliacao.getNota(),
                    avaliacao.getDataAvaliacao());
            System.out.printf("    Comentário: %s\n", avaliacao.getAvaliacao());
            if (avaliacao.getDataConsumo() != null) {
                System.out.printf("    Data de Consumo: %s\n",
                        avaliacao.getDataConsumo());
            }
            System.out.println();
        }

        int escolha;

        do {
            System.out.print("Escolha o número da avaliação (0 para cancelar): ");
            escolha = (int) Servicos.getValidarEntrada("int");

            if (escolha == 0) {
                return -1;
            }

            if (escolha < 1 || escolha > avaliacoes.size()) {
                System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha < 1 || escolha > avaliacoes.size());

        return escolha - 1;
    }

    /**
     * Permite ao usuário sobreescrever uma avaliação específica de uma mídia.
     * Ao finalizar cada alteração é chamado o método para persistir os
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void sobreescreverAvaliacao(LivroController livroController,
                                              FilmeController filmeController,
                                              SerieController serieController,
                                              Scanner scanner) {

        Midia midia = getMidia(livroController, filmeController, serieController, scanner);
        if (midia == null) return;

        if (midia instanceof Serie serie) {
            System.out.print("Número da temporada: ");
            int numeroTemporada = (int) Servicos.getValidarEntrada("int");

            for (Temporada temp : serie.getTemporadas()) {
                if (temp.getNumeroTemporada() == numeroTemporada) {
                    int indiceAvaliacao = selecionarAvaliacao(temp.getAvaliacoes());
                    if (indiceAvaliacao == -1) {
                        System.out.println("Operação cancelada.");
                        return;
                    }

                    float nota = (float) Servicos.getValidarEntrada("float");
                    System.out.print("Nova avaliação: ");
                    String comentario = scanner.nextLine();

                    Avaliacao novaAvaliacao = configurandoAvaliacao(nota, comentario, scanner);
                    temp.getAvaliacoes().set(indiceAvaliacao, novaAvaliacao);
                    serie.setNota();
                    System.out.println("Avaliação sobreescrita com sucesso!");
                    Servicos.salvandoSeries();
                    return;
                }
            }
            System.out.println("Temporada não encontrada.");
        } else {
            int indiceAvaliacao = selecionarAvaliacao(midia.getAvaliacoes());
            if (indiceAvaliacao == -1) {
                System.out.println("Operação cancelada.");
                return;
            }

            System.out.print("Nova nota (1 a 5 estrelas): ");
            float nota = (float) Servicos.getValidarEntrada("float");

            System.out.print("Nova avaliação: ");
            String comentario = scanner.nextLine();

            Avaliacao novaAvaliacao = configurandoAvaliacao(nota, comentario, scanner);
            midia.getAvaliacoes().set(indiceAvaliacao, novaAvaliacao);
            System.out.println("Avaliação sobreescrita com sucesso!");

            if (midia.getTipoMidia().equals("Filme")) {
                Servicos.salvandoFilmes();
            } else {
                Servicos.salvandoLivros();
            }

        }
    }

    /**
     * Permite ao usuário deletar uma avaliação específica de uma mídia.
     * Ao finalizar cada alteração é chamado o método para persistir os dados
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void deletarAvaliacao(LivroController livroController,
                                        FilmeController filmeController,
                                        SerieController serieController,
                                        Scanner scanner) {
        List<? extends Midia> lista = ViewLista.selecionarLista(
                livroController, filmeController, serieController, scanner
        );

        if (lista.isEmpty()) {
            System.out.println("\nNenhuma mídia cadastrada.");
            return;
        }

        List<? extends Midia> resultadosBusca = ViewBusca.buscarPorTitulo(lista, scanner);
        if (resultadosBusca.isEmpty()) return;

        int indiceMidia = ViewLista.selecionarMidia(resultadosBusca);
        if (indiceMidia == -1) {
            System.out.println("\nOperação cancelada.");
            return;
        }

        Midia midia = resultadosBusca.get(indiceMidia);

        if (midia instanceof Serie serie) {
            System.out.print("Número da temporada: ");
            int numeroTemporada = (int) Servicos.getValidarEntrada("int");

            for (Temporada temp : serie.getTemporadas()) {
                if (temp.getNumeroTemporada() == numeroTemporada) {
                    int indiceAvaliacao = selecionarAvaliacao(temp.getAvaliacoes());
                    if (indiceAvaliacao == -1) {
                        System.out.println("Operação cancelada.");
                        return;
                    }

                    System.out.print("Tem certeza que deseja deletar esta avaliação? (sim/não): ");
                    String confirmacao = scanner.nextLine().trim().toLowerCase();

                    if (confirmacao.equals("sim")) {
                        temp.getAvaliacoes().remove(indiceAvaliacao);
                        serie.setNota();
                        System.out.println("Avaliação removida com sucesso!");
                        Servicos.salvandoSeries();
                        return;
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                    return;
                }
            }
            System.out.println("Temporada não encontrada.");
        } else {
            int indiceAvaliacao = selecionarAvaliacao(midia.getAvaliacoes());
            if (indiceAvaliacao == -1) {
                System.out.println("Operação cancelada.");
                return;
            }

            System.out.print("Tem certeza que deseja deletar esta avaliação? (sim/não): ");
            String confirmacao = scanner.nextLine().trim().toLowerCase();

            if (confirmacao.equals("sim")) {

                midia.getAvaliacoes().remove(indiceAvaliacao);
                System.out.println("Avaliação removida com sucesso!");
                if (midia.getTipoMidia().equals("Filme")) {
                    Servicos.salvandoFilmes();
                } else {
                    Servicos.salvandoLivros();
                }

            } else {
                System.out.println("Operação cancelada.");
            }
        }
    }

}
