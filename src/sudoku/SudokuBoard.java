package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Klasa odpowiadająca za przygotowanie planszy do gry.
 * @author Kinga Spytkowska, Patrycja Oświęcimska
 */

public class SudokuBoard {

    /** Prywatne pole
     * określające wielkość planszy.
     */
    private final int SIZE = 9;

    /** Prywatne pole
     * określające ilość popełnionych błędów.
     */
    private int mistakes = 0;

    /** Prywatne pole określające
     * dwuwymiarową tablicę obiektów typu textField.
     */
    private JTextField textField[][] = new JTextField[SIZE][SIZE];

    /** Prywatne pole określające
     * dwuwymiarową tablicę obiektów typu int.
     */
    private int[][] fields = new int[SIZE][SIZE];

    /**
     * Obiekt klasy sudokuGUI.
     */
    private sudokuGUI sudokuGUI;


    /** Konstruktor klasy SudokuBoard.
     * Konstruktor ten przyjmuje jeden argument "sudokuGUI sudokuG",
     * który jest przypisywany do pola prywatnego "sudokuGUI" w klasie "SudokuBoard".
     */
    public SudokuBoard(sudokuGUI sudokuG) {
        this.sudokuGUI = sudokuG;
    }

    /** Metoda, która przypisuje pola tekstowe z listy do tablicy "textField".
     * Metoda przegląda każdą podmacierz w tablicy "textField" za pomocą pętli for, a następnie przypisuje
     * odpowiednie pola tekstowe z listy do odpowiednich pozycji w głównej tablicy za pomocą indeksów tempRow i tempColumn.
     * @param lists lista pól tekstowych
     */


    private void mapListToBoard(List<List<JTextField>> lists) {
        for (int submatrix = 0; submatrix < SIZE; submatrix++) {
            int tempRow = ((submatrix / 3) * 3) - 1;
            int initTempColumn = submatrix % 3 * 3;
            int tempColumn = initTempColumn;
            for(int column = 0; column < SIZE; column++) {
                if (column % 3 == 0) {
                    tempRow += 1;
                    tempColumn = initTempColumn;
                }

                var field = lists.get(submatrix).get(column);
                textField[tempRow][tempColumn] = field;
                tempColumn += 1;
            }
        }
    }

    /** Metoda, która przygotowuje pola tekstowe do użycia w grze.
     * Metoda korzysta z metody statycznej "getJTextFieldLists" z klasy "TextFieldPreparator",
     * aby uzyskać listę list pól tekstowych z paneli.
     * Następnie metoda mapListToBoard jest wywoływana z tą listą,
     * aby przypisać pola tekstowe do głównej tablicy w klasie.
     * @param jPanels lista paneli typu JPanel
     */

    public void prepareTextField(List<JPanel> jPanels) {

        var textFields = TextFieldPreparator.getJTextFieldLists(jPanels);

        mapListToBoard(textFields);
    }

    /**
     * Metoda inicjalizująca planszę odpowiednimi wartościami.
     * @param numbers wylosowana zmienna w ramach danego levelu
     */

    private void initBoard(String numbers){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                int n = j + (SIZE*i);
                this.fields[i][j] = Integer.parseInt(String.valueOf(numbers.charAt(n)));
            }
        }

    }

    /**
     * Metoda przygotowująca planszę do gry.
     * Najpierw następuje czyszczenie planszy,
     * kolejno plansza zostaje uzupełniona odpowiednimi wartościami,
     * kolejno zero zostaje zmienione na puste pole,
     * kolejno wartości zostają zablokowane przed zmianą,
     * kolejno na każde pole na planszy zostaje ustawiony nasłuchiwacz na zmiany.
     * @param numbers wylosowana zmienna w ramach danego levelu, która zostaje przekazana do metody initBoard()
     */

    public void prepareBoard(String numbers){
        reset();
        initBoard(numbers);
        setEditTextValues();
        setUnchangeableEditTexts();
        setEditTextsListeners();
    }

    /**
     * Metoda zmieniająca zera na puste pola.
     */

    private void setEditTextValues(){
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(fields[i][j] == 0){
                    textField[i][j].setText("");
                }else{
                    textField[i][j].setText(Integer.toString(fields[i][j]));
                }

            }
        }
    }

    /**
     * Metoda blokująca pola, z wartościami zainicjalizowanymi, przed zmianą.
     */

    private void setUnchangeableEditTexts(){
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(fields[i][j] != 0) {
                    textField[i][j].setEditable(false);
                    textField[i][j].setSelectedTextColor(Color.BLACK);
                    textField[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                }
            }
        }
    }

    /**
     * Metoda informująca o ilości popełnionych błędów.
     * Pyta czy gracz chce zagrać ponownie.
     * Jeśli tak to resetuje planszę,
     * jeśli nie to zamyka program.
     */

    private void message(int y){
        int reply = JOptionPane.showConfirmDialog(null,"Popełniłeś " + y +
                " błędów. Czy chcesz zagrać ponownie?", "Koniec gry", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            reset();
        }else{
            System.exit(0);
        }
    }

    /**
     * Metoda sprawdzająca czy wpisana liczba na planszy jest poprawna.
     * Jeśli jest poprawna to kolor tła będzie zielony.
     * Jeśli jest niepoprawna to kolor tła będzie czerwony,
     * licznik błędów się zwiększy
     * a w przypadku zbyt dużej ilości błędów wyświetli się komunikat.
     */

    private void checkBoard(int i, int j){
        boolean isCorrectNumber = BoardChecker.isCorrectPlace(fields, i, j);
        if(isCorrectNumber){
            textField[i][j].setBackground(Color.green);
        }else{
            textField[i][j].setBackground(Color.red);
            mistakes++;
            sudokuGUI.mistackes(mistakes);
            if(sudokuGUI.choose == 1 && mistakes == 10){
                message(10);
            } else if (sudokuGUI.choose == 2 && mistakes == 6) {
                message(6);
            }else if (sudokuGUI.choose == 3 && mistakes == 3) {
                message(3);
            }

        }
    }

    /**
     * Metoda sprawdzająca czy to już koniec gry.
     * Jeśli tak to wyświetli się informacja o wygranej, o czasie gry, o ilości popełnionych błędów
     * oraz pytanie czy gracz chce zagrać ponownie.
     * Jeśli gracz chce zagrać ponownie to plansza się zresetuje,
     * jeśli gracz nie chce zagrać ponownie to nastąpi zamknięcie programu.
     */


    private void checkGameOver(){
        if(BoardChecker.isGameOver(fields)){
            String message = "Gratulacje, wygrałeś!! Twój czas to: " + sudokuGUI.timeFieldStop() + ".\nPopełniłeś: " +
                    mistakes + " błędów." +
                    "\nZdobyłeś: " + sudokuGUI.punctation(mistakes) + " punktów. \nCzy chcesz zagrać ponownie? ";
            String title = "Koniec gry";
            int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                reset();
            }else{
                System.exit(0);
            }
        }

    }

    /**
     * Metoda usuwająca nasłuchiwacze z pól na planszy.
     * Wykorzystywana przy resetowaniu planszy
     */

    private void removeListenersFromTextField(JTextField jTextField){
        var textListeners = jTextField.getKeyListeners();
        for(int k = 0; k < textListeners.length; k++){
            jTextField.removeKeyListener(textListeners[k]);
        }
    }

    /**
     * Metoda resetująca planszę do gry.
     * Ustawia wszystkie pola puste,
     * ustawia licznik błędów na zero,
     * włącza czas gry od nowa,
     * ustawia wszystkie tła na planszy na białe,
     * ustawia aby można było edytować pola
     * i usuwa nasłuchiwacze z pól.
     */

    private void reset(){
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                textField[i][j].setText("");
                sudokuGUI.mistackes(0);
                mistakes = 0;
                sudokuGUI.timeFieldStart();
                textField[i][j].setBackground(Color.white);
                textField[i][j].setEditable(true);
                removeListenersFromTextField(textField[i][j]);
            }
        }

    }

    /**
     * Metoda przypisująca liczby wpisane przez użytkownika
     * do głównej tablicy.
     */
    private void assignEditTextValueToBoard(int i, int j) {
        try {
            String editTextAsString = textField[i][j].getText();
            fields[i][j] = Integer.parseInt(editTextAsString);
        }catch (Exception e) {
            fields[i][j] = 0;
        }

    }

    /**
     * Metoda ustawiająca nasłuchiwacze
     * na pola na planszy.
     */

    private void setEditTextsListeners(){
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++){
                textField[i][j].addKeyListener(getEditTextOnChangeBehaviour(i,j));
            }
        }
    }
    /**
     * Metoda zapewniająca instancję klasy implementującą zachowaine na zmianę textField[i][j].
     * @param i numer wiersza
     * @param j numer kolumny
     * @return zwraca nową instancję klasy KeyListener1
     */

    private KeyListener getEditTextOnChangeBehaviour(int i, int j){
        return new KeyListener1(i, j);
    }

    /**
     * Klasa implementująca interfejs KeyListener.
     * Służy do określenia zachowania pola tekstowego po wystąpieniu zdarzenia,
     * takiego jak wybranie pola, zwolnienie pola lub wpisanie znaku.
     */

    public class KeyListener1 implements KeyListener {

        /** Prywatne pole
         * określające numer wiersza.
         */

        private final int i;

        /** Prywatne pole
         * określające numer kolumny.
         */
        private final int j;

        /** Konstruktor klasy KeyListener1.
         * Konstruktor ten przyjmuje dwa argumenty int i i j,
         * które są następnie przypisywane do pól prywatnych o tej samej nazwie w klasie "KeyListener1".
         * @param i numer wiersza
         * @param j numer kolumny
         */

        public KeyListener1(int i, int j) {
            this.i = i;
            this.j = j;
        }

        /**
         * Metoda wywoływana w chwili wybrania pola na planszy.
         * Ustawia aby wpisywane liczby było wyśrodkowane.
         */
        public void keyPressed(KeyEvent e) {
            textField[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        }

        /**
         * Metoda wywoływana gdy użytkownik wypełnia pole.
         * Wartość podana przez użytkownika jest przypisywana do głównej tablicy.
         * Sprawdzana jest poprawność wpisanej liczby.
         * Sprawdzane jest to to już koniec gry.
         */

        public void keyReleased (KeyEvent e) {
            assignEditTextValueToBoard(this.i, this.j);
            checkBoard(this.i, this.j);
            checkGameOver();
        }

        /**
         * Metoda sprawdzająca czy wpisana przez użytkownika wartość jest cyfrą.
         * Jeśli nie jest cyfrą to zostaje ona zignorowana.
         */
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            textField[i][j].setText("");
            if(!Character.isDigit(c)){
                e.consume();
            }

        }
    }





}


