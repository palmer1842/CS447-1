Cops and Robbers - A 2D Arcade Game written by Jake Palmer
CS447 - October 21, 2019

Controls:

In Game:
W - Forward
A - Turn left
S - Reverse
D - Turn Right

In Menu
A - Select Cop game mode
D - Select Robber game mode
Space Bar - Transition screens when prompted

Cheat Codes:
T - Toggle Path finding overlay
L - Skip to Level 2

Low Bar Items Status:

1. Grid Based Road System - Complete
2. Vehicle Entity         - Complete
3. Robber Game Mode       - Complete
4. Cop Game Mode          - Partially Complete
5. Neutral Vehicles       - Partially Complete
   and Obstacles
6. Scoring System         - Partially Complete

All of the low bar items have some presence in the game, however the neutral vehicles and scoring system are not as
complete as I would have liked. The player and computer drive through the neutral vehicles, and the scoring system
does not consider as many variables as I had originally planned. The Cop game mode could be considered 'complete' in
that it is a playable game, but the Robber AI is not as advanced as I would have liked. It only drives to the safe
house, without avoiding the cop.

Bugs:

If you reverse and then immediately go forward, images aren't removed properly and you see two different orientations.

The AI doesn't know how to turn around, so if the path finding says to go in the opposite direction, the AI will ignore
that and probably get stuck on a wall.
