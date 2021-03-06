import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 * <p>
 * Esta classe eh parte da aplicacao "World of Zuul". "World of Zuul" eh um jogo de aventura muito
 * simples, baseado em texto.
 * <p>
 * Um "Ambiente" representa uma localizacao no cenario do jogo. Ele eh conectado aos outros
 * ambientes atraves de saidas. As saidas sao nomeadas como norte, sul, leste e oeste. Para cada
 * direcao, o ambiente guarda uma referencia para o ambiente vizinho, ou null se nao ha saida
 * naquela direcao.
 *
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */
public class Ambiente {

	/**
	 * Atributo que da nome ao ambiente
	 */
	private String nome;
	/**
	 * Atributo que informa se ambiente possui o tesouro
	 */
	private boolean temTesouro = false;
	/**
	 * Atributo que informa a dica que o ambiente possui
	 */
	private String dica = "";
	/**
	 * Atributo que contem todas as saidas ligada ao ambiente
	 */
	private HashMap<String, Ambiente[]> saidas = new HashMap<>();
	/**
	 * Atributo que diz se a chave mestra esta no ambiente
	 */
	private int chaveMestra = 0;

	/**
	 * Cria um ambiente com a "descricao" passada. Inicialmente, ele nao tem saidas. "descricao" eh
	 * algo como "uma cozinha" ou " Create a room described "description". Initially, it has no
	 * exits. "description" is something like "a kitchen" or "um jardim aberto".
	 *
	 * @param descricao A descricao do ambiente.
	 */
	public Ambiente(String nome) {
		this.nome = nome;
	}

	/**
	 * Metodo para se obter a saida referente a direção informada
	 *
	 * @param direcao
	 * @return saidas
	 */
	public Ambiente[] getSaida(String direcao) {
		return this.saidas.get(direcao);
	}

	/**
	 * Metodo que retorna os ambientes vizinhos ao ambiente ambiente
	 *
	 * @return ambientesVizinhos
	 */
	public ArrayList<Ambiente> getVizinho() {
		ArrayList<Ambiente> vizinhos = new ArrayList<>();
		for (Entry<String, Ambiente[]> saida : saidas.entrySet()) {

			for (Ambiente ambiente : saida.getValue()) {
				vizinhos.add(ambiente);
			}
		}
		return vizinhos;
	}

	/**
	 * Metodo para conferir se ambiente possui o tesouro
	 *
	 * @return se tem tesouro
	 */
	public boolean getTemTesouro() {
		return this.temTesouro;
	}

	/**
	 * Metodo para adicionar tesouro no ambiente
	 */
	public void setTemTesouro() {
		this.temTesouro = true;
	}

	/**
	 * @return O nome do ambiente.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Metodo para pegar dica do ambiente
	 *
	 * @return dica
	 */
	public String getDica() {
		return dica;
	}

	/**
	 * Metodo para instalar dica no ambiente
	 *
	 * @param dica
	 */
	public void setDica(String dica) {
		this.dica = dica;
	}

	/**
	 * Meotodo para pegar a chaveMestra
	 *
	 * @return chaveMestra
	 */
	public int getChaveMestra() {
		return chaveMestra;
	}

	/**
	 * Metodo para colocar chave mestra no ambiente
	 *
	 * @param chaveMestra
	 */
	public void setChaveMestra(int chaveMestra) {
		this.chaveMestra = chaveMestra;
	}

	/**
	 * Metodo para retornar todas as saidas do ambiente atual
	 *
	 * @return saidas
	 */
	public String getTodasSaidas() {
		StringBuilder saidasString = new StringBuilder();
		for (Entry<String, Ambiente[]> saida : saidas.entrySet()) {
			saidasString.append("  ").append(saida.getKey()).append(":\n");
			for (Ambiente ambiente : saida.getValue()) {
				saidasString.append("    -");
				saidasString.append(ambiente.getNome()).append(" ");
				saidasString.append("\n");
			}
		}
		return saidasString.toString();
	}

	/**
	 * Define as saidas do ambiente. Cada direcao ou leva a um outro ambiente ou eh null (nenhuma
	 * saida para la).
	 *
	 * @param norte A saida norte.
	 * @param leste A saida leste.
	 * @param sul   A saida sul.
	 * @param oeste A saida oeste.
	 */
	public void ajustarSaidas(Ambiente[] norte, Ambiente[] leste,
		Ambiente[] sul, Ambiente[] oeste) {
        if (norte != null) {
            saidas.put("norte", norte);
        }
        if (leste != null) {
            saidas.put("leste", leste);
        }
        if (sul != null) {
            saidas.put("sul", sul);
        }
        if (oeste != null) {
            saidas.put("oeste", oeste);
        }
	}

	/**
	 * Metodo para adicionar nova saida ao ambiente
	 *
	 * @param direcao
	 * @param ambiente
	 */
	public void adicionarSaida(String direcao, Ambiente ambiente) {
		Ambiente[] ambientes = saidas.get(direcao);
		ambientes[ambientes.length] = ambiente;
		saidas.put(direcao, ambientes);
	}
}
