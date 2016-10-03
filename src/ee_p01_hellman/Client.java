package ee_p01_hellman;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * @authors Martinez Carrera Dulce Carolina
 * 			Sánchez Lázcares Perla Melina
 * 			Jiménez Rocha Alejandra
 * Credits: TechWorld3g
 * 
 * Clase que implementa la interfaz gráfica para un cliente del chat.
 */

public class Client extends javax.swing.JFrame{
	/**
	 * Variables que se usarán en la clase
	 */
	MerkleHellman mh = new MerkleHellman();
	private static final long serialVersionUID = 1L;
	String username, address = "localhost";
    ListaLigadaSimple<String> usuarios = new ListaLigadaSimple<String>();
    int port = 2222;
    Boolean isConnected = false;
    
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
        
    /**
     * Método que crea un hilo para el control de usuarios.
     */
    public void ListenThread(){
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    /**
     * Método que agrega un nuevo usuario a la lista de usuarios.
     * @param data
     */
    public void userAdd(String data){
    	usuarios.inserta_final(data);
    }
       
    /**
     * Método que elimina un usuario al desconectarse.
     * @param data
     */
    public void userRemove(String data){
         ta_chat.append(data + " ahora está desconectado.\n");
         usuarios.elimina_elemento(data);
    }
    
    /**
     * Método que muestra los elementos en la lista de usuarios.
     */
    public void writeUsers(){
         usuarios.recorreIterativo();
    }
    
    /**
     * Método que permite mostrar cuando se desconceta un usuario.
     */
    public void sendDisconnect(){
        String bye = (username + ": :Desconectado");
        try{
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e){
            ta_chat.append("No se pueden enviar mensajes a usuarios desconectados.\n");
        }
    }
    
    /**
     * Método que permite desconectarse a un usuario.
     */
    public void Disconnect(){
        try{
            ta_chat.append("Desconectado.\n");
            sock.close();
        } catch(Exception ex) {
            ta_chat.append("Fallo al desconectar. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);
    }
    
    /**
     * Método que manda a llamar a los componentes.
     */
    public Client(){
        initComponents();
    }
    
    /**
     * Clase interna que maneja los estados de conectado y desconectado
     *
     */
    public class IncomingReader implements Runnable{
    	/**
    	 * Sobrescritura del método run() de la clase Thread
    	 */
        @Override
        public void run(){
            String[] data;
            String stream, done = "Terminado", connect = "Conectado", disconnect = "Desconectado", chat = "Chat";

            try{
                while ((stream = reader.readLine()) != null){
                     data = stream.split(":");

                     if (data[2].equals(chat)){
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                     }else if (data[2].equals(connect)){
                        ta_chat.removeAll();
                        userAdd(data[0]);
                     }else if (data[2].equals(disconnect)){
                         userRemove(data[0]);
                     }else if (data[2].equals(done)){
                        writeUsers();
                     }
                }
           }catch(Exception ex) {}
        }
    }
    
    /**
     * Inicialización de las variables a utilizar y campos de la interfaz
     */
    private void initComponents() {
        lb_address = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField();
        lb_port = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField();
        lb_password = new javax.swing.JLabel();
        tf_password = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        b_anonymous = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Seguro - Cliente");
        setName("Cliente");
        setResizable(false);

        lb_address.setText("Dirección: ");

        tf_address.setText("localhost");
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);}});

        lb_port.setText("Puerto:");

        tf_port.setText("2222");
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_portActionPerformed(evt);}});

        lb_username.setText("Nombre de usuario:");

        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt){
            	tf_usernameActionPerformed(evt);}});

        b_connect.setText("Conectar");
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);}});

        b_disconnect.setText("Desconectar");
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);}});

        b_anonymous.setText("Entrar como anónimo");
        b_anonymous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_anonymousActionPerformed(evt);}});

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_send.setText("Enviar");
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);}});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_address, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                            .addComponent(tf_username))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_password)
                            .addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(b_connect)
                                .addGap(2, 2, 2)
                                .addComponent(b_disconnect)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(b_anonymous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_name)
                .addGap(201, 201, 201))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_address)
                    .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_port)
                    .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_anonymous))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_username)
                    .addComponent(tf_password)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_username)
                        .addComponent(lb_password)
                        .addComponent(b_connect)
                        .addComponent(b_disconnect)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_chat)
                    .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_name)));
        pack();
    }

    /**
     * Método que permitirá ejecutar la acción dirección.
     * @param evt
     */
    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {}

    /**
     * Método que permitirá ejecutar la acción puerto.
     * @param evt
     */
    private void tf_portActionPerformed(java.awt.event.ActionEvent evt) {}

    /**
     * Método que permitirá ejecutar la acción nombre de usuario.
     * @param evt
     */
    private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {}

    /**
     * Método que permitirá ejecutar la acción conectar
     * @param evt
     */
    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {
        if (isConnected == false){
            username = tf_username.getText();
            tf_username.setEditable(false);

            try{
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":fue conectado.:Conectado");
                writer.flush(); 
                isConnected = true; 
            }catch (Exception ex){
                ta_chat.append("No se pudo conectar. Intente nuevamente. \n");
                tf_username.setEditable(true);
            }
            ListenThread();            
        } else if (isConnected == true){
            ta_chat.append("Ahora estás conectado. \n");
        }
    }

    /**
     * Método que permitirá ejecutar la acción desconectar.
     * @param evt
     */
    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {
        sendDisconnect();
        Disconnect();
    }

    /**
     * Método que permitirá ejecutar la acción entrar como anónimo.
     * @param evt
     */
    private void b_anonymousActionPerformed(java.awt.event.ActionEvent evt) {
        tf_username.setText("");
        if (isConnected == false){
            String anon="anon";
            Random generator = new Random(); 
            int i = generator.nextInt(999) + 1;
            String is=String.valueOf(i);
            anon=anon.concat(is);
            username=anon;
            
            tf_username.setText(anon);
            tf_username.setEditable(false);

            try{
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(anon + ":fue conectado.:Conectado");
                writer.flush(); 
                isConnected = true; 
            } 
            catch (Exception ex) 
            {
                ta_chat.append("No se pudo conectar. Intente nuevamente. \n");
                tf_username.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ta_chat.append("Ahora estás conectado. \n");
        }        
    }

    /**
     * Método que permitirá ejecutar la acción enviar, aquí irá el encriptamiento del mensaje.
     * @param evt
     */
    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
               writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
               writer.flush(); 
               for(int i = 0; i < tf_chat.getText().length(); i++){
       				System.out.println(mh.mensajeEncriptado(tf_chat.getText().charAt(i)));
       				writer.println(username + ":" + mh.desencriptar());
       				System.out.println();
       			}	
            } catch (Exception ex) {
                ta_chat.append("El mensaje no fue enviado. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }

    /**
     * Método main que acciona el hilo.
     * @param args
     */
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new Client().setVisible(true);
            }});
    }
    
    /**
     * Botones que se usan en la interfaz.
     */
    private javax.swing.JButton b_anonymous;
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_password;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_username;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_password;
    private javax.swing.JTextField tf_port;
    private javax.swing.JTextField tf_username;
}