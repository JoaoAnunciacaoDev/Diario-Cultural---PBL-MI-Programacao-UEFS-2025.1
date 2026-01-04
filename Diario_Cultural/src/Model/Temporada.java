package Model;

import java.util.List;
import java.util.Map;

/**
 * Classe que representa uma temporada de uma série de TV no sistema, estendendo a classe Audiovisual.
 * Cada temporada possui seu próprio número identificador, quantidade de episódios e herda
 * características audiovisuais como elenco, avaliações e informações de disponibilidade.
 *
 * @see Audiovisual
 * @see Midia
 * @see Serie
 */
public class Temporada extends Audiovisual {
    /**
     * Número sequencial que identifica a temporada dentro de uma série.
     * Representa a ordem cronológica ou de produção da temporada.
     * 
     * @see int
     */
    private int numeroTemporada;

    /**
     * Total de episódios que compõem a temporada.
     * Indica o número completo de episódios produzidos nesta temporada.
     * 
     * @see int
     */
    private int quantidadeEpisodios;

    /**
     * Construtor para criar uma nova temporada.
     *
     * @param titulo título da temporada
     * @param genero lista de gêneros da temporada
     * @param ano_de_lancamento ano de lançamento da temporada
     * @param elenco mapa com funções e lista de nomes do elenco da temporada
     * @param titulo_original título original da temporada em idioma estrangeiro
     * @param onde_assistir lista de plataformas onde a temporada está disponível
     * @param numeroTemporada número identificador da temporada na série
     * @param quantidadeEpisodios quantidade total de episódios da temporada
     */
    public Temporada(String titulo, List<String> genero, int ano_de_lancamento, Map<String, List<String>> elenco,
                     String titulo_original, List<String> onde_assistir, int numeroTemporada, int quantidadeEpisodios) {
        super(titulo, genero, ano_de_lancamento, elenco, titulo_original, onde_assistir);
        this.numeroTemporada = numeroTemporada;
        this.quantidadeEpisodios = quantidadeEpisodios;
    }

    /**
     * Obtém o número identificador da temporada.
     *
     * @return número da temporada
     */
    public int getNumeroTemporada() {
        return numeroTemporada;
    }

    /**
     * Obtém a quantidade total de episódios da temporada.
     *
     * @return quantidade de episódios
     */
    public int getQuantidadeEpisodios() {
        return quantidadeEpisodios;
    }

    /**
     * Define um novo número para a temporada.
     *
     * @param numeroTemporada novo número identificador da temporada
     */
    public void setNumeroTemporada(int numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }

    /**
     * Define uma nova quantidade de episódios para a temporada.
     *
     * @param quantidadeEpisodios nova quantidade total de episódios
     */
    public void setQuantidadeEpisodios(int quantidadeEpisodios) {
        this.quantidadeEpisodios = quantidadeEpisodios;
    }

    /**
     * Sobrescreve o método toString() para retornar uma representação em texto da temporada.
     * Inclui o número da temporada, quantidade de episódios e avaliações registradas.
     *
     * @return string formatada com os dados da temporada
     */
    @Override
    public String toString() {
        return "Temporada " + numeroTemporada + " - " + quantidadeEpisodios + " episódios\n" +
                "Avaliação(ões): " + getAvaliacoes() +
                "Título Original: " + getTituloOriginal();
    }
}