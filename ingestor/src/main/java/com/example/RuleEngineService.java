package com.example;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.boot.json.JacksonJsonParser;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;


/**
 * Handles the Rule Session and insertions into the Rule Session
 * @author 212572312
 *
 */
@Service
public class RuleEngineService {
	
	ArrayList<Asset> allAssets = new ArrayList<Asset>();
	ArrayList<ParkingSpots> allLocations = new ArrayList<ParkingSpots>();
	ArrayList<Event> events = new ArrayList<Event>();
	
	final String authToken = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIzYTgxMzQxZC03ODAyLTQ4MDYtOTgwNC1kYzRiYWE1ZjZiNzEiLCJzdWIiOiJhZG1pbiIsInNjb3BlIjpbImNsaWVudHMucmVhZCIsImNsaWVudHMuc2VjcmV0IiwiaWRwcy53cml0ZSIsInVhYS5yZXNvdXJjZSIsImllLXBhcmtpbmcuem9uZXMuMzczYjMwZjUtYmQ4ZS00MGE4LTlkYzItYmM3M2IyN2QzYzI4LmFkbWluIiwiY2xpZW50cy5hZG1pbiIsImllLXRyYWZmaWMuem9uZXMuMTMzM2U5OTMtZWYwZS00NGRmLWEyMTMtMjRjMTYwMzJhZDJiLnVzZXIiLCJzY2ltLnJlYWQiLCJ6b25lcy4xNjJiNmVjZS04OTEwLTQ2NTAtOTJlYi00OGFhY2Q4YmM2Y2EuYWRtaW4iLCJjbGllbnRzLndyaXRlIiwiaWUtcGFya2luZy56b25lcy4zNzNiMzBmNS1iZDhlLTQwYTgtOWRjMi1iYzczYjI3ZDNjMjgudXNlciIsImlkcHMucmVhZCIsInNjaW0ud3JpdGUiXSwiY2xpZW50X2lkIjoiYWRtaW4iLCJjaWQiOiJhZG1pbiIsImF6cCI6ImFkbWluIiwiZ3JhbnRfdHlwZSI6ImNsaWVudF9jcmVkZW50aWFscyIsInJldl9zaWciOiI1YzYyZThmNyIsImlhdCI6MTQ3MDQzNDkxNSwiZXhwIjoxNDcwNDc4MTE1LCJpc3MiOiJodHRwczovLzE2MmI2ZWNlLTg5MTAtNDY1MC05MmViLTQ4YWFjZDhiYzZjYS5wcmVkaXgtdWFhLnJ1bi5hd3MtdXN3MDItcHIuaWNlLnByZWRpeC5pby9vYXV0aC90b2tlbiIsInppZCI6IjE2MmI2ZWNlLTg5MTAtNDY1MC05MmViLTQ4YWFjZDhiYzZjYSIsImF1ZCI6WyJhZG1pbiIsImNsaWVudHMiLCJpZHBzIiwidWFhIiwiaWUtcGFya2luZy56b25lcy4zNzNiMzBmNS1iZDhlLTQwYTgtOWRjMi1iYzczYjI3ZDNjMjgiLCJpZS10cmFmZmljLnpvbmVzLjEzMzNlOTkzLWVmMGUtNDRkZi1hMjEzLTI0YzE2MDMyYWQyYiIsInNjaW0iLCJ6b25lcy4xNjJiNmVjZS04OTEwLTQ2NTAtOTJlYi00OGFhY2Q4YmM2Y2EiXX0.NGRdpCyDHe5vauYQC4GxtYSF6yA8dwrCNa_xD9vbN5Aa-DWJVQ9tMN0fwlCkKZ5adI45nAjBG1KRvcmUqTbEFctsUvhiIWncd2Cmh11is2Xtqhe4LGzpJTSq8rI4HGOy61QY_ux5VF9g56x3N1BFgFs6f90i1anYsQahZqPF8gWKIMFtaiyhAdmRuAGNYdqY7kCvow5itGe34QhqpnXPCQDkAbHuUASdgA1WMSMR1Wrmk7gV-AR6SwrGw0NMzmlBUSyeGb6q7ci70xphTtlXknOH8_a_sKpgHxGVWpj09QHKy0tcs1KFbTMDJJH_W2NbpiBmTtTiuez-wIQm55uqPg";
//	private static ParkingSpotsRepository repo;

	
//	@Autowired
//	public RuleEngineService() {
//		// Connects to Repositories
////	    this.repo = repo;
//	    
//
//		System.out.println("Getting Assets");
//		HttpHeaders header = new HttpHeaders();
//		header.add("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI0ZWEwMmYwMC1kYjY0LTQ0ZGYtODU5My00NzJlMGQyZWM2ODkiLCJzdWIiOiJhZG1pbiIsInNjb3BlIjpbImNsaWVudHMucmVhZCIsImNsaWVudHMuc2VjcmV0IiwiaWRwcy53cml0ZSIsInVhYS5yZXNvdXJjZSIsImllLXBhcmtpbmcuem9uZXMuMzczYjMwZjUtYmQ4ZS00MGE4LTlkYzItYmM3M2IyN2QzYzI4LmFkbWluIiwiY2xpZW50cy5hZG1pbiIsImllLXRyYWZmaWMuem9uZXMuMTMzM2U5OTMtZWYwZS00NGRmLWEyMTMtMjRjMTYwMzJhZDJiLnVzZXIiLCJzY2ltLnJlYWQiLCJ6b25lcy4xNjJiNmVjZS04OTEwLTQ2NTAtOTJlYi00OGFhY2Q4YmM2Y2EuYWRtaW4iLCJjbGllbnRzLndyaXRlIiwiaWUtcGFya2luZy56b25lcy4zNzNiMzBmNS1iZDhlLTQwYTgtOWRjMi1iYzczYjI3ZDNjMjgudXNlciIsImlkcHMucmVhZCIsInNjaW0ud3JpdGUiXSwiY2xpZW50X2lkIjoiYWRtaW4iLCJjaWQiOiJhZG1pbiIsImF6cCI6ImFkbWluIiwiZ3JhbnRfdHlwZSI6ImNsaWVudF9jcmVkZW50aWFscyIsInJldl9zaWciOiI1YzYyZThmNyIsImlhdCI6MTQ3MDM2NzEyOCwiZXhwIjoxNDcwNDEwMzI4LCJpc3MiOiJodHRwczovLzE2MmI2ZWNlLTg5MTAtNDY1MC05MmViLTQ4YWFjZDhiYzZjYS5wcmVkaXgtdWFhLnJ1bi5hd3MtdXN3MDItcHIuaWNlLnByZWRpeC5pby9vYXV0aC90b2tlbiIsInppZCI6IjE2MmI2ZWNlLTg5MTAtNDY1MC05MmViLTQ4YWFjZDhiYzZjYSIsImF1ZCI6WyJhZG1pbiIsImNsaWVudHMiLCJpZHBzIiwidWFhIiwiaWUtcGFya2luZy56b25lcy4zNzNiMzBmNS1iZDhlLTQwYTgtOWRjMi1iYzczYjI3ZDNjMjgiLCJpZS10cmFmZmljLnpvbmVzLjEzMzNlOTkzLWVmMGUtNDRkZi1hMjEzLTI0YzE2MDMyYWQyYiIsInNjaW0iLCJ6b25lcy4xNjJiNmVjZS04OTEwLTQ2NTAtOTJlYi00OGFhY2Q4YmM2Y2EiXX0.GMbCMp8nJUWoJmoy-C4bfQbpHbH4f-s__Qe-ImZSjZ7eemNB7jifu4J-kgmi3BBFrYItPG8OAZD9r03PD7GpzodfYxYO5DeJR2D6HkNWW4HZA4EGAV8m0WIlvOEbV_52S5HBD4zWPWFxVQt7B5Hw2GBpnwS4aVvOxnzib3mJ3bS0idInSZwgOr1x8qPyjd3pGK1wpOQp8MlSZMhgrxH24hoUooh5uqbi9sd9Nxvef33mZgOhPCKtxhU66oApUV0YycyubF7wiWtxTgl2ysLrP0dxYIdUwxBFuUIqi0eOP3hJPmQO4_c_YsNM0ZFWUkVFXyGkoeq9MDRImNl-Po27cA");
//		header.add("Predix-Zone_Id", "373b30f5-bd8e-40a8-9dc2-bc73b27d3c28");
//		RestTemplate rest = new RestTemplate();
//		String shiet = rest.getForObject("https://ie-parking.run.aws-usw02-pr.ice.predix.io/v1/locations/search?q=location-type:PARKING_SPOT&bbox=0.716:-117.163,82.720:117.263&size=20&page=0", String.class);
//		
//		System.out.println("AYYLMAO " + shiet);
//	    
//	    
//	}

    public double getParkingPrice() {
        double total = 22;
        double filled = 0;
        for (ParkingSpots parkingSpots : allLocations) {
            if (parkingSpots.getStatus() == true) {
                filled++;
            }
        }
        System.out.println(filled);
        System.out.println(total);
        double percentFilled = filled/total;
        double price = 3 + percentFilled*5;
        return price;
    }

    public String getParkingDetails() {
        String json2Return = "{ \"data\":[";
        for(ParkingSpots parkingSpots : allLocations) {
            json2Return = json2Return + parkingSpots.toJson() + ",";
//            System.out.println(parkingSpots.toJson());
        }
        json2Return = json2Return.substring(0, json2Return.length() - 1);
        json2Return = json2Return + "]}";
        System.out.println(json2Return);
        return json2Return;
    }
	
	public String getEvents(long start, long end){
		String s = "{\"events\": [";
		for(Event e: events){
			if(e.getTs() >= start && e.getTs() <= end){
				s += "{\"ts\":" + e.getTs() + "," + "\"filled\":" + e.getNum_filled() + ",\"open\":" + e.getNum_open() + "},";
			}
		}
		s = s.substring(0, s.length()-1);
		s += "]}";
		System.out.println(s);
		return s;
	}

	
	public void getParkingEvents() {
		Date date = new Date();
		long end = date.getTime();
		long start = end - 15000;
		
		JacksonJsonParser parser = new JacksonJsonParser();
		
		HttpHeaders header = new HttpHeaders();
        header.add("Authorization", authToken);
        header.add("Predix-Zone-Id", "373b30f5-bd8e-40a8-9dc2-bc73b27d3c28");
        HttpEntity entity = new HttpEntity(header);
        RestTemplate rest = new RestTemplate();
        
        for (; ; ) {
        	for(Asset a: allAssets){
        		System.out.println("ASSET: " + a.getAsset_id());
        		String id = a.getAsset_id();
        		String url = "https://ie-parking.run.aws-usw02-pr.ice.predix.io/v1/assets/"+id+"/events?assetId="+id+"&event-types=PKIN,PKOUT&start-ts=" + start + "&end-ts=" + end;
        		String shiet = rest.exchange(url, HttpMethod.GET, entity, String.class).getBody();
//        		JacksonJsonParser parser = new JacksonJsonParser();
//        		Map<String, Object> parsedData = parser.parseMap(shiet);
        		System.out.println(shiet);
        		if (shiet != null && !shiet.isEmpty()) {
        			Map<String, Object> eventsData = parser.parseMap(shiet);
            		
            		
            		ArrayList<LinkedHashMap> mp = ((ArrayList<LinkedHashMap>)((LinkedHashMap) eventsData.get("_embedded")).get("events"));
            		System.out.println(mp);
            		for(LinkedHashMap l : mp){
            			long ts = (long) ((LinkedHashMap)l).get("timestamp");
            			String event_type = (String) ((LinkedHashMap)l).get("event-type");
            			String location_uid = (String) ((LinkedHashMap)l).get("location-uid");
            			
            			
            			for(ParkingSpots s: allLocations){
            				if(s.getLocationuid().equals(location_uid)){
            					if(event_type.equals("PKIN")){
            						if(!s.getStatus()){
            							s.setStatus(true);
            							s.setLast_ts(ts);
            							Event last = events.get(events.size() - 1);
                						Event e = new Event();
                						
                	        			e.setNum_filled(Math.min(22,last.getNum_filled() + 1));
                	        			e.setNum_open(22 - e.getNum_filled());
                	        			e.setTs(ts);
                	        			
                	        			events.add(e);
            						}
            					}
            					else{
            						if(s.getStatus()){
            							s.setStatus(false);
            							s.setLast_ts(ts);
            							Event last = events.get(events.size() - 1);
                						Event e = new Event();
                	        			e.setNum_filled(Math.max(0, last.getNum_filled() - 1));
                	        			e.setNum_open(22 - e.getNum_filled());
                	        			e.setTs(ts);
                	        			events.add(e);
            						}
            					}
            					break;
            				}
            			}
            		}
        		}
        		
        	}
        	start = end;
        	end = start + 15000;
            try {
                //Sleep for a minute
                System.out.println("Sleeping for a minute");
                Thread.sleep(15000);

                System.out.println("We back fam");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

	
	public void initialize(){
		System.out.println("Getting Assets and locations");
		
		Date date = new Date();
		
		Event init_event = new Event();
		init_event.setNum_filled(0);
		init_event.setNum_open(22);
		init_event.setTs(date.getTime());
		events.add(init_event);
		
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", authToken);
		header.add("Predix-Zone-Id", "373b30f5-bd8e-40a8-9dc2-bc73b27d3c28");
		HttpEntity entity  = new HttpEntity(header);
		RestTemplate rest = new RestTemplate();
		String url = "https://ie-parking.run.aws-usw02-pr.ice.predix.io/v1/locations/search?q=location-type:PARKING_SPOT&bbox=0.716:-117.163,82.720:117.263&size=40&page=0";
        String locationQueryResponse = rest.exchange(url, HttpMethod.GET, entity, String.class).getBody();

		JacksonJsonParser parser = new JacksonJsonParser();
		Map<String, Object> parsedData = parser.parseMap(locationQueryResponse);
		LinkedHashMap locations = (LinkedHashMap)parsedData.get("_embedded");
        System.out.println(locations);

        ArrayList<LinkedHashMap> allLocs = (ArrayList<LinkedHashMap>)locations.get("locations");
//
		System.out.println("_____________ ");
		for(int i = 0; i < allLocs.size(); i++){
            String locationUid = (String) ((LinkedHashMap) allLocs.get(i)).get("location-uid");
            LinkedHashMap geoCoordinates = (LinkedHashMap) allLocs.get(i).get("coordinates");
            String p1Coordinates = (String) geoCoordinates.get("P1");
            String [] p1CoordinatesArray = p1Coordinates.split(",");
            System.out.println(p1CoordinatesArray[0]);
            System.out.println(p1CoordinatesArray[1]);

            System.out.println(locationUid);
            System.out.println(geoCoordinates.toString());

			LinkedHashMap lnks = (LinkedHashMap) allLocs.get(i).get("_links");
			String s = ((LinkedHashMap)lnks.get("self")).get("href").toString();
			String[] s_arr = s.split("/");
            System.out.println("LOCATION: " + s_arr[s_arr.length - 1]);
			ParkingSpots new_loc = new ParkingSpots();
			new_loc.setNumeric_id(s_arr[s_arr.length - 1]);
            new_loc.setLocationuid(locationUid);
            new_loc.setLatitude(Float.parseFloat(p1CoordinatesArray[0]));
            new_loc.setLongitude(Float.parseFloat(p1CoordinatesArray[1]));
            new_loc.setStatus(false);


			String assetLoc = rest.exchange("https://ie-parking.run.aws-usw02-pr.ice.predix.io/v1/locations/" + s_arr[s_arr.length - 1], HttpMethod.GET, entity, String.class).getBody();
			Map<String, Object> pData = parser.parseMap(assetLoc);
//			System.out.println(((ArrayList<LinkedHashMap>)((LinkedHashMap)pData.get("_embedded")).get("assets")).toString());

			LinkedHashMap lc = ((ArrayList<LinkedHashMap>)((LinkedHashMap)pData.get("_embedded")).get("assets")).get(0);
//			System.out.println(lc);
			String assetURL = ((LinkedHashMap)((LinkedHashMap) lc.get("_links")).get("self")).get("href").toString();
//			System.out.println("ASSET: " + assetURL);
			String[] ls_asset = assetURL.split("/");
			String asset = ls_asset[ls_asset.length - 1];
			System.out.println("ASSET: " + asset);
			new_loc.setAsset(asset);
			Boolean found = false;
			for(Asset a : allAssets){
				if(a.getAsset_id().equals(asset)){
					ArrayList<ParkingSpots> spotsForAsset = a.getLocs();
					spotsForAsset.add(new_loc);
					a.setLocs(spotsForAsset);
					found = true;
					break;
				}
			}
			if(!found){
				Asset a = new Asset();
				ArrayList<ParkingSpots> Asset_spots = new ArrayList<ParkingSpots>();
				Asset_spots.add(new_loc);
				a.setLocs(Asset_spots);
				a.setAsset_id(asset);
				allAssets.add(a);
			}

			allLocations.add(new_loc);
            System.out.println("test" + new_loc);

		}
    }
	
	
	
}
