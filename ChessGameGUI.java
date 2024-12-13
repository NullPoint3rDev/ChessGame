import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/*
Still need to fix GUI and optimization
 */

public class ChessGameGUI extends JFrame {
    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final ChessGame game = new ChessGame();

    private final Map<Class<? extends Piece>, String> pieceUnicodeMap = new HashMap<>() {
        {
            put(Pawn.class, "\u265F");
            put(Rook.class, "\u265C");
            put(Knight.class, "\u265E");
            put(Bishop.class, "\u265D");
            put(Queen.class, "\u265B");
            put(King.class, "\u265A");
        }
    };

    public ChessGameGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initializeBoard();
        addGameResetOption();
        pack();
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;
                ChessSquareComponent square = new ChessSquareComponent(row, col);

                // Setting start square color
                Color backgroundColor = (row + col) % 2 == 0 ? new Color(14, 152, 245) :
                        new Color(6, 134, 245);
                square.setBackground(backgroundColor);

                System.out.println("Row: " + row + ",Col: " + col + ",Color: " + backgroundColor);

                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(finalRow, finalCol);
                    }
                });
                add(square);
                squares[row][col] = square;
            }
        }
        refreshBoard();

    }

    private void refreshBoard() {
        ChessBoard board = game.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    // If using Unicode symbols:
                    String symbol = pieceUnicodeMap.get(piece.getClass());
                    Color color = (piece.getColor() == PieceColor.WHITE) ? new Color(22, 220, 220) : new Color(0, 0, 0);
                    squares[row][col].setPieceSymbol(symbol, color);
                } else {
                    squares[row][col].clearPieceSymbol();
                }
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        boolean moveResult = game.handleSquareSelection(row, col);
        clearHighlights();
        if (moveResult) {
            refreshBoard();
            checkGameState();
            checkGameOver();
        } else if (game.isPieceSelected()) {
            highlightLegalMoves(new Position(row, col));
        }
        refreshBoard();
    }

    private void checkGameState() {
        PieceColor currentPlayer = game.getCurrentPlayerColor();
        boolean inCheck = game.isInCheck(currentPlayer);

        if (inCheck) {
            JOptionPane.showMessageDialog(this, currentPlayer + " is in check!");
        }
    }

    private void highlightLegalMoves(Position position) {
        List<Position> legalMoves = game.getLegalMovesForPieceAt(position);
        for (Position move : legalMoves) {
            squares[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
        }
    }

    private void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                // Restoring colors depends on position
                Color backgroundColor = (row + col) % 2 == 0 ? new Color(14, 152, 245) :
                        new Color(6, 134, 245);
                squares[row][col].setBackground(backgroundColor); // Reset color
            }
        }
    }

    private void addGameResetOption() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(e -> resetGame());
        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void resetGame() {
        game.resetGame();
        refreshBoard();
    }

    private void checkGameOver() {
        if (game.isCheckmate(game.getCurrentPlayerColor())) {
            int response = JOptionPane.showConfirmDialog(this, "Checkmate! Would you like to play again?", "Game Over",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGameGUI::new);
    }
}