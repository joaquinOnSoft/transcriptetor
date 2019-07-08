package com.opentext.explore.transcriptetor;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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

        Option optJsonFile = new Option("f", "file", true, "Path to the JSON file containing the 'Explore' transcription");        
        optJsonFile.setRequired(true);
        options.addOption(optJsonFile);

        
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
        
        String filePath = cmd.getOptionValue("file");
        
        TranscriptionConverter converter = new TranscriptionConverter();
        converter.jsonFile2HumanReadableTxt(filePath);
    }
}
