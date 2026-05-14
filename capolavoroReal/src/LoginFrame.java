import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("SafeVault - Accesso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new GridBagLayout()); // Per centrare il contenuto
        root.setBackground(new Color(30, 31, 34)); // Grigio scurissimo moderno

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(300, 220));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Effetto "Shadow/Border" di FlatLaf
        card.putClientProperty("FlatLaf.style", """
            arc: 30;
            background: #2B2D31;
        """);

        JLabel title = new JLabel("Bentornato");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Inserisci la Master Password");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setForeground(Color.GRAY);

        JPasswordField master = new JPasswordField();
        master.setMaximumSize(new Dimension(250, 40));
        master.putClientProperty("JTextField.placeholderText", "Password...");
        master.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton login = new JButton("Sblocca Vault");
        login.setBackground(new Color(88, 101, 242)); // Colore tipo Discord
        login.setForeground(Color.WHITE);
        login.setMaximumSize(new Dimension(250, 40));
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        login.addActionListener(e -> {
            try {
                String pass = new String(master.getPassword());
                String data = Storage.load(pass);
                new ManagerFrame(pass, data);
                dispose();
            } catch (Exception ex) {
                master.putClientProperty("JComponent.outline", "error");
                JOptionPane.showMessageDialog(this, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(sub);
        card.add(Box.createVerticalStrut(25));
        card.add(master);
        card.add(Box.createVerticalStrut(15));
        card.add(login);

        root.add(card);
        add(root);
        setVisible(true);
    }
}