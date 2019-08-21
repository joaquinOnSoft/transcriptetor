package com.opentext.explore.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {
	public static String readLineByLine(String filePath)
	{
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
		{
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		return contentBuilder.toString();
	}

	public static void writeToFile(String filePath, String str) {
		try {
			File file = new File(filePath);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(str);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}


	public static List<String> listFilesInDirectory(String path){
		List<String> result = null;

		try  {
			Stream<Path> walk = Files.walk(Paths.get(path));
			result = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
			
			walk.close();
		} 
		catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}

		return result;
	}
	
	public static String getFileName(String path) {
		String fileName = null;
		
		if(path != null) {
			File f = new File(path);
			fileName =  f.getName();	
		}
		
		return fileName;
	}
	
	public static String getParent(String path) {
		String parent = null;
		
		if(path != null) {
			File f = new File(path);
			parent =  f.getParent();	
		}
		
		return parent;
	}	
}
