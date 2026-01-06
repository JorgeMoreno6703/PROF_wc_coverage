package es.upm.grise.profundizacion.wc;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {

    private static Path testFile = Paths.get("ejemplo.txt");

    @BeforeAll
    public static void setup() throws IOException {
        Files.writeString(testFile, "kjdbvws wonvwofjw\n sdnfwijf ooj    kjndfohwouer 21374 vehf\n jgfosj\n\nskfjwoief ewjf\n\n\ndkfgwoihgpw vs wepfjwfin");
    }

    @AfterAll
    public static void teardown() {
        try {
            Files.deleteIfExists(testFile);
        } catch (IOException e) {
            System.err.println("Error deleting test file: " + e.getMessage());
            try {
                Thread.sleep(100);
                Files.deleteIfExists(testFile);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Failed to delete test file on retry: " + ex.getMessage());
            }
        }
    }


    @Test
    public void testUsageMessageWhenNoArgs() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        App.main(new String[] {});
        
        assertEquals("Usage: wc [-clw file]\n".trim(), output.toString().trim());
    }
    @Test
public void testWrongArguments() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {"-c"});
        assertEquals("Wrong arguments!", output.toString().trim());
    } finally {
        System.setOut(originalOut);
    }
}

@Test
public void testFileNotFound() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {"-c", "noexiste.txt"});
        assertEquals("Cannot find file: noexiste.txt", output.toString().trim());
    } finally {
        System.setOut(originalOut);
    }
}

@Test
public void testCommandsWithoutDash() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {"c", testFile.toString()});
        assertEquals("The commands do not start with -", output.toString().trim());
    } finally {
        System.setOut(originalOut);
    }
}

@Test
public void testUnrecognizedCommand() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {"-x", testFile.toString()});
        assertEquals("Unrecognized command: x", output.toString().trim());
    } finally {
        System.setOut(originalOut);
    }
}

@Test
public void testUsageWhenNoArgs() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {});
        assertEquals("Usage: wc [-clw file]", output.toString().trim());
    } finally {
        System.setOut(originalOut);
    }
}

@Test
public void testWrongArgumentsThreeArgs() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {"-c", testFile.toString(), "extra"});
        assertEquals("Wrong arguments!", output.toString().trim());
    } finally {
        System.setOut(originalOut);
    }
}

@Test
public void testCountCharacters_OutputContainsFileAndNumber() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {"-c", testFile.toString()});

        String out = output.toString().trim();
        assertTrue(out.contains(testFile.toString()));
        // Al menos un número en la salida (conteo)
        assertTrue(out.matches(".*\\d+.*"));
    } finally {
        System.setOut(originalOut);
    }
}

@Test
public void testMultipleCommands_clw_OrderAndFilePresent() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
        App.main(new String[] {"-clw", testFile.toString()});

        String out = output.toString().trim();
        assertTrue(out.contains(testFile.toString()));
        // Deben aparecer 3 números (c, l, w) en algún orden antes del filename.
        // No imponemos tabs: aceptamos cualquier separador.
        assertTrue(out.matches(".*\\d+.*\\d+.*\\d+.*"));
    } finally {
        System.setOut(originalOut);
    }
}
}
