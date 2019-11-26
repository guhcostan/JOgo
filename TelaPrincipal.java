import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A classe "TelaPrincipal" é responsável por gerar a interface gráfica
 * principal com todos os componentes necessários
 */
class TelaPrincipal {
    private Jogo jogo;
    private JFrame janela;
    private JPanel painelEsquerda;
    private JPanel painelSul;
    private JScrollPane painelConsole;
    private JTextArea console;
    private JTextField input;

    TelaPrincipal(Jogo j) {
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
    private void montarJanela() {
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
    void exibir() {
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
        janela.setVisible(true);
    }

    /**
     * Retorna o input (JTextField) da janela
     *
     * @return Retorna o input da janela
     */
    JTextField getInput() {
        return input;
    }

    /**
     * Fecha a janela e por consequência finaliza o programa
     */
    void fechar() {
        janela.dispose();
    }

    /**
     * Rola o painelConsole até o final do console
     */
    private void rolarFinal() {
        console.selectAll();
        int x = console.getSelectionEnd();
        console.select(x, x);
    }

    /**
     * Adiciona no console uma quebra de linha
     */
    void adicionaTextoConsole() {
        console.append("\n");
        rolarFinal();
    }

    /**
     * Adiciona no console um texto que é informado por parâmetro e também uma quebra de linha
     *
     * @param texto
     */
    void adicionaTextoConsole(String texto) {
        console.append(texto + "\n");
        rolarFinal();
    }

}