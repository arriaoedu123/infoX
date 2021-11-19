package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JDesktopPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JCheckBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Usuarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPesquisar;
	private JTable table;
	private JTextField txtId;
	private JTextField txtUsuario;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JComboBox cboPerfil;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// evento ao iniciar o JDialog
				chkSenha.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users.png")));
		setTitle("Usu\u00E1rios");
		setBounds(100, 100, 677, 405);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// evento disparado ao soltar a tecla
				pesquisarUsuario();
			}
		});
		txtPesquisar.setBounds(10, 11, 147, 20);
		contentPanel.add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(10, 42, 641, 81);
		contentPanel.add(desktopPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 641, 81);
		desktopPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// evento disparado ao clicar na linha da tabela
				setarCampos();
				setarSenha();
			}
		});
		scrollPane.setViewportView(table);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(53, 134, 86, 20);
		contentPanel.add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(10, 137, 46, 14);
		contentPanel.add(lblNewLabel);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(205, 134, 243, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Usu\u00E1rio");
		lblNewLabel_1.setBounds(149, 137, 46, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(10, 179, 46, 14);
		contentPanel.add(lblNewLabel_2);

		txtLogin = new JTextField();
		txtLogin.setBounds(53, 176, 169, 20);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setBounds(242, 179, 46, 14);
		contentPanel.add(lblNewLabel_3);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(298, 176, 353, 20);
		contentPanel.add(txtSenha);

		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setBounds(10, 222, 46, 14);
		contentPanel.add(lblNewLabel_4);

		cboPerfil = new JComboBox();
		cboPerfil.setEditable(true);
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "administrador", "operador" }));
		cboPerfil.setBounds(53, 218, 122, 22);
		contentPanel.add(cboPerfil);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// evento disparado ao clicar no botão de adicionar
				adicionarUsuario();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(10, 275, 80, 80);
		contentPanel.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// evento disparado ao clicar no botão de editar
				if (chkSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioSemSenha();
				}
				
			}
		});
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update.png")));
		btnEditar.setBounds(100, 275, 80, 80);
		contentPanel.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// evento disparado ao clicar no botão de excluir
				excluirUsuario();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete.png")));
		btnExcluir.setBounds(190, 275, 80, 80);
		contentPanel.add(btnExcluir);

		// uso da biblioteca atxy2k para validações
		RestrictedTextField nome = new RestrictedTextField(this.txtUsuario);
		nome.setLimit(50);
		RestrictedTextField email = new RestrictedTextField(this.txtLogin);
		email.setLimit(50);
		RestrictedTextField senha = new RestrictedTextField(this.txtSenha);
		senha.setLimit(250);
		RestrictedTextField login = new RestrictedTextField(this.txtSenha);
		
		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Usuarios.class.getResource("/img/pesquisar.png")));
		lblNewLabel_5.setBounds(163, 5, 32, 32);
		contentPanel.add(lblNewLabel_5);
		
		chkSenha = new JCheckBox("Confirmar altera\u00E7\u00E3o de senha");
		chkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkSenha.isSelected()) {
					txtSenha.setEnabled(true);
				} else {
					txtSenha.setEnabled(false);
				}
			}
		});
		chkSenha.setBounds(294, 203, 199, 23);
		contentPanel.add(chkSenha);
		login.setLimit(50);
		
	} // fim do construtor

	// criando um objeto para acessar a classe DAO
	DAO dao = new DAO();
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JLabel lblNewLabel_5;
	private String testeSenha;
	private JCheckBox chkSenha;

	/**
	 * Método responsável pela pesquisa do usuário com uso da biblioteca rs2xml
	 */
	private void pesquisarUsuario() {
		// ? -> parâmetro
		String read = "select id as ID, usuario as Usuário, login as Login, perfil as Perfil from usuarios where usuario like ?";
		try {
			// abrir a conexao com o banco
			Connection con = dao.conectar();
			// preparar a query(instrucao sql) para pesquisar no banco
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parametro(?) Atencao ao % para completar a query
			// 1 -> parâmetro
			pst.setString(1, txtPesquisar.getText() + "%");
			// executar a query e obter os dados do banco (resultado)
			ResultSet rs = pst.executeQuery();
			// popular(preencher) a tabela com os dados do banco
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Método responsável por setar os campos da tabelo no formulário
	 */
	private void setarCampos() {
		// a linha abaixo obtem o conteúdo da linha da tabela
		// int (índice) [0], [1], [2],...
		int setar = table.getSelectedRow();
		// setar os campos
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
		txtUsuario.setText(table.getModel().getValueAt(setar, 1).toString());
		txtLogin.setText(table.getModel().getValueAt(setar, 2).toString());
		cboPerfil.setSelectedItem(table.getModel().getValueAt(setar, 3).toString());
		// gerenciar os botões
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		chkSenha.setVisible(true);
		txtSenha.setEnabled(false);
	}
	
	/**
	 * Método específico para setar a senha
	 */
	public void setarSenha() {
		String read2 = "select senha from usuarios where id = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, txtId.getText());
			// a linha abaixo executa a instrução sql e armazena o resultado no objeto rs
			ResultSet rs = pst.executeQuery();
			// a linha abaixo verifica se existe uma senha para o id
			if (rs.next()) {
				txtSenha.setText(rs.getString(1));
			}
			String testeSenha = rs.getString(1);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Método responsável por adicionar um usuário no banco de dados
	 */
	private void adicionarUsuario() {
		// validação de campos obrigatórios
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o usuário", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtLogin.requestFocus();
		/*se if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtSenha.requestFocus();*/
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			cboPerfil.requestFocus();
		} else {
			// inserir o cliente no banco
			String create = "insert into usuarios (usuario,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}
				con.close();
				// o catch abaixo se refere ao valor duplicado de email(UNIQUE)
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login já existente\nCadastre outro Login", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao adicionar o usuário", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método responsável por editar um usuário no banco de dados
	 */
	private void editarUsuario() {
		// validação de campos obrigatórios
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o usuário", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			cboPerfil.requestFocus();
		} else {
			// inserir o cliente no banco
			String update = "update usuarios set usuario = ?, login = ?, senha = md5(?), perfil = ? where id = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtId.getText());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}
				con.close();
				// o catch abaixo se refere ao valor duplicado de email(UNIQUE)
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login já existente\nCadastre outro Login", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao editar os dados do usuário", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método responsável por editar um usuário no banco de dados
	 */
	private void editarUsuarioSemSenha() {
		// validação de campos obrigatórios
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o usuário", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			cboPerfil.requestFocus();
		} else {
			// inserir o cliente no banco
			String update = "update usuarios set usuario = ?, login = ?, perfil = ? where id = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtId.getText());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}
				con.close();
				// o catch abaixo se refere ao valor duplicado de email(UNIQUE)
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login já existente\nCadastre outro Login", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao editar os dados do usuário", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método responsável por excluir o usuário do banco de dados
	 */
	private void excluirUsuario() {
		// confimação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Deseja excluir este usuário?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where id = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				int excluir = pst.executeUpdate();
				if (excluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao excluir o usuário", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método responsável por limpar os campos e gereniar os botões
	 */
	private void limpar() {
		txtId.setText(null);
		txtUsuario.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		cboPerfil.setSelectedItem(null);
		// limpar a tabela
		((DefaultTableModel) table.getModel()).setRowCount(0);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		chkSenha.setVisible(false);
	}
}
