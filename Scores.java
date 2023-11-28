import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scores extends JPanel {
    private final CardLayout cardLayout;
    private final Font font;
    private final JPanel panelContainer;
    private final DataBase dataBase;
    public Scores(CardLayout cardLayout, Font font, JPanel panelContainer) {
        this.cardLayout = cardLayout;
        this.font = font;
        this.panelContainer = panelContainer;
        dataBase = new DataBase();
        add(scoresPanel());
    }
    public JPanel scoresPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.add(genParent());
        return mainPanel;
    }
    private ImagePanel genParent() {
        ImagePanel panel = new ImagePanel();
        panel.setPreferredSize(new Dimension(400, 550));
        panel.add(topContent());
        panel.add(mainTitle());
        panel.add(generateLeaders());
        return panel;
    }
    private JPanel topContent() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(400, 50));
        panel.add(generateExitButton());
        return panel;
    }
    private JLabel mainTitle() {
        JLabel score = new JLabel("Scores");
        score.setBounds(50, 50, 150, 50);
        score.setFont(font.deriveFont(Font.BOLD, 48f));
        score.setForeground(Color.WHITE);
        Border mainTitleborder = score.getBorder();
        Border marginTopFifty = new EmptyBorder(50, 0, 50, 0);
        score.setBorder(new CompoundBorder(mainTitleborder, marginTopFifty));
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        return score;
    }
    private JPanel generateLeaders() {
        JPanel main = new JPanel();
        main.setPreferredSize(new Dimension(300, 200));
        main.setOpaque(false);
        if (!dataBase.getAll().isEmpty()) for (String[] data : dataBase.getAll()) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(300, 40));
            panel.setLayout(new BorderLayout());
            panel.setBackground(Color.BLACK);
            JLabel score = newScoreLabel(data[1]);
            JLabel date = newDateLabel(data[2]);
            panel.add(score, BorderLayout.WEST);
            panel.add(date, BorderLayout.EAST);
            main.add(panel);
        }
        return main;
    }
    private JButton generateExitButton() {
        JButton returnButton = new JButton("Back to menu");
        returnButton.setForeground(Color.RED);
        returnButton.setBackground(Color.BLACK);
        returnButton.setFont(font.deriveFont(Font.PLAIN, 16f));
        returnButton.setFocusable(false);
        returnButton.setBorderPainted(false);
        returnButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContainer, "Home");
            }
        });
        return returnButton;
    }
    private JLabel newScoreLabel(String score) {
        JLabel scoreLabel = new JLabel(score);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        scoreLabel.setFont(font.deriveFont(Font.PLAIN, 20f));
        return scoreLabel;
    }

    private JLabel newDateLabel(String date) {
        JLabel dateLabel = new JLabel(date);
        dateLabel.setForeground(Color.white);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        dateLabel.setFont(font.deriveFont(Font.PLAIN, 16f));
        return dateLabel;
    }
}
