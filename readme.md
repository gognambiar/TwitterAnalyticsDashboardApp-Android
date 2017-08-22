Twitter-Analysis-Dashboard-with-Android FrontEnd and NodeJS Backend

App that givesTwitter Analysis on topic of users choice

Android App which contacts Node JS backend.

The NodeJS file is placed in nodejsfile folder.

User is given an option to enter a topic of their choice. When the Analyze button is clicked the App calls a NodeJS Server REST written in backend to fetch the required details. These details are then put in graphs to give a visual analysis done.

Type of Analysis done -

Sentiment Analysis - The sentiments of the tweets pertaining to the topic are analyzed and plotted in a line graph. The types of sentiments are grouped as follows - a. Very Happy b. Happy c. Neutral d. Sad e. Very Sad

Source Analalysis - The Source of tweets are analyzed and plotted in a pie chart. The Types of sources are grouped into these main categories a. Android b. iPhone c. Web Client d. Google

The sentiments library available in nodejs is used to derive the sentiments of the user. GraphView and MPAndroidChart libraries have been used to make the visualizations.

Sample -

![header image](https://github.com/gognambiar/Android-Twitter-Analytics-Dashboard/blob/master/androidtwitter1.png)

Once the user clicks the Analyze Button it goes to the next screen with the visualizations -

![header image](https://github.com/gognambiar/Android-Twitter-Analytics-Dashboard/blob/master/androidtwitter2.png)
