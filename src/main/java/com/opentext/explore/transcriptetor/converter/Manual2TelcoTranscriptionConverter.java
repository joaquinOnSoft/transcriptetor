package com.opentext.explore.transcriptetor.converter;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Joaquín Garzón
 */
public class Manual2TelcoTranscriptionConverter extends AbstractTranscriptionConverter{

	
	private static final String LINE_FILLER = "\t0\t0\t0\t0\n";
	
	private List<String> ignoredWords;
	
	public Manual2TelcoTranscriptionConverter(){
		ignoredWords = new LinkedList<String>();
		
		//Speaker identifiers
		ignoredWords.add("A:"); //Agent
		ignoredWords.add("C:"); //Client
		ignoredWords.add("N:"); //Noise
		
		//Punctuation marks
		ignoredWords.add(",");
		ignoredWords.add(".");
		ignoredWords.add("¿");
		ignoredWords.add("?");
	}
	
	@Override
	protected String process(String inputStr) {
		
		if(inputStr != null) {
			StringBuffer output = new StringBuffer();
					
			String[] words = inputStr.split("\\s+");
			
			for (String word : words) {
				word = word.toUpperCase();
				
				//Ignore punctuation marks
				word = word.replaceAll("(\\¿|\\?|\\.|\\,)", "");
				
				
				if( !ignoredWords.contains(word) ) {
					output.append(word);
					output.append(LINE_FILLER);
				}
			}
			
			return output.toString();
		}
		else {
			return null;
		}
		
	}
}
