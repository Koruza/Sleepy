# -*- coding: utf-8 -*-

import os
import sys
import numpy as np

# The following are classifiers you may be interested in using:
from sklearn.tree import DecisionTreeClassifier # decision tree classifier
from sklearn.ensemble import RandomForestClassifier # random forest classifier
from sklearn.neighbors import NearestNeighbors # k-nearest neighbors (k-NN) classiifer
from sklearn.svm import SVC #SVM classifier

from USleepfeatures import extract_features
from sklearn import cross_validation
from sklearn.metrics import confusion_matrix
import pickle


# %%---------------------------------------------------------------------------
#
#		                 Load Data From Disk
#
# -----------------------------------------------------------------------------

data_dir = 'data' # directory where the data files are stored

output_dir = 'training_output' # directory where the classifier(s) are stored

if not os.path.exists(output_dir):
    os.mkdir(output_dir)


class_names = [] # the set of classes, i.e. speakers

data = np.zeros((0,3))
for filename in os.listdir(data_dir):
    if filename.endswith("data.csv"):
        filename_components = filename.split("-") # split by the '-' character
        light = filename_components[0]
        print("Loading data for {}.".format(light))
        if light not in class_names:
            class_names.append(light)
        sys.stdout.flush()
        data_file = os.path.join('data', filename)
        data_for_current_speaker = np.genfromtxt(data_file, delimiter=',')
        print("Loaded {} raw labelled audio data samples.".format(len(data_for_current_speaker)))
        sys.stdout.flush()
        data = np.append(data, data_for_current_speaker, axis=0)
        print data

print("Found data for {} speakers : {}".format(len(class_names), ", ".join(class_names)))

# %%---------------------------------------------------------------------------
#
#		                Extract Features & Labels
#
# -----------------------------------------------------------------------------

# You may need to change n_features depending on how you compute your features
# we have it set to 3 to match the dummy values we return in the starter code.
n_features = 3

print("Extracting features and labels for {} audio windows...".format(data.shape[0]))
sys.stdout.flush()

X = np.zeros((0,n_features))
y = np.zeros(0,)


for i,window_with_timestamp_and_label in enumerate(data):
    window = window_with_timestamp_and_label[1:-1] # get window without timestamp/label
    label = data[i,-1] # get label
    x = extract_features(window)  # extract features

    # if # of features don't match, we'll tell you!
    if (len(x) != X.shape[1]):
        print("Received feature vector of length {}. Expected feature vector of length {}.".format(len(x), X.shape[1]))

    X = np.append(X, np.reshape(x, (1,-1)), axis=0)
    y = np.append(y, label)

print("Finished feature extraction over {} windows".format(len(X)))
print("Unique labels found: {}".format(set(y)))
sys.stdout.flush()


# %%---------------------------------------------------------------------------
#
#		                Train & Evaluate Classifier
#
# -----------------------------------------------------------------------------

n = len(y)
n_classes = len(class_names)

totalPrec =[0,0]
totalRecall = [0,0]
totalAcc = 0

# TODO: Train your classifier!
cv = cross_validation.KFold(n, n_folds=10, shuffle=True, random_state=None)

tree = DecisionTreeClassifier(criterion ="entropy",max_depth=3,max_features=3)

for i, (train_indexes, test_indexes) in enumerate(cv):
    X_train = X[train_indexes, :]
    y_train = y[train_indexes]
    X_test = X[test_indexes, :]
    y_test = y[test_indexes]
    tree.fit(X_train, y_train)
    y_pred = tree.predict(X_test)
    conf = confusion_matrix(y_test,y_pred)
    print("Fold {}".format(i))
    print conf
    totalDiag=0.0
    total = 0.0
    sumCol = []
    prec = []
    sumRow = []
    recall = []

    for x in range(conf.shape[0]):
        totalDiag += conf[x][x]
        total += sum(conf[x])
        sumCol.append((float)(sum(conf[:,x])))
        sumRow.append((float)(sum(conf[x,:])))
        if np.isnan(conf[x][x]/sumCol[x]):
            prec.append(0)
        else:
            prec.append(conf[x][x]/sumCol[x])
            totalPrec[x] += conf[x][x]/sumCol[x]
        if np.isnan(conf[x][x]/sumRow[x]):
            recall.append(0)
        else:
            recall.append(conf[x][x]/sumRow[x])
            totalRecall[x] +=conf[x][x]/sumRow[x]

    acc = totalDiag / total

    print("\n")



# TODO: Output the average accuracy, precision and recall over the 10 folds
    print "Accuracy: ",acc
    print "Precision: ",prec
    print "Recall: ",recall
    print("\n")
    totalAcc += acc


print "Avg Accuracy: ", totalAcc/10
print "Avg Precision: ", [x / 10 for x in totalPrec]
print "Avg Recall: ", [x / 10 for x in totalRecall]
tree.fit(X, y)

# TODO: set your best classifier below, then uncomment the following line to train it on ALL the data:
best_classifier = tree
# best_classifier.fit(X,y)

classifier_filename='classifier.pickle'
print("Saving best classifier to {}...".format(os.path.join(output_dir, classifier_filename)))
with open(os.path.join(output_dir, classifier_filename), 'wb') as f: # 'wb' stands for 'write bytes'
    pickle.dump(best_classifier, f)
