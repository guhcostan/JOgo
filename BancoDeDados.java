import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe manipuladora de arquivos, gravando os principais dados da execução do programa
 */
public class BancoDeDados {

	/**
	 * Atributo que contem o nome do arquivo que será o banco de dados
	 */
	private static String nomeArquivo = "db.txt";

	/**
	 * Atributo que contem referencia pro arquivo
	 */
	private static File arquivo = new File(nomeArquivo);

	/**
	 * Metodo de inicialização do banco de dados, criando ou recriando o arquivo
	 */
	public static void iniciar(){
		if(arquivo.exists()){
			arquivo.delete();
		}
		try {
			arquivo.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo de gravação de textos no banco de dados
	 * @param texto
	 * @throws IOException
	 */
	public static void gravar(String texto) throws IOException {
		try(PrintWriter escritorTexto = new PrintWriter(new FileWriter(nomeArquivo, true))){
			escritorTexto.printf(texto + "\n");
		}catch (Exception e){
			throw e;
		}
	}
}
