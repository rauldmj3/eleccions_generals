import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class IntroCandidats {
    public static void candidats() throws Exception {
        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");
        BufferedReader bfLector = null;
        try {
            Path pathActual = Paths.get(System.getProperty("user.dir"));
            Path pathFitxer = Paths.get(pathActual.toString(), "src/datos", "04021911.DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
            String codi_candidatura = strLinia.substring(15, 21).trim();
            String num_ordre = strLinia.substring(21, 24).trim();
            String nom = strLinia.substring(25, 50).trim();
            String cog1 = strLinia.substring(50, 75).trim();
            String cog2 = strLinia.substring(75, 100).trim();
            String tipus = strLinia.substring(24, 25).trim();
            String ine = strLinia.substring(9, 11).trim();
            int candidatura_id = selectCandidatura(codi_candidatura, con);
            int persona_id = selectPersona(nom, cog1, cog2, con);
            int provincia_id = selectProvincia(ine, con);
            try {
                // the mysql insert statement
                String query = " INSERT INTO candidats (candidat_id,candidatura_id,persona_id,provincia_id,num_ordre,tipus)"
                        + " values (?,?,?,?,?,?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, i);
                preparedStmt.setInt(2, candidatura_id);
                preparedStmt.setInt(3, persona_id);
                preparedStmt.setInt(4, provincia_id);
                preparedStmt.setString(5, num_ordre);
                preparedStmt.setString(6, tipus);

                // execute the preparedstatement
                preparedStmt.execute();

            } catch (Exception e) {
                System.out.println(e);
            }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bfLector != null)
                    bfLector.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int selectCandidatura(String codi, Connection con) {
        int candidatura = 0;
        try {
            String query = "SELECT candidatura_id " +
                    " FROM candidatures " +
                    "WHERE codi_candidatura = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, codi);

            ResultSet rs = preparedStmt.executeQuery();
            rs.next();
            candidatura = rs.getInt("candidatura_id");
            return candidatura;

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int selectPersona(String nom, String cognom1, String cognom2, Connection con) {
        int persona = 0;
        try {
            String query = "SELECT persona_id " +
                    " FROM persones " +
                    "WHERE nom = ? AND cog1 = ? AND cog2 = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, nom);
            preparedStmt.setString(2, cognom1);
            preparedStmt.setString(3, cognom2);

            ResultSet rs = preparedStmt.executeQuery();
            rs.next();
            persona = rs.getInt("persona_id");

            return persona;

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int selectProvincia(String ine, Connection con) {
        int provincia = 0;
        try {
            String query = "SELECT provincia_id " +
                    " FROM provincies " +
                    "WHERE codi_ine = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, ine);

            ResultSet rs = preparedStmt.executeQuery();
            rs.next();
            provincia = rs.getInt("provincia_id");
            return provincia;

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

}

