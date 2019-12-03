import java.util.ArrayList;
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
    private int nTentativas;
    private int nTentativasChaveMestra;
    private TelaPrincipal telaPrincipal;
    private boolean temCargaExplosiva;

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    Jogo() {
        criarAmbientes();
        criarNTentativas();
        criarTesouro();
        sortearChaveMestra();
    }

    private void criarNTentativas() {
        nTentativas = new Random().nextInt(30) + 20;
        nTentativasChaveMestra = 0;
    }

    private void criarTesouro() {
        temCargaExplosiva = true;

        Ambiente ambienteTesouro = ambientes[new Random().nextInt(ambientes.length)];
        ambienteTesouro.setTemTesouro();
        gerarDicasParaAmbientes(ambienteTesouro);

        System.out.println(ambienteTesouro.getNome());
    }

    /**
     * Gera três dicas com o texto "O tesouro não está no(a) X" e uma dica com o
     * texto "O tesouro está próximo ao(à) Y”
     */
    private void gerarDicasParaAmbientes(Ambiente ambienteTesouro) {

        ArrayList<Ambiente> vizinhos;
        Ambiente vizinhoDoTesouro;
        Ambiente randomAmbienteDicaUm;
        Ambiente randomAmbienteDicaDois;
        Ambiente randomAmbiente;

        int contraNumeroDeDicasGeradas = 0;
        while (contraNumeroDeDicasGeradas < 3) {

            randomAmbienteDicaUm = ambientes[new Random().nextInt(ambientes.length)];
            randomAmbiente = ambientes[new Random().nextInt(ambientes.length)];

            if (randomAmbienteDicaUm.getDica().equals("") && randomAmbiente.getTemTesouro() == false) {
                randomAmbienteDicaUm
                        .setDica("<html><br> O tesouro não está <br> no(a) " + randomAmbiente.getNome() + "</html>");
                contraNumeroDeDicasGeradas++;
            }
        }

        vizinhos = ambienteTesouro.getVizinho();
        vizinhoDoTesouro = vizinhos.get(new Random().nextInt(vizinhos.size()));
        randomAmbienteDicaDois = ambientes[new Random().nextInt(ambientes.length)];
        randomAmbienteDicaDois
                .setDica("<html><br> O tesouro está <br> próximo ao(à) " + vizinhoDoTesouro.getNome() + "</html>");
    }

    private void sortearChaveMestra() {
        Ambiente ambienteChaveMestra = ambientes[new Random().nextInt(ambientes.length)];
        ambienteChaveMestra.setChaveMestra(new Random().nextInt(ambientes.length));

        System.out.println(ambienteChaveMestra.getNome());
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
        escritorio.ajustarSaidas(null, null, new Ambiente[] { salaTv }, null);
        salaTv.ajustarSaidas(new Ambiente[] { escritorio }, new Ambiente[] { salaJantar }, new Ambiente[] { jardim },
                null);
        jardim.ajustarSaidas(new Ambiente[] { salaTv, cozinha }, null, null, null);
        cozinha.ajustarSaidas(new Ambiente[] { salaJantar }, null, new Ambiente[] { jardim }, null);
        salaJantar.ajustarSaidas(null, new Ambiente[] { corredor }, new Ambiente[] { cozinha },
                new Ambiente[] { salaTv });
        corredor.ajustarSaidas(new Ambiente[] { quarto1, quarto2 }, new Ambiente[] { quarto3 },
                new Ambiente[] { banheiro1, quarto4 }, new Ambiente[] { salaJantar });
        quarto1.ajustarSaidas(null, null, new Ambiente[] { corredor }, null);
        quarto2.ajustarSaidas(null, null, new Ambiente[] { corredor }, null);
        quarto3.ajustarSaidas(null, null, new Ambiente[] { banheiro2 }, new Ambiente[] { corredor });
        quarto4.ajustarSaidas(new Ambiente[] { corredor }, null, null, null);
        banheiro1.ajustarSaidas(new Ambiente[] { corredor }, null, null, null);
        banheiro2.ajustarSaidas(new Ambiente[] { quarto3 }, null, null, null);

        ambienteAtual = salaTv; // o jogo comeca do lado de fora
        ambientes = new Ambiente[] { escritorio, salaTv, cozinha, salaJantar, jardim, quarto1, quarto2, quarto3,
                quarto4, banheiro1, banheiro2, corredor };

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

        telaPrincipal.atualizaDicas(ambienteAtual.getDica());
        if (ambienteAtual.getChaveMestra() != 0) {
            telaPrincipal.adicionaTextoConsole("Você encontrou a chave mestra.");
            telaPrincipal.adicionaTextoConsole("Agora você pode entrar em lugares onde a porta está emperrada.\n");
            nTentativasChaveMestra = ambienteAtual.getChaveMestra();
            ambienteAtual.setChaveMestra(0);
        }
        telaPrincipal.atualizaTentativas(nTentativas, nTentativasChaveMestra);
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     */
    void processarComando() {
        Comando comando = analisador.pegarComando();

        if (comando.ehDesconhecido()) {
            telaPrincipal.adicionaTextoConsole("Eu nao entendi o que voce disse...\n");
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
            case "explodir":
                explodir();
                break;
            }
        }
    }

    private void escolherOpcao(Comando comando) {
        Ambiente proximoAmbiente = null;

        if (direcaoEscolhida != null) {
            try {
                Ambiente[] saidas = ambienteAtual.getSaida(direcaoEscolhida);
                if (saidas.length >= Integer.parseInt(comando.getSegundaPalavra())) {
                    proximoAmbiente = saidas[Integer.parseInt(comando.getSegundaPalavra()) - 1];
                    ambienteAtual = proximoAmbiente;
                    debitaSaldoTentativas();

                    verificaEhUltimaTentaiva();
                } else {
                    telaPrincipal.adicionaTextoConsole("Opção invalida!\n");
                }

                direcaoEscolhida = null;
                ImprimirLocalizacaoAtual();

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            telaPrincipal.adicionaTextoConsole("Nenhuma direção foi escolhida!\n");
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
        telaPrincipal.adicionaTextoConsole();
    }

    /**
     * Tenta ir em uma direcao. Se existe uma saida entra no novo ambiente, caso
     * contrario imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando) {
        if (nTentativas == 0) {
            telaPrincipal.adicionaTextoConsole("Você não pode mais se mover! Seu número de tentativas acabou.\n");
        } else if (!comando.temSegundaPalavra()) {
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
                        telaPrincipal.adicionaTextoConsole(
                                "Ha mais de uma porta, escolha uma, digite 'opcao' e o numero da opcao!");
                        for (int x = 0; x < saidas.length; x++)
                            telaPrincipal.adicionaTextoConsole((x + 1) + " - " + saidas[x].getNome());
                        direcaoEscolhida = direcao;
                    } else if (saidas.length == 1) {
                        proximoAmbiente = saidas[0];
                        ambienteAtual = proximoAmbiente;

                        debitaSaldoTentativas();

                        ImprimirLocalizacaoAtual();

                        verificaEhUltimaTentaiva();
                    }
                }
            } catch (Exception e) {
                telaPrincipal.adicionaTextoConsole("Opção invalida!\n");
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

    public int getNTentativas() {
        return nTentativas;
    }

    public int getNTentativasChaveMestra() {
        return nTentativasChaveMestra;
    }

    private void debitaSaldoTentativas() {
        if (nTentativasChaveMestra != 0) {
            nTentativasChaveMestra = this.nTentativasChaveMestra - 1;
        } else if (nTentativas > 0) {
            nTentativas = this.nTentativas - 1;
        }

        telaPrincipal.atualizaTentativas(nTentativas, nTentativasChaveMestra);
    }

    private void explodir() {
        if (temCargaExplosiva) {
            telaPrincipal.adicionaTextoConsole("BOOOOM!");

            if (ambienteAtual.getTemTesouro()) {
                telaPrincipal.addImagemTesouro();
                telaPrincipal.adicionaTextoConsole("Parabéns! Você encontrou o tesouro.");
            } else {
                telaPrincipal.addImagemGameOver();
                telaPrincipal.adicionaTextoConsole("Se F#de0!");
                telaPrincipal.adicionaTextoConsole("Você usou sua única carga e não encontrou o tesoura.");
            }
        }

        temCargaExplosiva = false;
        fimDeJogo();
    }

    private void fimDeJogo() {
        telaPrincipal.getInput().setEditable(false);
    }

    private void verificaEhUltimaTentaiva() {
        if (nTentativas == 0 && temCargaExplosiva) {
            telaPrincipal.adicionaTextoConsole("Seu número de tentativas acabou."
                    + " Agora você pode apenas mandar explodir para tentar encontrar o tesouro.\n");
        }
    }
}
