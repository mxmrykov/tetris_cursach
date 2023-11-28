package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Screens extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final double width = screenSize.getWidth();
    final double height = screenSize.getHeight();
    private final JPanel panelContainer;
    private final CardLayout cardLayout;
    public Font font;
    public final JFrame frame;

    public Screens() {
        frame = new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(400, 560));
        frame.setPreferredSize(new Dimension(400, 560));
        setBounds((int) width / 2 - 200, (int) height / 2 - 280, 400, 560);
        frame.setLayout(new BorderLayout());
        panelContainer = new JPanel();
        cardLayout = new CardLayout();
        panelContainer.setLayout(cardLayout);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/lib/LLPixel.ttf"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game game = new Game(cardLayout, font, panelContainer);
        Home home = new Home(cardLayout, font, panelContainer);
        Scores scores = new Scores(cardLayout, font, panelContainer);

        home.game = game;
        home.scores = scores;
        game.home = home;
        game.scores = scores;

        panelContainer.add(home, "Home");
        panelContainer.add(scores, "Score");
        panelContainer.add(game, "Game");
        add(panelContainer);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.rotate();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.moveDown();
                }
            }
        });
    }
}
