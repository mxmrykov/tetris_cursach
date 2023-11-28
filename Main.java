import javax.swing.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Screens screens = new Screens();
                screens.setVisible(true);
                screens.pack();
            }
        });
    }
}
