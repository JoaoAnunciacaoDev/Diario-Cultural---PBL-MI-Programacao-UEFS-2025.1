package Testes;

import Controller.MidiaController;
import Model.Midia;
import Model.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para {@link MidiaController}.
 * Implementa testes para todas as funcionalidades principais do controlador de mídias,
 * incluindo busca, ordenação, filtragem e modificação de dados.
 * Utiliza uma implementação mock de {@link Midia} para isolar os testes
 * de dependências externas.
 *
 * @see MidiaController
 * @see Midia
 * @see Avaliacao
 */
class teste_midiaController {

    /**
     * Implementação mock da classe Midia para fins de teste.
     * Simplifica a classe original mantendo apenas as funcionalidades
     * necessárias para os testes.
     */
    private static class MidiaMock extends Midia {
        private List<Avaliacao> avaliacoes = new ArrayList<>();

        /**
         * Construtor da classe mock.
         *
         * @param titulo título da mídia
         * @param generos lista de gêneros
         * @param anoLancamento ano de lançamento
         */
        public MidiaMock(String titulo, List<String> generos, int anoLancamento) {
            super(titulo, generos, anoLancamento);
        }

        @Override
        public void setAvaliacao(Avaliacao avaliacao) {
            avaliacoes.add(avaliacao);
        }

        @Override
        public List<Avaliacao> getAvaliacoes() {
            return avaliacoes;
        }
    }

    private List<Midia> listaMidias;
    private Midia midia1, midia2, midia3;

    /**
     * Configura o ambiente de teste antes de cada caso de teste.
     * Cria instâncias mock de mídias com dados de exemplo e
     * adiciona avaliações para testar ordenação.
     */
    @BeforeEach
    void setUp() {
        midia1 = new MidiaMock("Breaking Bad", Arrays.asList("Drama", "Crime"), 2008);
        midia2 = new MidiaMock("The Crown", Arrays.asList("Drama", "História"), 2016);
        midia3 = new MidiaMock("Stranger Things", Arrays.asList("Ficção Científica", "Drama"), 2016);

        listaMidias = Arrays.asList(midia1, midia2, midia3);

        midia1.setAvaliacao(new Avaliacao(4.5f, "Ótimo!", "01/01/2024"));
        midia2.setAvaliacao(new Avaliacao(3.5f, "Bom", "01/01/2024"));
        midia3.setAvaliacao(new Avaliacao(5.0f, "Excelente!", "01/01/2024"));
    }

    /**
     * Agrupa testes relacionados às funcionalidades de busca.
     * Testa diferentes critérios de busca como título, gênero e ano.
     */
    @Nested
    class BuscaTests {
        /**
         * Testa a busca por título exato.
         */
        @Test
        void testBuscaTituloExato() {
            List<? extends Midia> resultado = MidiaController.busca_titulo("Breaking Bad", listaMidias);
            assertEquals(1, resultado.size());
            assertEquals("Breaking Bad", resultado.get(0).getTitulo());
        }

        /**
         * Testa a busca por título com acentuação diferente.
         */
        @Test
        void testBuscaTituloComAcentuacao() {
            List<? extends Midia> resultado = MidiaController.busca_titulo("breaking bád", listaMidias);
            assertEquals(1, resultado.size());
            assertEquals("Breaking Bad", resultado.get(0).getTitulo());
        }

        /**
         * Testa a busca por gênero.
         */
        @Test
        void testBuscaGenero() {
            List<? extends Midia> resultado = MidiaController.busca_genero("Drama", listaMidias);
            assertEquals(3, resultado.size());
        }

        /**
         * Testa a busca por ano de lançamento.
         */
        @Test
        void testBuscaAno() {
            List<? extends Midia> resultado = MidiaController.busca_ano(2016, listaMidias);
            assertEquals(2, resultado.size());
        }
    }

    /**
     * Agrupa testes relacionados às funcionalidades de ordenação.
     * Testa a ordenação por avaliação em ordem crescente e decrescente.
     */
    @Nested
    class OrdenacaoTests {
        /**
         * Testa a ordenação por melhores avaliações.
         */
        @Test
        void testOrdenarBemAvaliado() {
            List<? extends Midia> resultado = MidiaController.ordenarBemAvaliado(listaMidias);
            assertEquals("Stranger Things", resultado.get(0).getTitulo());
            assertEquals("Breaking Bad", resultado.get(1).getTitulo());
            assertEquals("The Crown", resultado.get(2).getTitulo());
        }

        /**
         * Testa a ordenação por piores avaliações.
         */
        @Test
        void testOrdenarMalAvaliado() {
            List<? extends Midia> resultado = MidiaController.ordenarMalAvaliado(listaMidias);
            assertEquals("The Crown", resultado.get(0).getTitulo());
            assertEquals("Breaking Bad", resultado.get(1).getTitulo());
            assertEquals("Stranger Things", resultado.get(2).getTitulo());
        }
    }

    /**
     * Agrupa testes relacionados às funcionalidades de filtragem.
     * Testa diferentes critérios de filtragem como gênero e ano.
     */
    @Nested
    class FiltragemTests {
        /**
         * Testa a filtragem por gênero específico.
         */
        @Test
        void testFiltrarPorGenero() {
            List<Midia> resultado = MidiaController.filtrarPorGenero(listaMidias, "História");
            assertEquals(1, resultado.size());
            assertTrue(resultado.stream().anyMatch(m -> m.getTitulo().equals("The Crown")));
        }

        /**
         * Testa a filtragem por ano de lançamento.
         */
        @Test
        void testFiltrarPorAno() {
            List<Midia> resultado = MidiaController.filtrarPorAno(listaMidias, 2016);
            assertEquals(2, resultado.size());
            assertTrue(resultado.stream().allMatch(m -> m.getAnoLancamento() == 2016));
        }
    }

    /**
     * Agrupa testes relacionados às funcionalidades de modificação de dados.
     * Testa operações de alteração como mudança de título e manipulação de gêneros.
     */
    @Nested
    class ModificacaoTests {
        /**
         * Testa a alteração do título de uma mídia.
         */
        @Test
        void testSetTitulo() {
            String novoTitulo = "Novo Título";
            MidiaController.setTitulo(midia1, novoTitulo);
            assertEquals(novoTitulo, midia1.getTitulo());
        }

        /**
         * Testa a adição de novos gêneros a uma mídia.
         */
        @Test
        void testAddGenero() {
            List<String> novosGeneros = Arrays.asList("Suspense", "Ação");
            MidiaController.addGenero(midia1, novosGeneros);
            assertTrue(midia1.getGeneros().containsAll(novosGeneros));
        }

        /**
         * Testa a remoção de um gênero de uma mídia.
         */
        @Test
        void testRemoverGenero() {
            MidiaController.removerGenero(midia1, "Crime");
            assertFalse(midia1.getGeneros().contains("Crime"));
        }
    }
}