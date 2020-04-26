package co.edu.uan.data.publisher.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name = "application_user")
@Entity
public class User {

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String username;

	@Column
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	@JsonIgnore
	private UserRole role;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> transactions = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DataLocation> locations = new ArrayList<>();

	public void addTransaction(Transaction newTransaction) {
		newTransaction.setUser(this);
		transactions.add(newTransaction);
	}
	
	public void addLocation(DataLocation newLocation) {
	  newLocation.setUser(this);
	  locations.add(newLocation);
	}

}
