import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import com.formdev.flatlaf.FlatClientProperties; // Importante per le proprietà speciali

public class ManagerFrame extends JFrame {
    private final String master;
    // Modello per la tabella (Colonne: Servizio, Password)
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Servizio", "Password"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Rende la tabella non modificabile direttamente
        }
    };
    private final JTable table = new JTable(tableModel);

    public ManagerFrame(String master, String data) {
        this.master = master;

        setTitle("SafeVault - Gestore Password");
        setSize(700, 600); // Leggermente più grande per la tabella
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // PANNELLO PRINCIPALE CON PADDING GENEROSO
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(UIManager.getColor("Frame.background"));

        // TITOLO (Più moderno, allineato a sinistra)
        JLabel title = new JLabel("Il Tuo Vault Personale");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(UIManager.getColor("AccentColor"));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // --- AREA CENTRALE: TABELLA ---
        if (!data.isEmpty()) {
            for (String line : data.split("\n")) {
                if (line.contains(" : ")) {
                    String[] parts = line.split(" : ");
                    if (parts.length == 2) {
                        tableModel.addRow(parts);
                    }
                }
            }
        }

        // Configurazione Tabella
        table.setRowHeight(35); // Righe più alte per togliere affollamento
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setShowGrid(false); // Nascondiamo la griglia classica
        table.setIntercellSpacing(new Dimension(0, 0));

        // Proprietà speciale FlatLaf per arrotondare gli angoli dello scrollpane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.putClientProperty(FlatClientProperties.STYLE, "arc: 12; background: $Panel.background;");

        mainPanel.add(scroll, BorderLayout.CENTER);


        // --- AREA INFERIORE: INPUT E BOTTONI ---
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.setOpaque(false);

        // Pannello Input (Servizio e Password)
        JPanel inputFieldsPanel = new JPanel(new GridBagLayout());
        inputFieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField serviceField = new JTextField();
        // Proprietà FlatLaf per Placeholder e bordi speciali
        serviceField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nome Servizio (es. Google)");
        serviceField.putClientProperty(FlatClientProperties.STYLE, "showClearButton: true;");



        JTextField passField = new JTextField();
        passField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        passField.putClientProperty(FlatClientProperties.STYLE, "showClearButton: true;");

        gbc.gridx = 0; gbc.gridy = 0; inputFieldsPanel.add(new JLabel("Servizio:"), gbc);
        gbc.gridx = 1; inputFieldsPanel.add(serviceField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputFieldsPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; inputFieldsPanel.add(passField, gbc);

        // Pannello Bottoni
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setOpaque(false);

        JButton addBtn = new JButton("Aggiungi");
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        // Lo rendiamo un bottone "principale" usando lo stile di FlatLaf
        addBtn.putClientProperty(FlatClientProperties.STYLE, "background: $AccentColor; foreground: #FFFFFF;");

        JButton saveBtn = new JButton("Salva nel Vault");
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        buttonsPanel.add(saveBtn);
        buttonsPanel.add(addBtn);

        southPanel.add(inputFieldsPanel, BorderLayout.CENTER);
        southPanel.add(buttonsPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // --- AZIONI ---
        addBtn.addActionListener(e -> {
            if (!serviceField.getText().isEmpty() && !passField.getText().isEmpty()) {
                tableModel.addRow(new Object[]{serviceField.getText(), passField.getText()});
                serviceField.setText("");
                passField.setText("");
            } else {
                serviceField.putClientProperty("JComponent.outline", "error");
                passField.putClientProperty("JComponent.outline", "error");
            }
        });

        saveBtn.addActionListener(e -> {
            try {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    sb.append(tableModel.getValueAt(i, 0))
                            .append(" : ")
                            .append(tableModel.getValueAt(i, 1))
                            .append("\n");
                }
                Storage.save(sb.toString(), master);

                // Un piccolo popup più pulito
                JOptionPane.showMessageDialog(this, "Vault aggiornato con successo!", "Salvato", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nel salvataggio", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(mainPanel);
        setVisible(true);
    }
}