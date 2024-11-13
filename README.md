# GitHub: Provide the link to your GitHub repository.  
  https://github.com/Jephtha-T/DMS_CW2024.git
  
# Compilation Instructions: 
Provide a clear, step-by-step guide on how to compile the  
code to produce the application. Include any dependencies or special settings  
required.  
  
  
# Implemented and Working Properly: 
List the features that have been successfully  
implemented and are functioning as expected. Provide a brief description of each.  
  
Main Menu Screen  
Replay button  
Level Select Screen  
- Fixed Hitboxes (Blank Spaces removed in resource images to reflect hitboc properly)
Impact VFX  
Scoreboard  
Weapon Upgrades  
- Items   (Items to be picked up by Userplane for power ups)
  - ShieldItem (Invulnerability Shield for User)
Music  
Different Enemies  
More Levels  
walls lasers
  
  
# Implemented but Not Working Properly: 
List any features that have been  
implemented but are not working correctly. Explain the issues you encountered,  
and if possible, the steps you took to address them.  
  
  
# Features Not Implemented: 
Identify any features that you were unable to  
implement and provide a clear explanation for why they were left out.  
  
  
# New Java Classes: 
Enumerate any new Java classes that you introduced for the  
assignment. Include a brief description of each class's purpose and its location in the  
code.  

MyObserver
- observer interface to replace depreciated observable classes used in Controller and LevelParent Classes
  
Items
- Class for items that can be picked up by user plane for power ups

ShieldItem

# Modified Java Classes: 
List the Java classes you modified from the provided code  
base. Describe the changes you made and explain why these modifications were  
necessary.  

ShieldImage
- image path fixed from shield.jpg to shield.png
- Now extends ActiveActor instead of ImageView 

Controller
- IllegalArgumentException and  SecurityException throw removed from launchgame
- Observer class extension removed as it is depreciated, replaced with Observer interface which notifies all listed observers if changes occur

LevelParent
- Observer class extension removed as it is depreciated
- import java.util.*;, import javafx.animation.*;, javafx.scene.image.*;, javafx.scene.input.*; removed and actual necessary imports are specified
- updateActors method lambdas replaced with ActiveActorDestructible
- removeDestroyedActors lambda replaced with ActiveActorDestructible, collect(Collectors.toList()) replaced with ToList()
- LevelView made final
- getClass().getResource(backgroundImageName).toExternalForm() in this.background replaced with Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()) to prevent null pointer exception
- friendly & enemy units, user & enemy projectiles changed to be an array list
- InitializeScene, removed initializeBackground(); , initializeFriendlyUnits(); instead moved to LevelParent Method
- InitializeBackground method removed handleKeyPressed, handleKeyReleased instead moved as new methods each
- SetChanged, removed as observer interface is available and can be notified directly
- fireProjectile method added if(projectile != null) clause similar to spawnEnemyProjectile method
- handlePlaneCollisions, handleUserProjectileCollisions, handleEnemyProjectileCollisions removed, instead handlecollisions now uses handleCollisionBetweenLists to handle all collisions from different actors
- HandleEnemyPenetration, added boolean return to remove enemy from list if destroyed, root.getChildren().remove(enemy); added to remove enemy sprite once penetrated
- updateKillCount for loop removed, instead it only runs once per call
- gameActive boolean flag added so that user can no longer input actions once game is lost or won
- Items list array added and updated, destroyed & added to root accordingly
- User & Item collision handled to trigger item effect  


UserPlane
- incrementKillCount now accepts a count int argument to accomodate for using array lists in level parent when a enemy plane is destroyed
- updatePosition no longer checks if moving before updating
- Shield Methods added much like in Boss Class that activates when shield item is picked up

ActiveActor
- getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm() changed to Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()) to prevent null pointer exception
- this.setImage(new Image(IMAGE_LOCATION + imageName)); removed as setimage using getClass().getResource() is more efficient, portable and flexible for javaFx applications compared to direct pathing

ActiveActorDestructible (This is not merged with ActiveActor for maintainability incase any new features include non destructible objects)
- setDestroyed method removed, integrated directly into destroy() to set isDestroyed to true when called

Boss
- shieldShouldBeActivated & shieldExhausted removed and instead integrated directly into updateShield with a new else if loop system
- updatePosition() added line to update shield position as well
- Activate and Deactive Shield now calls show and hide shield properly

LevelOne
- SpawnItems Method added for all items 

LevelViewLevelTwo
- Any lines related to spawning and updating shield removed, instead moved to Boss class directly & addition to root done in LevelTwo Class

LevelTwo
- SpawnEnemyUnits() now also spawns in the shield image so that it is in root and can be called properly

# Unexpected Problems: 
Communicate any unexpected challenges or issues you  
encountered during the assignment. Describe how you addressed or attempted to  
resolve them.