package Service;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.SerieController;
import Model.Midia;

import java.text.Normalizer;
import java.util.*;

/**
 * Classe utilitária que fornece serviços comuns para entrada de dados
 * e normalização de texto utilizados em todo o sistema.
 */
public class Servicos {

    /** Scanner estático para entrada de dados do usuário */
    public static Scanner  scanner = new Scanner(System.in);

    /**
     * Valida e converte a entrada do usuário para o tipo especificado.
     * Continua solicitando entrada até receber um valor válido.
     *
     * @param type tipo de dado desejado ("int" ou "float")
     * @return objeto convertido para o tipo especificado
     */
    public static Object getValidarEntrada(String type) {
        Object value = null;
        boolean valid = false;

        while (!valid) {
            try {
                String input = scanner.nextLine();

                switch (type.toLowerCase()) {
                    case "int":
                        value = Integer.parseInt(input);
                        valid = true;
                        break;
                    case "float":
                        value = Float.parseFloat(input);
                        valid = true;
                        break;
                    default:

                }
            } catch (Exception e) {
                System.out.println("Valor inválido! Tente novamente.");
            }
        }
        return value;
    }

    /**
     * Obtém uma lista de gêneros ou plataformas de streaming do usuário.
     * Os itens devem ser inseridos separados por vírgula.
     *
     * @param qual_cadastro tipo de entrada ("genero" para gêneros, outro valor para plataformas)
     * @return lista de strings com os itens informados
     */
    public static List<String> getGenerosOUOndeAssistir(String qual_cadastro) {

        if (qual_cadastro.equals("genero")) {

            System.out.print("Digite os gêneros separados por vírgula:\n");

        } else {

            System.out.print("Digite onde a obra está disponível para assistir separados por vírgula:\n");

        }

        String genero = "";
        while (genero.isBlank()) {

            genero = scanner.nextLine();
            if (genero.isBlank()) {System.out.print("Digite separados por vírgula:\n");}

        }

        List<String> generos = new ArrayList<>();

        for (String entrada : genero.split(",")) {

            generos.add(entrada.trim());

        }

        return generos;
    }

    /**
     * Obtém do usuário informações sobre o elenco, organizadas por função.
     * Continua solicitando entradas até que o usuário digite 'sair'.
     *
     * @return mapa onde a chave é a função e o valor é uma lista de nomes
     */
    public static Map<String, List<String>> getElenco() {

        boolean rodando_laco = true;
        Map<String, List<String>> elenco = new HashMap<>();

        while (rodando_laco) {

            String funcao = getValidarString("\nDigite a função do integrante (ex: Ator, Coadjuvante, Dublê) ou 'sair' para finalizar: ");

            if (funcao.equalsIgnoreCase("sair")) {

                rodando_laco = false;

            } else {
                
                String nome = getValidarString("Digite o nome do integrante: ");

                elenco.putIfAbsent(funcao, new ArrayList<>());
                elenco.get(funcao).add(nome);

            }
        }

        return elenco;

    }

    /**
     * Normaliza um título removendo acentos, caracteres especiais e espaços.
     * Útil para comparações e buscas insensíveis a esses elementos.
     *
     * @param titulo título a ser normalizado
     * @return texto normalizado sem acentos, caracteres especiais ou espaços
     */
    public static String normalizarTitulo(String titulo) {
        String normalizado = Normalizer.normalize(titulo, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        normalizado = normalizado.replaceAll("[^\\p{L}\\p{Nd} ]+", "");
        normalizado = normalizado.replaceAll("\\s+", "");

        return normalizado.toLowerCase();
    }

    /**
     * Verifica se uma lista é homogênea (todos os elementos são do mesmo tipo).
     *
     * @param lista lista a ser verificada
     * @return true se todos os elementos são do mesmo tipo, false caso contrário
     */
    public static boolean verificaListaHomogenea(List<? extends Midia> lista) {
        if (lista == null || lista.isEmpty()) {
            return true;
        }

        Class<?> tipo = null;
        for (Object elemento : lista) {
            if (elemento != null) {
                if (tipo == null) {
                    tipo = elemento.getClass();
                } else if (tipo != elemento.getClass()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Obtém uma string válida do usuário, garantindo que não esteja vazia.
     * Continua solicitando entrada até receber um valor válido.
     *
     * @param mensagem mensagem a ser exibida para o usuário
     * @return string válida não vazia
     */
    public static String getValidarString(String mensagem) {
        String entrada;

        do {
            System.out.print(mensagem);
            entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Erro: O texto não pode estar vazio. Tente novamente.");
            }
        } while (entrada.isEmpty());

        return entrada;
    }

    /**
     * Salva a lista de livros no sistema, exibindo mensagens de progresso.
     * 
     * Este método imprime uma mensagem de aviso antes de iniciar o salvamento,
     * chama o método de salvamento da classe LivroController e confirma o sucesso.
     * 
     * @see LivroController#salvarLivros()
     */
    public static void salvandoLivros() {
        System.out.println("Salvando💾...Não desligue o computador enquanto isso.");
        LivroController.salvarLivros();
        System.out.println("Salvo com sucesso.");
    }

    /**
     * Salva a lista de filmes no sistema, exibindo mensagens de progresso.
     * 
     * Este método imprime uma mensagem de aviso antes de iniciar o salvamento,
     * chama o método de salvamento da classe FilmeController e confirma o sucesso.
     * 
     * @see FilmeController#salvarFilmes()
     */
    public static void salvandoFilmes() {
        System.out.println("Salvando💾...Não desligue o computador enquanto isso.");
        FilmeController.salvarFilmes();
        System.out.println("Salvo com sucesso.");
    }

    /**
     * Salva a lista de séries no sistema, exibindo mensagens de progresso.
     * 
     * Este método imprime uma mensagem de aviso antes de iniciar o salvamento,
     * chama o método de salvamento da classe SerieController e confirma o sucesso.
     * 
     * @see SerieController#salvarSeries()
     */
    public static void salvandoSeries() {
        System.out.println("Salvando💾...Não desligue o computador enquanto isso.");
        SerieController.salvarSeries();
        System.out.println("Salvo com sucesso.");
    }

}