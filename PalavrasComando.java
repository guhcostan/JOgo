import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Esta classe eh parte da aplicacao "World of Zuul". "World of Zuul" eh um jogo
 * de aventura muito simples, baseado em texto.
 * <p>
 * Essa classe guarda uma enumeracao de todos os comandos conhecidos do jogo.
 * Ela eh usada no reconhecimento de comandos como eles sao digitados.
 *
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */

class PalavrasComando {

	/**
	 * um vetor constante que guarda todas as palavras de comandos validas
	 */
	static List<String> comandosValidos = new ArrayList<>();

	/**
	 * Instancia unica da classe
	 */
	private static PalavrasComando instance;

	/**
	 * Construtor - inicializa as palavras de comando.
	 */
	PalavrasComando() {
		comandosValidos = Arrays.asList("ir", "sair", "opcao", "ajuda", "observar", "explodir");
	}

	/**
	 * Sigleton para garantir o uso da instancia unica
	 *
	 * @return instancia
	 */
	public static PalavrasComando getInstance() {
		if (instance == null) {
			synchronized (PalavrasComando.class) {
				if (instance == null) {
					instance = new PalavrasComando();
				}
			}
		}
		return instance;
	}

	/**
	 * Verifica se uma dada String eh uma palavra de comando valida.
	 *
	 * @return true se a string dada eh um comando valido, false se nao eh.
	 */
	boolean ehComando(String umaString) {
		return comandosValidos.contains(umaString);
	}
}
