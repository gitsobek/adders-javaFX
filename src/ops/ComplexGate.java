package ops;

import repository.MultiGate;

import static ops.SimpleGate.AND;
import static ops.SimpleGate.OR;
import static ops.SimpleGate.XOR;

public class ComplexGate {

    public static MultiGate HALF_ADDER = new MultiGate() {
        @Override
        public boolean[] executeLogic(boolean... inputs) {
            if(inputs.length != 2)
                throw new IllegalArgumentException();
            return new boolean[] {
                    XOR.executeLogic(inputs[0], inputs[1]),
                    AND.executeLogic(inputs[0], inputs[1])
            };
        }
    };

    public static MultiGate FULL_ADDER = new MultiGate() {
        @Override
        public boolean[] executeLogic(boolean... inputs) {
            if(inputs.length != 3)
                throw new IllegalArgumentException();
            boolean[] firstHalfAdderOutput = HALF_ADDER.executeLogic(inputs[0], inputs[1]);
            boolean[] secondHalfAdderOutput = HALF_ADDER.executeLogic(firstHalfAdderOutput[0], inputs[2]);
            return new boolean[] {
                    secondHalfAdderOutput[0],
                    OR.executeLogic(firstHalfAdderOutput[1], secondHalfAdderOutput[1])
            };
        }
    };

}
