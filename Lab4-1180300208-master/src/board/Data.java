package board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 计划项列表。
 * 
 * @author 梁书育
 *
 * @param <L> Data中存储的内容，在这里是计划项
 */
public abstract class Data<L> implements Iterable<L> {

	List<L> dataList = new ArrayList<>();
	
	// Abstraction function:
	// 	  计划项列表，存储着计划项
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    返回计划项的List进行了防御式拷贝
			
	/**
	 * 向计划项列表中添加计划项（不能重复添加）。
	 * 
	 * @param data 要添加的计划项
	 */
	public void addData(L data) {
		if(!dataList.contains(data)) {
			dataList.add(data);
		}
	}
	
	/**
	 * 删除计划项列表中的计划项。
	 * 
	 * @param data 要删除的计划项
	 */
	public void deleteData(L data) {
		if(dataList.contains(data)) {
			dataList.remove(data);
		}
	}

	/**
	 * 获得计划项列表。
	 * 
	 * @return 计划项列表
	 */
	public List<L> getDataList() {
		List<L> dataListClone = new ArrayList<>();
		dataListClone.addAll(dataList);
		return dataListClone;
	}
	
	@Override
	public Iterator<L> iterator() {
		return new DataIterator();
	}
	
	/**
	 * 为Data类添加的Iterator类。
	 * 
	 * @author 梁书育
	 */
	private class DataIterator implements Iterator<L> {

		int cursor = 0;
		
		@Override
		public boolean hasNext() {
			return cursor != dataList.size();
		}

		@Override
		public L next() {
			int i = cursor;
			if(i >= dataList.size())
				throw new NoSuchElementException();
			cursor++;
			return dataList.get(i);
		}
		
	}

}
