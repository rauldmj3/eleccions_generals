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


public class VotsMunicipi{


    public static void VotsM() throws Exception {

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
                int votsTotals = 0;


                int num_meses = Integer.parseInt(strLinia.substring(8, 9));
                int cens = Integer.parseInt(strLinia.substring(149, 157));
                int votsPrimeraVolta = Integer.parseInt(strLinia.substring(173, 181));
                int votsSegonaVolta = Integer.parseInt(strLinia.substring(181, 189));
                votsTotals = votsPrimeraVolta + votsSegonaVolta;
                int votsValids = Integer.parseInt(strLinia.substring(216, 224));
                int votsCandidatura = Integer.parseInt(strLinia.substring(205, 213));
                int votsBlancs = Integer.parseInt(strLinia.substring(189, 197));
                int votsNuls = Integer.parseInt(strLinia.substring(197, 205));
                int municipi_id = selectMunicipi(strLinia.substring(18, 118).trim(), con);

                //Preparem el Date

                try {

                    // the mysql insert statement
                    String query = " INSERT INTO eleccions_municipis (eleccio_id, municipi_id, num_meses, cens, vots_emesos, vots_valis, vots_candidatures, vots_blancs, vots_nuls)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setInt(1, 1);
                    preparedStmt.setInt(2, municipi_id);
                    preparedStmt.setInt(3, num_meses);
                    preparedStmt.setInt(4, cens);
                    preparedStmt.setInt(5, votsTotals);
                    preparedStmt.setInt(6, votsValids);
                    preparedStmt.setInt(7, votsCandidatura);
                    preparedStmt.setInt(8, votsBlancs);
                    preparedStmt.setInt(9, votsNuls);

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
