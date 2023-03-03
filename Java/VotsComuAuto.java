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


public class VotsComuAuto {


    public static void VotsCA() throws Exception {

        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.214.178:3306/eleccions2019", "perepi", "pastanaga");
        BufferedReader bfLector = null;
        try {
            Path pathActual = Paths.get(System.getProperty("user.dir"));
            Path pathFitxer = Paths.get(pathActual.toString(), "src/datos", "08021911.DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {

            int votsTotals = 0;

            int vots = Integer.parseInt(strLinia.substring(20, 28));
            int candidatura_id = selectCandidatura(strLinia.substring(14, 20).trim(), con);
            int comunitat_aut_id = Integer.parseInt(strLinia.substring(9, 11).trim());

            //Preparem el Date
                if (!(comunitat_aut_id==99)) {
                    try {

                        // the mysql insert statement
                        String query = " INSERT INTO vots_candidatures_ca (comunitat_aut_id, candidatura_id, vots)"
                                + " values (?, ?, ?)";

                        // create the mysql insert preparedstatement
                        PreparedStatement preparedStmt = con.prepareStatement(query);
                        preparedStmt.setInt(1, comunitat_aut_id);
                        preparedStmt.setInt(2, candidatura_id);
                        preparedStmt.setInt(3, vots);


                        // execute the preparedstatement
                        preparedStmt.execute();

                        //Tanquem la connexió

                    } catch (Exception e) {
                        System.out.println(e);
                    }
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

    public static int selectCandidatura(String ine, Connection con) {
        int candidatura = 0;
        try {
            String query = "SELECT candidatura_id " +
                    " FROM candidatures " +
                    "WHERE codi_candidatura = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, ine);

            ResultSet rs = preparedStmt.executeQuery();
            rs.next();
            candidatura = rs.getInt("candidatura_id");
            return candidatura;

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}