
# Driving Learning Permit Test Application – Alpha Version

This is the Alpha version of a Java-based desktop application that simulates a driving permit test. It provides 20 multiple-choice questions to help users prepare for their actual learning permit exam.

## Project Overview

- Technology: Java SE with Swing
- Architecture: Object-Oriented Design
- Team Members: Darshil and Preet
- Phase: Alpha Version (first working prototype)

## Features Implemented in Alpha Version

- Login screen using a hardcoded user
- Home screen with a welcome message and a "Start Quiz" button
- Quiz screen that displays 20 questions one-by-one
- Score screen to display results after quiz submission
- Backend model classes: User, Question, Quiz, DatabaseManager
- Separated GUI and backend logic for easier future extension

## How to Run the Application

### Compile & Run from Command Line

1. Place all `.java` files in one folder
2. Open terminal in that folder
3. Run:

```
javac *.java
java Main
```

### Or use Eclipse IDE

1. Create a new Java project
2. Add all `.java` files as separate classes
3. Right-click `Main.java` and choose "Run As > Java Application"

## Login Credentials (Hardcoded)

- Username: test
- Password: pass

## Application Flow

1. Run `Main.java`
2. Shows `LoginScreen`
3. On success, shows `HomeScreen`
4. Clicking "Start Quiz" shows `QuizScreen`
5. After last question, shows `ScoreScreen`
6. Return to `HomeScreen`

## Data Handling

- User is hardcoded in `DatabaseManager`
- Questions are hardcoded and shuffled each session
- Answers and score exist only during current session

## Code Structure

### Model Classes (by Preet)

- User.java
- Question.java
- Quiz.java
- DatabaseManager.java

### GUI Screens (by Darshil)

- LoginScreen.java
- HomeScreen.java
- QuizScreen.java
- ScoreScreen.java
- Main.java

## Work Division

- Preet worked on the backend logic (models, data handling)
- Darshil worked on the frontend GUI and flow integration

## Planned for Beta Version

- User registration screen
- Persistent storage (file or database)
- Quiz history per user
- Score tracking
- Input validation and styling
- Support for multiple users

Status: Alpha version – functional, ready for Beta extension
