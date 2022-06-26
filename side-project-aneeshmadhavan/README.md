Welcome to the Expenses Manager!
============

## About the App

The expenses manager is a simple Web Application made primarily for students (can be freely used by others as well!) 
to help them keep track of their day-to-day expenditure. The app supports login with Slack to keep the authentication process simple and secure.
Each user can log their expenses by adding an amount, a description and a category for each of their transactions that they can view 
in a table which displays information in a manner which is easy on the eye. Users can also analyze their spending by generating a 3 Dimensional pie chart
which shows the expenditure by categories to help gauge where and how the money is being spent. The app also gives alerts
when a user goes below $50 serving as a reminder to either update their income or slow down on their expenditure.

## How to use the App

1) The home page of the app.

![Screenshot](/images/home.png)
2) Clicking the button on the top right, you can Sign in with Slack.

![Screenshot](/images/slack.png)
3) You will be asked to enter an income the first time your account is created.

![Screenshot](/images/income.png)
4) You will then be redirected to your Workspace after signing in.

![Screenshot](/images/workspace.png)
5) You can log your expenses by adding an amount, a description and a category from the available ones for each entry.

![Screenshot](/images/expense.png)
6) Each entry will be added to the database and will be visible in your workspace.

![Screenshot](/images/updatedworkspace.png)
7) Once your balance goes below $50, you'll be issued with a warning.

![Screenshot](/images/alert.png)
8) You can click on the 'Analyse Expenses' on the top right of your workspace to see a 3D pie chart with distribution
of the expenditure by category. Could be interesting to see how your chart compares to your peers!

![Screenshot](/images/piechart.png)
10) Finally, when you're done using the app, click on the logout button to exit the app!.

## Architecture and Implementation Details

The Expenses Manager is a two-tier web application with a Java (Jetty/Servlets) front end and an MySQL backend.
Each feature like "Enter Expense" or "Expense Analyzer" has a dedicated Servlet that processes the incoming requests
and serves content or redirects to other servlets accordingly. The MySQL backend has two tables storing the user and expenses
information respectively. Apache DBCP Connection pooling is used to interact with the database from the Servlets. HTML is
served using the Thymeleaf template engine. The app has been tested by having multiple users on the server simultaneously
using their workspaces and observing the database behaviour carefully.

