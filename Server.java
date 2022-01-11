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

class Server 
{
	public static void main(String[] args) 
	{
		HashMap<String, ArrayList<String>> mailboxes = new HashMap<String, ArrayList<String>>();
		
		try 
		{
			// Create server Socket that listens/bonds to port/endpoint address 6666 (any port id of your choice, should be >=1024, as other port addresses are reserved for system use).
			// The default maximum number of queued incoming connections is 50 (the maximum number of clients to connect to this server).
			ServerSocket mySocket = new ServerSocket(6666);

			System.out.println("Startup the server side over port 6666 ....");
			System.out.println(InetAddress.getLocalHost());

			// use the created ServerSocket and accept() to start listening for incoming client requests targeting this server and this port.
			// accept() blocks the current thread (server application) waiting until a connection is requested by a client.
			// the created connection with a client is represented by the returned Socket object.
			Socket connectedClient = mySocket.accept();
	        
			// reaching this point means that a client established a connection with your server and this particular port.
			System.out.println("Connection established.");

			// to interact (read incoming data / send data) with the connected client, we need to create the following:

			// BufferReader object to read data coming from the client
			BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

			// PrintStream object to send data to the connected client
			PrintStream ps = new PrintStream(connectedClient.getOutputStream());
			
			// Let's keep reading data from the client, as long as the client doesn't send "exit".
			String inputData = ""; //***note: original error during development was that I assigned inputData = br
		
			while (!(inputData = br.readLine()).equals("exit"))
			{    
				String[] newString0 = inputData.split("push\\s+|,\\s+\\{|\\},\\s+", 4);
				String[] newString1 = inputData.split("\\s+", 2);
						
				if (newString1[0].equals("newclient") && newString1.length > 1) //newclient command
				{
					String username = newString1[1];
					
					if (mailboxes.containsKey(username))  //detects if sender's username is already taken.
					{
						ps.println("This username already exists.");
					}
					
					else if (newString1[1].contains(" "))
					{
						ps.println("Username cannot contain space(s) between characters.");
					}
					
					else
					{
						mailboxes.put(username, new ArrayList());
						ps.println("Welcome to our communication server, you are added as our new client."); 
					}
				}
				
				else if (newString1[0].equals("push") && newString0.length == 4) push: //push command
				{
					String[] tempNS = new String[newString0.length - 1]; 
					for (int i=0; i < tempNS.length; i++)  //gets rid of empty value in newString0[0] caused by a String that starts with a .split() delimiter.
					{
						tempNS[i] = newString0[i+1];
					}
					newString0 = tempNS;
					System.out.println(newString0[0]);
					String username = newString0[0];
					
					if (!mailboxes.containsKey(username)) //detects if sender's username is invalid.
					{
						ps.println("This username doesn't exist.");
						break push;
					}
					
					else 
					{
						String message = "FROM: " + newString0[0] + "  " +
								 "TO: " + newString0[1] + "  " + "MSG: " +
								 newString0[2] + "  //BREAK//";
				
						String[] recipients = newString0[1].split(",\\s+|,");
						
						for (int i=0; i < recipients.length; i++)
						{
							String to = recipients[i].trim();
						
							if (!mailboxes.containsKey(to))  //if a single receiver is invalid, the user will be notified and have to resubmit the command.
							{
								ps.println("The reciever \"" + to + "\" doesn't exist.");
								break push;
							}
						}
							for (int i=0; i < recipients.length; i++)
							{
								String to = recipients[i].trim();
								
								ArrayList<String> temp = mailboxes.get(to);
								temp.add(0, message);
								mailboxes.replace(to, temp);
							}
						
						ps.println("The message was successfully forwarded to the reciever(s).");
					}
				}
				
				else if (newString1[0].equals("pull") && newString1.length == 2) //pull command
				{
					String username = newString1[1];
					
					if (!mailboxes.containsKey(username)) //determines if username is invalid.
					{
						ps.println("This username doesn't exist.");
					}
					
					else
					{
						ps.println(mailboxes.get(username));
					}
				}
				
				else if (inputData.equals("knowothers")) //knowothers command
				{
					ps.println(mailboxes.keySet());
				}

				else  //all other unsupported commands
				{
					ps.println("Unsupported command");
				}

				System.out.println("received a message from client: " + inputData);  //print the incoming data from the client.
				System.out.println();
			}

			if (inputData.equals("exit"))  //closes the input/output streams and the created client/server sockets.
			{
				ps.println("Thanks for using our communication server.");
				ps.close();
				br.close();
				mySocket.close();
				connectedClient.close();
			}
		} 
		
		catch (Exception exc) 
		{
			System.out.println("Error :" + exc.toString());
		}
	}
}