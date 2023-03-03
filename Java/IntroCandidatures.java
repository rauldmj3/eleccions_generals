import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class IntroCandidatures {

    public static void candidatures() throws Exception {
        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");
        BufferedReader bfLector = null;
        try {
            Path pathActual = Paths.get(System.getProperty("user.dir"));
            Path pathFitxer = Paths.get(pathActual.toString(), "src/datos", "03021911.DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                String eleccio = strLinia.substring(0, 1).trim();
                String codi_ca = strLinia.substring(8, 14).trim();
                String nom_curt = strLinia.substring(14, 64).trim();
                String nom_llarg = strLinia.substring(64, 214).trim();
                String codi_pro = strLinia.substring(214, 220).trim();
                String codi_auto = strLinia.substring(220, 226).trim();
                String codi_nac = strLinia.substring(226, 232).trim();
                try {


                    // the mysql insert statement
                    String query = " INSERT INTO candidatures (candidatura_id, eleccio_id, codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional)"
                            + " values (?, ?, ?, ?, ?, ?, ?,?)";


                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setInt(1, i);
                    preparedStmt.setString(2, "1");
                    preparedStmt.setString(3, codi_ca);
                    preparedStmt.setString(4, nom_curt);
                    preparedStmt.setString(5, nom_llarg);
                    preparedStmt.setString(6, codi_pro);
                    preparedStmt.setString(7, codi_auto);
                    preparedStmt.setString(8, codi_nac);

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
}
