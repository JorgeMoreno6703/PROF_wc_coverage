package es.upm.grise.profundizacion.wc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CounterTest {

    @Test
    public void testCountCharactersWordsAndLines() throws IOException {
        String content = "Esta frase\nes un ejemplo para\nel test de recuento.\n";
        BufferedReader reader = new BufferedReader(new StringReader(content));
        
        Counter counter = new Counter(reader);
        
        assertEquals(51, counter.getNumberCharacters());
        assertEquals(3, counter.getNumberLines());
        assertEquals(10, counter.getNumberWords());
    }
    @Test
public void testOnlyLetters() throws IOException {
    BufferedReader reader = new BufferedReader(new StringReader("abcXYZ"));
    Counter counter = new Counter(reader);

    assertEquals(6, counter.getNumberCharacters());
    assertEquals(0, counter.getNumberLines());
    assertEquals(0, counter.getNumberWords());
}

@Test
public void testSpacesTabsAndNewlines() throws IOException {
    BufferedReader reader = new BufferedReader(new StringReader("a\tb c\n\n"));
    Counter counter = new Counter(reader);

    assertEquals(7, counter.getNumberCharacters());
    assertEquals(2, counter.getNumberLines());
    assertEquals(4, counter.getNumberWords());
}

   

}

