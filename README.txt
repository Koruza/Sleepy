USleep
Emily Yu, Ka Wo Fong, Stanley Lok

This app uses the built-in ambient light sensor on Android phones to determine
and keep track of the amount of time that a user uses their phone when they are
about to sleep. There is also a history page to keep track of each phone use 
session.

We used taxCollector.py to collect light and dark data to determine whether the 
user is using their phone in the dark (and presumably at night). We used 
U-train.py to train the data collected and create a classifier file using pickle.
The features we used are defined in Usleepfeatures.py. Finally, we used 
usleep-recognition.py use the classifier and send the classification back to 
the Android side and perform the associated operations on the app.

In order to get the app to work, you must run the usleep-recognition.py script 
found in the scripts directory. 