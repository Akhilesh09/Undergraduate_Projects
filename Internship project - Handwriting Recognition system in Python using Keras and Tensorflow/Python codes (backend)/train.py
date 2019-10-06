import keras
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dropout,Dense, Flatten, Conv2D, MaxPool2D
from keras.optimizers import RMSprop,Adam
from keras.models import load_model,model_from_json
import tensorflow as tf
from random import shuffle
import numpy as np
import random
import os
import h5py
import cv2
import matplotlib.pyplot as plt
from keras.preprocessing import image
train_d='C:\\ProgramData\\training_images'
test_d='C:\\ProgramData\\testing_images'

def one_hot_label(img):
 label=img.split('.')[0]
 #print(label)
 return label
 
 
def train_data_with_label():
 train_images=[]
 for i in os.listdir('C:\\ProgramData\\training_images'):
  path=os.path.join('C:\\ProgramData\\training_images',i)
  #print(path)
  img = image.load_img(path=path,grayscale=True,target_size=(28,28))
  #img=cv2.imread(path,cv2.IMREAD_GRAYSCALE)
  #img=cv2.resize(img,(28,28))
  #print(img.size)
  img =image.img_to_array(img) 
  #print(np.array(img))
  bc=0
  wc=0
  for x in img:
   for y in x:
    if(y<127):
     bc+=1
    else:
     wc+=1
    if(bc>wc):
     #print("white on black")
     ret,thresh1 = cv2.threshold(img,127,255,cv2.THRESH_BINARY)
     #print(thresh1)
    else:
     #print("black on white")
     ret,thresh1 = cv2.threshold(img,127,255,cv2.THRESH_BINARY_INV)
     #print(thresh1)
  train_images.append([thresh1,one_hot_label(i)])
  shuffle(train_images)
 return train_images
  
def test_data_with_label():
 test_images=[]
 for i in os.listdir('C:\\ProgramData\\testing_images'):
  path=os.path.join('C:\\ProgramData\\testing_images',i)
  img = image.load_img(path=path,grayscale=True,target_size=(28,28))
  #print(img.size)
  img =image.img_to_array(img) 
  #print(np.array(img))
  bc=0
  wc=0
  for x in img:
   for y in x:
    if(y<127):
     bc+=1
    else:
     wc+=1
    if(bc>wc):
     #print("white on black")
     ret,thresh1 = cv2.threshold(img,127,255,cv2.THRESH_BINARY)
     #print(thresh1)
    else:
     #print("black on white")
     ret,thresh1 = cv2.threshold(img,127,255,cv2.THRESH_BINARY_INV)
     #print(thresh1)
  test_images.append([thresh1,one_hot_label(i)])
  shuffle(test_images)
 return test_images
  
#print(os.listdir(train_d))
#print(os.listdir(test_d))
  
mnist_train_images = train_data_with_label()
#print(len(mnist_train_images))
mnist_test_images = test_data_with_label()

#for i in mnist_train_images:
#print(i[1])
#for i in mnist_train_images:
#tr_img_data.append(i[0].reshape(1,784))
tr_img_data=np.array([i[0] for i in mnist_train_images])
#print(tr_img_data.shape)
tr_img_data=tr_img_data.reshape(len(os.listdir(train_d)),784)
#print(tr_img_data.shape)
tr_lbl_data = np.array([str(i[1]) for i in mnist_train_images])
tr_lbl_data=keras.utils.to_categorical(tr_lbl_data,10)

tst_img_data=[]
#for i in mnist_train_images:
#tst_img_data.append(i[0].reshape(1,784))
tst_img_data=np.array([i[0] for i in mnist_test_images])
#print(tst_img_data.shape)
tst_img_data=tst_img_data.reshape(len(os.listdir(test_d)),784)
#print(tst_img_data.shape)
tst_lbl_data = np.array([str(i[1]) for i in mnist_test_images])
tst_lbl_data=keras.utils.to_categorical(tst_lbl_data,10)

model = Sequential()
model.add(Dense(512, activation='relu', input_shape=(784,)))
model.add(Dense(10, activation='softmax'))
#model.summary()
model.compile(loss='categorical_crossentropy',optimizer='rmsprop',metrics=['accuracy'])
#print(model.get_weights())
history = model.fit(tr_img_data, tr_lbl_data,batch_size=1000,epochs=60,verbose=2,validation_data=(tst_img_data, tst_lbl_data))
score=model.evaluate(tst_img_data, tst_lbl_data,verbose=2)

print(score)
#print(model.get_weights())
with open('model_mnist.json', 'w') as f:
 f.write(model.to_json())
f.close()
#model.save("model.h5")
model.save_weights("weights.h5")
print("Model trained successfully.")
print("Saved model to disk")