ReadMe.txt
-----Boone and Perdue Adversarial Game Team Project ReadMe file-----
HOW TO COMPILE:
1) On a *nix terminal, confirm that java is installed and is version 1.8.0_45 or later.
	+Note that empress virtual servers are currently equipped with version 1.8.0_45.
		The version can be found on most consoles by typing,
			java -version

2) While in the /src/ folder, type
	javac AdversarialGame.java

HOW TO RUN:
+Note: It is reccommended that the active terminal window be maximized to avoid 
	text wrapping when the program starts.

1) Once compiled, enter the following command in the terminal.
	java AdversarialGame

HOW TO PLAY:
1) Once the game is running, the game will display a logo and a large body of 
	text detailing the nature of the game. You will be prompted to specify a 
	game mode.
2) Choose a game mode. 
	PVP allows two humans to enter move commands until one 
	is defeated.
	PVA allows a human player to challenge an Agent. Human is Player1 and 
		takes the first turn.
	AVA displays the outcome of two agents playing against each other.

	ENTER A MOVE:
	3) If PVP or PVA is selected, enter a move in the format: 
		row column [ direction_of_movement | direction_of_rotation ]
			-there is a space between each of the three commands
			-row and column are integers 1-4, indicating the location of the piece intended to move.
			-the third argument can be EITHER
				+ cardinal direction (up, down, left, right, u, d, l, r)
				+ rotation (cc, c) where cc represents counter-clockwise and c represents clockwise.
		+Example: 	Rotate P1 to face right. = "4 1 c"
					Move P1 up one space. = "4 1 u"

	