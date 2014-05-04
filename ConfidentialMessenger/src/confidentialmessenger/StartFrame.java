package confidentialmessenger;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Shershnev
 */
public class StartFrame extends javax.swing.JFrame implements MessagingInterface {

    public StartFrame() { 
        isLargeMessage = false;
        accumMessage = "";
        isMadeSettings = false;
        rsa = new RSAcryptosystem();
        try {
            book = new CodeBook();
        } catch (ParserConfigurationException | IOException ex) {
            makeMessageAndClose("Произошла ошибка, связанная с записной книжкой" + '\n'
                    + "Попробуйте удалить файл codeBook.xml");
            System.exit(0);
        }
        try {
            makeServer();
        } catch (RemoteException ex) {
            makeMessageAndClose("Произошла ошибка, связанная с созданием заглушки" + '\n'
                    + "для соединения. Попробуйте запустить приложение заново.");
        }

        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (screenSize.width - this.getWidth()) / 2;
        int locationY = (screenSize.height - this.getHeight()) / 2;
        this.setBounds(locationX, locationY, this.getWidth(), this.getHeight());

        getIp();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Конфиденциальная переписка");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                beforeClose(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Выбор ключей");
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
                .addGap(119, 119, 119)
                .addComponent(jButton1)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jButton1)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!isMadeSettings) {
            String s = null;
            BigInteger first = BigInteger.ONE;
            BigInteger second = BigInteger.ONE;
            while (s == null) {
                s = JOptionPane.showInputDialog(this, 
                        "Корректно скопируйте 1 часть вашего секретного ключа:");
                try {
                    first = new BigInteger(s);
                }
                catch (Exception e) {
                    s = null;
                }
            }
            s = null;
            while (s == null) {
                s = JOptionPane.showInputDialog(this, 
                        "Корректно скопируйте 2 часть вашего секретного ключа:");
                try {
                    second = new BigInteger(s);
                }
                catch (Exception e) {
                    s = null;
                }
            }
            rsa.setCloseKey(first, second);
            Object[] buttons = {"Да", "Нет"}; 
            int check = JOptionPane.showOptionDialog(null, "Попытаться найти ключи собеседника" + '\n'
                                                            + "в записной книжке?", 
                                                            "Ключи собеседника", 
                                                            JOptionPane.YES_NO_OPTION, 
                                                            JOptionPane.QUESTION_MESSAGE, 
                                                            null, buttons, buttons[0]);  
            if (check == -1) {
                System.exit(0);
            }
            if (check == 0) {
                s = JOptionPane.showInputDialog(this, "Введите имя записи в записной книжке:");
                try {
                    first = book.geteKey(s);
                    second = book.getnKey(s);
                } catch (RecordDoNotExistException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Запись с таким именем не найдена." + '\n'
                            + "Придёться вставить ключи вручную.",
                            "Запись не существует",
                            JOptionPane.INFORMATION_MESSAGE);
                    check = 1;
                }                 
            }
            if (check == 1) {
                s = null;
                while (s == null) {
                    s = JOptionPane.showInputDialog(this, 
                            "Корректно скопируйте 1 часть ключа собеседника:");
                    try {
                        first = new BigInteger(s);
                    } catch (Exception e) {
                        s = null;
                    }
                }
                s = null;
                while (s == null) {
                    s = JOptionPane.showInputDialog(this, 
                            "Корректно скопируйте 2 часть ключа собеседника:");
                    try {
                        second = new BigInteger(s);
                    } catch (Exception e) {
                        s = null;
                    }
                }                
                s = null;
                while (s == null) {
                    s = JOptionPane.showInputDialog(this, "Введите имя собеседника для сохранения в базе:");            
                    try {
                        book.addRecord(s, first, second);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Выбранное имя некорректно или уже занято."
                                , "Ошибка", JOptionPane.ERROR_MESSAGE);
                        s = null;
                    }
                }            
            }
            rsa.setOpenKey(first, second);
            jButton1.setText("Начать чат");
            isMadeSettings = true;
            JOptionPane.showMessageDialog(this, "Для конфиденциальной переписки всё подготовлено." + '\n'
                                + "Убедившись, что подготовился ваш собеседник, нажмите Начать чат."
                                , "Ожидание собеседника", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            connect();
            jButton1.setVisible(false);
            makeChatFrame();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void beforeClose(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_beforeClose
        //защита от снимка оперативной памяти
        rsa.setCloseKey(rsa.getOpenE(), rsa.getOpenN());
        System.exit(0);
    }//GEN-LAST:event_beforeClose
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        sendMessage();
    }                      

    private void ctrlKeyPressed(java.awt.event.KeyEvent evt) {   
        int codeEvent = evt.getKeyCode();  
        int modifiers = evt.getModifiers();  
        if(codeEvent == KeyEvent.VK_ENTER && modifiers == KeyEvent.CTRL_MASK) {
            sendMessage();
        }        
    }          
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartFrame().setVisible(true);
            }
        });
    }    
    
    private void makeServer() throws RemoteException {
        MessagingInterface myStub;
        Registry myRegistry;
        myStub = (MessagingInterface) UnicastRemoteObject.exportObject(this, 0);
        int i = 1421;        
        while (i < 1460) {
            try {                                                                  
                myRegistry = LocateRegistry.createRegistry(i);
                myRegistry.bind("Hello", myStub);
                System.err.println("Server ready: " + i);                
                break;
            } catch (RemoteException | AlreadyBoundException ex) {
                i++;                
            }
        }
        if (i == 1460) {
            makeMessageAndClose("Произошла ошибка, связанная с созданием заглушки" + '\n'
                    + "для соединения. Попробуйте запустить приложение заново.");
        }
    }
    
    private void makeMessageAndClose(String s) {
        JOptionPane.showMessageDialog(this, s, "Ошибка", JOptionPane.ERROR_MESSAGE);
        close();        
    }
    
    private void makeChatFrame() {         
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 18));
        jTextArea1.setRows(5);
        jTextArea1.setFocusable(false);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());        
        jScrollPane1.setViewportView(jTextArea1);                

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18));
        jButton2.setText("Отправить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setCaretPosition(jTextArea1.getDocument().getLength());
        jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ctrlKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, screenSize.width - 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jTextArea2.requestFocus();
        
        pack();
                
        this.setBounds(75, 40, screenSize.width - 150, screenSize.height - 100);
    }    
    
    private void close() {
        //защита от снимка оперативной памяти
        rsa.setCloseKey(rsa.getOpenE(), rsa.getOpenN());
        System.exit(0);
    }
    
    private void connect() {
        int i = 1421;
        while (i < 1460) {
            try {
                registry = LocateRegistry.getRegistry(ip, i);
                stub = (MessagingInterface) registry.lookup("Hello");                
                break;
            } catch (RemoteException | NotBoundException d) {
                i++;
            }
        }
        if (i == 1460) {
            makeMessageAndClose("Произошла ошибка, связанная с соединением. Указан неверный" + '\n'
                    + "ip адрес или собеседник ещё не создал подключение." + '\n'
                    + "Попробуйте запустить приложение заново, используя другие данные" + '\n'
                    + "и убедившись в готовности собеседника");
        }
    }
    
    private void getIp() {
        ip = JOptionPane.showInputDialog(this, "Введите ip собеседника:");
        if (ip == null)
            makeMessageAndClose("Вы не ввели ip адрес." + '\n' + "Приложение будет закрыто.");
    }    
    
    private void sendMessage() {
        BigInteger code;
        Date date = new Date();
        String text = jTextArea2.getText();                              
        jTextArea1.append("Вы в " + date.toString().substring(11,16) + ": " + text + '\n');
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        jTextArea2.setText("");          
        try {
            if (text.length() > 110) {
                stub.startReceiveMessage();
                String buf = "";
                while (text.length() > 110) {
                    buf = text.substring(0, 110);       
                    text = text.substring(110);
                    code = rsa.stringToBigInteger(buf);
                    stub.receiveMessage(rsa.encrypt(code), rsa.getSignature(code));
                }
                code = rsa.stringToBigInteger(text);
                stub.receiveMessage(rsa.encrypt(code), rsa.getSignature(code));
                stub.endReceiveMessage();
            }
            else {
                code = rsa.stringToBigInteger(text);
                stub.receiveMessage(rsa.encrypt(code), rsa.getSignature(code));
            }
        } catch (Exception ex) {
            makeMessageAndClose("Соединение было разорвано. Приложение удаляет" + '\n'
                    + "закрытые ключи и закрывается.");
        }
    }

    @Override
    public void startReceiveMessage() throws RemoteException {
        isLargeMessage = true;
    }

    @Override
    public void endReceiveMessage() throws RemoteException {
        isLargeMessage = false;
        Date date = new Date();        
        jTextArea1.append("Он в " + date.toString().substring(11,16) + ": " + accumMessage + '\n');  
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        accumMessage = "";
    }
    
    @Override
    public void receiveMessage(BigInteger c, BigInteger sign) throws RemoteException {
        Date date = new Date();        
        BigInteger message = rsa.decrypt(c);
        if (message.compareTo(rsa.decryptSignature(sign)) == 0) {
            if (isLargeMessage) {
                accumMessage += rsa.bigIntegerToString(message);
            }
            else {
                jTextArea1.append("Он в " + date.toString().substring(11,16) + ": " + rsa.bigIntegerToString(message) + '\n');  
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            }
        }
        else {
            makeMessageAndClose("Подпись сообщения не принадлежит автору." + '\n'
                    + "Сбой приложения или попытка обмана." + '\n'
                    + "Приложение удаляет закрытые ключи" + '\n'
                    + "и закрывается");
        }
    }
    
    private boolean isLargeMessage;
    private boolean isMadeSettings;
    private CodeBook book;    
    private RSAcryptosystem rsa;
    private static String ip;
    private static Registry registry;
    private static MessagingInterface stub;   
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private String accumMessage;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
