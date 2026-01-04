package ViewJavaFX.MenuPrincipal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utilitário para troca de cenas em aplicações JavaFX.
 *
 * Esta classe fornece um método estático para carregar e exibir uma nova cena
 * em um palco (Stage) existente, utilizando um arquivo FXML como base.
 *
 * <p>A classe é projetada para simplificar a navegação entre diferentes
 * interfaces gráficas em uma aplicação JavaFX.</p>
 */
public class TrocarCena {

    /**
     * Troca a cena atual do palco por uma nova cena carregada a partir de um arquivo FXML.
     *
     * Este método realiza as seguintes operações:
     * <ul>
     *   <li>Carrega o arquivo FXML especificado</li>
     *   <li>Cria uma nova cena com o conteúdo carregado</li>
     *   <li>Define a nova cena no palco fornecido</li>
     *   <li>Exibe o palco atualizado</li>
     * </ul>
     *
     * @param stage O palco (janela) no qual a cena será trocada
     * @param fxmlFile Caminho para o arquivo FXML que define a nova interface
     * @throws IOException Se houver erro ao carregar o arquivo FXML
     *
     * @see javafx.fxml.FXMLLoader
     * @see javafx.scene.Scene
     * @see javafx.stage.Stage
     */
    public static void trocaCena(Stage stage, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(TrocarCena.class.getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
