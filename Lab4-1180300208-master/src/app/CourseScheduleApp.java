package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import applicationEntry.CourseEntry;
import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import exception.EntryCancelException;
import exception.LocationConflictException;
import exception.ResourceConflictException;
import resource.Teacher;

/**
 * 课程计划项app
 * 
 * @author 梁书育
 *
 */
public class CourseScheduleApp {

	private CourseScheduleOperation cso;

	// logging
	private static Logger logger = Logger.getLogger(CourseScheduleApp.class.getName());
	private static FileHandler fileTxt;
	private static MyFormatter formatterTxt;

	public CourseScheduleApp() {
		this.cso = new CourseScheduleOperation();
		logger.setLevel(Level.INFO);
		logger.setUseParentHandlers(false);
		try {
			fileTxt = new FileHandler("CourseLogging.txt");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		formatterTxt = new MyFormatter();
		fileTxt.setLevel(Level.INFO);
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
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
		logger.log(Level.INFO, "添加了" + resource.getName() + "老师");
		return cso.addResource(resource);
	}

	/**
	 * 删除资源。
	 * 
	 * @param resource 删除要添加的资源
	 */
	public boolean delResource(Teacher resource) {
		try {
			logger.log(Level.INFO, "删除了" + resource.getName() + "老师");
			return cso.delResource(resource);
		} catch (ResourceConflictException e) {
			e.printMessage();
			logger.log(Level.WARNING, "由于" + resource.getName() + "教师的课程尚未结束，删除失败", e);
		}
		return false;
	}

	/**
	 * 添加位置。
	 * 
	 * @param loc 要添加的位置
	 */
	public boolean addLocation(Location loc) {
		logger.log(Level.INFO, "添加了" + loc.getLocName() + "教室");
		return cso.addLocation(loc);
	}

	/**
	 * 删除位置。
	 * 
	 * @param loc 要删除的位置
	 */
	public boolean delLocation(Location loc) {
		try {
			logger.log(Level.INFO, "删除了" + loc.getLocName() + "教室");
			return cso.delLocation(loc);
		} catch (LocationConflictException e) {
			e.printMessage();
			logger.log(Level.WARNING, "由于" + loc.getLocName() + "教室被占用，删除失败", e);
		}
		return false;
	}

	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param loc      教室信息
	 * @param timeslot 上下课时间
	 * @return {@code true}添加成功，否则{@code false}
	 */
	public boolean addPlan(String planName, Location loc, Timeslot timeslot) {
		logger.log(Level.INFO, "添加了" + planName + "课程");
		return cso.addPlan(planName, loc, timeslot);
	}

	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void cancelPlan(String planName) {
		try {
			cso.cancelPlan(planName);
			logger.log(Level.INFO, "取消了" + planName + "课程");
		} catch (EntryCancelException e) {
			e.printMessage();
			logger.log(Level.WARNING, planName + "课程已经开始，无法取消", e);
		}
	}

	/**
	 * 为名称为planName的计划项分配资源。
	 * 
	 * @param planName
	 * @param resource
	 */
	public boolean distributeResource(String planName, Teacher resource) {
		try {
			logger.log(Level.INFO, "为" + planName + "课程分配了" + resource.getName() + "教师");
			return cso.distributeResource(planName, resource);
		} catch (ResourceConflictException e) {
			e.printMessage();
			logger.log(Level.WARNING, "由于" + resource.getName() + "在该课程时间有其他课程，分配失败", e);
		}
		return false;
	}

	/**
	 * 启动名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 */
	public void startPlan(String planName) {
		cso.startPlan(planName);
		logger.log(Level.INFO, "启动了" + planName + "课程");
	}

	/**
	 * 改变计划项名称为planName的计划项的位置
	 * 
	 * @param planName 计划项名称
	 * @param postLoc  改变后的位置
	 * @return 改变成功返回{@code true}，否则返回{@code false}
	 */
	public boolean changeLocation(String planName, Location postLoc) {
		try {
			logger.log(Level.INFO, "将" + planName + "课程调整到了" + postLoc.getLocName() + "教室");
			return cso.changeLocation(planName, postLoc);
		} catch (LocationConflictException e) {
			logger.log(Level.WARNING, "由于" + postLoc.getLocName() + "教室在该课程上课时间被其他课程占用，更改教室失败", e);
			e.printMessage();
		}
		return false;
	}

	/**
	 * 结束名称为planName的计划项
	 * 
	 * @param planName 计划项名称
	 */
	public void endPlan(String planName) {
		cso.endPlan(planName);
		logger.log(Level.INFO, "停止了" + planName + "课程");
	}

	/**
	 * 获得名称为planName的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName) {
		logger.log(Level.INFO, "获取了" + planName + "课程的状态信息");
		return cso.getState(planName);
	}

	/**
	 * 检测是否有资源和位置的冲突。
	 * 
	 * @return {@code true}如果有资源或位置冲突；{@code false}如果没有资源或位置的冲突。
	 */
	public boolean checkLocationAndResourseConflict() {
		logger.log(Level.INFO, "检测了课程列表中是否有教室和教师冲突");
		return cso.checkLocationAndResourseConflict();
	}

	/**
	 * 获得所有用到某一特定资源的计划项列表。
	 * 
	 * @param resource 选定的资源
	 * @return 使用这一特定资源的计划项列表
	 */
	public List<PlanningEntry<Teacher>> getAllPlanWithResource(Teacher resource) {
		logger.log(Level.INFO, "取得了" + resource.getName() + "教师所教授的全部课程列表");
		return cso.getAllPlanWithResource(resource);
	}

	/**
	 * 查看位置{@code locName}的看板。
	 * 
	 * @param locName 位置名称
	 */
	public void viewBoardOfLoc(String locName) {
		cso.viewBoardOfLoc(locName);
		logger.log(Level.INFO, "查看了" + locName + "教室的课程列表");
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
		System.out.println("  16、查询日志信息");
		System.out.println("  17、退出");
		logger.log(Level.INFO, "打印了操作列表");
	}

	public void searchLog(Scanner in) {
		
		String fileName = "CourseLogging.txt";
		BufferedReader br = null;
		String lineTxt;
		int lineNum = 0;
		String time = null;
		String level = null;
		String operation = null;
		String out = "";
		
		System.out.println("请根据提示输入筛选条件：");
		System.out.println("  输入要查看的时间 (格式为yyyy/MM/dd HH:mm:ss)，不用此筛选条件则输入null");
		time = in.nextLine();
		System.out.println("  输入要查看的重要级别 (INFO或WARNING)，不用此筛选条件则输入null");
		level = in.nextLine();
		System.out.println("  输入要查看的操作，不用此筛选条件则输入null");
		operation = in.nextLine();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
			boolean canPrint = true;
			
			while ((lineTxt = br.readLine()) != null) {

				lineNum = lineNum % 7;

				switch (lineNum) {
				case 0:
					if (time.toLowerCase().equals("null") || time.equals(lineTxt)) {
						out += lineTxt + "\n";
					} else {
						canPrint = false;
					}
					break;
				case 1:
					if (level.toLowerCase().equals("null") || ("[" + level + "]").equals(lineTxt)) {
						out += "重要性：" + lineTxt + "\n";
					} else {
						canPrint = false;
					}
					break;
				case 3:
					if (operation.toLowerCase().equals("null") || ("[" + operation + "]").equals(lineTxt)) {
						out += "调用方法：" + lineTxt + "\n";
					} else {
						canPrint = false;
					}
					break;
				case 5:
					out += "具体操作：" + lineTxt + "\n";
					break;
				case 6:
					if (canPrint) {
						System.out.println(out);
					} else {
						canPrint = true;
					}
					out = "";
					break;
				}
				
				lineNum++;
			}
			
			br.close();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		String choose;
		Scanner in = new Scanner(System.in);
		CourseScheduleApp csa = new CourseScheduleApp();

		System.out.println("==========================");
		System.out.println(" 欢迎来到课程计划项模拟app！");
		System.out.println("==========================\n");

		csa.help();

		while (true) {
			System.out.println("\n请输入您的选择：");
			choose = in.nextLine();
			try {
				if (choose.equals("1")) {
					System.out.println("请输入要添加的教师资源信息：身份证号、姓名、性别、职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Teacher teacher = new Teacher(messages[0], messages[1], messages[2], messages[3]);
					if (csa.addResource(teacher)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if (choose.equals("2")) {
					System.out.println("请输入要删除的教师资源信息：身份证号、姓名、性别、职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Teacher teacher = new Teacher(messages[0], messages[1], messages[2], messages[3]);
					if (csa.delResource(teacher)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if (choose.equals("3")) {
					System.out.println("请输入要添加的教室名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, false);
					if (csa.addLocation(location)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if (choose.equals("4")) {
					System.out.println("请输入要删除的教室名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, false);
					if (csa.delLocation(location)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if (choose.equals("5")) {
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
					if (csa.addPlan(messages[0], new Location(messages[1], false),
							new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2))) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if (choose.equals("6")) {
					System.out.println("请输入要取消的课程名称：");
					String messageLine = in.nextLine();
					csa.cancelPlan(messageLine);
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if (choose.equals("7")) {
					System.out.println("请输入要分配的课程名称、教师身份证号、教师姓名、教师性别、教师职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					if (csa.distributeResource(messages[0],
							new Teacher(messages[1], messages[2], messages[3], messages[4]))) {
						System.out.println("分配成功！");
					} else {
						System.out.println("分配失败！");
					}
				} else if (choose.equals("8")) {
					System.out.println("请输入要开始的课程名称：");
					String messageLine = in.nextLine();
					csa.startPlan(messageLine);
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if (choose.equals("9")) {
					System.out.println("请输入要更改位置的课程名称和要更改的教室名称：（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					if (csa.changeLocation(messages[0], new Location(messages[1], false))) {
						System.out.println("更换位置成功！");
					} else {
						System.out.println("更换位置失败！");
					}
				} else if (choose.equals("10")) {
					System.out.println("请输入要取消的课程名称：");
					String messageLine = in.nextLine();
					csa.endPlan(messageLine);
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if (choose.equals("11")) {
					System.out.println("请输入要查看状态的的课程名称：");
					String messageLine = in.nextLine();
					System.out.println("当前该课程状态为：" + csa.getState(messageLine));
				} else if (choose.equals("12")) {
					if (csa.checkLocationAndResourseConflict()) {
						System.out.println("存在冲突！");
					} else {
						System.out.println("不存在冲突！");
					}
				} else if (choose.equals("13")) {
					System.out.println("请输入要查找的教师资源信息：身份证号、姓名、性别、职称（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Teacher teacher = new Teacher(messages[0], messages[1], messages[2], messages[3]);
					System.out.println(teacher.getName() + "的课程有：");
					for (PlanningEntry<Teacher> plan : csa.getAllPlanWithResource(teacher)) {
						System.out.println(plan.getPlanningName() + "\t" + plan.getPresentStateName());
					}
					System.out.println("是否继续查找某一课程的前序课程？（y\\n）");
					messageLine = in.nextLine();
					if (messageLine.toLowerCase().equals("y")) {
						PlanningEntryAPIs<Teacher> help = new PlanningEntryAPIs<>();
						System.out.println("请输入查找前序计划项的课程名称：");
						messageLine = in.nextLine();
						CourseEntry ce = csa.getPlan(messageLine);
						CourseEntry ce2 = (CourseEntry) help.findPreEntryPerResource(teacher, ce,
								csa.getAllPlanWithResource(teacher));
						if (ce2 != null) {
							System.out.println("前序计划项为：" + ce2.getPlanningName());
						} else {
							System.out.println("未找到前序计划项！");
						}
					}
				} else if (choose.equals("14")) {
					System.out.println("请输入要查看看板的位置：");
					csa.viewBoardOfLoc(in.nextLine());
				} else if (choose.equals("15")) {
					csa.help();
				} else if (choose.equals("16")) {
					csa.searchLog(in);
				} else if (choose.equals("17")) {
					System.out.println("感谢您的使用！");
					in.close();
					logger.log(Level.INFO, "退出程序");
					fileTxt.close();
					System.exit(0);
				} else {
					System.out.println("请输入正确的选项！");
				}

			} catch (Exception e) {
				System.out.println("输入内容不符合规范！");
			}
		}
	}
}
