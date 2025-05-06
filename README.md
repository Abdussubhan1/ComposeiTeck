Iteck Technician Application Flow:

-> Splash Screen for 2 secs
-> Technician Login Screen
-> if Login success then -> Device entry screen --- Else "Technician Not registered"
-> 2 pics take
-> Testing screen
-> Location Check , success if device and mobile is in 100 m radius
-> Battery check in 3 steps i.e., Connected then Disconnected then again Connected
-> Ignition Check (same as battery)
-> Relay mechanism
-> After testing completes, take 2 pics post installation
-> Generate a ticket based on all information
-> Submit all data
-> If submission succeeds then a notification shows and clear memory
-> Last screen plays a lottie animation and a home button to navigate to Device entry screen


Extras :

Every 5 mins mobile location is checked via FireBase service from backend.
Location services (Everytime) is required and cumpolsory
FCM is saved in sharedPreferences.
