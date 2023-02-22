import java.sql.Connection;
import java.sql.Date;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Scanner;

public class IntroCandidats {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");

        do {
            String linea = scan.nextLine();
            String codi_candidatura = linea.substring(15, 21).trim();
            String num_ordre = linea.substring(21, 24).trim();
            String nom = linea.substring(25, 50).trim();
            String cog1 = linea.substring(50, 75).trim();
            String cog2 = linea.substring(75, 100).trim();
            String tipus = linea.substring(24, 25).trim();
            String ine = linea.substring(9, 11).trim();
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
        } while (scan.hasNext());
        con.close();

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

