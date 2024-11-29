package com.example.demo.LevelControl;

import com.example.demo.Actors.*;
import com.example.demo.Config;
import javafx.scene.Group;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CollisionManager {
    private final Group mRoot; // mRoot node for removing actors
    private final UserPlane mUser; // Reference to the user plane
    private int currentNumberOfEnemies; // Reference to track enemy count


    public CollisionManager(Group mRoot, UserPlane mUser) {
        this.mRoot = mRoot;
        this.mUser = mUser;
    }

    public void setCurrentNumberOfEnemies(int currentNumberOfEnemies) {
        this.currentNumberOfEnemies = currentNumberOfEnemies;
    }

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

    private int handleFriendlyCollisions(
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyUnits) {

        return processCollisionsBetweenLists(userProjectiles, enemyUnits);
    }

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

    protected int handleEnemyPenetration(List<ActiveActorDestructible> enemyUnits) {
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
        return enemiesDestroyed.get();
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        double enemyX = enemy.getTranslateX() + enemy.getLayoutX();
        return enemyX <= 0; //Has crossed left side of screen
    }

    public void handleEnemyDestruction(ActiveActorDestructible enemy) {
        // Ensure this is a FighterPlane
        if (!(enemy instanceof FighterPlane)) {
            return;
        }

        // Play explosion animation
        SpriteSheetAnimation explosion = new SpriteSheetAnimation(
                Config.EXPLOSION_SPRITESHEET_PATH,
                Config.EXPLOSION_FRAME_WIDTH,
                Config.EXPLOSION_FRAME_HEIGHT,
                Config.EXPLOSION_TOTAL_FRAMES,
                Config.EXPLOSION_FRAME_DURATION
        );
        double centerX = enemy.getBoundsInParent().getMinX() + enemy.getBoundsInParent().getWidth() / 2;
        double centerY = enemy.getBoundsInParent().getMinY() + enemy.getBoundsInParent().getHeight() / 2;

        explosion.getImageView().setX(centerX - Config.EXPLOSION_FRAME_WIDTH / 2);
        explosion.getImageView().setY(centerY - Config.EXPLOSION_FRAME_HEIGHT / 2);


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
