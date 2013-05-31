package w577.mods.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomFile {
	
	private String location;
	private ArrayList<int[]> intLines;
	private int lineLength;
	private boolean opened;

	public CustomFile(String loc) {
		this.location = loc;
		this.intLines = new ArrayList<int[]>();
		this.opened = false;
		open();
	}
	
	public void open() {
		if (this.opened) {
			System.out.println("[CustomFile] Error with CustomFile: " + this.location + ". File already opened!");
			return;
		}
//		InputStream is = CustomFile.class.getResourceAsStream("/../../../" + this.location);
//		if (is == null) {
//			System.out.println("[CustomFile] Couldn't find " + this.location + " using prior slash. Attempting otherwise!");
			InputStream is = CustomFile.class.getResourceAsStream("../../../" + this.location);
			if (is == null) {
				System.out.println("[CustomFile] Couldn't find " + this.location + " using no prior slash. Attempting otherwise!");
				is = CustomFile.class.getResourceAsStream("/" + this.location);
				if (is == null) {
//					System.out.println("[CustomFile] Couldn't find " + this.location + " using prior slash and no backing up. Attempting otherwise!");
//					is = CustomFile.class.getResourceAsStream(this.location);
//					if (is == null) {
//						System.out.println("[CustomFile] Couldn't find " + this.location + " using no prior slash and no backing up. Attempting otherwise!");
//						is = CustomFile.class.getClassLoader().getResourceAsStream("/../../../" + this.location);
//						if (is == null) {
//							System.out.println("[CustomFile] Couldn't find " + this.location + " using prior slash and class loader. Attempting otherwise!");
//							is = CustomFile.class.getClassLoader().getResourceAsStream("../../../" + this.location);
//							if (is == null) {
								System.out.println("[CustomFile] Error with CustomFile: " + this.location + ". No such file exists!");
								return;
//							}
//						}
//					}
				}
			}
//		}
		try {
			InputStreamReader isReader = new InputStreamReader(is);
			BufferedReader bReader = new BufferedReader(isReader);
			String line = bReader.readLine();
			while (line != null) {
				if (line.length() == 0 || line.charAt(0) == '#') {
					line = bReader.readLine();
					continue;
				}
				line = line.replaceAll("(\\r|\\n)", "");
				String[] curLine = line.split("/");
				int[] intLine = new int[curLine.length];
				for (int i = 0; i < curLine.length; i ++) {
					intLine[i] = Integer.parseInt(curLine[i]);
				}
				if (!intLines.isEmpty() && curLine.length != lineLength) {
					System.out.println("[CustomFile] Error with CustomFile: " + this.location + ". Size of line is variant!");
					bReader.close();
					return;
				}
				intLines.add(intLine);
				lineLength = intLine.length;
				line = bReader.readLine();
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("[CustomFile] Opened CustomFile: " + this.location +".");
		this.opened = true;
	}
	
	public void save() {
		if (!opened) {
			System.out.println("[CustomFile] Error with CustomFile: " + location + ". Trying to use an un-opened file!");
			return;
		}
	}
	
	public int[] getLine(int line) {
		if (!opened) {
			System.out.println("[CustomFile] Error with CustomFile: " + location + ". Trying to use an un-opened file!");
			return null;
		}
		if (line >= size() || line < 0) {
			System.out.println("[CustomFile] Error with CustomFile: " + location + ". Trying to get a line outside of range!");
			return null;
		}
		return intLines.get(line);
	}
	
	public int size() {
		if (!opened) {
			System.out.println("[CustomFile] Error with CustomFile: " + location + ". Trying to use an un-opened file!");
			return 0;
		}
		return intLines.size();
	}
}
