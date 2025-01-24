package com.thg.accelerator23.connectn.ai.jackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.math.BigInteger;

// board.hasCounterAtPosition is used to check if a counter has been placed at the given position.
// board.getCounterPlacements returns a 2D counter array of the current game state.

// This implementation uses alpha-beta pruning!
public class TestNN extends Player {

	private static final int MAX_DEPTH = 8; // Maximum depth of the explored game tree.
	public long overallTime;
	public HashMap<BigInteger, Double> cache;
	public HashMap<BigInteger, Double> wonCache;
	public HashMap<BigInteger, Double> lostCache;

	public TestNN(Counter counter) {
		super(counter, TestNN.class.getName());
		this.overallTime = 0;
		this.cache = new HashMap<>();
		this.wonCache = new HashMap<>();
		this.lostCache = new HashMap<>();
	}

	public boolean isCached(BitBoard board) {
		return this.cache.containsKey(board.gPB().shiftLeft(80).add(board.gOB()));
	}

	public double getCachedValue(BitBoard board) {
		return this.cache.get(board.gPB().shiftLeft(80).add(board.gOB()));
	}

	public void putCachedValue(BitBoard board, Double value) {
		this.cache.put(board.gPB().shiftLeft(80).add(board.gOB()), value);
	}

	public boolean isWonCached(BitBoard board) {
		return this.wonCache.containsKey(board.gPB().shiftLeft(80).add(board.gOB()));
	}

	public double getWonCachedValue(BitBoard board) {
		return this.wonCache.get(board.gPB().shiftLeft(80).add(board.gOB()));
	}

	public void putWonCachedValue(BitBoard board, Double value) {
		this.wonCache.put(board.gPB().shiftLeft(80).add(board.gOB()), value);
	}

	public boolean isLostCached(BitBoard board) {
		return this.lostCache.containsKey(board.gPB().shiftLeft(80).add(board.gOB()));
	}

	public double getLostCachedValue(BitBoard board) {
		return this.lostCache.get(board.gPB().shiftLeft(80).add(board.gOB()));
	}

	public void putLostCachedValue(BitBoard board, Double value) {
		this.lostCache.put(board.gPB().shiftLeft(80).add(board.gOB()), value);
	}

	/**
	 * @param move  Column index of the move.
	 * @param board The current game board.
	 * @return A bias value for prioritizing central moves.
	 */
	private int calculateCentralBias(int move) {
		return -Math.abs(5 - move); // Higher bias for moves closer to the center.
	}

	/**
	 * @param move  Column index of the move.
	 * @param board The current game board.
	 * @return A bias value for prioritizing central moves.
	 */
	public ArrayList<Integer> getSortedAvailableMoves(BitBoard board) {
		ArrayList<Integer> availableMoves = BBOps.getAvailableMoves(board);
		availableMoves.sort(Comparator.comparingInt(move -> -calculateCentralBias(move)));
		return availableMoves;
	}

	/**
	 * @param board Representation of the game state.
	 * @return An integer representing the "best" move obtained by the minimax
	 *         algorithm.
	 */
	@Override
	public int makeMove(Board inputBoard) {
		long startTime = System.nanoTime();
		BitBoard board = BBOps.convertToBitBoard(inputBoard, getCounter().toString());
		long timeOut = startTime + 9800000000l;
		List<Integer> availableMoves = getSortedAvailableMoves(board);

		// System.out.println(availableMoves);

		int max_depth_reached = 0;
		int overallBestMove = -1;

		for (int max_depth = 2; max_depth < 50; max_depth++) {
			int bestMove = -1;
			double moveValue;
			double bestValue = -Double.MAX_VALUE;
			double alpha = -Double.MAX_VALUE;
			double beta = Double.MAX_VALUE;
			// Iterate over all valid moves.
			for (int move : availableMoves) {
				// Simulate making a move.
				BitBoard simulatedBoard = BBOps.doMove(board, move, true);

				// Evaluate how move via minimax algorithm alpha-beta pruning.
				moveValue = minimax(simulatedBoard, max_depth, false, alpha, beta, 0, timeOut);

				// Dynamically update best move based on the results of the algorithm.
				if (moveValue > bestValue) {
					bestValue = moveValue;
					bestMove = move;
				}

				// Update alpha to reflect the best value found so far.
				alpha = Math.max(alpha, bestValue);
			}
			max_depth_reached = max_depth;
			overallBestMove = bestMove;
			if (System.nanoTime() > timeOut) {
				break;
			}
		}

		// Calculate elapsed time in nanoseconds (convert to milliseconds if needed)
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		overallTime = overallTime + elapsedTime;

		return overallBestMove;
	}

	/**
	 * @param board        Representation of the game state.
	 * @param depth        The depth of the explored game tree.
	 * @param isMaximising Flag indicating whether the current move is for the
	 *                     maximising player.
	 * @param alpha        Parameter used in pruning branches.
	 * @param beta         Parameter used in pruning branches.
	 * @return The evaluation score of the best move.
	 */
	private double minimax(BitBoard board, int depth, boolean isMaximising, double alpha, double beta,
			int realDepth, long timeOut) {

		// Winning/losing move.
		// if (this.isCached(board)) {
		// return this.getCachedValue(board) * Math.pow(0.99, realDepth);
		// }

		if (isMaximising && (this.isLostCached(board))) {
			return -1000 * (Math.pow(0.99, realDepth));
		}
		if (!isMaximising && (this.isWonCached(board))) {
			return 1000 * (Math.pow(0.99, realDepth));
		}

		if (isMaximising && BBOps.isLoss(board)) {
			this.putLostCachedValue(board, (double) -1000);
			return -1000 * (Math.pow(0.99, realDepth));
		}
		if (!isMaximising && BBOps.isWin(board)) {
			this.putWonCachedValue(board, (double) 1000);
			return 1000 * (Math.pow(0.99, realDepth));
		}

		// Draw or depth limit reached.
		if (depth == 0 || System.nanoTime() >= timeOut || getSortedAvailableMoves(board).isEmpty()) {
			if (this.isCached(board)) {
				return this.getCachedValue(board);
			} else {
				this.putCachedValue(board, BBOps.evaluateBoard(board));
			}
			return this.getCachedValue(board) * (Math.pow(0.99, realDepth));
		}

		if (isMaximising) {
			double bestValue = -Double.MAX_VALUE;

			// Simulate each of the available moves.
			for (int i = 0; i < getSortedAvailableMoves(board).size(); i++) {
				if (System.nanoTime() >= timeOut) {
					if (this.isCached(board)) {
						return this.getCachedValue(board);
					}
					return bestValue;
				}
				int move = getSortedAvailableMoves(board).get(i);
				BitBoard simulatedBoard = BBOps.doMove(board, move, true);

				// Recursive step with updated alpha.
				double moveValue = minimax(simulatedBoard, depth - 1, false, alpha, beta,
						realDepth + 1, timeOut);

				bestValue = Math.max(bestValue, moveValue);
				alpha = Math.max(alpha, bestValue);

				// Prune if beta <= alpha.
				if (beta <= alpha) {
					break;
				}
			}
			this.putCachedValue(board, bestValue);

			return bestValue;
		} else {
			double bestValue = Double.MAX_VALUE;

			// Simulate each of the available moves.
			for (int i = 0; i < getSortedAvailableMoves(board).size(); i++) {
				if (System.nanoTime() >= timeOut) {
					if (this.isCached(board)) {
						return this.getCachedValue(board);
					}
					return bestValue;
				}
				int move = getSortedAvailableMoves(board).get(i);

				BitBoard simulatedBoard = BBOps.doMove(board, move, false);

				// Recursive step with updated beta.
				double moveValue = minimax(simulatedBoard, depth - 1, true, alpha, beta, realDepth + 1,
						timeOut);

				bestValue = Math.min(bestValue, moveValue);
				beta = Math.min(beta, bestValue);

				// Prune if beta <= alpha.
				if (beta <= alpha) {
					break;
				}
			}
			this.putCachedValue(board, bestValue);
			return bestValue;
		}
	}

}
