import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ChatClient 
{
    public static void main(String[] args) throws Exception 
    {
        String message = "";
        String username = "";
        Scanner scanner = new Scanner(System.in);
        
        Socket socket = new Socket("codebank.xyz", 38001);
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = socket.getOutputStream();
        PrintStream out = new PrintStream(os, true, "UTF-8");
        
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        out.println(username.trim());

        Runnable RetrieveMessages = () -> 
        { 
            try 
            {
                String serv_message="";
                while ((serv_message = br.readLine()) != null) 
                {
                    if(serv_message.equals(("Name in use.").trim()))
                    {
                        System.out.println(serv_message);
                        socket.close();
                        return;
                    }
                    
                    System.out.println(serv_message);
                }
            } 
            catch (IOException ex) 
            {
                try 
                {
                    socket.close();
                }
                catch (IOException ex1) 
                {
                    System.out.println("Can't close server");
                    return;
                }
            }
        };

        Thread getMessages = new Thread(RetrieveMessages);
        getMessages.start();

        while(true)
        {
            message = scanner.nextLine();
            
            if(((message.trim()).equals("exit")))
            {
                socket.close();
                return;
            }
            else
            {
                out.println(message);
            }
        }
    }
}












