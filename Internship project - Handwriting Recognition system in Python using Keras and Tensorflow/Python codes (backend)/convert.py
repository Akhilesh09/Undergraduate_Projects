import keras
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dropout,Dense, Flatten, Conv2D, MaxPool2D
from keras.optimizers import RMSprop,Adam
from keras.models import load_model,model_from_json
from keras.preprocessing import image
from PIL import Image
import matplotlib
import matplotlib.pyplot as plt
import os
import sys
import cv2
import numpy as np
model.load_weights("weights.h5")
print(model.get_weights)
model=load_model("model.h5")
model.summary()
img = Image.open('3.png').convert("L")
img = img.resize((28,28))
im2arr = np.array(img)
im2arr = im2arr.reshape(1,28,28,1)
test_img = im2arr.reshape(1,784)
# Predicting the Test set results
y_pred = model.predict(test_img)
print(y_pred)
y_pred = model.predict_classes(test_img)
print(y_pred)
f=open('ans.txt','w')
print('It is the number: %d' % y_pred[0])
f.write(('It is the number: %d' % y_pred[0]))
f.close()