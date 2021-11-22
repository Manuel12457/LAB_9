package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BContinente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContinentesDao extends BaseDao{

    public ArrayList<BContinente> listarContinentes() {

        ArrayList<BContinente> listaContinentes = new ArrayList<>();

        String sql = "SELECT * FROM lab9.continentes;";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                BContinente continente = new BContinente(rs.getInt(1),rs.getString(2));
                listaContinentes.add(continente);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaContinentes;

    }

}
