import javax.swing.text.Position;

public abstract class Piece {
    protected Position position;
    protected PieceColor pieceColor;

    public Piece(Position position, PieceColor pieceColor) {
        this.position = position;
        this.pieceColor = pieceColor;
    }

    public Position getPosition() {
        return position;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    // This setter needed for updating a piece's position
    public void setPosition(Position position) {
        this.position = position;
    }

    // This function will determine if move is legal
    public abstract boolean isMoveValid(Position newPosition, Piece[][] board);

}
