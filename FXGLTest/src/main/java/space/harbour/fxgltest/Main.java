package space.harbour.fxgltest;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends GameApplication {

    private Entity player;
    private List<Entity> enemies;
    private int score;
    private enum EntityType {
        PLAYER, ENEMY
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");

        settings.setMainMenuEnabled(true);
    }

    @Override
    protected void initUI() {
        Text scoreText = FXGL.getUIFactoryService().newText("", Color.BLACK, 24);
        scoreText.setTranslateX(50);
        scoreText.setTranslateY(100);
        scoreText.textProperty().bind(
            new ReadOnlyStringWrapper("Score: ").concat(
                    FXGL.getWorldProperties().intProperty("score").asString()
            ));
        FXGL.getGameScene().addUINode(scoreText);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .at(300, FXGL.getSettings().getHeight() - 25)
                .viewWithBBox(new Rectangle(25, 25, Color.BLUE))
                .collidable()
                .buildAndAttach();

        enemies = new ArrayList<>();
        FXGL.getGameTimer().runAtInterval(() -> {
            Entity newEnemy = FXGL.entityBuilder()
                    .type(EntityType.ENEMY)
                    .at(FXGLMath.random(0, FXGL.getSettings().getWidth()), 0)
                    .viewWithBBox(new Rectangle(50, 50, Color.RED))
                    .collidable()
                    .buildAndAttach();
            enemies.add(newEnemy);
        }, Duration.seconds(0.5));
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5); // move right 5 pixels
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5); // move right 5 pixels
            }
        }, KeyCode.A);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY,
                EntityType.PLAYER) {

            @Override
            protected void onCollision(Entity enemy, Entity player) {
                enemy.removeFromWorld();
                score += 1;
                FXGL.set("score", score);
                FXGL.play("blop.mp3");
            }
        });
    }

    @Override
    protected void onUpdate(double delta) {
        super.onUpdate(delta);

        for (Entity enemy : enemies) {
            enemy.translateY(10);

            if (enemy.getY() > FXGL.getSettings().getHeight()) {
                enemy.removeFromWorld();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
