package sudoku;

import javax.swing.*;
/**
 * Aplikacja do gry SUDOKU
 * @author Kinga Spytkowska, Patrycja Oświęcimska
 */

public class Main {

    /**
     * Metoda wyświetlająca okno gry
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new sudokuGUI().setVisible(true)
        );

    }

}