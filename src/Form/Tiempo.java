/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jluis
 */
public class Tiempo implements Runnable{
    String etiquetaReloj;
    
    public void reloj() {
        Calendar calendario = Calendar.getInstance();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("HH:mm");
        
        Runnable runnable = new Runnable() {
            @Override
            public void run() { 
                while (true) {
                    try {
                        Thread.sleep(500); 
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Tiempo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    etiquetaReloj = (formateador.format(LocalDateTime.now()));
                    
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
