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
import java.awt.Toolkit;
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
public class RelojNuevoScanner extends javax.swing.JFrame {

    int marca;
    int id;
    String hora, minutos, segundos, ampm;
    Calendar calendario;
    Thread h1;
    int escribe;

    /**
     * Creates new form NewJFrame
     */
    public RelojNuevoScanner() {
        initComponents();
        HoraActual();
      this.setExtendedState(MAXIMIZED_BOTH);
        Dimension pantallaTamano = Toolkit.getDefaultToolkit().getScreenSize();
       //this.setLocation((pantallaTamano.width/2)-(this.getWidth()/2), (pantallaTamano.height/2)-(this.getHeight()/2));
       alinear();
        //PCodigo.setSize(900,500);
      //  PCodigo.setLocation((pantallaTamano.width/2)-(this.getWidth()/2), (pantallaTamano.height/2)-(this.getHeight()/2));
    }

    Timer timer = new Timer(500, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            semaforo.setBackground(Color.white);
            PCodigo.setBackground(Color.white);
            codigo.setText("");
            Mensaje.setText("");
        }
    });

    Timer timer2 = new Timer(60000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            marca = 0;
            codigo.setText("");
            //System.out.println("QUITA LA MARCA");

        }
    });

    /* Timer timerEscribir = new Timer(2000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            codigo.setText("");
            System.out.println("TERMINA");
        }
    });*/
    

    
    public void alinear() {
        
      codigo.requestFocus();
       /* 
        Dimension desktopSize = semaforo.getSize();
        Dimension FrameSize = jPanel1.getSize();
        jPanel1.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        jPanel1.show();*/
    }

    public void HoraActual() {
        HoraActual ma = new HoraActual();
        semaforo.add(ma);
        //Dimension desktopSize = semaforo.getSize();
        //Dimension FrameSize = ma.getSize();
        ma.setLocation(25, 25);
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
            g.setCodigo(Integer.parseInt(codigo.getText().substring(1, 5)));
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
            g.setCodigo(Integer.parseInt(codigo.getText().substring(1, 5)));
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
            ResultSet rs = stmt.executeQuery("select COUNT(codigo) as codigo, sum(id_reloj) as id_reloj from reloj where codigo= " + codigo.getText().substring(1, 5) + " and estado = 1 and to_date(fecha,'dd/mm/yy') = to_date(sysdate,'dd/mm/yy')");
            rs.next();
            int c = rs.getInt("codigo");
            id = rs.getInt("id_reloj");
            if (c == 0) {
                guardarINGRESO();
                semaforo.setBackground(Color.green);
                PCodigo.setBackground(Color.green);
                Mensaje.setText("ENTRADA");alinear();
            } else if (marca != Integer.parseInt(codigo.getText().substring(1, 5))) {
                guardarSALIDA();
                semaforo.setBackground(Color.yellow);
                PCodigo.setBackground(Color.yellow);
                Mensaje.setText("SALIDA");alinear();
                //Thread.sleep(4000);
                //semaforo.setBackground(Color.WHITE);
            } else {
                semaforo.setBackground(Color.red);
                PCodigo.setBackground(Color.red);
                Mensaje.setText("YA MARCADO");alinear();
                timer.setRepeats(false);
                timer.start();
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelojNuevoScanner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void marcar() {

        Calendar calendario = new GregorianCalendar();
        int hora;
        hora = calendario.get(Calendar.HOUR_OF_DAY);

        String s = codigo.getText();
        char firstCharacter = s.charAt(0);

        try {

            if (firstCharacter == '%') {
                if (hora == 5 && marca != Integer.parseInt(codigo.getText().substring(1, 5))) {
                    guardarINGRESO();
                    semaforo.setBackground(Color.green);
                    PCodigo.setBackground(Color.green);
                    System.out.println("SIN VERIFICAR EN BASE DE DATOS");
                } else {
                    validarExistencia();
                    System.out.println("CON VERIFICAR EN BASE DE DATOS");

                }
                marca = Integer.parseInt(codigo.getText().substring(1, 5));
                timer2.setRepeats(false);
                timer2.start();
                codigo.setText("");

            } else {
                System.out.println("NO");
                Mensaje.setText("ERROR");
                semaforo.setBackground(Color.red);
                PCodigo.setBackground(Color.red);
                codigo.setText("");
                timer.setRepeats(false);
                timer.start();
                codigo.setText("");
                
            }
        } catch (Exception e) {
            semaforo.setBackground(Color.red);
            PCodigo.setBackground(Color.red);
            codigo.setText("");
            timer.setRepeats(false);
            timer.start();
            codigo.setText("");
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

        semaforo = new javax.swing.JPanel();
        PCodigo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        Mensaje = new javax.swing.JLabel();

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

        semaforo.setBackground(new java.awt.Color(255, 255, 255));
        semaforo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        semaforo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                semaforoMouseClicked(evt);
            }
        });

        PCodigo.setBackground(new java.awt.Color(255, 255, 255));
        PCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PCodigoMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Codigo");

        codigo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        codigo.setAutoscrolls(false);
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
        });

        Mensaje.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        Mensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Mensaje.setAutoscrolls(true);

        javax.swing.GroupLayout PCodigoLayout = new javax.swing.GroupLayout(PCodigo);
        PCodigo.setLayout(PCodigoLayout);
        PCodigoLayout.setHorizontalGroup(
            PCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PCodigoLayout.createSequentialGroup()
                .addGroup(PCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PCodigoLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel1))
                    .addGroup(PCodigoLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PCodigoLayout.createSequentialGroup()
                .addComponent(Mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PCodigoLayout.setVerticalGroup(
            PCodigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PCodigoLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(Mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout semaforoLayout = new javax.swing.GroupLayout(semaforo);
        semaforo.setLayout(semaforoLayout);
        semaforoLayout.setHorizontalGroup(
            semaforoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(semaforoLayout.createSequentialGroup()
                .addGap(719, 719, 719)
                .addComponent(PCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        semaforoLayout.setVerticalGroup(
            semaforoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(semaforoLayout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(PCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );

        PCodigo.getAccessibleContext().setAccessibleParent(this);

        getContentPane().add(semaforo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
        marcar();
    }//GEN-LAST:event_codigoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        alinear();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        /*Dimension desktopSize = semaforo.getSize();
        Dimension FrameSize = PCodigo.getSize();
        PCodigo.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        PCodigo.show();*/
    }//GEN-LAST:event_formWindowGainedFocus

    private void semaforoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_semaforoMouseClicked
        alinear();
    }//GEN-LAST:event_semaforoMouseClicked

    private void PCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PCodigoMouseClicked
        alinear();
    }//GEN-LAST:event_PCodigoMouseClicked

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

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        if (codigo.getText().compareTo("") != 0) {
            String a = codigo.getText();
            char first = a.charAt(0);
            if (first != '%') {
                codigo.setText("");
            }
        }
    }//GEN-LAST:event_codigoKeyReleased

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
            java.util.logging.Logger.getLogger(RelojNuevoScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RelojNuevoScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RelojNuevoScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RelojNuevoScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new RelojNuevoScanner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Mensaje;
    private javax.swing.JPanel PCodigo;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel semaforo;
    // End of variables declaration//GEN-END:variables

}
