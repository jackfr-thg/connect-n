package com.thg.accelerator23.connectn.ai.jackjack;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.ArrayList;

class BBOps {
	public static final BigInteger zero = BigInteger.ZERO;
	public static final BigInteger one = BigInteger.ONE;

	public static final int vShift = 1;
	public static final int hShift = 8;
	public static final int dPosShift = 7;
	public static final int dNegShift = 9;

	public static final BigInteger vBlocked = new BigInteger(
			"11111000111110001111100011111000111110001111100011111000111110001111100011111000", 2);
	public static final BigInteger hBlocked = new BigInteger(
			"11111111111111111111111111111111111111111111111111111111000000000000000000000000", 2);
	public static final BigInteger dPosBlocked = new BigInteger(
			"11111000111110001111100011111000111110001111100011111000000000000000000000000", 2);
	public static final BigInteger dNegBlocked = new BigInteger(
			"11111000111110001111100011111000111110001111100011111000000000000000000000000000", 2);

	public static final BigInteger bv = new BigInteger(
			"11111000111110001111100011111000111110001111100011111000111110001111100011111000", 2);
	public static final BigInteger bh1 = new BigInteger(
			"11111111111111111111111111111111111111111111111111111111000000000000000000000000", 2);
	public static final BigInteger bh2 = new BigInteger(
			"00000000111111111111111111111111111111111111111111111111111111110000000000000000", 2);
	public static final BigInteger bh3 = new BigInteger(
			"00000000000000001111111111111111111111111111111111111111111111111111111100000000", 2);
	public static final BigInteger bh4 = new BigInteger(
			"00000000000000000000000011111111111111111111111111111111111111111111111111111111", 2);
	public static final BigInteger bdp1 = new BigInteger(
			"00011111000111110001111100011111000111110001111100011111000000000000000000000000", 2);
	public static final BigInteger bdp2 = new BigInteger(
			"00000000001111100011111000111110001111100011111000111110001111100000000000000000", 2);
	public static final BigInteger bdp3 = new BigInteger(
			"00000000000000000111110001111100011111000111110001111100011111000111110000000000", 2);
	public static final BigInteger bdp4 = new BigInteger(
			"00000000000000000000000011111000111110001111100011111000111110001111100011111000", 2);
	public static final BigInteger bdn1 = new BigInteger(
			"11111000111110001111100011111000111110001111100011111000000000000000000000000000", 2);
	public static final BigInteger bdn2 = new BigInteger(
			"00000000011111000111110001111100011111000111110001111100011111000000000000000000", 2);
	public static final BigInteger bdn3 = new BigInteger(
			"00000000000000000011111000111110001111100011111000111110001111100011111000000000", 2);
	public static final BigInteger bdn4 = new BigInteger(
			"00000000000000000000000000011111000111110001111100011111000111110001111100011111", 2);

	public static final int[] shifts = { 1, 8, 7, 9 };
	public static final BigInteger[] blockers = { vBlocked, hBlocked, dPosBlocked,
			dNegBlocked };

	public static final BigInteger[] cachedShifts = { one,
			one.shiftLeft(1),
			one.shiftLeft(2),
			one.shiftLeft(3),
			one.shiftLeft(4),
			one.shiftLeft(5),
			one.shiftLeft(6),
			one.shiftLeft(7),
			one.shiftLeft(8),
			one.shiftLeft(9),
			one.shiftLeft(10),
			one.shiftLeft(11),
			one.shiftLeft(12),
			one.shiftLeft(13),
			one.shiftLeft(14),
			one.shiftLeft(15),
			one.shiftLeft(16),
			one.shiftLeft(17),
			one.shiftLeft(18),
			one.shiftLeft(19),
			one.shiftLeft(20),
			one.shiftLeft(21),
			one.shiftLeft(22),
			one.shiftLeft(23),
			one.shiftLeft(24),
			one.shiftLeft(25),
			one.shiftLeft(26),
			one.shiftLeft(27),
			one.shiftLeft(28),
			one.shiftLeft(29),
			one.shiftLeft(30),
			one.shiftLeft(31),
			one.shiftLeft(32),
			one.shiftLeft(33),
			one.shiftLeft(34),
			one.shiftLeft(35),
			one.shiftLeft(36),
			one.shiftLeft(37),
			one.shiftLeft(38),
			one.shiftLeft(39),
			one.shiftLeft(40),
			one.shiftLeft(41),
			one.shiftLeft(42),
			one.shiftLeft(43),
			one.shiftLeft(44),
			one.shiftLeft(45),
			one.shiftLeft(46),
			one.shiftLeft(47),
			one.shiftLeft(48),
			one.shiftLeft(49),
			one.shiftLeft(50),
			one.shiftLeft(51),
			one.shiftLeft(52),
			one.shiftLeft(53),
			one.shiftLeft(54),
			one.shiftLeft(55),
			one.shiftLeft(56),
			one.shiftLeft(57),
			one.shiftLeft(58),
			one.shiftLeft(59),
			one.shiftLeft(60),
			one.shiftLeft(61),
			one.shiftLeft(62),
			one.shiftLeft(63),
			one.shiftLeft(64),
			one.shiftLeft(65),
			one.shiftLeft(66),
			one.shiftLeft(67),
			one.shiftLeft(68),
			one.shiftLeft(69),
			one.shiftLeft(70),
			one.shiftLeft(71),
			one.shiftLeft(72),
			one.shiftLeft(73),
			one.shiftLeft(74),
			one.shiftLeft(75),
			one.shiftLeft(76),
			one.shiftLeft(77),
			one.shiftLeft(78),
			one.shiftLeft(79),
	};

	public static BitBoard convertToBitBoard(Board board, String playerString) {
		Counter[][] arr = board.getCounterPlacements();

		String oppString = (playerString == "X") ? "O" : "X";

		BigInteger playerBoard = zero;
		BigInteger oppBoard = zero;

		BitBoard newBoard = new BitBoard(playerBoard, oppBoard, 0);

		for (int col = 0; col < 10; col++) {
			boolean foundNull = false;
			for (int row = 0; row < 8; row++) {
				// Calculate the index of bit position for this cell (0 to 79)
				int position = ((9 - col) * 8) + (row);

				// Shift to get binary number with 1 at position of counter to (possibly add)
				BigInteger bitPosition = cachedShifts[position];
				if (arr[col][row] != null) {
					// add 1 to binary numbers at current position if a counter exists there
					if (playerString.equals(arr[col][row].getStringRepresentation())) {
						playerBoard = playerBoard.or(bitPosition);
					} else if (oppString.equals(arr[col][row].getStringRepresentation())) {
						oppBoard = oppBoard.or(bitPosition);
					}
				} else {
					// populate heights for current collumn if found first null variable
					if (!foundNull) {
						newBoard.sH(col, row);
						foundNull = true;
					}
				}
			}
			if (!foundNull) {
				// Set height of current collumn (ie where to put next counter) to -1 if is full
				newBoard.sH(col, 8);
			}
		}
		return new BitBoard(playerBoard, oppBoard, newBoard.gHFull());
	}

	public static boolean isMoveLegal(BitBoard board, int move) {
		return board.gH(move) != 8;
	}

	public static ArrayList<Integer> getAvailableMoves(BitBoard board) {
		ArrayList<Integer> legalMoves = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (isMoveLegal(board, i)) {
				legalMoves.add(i);
			}
		}
		return legalMoves;
	}

	public static BitBoard doMove(BitBoard board, int move, boolean isPlayersMove) {
		if (!isMoveLegal(board, move)) {
			return board;
		}
		long newHeights = board.incrH(move);

		BigInteger newPlayerBoard;
		BigInteger newOppBoard;

		if (isPlayersMove) {
			newPlayerBoard = board.gPB().xor(cachedShifts[(8 * (9 - move)) + (board.gH(move))]);
			newOppBoard = board.gOB();
		} else {
			newPlayerBoard = board.gPB();
			newOppBoard = board.gOB().xor(cachedShifts[(8 * (9 - move)) + (board.gH(move))]);
		}

		return new BitBoard(newPlayerBoard, newOppBoard, newHeights);
	}

	public static boolean isWin(BitBoard board) {
		return (!vBlocked.and(board.gPB()).and(board.gPB().shiftLeft(1)).and(board.gPB().shiftLeft(2))
				.and(board.gPB().shiftLeft(3))
				.or(hBlocked.and(board.gPB()).and(board.gPB().shiftLeft(8))
						.and(board.gPB().shiftLeft(16))
						.and(board.gPB().shiftLeft(24)))
				.or(dPosBlocked.and(board.gPB()).and(board.gPB().shiftLeft(7))
						.and(board.gPB().shiftLeft(14))
						.and(board.gPB().shiftLeft(21)))
				.or(dNegBlocked.and(board.gPB()).and(board.gPB().shiftLeft(9))
						.and(board.gPB().shiftLeft(18))
						.and(board.gPB().shiftLeft(27)))
				.equals(zero));
	}

	public static boolean isLoss(BitBoard board) {
		return (!vBlocked.and(board.gOB()).and(board.gOB().shiftLeft(1)).and(board.gOB().shiftLeft(2))
				.and(board.gOB().shiftLeft(3))
				.or(hBlocked.and(board.gOB()).and(board.gOB().shiftLeft(8))
						.and(board.gOB().shiftLeft(16))
						.and(board.gOB().shiftLeft(24)))
				.or(dPosBlocked.and(board.gOB()).and(board.gOB().shiftLeft(7))
						.and(board.gOB().shiftLeft(14))
						.and(board.gOB().shiftLeft(21)))
				.or(dNegBlocked.and(board.gOB()).and(board.gOB().shiftLeft(9))
						.and(board.gOB().shiftLeft(18))
						.and(board.gOB().shiftLeft(27)))
				.equals(zero));
	}

	public static ArrayList<Integer> getWinningMoves(BitBoard board) {
		ArrayList<Integer> winningMoves = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (isMoveLegal(board, i) && isWin(doMove(board, i, true))) {
				winningMoves.add(i);
			}
		}
		return winningMoves;
	}

	public static ArrayList<Integer> getLosingMoves(BitBoard board) {
		ArrayList<Integer> losingMoves = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if (isMoveLegal(board, i) && isLoss(doMove(board, i, false))) {
				losingMoves.add(i);
			}
		}
		return losingMoves;
	}

	public static BigInteger findThreats(BitBoard board, BigInteger cB) {
		return cB.shiftRight(3).and(cB.shiftRight(2)).and(cB.shiftRight(1))
				.and((board.gPB().or(board.gOB())).not()).and(bv).or(
						cB.shiftLeft(8).and(cB.shiftLeft(16)).and(cB.shiftLeft(
								24)).and((board.gPB().or(board.gOB())).not()).and(bh1))
				.or(
						cB.shiftRight(8).and(cB.shiftLeft(8)).and(cB.shiftLeft(
								16)).and((board.gPB().or(board.gOB())).not()).and(bh2))
				.or(
						cB.shiftRight(16).and(cB.shiftRight(8)).and(cB.shiftLeft(
								8)).and((board.gPB().or(board.gOB())).not()).and(bh3))
				.or(
						cB.shiftRight(24).and(cB.shiftRight(16)).and(cB.shiftRight(
								8)).and((board.gPB().or(board.gOB())).not()).and(bh4))
				.or(
						cB.shiftLeft(7).and(cB.shiftLeft(14)).and(cB.shiftLeft(
								21)).and((board.gPB().or(board.gOB())).not()).and(bdp1))
				.or(
						cB.shiftRight(7).and(cB.shiftLeft(7)).and(cB.shiftLeft(
								14)).and((board.gPB().or(board.gOB())).not()).and(bdp2))
				.or(
						cB.shiftRight(14).and(cB.shiftRight(7)).and(cB.shiftLeft(
								7)).and((board.gPB().or(board.gOB())).not()).and(bdp3))
				.or(
						cB.shiftRight(21).and(cB.shiftRight(14)).and(cB.shiftRight(
								7)).and((board.gPB().or(board.gOB())).not()).and(bdp4))
				.or(
						cB.shiftLeft(9).and(cB.shiftLeft(18)).and(cB.shiftLeft(
								27)).and((board.gPB().or(board.gOB())).not()).and(bdn1))
				.or(
						cB.shiftRight(9).and(cB.shiftLeft(9)).and(cB.shiftLeft(
								18)).and((board.gPB().or(board.gOB())).not()).and(bdn2))
				.or(
						cB.shiftRight(18).and(cB.shiftRight(9)).and(cB.shiftLeft(
								9)).and((board.gPB().or(board.gOB())).not()).and(bdn3))
				.or(
						cB.shiftRight(27).and(cB.shiftRight(18)).and(cB.shiftRight(9))
								.and((board.gPB().or(board.gOB())).not()).and(bdn4));

	}

	public static BigInteger findPlayerThreats(BitBoard board) {
		return board.gPB().shiftRight(3).and(board.gPB().shiftRight(2)).and(board.gPB().shiftRight(1))
				.and((board.gPB().or(board.gOB())).not()).and(bv).or(
						board.gPB().shiftLeft(8).and(board.gPB().shiftLeft(16))
								.and(board.gPB().shiftLeft(
										24))
								.and((board.gPB().or(board.gOB())).not()).and(bh1))
				.or(
						board.gPB().shiftRight(8).and(board.gPB().shiftLeft(8))
								.and(board.gPB().shiftLeft(
										16))
								.and((board.gPB().or(board.gOB())).not()).and(bh2))
				.or(
						board.gPB().shiftRight(16).and(board.gPB().shiftRight(8))
								.and(board.gPB().shiftLeft(
										8))
								.and((board.gPB().or(board.gOB())).not()).and(bh3))
				.or(
						board.gPB().shiftRight(24).and(board.gPB().shiftRight(16))
								.and(board.gPB().shiftRight(
										8))
								.and((board.gPB().or(board.gOB())).not()).and(bh4))
				.or(
						board.gPB().shiftLeft(7).and(board.gPB().shiftLeft(14))
								.and(board.gPB().shiftLeft(
										21))
								.and((board.gPB().or(board.gOB())).not()).and(bdp1))
				.or(
						board.gPB().shiftRight(7).and(board.gPB().shiftLeft(7))
								.and(board.gPB().shiftLeft(
										14))
								.and((board.gPB().or(board.gOB())).not()).and(bdp2))
				.or(
						board.gPB().shiftRight(14).and(board.gPB().shiftRight(7))
								.and(board.gPB().shiftLeft(
										7))
								.and((board.gPB().or(board.gOB())).not()).and(bdp3))
				.or(
						board.gPB().shiftRight(21).and(board.gPB().shiftRight(14))
								.and(board.gPB().shiftRight(
										7))
								.and((board.gPB().or(board.gOB())).not()).and(bdp4))
				.or(
						board.gPB().shiftLeft(9).and(board.gPB().shiftLeft(18))
								.and(board.gPB().shiftLeft(
										27))
								.and((board.gPB().or(board.gOB())).not()).and(bdn1))
				.or(
						board.gPB().shiftRight(9).and(board.gPB().shiftLeft(9))
								.and(board.gPB().shiftLeft(
										18))
								.and((board.gPB().or(board.gOB())).not()).and(bdn2))
				.or(
						board.gPB().shiftRight(18).and(board.gPB().shiftRight(9))
								.and(board.gPB().shiftLeft(
										9))
								.and((board.gPB().or(board.gOB())).not()).and(bdn3))
				.or(
						board.gPB().shiftRight(27).and(board.gPB().shiftRight(18))
								.and(board.gPB().shiftRight(9))
								.and((board.gPB().or(board.gOB())).not()).and(bdn4));

	}

	public static BigInteger findOppThreats(BitBoard board) {
		return board.gOB().shiftRight(3).and(board.gOB().shiftRight(2)).and(board.gOB().shiftRight(1))
				.and((board.gPB().or(board.gOB())).not()).and(bv).or(
						board.gOB().shiftLeft(8).and(board.gOB().shiftLeft(16))
								.and(board.gOB().shiftLeft(
										24))
								.and((board.gPB().or(board.gOB())).not()).and(bh1))
				.or(
						board.gOB().shiftRight(8).and(board.gOB().shiftLeft(8))
								.and(board.gOB().shiftLeft(
										16))
								.and((board.gPB().or(board.gOB())).not()).and(bh2))
				.or(
						board.gOB().shiftRight(16).and(board.gOB().shiftRight(8))
								.and(board.gOB().shiftLeft(
										8))
								.and((board.gPB().or(board.gOB())).not()).and(bh3))
				.or(
						board.gOB().shiftRight(24).and(board.gOB().shiftRight(16))
								.and(board.gOB().shiftRight(
										8))
								.and((board.gPB().or(board.gOB())).not()).and(bh4))
				.or(
						board.gOB().shiftLeft(7).and(board.gOB().shiftLeft(14))
								.and(board.gOB().shiftLeft(
										21))
								.and((board.gPB().or(board.gOB())).not()).and(bdp1))
				.or(
						board.gOB().shiftRight(7).and(board.gOB().shiftLeft(7))
								.and(board.gOB().shiftLeft(
										14))
								.and((board.gPB().or(board.gOB())).not()).and(bdp2))
				.or(
						board.gOB().shiftRight(14).and(board.gOB().shiftRight(7))
								.and(board.gOB().shiftLeft(
										7))
								.and((board.gPB().or(board.gOB())).not()).and(bdp3))
				.or(
						board.gOB().shiftRight(21).and(board.gOB().shiftRight(14))
								.and(board.gOB().shiftRight(
										7))
								.and((board.gPB().or(board.gOB())).not()).and(bdp4))
				.or(
						board.gOB().shiftLeft(9).and(board.gOB().shiftLeft(18))
								.and(board.gOB().shiftLeft(
										27))
								.and((board.gPB().or(board.gOB())).not()).and(bdn1))
				.or(
						board.gOB().shiftRight(9).and(board.gOB().shiftLeft(9))
								.and(board.gOB().shiftLeft(
										18))
								.and((board.gPB().or(board.gOB())).not()).and(bdn2))
				.or(
						board.gOB().shiftRight(18).and(board.gOB().shiftRight(9))
								.and(board.gOB().shiftLeft(
										9))
								.and((board.gPB().or(board.gOB())).not()).and(bdn3))
				.or(
						board.gOB().shiftRight(27).and(board.gOB().shiftRight(18))
								.and(board.gOB().shiftRight(9))
								.and((board.gPB().or(board.gOB())).not()).and(bdn4));

	}

	public static String convertThreatsToString(BigInteger threats) {
		StringBuilder boardString = new StringBuilder();
		boardString.append(" |");
		for (int row = 7; row > -1; row--) {
			for (int col = 9; col > -1; col--) {
				// Calculate the position in the binary representation
				int bitPosition = (8 * col) + row;

				// Determine if this position is occupied by player1 or player2
				boolean isPlayer = threats.testBit(bitPosition);

				// Append the appropriate character
				if (isPlayer) {
					boardString.append("T|"); // Represent player 1 with 'X'
				} else {
					boardString.append(" |"); // Empty space
				}
			}
			boardString.append("\n"); // Add a newline at the end of each row
			boardString.append("----------------------"); // Add a newline at the end of each row
			boardString.append("\n |"); // Add a newline at the end of each row
		}
		return boardString.toString();
	}

	public static double evaluateBoard(BitBoard board) {
		double lambda = (double) 100;
		double base = (double) 0.7;
		double score = (double) 0;
		BigInteger plThreats = findPlayerThreats(board);
		BigInteger opThreats = findOppThreats(board);

		for (int i = 0; i < 10; i++) {
			double subScore = (double) 0;
			for (int j = board.gH(i) + 1; j < 8; j++) {
				if (plThreats.testBit((8 * i) + j)) {
					subScore += Math.pow(base, j);
				}
				if (opThreats.testBit((8 * i) + j)) {
					subScore -= Math.pow(base, j);
				}
			}
			score += (Math.pow(base, -board.gH(i))) * subScore;
		}
		return score * lambda;
	}
}
