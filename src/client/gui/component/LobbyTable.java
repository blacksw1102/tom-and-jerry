package client.gui.component;

import javax.swing.JTable;

public class LobbyTable extends JTable {

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
