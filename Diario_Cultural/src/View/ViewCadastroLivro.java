package View;

import Controller.LivroController;
import Service.Servicos;

import java.util.List;
import java.util.Scanner;

/**
 * Responsável pela interface de cadastro de livros no sistema.
 * Gerencia a coleta e validação dos dados necessários para cadastrar um novo livro.
 */
public class ViewCadastroLivro {

    /**
     * Realiza o processo de cadastro de um novo livro.
     * Ao realizar o cadastro é chamado o método de salvandoLivros() para persistir os dados.
     *
     * @param controller controlador de livros
     * @param scanner scanner para entrada de dados
     */
    public static void cadastrarLivro(LivroController controller, Scanner scanner) {

        System.out.print("\nCadastrando livro:\n");

        String titulo = Servicos.getValidarString("Título: ");

        String autor = Servicos.getValidarString("Autor: ");

        String editora = Servicos.getValidarString("Editora: ");

        List<String> generos = Servicos.getGenerosOUOndeAssistir("genero");

        System.out.print("Ano de Publicação: ");
        int anoDePublicacao = (int) Servicos.getValidarEntrada("int");

        String isbn =  Servicos.getValidarString("ISBN: ");

        String resposta = Servicos.getValidarString("Possui exemplar (sim / não): ");
        boolean possui;
        possui = resposta.equalsIgnoreCase("sim");

        controller.cadastrarLivro(titulo, autor, editora, generos, anoDePublicacao, isbn, possui);

        System.out.println("\nLivro cadastrado com sucesso!");
        Servicos.salvandoLivros();

    }
}
