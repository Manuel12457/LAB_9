package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BAlumno;
import com.example.lab_9.Beans.BContinente;
import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Beans.BParticipante;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuParticipantesDao extends BaseDao{

    public ArrayList<BParticipante> listarParticipantes() {

        ArrayList<BParticipante> listaParticipantes = new ArrayList<>();

        String sql = "SELECT * FROM lab9.participantes;";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                BParticipante participante = new BParticipante();
                participante.setIdParticipante(rs.getInt(1));
                participante.setNombre(rs.getString(2));
                participante.setApellido(rs.getString(3));
                participante.setEdad(rs.getInt(4));
                participante.setGenero(rs.getString(5));

                BPais pais = new BPais();
                pais.setIdPais(rs.getInt(6));
                participante.setPais(pais);

                BAlumno alumno = new BAlumno();
                alumno.setIdAlumno(rs.getInt(7));
                participante.setAlumno(alumno);

                listaParticipantes.add(participante);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaParticipantes;

    }

    public BParticipante obtenerParticipantePorId(int id) {

        BParticipante participante = new BParticipante();

        String sql = "SELECT * FROM lab9.participantes where idParticipantes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    participante.setIdParticipante(rs.getInt(1));
                    participante.setNombre(rs.getString(2));
                    participante.setApellido(rs.getString(3));
                    participante.setEdad(rs.getInt(4));
                    participante.setGenero(rs.getString(5));

                    MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
                    BPais pais = menuPaisesDao.obtenerPaisPorId(rs.getInt(6));
                    participante.setPais(pais);

                    //Falta agregar la instancia alumno

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return participante;

    }

    public String anhadirParticipante(BParticipante participante) {

        String sql = "insert into lab9.participantes (nombre, apellido, edad, genero, Paises_idPaises, Alumno_idAlumno)\n" +
                "values (?,?,?,?,?, null);";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, participante.getNombre());
            pstmt.setString(2, participante.getApellido());
            pstmt.setInt(3, participante.getEdad());
            pstmt.setString(4, participante.getGenero());
            pstmt.setInt(5, participante.getPais().getIdPais());

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String editarParticipante(BParticipante participante) {

        String sql = "update lab9.participantes set nombre = ?, apellido = ?, edad = ?, genero = ?, Paises_idPaises = ?\n" +
                "where idParticipantes = 1;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, participante.getNombre());
            pstmt.setString(2, participante.getApellido());
            pstmt.setInt(3, participante.getEdad());
            pstmt.setString(4, participante.getGenero());
            pstmt.setInt(5, participante.getPais().getIdPais());

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String eliminarParticipante(int idParticipante) {

        String sql = "delete from lab9.participantes where idPartipantes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idParticipante);
            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public boolean nombreEmpiezaConLetra(int idParticipante) {

        String sql = "select nombre from lab9.participantes where idParticipantes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idParticipante);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {

                    String nombre = rs.getString(1);
                    try {
                        String p = nombre.substring(0,1);
                        int pInt = Integer.parseInt(p); //Es un numero
                        return false;
                    } catch (NumberFormatException e) { //No es un numero
                        return true;
                    }

                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean apellidoEmpiezaConLetra(int idParticipante) {

        String sql = "select apellido from lab9.participantes where idParticipantes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idParticipante);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {

                    String apellido = rs.getString(1);
                    try {
                        String p = apellido.substring(0,1);
                        int pInt = Integer.parseInt(p); //Es un numero
                        return false;
                    } catch (NumberFormatException e) { //No es un numero
                        return true;
                    }

                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean validarEdad(int idParticipante) {

        String sql = "select edad from lab9.participantes where idParticipantes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idParticipante);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {

                    if (rs.getInt(1) > 18) {
                        return true; //El participante tiene mas de 18 anhos
                    } else {
                        return false; //El participante tiene 18 anhos o menos
                    }

                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean validarGenero(int idParticipante) {

        String sql = "select genero from lab9.participantes where idParticipantes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idParticipante);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {

                    String genero = rs.getString(1);
                    if (genero.equals("M") || genero.equals("F") || genero.equals("Otro")) {
                        return true; //El participante cumple con las validaciones
                    } else {
                        return false; //El participante NO cumple con las validaciones
                    }

                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public HashMap<String, Float> porcentajeMF() {

        HashMap<String, Float> arreglo = new HashMap<>();

        String sql = "select genero, count(idParticipantes), truncate(count(idParticipantes) * 100 / (select count(idParticipantes) from lab9.participantes where genero = 'M' or genero = 'F'),2) as 'porcentaje'\n" +
                "from lab9.participantes group by genero having genero = 'M' or genero = 'F';";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();
            arreglo.put("M",rs.getFloat(3));
            rs.next();
            arreglo.put("F",rs.getFloat(3));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return arreglo;

    }

    public float promedioEdad() {

        String sql = "select truncate(avg(edad),2) from lab9.participantes;";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();
            return rs.getFloat(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    public HashMap<String, Integer> paisesYNumeroParticipantes() {

        HashMap<String, Integer> arreglo = new HashMap<>();

        String sql = "select pa.nombre, count(p.idParticipantes) as '# participantes' from lab9.participantes p\n" +
                "left join lab9.paises pa on (p.Paises_idPaises = pa.idPaises)\n" +
                "group by pa.nombre\n" +
                "order by '# participantes';";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                arreglo.put(rs.getString(1),rs.getInt(2));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return arreglo;

    }

}
