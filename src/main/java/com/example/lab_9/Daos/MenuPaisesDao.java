package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BContinente;
import com.example.lab_9.Beans.BPais;

import java.sql.*;
import java.util.ArrayList;

public class MenuPaisesDao extends BaseDao{

    public ArrayList<BPais> listarPaises(String idContinente) {

        ArrayList<BPais> listaPaises = new ArrayList<>();

        String sql = "SELECT p.idPaises, p.nombre, p.poblacion, p.tamanho, c.nombre as 'continente', c.idContinentes FROM lab9.paises p\n" +
                "inner join lab9.continentes c on (p.Continentes_idContinentes = c.idContinentes)\n" +
                "where p.Continentes_idContinentes like ?\n" +
                "order by p.nombre;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, "%" + idContinente + "%");

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    BPais pais = new BPais();
                    pais.setIdPais(rs.getInt(1));
                    pais.setNombre(rs.getString(2));
                    pais.setPoblacion(rs.getLong(3));
                    pais.setTamanho(rs.getDouble(4));

                    ContinentesDao continentesDao = new ContinentesDao();
                    BContinente continente = continentesDao.obtenerContinentePorId(rs.getInt(6));
                    pais.setContinente(continente);

                    listaPaises.add(pais);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaPaises;

    }

    public BPais obtenerPaisPorId(int id) {

        BPais pais = new BPais();

        String sql = "select * from lab9.paises where idPaises = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    pais.setIdPais(rs.getInt(1));
                    pais.setNombre(rs.getString(2));
                    pais.setPoblacion(rs.getLong(3));
                    pais.setTamanho(rs.getDouble(4));

                    ContinentesDao continentesDao = new ContinentesDao();
                    BContinente continente = continentesDao.obtenerContinentePorId(rs.getInt(5));
                    pais.setContinente(continente);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pais;

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

    public String eliminarPais(int idPais) {

        //Obtengo la lista de id de los participantes que estan en dicho pais
        String sql1 = "select p.idParticipantes ,a.idAlumno, u.idUniversidad from lab9.participantes p\n" +
                "inner join lab9.alumno a on (p.Alumno_idAlumno = a.idAlumno)\n" +
                "inner join lab9.universidad u on (a.Universidad_idUniversidad = u.idUniversidad)\n" +
                "where u.Paises_idPaises = ?;";

        ArrayList<Integer> listaIdParticipante = new ArrayList<>();
        ArrayList<Integer> listaIdAlumno = new ArrayList<>();
        ArrayList<Integer> listaIdUniversidad = new ArrayList<>();
        int id;

        try (Connection conn1 = this.getConnection();
             PreparedStatement pstmt = conn1.prepareStatement(sql1);) {

            pstmt.setInt(1, idPais);

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    listaIdParticipante.add(rs.getInt(1));
                    listaIdAlumno.add(rs.getInt(2));

                    id = rs.getInt(3);
                    if (listaIdUniversidad.size() != 0) { //Hay contenido en la lista
                        for (int i : listaIdUniversidad) {
                            if (id != i) { //Si existe el id, no se agrega
                                listaIdUniversidad.add(id);
                            }
                        }
                    } else { //No hay contenido en la lista
                        listaIdUniversidad.add(id);
                    }

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String contenidoIn = " ";

        for (int i = 0; i < listaIdParticipante.size(); i++) {
            if (i == listaIdParticipante.size() - 1) {
                contenidoIn = contenidoIn + i;
            } else {
                contenidoIn = contenidoIn + i + ",";
            }
        }

        //Actualizo a null los idalumno de los participantes
        String sql2 = "update lab9.participantes set Alumno_idAlumno = null where idParticipantes in (" + contenidoIn + ");";

        try (Connection conn2 = this.getConnection();
             Statement stmt = conn2.createStatement();) {

            stmt.executeUpdate(sql2);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String contenidoIn2 = " ";

        for (int j = 0; j < listaIdAlumno.size(); j++) {
            if (j == listaIdAlumno.size() - 1) {
                contenidoIn2 = contenidoIn2 + j;
            } else {
                contenidoIn2 = contenidoIn2 + j + ",";
            }
        }

        //Elimino los alumnos de dicho pais
        String sql3 = "delete from lab9.alumno where idAlumno in (" + contenidoIn2 + ");";

        try (Connection conn4 = this.getConnection();
             Statement stmt = conn4.createStatement();) {

            stmt.executeUpdate(sql3);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String contenidoIn3 = " ";

        for (int k = 0; k < listaIdUniversidad.size(); k++) {
            if (k == listaIdUniversidad.size() - 1) {
                contenidoIn3 = contenidoIn3 + k;
            } else {
                contenidoIn3 = contenidoIn3 + k + ",";
            }
        }

        //Elimino las universidades de dicho pais
        String sql4 = "delete from lab9.universidad where idUniversidad in (" + contenidoIn3 + ");";

        try (Connection conn4 = this.getConnection();
             Statement stmt = conn4.createStatement();) {

            stmt.executeUpdate(sql4);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //Elimino el pais
        String sql = "delete from lab9.paises where idPaises = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idPais);
            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

}
