package Main.Java.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import Main.Java.DAO.FuncionarioDAO;
import Main.Java.Model.Funcionario;

public class MainFrame extends JFrame {
    private FuncionarioDAO funcionarioDAO;
    private DefaultTableModel tableModel;

    private JComboBox<String> comboBoxTipoIdentificacion;

    private JTextField textFieldNumeroIdentificacion;
    private JTextField textFieldNombres;
    private JTextField textFieldApellidos;
    private JComboBox<String> comboBoxEstadoCivil;
    private JComboBox<String> comboBoxSexo;
    private JTextField textFieldDireccion;
    private JTextField textFieldTelefono;
    private JSpinner spinnerFechaNacimiento;

    private JComboBox<String> comboBoxFuncionariosParaBorrar;

    public MainFrame(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;

        setTitle("Gestión de Funcionarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear modelo de tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Tipo de Identificación");
        tableModel.addColumn("Número de Identificación");
        tableModel.addColumn("Nombres");
        tableModel.addColumn("Apellidos");
        tableModel.addColumn("Estado Civil");
        tableModel.addColumn("Sexo");
        tableModel.addColumn("Dirección");
        tableModel.addColumn("Teléfono");
        tableModel.addColumn("Fecha de Nacimiento");

        // Crear campos de entrada
        comboBoxTipoIdentificacion = new JComboBox<>(new String[]{"Cédula", "Pasaporte"});
        textFieldNumeroIdentificacion = new JTextField(20);
        textFieldNombres = new JTextField(20);
        textFieldApellidos = new JTextField(20);
        String[] estadoCivilOptions = {"Soltero", "Casado", "Divorciado"};
        comboBoxEstadoCivil = new JComboBox<>(estadoCivilOptions);
        String[] sexoOptions = {"Masculino", "Femenino", "Otro"};
        comboBoxSexo = new JComboBox<>(sexoOptions);
        textFieldDireccion = new JTextField(20);
        textFieldTelefono = new JTextField(20);

        // Crear SpinnerDateModel para el JSpinner de fecha de nacimiento
        SpinnerDateModel model = new SpinnerDateModel();
        spinnerFechaNacimiento = new JSpinner(model);
        spinnerFechaNacimiento.setEditor(new JSpinner.DateEditor(spinnerFechaNacimiento, "dd/MM/yyyy"));

        // Botón para insertar funcionario
        JButton btnInsertar = new JButton("Insertar Funcionario");
        btnInsertar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertarFuncionario();
            }
        });

        // Botón para eliminar funcionario
        JButton btnBorrarFuncionario = new JButton("Borrar Funcionario");
        btnBorrarFuncionario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarFuncionarioSeleccionado();
            }
        });

        // Crear tabla y scroll pane
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel para borrar funcionarios
        JPanel panelBorrarFuncionario = new JPanel();
        panelBorrarFuncionario.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBorrarFuncionario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        comboBoxFuncionariosParaBorrar = new JComboBox<>();
        comboBoxFuncionariosParaBorrar.setPreferredSize(new Dimension(200, 30));
        panelBorrarFuncionario.add(new JLabel("Seleccionar Funcionario a eliminar:"));
        panelBorrarFuncionario.add(comboBoxFuncionariosParaBorrar);
        panelBorrarFuncionario.add(btnBorrarFuncionario);

        // Panel para los campos de entrada
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(0, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCampos.add(new JLabel("Tipo Identificación:"));
        panelCampos.add(comboBoxTipoIdentificacion);
        panelCampos.add(new JLabel("Número Identificación:"));
        panelCampos.add(textFieldNumeroIdentificacion);
        panelCampos.add(new JLabel("Nombres:"));
        panelCampos.add(textFieldNombres);
        panelCampos.add(new JLabel("Apellidos:"));
        panelCampos.add(textFieldApellidos);
        panelCampos.add(new JLabel("Estado Civil:"));
        panelCampos.add(comboBoxEstadoCivil);
        panelCampos.add(new JLabel("Sexo:"));
        panelCampos.add(comboBoxSexo);
        panelCampos.add(new JLabel("Dirección:"));
        panelCampos.add(textFieldDireccion);
        panelCampos.add(new JLabel("Teléfono:"));
        panelCampos.add(textFieldTelefono);
        panelCampos.add(new JLabel("Fecha de Nacimiento:"));
        panelCampos.add(spinnerFechaNacimiento);

        // Panel para botones de insertar y borrar
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 1, 5, 5));
        panelBotones.add(btnInsertar);
        panelBotones.add(panelBorrarFuncionario);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.NORTH);
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        tableModel.setRowCount(0);

        try {
            java.util.List<Funcionario> funcionarios = funcionarioDAO.getAll();

            for (Funcionario funcionario : funcionarios) {
                Object[] fila = {
                    funcionario.getId(),
                    funcionario.getTipoIdentificacion(),
                    funcionario.getNumeroIdentificacion(),
                    funcionario.getNombres(),
                    funcionario.getApellidos(),
                    funcionario.getEstadoCivil(),
                    funcionario.getSexo(),
                    funcionario.getDireccion(),
                    funcionario.getTelefono(),
                    funcionario.getFechaNacimiento()
                };
                tableModel.addRow(fila);
            }

            comboBoxFuncionariosParaBorrar.removeAllItems();
            for (Funcionario funcionario : funcionarios) {
                comboBoxFuncionariosParaBorrar.addItem(funcionario.getNombres() + " " + funcionario.getApellidos() + " - " + funcionario.getNumeroIdentificacion());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la tabla");
        }
    }

    private void insertarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setTipoIdentificacion((String) comboBoxTipoIdentificacion.getSelectedItem());
        funcionario.setNumeroIdentificacion(textFieldNumeroIdentificacion.getText());
        funcionario.setNombres(textFieldNombres.getText());
        funcionario.setApellidos(textFieldApellidos.getText());
        funcionario.setEstadoCivil((String) comboBoxEstadoCivil.getSelectedItem());
        funcionario.setSexo((String) comboBoxSexo.getSelectedItem());
        funcionario.setDireccion(textFieldDireccion.getText());
        funcionario.setTelefono(textFieldTelefono.getText());
        funcionario.setFechaNacimiento((Date) spinnerFechaNacimiento.getValue());

        try {
            funcionarioDAO.insert(funcionario);
            JOptionPane.showMessageDialog(this, "Funcionario insertado correctamente");
            cargarDatosTabla();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar funcionario");
        }
    }

    private void eliminarFuncionarioSeleccionado() {
        int index = comboBoxFuncionariosParaBorrar.getSelectedIndex();
        if (index != -1) {
            try {
                funcionarioDAO.delete(funcionarioDAO.getAll().get(index).getId());
                cargarDatosTabla();
                JOptionPane.showMessageDialog(this, "Funcionario eliminado correctamente");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar funcionario");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un funcionario para eliminar");
        }
    }
}
