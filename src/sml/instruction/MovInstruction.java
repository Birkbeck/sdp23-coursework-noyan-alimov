package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import java.util.Objects;

/** Instruction that executes "move" operation (stores value from register to source)
 * @author Noyan Alimov
 */

public class MovInstruction extends Instruction {
    private final RegisterName result;
    private final int value;

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, RegisterName result, int value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, result, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovInstruction other = (MovInstruction) o;
        return Objects.equals(this.label, other.label) && Objects.equals(this.result, other.result) && Objects.equals(this.value, other.value);
    }
}
