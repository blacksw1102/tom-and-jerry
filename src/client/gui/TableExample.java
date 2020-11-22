package client.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

public class TableExample extends JFrame
{
    public TableExample()
    {
        setLayout(null);
        setMinimumSize(new Dimension(1280, 720));
        setPreferredSize(new Dimension(1280, 720));
    	
        //headers for the table
        String[] columns = new String[] {
            "Id", "Name", "Hourly Rate", "Part Time"
        };
         
        Object[][] data = new Object[][] {
            {1, "John", 40.0, false },
            {2, "Rambo", 70.0, false },
            {3, "Zorro", 60.0, true },
            {3, "Zorro", 60.0, true },
            {3, "Zorro", 60.0, true },
            {3, "Zorro", 60.0, true },
            {3, "Zorro", 60.0, true },
            {3, "Zorro", 60.0, true },
            {3, "Zorro", 60.0, true },
            {3, "Zorro", 60.0, true },
        };
        
        //add the table to the frame
        JTable table = new JTable(data, columns);
        table.getTableHeader().setPreferredSize(new Dimension(800, 40));
        table.setRowHeight(40);
        table.setShowGrid(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(1280/2-800/2, 100, 800, 400);
        this.add(sp);

         
        this.setTitle("Table Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.setVisible(true);
    }
     
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TableExample();
            }
        });
    }   
}