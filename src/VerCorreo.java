
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aparracorbacho
 */
public class VerCorreo extends javax.swing.JFrame {

    String enviadop, asunto, texto, hora, fecha, archivos, narchivos;
    String usuario;
    int id;
    MySqlC mysql = new MySqlC();
    static String[] archivosAdjuntos;
    static String nombrearchivo;
        

    /**
     * Creates new form VerCorreo
     */
    public VerCorreo() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(VerCorreo.DISPOSE_ON_CLOSE);
        mysql.conn();
    }

    public void setValores(int id) {
        this.id = id;
        try {
            String sqlcargar = "Select * from correos where id = '" + id + "'";
            ResultSet rs = mysql.consulta(sqlcargar);
            while (rs.next()) {
                enviadop = rs.getString(2);
                asunto = rs.getString(4);
                texto = rs.getString(5);
                fecha = rs.getString(6);
                hora = rs.getString(7);
                narchivos = rs.getString(9);
            }
            enviadoLabel.setText(enviadop);
            asuntoLabel.setText(asunto);
            contenidoField.setText(texto);
            fechaField.setText(fecha);
            horaField.setText(hora);
            contenidoField.setEditable(false);
            if ((narchivos.trim().length() == 0) || ("null".equals(narchivos))) {
                adjuntosField.setText("No hay ningun adjunto");
            } else {
                archivosAdjuntos = narchivos.split("#");
                int carchivos = 0;
                int altura = 350;
                for (int i = 0; i < archivosAdjuntos.length; i++) {
                    if (carchivos == 0) {
                        adjuntosField.setText(archivosAdjuntos[i]);
                    } else {
                        adjuntosField.setText(adjuntosField.getText() + "<br>" + archivosAdjuntos[i]);
                    }
                    carchivos++;
                    nombrearchivo = archivosAdjuntos[i];                                                              
                    
                    JButton boton = new JButton();
                    
                    i = i + 1;
                    boton.setName(String.valueOf(i));
                    boton.setText("Abrir adjunto " + i);
                    boton.setBounds(650, altura, 120, 25);
                    altura = 350 + 22 * i;
                    i = i - 1;
                    
                    boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           
                     
                            try {
                                JButton presionado = (JButton)e.getSource();
                                for (int i = 0; i < archivosAdjuntos.length; i++) { if (i == (Integer.parseInt(presionado.getName())-1)) {nombrearchivo = archivosAdjuntos[i];}}
                                String server = "51.254.137.26";
                                String username = "proyecto";
                                String password = "proyecto";
                                FileOutputStream darchivo;
                                FTPClient ftp = new FTPClient();
                                ftp.connect(server);
                                ftp.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE); 
                                ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                                if (!ftp.login(username, password)) {
                                    ftp.logout();
                                }
                                int reply = ftp.getReplyCode();
                                if (!FTPReply.isPositiveCompletion(reply)) {
                                    ftp.disconnect();
                                }

                                darchivo = new FileOutputStream(nombrearchivo);
                                ftp.retrieveFile(nombrearchivo, darchivo);
                                darchivo.close();                               
                                JOptionPane.showMessageDialog(null, "Archivo descargado correctamente, pulsa aceptar para abrirlo", "Descargado!", JOptionPane.INFORMATION_MESSAGE);
                                Runtime.getRuntime().exec("cmd /c start "+nombrearchivo);
                                
                                ftp.logout();
                                ftp.disconnect();
                               } catch (IOException ex) {
                                Logger.getLogger(VerCorreo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                    });
                  add(boton);  
                }
                 adjuntosField.setText("<html><body>" + adjuntosField.getText() + "</html></body>");
            }

        } catch (SQLException ex) {
            Logger.getLogger(VerCorreo.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        enviadoLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        asuntoLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        salirdecorreo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        contenidoField = new javax.swing.JTextArea();
        Responder = new javax.swing.JButton();
        exportar = new javax.swing.JButton();
        borrarcorreo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fechaField = new javax.swing.JLabel();
        horaField = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        adjuntosField = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ver Correo - Correo");

        jLabel1.setText("Enviado por:");

        enviadoLabel.setText("Enviado");

        jLabel2.setText("Asunto:");

        asuntoLabel.setText("Asunto");

        jLabel3.setText("Contenido:");

        salirdecorreo.setText("Salir de este correo");
        salirdecorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirdecorreoActionPerformed(evt);
            }
        });

        contenidoField.setColumns(20);
        contenidoField.setRows(5);
        jScrollPane1.setViewportView(contenidoField);

        Responder.setText("Responder");
        Responder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResponderActionPerformed(evt);
            }
        });

        exportar.setText("Exportar Correo");
        exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarActionPerformed(evt);
            }
        });

        borrarcorreo.setText("Borrar");
        borrarcorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarcorreoActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha:");

        jLabel5.setText("Hora:");

        fechaField.setText("fecha");

        horaField.setText("hora");

        jLabel6.setText("Archivos adjuntos:");

        adjuntosField.setText("adjuntos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(exportar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                        .addComponent(salirdecorreo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2))
                            .addComponent(jLabel6))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(asuntoLabel)
                                            .addComponent(enviadoLabel))
                                        .addGap(157, 157, 157)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(horaField)
                                            .addComponent(fechaField)))
                                    .addComponent(adjuntosField))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Responder)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(borrarcorreo)))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(enviadoLabel)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fechaField))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(asuntoLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(horaField)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(Responder)
                        .addGap(29, 29, 29)
                        .addComponent(borrarcorreo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(adjuntosField))
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportar)
                    .addComponent(salirdecorreo))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirdecorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirdecorreoActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_salirdecorreoActionPerformed

    private void ResponderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResponderActionPerformed
        // TODO add your handling code here:
        EscribirCorreo escribir = new EscribirCorreo();
        escribir.setValores(usuario, enviadop, asunto, texto);
        escribir.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ResponderActionPerformed

    private void exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarActionPerformed
        // Codigo para exportar correo
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String correoexportado = asunto.replace(" ", "").replace(":", "_") + ".txt";
            fichero = new FileWriter(correoexportado);
            String fileLocal = new String(correoexportado);
            ResultSet rs = null;
            rs = mysql.consulta("Select * from correos where id = '" + id + "' ");
            while (rs.next()) {
                pw = new PrintWriter(fichero);
                pw.println("#Titulo: " + rs.getString(4));
                pw.println("#Enviado por: " + rs.getString(2));
                pw.println("#Fecha y hora: " + rs.getString(6) + " " + rs.getString(7));
                pw.println("#Contenido:\n" + rs.getString(5));
                pw.println("#######################################################\n");
            }
            JOptionPane.showMessageDialog(null, "Fichero exportado correctamente, acepta para abrir", "Exportacion correcta", JOptionPane.PLAIN_MESSAGE);
            Runtime.getRuntime().exec("cmd /c start " + fileLocal);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_exportarActionPerformed

    private void borrarcorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarcorreoActionPerformed
        // Borrar correo
        int resp = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar el correo");
        if (JOptionPane.OK_OPTION == resp) {
            mysql.accion("delete from correos where id = '" + id + "' ");
            JOptionPane.showMessageDialog(null, "Correo borrado con exito", "Correo borrado", JOptionPane.INFORMATION_MESSAGE);
            mysql.close();
            this.dispose();

        }
    }//GEN-LAST:event_borrarcorreoActionPerformed

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
            java.util.logging.Logger.getLogger(VerCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerCorreo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Responder;
    private javax.swing.JLabel adjuntosField;
    private javax.swing.JLabel asuntoLabel;
    private javax.swing.JButton borrarcorreo;
    private javax.swing.JTextArea contenidoField;
    private javax.swing.JLabel enviadoLabel;
    private javax.swing.JButton exportar;
    private javax.swing.JLabel fechaField;
    private javax.swing.JLabel horaField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton salirdecorreo;
    // End of variables declaration//GEN-END:variables

}
