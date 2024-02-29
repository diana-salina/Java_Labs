import ru.nsu.salina.commands.*;

import org.junit.*;
import ru.nsu.salina.exceptions.InvalidStackSizeException;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PlusTest {
    @Test
    public void test1() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "a 2.3";
        String stackParameters = "a";
        Double expected = 4.6 + 2.3;

        Command define = new DefineCommand();
        define.executeCommand(map, stack, parameters);
        Command push = new PushCommand();
        push.executeCommand(map, stack,stackParameters);
        parameters = "b 4.6";
        stackParameters = "b";
        define.executeCommand(map, stack, parameters);
        push.executeCommand(map, stack,stackParameters);

        Command plus = new PlusCommand();
        plus.executeCommand(map, stack, "");

        assertEquals(stack.lastElement(), expected);
    }

    @Test
    public void test2() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "a 2.3";
        String stackParameters = "a";

        Command define = new DefineCommand();
        define.executeCommand(map, stack, parameters);
        Command push = new PushCommand();
        push.executeCommand(map, stack,stackParameters);

        Command plus = new PlusCommand();
        try {
            plus.executeCommand(map, stack, "");
        } catch (InvalidStackSizeException ex) {
            assertEquals("Stack is too small", ex.getMessage());
        }
    }
}
