package applicationEntry;

import characterEntryImpl.SingularChangeableLocationEntryImpl;
import characterEntryImpl.SingularResourceEntryImpl;
import characterEntryImpl.UnblockableEntryImpl;
import common.element.Location;
import common.element.Timeslot;
import source.Teacher;

/**
 * 实现CourseEntry的工厂类。
 * 
 * @author 梁书育
 */
public class CourseFactory {

	/**
	 * 通过给定一定的条件实现CourseEntry。
	 * 
	 * @param name 课程名称
	 * @param scle 实现好的单一可更换位置接口的实现类
	 * @param sre 实现好的计划项单一资源接口的实现类
	 * @param ue 实现好的时间不可阻塞接口的实现类
	 * @return 新的课程计划项
	 */
	public CourseEntry getEntry(String name, SingularChangeableLocationEntryImpl scle, SingularResourceEntryImpl<Teacher> sre,
			UnblockableEntryImpl ue) {
		return new CourseEntry(name, scle, sre, ue);
	}
	
	/**
	 * 通过给定一定的条件实现CourseEntry。
	 * 
	 * @param name 课程名称
	 * @param loc 上课教室
	 * @param timeslot 上下课时间
	 * @return 新的课程计划项
	 */
	public CourseEntry getEntry(String name, Location loc, Timeslot timeslot) {
		SingularChangeableLocationEntryImpl scle = new SingularChangeableLocationEntryImpl(loc);
		SingularResourceEntryImpl<Teacher> sre = new SingularResourceEntryImpl<Teacher>();
		UnblockableEntryImpl ue = new UnblockableEntryImpl(timeslot);
		return new CourseEntry(name, scle, sre, ue);
	}
}
