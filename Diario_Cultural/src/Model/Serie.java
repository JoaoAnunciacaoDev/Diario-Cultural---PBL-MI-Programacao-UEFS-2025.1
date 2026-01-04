package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Classe que representa uma série de TV no sistema, estendendo a classe Audiovisual.
 * Gerencia informações específicas de séries como temporadas, status de exibição
 * e sistema de notas por temporada.
 */
public class Serie extends Audiovisual{
    /**
     * Ano em que a série foi encerrada/finalizada.
     * Representa o ano último ano de produção ou transmissão da série.
     * 
     * @see int
     */
    private int anoEncerramento;

    /**
     * Lista de temporadas que compõem a série.
     * Armazena todas as temporadas em ordem cronológica.
     * Permite gerenciamento e organização das temporadas da série.
     * Inicializada como uma ArrayList vazia para garantir não-nulidade.
     * 
     * @see List
     * @see Temporada
     * @see ArrayList
     */
    private List<Temporada> temporadas = new ArrayList<>();

    /**
     * Nota média ou agregada da série.
     * Representa a avaliação geral da série.
     * Inicializada com 0.0 para indicar ausência de avaliações.
     * Pode ser calculada a partir das avaliações das temporadas.
     * 
     * @see float
     */
    private float nota = 0.0f;

    /**
     * Construtor para criar uma nova série.
     *
     * @param titulo título da série
     * @param genero lista de gêneros da série
     * @param ano_de_lancamento ano da primeira exibição
     * @param anoEncerramento ano em que a série foi encerrada (0 se ainda em exibição)
     * @param elenco mapa com funções e lista de nomes do elenco
     * @param tituloOriginal título original da série em idioma estrangeiro
     * @param ondeAssistir lista de plataformas onde a série está disponível
     */
    public Serie(String titulo, List<String> genero, int ano_de_lancamento, int anoEncerramento, Map<String, List<String>> elenco, String tituloOriginal, List<String> ondeAssistir) {
        super(titulo, genero, ano_de_lancamento, elenco, tituloOriginal, ondeAssistir);
        this.anoEncerramento = anoEncerramento;
    }

    /**
     * Retorna ano de encerramento da série (0 se ainda em exibição)
     *
     * @return ano de encerramento da série (0 se ainda em exibição)
     */
    public int getAnoEncerramento() {
        return anoEncerramento;
    }

    public void setAnoEncerramento(int anoEncerramento) {
        this.anoEncerramento = anoEncerramento;
    }

    /**
     * Retorna lista de todas as temporadas da série
     *
     * @return lista de todas as temporadas da série
     */
    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    /**
     * Adiciona uma nova temporada à série.
     *
     * @param temporada objeto Temporada a ser adicionado
     */
    public void addTemporada(Temporada temporada) {
        temporadas.add(temporada);
    }

    /**
     * Retorna nota média geral da série
     *
     * @return nota média geral da série
     */
    public float getNota() {
        return nota;
    }

    /**
     * Calcula e atualiza a nota média da série baseada nas notas
     * das últimas avaliações de cada temporada.
     * A nota é calculada como média aritmética das notas das temporadas
     * que possuem avaliações.
     */
    public void setNota() {
        float notaMedia = 0;

        if (! temporadas.isEmpty()) {

            float notaSoma = 0;
            int contador = 0;

            for (Temporada t : temporadas) {

                if (! t.getAvaliacoes().isEmpty()) {

                    notaSoma += t.getAvaliacoes().getLast().getNota();
                    contador += 1;

                }

            }

            notaMedia = notaSoma / contador;

        }

        this.nota = notaMedia;
    }

    /**
     * Ordena a lista de temporadas pelo número da temporada
     * em ordem crescente.
     */
    public void ordernarTemporadas() {
        temporadas.sort(Comparator.comparingInt(Temporada::getNumeroTemporada));
    }

    /**
     * Sobrescreve o método da classe base para retornar o tipo específico da mídia.
     *
     * @return "Série" como tipo da mídia
     */
    @Override
    public String getTipoMidia() {
        return "Série";
    }

    /**
     * Sobrescreve o método toString() para incluir informações específicas de série.
     * Além das informações básicas de audiovisual, inclui:
     * - Status de exibição (em exibição ou ano de encerramento)
     * - Nota média geral
     * - Lista de temporadas com número de episódios e notas individuais
     *
     * @return string formatada com todos os dados da série
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());

        sb.append("\nDetalhes da Série:\n").append(String.format(

                """
                          Status: %s
                          Nota: %.1f / 5,0
                        """, getAnoEncerramento() == 0 ? "Em exibição" : "Encerrada em " + getAnoEncerramento(), nota

                ));

        if (!temporadas.isEmpty()) {
            sb.append("\nTemporadas:\n");
            temporadas.forEach(temp -> sb.append(String.format(

                            "  - Temporada %d: %d episódios (Nota: %.1f)\n",
                            temp.getNumeroTemporada(),
                            temp.getQuantidadeEpisodios(),
                            temp.getAvaliacoes().isEmpty() ? 0.0f : temp.getAvaliacoes().getLast().getNota()

                    )));

        }

        return sb.toString();
    }
}