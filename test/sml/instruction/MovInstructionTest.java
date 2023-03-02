package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

class MovInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        //...
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeValid() {
        Instruction instruction = new MovInstruction(null, EAX, 11);
        instruction.execute(machine);
        Assertions.assertEquals(11, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo() {
        Instruction instruction = new MovInstruction(null, EAX, 3);
        instruction.execute(machine);
        Assertions.assertEquals(3, machine.getRegisters().get(EAX));
    }
}