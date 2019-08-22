package com.opentext.explore.transcriptetor.converter;

import com.opentext.explore.util.FileHelper;

public abstract class AbstractTranscriptionConverter implements ITranscriptionConverter {
	
	
	@Override
	public void convert(String fileInput, String fileOutput) {
		if(fileInput != null) {
			String inputStr = FileHelper.readLineByLine(fileInput);
			
			String outputStr = process(inputStr);
			
			if(outputStr != null) {
				FileHelper.writeToFile(fileOutput, outputStr);
			}
		}
	}
	
	
	protected abstract String process (String inputStr);
}
