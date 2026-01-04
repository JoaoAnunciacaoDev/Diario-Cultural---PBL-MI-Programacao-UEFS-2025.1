package Controller;

import Model.Avaliacao;
import Model.Midia;
import Model.Serie;
import Service.Servicos;

import java.util.*;
import java.util.stream.Collectors;

import static Service.Servicos.normalizarTitulo;

/**
 * Controlador abstrato que fornece operações comuns para gerenciamento de mídias.
 * Implementa funcionalidades de busca, ordenação e filtros para coleções de mídias.
 */
public abstract class MidiaController {

    /**
     * Busca mídias pelo título exato (ignorando case e acentuação).
     *
     * @param titulo título a ser buscado
     * @param midiaLista lista de mídias onde realizar a busca
     * @return lista de mídias que correspondem ao título buscado
     */
    public static List<? extends Midia> busca_titulo(String titulo, List<? extends Midia> midiaLista) {

        List<Midia> resultados_da_busca =  new ArrayList<>();

        for (Midia midia : midiaLista) {
            if (normalizarTitulo(midia.getTitulo()).equalsIgnoreCase(normalizarTitulo(titulo))) {
                resultados_da_busca.add(midia);
            }
        }

        return resultados_da_busca;

    }

    /**
     * Busca mídias por gênero (busca parcial, ignorando case e acentuação).
     *
     * @param genero gênero a ser buscado
     * @param midiaLista lista de mídias onde realizar a busca
     * @return lista de mídias que contêm o gênero buscado
     */
    public static List<? extends Midia> busca_genero(String genero, List<? extends Midia> midiaLista) {

        List<Midia> resultados_da_busca =  new ArrayList<>();

        for (Midia midia : midiaLista) {
            for (String s : midia.getGeneros()) {
                if (normalizarTitulo(s).toLowerCase().contains(normalizarTitulo(genero).toLowerCase())) {
                    resultados_da_busca.add(midia);
                }
            }
        }

        return resultados_da_busca;

    }

    /**
     * Busca mídias por ano de lançamento específico.
     *
     * @param ano ano de lançamento a ser buscado
     * @param midiaLista lista de mídias onde realizar a busca
     * @return lista de mídias lançadas no ano especificado
     */
    public static List<? extends Midia> busca_ano(int ano, List<? extends Midia> midiaLista) {

        List<Midia> resultados_da_busca =  new ArrayList<>();

        for (Midia midia : midiaLista) {

            if (midia.getAnoLancamento() == ano) {
                resultados_da_busca.add(midia);
            }

        }

        return resultados_da_busca;

    }

    /**
     * Ordena a lista de mídias por avaliação, da maior para a menor nota.
     *
     * @param midiaLista lista de mídias a ser ordenada
     * @return lista ordenada por avaliação em ordem decrescente
     */
    public static List<? extends Midia> ordenarBemAvaliado(List<? extends Midia> midiaLista) {

        return midiaLista.stream().sorted((m1, m2) -> {

            float nota1 = extrairNotaMaisRecente(m1);
            float nota2 = extrairNotaMaisRecente(m2);

            return Float.compare(nota2, nota1);

        }).collect(Collectors.toList());

    }

    /**
     * Ordena a lista de mídias por avaliação, da menor para a maior nota.
     *
     * @param midiaLista lista de mídias a ser ordenada
     * @return lista ordenada por avaliação em ordem crescente
     */
    public static List<? extends Midia> ordenarMalAvaliado(List<? extends Midia> midiaLista) {

        return midiaLista.stream().sorted((m1, m2) -> {

            float nota1 = extrairNotaMaisRecente(m1);
            float nota2 = extrairNotaMaisRecente(m2);

            return Float.compare(nota1, nota2);

        }).collect(Collectors.toList());

    }

    /**
     * Extrai a nota mais recente de uma mídia.
     *
     * @param midia mídia da qual extrair a nota
     * @return nota mais recente ou 0.0 se não houver avaliações
     */
    public static float extrairNotaMaisRecente(Midia midia) {

        if (midia.getAvaliacoes() == null || midia.getAvaliacoes().isEmpty()) {

            if (midia instanceof Serie) {
                return ((Serie) midia).getNota();
            }

            return 0.0f;

        }

        return midia.getAvaliacoes().getLast().getNota();

    }

    /**
     * Filtra a lista de mídias por um gênero específico.
     *
     * @param midiaLista lista de mídias a ser filtrada
     * @param genero gênero para filtrar
     * @return lista filtrada contendo apenas mídias do gênero especificado
     */
    public static List<Midia> filtrarPorGenero(List<? extends Midia> midiaLista, String genero) {
        return midiaLista.stream().filter(m -> m.getGeneros().stream().anyMatch(g -> Servicos.normalizarTitulo(g).equalsIgnoreCase(Servicos.normalizarTitulo(genero)))).collect(Collectors.toList());
    }

    /**
     * Extrai todos os gêneros únicos de uma coleção consolidada de mídias.
     *
     * Este método combina livros, filmes e séries, e então coleta todos os gêneros distintos,
     * removendo valores nulos ou em branco.
     *
     * @param lc Controlador de Livros para obter a lista de livros
     * @param fc Controlador de Filmes para obter a lista de filmes
     * @param sc Controlador de Séries para obter a lista de séries
     * @return Um conjunto ordenado (TreeSet) contendo todos os gêneros únicos
     */
    public static Set<String> extrairGeneros(LivroController lc,
                                             FilmeController fc,
                                             SerieController sc) {

        List<Midia> todasMidias = consolidarMidias(lc.getLivros(), fc.getFilmes(), sc.getSeries());

        return todasMidias.stream()
                .map(Midia::getGeneros)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(g -> g != null && !g.isBlank())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Extrai todos os anos de lançamento únicos de uma coleção consolidada de mídias.
     *
     * Este método combina livros, filmes e séries, e então coleta todos os anos de lançamento
     * distintos, removendo valores nulos.
     *
     * @param lc Controlador de Livros para obter a lista de livros
     * @param fc Controlador de Filmes para obter a lista de filmes
     * @param sc Controlador de Séries para obter a lista de séries
     * @return Um conjunto ordenado (TreeSet) contendo todos os anos de lançamento únicos
     */
    public static Set<Integer> extrairAnos(LivroController lc,
                                           FilmeController fc,
                                           SerieController sc) {

        List<Midia> todasMidias = consolidarMidias(lc.getLivros(), fc.getFilmes(), sc.getSeries());

        return todasMidias.stream()
                .map(Midia::getAnoLancamento)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Consolida múltiplas listas de mídias em uma única lista.
     *
     * Este método combina listas de diferentes tipos de mídias (livros, filmes, séries)
     * em uma lista unificada, ignorando listas nulas.
     *
     * @param listasDeMidias Uma quantidade variável de listas de mídias para consolidar
     * @return Uma lista unificada contendo todas as mídias das listas de entrada
     */
    private static List<Midia> consolidarMidias(List<? extends Midia>... listasDeMidias) {
        List<Midia> todasMidias = new ArrayList<>();
        for (List<? extends Midia> lista : listasDeMidias) {
            if (lista != null) {
                todasMidias.addAll(lista);
            }
        }
        return todasMidias;
    }

    /**
     * Filtra a lista de mídias por ano de lançamento.
     *
     * @param midias lista de mídias a ser filtrada
     * @param anoLancamento ano para filtrar
     * @return lista filtrada contendo apenas mídias do ano especificado
     */
    public static List<Midia> filtrarPorAno(List<? extends Midia> midias, int anoLancamento) {
        return midias.stream().filter(m -> m.getAnoLancamento() == anoLancamento).collect(Collectors.toList());
    }

    /**
     * Define a avaliação para uma mídia específica.
     *
     * Este método atribui uma nova avaliação ao objeto de mídia passado como parâmetro,
     * atualizando sua classificação ou pontuação no sistema.
     *
     * @param midia A mídia (livro, filme ou série) que receberá a nova avaliação
     * @param avaliacao O objeto de avaliação a ser associado à mídia
     */
    public static void setAvaliacao(Midia midia, Avaliacao avaliacao) {
        midia.setAvaliacao(avaliacao);
    }

    /**
     * Retorna título da mídia, solicitando do Model
     *
     * @param midia mídia da qual obter o título
     * @return título da mídia
     */
    public static String getTitulo(Midia midia) { return midia.getTitulo(); }

    /**
     * Retorna lista de gêneros da mídia
     *
     * @param midia mídia da qual obter os gêneros
     * @return lista de gêneros da mídia
     */
    public static List<String> getGeneros(Midia midia) { return midia.getGeneros(); }

    /**
     * Retorna a lista de avaliações da mídia, solicitando do Model.
     *
     * @param midia mídia da qual obter as avaliações
     * @return lista de avaliações da mídia
     */
    public static List<Avaliacao> getAvaliacoes(Midia midia) { return midia.getAvaliacoes(); }

    /**
     * Retorna a a últma avaliação da mídia.
     *
     * @param midia mídia da qual obter a avaliaçõe
     * @return última avaliação da mídia
     */
    public static Avaliacao getUltimaAvaliacao(Midia midia) { return (midia.getAvaliacoes() != null && !midia.getAvaliacoes().isEmpty()) ? midia.getAvaliacoes().getLast() : null; }

    /**
     * Retorna o tipo da mídia, solicitando do Model.
     *
     * @param midia mídia da qual obter o tipo
     * @return tipo da mídia
     */
    public static String getTipoMidia(Midia midia) { return midia.getTipoMidia(); }

    /**
     * Retorna o ano de lançamento da mídia, solicitando do Model.
     *
     * @param midia mídia da qual obter o ano de lançamento
     * @return ano de lançamento da mídia
     */
    public static int getAnoLancamento(Midia midia) { return midia.getAnoLancamento(); }

    /**
     * Define um novo título para a mídia.
     *
     * @param midia mídia a ser modificada
     * @param novoTitulo novo título a ser definido
     */
    public static void setTitulo(Midia midia, String novoTitulo) { midia.setTitulo(novoTitulo); }

    /**
     * Define um novo ano de lançamento para a mídia.
     *
     * @param midia mídia a ser modificada
     * @param novoAno novo ano a ser definido
     */
    public static void setAno(Midia midia, int novoAno) { midia.setAnoLancamento(novoAno); }

    /**
     * Define uma nova lista de gêneros para a mídia.
     *
     * @param midia mídia a ser modificada
     * @param novosGeneros nova lista de gêneros
     */
    public static void setGenero(Midia midia, List<String> novosGeneros) { midia.setGenero(novosGeneros); }

    /**
     * Adiciona novos gêneros à lista existente.
     *
     * @param midia mídia a ser modificada
     * @param novosGeneros lista de gêneros a serem adicionados
     */
    public static void addGenero(Midia midia, List<String> novosGeneros) { midia.addGeneros(novosGeneros); }

    /**
     * Remove um gênero específico da mídia.
     *
     * @param midia mídia a ser modificada
     * @param genero gênero a ser removido
     */
    public static void removerGenero(Midia midia, String genero) { midia.removerGenero(genero); }

    /**
     * Retorna se o usuário já consumiu ou não tal obra.
     *
     * @param midia mídia que será verificada.
     */
    public static void setJaConsumiu(Midia midia, boolean consumiu) { midia.setJaConsumiu(consumiu); }

    public static void setNota(Midia midia, float nota) { if (!midia.getAvaliacoes().isEmpty()) midia.getAvaliacoes().getLast().setNota(nota); }

    /**
     * Retorna se o usuário já consumiu ou não tal obra.
     *
     * @param midia mídia que será verificada.
     */
    public static String getJaConsumiu(Midia midia) { return midia.getJa_Consumiu() ? "Sim" : "Não"; }

    /**
     * Verifica se uma string representa um número inteiro válido.
     *
     * Este método tenta converter a string fornecida para um número inteiro.
     * Retorna true se a conversão for bem-sucedida, false caso contrário.
     *
     * @param texto a string a ser verificada
     * @return true se a string puder ser convertida para um número inteiro, false caso contrário
     */
    public static boolean checkInteiro(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica se uma string representa um número de ponto flutuante válido.
     *
     * Este método tenta converter a string fornecida para um número de ponto flutuante.
     * Retorna true se a conversão for bem-sucedida, false caso contrário.
     *
     * @param texto a string a ser verificada
     * @return true se a string puder ser convertida para um número de ponto flutuante, false caso contrário
     */
    public static boolean checkFloat(String texto) {
        try {
            Float.parseFloat(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}