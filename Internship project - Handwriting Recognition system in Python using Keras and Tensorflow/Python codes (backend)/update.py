import socket
import os
import sys
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # create TCP socket
serverSocket.bind(('127.0.0.1', 25000))
serverSocket.settimeout(20)
try:
 serverSocket.listen(0) # listen for connections, max. non-accepted connections set to 1
 connection, address = serverSocket.accept() # accept a connection (blocking)
 print("Connected to %s on %s" %(address[0], address[1]))
 f=open('label.txt','w')
 while True:
  try:
   data = connection.recv(1024) # receive data to connected client (blocking)
   if(len(data)<1):
    break
   f.write(data.decode())
  except socket.error as msg:
   print("Client disconnected")
	# close the socket connection
 connection.close()
 serverSocket.close() # close the socket

 f=open('label.txt')
 l=f.read()
 f.close()
 f=open('x.png','rb')
 img=f.read()
 f.close()
 train_d='C:\\ProgramData\\training_images'
 x=len(os.listdir(train_d))
 y=str(l)+'.'+str((x+1))+'.png'
 path=os.path.join(train_d,y)
 f=open(path,'wb')
 f.write(img)
 f.close()
 test_d='C:\\ProgramData\\testing_images'
 x=len(os.listdir(test_d))
 y=str(l)+'.'+str((x+1))+'.png'
 path=os.path.join(test_d,y)
 f=open(path,'wb')
 f.write(img)
 f.close()
 os.system('python mytrain1.py')
except:
 print("no requests")