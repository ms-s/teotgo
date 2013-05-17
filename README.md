TimeEdit on the Go (TeotGo)

I wrote this Android app back in the day (2011) as a class assignment for the Mobile Development course. This application was written partly from a personal need. At KTH, all course schedules are maintained in a system called TimeEdit. TimeEdit is a web portal where students can search for a schedule of their courses. The schedule can then be saved as HTML document for future use or it can also be downloaded in iCal-format. The iCal file can then be imported to different calendars like Google and Microsoft Exchange Calendar.  Many smartphones (Android phones, iPhones) can nowadays synchronize their native mobile calendar with the user’s online calendar. Therefore, when the online calendar is updated with a new course, the phone calendar will also be synchronized with the same content. This is only currently available way to store course schedules into a mobile calendar, besides manually entering all the schedule’s events to the mobile calendar, which is time consuming and inefficient.

However, what I wanted was a bit more customization than the standard way of adding course schedules. The problems were twofold (this was in 2011, I think over the years TimeEdit has improved): first, you need to add one course, import it and then do the same procedure for every course henceforth; second, you cannot automatically add a reminder to your phone for each course. Besides, I wanted a reminder for 15 minute before the courses which were held in the campus close to my place and 40 minutes for the courses which were held at the campus a bit farther away. Therefore this app. The app doesn't have the fanciest of UI's and probably doesn't handle all error conditions. But the idea was to learn as many features of Android as possible within a few weeks. 

What this application does: Basically on the first screen you can search for course in a textbox. It uses a few php scripts (thanks to Amy Skinner for them) in the backend to interact with the online KTH TimeEdit system. While searching for course and parsing the XML data, it uses multithreading and dialog boxes so that the application doesn't seem to hang. You can see this in the screenshots below:

![Alt text](/screenshots/1.png) ![Alt text](/screenshots/2.png)

Using Android Message Handler for inter-thread communication, it uses a multi-choice alert dialog to show a list of courses received from the PHP scripts that match the searched string. The user chooses the appropriate course(s) as shown in the screenshots below:

![Alt text](/screenshots/3.png)

After this, the user is shown the selected courses in a custom list view where the user can add more courses and remove previously added courses. Once the user is sure about the course selections the user completes the selection and moves on to the next step. This is shown in the screenshot below:

![Alt text](/screenshots/4.png)

The user is finally shown all the course selected previously with an option to store them in the calendar under a different name and optionally add reminders of varying durations for the courses. The user can, at this stage go back and add or remove courses selected previously. Once the user fills out the details and submits, all the course events are added to the calendar and the appropriate selected reminders are added as seen in the screenshot below:

![Alt text](/screenshots/5.png)

What to do to make this application run:

		1. Get the code and import it into eclipse.

		2. This android app for written for Android 1.6 Donut. So you will need those Android dependencies.

		3. This app uses XStream 1.3.1 ( http://xstream.codehaus.org/ ) and MXP1: XML Pull Parser (XPP) edition 3 ( http://grepcode.com/snapshot/repo1.maven.org/maven2/xpp3/xpp3_min/1.1.4c ) so you will need to include them in the project dependencies.

		4. Upload on a phone and run. 
	

While writing this app, another challenge was, how to test this app on the emulator. The android emulator does not have a calendar app that you can use. So i followed this neat trick to get the calendar working on an emulator (http://techdroid.kbeanie.com/2009/11/android-market-on-emulator.html). Also, I didn't want to test this app while debugging on my regular android phone with my regular gmail account so I got another gmail account on the phone. 
	


