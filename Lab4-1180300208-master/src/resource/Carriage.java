package resource;

/**
 * 不可变资源——车厢。
 * 具有车厢编号、类型、定员数、出厂年份的属性。
 * 
 * @author 梁书育
 */
public class Carriage {

	private String carriageID;
	private String carriageType;
	private int seatNumber;
	private int productYear;
	
	// Abstraction function:
	// 	  表示列车的一节车厢
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    使用的rep均为不可变类型
	
	/**
	 * 创建一个新的车厢信息。
	 * 
	 * @param carriageID 车厢编号
	 * @param carriageType 车厢类型
	 * @param seatNumber 定员数
	 * @param productYear 出厂年份
	 */
	public Carriage(String carriageID, String carriageType, int seatNumber, int productYear) {
		this.carriageID = carriageID;
		this.carriageType = carriageType;
		this.seatNumber = seatNumber;
		this.productYear = productYear;
	}

	/**
	 * 获得该车厢的车厢编号。
	 * 
	 * @return 该车厢的车厢编号
	 */
	public String getCarriageID() {
		return carriageID;
	}

	/**
	 * 获得该车厢的车厢类型。
	 * 
	 * @return 该车厢的车厢类型
	 */
	public String getCarriageType() {
		return carriageType;
	}

	/**
	 * 获得该车厢的定员数。
	 * 
	 * @return 该车厢的定员数
	 */
	public int getSeatNumber() {
		return seatNumber;
	}

	/**
	 * 获得该车厢的出厂年份。
	 * 
	 * @return 该车厢的出厂年份
	 */
	public int getProductYear() {
		return productYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carriageID == null) ? 0 : carriageID.hashCode());
		result = prime * result + ((carriageType == null) ? 0 : carriageType.hashCode());
		result = prime * result + productYear;
		result = prime * result + seatNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carriage other = (Carriage) obj;
		if (carriageID == null) {
			if (other.carriageID != null)
				return false;
		} else if (!carriageID.equals(other.carriageID))
			return false;
		if (carriageType == null) {
			if (other.carriageType != null)
				return false;
		} else if (!carriageType.equals(other.carriageType))
			return false;
		if (productYear != other.productYear)
			return false;
		if (seatNumber != other.seatNumber)
			return false;
		return true;
	}
	
	
}
