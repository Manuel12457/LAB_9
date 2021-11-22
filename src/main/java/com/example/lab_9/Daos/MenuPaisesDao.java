package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BContinente;
import com.example.lab_9.Beans.BPais;

import java.sql.*;
import java.util.ArrayList;

public class MenuPaisesDao extends BaseDao{

    public ArrayList<BPais> listarPaises(int idContinente) {

        ArrayList<BPais> listaPaises = new ArrayList<>();

        String sql = "SELECT p.idPaises, p.nombre, p.poblacion, p.tamanho, c.nombre as 'continente' FROM lab9.paises p\n" +
                "inner join lab9.continentes c on (p.Continentes_idContinentes = c.idContinentes)\n" +
                "where p.Continentes_idContinentes = ?\n" +
                "order by p.nombre;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idContinente);

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    BPais pais = new BPais();
                    pais.setIdPais(rs.getInt(1));
                    pais.setNombre(rs.getString(2));
                    pais.setPoblacion(rs.getLong(3));
                    pais.setTamanho(rs.getDouble(4));

                    BContinente continente = new BContinente(idContinente,rs.getString(5));
                    pais.setContinente(continente);

                    listaPaises.add(pais);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaPaises;

    }

    public String anhadirPais(BPais pais) {

        String sql = "insert into lab9.paises (nombre, poblacion, tamanho, Continentes_idContinentes)\n" +
                "values (?, ?, ?, ?);";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,pais.getNombre());
            pstmt.setLong(2, pais.getPoblacion());
            pstmt.setDouble(3, pais.getTamanho());

            pstmt.setInt(4, pais.getContinente().getIdContinente());

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String editarPais(BPais pais) {

        String sql = "update lab9.paises set nombre = ?, poblacion = ?, tamanho = ?, Continentes_idContinentes = ?\n" +
                "where idPaises = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,pais.getNombre());
            pstmt.setLong(2, pais.getPoblacion());
            pstmt.setDouble(3, pais.getTamanho());
            pstmt.setInt(4, pais.getContinente().getIdContinente());
            pstmt.setInt(5, pais.getIdPais());

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String eliminarPais(String idPais) {

        String sql = "delete from lab9.paises where idPaises = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, idPais);
            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

}
