package lwjgl.playground.flappy.level;

import lwjgl.playground.flappy.graphics.Shader;
import lwjgl.playground.flappy.graphics.Texture;
import lwjgl.playground.flappy.graphics.VertexArray;
import lwjgl.playground.flappy.math.Matrix4f;
import lwjgl.playground.flappy.math.Vector3f;

import java.util.Random;

/**
 * Created by Kyle Flynn on 8/31/2016.
 */
public class Level {

    private Pipe[] pipes;
    private Bird bird;

    private VertexArray background;
    private Texture bgTexture;

    private Random random;

    private int xScroll;
    int bgMaps = 0;

    private int index = 0;
    private float offset;

    private boolean isDead;

    public Level() {
        float[] vertices = new float[] {
                -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
                -10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
                  0.0f,  10.0f * 9.0f / 16.0f, 0.0f,
                  0.0f, -10.0f * 9.0f / 16.0f, 0.0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        offset = 5.0f;
        isDead = false;

        background = new VertexArray(vertices, indices, tcs);
        bgTexture = new Texture("res/bg.jpeg");

        random = new Random();
        pipes = new Pipe[5 * 2];
        bird = new Bird();

        createPipes();
    }

    public void update() {
        if (!isDead) {
            xScroll--;

            if (-xScroll % 335 == 0) {
                bgMaps++;
            }

            if (-xScroll > 250 && -xScroll % 120 == 0) {
                updatePipes();
            }
        }

        bird.update();

        if (!isDead && collision()) {
            isDead = true;
            System.out.println("COLLISION");
        }

    }

    public void render() {
        bgTexture.bind();
        Shader.BG.enable();
        background.bind();
        for (int i = bgMaps; i < bgMaps + 4; i++) {
            Shader.BG.setUniformMatrix4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
            background.draw();
        }
        Shader.BG.disable();
        bgTexture.unbind();

        renderPipes();
        bird.render();
    }

    private void createPipes() {
        Pipe.create();
        for (int i = 0; i < 5 * 2; i+=2) {
            pipes[i] = new Pipe(offset + index * 3.0f, random.nextFloat() * 4.0f);
            pipes[i + 1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 12.0f);
            index += 2;
        }
    }

    private void updatePipes() {
        pipes[index % 10] = new Pipe(offset + index * 3.0f, random.nextFloat() * 4.0f);
        pipes[(index + 1) % 10] = new Pipe(pipes[index % 10].getX(), pipes[index % 10].getY() - 11.5f);
        index += 2;
    }

    private void renderPipes() {
        Shader.PIPE.enable();
        Shader.PIPE.setUniformMatrix4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.05f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();

        // 5 * 2 to symbolize 5 on top and bottom
        for (int i = 0; i < 5 * 2; i++) {
            Shader.PIPE.setUniformMatrix4f("ml_matrix", pipes[i].getModelMatrix());
            Shader.PIPE.setUniformi("top", i % 2 == 0 ? 1 : 0);
            Pipe.getMesh().draw();
        }
        Pipe.getTexture().unbind();
        Pipe.getMesh().unbind();
        Shader.PIPE.disable();
    }

    private boolean collision() {
        for (int i = 0; i < 5 * 2; i++) {
            float bx = -xScroll * 0.05f;
            float by = bird.getY();

            float px = pipes[i].getX();
            float py = pipes[i].getY();

            float bx0 = bx - bird.getSize() / 2.0f;
            float bx1 = bx + bird.getSize() / 2.0f;
            float by0 = by - bird.getSize() / 2.0f;
            float by1 = by + bird.getSize() / 2.0f;

            float px0 = px;
            float px1 = px + Pipe.getWidth();
            float py0 = py;
            float py1 = py + Pipe.getHeight();

            if (bx1 > px0 && bx0 < px1) {
                if (by1 > py0 && by0 < py1) {
                    return true;
                }
            }
        }
        return false;
    }

}
