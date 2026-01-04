package View;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.MidiaController;
import Controller.SerieController;

import Model.*;
import Service.Servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe responsável pela interface de atualização de mídias no sistema.
 * Permite a atualização de diferentes tipos de mídia (Livros, Filmes e Séries)
 * e seus respectivos atributos.
 */
public class ViewAtualizar {

    /**
     * Processa a atualização de uma mídia selecionada pelo usuário.
     *
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     * @param scanner objeto para leitura de entrada do usuário
     */
    public static void processarAtualizacao(LivroController livroController, FilmeController filmeController, SerieController serieController, Scanner scanner) {

        atualizarMidia(ViewLista.selecionarLista(livroController, filmeController, serieController, scanner), scanner, livroController, filmeController, serieController);

    }

    /**
     * Atualiza uma mídia específica baseada na seleção do usuário.
     *
     * @param lista lista de mídias disponíveis
     * @param scanner objeto para leitura de entrada do usuário
     * @param livroController controlador de livros
     * @param filmeController controlador de filmes
     * @param serieController controlador de séries
     */
    public static void atualizarMidia(List<? extends Midia> lista, Scanner scanner, LivroController livroController, FilmeController filmeController, SerieController serieController) {

        if (lista.isEmpty()) {

            System.out.println("\nNenhuma mídia cadastrada.");
            return;

        }

        List<? extends Midia> resultadosBusca = ViewBusca.buscarPorTitulo(lista, scanner);

        if (resultadosBusca.isEmpty()) return;

        int indiceMidia = ViewLista.selecionarMidia(resultadosBusca);

        Midia midiaParaAtualizar = resultadosBusca.get(indiceMidia);

        if (midiaParaAtualizar.getTipoMidia().equals("Série")) {

            System.out.println("\nDesejar Atualizar:");
            System.out.println("[1] Série");
            System.out.println("[2] Temporada");

            System.out.print("\nEscolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {

                atualizarSerie((Serie) midiaParaAtualizar, scanner);

            } else {

                atualizarTemporada((Serie) midiaParaAtualizar, scanner);

            }

        } else if (midiaParaAtualizar.getTipoMidia().equals("Livro")) {

            atualizarLivro((Livro) midiaParaAtualizar, scanner);

        } else {

            atualizarFilme((Filme) midiaParaAtualizar, scanner);

        }

    }

    /**
     * Atualiza os atributos de um livro específico.
     * Ao finalizar cada alteração é chamado o método salvandoLivros()
     * da classe Servicos para persistir os dados.
     *
     * @param livro livro a ser atualizado
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void atualizarLivro(Livro livro, Scanner scanner) {

        while (true) {

            System.out.println("\nQual campo deseja atualizar?");
            System.out.println("[1] Título");
            System.out.println("[2] Autor");
            System.out.println("[3] Editora");
            System.out.println("[4] ISBN");
            System.out.println("[5] Ano de publicação");
            System.out.println("[6] Gênero");
            System.out.println("[7] Possui exemplar?");
            System.out.println("[8] Voltar ao Menu");

            System.out.print("\nEscolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1" -> {
                    LivroController.setTitulo(livro, Servicos.getValidarString("Novo título: "));
                    Servicos.salvandoLivros();
                }

                case "2" -> {
                    LivroController.setAutor(livro, Servicos.getValidarString("Novo autor: "));
                    Servicos.salvandoLivros();
                }

                case "3" -> {
                    LivroController.setEditora(livro, Servicos.getValidarString("Nova editora: "));
                    Servicos.salvandoLivros();
                }

                case "4" -> {
                    LivroController.setIsbn(livro, Servicos.getValidarString("Novo ISBN: "));
                    Servicos.salvandoLivros();
                }

                case "5" -> {

                    System.out.print("Novo ano de publicação: ");
                    LivroController.setAno(livro, (int) Servicos.getValidarEntrada("int"));
                    Servicos.salvandoLivros();

                }

                case "6" -> {
                    atualizarGenerosOuOndeAssistir(livro, LivroController.getGeneros(livro), LivroController.getTitulo(livro), "genero", scanner);
                    Servicos.salvandoLivros();
                }

                case "7" -> {

                    String resposta = Servicos.getValidarString("Possui exemplar? (sim/não): ");
                    LivroController.setPossuiExemplar(livro, resposta.equalsIgnoreCase("sim"));
                    Servicos.salvandoLivros();

                }

                case "8" -> {

                    System.out.println("Atualização finalizada.");
                    return;

                }

                default -> System.out.println("Opção inválida. Tente novamente.");

            }

        }

    }

    /**
     * Atualiza os atributos de um filme específico.
     * Ao finalizar cada alteração é chamado o método salvandoFilmes()
     * da classe Servicos para persistir os dados.
     *
     * @param filme filme a ser atualizado
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void atualizarFilme(Filme filme, Scanner scanner) {

        while (true) {

            System.out.println("\nQual campo deseja atualizar?");
            System.out.println("[1] Título");
            System.out.println("[2] Gênero");
            System.out.println("[3] Ano de Lançamento");
            System.out.println("[4] Duração");
            System.out.println("[5] Direção");
            System.out.println("[6] Roteiro");
            System.out.println("[7] Elenco");
            System.out.println("[8] Título Original");
            System.out.println("[9] Onde Assistir");
            System.out.println("[10] Voltar");


            System.out.print("\nEscolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1" -> {
                    FilmeController.setTitulo(filme, Servicos.getValidarString("Novo título: "));
                    Servicos.salvandoFilmes();
                }

                case "2" -> {

                    System.out.print("Novo gênero: ");
                    atualizarGenerosOuOndeAssistir(filme, filme.getGeneros(), filme.getTitulo(), "genero", scanner);
                    Servicos.salvandoFilmes();

                }

                case "3" -> {

                    System.out.print("Novo ano de lançamento: ");
                    FilmeController.setAno(filme, (int) Servicos.getValidarEntrada("int"));
                    Servicos.salvandoFilmes();

                }

                case "4" -> {

                    System.out.print("Nova duração: ");
                    FilmeController.setDuracao(filme, (int) Servicos.getValidarEntrada("int"));
                    Servicos.salvandoFilmes();

                }

                case "5" -> {
                    FilmeController.setDirecao(filme, Servicos.getValidarString("Nova Direção: "));
                    Servicos.salvandoFilmes();
                }

                case "6" -> {
                    FilmeController.setRoteiro(filme, Servicos.getValidarString("Novo Roteiro: "));
                    Servicos.salvandoFilmes();
                }

                case "7" -> {
                    atualizarElenco(FilmeController.getElenco(filme), scanner);
                    Servicos.salvandoFilmes();
                }

                case "8" -> {
                    FilmeController.setTituloOriginal(filme, Servicos.getValidarString("Novo Título Original: "));
                    Servicos.salvandoFilmes();
                }

                case "9" -> {
                    atualizarGenerosOuOndeAssistir(filme, FilmeController.getOndeAssistir(filme), FilmeController.getTitulo(filme), "ondeAssistir", scanner);
                    Servicos.salvandoFilmes();
                }

                case "10" -> {

                    System.out.println("Atualização finalizada.");
                    return;

                }

                default -> System.out.println("Opção inválida.");

            }

        }
    }

    /**
     * Atualiza os atributos de uma série específica.
     * Ao finalizar cada alteração é chamado o método salvandoSeries()
     * da classe Servicos para persistir os dados.
     *
     * @param serie série a ser atualizada
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void atualizarSerie(Serie serie, Scanner scanner) {

        while (true) {

            System.out.println("\nQual campo deseja atualizar?");
            System.out.println("[1] Título");
            System.out.println("[2] Gênero");
            System.out.println("[3] Ano de Lançamento");
            System.out.println("[4] Elenco");
            System.out.println("[5] Título Original");
            System.out.println("[6] Onde Assistir");
            System.out.println("[7] Voltar");


            System.out.print("\nEscolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1" -> {
                    SerieController.setTitulo(serie, Servicos.getValidarString("Novo Título: "));
                    Servicos.salvandoSeries();
                }

                case "2" -> {

                    System.out.print("Novo gênero: ");
                    atualizarGenerosOuOndeAssistir(serie, serie.getGeneros(), serie.getTitulo(), "genero", scanner);
                    Servicos.salvandoSeries();

                }

                case "3" -> {

                    System.out.print("Novo ano de lançamento: ");
                    SerieController.setAno(serie, (int) Servicos.getValidarEntrada("int"));
                    Servicos.salvandoSeries();

                }

                case "4" -> {
                    atualizarElenco(SerieController.getElenco(serie), scanner);
                    Servicos.salvandoSeries();
                }

                case "5" -> {

                    System.out.print("Novo Título Original: ");
                    SerieController.setTituloOriginal(serie, Servicos.getValidarString("Novo Título Original: "));
                    Servicos.salvandoSeries();

                }

                case "6" -> {
                    atualizarGenerosOuOndeAssistir(serie, SerieController.getOndeAssistir(serie), SerieController.getTitulo(serie), "ondeAssistir", scanner);
                    Servicos.salvandoSeries();
                }

                case "7" -> {

                    System.out.println("Atualização finalizada.");
                    return;

                }

                default -> System.out.println("Opção inválida.");

            }

        }

    }

    /**
     * Atualiza os atributos de uma temporada específica de uma série.
     * Ao finalizar cada alteração é chamado o método salvandoSeries()
     * da classe Servicos para persistir os dados.
     *
     * @param temporada temporada a ser atualizada
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void atualizarTemporada(Serie temporada, Scanner scanner) {

        while (true) {

            System.out.println("\nQual campo deseja atualizar?");
            System.out.println("[1] Elenco");
            System.out.println("[2] Onde Assistir");
            System.out.println("[3] Ano de Lançamento");
            System.out.println("[4] Título Original");
            System.out.println("[5] Voltar");


            System.out.print("\nEscolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1" -> {
                    atualizarElenco(SerieController.getElenco(temporada), scanner);
                    Servicos.salvandoSeries();
                }

                case "2" -> {
                    atualizarGenerosOuOndeAssistir(temporada, SerieController.getOndeAssistir(temporada), SerieController.getTitulo(temporada), "ondeAssistir", scanner);
                    Servicos.salvandoSeries();
                }

                case "3" -> {

                    System.out.print("Novo ano de lançamento: ");
                    SerieController.setAno(temporada, (int) Servicos.getValidarEntrada("int"));
                    Servicos.salvandoSeries();

                }

                case "4" -> {
                    SerieController.setTituloOriginal(temporada, Servicos.getValidarString("Novo Título Original: "));
                    Servicos.salvandoSeries();
                }

                case "5" -> {

                    System.out.println("Atualização finalizada.");
                    return;

                }

                default -> System.out.println("Opção inválida.");

            }
        }
    }

    /**
     * Atualiza a lista de gêneros de uma mídia.
     * Permite adicionar, remover ou substituir gêneros.
     *
     * @param <T> tipo de mídia que estende a classe Midia
     * @param midia mídia a ser atualizada
     * @param lista lista atual de gêneros
     * @param titulo título da mídia
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static <T extends Midia> void atualizarGenerosOuOndeAssistir(T midia, List<String> lista, String titulo, String type, Scanner scanner) {

        while (true) {

            System.out.println("\n" + titulo + " atual:\n" + lista);
            System.out.println("O que deseja fazer?");
            System.out.println("\n[1] Adicionar");
            System.out.println("[2] Remover");
            System.out.println("[3] Substituir tudo");
            System.out.println("[4] Voltar");

            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {

                case "1" -> {

                    if (type.equals("genero")) {
                        MidiaController.addGenero(midia, Servicos.getGenerosOUOndeAssistir("genero"));
                    } else {
                        if (midia.getTipoMidia().equals("Filme")) FilmeController.addOndeAssistir((Filme) midia, Servicos.getGenerosOUOndeAssistir("ondeAssistir"));
                        else SerieController.addOndeAssistir((Serie) midia, Servicos.getGenerosOUOndeAssistir("ondeAssistir"));
                    }

                }

                case "2" -> {

                    System.out.print("Digite o nome a remover: ");
                    String remover = scanner.nextLine();

                    boolean encontrou = false;

                    for (String m : lista) {

                        if (Servicos.normalizarTitulo(m).equalsIgnoreCase(Servicos.normalizarTitulo(remover))) {
                            encontrou = true;
                            if (type.equals("genero")) {
                                MidiaController.removerGenero(midia, Servicos.normalizarTitulo(remover));
                                lista = MidiaController.getGeneros(midia);
                                break;
                            } else {
                                if (midia.getTipoMidia().equals("Filme")) {
                                    FilmeController.removerOndeAssistir((Filme) midia, Servicos.normalizarTitulo(remover));
                                    lista = FilmeController.getOndeAssistir((Filme) midia);
                                    break;
                                } else {
                                    SerieController.removerOndeAssistir((Serie) midia, Servicos.normalizarTitulo(remover));
                                    lista = SerieController.getOndeAssistir((Serie) midia);
                                    break;
                                }
                            }

                        }
                    }

                    if (!encontrou) {
                        System.out.println("Não encontrado na lista.");
                    }

                }

                case "3" -> {

                    if (type.equals("genero")) {
                        MidiaController.setGenero(midia, Servicos.getGenerosOUOndeAssistir("genero"));

                    } else {
                        if (midia.getTipoMidia().equals("Filme")) {
                            FilmeController.setOndeAssistir((Filme) midia, Servicos.getGenerosOUOndeAssistir("ondeAssistir"));
                            lista = FilmeController.getOndeAssistir((Filme) midia);
                        } else {
                            SerieController.setOndeAssistir((Serie) midia, Servicos.getGenerosOUOndeAssistir("ondeAssistir"));
                            lista = SerieController.getOndeAssistir((Serie) midia);
                        }
                    }

                }

                case "4" -> {return;}

                default -> System.out.println("Opção inválida.");

            }

        }
    }

    /**
     * Atualiza o elenco de uma mídia.
     * Permite adicionar, remover pessoas e funções, ou substituir listas completas.
     *
     * @param elenco mapa do elenco atual
     * @param scanner objeto para leitura de entrada do usuário
     */
    private static void atualizarElenco(Map<String, List<String>> elenco, Scanner scanner) {

        while (true) {

            System.out.println("\n--- Elenco Atual ---");
            if (elenco.isEmpty()) {

                System.out.println("Sem cadastro de elenco.");

            }

            else {

                elenco.forEach((funcao, nomes) -> System.out.println(funcao + ": " + nomes));

            }

            System.out.println("\nO que deseja fazer?");
            System.out.println("[1] Adicionar pessoa");
            System.out.println("[2] Remover pessoa");
            System.out.println("[3] Remover função inteira");
            System.out.println("[4] Substituir lista de pessoas de uma função");
            System.out.println("[5] Voltar");

            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> {

                    System.out.print("Digite a função (ex: ator): ");
                    String funcaoAdd = scanner.nextLine();

                    elenco.putIfAbsent(funcaoAdd, new ArrayList<>());
                    List<String> listaAdd = elenco.get(funcaoAdd);

                    System.out.println("Digite os nomes (vazio para parar):");

                    while (true) {

                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        if (nome.isEmpty()) break;
                        listaAdd.add(nome);


                    }

                }

                case "2" -> {

                    System.out.print("Digite a função: ");
                    String funcaoRemoverPessoa = scanner.nextLine();

                    if (!elenco.containsKey(funcaoRemoverPessoa)) {

                        System.out.println("Função não encontrada.");
                        break;

                    }

                    List<String> listaRemocao = elenco.get(funcaoRemoverPessoa);
                    System.out.println("Pessoas nessa função: " + listaRemocao);

                    System.out.print("Nome da pessoa para remover: ");
                    String nomeRemover = scanner.nextLine();

                    if (listaRemocao.removeIf(nome -> nome.equalsIgnoreCase(nomeRemover))) {

                        System.out.println("Removido com sucesso.");

                    } else {

                        System.out.println("Pessoa não encontrada.");

                    }

                }

                case "3" -> {

                    System.out.print("Digite a função para remover: ");
                    String funcaoExcluir = scanner.nextLine();

                    if (elenco.remove(funcaoExcluir) != null) {

                        System.out.println("Função removida com sucesso.");

                    } else {

                        System.out.println("Função não encontrada.");

                    }

                }

                case "4" -> {

                    System.out.print("Digite a função: ");
                    String funcaoSubstituir = scanner.nextLine();

                    List<String> novaLista = new ArrayList<>();
                    System.out.println("Digite os novos nomes (vazio para parar):");

                    while (true) {

                        System.out.print("Nome: ");
                        String nomeNovo = scanner.nextLine();
                        if (nomeNovo.isEmpty()) break;
                        novaLista.add(nomeNovo);

                    }

                    elenco.put(funcaoSubstituir, novaLista);
                    System.out.println("Função atualizada.");

                }

                case "5" -> { return; }

                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


}
