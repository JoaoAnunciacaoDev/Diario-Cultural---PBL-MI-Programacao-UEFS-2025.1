package Model;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Classe que representa um filme no sistema, estendendo a classe Audiovisual.
 * Adiciona atributos e comportamentos específicos de filmes como duração,
 * direção e roteiro, além dos elementos herdados de conteúdo audiovisual.
 *
 * @see Filme
 */
public class Filme extends Audiovisual {
    /**
     * Duração total da mídia em minutos.
     * Representa o tempo de reprodução total do conteúdo.
     * Útil para fornecer informações sobre o comprimento do material.
     */
    private int duracao;

    /**
     * Nome do diretor responsável pela produção.
     * Armazena informações sobre quem dirigiu o filme.
     * Pode incluir um único diretor ou múltiplos diretores, dependendo do formato.
     */
    private String direcao;

    /**
     * Informações sobre o roteirista que desenvolveu o conteúdo.
     * Registra o nome do responsável pela criação do roteiro.
     */
    private String roteiro;

    /**
     * Construtor para criar um novo filme.
     *
     * @param titulo título do filme
     * @param genero lista de gêneros do filme
     * @param ano_de_lancamento ano de lançamento do filme
     * @param duracao duração do filme em minutos
     * @param direcao nome do diretor(a) do filme
     * @param roteiro nome do roteirista(s) do filme
     * @param elenco mapa com funções e lista de nomes do elenco
     * @param tituloOriginal título original do filme em idioma estrangeiro
     * @param ondeAssistir lista de plataformas onde o filme está disponível
     */
    public Filme(String titulo, List<String> genero, int ano_de_lancamento, int duracao, String direcao, String roteiro,
                 Map<String, List<String>> elenco, String tituloOriginal, List<String> ondeAssistir) {

        super(titulo, genero, ano_de_lancamento, elenco, tituloOriginal, ondeAssistir);
        this.duracao = duracao;
        this.direcao = direcao;
        this.roteiro = roteiro;
    }

    /**
     * Retorna a duração do filme em minutos
     *
     * @return duração do filme em minutos
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Retorna nome do diretor(a) do filme
     *
     * @return nome do diretor(a) do filme
     */
    public String getDirecao() {
        return direcao;
    }

    /**
     * Retorna nome do roteirista do filme
     *
     * @return nome do roteirista do filme
     */
    public String getRoteiro() {
        return roteiro;
    }

    /**
     * Define uma nova duração para o filme.
     *
     * @param duracao nova duração em minutos
     */
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    /**
     * Define um novo diretor para o filme.
     *
     * @param direcao nome do novo diretor(a)
     */
    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    /**
     * Define um novo roteirista para o filme.
     *
     * @param roteiro nome do(s) novo(s) roteirista(s)
     */
    public void setRoteiro(String roteiro) {
        this.roteiro = roteiro;
    }

    /**
     * Sobrescreve o método da classe base para retornar o tipo específico da mídia.
     *
     * @return "Filme" como tipo da mídia
     */
    @Override
    public String getTipoMidia() {
        return "Filme";
    }

    /**
     * Sobrescreve o método toString() para incluir informações específicas de filme.
     * Além das informações básicas da mídia e audiovisual, inclui:
     * - Duração em minutos
     * - Direção
     * - Roteiro
     * - Última avaliação (nota, comentário e datas)
     * - Elenco completo organizado por função
     * - Título original e plataformas disponíveis
     *
     * @return string formatada com todos os dados do filme
     */
    @Override
    public String toString() {
        String ultimaNotaStr;
        String ultimoComentarioStr;
        String dataConsumo = "";
        String dataAvaliacao = "";
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (getAvaliacoes().isEmpty()) {

            ultimaNotaStr = "Sem avaliações cadastradas";
            ultimoComentarioStr = "Sem avaliações cadastradas";

        } else {

            ultimaNotaStr = String.format("%.1f", getAvaliacoes().getLast().getNota());
            ultimoComentarioStr = getAvaliacoes().getLast().getAvaliacao();
            dataConsumo = String.format(getAvaliacoes().getLast().getDataConsumo());
            dataAvaliacao = String.format(getAvaliacoes().getLast().getDataAvaliacao());

        }

        StringBuilder sb = new StringBuilder(super.toString());

        sb.append("\nDetalhes do Filme:\n").append(String.format(
                """
                          Duração: %d minutos
                          Direção: %s
                          Roteiro: %s
                          Última Nota: %s
                          Último Comentário: %s
                          Consumido em: %s
                          Avaliado em: %s\
                        """,

                        duracao, direcao, roteiro, ultimaNotaStr, ultimoComentarioStr, dataConsumo, dataAvaliacao));

        if (!getElenco().isEmpty()) {
            sb.append("\nElenco:\n");
            getElenco().forEach((categoria, atores) -> sb.append("  ").append(categoria).append(": ").append(String.join(", ", atores)).append("\n"));
        }

        sb.append(String.format("""
                
                Título Original: %s
                Disponível em: %s""",
                getTituloOriginal(), getOndeAssistir().isEmpty() ? "Nenhuma plataforma cadastrada" : String.join(", ", getOndeAssistir())));

        return sb.toString();
    }
}