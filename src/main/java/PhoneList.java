import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.StringTokenizer;

enum PhoneCorrect{
	YES("YES"),NO("NO");
	private String name;
	
	PhoneCorrect(final String name){
		this.name = name;
	}
	public String toString(){
		return name;
	}
}
class PhoneTree {
	PhoneTree[] nodes = new PhoneTree[10];
	boolean end;
}
public class PhoneList {
	
	public static void main (String[] args) {
		scanFromBufferedInput(System.in, "", false);

	}
	
	public static void scanFromBufferedInput(InputStream in, String fileName, boolean debug) {
		final long startTime = new Date().getTime();

		InputStream inputStream = in;
		if (fileName != null && !fileName.isEmpty()) {
			inputStream = scanFromFile(fileName, debug);
		}

		final BufferedInputStreamPhoneList buffer = new BufferedInputStreamPhoneList(inputStream);

		int testCount = buffer.getInt();
		for (int t = 0; t < testCount; t++) {
			int phoneCount = buffer.getInt();
			PhoneTree tree = new PhoneTree();
			PhoneCorrect phoneCorrect  = PhoneCorrect.YES;
			for (int p = 0; p < phoneCount; p++) {
				final String phoneNumber = buffer.getWord();
				if (PhoneCorrect.YES == phoneCorrect){
					phoneCorrect = scanPhoneToRefactor(tree, phoneNumber);
				}
			}
			
			System.out.println(phoneCorrect);
		}

		if (debug) {
			System.out.println("Total time: " + (new Date().getTime() - startTime));
		}

	}
	
	private static PhoneCorrect scanPhone(PhoneTree phone, final String phoneNumber) {
		PhoneCorrect phoneCorrect = PhoneCorrect.YES;
		final int lastElementIdx = phoneNumber.length() - 1;
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			for (int i = 0; i < phoneNumber.length(); i++) {
				int number = (int) phoneNumber.charAt(i) - 48;
				PhoneTree nextElement = phone.nodes[number];
				if (nextElement == null) {
					nextElement = new PhoneTree();
					phone.nodes[number] = nextElement;
				} else {
					if (nextElement.end || i == lastElementIdx) {
						phoneCorrect = PhoneCorrect.NO;
						break;
					}
				}
				
				phone = nextElement;
				
				if (i == lastElementIdx){
					nextElement.end = true;
				}
				
			}
		}
		return phoneCorrect;
	}	
	
	public static PhoneCorrect scanPhoneToRefactor(PhoneTree phone, final String phoneNumber) {
		PhoneCorrect phoneCorrect = PhoneCorrect.YES;
		final int lastElementIdx = phoneNumber.length() - 1;
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			for (int i = 0; i < phoneNumber.length(); i++) {
				int number = (int) phoneNumber.charAt(i) - 48;
				if (phone.nodes[number] != null) {
					if (phone.nodes[number].end || i == lastElementIdx) {
						phoneCorrect = phoneCorrect.NO;
						break;
					}
				} else {
					phone.nodes[number] = new PhoneTree();
				}
				phone = phone.nodes[number];
			}
			phone.end = true;
		}
		return phoneCorrect;

	}
	

	public static InputStream scanFromFile(final String fileName, boolean debug) {
		final String singleLine = convertFileInputToString(fileName);
		final InputStream inputStream = new ByteArrayInputStream(singleLine.getBytes());
		return inputStream;
	}
	
	public static String convertFileInputToString(final String fileName){
		final StringBuilder builder = new StringBuilder();

	
		boolean firstLine = true;
		try {
			String currentLine;
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				while ((currentLine = br.readLine()) != null) {
					if (firstLine){
						firstLine = false;
						builder.append(currentLine);
					}
					else{
						builder.append(" " + currentLine);
					}
					
				}
			}
		} catch (IOException ex) {
			// ignore
		}
		
		
		return builder.toString();
	}
	
	
}


class BufferedInputStreamPhoneList extends PrintWriter {
	
	private BufferedReader reader;
	private String line;
	private StringTokenizer tockenizer;
	private String token;	
	
	public BufferedInputStreamPhoneList(InputStream i) {
		super(new BufferedOutputStream(System.out));
		reader = new BufferedReader(new InputStreamReader(i));
	}

	public BufferedInputStreamPhoneList(InputStream i, OutputStream o) {
		super(new BufferedOutputStream(o));
		reader = new BufferedReader(new InputStreamReader(i));
	}

	public boolean hasMoreTokens() {
		return peekToken() != null;
	}

	public int getInt() {
		return Integer.parseInt(nextToken());
	}

	public double getDouble() {
		return Double.parseDouble(nextToken());
	}

	public long getLong() {
		return Long.parseLong(nextToken());
	}

	public String getWord() {
		return nextToken();
	}


	private String peekToken() {
		if (token == null)
			try {
				while (tockenizer == null || !tockenizer.hasMoreTokens()) {
					line = reader.readLine();
					if (line == null)
						return null;
					tockenizer = new StringTokenizer(line);
				}
				token = tockenizer.nextToken();
			} catch (IOException e) {
			}
		return token;
	}

	private String nextToken() {
		String ans = peekToken();
		token = null;
		return ans;
	}
}
