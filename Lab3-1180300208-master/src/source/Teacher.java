package source;

/**
 * 不可变资源——教师。
 * 具有身份证号、姓名、性别、职称属性。
 * 
 * @author 梁书育
 */
public class Teacher {

	private String teacherID;
	private String name;
	private String gender;
	private String position;
	
	// Abstraction function:
	// 	  表示一位教师
	
	// Representation invariant:
	//	  无
	
	// Safety from rep exposure:
	//    使用的变量均为不可变类型
	
	/**
	 * 构建一个教师信息。
	 * 
	 * @param ID 身份证号
	 * @param name 姓名
	 * @param gender 性别
	 * @param position 职称
	 */
	public Teacher(String teacherID, String name, String gender, String position) {
		this.teacherID = teacherID;
		this.name = name;
		this.gender = gender;
		this.position = position;
	}

	/**
	 * 获得该教师的身份证号。
	 * 
	 * @return 该教师的身份证号
	 */
	public String getTeacherID() {
		return teacherID;
	}

	/**
	 * 获得该教师的姓名。
	 * 
	 * @return 该教师的姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获得该教师的性别。
	 * 
	 * @return 该教师的性别
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * 获得该教师的职称。
	 * 
	 * @return 该教师的职称
	 */
	public String getPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((teacherID == null) ? 0 : teacherID.hashCode());
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
		Teacher other = (Teacher) obj;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (teacherID == null) {
			if (other.teacherID != null)
				return false;
		} else if (!teacherID.equals(other.teacherID))
			return false;
		return true;
	}
	
	
}
