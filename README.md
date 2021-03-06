QR Code Alarm App
----------------

B260 Monday Group 9 User Stories

The priority scale works as follows:
The lower the number, the higher the priority
The higher the number, the lower the priority
ex: 1 is highest, 10 is lowest
The priorities assigned denote how important a task is for the entire application

1. Alarm System
---
As a user that wants an alarm
I want to be able to set an alarm at a specific time
so that I can wake up at that particular time I set
Priority 1

	TASKS:
	- Alarm Creation Activity that allows users to set an alarm
	- Local save storage to store these times so that the app can remember
	- Alarm goes off at this particular time
	- An alarm activity shows up on the screen when the alarm goes off that lets the user know
	"It's time to wake up"

As a user that wants an alarm
I want an alarm that makes noise
so that I can wake up in that instance of time.
Priority 2

	TASKS:
	- Alarm app plays a sound file at max volume

As a user that wants an alarm
I want to be able to snooze the alarm
so that, in case I am not able to wake up immediately, I can still wake up within a reasonable amount of time
from the time I initially set.
Priority 2

	TASKS:
	- Make a button within the alarm activity page that temporarily pauses the alarm for
	a set amount of time.
	- Standard alarms uses a 5 min pause period whenever users decide to snooze
	- Snooze functionality should be enabled, perhaps via some boolean flag, and so this button
	would only be visible when the alarm is active. It would not make sense to snooze when nothing is
	happening otherwise.

As a user that wants an alarm
I want the app to have a finite number of snoozes
so that I do not abuse the snoozes in order to avoid waking up late
Priority 3

	TASKS:
	- Have an integer variable in the alarm activity page that stores the number of snoozes we have
	- This integer variable decrements by 1 when a snooze is used
	- When the integer variable reaches 0, some event should be triggered so that the snooze function is disabled

As a user that wants an alarm
I want the app to make my phone vibrate when the alarm goes off
So that, in addition to the alarm noise, the vibration will also aid in me waking.
Priority 5

	TASKS:
	- The app should vibrate at the same time the alarm is going off

As a customer that wants an alarm app made
I want the alarm to not be scheduled within 1 hour of another set alarm time
So that users that want alarms do not abuse the snooze reward system
Priority 10

	TASKS:
	- In the alarm creation activity there should be a function that checks whether
	the time we want to set is at least 1 hour apart from all the other times that have already been set.
	- This means that there is a limit of 23-24 alarms for every day that could ever be set on any given day.

2. QR Code
---

As a user that wants an alarm
I want to be able to turn the alarm off via QR Code
So that it will help me wake up sooner
Priority 2

	TASKS:
	- Alarm activity should have a button that reads "turn off alarm"
	- This button will lead to a QR-Reader activity, at this point the alarm is still sounding off
	- When the App reads the a valid QR it will turn off the alarm
	- When the App reads an invalid QR nothing should happen (invalid QR code being anything that isn't a QR Code)

As a customer that wants an alarm app made
I want only one QR Code for the app to turn the alarm off
So that it will be more convenient for users of the app, and avoid the hassle of generating QR codes.
Priority 4

	TASKS:
	- Alarm should have only one QR Code that is hard coded that is the only QR Code that is accepted
	by this app

3. GPS
---

As a user that wants an alarm
I want to be able to set my house location within the app
So that, when I am in proximity of my home I can turn the alarm off by QR Code, otherwise, the app will function
as a regular alarm
Priority 3

	TASKS:
	- The app should have an map activity that shows the user's current location, and asks them if this
	is where there home location is.
	- The Alarm activity should have a "Set Home Location" button that leads to this map activity
	- There should be a "Show Home Location" button on the Alarm Activity that pulls up the map, but simply
	shows the user where the current set location is

As a user that wants an alarm, and is at home,
I want the app to phone a friend when I do not wake up after a certain time
So that friend can make sure I wake up on time
Priority 5

	TASKS:
	- The app will require user permission to access phone calls
	- The app will place calls to a friend that has been specified from within the app
	after some time when the alarm goes off.
	- A timer should start counting as soon as the alarm goes off.
	- If the timer is >= the time the app is programmed to wait after the alarm goes off, it will
	phone that friend.
	- The alarm activity should have a button that "sets friend to call" that leads them to activity
	which will populate with the host phone's contact list. The user will select this contact, and this
	contact's phone number will be saved to the app.
	- There should be a button that lets users see who they currently have set as their friend.
	- If no friend is set, this feature will not do anything.

As a user that wants an alarm but is not at home
I want the alarm to not require QR Code, but still call a friend when the alarm does not turn off
So that I can wake up on time when it is not guaranteed I will be near a QR Code.
Priority 5

	TASKS:
	- The alarm activity should trigger another activity to start in the background that checks current location
	- If the location returned is not within proximity (10 meters) of the user's home the app will decide the
	user is not at home
	- The button to turn off alarm will simply shut the alarm off instead of going to the QR-Reader Activity, a
	boolean flag should be used in this case to decide whether or not the button goes to an activity, or simply
	shuts the alarm off.
	- The app calling a friend feature will be unaffected.

4. Phone a friend
---

As a user that wants an alarm
I want the app to call a friend after some number of snoozes
So that friend can call me to ensure I wake up
Priority 5

	TASKS:
	- Using the integer variable that keeps count of the snoozes should also be used to determined whether the
	app calls a friend.
	- In addition to the snooze button becomed disabled, the same function that turns this feature off should be used
	to trigger the "call friend" event, assuming it is set.

5. Store
---

As the customer that wants the alarm app made
I want snooze credits to be generated when the user wakes up on time
So that it will reward users and motivate them to wake up on time
Priority 5

	TASKS:
	- Snooze credits are generated when the user wakes up on time. This is defined when an alarm instance is triggered
	in which the alarm sounds off, if no snooze is used and if it does not call a friend, then some snooze credit is
	generated.
	- Credits are to be stored in a double variable, because snoozes will be generated partially, for example a quarter
	or .25 of a snooze credit may be generated, or .333 or a third of a snooze credit may be generated.

As the customer that wants the alarm app made
I want a store page menu in the app
So that users may purhcase snooze credits from within this app. 
Priority 9

	TASKS:
	- Button in the main alarm activity that leads to a store activity page.
	- Snoozes may be purchased here when the user presses a "buy snooze(s)" button using "Snooze Credits"
	- These snoozes bought will be credited to the snooze int variable in the alarm activity.
