package Model;

import Service.Servicos;

import java.util.List;
import java.util.Map;

/**
 * Classe abstrata que representa uma mídia audiovisual, estendendo a classe Midia.
 * Define características comuns a filmes e séries, como elenco, título original
 * e plataformas de streaming onde o conteúdo está disponível.
 *
 * @see Midia
 */
public class Audiovisual extends Midia {
    /**
     * Mapa que representa o elenco da mídia audiovisual, organizado por categorias.
     * Permite agrupar atores e atrizes em diferentes papéis, como "Protagonistas" ou "Coadjuvantes".
     * A chave representa a categoria do elenco e o valor é a lista de nomes dos artistas.
     * 
     * @see Map
     * @see List
     */
    private Map<String, List<String>> elenco;

    /**
     * Título original da mídia em seu idioma de produção.
     * Representa o nome da mídia em seu idioma nativo ou de origem, 
     * podendo ser diferente do título em português.
     */
    private String tituloOriginal;

    /**
     * Lista de plataformas de streaming onde a mídia está disponível.
     * Armazena os serviços de streaming ou canais onde o conteúdo pode ser assistido.
     * Permite múltiplas opções de plataformas para o usuário.
     */
    private List<String> ondeAssistir;

    /**
     * Construtor para criar uma nova mídia audiovisual.
     *
     * @param titulo título em português do conteúdo
     * @param genero lista de gêneros do conteúdo
     * @param ano ano de lançamento
     * @param elenco mapa que associa categorias (ex: "Protagonistas", "Coadjuvantes")
     *              com listas de nomes do elenco
     * @param tituloOriginal título original em idioma estrangeiro
     * @param ondeAssistir lista de plataformas onde o conteúdo está disponível
     */
    public Audiovisual(String titulo, List<String> genero, int ano, Map<String, List<String>> elenco, String tituloOriginal, List<String> ondeAssistir) {
        super(titulo, genero, ano);
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
    }

    /**
     * Retorna a lista de categorias do elenco e seus respectivos membros
     *
     * @return mapa com categorias e respectivos membros do elenco
     */
    public Map<String, List<String>> getElenco() {
        return elenco;
    }

    /**
     * Retorna o título original da mídia.
     *
     * @return título original do conteúdo
     */
    public String getTituloOriginal() {
        return tituloOriginal;
    }

    /**
     * Retorna a lista de plataformas onde o conteúdo está disponível
     *
     * @return lista de plataformas onde o conteúdo está disponível
     */
    public List<String> getOndeAssistir() {
        return ondeAssistir;
    }

    /**
     * Define um novo mapa completo de elenco.
     *
     * @param elenco novo mapa de categorias e membros do elenco
     */
    public void setElenco(Map<String, List<String>> elenco) {
        this.elenco = elenco;
    }

    /**
     * Adiciona uma nova categoria de elenco com seus respectivos membros.
     *
     * @param categoria nome da categoria (ex: "Protagonistas", "Coadjuvantes")
     * @param nomes lista de nomes dos membros do elenco nesta categoria
     */
    public void adicionarCategoriaElenco(String categoria, List<String> nomes) {
        elenco.put(categoria, nomes);
    }

    /**
     * Define um novo título original.
     *
     * @param tituloOriginal novo título original
     */
    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    /**
     * Define uma nova lista completa de plataformas.
     *
     * @param ondeAssistir nova lista de plataformas
     */
    public void setOndeAssistir(List<String> ondeAssistir) {
        this.ondeAssistir = ondeAssistir;
    }

    /**
     * Adiciona novas plataformas à lista existente.
     *
     * @param ondeAssistir lista de novas plataformas a serem adicionadas
     */
    public void addOndeAssistir(List<String> ondeAssistir) {
        this.ondeAssistir.addAll(ondeAssistir);
    }

    /**
     * Remove uma plataforma de streaming da lista de onde assistir.
     * 
     * A remoção é feita de forma case-insensitive e normalizada, usando o método
     * {@link Service.Servicos#normalizarTitulo(String)} para comparação.
     * 
     * @param ondeAssistir nome da plataforma de streaming a ser removida
     * @see Service.Servicos#normalizarTitulo(String)
     */
    public void removerOndeAssistir(String ondeAssistir) {
        this.ondeAssistir.removeIf(g -> Servicos.normalizarTitulo(g).equalsIgnoreCase(Servicos.normalizarTitulo(ondeAssistir)));
    }

}