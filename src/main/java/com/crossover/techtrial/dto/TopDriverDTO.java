/**
 * 
 */
package com.crossover.techtrial.dto;

import java.io.Serializable;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 * @author crossover
 *
 */

@Entity
@SqlResultSetMapping(
		name="TopDriverDTOMapping",
		classes={
			@ConstructorResult(
				targetClass = TopDriverDTO.class,
				
					columns={
					   
					    @ColumnResult(name="name",type = String.class),
					    @ColumnResult(name="email",type =  String.class),
					    @ColumnResult(name="totalMinutes",type = Long.class),
					    @ColumnResult(name="maximumDuration",type = Long.class),
					    @ColumnResult(name="totalRides",type = Long.class),
					    
					   }
				)
			}
	)
public class TopDriverDTO implements Serializable {
	
	private static final long serialVersionUID = 3605549122072628877L;

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  Long id;
  
	  private String name;
	  
	  private String email;
	  
	  private Long totalMinutes;
	  
	  private Long maximumDuration;
	  
	  private Long totalRides;
	  
	  


	public TopDriverDTO(String name, String email, Long totalMinutes, Long maximumDuration,Long totalRides) {
		super();
		this.name = name;
		this.email = email;
		this.totalMinutes = totalMinutes;
		this.maximumDuration = maximumDuration;
		this.totalRides = totalRides;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getTotalMinutes() {
		return totalMinutes;
	}


	public void setTotalMinutes(Long totalMinutes) {
		this.totalMinutes = totalMinutes;
	}


	public Long getMaximumDuration() {
		return maximumDuration;
	}


	public void setMaximumDuration(Long maximumDuration) {
		this.maximumDuration = maximumDuration;
	}


	public Long getTotalRides() {
		return totalRides;
	}


	public void setTotalRides(Long totalRides) {
		this.totalRides = totalRides;
	}

/*
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	*/
  
  

    
}
