package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BContinente;

import java.sql.*;
import java.util.ArrayList;

public class ContinentesDao extends BaseDao{

    public ArrayList<BContinente> listarContinentes() {

        ArrayList<BContinente> listaContinentes = new ArrayList<>();

        String sql = "SELECT * FROM lab9.continentes;";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                BContinente continente = new BContinente();
                continente.setIdContinente(rs.getInt(1));
                continente.setContinente(rs.getString(2));
                listaContinentes.add(continente);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaContinentes;

    }

    public BContinente obtenerContinentePorId(int id) {

        BContinente continente = new BContinente();

        String sql = "SELECT * FROM lab9.continentes where idContinentes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    continente.setIdContinente(rs.getInt(1));
                    continente.setContinente(rs.getString(2));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return continente;
    }

}
