package za.ttd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;

public class ttd extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal(
				"core/assets/sprites/out/sprites.atlas"
		));
		animation = new Animation(
				1/10f,
				textureAtlas.findRegion("characters/decayLeft"),
				textureAtlas.findRegion("characters/decayFull"),
				textureAtlas.findRegion("characters/decayRight")
		);
	}

	@Override
	public void dispose() {
		batch.dispose();
		textureAtlas.dispose();
	}

	@Override
	public void render() {
		Gdx.gl20.glClearColor(0,0,0,1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedTime, true), 0,0);
		batch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
