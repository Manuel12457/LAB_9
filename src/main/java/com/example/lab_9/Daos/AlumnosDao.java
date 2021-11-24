package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BAlumno;
import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Beans.BParticipante;
import com.example.lab_9.Beans.BUniversidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnosDao extends BaseDao{

    public BAlumno obtenerAlumnoPorId(int id) {

        BAlumno alumno = new BAlumno();

        String sql = "SELECT * FROM lab9.alumno where idAlumno = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    alumno.setIdAlumno(rs.getInt(1));
                    alumno.setCodigo(rs.getString(2));
                    alumno.setPromedioPonderado(rs.getDouble(3));
                    alumno.setCondicion(rs.getString(4));

                    MenuUniversidadesDao menuUniversidadesDao = new MenuUniversidadesDao();
                    BUniversidad universidad = menuUniversidadesDao.obtenerUniversidadPorId(rs.getInt(5));
                    alumno.setUniversidad(universidad);

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return alumno;

    }

    public ArrayList<BParticipante> listarAlumnos(int idUniversidad) {

        ArrayList<BParticipante> listaAlumnos = new ArrayList<>();
        MenuParticipantesDao menuParticipantesDao = new MenuParticipantesDao();

        String sql = "select p.idParticipantes from lab9.participantes p\n" +
                "inner join lab9.alumno a on (p.Alumno_idAlumno = a.idAlumno)\n" +
                "inner join lab9.universidad u on (a.Universidad_idUniversidad = u.idUniversidad)\n" +
                "where u.idUniversidad = ?\n" +
                "order by a.promedioPonderado desc;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idUniversidad);

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    BParticipante participante = menuParticipantesDao.obtenerParticipantePorId(rs.getInt(1));
                    listaAlumnos.add(participante);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaAlumnos;
    }

    public String anhadirAlumno(BAlumno alumno, int idParticipante) {

        boolean exito1;
        boolean exito2;

        String sql = "insert into lab9.alumno (codigoAlumno, promedioPonderado, Universidad_idUniversidad)\n" +
                "value (?,?,?);";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, alumno.getCodigo());
            pstmt.setDouble(2, alumno.getPromedioPonderado());
            pstmt.setInt(3, alumno.getUniversidad().getIdUniversidad());

            pstmt.executeUpdate();
            exito1 = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            exito1 = false;
        }

        String sql1 = "update lab9.participantes set Alumno_idAlumno = ? where idParticipantes = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);) {

            pstmt.setInt(1, alumno.getIdAlumno());
            pstmt.setInt(2, idParticipante);

            pstmt.executeUpdate();
            exito2 = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            exito2 = false;
        }

        if (exito1 && exito2) {
            return "e";
        } else {
            return "ne";
        }

    }

    public String editarAlumno(BAlumno alumno) {

        String sql = "update lab9.alumno set codigoAlumno = ?, promedioPonderado = ?, Universidad_idUniversidad = ?\n" +
                "where idAlumno = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, alumno.getCodigo());
            pstmt.setDouble(2, alumno.getPromedioPonderado());
            pstmt.setInt(3, alumno.getUniversidad().getIdUniversidad());
            pstmt.setInt(4, alumno.getIdAlumno());

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String condicionEliminadoAlumno(int idAlumno) {

        String sql = "update lab9.alumno set condicion = 'eliminado' where idAlumno = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idAlumno);

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String eliminarAlumno(int idAlumno) {

        //Buscar el participante que sea ese alumno;
        String sql = "select idParticipantes from lab9.participantes where Alumno_idAlumno = ?;";
        int idParticipante = 0;

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idAlumno);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    idParticipante = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //Colocar el idAlumno en null del participante
        String sql1 = "update lab9.participantes set Alumno_idAlumno = null where idParticipantes = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);) {

            pstmt.setInt(1, idParticipante);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //Ahora se elimina el alumno
        String sql2 = "delete from lab9.alumno where idAlumno = ?;";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql2);) {

            pstmt.setInt(1, idAlumno);
            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public boolean codigoExisteEnUniversidad(String codigo, int idUniversidad) {

        String sql = "select codigoAlumno from lab9.alumno where Universidad_idUniversidad = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idUniversidad);

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    if (codigo.equals(rs.getString(1))) {
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;

    }

}
