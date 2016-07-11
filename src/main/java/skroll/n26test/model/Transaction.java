package skroll.n26test.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private List<Long> children;
	
	public Transaction() {
		children = new ArrayList<Long>();
	}
	
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
	public List<Long> getChildren() {
		return this.children;
	}
	
	public void addChild(Long childId) {
		this.children.add(childId);
	}
}
