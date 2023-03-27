package restaurant;

public class DtoMenu {
	private String mCafeNum;
	private String mName;
	private int mPrice;
	private String mCode;
	public String getmCafeNum() {
		return mCafeNum;
	}
	public void setmCafeNum(String mCafeNum) {
		this.mCafeNum = mCafeNum;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getmPrice() {
		return mPrice;
	}
	public void setmPrice(int mPrice) {
		this.mPrice = mPrice;
	}
	public String getmCode() {
		return mCode;
	}
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	@Override
	public String toString() {
		return "[메뉴이름] " + mName + " [가격] " + mPrice + "원";
	}
}
