import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class Ahorcado extends JFrame {

	private JPanel contentPane;
	private AreaDibujo areaDibujo;
	private PanelTeclado panelTeclado;
	private JPanel panelCanvas;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ahorcado frame = new Ahorcado();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ahorcado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		areaDibujo=new AreaDibujo();
		//PANEL PARA EL TECLADO VIRTUAL
		panelTeclado = new PanelTeclado(areaDibujo);
		contentPane.add(panelTeclado);
		//PANEL PARA EL CANVAS
		panelCanvas = new JPanel();
		panelCanvas.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelCanvas.setLayout(new BorderLayout());
		contentPane.add(panelCanvas);
		panelCanvas.add(areaDibujo);
	}

}

