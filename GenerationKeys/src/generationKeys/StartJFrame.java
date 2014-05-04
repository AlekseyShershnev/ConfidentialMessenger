package generationKeys;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Shershnev
 */
public class StartJFrame extends javax.swing.JFrame {

    public StartJFrame() {
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (screenSize.width - this.getWidth()) / 2;
        int locationY = (screenSize.height - this.getHeight()) / 2;
        this.setBounds(locationX, locationY, this.getWidth(), this.getHeight());
        entropy = new byte[16];
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Генерация ключей");
        setBounds(new java.awt.Rectangle(400, 200, 300, 300));
        setMaximumSize(new java.awt.Dimension(300, 300));
        setMinimumSize(new java.awt.Dimension(300, 300));
        setPreferredSize(new java.awt.Dimension(350, 350));
        setResizable(false);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Начать генерацию ключей");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jButton1)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jButton1)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        String s = new String();
        JOptionPane.showMessageDialog(this,
                "На первом этапе генерации необходимо ввести" + '\n'
                + "16 целых чисел в диапазоне [-128,127]." + '\n'
                + "Старайтесь вводить различные числа без каких" + '\n'
                + "-либо закономерностей.",
                "1 этап генерации",
                JOptionPane.INFORMATION_MESSAGE);
        
        for (int i = 0; i < 16; i++) {
            s = JOptionPane.showInputDialog(this,
                    "Введите число " + (i + 1) + ":");
            if (s == null)
                System.exit(0);
            try {
                entropy[i] = Byte.valueOf(s);                
            }
            catch (Exception e) {                
                JOptionPane.showMessageDialog(this,
                        "Вводимые числа должны быть в диапозоне [-128,127]",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                 i--;
            }            
        }
        
        jButton1.setVisible(false);
        JOptionPane.showMessageDialog(this,
                "На втором этапе генерации необходимо " + '\n'
                + "подвигать курсор около минуты." + '\n'
                + "Генерация начнётся после нажатия ОК",
                "2 этап генерации",
                JOptionPane.INFORMATION_MESSAGE);                       
        
        Point location = new Point();        
        double oldX = 0;
        double oldY = 0;
        double x = 0;
        double y = 0;
        double progress = 0;
        byte bit = 0;
        byte arrayOfBits = 0;
                      
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 8; j++) {
                location = MouseInfo.getPointerInfo().getLocation();
                x = location.getX();
                y = location.getY();
                if ((oldX == x) && (oldY == y))
                    JOptionPane.showMessageDialog(this,
                            "Двигайте курсор." + '\n'
                            + "Текущий прогресс: " + (int)progress + "%.",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                bit = (byte)(((x % 2) + (y % 2)) % 2);
                arrayOfBits += 2 ^ i * bit;
                try {
                    Thread.sleep(250);                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                oldX = x;
                oldY = y;   
                progress += 0.78125;
            }                                                
            entropy[i] = (byte) (entropy[i] ^ arrayOfBits);
        }
        try {
            generateKeys();                        
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(StartJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartJFrame().setVisible(true);
            }
        });
    }                    

    private byte[] entropy;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    
    private void makeMessageAndWait(String s) {
        JOptionPane.showMessageDialog(this, s, "Скопируйте", JOptionPane.INFORMATION_MESSAGE);        
    }
        
    private void generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG", "SUN");
        rnd.setSeed(entropy);
        BigInteger p, q, e, phi, d, n;
        
        p = BigInteger.probablePrime(1023, rnd);
        
        rnd = SecureRandom.getInstance("SHA1PRNG", "SUN");
        rnd.setSeed(entropy);
        
        q = BigInteger.probablePrime(1025, rnd);                   
        n = p.multiply(q); 
                        
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));                        
                        
        e = BigInteger.probablePrime(2046, rnd);
        
        d = e.modInverse(phi);    
        
        //Проверка, что d больше 512 бит
        if (d.bitLength() < 512)
            generateKeys();                

        StringSelection stringSelection = new StringSelection(d.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        
        makeMessageAndWait("В буфере обмена находится 1 часть секретного ключа." + '\n'
                + "Скопируйте на съёмный носитель и нажмите Ok.");
        
        stringSelection = new StringSelection(n.toString());      
        clipboard.setContents(stringSelection, null);

        makeMessageAndWait("В буфере обмена находится 2 часть секретного ключа." + '\n'
                + "Скопируйте на съёмный носитель и нажмите Ok.");
        
        stringSelection = new StringSelection(e.toString());      
        clipboard.setContents(stringSelection, null);

        makeMessageAndWait("В буфере обмена находится 1 часть открытого ключа." + '\n'
                + "Скопируйте на съёмный носитель и нажмите Ok.");
        
        stringSelection = new StringSelection(n.toString());      
        clipboard.setContents(stringSelection, null);

        makeMessageAndWait("В буфере обмена находится 2 часть открытого ключа." + '\n'
                + "Скопируйте на съёмный носитель и нажмите Oк.");
        
        JOptionPane.showMessageDialog(this,
                "Ключи сгенерированы, руководство по использованию" + '\n'
                + "прилагается к данному приложению.",
                "Успех",
                JOptionPane.INFORMATION_MESSAGE);  
        
        //защита от снимка оперативной памяти
        for (byte i = 0; i < 16; i++)
            entropy[i] = i;
        p = BigInteger.probablePrime(1023, rnd);
        q = BigInteger.probablePrime(1025, rnd);
        phi = BigInteger.probablePrime(2046, rnd);
        System.exit(0);
    }    
}
