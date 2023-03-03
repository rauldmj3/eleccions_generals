import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class IntroPersones {

    public static void persones() throws Exception {
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
                String nom = strLinia.substring(25, 50).trim();
                String cog1 = strLinia.substring(50, 75).trim();
                String cog2 = strLinia.substring(75, 100).trim();
                String sexe = strLinia.substring(100, 101).trim();
                try {


                    // the mysql insert statement
                    String query = " INSERT INTO persones (persona_id,nom,cog1,cog2,sexe,data_naixement,dni)"
                            + " values (?,?,?,?,?,?,?)";


                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setInt(1, i);
                    preparedStmt.setString(2, nom);
                    preparedStmt.setString(3, cog1);
                    preparedStmt.setString(4, cog2);
                    preparedStmt.setString(5, sexe);
                    preparedStmt.setString(6, null);
                    preparedStmt.setString(7, null);


                    // execute the preparedstatement
                    preparedStmt.execute();
                    if (i % 500 == 0) {
                        con.close();
                        con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");
                    }
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
