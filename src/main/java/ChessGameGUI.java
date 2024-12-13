import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChessGameGUI extends Application {

    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final ChessGame game = new ChessGame();

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        initializeBoard(grid);

        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(e -> resetGame());
        grid.add(resetButton, 0, 8, 8, 1); // Размещение кнопки внизу доски

        Scene scene = new Scene(grid, 500, 550);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeBoard(GridPane grid) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessSquareComponent square = new ChessSquareComponent(row, col);
                int finalRow = row;
                int finalCol = col;
                square.setOnMouseClicked(e -> handleSquareClick(finalRow, finalCol));
                squares[row][col] = square;
                grid.add(square, col, row);
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
                    String symbol = getPieceUnicode(piece);
                    Color color = piece.getColor() == PieceColor.WHITE ? Color.WHITE : Color.BLACK;
                    squares[row][col].setPieceSymbol(symbol, color);
                } else {
                    squares[row][col].clearPieceSymbol();
                }
            }
        }
    }


    private String getPieceUnicode(Piece piece) {
        // Пример получения символа Unicode для фигур
        if (piece instanceof Pawn) return "\u2659";
        if (piece instanceof Rook) return "\u2656";
        if (piece instanceof Knight) return "\u2658";
        if (piece instanceof Bishop) return "\u2657";
        if (piece instanceof Queen) return "\u2655";
        if (piece instanceof King) return "\u2654";
        return "";
    }

    private ChessSquareComponent highlightedSquare = null;

    private void handleSquareClick(int row, int col) {
        if (highlightedSquare != null) {
            highlightedSquare.removeHighlightPiece(); // Removing highlight from the previous piece
        }
        highlightedSquare = squares[row][col];
        highlightedSquare.highlightPiece(); // Highlighting chosen piece

        boolean moveResult = game.handleSquareSelection(row, col);
        if (moveResult) {
            refreshBoard();
            checkGameState();
        }
    }

    private void checkGameState() {
        PieceColor currentPlayer = game.getCurrentPlayerColor();
        boolean inCheck = game.isInCheck(currentPlayer);
        if (inCheck) {
            showAlert(currentPlayer + " is in check!");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game State");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetGame() {
        game.resetGame();
        refreshBoard();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
