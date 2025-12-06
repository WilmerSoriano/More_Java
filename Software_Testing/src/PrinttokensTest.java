import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

import java.io.BufferedReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrinttokensTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private Printtokens printtoken;
    
    @BeforeEach
    public void setUpStreams() {
    	printtoken = new Printtokens();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

	@Test
	void testOpen_character_stream () throws IOException{ // Without: ...() throws IOException{} cannot open file?
		System.out.println("=== Testing: Open_character_stream ===");
		
		System.out.println("*Start: Path1[1, 2, 4]");
		BufferedReader read1  = printtoken.open_character_stream(null);
		assertNotNull(read1);
		System.out.println("*End: Path1");
		
		System.out.println("**Start: Path2[1,3,4]");
		File file = new File("validFile.txt");
		// For every method were going to reopen and close file for best reliability
		file.deleteOnExit();
		assertNotNull(printtoken.open_character_stream(file.getAbsolutePath()));
		System.out.println("**End: Path2");
		
		System.out.println("=== Open_character_stream Finished. ===");
	}

	@Test
	void testGet_char() { // Might need, throws IOExce...
		System.out.println("=== Testing: Get Char ===");
		
		System.out.println("*Start: Path [1,2]");
		BufferedReader br = new BufferedReader(new StringReader(""));
		assertEquals(-1, printtoken.get_char(br));
		System.out.println("*End: Path [1,2]");
		
		System.out.println("=== Get Char Finished. ===");
	}

	@Test
	void testUnget_char() {
		System.out.println("=== Testing: Unget Char ===");
		
        System.out.println("*Start: Path [1,2]");
        BufferedReader br = new BufferedReader(new StringReader("a"));
        printtoken.get_char(br); 
        assertEquals('a', printtoken.unget_char('a', br));
        System.out.println("*End: Path [1,2]");
		
		System.out.println("=== Unget Char Finished. ===");
	}

	@Test
	void testOpen_token_stream() {
		System.out.println("=== Testing: Open Token Stream ===");
		
        System.out.println("*Start: Path [1,2,4]");
        assertNotNull(printtoken.open_token_stream(null));
        System.out.println("*End: Path [1,2,4]");
        
        System.out.println("**Start: Path [1,3,4]");
        File file = new File("validFile.txt");
        file.deleteOnExit();
        assertNotNull(printtoken.open_token_stream(file.getAbsolutePath()));
        System.out.println("**End: Path [1,3,4]");
        
		System.out.println("=== Open Token Stream Finished. ===");
	}

	@Test
	void testGet_token() {
		System.out.println("=== Testing: Get Token ===");
		
	    System.out.println("*Start: Path [1,2]");
	    BufferedReader br1 = new BufferedReader(new StringReader(""));
	    assertNull(printtoken.get_token(br1));
	    System.out.println("*End: Path [1,2]");
	    
	    System.out.println("*Start: Path [1,3,4,5,6]");
	    BufferedReader br2 = new BufferedReader(new StringReader("123 "));
	    assertEquals("123", printtoken.get_token(br2));
	    System.out.println("*End: Path [1,3,4,5,6]");
	    
	    System.out.println("*Start: Path [1,3,4,5,7,8]");
	    BufferedReader br3 = new BufferedReader(new StringReader(";"));
	    assertEquals(";", printtoken.get_token(br3));
	    System.out.println("*End: Path [1,3,4,5,7,8]");
	    
	    System.out.println("*Start: Path [1,3,4,5,7,9,10,11,12,13,14]");
	    BufferedReader br4 = new BufferedReader(new StringReader("\"hello\""));
	    assertEquals("\"hello\"", printtoken.get_token(br4));
	    System.out.println("*End: Path [1,3,4,5,7,9,10,11,12,13,14]");
	    
	    System.out.println("*Start: Path [1,3,4,5,7,9,10,11,12,13,15,16,18,19,20]");
	    BufferedReader br5 = new BufferedReader(new StringReader(";comment"));
	    assertEquals(";comment", printtoken.get_token(br5));
	    System.out.println("*End: Path [1,3,4,5,7,9,10,11,12,13,15,16,18,19,20]");
	    
	    System.out.println("*Start: Path [1,3,4,5,7,9,10,11,12,13,15,16,17,19,21,22]");
	    BufferedReader br6 = new BufferedReader(new StringReader("x"));
	    assertEquals("x", printtoken.get_token(br6));
	    System.out.println("*End: Path [1,3,4,5,7,9,10,11,12,13,15,16,17,19,21,22]");
	    
	    System.out.println("*Start: Path [1,3,4,5,7,9,10,11,12,13,15,16,17,19,21,23,25]");
	    BufferedReader br7 = new BufferedReader(new StringReader("12a"));
	    assertEquals("12a", printtoken.get_token(br7));
	    System.out.println("*End: Path [1,3,4,5,7,9,10,11,12,13,15,16,17,19,21,23,25]");
        
		System.out.println("=== Get Token Finished. ===");
	}

	@Test
	void testIs_token_end() {
		System.out.println("=== Testing: Is Token End ===");
		
		System.out.println("*Start: Path[1,2]");
		assertTrue(Printtokens.is_token_end(0, -1));
		System.out.println("*End: Path[1,2]");
		
        System.out.println("**Start: Path [1,3,4,5]");
        assertTrue(Printtokens.is_token_end(1, (int)'"'));
        System.out.println("**End: Path [1,3,4,5]");
		System.out.println("=== is Token End Finished. ===");
	}

	@Test
	void testToken_type() throws IOException {
		System.out.println("=== Testing: Token type ===");
		
        System.out.println("*Start: Path [1,2]");
        assertEquals(Printtokens.keyword, Printtokens.token_type("if"));
        System.out.println("*End: Path [1,2]");
        
        System.out.println("**Start: Path [1,3,5,7,9,11,13,14,15]");
        assertEquals(Printtokens.error, Printtokens.token_type("$"));
        System.out.println("**End: Path [1,3,5,7,9,11,13,14,15]");
        
		System.out.println("=== Token Type Finished. ===");
	}

	@Test
	void testPrint_token() {
		System.out.println("=== Testing: Print Token ===");
		
        System.out.println("*Start: Path [1,2]");
        printtoken.print_token("if");
        assertTrue(outContent.toString().contains("keyword,\"if\""));
        System.out.println("*End: Path [1,2]");
        
        System.out.println("*Start: Path [1,3]");
        printtoken.print_token("(");
        assertTrue(outContent.toString().contains("lparen."));
        System.out.println("*End: Path [1,3]");
        
        System.out.println("*Start: Path [1,4]");
        printtoken.print_token("varName");
        assertTrue(outContent.toString().contains("identifier,\"varName\""));
        System.out.println("*End: Path [1,4]");
        
        System.out.println("*Start: Path [1,5]");
        printtoken.print_token("12345");
        assertTrue(outContent.toString().contains("numeric,12345"));
        System.out.println("*End: Path [1,5]");
        
        System.out.println("*Start: Path [1,6]");
        printtoken.print_token("\"hello\"");
        assertTrue(outContent.toString().contains("string,\"hello\""));
        System.out.println("*End: Path [1,6]");
        
        System.out.println("*Start: Path [1,7]");
        printtoken.print_token("#a");
        assertTrue(outContent.toString().contains("character,\"a\""));
        System.out.println("*End: Path [1,7]");
        
        System.out.println("*Start: Path [1,8]");
        printtoken.print_token(";this is a comment");
        assertTrue(outContent.toString().contains("comment,\";this is a comment\""));
        System.out.println("*End: Path [1,8]");
        
        System.out.println("*Start: Path [1,9]");
        printtoken.print_token("$");
        assertTrue(outContent.toString().contains("error,\"$\""));
        System.out.println("*End: Path [1,9]");
		
		System.out.println("=== Print Token Finished. ===");
	}

	@Test
	void testIs_comment() {
		System.out.println("=== Testing: Is Comment ===");
		
        System.out.println("*Start: Path [1,2]");
        assertTrue(Printtokens.is_comment(";comment"));
        System.out.println("*End: Path [1,2]");
        
		System.out.println("=== Is Comment Finished. ===");
	}

	@Test
	void testIs_keyword() {
		System.out.println("=== Testing: Is Keyword ===");
		
		System.out.println("*Start: Path1 [1,2] ");
		assertTrue(Printtokens.is_keyword("if"));
		System.out.println("*End: Path1 [1,2]");
		
		System.out.println("**Start: Path2[1,3]");
		assertFalse(Printtokens.is_keyword("foo"));
		System.out.println("**End: Path2[1,3]");
		
		System.out.println("=== Is Keyword Finished. ===");
	}

	@Test
	void testIs_char_constant() {		
		System.out.println("=== Testing: Is Char Constant ===");
		
        System.out.println("*Start: Path [1,2]");
        assertTrue(Printtokens.is_char_constant("#a"));
        System.out.println("*End: Path [1,2]");
        
        
		System.out.println("=== Is Char Constant Finished. ===");
	}

	@Test
	void testIs_num_constant() {
		System.out.println("=== Testing: Is Num Constant ===");
		System.out.println("*Path1: Start");
		assertTrue(Printtokens.is_num_constant("123"));
		System.out.println("*Path1: End");
		
		System.out.println("**Path2: Start(invalid)");
		assertFalse(Printtokens.is_num_constant("12a"));
		System.out.println("**Path2: End");
		
		System.out.println("=== Is Num Constant Finished. ===");
	}

	@Test
	void testIs_str_constant() {
		System.out.println("=== Testing: Is Str Constant ===");
        
		System.out.println("*Start: Path [1,7]");
        assertTrue(Printtokens.is_str_constant("\"hello\""));
        System.out.println("*End: Path [1,7]");
        
		System.out.println("=== Is Str Constant Finished. ===");
	}

	@Test
	void testIs_identifier() {
		System.out.println("=== Testing: Is Identifier ===");
		
        System.out.println("*Start: Path [1,7]");
        assertTrue(Printtokens.is_identifier("x1"));
        System.out.println("*End: Path [1,7]");
        
        
		System.out.println("=== Is Identifier Finished. ===");
	}

	@Test
	void testPrint_spec_symbol() {
		System.out.println("=== Testing: Print Spec Symbol ===");
        
		System.out.println("*Start: Path [1,2]");
        Printtokens.print_spec_symbol("(");
        assertTrue(outContent.toString().contains("lparen.\n"));
        System.out.println("*End: Path [1,2]");
        
        System.out.println("**Start: Path [1,3,4]");
        Printtokens.print_spec_symbol(")");
        assertTrue(outContent.toString().contains("rparen.\n"));
        System.out.println("**End: Path [1,3,4]");

        System.out.println("***Start: Path [1,3,5,6]");
        Printtokens.print_spec_symbol("[");
        assertTrue(outContent.toString().contains("lsquare.\n"));
        System.out.println("***End: Path [1,3,5,6]");

        System.out.println("****Start: Path [1,3,5,7,8]");
        Printtokens.print_spec_symbol("]");
        assertTrue(outContent.toString().contains("rsquare.\n"));
        System.out.println("****End: Path [1,3,5,7,8]");

        System.out.println("*****Start: Path [1,3,5,7,9,10]");
        Printtokens.print_spec_symbol("'");
        assertTrue(outContent.toString().contains("quote.\n"));
        System.out.println("*****End: Path [1,3,5,7,9,10]");

        System.out.println("******Start: Path [1,3,5,7,9,11,12]");
        Printtokens.print_spec_symbol("`");
        assertTrue(outContent.toString().contains("bquote.\n"));
        System.out.println("******End: Path [1,3,5,7,9,11,12]");

        System.out.println("*******Start: Path [1,3,5,7,9,11,13,14]");
        Printtokens.print_spec_symbol(",");
        assertTrue(outContent.toString().contains("comma.\n"));
        System.out.println("*******End: Path [1,3,5,7,9,11,13,14]");
        
		System.out.println("=== Print Spec Symbol Finished. ===");
	}

	@Test
	void testIs_spec_symbol() {
		System.out.println("=== Testing: Is Spec Symbol ===");
        
		System.out.println("*Start: Path [1,2]");
        assertTrue(Printtokens.is_spec_symbol('('));
        System.out.println("*End: Path [1,2]");
        
        System.out.println("*Start: Path [1,3,4]");
        assertTrue(Printtokens.is_spec_symbol(')'));
        System.out.println("*End: Path [1,3,4]");

        System.out.println("*Start: Path [1,3,5,6]");
        assertTrue(Printtokens.is_spec_symbol('['));
        System.out.println("*End: Path [1,3,5,6]");

        System.out.println("*Start: Path [1,3,5,7,8]");
        assertTrue(Printtokens.is_spec_symbol(']'));
        System.out.println("*End: Path [1,3,5,7,8]");

        System.out.println("*Start: Path [1,3,5,7,9,10]");
        assertTrue(Printtokens.is_spec_symbol('/'));
        System.out.println("*End: Path [1,3,5,7,9,10]");

        System.out.println("*Start: Path [1,3,5,7,9,11,12]");
        assertTrue(Printtokens.is_spec_symbol('`'));
        System.out.println("*End: Path [1,3,5,7,9,11,12]");

        System.out.println("*Start: Path [1,3,5,7,9,11,13,14]");
        assertTrue(Printtokens.is_spec_symbol(','));
        System.out.println("*End: Path [1,3,5,7,9,11,13,14]");

        System.out.println("*Start: Path [1,3,5,7,9,11,13,15]");
        assertFalse(Printtokens.is_spec_symbol('x'));
        System.out.println("*End: Path [1,3,5,7,9,11,13,15]");
       
		System.out.println("=== Is Spec Symbol Finished. ===");
	}

	@Test
	void testMain() throws IOException {
	    System.out.println("=== Testing: Main ===");
	    
	    Printtokens.main(new String[] {"arg1", "arg2"});
	    assertTrue(outContent.toString().contains("Error! Please give the token stream"));

	    // Test no arguments (Path [1,2,5,6,7,8])
	    outContent.reset();
	    System.setIn(new ByteArrayInputStream("123".getBytes())); 
	    Printtokens.main(new String[0]);
	    assertTrue(outContent.toString().contains("numeric,123"));

	    // Test valid file input (Path [1,3,5,6,7,8])
	    outContent.reset();
	    File tempFile = File.createTempFile("valid", ".txt");
	    try (FileWriter writer = new FileWriter(tempFile)) {
	        writer.write("if");
	    }
	    Printtokens.main(new String[]{tempFile.getAbsolutePath()});
	    assertTrue(outContent.toString().contains("keyword,\"if\""));
	    tempFile.delete();

	    System.out.println("=== Main Finished. ===");
	}

}
