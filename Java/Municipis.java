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


public class Municipis {


    public static void municipi() throws Exception {

        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.214.178:3306/eleccions2019", "perepi", "pastanaga");
        BufferedReader bfLector = null;
        try {
            Path pathActual = Paths.get(System.getProperty("user.dir"));
            Path pathFitxer = Paths.get(pathActual.toString(), "src/datos", "05021911.DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {

                String lineaNom = strLinia.substring(18,118);
                String nom = lineaNom.trim();
                String codi_ine = strLinia.substring(13, 16);
                String districte = strLinia.substring(16, 18);
                int provincia_id = selectProvincia(strLinia.substring(11,13).trim(), con);

                //Preparem el Date

                try {

                // the mysql insert statement
                String query = " INSERT INTO municipis (municipi_id, nom, codi_ine, provincia_id, districte)"
                        + " values (?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, i);
                preparedStmt.setString(2, nom);
                preparedStmt.setString(3, codi_ine);
                preparedStmt.setInt(4, provincia_id);
                preparedStmt.setString(5, districte);


                // execute the preparedstatement
                preparedStmt.execute();

                //Tanquem la connexió

            } catch(Exception e){
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

    /**
     * Aquest SELECT agafa la provincia_id de la taula provincies per importarlo a la taula municipis.
     * @param ine
     * @param con
     * @return
     */
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
