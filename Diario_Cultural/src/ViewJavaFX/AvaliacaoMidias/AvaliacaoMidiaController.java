package ViewJavaFX.AvaliacaoMidias;

import Controller.MidiaController;
import Model.Avaliacao;
import Model.Midia;
import Model.Serie;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

/**
 * Controlador responsável pela interface de avaliação de mídias no sistema Diário Cultural.
 *
 * Esta classe gerencia a funcionalidade de registro de avaliações para diferentes tipos de mídias,
 * com suporte especial para séries que requerem seleção de temporada.
 *
 * Principais responsabilidades:
 * <ul>
 *   <li>Validar dados de avaliação</li>
 *   <li>Registrar novas avaliações</li>
 *   <li>Fornecer interface para entrada de dados de avaliação</li>
 *   <li>Gerenciar a seleção de temporadas para séries</li>
 * </ul>
 *
 */
 public class AvaliacaoMidiaController {

    @FXML private Label tituloMidiaLabel;
    @FXML private ChoiceBox<Integer> notaChoiceBox;
    @FXML private Label temporadaLabel;
    @FXML private ChoiceBox<Integer> temporadaChoiceBox;
    @FXML private DatePicker dataConsumoPicker;
    @FXML private TextArea comentarioTextArea;
    @FXML private Button salvarButton;
    @FXML private Button cancelarButton;

    private Midia midiaParaAvaliar;

    /**
     * Inicializa os componentes da tela de avaliação.
     *
     * Configura a escolha de notas padrão, definindo o valor inicial como 5.
     */
    @FXML
    public void initialize() {
        notaChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        notaChoiceBox.setValue(5);
    }

    /**
     * Configura a tela de avaliação com os detalhes da mídia a ser avaliada.
     *
     * Para séries, ativa a seleção de temporada e popula as opções disponíveis.
     * Para outras mídias, oculta a seleção de temporada.
     *
     * @param midia Mídia que será avaliada
     */
    public void iniciar(Midia midia) {
        this.midiaParaAvaliar = midia;
        tituloMidiaLabel.setText("Avaliando: " + midia.getTitulo());

        if (midia instanceof Serie) {
            Serie serie = (Serie) midia;
            temporadaLabel.setVisible(true);
            temporadaChoiceBox.setVisible(true);

            temporadaChoiceBox.getItems().setAll(
                    IntStream.rangeClosed(1, serie.getTemporadas().size()).boxed().toArray(Integer[]::new)
            );
        }
    }

    /**
     * Salva a avaliação da mídia após validar os dados de entrada.
     *
     * Realiza as seguintes etapas:
     * <ul>
     *   <li>Valida a presença de nota, data de consumo e comentário</li>
     *   <li>Verifica a temporada para séries</li>
     *   <li>Cria um novo objeto de Avaliacao</li>
     *   <li>Associa a avaliação à mídia</li>
     *   <li>Exibe mensagem de sucesso</li>
     *   <li>Fecha a janela de avaliação</li>
     * </ul>
     */
    @FXML
    private void salvarAvaliacao() {
        if (notaChoiceBox.getValue() == null || dataConsumoPicker.getValue() == null || comentarioTextArea.getText() == null || comentarioTextArea.getText().isBlank()) {
            mostrarAlerta("Erro", "Por favor, preencha a nota, a data de consumo e o comentário.");
            return;
        }

        LocalDate dataConsumido = dataConsumoPicker.getValue();
        LocalDate hoje = LocalDate.now();
        if (dataConsumido.isAfter(hoje)) {
            mostrarAlerta("Erro de Validação", "A data de consumo não pode ser uma data futura.");
            return;
        }

        Integer temporada = null;
        if (midiaParaAvaliar instanceof Serie) {
            temporada = temporadaChoiceBox.getValue();
            if (temporada == null) {
                mostrarAlerta("Erro", "Por favor, selecione a temporada da série.");
                return;
            }
        }

        int nota = notaChoiceBox.getValue();
        LocalDate dataConsumo = dataConsumoPicker.getValue();
        String comentario = comentarioTextArea.getText();

        LocalDateTime hora_agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = hora_agora.format(formatter);

        Avaliacao novaAvaliacao = new Avaliacao(nota, comentario, dataFormatada, dataConsumo);

        MidiaController.setAvaliacao(midiaParaAvaliar, novaAvaliacao);

        mostrarAlerta("Sucesso", "Avaliação salva com sucesso!");

        fecharJanela();
    }

    /**
     * Cancela o processo de avaliação e fecha a janela.
     */
    @FXML
    private void cancelar() {
        fecharJanela();
    }

    /**
     * Fecha a janela atual de avaliação.
     *
     * Obtém o palco (Stage) atual a partir do botão de salvar e o fecha.
     */
    private void fecharJanela() {
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Exibe uma mensagem de alerta ao usuário.
     *
     * @param titulo Título da mensagem de alerta
     * @param mensagem Conteúdo da mensagem a ser exibida
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}