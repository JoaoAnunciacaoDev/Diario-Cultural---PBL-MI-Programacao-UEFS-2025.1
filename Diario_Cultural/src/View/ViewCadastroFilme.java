package View;

import Controller.FilmeController;
import Service.Servicos;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Responsável pela interface de cadastro de filmes no sistema.
 * Gerencia a coleta e validação dos dados necessários para cadastrar um novo filme.
 */
public class ViewCadastroFilme {

    /**
     * Realiza o processo de cadastro de um novo filme.
     * Ao realizar o cadastro é chamado o método de salvandoFilmes() para persistir os dados.
     *
     * @param controller controlador de livros
     * @param scanner scanner para entrada de dados
     */
    public static void cadastrarFilme(FilmeController controller, Scanner scanner) {

        System.out.print("\nCadastrando Filme:\n");

        String titulo = Servicos.getValidarString("Título: ");

        List<String> generos = Servicos.getGenerosOUOndeAssistir("genero");

        System.out.print("Ano de Lançamento: ");
        int anoDeLancamento = (int) Servicos.getValidarEntrada("int");

        System.out.print("Duração (em minutos): ");
        int duracao = (int) Servicos.getValidarEntrada("int");

        String direcao = Servicos.getValidarString("Direção: ");

        String roteiro = Servicos.getValidarString("Roteiro: ");

        Map<String, List<String>> elenco = Servicos.getElenco();

        String tituloOriginal = Servicos.getValidarString("\nTítulo Original: ");

        List<String> ondeAssistir = Servicos.getGenerosOUOndeAssistir("onde_assistir");

        controller.cadastrarFilme(titulo, generos, anoDeLancamento, duracao, direcao, roteiro, elenco, tituloOriginal, ondeAssistir);

        System.out.println("\nFilme cadastrado com sucesso!");
        Servicos.salvandoFilmes();

    }
}
