import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BancoDeDados {

	private static String nomeArquivo = "db.txt";
	private static File arquivo = new File(nomeArquivo);

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

	public static void gravar(String texto) throws IOException {
		PrintWriter escritorTexto = new PrintWriter(new FileWriter(nomeArquivo, true));
		escritorTexto.printf(texto + "\n");
		escritorTexto.close();
		escritorTexto.close();
	}
}
