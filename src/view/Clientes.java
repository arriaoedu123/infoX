package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Clientes extends JDialog {
	private JTextField txtPesquisar;
	private JTextField txtIdCli;
	private JTextField txtNomeCli;
	private JTextField txtFoneCli;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox cboUf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
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
	public Clientes() {
		setTitle("Clientes");
		setResizable(false);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/pc.png")));
		setBounds(100, 100, 654, 445);
		getContentPane().setLayout(null);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtPesquisar.setBounds(10, 11, 190, 32);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel.setIcon(new ImageIcon(Clientes.class.getResource("/img/pesquisar.png")));
		lblNewLabel.setBounds(210, 11, 32, 32);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("* Campos obrigat\u00F3rios");
		lblNewLabel_1.setBounds(500, 20, 128, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(10, 167, 11, 14);
		getContentPane().add(lblNewLabel_2);

		txtIdCli = new JTextField();
		txtIdCli.setEditable(false);
		txtIdCli.setBounds(30, 164, 86, 20);
		getContentPane().add(txtIdCli);
		txtIdCli.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("* Nome");
		lblNewLabel_3.setBounds(135, 167, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtNomeCli = new JTextField();
		txtNomeCli.setBounds(191, 164, 225, 20);
		getContentPane().add(txtNomeCli);
		txtNomeCli.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("* Fone");
		lblNewLabel_4.setBounds(441, 167, 37, 14);
		getContentPane().add(lblNewLabel_4);

		txtFoneCli = new JTextField();
		txtFoneCli.setBounds(488, 164, 140, 20);
		getContentPane().add(txtFoneCli);
		txtFoneCli.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("CEP");
		lblNewLabel_5.setBounds(10, 211, 28, 14);
		getContentPane().add(lblNewLabel_5);

		txtCep = new JTextField();
		txtCep.setBounds(39, 208, 99, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);

		JButton btnCep = new JButton("Buscar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCep.setBounds(153, 207, 79, 23);
		getContentPane().add(btnCep);

		JLabel lblNewLabel_6 = new JLabel("* Endere\u00E7o");
		lblNewLabel_6.setBounds(251, 211, 65, 14);
		getContentPane().add(lblNewLabel_6);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(323, 208, 305, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("* N\u00FAmero");
		lblNewLabel_7.setBounds(10, 255, 54, 14);
		getContentPane().add(lblNewLabel_7);

		txtNumero = new JTextField();
		txtNumero.setBounds(70, 252, 61, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Complemento");
		lblNewLabel_8.setBounds(160, 255, 82, 14);
		getContentPane().add(lblNewLabel_8);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(250, 253, 107, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("* Bairro");
		lblNewLabel_9.setBounds(10, 300, 46, 14);
		getContentPane().add(lblNewLabel_9);

		txtBairro = new JTextField();
		txtBairro.setBounds(60, 297, 99, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("* Cidade");
		lblNewLabel_10.setBounds(181, 300, 61, 14);
		getContentPane().add(lblNewLabel_10);

		txtCidade = new JTextField();
		txtCidade.setBounds(237, 298, 95, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("* UF");
		lblNewLabel_11.setBounds(361, 300, 28, 14);
		getContentPane().add(lblNewLabel_11);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(399, 298, 54, 22);
		getContentPane().add(cboUf);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(10, 329, 66, 66);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCliente();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnEditar.setBounds(86, 329, 66, 66);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/delete.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		btnExcluir.setBounds(161, 329, 66, 66);
		getContentPane().add(btnExcluir);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(10, 52, 618, 102);
		getContentPane().add(desktopPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 618, 102);
		desktopPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		scrollPane.setViewportView(table);

		// uso da biblioteca atxy2k para validações
		RestrictedTextField nome = new RestrictedTextField(this.txtNomeCli);
		nome.setLimit(50);
		RestrictedTextField fone = new RestrictedTextField(this.txtFoneCli);
		fone.setLimit(15);
		RestrictedTextField cep = new RestrictedTextField(this.txtCep);
		cep.setLimit(8);
		RestrictedTextField endereco = new RestrictedTextField(this.txtEndereco);
		endereco.setLimit(50);
		RestrictedTextField numero = new RestrictedTextField(this.txtNumero);
		numero.setLimit(12);
		RestrictedTextField complemento = new RestrictedTextField(this.txtComplemento);
		complemento.setLimit(30);
		RestrictedTextField bairro = new RestrictedTextField(this.txtBairro);
		bairro.setLimit(50);
		RestrictedTextField cidade = new RestrictedTextField(this.txtCidade);
		cidade.setLimit(50);

	} // fim do construtor

	/**
	 * buscarCep
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						// lblStatus.setIcon(new
						// javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// criando um objeto para acessar a classe DAO
	DAO dao = new DAO();
	private JTable table;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;

	private void pesquisarCliente() {
		// ? -> parâmetro
		String read = "select idcli as ID, nome as Cliente, cep as CEP, endereco as Endereço, numero as Número,"
				+ "complemento as Complemento, bairro as Bairro, cidade as Cidade, uf as UF, fone as Fone,"
				+ "email as Email from clientes where nome like ?";
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
	 * Método responsável por adicionar um cliente no banco de dados
	 */
	private void adicionarCliente() {
		// validação de campos obrigatórios
		if (txtNomeCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtNomeCli.requestFocus();
		} else if (txtFoneCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtFoneCli.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			cboUf.requestFocus();
		} else {
			// inserir o cliente no banco
			String create = "insert into clientes (nome,fone,cep,endereco,numero,complemento,bairro,cidade,uf) values (?,?,?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtNomeCli.getText());
				pst.setString(2, txtFoneCli.getText());
				pst.setString(3, txtCep.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCidade.getText());
				pst.setString(9, cboUf.getSelectedItem().toString());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao adicionar o cliente", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
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
		txtIdCli.setText(table.getModel().getValueAt(setar, 0).toString());
		txtNomeCli.setText(table.getModel().getValueAt(setar, 1).toString());
		txtFoneCli.setText(table.getModel().getValueAt(setar, 2).toString());
		txtCep.setText(table.getModel().getValueAt(setar, 2).toString());
		txtEndereco.setText(table.getModel().getValueAt(setar, 3).toString());
		txtNumero.setText(table.getModel().getValueAt(setar, 4).toString());
		txtComplemento.setText(table.getModel().getValueAt(setar, 5).toString());
		txtBairro.setText(table.getModel().getValueAt(setar, 6).toString());
		txtCidade.setText(table.getModel().getValueAt(setar, 7).toString());
		cboUf.setSelectedItem(table.getModel().getValueAt(setar, 8).toString());
		// gerenciar os botões
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
	}
	
	/**
	 * Método responsável por editar os dados do cliente no banco de dados
	 */
	private void editarCliente() {
		// validação de campos obrigatórios
		if (txtNomeCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtNomeCli.requestFocus();
		} else if (txtFoneCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtFoneCli.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			cboUf.requestFocus();
		} else {
			// inserir o cliente no banco
			String update = "update clientes set nome = ?, fone = ?, cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?,uf = ? where idcli = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtNomeCli.getText());
				pst.setString(2, txtFoneCli.getText());
				pst.setString(3, txtCep.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(8, txtCidade.getText());
				pst.setString(9, cboUf.getSelectedItem().toString());
				pst.setString(10, txtIdCli.getText());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do cliente editado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao adicionar o cliente", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método para excluir o cliente do banco de dados
	 */
	private void excluirCliente() {
		// confimação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Deseja excluir este cliente?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where idcli = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtIdCli.getText());
				int excluir = pst.executeUpdate();
				if (excluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao excluir\nCliente possui pedido em aberto", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao excluir o cliente", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método para limpar os campos e gerenciar os botões
	 */
	private void limpar() {
		txtIdCli.setText(null);
		txtNomeCli.setText(null);
		txtFoneCli.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		// limpar a tabela
		((DefaultTableModel) table.getModel()).setRowCount(0);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
}
