package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Beans.BParticipante;
import com.example.lab_9.Beans.BUniversidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuUniversidadesDao extends BaseDao{

    public ArrayList<BUniversidad> listarUniversidades(String order) {

        ArrayList<BUniversidad> listaUniversidad = new ArrayList<>();
        String orden = "u.ranking";

        if (order.equals("ranking")) {
            orden = "u.ranking";
        } else if (order.equals("nombre")) {
            orden = "u.nombre";
        } else if (order.equals("alumnos")) {
            orden = "`# alumnos`";
        } else if (order.equals("pais")) {
            orden = "p.nombre";
        }

        String sql = "select u.idUniversidad, u.nombre, p.nombre, u.Paises_idPaises, u.ranking, u.foto, (select count(*) from lab9.alumno group by Universidad_idUniversidad having Universidad_idUniversidad = idUniversidad) as `# alumnos`\n" +
                "from lab9.universidad u\n" +
                "inner join lab9.paises p on (u.Paises_idPaises = p.idPaises)\n" +
                "order by ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, orden);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    BUniversidad universidad = new BUniversidad();
                    universidad.setIdUniversidad(rs.getInt(1));
                    universidad.setNombre(rs.getString(2));
                    universidad.setRanking(rs.getInt(5));
                    universidad.setFoto(rs.getString(6));
                    universidad.setAlumnos(rs.getInt(7));

                    MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
                    BPais pais = menuPaisesDao.obtenerPaisPorId(rs.getInt(4));
                    universidad.setPais(pais);

                    listaUniversidad.add(universidad);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaUniversidad;

    }

    public String anhadirUniversidad(BUniversidad universidad) {

        String sql = "insert into lab9.universidad (ranking, foto, Paises_idPaises, nombre)\n" +
                "values (?, ?, ?, ?);";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, universidad.getRanking());
            pstmt.setString(2, universidad.getFoto());
            pstmt.setInt(3, universidad.getPais().getIdPais());
            pstmt.setString(4, universidad.getNombre());

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String editarUniversidad(BUniversidad universidad) {

        String sql = "update lab9.universidad set nombre = ?, ranking = ?, foto = ?, Paises_idPaises = ?\n" +
                "where idUniversidad = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, universidad.getNombre());
            pstmt.setInt(2, universidad.getRanking());
            pstmt.setString(3, universidad.getFoto());
            pstmt.setInt(4, universidad.getPais().getIdPais());
            pstmt.setInt(5, universidad.getIdUniversidad());

            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public String eliminarUniversidad(int idUniversidad) {

        String sql = "delete from lab9.universidad where idUniversidad = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idUniversidad);
            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    //Para eliminar los alumnos al momento de eliminar la universidad
    //Primero debe colocarse el idAlumno en los participantes a null para luego eliminar el idAlumno de la tabla alumnos
    //1) Buscar todos los participantes cuya idUniversidad corresponda a la universidad a borrar
    //2)
    //update lab9.participantes set Alumno_idAlumno = null where idParticipantes = 12;
    //delete from lab9.alumno where idAlumno = 2;

}
