package applicationEntry;

import characterEntry.ChangeableLocationEntry;
import characterEntry.SingularLocationEntry;
import characterEntry.SingularResourceEntry;
import characterEntry.UnblockableEntry;
import source.Teacher;

/**
 * 课程计划项接口。
 * 
 * @author 梁书育
 */
public interface CoursePlanningEntry
		extends SingularLocationEntry, ChangeableLocationEntry, SingularResourceEntry<Teacher>, UnblockableEntry { }
