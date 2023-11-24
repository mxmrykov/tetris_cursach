import javax.swing.*;
import java.awt.*;

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        frame.setPreferredSize(new Dimension(400, 550));
        setBounds((int) width / 2 - 200, (int) height / 2 - 260, 400, 550);
        frame.setLayout(new BorderLayout());
        panelContainer = new JPanel();
        cardLayout = new CardLayout();
        panelContainer.setLayout(cardLayout);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/LLPixel.ttf"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game game = new Game(cardLayout, font, panelContainer);
        Home home = new Home(cardLayout, font, panelContainer);

        home.game = game;
        game.home = home;

        panelContainer.add(home, "Home");
        panelContainer.add(game, "Game");
        add(panelContainer);
    }
}
