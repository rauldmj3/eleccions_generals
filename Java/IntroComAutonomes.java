import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class IntroComAutonomes {


    public static void comAutonomes() throws Exception {
        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");
        BufferedReader bfLector = null;
        try {
            Path pathActual = Paths.get(System.getProperty("user.dir"));
            Path pathFitxer = Paths.get(pathActual.toString(), "src/datos", "07021911.DAT");
            bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
            String strLinia;
            while ((strLinia = bfLector.readLine()) != null) {
                String codi = strLinia.substring(11, 13).trim();
                String codi_ine = strLinia.substring(9, 11).trim();
                int codi_aut_id = Integer.parseInt(strLinia.substring(9, 11).trim());
                String nom = strLinia.substring(14, 64).trim();
                if (codi.equals("99") && codi_aut_id != 99) {
                    try {
                        // the mysql insert statement
                        String query = " INSERT INTO comunitats_autonomes (comunitat_aut_id,nom,codi_ine)"
                                + " values (?,?,?)";

                        PreparedStatement preparedStmt = con.prepareStatement(query);
                        preparedStmt.setInt(1, codi_aut_id);
                        preparedStmt.setString(2, nom);
                        preparedStmt.setString(3, codi_ine);

                        preparedStmt.execute();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    i++;
                }
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

