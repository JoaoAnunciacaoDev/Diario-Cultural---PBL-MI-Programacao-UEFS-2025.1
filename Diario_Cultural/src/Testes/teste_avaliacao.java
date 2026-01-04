package Testes;

import Model.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe de teste para {@link Avaliacao} usando JUnit 5.
 * Testa a criação, manipulação e formatação de avaliações de mídia.
 */
public class teste_avaliacao {

    private Avaliacao avaliacao;
    private final float NOTA = 4.5f;
    private final String COMENTARIO = "Excelente filme!";
    private final String DATA_AVALIACAO = "01/01/2024";
    private final LocalDate DATA_CONSUMO = LocalDate.of(2023, 12, 31);

    /**
     * Configuração inicial executada antes de cada teste.
     */
    @BeforeEach
    public void setUp() {
        avaliacao = new Avaliacao(NOTA, COMENTARIO, DATA_AVALIACAO);
    }

    /**
     * Testa se o construtor inicializa corretamente todos os atributos.
     */
    @Test
    public void testConstrutorAvaliacao() {
        assertAll("Construtor da Avaliação",
                () -> assertEquals(NOTA, avaliacao.getNota()),
                () -> assertEquals(COMENTARIO, avaliacao.getAvaliacao()),
                () -> assertEquals(DATA_AVALIACAO, avaliacao.getDataAvaliacao()),
                () -> assertNull(avaliacao.getDataConsumo())
        );
    }

    /**
     * Testa a alteração da nota.
     */
    @Test
    public void testSetNota() {
        float novaNota = 5.0f;
        avaliacao.setNota(novaNota);
        assertEquals(novaNota, avaliacao.getNota());
    }

    /**
     * Testa a alteração do comentário.
     */
    @Test
    public void testSetAvaliacao() {
        String novoComentario = "Ainda melhor na segunda vez!";
        avaliacao.setAvaliacao(novoComentario);
        assertEquals(novoComentario, avaliacao.getAvaliacao());
    }

    /**
     * Testa a alteração da data de avaliação.
     */
    @Test
    public void testSetDataAvaliacao() {
        String novaData = "02/01/2024";
        avaliacao.setDataAvaliacao(novaData);
        assertEquals(novaData, avaliacao.getDataAvaliacao());
    }

    /**
     * Testa a alteração da data de consumo.
     */
    @Test
    public void testSetDataConsumo() {
        avaliacao.setDataConsumo(DATA_CONSUMO);
        assertEquals(DATA_CONSUMO, avaliacao.getDataConsumo());
    }

    /**
     * Testa o método toString com todos os campos preenchidos.
     */
    @Test
    public void testToStringComTodosCampos() {
        avaliacao.setDataConsumo(DATA_CONSUMO);
        String resultado = avaliacao.toString();

        assertAll("ToString com todos os campos",
                () -> assertTrue(resultado.contains(String.format("%.1f", NOTA))),
                () -> assertTrue(resultado.contains(COMENTARIO)),
                () -> assertTrue(resultado.contains(DATA_AVALIACAO)),
                () -> assertTrue(resultado.contains(DATA_CONSUMO.format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
        );
    }

    /**
     * Testa a precisão da nota com um decimal.
     */
    @Test
    public void testPrecisaoNota() {
        float notaComMuitasDecimais = 4.66666f;
        avaliacao.setNota(notaComMuitasDecimais);
        String resultado = avaliacao.toString();
        assertTrue(resultado.contains("4,7") || resultado.contains("4.7"));
    }

    /**
     * Testa se a formatação da data de consumo está no padrão brasileiro.
     */
    @Test
    public void testFormatacaoDataConsumo() {
        avaliacao.setDataConsumo(DATA_CONSUMO);
        String resultado = avaliacao.toString();
        assertTrue(resultado.contains("31/12/2023"));
    }
}