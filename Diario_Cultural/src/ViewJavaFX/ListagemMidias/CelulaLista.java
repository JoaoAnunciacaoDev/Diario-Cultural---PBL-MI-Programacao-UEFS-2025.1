package ViewJavaFX.ListagemMidias;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Representa uma célula personalizada para uma lista de mídias na interface gráfica JavaFX.
 *
 * Esta classe estende {@link ListCell} para criar uma representação customizada
 * de cada item na lista de mídias. Cada célula pode ser configurada com uma ação
 * de exclusão personalizada.
 *
 * Características principais:
 * <ul>
 *   <li>Carrega um layout FXML personalizado para cada célula</li>
 *   <li>Permite definir uma ação de exclusão dinâmica</li>
 *   <li>Configura o conteúdo gráfico de cada célula da lista</li>
 * </ul>
 *
 */
 public class CelulaLista extends ListCell<MidiaTabela> {

    /**
     * Nó gráfico que representa o conteúdo da célula.
     */
    private Node graphic;

    /**
     * Controlador responsável por gerenciar os itens individuais da lista.
     */
    private MidiaItemController controllerItem;

    /**
     * Função de callback para executar a ação de exclusão de um item.
     */
    private final Consumer<MidiaTabela> acaoDeExclusao;

    /**
     * Construtor que inicializa a célula da lista com uma ação de exclusão.
     *
     * Realiza as seguintes etapas:
     * <ul>
     *   <li>Armazena a função de exclusão</li>
     *   <li>Carrega o layout FXML para a célula</li>
     *   <li>Obtém o controlador do item</li>
     * </ul>
     *
     * @param acaoDeExclusao Função que será chamada quando um item for deletado
     */
    public CelulaLista(Consumer<MidiaTabela> acaoDeExclusao) {
        this.acaoDeExclusao = acaoDeExclusao;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewJavaFX/CenasFXML/MidiaItem.fxml"));
            graphic = loader.load();
            controllerItem = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao carregar o FXML para a célula da lista", e);
        }
    }

    /**
     * Atualiza o conteúdo da célula da lista.
     *
     * Este método é chamado automaticamente pelo JavaFX para cada item na lista.
     * Configura o conteúdo gráfico da célula baseado no item atual:
     * <ul>
     *   <li>Define o conteúdo como nulo se o item estiver vazio</li>
     *   <li>Configura os dados do item utilizando o controlador</li>
     *   <li>Define o gráfico personalizado para a célula</li>
     * </ul>
     *
     * @param midia Item da mídia a ser exibido na célula
     * @param empty Indica se a célula está vazia
     */
    @Override
    protected void updateItem(MidiaTabela midia, boolean empty) {
        super.updateItem(midia, empty);

        if (empty || midia == null) {
            setGraphic(null);
        } else {
            controllerItem.setDados(midia.getMidiaOriginal(), midia, acaoDeExclusao);
            setGraphic(graphic);
        }
    }
}