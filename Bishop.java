import javax.swing.text.Position;

public class Bishop extends Piece {


    public Bishop(Position position, PieceColor pieceColor) {
        super(position, pieceColor);
    }

    @Override
    public boolean isMoveValid(Position newPosition, Piece[][] board) {
        int rowDifference = Math.abs(position.getRow() - newPosition.getRow());
        int colorDifference = Math.abs(position.getColumn() - newPosition.getColumn());

        if (rowDifference != colorDifference) {
            return false; // Move is not diagonal so that's illegal
        }

        int rowStep = newPosition.getRow() > position.getRow() ? 1 : -1;
        int colStep = newPosition.getColumn() > position.getColumn() ? 1 : -1;
        int steps = rowDifference - 1; // Number of squares to check for obstruction

        // Check for obstructions along the path
        for (int i = 1; i <= steps; i++) {
            if (board[position.getRow() + i * rowStep][position.getColumn() + i * colStep] != null) {
                return false; // There is a piece in the way
            }
        }

        /* Let's check for the destination square (if it contains opponent's piece, or it's empty */
        Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
        if (destinationPiece == null) {
            return true; // Destination is empty so that move is legal
        } else if (destinationPiece.getPieceColor() != this.getPieceColor()) {
            return true; // Destination contains opponent's piece, so we can capture it legally
        }
        return false; // Destination contains the same color piece, so move is illegal
    }
}
