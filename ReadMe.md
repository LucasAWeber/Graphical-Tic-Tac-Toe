# Object Oriented Tic Tac Toe Game Suite

Object oriented game of numerical Tic Tac Toe, and classic Tic Tac Toe with a GUI aswell as classic Tic Tac Toe in terminal

## Description

An object oriented game suite with full GUI. User is able to choose between classic Tic Tac Toe and numerical, saving and loading the board and player stats. Plus many more features including being able to switch games whenever, and being able to restart any game. Player stats saved and loaded as csv file. Prompts user to load player stats on startup and prompts user to save stats when closing game. It loads and saves both player 1 and player 2 stats. Example of playerstats.csv file:
```
15,5,25
5,15,25
  
  ```
Where player 1 has 15 wins, 5 losses, and 25 total games, and player 2 has 5 wins, 15 losses, and 25 total games.

## Getting Started

### Dependencies

* Gradle
* Java

### Executing program

* navigate to the A3 repository:
```
cd A3/
```
* compile program using gradle:
```
gradle build
```
* it will return **BUILD SUCCESSFUL**
* now locate .jar file
```
cd build\libs
```
* use the command:
```
java -jar A3.jar
```

## Limitations

Current state of the program, it doesnt account for incorrectly formatted files very well so it is run with the assumption that the user will load from properly formatted files.

## Author Information

Lucas Weber
