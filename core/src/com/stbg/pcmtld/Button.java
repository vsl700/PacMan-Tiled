package com.stbg.pcmtld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class Button {
	String text, mnText;
	int mn;

	BitmapFont font;
	Color color, color2;
	OrthographicCamera cam;
	ShapeRenderer shape;
	SpriteBatch batch;

	byte multitouch = 0;
	int type; // 0 - normal, 1 - toggle, 2 - radio
	float x, mnX;
	float y;
	float width = 0, mnWidth = 0;
	float height = 0;
	boolean mark;

	boolean touched;
	boolean toggled;
	boolean touchable;
	boolean mnemoniced;
	boolean holdOn;

	public Button(String text, BitmapFont font, SpriteBatch batch, ShapeRenderer shape, OrthographicCamera cam, boolean mark, int type) {
		this.text = text;
		this.font = font;
		color = font.getColor().cpy();
		this.mark = mark;
		this.type = type;
		this.cam = cam;
		this.shape = shape;
		this.batch = batch;
		
		color2 = Color.BLACK;
		// resize();
	}

	public Button(String text, BitmapFont font, SpriteBatch batch, ShapeRenderer shape, OrthographicCamera cam, boolean mark, int type, int mnemonic) {
		this.text = text;
		this.font = font;
		color = font.getColor().cpy();
		this.mark = mark;
		this.type = type;
		this.cam = cam;
		this.shape = shape;
		this.batch = batch;
		mn = mnemonic;
		
		color2 = Color.BLACK;
		// resize();
	}

	public void render() {
		touchable = true;

		/*
		 * shape.setProjectionMatrix(cam.combined);
		 * 
		 * if((justTouched(true) || isTouched(true) || toggled) && mark){
		 * shape.begin(ShapeRenderer.ShapeType.Filled);
		 * font.setColor(Color.BLACK); } else {
		 * shape.begin(ShapeRenderer.ShapeType.Line); } shape.rect(x, y, width,
		 * heigth); shape.end();
		 */

		//System.out.println(isLocalTouched() + ", " + isMouseOn() + ", " + checkKey() + ", " + toggled);

		if (mark && (isLocalTouched() || isMouseOn() || toggled)) {
			font.setColor(color2);
		}
		// font.draw(batch, text, x + width /2 - font.getSpaceWidth() * 1.5f *
		// text.length(), y + heigth/2 + font.getLineHeight()/3); //x + width -
		// getSW *5 (old one)

		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT) && mn > 0) {
			if (!mnemoniced) {
				mnemoniced = true;
			}
			font.draw(batch, mnText, mnX, y + height);
		} else {
			if (mnemoniced) {
				mnemoniced = false;
			}

			font.draw(batch, text, x, y + height);
		}

		font.setColor(color);
	}

	/*
	 * public void render(String text){ touchable = true;
	 * 
	 * /*shape.setProjectionMatrix(cam.combined);
	 * 
	 * if((justTouched(true) || isTouched(true) || toggled) && mark){
	 * shape.begin(ShapeRenderer.ShapeType.Filled); font.setColor(Color.BLACK);
	 * } else { shape.begin(ShapeRenderer.ShapeType.Line); } shape.rect(x, y,
	 * width, heigth); shape.end();
	 * 
	 * 
	 * if(mark && (justLocalTouched() || isLocalTouched() || isMouseOn() ||
	 * toggled)) { font.setColor(Color.BLACK); } //font.draw(batch, text, x +
	 * width /2 - font.getSpaceWidth() * 1.5f * text.length(), y + heigth/2 +
	 * font.getLineHeight()/3); //x + width - getSW *5 (old one)
	 * font.draw(batch, text, x, y + height); font.setColor(color); }
	 */

	private boolean isLocalTouched() {
		// if(type == 0)
		if (touchable) {
			// if(type == 0)
			for (int i = 0; i < 5; i++) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				cam.unproject(touchPos);

				if (mnemoniced)
					touched = (Gdx.input.isTouched(i) && touchPos.x >= mnX && touchPos.x <= mnX + mnWidth && touchPos.y <= y + height && touchPos.y >= y) || checkKey();
				else
					touched = (Gdx.input.isTouched(i) && touchPos.x >= x && touchPos.x <= x + width && touchPos.y <= y + height && touchPos.y >= y) || checkKey();

				if (!holdOn) {
					if (touched) {
						// touchable = false;
						multitouch = (byte) i;
						return true;
					}
				} else if (!touched)
					holdOn = false;
				// else if(type == 1){
				// touchable = false;

				// return toggled;
				// }
			}
			// else
			if (type != 0)
				return toggled;
		}
		// else if(type == 1){
		// if()
		// }

		return false;
	}

	public boolean isTouched() {
		// if(type == 0)
		if (touchable) {
			touchable = false;
			// if(type == 0)
			for (int i = 0; i < 5; i++) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				cam.unproject(touchPos);
				if (mnemoniced)
					touched = (Gdx.input.isTouched(i) && touchPos.x >= mnX && touchPos.x <= mnX + mnWidth && touchPos.y <= y + height && touchPos.y >= y) || checkKey();
				else
					touched = (Gdx.input.isTouched(i) && touchPos.x >= x && touchPos.x <= x + width && touchPos.y <= y + height && touchPos.y >= y) || checkKey();
				// if(!local)
				// touchable = false;

				if (touched) {
					// touchable = false;
					multitouch = (byte) i;
					if (type != 0 && !toggled)
						toggled = true;
					return true;
				}
				// else if(type == 1){
				// touchable = false;

				// return toggled;
				// }
			}
			if (type != 0)
				return toggled;
		}
		// else if(type == 1){
		// if()
		// }

		return false;
	}

	public boolean isMouseOn() {
		Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(touchPos);

		if (mnemoniced)
			return touchPos.x >= mnX && touchPos.x <= mnX + mnWidth && touchPos.y <= y + height && touchPos.y >= y;
		else
			return touchPos.x >= x && touchPos.x <= x + width && touchPos.y <= y + height && touchPos.y >= y;
	}

	public boolean justReleased() {
		Vector3 touchPos = new Vector3(Gdx.input.getX(multitouch), Gdx.input.getY(multitouch), 0);
		cam.unproject(touchPos);

		if (touched && !(Gdx.input.isTouched(multitouch) && touchPos.x >= x && touchPos.x <= x + width && touchPos.y <= y + height && touchPos.y >= y)) {
			touched = false;
			return true;
		}

		return false;
	}

	/*
	 * private boolean justLocalTouched() { if (touchable) { for (int i = 0; i <
	 * 10; i++) { Vector3 touchPos = new Vector3(Gdx.input.getX(i),
	 * Gdx.input.getY(i), 0); cam.unproject(touchPos);
	 * 
	 * boolean touch = (Gdx.input.justTouched() && touchPos.x >= x && touchPos.x
	 * <= x + width && touchPos.y <= y + height && touchPos.y >= y) ||
	 * checkJustKey();
	 * 
	 * if (touch) { switch (type) { case 0: return true; case 1: toggled =
	 * !toggled; break; case 2: if (!toggled) toggled = true; break; } break; }
	 * 
	 * // if(touch) // return touch; } } return false; }
	 */

	public boolean justTouched() {
		if (touchable) {
			touchable = false;
			for (int i = 0; i < 10; i++) {
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				cam.unproject(touchPos);

				boolean touch;
				if (mnemoniced)
					touch = (Gdx.input.justTouched() && touchPos.x >= mnX && touchPos.x <= mnX + mnWidth && touchPos.y <= y + height && touchPos.y >= y) || checkJustKey();
				else
					touch = (Gdx.input.justTouched() && touchPos.x >= x && touchPos.x <= x + width && touchPos.y <= y + height && touchPos.y >= y) || checkJustKey();

				if (touch) {
					switch (type) {
					case 0:
						return true;
					case 1:
						toggled = !toggled;
						break;
					case 2:
						if (!toggled)
							toggled = true;
						break;
					}
					break;
				}
			}
		}
		return false;
	}

	boolean checkKey() {
		return Gdx.input.isKeyPressed(Keys.valueOf(Keys.toString(mn)));
	}

	boolean checkJustKey() {
		return Gdx.input.isKeyJustPressed(Keys.valueOf(Keys.toString(mn)));
	}

	void mnResize() {
		String temp, k = Keys.toString(mn);
		if(k.equals("Escape"))
			temp = "Esc. ";
		else temp = k + ". ";
		mnText = temp + text;

		mnWidth = 0;
		boolean flag = false;
		for (int i = 0; i < mnText.length(); i++) {
			char tempCh = mnText.charAt(i);
			mnWidth += font.getData().getGlyph(tempCh).width * font.getScaleX();

			if (tempCh == ' ' && !flag) {
				mnX = x - mnWidth;
				flag = true;
			}
		}
	}

	public void resize() {
		width = 0;

		for (int i = 0; i < text.length(); i++) {
			width += font.getData().getGlyph(text.charAt(i)).width * font.getScaleX();
		}

		height = font.getLineHeight() - 13;
	}

	public void resize(String text) {
		width = 0;

		boolean flag = true;
		for (int i = 0; i < text.length(); i++) {
			char temp = text.charAt(i);

			width += font.getData().getGlyph(temp).width * font.getScaleX();

			if (flag)
				x -= width;

			if (temp == ' ')
				flag = false;
		}

		height = font.getLineHeight() - 13;

	}
	
	public void holdOn() {
		holdOn = true;
	}

	public void setToggled(boolean b) {
		toggled = b;
	}
	
	public void setMarkColor(Color c) {
		color2 = c;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;

		holdOn = true;
		
		mnResize();
		resize();
	}

	public void setLocation(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;

		holdOn = true;

		mnResize();
		resize();
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float heigth) {
		this.height = heigth;
	}

	public void setText(String text) {
		this.text = text;
		
		mnResize();
		resize();
	}

	public float getTouchX() {
		return Gdx.input.getX() - x;
	}

	public float getTouchX(int i) {
		return Gdx.input.getX(i) - x;
	}

	public float getTouchY() {
		return Gdx.input.getY() - y;
	}

	public float getTouchY(int i) {
		return Gdx.input.getY(i) - y;
	}
}
