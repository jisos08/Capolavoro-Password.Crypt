import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class UIStyle {
    public static void applyModernDark() {
        // Colori SFONDO (Più scuro per il frame, leggermente più chiaro per i pannelli)
        Color bgMain = new Color(33, 37, 43); // Grigio scuro moderno
        Color bgPanel = new Color(40, 44, 52); // Grigio pannello
        Color fgMain = new Color(220, 220, 220); // Testo quasi bianco
        Color accentBlue = new Color(59, 130, 246); // Blu accento

        UIManager.put("Panel.background", bgPanel);
        UIManager.put("Frame.background", bgMain);
        UIManager.put("Label.foreground", fgMain);

        // BOTTONI
        UIManager.put("Button.background", bgPanel); // Sfondo bottone normale
        UIManager.put("Button.foreground", fgMain);

        // Bottoni "Azione" (usando il blu come accento)
        UIManager.put("Button.focusedBackground", accentBlue);

        // CAMPI DI TESTO
        UIManager.put("TextField.background", new Color(45, 49, 58));
        UIManager.put("TextField.foreground", fgMain);
        UIManager.put("TextField.caretForeground", Color.WHITE);
        UIManager.put("PasswordField.background", new Color(45, 49, 58));

        // TABELLA (La JList è stata sostituita da JTable nel ManagerFrame)
        UIManager.put("Table.background", bgPanel);
        UIManager.put("Table.foreground", fgMain);
        UIManager.put("Table.selectionBackground", accentBlue);
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.gridColor", new Color(55, 59, 67)); // Griglia sottile
        UIManager.put("TableHeader.background", bgMain);
        UIManager.put("TableHeader.foreground", fgMain);
    }
}