
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aparracorbacho
 */
public class MySqlC {
    static String bd = "proyecto_prom";
    static String login = "proyecto";
    static String password = "proyecto";
    static String url = "jdbc:mysql://51.254.137.26/"+bd;
    Connection conn = null;
    Statement stmt = null;
    
 /**
 * Con el metodo conn definimos la conexion a la base de datos
 */
   
    public void conn(){
        try {
            conn = (Connection) DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 /**
 * Con el metodo close cerramos la conexion a la base de datos
 */
    public void close(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 /**
 * Con el metodo consulta devuelves un ResulSet de la consulta que has ejecutado
 * @param sql String que corresponde a la consulta que queremos hacer
 * @return Devuelve un valor ResultSet con la consulta
 */
    public ResultSet consulta(String sql){
        ResultSet rs = null;
        try {
            stmt = (Statement) conn.createStatement();
            String sqlC = sql;
            rs = stmt.executeQuery(sqlC);
            } catch (SQLException ex) {
            Logger.getLogger(MySqlC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
 /**
 * Con el metodo accion puedes hacer tanto un insert, delete o uptade ya que la consulta la envias por el String
 * @param sql String que corresponde a la accion que queremos realizar
 */    

    public void accion(String sql){
        try {
            stmt = (Statement) conn.createStatement();
            String sqlC = sql;
            stmt.executeUpdate(sqlC); 
        } catch (SQLException ex) {
            Logger.getLogger(MySqlC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  /**
 * Con el metodo compruebaUser comprobamos si el usuario esta dentro de la base de datos
 * @param usuario Nombre de usuario que queremos comprobar
 * @param pass Password del usuario que queremos comprobar
 */ 
    public int compruebaUser(String usuario, String pass){
        int autenticado;
        String pruebapass = null;
         try {
             ResultSet rs = null;
             rs = consulta("Select * from usuarios where usuario = '"+usuario+"'");
              while(rs.next()){
              pruebapass = rs.getString("pass");         
              }
                                       
         } catch (SQLException ex) {
             Logger.getLogger(MySqlC.class.getName()).log(Level.SEVERE, null, ex);
         }
         if (pruebapass.equals(pass)) { autenticado = 1; } else { autenticado = 0; }     
         return autenticado;
    }
}
