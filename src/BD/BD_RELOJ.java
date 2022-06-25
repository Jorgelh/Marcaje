/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import Form.ClassReloj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author jluis
 */
public class BD_RELOJ {
    
    
    
    
     public static void IngresoDatosRelojin(ClassReloj c) throws SQLException {
        
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into RELOJ(ID_RELOJ,CODIGO,FECHA,INGRESO,ESTADO) VALUES(ID_RELOJ.NEXTVAL,?,SYSDATE,SYSDATE,1)");
        ps.setInt(1, c.getCodigo());
        ps.execute();
        cnn.close();
        ps.close();
    }  
    
    
    
    public static void IngresoDatosRelojout(ClassReloj c) throws SQLException {
        
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("UPDATE RELOJ SET SALIDA = SYSDATE,ESTADO = 2 WHERE CODIGO = ? AND ESTADO = 1");
        ps.setInt(1, c.getCodigo());
        ps.execute();
        cnn.close();
        ps.close();
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
