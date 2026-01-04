package ViewJavaFX.CadastroMidias;

/**
 * Representa um integrante do elenco em uma produção cultural.
 *
 * Esta classe encapsula as informações básicas de um membro do elenco,
 * armazenando sua função (papel/cargo) e nome.
 *
 */
 public class IntegranteElenco {
    /**
     * Função/papel do integrante do elenco.
     * Pode representar um cargo como ator, diretor, roteirista, etc.
     *
     */
    private String funcao;

    /**
     * Nome do integrante do elenco.
     */
    private String nome;

    /**
     * Construtor para criar um novo integrante do elenco.
     *
     * @param funcao Função ou papel do integrante na produção
     * @param nome Nome completo do integrante
     */
    public IntegranteElenco(String funcao, String nome) {
        this.funcao = funcao;
        this.nome = nome;
    }

    /**
     * Obtém a função do integrante do elenco.
     *
     * @return A função do integrante como uma string
     */
    public String getFuncao() { return funcao; }

    /**
     * Obtém o nome do integrante do elenco.
     *
     * @return O nome do integrante como uma string
     */
    public String getNome() { return nome; }
}

