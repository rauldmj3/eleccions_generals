import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Scanner;

public class IntroPersones {

        static Scanner scan = new Scanner(System.in);
public static void main(String[] args) throws Exception{
        int i=1;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");

        do {
                String linea = scan.nextLine();
                String nom=linea.substring(25,50).trim();
                String cog1=linea.substring(50,75).trim();
                String cog2=linea.substring(75,100).trim();
                String sexe=linea.substring(100,101).trim();
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
                        if(i%500==0){
                                con.close();
                                con = DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/eleccions2019", "perepi", "pastanaga");
                        }

                } catch (Exception e) {
                        System.out.println(e);
                }
                i++;
        }while(scan.hasNext());
        con.close();
        }
}
