package adders;

import repository.MultiGate;

public class Adder {

    private int numberOfBits = 0;
    private int firstNumber = 0;
    private int secondNumber = 0;
    private int residues = 0;

    private String firstNumberDisplay = "";
    private String secondNumberDisplay = "";
    private String outputNumberDisplay = "";
    private String outputCarryDisplay = "";
    int outputNumber = 0;

    public Adder(int numberOfBits, int firstNumber, int secondNumber, int residues) {
        this.numberOfBits = numberOfBits;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.residues = residues;
    }

    public int module(int sum, int m) {
        return sum % m;
    }

    public int runAdder() {
        int maxRange = 1 << numberOfBits;

        boolean[] inputs = new boolean[numberOfBits << 1];

        if ((firstNumber < 0) || (firstNumber >= maxRange)) {
            System.out.println("Pierwsza liczba jest spoza zakresu!");
        }
        if ((secondNumber < 0) || (secondNumber >= maxRange)) {
            System.out.println("Druga liczba jest spoza zakresu!");
        }

        MultiGate multiBitAdder = BuildAdder.buildAdder(numberOfBits);

        for (int i = 0; i < numberOfBits; i++) {
            boolean firstBit = ((firstNumber >>> i) & 1) == 1;
            boolean secondBit = ((secondNumber >>> i) & 1) == 1;
            inputs[i] = firstBit;
            inputs[numberOfBits + i] = secondBit;
            firstNumberDisplay = (firstBit ? "1" : "0") + firstNumberDisplay;
            secondNumberDisplay = (secondBit ? "1" : "0") + secondNumberDisplay;
        }

        boolean[] outputs = multiBitAdder.executeLogic(inputs);

        for (int i = numberOfBits; i >= 0; i--) {
            outputNumber = (outputNumber << 1) | (outputs[i] ? 1 : 0);
            if (i == numberOfBits)
                outputCarryDisplay = outputs[i] ? "1" : "0";
            else
                outputNumberDisplay += (outputs[i] ? "1" : "0");
        }

        int generatedResidue = module(outputNumber, residues);
        return generatedResidue;
    }

    public void printResult() {
        System.out.println("Liczba bitów = " + numberOfBits);
        System.out.println("A=" + firstNumberDisplay + " (" + firstNumber + "), B=" + secondNumberDisplay + " (" + secondNumber + "), C=" + outputCarryDisplay + ", S=" + outputNumberDisplay + " (" + outputNumber + ")");
    }

    public String printFullResultForController() {
        return "A=" + firstNumberDisplay + " (" + firstNumber + "), B=" + secondNumberDisplay + " (" + secondNumber + "), C=" + outputCarryDisplay + ", S=" + outputNumberDisplay + " (" + outputNumber + ")";
    }

    public String printResultForController() {
        return "Wynik: S=" + outputNumberDisplay + " (" + outputNumber + "), C=" + outputCarryDisplay;
    }

    public String printInputA() {
        return "A=" + firstNumberDisplay + " (" + firstNumber + ")";
    }

    public String printInputB() {
        return "B=" + secondNumberDisplay + " (" + secondNumber + ")";
    }
}
