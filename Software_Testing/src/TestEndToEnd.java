import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

//import java.io.BufferedReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEndToEnd {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    //private Printtokens printtoken;
    
    @BeforeEach
    public void setUpStreams() {
    	//printtoken = new Printtokens();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
    // This test will mash all Scenarios from 1-10 into 1 single Method.(Full Coverage)
    @Test
    void testAllEndToEndScenarios() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/testFile.txt");
        assertNotNull(inputStream, "testFile.txt not found");
        
        byte[] bytes = inputStream.readAllBytes();
        String input = new String(bytes);
        
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Printtokens.main(new String[0]);

        String[] expectedOutputs = {
            "keyword,\"and\".",
            "keyword,\"or\".",
            "keyword,\"if\".",
            "keyword,\"xor\".",
            "keyword,\"lambda\".",
            "keyword,\"=>\".",
            "lparen.",
            "rparen.",
            "lsquare.",
            "rsquare.",
            "bquote.",
            "quote.",
            "comma.",
            "identifier,\"x\".",
            "identifier,\"x1\".",
            "error,\"1x\".",
            "error,\"x$\".",
            "numeric,123.",
            "error,\"12a\".",
            "string,\"hello\".",
            "error,\"\"hello\".", // Unterminated string
            "character,\"a\".",   // #a
            "identifier,\"a\".",  // a
            "error,\"#1\".",      // #1
            "error,\"$\"."        // $
        };

        String actualOutput = outContent.toString().replaceAll("\r\n", "\n");
        String[] actualOutputs = actualOutput.split("\n");

        assertEquals(expectedOutputs.length, actualOutputs.length, "Line count mismatch.");

        for (int i = 0; i < expectedOutputs.length; i++) {
            assertEquals(expectedOutputs[i], actualOutputs[i].trim(), "Mismatch at line " + (i + 1));
        }
    }

    // Below are other Test Coverage not covered by Full.
    @Test
    void testEndToEnd11() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        Printtokens.main(new String[]{tempFile.getAbsolutePath()});
        assertEquals("", outContent.toString().trim());
        tempFile.delete();
    }

    @Test
    void testEndToEnd12() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("456 ");
        }
        Printtokens.main(new String[]{tempFile.getAbsolutePath()});
        assertTrue(outContent.toString().contains("numeric,456"));
        tempFile.delete();
    }

    @Test
    void testEndToEnd13() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(",");
        }
        Printtokens.main(new String[]{tempFile.getAbsolutePath()});
        assertTrue(outContent.toString().contains("comma."));
        tempFile.delete();
    }

    @Test
    void testEndToEnd14() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("\"world\"");
        }
        Printtokens.main(new String[]{tempFile.getAbsolutePath()});
        assertTrue(outContent.toString().contains("string,\"world\""));
        tempFile.delete();
    }

    @Test
    void testEndToEnd15() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(";file_comment");
        }
        Printtokens.main(new String[]{tempFile.getAbsolutePath()});
        assertTrue(outContent.toString().contains("comment,\";file_comment\""));
        tempFile.delete();
    }

    @Test
    void testEndToEnd16() {
        Printtokens.main(new String[]{"arg1", "arg2"});
        assertTrue(outContent.toString().contains("Error! Please give the token stream"));
    }
}
