import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Scanner;

public class IntroProvincies {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");

        do {
            String linea = scan.nextLine();
            String codi_ine = linea.substring(11, 13).trim();
            int comunitat_aut_id = Integer.parseInt(linea.substring(9, 11).trim());
            String nom = linea.substring(14, 64).trim();
            int num_escons = Integer.parseInt(linea.substring(149,155).trim());
            if (!codi_ine.equals("99")&&comunitat_aut_id!=99) {
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
        while (scan.hasNext());
        con.close();

    }
}
