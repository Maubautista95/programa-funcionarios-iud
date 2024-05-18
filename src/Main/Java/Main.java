package Main.Java;
import Main.Java.DAO.FuncionarioDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Main.Java.UI.MainFrame;


public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/funcionarios_bdd", "postgres", "Admin");
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
            JFrame frame = new MainFrame(funcionarioDAO);
            frame.setTitle("Gestión de Funcionarios");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
