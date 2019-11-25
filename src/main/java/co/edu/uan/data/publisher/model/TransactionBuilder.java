package co.edu.uan.data.publisher.model;

import java.util.UUID;

import lombok.Data;

@Data
public class TransactionBuilder {
	
	private TransactionBuilder() {
		
	}
	
	public static Transaction createEmptyTransaction() {
		Transaction transaction = new Transaction();
		transaction.setUuid(System.currentTimeMillis());
		transaction.setStatus(StatusEnum.FAILED);
		return transaction;
	}
	
	public static Transaction createTransactionForUser(User user) {
		Transaction transaction = createEmptyTransaction();
		user.addTransaction(transaction);
		return transaction;
	}

}
