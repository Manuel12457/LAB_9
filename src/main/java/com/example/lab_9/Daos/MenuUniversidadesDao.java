package com.example.lab_9.Daos;

import com.example.lab_9.Beans.BAlumno;
import com.example.lab_9.Beans.BPais;
import com.example.lab_9.Beans.BParticipante;
import com.example.lab_9.Beans.BUniversidad;
import com.example.lab_9.Dtos.DtoUniversidad;

import java.sql.*;
import java.util.ArrayList;

public class MenuUniversidadesDao extends BaseDao{

    public ArrayList<DtoUniversidad> listarUniversidades(String order) {

        ArrayList<DtoUniversidad> listaUniversidad = new ArrayList<>();
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
                "order by " + orden + ";";

        System.out.println(sql);

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                BUniversidad universidad = obtenerUniversidadPorId(rs.getInt(1));

                DtoUniversidad dtoUniversidad = new DtoUniversidad();
                dtoUniversidad.setAlumnos(rs.getInt(7));
                dtoUniversidad.setUniversidad(universidad);

                listaUniversidad.add(dtoUniversidad);
            }

        } catch (SQLException e) {
            System.out.println("No se pudo realizar la busqueda");
        }

        return listaUniversidad;

    }

    public BUniversidad obtenerUniversidadPorId(int id) {

        BUniversidad universidad = new BUniversidad();

        String sql = "select * from lab9.universidad where idUniversidad = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    universidad.setIdUniversidad(rs.getInt(1));
                    universidad.setRanking(rs.getInt(2));
                    universidad.setFoto(rs.getString(3));
                    universidad.setNombre(rs.getString(5));

                    MenuPaisesDao menuPaisesDao = new MenuPaisesDao();
                    BPais pais = menuPaisesDao.obtenerPaisPorId(rs.getInt(4));
                    universidad.setPais(pais);

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return universidad;

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

        //Obtengo la lista de id de los participantes que estan en dicha universidad
        String sql1 = "select p.idParticipantes ,a.idAlumno from lab9.participantes p\n" +
                "inner join lab9.alumno a on (p.Alumno_idAlumno = a.idAlumno)\n" +
                "inner join lab9.universidad u on (a.Universidad_idUniversidad = u.idUniversidad)\n" +
                "where u.idUniversidad = ?;";

        ArrayList<Integer> listaIdParticipante = new ArrayList<>();
        ArrayList<Integer> listaIdAlumno = new ArrayList<>();

        try (Connection conn1 = this.getConnection();
             PreparedStatement pstmt = conn1.prepareStatement(sql1);) {

            pstmt.setInt(1, idUniversidad);

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    listaIdParticipante.add(rs.getInt(1));
                    listaIdAlumno.add(rs.getInt(2));
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

        //Elimino los alumnos de la universidad
        String sql6 = "delete from lab9.alumno where idAlumno in (" + contenidoIn2 + ");";

        try (Connection conn6 = this.getConnection();
             Statement stmt = conn6.createStatement();) {

            stmt.executeUpdate(sql6);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //Elimino la universidad
        String sql4 = "delete from lab9.universidad where idUniversidad = ?;";

        try (Connection conn4 = this.getConnection();
             PreparedStatement pstmt = conn4.prepareStatement(sql4);) {

            pstmt.setInt(1, idUniversidad);
            pstmt.executeUpdate();
            return "e";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "ne";
        }

    }

    public boolean validarExistenciaUniversidadesEnPais(int idPais) {

        String sql = "select * from lab9.universidad where Paises_idPaises = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idPais);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    return false; //Aun hay universidades
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; //Hubo un error
        }

        return true; //Ya no hay universidades, por loq que el pais debe eliminarse
                    // (recurrir a la funcion en ManuPaisesDao)
    }


}
