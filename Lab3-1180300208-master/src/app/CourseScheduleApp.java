package app;

import java.util.List;
import java.util.Scanner;

import applicationEntry.CourseEntry;
import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import source.Teacher;

/**
 * 课程计划项app
 * 
 * @author 梁书育
 *
 */
public class CourseScheduleApp {

	private CourseScheduleOperation cso;
	
	public CourseScheduleApp() {
		this.cso = new CourseScheduleOperation();
	}
	
	/**
	 * 获得名称为planName的计划项
	 * 
	 * @param planName 计划项名称
	 * @return 如果存在该车次则返回该计划项，若不存在则返回{@code null}
	 */
	public CourseEntry getPlan(String planName) {
		return cso.getPlan(planName);
	}
	
	/**
	 * 添加资源。
	 * 
	 * @param resource 要添加的资源
	 */
	public boolean addResource(Teacher resource) {
		return cso.addResource(resource);
	}
	
	/**
	 * 删除资源。
	 * 
	 * @param resource 删除要添加的资源
	 */
	public boolean delResource(Teacher resource) {
		return cso.delResource(resource);
	}
	
	/**
	 * 添加位置。
	 * 
	 * @param loc 要添加的位置
	 */
	public boolean addLocation(Location loc) {
		return cso.addLocation(loc);
	}
	
	/**
	 * 删除位置。
	 * 
	 * @param loc 要删除的位置
	 */
	public boolean delLocation(Location loc) {
		return cso.delLocation(loc);
	}
	
	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param loc 教室信息
	 * @param timeslot 上下课时间
	 * @return {@code true}添加成功，否则{@code false}
	 */
	public boolean addPlan(String planName, Location loc, Timeslot timeslot) {
		return cso.addPlan(planName, loc, timeslot);
	}
	
	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void cancelPlan(String planName) {
		cso.cancelPlan(planName);
	}
	
	/**
	 * 为名称为planName的计划项分配资源。
	 * 
	 * @param planName
	 * @param resource
	 */
	public boolean distributeResource(String planName, Teacher resource) {
		return cso.distributeResource(planName, resource);
	}
	
	/**
	 * 启动名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void startPlan(String planName) {
		cso.startPlan(planName);
	}
	
	/**
	 * 改变计划项名称为planName的计划项的位置
	 * 
	 * @param planName 计划项名称
	 * @param postLoc 改变后的位置
	 * @return 改变成功返回{@code true}，否则返回{@code false}
	 */
	public boolean changeLocation(String planName, Location postLoc) {
		return cso.changeLocation(planName, postLoc);
	}
	
	/**
	 * 结束名称为planName的计划项
	 * 
	 * @param planName 计划项名称
	 */
	public void endPlan(String planName) {
		cso.endPlan(planName);
	}
	
	/**
	 * 获得名称为planName的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName) {
		return cso.getState(planName);
	}
	
	/**
	 * 检测是否有资源和位置的冲突。
	 * 
	 * @return {@code true}如果有资源或位置冲突；{@code false}如果没有资源或位置的冲突。
	 */
	public boolean checkLocationAndResourseConflict() {
		return cso.checkLocationAndResourseConflict();
	}
	
	/**
	 * 获得所有用到某一特定资源的计划项列表。
	 * 
	 * @param resource 选定的资源
	 * @return 使用这一特定资源的计划项列表
	 */
	public List<PlanningEntry<Teacher>> getAllPlanWithResource(Teacher resource) {
		return cso.getAllPlanWithResource(resource);
	}
	
	/**
	 * 查看位置{@code locName}的看板。
	 * 
	 * @param locName 位置名称
	 */
	public void viewBoardOfLoc(String locName) {
		cso.viewBoardOfLoc(locName);
	}
	
	/**
	 * 打印提示。
	 */
	public void help() {
		System.out.println("\n操作序号：");
		System.out.println("   1、增加教师");
		System.out.println("   2、删除教师");
		System.out.println("   3、增加教室");
		System.out.println("   4、删除教室");
		System.out.println("   5、增加新的课程");
		System.out.println("   6、取消课程");
		System.out.println("   7、为课程分配老师");
		System.out.println("   8、开始课程");
		System.out.println("   9、为某课程更换教室");
		System.out.println("  10、结束课程");
		System.out.println("  11、查看课程状态");
		System.out.println("  12、检测课程列表中可能存在的位置和资源独占冲突");
		System.out.println("  13、列出某一教师的所有课程");
		System.out.println("  14、可视化展示当前时刻某一位置的信息板");
		System.out.println("  15、提示操作序号");
		System.out.println("  16、退出");
	}
	
	public static void main(String[] args) {
		
		String choose;
		Scanner in = new Scanner(System.in);
		CourseScheduleApp csa = new CourseScheduleApp();
		
		System.out.println("==========================");
		System.out.println(" 欢迎来到课程计划项模拟app！");
		System.out.println("==========================\n");
		
		csa.help();
		
		while(true) {
			System.out.println("\n请输入您的选择：");
			choose = in.nextLine();
			try {
				if(choose.equals("1")) {
					System.out.println("请输入要添加的教师资源信息：身份证号、姓名、性别、职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Teacher teacher = new Teacher(messages[0], messages[1], messages[2], messages[3]);
					if(csa.addResource(teacher)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("2")) {
					System.out.println("请输入要添加的教师资源信息：身份证号、姓名、性别、职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Teacher teacher = new Teacher(messages[0], messages[1], messages[2], messages[3]);
					if(csa.delResource(teacher)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if(choose.equals("3")) {
					System.out.println("请输入要添加的教室名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, false);
					if(csa.addLocation(location)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("4")) {
					System.out.println("请输入要删除的教室名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, false);
					if(csa.delLocation(location)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if(choose.equals("5")) {
					System.out.println("请输入要添加的新的课程信息：课程名称、上课教室、上课时间、下课时间（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					String[] startTime = messages[2].split("-");
					String[] endTime = messages[3].split("-");
					int y1 = Integer.valueOf(startTime[0]);
					int mon1 = Integer.valueOf(startTime[1]);
					int d1 = Integer.valueOf(startTime[2]);
					int h1 = Integer.valueOf(startTime[3]);
					int min1 = Integer.valueOf(startTime[4]);
					int y2 = Integer.valueOf(endTime[0]);
					int mon2 = Integer.valueOf(endTime[1]);
					int d2 = Integer.valueOf(endTime[2]);
					int h2 = Integer.valueOf(endTime[3]);
					int min2 = Integer.valueOf(endTime[4]);
					if(csa.addPlan(messages[0], new Location(messages[1], false), new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2))) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("6")) {
					System.out.println("请输入要取消的课程名称：");
					String messageLine = in.nextLine();
					csa.cancelPlan(messageLine);
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if(choose.equals("7")) {
					System.out.println("请输入要分配的课程名称、教师身份证号、教师姓名、教师性别、教师职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					if(csa.distributeResource(messages[0], new Teacher(messages[1], messages[2], messages[3], messages[4]))) {
						System.out.println("分配成功！");
					} else {
						System.out.println("分配失败！");
					}
				} else if(choose.equals("8")) {
					System.out.println("请输入要开始的课程名称：");
					String messageLine = in.nextLine();
					csa.startPlan(messageLine);
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if(choose.equals("9")){
					System.out.println("请输入要更改位置的课程名称和要更改的教室名称：（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					if(csa.changeLocation(messages[0], new Location(messages[1], false))) {
						System.out.println("更换位置成功！");
					} else {
						System.out.println("更换位置失败！");
					}
				} else if(choose.equals("10")) {
					System.out.println("请输入要取消的课程名称：");
					String messageLine = in.nextLine();
					csa.endPlan(messageLine);
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if(choose.equals("11")) {
					System.out.println("请输入要查看状态的的课程名称：");
					String messageLine = in.nextLine();
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if(choose.equals("12")) {
					if(csa.checkLocationAndResourseConflict()) {
						System.out.println("存在冲突！");
					} else {
						System.out.println("不存在冲突！");
					}
				} else if(choose.equals("13")) {
					System.out.println("请输入要查找的教师资源信息：身份证号、姓名、性别、职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Teacher teacher = new Teacher(messages[0], messages[1], messages[2], messages[3]);
					System.out.println(teacher.getName() + "的课程有：");
					for(PlanningEntry<Teacher> plan : csa.getAllPlanWithResource(teacher)) {
						System.out.println(plan.getPlanningName() + "\t" + plan.getPresentStateName());
					}
					System.out.println("是否继续查找某一课程的前序课程？（y\\n）");
					messageLine = in.nextLine();
					if(messageLine.toLowerCase().equals("y")) {
						PlanningEntryAPIs<Teacher> help = new PlanningEntryAPIs<>();
						System.out.println("请输入查找前序计划项的课程名称：");
						messageLine = in.nextLine();
						CourseEntry ce = csa.getPlan(messageLine);
						CourseEntry ce2 = (CourseEntry) help.findPreEntryPerResource(teacher, ce, csa.getAllPlanWithResource(teacher));
						if(ce2 != null) {
							System.out.println("前序计划项为：" + ce2.getPlanningName());
						} else {
							System.out.println("未找到前序计划项！");
						}
					}
				} else if(choose.equals("14")) {
					System.out.println("请输入要查看看板的位置：");
					csa.viewBoardOfLoc(in.nextLine());
				} else if(choose.equals("15")) {
					csa.help();
				} else if(choose.equals("16")) {
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
