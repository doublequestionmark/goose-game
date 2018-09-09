# goose-game
goose-game is a Java console application that implements the [Game of the Goose](https://en.wikipedia.org/wiki/Game_of_the_Goose).

## Running the application
1. Navigate to the 'deploy' directory of the project
2. Run start.bat (Windows) or start.sh (Linux)

## Using the application
Available commands:
- **add player playerName** : add a player, called _playerName_, to the game (example: add player Bob)
- **start** : start the game
- **move playerName x, y** : the player called _playerName_ gets _x_ and _y_ from the roll of the dice and then updates his position (example: move Bob 3, 4)
- **move playerName** : the player called _playerName_ asks the game to roll the dice for him and then updates his position (example: move Bob)
- **exit** : the game ends

The minimum number of players is two. The goal of the game is to reach the number sixty-three before the other players. When a player reaches the number sixty-three, he wins and the game ends.
