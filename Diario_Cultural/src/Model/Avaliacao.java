package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa uma avaliação de mídia no sistema.
 * Armazena informações sobre nota, comentário e datas relacionadas
 * à avaliação e ao consumo da mídia.
 */
public class Avaliacao implements Serializable {
    /**
     * Nota atribuída à mídia, representada por um valor decimal.
     * Inicializado com 0.0, indica a avaliação geral da mídia.
     * Pode variar de 0 a 5, representando uma escala de qualidade.
     * 
     * @see Float
     */
    private float nota = 0.0f;

    /**
     * Comentário textual detalhado sobre a avaliação da mídia.
     * Permite ao usuário adicionar observações ou impressões pessoais 
     * sobre o conteúdo assistido.
     */
    private String avaliacao;

    /**
     * Data em que a avaliação foi realizada, armazenada como String.
     * Formato geralmente segue o padrão "dd/MM/yyyy".
     * Registra o momento específico da avaliação pelo usuário.
     */
    private String dataAvaliacao;

    /**
     * Data em que a mídia foi consumida/assistida.
     * Utiliza a classe {@link LocalDate} para representar a data precisa.
     * Permite rastrear quando o usuário efetivamente assistiu o conteúdo.
     * 
     * @see LocalDate
     */
    private LocalDate dataConsumo;

    /**
     * Construtor para criar uma nova avaliação.
     *
     * @param nota nota atribuída à mídia (valor numérico)
     * @param avaliacao comentário textual sobre a mídia
     * @param dataAvaliacao data em que a avaliação foi realizada
     */
    public Avaliacao(float nota, String avaliacao, String dataAvaliacao) {
        this.nota = nota;
        this.avaliacao = avaliacao;
        this.dataAvaliacao = dataAvaliacao;
    }

    /**
     * Construtor para criar uma nova avaliação.
     *
     * @param nota nota atribuída à mídia (valor numérico)
     * @param avaliacao comentário textual sobre a mídia
     * @param dataAvaliacao data em que a avaliação foi realizada
     */
    public Avaliacao(float nota, String avaliacao, String dataAvaliacao, LocalDate dataConsumo) {
        this.nota = nota;
        this.avaliacao = avaliacao;
        this.dataAvaliacao = dataAvaliacao;
        this.dataConsumo = dataConsumo;
    }

    /**
     * Retorna a nota de avaliação da mídia
     *
     * @return nota atribuída à mídia
     */
    public float getNota() {
        return nota;
    }

    /**
     * Retorna o comentário da avaliação.
     *
     * @return comentário textual da avaliação
     */
    public String getAvaliacao() {
        return avaliacao;
    }

    /**
     * Retorna a data em que a avaliação foi realizada
     *
     * @return data em que a avaliação foi realizada
     */
    public String getDataAvaliacao() {
        return dataAvaliacao;
    }

    /**
     * Retorna a data em que a avaliação foi realizada
     *
     * @return data em que a mídia foi consumida
     */
    public String getDataConsumo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataConsumo.format(formatter);
    }

    /**
     * Define uma nova nota para a avaliação.
     *
     * @param nota nova nota a ser atribuída
     */
    public void setNota(float nota) {
        this.nota = nota;
    }

    /**
     * Define um novo comentário para a avaliação.
     *
     * @param avaliacao novo comentário textual
     */
    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    /**
     * Define uma nova data de avaliação.
     *
     * @param dataAvaliacao nova data em que a avaliação foi realizada
     */
    public void setDataAvaliacao(String dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    /**
     * Define uma nova data de consumo da mídia.
     *
     * @param dataConsumo nova data em que a mídia foi consumida
     */
    public void setDataConsumo(LocalDate dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    /**
     * Sobrescreve o método toString() para formatar a exibição da avaliação.
     * Inclui:
     * - Nota numérica
     * - Comentário (ou "Sem comentário" se null)
     * - Data de consumo formatada no padrão brasileiro (ou "Sem data de consumo" se null)
     * - Data da avaliação (ou "Sem data de avaliação" se null)
     *
     * @return string formatada com todos os dados da avaliação
     */
    @Override
    public String toString() {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
                "  - Nota: %.1f\n" +
                        "    Comentário: %s\n" +
                        "    Data de consumo: %s\n" +
                        "    Data da avaliação: %s\n",
                nota,
                avaliacao == null ? "Sem comentário" : avaliacao,
                dataConsumo == null ? "Sem data de consumo" : dataConsumo.format(formatoBrasileiro),
                dataAvaliacao == null ? "Sem data de avaliação" : dataAvaliacao
        );
    }
}