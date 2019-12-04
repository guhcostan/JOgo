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
    private JPanel painelDireita;
    private JPanel painelSul;
    private JScrollPane painelConsole;
    private JTextArea console;
    private JTextField input;
    private ImagePanel imagem;

    TelaPrincipal(Jogo j) {
        janela = new JFrame("World of Zuul");
        painelEsquerda = new JPanel();
        painelDireita = new JPanel();
        painelSul = new JPanel();
        console = new JTextArea();
        painelConsole = new JScrollPane(console);
        input = new JTextField();
        imagem = new ImagePanel("./imagens/imagem.png");
        jogo = j;

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jogo.processarComando();
                input.setText("");
            }
        });

        montarJanela();
        atualizaTentativas(j.getNTentativas(), j.getNTentativasChaveMestra());
    }

    /**
     * Função monta a janela, configura as opções dos componentes
     * e adiciona os campos, imagem, paineis na janela(JFrame)
     */
    private void montarJanela() {
        janela.setSize(1050, 620);
        janela.setLayout(new BorderLayout());

        painelSul.setLayout(new BoxLayout(painelSul, BoxLayout.Y_AXIS));

        console.setEditable(false);

        painelConsole.setPreferredSize(new Dimension(1000, 200));
        painelConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        painelSul.add(painelConsole);
        painelSul.add(input);

        painelEsquerda.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
        painelDireita.setPreferredSize(new Dimension(200, 155));
        atualizaDicas();
        janela.add(painelEsquerda, BorderLayout.WEST);
        janela.add(painelDireita, BorderLayout.EAST);
        janela.add(imagem, BorderLayout.CENTER);
        janela.add(painelSul, BorderLayout.SOUTH);
    }

    private void atualizaDicas() {
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
        painelDireita.add(new JLabel("Dicas encontradas:")).setFont(new Font("SansSerif", Font.PLAIN, 15));

    }

    public void atualizaDicas(String texto) {
        painelDireita.add(new JLabel(texto)).setFont(new Font("SansSerif", Font.PLAIN, 15));
    }

    public void atualizaTentativas(int nTentativas, int nTentativasMestra) {
        painelEsquerda.removeAll();
        painelEsquerda.setLayout(new BoxLayout(painelEsquerda, BoxLayout.Y_AXIS));
        painelEsquerda.add(new JLabel("<html>Número de<br> tentativas restantes:<br></html>")).setFont(new Font("SansSerif", Font.BOLD, 16));
        painelEsquerda.add(new JLabel(String.valueOf(nTentativas))).setFont(new Font("SansSerif", Font.PLAIN, 15));
        painelEsquerda.add(new JLabel("<html><br>Durabilidade da<br> chave mestra:<br></html>")).setFont(new Font("SansSerif", Font.BOLD, 16));
        System.out.println(nTentativasMestra);
        painelEsquerda.add(new JLabel(String.valueOf(nTentativasMestra))).setFont(new Font("SansSerif", Font.PLAIN, 15));
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

    public void warning(String texto){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane(texto,JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Warning!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
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

    public void addImagemGameOver() {
        janela.remove(imagem);
        imagem = new ImagePanel("./imagens/game_over.png");
        janela.add(imagem, BorderLayout.CENTER);
        janela.revalidate();
    }

    public void addImagemTesouro() {
        janela.remove(imagem);
        imagem = new ImagePanel("./imagens/tesouro.png");
        janela.add(imagem, BorderLayout.CENTER);
        janela.revalidate();
    }

    public void travarInput() {
        input.setEditable(false);
    }

    public JTextField getInput() {
        return input;
    }
}