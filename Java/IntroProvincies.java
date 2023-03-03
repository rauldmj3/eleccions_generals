import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class IntroProvincies {

    public static void provincies() throws Exception {
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
                String codi_ine = strLinia.substring(11, 13).trim();
                int comunitat_aut_id = Integer.parseInt(strLinia.substring(9, 11).trim());
                String nom = strLinia.substring(14, 64).trim();
                int num_escons = Integer.parseInt(strLinia.substring(149, 155).trim());
                if (!codi_ine.equals("99") && comunitat_aut_id != 99) {
                    try {
                        // the mysql insert statement
                        String query = " INSERT INTO provincies (provincia_id,comunitat_aut_id,nom,codi_ine,num_escons)"
                                + " values (?,?,?,?,?)";


                        // create the mysql insert preparedstatement
                        PreparedStatement preparedStmt = con.prepareStatement(query);
                        preparedStmt.setInt(1, i);
                        preparedStmt.setInt(2, comunitat_aut_id);
                        preparedStmt.setString(3, nom);
                        preparedStmt.setString(4, codi_ine);
                        preparedStmt.setInt(5, num_escons);

                        // execute the preparedstatement
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

