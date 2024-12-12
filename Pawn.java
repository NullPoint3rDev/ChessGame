import javax.swing.text.Position;

public class Pawn extends Piece {

    public Pawn(Position position, PieceColor pieceColor) {
        super(position, pieceColor);
    }

    @Override
    public boolean isMoveValid(Position newPosition, Piece[][] board) {
        int forwardMovingDirection = pieceColor == PieceColor.WHITE ? -1 : 1;
        int rowDifference = (newPosition.getRow() - position.getRow()) * forwardMovingDirection;
        int colorDifference = newPosition.getColumn() - position.getColumn();

        // Now move forward
        if (colorDifference == 0 && rowDifference == 1 &&
                board[newPosition.getRow()][newPosition.getColumn()] == null) {
            return true;
        }
        // Move forward one square


        // Initial two-square move
        boolean isStartingPosition = (pieceColor == PieceColor.WHITE && position.getRow() == 6)
                || (pieceColor == PieceColor.BLACK && position.getRow() == 1);
        if (colorDifference == 0 && rowDifference == 2 && isStartingPosition
                && board[newPosition.getRow()][newPosition.getColumn] == null) {
            // Check if we have blocking pieces
            int middleRow = position.getRow() + forwardMovingDirection;
            if (board[middleRow][position.getColumn] == null) {
                return true; // Move forward two squares
            }
        }

        // Diagonal capture
        if (Math.abs(colorDifference) == 1 && rowDifference == 1 &&
                board[newPosition.getRow()][newPosition.getColumn()] != null &&
                board[newPosition.getRow()][newPosition.getColumn].pieceColor != this.pieceColor) {
            return true; // Capture opponent's piece
        }
        return false;
    }
}