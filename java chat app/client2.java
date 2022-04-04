import java.net.*;
import java.io.*;

public class client2 {



    Socket socket;

    BufferedReader br;
    PrintWriter out;


    public client2()
    {
        try{

            System.out.println("Sending the req to server...");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Conection done...");

            br =new BufferedReader(new InputStreamReader(socket.getInputStream()));


            out=new PrintWriter(socket.getOutputStream());


            startReading();
            startWriting();


        }

        catch(Exception e)
        {
            
        }
    }


    public void startReading()
    {
                //this thread read the data
                Runnable r1=()->{

                    System.out.println("The Reader is started...");

                    while(true)
                    {
                       
                                try{
                                    String msg=br.readLine();
                                    if(msg.equals("exit"))
                                    {
                                        System.out.println("The Server Terminated the Chat");
                                        socket.close();
                                        break;
                                    }
                                    System.out.println("Server :"+msg);

                                }
                                catch(IOException e)
                                {
                                    e.printStackTrace();
                                }
                        

                    }

                };


                new Thread(r1).start();

    }


    public void startWriting()
    {
        ///this thread reads data from user and sends to client
        Runnable r2=()->{

            System.out.println("The Writer is started...");
            while(true)
                    {
                       
                                try{

                                    BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
                                    String content=br1.readLine();
                                    out.println(content);
                                    out.flush();


                                }
                                catch(IOException e)
                                {
                                    e.printStackTrace();//this willl print on which line the exception has occured
                                }
                        

                    }

                };

                new Thread(r2).start();
                            
    }
    public static void main(String[] args) {


        System.out.println("Hi the client is running...");
        new client2();

    }
}

