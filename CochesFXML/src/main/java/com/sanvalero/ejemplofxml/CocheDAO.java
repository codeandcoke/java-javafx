package com.sanvalero.ejemplofxml;

import com.sanvalero.ejemplofxml.domain.Coche;
import com.sanvalero.ejemplofxml.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CocheDAO {

    private Connection conexion;

    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                username, password);
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public void guardarCoche(Coche coche) throws SQLException {
        String sql = "INSERT INTO coches (matricula, marca, modelo, tipo) VALUES (?, ?, ?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, coche.getMatricula());
        sentencia.setString(2, coche.getMarca());
        sentencia.setString(3, coche.getModelo());
        sentencia.setString(4, coche.getTipo());
        sentencia.executeUpdate();
    }

    public void eliminarCoche(Coche coche) throws SQLException {
        String sql = "DELETE FROM coches WHERE matricula = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, coche.getMatricula());
        sentencia.executeUpdate();
    }

    public void modificarCoche(Coche cocheAntiguo, Coche cocheNuevo) throws SQLException {
        String sql = "UPDATE coches SET matricula = ?, marca = ?, modelo = ?, tipo = ? WHERE id = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, cocheNuevo.getMatricula());
        sentencia.setString(2, cocheNuevo.getMarca());
        sentencia.setString(3, cocheNuevo.getModelo());
        sentencia.setString(4, cocheNuevo.getTipo());
        sentencia.setInt(5, cocheAntiguo.getId());
        sentencia.executeUpdate();
    }

    public List<Coche> obtenerCoches() throws SQLException {
        List<Coche> coches = new ArrayList<>();
        String sql = "SELECT * FROM coches";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Coche coche = new Coche();
            coche.setId(resultado.getInt(1));
            coche.setMatricula(resultado.getString(2));
            coche.setMarca(resultado.getString(3));
            coche.setModelo(resultado.getString(4));
            coche.setTipo(resultado.getString(5));

            coches.add(coche);
        }

        return coches;
    }

    public boolean existeCoche(String matricula) throws SQLException {
        String sql = "SELECT * FROM coches WHERE matricula = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();

        return resultado.next();
    }
}
