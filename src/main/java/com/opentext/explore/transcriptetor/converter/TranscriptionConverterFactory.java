package com.opentext.explore.transcriptetor.converter;

public class TranscriptionConverterFactory {

	public static final String MANUAL_2_TELCO = "MANUAL2TELCO";
	public static final String SOLR_2_HUMAN_READABLE = "SOLR2HR";
	
	public ITranscriptionConverter getTranscriptionConverter(String type) {
		switch (type) {
		case MANUAL_2_TELCO:
			return new Manual2TelcoTranscriptionConverter();
		case SOLR_2_HUMAN_READABLE:
			return new Solr2HumanReadableTranscriptionConverter();
		default:
			return null;
		}
	}
}
