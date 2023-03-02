package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;
import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

class JnzInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        machine.getLabels().addLabel("statement1", 3);
        //...
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeValid() {
        registers.set(EAX, 5);
        Instruction instruction = new JnzInstruction(null, EAX, "statement1");
        Assertions.assertEquals(machine.getLabels().getAddress("statement1"), instruction.execute(machine));
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, 0);
        Instruction instruction = new JnzInstruction(null, EAX, "statement1");
        Assertions.assertEquals(NORMAL_PROGRAM_COUNTER_UPDATE, instruction.execute(machine));
    }
}