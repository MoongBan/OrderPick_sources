package restaurant;

public class DtoOrder {
	private String odDate;
	private String odMid;
	private int odQty;
	private String odCode;
	private int odNum;
	private int odtotalPrice;
	private String odMenuName;
	
	public int getOdtotalPrice() {
		return odtotalPrice;
	}
	public void setOdtotalPrice(int odtotalPrice) {
		this.odtotalPrice = odtotalPrice;
	}
	public String getOdMenuName() {
		return odMenuName;
	}
	public void setOdMenuName(String odMenuName) {
		this.odMenuName = odMenuName;
	}
	public int getOdNum() {
		return odNum;
	}
	public void setOdNum(int odNum) {
		this.odNum = odNum;
	}
	public String getOdDate() {
		return odDate;
	}
	public void setOdDate(String odDate) {
		this.odDate = odDate;
	}
	public String getOdMid() {
		return odMid;
	}
	public void setOdMid(String odMid) {
		this.odMid = odMid;
	}
	public int getOdQty() {
		return odQty;
	}
	public void setOdQty(int odQty) {
		this.odQty = odQty;
	}
	public String getOdCode() {
		return odCode;
	}
	public void setOdCode(String odcode) {
		this.odCode = odcode;
	}
	
}
