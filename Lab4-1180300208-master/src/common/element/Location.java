package common.element;

/**
 * 计划项的位置信息（不可变类型）。
 * 
 * @author 梁书育
 */
public class Location {

	final double longitude;
	final double latitude;
	final String locName;
	final boolean shareable;
	
	// Abstraction function:
	// 	  表示一个位置信息
	
	// Representation invariant:
	//    经度纬度应该符合实际
	//    即经度范围为-180 ~ 180（用负数表示西经，用正数表示东经）
	//      维度范围为-90 ~ 90（用负数表示南纬，用正数表示北纬）
	
	// Safety from rep exposure:
	//    使用的均为不可变类型的变量
	
	/**
	 * 位置构造器，如没有提供经纬度信息则将经纬度默认设定为0.
	 * 
	 * @param name 位置名称
	 * @param shareable 是否可以共享
	 */
	public Location(String name, boolean shareable) {
		this.longitude = 0.0;
		this.latitude = 0.0;
		this.locName = name;
		this.shareable = shareable;
	}
	
	public void checkRep() {
		assert longitude >= -180;
		assert longitude <= 180;
		assert latitude >= -90;
		assert latitude <= 90;
	}
	
	/**
	 * 提供经纬度信息的位置构造器。
	 * 
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param name 位置名称
	 * @param shareable 是否可以共享
	 */
	public Location(double longitude, double latitude, String name, boolean shareable) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.locName = name;
		this.shareable = shareable;
		checkRep();
	}
	
	/**
	 * 取得位置名称。
	 * 
	 * @return 位置名称
	 */
	public String getLocName() {
		return locName;
	}

	/**
	 * 取得位置经度。
	 * 
	 * @return 经度
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 取得位置纬度。
	 * 
	 * @return 纬度
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 取得是否可共享状态
	 * 
	 * @return {@code true}可共享，{@code false}不可共享
	 */
	public boolean isShareable() {
		return shareable;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((locName == null) ? 0 : locName.hashCode());
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (shareable ? 1231 : 1237);
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
		Location other = (Location) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (locName == null) {
			if (other.locName != null)
				return false;
		} else if (!locName.equals(other.locName))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (shareable != other.shareable)
			return false;
		return true;
	}

	
}
