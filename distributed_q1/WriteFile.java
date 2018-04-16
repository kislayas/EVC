package distributed_q1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
	
	public BufferedWriter bw = null;
	public FileWriter fw = null;

	public WriteFile(String fileName) throws IOException {

		fw = new FileWriter(fileName);
		bw = new BufferedWriter(fw);
	}
}