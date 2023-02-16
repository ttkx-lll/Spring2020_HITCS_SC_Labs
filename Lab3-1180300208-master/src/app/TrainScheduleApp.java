package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import applicationEntry.TrainEntry;
import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import source.Carriage;

/**
 * 列车计划项app
 * 
 * @author 梁书育
 *
 */
public class TrainScheduleApp {

	private TrainScheduleOperation tso;
	
	public TrainScheduleApp() {
		this.tso = new TrainScheduleOperation();
	}
	
	/**
	 * 获得名称为planName的计划项
	 * 
	 * @param planName 计划项名称
	 * @return 如果存在该车次则返回该计划项，若不存在则返回{@code null}
	 */
	public TrainEntry getPlan(String planName) {
		return tso.getPlan(planName);
	}
	
	/**
	 * 添加资源。
	 * 
	 * @param resource 要添加的资源
	 */
	public boolean addResource(Carriage resource) {
		return tso.addResource(resource);
	}
	
	/**
	 * 删除资源。
	 * 
	 * @param resource 删除要添加的资源
	 */
	public boolean delResource(Carriage resource) {
		return tso.delResource(resource);
	}
	
	/**
	 * 添加位置。
	 * 
	 * @param loc 要添加的位置
	 */
	public boolean addLocation(Location loc) {
		return tso.addLocation(loc);
	}
	
	/**
	 * 删除位置。
	 * 
	 * @param loc 要删除的位置
	 */
	public boolean delLocation(Location loc) {
		return tso.delLocation(loc);
	}
	
	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param start 起始位置
	 * @param end 终止位置
	 * @param midLocList 中间经停位置
	 * @param totalTimeslot 总体起止时间
	 * @param blockTimeslotList 中间经停阻塞时间
	 * @return {@code true}添加成功，否则{@code false}
	 */
	public boolean addPlan(String planName, Location start, Location end, List<Location> midLocList, 
			Timeslot totalTimeslot, List<Timeslot> blockTimeslotList) {
		return tso.addPlan(planName, start, end, midLocList, totalTimeslot, blockTimeslotList);
	}
	
	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void cancelPlan(String planName) {
		tso.cancelPlan(planName);
	}
	
	/**
	 * 为名称为planName的计划项分配资源（添加一个车厢）
	 * 
	 * @param planName 计划项名称
	 * @param resource 资源
	 */
	public boolean distributeResource(String planName, Carriage resource) {
		return tso.distributeResource(planName, resource);
	}
	
	/**
	 * 启动名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void startPlan(String planName) {
		tso.startPlan(planName);
	}
	
	/**
	 * 阻塞名称为planNmae的计划项
	 * 
	 * @param planName 计划项名称
	 */
	public void blockPlan(String planName) {
		tso.blockPlan(planName);
	}
	
	/**
	 * 重新开始名称为planName的计划项
	 * 
	 * @param planName 计划项名称
	 */
	public void restartPlan(String planName) {
		tso.restartPlan(planName);
	}
	
	/**
	 * 结束名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void endPlan(String planName) {
		tso.endPlan(planName);
	}
	
	/**
	 * 获得名称为planName的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName) {
		return tso.getState(planName);
	}
	
	/**
	 * 检测是否有资源和位置的冲突。
	 * 
	 * @return {@code true}如果有资源或位置冲突；{@code false}如果没有资源或位置的冲突。
	 */
	public boolean checkLocationAndResourseConflict() {
		return tso.checkLocationAndResourseConflict();
	}
	
	/**
	 * 获得所有用到某一特定资源的计划项列表。
	 * 
	 * @param resource 选定的资源
	 * @return 使用这一特定资源的计划项列表
	 */
	public List<PlanningEntry<Carriage>> getAllPlanWithResource(Carriage resource) {
		return tso.getAllPlanWithResource(resource);
	}
	
	/**
	 * 查看位置{@code locName}的看板。
	 * 
	 * @param locName 位置名称
	 */
	public void viewBoardOfLoc(String locName) {
		tso.viewBoardOfLoc(locName);
	}
	
	/**
	 * 打印提示。
	 */
	public void help() {
		System.out.println("\n操作序号：");
		System.out.println("   1、增加资源");
		System.out.println("   2、删除资源");
		System.out.println("   3、增加位置");
		System.out.println("   4、删除位置");
		System.out.println("   5、增加新的车次");
		System.out.println("   6、取消车次");
		System.out.println("   7、为车次分配一节车厢");
		System.out.println("   8、启动车次");
		System.out.println("   9、阻塞车次");
		System.out.println("  10、重新启动车次");
		System.out.println("  11、结束车次");
		System.out.println("  12、查看车次状态");
		System.out.println("  13、检测车次列表中可能存在的位置和资源独占冲突");
		System.out.println("  14、列出使用某一车次的所有计划项");
		System.out.println("  15、可视化展示当前时刻某一位置的信息板");
		System.out.println("  16、提示操作序号");
		System.out.println("  17、退出");
	}
	
	public static void main(String[] args) {
		
		String choose;
		Scanner in = new Scanner(System.in);
		TrainScheduleApp tsa = new TrainScheduleApp();
		
		System.out.println("==========================");
		System.out.println(" 欢迎来到列车计划项模拟app！");
		System.out.println("==========================\n");
		
		tsa.help();
		
		while(true) {
			System.out.println("\n请输入您的选择：");
			choose = in.nextLine();
			try {
				if(choose.equals("1")) {
					System.out.println("请输入要添加的车厢资源信息：车厢编号、车厢型号、定员数、出厂年份（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Carriage carriage = new Carriage(messages[0], messages[1], Integer.valueOf(messages[2]), Integer.valueOf(messages[3]));
					if(tsa.addResource(carriage)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("2")) {
					System.out.println("请输入要添加的车厢资源信息：车厢编号、车厢型号、定员数、出厂年份（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Carriage carriage = new Carriage(messages[0], messages[1], Integer.valueOf(messages[2]), Integer.valueOf(messages[3]));
					if(tsa.delResource(carriage)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if(choose.equals("3")) {
					System.out.println("请输入要添加的位置名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, true);
					if(tsa.addLocation(location)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("4")) {
					System.out.println("请输入要删除的位置名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, true);
					if(tsa.delLocation(location)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if(choose.equals("5")) {
					
					String messageLine, planName;
					String messages[];
					Location start, end;
					LocalDateTime startTime, endTime;
					Timeslot totalTimeslot;
					int year, month, dayOfMonth, hour, minute;
					List<Location> midLocList = new ArrayList<Location>();
					List<Timeslot> blockTimeslotList = new ArrayList<Timeslot>();
					
					System.out.println("请根据提示输入要添加的新的车次信息：");
					System.out.println("请输入车次名称：");
					planName = in.nextLine();
					
					System.out.println("请输入始发地点：");
					start = new Location(in.nextLine(), true);
					
					System.out.println("请输入始发时间：（时间格式为：yyyy-MM-dd-hh-mm）");
					messageLine = in.nextLine();
					messages = messageLine.split("-");
					year = Integer.valueOf(messages[0]);
					month = Integer.valueOf(messages[1]);
					dayOfMonth = Integer.valueOf(messages[2]);
					hour = Integer.valueOf(messages[3]);
					minute = Integer.valueOf(messages[4]);
					startTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
					
					System.out.println("请输入终点地点：");
					end = new Location(in.nextLine(), true);
					
					System.out.println("请输入终止时间：（时间格式为：yyyy-MM-dd-hh-mm）");
					messageLine = in.nextLine();
					messages = messageLine.split("-");
					year = Integer.valueOf(messages[0]);
					month = Integer.valueOf(messages[1]);
					dayOfMonth = Integer.valueOf(messages[2]);
					hour = Integer.valueOf(messages[3]);
					minute = Integer.valueOf(messages[4]);
					endTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
					
					totalTimeslot = new Timeslot(startTime, endTime);
					
					while(true) {
						System.out.println("请输入中间经停车站及停车起止时间：（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm，输入“stop”停止输入）");
						messageLine = in.nextLine();
						if(messageLine.toLowerCase().equals("stop")) {
							break;
						}
						messages = messageLine.split(" ");
						String[] starttime = messages[1].split("-");
						String[] endtime = messages[2].split("-");
						int y1 = Integer.valueOf(starttime[0]);
						int mon1 = Integer.valueOf(starttime[1]);
						int d1 = Integer.valueOf(starttime[2]);
						int h1 = Integer.valueOf(starttime[3]);
						int min1 = Integer.valueOf(starttime[4]);
						int y2 = Integer.valueOf(endtime[0]);
						int mon2 = Integer.valueOf(endtime[1]);
						int d2 = Integer.valueOf(endtime[2]);
						int h2 = Integer.valueOf(endtime[3]);
						int min2 = Integer.valueOf(endtime[4]);
						midLocList.add(new Location(messages[0], true));
						blockTimeslotList.add(new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2));
					}					
					
					if(tsa.addPlan(planName, start, end, midLocList, totalTimeslot, blockTimeslotList)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
					
				} else if(choose.equals("6")) {
					System.out.println("请输入要取消的车次名称：");
					String messageLine = in.nextLine();
					tsa.cancelPlan(messageLine);
					System.out.println("当前该车次状态为：" + tsa.getState(messageLine));
				} else if(choose.equals("7")) {
					System.out.println("请输入要分配的车次名称、车厢编号、车厢型号、定员数、出厂年份（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					if(tsa.distributeResource(messages[0], new Carriage(messages[1], messages[2], Integer.valueOf(messages[3]), Integer.valueOf(messages[4])))) {
						System.out.println("分配成功！");
					} else {
						System.out.println("分配失败！");
					}
				} else if(choose.equals("8")) {
					System.out.println("请输入要启动的车次名称：");
					String messageLine = in.nextLine();
					tsa.startPlan(messageLine);
					System.out.println("当前该车次状态为：" + tsa.getState(messageLine));
				}else if(choose.equals("9")) {
					System.out.println("请输入要阻塞的车次名称：");
					String messageLine = in.nextLine();
					tsa.blockPlan(messageLine);
					System.out.println("当前该车次状态为：" + tsa.getState(messageLine));
				}else if(choose.equals("10")) {
					System.out.println("请输入要重新启动的车次名称：");
					String messageLine = in.nextLine();
					tsa.restartPlan(messageLine);
					System.out.println("当前该车次状态为：" + tsa.getState(messageLine));
				} else if(choose.equals("11")) {
					System.out.println("请输入要结束的车次名称：");
					String messageLine = in.nextLine();
					tsa.endPlan(messageLine);
					System.out.println("当前该车次状态为：" + tsa.getState(messageLine));
				} else if(choose.equals("12")) {
					System.out.println("请输入要查看状态的车次名称：");
					String messageLine = in.nextLine();
					System.out.println("当前该车次状态为：" + tsa.getState(messageLine));
				} else if(choose.equals("13")) {
					if(tsa.checkLocationAndResourseConflict()) {
						System.out.println("存在冲突！");
					} else {
						System.out.println("不存在冲突！");
					}
				} else if(choose.equals("14")) {
					System.out.println("请输入要查找的车厢资源信息：车厢编号、车厢型号、定员数、出厂年份（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Carriage carriage = new Carriage(messages[0], messages[1], Integer.valueOf(messages[2]), Integer.valueOf(messages[3]));
					System.out.println("占用" + carriage.getCarriageID() + "的车次有：");
					for(PlanningEntry<Carriage> plan : tsa.getAllPlanWithResource(carriage)) {
						System.out.println(plan.getPlanningName() + "\t" + plan.getPresentStateName());
					}
					System.out.println("是否继续查找某一航班的前序计划项？（y\\n）");
					messageLine = in.nextLine();
					if(messageLine.toLowerCase().equals("y")) {
						PlanningEntryAPIs<Carriage> help = new PlanningEntryAPIs<>();
						System.out.println("请输入查找前序计划项的车次名称：");
						messageLine = in.nextLine();
						TrainEntry te = tsa.getPlan(messageLine);
						TrainEntry te2 = (TrainEntry) help.findPreEntryPerResource(carriage, te, tsa.getAllPlanWithResource(carriage));
						if(te2 != null) {
							System.out.println("前序计划项为：" + te2.getPlanningName());
						} else {
							System.out.println("未找到前序计划项！");
						}
					}
				} else if(choose.equals("15")) {
					System.out.println("请输入要查看看板的位置：");
					tsa.viewBoardOfLoc(in.nextLine());
				} else if(choose.equals("16")) {
					tsa.help();
				} else if(choose.equals("17")) {
					System.out.println("感谢您的使用！");
					in.close();
					System.exit(0);
				} else {
					System.out.println("请输入正确的选项！");
				}

			} catch(Exception e) {
				System.out.println("输入内容不符合规范！");
			}
		}
	}
}
