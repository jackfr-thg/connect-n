package com.thg.accelerator23.connectn.ai.jackjack;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import ai.onnxruntime.*;
import java.util.Collections;

public class TestNN extends Player {
	public TestNN(Counter counter) {
		// TODO: fill in your name here
		super(counter, TestNN.class.getName());
	}

	@Override
	public int makeMove(Board board) {
		// TODO: some crazy analysis
		// TODO: make sure said analysis uses less than 2G of heap and returns within 10
		// seconds on whichever machine is running it
		String modelPath = "test.onnx"; // Path to your ONNX file
		try (OrtEnvironment env = OrtEnvironment.getEnvironment();
				OrtSession session = env.createSession(modelPath, new OrtSession.SessionOptions())) {

			// Prepare dummy input (random or empty, matching the input size of 1x42)
			float[] inputData = new float[42]; // 42 floats (flattened Connect-4 board)
			OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputData, new long[] { 1, 42 });

			// Run the model
			OrtSession.Result result = session.run(Collections.singletonMap("input", inputTensor));

			// Extract and print the output
			float[][] output = (float[][]) result.get(0).getValue();
			// System.out.println("Model Output: " + java.util.Arrays.toString(output[0]));
			return ouput[0][0];
		}

		return 4;
	}
}
