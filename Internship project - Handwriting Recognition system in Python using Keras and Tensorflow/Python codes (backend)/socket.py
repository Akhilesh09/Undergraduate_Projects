import socket
import os
import sys
 
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # create TCP socket
serverSocket.bind(('127.0.0.1', 35000))
serverSocket.settimeout(10)
try:
 serverSocket.listen(1) # listen for connections, max. non-accepted connections set to 1
 connection, address = serverSocket.accept() # accept a connection (blocking)
 print("Connected to %s on %s" %(address[0], address[1]))
 f=open('x.png','wb')
 while True:
  try:
   data = connection.recv(1024) # receive data to connected client (blocking)
   if(len(data)<1):
    break
   f.write(data)
  except socket.error as msg:
    print("Client disconnected")
    connection.close() # close the socket connection
 connection.close()
 serverSocket.close() # close the socket
 f.close()

#os.system('python change.py')
 os.system('python convert.py')
 f=open('ans.txt')
 x=f.read()
 #print(x)

 sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# Connect the socket to the port where the server is listening
 server_address = ('127.0.0.1', 10000)
 print('connecting to %s port %s' % server_address)
 sock.connect(server_address)

#After the connection is established, data can be sent through the socket with sendall() and received with recv(), just as in the server.

 try:
    
    # Send data
    print('sending "%s"' % x)
    sock.send(x.encode())
 finally:
    print('closing socket')
 sock.close()
 print("Client disconnected")
 os.system('python update.py')
except:
 print("no requests")