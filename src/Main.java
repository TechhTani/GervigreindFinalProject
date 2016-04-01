import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Kristjan on 1.4.2016.
 */
public class Main {
	
	/**
	 * main procedure
	 */
	public static void main(String[] args) throws Exception {
	    try(BufferedReader br = new BufferedReader(new FileReader("easy-puzzle.txt"))) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        String everything = sb.toString();
	        System.out.print(everything);
	    }
	}
    
}
