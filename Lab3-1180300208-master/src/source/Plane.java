package source;

/**
 * 不可变资源——飞机。
 * 具有飞机编号、飞机机型、飞机座位数、机龄信息
 * 
 * @author 梁书育
 */
public class Plane {

	private String planeID;
	private String planeType;
	private int seatNumber;
	private float planeAge;
	
	// Abstraction function:
	// 	  表示一架飞机
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    使用的变量均为不可变类型
	
	/**
	 * 构建一个新的飞机信息。
	 * 
	 * @param planeID 飞机编号
	 * @param planeType 飞机机型
	 * @param seatNumber 飞机座位数
	 * @param planeAge 飞机机龄
	 */
	public Plane(String planeID, 
				 String planeType, 
				 int seatNumber, 
				 float planeAge) {
		this.planeID = planeID;
		this.planeType = planeType;
		this.seatNumber = seatNumber;
		this.planeAge = planeAge;
	}

	/**
	 * 获得该飞机的编号。
	 * 
	 * @return 该飞机的编号
	 */
	public String getPlaneID() {
		return planeID;
	}

	/**
	 * 获得该飞机的型号。
	 * 
	 * @return 该飞机的机型
	 */
	public String getPlaneType() {
		return planeType;
	}

	/**
	 * 获得该飞机的座位数
	 * 
	 * @return 该飞机的座位数
	 */
	public int getSeatNumber() {
		return seatNumber;
	}

	/**
	 * 获得该飞机的机龄。
	 * 
	 * @return 该飞机的机龄
	 */
	public float getPlaneAge() {
		return planeAge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(planeAge);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((planeID == null) ? 0 : planeID.hashCode());
		result = prime * result + ((planeType == null) ? 0 : planeType.hashCode());
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
		Plane other = (Plane) obj;
		if (Double.doubleToLongBits(planeAge) != Double.doubleToLongBits(other.planeAge))
			return false;
		if (planeID == null) {
			if (other.planeID != null)
				return false;
		} else if (!planeID.equals(other.planeID))
			return false;
		if (planeType == null) {
			if (other.planeType != null)
				return false;
		} else if (!planeType.equals(other.planeType))
			return false;
		if (seatNumber != other.seatNumber)
			return false;
		return true;
	}
	
	
}
