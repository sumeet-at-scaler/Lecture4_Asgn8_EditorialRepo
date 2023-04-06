import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void dataExists(){
        try {
            Field dataField = Node.class.getDeclaredField("data");
            assertEquals(dataField.getType().toString(), "int");
        } catch (NoSuchFieldException e) {
            fail("data not found");
        }
    }

    @Test
    void nextExists(){
        try {
            Field nextField = Node.class.getDeclaredField("next");
            assertEquals(nextField.getType().toString(), "class Node");
        } catch (NoSuchFieldException e) {
            fail("next not found");
        }
    }

    @Test
    public void testNodeConstructors() throws Exception {
        Class<?> nodeClass = Node.class;

        // Get the constructors of the Node class
        Constructor<?>[] constructors = nodeClass.getDeclaredConstructors();

        // Check that there are two constructors
        assertNotNull(constructors);
        assertEquals(2, constructors.length);

        // Check that the constructors are public and have the correct parameter types
        for (Constructor<?> constructor : constructors) {
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
            if (constructor.getParameterCount() == 1) {
                assertEquals(int.class, constructor.getParameterTypes()[0]);
            } else if (constructor.getParameterCount() == 2) {
                assertEquals(int.class, constructor.getParameterTypes()[0]);
                assertEquals(Node.class, constructor.getParameterTypes()[1]);
            } else {
                fail("Constructor has incorrect number of parameters");
            }
        }
    }

    @Test
    void iteratorMethodExists(){
        try {
            Method iteratorMethod = Node.class.getDeclaredMethod("iterator");
            assertEquals(iteratorMethod.toString(), "public java.util.Iterator Node.iterator()");
        } catch (NoSuchMethodException e) {
            fail("iteratorMethod not found");
        }
    }

    @Test
    void iterableOfNodeImplemented(){
        List<Class<?>> interfaces = Arrays.asList(Node.class.getInterfaces());

        assertEquals(1, interfaces.size());
        assertTrue(interfaces.contains(Iterable.class));
    }


    @Test
    void iteratorWorksFine() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Constructor<?> constructor = Node.class.getDeclaredConstructor(int.class, Node.class);
        constructor.setAccessible(true);


        // Create a new instance of the Node class with a data value of 42
        Node node3 = (Node) constructor.newInstance(30, null);
        Node node2 = (Node) constructor.newInstance(20, node3);
        Node node1 = (Node) constructor.newInstance(10, node2);

        Iterable<Node> iterable = (Iterable<Node>) node1;
        int[] expected = {10, 20, 30};
        int i = 0;
        for(Node temp: iterable){
            Field dataField = Node.class.getDeclaredField("data");
            dataField.setAccessible(true);
            int value = (int)dataField.get(temp);
            assertEquals(expected[i], value);
            i++;
        }

        assertEquals(i, expected.length);
    }

}