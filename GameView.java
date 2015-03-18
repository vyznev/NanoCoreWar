import javax.swing.*;
import java.awt.*;
/**
 * This class represents a single instruction line.
 * 
 * @author TheBestOne, Ilmari Karonen
 * @version 3/18/15
 */
public class GameView extends JComponent{
    final static Color[] specialColors = new Color[]{
            new Color(0,0,0),
            new Color(190, 255, 152),
            Color.yellow,
            new Color(0, 93, 14),
            new Color(96, 92, 4),
            new Color(0, 93, 14),
            new Color(96, 92, 4),
            new Color(0, 93, 14),
            new Color(96, 92, 4)
    };

    final static Color playerOneColor = Color.green;
    final static Color playerTwoColor = Color.white;

    private final Game game;

    public final int[] coreData;
    public final JFrame frame;

    private int playerOneLocation;
    private int playerTwoLocation;
    private int time, turn;

    final static int width = 128;
    final static int height = 64;

    public GameView(Game game) {
        this.game = game;
        this.coreData = new int[game.coreSize];
        this.frame = new JFrame("Game");
        frame.add(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(width*4, height*4);
    }

    @Override
    public void paint(Graphics g) {
        int pixelWidth = getSize().width;
        int pixelHeight = getSize().height;
        if (width > pixelWidth){
            pixelWidth = width;
            setSize(width, pixelHeight);
        }
        if (height > pixelHeight){
            pixelHeight = height;
            setSize(pixelWidth, height);
        }
        int squareWidth = Math.min(pixelWidth / width, pixelHeight / height);
        for (int x = 0; x < squareWidth * width; x += squareWidth){
            for (int y = 0; y < squareWidth * height; y += squareWidth){
                int index = (y / squareWidth) * width + (x / squareWidth);
                Color color = specialColors[coreData[index]];
                if (index == playerOneLocation){
                    color = playerOneColor;
                }
                if (index == playerTwoLocation){
                    color = playerTwoColor;
                }
                g.setColor(color);
                g.fillRect(x, y, squareWidth, squareWidth);
            }
        }
    }

    public void viewCore(Instruction[] core, int ploc, int xloc, int step){
        this.time = (step >> 1);
        this.turn = (step & 1);
        this.playerOneLocation = (turn == 0 ? ploc : xloc);
        this.playerTwoLocation = (turn == 0 ? xloc : ploc);

        repaint();
        try {
            Thread.sleep(1); // 1000 steps per second
        } catch (InterruptedException e) { }
    }
}
