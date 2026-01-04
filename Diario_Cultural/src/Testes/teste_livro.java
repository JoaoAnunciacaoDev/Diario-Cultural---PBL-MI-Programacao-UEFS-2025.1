package Testes;

import Model.Livro;
import Model.Midia;
import Model.Avaliacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de teste para {@link Livro} usando JUnit 5.
 * Testa tanto as funcionalidades específicas de Livro quanto
 * aquelas herdadas de {@link Midia}.
 *
 * @see Livro
 * @see Midia
 */
public class teste_livro {

    private Livro livro;
    private final String TITULO = "O Pequeno Príncipe";
    private final List<String> GENEROS = Arrays.asList("Literatura Infantil", "Fábula");
    private final int ANO = 1943;
    private final String AUTOR = "Antoine de Saint-Exupéry";
    private final String ISBN = "9788574068190";
    private final String EDITORA = "Agir";

    /**
     * Configuração inicial executada antes de cada teste.
     * Cria uma nova instância de Livro com dados do "O Pequeno Príncipe".
     */
    @BeforeEach
    public void setUp() {
        livro = new Livro(TITULO, GENEROS, ANO, AUTOR, ISBN, true, EDITORA);
    }

    /**
     * Verifica se o construtor inicializa corretamente todos os atributos,
     * tanto os específicos de Livro quanto os herdados de Midia.
     */
    @Test
    public void testConstrutorLivro() {
        assertAll("Construtor do Livro",
                () -> assertEquals(AUTOR, livro.getAutor()),
                () -> assertEquals(ISBN, livro.getIsbn()),
                () -> assertEquals(EDITORA, livro.getEditora()),
                () -> assertEquals(TITULO, livro.getTitulo()),
                () -> assertEquals(GENEROS, livro.getGeneros()),
                () -> assertEquals(ANO, livro.getAnoLancamento()),
                () -> assertFalse(livro.getJa_Consumiu())
        );
    }

    /**
     * Verifica se a alteração do título herdado da classe Midia funciona corretamente.
     */
    @Test
    public void testSetTitulo() {
        String novoTitulo = "Le Petit Prince";
        livro.setTitulo(novoTitulo);
        assertEquals(novoTitulo, livro.getTitulo());
    }

    /**
     * Verifica se novos gêneros são adicionados corretamente à lista existente.
     */
    @Test
    public void testAddGeneros() {
        List<String> novosGeneros = Arrays.asList("Filosofia", "Alegoria");
        livro.addGeneros(novosGeneros);
        assertTrue(livro.getGeneros().containsAll(novosGeneros));
    }

    /**
     * Verifica se a remoção de um gênero específico funciona corretamente.
     */
    @Test
    public void testRemoverGenero() {
        livro.removerGenero("Fábula");
        assertFalse(livro.getGeneros().contains("Fábula"));
    }

    /**
     * Verifica se a adição de uma avaliação ao livro funciona corretamente.
     */
    @Test
    public void testSetAvaliacao() {
        Avaliacao avaliacao = new Avaliacao(5.0f, "Excelente!", "25/07/2008");
        livro.setAvaliacao(avaliacao);
        assertTrue(livro.getAvaliacoes().contains(avaliacao));
    }

    /**
     * Verifica se a alteração do status de consumo do livro funciona corretamente.
     */
    @Test
    public void testSetJaConsumiu() {
        livro.setJaConsumiu(true);
        assertTrue(livro.getJa_Consumiu());
    }

    /**
     * Verifica se a alteração do autor do livro funciona corretamente.
     */
    @Test
    public void testSetAutor() {
        String novoAutor = "Antoine Saint-Exupéry";
        livro.setAutor(novoAutor);
        assertEquals(novoAutor, livro.getAutor());
    }

    /**
     * Verifica se a alteração da editora do livro funciona corretamente.
     */
    @Test
    public void testSetEditora() {
        String novaEditora = "Nova Fronteira";
        livro.setEditora(novaEditora);
        assertEquals(novaEditora, livro.getEditora());
    }

    /**
     * Verifica se o método getTipoMidia retorna corretamente o tipo "Livro".
     */
    @Test
    public void testGetTipoMidia() {
        assertEquals("Livro", livro.getTipoMidia());
    }

    /**
     * Verifica se o método toString retorna uma string contendo
     * todas as informações relevantes do livro, tanto da classe base
     * quanto da classe específica.
     */
    @Test
    public void testToString() {
        String resultado = livro.toString();

        assertAll("Verificação do toString",
                // Informações da classe Midia
                () -> assertTrue(resultado.contains(TITULO)),
                () -> assertTrue(resultado.contains(String.valueOf(ANO))),
                () -> assertTrue(resultado.contains("Literatura Infantil")),
                () -> assertTrue(resultado.contains("Não consumido")),

                // Informações específicas de Livro
                () -> assertTrue(resultado.contains(AUTOR)),
                () -> assertTrue(resultado.contains(EDITORA)),
                () -> assertTrue(resultado.contains(ISBN))
        );
    }

    /**
     * Verifica se a alteração do ISBN do livro funciona corretamente.
     */
    @Test
    public void testSetIsbn() {
        String novoIsbn = "9788574068206";
        livro.setIsbn(novoIsbn);
        assertEquals(novoIsbn, livro.getIsbn());
    }
}