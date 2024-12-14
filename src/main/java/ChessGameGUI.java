import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChessGameGUI extends Application {

    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final ChessGame game = new ChessGame();

    @Override
    public void start(Stage primaryStage) {
        GridPane boardGrid = new GridPane();
        initializeBoard(boardGrid);

        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(e -> resetGame());

        BorderPane layout = new BorderPane();
        layout.setCenter(boardGrid);
        layout.setBottom(resetButton);

        Scene scene = new Scene(layout);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> adjustBoard(boardGrid, scene));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> adjustBoard(boardGrid, scene));

        adjustBoard(boardGrid, scene);
        primaryStage.setTitle("Chess Game");
        primaryStage.show();
    }

    private void adjustBoard(GridPane boardGrid, Scene scene) {
        double size = Math.min(scene.getWidth(), scene.getHeight());
        double squareSize = size / 8;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessSquareComponent square = squares[row][col];
                if (square != null) {
                    square.resizeSquare(squareSize);
                }
            }
        }
    }

    private void initializeBoard(GridPane grid) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessSquareComponent square = new ChessSquareComponent(row, col, 60);
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
        if (piece instanceof Pawn) return "\u2659";
        if (piece instanceof Rook) return "\u2656";
        if (piece instanceof Knight) return "\u2658";
        if (piece instanceof Bishop) return "\u2657";
        if (piece instanceof Queen) return "\u2655";
        if (piece instanceof King) return "\u2654";
        return "";
    }

    private void handleSquareClick(int row, int col) {
        ChessSquareComponent highlightedSquare = squares[row][col];
        highlightedSquare.highlightPiece();

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
