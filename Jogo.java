import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Essa eh a classe principal da aplicacao "World of Zull". "World of Zuul" eh
 * um jogo de aventura muito simples, baseado em texto. Usuarios podem caminhar
 * em um cenario. E eh tudo! Ele realmente precisa ser estendido para fazer algo
 * interessante!
 *
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o metodo
 * "jogar".
 *
 * Essa classe principal cria e inicializa todas as outras: ela cria os
 * ambientes, cria o analisador e comeca o jogo. Ela tambeme avalia e executa os
 * comandos que o analisador retorna.
 *
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */

public class Jogo {
	private Analisador analisador;
	private Ambiente ambienteAtual;
	private Ambiente[] ambientes;
	private boolean terminado = false;
	/**
	 * Cria o jogo e incializa seu mapa interno.
	 */
	public Jogo() {
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
		Ambiente escritorio, salaTv, cozinha, salaJantar, jardim, quarto1, quarto2, quarto3, quarto4, banheiro1, banheiro2, corredor;

		// cria os ambientes
		escritorio = new Ambiente(
				"escritorio");
		salaTv = new Ambiente(
				"sala de tv");
		cozinha = new Ambiente(
				"cozinha");
		salaJantar = new Ambiente(
				"sala de jantar");
		jardim = new Ambiente(
				"jardim");
		quarto1 = new Ambiente(
				"quarto 1");
		quarto2 = new Ambiente(
				"quarto 2");
		quarto3 = new Ambiente(
				"quarto 3");
		quarto4 = new Ambiente(
				"quarto 4");
		banheiro1 = new Ambiente(
				"banheiro 1");
		banheiro2 = new Ambiente(
				"banheiro 2");
		corredor = new Ambiente(
				"correodr");

		// inicializa as saidas dos ambientes
		escritorio.ajustarSaidas(null, null, new Ambiente[] { salaTv }, null);
		salaTv.ajustarSaidas(new Ambiente[] { escritorio },
				new Ambiente[] { salaJantar }, new Ambiente[] { jardim }, null);
		jardim.ajustarSaidas(new Ambiente[] { salaTv, cozinha }, null, null,
				null);
		cozinha.ajustarSaidas(new Ambiente[] { salaJantar }, null,
				new Ambiente[] { jardim }, null);
		salaJantar.ajustarSaidas(null, new Ambiente[] { corredor },
				new Ambiente[] { cozinha }, new Ambiente[] { salaTv });
		corredor.ajustarSaidas(new Ambiente[] { quarto1, quarto2 },
				new Ambiente[] { quarto3 },
				new Ambiente[] { banheiro1, quarto4 },
				new Ambiente[] { salaJantar });
		quarto1.ajustarSaidas(null, null, new Ambiente[] { corredor }, null);
		quarto2.ajustarSaidas(null, null, new Ambiente[] { corredor }, null);
		quarto3.ajustarSaidas(null, null, new Ambiente[] { banheiro2 },
				new Ambiente[] { corredor });
		quarto4.ajustarSaidas( new Ambiente[] { corredor }, null, null, null);
		banheiro1.ajustarSaidas(new Ambiente[] { corredor }, null, null, null);
		banheiro2.ajustarSaidas(new Ambiente[] { quarto3 }, null, null, null);

		ambienteAtual = salaTv; // o jogo comeca do lado de fora
		ambientes = new Ambiente[]{escritorio, salaTv, cozinha, salaJantar, jardim, quarto1, quarto2, quarto3, quarto4, banheiro1, banheiro2, corredor};

	}


	public static void printConsoleConfig(JTextArea txtConsole) {

		// Now create a new TextAreaOutputStream to write to our JTextArea control and wrap a
// PrintStream around it to support the println/printf methods.
		PrintStream out = new PrintStream(new TextAreaOutputStream(txtConsole));

// redirect standard output stream to the TextAreaOutputStream
		System.setOut(out);

// redirect standard error stream to the TextAreaOutputStream
		System.setErr(out);

// now test the mechanism
		System.out.println("Hello World");
	}


	/**
	 * Rotina principal do jogo. Fica em loop ate terminar o jogo.
	 */
	public void jogar() {

		JFrame janela = new JFrame();
		janela.setSize(1000, 500);
		janela.setLayout(new BorderLayout());
		JPanel painelEsquerda = new JPanel();
		janela.setVisible(true);
		janela.add(painelEsquerda, BorderLayout.WEST);
		JPanel painelSul = new JPanel();
		painelSul.setLayout(new BoxLayout(painelSul, BoxLayout.Y_AXIS));
		JTextArea console = new JTextArea();
		console.setEditable(false);
		printConsoleConfig(console);
		painelSul.add(console);
		JTextField input = new JTextField();
		painelSul.add(input);
		janela.add(painelSul, BorderLayout.SOUTH);
		janela.add(new ImagePanel(), BorderLayout.CENTER);

		analisador = new Analisador(input);
		imprimirBoasVindas();

		// Entra no loop de comando principal. Aqui nos repetidamente lemos
		// comandos e os executamos ate o jogo terminar.

		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Comando comando = analisador.pegarComando();

				terminado = processarComando(comando);
				input.setText("");
			}
		});
		while (!terminado) {

		}
		System.out.println("Obrigado por jogar. Ate mais!");
	}

	/**
	 * Imprime a mensagem de abertura para o jogador.
	 */
	private void imprimirBoasVindas() {
		System.out.println();
		System.out.println("Bem-vindo ao World of Zuul!");
		System.out
				.println("World of Zuul eh um novo jogo de aventura, incrivelmente chato.");
		System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
		System.out.println();

		ImprimirLocalizacaoAtual();
	}

	private void ImprimirLocalizacaoAtual() {
		System.out.println("Voce esta na " + ambienteAtual.getNome() + "da casa mal assombrada.");

		System.out.print("Saidas: ");
		System.out.println(ambienteAtual.getTodasSaidas());
		System.out.println();
	}

	/**
	 * Dado um comando, processa-o (ou seja, executa-o)
	 *
	 * @param comando
	 *            O Comando a ser processado.
	 * @return true se o comando finaliza o jogo.
	 */
	private boolean processarComando(Comando comando) {
		boolean querSair = false;

		if (comando.ehDesconhecido()) {
			System.out.println("Eu nao entendi o que voce disse...");
			return false;
		}

		String palavraDeComando = comando.getPalavraDeComando();
		if (palavraDeComando.equals("ajuda")) {
			imprimirAjuda();
		} else if (palavraDeComando.equals("ir")) {
			irParaAmbiente(comando);
		} else if (palavraDeComando.equals("sair")) {
			querSair = sair(comando);
		} else if (palavraDeComando.equals("observar")) {
			ImprimirLocalizacaoAtual();
		}

		return querSair;
	}

	// Implementacoes dos comandos do usuario

	/**
	 * Printe informacoes de ajuda. Aqui nos imprimimos algo bobo e enigmatico e
	 * a lista de palavras de comando
	 */
	private void imprimirAjuda() {
		System.out
				.println("Voce esta perdido. Voce esta sozinho. Voce caminha");
		System.out.println("pela universidade.");
		System.out.println();
		System.out.println("Suas palavras de comando sao:");
		for (String comando : PalavrasComando.comandosValidos) {
			System.out.println(comando + " ");
		}

	}

	/**
	 * Tenta ir em uma direcao. Se existe uma saida entra no novo ambiente, caso
	 * contrario imprime mensagem de erro.
	 */
	private void irParaAmbiente(Comando comando) {
		if (!comando.temSegundaPalavra()) {
			// se nao ha segunda palavra, nao sabemos pra onde ir...
			System.out.println("Ir pra onde?");
			return;
		}

		String direcao = comando.getSegundaPalavra();

		Scanner scanner = new Scanner(System.in);

		// Tenta sair do ambiente atual
		Ambiente proximoAmbiente = null;

		try{
			Ambiente[] saidas = ambienteAtual.getSaida(direcao);
			if(saidas != null){
				if (saidas.length > 1) {
					System.out.println("Ha mais de uma porta, escolha uma!");
					for (int x = 0; x < saidas.length; x++)
						System.out.println(x + " - " + saidas[x].getNome());
					int opcao = scanner.nextInt();
					if(saidas.length >= opcao){
						proximoAmbiente = saidas[opcao];
					}else{
						System.out.println("Opção invalida!");
					}
				}else if(saidas.length == 1){
					proximoAmbiente = saidas[0];
				}
				ambienteAtual = proximoAmbiente;

				ImprimirLocalizacaoAtual();
			}
		}
		catch(Exception e){
			System.out.println("Opção invalida!");
		}

	}

	/**
	 * "Sair" foi digitado. Verifica o resto do comando pra ver se nos queremos
	 * realmente sair do jogo.
	 *
	 * @return true, se este comando sai do jogo, false, caso contrario
	 */
	private boolean sair(Comando comando) {
		if (comando.temSegundaPalavra()) {
			System.out.println("Sair o que?");
			return false;
		} else {
			return true; // sinaliza que nos queremos sair
		}
	}
}
