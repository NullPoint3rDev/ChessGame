import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessSquareComponent extends StackPane {
    private final int row;
    private final int col;
    private final Rectangle background;
    private final Label pieceLabel;
    private Color originalColor;

    public ChessSquareComponent(int row, int col, double size) {
        this.row = row;
        this.col = col;

        background = new Rectangle(size, size);
        if ((row + col) % 2 == 0) {
            background.setFill(Color.web("#77DD77"));
        } else {
            background.setFill(Color.web("#CFC38C"));
        }
        getChildren().add(background);

        pieceLabel = new Label();
        pieceLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        getChildren().add(pieceLabel);
    }

    public void setPieceSymbol(String symbol, Color color) {
        pieceLabel.setText(symbol);
        pieceLabel.setTextFill(color);
        originalColor = color;
    }

    public void clearPieceSymbol() {
        pieceLabel.setText("");
    }

    public void resizeSquare(double size) {
        background.setWidth(size);
        background.setHeight(size);
    }

    public void highlightPiece() {
        originalColor = (Color) pieceLabel.getTextFill();
        pieceLabel.setTextFill(Color.ROYALBLUE);
    }

    public void removeHighlightPiece() {
        pieceLabel.setTextFill(originalColor);
    }
}
