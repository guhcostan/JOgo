import java.util.Random;

/**
 * Essa eh a classe principal da aplicacao "World of Zull". "World of Zuul" eh
 * um jogo de aventura muito simples, baseado em texto. Usuarios podem caminhar
 * em um cenario. E eh tudo! Ele realmente precisa ser estendido para fazer algo
 * interessante!
 * <p>
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o metodo
 * "jogar".
 * <p>
 * Essa classe principal cria e inicializa todas as outras: ela cria os
 * ambientes, cria o analisador e comeca o jogo. Ela tambeme avalia e executa os
 * comandos que o analisador retorna.
 *
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */

class Jogo {
    private Analisador analisador;
    private Ambiente ambienteAtual;
    private String direcaoEscolhida;
    private Ambiente[] ambientes;
    private TelaPrincipal telaPrincipal;

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    Jogo() {
        criarAmbientes();
        criaTesouro();
    }

    private void criaTesouro() {
        ambientes[new Random().nextInt(ambientes.length)].setTesouro();
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {
        Ambiente escritorio, salaTv, cozinha, salaJantar, jardim, quarto1, quarto2, quarto3, quarto4, banheiro1,
                banheiro2, corredor;

        // cria os ambientes
        escritorio = new Ambiente("escritorio");
        salaTv = new Ambiente("sala de tv");
        cozinha = new Ambiente("cozinha");
        salaJantar = new Ambiente("sala de jantar");
        jardim = new Ambiente("jardim");
        quarto1 = new Ambiente("quarto 1");
        quarto2 = new Ambiente("quarto 2");
        quarto3 = new Ambiente("quarto 3");
        quarto4 = new Ambiente("quarto 4");
        banheiro1 = new Ambiente("banheiro 1");
        banheiro2 = new Ambiente("banheiro 2");
        corredor = new Ambiente("corredor");

        // inicializa as saidas dos ambientes
        escritorio.ajustarSaidas(null, null, new Ambiente[]{salaTv}, null);
        salaTv.ajustarSaidas(new Ambiente[]{escritorio}, new Ambiente[]{salaJantar}, new Ambiente[]{jardim},
                null);
        jardim.ajustarSaidas(new Ambiente[]{salaTv, cozinha}, null, null, null);
        cozinha.ajustarSaidas(new Ambiente[]{salaJantar}, null, new Ambiente[]{jardim}, null);
        salaJantar.ajustarSaidas(null, new Ambiente[]{corredor}, new Ambiente[]{cozinha},
                new Ambiente[]{salaTv});
        corredor.ajustarSaidas(new Ambiente[]{quarto1, quarto2}, new Ambiente[]{quarto3},
                new Ambiente[]{banheiro1, quarto4}, new Ambiente[]{salaJantar});
        quarto1.ajustarSaidas(null, null, new Ambiente[]{corredor}, null);
        quarto2.ajustarSaidas(null, null, new Ambiente[]{corredor}, null);
        quarto3.ajustarSaidas(null, null, new Ambiente[]{banheiro2}, new Ambiente[]{corredor});
        quarto4.ajustarSaidas(new Ambiente[]{corredor}, null, null, null);
        banheiro1.ajustarSaidas(new Ambiente[]{corredor}, null, null, null);
        banheiro2.ajustarSaidas(new Ambiente[]{quarto3}, null, null, null);

        ambienteAtual = salaTv; // o jogo comeca do lado de fora
        ambientes = new Ambiente[]{escritorio, salaTv, cozinha, salaJantar, jardim, quarto1, quarto2, quarto3,
                quarto4, banheiro1, banheiro2, corredor};

    }

    /**
     * Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    void jogar() {
        telaPrincipal = new TelaPrincipal(this);
        telaPrincipal.exibir();

        analisador = new Analisador(telaPrincipal.getInput());
        imprimirBoasVindas();
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        telaPrincipal.adicionaTextoConsole("Bem-vindo ao World of Zuul!");
        telaPrincipal.adicionaTextoConsole("World of Zuul eh um novo jogo de aventura, incrivelmente chato.");
        telaPrincipal.adicionaTextoConsole("Digite 'ajuda' se voce precisar de ajuda.");
        telaPrincipal.adicionaTextoConsole("");

        ImprimirLocalizacaoAtual();
    }

    /**
     * Função imprime na tela a localização atual do jogador
     */
    private void ImprimirLocalizacaoAtual() {
        telaPrincipal.adicionaTextoConsole(">Voce esta na " + ambienteAtual.getNome() + " da casa mal assombrada.");

        telaPrincipal.adicionaTextoConsole("Saidas: ");
        telaPrincipal.adicionaTextoConsole(ambienteAtual.getTodasSaidas());
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     */
    void processarComando() {
        Comando comando = analisador.pegarComando();

        if (comando.ehDesconhecido()) {
            telaPrincipal.adicionaTextoConsole("Eu nao entendi o que voce disse...");
        } else {
            String palavraDeComando = comando.getPalavraDeComando();
            switch (palavraDeComando) {
                case "ajuda":
                    imprimirAjuda();
                    break;
                case "ir":
                    irParaAmbiente(comando);
                    break;
				case "opcao":
					escolherOpcao(comando);
					break;
                case "sair":
                    sair(comando);
                    break;
                case "observar":
                    ImprimirLocalizacaoAtual();
                    break;
            }
        }
    }

	private void escolherOpcao(Comando comando) {
		Ambiente proximoAmbiente = null;

		try {
			Ambiente[] saidas = ambienteAtual.getSaida(direcaoEscolhida);
			if (saidas.length >= Integer.parseInt(comando.getSegundaPalavra())) {
				proximoAmbiente = saidas[Integer.parseInt(comando.getSegundaPalavra())];
			} else {
				telaPrincipal.adicionaTextoConsole("Opção invalida!");
			}
			ambienteAtual = proximoAmbiente;

			ImprimirLocalizacaoAtual();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	// Implementacoes dos comandos do usuario

    /**
     * Printe informacoes de ajuda. Aqui nos imprimimos algo bobo e enigmatico e a
     * lista de palavras de comando
     */
    private void imprimirAjuda() {
        telaPrincipal.adicionaTextoConsole("Voce esta perdido. Voce esta sozinho. Voce caminha");
        telaPrincipal.adicionaTextoConsole("pela universidade.");
        telaPrincipal.adicionaTextoConsole();
        telaPrincipal.adicionaTextoConsole("Suas palavras de comando sao:");
        for (String comando : PalavrasComando.comandosValidos) {
            telaPrincipal.adicionaTextoConsole(comando + " ");
        }

    }

    /**
     * Tenta ir em uma direcao. Se existe uma saida entra no novo ambiente, caso
     * contrario imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde ir...
            telaPrincipal.adicionaTextoConsole("Ir pra onde?");
        } else {
            String direcao = comando.getSegundaPalavra();

            // Tenta sair do ambiente atual
            Ambiente proximoAmbiente = null;

            try {
                Ambiente[] saidas = ambienteAtual.getSaida(direcao);
                if (saidas != null) {
                    if (saidas.length > 1) {
                        telaPrincipal.adicionaTextoConsole("Ha mais de uma porta, escolha uma, digite 'opcao' e o numero da opcao!");
                        for (int x = 0; x < saidas.length; x++)
                            telaPrincipal.adicionaTextoConsole(x + " - " + saidas[x].getNome());
							direcaoEscolhida = direcao;
                    } else if (saidas.length == 1) {
                        proximoAmbiente = saidas[0];
						ambienteAtual = proximoAmbiente;

						ImprimirLocalizacaoAtual();
                    }
                }
            } catch (Exception e) {
                telaPrincipal.adicionaTextoConsole("Opção invalida!");
            }
        }

    }

    /**
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nos queremos
     * realmente sair do jogo.
     *
     * @return true, se este comando sai do jogo, false, caso contrario
     */
    private void sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            telaPrincipal.adicionaTextoConsole("Sair o que?");
        } else {
            telaPrincipal.fechar();
        }
    }
}
