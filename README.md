Scrabble Game

This game is played among computer and a player with matching words placed.
Human makes the first move and computer places words matching the word placed by human or creates a new word.

Start the game:
Run Main method to start the game. The Main consists of Start method which uses all the other classes to set the game on action.

Play the game:
The game starts when "Play" button is pressed. Place each alphabet on the board to create a word and press "Enter". The GUI shows your score, computer score and the tiles remaining.
Computer then places word on the board to create a matching word from the dictionary. Again, "Play" button activates users turn. The game goes on.

Main:
Run main to start the game. Main consists of start method. It consists of two button human tray, GUI board and the module for score. All the classes and methods are used when the game is played. So, it is the main component of the game.

Player:
The player class consists of tray, logic, findPosition, findWord. The tray is assigned to the player when the instance is created from which the player chooses the word to place it in the game board. It consists of getter and setter method to find the position of the tray, word, and score. It also has ComputerPlayer which creates the computer player.
Board:
The board consists of methods to create board, word, and get the co-ordinate of the word. It also checks for the legal/illegal move. The createBoard method creates the board, setHumanWord assigns the word to the human. getHumanIndex returns the index of the word, isNewChar checks for the new character to be played. isLegalMove determines the move of the player. rotateBoard checks for the horizontal/vertical match words. updateBoard updates the board with the character played into the GUI. getTotScore gives the total score of the board. getScoreWord gives the score of the word.

Trie :
The trie class is for the dictionaryReader to read the dictionary. The trie class consists of inner class : Node. Trie consists of add method to add the word, search for the word and then starts with also search for the prefix. getRoot gets the node of the trie. The inner class node creates a node of 26 alphabets.
DictionaryReader:
It reads the dictionary using the trie class.

DisplayScore:
Tray class creates a tray in the GUI for the computer score, your score and the remaining tiles. It helps to add up the score and informs player about their moves.

Tray:
It reads the board and store the value of the character in array list. It gives the human a tray with 7 alphabets to create a word. getScore get the score of the character. createFullTray creates a hundred character list. Draw method takes an integer and draws integer from boneyard. addChar adds a character.

Trayclass:
It consists of draw method which displays the human word tray and sets to action. It has getPlayed which returns the play of the user. setFinalChar sets the final character played.
GameManager :
GameManager uses various classes and gives main idea of the game. Human provides the information and the GameManager verifies the board and make the game ready to be played. It coordinates and assigns human and computer the words and checks for if it matches. Then, in the computers turn it places a word and creates a matching word to score. It determines the winner if the board is empty.

Logic:
It is the main logic of the game. It finds all the possible words from the dictionary using the computer characters. It checks for the left/right character placed in the game board. Logic takes board and the dictionary and assigns the score to the board. getScore is for the score calculation. setTray sets the tray for the computer.


GUI:
It is the representation of the game through the board with different components using the graphic context 2D of canvas. draw method draws squares in the board consisting of different sets of colors for different multiplier.

Problems/Bugs:
It does not always run from the beginning. There is an issue while setting the game to play. Illegal move message is displayed, and vertical arrangements are not allowed in the game for human. You can start horizontally. It also shows error showing the remaining character of the boneyard.  






