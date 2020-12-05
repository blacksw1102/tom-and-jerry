package client.gui.component;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Borders {
    public static CompoundBorder backgroundBorder = new CompoundBorder(new LineBorder(Color.BLACK, 5), new EmptyBorder(40, 300, 40, 300));
    public static LineBorder normalBorder = new LineBorder(Color.BLACK, 3);
    public static LineBorder debugBorder = new LineBorder(Color.RED, 3);
    public static MatteBorder listRowBorder = new MatteBorder(0, 0, 3, 0,Color.BLACK);
    public static EmptyBorder listRowPadding = new EmptyBorder(3, 7, 3, 7);
}
