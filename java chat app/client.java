import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import java.io.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class client extends JFrame {



    Socket socket;

    BufferedReader br;
    PrintWriter out;


    //declare the componenet
    private JLabel heading=new JLabel("Client area");//heading on the text filed
    private JTextArea msgarea=new JTextArea();//here all msg will be  displayed
    private JTextField msgfield= new JTextField();//here we will write the msg
    private Font font=new Font("Roboto",Font.PLAIN,20);//font defined here

    




        //constructor where all operations are done
    public client()
    {
        try{

            System.out.println("Sending the req to server...");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Conection done...");

            br =new BufferedReader(new InputStreamReader(socket.getInputStream()));


            out=new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();


            startReading();
            // startWriting();


        }

        catch(Exception e)
        {
            
        }
    }

    private void handleEvents()
    {
        msgfield.addKeyListener((KeyListener) new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                
                if(e.getKeyCode()==10)
                {
                    String contentToSend=msgfield.getText();
                    msgarea.append("Me: " +contentToSend+"\n\n\n");
                    out.println(contentToSend);
                    if(contentToSend.equals("exit"))
                {
                    // JOptionPane.showMessageDialog(this,"Client Terminated the chat");
                    msgfield.setEnabled(false);
                    // socket.close();
                    // break;
                }
                    out.flush();
                    msgfield.setText("");
                    msgfield.requestFocus();
                }
                
            }

        });
    }

    private void createGUI(){
        //gui code
        this.setTitle("Client Messenger[End]");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //coding for component
        heading.setFont(font);
        msgarea.setFont(font);
        msgfield.setFont(font);
        heading.setIcon(new ImageIcon("icons8-chat-30.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        heading.setBackground(Color.LIGHT_GRAY);
        msgarea.setEditable(false);

        msgfield.setHorizontalAlignment(SwingConstants.CENTER);
        //frame layout
        this.setLayout(new BorderLayout());//this dived the frame in 5 parts north-->title center__>title area south -->writing area
        //addingthe components to frame
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane=new JScrollPane(msgarea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(msgfield,BorderLayout.SOUTH);







        this.setVisible(true);


    }


    //reading the msg
    public void startReading()
    {
                //this thread read the data
                Runnable r1=()->{

                    System.out.println("The Reader is started...");

                    try{

                        while(!socket.isClosed())
                        {
                           
                                    
                                        String msg=br.readLine();
                                        if(msg.equals("exit"))
                                        {
                                            System.out.println("The Server Terminated the Chat");
                                            JOptionPane.showMessageDialog(this,"Server Terminated the chat");
                                            msgfield.setEnabled(false);
                                            socket.close();
                                            break;
                                        }
                                        //System.out.println("Server :"+msg);
                                        msgarea.append("server: "+msg+"\n\n\n");
    
                        }
                    }

                    catch(Exception e)
                    {
                        System.out.println("Connection closed..");
                    }

                                
                        

                    };

                    new Thread(r1).start();
                }



    

                //writting the msg
    public void startWriting()
    {
        ///this thread reads data from user and sends to client
        Runnable r2=()->{

            System.out.println("The Writer is started...");
            try{

                while( !socket.isClosed())
                        {
                           
                                    
    
                        BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
                        String content=br1.readLine();
                        out.println(content);
                        out.flush();
                        if(content.equals("exit"))
                        {
                            socket.close();
                            break;
                        }
    
    
                                
                                    
                            
    
                        }

                        
            }
            catch(Exception e)
            {
                System.out.println("Connection closed..");
            }

                };

                new Thread(r2).start();
                            
    }
    public static void main(String[] args) {


        System.out.println("Hi the client is running...");
        new client();

    }

}

