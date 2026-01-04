package ViewJavaFX.ListagemMidias;

import Controller.FilmeController;
import Controller.LivroController;
import Controller.MidiaController;
import Controller.SerieController;

import Model.*;

import ViewJavaFX.AvaliacaoMidias.AvaliacaoMidiaController;
import ViewJavaFX.MenuPrincipal.RegistroControladores;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.Function;

/**
 * Controlador responsável por gerenciar a interface de um item de mídia individual.
 *
 * Esta classe controla a exibição e interação com os detalhes de uma mídia
 * em uma interface gráfica JavaFX. Permite edição de informações, visualização
 * de detalhes e exclusão de mídias.
 *
 * Características principais:
 * <ul>
 *   <li>Gerencia elementos FXML para exibição de informações</li>
 *   <li>Suporta edição de título, ano de lançamento e nota</li>
 *   <li>Permite exclusão de mídias</li>
 *   <li>Controla a visualização de detalhes adicionais</li>
 * </ul>
 *
 */
 public class MidiaItemController {
    @FXML Button deletarMidia;

    @FXML Label tipoMidia;
    @FXML Label tituloMidia;
    @FXML Label anoLancamentoMidia;
    @FXML Label notaMidia;
    @FXML Button botaoDetalhes;
    @FXML TilePane detalhesPane;
    @FXML VBox paideTodos;
    @FXML VBox detalhesVbox;
    @FXML VBox avaliacaoVbox;
    @FXML VBox elencoVbox;
    @FXML HBox hboxInfo;
    @FXML Button editarTitulo;
    @FXML Button editarAnoLancamento;
    @FXML Button editarNota;
    @FXML Button avaliarMidia;

    /**
     * Callback para ação de exclusão de mídia.
     */
    private Consumer<MidiaTabela> acaoDeExclusaoCallback;

    /**
     * Item de mídia na tabela representado por esta célula.
     */
    private MidiaTabela midiaTabelaItem;

    /**
     * Mídia original associada a este item.
     */
    private Midia midiaOriginal;

    /**
     * Indica se os detalhes da mídia estão sendo visualizados.
     */
    boolean vendoDetalhes = false;

    /**
     * Configura os dados da mídia na interface gráfica.
     *
     * Realiza as seguintes etapas:
     * <ul>
     *   <li>Armazena a mídia original e o item da tabela</li>
     *   <li>Configura o callback de exclusão</li>
     *   <li>Preenche os campos de informação da mídia</li>
     * </ul>
     *
     * @param midia Mídia original a ser exibida
     * @param item Item da tabela correspondente à mídia
     * @param callback Função de callback para exclusão da mídia
     */
    public void setDados(Midia midia, MidiaTabela item, Consumer<MidiaTabela> callback) {

        this.acaoDeExclusaoCallback = callback;
        this.midiaTabelaItem = item;
        this.midiaOriginal = midia;

        tipoMidia.setText(MidiaController.getTipoMidia(midiaOriginal));

        tituloMidia.setText(MidiaController.getTitulo(midiaOriginal));

        anoLancamentoMidia.setText(String.valueOf(MidiaController.getAnoLancamento(midiaOriginal)));

        notaMidia.setText(String.format("%.1f", MidiaController.extrairNotaMaisRecente(midiaOriginal)));

    }

    /**
     * Método para edição do título da mídia.
     *
     * Abre um campo de edição para alterar o título da mídia:
     * <ul>
     *   <li>Utiliza um método genérico de edição de campo</li>
     *   <li>Valida que o novo título não está em branco</li>
     *   <li>Atualiza o título através do MidiaController</li>
     * </ul>
     */
    @FXML
    private void edicaoTitulo() {
        editarCampoHeader(hboxInfo, editarTitulo,
                () -> MidiaController.getTitulo(midiaOriginal),
                (novoValor) -> MidiaController.setTitulo(midiaOriginal, novoValor),
                (novoValor) -> !novoValor.isBlank());
    }

    /**
     * Método para edição do ano de lançamento da mídia.
     *
     * Abre um campo de edição para alterar o ano de lançamento:
     * <ul>
     *   <li>Utiliza um método genérico de edição de campo</li>
     *   <li>Valida que o novo valor é um número inteiro válido</li>
     *   <li>Atualiza o ano através do MidiaController</li>
     * </ul>
     */
    @FXML
    private void edicaoAnoLancamento() {
        editarCampoHeader(hboxInfo, editarAnoLancamento,
                () -> String.valueOf(MidiaController.getAnoLancamento(midiaOriginal)),
                (novoValor) -> MidiaController.setAno(midiaOriginal, Integer.parseInt(novoValor)),
                (novoValor) -> MidiaController.checkInteiro(novoValor));
    }

    /**
     * Abre a edição da nota para a mídia atual.
     *
     * Este método inicializa a edição do campo de nota usando um diálogo editável.
     * Permite alterar a nota da mídia com validações específicas:
     * - Converte vírgulas para pontos decimais
     * - Verifica se o valor é um número float válido
     * - Restringe a nota entre 0.0 e 5.0
     */
    @FXML
    private void edicaoNota() {
        editarCampoHeader(hboxInfo, editarNota,
                () -> String.format("%.1f", MidiaController.extrairNotaMaisRecente(midiaOriginal)),
                (novoValor) -> MidiaController.setNota(midiaOriginal, Float.parseFloat(novoValor.replace(",", "."))),
                (novoValor) -> {
                    String valorFormatado = novoValor.replace(",", ".");
                    if (!MidiaController.checkFloat(valorFormatado)) return false;
                    float valorFloat = Float.parseFloat(valorFormatado);
                    return valorFloat >= 0.0f && valorFloat <= 5.0f;
                });
    }

    /**
     * Alterna a visibilidade dos detalhes da mídia.
     *
     * Este método:
     * - Inverte o estado de visualização dos detalhes
     * - Mostra/esconde painéis e elementos de interface relacionados aos detalhes
     * - Para séries, oculta o campo de edição de nota
     * - Chama {@link #preencherDetalhes()} quando os detalhes são exibidos
     */
    @FXML
    private void verDetalhes() {
        vendoDetalhes = !vendoDetalhes;
        detalhesPane.setVisible(vendoDetalhes);
        detalhesPane.setManaged(vendoDetalhes);
        editarTitulo.setVisible(vendoDetalhes);
        editarAnoLancamento.setVisible(vendoDetalhes);
        if (!"Série".equals(MidiaController.getTipoMidia(midiaOriginal))) {
            editarNota.setVisible(vendoDetalhes);
        }
        if (vendoDetalhes) {
            preencherDetalhes();
        }
    }

    /**
     * Preenche os detalhes da mídia na interface.
     *
     * Método responsável por:
     * - Limpar contêineres de detalhes existentes
     * - Adicionar informações básicas como consumo e gêneros
     * - Usar pattern matching para exibir detalhes específicos de cada tipo de mídia
     * - Chamar métodos específicos para Filme, Série ou Livro
     */
    private void preencherDetalhes() {
        detalhesVbox.getChildren().clear();
        avaliacaoVbox.getChildren().clear();
        elencoVbox.getChildren().clear();

        adicionarLinhaEditavelCheckbox(detalhesVbox.getChildren(), "Já consumiu? ",
                () -> "Sim".equals(MidiaController.getJaConsumiu(midiaOriginal)),
                (novoValor) -> MidiaController.setJaConsumiu(midiaOriginal, novoValor));

        String generos = String.join(", ", MidiaController.getGeneros(midiaOriginal));
        detalhesVbox.getChildren().add(new Label("Gênero(s): " + generos));

        if (midiaOriginal instanceof Filme filme) {
            exibirInformacoesFilme(filme);
        } else if (midiaOriginal instanceof Serie serie) {
            exibirInformacoesSerie(serie);
        } else if (midiaOriginal instanceof Livro livro) {
            exibirInformacoesLivro(livro);
        }
    }

    /**
     * Exibe informações específicas de um Filme.
     *
     * Adiciona à interface detalhes como:
     * - Duração do filme
     * - Direção
     * - Roteiro
     * - Elenco
     * - Avaliações
     *
     * @param filme Objeto Filme a ser exibido
     */
    private void exibirInformacoesFilme(Filme filme) {
        adicionarLinhaEditavel(detalhesVbox.getChildren(), "Duração: ",
                () -> Integer.toString(FilmeController.getDuracao(filme)),
                (novo) -> FilmeController.setDuracao(filme, Integer.parseInt(novo)),
                (novo) -> MidiaController.checkInteiro(novo),
                (valor) -> valor + " min");

        adicionarLinhaEditavel(detalhesVbox.getChildren(), "Direção: ", () -> FilmeController.getDirecao(filme), (novo) -> FilmeController.setDirecao(filme, novo));
        adicionarLinhaEditavel(detalhesVbox.getChildren(), "Roteiro: ", () -> FilmeController.getRoteiro(filme), (novo) -> FilmeController.setRoteiro(filme, novo));

        preencherElenco(FilmeController.getElenco(filme));
        preencherAvaliacao(midiaOriginal);
    }

    /**
     * Exibe informações específicas de uma Série.
     *
     * Apresenta na interface:
     * - Ano de encerramento
     * - Número de temporadas
     * - Elenco de cada temporada
     * - Avaliações por temporada
     *
     * @param serie Objeto Serie a ser exibido
     */
    private void exibirInformacoesSerie(Serie serie) {
        adicionarLinhaEditavel(detalhesVbox.getChildren(), "Ano de Encerramento: ",
                () -> SerieController.getAnoEncerramento(serie),
                (novo) -> SerieController.setAnoEncerramento(serie, Integer.parseInt(novo)),
                (str) -> MidiaController.checkInteiro(str) && Integer.parseInt(str) >= 0,
                (valor) -> valor
        );

        detalhesVbox.getChildren().add(new Label("Temporadas: " + serie.getTemporadas().size()));

        for (var temporada : serie.getTemporadas()) {
            elencoVbox.getChildren().add(new Label("--- Elenco Temporada " + temporada.getNumeroTemporada() + " ---"));
            preencherElenco(SerieController.getElenco(temporada));
            preencherAvaliacao(temporada, "Temporada " + temporada.getNumeroTemporada() + ": ");
        }
    }

    /**
     * Exibe informações específicas de um Livro.
     *
     * Adiciona à interface detalhes como:
     * - Autor
     * - Editora
     * - Status de posse do exemplar
     * - Avaliações
     *
     * @param livro Objeto Livro a ser exibido
     */
    private void exibirInformacoesLivro(Livro livro) {
        adicionarLinhaEditavel(detalhesVbox.getChildren(), "Autor: ", () -> LivroController.getAutor(livro), (novo) -> LivroController.setAutor(livro, novo));
        adicionarLinhaEditavel(detalhesVbox.getChildren(), "Editora: ", () -> LivroController.getEditora(livro), (novo) -> LivroController.setEditora(livro, novo));
        adicionarLinhaEditavelCheckbox(detalhesVbox.getChildren(), "Possui Exemplar? ", () -> LivroController.getPossuiExemplar(livro), (novo) -> LivroController.setPossuiExemplar(livro, novo));
        preencherAvaliacao(midiaOriginal);
    }

    /**
     * Preenche o elenco de uma mídia na interface gráfica.
     *
     * @param elenco Um mapa onde a chave representa o papel (ex: "Ator", "Diretor")
     *               e o valor é uma lista de nomes correspondentes a esse papel
     */
    private void preencherElenco(Map<String, List<String>> elenco) {
        for (Map.Entry<String, List<String>> entry : elenco.entrySet()) {
            String nomes = String.join(", ", entry.getValue());
            elencoVbox.getChildren().add(new Label(entry.getKey() + "(s): " + nomes));
        }
    }

    /**
     * Preenche as informações de avaliação de uma mídia na interface gráfica.
     *
     * @param midia A mídia para a qual será exibida a avaliação
     * @param prefixo Um prefixo opcional para os labels de avaliação
     */
    private void preencherAvaliacao(Midia midia, String prefixo) {
        Avaliacao ultimaAvaliacao = MidiaController.getUltimaAvaliacao(midia);
        if (ultimaAvaliacao != null) {
            avaliacaoVbox.getChildren().addAll(
                    new Label(prefixo + "Nota: " + MidiaController.extrairNotaMaisRecente(midia)),
                    new Label(prefixo + "Comentário: " + ultimaAvaliacao.getAvaliacao()),
                    new Label(prefixo + "Consumido em: " + ultimaAvaliacao.getDataConsumo())
            );
        }
    }

    /**
     * Sobrecarga do método preencherAvaliacao sem prefixo.
     *
     * @param midia A mídia para a qual será exibida a avaliação
     */
    private void preencherAvaliacao(Midia midia) {
        preencherAvaliacao(midia, "");
    }

    /**
     * Adiciona uma linha editável genérica à interface gráfica.
     * Método simplificado que usa validação padrão e sem formatação especial.
     *
     * @param container A lista de nós onde a linha será adicionada
     * @param titulo Título do campo editável
     * @param getter Função para obter o valor atual
     * @param setter Função para definir o novo valor
     */
    private void adicionarLinhaEditavel(ObservableList<Node> container, String titulo, Supplier<String> getter, Consumer<String> setter) {
        adicionarLinhaEditavel(container, titulo, getter, setter, (str) -> !str.isBlank(), (valor) -> valor);
    }

    /**
     * Adiciona uma linha editável com validações e formatação personalizadas.
     *
     * @param container A lista de nós onde a linha será adicionada
     * @param titulo Título do campo editável
     * @param getter Função para obter o valor atual
     * @param setter Função para definir o novo valor
     * @param validator Função para validar o novo valor
     * @param formatter Função para formatar o valor exibido
     */
    private void adicionarLinhaEditavel(ObservableList<Node> container, String titulo, Supplier<String> getter, Consumer<String> setter, Predicate<String> validator, Function<String, String> formatter) {
        HBox linha = new HBox(10);
        Label label = new Label(titulo + formatter.apply(getter.get()));
        Button editar = new Button("Editar");
        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        editar.setOnAction(e -> {
            TextField campoEdicao = new TextField(getter.get());
            Button salvar = new Button("Salvar");

            salvar.setOnAction(ev -> {
                String novoValor = campoEdicao.getText().trim();
                if (validator.test(novoValor)) {
                    setter.accept(novoValor);
                    label.setText(titulo + formatter.apply(getter.get()));
                    salvarDados();
                }
                linha.getChildren().setAll(label, espacador, editar);
            });

            linha.getChildren().setAll(new Label(titulo), campoEdicao, espacador, salvar);
        });

        linha.getChildren().addAll(label, espacador, editar);
        container.add(linha);
    }

    /**
     * Adiciona uma linha editável com um checkbox para valores booleanos.
     *
     * @param container A lista de nós onde a linha será adicionada
     * @param titulo Título do campo editável
     * @param getter Função para obter o valor atual booleano
     * @param setter Função para definir o novo valor booleano
     */
    private void adicionarLinhaEditavelCheckbox(ObservableList<Node> container, String titulo, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        HBox linha = new HBox(10);
        Label label = new Label(titulo + (getter.get() ? "Sim" : "Não"));
        Button editar = new Button("Editar");
        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        editar.setOnAction(e -> {
            CheckBox campoEdicao = new CheckBox();
            campoEdicao.setSelected(getter.get());
            Button salvar = new Button("Salvar");

            salvar.setOnAction(ev -> {
                setter.accept(campoEdicao.isSelected());
                label.setText(titulo + (getter.get() ? "Sim" : "Não"));
                linha.getChildren().setAll(label, espacador, editar);
                salvarDados();
            });

            linha.getChildren().setAll(new Label(titulo), campoEdicao, espacador, salvar);
        });

        linha.getChildren().addAll(label, espacador, editar);
        container.add(linha);
    }

    /**
     * Edita um campo no cabeçalho da interface.
     *
     * @param linha A linha (HBox) contendo o campo a ser editado
     * @param botaoEditar O botão de edição
     * @param getter Função para obter o valor atual
     * @param setter Função para definir o novo valor
     * @param validator Função para validar o novo valor
     */
    private void editarCampoHeader(HBox linha, Button botaoEditar, Supplier<String> getter, Consumer<String> setter, Predicate<String> validator) {
        int indexBotao = linha.getChildren().indexOf(botaoEditar);
        if (indexBotao <= 0 || !(linha.getChildren().get(indexBotao - 1) instanceof Label)) return;

        Label labelAntigo = (Label) linha.getChildren().get(indexBotao - 1);
        TextField campoEdicao = new TextField(getter.get());
        Button botaoSalvar = new Button("Salvar");

        linha.getChildren().set(indexBotao - 1, campoEdicao);
        linha.getChildren().set(indexBotao, botaoSalvar);

        botaoSalvar.setOnAction(ev -> {
            String novoValor = campoEdicao.getText();
            if (validator.test(novoValor)) {
                setter.accept(novoValor);
                labelAntigo.setText(getter.get());
                salvarDados();
            }
            linha.getChildren().set(indexBotao - 1, labelAntigo);
            linha.getChildren().set(indexBotao, botaoEditar);
        });
    }

    /**
     * Salva os dados da mídia atual no controlador correspondente de acordo com o tipo de mídia.
     * Utiliza um switch para determinar o tipo de mídia e chama o método de salvamento apropriado.
     *
     * Os tipos de mídia suportados são:
     * - Livro: chama LivroController.salvarLivros()
     * - Filme: chama FilmeController.salvarFilmes()
     * - Série: chama SerieController.salvarSeries()
     */
    void salvarDados() {
        switch (midiaOriginal.getTipoMidia()){
            case "Livro" -> LivroController.salvarLivros();
            case "Filme" -> FilmeController.salvarFilmes();
            case "Série" -> SerieController.salvarSeries();
        }
    }

    /**
     * Manipula a exclusão de uma mídia específica do sistema.
     *
     * O método realiza as seguintes ações:
     * 1. Remove a mídia do controlador correspondente baseado no seu tipo
     * 2. Oculta o painel de detalhes
     * 3. Aciona um callback de exclusão se definido
     *
     * @throws IOException se ocorrer um erro durante o processo de remoção
     */
    @FXML
    private void onDeletarMidia() throws IOException {
        switch (MidiaController.getTipoMidia(midiaOriginal)) {
            case "Livro" -> RegistroControladores.getLivroController().removerMidia((Livro) midiaOriginal);
            case "Filme" -> RegistroControladores.getFilmeController().removerMidia((Filme) midiaOriginal);
            case "Série" -> RegistroControladores.getSerieController().removerMidia((Serie) midiaOriginal);
        }

        ocultarPanel();

        if (acaoDeExclusaoCallback != null) {
            System.out.println("1. MidiaItemController: Chamando o callback para deletar o item: " + midiaTabelaItem.getTitulo());

            acaoDeExclusaoCallback.accept(this.midiaTabelaItem);
        } else {
            System.out.println("ERRO: O callback de exclusão (acaoDeExclusaoCallback) está nulo!");
        }
    }

    /**
     * Oculta os painéis e elementos de detalhes da mídia.
     *
     * Realiza as seguintes ações:
     * - Define vendoDetalhes como false
     * - Torna o painel de detalhes invisível e não gerenciado
     * - Esconde os campos de edição de título, ano de lançamento e nota
     */
    private void ocultarPanel() {
        vendoDetalhes = false;
        detalhesPane.setVisible(false);
        detalhesPane.setManaged(false);
        editarTitulo.setVisible(false);
        editarAnoLancamento.setVisible(false);
        editarNota.setVisible(false);
    }

    /**
     * Abre a janela de avaliação para a mídia atual.
     *
     * O método:
     * 1. Carrega o FXML da tela de avaliação
     * 2. Inicializa o controlador de avaliação com a mídia atual
     * 3. Exibe a janela de avaliação como um modal
     * 4. Após fechar a janela, oculta o painel e salva os dados
     *
     * Trata possíveis exceções de IOException durante o carregamento do FXML
     */
    @FXML
    private void onAvaliarMidia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewJavaFX/CenasFXML/AvaliacaoMidia.fxml"));
            Parent root = loader.load();

            AvaliacaoMidiaController controller = loader.getController();

            controller.iniciar(this.midiaTabelaItem.getMidiaOriginal());

            Stage stage = new Stage();
            stage.setTitle("Registrar Avaliação");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            ocultarPanel();

            salvarDados();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
