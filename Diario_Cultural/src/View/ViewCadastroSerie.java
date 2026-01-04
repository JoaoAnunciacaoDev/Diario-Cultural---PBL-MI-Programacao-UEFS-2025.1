package View;

import Controller.SerieController;
import Model.Midia;
import Model.Serie;
import Model.Temporada;
import Service.Servicos;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Responsável pela interface de cadastro de séries e temporadas no sistema.
 * Gerencia a coleta e validação dos dados necessários para cadastrar um novo filme.
 */

public class ViewCadastroSerie {

    /**
     * Realiza o processo de cadastro de uma nova série.
     * Ao realizar o cadastro é chamado o método de salvandoSeries()
     * da classe Servicos para persistir os dados.
     * @param controller controlador de série
     * @param scanner scanner para entrada de dados
     */
    public static void cadastrarSerie(SerieController controller, Scanner scanner) {

        System.out.print("\nCadastrando Série:\n");

        String titulo = Servicos.getValidarString("Título: ");

        List<String> generos = Servicos.getGenerosOUOndeAssistir("genero");

        System.out.print("Ano de Lançamento: ");
        int anoDeLancamento = (int) Servicos.getValidarEntrada("int");

        System.out.print("Ano de Encerramento (0 se ainda em exibição): ");
        int anoDeEncerramento = (int) Servicos.getValidarEntrada("int");

        Map<String, List<String>> elenco = Servicos.getElenco();

        String tituloOriginal = Servicos.getValidarString("Título Original: ");

        List<String> ondeAssistir = Servicos.getGenerosOUOndeAssistir("onde_assistir");

        controller.cadastrarSerie(titulo, generos, anoDeLancamento, anoDeEncerramento, elenco, tituloOriginal, ondeAssistir);

        System.out.println("\nSérie cadastrada com sucesso!");
        Servicos.salvandoSeries();

    }

    /**
     * Realiza o processo de cadastro de uma nova temporada.
     * Ao realizar o cadastro é chamado o método de salvarLivros() para persistir os dados.
     *
     * @param controller controlador de série
     * @param scanner scanner para entrada de dados
     */
    public static void cadastrarTemporada(SerieController controller, Scanner scanner) {

        if (controller.getSeries().isEmpty()) {
            System.out.println("\nNenhuma série cadastrada ainda.");
            return;
        }

        System.out.println("\nCadastrar Temporada:");

        for (int i = 0; i < controller.getSeries().size(); i++) {

            Serie serie = controller.getSeries().get(i);
            System.out.println("[" + (i + 1) + "] " + serie.getTitulo() + " " + serie.getAnoLancamento() + " Temporadas: " + serie.getTemporadas().size());

        }

        List<? extends Midia> resultados_da_busca = ViewBusca.buscarPorTitulo(controller.getSeries(), scanner);

        int indice_serie = ViewLista.selecionarMidia(resultados_da_busca);

        if (indice_serie < 0) return;

        Serie serie_selecionada = (Serie) resultados_da_busca.get(indice_serie);

        System.out.print("Ano de lançamento da temporada: ");
        int ano_de_lancamento = (int) Servicos.getValidarEntrada("int");

        Map<String, List<String>> elenco_temporada = Servicos.getElenco();

        int numero_temporada = 0;

        while (true) {

            System.out.print("Número da temporada: ");
            numero_temporada = (int) Servicos.getValidarEntrada("int");

            if (numero_temporada == -1) {

                System.out.println("Operação cancelada.");
                return;

            }

            boolean temporadaExiste = false;

            for (Temporada temporada : serie_selecionada.getTemporadas()) {

                if (temporada.getNumeroTemporada() == numero_temporada) {

                    temporadaExiste = true;
                    break;

                }
            }

            if (!temporadaExiste) {

                break;

            } else System.out.println("Essa temporada já existe. Por favor, digite outro número ou -1 para cancelar.");

        }

        System.out.print("Quantidade de episódios da temporada: ");
        int quantidade_episodios = (int) Servicos.getValidarEntrada("int");

        List<String> ondeAssistir = Servicos.getGenerosOUOndeAssistir("onde_assistir");

        controller.cadastrarTemporada(serie_selecionada.getTitulo(), SerieController.getGeneros(serie_selecionada), ano_de_lancamento, elenco_temporada,
                ((Serie) resultados_da_busca.get(indice_serie)).getTituloOriginal(), ondeAssistir, numero_temporada, quantidade_episodios, serie_selecionada);

        System.out.println("\nTemporada cadastrada com sucesso!");
        Servicos.salvandoSeries();

    }

}
