package Model;

import Service.Servicos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base que representa uma mídia genérica no sistema.
 * Contém atributos e métodos comuns a todos os tipos de mídia
 * como título, gêneros, ano de lançamento e avaliações.
 */
public class Midia implements Serializable {
    /**
     * Título da mídia.
     */
    private String titulo;

    /**
     * Lista de gêneros aos quais a mídia pertence.
     * Permite classificar a mídia em categorias como "Drama", "Comédia", "Ação", etc.
     */
    private List<String> generos;

    /**
     * Ano de lançamento da mídia.
     * Indica o ano em que a mídia foi originalmente publicada.
     */
    private int anoLancamento;

    /**
     * Lista de avaliações associadas à mídia.
     * Armazena o histórico de avaliações feitas pelo usuário, 
     * inicialmente vazia e pode ser preenchida ao longo do tempo.
     */
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    /**
     * Indica se a mídia já foi consumida pelo usuário.
     * Por padrão, é inicializado como {@code false}, 
     * podendo ser alterado para {@code true} quando o usuário assistir/consumir a mídia.
     */
    private boolean jaConsumiu = false;

    /**
     * Construtor para criar uma nova mídia.
     *
     * @param titulo título da mídia
     * @param generos lista de gêneros da mídia
     * @param anoLancamento ano de lançamento ou publicação da mídia
     */
    public Midia(String titulo, List<String> generos, int anoLancamento) {
        this.titulo = titulo;
        this.generos = generos;
        this.anoLancamento = anoLancamento;
    }

    /**
     * Retorna o título da mídia
     *
     * @return titulo da mídia
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Retorna a lista de gêneros da mídia
     *
     * @return lista de gêneros da mídia
     */
    public List<String> getGeneros() {
        return generos;
    }

    /**
     * Retorna o ano de lançamento/publicação da mídia
     *
     * @return ano de lançamento da mídia
     */
    public int getAnoLancamento() {
        return anoLancamento;
    }

    /**
     * Retorna a lista de avaliações da mídia
     *
     * @return lista de avaliações da mídia
     */
    public List<Avaliacao> getAvaliacoes () {
        return avaliacoes;
    }

    /**
     * Retorna se o usuário já consumiu a obra ou não
     *
     * @return true se a mídia já foi consumida, false caso contrário
     */
    public boolean getJa_Consumiu() {
        return jaConsumiu;
    }

    /**
     * Define um novo título para a mídia.
     *
     * @param titulo novo título
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Substitui a lista atual de gêneros por uma nova.
     *
     * @param generos nova lista de gêneros
     */
    public void setGenero(List<String> generos) {

        this.generos = generos;

    }

    /**
     * Adiciona um novo gênero à lista de gêneros.
     *
     * @param genero gênero a ser adicionado
     */
    public void setGenero(String genero) {

        this.generos.add(genero);

    }

    /**
     * Adiciona uma lista de gêneros aos já existentes.
     *
     * @param generos lista de gêneros a serem adicionados
     */
    public void addGeneros(List<String> generos) {
        this.generos.addAll(generos);
    }

    /**
     * Remove um gênero específico da lista de gêneros.
     * A comparação é feita ignorando maiúsculas/minúsculas e acentos.
     *
     * @param genero gênero a ser removido
     */
    public void removerGenero(String genero) { this.generos.removeIf(g -> Servicos.normalizarTitulo(g).equalsIgnoreCase(Servicos.normalizarTitulo(genero))); }

    /**
     * Define um novo ano de lançamento para a mídia.
     *
     * @param anoDeLancamento novo ano de lançamento
     */
    public void setAnoLancamento(int anoDeLancamento) {
        this.anoLancamento = anoDeLancamento;
    }

    /**
     * Adiciona uma nova avaliação à mídia.
     *
     * @param avaliacao avaliação a ser adicionada
     */
    public void setAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    /**
     * Define se a mídia já foi consumida ou não.
     *
     * @param jaConsumiu true se já foi consumida, false caso contrário
     */
    public void setJaConsumiu(boolean jaConsumiu) {
        this.jaConsumiu = jaConsumiu;
    }

    /**
     * Retorna o tipo específico da mídia.
     * Método destinado a ser sobrescrito pelas classes filhas.
     *
     * @return tipo da mídia (null na classe base)
     */
    public String getTipoMidia() {
        return null;
    };

    /**
     * Retorna uma representação em string da mídia.
     * Inclui título, ano de lançamento, gêneros e status de consumo.
     *
     * @return string formatada com os dados da mídia
     */
    public String toString() {
        return String.format(
                """
                        \nTítulo: %s
                        Ano de Lançamento: %d
                        Gêneros: %s
                        Status: %s
                        """,
                titulo,
                anoLancamento,
                generos.isEmpty() ? "Nenhum" : String.join(", ", generos),
                jaConsumiu ? "Já consumido" : "Não consumido"
        );
    }
}