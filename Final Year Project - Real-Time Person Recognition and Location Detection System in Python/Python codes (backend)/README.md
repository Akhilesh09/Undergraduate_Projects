# live-mnist

MNIST Live - Run Digit Recognition powered by a Conv Net using live video feed from your webcam. The script uses a convolutional neural network in keras plus some adaptive treshholding and contours in open cv.


### Running the Code:
To run the code on your machine first clone the repo,

    git clone s://github.com/root-master/live-mnist
    cd live-mnist

now create a conda environment with all the dependendencies (this might take a while since the script uses both keras and open cv)

    conda env create -f environment.yml
    source activate live-mnist

Finally, run the `live_mnist.py` script. It will take a few seconds for the webcam to start:

    python live_mnist.py orig

If you want to see the tresholded image run:

  python live_mnist.py tresh

The tresholded rectangles is what the CNN actually sees sees and was done so that the training data (the MNIST dataset) and the real world data are as similar as possible.

There is a basic CNN in this repo that gets loaded automatically using keras in the script - however you can play around with training your own - take a look at the `train_model.py` script for the architecture. It will take around 700 seconds on a Macbook Pro to get over 99% accuracy.


### Webcam output - Happy because of detection:
![](happy-detect.png)
