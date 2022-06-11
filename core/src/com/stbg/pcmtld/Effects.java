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
