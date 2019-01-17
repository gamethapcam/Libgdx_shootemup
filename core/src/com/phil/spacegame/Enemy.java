package com.phil.spacegame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends ShootingObject implements SpawnObject {

    //for SpawnObject interface
    private boolean spawned;

    private float speed;
    private int score;

    public Enemy(String arg) {}

    //to call when object is respawned
    public void init(int type, float posX, float posY) {
        //reset guns
        super.init(SpawnType.MissileEnemy);

        //spawn type
        if (type == 0) {
            if (!containsAnimation("ANIM1"))
                addAnimation(Spacegame.resources.get(Spacegame.resources.tilesetSpaceships, Texture.class),
                    4, 8, 0, 4, 0.05f, "ANIM1", true);
            setAnimation("ANIM1");
            setSize(180, 90);
            setCollisionArea(20, 20, 120, 60);
            setGunPower(10.0f);
            setShootingInterval(1.3f);
            setGunType(0);
            addGun(180, 600, 0, 50);
            speed = -200.0f;
            setScore(100);
        }
        else if (type == 1) {
            if (!containsAnimation("ANIM2"))
                addAnimation(Spacegame.resources.get(Spacegame.resources.tilesetSpaceships, Texture.class),
                        4, 8, 4, 4, 0.05f, "ANIM2", true);
            setAnimation("ANIM2");
            setSize(180, 90);
            setCollisionArea(20, 20, 120, 60);
            setGunPower(10.0f);
            setShootingInterval(1.2f);
            setGunType(0);
            addGun(187, 600, 0, 50);
            addGun(173, 600, 0, 50);
            speed = -200.0f;
            setScore(120);
        }
        else if (type == 2) {
            if (!containsAnimation("ANIM3"))
                addAnimation(Spacegame.resources.get(Spacegame.resources.tilesetSpaceships, Texture.class),
                    4, 8, 8, 4, 0.05f, "ANIM3", true);
            setAnimation("ANIM3");
            setSize(180, 90);
            setCollisionArea(20, 20, 120, 60);
            setGunPower(10.0f);
            setShootingInterval(1.4f);
            setGunType(0);
            addGun(180, 500.0f, 0, 10);
            addGun(180, 500.0f, 0, 30);
            addGun(180, 500.0f, 0, 50);
            speed = -200.0f;
            setScore(150);
        }
        else if (type == 3) {
            if (!containsAnimation("ANIM4"))
                addAnimation(Spacegame.resources.get(Spacegame.resources.tilesetSpaceships, Texture.class),
                    4, 8, 12, 4, 0.05f, "ANIM4", true);
            setAnimation("ANIM4");
            setSize(180, 90);
            setCollisionArea(20, 20, 120, 60);
            setGunPower(10.0f);
            setShootingInterval(1.5f);
            setGunType(0);
            addGun(180, 450, 0, 50);
            addGun(190, 450, 0, 50);
            addGun(170, 450, 0, 50);
            speed = -180.0f;
            setScore(200);
        }

        //set initial position (random y-position)
        setPosition(posX, posY);
    }

    public void setScore(int points) {
        this.score = points;
    }

    public int getScore() {
        return score;
    }

    public void hit(float power) {
        Explosion expl = (Explosion)Gameplay.spawnPool.getFromPool(SpawnType.Explosion);
        expl.init(getX(), getY());
        kill(Gameplay.spawnPool);
        //TODO reduce power instead of killing directly
    }

    private void move(float delta) {
        //update position
        setX(getX() + (speed * delta));

        //remove from gameplay when out of screen
        if (getX() < -getWidth()) {
            kill(Gameplay.spawnPool);
        }
    }

    //Interface methods

    public void update(float delta) {
        super.update(delta);
        move(delta);
    }

    public void draw(SpriteBatch sb) {
        super.draw(sb);
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void kill(SpawnPool pool) {
        //spawn explosion
        Explosion expl = (Explosion)Gameplay.spawnPool.getFromPool(SpawnType.Explosion);
        expl.init(getX(), getY());
        //despawn
        pool.returnToPool(this);
        setPosition(Spacegame.screenWidth, 0);
    }
}
