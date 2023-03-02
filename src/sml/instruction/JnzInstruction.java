package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import java.util.Objects;

/** Instruction that executes "jnz" operation (if source is not zero, execute given statement)
 * @author Noyan Alimov
 */

public class JnzInstruction extends Instruction {
    private final String nextStatementToExecute;
    private final RegisterName source;

    public static final String OP_CODE = "jnz";

    public JnzInstruction(String label, String nextStatementToExecute, RegisterName source) {
        super(label, OP_CODE);
        this.nextStatementToExecute = nextStatementToExecute;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int value = m.getRegisters().get(source);
        if (value != 0) return m.getLabels().getAddress(nextStatementToExecute);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + nextStatementToExecute + " " + source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, nextStatementToExecute, source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JnzInstruction other = (JnzInstruction) o;
        return Objects.equals(this.label, other.label) && Objects.equals(this.nextStatementToExecute, other.nextStatementToExecute) && Objects.equals(this.source, other.source);
    }
}
