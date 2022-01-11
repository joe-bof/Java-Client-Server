/* 
@Author Joe Bofani
@Since 12/6/2021
CS 331-3, Professor Khaled
Must run the server before the client.
Check fire-wall settings if connection errors occur.
*/

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.*;

class ClientT1
{
    public static void main(String[] args) 
    {
        System.out.println("Below is a list of acceptable commands with examples and guidelines:\n\n"
        		+ "* Must follow exact notation; capitalization and no spaces before comma.\n"
        		+ "* Trailing and leading spaces will be trimmed.\n\n"
				+ "1) Newclient username : Creates a mailbox w/ the specified username.\n" 
				+ "   Newclient Victoria\n\n"
				+ "2) Push username, {recievers}, message : Send a message to one or more specified recievers.\n"
				+ "   Push Victoria, {Jim, Bill}, Hello!!\n\n"
				+ "3) Pull username : Retrieves all messages for the specified user in order.\n"
				+ "   Pull Victoria\n\n"
				+ "4) KnowOthers : provides a list of all users on the communication server.\n\n"
				+ "5) Exit : terminates the connection with the communication server.\n"
        		+ "______________________________________________________________________________________________________\n\n");

        try {
            
            // Create client socket to connect to certain server (Server IP, Port address).
            // use either "localhost" or "127.0.0.1" if the server runs on the same device as the client.
            Socket mySocket = new Socket("192.168.1.151", 6666);

            // to interact (send data / read incoming data) with the server, we need to create the following:
            
            //DataOutputStream object to send data through the socket
            DataOutputStream outStream = new DataOutputStream(mySocket.getOutputStream());

            // BufferReader object to read data coming from the server through the socket
            BufferedReader inStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            String statement = "";

            Scanner in = new Scanner(System.in);
            
            while(!statement.equals("exit"))  
            {
            	statement = in.nextLine().toLowerCase().trim(); //read user input from the terminal data to the server.  //server only reads commands in lowercase; brings uniformity. 
                
                int validCheck = 0; //flag

                String[] splitter = statement.split("\\s");

                if (splitter[0].equals("newclient") || splitter[0].equals("push") || splitter[0].equals("pull") || splitter[0].equals("knowothers") || splitter[0].equals("exit"))
                {
                    validCheck = 1; //checks to see if Strings are legitimate commands; e.g. "hello" is an illegitimate command.
                }
                
                else
                {
                	validCheck = 0;
                }

                if (validCheck == 0) //checks for legitimate commands.
                {
                    statement = "";  //illegitimate commands will be transmitted to server as a blank String, and ignored.
                }

                
                outStream.writeBytes(statement+"\n");  // send such input data to the server.

                String str = inStream.readLine();  // receive response from server.
                
                String[] seperator = str.split("//BREAK//,");//for "pull" responses, use //BREAK// as newline delimiter.

                for (int i=0; i < seperator.length; i++)
                {
                	String tempString = "";
                	tempString = seperator[i].replaceAll("//BREAK//", "");//for "pull" responses, the last message shows a //BREAK// delimiter.
                	System.out.println(tempString);
                }   
            }
            
            // close connection.
            outStream.close();
            inStream.close();
            mySocket.close();
        } 
        
        catch (Exception exc) 
        {
            System.out.println("Error is : " + exc.toString());
            exc.printStackTrace(System.out);
        }
    }
}

