package com.opentext.explore.transcriptetor.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Solr2HumanReadableTranscriptionConverter extends AbstractTranscriptionConverter {

	@Override
	protected String process(String jsonStr) {
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
		
		//System.out.print(str);
		
		return str == null? null : str.toString();
	}
}
