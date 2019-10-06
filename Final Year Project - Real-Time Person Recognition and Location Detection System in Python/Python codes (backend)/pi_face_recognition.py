from imutils.video import VideoStream
from imutils.video import FPS
from imutils import paths
from keras.models import load_model
from keras import backend as K
import face_recognition
import imutils
import pickle
import time
import cv2
import os
import keras
import numpy as np
import socket
import datetime

def img_to_mnist(frame, tresh = 90):
    gray_img = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    gray_img = cv2.GaussianBlur(gray_img, (5, 5), 0)
    #adaptive here does better with variable lighting:
    gray_img = cv2.adaptiveThreshold(gray_img, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C,cv2.THRESH_BINARY_INV, blockSize = 321, C = 28)
    return gray_img

def extract_digit(final_img, rect, pad = 10):
    x, y, w, h = rect
    cropped_digit = final_img[y-pad:y+h+pad, x-pad:x+w+pad]
    cropped_digit = cropped_digit/255.0

    #only look at images that are somewhat big:
    if cropped_digit.shape[0] >= 32 and cropped_digit.shape[1] >= 32:
        cropped_digit = cv2.resize(cropped_digit, (SIZE, SIZE))
    else:
        return
    return cropped_digit

def annotate(frame, label, location = (20,30)):
    #writes label on image#
    cv2.putText(frame, label, location, font,
                fontScale = 0.5,
                color = (255, 255, 0),
                thickness =  1,
                lineType =  cv2.LINE_AA)
    
def recognise_sign():
    vs1=VideoStream(src=0, usePiCamera=False, resolution=frameSize,framerate=32).start()
    s=""
    while True:
        frame = vs1.read()
        if cv2.waitKey(1) & 0xFF == ord("c"):
            final_img = img_to_mnist(frame)
            image_shown = frame
            scontours, _ = cv2.findContours(final_img.copy(), cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
            contours = sorted(scontours, key=lambda ctr: cv2.boundingRect(ctr)[0])
            rects = [cv2.boundingRect(contour) for contour in contours]
            rects = [rect for rect in rects if rect[2] >= 3 and rect[3] >= 8]

            #draw rectangles and predict:
            for rect in rects:

                x, y, w, h = rect
                mnist_frame = extract_digit(final_img, rect, pad = 15)

                if mnist_frame is not None: #and i % 25 == 0:
                    mnist_frame = np.expand_dims(mnist_frame, first_dim) #needed for keras
                    mnist_frame = np.expand_dims(mnist_frame, second_dim) #needed for keras

                    class_prediction = model.predict_classes(mnist_frame, verbose = False)[0]
                    prediction = np.around(np.max(model.predict(mnist_frame, verbose = False)), 2)
                    label = str(prediction) # if you want probabilities

                    cv2.rectangle(image_shown, (x - 15, y - 15), (x + 15 + w, y + 15 + h),
                                  color = (255, 255, 0))

                    label = labelz[class_prediction]
                    s+=label
                    annotate(image_shown, label, location = (rect[0], rect[1]))
            cv2.imshow('frame', image_shown)
            key=cv2.waitKey(5000)
            if key == ord("d"):
                break
        if cv2.waitKey(1) & 0xFF == ord("a"):
            break
        if cv2.waitKey(1) & 0xFF == ord("x"):
            cv2.destroyAllWindows()
            vs.stop()
            return s,1
        cv2.imshow('frame', frame)
        
    cv2.destroyAllWindows()
    vs.stop()
    return s,0
            

#this adds unknown faces to dataset and re-encodes dataset
def retrain():
        print("[INFO] quantifying faces...")
        imagePaths = list(paths.list_images("dataset"))

        # initialize the list of known encodings and known names
        knownEncodings = []
        knownNames = []

        # loop over the image paths
        for (i, imagePath) in enumerate(imagePaths):
                # extract the person name from the image path
                print("[INFO] processing image {}/{}".format(i + 1,len(imagePaths)))
                name = imagePath.split(os.path.sep)[-2]
                #print(name)

                # load the input image and convert it from RGB (OpenCV ordering)
                # to dlib ordering (RGB)
                image = cv2.imread(imagePath)
                rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

                # detect the (x, y)-coordinates of the bounding boxes
                # corresponding to each face in the input image
                boxes = face_recognition.face_locations(rgb,
                        model='cnn')

                # compute the facial embedding for the face
                encodings = face_recognition.face_encodings(rgb, boxes)

                # loop over the encodings
                for encoding in encodings:
                        # add each encoding + name to our set of known names and
                        # encodings
                        knownEncodings.append(encoding)
                        knownNames.append(name)

        # dump the facial encodings + names to disk
        print("[INFO] serializing encodings...")
        data = {"encodings": knownEncodings, "names": knownNames}
        f = open("encodings.pickle", "wb")
        f.write(pickle.dumps(data))
        f.close()

s = socket.socket()          
port = 12350                
s.bind(('127.0.0.1', port))         
s.listen(5)      
while True:  
    c, addr = s.accept()
    SIZE = 28
    model = load_model("full_model.mnist")
    labelz = dict(enumerate(["0", "1", "2", "3", "4","5", "6", "7", "8", "9"]))
    i=1
    font = cv2.FONT_HERSHEY_SIMPLEX

    img_rows, img_cols = 28, 28

    if K.image_data_format() == 'channels_first':
        input_shape = (1, img_rows, img_cols)
        first_dim = 0
        second_dim = 1
    else:
        input_shape = (img_rows, img_cols, 1)
        first_dim = 0
        second_dim = 3



    usingPiCamera = True
    frameSize = (320, 240)
    # load the known faces and embeddings along with OpenCV's Haar
    # cascade for face detection
    print("[INFO] loading encodings + face detector...")
    data = pickle.loads(open("encodings.pickle", "rb").read())
    detector = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")

    # initialize the video stream and allow the camera sensor to warm up
    print("[INFO] starting video stream...")
    #print(str(int(data["names"][-1][-1])+1))
    vs =VideoStream(src=0, usePiCamera=False, resolution=frameSize,framerate=32).start()
    # vs = VideoStream(usePiCamera=True).start()
    time.sleep(2.0)
    i=1
    k=1
    j=0
    a=0
    b=1
    timez=[]
    times={}
    # start the FPS counter
    fps = FPS().start()
    ppl={}
    people=[]
    # loop over frames from the video file stream
    while True:
            # grab the frame from the threaded video stream and resize it
            # to 500px (to speedup processing
            
            frame = vs.read()
            frame = imutils.resize(frame, width=500)
            
            # convert the input frame from (1) BGR to grayscale (for face
            # detection) and (2) from BGR to RGB (for face recognition)
            gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            
            # detect faces in the grayscale frame        
            rects = detector.detectMultiScale(gray, scaleFactor=1.1, 
                    minNeighbors=5, minSize=(104, 104),
                    flags=cv2.CASCADE_SCALE_IMAGE)
            boxes = [(y, x + w, y + h, x) for (x, y, w, h) in rects]

            # compute the facial embeddings for each face bounding box
            encodings = face_recognition.face_encodings(rgb, boxes)
            names=[]

            # loop over the facial embeddings
            for encoding in encodings:
                    # attempt to match each face in the input image to our known
                    # encodings
                    matches = face_recognition.compare_faces(data["encodings"],
                            encoding,tolerance=0.49)
                    
                    #print(face_recognition.face_distance(data["encodings"],encoding))
                    name = "Unknown Face"

                    # check to see if we have found a match
                    if True in matches:
                            # find the indexes of all matched faces then initialize a
                            # dictionary to count the total number of times each face
                            # was matched
                            matchedIdxs = [i for (i, b) in enumerate(matches) if b]
                            counts = {}

                            # loop over the matched indexes and maintain a count for
                            # each recognized face face
                            for i in matchedIdxs:
                                    name = data["names"][i]
                                    counts[name] = counts.get(name, 0) + 1
                                    name = max(counts, key=counts.get)

                            # determine the recognized face with the largest number
                            # of votes (note: in the event of an unlikely tie Python
                            # will select first entry in the dictionary)
                    if name=="Unknown Face":
                        #create folder for unknown face
                        if(a==0):
                            if(data["names"][-1][-1].isdigit()):
                                k=int(data["names"][-1][-1])+1
                            path="dataset/Unknown Face "+str(k)
                            os.mkdir(path)
                        #save 30 snaps of new unknown face
                        if j<30:
                            for (x, y, w, h) in rects:
                                cv2.rectangle(frame, (x,y), (x+w,y+h),(0, 255, 0), 2)
                                cv2.putText(frame, name, (x, y), cv2.FONT_HERSHEY_SIMPLEX,0.75, (0, 255, 0), 2)
                                cv2.imshow("Frame", frame)
                                cv2.imwrite(path+"/"+str(j)+".jpg", frame[y:y+h,x:x+w])
                            j += 1
                            a=1
                            continue
                        #call retrain after taking 30 snaps
                        if j==30:
                            retrain()
                            data = pickle.loads(open("encodings.pickle", "rb").read())
                            j=0
                            k+=1
                            a=0
                            continue
                    names.append(name)
                    if name not in people:
                            people.append(name)
                            timez.append(str(datetime.datetime.fromtimestamp(time.time()).strftime('%I:%M:%S %p')))
                    for ((top, right, bottom, left), name) in zip(boxes, names):
                            # draw the predicted face name on the image
                            cv2.rectangle(frame, (left, top), (right, bottom),(0, 255, 0), 2)
                            y = top - 15 if top - 15 > 15 else top + 15
                            cv2.putText(frame, name, (left, y), cv2.FONT_HERSHEY_SIMPLEX,0.75, (0, 255, 0), 2)
                            cv2.imwrite("C:/wamp64/www/"+name+".jpg", frame[top:bottom,left:right])
            cv2.imshow("Frame", frame)
            key = cv2.waitKey(1) & 0xFF
            # if key pressed is x, then quit
            if cv2.waitKey(1) & 0xFF == ord("x"):
                break

            #if key pressed is c
            if key == ord("p"):
                    vs.stop()
                    #stop first stream
                    x,y=recognise_sign()
                    print("Room " +x)
                    ppl["Room " +x]=people
                    times["Room " +x]=timez
                    for i in people:
                        print(i)
                    people=[]
                    timez=[]
                    # start old stream
                    if y==0:
                        vs =VideoStream(src=0, usePiCamera=False, resolution=frameSize,framerate=32).start()
                    else:
                        file = open('C:\wamp64\www\output.txt','w') 
                        for k in ppl:
                            file.write(k+"\n")
                            h=0
                            for j in ppl[k]:
                                file.write(j+" at "+times[k][h]+"\n")
                                h+=1
                            file.write("*\n")
                        file.close()
                        break
            # update the FPS counter
            fps.update()

    # stop the timer
    fps.stop()
    # do a bit of cleanup
    cv2.destroyAllWindows()
    vs.stop()

    c.send('Thank you for connecting'.encode()) 
    c.close()
    break
'''if key == ord("x"):
    print(ppl)
    file = open('C:\wamp64\www\output.txt','w') 
    for k in ppl:
        file.write(k+"\n")
        for j in ppl[k]:
            file.write(j+"\n")
        file.write("*\n")
    file.close()
    f.close()
    break'''


