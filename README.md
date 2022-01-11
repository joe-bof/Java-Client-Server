# Java-Client-Server
2 programs that use Java socket programming to establish a mail server in a client-server architecture.

The clients in the client-server model cannot communicate directly with each other. In this project, you will build a communication server using sockets allowing clients to communicate. Clients connect with the centralized server and register their usernames (e.g., Adam, Olivia, John) with the server. A client can then communicate with other client(s) through the server using their usernames, forwarding data to the server and the server forwards data to the intended receivers. The server should implement a famous messaging pattern called Push-Pull. Imagine client A would like to send message to client B:
  • First step: client A builds the message and forwards the message to the server – this is Push
  • Second step: the server keeps the message locally on the server
  • Third step: client B asks the server if there are any messages available and the server then forwards client A’s message – this is Pull.
  
For Push, the central server allows unicasting and multicasting of messages. A client can send a message to another client (e.g., Hi Server, this is Adam, please forward this message to Olivia), and another client can send a message to multiple clients (e.g., Hi Server, this is Adam, please forward this message to Olivia and John)

For Pull, the client explicitly asks the server if there are available messages, and the server forwards the messages (if
any) to that client.

Think of the way a server can use to keep track of these messages, you need mailboxes per registered user. One way to implement this is to use 2D array or 2D arraylist. When a client registers him/herself at the server, the server reserves a row in that 2D array or a dynamic array for that client. Then for future messages intended to that client, the server places the messages in client’s array or arraylist. Another way is to use Map, with key to be the client’s username and the value be the arraylist of messages. Again, think of the way you find appropriate to implement this. 

All what a client needs to know: 1) the IP of the server; and 2) the username(s) of the intended receiver(s). A client (with a unique identifier) -through his/her local interface- connects to the server, requests certain operation (command), and then displays results back. Any client should open socket with the server, over which the client can
send command and receive results (you should not close the connection with the server).

*NOTE: list and examples of all client commands is in the client program, and will display tp the user upon boot up._


Files included (6):
- Client Output Screenshot.pdf --> screenshot of the client program while running.
- ClientT1.java --> client side application.
- READ_ME.txt --> text description of how the program works.
- Server Output Screenshot 1.pdf --> screenshot 1/2 of the server program while running.
- Server Output Screenshot 2.pdf --> screenshot 2/2 of the server program while running.
- Server.java --> server side application.
