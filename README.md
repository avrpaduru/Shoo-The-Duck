# Shoot-the-Duck-in-Java
A GUI game developed in Java using swings. The goal of this application is to use GUI of java using swings to develop a simple game. In the game, there will be random animals (cat, dog, duck etc.,) will be displayed in a random position for each 0.75/1/1.5 seconds of time period based on the difficulty choosen. A player has to shoot only the duck, shooting any other animal or missing duck 5 times will end the game. Shooting in this case is a mouse click on the frame. The game will last for 2 mins if you don't miss duck 5 times and don't shoot animals other than duck. Score is equal to the number of ducks that you shot. At the end of the game, a badge (bronze/silver/gold) will be displayed based on the number of kills.
# Panels
1) Start Panel: This displays Shoot the Duck name and also shows the pictures of the animals that I have used in the game. You can skip the animation by clicking on the skip button at the bottom.
2) UserName Panel: This panel collects the user name at the bottom and displays the instructions in the middle (There will be dropdown box on the top to select the existing users). The image of the target animal(duck) is displayed at the center of the panel. Below the username, you will be asked to choose the difficulty of game, you need to choose easy/medium/hard(easy is selected by default). Based on the difficulty, the time period to change the image is selected, for easy: image of  an animal in the game is changed for each 1.5 seconds, for medium it is 1 second and for hard it is 0.75 seconds.
2) Game Panel: This is the panel where a user can play the game.
3) Result Panel: This panel displays the badge that a player has gained based on his number of kills, displays players previous score and current score in the corresponding difficulty level.
4) UserDetails; This panel displays all the existing users and their corresponding socre for each difficulty level.
# How to play the game
1) You need to download jdk and set the classpath in your environmental variables. You can google it or search it in youtube to install and setting up classpath, below is a youtube link that might be helpful to you:
https://www.youtube.com/watch?v=7OhO91g9ID0

I was using JDK8 when I have developed it.

2) Download the game folder named "Shood the Duck" and go to the game folder(Shoot the Duck) and double click on the "Game.bat" file to run the game. Alternatively, you can go to the corresponding directory from you command promt and then you can execute the following command in your command promt:
java -cp ./classfiles GameFrame
# How to remove existing users
You can remove all the existing users by deleting the users.ser file in the Shoot the Duck folder. You cannot remove a single player in this game, you can only remove all the users or none. Users.ser file will be visible when atleast 1 player plays the game.
