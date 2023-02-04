package sudoku;

/**
 * Klasa sprawdzająca poprawność wpisywanych liczb na planszy
 * @author Kinga Spytkowska, Patrycja Oświęcimska
 */

public class BoardChecker {

    /** Prywatne, statyczne pole
     * Określa wielkość planszy
     */
    private static final int SIZE = 9;

    /**
     * Metoda sprawdzająca czy dana liczba powtarza się w danym wierszu
     * @param board aktualna plansza
     * @param number liczba do wpisania
     * @param row numer wiersza
     * @return zwraca informację o tym czy dana liczba znajduje się w danym wierszu
     */

    public static boolean isNumberInRow(int[][] board, int number, int row) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == number) {
                count = count + 1;
            }
        }
        if(count > 1){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdzająca czy dana liczba powtarza się w danej kolumnie
     * @param board aktualna plansza
     * @param number liczba do wpisania
     * @param column numer kolumny
     * @return zwraca informację o tym czy dana liczba powtarza się w danej kolumnie
     */

    public static boolean isNumberInColumn(int[][] board, int number, int column) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (board[i][column] == number) {
                count = count + 1;
            }
        }
        if(count > 1){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdzająca czy dana liczba powtarza się w danym kwadracie
     * @param board aktualna plansza
     * @param number liczba do wpisania
     * @param column numer kolumny
     * @param row numer wiersza
     * @return zwraca informację o tym czy dana liczba powtarza się w danym kwadracie
     */

    public static boolean isNumberInSquare(int[][] board, int number, int column, int row) {
        //chcemy się dostać do początku kwadratu 3x3, w którym znajduje się wpisywana przez nas liczba
        int localRow = row - row % 3;
        int localColumn = column - column % 3;
        int count = 0;


        for (int i = localRow; i < localRow + 3; i++) {
            for(int j = localColumn; j<localColumn + 3; j++){
                if (board[i][j] == number) {
                    count = count + 1;
                }
            }
        }

        if(count > 1){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdzająca czy dana liczba nie powtarza się ani w kolumnie, ani w wierszu, ani w kwadracie
     * @param board aktualna plansza
     * @param column numer kolumny
     * @param row numer wiersza
     * @return zwraca informację o tym czy dane miejsce jest poprawne dla danej liczby
     */

    public static boolean isCorrectPlace(int[][] board, int row, int column){
        return !isNumberInColumn(board,board[row][column],column) &&
                !isNumberInRow(board,board[row][column],row) &&
                !isNumberInSquare(board,board[row][column],column,row);

    }

    /**
     * Metoda sprawdzająca czy to już jest koniec gry, czy gracz wygrał
     * @param board aktualna plansza
     * @return zwraca informację o tym czy to już koniec gry
     */



    public static boolean isGameOver(int[][] board){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j< SIZE; j++){
                if(!isCorrectPlace(board, i, j) || board[i][j] == 0){
                    return false;
                }
            }
        }
        return true;

    }


}


