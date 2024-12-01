# GitHub: Provide the link to your GitHub repository.
  https://github.com/Jephtha-T/DMS_CW2024.git

# Compilation Instructions:
Provide a clear, step-by-step guide on how to compile the
code to produce the application. Include any dependencies or special settings
required.

# Implemented and Working Properly:
List the features that have been successfully
implemented and are functioning as expected. Provide a brief description of each.

- Main Menu Screen
- Pause Function
- Replay button
- Fixed Hitboxes (Blank Spaces removed in resource images to reflect hitbox properly)
- Impact VFX
- Scoreboard  (Clearly Shows kill per level and kills needed to go to next level)
- Items   (Items to be picked up by Userplane for power ups)
  - ShieldItem (Invulnerability Shield for User from projectiles)
  - Multishot (Shoots 3 projectiles instead of one)
  - Health (Restore 1 heart)
- Music & SFX
- Different Enemies
  - Bomber (Moves vertically with slower projectiles)
  - Charger (No projectiles, instead rushes horizontally)
- More Levels
  - Endless Mode
  - Level two before boss level

# Implemented but Not Working Properly:
List any features that have been
implemented but are not working correctly. Explain the issues you encountered,
and if possible, the steps you took to address them.


# Features Not Implemented:
Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.
- Walls/ Obstacles

# New Java Classes:
Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.

MyObservable
- observer interface to replace depreciated observable classes used in Controller and LevelParent Classes

LevelManager
- handles level transitions instead of Controller and Level Parent
- Used as having a dedicated class for management is much more efficient if main menus, more levels or pause menus are added

CollisionManager
- Handles all collisions between items, planes and projectiles, made to reduce complexity of levelparent

GameLoop
- Handles actual Game loop and timeline, made to reduce complexity of LevelParent

Items
- Class for items that can be picked up by user plane for power ups

Config
- Class for data storage to keep all variables in a centralized file for ease of access

ShieldItem

Health Item

MultiShotItem

Main Menu Controller
- New controller for main menu that loads when main is run, buttons to play and exit

SoundManager
- Handles all methods related to music and SFX

SpriteSheetAnimation
- Handles animations (Currently Explosion animation when a fighter plane is destroyed)

BomberPlane
- New bomber enemy type

BomberProjectile
- BomberPlanes slower moving projectile

ChargePlane
- New Charger enemy type

LevelThree
- Previously leveltwo (Boss Level)

LevelEndless
- Endless mode where all items and normal enemy types can spawn

MainMenuController
- Controller for Main menu screen and its fxml, old controller class removed as game now only starts from main menu

PauseMenuController
- Controller for Pause Menu and its fxml

PauseMenuManager
- Manages calling all methods for when the game is paused

BaseLevel
- Handles Base methods/logic that applies to each levels
- has methods to spawn every actor in a level


# Modified Java Classes:
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.

General Changes
- Math.random changed in most cases to use java.security.SecureRandom().nextDouble() to increase security
- most variables now take their values from config class which holds most data values

ShieldImage
- image path fixed from shield.jpg to shield.png
- Now extends ActiveActor instead of ImageView
- Shield Effect now has a different visual compared to shield item

Controller
- IllegalArgumentException and  SecurityException throw removed from launchgame
- Observer class extension removed as it is depreciated
- Update and Gotolevel removed, handled by levelmanager instead
- !!! Class removed after MainMenuController was introduced as game now only starts from MainMenu

LevelParent
- Observer class extension removed as it is depreciated (Replaced with Composition of a MyObservable class instead of inheritance)
- import java.util.*;, import javafx.animation.*;, javafx.scene.image.*;, javafx.scene.input.*; removed and actual necessary imports are specified
- updateActors method lambdas replaced with ActiveActorDestructible
- removeDestroyedActors lambda replaced with ActiveActorDestructible, collect(Collectors.toList()) replaced with ToList()
- LevelView made final
- getClass().getResource(backgroundImageName).toExternalForm() in this.background replaced with Objects.requireNonNull((getClass().getResource(backgroundImageName)).toExternalForm()) to prevent null pointer exception
- friendly & enemy units, user & enemy projectiles changed to be an array list
- InitializeScene, removed initializeBackground(); , initializeFriendlyUnits(); instead moved to LevelParent Method
- InitializeBackground method removed handleKeyPressed, handleKeyReleased instead moved as new methods each
- SetChanged, removed as observer interface is available and can be notified directly
- fireProjectile method added if(projectile != null) clause similar to spawnEnemyProjectile method
- Collision Handling now moved to its own class as it was too complicated to be in one class as levelparent
- HandleEnemyPenetration, added boolean return to remove enemy from list if destroyed, root.getChildren().remove(enemy); added to remove enemy sprite once penetrated
- updateKillCount for loop removed, instead it only runs once per call
- gameActive boolean flag added so that user can no longer input actions once game is lost or won
- Items list array added and updated, destroyed & added to root accordingly
- User & Item collision handled to trigger item effect
- Gotonextlevel removed, handled by level manager now
- Any Methods working with timeline has been changed to divert to GameLoop class
- Initialize Timeline method removed as now unnecessary and simply calls GameLoop
- Calls Sound Manager for all Music and SFX
- Pause method implemented when user presses Esc to show a pause screen and stop timeline
- Update Scene now also refreshes killcount text so that it is always on the top most layer of the scene
- Added Escape key in HandleKeyPressed() Method to pause
- fireprojectiles() Now can handle when user has multishot enabled to return a list of actors instead of just one projectile
- stopgame() now clears all actors properly

FighterPlane
- HealthAtZero method removed, worked inline at takedamage function

UserPlane
- incrementKillCount now accepts a count int argument to accommodate for using array lists in level parent when an enemy plane is destroyed
- updatePosition no longer checks if moving before updating
- Shield Methods added much like in Boss Class that activates when shield item is picked up
- truedamage now added for when enemy penetrates defense line even when plane is shielded
- Multishot flag added alosng with fireMultishot() method to shoot multiple projectiles
- RestoreHealth() Method added for when health item is picked up
- Added Cooldown for projectile fire so that user cannot simply spam fire

ActiveActor
- getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm() changed to (Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()) to prevent null pointer exception
- this.setImage(new Image(IMAGE_LOCATION + imageName)); removed as setimage using getClass().getResource() is more efficient, portable and flexible for javaFx applications compared to direct pathing
- Added error logging in case image is not loaded properly

ActiveActorDestructible (This is not merged with ActiveActor for maintainability in case any new features include non-destructible objects)
- setDestroyed method removed, integrated directly into destroy() to set isDestroyed to true when called

Boss
- shieldShouldBeActivated & shieldExhausted removed and instead integrated directly into updateShield with a new else if loop system
- updatePosition() added line to update shield position as well
- Activate and Deactivate Shield now calls show and hide shield properly

Destructible
- Removed, having such a small interface is redundant and is instead directly integrated onto ActiveActorDestructible

LevelOne
- SpawnItems Method added for all items
- check if game over now properly transitions to next level when killcount reaches limit
- Now extends BaseLevel first instead of LevelParent directly (For modularity and reduce code duplication)

LevelViewLevelTwo
- Entirely Removed, instead moved to Boss class directly & addition to root done in LevelTwo Class

LevelTwo
- SpawnEnemyUnits() now also spawns in the shield image so that it is in root and can be called properly
- !!! LevelTwo is now more similar to LevelOne except it introduces new enemies and items (Boss moved to LevelThree)
- Now extends BaseLevel first instead of LevelParent directly (For modularity and reduce code duplication)



# Unexpected Problems:
Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.