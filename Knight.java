import javax.swing.text.Position;

public class Knight extends Piece {


    public Knight(Position position, PieceColor pieceColor) {
        super(position, pieceColor);
    }

    @Override
    public boolean isMoveValid(Position newPosition, Piece[][] board) {
        if (newPosition.equals(this.position)) {
            return false; /* Checking if we at the same position. Notice, that we cannot
            move to the same position */
        }

        int rowDifference = Math.abs(this.position.getRow() - newPosition.getRow());
        int colorDifference = Math.abs(this.position.getColumn() - newPosition.getColumn());

        // Now let's check if we can use 'L' ('Г') move pattern
        boolean isMoveLegal = (rowDifference == 2 && colorDifference == 1)
                || (rowDifference == 1 && colorDifference == 2);
        if (!isMoveLegal) {
            return false; // Illegal knight's move
        }

        // Move is legal if the destination square is empty or contains opponent's piece
        Piece targetPiece = board[newPosition.getRow()][newPosition.getColumn()];
        if (targetPiece == null) {
            return true; // Square is empty so the move is legal
        } else {
            return targetPiece.getPieceColor() != this.getPieceColor(); /* Can capture if this
            is opponent's piece */
        }
    }
}
