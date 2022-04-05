# MyDiabetesDiary
Final project for CS50:Introduction to Computer Science from HarvardX called My Diabetes Diary (https://www.youtube.com/watch?v=b31_2KGM2A0). 
I decided I wanted to build and app because as a type 1 diabetic I found that the apps 
I was using to track my diabetes information were alwalys missing something or not quite what I was looking for. My app has 3 sepearate screens, 
the first is a note taking function, the second a log for my insulin pump settings and the third is graphs. The notes taking function has an add 
and delete featrure with new notes being set to the top of the screen. If a note is clicked it brings you to a screen where you can edit or add 
information regarding your blood sugars, insulin intake and other information. The information entered in the note is saved into a SQLite database 
using the Room function in Android Studio. When the note is exited the information is automatically saved into the database.On the main notes page
I have also set up a swipe to delete function. If the user swipes from right to left, a snackbar appears at the bottom of the screen asking the user
to confirm if they would like to delete the note. On the sescond menu item, the pump settings,a user could input their insulin pump settings as a 
way to have a backup of this information if the pump was lost, stolen or no longer functioning. The total Basal (background insulin,not for meals) 
is calculated based on the input times and rate of insulin per hour. The ISF(insulin sensitivity factor) is also calculated so that a user could 
compare to what they are currently using and adjust if needed. On the third menu item, graphs (graphs are from the AndroidPlot inplementation), there
are currently 3 graphs and some average values. The first graph is a scatter plot of time versus blood glucose. This informaton is taken directly
from the database where notes are stored and can help spot trends of high or low blood sugars in order to adjust insulin intake if needed. 
The second graph is a pie chart that shows what percent of the time a user is in an ideal range for blood sugar, this information is taken from 
the notes database as well. The third chart takes information from the pump settings chart on the second menu item as well as the averge 
bolus(meal time inuslin) that is calculated on the top of the page.
