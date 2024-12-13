import javax.swing.*;
import java.awt.*;

/*
Still need to fix GUI and optimization
 */
public class ChessSquareComponent extends JButton {
    private int row;
    private int col;

    public ChessSquareComponent(int row, int col) {
        this.row = row;
        this.col = col;
        initButton();
    }

    private void initButton() {
        setPreferredSize(new Dimension(64, 64));
        setOpaque(true);

        if ((row + col) % 2 == 0) {
            setBackground(new Color(14, 152, 245));
        } else {
            setBackground(new Color(6, 134, 245));
        }

        setForeground(new Color(218, 116, 189));

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font("Serif", Font.BOLD, 36));
    }

    public void setPieceSymbol(String symbol, Color color) {
        this.setText(symbol);
        this.setForeground(color);
    }

    public void clearPieceSymbol() {
        this.setText("");
    }
}