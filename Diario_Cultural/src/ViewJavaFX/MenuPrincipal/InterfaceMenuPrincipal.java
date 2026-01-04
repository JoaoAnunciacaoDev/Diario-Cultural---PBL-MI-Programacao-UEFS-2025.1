package ViewJavaFX.MenuPrincipal;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.MidiaController;
import Controller.SerieController;

import Model.Filme;
import Model.Livro;
import Model.Midia;
import Model.Serie;

import Service.Servicos;

import ViewJavaFX.ListagemMidias.CelulaLista;
import ViewJavaFX.ListagemMidias.MidiaTabela;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que representa a interface principal do menu de gerenciamento de mídias.
 *
 * Esta classe é responsável por gerenciar a visualização, filtragem e manipulação
 * de mídias como livros, filmes e séries em uma interface JavaFX.
 *
 * Fornece funcionalidades como:
 * - Carregamento de mídias de diferentes controllers
 * - Filtragem de mídias por gênero, ano e termo de busca
 * - Ordenação de mídias por avaliação
 * - Remoção de mídias
 * - Navegação para tela de cadastro de novas mídias
 *
 */
public class InterfaceMenuPrincipal {

    LivroController livroController = new LivroController();
    FilmeController filmeController = new FilmeController();
    SerieController serieController = new SerieController();

    @FXML private Button botaoSair;
    @FXML private ListView<MidiaTabela> listView;
    @FXML private TextField caixaBusca;
    @FXML private ChoiceBox<String> filtroOrdenacao;
    @FXML private ChoiceBox<String> filtroGenero;
    @FXML private ChoiceBox<Object> filtroAno;

    private final List<MidiaTabela> listaMestraCompleta = new ArrayList<>();

    private ObservableList<MidiaTabela> dadosLista;

    /**
     * Inicializa a interface principal, carregando a lista mestra de mídias
     * e configurando os componentes da interface.
     *
     * Configura:
     * - Lista de mídias
     * - Célula personalizada para lista
     * - Filtros de ordenação, gênero e ano
     */
    @FXML
    public void initialize() {
        carregarListaMestra();

        dadosLista = FXCollections.observableArrayList(listaMestraCompleta);

        listView.setCellFactory(lv -> new CelulaLista(this::removerMidia));
        listView.setItems(dadosLista);

        configurarFiltros();
    }

    /**
     * Carrega todos os dados dos controllers para a lista mestra.
     *
     * Adiciona mídias de diferentes tipos (Livro, Filme, Série)
     * a uma lista unificada para exibição e manipulação.
     */
    private void carregarListaMestra() {
        listaMestraCompleta.clear();
        adicionarMidias(listaMestraCompleta, livroController.getLivros(), "Livro", livroController);
        adicionarMidias(listaMestraCompleta, filmeController.getFilmes(), "Filme", filmeController);
        adicionarMidias(listaMestraCompleta, serieController.getSeries(), "Série", serieController);
    }

    /**
     * Configura os filtros da interface com listeners para atualização dinâmica.
     *
     * Adiciona opções para:
     * - Ordenação por avaliação
     * - Filtragem por gênero
     * - Filtragem por ano
     * - Busca por texto
     */
    private void configurarFiltros() {
        filtroOrdenacao.getItems().addAll("Bem Avaliados", "Mal Avaliados");
        filtroOrdenacao.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> aplicarTodosOsFiltros());

        filtroGenero.getItems().add("Todos os Gêneros");
        filtroGenero.getItems().addAll(MidiaController.extrairGeneros(livroController, filmeController, serieController));
        filtroGenero.getSelectionModel().selectFirst();
        filtroGenero.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> aplicarTodosOsFiltros());

        filtroAno.getItems().add("Todos os Anos");
        filtroAno.getItems().addAll(MidiaController.extrairAnos(livroController, filmeController, serieController));
        filtroAno.getSelectionModel().selectFirst();
        filtroAno.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> aplicarTodosOsFiltros());

        caixaBusca.textProperty().addListener((obs, old, val) -> aplicarTodosOsFiltros());
    }

    /**
     * Aplica todos os filtros definidos pelo usuário na lista de mídias.
     *
     * Realiza filtragem com base em:
     * - Termo de busca (título, autor, diretor, etc)
     * - Gênero selecionado
     * - Ano selecionado
     * - Ordenação por avaliação
     */
    private void aplicarTodosOsFiltros() {
        String generoSelecionado = filtroGenero.getValue();
        Object anoSelecionadoObj = filtroAno.getValue();
        String termoBusca = caixaBusca.getText();

        List<MidiaTabela> listaFiltrada = listaMestraCompleta.stream().filter(midiaTabela -> {
                    if (termoBusca == null || termoBusca.isBlank()) {
                        return true;
                    }

                    String termoBuscaNormalizado = Servicos.normalizarTitulo(termoBusca);
                    Midia midiaOriginal = midiaTabela.getMidiaOriginal();

                    if (Servicos.normalizarTitulo(midiaOriginal.getTitulo()).contains(termoBuscaNormalizado)) {
                        return true;
                    }

                    if (midiaOriginal instanceof Livro) {
                        Livro livro = (Livro) midiaOriginal;
                        if (livro.getAutor() != null && Servicos.normalizarTitulo(livro.getAutor()).contains(termoBuscaNormalizado)) {
                            return true;
                        }
                        if (livro.getIsbn() != null && Servicos.normalizarTitulo(livro.getIsbn()).equalsIgnoreCase(termoBuscaNormalizado)) {
                            return true;
                        }
                    }
                    else if (midiaOriginal instanceof Filme) {
                        Filme filme = (Filme) midiaOriginal;
                        if (filme.getDirecao() != null && Servicos.normalizarTitulo(filme.getDirecao()).contains(termoBuscaNormalizado)) {
                            return true;
                        }
                        if (filme.getElenco() != null) {
                            for (List<String> nomes : filme.getElenco().values()) {
                                for (String nome : nomes) {
                                    if (Servicos.normalizarTitulo(nome).contains(termoBuscaNormalizado)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    return false;
                }).filter(midiaTabela -> {
                    if (generoSelecionado == null || generoSelecionado.equals("Todos os Gêneros")) {
                        return true;
                    }
                    return MidiaController.filtrarPorGenero(List.of(midiaTabela.getMidiaOriginal()), generoSelecionado).size() > 0;
                }).filter(midiaTabela -> {
                    if (anoSelecionadoObj == null || anoSelecionadoObj.equals("Todos os Anos")) {
                        return true;
                    }
                    return MidiaController.filtrarPorAno(List.of(midiaTabela.getMidiaOriginal()), (Integer) anoSelecionadoObj).size() > 0;
                }).collect(Collectors.toList());

        String ordenacao = filtroOrdenacao.getValue();
        if (ordenacao != null) {
            if (ordenacao.equals("Bem Avaliados")) {
                listaFiltrada.sort(Comparator.comparing(MidiaTabela::getNota).reversed());
            } else if (ordenacao.equals("Mal Avaliados")) {
                listaFiltrada.sort(Comparator.comparing(MidiaTabela::getNota));
            }
        }

        dadosLista.setAll(listaFiltrada);
    }

    /**
     * Adiciona mídias de um tipo específico à lista mestra.
     *
     * @param <T> Tipo genérico de mídia (Livro, Filme, Série)
     * @param linhas Lista de linhas da tabela de mídias
     * @param midias Lista de mídias a serem adicionadas
     * @param tipo Tipo de mídia (ex: "Livro", "Filme")
     * @param controller Controlador correspondente ao tipo de mídia
     */
    private <T> void adicionarMidias(List<MidiaTabela> linhas, List<T> midias, String tipo, MidiaController controller) {
        for (T midia : midias) {
            float nota = controller.extrairNotaMaisRecente((Midia) midia);
            int ano = controller.getAnoLancamento((Midia) midia);
            String titulo = controller.getTitulo((Midia) midia);
            linhas.add(new MidiaTabela(titulo, tipo, nota, ano, (Midia) midia, controller));
        }
    }

    /**
     * Remove uma mídia específica da lista e dos controladores.
     *
     * Realiza as seguintes ações:
     * - Remove mídia da lista mestra
     * - Remove mídia do controlador correspondente
     * - Reaplica filtros
     * - Salva alterações
     *
     * @param midiaParaRemover Mídia a ser removida
     */
    private void removerMidia(MidiaTabela midiaParaRemover) {
        listaMestraCompleta.remove(midiaParaRemover);
        switch (midiaParaRemover.getTipoMidia()) {
            case "Livro" -> { livroController.removerMidia((Livro) midiaParaRemover.getMidiaOriginal()); }
            case "Filme" -> { filmeController.removerMidia((Filme) midiaParaRemover.getMidiaOriginal()); }
            case "Série" -> { serieController.removerMidia((Serie) midiaParaRemover.getMidiaOriginal());}
        }
        aplicarTodosOsFiltros();
        salvarDados();
    }

    /**
     * Salva os dados de todos os controllers.
     *
     * Persiste as alterações para:
     * - Livros
     * - Filmes
     * - Séries
     */
    void salvarDados() {
            LivroController.salvarLivros();
            FilmeController.salvarFilmes();
            SerieController.salvarSeries();
    }

    /**
     * Ação do botão de busca.
     * Aplica todos os filtros definidos na interface.
     */
    @FXML
    private void Buscar() {
        aplicarTodosOsFiltros();
        System.out.println("Filtros aplicados via botão Buscar.");
    }

    /**
     * Navega para a tela de cadastro de mídias.
     *
     * @throws IOException Se houver erro ao carregar a nova tela
     */
    @FXML
    private void cadastrarMidia() throws IOException {
        Stage stage = (Stage) botaoSair.getScene().getWindow();
        TrocarCena.trocaCena(stage, "/ViewJavaFX/CenasFXML/CadastroMidias.fxml");
    }

    /**
     * Encerra a aplicação.
     */
    @FXML
    private void sair() {
        System.exit(0);
    }
}