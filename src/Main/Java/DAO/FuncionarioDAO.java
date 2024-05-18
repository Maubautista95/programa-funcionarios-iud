package Main.Java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Main.Java.Model.Funcionario;

public class FuncionarioDAO {

    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO Funcionarios (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, funcionario.getTipoIdentificacion());
            statement.setString(2, funcionario.getNumeroIdentificacion());
            statement.setString(3, funcionario.getNombres());
            statement.setString(4, funcionario.getApellidos());
            statement.setString(5, funcionario.getEstadoCivil());
            statement.setString(6, funcionario.getSexo());
            statement.setString(7, funcionario.getDireccion());
            statement.setString(8, funcionario.getTelefono());
            statement.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            statement.executeUpdate();
        }
    }

    public List<Funcionario> getAll() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionarios";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                funcionario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                funcionario.setNombres(rs.getString("nombres"));
                funcionario.setApellidos(rs.getString("apellidos"));
                funcionario.setEstadoCivil(rs.getString("estado_civil"));
                funcionario.setSexo(rs.getString("sexo"));
                funcionario.setDireccion(rs.getString("direccion"));
                funcionario.setTelefono(rs.getString("telefono"));
                funcionario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }

    //Método borrar
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Funcionarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    //Método update
    
    public void update(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE Funcionarios SET tipo_identificacion = ?, numero_identificacion = ?, nombres = ?, apellidos = ?, estado_civil = ?, sexo = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, funcionario.getTipoIdentificacion());
            statement.setString(2, funcionario.getNumeroIdentificacion());
            statement.setString(3, funcionario.getNombres());
            statement.setString(4, funcionario.getApellidos());
            statement.setString(5, funcionario.getEstadoCivil());
            statement.setString(6, funcionario.getSexo());
            statement.setString(7, funcionario.getDireccion());
            statement.setString(8, funcionario.getTelefono());
            statement.setDate(9, new java.sql.Date(funcionario.getFechaNacimiento().getTime()));
            statement.setInt(10, funcionario.getId());
            statement.executeUpdate();
        }
    }

}
