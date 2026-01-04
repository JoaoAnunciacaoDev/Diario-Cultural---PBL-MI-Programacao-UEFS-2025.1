package Testes;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.SerieController;
import Model.Avaliacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitária para popular o sistema com um conjunto predefinido de mídias.
 * Fornece um método estático para cadastrar livros, filmes e séries com seus respectivos dados e avaliações.
 *
 * As mídias cadastradas incluem:
 * Livros:
 * <ul>
 *     <li>O Pequeno Príncipe (Antoine de Saint-Exupéry)</li>
 *     <li>1984 (George Orwell)</li>
 *     <li>Dom Casmurro (Machado de Assis)</li>
 * </ul>
 *
 * Filmes:
 * <ul>
 *     <li>Interestelar (Christopher Nolan)</li>
 *     <li>Cidade de Deus (Fernando Meirelles)</li>
 *     <li>Pulp Fiction (Quentin Tarantino)</li>
 * </ul>
 *
 * Séries:
 * <ul>
 *     <li>The Boys (2 temporadas)</li>
 *     <li>Supernatural (1 temporada)</li>
 *     <li>Breaking Bad (1 temporada)</li>
 *     <li>Black Mirror (1 temporada)</li>
 * </ul>
 *
 * Cada mídia é cadastrada com:
 * <ul>
 *     <li>Dados básicos (título, autor/diretor, ano)</li>
 *     <li>Informações específicas do tipo (ISBN para livros, duração para filmes, etc.)</li>
 *     <li>Gêneros</li>
 *     <li>Plataformas de streaming (quando aplicável)</li>
 *     <li>Avaliações com notas e comentários</li>
 *     <li>Datas de consumo</li>
 * </ul>
 *
 * @see Model.Midia
 * @see Model.Livro
 * @see Model.Serie
 * @see Model.Temporada
 * @see Controller.LivroController
 * @see Controller.FilmeController
 * @see Controller.SerieController
 * @see Model.Avaliacao
 */
public class CadastroPreDefinido {

    /**
     * Cadastra um conjunto predefinido de mídias no sistema.
     * Popula o sistema com livros, filmes e séries, incluindo suas avaliações e dados específicos.
     * Todas as mídias são marcadas como já consumidas e recebem avaliações com datas de consumo.
     *
     * @param livroController controlador para gerenciamento de livros
     * @param filmeController controlador para gerenciamento de filmes
     * @param serieController controlador para gerenciamento de séries
     */
    public static void cadastrarMidiasPadrao(LivroController livroController, FilmeController filmeController, SerieController serieController) {

        livroController.cadastrarLivro(
                "O Pequeno Príncipe",
                "Antoine de Saint-Exupéry",
                "Agir",
                new ArrayList<>(List.of("Fábula", "Filosofia", "Infantil")),
                1943,
                "978-85-220-0844-8",
                true
        );

        // Novos livros
        livroController.cadastrarLivro(
                "1984",
                "George Orwell",
                "Companhia das Letras",
                new ArrayList<>(List.of("Ficção Científica", "Distopia", "Política")),
                1949,
                "978-85-359-0277-1",
                true
        );

        livroController.cadastrarLivro(
                "Dom Casmurro",
                "Machado de Assis",
                "Martin Claret",
                new ArrayList<>(List.of("Romance", "Literatura Brasileira", "Clássico")),
                1899,
                "978-85-7232-233-4",
                false
        );

        // Avaliações dos livros
        Avaliacao avaliacaoLivro1 = new Avaliacao(5.0f, "Atemporal!!", "08/04/2025 14:00:23");
        avaliacaoLivro1.setDataConsumo(LocalDate.of(2012, 7,25));
        Avaliacao avaliacaoLivro2 = new Avaliacao(4.7f, "Assustadoramente atual", "08/04/2025 14:02:15");
        avaliacaoLivro2.setDataConsumo(LocalDate.of(2023, 6, 15));
        Avaliacao avaliacaoLivro3 = new Avaliacao(4.9f, "Obra-prima da literatura", "08/04/2025 14:03:30");
        avaliacaoLivro3.setDataConsumo(LocalDate.of(2024, 1, 10));

        livroController.getLivros().get(0).setJaConsumiu(true);
        livroController.getLivros().get(0).setAvaliacao(avaliacaoLivro1);
        livroController.getLivros().get(1).setJaConsumiu(true);
        livroController.getLivros().get(1).setAvaliacao(avaliacaoLivro2);
        livroController.getLivros().get(2).setJaConsumiu(true);
        livroController.getLivros().get(2).setAvaliacao(avaliacaoLivro3);

        // Filmes existentes
        filmeController.cadastrarFilme(
                "Interestelar",
                new ArrayList<>(List.of("Ficção Científica", "Drama", "Aventura")),
                2014,
                169,
                "Christopher Nolan",
                "Christopher Nolan, Jonathan Nolan",
                new HashMap<>(Map.of("Ator", new ArrayList<>(List.of("Matthew McConaughey", "Michael Caine")))),
                "Interstellar",
                new ArrayList<>(List.of("HBO Max", "Amazon Prime Video"))
        );

        // Novos filmes
        filmeController.cadastrarFilme(
                "Cidade de Deus",
                new ArrayList<>(List.of("Drama", "Crime", "Ação")),
                2002,
                130,
                "Fernando Meirelles",
                "Bráulio Mantovani",
                new HashMap<>(Map.of(
                        "Ator", new ArrayList<>(List.of("Alexandre Rodrigues", "Leandro Firmino")),
                        "Coadjuvante", new ArrayList<>(List.of("Seu Jorge", "Matheus Nachtergaele"))
                )),
                "City of God",
                new ArrayList<>(List.of("Globoplay", "Netflix"))
        );

        filmeController.cadastrarFilme(
                "Pulp Fiction",
                new ArrayList<>(List.of("Crime", "Drama", "Sátira")),
                1994,
                154,
                "Quentin Tarantino",
                "Quentin Tarantino",
                new HashMap<>(Map.of(
                        "Ator", new ArrayList<>(List.of("John Travolta", "Samuel L. Jackson")),
                        "Coadjuvante", new ArrayList<>(List.of("Uma Thurman", "Bruce Willis"))
                )),
                "Pulp Fiction",
                new ArrayList<>(List.of("Prime Video", "Apple TV+"))
        );

        // Avaliações dos filmes
        Avaliacao avaliacaoFilme1 = new Avaliacao(4.8f, "Muito massa", "08/04/2025 14:01:12");
        avaliacaoFilme1.setDataConsumo(LocalDate.of(2018, 4, 13));
        Avaliacao avaliacaoFilme2 = new Avaliacao(4.9f, "Cinema brasileiro no seu melhor", "08/04/2025 14:02:45");
        avaliacaoFilme2.setDataConsumo(LocalDate.of(2023, 8, 20));
        Avaliacao avaliacaoFilme3 = new Avaliacao(5.0f, "Obra-prima do cinema", "08/04/2025 14:03:30");
        avaliacaoFilme3.setDataConsumo(LocalDate.of(2024, 2, 15));

        filmeController.getFilmes().get(0).setJaConsumiu(true);
        filmeController.getFilmes().get(0).setAvaliacao(avaliacaoFilme1);
        filmeController.getFilmes().get(1).setJaConsumiu(true);
        filmeController.getFilmes().get(1).setAvaliacao(avaliacaoFilme2);
        filmeController.getFilmes().get(2).setJaConsumiu(true);
        filmeController.getFilmes().get(2).setAvaliacao(avaliacaoFilme3);

        // Séries existentes e suas temporadas
        serieController.cadastrarSerie(
                "The Boys",
                new ArrayList<>(List.of("Ação", "Drama", "Super-herói")),
                2019,
                0,
                new HashMap<>(Map.of("Ator", List.of("Karl Urban", "Jack Quaid", "Antony Starr"))),
                "The Boys",
                new ArrayList<>(List.of("Amazon Prime Video"))
        );

        serieController.cadastrarSerie(
                "SuperNatural",
                new ArrayList<>(List.of("Ação", "Drama", "Sombrio", "Fantasia")),
                2005,
                2020,
                new HashMap<>(Map.of("Ator", List.of("Jensen Ackles"))),
                "SuperNatural",
                new ArrayList<>(List.of("Amazon Prime Video"))
        );

        // Novas séries
        serieController.cadastrarSerie(
                "Breaking Bad",
                new ArrayList<>(List.of("Drama", "Crime", "Suspense")),
                2008,
                2013,
                new HashMap<>(Map.of(
                        "Ator", List.of("Bryan Cranston", "Aaron Paul"),
                        "Coadjuvante", List.of("Bob Odenkirk", "Giancarlo Esposito")
                )),
                "Breaking Bad",
                new ArrayList<>(List.of("Netflix"))
        );

        serieController.cadastrarSerie(
                "Black Mirror",
                new ArrayList<>(List.of("Ficção Científica", "Drama", "Suspense")),
                2011,
                0,
                new HashMap<>(Map.of("Ator", List.of("Diversos"))),
                "Black Mirror",
                new ArrayList<>(List.of("Netflix"))
        );

        // Temporadas existentes e novas
        // The Boys temporadas
        serieController.cadastrarTemporada(
                "The Boys",
                new ArrayList<>(List.of("Ação", "Drama")),
                2019,
                new HashMap<>(Map.of("Ator", List.of("Karl Urban", "Jack Quaid", "Antony Starr"))),
                "The Boys",
                new ArrayList<>(List.of("Amazon Prime Video")),
                1,
                8,
                serieController.getSeries().get(0)
        );

        serieController.cadastrarTemporada(
                "The Boys",
                new ArrayList<>(List.of("Ação", "Drama", "Política")),
                2019,
                new HashMap<>(Map.of("Ator", List.of("Karl Urban", "Jack Quaid", "Antony Starr", "Aya Cash"))),
                "The Boys",
                new ArrayList<>(List.of("Amazon Prime Video")),
                2,
                8,
                serieController.getSeries().get(0)
        );

        // Supernatural temporada
        serieController.cadastrarTemporada(
                "SuperNatural",
                new ArrayList<>(List.of("Ação", "Drama", "Fantasia", "Sombrio")),
                2005,
                new HashMap<>(Map.of("Ator", List.of("Jensen Ackles"))),
                "SuperNatural",
                new ArrayList<>(List.of("Amazon Prime Video")),
                1,
                25,
                serieController.getSeries().get(1)
        );

        // Breaking Bad temporadas
        serieController.cadastrarTemporada(
                "Breaking Bad",
                new ArrayList<>(List.of("Drama", "Crime")),
                2008,
                new HashMap<>(Map.of("Ator", List.of("Bryan Cranston", "Aaron Paul"))),
                "Breaking Bad",
                new ArrayList<>(List.of("Netflix")),
                1,
                7,
                serieController.getSeries().get(2)
        );

        // Black Mirror temporadas
        serieController.cadastrarTemporada(
                "Black Mirror",
                new ArrayList<>(List.of("Ficção Científica", "Drama")),
                2011,
                new HashMap<>(Map.of("Ator", List.of("Daniel Kaluuya"))),
                "Fifteen Million Merits",
                new ArrayList<>(List.of("Netflix")),
                1,
                3,
                serieController.getSeries().get(3)
        );

        // Avaliações das temporadas
        Avaliacao avaliacaoTemporada1 = new Avaliacao(4.2f, "Ótimo começo", "08/04/2025 13:49:41");
        avaliacaoTemporada1.setDataConsumo(LocalDate.of(2020, 1, 5));
        Avaliacao avaliacaoTemporada2 = new Avaliacao(4.8f, "Bizarro", "08/04/2025 13:51:39");
        avaliacaoTemporada2.setDataConsumo(LocalDate.of(2021, 9, 5));
        Avaliacao avaliacaoTemporadaSuper = new Avaliacao(4.5f, "Maneirão, hein?", "08/04/2025 13:52:16");
        avaliacaoTemporadaSuper.setDataConsumo(LocalDate.of(2016, 5, 25));
        Avaliacao avaliacaoBreakingBad = new Avaliacao(5.0f, "Melhor piloto de série", "08/04/2025 13:53:30");
        avaliacaoBreakingBad.setDataConsumo(LocalDate.of(2023, 12, 1));
        Avaliacao avaliacaoBlackMirror = new Avaliacao(4.7f, "Perturbador", "08/04/2025 13:54:45");
        avaliacaoBlackMirror.setDataConsumo(LocalDate.of(2024, 1, 15));

        // Atribuindo avaliações
        serieController.getSeries().getFirst().getTemporadas().getFirst().setAvaliacao(avaliacaoTemporada1);
        serieController.getSeries().getFirst().getTemporadas().getLast().setAvaliacao(avaliacaoTemporada2);
        serieController.getSeries().get(1).getTemporadas().getFirst().setAvaliacao(avaliacaoTemporadaSuper);
        serieController.getSeries().get(2).getTemporadas().getFirst().setAvaliacao(avaliacaoBreakingBad);
        serieController.getSeries().getLast().getTemporadas().getFirst().setAvaliacao(avaliacaoBlackMirror);

        // Marcando séries como consumidas
        for (var serie : serieController.getSeries()) {
            serie.setJaConsumiu(true);
            serie.setNota();
        }

        livroController.salvarLivros();
        filmeController.salvarFilmes();
        serieController.salvarSeries();

        System.out.println("📚 Mídias padrão cadastradas com sucesso!\n");
    }

}
