import ru.nsu.salina.exceptions.NullStackException;
import ru.nsu.salina.factory.commands.*;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PopTest {
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
        parameters = "b 4.6";
        stackParameters = "b";
        define.executeCommand(map, stack, parameters);
        push.executeCommand(map, stack,stackParameters);

        PopCommand pop = new PopCommand();
        pop.executeCommand(map, stack, "");

        assertEquals(stack.lastElement(), expected);
    }

    public void test2() {
        Stack<Double> stack = new Stack<>();
        Map<String, Double> map = new HashMap<>();

        PopCommand pop = new PopCommand();
        try {
            pop.executeCommand(map, stack, "");
        } catch (NullStackException ex) {
            assertEquals("Stack do not exist", ex.getMessage());
        }
    }
}