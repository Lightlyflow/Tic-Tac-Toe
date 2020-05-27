import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    private TicTacToeButton[][] arr;
    private int[][] boardState;
    private String winner;
    private int turn;
    private GridBagConstraints c;

    public TicTacToe() {
        super("Tic Tac Toe");
        init();
    }
    void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.getContentPane().setLayout(new GridBagLayout());

        turn = 0;
        arr = new TicTacToeButton[3][3];
        boardState = new int[3][3];
        winner = "";
        c = new GridBagConstraints();

        // Create main board buttons
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                arr[row][col] = new TicTacToeButton("_", row, col);
                this.c.gridx = col;
                this.c.gridy = row;
                this.c.fill = GridBagConstraints.BOTH;
                this.c.weightx = 1.0;
                this.c.weighty = 1.0;

                arr[row][col].addActionListener(e -> {
                    try {
                        TicTacToeButton component = (TicTacToeButton) e.getSource();
                        component.setEnabled(false);
                        if (turn++ % 2 == 0) {
                            component.setBackground(Color.green);
                            boardState[component.getRow()][component.getCol()] = -1;
                            component.setText("X");
                        } else {
                            component.setBackground(Color.red);
                            boardState[component.getRow()][component.getCol()] = 1;
                            component.setText("O");
                        }
                        if (checkWin()) {
                            JOptionPane.showMessageDialog(this, String.format("%s won!", winner));
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

                this.getContentPane().add(arr[row][col], c);
            }
        }

        addResetButton();

        this.setVisible(true);
    }
    void addResetButton () {
        this.c.gridx = 0;
        this.c.gridy = 4;
        this.c.gridwidth = 3;
        this.c.fill = GridBagConstraints.BOTH;
        this.c.weightx = 1.0;
        this.c.weighty = 0.1;
        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> {
            turn = 0;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    arr[r][c].setEnabled(true);
                    arr[r][c].setBackground(Color.WHITE);
                    arr[r][c].setText(" ");
                    boardState[r][c] = 0;
                }
            }
        });
        this.add(resetButton, c);
    }
    boolean checkWin() {
        // Check horizontal wins
        for (int[] row : boardState) {
            if(row[0]==0) {
                continue;
            }
            if (row[0] + row[1] + row[2] == 3) {
                winner = "Player 2";
                return true;
            } else if (row[0] + row[1] + row[2] == -3) {
                winner = "Player 1";
                return true;
            }
        }
        // Check vertical wins
        for (int r = 0; r < 3; r++) {
            if (boardState[0][r] + boardState[1][r] +boardState[2][r] == 3) {
                winner = "Player 2";
                return true;
            } else if (boardState[0][r] + boardState[1][r] +boardState[2][r] == -3) {
                winner = "Player 1";
                return true;
            }
        }
        // Check diagonals
        if (boardState[0][2] + boardState[1][1] + boardState[2][0] == 3) {
            winner = "Player 2";
            return true;
        } else if (boardState[0][2] + boardState[1][1] + boardState[2][0] == -3) {
            winner = "Player 1";
            return true;
        } else if (boardState[0][0] + boardState[1][1] + boardState[2][2] == 3) {
            winner = "Player 2";
            return true;
        } else if (boardState[0][0] + boardState[1][1] + boardState[2][2] == -3) {
            winner = "Player 1";
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}

class TicTacToeButton extends JButton {
    private final int ROW;
    private final int COL;

    public TicTacToeButton(String name, int row, int col) {
        super(name);
        ROW = row;
        COL = col;
    }
    public int getRow() {   return ROW; }
    public int getCol() {   return COL; }
}