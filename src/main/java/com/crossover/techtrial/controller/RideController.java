/**
 * 
 */
package com.crossover.techtrial.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.exceptions.ApiErrorResponse;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.RideService;

/**
 * RideController for Ride related APIs.
 * @author crossover
 *
 */
@RestController
public class RideController {
  
  @Autowired
  RideService rideService;
  
  @Autowired
  PersonService personService;
  
  @Autowired
  EntityManager em;

  @PostMapping(path ="/api/ride/{driver-id}/{rider-id}")
  public ResponseEntity<?> createNewRide(
		  @PathVariable(value="driver-id") Long driverId,
		  @PathVariable(value="rider-id") Long riderId,
		  @RequestBody Ride ride) throws ParseException {
	  
	  /**
	   * Verifying that driver or rider is registered
	   * **/
	  
	  Person driver = personService.findById(driverId);
	  Person rider = personService.findById(riderId);
	  
	  if(driver == null || !driver.getCategory().equals("driver")) {
		  return new ApiErrorResponse().send(HttpStatus.EXPECTATION_FAILED,"Driver is not registered");
	  }
	  else if(rider == null || !rider.getCategory().equals("rider")) {
		  return new ApiErrorResponse().send(HttpStatus.EXPECTATION_FAILED,"Rider is not registered");
	  }
	  else {
		  ride.setDriver(driver);
		  ride.setRider(rider);  
	  }
	  
	  /**
	   * Verifying Start time and end time requirements
	   * **/
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  
	  Date startDateTime = sdf.parse(ride.getStartTime());
	  Date endDateTime = sdf.parse(ride.getEndTime());
	  
	  if(startDateTime.compareTo(endDateTime) == 0) {
		  return new ApiErrorResponse().send(HttpStatus.EXPECTATION_FAILED,"End time is equal to Start time");
	  }
	  if(startDateTime.after(endDateTime)) {
		  return new ApiErrorResponse().send(HttpStatus.EXPECTATION_FAILED,"Start time is after End time");
	  }
	  
	  
    return ResponseEntity.ok(rideService.save(ride));
  }
  
  @GetMapping(path = "/api/ride/{ride-id}")
  public ResponseEntity<Ride> getRideById(@PathVariable(name="ride-id",required=true)Long rideId){
    Ride ride = rideService.findById(rideId);
    if (ride!=null)
      return ResponseEntity.ok(ride);
    return ResponseEntity.notFound().build();
  }
  
  /**
   * This API returns the top 5 drivers with their email,name, total minutes, maximum ride duration in minutes.
   * Only rides that starts and ends within the mentioned durations should be counted.
   * Any rides where either start or endtime is outside the search, should not be considered.
   * 
   * DONT CHANGE METHOD SIGNATURE AND RETURN TYPES
   * @return
   */
  @GetMapping(path = "/api/top-rides")
  public ResponseEntity<List<TopDriverDTO>> getTopDriver(
      @RequestParam(value="max", defaultValue="5") Long count,
      @RequestParam(value="startTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
      @RequestParam(value="endTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime){
    List<TopDriverDTO> topDrivers = new ArrayList<TopDriverDTO>();
    /**
     * Your Implementation Here. And Fill up topDrivers Arraylist with Top
     * 
     */
    Query q = em.createNativeQuery("select a.driver_id,b.email,b.name, sum(TIME_TO_SEC(TIMEDIFF(a.end_time,a.start_time))/60) totalMinutes, TIME_TO_SEC(MAX(TIMEDIFF(a.end_time,a.start_time)))/60 maximumDuration, count(a.driver_id) totalRides \n" + 
    		"from crossride.ride a\n" + 
    		"left join crossride.person b on a.driver_id=b.id\n" + 
    		"where a.start_time >= '"+startTime+"' and a.end_time <= '"+endTime+"'\n" + 
    		"group by driver_id\n" + 
    		"order by totalRides desc\n" + 
    		"limit "+count+"","TopDriverDTOMapping");
    
    topDrivers = q.getResultList();
    
    return ResponseEntity.ok(topDrivers);
    
  }
  
}
