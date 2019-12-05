import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Classe responsavel por mostrar uma imagem, implementada para facilitar o uso das mesmas
 */
public class ImagePanel extends JPanel {

	/**
	 * Referencia para a imagem
	 */
	private BufferedImage image;

	/**
	 * Instancia a classe para que possa ser utilizada a imagem
	 *
	 * @param pastaArq caminho para o arquivo de imagem
	 */
	public ImagePanel(String pastaArq) {
		try {
			image = ImageIO.read(new File(pastaArq));
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Sobrescrição do metodo de desenhar para que desenhe da forma escolhida
	 *
	 * @param g parte grafica
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

}