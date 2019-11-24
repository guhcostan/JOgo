import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.awt.Dimension;

/**
 * A classe "TelaPrincipal" é responsável por gerar a interface gráfica
 * principal com todos os componentes necessários
 */
public class TelaPrincipal {
    private Jogo jogo;
    private JFrame janela;
    private JPanel painelEsquerda;
    private JPanel painelSul;
    private JScrollPane painelConsole;
    private JTextArea console;
    private JTextField input;

    public TelaPrincipal(Jogo j) {
        janela = new JFrame("World of Zuul");
        painelEsquerda = new JPanel();
        painelSul = new JPanel();
        console = new JTextArea();
        painelConsole = new JScrollPane(console);
        input = new JTextField();
        jogo = j;

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jogo.processarComando();
                input.setText("");
            }
        });

        montarJanela();
    }

    /**
     * Função monta a janela, configura as opções dos componentes 
     * e adiciona os campos, imagem, paineis na janela(JFrame)
     */
    public void montarJanela() {
        janela.setSize(1000, 550);
        janela.setLayout(new BorderLayout());

        painelSul.setLayout(new BoxLayout(painelSul, BoxLayout.Y_AXIS));

        console.setEditable(false);

        painelConsole.setPreferredSize(new Dimension(1000, 155));
        painelConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        painelSul.add(painelConsole);
        painelSul.add(input);

        janela.add(painelEsquerda, BorderLayout.WEST);
        janela.add(new ImagePanel(), BorderLayout.CENTER);
        janela.add(painelSul, BorderLayout.SOUTH);
    }

    /**
     * Configura a janela para terminar a execução do programa quando fecha a janela,
     * iniciar centralizada, não permitir alterar seu tamanho padrão e por fim a deixa visível
     */
    public void exibir() {
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
        janela.setVisible(true);
    }

    /**
     * Retorna o input (JTextField) da janela
     * @return Retorna o input da janela
     */
    public JTextField getInput() {
        return input;
    }

    /**
     * Fecha a janela e por consequência finaliza o programa
     */
    public void fechar() {
        janela.dispose();
    }

    /**
     * Rola o painelConsole até o final do console
     */
    public void rolarFinal() {
        console.selectAll();
        int x = console.getSelectionEnd();
        console.select(x, x);
    }

    /**
     * Adiciona no console uma quebra de linha
     */
    public void adicionaTextoConsole() {
        console.append("\n");
        rolarFinal();
    }

    /**
     * Adiciona no console um texto que é informado por parâmetro e também uma quebra de linha
     * @param texto
     */
    public void adicionaTextoConsole(String texto) {
        console.append(texto + "\n");
        rolarFinal();
    }

}