package game.server;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ServerFrame extends JFrame {

	public static JTextArea ta = new JTextArea();
	private JPanel contentPane;

	public ServerFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane js = new JScrollPane();

		ta.setColumns(20);
		ta.setRows(5);
		js.setBounds(0, 0, 400, 400);
		contentPane.add(js);
		js.setViewportView(ta);

		setVisible(true);

		new Server();
	}

	public static JTextArea getTa() {
		return ta;
	}
}
