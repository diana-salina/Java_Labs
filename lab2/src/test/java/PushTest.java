import ru.nsu.salina.commands.DefineCommand;
import ru.nsu.salina.commands.PushCommand;
import ru.nsu.salina.factory.commands.*;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PushTest {
    @Test
    public void test1() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "a 2.3";
        String stackParameters = "a";
        Double expected = 2.3;

        DefineCommand define = new DefineCommand();
        define.executeCommand(map, stack, parameters);
        PushCommand push = new PushCommand();
        push.executeCommand(map, stack,stackParameters);

        assertEquals(stack.lastElement(), expected);
    }

    @Test
    public void test2() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "a 2.3";
        String stackParameters = "a b";

        DefineCommand define = new DefineCommand();
        define.executeCommand(map, stack, parameters);
        PushCommand push = new PushCommand();

        try {
            push.executeCommand(map, stack, stackParameters);
        } catch (IllegalArgumentException ex) {
            assertEquals("Space in name", ex.getMessage());
        }
    }

    @Test
    public void test3() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();
        String parameters = "a 2.3";
        String stackParameters = "b";

        DefineCommand define = new DefineCommand();
        define.executeCommand(map, stack, parameters);
        PushCommand push = new PushCommand();

        try {
            push.executeCommand(map, stack, stackParameters);
        } catch (IllegalArgumentException ex) {
            assertEquals("Invalid name", ex.getMessage());
        }
    }
}