package hu.ait.tictactoe.model;

public class TicTacToeModel {

    private static TicTacToeModel instance = null;

    public static TicTacToeModel getInstance() {
        if (instance == null) {
            instance = new TicTacToeModel();
        }

        return instance;
    }

    private TicTacToeModel() {
    }

    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    private short [][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    private short nextPlayer = CIRCLE;

    public void resetGame() {
        nextPlayer = CIRCLE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }
        }
    }

    public short getFieldContent(int x, int y) {
        return model[x][y];
    }

    public void setFieldContent(int x, int y, short player) {
        model[x][y] = player;
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    public void switchPlayers() {
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;

        /*if (nextPlayer==CIRCLE) {
            nextPlayer = CROSS;
        } else {
            nextPlayer = CIRCLE;
        }*/
    }
}
