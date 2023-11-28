import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JPanel {
    private final JLabel count = generateScoreLabel();
    public int lastCount = 0;
    private GameZone gameZone = generateGameZone();
    private int ct = 0;
    private Timer tm;
    public Home home;
    public final CardLayout cardLayout;
    private final Font font;
    private final JPanel panelContainer;
    public Game(CardLayout cardLayout, Font font, JPanel panelContainer) {
        this.cardLayout = cardLayout;
        this.font = font;
        this.panelContainer = panelContainer;
        add(setGameScreen());
    }

    public void restartGame() {
        gameZone.restart();
    }
    public int getScore() {
        return gameZone.getCount();
    }
    public void pause() {
        gameZone.pause();
    }

    public void play() {
        gameZone.resume();
    }
    public JPanel setGameScreen() {
        JPanel gameScreen = generateGamePanel();

        UIManager.put("Button.select", Color.BLACK);

        count.setText("Score: " + gameZone.getCount());

        gameScreen.add(topPanel(count));
        gameScreen.add(gameZone);
        gameScreen.add(generateLeftButton());
        gameScreen.add(generateRotateButton());
        gameScreen.add(generateRightButton());
        gameScreen.setName("Game");

        Timer timer;
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastCount != gameZone.getCount()) {
                    home.updateScore(gameZone.getCount());
                    lastCount = gameZone.getCount();
                    animationForCount();
                    count.setText("Score: " + gameZone.getCount());
                    if (gameZone.gameOver) {
                        home.dataBase.saveData(gameZone.getCount());
                    }
                }
            }
        });
        timer.start();

        return gameScreen;
    }
    private void animationForCount() {
        tm = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ct < 3) {
                    if (ct % 2 == 0) {
                        count.setForeground(Color.YELLOW);
                    } else {
                        count.setForeground(Color.RED);
                    }
                    ct++;
                } else {
                    tm.stop();
                    count.setForeground(Color.BLUE);
                    ct = 0;
                }
            }
        });
        tm.start();
    }

    private JPanel topPanel(JLabel count) {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.black);
        topPanel.setPreferredSize(new Dimension(380, 30));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(generateExitButton(), BorderLayout.WEST);
        topPanel.add(count, BorderLayout.EAST);
        return topPanel;
    }

    private JPanel generateGamePanel() {
        JPanel gameScreen = new JPanel();
        gameScreen.setBackground(Color.black);
        gameScreen.setPreferredSize(new Dimension(400, 520));
        return gameScreen;
    }

    private JButton generateExitButton() {
        JButton returnButton = new JButton("Back to menu");
        returnButton.setForeground(Color.RED);
        returnButton.setBackground(Color.black);
        returnButton.setFont(font.deriveFont(Font.PLAIN, 16f));
        returnButton.setFocusable(false);
        returnButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
                cardLayout.show(panelContainer, "Home");
            }
        });
        return returnButton;
    }

    private JLabel generateScoreLabel() {
        JLabel count = new JLabel();
        Font f;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/LLPixel.ttf"));
            count.setFont(f.deriveFont(Font.PLAIN, 24f));
            count.setForeground(Color.blue);
            return count;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private GameZone generateGameZone() {
        GameZone gameZone = new GameZone();
        gameZone.setPreferredSize(new Dimension(380, 420));
        gameZone.setBackground(Color.black);
        gameZone.setBorder(BorderFactory.createLineBorder(Color.white));
        return gameZone;
    }

    private JButton generateLeftButton() {
        JButton leftButton = new JButton("❮");
        leftButton.setForeground(Color.white);
        leftButton.setBackground(Color.black);
        leftButton.setFont(new Font("Regular", Font.PLAIN, 24));
        leftButton.setFocusable(false);
        leftButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameZone.moveLeft();
            }
        });
        return leftButton;
    }

    private JButton generateRightButton() {
        JButton rightButton = new JButton("❯");
        rightButton.setForeground(Color.white);
        rightButton.setBackground(Color.black);
        rightButton.setFont(new Font("Regular", Font.PLAIN, 24));
        rightButton.setFocusable(false);
        rightButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameZone.moveRight();
            }
        });
        return rightButton;
    }

    private JButton generateRotateButton() {
        JButton rotateBtn = new JButton("⭯");
        rotateBtn.setForeground(Color.white);
        rotateBtn.setBackground(Color.black);
        rotateBtn.setFont(new Font("Regular", Font.PLAIN, 24));
        rotateBtn.setFocusable(false);
        rotateBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        rotateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameZone.rotate();
            }
        });
        return rotateBtn;
    }
}
