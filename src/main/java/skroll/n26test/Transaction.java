package skroll.n26test;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonInclude(Include.NON_DEFAULT)
public class Transaction {
	@JsonIgnore
	private long transactionId;
	private double amount;
	private String type;
	private long parentId;
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@JsonIgnore
	public long getTransactionId() {
		return transactionId;
	}
	@JsonProperty
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
}
