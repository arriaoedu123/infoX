package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Sobre extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setResizable(false);
		setTitle("Sobre");
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/pc.png")));
		setBounds(100, 100, 378, 260);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sistema de E-Commerce - Ver 1.0");
		lblNewLabel.setBounds(37, 41, 267, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Autor: Arreaum");
		lblNewLabel_1.setBounds(37, 85, 275, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sob a licen\u00E7a MIT");
		lblNewLabel_2.setBounds(37, 126, 222, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit.png")));
		lblNewLabel_3.setBounds(251, 126, 64, 64);
		getContentPane().add(lblNewLabel_3);

	}
}
