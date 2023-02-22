import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Scanner;

public class IntroCandidatures {

        static Scanner scan = new Scanner(System.in);
public static void main(String[] args) throws Exception{
        int i=1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");

        do {
                String linea = scan.nextLine();
                String eleccio=linea.substring(0,1).trim();
                String codi_ca=linea.substring(8,14).trim();
                String nom_curt=linea.substring(14,64).trim();
                String nom_llarg=linea.substring(64,214).trim();
                String codi_pro=linea.substring(214,220).trim();
                String codi_auto=linea.substring(220,226).trim();
                String codi_nac=linea.substring(226,232).trim();
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
        }while(scan.hasNext());
        con.close();
}}
