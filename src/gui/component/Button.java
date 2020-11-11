package gui.component;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

import static gui.Borders.normalBorder;

public class Button extends JButton {
    Button() {
        this(null, null);
    }

    public Button(Icon icon) {
        this(null, icon);
    }

    public Button(String text) {
        this(text, null);
    }

    public Button(String text, Icon icon) {
        super(text, icon);
        this.setFocusPainted(false);
        //this.setBorder(normalBorder);
        this.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 3), new EmptyBorder(5, 5, 5, 5)));
    }
}
