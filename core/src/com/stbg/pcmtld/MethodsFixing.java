package com.stbg.pcmtld;

import static org.junit.Assert.*;

import org.junit.Test;

public class MethodsFixing {

	@Test
	public void rangeTest() {
		int[][][] map = new int[][][] {{{0, 0, 1, 1, 1}, {0, 0, 0, 0, 0}, {1, 0, 0, 0, 0}}};
		assertEquals(true, CustomGameMapLoader.isRangeOfTiles(0, 4, 3, 1, map));
	}

}
