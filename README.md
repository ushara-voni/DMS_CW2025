# Tetris
This project is a JavaFX implementation of the classic Tetris game.
## GitHub Repository
https://github.com/ushara-voni/DMS_CW2025.git

## Compilation Instructions
* This project uses JavaFX and Maven  
* Before compiling ensure the following is installed:
  * JDK 17 or higher
  * Maven 3.8 +  
* Compile the Project: `mvn clean compile`
* Run the Game: `mvn javafx:run`

## Project Structure
```bash
DMS_CW2025/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/comp2042/
│   │   │       ├── Main.java
│   │   │       │
│   │   │       ├── Controllers/               
│   │   │       │   ├── MainMenuController.java
│   │   │       │   ├── GuiController.java
│   │   │       │   ├── GameController.java     
│   │   │       │   ├── GameOverController.java
│   │   │       │   └── InstructionsController.java
│   │   │       │
│   │   │       ├── logic/                      
│   │   │       │   ├── core/
│   │   │       │   │   ├── TetrisBoard.java
│   │   │       │   │   ├── BoardGrid.java
│   │   │       │   │   ├── ClearRow.java
│   │   │       │   │   ├── Score.java
│   │   │       │   │   └── ViewData.java
│   │   │       │   │
│   │   │       │   └── bricks/
│   │   │       │       ├── Brick.java
│   │   │       │       ├── BrickFactory.java
│   │   │       │       ├── BrickManager.java
│   │   │       │       ├── MatrixOperations.java
│   │   │       │       └── TetrominoType.java
│   │   │       │
│   │   │       ├── Infrastructure/
│   │   │       │   ├── Audio/
│   │   │       │   │   └── MusicManager.java
│   │   │       │   └── HighScoreManager.java
│   │   │       │
│   │   │       └── UI/                         
│   │   │           ├── Renderers/
│   │   │           │   ├── BoardRenderer.java
│   │   │           │   ├── BrickRenderer.java
│   │   │           │   ├── PreviewRenderer.java
│   │   │           │   └── RendererManager.java
│   │   │           │
│   │   │           ├── PauseManager.java
│   │   │           ├── NotificationPanel.java
│   │   │           ├── BrickPositioner.java
│   │   │           └── GameLoop.java
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── MainMenu.fxml
│   │       │   ├── gameLayout.fxml
│   │       │   ├── GameOverScreen.fxml
│   │       │   └── Instructions.fxml
│   │       │
│   │       ├── audio/                         
│   │       ├── images/                         
│   │       └── style/
│   │           └── style.css
│   │
├── README.md
└── pom.xml   

```





## Implemented and Working Properly


### GamePlay Mechanics
* **Hard drop**: Instantly drop the brick to the lowest position when space bar key is pressed
* **Hold piece**: Store or swap the currently active brick
* **Score** : There was a bug in the base code where score increments when down arrow was clicked. Now score increments correctly and is display on screen
* **Level progression**: Game speed increases after a set number of lines is cleared. Also shows notification of level up
* **Game over detection**:There was a bug in the base code where game overcomes in the middle of the board. Now gameplay stops when bricks reach the top of the board.
* **High Score**: High Score of player is saved and reloaded whenever a new game is started . WHen new high score is reached high score is updated on screen
* **Next Brick Preview** : shows preview of the next brick to player
### User Interface 

* Notifications: for score bonuses and level ups
* Ghost Brick: shows where the brick will land 
* Pause / Resume : button toggles and icon updates

### Audio Features
* Background music plays during gameplay
* Sound effects for button presses, score bonuses and game over

### Screens
* **Main Menu**: the first screen player sees. Consists of Play, Instructions, Exit buttons
* **Game Screen**: The tetris board with resume/pause , home ,restart buttons above the board and next piece preview,score,highscore,hold brick components on the side of board
* **Game Over Screen**: The last screen the player sees. Consists of Home, Restart, Exit buttons and Score and HighScore.
* **Instructions Screen**: Shows the player instructions to play the game


## Features Not Implemented
* **Settings** : To change the volume of music or change keys settings for game mechanics
* **Save/Load Game**: No functionality to save the current game state and resume later
* **Power-ups** : No power-ups to improved and have unique gameplay 

## New Java Classes

### `MainMenuController.java`
* Handles main Menu interactions including Start, Instructions and Exit buttons
* Integrates with `MusicManager` to play sound effects and background music
* Connects to Main class for scene navigation

### `InstructionsController.java`
* Handles user interactions on the instruction screen
* Provides method to navigate back to the main menu
* Integrates `MusicManager` for button sound effects

### `GameOverController.java`
* Displays final score and high score
* Handles restart , return to menu and exit actions
* Integrates `MusicManager` for button sound effects

### `InputHandler.java`

* Handles all keyboard input for the game
* Maps keys to game actions: move, rotate , hard drop, hold, new game
* Uses callback functions to update GUI and communicate with InputEventListener

### `Brick.java`
* Represents a single Tetromino brick
* Holds a reference to its TetrominoType(shape and rotations)
* Tracks the current rotation index(0 -> last rotation)
* Provides the correct shape matric for the current rotation
* Does clockwise rotation of brick
* Can reset rotation when a new piece spawns

### `BrickFactory.java`
* Responsible for creating Tetromino brick objects
* Centralises object creation to keep brick generation consistent across the game
* Helps avoid repeating new Brick(...) logic in multiple places
* Used during gameplay to spawn new pieces

### `TetrominoType.java`
* Enum representing all 7 official Tetris pieces
* Each type defines: 
   * A set of rotation matrices(1-4 orientations)
   * A colour used when rendering the block
* Matrices use integers to indicate filled cells(0 = empty)
* Rotation states vary based on their shape
   * I, S, Z -> 2 rotations
   * T, L, J -> 4 rotations
   * O -> 1 rotation 

### `BrickManager.java`
* Manages gameplay logic of Tetris bricks
   * Current falling piece
   * Next preview piece
   * Hold piece feature
   * Movement and rotation and collision handling
   * Locking bricks into board grid
* Spawns new bricks from `BrickFactory`
* Tracks and update brick offset(x,y position)

### `BoardGrid.java`
* Represents the Tetris board matrix where bricks are stored permanently
* Handles row clearing, grid resetting and merging landed bricks

### `BoardRenderer.java`
* Handles visual rendering of the Tetris board using JavaFX
* Manages a grid of Rectangle objects representing each cell
* Maps tetromino IDs to their associated colors for display
* Keeps UI rendering separate from game logic

### `BrickRenderer.java`
* Renders both active falling pieces and their ghost projections on the board
* Renders the falling brick based on its shape and rotation
* Compute and render ghost piece(shows where the tetromino wil land)
* Maintain opacity and style for ghost and active piece
* Provide color mapping for tetromino IDs via `TetrominoType`

### `PreviewRenderer.java`
* Renders both previews of tetrominoes for Next Piece and Hold Piece panels
* Uses JavaFX GridPane and Rectangle for visualisation
* Clears previous preview before rendering new piece
* Handle null input by clearing the panel

### `RendererManager.java`
* Integrates all Tetris UI rendering components into a single manager
* Coordinates:
  * BoardRenderer
  * BrickRenderer
  * PreviewRenderer
* Initialise all rendering components
* Refresh the main board and background
* Render falling and ghost tetrominoes
* Render next and held tetromino previews
* Encapsulate rendering logic for simpler game controller integration

### `BrickPositioner.java`
* Utility class for calculating and updating the pixel position of a falling or ghost tetromino panel in the game UI

### `GameLoop.java`
* Manages the main game loop of the Tetris game.
* Uses JavaFX `Timeline` to repeatedly execute game ticked events at a fixed interval
* Initialises a `Timeline` with a `KeyFrame` that calls a `tickHandler` at every interval
* Supports starting, stopping , pausing and resuming the game loop
* Allows adjusting the game speed through a playback rate multiplier

### `NotificationManager.java`
* Manages and displays in game notifications such as score increments and level ups in the Tetris game
* Dynamically shows score notifications on the screen
* Dynamiccally shows level up notifications on the screen
* Uses `NotificationPanel` for creating and animating notification visuals.

### `PauseManager.java`
* Handles the pause functionality for the Tetris game
* Tracks the current pause state
* Allows toggling pause via `togglePause(Button btn)`
* Can explicitly set pause state using `setPaused(boolean value)`
* 
## Modified Java Classes

### `Main.java`
* Added navigation methods(`showMainMenu()`,`showGameScreen()`,`showGameOverScreen()`,`showInstructions()`)
* Main Menu handles switching between Main Menu , GameScreen,Instructions and GameOver
* Passed `Main` reference to controllers for better UI management 
* Updated window size and disabled resizing 
* removed direct `GameController` instantiation from Main(prevents gameplay from starting before user interaction)

### `GameController.java`
* Added support for hard drop, hold piece , level progression, line counter, speed increas ,next piece preview and UI animations
* Introduced audio management using `Music Manager`
* Integrated properties(IntegerProperty) for score/lines/level to bind with UI
* Implemented brick lock in logic and gameover signaling through GUI
* Added polling for cleared rows

### `GuiController.java`
* Refactored GUI to use `RendererManager`,`NotificationManager`, `GameLoop` and `PauseManager` for modular rendering and control
* Added support for next piece preview , hold piece and ghost piece rendering
* Added score, lines and level binding to UI labels 
* Integrated high score display and live updates
* Added audio using `MusicManager` for buttons and notifications
* Improved input handling with `InputHandler` for soft drop, hard drop , hold and rotation.
* Added pause/resume system with dynamic button icons
* Integrated scene switching via Main for menu/ game over screens
* Added level up notifications and game speed adjustments
* Before GuiController handled everything manually (ie drawing rectangles , input , gameloop and notifications in one class)
* Now responsibilities are split into dedicated manager classes

### `EventType.java`
* Added new enum values `HARD_DROP` and `HOLD`

### `InputEventListener.java`
* Added new methods: `onHardDropEvent()`,`onHoldEvent()`, `pollLastClearRow()`, `getBoardMatrix`,`getHeldBrickMatrix()`
* Updated `onDownEvent` to return ViewData instead of DownData

### `SimpleBoard.java`
* Changed name of file to TetrisBoard
* Uses `BoardGrid` to track blocks
* Uses `BrickManager` for current falling brick and hold mechanics
* Tracks score with `Score`
* Supports hold piece functionality via `holdBrick()` and `getHeldBrickMatrix()`
* Clears rows and returns `ClearRow` info
* Provides `getViewData()` for current falling brick
* Supports `newGame()` to reset board and score

### `NotificationPanel.java`
* Addition of `showLevel` for level up messages
* Enhanced styling and position

## Deleted classes
* From logic.bricks : `IBrick`,`JBrick`,`LBrick`,`OBrick`,`RandomBrickGenerator`,`SBrick`,`TBrick`,`ZBrick`
* `DownData.java`
* `NextShapeInfo.java`
* `GameOverPanel.java`
* `BrickGenerator.java`
* `Brick.java`(interface)
* `Brick Rotator.java`

### Individual Brick Classes(`IBrick`,`JBrick`,`LBrick`,`OBrick`,`SBrick`,`TBrick`,`ZBrick`)
* Previously , each Tetromino type had its own class
* This caused lots of repetitive code for rotation logic, color and matrix representation
* **Refactoring**:Now all bricks are represented by `Brick and TetrominoType` with `BrickFactory` handling creation
* **Benefit** : centralised logic ,less duplication and fewer files

### `Brick.java`(interface) and `BrickRotator.java`
* Interfaces and separate rotation classes were overcomplicating the design
* **Refactoring**: rotation logic moved into the Brick class itself and controlled via `MatrixOperations` and `BrickManager`
* **Benefit**: simpler and single source for each brick's state and rotation

### `RandomBrickGenerator.java` and `BrickGenerator.java`(interface)
* Changed to a factory pattern for brick production
* **Refactoring** : Centralised in `BrickFactory` to implement the Factory Pattern, so all brick creation is consistent

### `DownData.java` and `NextShapeInfo.java`
* These were data holding classes for gameplay events
* However `ViewData` already exists to handle data holding
*  **Refactoring** : Replaced by `ViewData`

### `GameOverPanel.java`
* When changing UI , GameOverPanel was not required anymore
* **Refactoring** : replaced with `GameOverController`

## Changes made to pom.xml
This project's `pom.xml` has been updated to support full JavaFX functionality and audio playback.Additions are:
* JavaFX Base module
* JavaFX Graphics module
* JavaFX Media module(audio)
* Full JAvaFX module list in plugin

## Unexpected Problems

### Score Incrementation issue
* There was a bug in the original code where the score incremented when down arrow was pressed
* This was cause due to GameController `onDownEvent()`
* Removed :   `if (event.getEventSource() == EventSource.USER) {
  board.getScore().add(1);
  }`

### Game Over happens early issue
* There was a bug in original code where game over happens too quickly
* Change the y value the `createNewBrick` method that was in `SimpleBoard` class originally.

### Grid and Brick Misalignment
* In the UI, the falling brick and board grid were not aligned properly 
* This could confuse the player in terms of which row and column the brick is actually positioned
* This was caused due to rendering issues and CSS
* There was inconsistent `Hgap` and `Vgap` values
* CSS `window_style.css` had padding around the borders which added more to the misalignment
* How it was solved
  * Ensured CSS did not add padding/margins that modify the cells in grid placement.
  * `#gamePanel {     
    -fx-background-color: #1d1d1d;
    -fx-padding: 0;
    -fx-hgap: 0;
    -fx-vgap: 0;
    -fx-border-color: #2e2e2e;
    -fx-border-width: 0;
    }
`
### Hold Brick not working correctly
* When implementing the Hold system ,several issues appeared:
  * The held brick sometimes kept its old rotation
  * When restarting the game sometimes the held brick from previous game will still be there
  * Swapping the current with the held brick cause inconsistent shapes
  * After using hold once,holding again in the same turn still worked(which should not happen)

* How it was solved
  * Reset rotation whenever a brick becomes the current brick `brick.resetRotation()`
  * Track hold usage per turn with `holdUsedThisTurn = true`
  * Prevent further holds until a new brick spawns

### Next Brick System Inconsistent
* Although `getNextBrick()` existed, the game sometimes:
  * Generated anew random brick instead of using the stored next brick
  * Used different sources for ghost brick ,spawning and rendering
  
* How it was solved
    * Use on single source of truth for the next brick :` public Brick getNextBrick(){return next;}`
    * When spawning:
      * Use next as the current brick
      * Generate a new next brick immediately
      * Reset rotation of the spawned brick
      * Example
        `current = next;
        current.resetRotation();
        next = brickGenerator.generateRandomBrick();`
      * This ensures:
       * Predictable next-brick previews
       * Ghost brick always uses the correct shape
       * No mismatched bricks during swapping or rotation
      
### Falling 'I' shaped tetromino issue
* The falling I shaped tetromino sometimes only displayed 3 blocks instead of 4. 
* This happened because the FallingBrickRenderer was initialising its rectangle grid based on the current brick's rotation matrix size 
* But I brick uses a 4 x 4 matrix. As a result , I block was just 3 blocks 
* How it was solved
  * Initialised the rectangles array to the maximum tetromino size(4 x 4)
  * `private static final int MAX_BRICK_SIZE = 4;`
  * `rectangles = new Rectangle[MAX_BRICK_SIZE][MAX_BRICK_SIZE];`
  
## Design implementation

### Factory Pattern
The `BrickFactory` class implements the Factory Design Pattern to centralise the creation of all Tetromino bricks used in the game.
Instead of instantiating `new Brick(...)` across multiple classes , the factor ensures a single , consistent place responsible for
* Constructing bricks based on a given `TetrominoType`
* Generating random bricks during gameplay

### Facade Pattern
The `RendererManager` provides one simple interface for all rendering operations
Internally it delegates work to multiple subsystems:
* `BoardRenderer`
* `BrickRenderer`
* `PreviewRenderer`

### Observer Pattern
The `InputHandler` implements the Observer pattern by:
* Observing a source of events
* Reacting to those events 
* Notifying other components(game logic and UI) about these changes

### Singleton Pattern
* The `MusicManager` uses the Singleton Design pattern to ensure the game has a single global controller for all audio
* It provides:
  * One shared instance of the background music player
  * Guaranteed prevention of overlapping soundtracks
  * Centralised control for:
    * `playBGM()`
    * `stopBGM()`
    * `setVolume()`
    * `playSFX()`
    

  