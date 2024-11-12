# GitHub: Provide the link to your GitHub repository.  
  https://github.com/Jephtha-T/DMS_CW2024.git
  
# Compilation Instructions: 
Provide a clear, step-by-step guide on how to compile the  
code to produce the application. Include any dependencies or special settings  
required.  
  
  
# Implemented and Working Properly: 
List the features that have been successfully  
implemented and are functioning as expected. Provide a brief description of each.  
  
  
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
  
# Modified Java Classes: 
List the Java classes you modified from the provided code  
base. Describe the changes you made and explain why these modifications were  
necessary.  

ShieldImage
- image path fixed from shield.jpg to shield.png
- new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) replaced to new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()) to prevent NullPointerException

Controller
- IllegalArgumentException and  SecurityException throw removed from launchgame
- Observer class extension removed as it is depreciated, replaced with Observer interface which notifies all listed observers if changes occur

Module-Info
- Added export statement for ovserver

LevelParent
- Observer class extension removed as it is depreciated
- import java.util.*;, import javafx.animation.*;, javafx.scene.image.*;, javafx.scene.input.*; removed and actual necessary imports are specified
- updateActors method lambdas replaced with ActiveActorDestructible
- removeDestroyedActors lambda replaced with ActiveActorDestructible, collect(Collectors.toList()) replaced with ToList()
- LevelView made final
- getClass().getResource(backgroundImageName).toExternalForm()in this.background replaced with Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm() to prevent null pointer exception
- friendly & enemy units, user & enemy projectiles changed to be an array list
- InitializeBackground method removed handleKeyPressed, handleKeyReleased instead moved as new methods each
- SetChanged, removed as observer interface is available and can be notified directly
- fireProjectile method added if(projectile != null) clause similar to spawnEnemyProjectile method
- handlePlaneCollisions, handleUserProjectileCollisions, handleEnemyProjectileCollisions removed, instead handlecollisions now uses handleCollisionBetweenLists to handle all collisions from different actors
- HandleEnemyPenetration, added boolean return to remove enemy from list if destroyed, root.getChildren().remove(enemy); added to remove enemy sprite once penetrated
- gameActive boolean flag added so that user can no longer input actions once game is lost or won


# Unexpected Problems: 
Communicate any unexpected challenges or issues you  
encountered during the assignment. Describe how you addressed or attempted to  
resolve them.