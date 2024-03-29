package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static sml.Registers.Register;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author Noyan Alimov
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        String opcode = scan();

        String opcodePrefix = opcode.substring(0, 1).toUpperCase() + opcode.substring(1);
        Class<?> c;
        try {
            c = Class.forName("sml.instruction." + opcodePrefix + "Instruction");
            String r = scan();
            String s = scan();
            Constructor<?>[] constructors = c.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Constructor<?> con = c.getConstructor(parameterTypes);
                Object[] args = null;
                if (parameterTypes.length == 2) {
                    args = new Object[] {label, Register.valueOf(r)};
                    return (Instruction) con.newInstance(args);
                }

                Class<?> thirdParamType = parameterTypes[2];
                if (thirdParamType == RegisterName.class) {
                    args = new Object[] {label, Register.valueOf(r), Register.valueOf(s)};
                } else if (thirdParamType == String.class) {
                    args = new Object[]{label, Register.valueOf(r), s};
                } else if (thirdParamType == int.class) {
                    args = new Object[] {label, Register.valueOf(r), Integer.parseInt(s)};
                }
                return (Instruction) con.newInstance(args);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
    }
}