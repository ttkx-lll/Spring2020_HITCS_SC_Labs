package applicationEntry;

import characterEntry.DoubleLocationEntry;
import characterEntry.SingularResourceEntry;
import characterEntry.UnblockableEntry;
import source.Plane;

/**
 * 航班计划项接口。
 * 
 * @author 梁书育
 */
public interface FlightPlanningEntry extends DoubleLocationEntry, UnblockableEntry, SingularResourceEntry<Plane> { }
