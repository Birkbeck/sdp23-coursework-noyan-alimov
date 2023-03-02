package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import java.util.Objects;

/** Instruction that executes "output" operation (just prints the content on the console)
 * @author Noyan Alimov
 */

public class OutInstruction extends Instruction {
    private final RegisterName source;

    public static final String OP_CODE = "out";

    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int value = m.getRegisters().get(source);
        System.out.println("Register " + source.name() + " has " + value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + " " + source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutInstruction other = (OutInstruction) o;
        return Objects.equals(this.label, other.label) && Objects.equals(this.source, other.source);
    }
}
