import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 * <p>
 * Esta classe eh parte da aplicacao "World of Zuul". "World of Zuul" eh um jogo
 * de aventura muito simples, baseado em texto.
 * <p>
 * Um "Ambiente" representa uma localizacao no cenario do jogo. Ele eh conectado
 * aos outros ambientes atraves de saidas. As saidas sao nomeadas como norte,
 * sul, leste e oeste. Para cada direcao, o ambiente guarda uma referencia para
 * o ambiente vizinho, ou null se nao ha saida naquela direcao.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */
public class Ambiente {
	public String nome;
	private boolean temTesouro = false;
	private HashMap<String, Ambiente[]> saidas = new HashMap<>();

	public Ambiente[] getSaida(String direcao) {
		return this.saidas.get(direcao);
	}
	
	public void setTesouro(){
		this.temTesouro = true;
	}

	public String getTodasSaidas() {
		StringBuilder saidasString = new StringBuilder();
		for (Entry<String, Ambiente[]> saida : saidas.entrySet()) {
			saidasString.append("  " + saida.getKey() + ":\n");
			for(Ambiente ambiente : saida.getValue()){
				saidasString.append("    -");
				saidasString.append(ambiente.getNome()).append(" ");
				saidasString.append("\n");
			}
		}
		return saidasString.toString();
	}

	/**
	 * Cria um ambiente com a "descricao" passada. Inicialmente, ele nao tem
	 * saidas. "descricao" eh algo como "uma cozinha" ou " Create a room
	 * described "description". Initially, it has no exits. "description" is
	 * something like "a kitchen" or "um jardim aberto".
	 * 
	 * @param descricao
	 *            A descricao do ambiente.
	 */
	public Ambiente(String nome) {
		this.nome = nome;
	}

	/**
	 * Define as saidas do ambiente. Cada direcao ou leva a um outro ambiente ou
	 * eh null (nenhuma saida para la).
	 * 
	 * @param norte
	 *            A saida norte.
	 * @param leste
	 *            A saida leste.
	 * @param sul
	 *            A saida sul.
	 * @param oeste
	 *            A saida oeste.
	 */
	public void ajustarSaidas(Ambiente[] norte, Ambiente[] leste,
			Ambiente[] sul, Ambiente[] oeste) {
		if (norte != null)
			saidas.put("norte", norte);
		if (leste != null)
			saidas.put("leste", leste);
		if (sul != null)
			saidas.put("sul", sul);
		if (oeste != null)
			saidas.put("oeste", oeste);
	}

	/**
	 * @return A descricao do ambiente.
	 */
	public String getNome() {
		return nome;
	}

	public void adicionarSaida(String direcao, Ambiente ambiente) {
		Ambiente[] ambientes = saidas.get(direcao);
		ambientes[ambientes.length] = ambiente;
		saidas.put(direcao, ambientes);
	}
}
