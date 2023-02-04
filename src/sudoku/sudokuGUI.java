package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Klasa odpowiadająca za wygląd okna programu
 * @author Kinga Spytkowska, Patrycja Oświęcimska
 */

public class sudokuGUI extends JFrame implements ActionListener{

    /** Prywatne pole typu JRadioButton
     * do wyboru łatwego poziomu gry.
     */
    private JRadioButton easyLevel;
    /** Prywatne pole typu JRadioButton
     * do wyboru średniego poziomu gry.
     */
    private JRadioButton mediumLevel;
    /** Prywatne pole typu JRadioButton
     * do wyboru trudnego poziomu gry.
     */
    private JRadioButton hardLevel;
    /** Prywatne pole typu JButton
     * odpowiadające za poddanie się gracza.
     */
    private JButton giveUpButton;
    /** Prywatne pole typu JTextField
     * określające ilość błędów popełnionych przez gracza.
     */

    private JTextField lifesNumber;

    /** Prywatne pola typu JPanel
     * określające planszę 9x9.
     */
    private JPanel board;
    private JPanel first;
    private JPanel second;
    private JPanel third;
    private JPanel fourth;
    private JPanel fifth;
    private JPanel sixth;
    private JPanel seventh;
    private JPanel eighth;
    private JPanel ninth;
    private JPanel mainPanel;
    private JLabel level;

    /** Prywatne pole typu JButton
     * odpowiadające za rozpoczęcie gry.
     */
    private JButton playButton;

    /** Prywatne pole typu JTextField
     * odpowiadające za wyświetlenie czasu gry.
     */
    private JTextField time;

    /** Prywatne pole typu float
     * określające liczbę punktów.
     */
    private float punctation = 0;
    /** Chronione pole typu int
     * przekazujące informację jaki level został wybrany przez uzytkownika.
     */
    protected int choose;


    /** Prywatne pole typu int
     * określające czas gry w sekundach.
     */
    private int t;
    /**
     * Utworzenie nowego obiektu klasy Timer o nazwie "timer".
     * Wartość 1000 oznacza interwał czasowy w milisekundach,
     * co oznacza, że Timer będzie wykonywał swoje zadanie co 1000 milisekund (czyli co sekundę).
     */
    Timer timer = new Timer(1000, this);

    /**
     * Utowrzenie nowej instancji klasy „SudokuBoard” i przypisanie jej do zmiennej „sudokuBoard”.
     */
    SudokuBoard sudokuBoard = new SudokuBoard(this);

    /**
     * Metoda ustawiająca tekst w polu lifesNumber na podaną liczbę błędów,
     * ustawia tekst jako nieedytowalny i wyrównanie do środka.
     * @param mistacke liczba błędów
     */
    public void mistackes(int mistacke){
        lifesNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lifesNumber.setText(String.valueOf(mistacke));
        lifesNumber.setEditable(false);

    }

    /**
     * Metoda obliczająca punktację na podstawie liczby błędów i czasu gry.
     * @param mistakes liczba błędów
     * @return punktacja
     */

    public float punctation(int mistakes){
        return punctation =(10000 - (50 * mistakes)) / t;
    }


    /**
     * Rozpoczyna liczenie czasu i ustawia pole time na początku na 0.
     */
    public void timeFieldStart(){
        t = 0;
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setText(formatTime(t));
        timer.start();
    }

    /**
     *Zatrzymuje liczenie czasu i zwraca jego aktualną wartość.
     *@return aktualny czas
     */

    public String timeFieldStop(){
        timer.stop();
        return formatTime(t);
    }

    /**
     *Metoda wywoływana przy każdym ticku timera. Aktualizuje licznik czasu i ustawia go na etykiecie.
     * @param e zdarzenie wywołane przez timer
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        t++;
        time.setText(formatTime(Integer.valueOf(t)));
        time.setEditable(false);

    }

    /**
     *Formatuje liczbę sekund na format czasu "godziny:minuty:sekundy".
     * @param time liczba sekund
     * @return string z czasem w formacie "godziny:minuty:sekundy"
     */

    private String formatTime(int time) {
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     *Tworzy okno dialogowe z pytaniem czy na pewno chcemy zakończyć grę.
     * Jeśli użytkownik potwierdzi wybór, gra zostanie zakończona.
     */

    public void giveUpButton(){

        String message = "Czy na pewno chcesz zakończyć grę?";
        String title = "Wyjście";
        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Tworzy okno dialogowe z prośbą o wybranie poziomu trudności.
     */

    public void chooseLevel(){
        JOptionPane.showMessageDialog(this,
                "Wybierz poziom trudności");

    }

    /**
     * Tworzy słuchacza zdarzeń dla przycisków wyboru poziomu trudności.
     * Po najechaniu kursorem na przycisk, pozostałe przyciski są odznaczane.
     * @param radioButtons lista przycisków, które mają być odznaczane
     * @return słuchacz zdarzeń dla przycisków
     */

    public FocusAdapter getRadioButtonFocusListener(List<JRadioButton> radioButtons){
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                for(JRadioButton radioButton: radioButtons){
                    radioButton.setSelected(false);
                }
            }
        };
    }

    /**
     * Ustawia słuchacza zdarzeń dla przycisków wyboru poziomu trudności
     */

    public void setFocusOnChooseLevelButtons() {
        easyLevel.addFocusListener(getRadioButtonFocusListener(Arrays.asList(mediumLevel, hardLevel)));
        mediumLevel.addFocusListener(getRadioButtonFocusListener(Arrays.asList(easyLevel, hardLevel)));
        hardLevel.addFocusListener(getRadioButtonFocusListener(Arrays.asList(easyLevel, mediumLevel)));
    }

    /**
     * Tworzy okno dialogowe z informacją o liczbie błędów, po której gra zostanie zakończona.
     * @param x liczba błędów po której gra zostanie zakończona
     */

    public void information(int x){
        String message = "W chwili gdy popełnisz " + x + " błędów gra zakończy się.";
        String title = "Pamiętaj!";
        JOptionPane.showMessageDialog(null,message, title, JOptionPane.OK_OPTION);
    }

    /**
     * Ustawia słuchacza dla przycisku "play" i
     * ustawia poziom trudności oraz liczbę błędów po której gra kończy się na podstawie wybranego przycisku.
     * Rozpoczyna liczenie czasu.
     */

    public void handlePlayButton(){
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(easyLevel.isSelected()){
                    sudokuBoard.prepareBoard(LevelProvider.getEasy());
                    information(10);
                    choose = 1;
                } else if (mediumLevel.isSelected()) {
                    sudokuBoard.prepareBoard(LevelProvider.getMedium());
                    information(6);
                    choose = 2;
                }else if (hardLevel.isSelected()){
                    sudokuBoard.prepareBoard(LevelProvider.getHard());
                    information(3);
                    choose = 3;
                }else{
                    chooseLevel();
                }

                timeFieldStart();
            }
        });
    }

    /**
     * Ustawia słuchacza dla przycisku "give up" i wywołuje metodę zakończenia gry.
     */

    public void handleGiveUpButton(){
        giveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                giveUpButton();
            }
        });
    }

    /**
     * Tworzy nowe okno GUI aplikacji sudoku.
     * Ustawia parametry okna, ustawia słuchaczy dla przycisków "play" i "give up",
     * ustawia słuchacza dla przycisków wyboru poziomu trudności,
     * przygotowuje planszę sudoku oraz ustawia odpowiednie parametry dla pól tekstowych.
     * @throws HeadlessException
     */

    public sudokuGUI() throws HeadlessException{
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);

        this.setFocusOnChooseLevelButtons();
        sudokuBoard.prepareTextField(Arrays.asList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth));
        handlePlayButton();
        handleGiveUpButton();



    }

}


