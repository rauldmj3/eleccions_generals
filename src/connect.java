import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class connect {

public static void main(String[] args) throws Exception{

        try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection con=DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/rrhh","perepi","pastanaga");


                //Preparem el Date
                Calendar calendar = Calendar.getInstance();
                java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

                // the mysql insert statement
                String query = " INSERT INTO empleats (empleat_id,nom,cognoms,email,telefon,data_contractacio,feina_codi,salari)"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt    (1, 300);
                preparedStmt.setString (2, "Pere");
                preparedStmt.setString (3, "Pi");
                preparedStmt.setString (4, "perepi@sapalomera.cat");
                preparedStmt.setString (5, "972350909");
                preparedStmt.setDate   (6, startDate);
                preparedStmt.setString (7, "IT_PROG");
                preparedStmt.setFloat  (8, 5000.12f);

                // execute the preparedstatement
                preparedStmt.execute();

                //Tanquem la connexió
                con.close();
        }
        catch(Exception e){
                System.out.println(e);
        }
}
        public static void conectarBD(){
                try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        Connection con=DriverManager.getConnection("jdbc:mysql://10.2.59.145:3306/rrhh","perepi","pastanaga");
                }catch(Exception e){
                        System.out.println(e);
                }
        }
}
