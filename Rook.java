import javax.swing.text.Position;

public class Rook extends Piece {


    public Rook(Position position, PieceColor pieceColor) {
        super(position, pieceColor);
    }

    @Override
    public boolean isMoveValid(Position newPosition, Piece[][] board) {
        /* Rooks can move vertically or horizontally for any number of squares
           They can't jump over pieces */

        if (position.getRow() == newPosition.getRow()) {
            int columnStart = Math.min(position.getColumn(), newPosition.getColumn()) + 1;
            int columnEnd = Math.max(position.getColumn(), newPosition.getColumn());
            for (int column = columnStart; column < columnEnd; column++) {
                if (board[position.getRow()][column] != null) {
                    return false; // If there is a piece in the way
                }
            }
        } else if (position.getColumn() == newPosition.getColumn()) {
            int rowStart = Math.min(position.getRow(), newPosition.getRow()) + 1;
            int rowEnd = Math.max(position.getRow(), newPosition.getRow());
            for (int row = rowStart; row < rowEnd; row++) {
                if (board[row][position.getColumn()] != null) {
                    return false; // If there is a piece in the way
                }
            }
        } else {
            return false; // Illegal rook's move (illegal direction)
        }

        // Capturing opponent's piece
        Piece destinationPiece = board[newPosition.getRow][newPosition.getColumn];
        if (destinationPiece == null) {
            return true; // Destination is empty, rook can be moved
        } else if (destinationPiece.getPieceColor() != this.getPieceColor()) {
            return true; // Destination has opponent's piece, we can beat his piece
        }

        return false; // Destination has the same piece color. Move is illegal
    }
}
