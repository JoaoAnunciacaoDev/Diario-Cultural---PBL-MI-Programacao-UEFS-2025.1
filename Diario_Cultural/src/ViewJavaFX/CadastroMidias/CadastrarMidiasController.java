package ViewJavaFX.CadastroMidias;

import Controller.SerieController;

import Model.Serie;

import ViewJavaFX.MenuPrincipal.RegistroControladores;
import ViewJavaFX.MenuPrincipal.TrocarCena;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;

/**
 * Controlador para a tela de cadastro de mídias (Livros, Filmes, Séries e Temporadas).
 * Responsável por validar os dados de entrada e interagir com as camadas de controle de dados.
 */
public class CadastrarMidiasController {

    @FXML private Button botaoVoltar;
    @FXML private Label livroStatusLabel;
    @FXML private Label filmeStatusLabel;
    @FXML private Label serieStatusLabel;
    @FXML private Label temporadaStatusLabel;

    @FXML private TextField tituloLivro;
    @FXML private TextField nomeAutorLivro;
    @FXML private TextField nomeEditora;
    @FXML private TextField caixaGeneroLivro;
    @FXML private TextField anoPublicacaoLivro;
    @FXML private TextField isbnLivro;
    @FXML private CheckBox possuiExemplar;
    @FXML private Label tituloLivroError;
    @FXML private Label autorError;
    @FXML private Label editoraError;
    @FXML private Label generosLivroError;
    @FXML private Label anoLivroError;
    @FXML private Label isbnError;

    @FXML private TextField tituloFilme;
    @FXML private TextField caixaGeneroFilme;
    @FXML private TextField anoLancamentoFilme;
    @FXML private TextField duracaoFilme;
    @FXML private TextField direcaoFilme;
    @FXML private TextField roteiroFilme;
    @FXML private TextField tituloOriginalFilme;
    @FXML private TextField ondeAssistirFilme;
    @FXML private Label tituloFilmeError;
    @FXML private Label generoFilmeError;
    @FXML private Label anoFilmeError;
    @FXML private Label duracaoFilmeError;
    @FXML private Label direcaoFilmeError;
    @FXML private Label roteiroFilmeError;
    @FXML private Label tituloOriginalFilmeError;
    @FXML private Label ondeAssistirFilmeError;
    @FXML private TextField funcaoElencoFilme;
    @FXML private TextField nomeElencoFilme;
    @FXML private ListView<String> elencoFilmeListView;
    @FXML private Label elencoFilmeError;

    @FXML private TextField tituloSerie;
    @FXML private TextField caixaGeneroSerie;
    @FXML private TextField anoLancamentoSerie;
    @FXML private TextField anoEncerramentoSerie;
    @FXML private TextField tituloOriginalSerie;
    @FXML private TextField ondeAssistirSerie;
    @FXML private Label tituloSerieError;
    @FXML private Label generoSerieError;
    @FXML private Label anoLancamentoSerieError;
    @FXML private Label anoEncerramentoSerieError;
    @FXML private Label tituloOriginalSerieError;
    @FXML private Label ondeAssistirSerieError;
    @FXML private TextField funcaoElencoSerie;
    @FXML private TextField nomeElencoSerie;
    @FXML private ListView<String> elencoSerieListView;
    @FXML private Label elencoSerieError;

    @FXML private ChoiceBox<Serie> serieSelecionada;
    @FXML private TextField numTemporada;
    @FXML private TextField anoLancamentoTemporada;
    @FXML private TextField quantidadeEpisodiosTemporada;
    @FXML private TextField ondeAssistirTemporada;
    @FXML private Label serieSelecionadaError;
    @FXML private Label numTemporadaError;
    @FXML private Label anoLancamentoTemporadaError;
    @FXML private Label qtdEpisodiosError;
    @FXML private Label ondeAssistirTemporadaError;
    @FXML private TextField funcaoElencoTemporada;
    @FXML private TextField nomeElencoTemporada;
    @FXML private ListView<String> elencoTemporadaListView;
    @FXML private Label elencoTemporadaError;

    private final Map<String, List<String>> elencoFilmeMap = new HashMap<>();
    private final Map<String, List<String>> elencoSerieMap = new HashMap<>();
    private final Map<String, List<String>> elencoTemporadaMap = new HashMap<>();

    /**
     * Inicializa os componentes da interface após o carregamento do FXML.
     *
     * Principais funcionalidades:
     * <ul>
     *   <li>Configura um conversor personalizado para o ChoiceBox de séries</li>
     *   <li>Define como as séries serão exibidas e selecionadas</li>
     *   <li>Popula o ChoiceBox com as séries disponíveis</li>
     * </ul>
     *
     * O conversor implementa dois métodos principais:
     * <ol>
     *   <li>{@code toString()}: Converte um objeto Serie em sua representação de string (título)</li>
     *   <li>{@code fromString()}: Converte uma string de volta para o objeto Serie correspondente</li>
     * </ol>
     *
     * @see StringConverter
     * @see Serie
     *
     */
    @FXML
    public void initialize() {
        serieSelecionada.setConverter(new StringConverter<>() {
            @Override
            public String toString(Serie serie) {
                return serie == null ? "Selecione uma série..." : serie.getTitulo();
            }

            @Override
            public Serie fromString(String string) {
                return serieSelecionada.getItems().stream().filter(s -> s.getTitulo().equals(string)).findFirst().orElse(null);
            }
        });
        popularSeriesChoiceBox();
    }

    /**
     * Método de cadastro de livro na interface gráfica.
     *
     * Realiza as seguintes ações:
     * <ul>
     *   <li>Limpa todas as mensagens de status anteriores</li>
     *   <li>Valida o formulário de livro</li>
     *   <li>Cadastra o livro através do controlador</li>
     *   <li>Salva os livros no repositório</li>
     *   <li>Limpa o formulário após o cadastro</li>
     * </ul>
     *
     */
    @FXML
    private void CadastrarLivro() {
        limparTodasMensagens();
        if (isFormularioLivroValido()) {
            livroStatusLabel.setText("Livro cadastrado com sucesso.");
            RegistroControladores.getLivroController().cadastrarLivro(
                    tituloLivro.getText(),
                    nomeAutorLivro.getText(),
                    nomeEditora.getText(),
                    separacaoLista(caixaGeneroLivro),
                    Integer.parseInt(anoPublicacaoLivro.getText()),
                    isbnLivro.getText(),
                    possuiExemplar.isSelected()
            );
            RegistroControladores.getLivroController().salvarLivros();
            limparFormularioLivro();
        }
    }


    /**
     * Método de cadastro de filme na interface gráfica.
     *
     * Executa as seguintes operações:
     * <ul>
     *   <li>Limpa mensagens de status anteriores</li>
     *   <li>Valida o formulário de filme</li>
     *   <li>Cadastra o filme através do controlador</li>
     *   <li>Salva os filmes no repositório</li>
     *   <li>Limpa o formulário após o cadastro</li>
     * </ul>
     *
     */
    @FXML
    private void CadastrarFilme() {
        limparTodasMensagens();
        if (isFormularioFilmeValido()) {
            filmeStatusLabel.setText("Filme cadastrado com sucesso.");
            RegistroControladores.getFilmeController().cadastrarFilme(
                    tituloFilme.getText(),
                    separacaoLista(caixaGeneroFilme),
                    Integer.parseInt(anoLancamentoFilme.getText()),
                    Integer.parseInt(duracaoFilme.getText()),
                    direcaoFilme.getText(),
                    roteiroFilme.getText(),
                    new HashMap<>(elencoFilmeMap),
                    tituloOriginalFilme.getText(),
                    separacaoLista(ondeAssistirFilme)
            );
            RegistroControladores.getFilmeController().salvarFilmes();
            limparFormularioFilme();
        }
    }

    /**
     * Método de cadastro de série na interface gráfica.
     *
     * Realiza as seguintes ações:
     * <ul>
     *   <li>Limpa todas as mensagens de status anteriores</li>
     *   <li>Valida o formulário de série</li>
     *   <li>Cadastra a série através do controlador</li>
     *   <li>Salva as séries no repositório</li>
     *   <li>Limpa o formulário após o cadastro</li>
     *   <li>Atualiza a choice box de séries</li>
     * </ul>
     *
     */
    @FXML
    private void CadastrarSerie() {
        limparTodasMensagens();
        if (isFormularioSerieValido()) {
            serieStatusLabel.setText("Série cadastrada com sucesso.");
            RegistroControladores.getSerieController().cadastrarSerie(
                    tituloSerie.getText(),
                    separacaoLista(caixaGeneroSerie),
                    Integer.parseInt(anoLancamentoSerie.getText()),
                    Integer.parseInt(anoEncerramentoSerie.getText()),
                    new HashMap<>(elencoSerieMap),
                    tituloOriginalFilme.getText(),
                    separacaoLista(ondeAssistirSerie)
            );
            RegistroControladores.getSerieController().salvarSeries();
            limparFormularioSerie();
            popularSeriesChoiceBox();
        }
    }

    /**
     * Método de cadastro de temporada na interface gráfica.
     *
     * Executa as seguintes operações:
     * <ul>
     *   <li>Limpa mensagens de status anteriores</li>
     *   <li>Valida o formulário de temporada</li>
     *   <li>Cadastra a temporada para a série selecionada</li>
     *   <li>Salva as séries no repositório</li>
     *   <li>Limpa o formulário após o cadastro</li>
     * </ul>
     *
     */
    @FXML
    private void CadastrarTemporada() {
        limparTodasMensagens();
        if (isFormularioTemporadaValido()) {
            temporadaStatusLabel.setText("Temporada cadastrada com sucesso.");
            RegistroControladores.getSerieController().cadastrarTemporada(
                    SerieController.getTitulo(serieSelecionada.getValue()),
                    SerieController.getGeneros(serieSelecionada.getValue()),
                    Integer.parseInt(anoLancamentoTemporada.getText()),
                    new HashMap<>(elencoTemporadaMap),
                    SerieController.getTituloOriginal(serieSelecionada.getValue()),
                    separacaoLista(ondeAssistirTemporada),
                    Integer.parseInt(numTemporada.getText()),
                    Integer.parseInt(quantidadeEpisodiosTemporada.getText()),
                    serieSelecionada.getValue()
            );
            RegistroControladores.getSerieController().salvarSeries();
            limparFormularioTemporada();
        }
    }

    /**
     * Adiciona um integrante do elenco de filme à lista de elenco.
     *
     * Este método é um manipulador de evento FXML que chama o método genérico
     * para adicionar um integrante ao elenco de filme.
     *
     * @see #adicionarIntegrante(TextField, TextField, Map, ListView, Label, Label)
     */
    @FXML
    private void AdicionarElencoFilme() {
        adicionarIntegrante(funcaoElencoFilme, nomeElencoFilme, elencoFilmeMap, elencoFilmeListView, filmeStatusLabel, elencoFilmeError);
    }

    /**
     * Adiciona um integrante do elenco de série à lista de elenco.
     *
     * Este método é um manipulador de evento FXML que chama o método genérico
     * para adicionar um integrante ao elenco de série.
     *
     * @see #adicionarIntegrante(TextField, TextField, Map, ListView, Label, Label)
     */
    @FXML
    private void AdicionarElencoSerie() {
        adicionarIntegrante(funcaoElencoSerie, nomeElencoSerie, elencoSerieMap, elencoSerieListView, serieStatusLabel, elencoSerieError);
    }

    /**
     * Adiciona um integrante do elenco de temporada à lista de elenco.
     *
     * Este método é um manipulador de evento FXML que chama o método genérico
     * para adicionar um integrante ao elenco de temporada.
     *
     * @see #adicionarIntegrante(TextField, TextField, Map, ListView, Label, Label)
     */
    @FXML
    private void AdicionarElencoTemporada() {
        adicionarIntegrante(funcaoElencoTemporada, nomeElencoTemporada, elencoTemporadaMap, elencoTemporadaListView, temporadaStatusLabel, elencoTemporadaError);
    }

    /**
     * Método genérico para adicionar um integrante ao elenco de uma mídia.
     *
     * Realiza as seguintes operações:
     * <ul>
     *   <li>Limpa todas as mensagens de status anteriores</li>
     *   <li>Valida o preenchimento da função e do nome do integrante</li>
     *   <li>Adiciona o integrante ao mapa de elenco</li>
     *   <li>Atualiza a lista de visualização do elenco</li>
     *   <li>Limpa os campos de entrada</li>
     *   <li>Atualiza as mensagens de status</li>
     * </ul>
     *
     * @param funcaoField Campo de texto contendo a função do integrante
     * @param nomeField Campo de texto contendo o nome do integrante
     * @param elencoMap Mapa para armazenar o elenco (função -> lista de nomes)
     * @param elencoView ListView para exibir os integrantes do elenco
     * @param statusLabel Label para exibir mensagens de status
     * @param errorLabel Label para exibir mensagens de erro
     *
     */
    private void adicionarIntegrante(TextField funcaoField, TextField nomeField, Map<String, List<String>> elencoMap, ListView<String> elencoView, Label statusLabel, Label errorLabel) {
        limparTodasMensagens();
        String funcao = funcaoField.getText().trim();
        String nome = nomeField.getText().trim();

        if (funcao.isEmpty() || nome.isEmpty()) {
            statusLabel.setText("Preencha a função e o nome para adicionar.");
            return;
        }
        elencoMap.computeIfAbsent(funcao, k -> new ArrayList<>()).add(nome);
        elencoView.getItems().add(funcao + ": " + nome);
        funcaoField.clear();
        nomeField.clear();
        errorLabel.setText("");
        statusLabel.setText("Integrante adicionado ao elenco.");
    }

    /**
     * Valida o formulário de cadastro de livro.
     *
     * Realiza a validação dos campos obrigatórios para o cadastro de um livro:
     * <ul>
     *   <li>Título</li>
     *   <li>Autor</li>
     *   <li>Editora</li>
     *   <li>Gêneros</li>
     *   <li>Ano de publicação</li>
     *   <li>ISBN</li>
     * </ul>
     *
     * @return {@code true} se todos os campos forem válidos, {@code false} caso contrário
     *
     * @see #validarCampo(TextField, Label, String)
     * @see #validarCampoInteiro(TextField, Label, String)
     */
    private boolean isFormularioLivroValido() {
        boolean isValido = true;
        if (!validarCampo(tituloLivro, tituloLivroError, "Título é obrigatório.")) isValido = false;
        if (!validarCampo(nomeAutorLivro, autorError, "Autor é obrigatório.")) isValido = false;
        if (!validarCampo(nomeEditora, editoraError, "Editora é obrigatória.")) isValido = false;
        if (!validarCampo(caixaGeneroLivro, generosLivroError, "Gênero(s) são obrigatórios.")) isValido = false;
        if (!validarCampoInteiro(anoPublicacaoLivro, anoLivroError, "Ano inválido.")) isValido = false;
        if (!validarCampo(isbnLivro, isbnError, "ISBN é obrigatório.")) isValido = false;
        return isValido;
    }

    /**
     * Valida o formulário de cadastro de filme.
     *
     * Verifica a integridade dos campos obrigatórios para o cadastro de um filme:
     * <ul>
     *   <li>Título</li>
     *   <li>Gêneros</li>
     *   <li>Ano de lançamento</li>
     *   <li>Duração</li>
     *   <li>Direção</li>
     *   <li>Roteiro</li>
     *   <li>Título original</li>
     *   <li>Local para assistir</li>
     *   <li>Elenco</li>
     * </ul>
     *
     * @return {@code true} se todos os campos forem válidos, {@code false} caso contrário
     *
     * @see #validarCampo(TextField, Label, String)
     * @see #validarCampoInteiro(TextField, Label, String)
     */
    private boolean isFormularioFilmeValido() {
        boolean isValido = true;
        if (!validarCampo(tituloFilme, tituloFilmeError, "Título é obrigatório.")) isValido = false;
        if (!validarCampo(caixaGeneroFilme, generoFilmeError, "Gênero(s) são obrigatórios.")) isValido = false;
        if (!validarCampoInteiro(anoLancamentoFilme, anoFilmeError, "Ano inválido.")) isValido = false;
        if (!validarCampoInteiro(duracaoFilme, duracaoFilmeError, "Duração inválida.")) isValido = false;
        if (!validarCampo(direcaoFilme, direcaoFilmeError, "Direção é obrigatória.")) isValido = false;
        if (!validarCampo(roteiroFilme, roteiroFilmeError, "Roteiro é obrigatório.")) isValido = false;
        if (!validarCampo(tituloOriginalFilme, tituloOriginalFilmeError, "Título original é obrigatório.")) isValido = false;
        if (!validarCampo(ondeAssistirFilme, ondeAssistirFilmeError, "Local para assistir é obrigatório.")) isValido = false;
        if (elencoFilmeMap.isEmpty()) {
            elencoFilmeError.setText("O elenco não pode estar vazio.");
            isValido = false;
        }
        return isValido;
    }

    /**
     * Valida o formulário de cadastro de série.
     *
     * Verifica a integridade dos campos obrigatórios para o cadastro de uma série:
     * <ul>
     *   <li>Título</li>
     *   <li>Gênero</li>
     *   <li>Ano de lançamento</li>
     *   <li>Ano de encerramento</li>
     *   <li>Título original</li>
     *   <li>Local para assistir</li>
     *   <li>Elenco</li>
     * </ul>
     *
     * @return {@code true} se todos os campos forem válidos, {@code false} caso contrário
     *
     * @see #validarCampo(TextField, Label, String)
     * @see #validarCampoInteiro(TextField, Label, String)
     */
    private boolean isFormularioSerieValido() {
        boolean isValido = true;
        if (!validarCampo(tituloSerie, tituloSerieError, "Título é obrigatório.")) isValido = false;
        if (!validarCampo(caixaGeneroSerie, generoSerieError, "Gênero é obrigatório.")) isValido = false;
        if (!validarCampoInteiro(anoLancamentoSerie, anoLancamentoSerieError, "Ano de lançamento inválido.")) isValido = false;
        if (!validarCampoInteiro(anoEncerramentoSerie, anoEncerramentoSerieError, "Ano de encerramento inválido.")) isValido = false;
        if (!validarCampo(tituloOriginalSerie, tituloOriginalSerieError, "Título original é obrigatório.")) isValido = false;
        if (!validarCampo(ondeAssistirSerie, ondeAssistirSerieError, "Local para assistir é obrigatório.")) isValido = false;
        if (elencoSerieMap.isEmpty()) {
            elencoSerieError.setText("O elenco não pode estar vazio.");
            isValido = false;
        }
        return isValido;
    }

    /**
     * Valida o formulário de cadastro de temporada.
     *
     * Verifica a integridade dos campos obrigatórios para o cadastro de uma temporada:
     * <ul>
     *   <li>Série selecionada</li>
     *   <li>Número da temporada</li>
     *   <li>Ano de lançamento</li>
     *   <li>Local para assistir</li>
     *   <li>Quantidade de episódios</li>
     *   <li>Elenco</li>
     * </ul>
     *
     * @return {@code true} se todos os campos forem válidos, {@code false} caso contrário
     *
     * @see #validarCampo(TextField, Label, String)
     * @see #validarCampoInteiro(TextField, Label, String)
     */
    private boolean isFormularioTemporadaValido() {
        boolean isValido = true;
        if (serieSelecionada.getValue() == null) {
            serieSelecionadaError.setText("Selecione uma série.");
            isValido = false;
        }
        if (!validarCampoInteiro(numTemporada, numTemporadaError, "Nº da temporada inválido.")) isValido = false;
        if (!validarCampoInteiro(anoLancamentoTemporada, anoLancamentoTemporadaError, "Ano de lançamento inválido.")) isValido = false;
        if (!validarCampo(ondeAssistirTemporada, ondeAssistirTemporadaError, "Local para assistir é obrigatório.")) isValido = false;
        if (!validarCampoInteiro(quantidadeEpisodiosTemporada, qtdEpisodiosError, "Quantidade de episódios inválida.")) isValido = false;
        if (elencoTemporadaMap.isEmpty()) {
            elencoTemporadaError.setText("O elenco não pode estar vazio.");
            isValido = false;
        }
        return isValido;
    }

    /**
     * Valida um campo de texto em um TextField.
     *
     * Verifica se o campo não está em branco. Se estiver, define uma mensagem de erro
     * no Label associado e retorna false. Caso contrário, retorna true.
     *
     * @param field O TextField a ser validado
     * @param errorLabel O Label onde a mensagem de erro será exibida
     * @param message A mensagem de erro a ser exibida se o campo estiver em branco
     * @return {@code true} se o campo não estiver em branco, {@code false} caso contrário
     */
    private boolean validarCampo(TextField field, Label errorLabel, String message) {
        if (field.getText().isBlank()) {
            errorLabel.setText(message);
            return false;
        }
        return true;
    }

    /**
     * Valida um campo de texto que deve conter um número inteiro positivo.
     *
     * Verifica se o campo não está em branco e contém um número inteiro válido e não negativo.
     * Se a validação falhar, define uma mensagem de erro no Label associado e retorna false.
     *
     * @param field O TextField a ser validado
     * @param errorLabel O Label onde a mensagem de erro será exibida
     * @param message A mensagem de erro a ser exibida se a validação falhar
     * @return {@code true} se o campo contiver um inteiro positivo, {@code false} caso contrário
     *
     * @see #validarInteiroPositivo(String)
     */
    private boolean validarCampoInteiro(TextField field, Label errorLabel, String message) {
        if (field.getText().isBlank() || !validarInteiroPositivo(field.getText())) {
            errorLabel.setText(message);
            return false;
        }
        return true;
    }

    /**
     * Valida se uma string representa um número inteiro positivo.
     *
     * Verifica se a string não é nula, não está em branco e pode ser convertida
     * para um inteiro maior ou igual a zero.
     *
     * @param text A string a ser validada
     * @return {@code true} se a string representar um inteiro não negativo, {@code false} caso contrário
     *
     */
    private boolean validarInteiroPositivo(String text) {
        if (text == null || text.isBlank()) return false;
        try {
            return Integer.parseInt(text) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Separa uma string de entrada em uma lista de strings.
     *
     * Divide o texto de um TextField usando a vírgula como separador,
     * remove espaços em branco no início e no final de cada item.
     *
     * @param campo O TextField contendo os itens a serem separados
     * @return Uma lista de strings resultante da separação
     *
     */
    private List<String> separacaoLista(TextField campo) {

        String texto = campo.getText();

        List<String> generos = new ArrayList<>();

        for (String s : texto.split(",")) {

            generos.add(s.trim());

        }

        return generos;

    }

    /**
     * Popula a ChoiceBox de séries com a lista de séries existentes.
     *
     * Este método realiza as seguintes operações:
     * <ul>
     *   <li>Recupera a lista de séries do controlador</li>
     *   <li>Limpa os itens atuais da ChoiceBox</li>
     *   <li>Adiciona todas as séries à ChoiceBox</li>
     *   <li>Mantém a seleção anterior, se ainda válida</li>
     * </ul>
     *
     */
    private void popularSeriesChoiceBox() {
        List<Serie> seriesExemplo = RegistroControladores.getSerieController().getSeries();
        if (serieSelecionada != null) {
            Serie selecionadaAnteriormente = serieSelecionada.getValue();
            serieSelecionada.getItems().clear();
            serieSelecionada.getItems().addAll(seriesExemplo);
            if (selecionadaAnteriormente != null && seriesExemplo.contains(selecionadaAnteriormente)) {
                serieSelecionada.setValue(selecionadaAnteriormente);
            }
        }
    }

    /**
     * Limpa todas as mensagens de status e etiquetas de erro nos formulários.
     *
     * Reseta os textos de todos os Labels de status e error para strings vazias,
     * abrangendo os formulários de livro, filme, série e temporada.
     *
     */
    private void limparTodasMensagens() {
        livroStatusLabel.setText(""); filmeStatusLabel.setText("");
        serieStatusLabel.setText(""); temporadaStatusLabel.setText("");

        tituloLivroError.setText(""); autorError.setText(""); editoraError.setText("");
        generosLivroError.setText(""); anoLivroError.setText(""); isbnError.setText("");

        tituloFilmeError.setText(""); generoFilmeError.setText(""); anoFilmeError.setText("");
        duracaoFilmeError.setText(""); direcaoFilmeError.setText(""); roteiroFilmeError.setText("");
        elencoFilmeError.setText(""); tituloOriginalFilmeError.setText(""); ondeAssistirFilmeError.setText("");

        tituloSerieError.setText(""); generoSerieError.setText(""); anoLancamentoSerieError.setText("");
        anoEncerramentoSerieError.setText(""); elencoSerieError.setText(""); tituloOriginalSerieError.setText("");
        ondeAssistirSerieError.setText("");

        serieSelecionadaError.setText(""); numTemporadaError.setText(""); anoLancamentoTemporadaError.setText("");
        qtdEpisodiosError.setText(""); elencoTemporadaError.setText(""); ondeAssistirTemporadaError.setText("");
    }

    /**
     * Limpa todos os campos do formulário de livro.
     *
     * Realiza as seguintes ações:
     * <ul>
     *   <li>Limpa os campos de texto</li>
     *   <li>Desmarca a opção de possuir exemplar</li>
     * </ul>
     *
     */
    private void limparFormularioLivro() {
        tituloLivro.clear(); nomeAutorLivro.clear(); nomeEditora.clear();
        caixaGeneroLivro.clear(); anoPublicacaoLivro.clear(); isbnLivro.clear();
        possuiExemplar.setSelected(false);
    }

    /**
     * Limpa todos os campos do formulário de filme.
     *
     * Executa as seguintes operações:
     * <ul>
     *   <li>Limpa os campos de texto</li>
     *   <li>Reseta o mapa de elenco</li>
     *   <li>Limpa a lista de view do elenco</li>
     * </ul>
     *
     */
    private void limparFormularioFilme() {
        tituloFilme.clear(); caixaGeneroFilme.clear(); anoLancamentoFilme.clear();
        duracaoFilme.clear(); direcaoFilme.clear(); roteiroFilme.clear();
        tituloOriginalFilme.clear(); ondeAssistirFilme.clear(); funcaoElencoFilme.clear();
        nomeElencoFilme.clear();
        elencoFilmeMap.clear();
        elencoFilmeListView.getItems().clear();
    }

    /**
     * Limpa todos os campos do formulário de série.
     *
     * Realiza as seguintes ações:
     * <ul>
     *   <li>Limpa os campos de texto</li>
     *   <li>Reseta o mapa de elenco</li>
     *   <li>Limpa a lista de view do elenco</li>
     * </ul>
     *
     */
    private void limparFormularioSerie() {
        tituloSerie.clear(); caixaGeneroSerie.clear(); anoLancamentoSerie.clear();
        anoEncerramentoSerie.clear(); tituloOriginalSerie.clear(); ondeAssistirSerie.clear();
        funcaoElencoSerie.clear(); nomeElencoSerie.clear();
        elencoSerieMap.clear();
        elencoSerieListView.getItems().clear();
    }

    /**
     * Limpa todos os campos do formulário de temporada.
     *
     * Executa as seguintes operações:
     * <ul>
     *   <li>Limpa a seleção da série</li>
     *   <li>Limpa os campos de texto</li>
     *   <li>Reseta o mapa de elenco</li>
     *   <li>Limpa a lista de view do elenco</li>
     * </ul>
     *
     */
    private void limparFormularioTemporada() {
        serieSelecionada.setValue(null);
        numTemporada.clear(); anoLancamentoTemporada.clear();
        quantidadeEpisodiosTemporada.clear(); funcaoElencoTemporada.clear();
        nomeElencoTemporada.clear();
        elencoTemporadaMap.clear();
        elencoTemporadaListView.getItems().clear();
    }

    /**
     * Retorna para a tela do menu principal.
     */
    @FXML
    private void voltarMenu() throws IOException {
        Stage stage = (Stage) botaoVoltar.getScene().getWindow();
        TrocarCena.trocaCena(stage, "/ViewJavaFX/CenasFXML/MenuPrincipal.fxml");
    }
}