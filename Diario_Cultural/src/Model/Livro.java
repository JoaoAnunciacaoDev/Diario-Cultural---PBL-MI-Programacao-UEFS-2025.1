package Model;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe que representa um livro no sistema, estendendo a classe base Midia.
 * Adiciona atributos e comportamentos específicos de livros como autor,
 * editora, ISBN e controle de exemplares.
 *
 * @see Midia
 */
public class Livro extends Midia {
    /**
     * Nome do autor do livro.
     * Armazena o nome completo ou identificação do criador da obra literária.
     * Fundamental para identificação e catalogação do livro.
     * 
     * @see String
     */
    private String autor;

    /**
     * Nome da editora responsável pela publicação do livro.
     * Representa a empresa ou instituição que produziu e distribuiu a obra.
     * Importante para rastreabilidade e informações bibliográficas.
     * 
     * @see String
     */
    private String editora;

    /**
     * Código ISBN (International Standard Book Number) do livro.
     * Identificador único internacional para livros e publicações.
     * Composto por 13 dígitos, permite identificação precisa da edição.
     * 
     * @see String
     */
    private String isbn;

    /**
     * Indica se existe um exemplar físico do livro disponível com o usuário.
     * {@code true} significa que há um exemplar físico no acervo.
     * {@code false} indica ausência de exemplar físico.
     * 
     * @see boolean
     */
    private boolean possuiExemplar;

    /**
     * Construtor para criar um novo livro.
     *
     * @param titulo título do livro
     * @param genero lista de gêneros do livro
     * @param anoDeLancamento ano de lançamento do livro
     * @param autor nome do autor do livro
     * @param isbn código ISBN do livro
     * @param possuiExemplar indica se possui exemplar físico disponível
     * @param editora nome da editora do livro
     */
    public Livro(String titulo, List<String> genero, int anoDeLancamento, String autor, String isbn, boolean possuiExemplar, String editora) {
        super(titulo, genero, anoDeLancamento);
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.possuiExemplar = possuiExemplar;
    }

    /**
     * Retorna o nome do autor do livro
     *
     * @return nome do autor do livro
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Retorna o nome da editora do livro
     *
     * @return nome da editora do livro
     */
    public String getEditora() {
        return editora;
    }

    /**
     * Retorna o código ISBN do livro
     *
     * @return código ISBN do livro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Retorna true se possui exemplar físico disponível, false caso contrário
     *
     * @return true se possui exemplar físico disponível, false caso contrário
     */
    public boolean getPossuiExemplar() {
        return possuiExemplar;
    }

    /**
     * Define um novo autor para o livro.
     *
     * @param autor novo nome do autor
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Define uma nova editora para o livro.
     *
     * @param editora novo nome da editora
     */
    public void setEditora(String editora) {
        this.editora = editora;
    }

    /**
     * Define um novo ISBN para o livro.
     *
     * @param isbn novo código ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Define se o livro possui exemplar físico disponível.
     *
     * @param possui_exemplar true se possui exemplar, false caso contrário
     */
    public void setPossuiExemplar(boolean possui_exemplar) {
        this.possuiExemplar = possui_exemplar;
    }

    /**
     * Sobrescreve o método da classe base para retornar o tipo específico da mídia.
     *
     * @return "Livro" como tipo da mídia
     */
    @Override
    public String getTipoMidia() {
        return "Livro";
    }

    /**
     * Sobrescreve o método toString() para incluir informações específicas de livro.
     * Além das informações básicas da mídia, inclui:
     * - Autor
     * - Editora
     * - ISBN
     * - Disponibilidade de exemplar
     * - Última avaliação (nota, comentário e datas)
     *
     * @return string formatada com todos os dados do livro
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

        return super.toString() +
                String.format(
                        """
                                
                                Detalhes do Livro:
                                  Autor: %s
                                  Editora: %s
                                  ISBN: %s
                                  Exemplar disponível: %s
                                  Última nota: %s
                                  Último Comentário: %s
                                  Consumido em: %s
                                  Avaliado em: %s""",

                        autor,
                        editora,
                        isbn,
                        possuiExemplar ? "Sim" : "Não",
                        ultimaNotaStr,
                        ultimoComentarioStr,
                        dataConsumo,
                        dataAvaliacao
                );
    }

}