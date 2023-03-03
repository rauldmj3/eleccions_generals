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


public class VotsCandidaturesMunicipis{


    public static void VotsM() throws Exception {

        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.50.16:3306/eleccions2019", "perepi", "pastanaga");
        BufferedReader bfLector = null;
        try {
            Path pathActual = Paths.get(System.getProperty("user.dir"));
            Path pathFitxer = Paths.get(pathActual.toString(), "src/datos", "06021911.DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {

                int eleccio_id = Integer.parseInt(strLinia.substring(8, 9)); //esto no se de donde lo saco xd
                int municipi_id = Integer.parseInt(strLinia.substring(12, 14));
                int candidatura_id = Integer.parseInt(strLinia.substring(17,22));
                int vots = Integer.parseInt(strLinia.substring(23, 30));

                //Preparem el Date

                try {

                    // the mysql insert statement
                    String query = " INSERT INTO eleccions_municipis (eleccio_id, municipi_id, num_meses, cens, vots_emesos, vots_valis, vots_candidatures, vots_blancs, vots_nuls)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    //esto no se demasiado q hace
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setInt(1, eleccio_id);
                    preparedStmt.setInt(2, municipi_id);
                    preparedStmt.setInt(3, candidatura_id);
                    preparedStmt.setInt(4, vots);


                    // execute the preparedstatement
                    preparedStmt.execute();

                    //Tanquem la connexió

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
    public static int selectProvincia (String ine, Connection con){
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
    public static int selectMunicipi (String nom, Connection con){
        int municipi = 0;
        try {
            String query = "SELECT municipi_id " +
                    " FROM municipis " +
                    "WHERE nom = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1, nom);

            ResultSet rs = preparedStmt.executeQuery();
            rs.next();
            municipi = rs.getInt("municipi_id");
            return municipi;

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}
