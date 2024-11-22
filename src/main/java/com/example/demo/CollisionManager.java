package com.example.demo;

import javafx.scene.Group;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CollisionManager {
    private final Group root; // Root node for removing actors
    private final UserPlane user; // Reference to the user plane
    private int currentNumberOfEnemies; // Reference to track enemy count

    public CollisionManager(Group root, UserPlane user) {
        this.root = root;
        this.user = user;
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
        totalEnemiesDestroyed += handleUserCollisions(friendlyUnits, enemyUnits, enemyProjectiles, items);

        // Friendly unit collisions
        totalEnemiesDestroyed += handleFriendlyCollisions(userProjectiles, enemyUnits);

        // Enemy penetration
        totalEnemiesDestroyed += handleEnemyPenetration(enemyUnits);
        return totalEnemiesDestroyed;
    }

    private int handleUserCollisions(
            List<ActiveActorDestructible> friendlyUnits,
            List<ActiveActorDestructible> enemyUnits,
            List<ActiveActorDestructible> enemyProjectiles,
            List<ActiveActorDestructible> items) {

        int enemiesDestroyed = 0;

        // UserPlane vs EnemyProjectiles
        processCollisionsWithUser(user, enemyProjectiles);

        // UserPlane vs EnemyUnits
        enemiesDestroyed += processCollisionsWithUser(user, enemyUnits);

        // UserPlane vs Items
        processItemCollisions(user, items);

        return enemiesDestroyed;
    }

    private int handleFriendlyCollisions(
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyUnits) {

        return processCollisionsBetweenLists(userProjectiles, enemyUnits);
    }

    private int processCollisionsWithUser(UserPlane user, List<ActiveActorDestructible> actors) {
        AtomicInteger destroyedCount = new AtomicInteger();

        actors.removeIf(actor -> {
            if (actor.getBoundsInParent().intersects(user.getBoundsInParent())) {
                if (user.isShielded()) {
                    actor.destroy();
                    root.getChildren().remove(actor);
                    destroyedCount.incrementAndGet();
                    System.out.println("Shield absorbed collision.");
                } else {
                    user.takeDamage();
                    actor.takeDamage();
                    if (actor.isDestroyed()) {
                        root.getChildren().remove(actor);
                        destroyedCount.incrementAndGet();
                    }
                }
                return true; // Remove from list
            }
            return false; // Keep in list
        });

        return destroyedCount.get();
    }

    private void processItemCollisions(UserPlane user, List<ActiveActorDestructible> items) {
        items.removeIf(item -> {
            if (item.getBoundsInParent().intersects(user.getBoundsInParent())) {
                if (item instanceof Item) {
                    ((Item) item).triggerEffect(user);
                    item.destroy();
                    root.getChildren().remove(item);
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
                        root.getChildren().remove(actor1);
                    }
                    if (actor2.isDestroyed()) {
                        root.getChildren().remove(actor2);
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
                user.takeTrueDamage();
                currentNumberOfEnemies--;
                enemy.destroy();
                root.getChildren().remove(enemy);
                enemiesDestroyed.getAndIncrement();
                return true;
            }
            return false;
        });
        return enemiesDestroyed.get();
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        // Logic to determine if the enemy has penetrated defenses
        return enemy.getTranslateY() > user.getLayoutY() + 50; // Example threshold
    }
}
