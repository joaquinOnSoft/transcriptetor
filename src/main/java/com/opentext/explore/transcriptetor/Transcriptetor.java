package com.opentext.explore.transcriptetor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.opentext.explore.transcriptetor.converter.ITranscriptionConverter;
import com.opentext.explore.transcriptetor.converter.TranscriptionConverterFactory;
import com.opentext.explore.util.FileHelper;

public class Transcriptetor {

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

        Option optJsonFile = new Option("f", "file", true, "Path to the file containing the original transcription");        
        options.addOption(optJsonFile);
        
        Option inputPathOpt = new Option("i", "input", true, "Path to the input folder containing all the transcriptions");        
        options.addOption(inputPathOpt);

        Option outputPathOpt = new Option("o", "output", true, "Path to the output folder");  
        outputPathOpt.setRequired(true);
        options.addOption(outputPathOpt);   
        
        Option transcriptionTypeOpt = new Option("t", "type", true, "Transcription converter type to be used. 1) MANUAL2TELCO Manual format to TELCO company format  2) SOLR2HR - Solr to Human readable format");        
        transcriptionTypeOpt.setRequired(true);
        options.addOption(transcriptionTypeOpt);         
        
        Option helpOpt = new Option("h", "help", false, "Print help info");        
        options.addOption(helpOpt);        

        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Transcriptetor", options);

            System.exit(1);
            return;
        }
        
        if(cmd.hasOption("help")) {
            formatter.printHelp("Transcriptetor", options);
            System.exit(0);        	
        }
        
        List<String> files = null;
        
        if(cmd.hasOption("file")) {
        	String filePath = cmd.getOptionValue("file");
        	files = new LinkedList<String>();
        	files.add(filePath);
        }
        
        if (cmd.hasOption("input")) {
            String inputPath = cmd.getOptionValue("input");                  
            files = FileHelper.listFilesInDirectory(inputPath);
        }
        
        String outputPath = cmd.getOptionValue("output");

        TranscriptionConverterFactory factory = new TranscriptionConverterFactory();
        ITranscriptionConverter converter = factory.getTranscriptionConverter(cmd.getOptionValue("type"));
        
        
        if(files != null && converter != null) {
        	String fileName = null;
        	
        	for (String file : files) {
            	System.out.println("Converting " + file + "...");
            	
            	fileName = FileHelper.getFileName(file);
            	
            	converter.convert(file, outputPath + File.separatorChar + fileName);	
    		}	
        }        
    }
}
