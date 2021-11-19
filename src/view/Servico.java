package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;

import model.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Atxy2k.CustomTextField.RestrictedTextField;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.ButtonGroup;

public class Servico extends JDialog {
	private JTextField txtPesquisar;
	// variável de apoio ao uso do checkbox
	private String tipo;
	int x;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servico dialog = new Servico();
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
	public Servico() {
		setTitle("Ordem de Servi\u00E7o");
		setBounds(100, 100, 882, 481);
		getContentPane().setLayout(null);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarServico();
			}
		});
		txtPesquisar.setBounds(430, 17, 191, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Servico.class.getResource("/img/pesquisar.png")));
		lblNewLabel.setBounds(633, 11, 32, 32);
		getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "OS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("");
		panel.setBounds(8, 53, 412, 113);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtOs = new JTextField();
		txtOs.setEditable(false);
		txtOs.setBounds(10, 23, 86, 20);
		panel.add(txtOs);
		txtOs.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setBounds(138, 26, 38, 14);
		panel.add(lblNewLabel_1);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(186, 23, 215, 20);
		panel.add(txtData);
		txtData.setColumns(10);

		chkOrcamento = new JCheckBox("Or\u00E7amento");
		chkOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "Orçamento";
			}
		});
		buttonGroup.add(chkOrcamento);
		chkOrcamento.setBounds(10, 75, 97, 23);
		panel.add(chkOrcamento);

		chkServico = new JCheckBox("Servi\u00E7o");
		chkServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "Serviço";
			}
		});
		buttonGroup.add(chkServico);
		chkServico.setBounds(106, 75, 73, 23);
		panel.add(chkServico);

		cboStatus = new JComboBox();
		cboStatus.setModel(new DefaultComboBoxModel(new String[] { "", "Aguardando aprova\u00E7\u00E3o", "Na bancada",
				"Aguardando retirada", "Retirado" }));
		cboStatus.setBounds(186, 75, 215, 22);
		panel.add(cboStatus);

		txtId = new JTextField();
		txtId.setBounds(770, 17, 86, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(734, 20, 26, 14);
		getContentPane().add(lblNewLabel_2);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(430, 59, 426, 104);
		getContentPane().add(desktopPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 459, 104);
		desktopPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Informa\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(146, 177, 710, 149);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Equipamento");
		lblNewLabel_3.setBounds(10, 32, 85, 14);
		panel_1.add(lblNewLabel_3);

		txtEquipamento = new JTextField();
		txtEquipamento.setBounds(104, 29, 596, 20);
		panel_1.add(txtEquipamento);
		txtEquipamento.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Defeito");
		lblNewLabel_4.setBounds(10, 76, 52, 14);
		panel_1.add(lblNewLabel_4);

		txtDefeito = new JTextField();
		txtDefeito.setBounds(72, 73, 628, 20);
		panel_1.add(txtDefeito);
		txtDefeito.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("T\u00E9cnico");
		lblNewLabel_5.setBounds(10, 121, 52, 14);
		panel_1.add(lblNewLabel_5);

		txtTecnico = new JTextField();
		txtTecnico.setBounds(72, 118, 346, 20);
		panel_1.add(txtTecnico);
		txtTecnico.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Valor");
		lblNewLabel_6.setBounds(520, 121, 46, 14);
		panel_1.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(Servico.class.getResource("/img/smartphone.png")));
		lblNewLabel_7.setBounds(8, 189, 128, 128);
		getContentPane().add(lblNewLabel_7);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitirOs();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Servico.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(8, 361, 70, 70);
		getContentPane().add(btnAdicionar);

		btnLer = new JButton("");
		btnLer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarOs();
			}
		});
		btnLer.setIcon(new ImageIcon(Servico.class.getResource("/img/read.png")));
		btnLer.setBounds(88, 361, 70, 70);
		getContentPane().add(btnLer);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOs();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setIcon(new ImageIcon(Servico.class.getResource("/img/update.png")));
		btnEditar.setBounds(168, 361, 70, 70);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOs();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(new ImageIcon(Servico.class.getResource("/img/delete.png")));
		btnExcluir.setBounds(248, 361, 70, 70);
		getContentPane().add(btnExcluir);

		btnImprimir = new JButton("");
		btnImprimir.setIcon(new ImageIcon(Servico.class.getResource("/img/print.png")));
		btnImprimir.setBounds(328, 361, 70, 70);
		getContentPane().add(btnImprimir);

		// uso da biblioteca atxy2k para validações
		RestrictedTextField equipamento = new RestrictedTextField(txtEquipamento);
		equipamento.setLimit(200);
		RestrictedTextField defeito = new RestrictedTextField(txtDefeito);
		defeito.setLimit(200);
		RestrictedTextField tecnico = new RestrictedTextField(txtTecnico);
		
		txtValor = new JTextField();
		txtValor.setText("0");
		txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				/*try {
			        x = Integer.parseInt(txtValor.getText());
			    } catch (NumberFormatException nfe) {
			    	JOptionPane.showMessageDialog(null, "Só números permitido", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
	                txtValor.setText("");
			    }*/
			}
		});
		txtValor.setBounds(576, 118, 96, 20);
		panel_1.add(txtValor);
		txtValor.setColumns(10);
		tecnico.setLimit(50);
		RestrictedTextField valor = new RestrictedTextField(txtValor);
		valor.setLimit(10);
	} // fim do construtor

	DAO dao = new DAO();
	private JTextField txtOs;
	private JTextField txtData;
	private JTable table;
	private JTextField txtId;
	private JTextField txtEquipamento;
	private JTextField txtDefeito;
	private JTextField txtTecnico;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox cboStatus;
	private JCheckBox chkOrcamento;
	private JCheckBox chkServico;
	private JButton btnAdicionar;
	private JButton btnLer;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnImprimir;
	private JTextField txtValor;

	private void pesquisarServico() {
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
	 * Método responsável por setar os campos da tabelo no formulário
	 */
	private void setarCampos() {
		// a linha abaixo obtem o conteúdo da linha da tabela
		// int (índice) [0], [1], [2],...
		int setar = table.getSelectedRow();
		// setar os campos
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
	}

	/**
	 * Método responsável pela pesquisa da OS
	 */
	private void pesquisarOs() {
		// técnica usada para capturar o número da OS
		String numOs = JOptionPane.showInputDialog("Número da OS");
		String read = "select * from tbos where os = " + numOs;
		String read2 = "select os as OS, dataos as DataOS, tipo as Tipo, statusos as Status, equipamento as Equipamento, defeito as Defeito, tecnico as Técnico, valor as Valor from tbos inner join clientes on tbos.idcli = clientes.idcli and os = "
				+ numOs;
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			PreparedStatement pst2 = con.prepareStatement(read2);
			ResultSet rs = pst.executeQuery();
			ResultSet rs2 = pst2.executeQuery();
			if (rs.next() && rs2.next()) {
				txtId.setText(rs.getString(9));
				txtOs.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				if (rs.getString(3).equals("Orçamento")) {
					chkOrcamento.setSelected(true);
					tipo = "Orçamento";
				} else {
					chkServico.setSelected(true);
					tipo = "Serviço";
				}
				cboStatus.setSelectedItem(rs.getString(4));
				txtEquipamento.setText(rs.getString(5));
				txtDefeito.setText(rs.getString(6));
				txtTecnico.setText(rs.getString(7));
				txtValor.setText(rs.getString(8));
				table.setModel(DbUtils.resultSetToTableModel(rs2));
				btnAdicionar.setEnabled(false);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "Número de OS não existe", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método responsável por adicionar uma OS no banco de dados
	 */
	private void emitirOs() {
		// validação de campos obrigatórios
		if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID", "Atenção!", JOptionPane.WARNING_MESSAGE);
			txtId.requestFocus();
		} else if (tipo == null) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo da OS", "Atenção!", JOptionPane.WARNING_MESSAGE);
		} else if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o status da OS", "Atenção!", JOptionPane.WARNING_MESSAGE);
			cboStatus.requestFocus();
		} else if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Equipamento", "Atenção!", JOptionPane.WARNING_MESSAGE);
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito", "Atenção!", JOptionPane.WARNING_MESSAGE);
			txtDefeito.requestFocus();
		} else {
			// inserir o cliente no banco
			String create = "insert into tbos (tipo,statusos,equipamento,defeito,tecnico,valor,idcli) values (?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, tipo);
				pst.setString(2, cboStatus.getSelectedItem().toString());
				pst.setString(3, txtEquipamento.getText());
				pst.setString(4, txtDefeito.getText());
				pst.setString(5, txtTecnico.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, txtId.getText());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "OS adicionada com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}
				con.close();
				// o catch abaixo se refere ao valor duplicado de email(UNIQUE)
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao adicionar a OS", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método responsável por adicionar uma OS no banco de dados
	 */
	private void editarOs() {
		// validação de campos obrigatórios
		if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID", "Atenção!", JOptionPane.WARNING_MESSAGE);
			txtId.requestFocus();
		} else if (tipo == null) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo da OS", "Atenção!", JOptionPane.WARNING_MESSAGE);
		} else if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o status da OS", "Atenção!", JOptionPane.WARNING_MESSAGE);
			cboStatus.requestFocus();
		} else if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Equipamento", "Atenção!", JOptionPane.WARNING_MESSAGE);
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito", "Atenção!", JOptionPane.WARNING_MESSAGE);
			txtDefeito.requestFocus();
		} else {
			// inserir o cliente no banco
			String create = "update tbos set tipo = ?, statusos = ?, equipamento = ?, defeito = ?, tecnico = ?, valor = ? where os = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, tipo);
				pst.setString(2, cboStatus.getSelectedItem().toString());
				pst.setString(3, txtEquipamento.getText());
				pst.setString(4, txtDefeito.getText());
				pst.setString(5, txtTecnico.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, txtOs.getText());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "OS editada com com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}
				con.close();
				// o catch abaixo se refere ao valor duplicado de email(UNIQUE)
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao editar a OS", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * Método responsável por excluir o usuário do banco de dados
	 */
	private void excluirOs() {
		// confimação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Deseja excluir esta OS?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from tbos where os = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtOs.getText());
				int excluir = pst.executeUpdate();
				if (excluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "OS excluída com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Erro ao excluir\nCliente possui pedido em aberto", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao excluir a OS", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Método responsável por limpar os campos e gereniar os botões
	 */
	private void limpar() {
		txtId.setText(null);
		txtOs.setText(null);
		txtData.setText(null);
		buttonGroup.clearSelection();
		cboStatus.setSelectedItem("");
		txtEquipamento.setText(null);
		txtDefeito.setText(null);
		txtTecnico.setText(null);
		txtValor.setText(null);
		// limpar a tabela
		((DefaultTableModel) table.getModel()).setRowCount(0);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnImprimir.setEnabled(false);
		tipo = null;
	}
}
