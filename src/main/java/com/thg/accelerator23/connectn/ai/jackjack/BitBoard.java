package com.thg.accelerator23.connectn.ai.jackjack;

import java.math.BigInteger;
import java.util.Arrays;

class BitBoard {
	BigInteger playerBoard;
	BigInteger oppBoard;
	long heights;
	static long COLUMN_MASK = (1 << 4) - 1;

	public BitBoard(BigInteger playerBoard, BigInteger oppBoard, long heights) {
		this.playerBoard = playerBoard;
		this.oppBoard = oppBoard;
		this.heights = heights;
	}

	public BigInteger gPB() {
		return playerBoard;
	}

	public BigInteger gOB() {
		return oppBoard;
	}

	public long gHFull() {
		return this.heights;
	}

	public int gH(int columnIndex) {
		return (int) ((this.heights >>> (columnIndex * 4)) & COLUMN_MASK);
	}

	public int gHStatic(long num, int columnIndex) {
		return (int) ((num >>> (columnIndex * 4)) & COLUMN_MASK);
	}

	public void sH(int columnIndex, int val) {
		this.heights = (this.heights & ~(COLUMN_MASK << (columnIndex * 4))) | ((long) val << (columnIndex * 4));
	}

	public long incrH(int columnIndex) {

		return (this.heights & ~(COLUMN_MASK << (columnIndex * 4)))
				| ((long) (this.gH(columnIndex) + 1) << (columnIndex * 4));
	}

	public int[] seeHeights() {
		int[] answer = new int[10];
		for (int i = 0; i < 10; i++) {
			answer[i] = this.gH(i);
		}
		return answer;
	}

	public int[] seeStaticHeights(long num) {
		int[] answer = new int[10];
		for (int i = 0; i < 10; i++) {
			answer[i] = gHStatic(num, i);
		}
		return answer;

	}

	@Override
	public String toString() {
		StringBuilder boardString = new StringBuilder();
		for (int row = 7; row > -1; row--) {
			for (int col = 9; col > -1; col--) {
				// Calculate the position in the binary representation
				int bitPosition = (8 * col) + row;

				// Determine if this position is occupied by player1 or player2
				boolean isPlayer = playerBoard.testBit(bitPosition);
				boolean isOpp = oppBoard.testBit(bitPosition);

				// Append the appropriate character
				if (isPlayer) {
					boardString.append('X'); // Represent player 1 with 'X'
				} else if (isOpp) {
					boardString.append('O'); // Represent player 2 with 'O'
				} else {
					boardString.append(' '); // Empty space
				}
			}
			boardString.append("\n"); // Add a newline at the end of each row

		}

		return boardString.toString();
	}

}
