package ops;

import repository.OneInputGate;
import repository.TwoInputGate;

public class SimpleGate {

    public static OneInputGate NOT = new OneInputGate() {
        @Override
        public boolean executeLogic(boolean input) {
            return !input;
        }
    };

    public static TwoInputGate AND = new TwoInputGate() {
        @Override
        public boolean executeLogic(boolean input1, boolean input2) {
            return input1 && input2;
        }
    };

    public static TwoInputGate OR = new TwoInputGate() {
        @Override
        public boolean executeLogic(boolean input1, boolean input2) {
            return input1 || input2;
        }
    };

    public static TwoInputGate XOR = new TwoInputGate() {
        @Override
        public boolean executeLogic(boolean input1, boolean input2) {
            return OR.executeLogic(
                    AND.executeLogic(input1, NOT.executeLogic(input2)),
                    AND.executeLogic(NOT.executeLogic(input1), input2)
            );
        }
    };

}
