import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessSquareComponent extends StackPane {
    private final int row;
    private final int col;
    private final Rectangle background;
    private final Label pieceLabel;

    public ChessSquareComponent(int row, int col) {
        this.row = row;
        this.col = col;

        // Инициализация цвета клетки
        background = new Rectangle(60, 60); // Размер клетки
        if ((row + col) % 2 == 0) {
            background.setFill(Color.web("#77DD77")); // Пастельно-зеленый
        } else {
            background.setFill(Color.web("#CFC38C")); // Темно-бежевый
        }
        getChildren().add(background);

        // Инициализация текста фигуры
        pieceLabel = new Label();
        pieceLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        getChildren().add(pieceLabel);
    }

    public void setPieceSymbol(String symbol, Color color) {
        pieceLabel.setText(symbol);
        pieceLabel.setTextFill(color); // Установка цвета текста
    }

    public void clearPieceSymbol() {
        pieceLabel.setText("");
    }

    public void highlightPiece() {
        pieceLabel.setTextFill(Color.ROYALBLUE); // Changing piece's color
    }

    public void removeHighlightPiece() {
        pieceLabel.setTextFill(Color.BLACK); // Back piece's color
    }
}
