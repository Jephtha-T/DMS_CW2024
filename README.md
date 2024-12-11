# DMS Coursework 2024 (Sky Battle)
  By Jephtha Ashter Tandri, 20600677  
  [Github Repository Link](https://github.com/Jephtha-T/DMS_CW2024.git)
---
## Compilation Instructions:
To compile and run the project, follow these steps:

1. Ensure **Java (JDK 17 or above)** &  **Maven** is installed on your system.
  - Verify Java & Maven installation by running the following command in a terminal:
    ```bash
    java -version
    mvn -v
    ```
  - If Java is not installed, download Java from [Oracle's JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
  - If Maven is not installed, download Maven from [Apache Maven](https://maven.apache.org/download.cgi).

3. Navigate to the project's root directory using a terminal.

4. Compile and run the project using Maven:
    ```bash
    mvn clean javafx:run
    ```

---

## Newly Implemented & Working Features:

**Main Menu Screen**
- Play Arcade Button (Level One)
- Play Endless Button (Endless Mode)
- Exit Button

**Pause Function & Menu**
- Press Esc to Access
- Resume Button
- Main Menu Button
- Exit Button

**Impact VFX**
- Destroyed Planes display an explosion animation

**Tutorial Screen**
- Displays at the start of Level One & Level Endless (Press Enter to Continue)

**Limited User Fire Rate**

**Scoreboard**
- Clearly shows kills per level and kills needed to go to the next level

**Items** (Items to be picked up by UserPlane for power-ups):
- ShieldItem (Invulnerability Shield for User from projectiles)
- Multishot (Shoots 3 projectiles instead of one)
- Health (Restore 1 heart)

**Music & SFX**
- Background Music
- Win Sound Effect
- Explosion Sound Effect
- Projectile Fire Sound Effect
- Game Over Sound Effect
- Level Start Sound Effect

**Different Enemies**
- Bomber (Moves vertically with slower projectiles)
- Charger (No projectiles, instead rushes horizontally)

**More Levels**
- Endless Mode
- Level Two before Boss Level


---
## Game Resources

Shield Image - https://seikio.itch.io/itemazing-tileset 

Explosion & Shield Effect - https://codemanu.itch.io/vfx-free-pack

Keyboard - https://cazwolf.itch.io/caz-pixel-keyboard

Level One & Two BG - https://ansimuz.itch.io/cyberpunk-street-environment

GameOver & WinGame BG - https://deep-fold.itch.io/space-background-generator

User Projectiles - https://bdragon1727.itch.io/fire-pixel-bullet-16x16

Level Three & Main Menu BG - https://ansimuz.itch.io/space-background

All other assets are made using Aseprite & Project files in WiP Folder


---

## Features Not Implemented:

**Walls/ Obstacles**
- Lack of Time and constant problems with pre-existing code caused this feature to be dropped

**Dialogue & Animations during & between levels**
- Lack of time caused this feature to be dropped as it is not necessary for the games main functionality and is instead purely cosmetic

**Game Window Resizing**
- Lack of Time and Over-complexity of introducing this function led to this feature being dropped after many attempts at implementing it

**Item Indicator**
- All items were meant to have a indicator on screen for it's remaining duration
- Due to the general structure of the GameLoop & time constraints, adding more animations on screen became difficult
---

# New Java Classes:

**MyObservable & MyObserver**
- observer interface to replace depreciated observable classes used in Controller and LevelParent Classes
- In controller package

**LevelManager**
- handles level transitions instead of Controller and Level Parent
- Used as having a dedicated class for management is much more efficient if main menus, more levels or pause menus are added
- In levels package

**CollisionManager**
- Handles all collisions between items, planes and projectiles, made to reduce complexity of LevelParent
- In levels package

**GameLoop**
- Handles actual Game loop and timeline, made to reduce complexity of LevelParent
- In levels package

**Item**
- Abstract class for items that can be picked up by user plane for power ups
- In actors package

**ShieldItem**
- Item for User to pick up to gain Invulnerability to damage for a period of time
- In actors package

**Health Item**
- Item for User to pick up to gain back 1 point of health 
- In actors package

**MultiShotItem**
- Item for User to pick up to fire 3 projectiles instead of 1 for a period of time
- In actors package

**Config**
- Class for data storage to keep all variables in a centralized file for ease of access
- Prevents Hardcoding and makes it easier to change values if game needs to be balanced
- In main package (com.example.demo)

**Main Menu Controller**
- New controller for main menu that loads when main is run, buttons to play and exit
- In controller package

**SoundManager**
- Handles all methods related to music and SFX
- In levels package

**SpriteSheetAnimation**
- Handles animations (Currently Explosion animation when a fighter plane is destroyed)
- In levels package

**BomberPlane**
- New bomber enemy type
- In actors package

**BomberProjectile**
- BomberPlanes slower moving projectile
- In actors package

**ChargePlane**
- New Charger enemy type
- In actors package

**LevelThree**
- Previously leveltwo (Boss Level)
- In levels package

**LevelEndless**
- Endless mode where all items and normal enemy types can spawn
- In levels package

**MainMenuController**
- Controller for Main menu screen and its fxml, old controller class removed as game now only starts from main menu
- In controller package

**PauseMenuController**
- Controller for Pause Menu and its fxml
- In controller package

**PauseMenuManager**
- Manages calling all methods for when the game is paused
- In levels package

**BaseLevel**
- Handles Base methods/logic that applies to each levels
- has methods to spawn every actor in a level
- In levels package

**HelpImage**
- Shows tutorial Screen for Level One & Endless
- In imagedisplay package


# Modified Java Classes:

**General Changes**
- Math.random changed in most cases to use java.security.SecureRandom().nextDouble() to increase security
- most variables now take their values from config class which holds most data values
  - Spawn Rates are not included in config as there are too many variables to be included if more enemy types and levels are added in future updates
- All Resource Images changed to fit new thematic, and ensure hitboxes are as tight as possible

**ShieldImage**
- image path fixed from shield.jpg to shield.png
- Now extends ActiveActor instead of ImageView
- Shield Effect now has a different visual compared to shield item to accommodate for the new shield item

**Controller**
- IllegalArgumentException and  SecurityException throw removed from launchgame
- Observer class extension removed as it is depreciated
- Update and Gotolevel removed, handled by levelmanager instead
- **!!! Class removed after MainMenuController was introduced as game now only starts from MainMenu**

**LevelParent**
- Observer class extension removed as it is depreciated (Replaced with Composition of a MyObservable class instead of inheritance)
- Import statements cleaned up
- update & Remove Destroyed Actors method lambdas replaced with ActiveActorDestructible
- collect(Collectors.toList()) replaced with ToList()
- LevelView made final
- getClass().getResource(backgroundImageName).toExternalForm() in this.background replaced with Objects.requireNonNull((getClass().getResource(backgroundImageName)).toExternalForm()) to prevent null pointer exception
- friendly & enemy units, user & enemy projectiles changed to be an array list
- initializeScene broken down into smaller methods for better readability
- handleKeyReleased  moved into its own method instead of being in Background initialization
- SetChanged, removed as observer interface is available and can be notified directly
- fireProjectile method added if(projectile != null) clause similar to spawnEnemyProjectile method
- Collision Handling now moved to its own class to reduce complexity
- Timeline now handled by GameLoop class to reduce complexity
  - Any Methods working with timeline has been changed to divert to GameLoop class
  - Initialize Timeline method removed as now unnecessary and simply calls GameLoop
- HandleEnemyPenetration, added boolean return to remove enemy from list if destroyed
  - root.getChildren().remove(enemy); added to remove enemy sprite once penetrated
- UpdateScene broken down into method for spawning new enemies and updating existing ones
  - Also, now updates killcount text to always be on top of the scene 
- gameActive boolean flag added so that user can no longer input actions once game is lost or won
- Items list array added and updated, destroyed & added to root accordingly
- User & Item collision handled to trigger item effect when calling CollisionManager
- Gotonextlevel removed, handled by level manager now
- Calls Sound Manager for all Music and SFX
- Pause method implemented when user presses Esc to show a pause screen and stop timeline
- Added Escape key in HandleKeyPressed() Method to pause
- Added Enter key in HandleKeyPressed() Method to skip tutorial screen
- fireprojectiles() Now can handle when user has multishot enabled to return a list of actors instead of just one projectile
- stopgame() now clears all actors properly
- added shouldShowHelpImage boolean to check if current level needs to show tutorial when initializing

**FighterPlane**
- HealthAtZero method removed, worked inline at takedamage function

**UserPlane**
- incrementKillCount now accepts a count int argument to accommodate for using array lists in level parent when an enemy plane is destroyed
- updatePosition no longer checks if moving before updating
- Shield Methods added much like in Boss Class that activates when shield item is picked up
- truedamage now added for when enemy penetrates defense line even when plane is shielded
- Multishot flag added along with fireMultishot() method to shoot multiple projectiles
- RestoreHealth() Method added for when health item is picked up
- Added Cooldown for projectile fire so that user cannot simply spam fire

**ActiveActor**
- getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm() changed to (Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()) to prevent null pointer exception
- this.setImage(new Image(IMAGE_LOCATION + imageName)); removed as setimage using getClass().getResource() is more efficient, portable and flexible for javaFx applications compared to direct pathing
- Added error logging in case image is not loaded properly

**ActiveActorDestructible**
- setDestroyed method removed, integrated directly into destroy() to set isDestroyed to true when called
- takedamage() made into abstract to ensure subclasses properly handle the method

**Boss**
- shieldShouldBeActivated & shieldExhausted removed and instead integrated directly into updateShield with a new else if loop system
- updatePosition() added line to update shield position as well
- Activate and Deactivate Shield now calls show and hide shield properly

**HeartDisplay**
- Added method to remove hearts

**Destructible**
- Removed, having such a small interface is redundant and is instead directly integrated onto ActiveActorDestructible

**LevelView**
- Added methods to show and hide help image for tutorial screen
- Added kill count tracking (Boss health for level three)
- Shows instructions to pause below heart display
- Adds instance of itself for access by other classes

**LevelOne**
- SpawnItems Method added for all items
- check if game over now properly transitions to next level when killcount reaches limit
- Now extends BaseLevel first instead of LevelParent directly (For modularity and reduce code duplication)

**LevelViewLevelTwo**
- Entirely Removed, instead moved to Boss class directly & addition to root done in LevelTwo Class

**LevelTwo**
- SpawnEnemyUnits() now also spawns in the shield image so that it is in root and can be called properly
- !!! LevelTwo is now more similar to LevelOne except it introduces new enemies and items (Boss moved to LevelThree)
- Now extends BaseLevel first instead of LevelParent directly (For modularity and reduce code duplication)

---

## Unexpected Problems:

- **Game Loop & Timeline**
  - The use of a game loop and timeline paired with the structure of active actors led to many problems when adding animations
    - A compromise was made so only one animation was introduced that did not interfere with the game loop
- **MyObserver Interface**
  - Replacing the depreciated Observer class with a custom interface was more difficult than expected
    - The Observer class was removed and replaced with a composition of MyObservable class instead