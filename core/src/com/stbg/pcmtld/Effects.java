package com.stbg.pcmtld;

public final class Effects {

	public static Effect speedEffect(int time) {
		return new Effect(time) {
			int speedOffset = 170;
			
			@Override
			public void onStart(Player player) {
				player.SPEED+= speedOffset;
			}

			/*@Override
			public void update(Player player, float delta) {}*/

			@Override
			public void onEnd(Player player) {
				player.SPEED-= speedOffset;
			}
			
		};
	}
	
	public static Effect invisibilityEffect(int time) {
		return new Effect(time) {
			
			@Override
			public void onStart(Player player) {
				player.invisible = true;
			}

			@Override
			public boolean update(Player player, float delta) {
				if(time <= 1.5f && time > 1)
					player.setStartTime(time);
				
				return super.update(player, delta);
			}

			@Override
			public void onEnd(Player player) {
				player.invisible = false;
			}
			
		};
	}
	
	public static Effect shootingEffect(int time) {
		return new Effect(time) {
			float timeInterval;
			
			@Override
			public void onStart(Player player) {}

			@Override
			public boolean update(Player player, float delta) {
				if(timeInterval <= 0) {
					player.shoot();
					timeInterval = 0.2f;
				}else
					timeInterval-= delta;
				
				return super.update(player, delta);
			}

			@Override
			public void onEnd(Player player) {}
			
		};
	}
	
	public static abstract class Effect {
		protected float time;
		
		public Effect(int time) {
			this.time = time;
		}
		
		public abstract void onStart(Player player);
		
		/**
		 * 
		 * @param player
		 * @param delta - delta time
		 * @return true if alive, false if dead
		 */
		public boolean update(Player player, float delta) {
			if(time <= 0) {
				onEnd(player);
				return false;
			}
			
			time -= delta;
			
			return true;
		}
		public abstract void onEnd(Player player);
	}
}
