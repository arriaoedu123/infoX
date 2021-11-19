package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("infoX - Login");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/pc.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(84, 49, 46, 14);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(140, 46, 200, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(84, 98, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(140, 95, 200, 20);
		contentPane.add(txtSenha);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnEntrar.setBounds(84, 169, 89, 32);
		contentPane.add(btnEntrar);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dberror.png")));
		lblStatus.setBounds(308, 169, 32, 32);
		contentPane.add(lblStatus);
	} // fim do construtor

	DAO dao = new DAO();

	/**
	 * Método responsável pela exibição do status de conexão
	 */
	private void status() {
		// criar um objeto de nome DAO para acessar o método de conexão na classe DAO
		DAO dao = new DAO();
		try {

			// abrir a conexão com o banco de dados
			Connection con = dao.conectar();

			// a linha abaixo exibe o retorno da conexão
			System.out.println("Conectado com sucesso");

			// mudando o ícone do rodapé no caso do banco e dados estar disponível
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbok.png")));
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dberror.png")));
			}

			/// IMPORTANTE! sempre encerrar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método responsável pela autenticação do usuário
	 */
	private void logar() {
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtSenha.requestFocus();
		} else {
			try {
				String read = "select * from usuarios where login = ? and senha = md5(?)";
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, txtSenha.getText());
				// a linha abaixo executa a query(instrução sql) armazenando o resultado no
				// objeto rs
				ResultSet rs = pst.executeQuery();
				// se existir login e senha correspondente
				if (rs.next()) {
					// capturar perfil do usuário
					String perfil = rs.getString(5);
					// tratamento de perfil de usuário
					if (perfil.equals("administrador")) {
						// ir para a área do administrador
						Principal principal = new Principal();
						principal.setVisible(true);
						// liberar os botões
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						// após o login, finalizar o JFrame
						this.dispose();
					} else {
						// ir para a área do usuário
						Principal principal = new Principal();
						principal.setVisible(true);
						// após o login, finalizar o JFrame
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Login e/ou senha inválido(s)", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}