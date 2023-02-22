import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Scanner;

public class IntroComAutonomes {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int i = 1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");

        do {
            String linea = scan.nextLine();
            String codi = linea.substring(11, 13).trim();
            String codi_ine=linea.substring(9,11).trim();
            int codi_aut_id = Integer.parseInt(linea.substring(9,11).trim());
            String nom = linea.substring(14, 64).trim();
            if (codi.equals("99")&& codi_aut_id!=99) {
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
        while (scan.hasNext());
        con.close();

    }
}

