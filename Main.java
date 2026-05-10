import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        // 1. Prepara la cartella 'data'
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.err.println("Errore creazione directory: " + e.getMessage());
        }

        // 2. SETTAGGIO ESTETICO AVANZATO FLATLAF
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());

            // --- Proprietà di Arrotondamento (Rende tutto più "morbido" e moderno) ---
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 12);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("CheckBox.arc", 4); // Meno arrotondato per i checkbox

            // --- Proprietà di Focus e Bordi ---
            UIManager.put("Component.focusWidth", 2); // Bordo di focus sottile e pulito
            UIManager.put("Component.innerFocusWidth", 1);

            // --- Palette Colori di Accento (Blu moderno) ---
            Color accentColor = new Color(59, 130, 246);
            UIManager.put("AccentColor", accentColor);
            UIManager.put("DefaultPalette.selectionBackground", accentColor);

            // --- Personalizzazione ScrollBar (Thumb arrotondato) ---
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

            // Applica anche gli stili custom
            UIStyle.applyModernDark();

        } catch (Exception ex) {
            System.err.println("Errore: Impossibile caricare FlatLaf.");
        }

        // 3. AVVIO (LoginFrame)
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}