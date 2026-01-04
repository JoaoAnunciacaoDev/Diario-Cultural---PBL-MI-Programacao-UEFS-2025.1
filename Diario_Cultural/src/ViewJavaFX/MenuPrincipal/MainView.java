package ViewJavaFX.MenuPrincipal;

import Testes.CadastroPreDefinido;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal que inicializa a aplicação JavaFX do Diário Cultural.
 *
 * Esta classe estende {@link javafx.application.Application} e é responsável por:
 * <ul>
 *   <li>Inicializar os controladores necessários</li>
 *   <li>Carregar dados de livros, filmes e séries</li>
 *   <li>Carregar dados predefinidos para testes se necessário</li>
 *   <li>Configurar e exibir a cena principal do aplicativo</li>
 * </ul>
 *
 * @author João Victor Anunciação da Silva
 * @version 1.0
 * @since 21/03/2025
 */
public class MainView extends Application {

    /**
     * Método principal que inicia a aplicação JavaFX.
     *
     * Este método chama o método {@code launch()} herdado da classe {@link javafx.application.Application},
     * que inicializa o ciclo de vida da aplicação JavaFX e chama o método {@code start()}.
     *
     * @param args Argumentos de linha de comando (não utilizados nesta aplicação)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método de inicialização principal da aplicação JavaFX.
     *
     * Realiza as seguintes tarefas:
     * <ul>
     *   <li>Inicializa os controladores do sistema</li>
     *   <li>Tenta carregar livros, filmes e séries existentes</li>
     *   <li>Cadastra mídias predefinidas se nenhum dado for carregado</li>
     *   <li>Carrega o layout FXML da tela principal</li>
     *   <li>Configura e exibe o palco principal da aplicação</li>
     * </ul>
     *
     * @param palcoPrimario O palco principal da aplicação JavaFX
     * @throws IOException Se houver erro ao carregar o arquivo FXML
     */
    @Override
    public void start(Stage palcoPrimario) throws IOException {

        RegistroControladores.initialize();

        boolean livrosCarregados = RegistroControladores.getLivroController().carregarLivros();
        boolean filmesCarregados = RegistroControladores.getFilmeController().carregarFilmes();
        boolean seriesCarregados = RegistroControladores.getSerieController().carregarSeries();

        if (!livrosCarregados && !filmesCarregados && !seriesCarregados) {
            CadastroPreDefinido.cadastrarMidiasPadrao(RegistroControladores.getLivroController(), RegistroControladores.getFilmeController(), RegistroControladores.getSerieController());
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewJavaFX/CenasFXML/MenuPrincipal.fxml"));
        Parent root = loader.load();

        Scene cena = new Scene(root);
        palcoPrimario.setTitle("Diário Cultural");
        palcoPrimario.setScene(cena);
        palcoPrimario.show();
    }
}
