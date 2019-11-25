package co.edu.uan.data.publisher.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name = "role")
@Entity
public class Role {
  
  @Id
  @GeneratedValue
  @JsonIgnore
  private int id;
  
  @Column
  private UserRoleEnumator userRole;
 
}
