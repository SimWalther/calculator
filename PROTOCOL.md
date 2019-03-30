#####What transport protocol do we use?
We will use TCP/IP

##### How does the client find the server (addresses and ports)?
IP private : 172.31.222.111

Port number : 2323

##### Who speaks first?
the client initiates the conversation

#####What is the sequence of messages exchanged by the client and the server?
(Client) : Hello

(Server) : What computation do you want ?

(Client) : send a computation

(Server) : send an answer 

(Server) : Ask again what operation you do want

#####What happens when a message is received from the other party?
close the connection.

#####What is the syntax of the messages? How we generate and parse them?
Plain calcul as 4 + 4 * 2

#####Who closes the connection and when?
The client close the connection (cmd : bye)
