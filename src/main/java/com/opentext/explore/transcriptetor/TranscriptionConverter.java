package com.opentext.explore.transcriptetor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TranscriptionConverter {

	private String readLineByLineJava8(String filePath)
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
	
	public String jsonFile2HumanReadableTxt(String filePath) {
		return jsonString2HumanReadableTxt(readLineByLineJava8(filePath));
	}
	
	public String jsonString2HumanReadableTxt(String jsonStr) {
		//System.out.println(jsonStr);
		
		StringBuilder str = null;
		JSONObject fragment = null;
		String speaker = null;
		String text = null;
		
		JSONObject jsonObject = null;
		try {
		     jsonObject = new JSONObject(jsonStr);
		}catch (JSONException err){
	        System.err.println(err.getMessage());
		}
		
		if(jsonObject != null) {
			//System.out.println(jsonObject);
			JSONObject channels = jsonObject.getJSONObject("channels");
			
			if(channels != null) {
				
				JSONObject firstChannelLabel = channels.getJSONObject("firstChannelLabel");
				if(firstChannelLabel != null) {
					
					JSONArray transcript = firstChannelLabel.getJSONArray("transcript");
					
					if(transcript != null) {
						
						str = new StringBuilder();
						int numTrasncript = transcript.length();
						
						for(int i=0; i<numTrasncript; i++) {
							fragment = transcript.getJSONObject(i);
							if(fragment != null) {
								speaker = fragment.getString("speaker"); 
								if(speaker != null) {
									str.append("SPEAKER ").append(speaker).append(": ");
								}
								
								text = fragment.getString("text");
								if(text != null) {
									
									String[] segments = text.split("\\.");
									int nSegments = segments == null? 0 : segments.length;
									
									if(nSegments >= 0 ) {
										for(int j=0; j<nSegments; j++) {
											if(j > 0) {
												str.append("\t");
											}
											str.append(segments[j]).append("\n");
										}
										
									}
									else {
										str.append(text).append("\n");	
									}
														
								}
							}							
						}
					}
				}
				
			}
		}
		
		System.out.print(str);
		
		return str == null? null : str.toString();
	}
}
