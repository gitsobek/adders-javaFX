package adders;

import repository.MultiGate;

import static ops.ComplexGate.FULL_ADDER;

public class BuildAdder {
    public static MultiGate buildAdder(int numberOfBits) {
        return inputs -> {
            if (inputs.length != (numberOfBits << 1))
                throw new IllegalArgumentException();

            boolean[] fullAdderInputs = new boolean[3];
            boolean[] fullAdderOutputs = new boolean[2];
            boolean[] multipleAdderOutputs = new boolean[numberOfBits + 1];

            for (int i = 0; i < numberOfBits; i++) {
                if(fullAdderOutputs == null)
                    fullAdderInputs[0] = false;
                else
                    fullAdderInputs[0] = fullAdderOutputs[1];

                fullAdderInputs[1] = inputs[i];
                fullAdderInputs[2] = inputs[numberOfBits + i];
                fullAdderOutputs = FULL_ADDER.executeLogic(fullAdderInputs);
                multipleAdderOutputs[i] = fullAdderOutputs[0];
            }

            if (fullAdderOutputs != null)
                multipleAdderOutputs[numberOfBits] = fullAdderOutputs[1];
            return multipleAdderOutputs;
        };
    }
}
