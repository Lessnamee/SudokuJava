package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa służąca do przygotowywania list z polami tekstowymi JTextField zawartych w kontenerach JPanel.
 * @author Kinga Spytkowska, Patrycja Oświęcimska
 */

class TextFieldPreparator {

    /**
     * Metoda pobierająca listę kontenerów JPanel i zwracająca listę list zawierających pola tekstowe JTextField
     * znajdujące się w tych kontenerach.
     * @param jPanels lista kontenerów JPanel, z których mają zostać pobrane pola tekstowe
     * @return lista list zawierających pola tekstowe JTextField znajdujące się w kontenerach JPanel
     */

    static List<List<JTextField>> getJTextFieldLists(List<JPanel> jPanels) {
        List<List<JTextField>> jTextFieldLists = new ArrayList<>();

        for(JPanel jPanel: jPanels) {
            var submatrix = TextFieldPreparator.getJTextFieldFromJPanel(jPanel);
            jTextFieldLists.add(submatrix);
        }

        return jTextFieldLists;
    }

    /**
     * Metoda pobierająca kontener JPanel i zwracająca listę zawierającą pola tekstowe JTextField znajdujące się w tym kontenerze.
     * @param jPanel kontener JPanel, z którego ma zostać pobrana lista zawierająca pola tekstowe
     * @return lista zawierająca pola tekstowe JTextField znajdujące się w kontenerze JPanel
     */

    private static List<JTextField> getJTextFieldFromJPanel(JPanel jPanel) {
        List<JTextField> textFields = new ArrayList<>();
        Component[] components = jPanel.getComponents();

        for(Component component: components) {
            if (component instanceof JTextField) {
                textFields.add((JTextField) component);
            }
        }

        return textFields;
    }

}
