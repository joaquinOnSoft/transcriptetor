package com.opentext.explore.converter;

import java.io.File;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.opentext.explore.transcriptetor.TranscriptionConverter;
import com.opentext.explore.util.FileHelper;

public class Converter {

	/**
	 * SEE: How to parse command line arguments in Java? [closed]
	 * http://stackoverflow.com/questions/367706/how-to-parse-command-line-arguments-in-java
	 * SEE: Commons CLI. The Apache Commons CLI library provides an API for parsing command
	 * line options passed to programs.  
	 * http://commons.apache.org/cli/
	 * @param args - Arguments received from the command line.
	 */
    public static void main(String args[]){
    	Options options = new Options();

        Option inputPathOpt = new Option("i", "input", true, "Path to the input folder");        
        inputPathOpt.setRequired(true);
        options.addOption(inputPathOpt);

        Option outputPathopt = new Option("o", "output", true, "Path to the output folder");        
        outputPathopt.setRequired(true);
        options.addOption(outputPathopt);        
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Converter", options);

            System.exit(1);
            return;
        }
        
        String inputPath = cmd.getOptionValue("input");
        String outputPath = cmd.getOptionValue("output");
        
        ITranscriptionConverter converter = new Manual2TelcoTranscriptionConverter();
        
        List<String> files = FileHelper.listFilesInDirectory(inputPath);
        
        if(files != null) {
        	String fileName = null;
        	
        	for (String file : files) {
            	System.out.println("Converting " + file + "...");
            	
            	fileName = FileHelper.getFileName(file);
            	
            	converter.convert(file, outputPath + File.separatorChar + fileName);	
    		}	
        }        
    }
}
