package ee_p01_hellman;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 * @authors Martinez Carrera Dulce Carolina
 * 			Sánchez Lazcares Perla Melina
 * 			Jiménez Rocha Alejandra
 * Credits: TechWorld3g
 * 
 * Clase que permitirá implementar el servidor del chat.
 */
public class Server extends javax.swing.JFrame{
	/**
	 * Variables a utilizar en la clase.
	 */
	private static final long serialVersionUID = 1L;
		ArrayList clientOutputStreams;
		ListaLigadaSimple<String> usuarios;

		/**
		 * Clase interna que permite el manejo de los clientes, cada que se conectan.
		 *
		 */
	   public class ClientHandler implements Runnable{
	       BufferedReader reader;
	       Socket sock;
	       PrintWriter client;

	       /**
	        * Método que permite obtener la dirección del cliente mediante un socket.
	        * @param clientSocket
	        * @param user
	        */
	       public ClientHandler(Socket clientSocket, PrintWriter user){
	            client = user;
	            try{
	                sock = clientSocket;
	                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
	                reader = new BufferedReader(isReader);
	            }catch (Exception ex){
	                ta_chat.append("Error inesperado. \n");
	            }
	       }

	       /**
	        * Sobrescritura del método run() de la clase Thread
	        */
	       @Override
	       public void run(){
	            String message, connect = "Conectado", disconnect = "Desconectado", chat = "Chat" ;
	            String[] data;

	            try{
	                while ((message = reader.readLine()) != null){
	                    ta_chat.append("Recibido: " + message + "\n");
	                    data = message.split(":");
	                    
	                    for (String token:data) 
	                        ta_chat.append(token + "\n");

	                    if (data[2].equals(connect)){
	                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
	                        userAdd(data[0]);
	                    }else if (data[2].equals(disconnect)){
	                        tellEveryone((data[0] + ":fue desconectado." + ":" + chat));
	                        userRemove(data[0]);
	                    }else if (data[2].equals(chat)){
	                        tellEveryone(message);
	                    }else{
	                        ta_chat.append("No se pudo conectar. \n");
	                    }
	                } 
	             }catch (Exception ex){
	                ta_chat.append("Conexión perdida. \n");
	                ex.printStackTrace();
	                clientOutputStreams.remove(client);
	             } 
	       } 
	    }

	   /**
	    * Método que llama a los componentes de la interfaz.
	    */
	    public Server(){
	        initComponents();
	    }

	    /**
	     * Método que inicializa las variables de la interfaz gráfica.
	     */
	    @SuppressWarnings("unchecked")
	    private void initComponents() {

	        jScrollPane1 = new javax.swing.JScrollPane();
	        ta_chat = new javax.swing.JTextArea();
	        b_start = new javax.swing.JButton();
	        b_end = new javax.swing.JButton();
	        b_users = new javax.swing.JButton();
	        b_clear = new javax.swing.JButton();
	        lb_name = new javax.swing.JLabel();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setTitle("Chat Seguro - Servidor");
	        setName("Servidor");
	        setResizable(false);

	        ta_chat.setColumns(20);
	        ta_chat.setRows(5);
	        jScrollPane1.setViewportView(ta_chat);

	        b_start.setText("Iniciar");
	        b_start.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                b_startActionPerformed(evt);
	            }
	        });

	        b_end.setText("Salir");
	        b_end.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                b_endActionPerformed(evt);
	            }
	        });

	        b_users.setText("Mostrar usuarios");
	        b_users.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                b_usersActionPerformed(evt);
	            }
	        });

	        b_clear.setText("Limpiar");
	        b_clear.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                b_clearActionPerformed(evt);}});

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jScrollPane1)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                            .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                            .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(b_users, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
	                .addContainerGap())
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(lb_name)
	                .addGap(209, 209, 209)));
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
	                .addGap(18, 18, 18)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(b_start)
	                    .addComponent(b_users))
	                .addGap(18, 18, 18)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(b_clear)
	                    .addComponent(b_end))
	                .addGap(4, 4, 4)
	                .addComponent(lb_name)));
	        pack();
	    }
	    
	    /**
	     * Método que permitirá ejecutar la acción salir.
	     * @param evt
	     */
	    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {
	        try{
	            Thread.sleep(5000); //5000 milisegndos son cinco segundos.
	        } 
	        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
	        
	        tellEveryone("El servidor se ha detenido. Todos los usuarios han sido desconectados.\n:Chat");
	        ta_chat.append("Deteniendo servidor... \n");
	        
	        ta_chat.setText("");
	    }
	    
	    /**
	     * Método que permitirá ejecutar la acción inciar.
	     * @param evt
	     */
	    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {
	        Thread starter = new Thread(new ServerStart());
	        starter.start();
	        
	        ta_chat.append("Iniciando servidor...\n");
	    }

	    /**
	     * Método que permitirá ejecutar la acción mostrar usuarios.
	     * @param evt
	     */
	    private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {
	        ta_chat.append("\n Mostrar usuarios : \n");
	        ta_chat.append(usuarios.recorreIterativo());
	    }

	    /**
	     * Método que permitirá ejecutar la acción limpiar.
	     * @param evt
	     */
	    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {
	        ta_chat.setText("");
	    }
	    
	    /**
	     * Método main que acciona el hilo.
	     * @param args
	     */
	    public static void main(String args[]){
	        java.awt.EventQueue.invokeLater(new Runnable(){
	            @Override
	            public void run() {
	                new Server().setVisible(true);
	            }});
	    }
	    
	    /**
	     * Clase interna que permite iniciar el servidor.
	     *
	     */
	    public class ServerStart implements Runnable{
	    	/**
	    	 * Sobrescritura del método run() de la clase Thread.
	    	 */
	        @Override
	        public void run(){
	            clientOutputStreams = new ArrayList();
	            usuarios = new ListaLigadaSimple<String>();  

	            try{
	                ServerSocket serverSock = new ServerSocket(2222);

	                while (true){
	                	Socket clientSock = serverSock.accept();
	                	PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
	                	clientOutputStreams.add(writer);

	                	Thread listener = new Thread(new ClientHandler(clientSock, writer));
	                	listener.start();
	                	ta_chat.append("Obteniendo conexión. \n");
	                }
	            }catch (Exception ex){
	                ta_chat.append("Error al encontrar conexión. \n");
	            }
	        }
	    }
	    
	    /**
	     * Método que agrega un usuario al servidor.
	     * @param data
	     */
	    public void userAdd (String data){
	        String message, add = ": :Conectado", done = "Servidor: :Terminado", name = data;
	        ta_chat.append("Antes " + name + " agregado. \n");
	        usuarios.inserta_final(name);
	        ta_chat.append("Después " + name + " agregado. \n");
	        usuarios.recorreIterativo();
	        tellEveryone(done);
	    }
	    
	    /**
	     * Método que elemina un usuario del servidor.
	     * @param data
	     */
	    public void userRemove (String data){
	        String message, add = ": :Conectado", done = "Servidor: :Terminado", name = data;
	        usuarios.elimina_elemento(name);
	        tellEveryone(done);
	    }
	    
	    /**
	     * Método que permite mostrar el mensaje enviado.
	     * @param message
	     */
	    public void tellEveryone(String message){
		Iterator it = clientOutputStreams.iterator();
		
	        while (it.hasNext()){
	            try{
	                PrintWriter writer = (PrintWriter) it.next();
	                writer.println(message);
	                ta_chat.append("Enviando: " + message + "\n");
	                writer.flush();
	                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
	            }catch (Exception ex){
	            	ta_chat.append("Error al enviar el mensaje. \n");
	            }
	        } 
	    }

	    /**
	     * Botones que se usan en la interfaz.
	     */
	    private javax.swing.JButton b_clear;
	    private javax.swing.JButton b_end;
	    private javax.swing.JButton b_start;
	    private javax.swing.JButton b_users;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JLabel lb_name;
	    private javax.swing.JTextArea ta_chat;
}