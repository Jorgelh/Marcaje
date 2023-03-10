/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import BD.BD;
import BD.BD_RELOJ;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author jluis
 */
public class Relojv21 extends javax.swing.JFrame{

    int marca;
    int id;
    String hora,minutos,segundos,ampm;
    Calendar calendario;    
    Thread h1;
    
    /**
     * Creates new form NewJFrame
     */
    public Relojv21() {
        initComponents();
        HoraActual();
        this.setExtendedState(MAXIMIZED_BOTH);
        alinear();
      
    }
    
    Timer timer = new Timer(500, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            semaforo.setBackground(Color.white);
            jPanel1.setBackground(Color.white);
            codigo.setText("");
        }
    });

    Timer timer2 = new Timer(60000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            marca = 0;
            //System.out.println("QUITA LA MARCA");

        }
    });

    
    
    public  void alinear () {
    
        codigo.requestFocus();
        Dimension desktopSize = semaforo.getSize();
        Dimension FrameSize = jPanel1.getSize();
        jPanel1.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        jPanel1.show();
    
    }
    
    
    public void HoraActual(){
        HoraActual ma = new HoraActual();
        semaforo.add(ma); 
        //Dimension desktopSize = semaforo.getSize();
        //Dimension FrameSize = ma.getSize();
        ma.setLocation( 25, 25);
        ma.show();
        alinear();
    
    }

    public void AgregarHoras() {

        try {
            Connection c = BD.getConnection();
            Statement ps = c.createStatement();
            ps.executeUpdate("BEGIN actualizarHoras(IDReloj=>" + id + "); COMMIT; END;");
            c.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
    }

    private void guardarINGRESO() {

        try {

            ClassReloj g = new ClassReloj();
            g.setCodigo(Integer.parseInt(codigo.getText()));
            BD_RELOJ.IngresoDatosRelojin(g);
            timer.setRepeats(false);
            timer.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR DE INSERTAR" + e);
        }

    }

    private void guardarSALIDA() {

        try {

            ClassReloj g = new ClassReloj();
            g.setCodigo(Integer.parseInt(codigo.getText()));
            BD_RELOJ.IngresoDatosRelojout(g);
            AgregarHoras();
            timer.setRepeats(false);
            timer.start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR DE INSERTAR" + e);
        }
    }

    private void validarExistencia() {

        try {
            Connection con = BD.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select COUNT(codigo) as codigo, sum(id_reloj) as id_reloj from reloj where codigo= " + codigo.getText() + " and estado = 1 and to_date(fecha,'dd/mm/yy') = to_date(sysdate,'dd/mm/yy')");
            rs.next();
            int c = rs.getInt("codigo");
            id = rs.getInt("id_reloj");
            if (c == 0) {
                guardarINGRESO();
                semaforo.setBackground(Color.green);
                jPanel1.setBackground(Color.green);
            } else if (marca != Integer.parseInt(codigo.getText())) {
                guardarSALIDA();
                semaforo.setBackground(Color.yellow);
                jPanel1.setBackground(Color.yellow);
                //Thread.sleep(4000);
                //semaforo.setBackground(Color.WHITE);
            } else {
                semaforo.setBackground(Color.red);
                jPanel1.setBackground(Color.red);
                timer.setRepeats(false);
                timer.start();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Relojv21.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        semaforo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA DE MARCAJE ELETTRONICI, S.A.");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        semaforo.setBackground(new java.awt.Color(255, 255, 255));
        semaforo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                semaforoMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Codigo");

        codigo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoFocusLost(evt);
            }
        });
        codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoActionPerformed(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codigoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout semaforoLayout = new javax.swing.GroupLayout(semaforo);
        semaforo.setLayout(semaforoLayout);
        semaforoLayout.setHorizontalGroup(
            semaforoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(semaforoLayout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(305, Short.MAX_VALUE))
        );
        semaforoLayout.setVerticalGroup(
            semaforoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, semaforoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
        );

        jDesktopPane1.setLayer(semaforo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(semaforo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(semaforo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jDesktopPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyTyped

    }//GEN-LAST:event_codigoKeyTyped

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        Calendar calendario = new GregorianCalendar();
        int hora;
        hora = calendario.get(Calendar.HOUR_OF_DAY);

        String Caracteres = codigo.getText();
        if (Caracteres.length() == 4) {
            evt.consume();
            if (hora == 5 && marca != Integer.parseInt(codigo.getText())) {
                guardarINGRESO();
                semaforo.setBackground(Color.green);
                jPanel1.setBackground(Color.green);
                System.out.println("SIN VERIFICAR EN BASE DE DATOS");
            } else {
                validarExistencia();
                System.out.println("CON VERIFICAR EN BASE DE DATOS");
            }
            marca = Integer.parseInt(codigo.getText());
            timer2.setRepeats(false);
            timer2.start();
            codigo.setText("");
        } else if (Caracteres.length() > 4) {
            codigo.setText("");
            semaforo.setBackground(Color.red);
            jPanel1.setBackground(Color.red);
        }
    }//GEN-LAST:event_codigoKeyReleased

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed

    }//GEN-LAST:event_codigoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
           alinear();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        Dimension desktopSize = semaforo.getSize();     
        Dimension FrameSize = jPanel1.getSize();
        jPanel1.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        jPanel1.show();
    }//GEN-LAST:event_formWindowGainedFocus

    private void semaforoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_semaforoMouseClicked
       alinear();
    }//GEN-LAST:event_semaforoMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
    alinear();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
    alinear();
    }//GEN-LAST:event_formMouseClicked

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
    alinear();
    }//GEN-LAST:event_codigoFocusLost

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
    alinear();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
    alinear();
    }//GEN-LAST:event_formWindowStateChanged

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    alinear();
    }//GEN-LAST:event_formComponentResized

    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Relojv21.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relojv21.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relojv21.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relojv21.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Relojv21().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codigo;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel semaforo;
    // End of variables declaration//GEN-END:variables

}
