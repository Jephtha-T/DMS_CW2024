package com.example.demo.levels;

import com.example.demo.actors.*;
import com.example.demo.Config;
import javafx.scene.Group;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages collisions between various actors in the game.
 * Handles collisions between the user plane, enemy units, projectiles, and items.
 */
public class CollisionManager {
    private final Group mRoot; // mRoot node for removing actors
    private final UserPlane mUser; // Reference to the user plane
    private int currentNumberOfEnemies; // Reference to track enemy count

    /**
     * Constructor for CollisionManager.
     *
     * @param mRoot the root node for removing actors
     * @param mUser the user plane
     */
    public CollisionManager(Group mRoot, UserPlane mUser) {
        this.mRoot = mRoot;
        this.mUser = mUser;
    }

    /**
     * Sets the current number of enemies.
     *
     * @param currentNumberOfEnemies the current number of enemies
     */
    public void setCurrentNumberOfEnemies(int currentNumberOfEnemies) {
        this.currentNumberOfEnemies = currentNumberOfEnemies;
    }

    /**
     * Handles collisions between various actors.
     *
     * @param enemyUnits the list of enemy units
     * @param userProjectiles the list of user projectiles
     * @param enemyProjectiles the list of enemy projectiles
     * @param items the list of items
     * @return the total number of enemies destroyed
     */
    public int handleCollisions(
            List<ActiveActorDestructible> enemyUnits,
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyProjectiles,
            List<ActiveActorDestructible> items) {
        int totalEnemiesDestroyed = 0;
        // UserPlane collisions
        totalEnemiesDestroyed += handleUserCollisions(enemyUnits, enemyProjectiles, items);

        // Friendly unit collisions
        totalEnemiesDestroyed += handleFriendlyCollisions(userProjectiles, enemyUnits);

        // Enemy penetration
        handleEnemyPenetration(enemyUnits);
        return totalEnemiesDestroyed;
    }

    /**
     * Handles collisions involving the user plane.
     *
     * @param enemyUnits the list of enemy units
     * @param enemyProjectiles the list of enemy projectiles
     * @param items the list of items
     * @return the number of enemies destroyed
     */
    private int handleUserCollisions(
            List<ActiveActorDestructible> enemyUnits,
            List<ActiveActorDestructible> enemyProjectiles,
            List<ActiveActorDestructible> items) {

        int enemiesDestroyed = 0;

        // UserPlane vs EnemyProjectiles
        processCollisionsWithUser(mUser, enemyProjectiles);

        // UserPlane vs EnemyUnits
        enemiesDestroyed += processCollisionsWithUser(mUser, enemyUnits);

        // UserPlane vs Items
        processItemCollisions(mUser, items);

        return enemiesDestroyed;
    }

    /**
     * Handles collisions between friendly units and enemy units.
     *
     * @param userProjectiles the list of user projectiles
     * @param enemyUnits the list of enemy units
     * @return the number of enemies destroyed
     */
    private int handleFriendlyCollisions(
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyUnits) {

        return processCollisionsBetweenLists(userProjectiles, enemyUnits);
    }

    /**
     * Processes collisions between the user plane and a list of actors.
     *
     * @param mUser the user plane
     * @param actors the list of actors
     * @return the number of actors destroyed
     */
    private int processCollisionsWithUser(UserPlane mUser, List<ActiveActorDestructible> actors) {
        AtomicInteger destroyedCount = new AtomicInteger();

        actors.removeIf(actor -> {
            if (actor.getBoundsInParent().intersects(mUser.getBoundsInParent())) {
                actor.destroy(); // Ensure it is marked as destroyed
                mUser.takeDamage();
                mRoot.getChildren().remove(actor); // Remove from the scene
                destroyedCount.incrementAndGet();
                return true; // Remove from the list
            }
            return false; // Keep in list
        });

        return destroyedCount.get();
    }

    /**
     * Processes collisions between the user plane and items.
     *
     * @param mUser the user plane
     * @param items the list of items
     */
    private void processItemCollisions(UserPlane mUser, List<ActiveActorDestructible> items) {
        items.removeIf(item -> {
            if (item.getBoundsInParent().intersects(mUser.getBoundsInParent()) && item instanceof Item castedItem) {
                castedItem.triggerEffect(mUser);
                castedItem.destroy();
                mRoot.getChildren().remove(castedItem);
                return true; // Remove from list
            }
            return false; // Keep in list
        });
    }

    /**
     * Processes collisions between two lists of actors.
     *
     * @param list1 the first list of actors
     * @param list2 the second list of actors
     * @return the number of actors destroyed
     */
    private int processCollisionsBetweenLists(
            List<ActiveActorDestructible> list1,
            List<ActiveActorDestructible> list2) {

        AtomicInteger destroyedCount = new AtomicInteger();

        list2.removeIf(actor2 -> {
            for (ActiveActorDestructible actor1 : list1) {
                if (actor2.getBoundsInParent().intersects(actor1.getBoundsInParent())) {
                    actor1.takeDamage();
                    actor2.takeDamage();

                    if (actor1.isDestroyed()) {
                        handleEnemyDestruction(actor1);
                    }
                    if (actor2.isDestroyed()) {
                        handleEnemyDestruction(actor2);
                        destroyedCount.incrementAndGet();
                        return true; // Remove actor2
                    }
                }
            }
            return false; // Keep actor2
        });

        return destroyedCount.get();
    }

    /**
     * Handles enemy penetration of defenses.
     *
     * @param enemyUnits the list of enemy units
     */
    protected void handleEnemyPenetration(List<ActiveActorDestructible> enemyUnits) {
        AtomicInteger enemiesDestroyed = new AtomicInteger();
        enemyUnits.removeIf(enemy -> {
            if (enemyHasPenetratedDefenses(enemy)) {
                mUser.takeTrueDamage();
                currentNumberOfEnemies--;
                mRoot.getChildren().remove(enemy);
                enemy.destroy();
                enemiesDestroyed.getAndIncrement();
                return true;
            }
            return false;
        });
        enemiesDestroyed.get();
    }

    /**
     * Checks if an enemy has penetrated defenses.
     *
     * @param enemy the enemy actor
     * @return true if the enemy has penetrated defenses, false otherwise
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        double enemyX = enemy.getTranslateX() + enemy.getLayoutX();
        return enemyX <= 0; // Has crossed left side of screen
    }

    /**
     * Handles the destruction of an enemy.
     *
     * @param enemy the enemy actor
     */
    public void handleEnemyDestruction(ActiveActorDestructible enemy) {
        // Ensure this is a FighterPlane
        if (!(enemy instanceof FighterPlane)) {
            return;
        }

        // Play explosion animation
        SpriteSheetAnimation explosion = new SpriteSheetAnimation(
                Config.EXPLOSION_FRAME_WIDTH,
                Config.EXPLOSION_FRAME_HEIGHT,
                Config.EXPLOSION_TOTAL_FRAMES,
                Config.EXPLOSION_FRAME_DURATION
        );
        double centerX = enemy.getBoundsInParent().getMinX() + enemy.getBoundsInParent().getWidth() / 2;
        double centerY = enemy.getBoundsInParent().getMinY() + enemy.getBoundsInParent().getHeight() / 2;

        explosion.getImageView().setX(centerX - (double) Config.EXPLOSION_FRAME_WIDTH / 2);
        explosion.getImageView().setY(centerY - (double) Config.EXPLOSION_FRAME_HEIGHT / 2);

        // Ensure no duplicate ImageView is added
        if (!mRoot.getChildren().contains(explosion.getImageView())) {
            mRoot.getChildren().add(explosion.getImageView());
        }

        // Play explosion and remove after
        explosion.playOnceAndRemoveAfter(mRoot);

        // Remove the enemy from the scene and collision tracking
        mRoot.getChildren().remove(enemy);
        enemy.destroy(); // Ensure this handles any internal cleanup
    }
}