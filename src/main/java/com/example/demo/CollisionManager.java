package com.example.demo;

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
            List<ActiveActorDestructible> friendlyUnits,
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
        totalEnemiesDestroyed += handleEnemyPenetration(enemyUnits);
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
                if (mUser.isShielded()) {
                    actor.destroy();
                    mRoot.getChildren().remove(actor);
                    destroyedCount.incrementAndGet();
                    System.out.println("Shield absorbed collision.");
                } else {
                    mUser.takeDamage();
                    actor.takeDamage();
                    if (actor.isDestroyed()) {
                        mRoot.getChildren().remove(actor);
                        destroyedCount.incrementAndGet();
                    }
                }
                return true; // Remove from list
            }
            return false; // Keep in list
        });

        return destroyedCount.get();
    }

    private void processItemCollisions(UserPlane mUser, List<ActiveActorDestructible> items) {
        items.removeIf(item -> {
            if (item.getBoundsInParent().intersects(mUser.getBoundsInParent())) {
                if (item instanceof Item) {
                    ((Item) item).triggerEffect(mUser);
                    item.destroy();
                    mRoot.getChildren().remove(item);
                    System.out.println("Item effect triggered.");
                }
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
                        mRoot.getChildren().remove(actor1);
                    }
                    if (actor2.isDestroyed()) {
                        mRoot.getChildren().remove(actor2);
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
                System.out.println("Enemy Penetrated Defense");
                enemy.destroy();
                mRoot.getChildren().remove(enemy);
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
}
