package Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class FileReader extends Game_Frame {
	public static Random random = new Random();
	static String result = null;
	
	public void output() {
		Scanner scanner = null;
		try {
			FileOutputStream oFile = new FileOutputStream("word_db.txt", true);
			File file = new File("word_db.txt");
			scanner = new Scanner(file);
			int lineNum = 0;
			if (file.length() == 0) {
				System.out.println("File is empty");
				System.exit(0);
			} // if
			while (scanner.hasNextLine()) {
				lineNum++;
				String line = scanner.nextLine();
				if (random.nextInt(lineNum) == 0) {
					result = line;
				} // if
			} // while
			if (result.isBlank() || result.isEmpty()) {
				output();
			} // if
			gameWord.add(result.toLowerCase());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			System.out.println("Made it to the end of the file");
			JOptionPane.showMessageDialog(null, "Made it to the end of the file", "File All Read",
					JOptionPane.ERROR_MESSAGE);
		} // catch
	}// read from file
	
}
