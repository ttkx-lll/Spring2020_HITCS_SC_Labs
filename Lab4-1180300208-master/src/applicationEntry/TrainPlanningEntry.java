package applicationEntry;

import characterEntry.BlockableEntry;
import characterEntry.MultipleLocationEntry;
import characterEntry.MultipleSortedResourceEntry;
import resource.Carriage;

/**
 * 列车计划项接口。
 * 
 * @author 梁书育
 */
public interface TrainPlanningEntry extends MultipleLocationEntry, MultipleSortedResourceEntry<Carriage>, BlockableEntry { }
