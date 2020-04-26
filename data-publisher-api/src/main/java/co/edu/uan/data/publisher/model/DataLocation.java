package co.edu.uan.data.publisher.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.vividsolutions.jts.geom.Geometry;

import lombok.Data;

@Data
@Entity
public class DataLocation {
  
  @Id
  @Column(name = "id")
  @NotNull
  @GeneratedValue
  private Long id;
  
  @NotNull
  @Column
  private MovilEnum movilAgresor;
  
  @NotNull
  @Column
  private int victimsAge;
  
  @NotNull
  @Column
  private Geometry location;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @Nullable
  private User user;
  
}
