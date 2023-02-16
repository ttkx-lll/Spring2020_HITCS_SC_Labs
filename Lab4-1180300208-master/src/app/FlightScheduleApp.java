package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.FlightScheduleApp;
import applicationEntry.FlightEntry;
import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import exception.DateInputIlleagalException;
import exception.EntryCancelException;
import exception.FlightDateException;
import exception.FlightMessageException;
import exception.FlightNameIllegalException;
import exception.LocationConflictException;
import exception.LocationNameException;
import exception.OverOneDayException;
import exception.PlaneAgeException;
import exception.PlaneIDException;
import exception.PlaneMessageException;
import exception.PlaneSeatNumberException;
import exception.PlaneTypeException;
import exception.ResourceConflictException;
import exception.SameLabelException;
import exception.TimeInputIlleagalException;
import resource.Plane;

/**
 * 航班计划项app
 * 
 * @author 梁书育
 *
 */
public class FlightScheduleApp {

	private boolean illegal = false;
	private FlightScheduleOperation fso;
	int number = 0;

	// logging
	private static Logger logger = Logger.getLogger(CourseScheduleApp.class.getName());
	private static FileHandler fileTxt;
	private static MyFormatter formatterTxt;

	public FlightScheduleApp() {
		setLog();
		this.fso = new FlightScheduleOperation();
	}

	/**
	 * 从文件读入
	 * 
	 * @param fileName
	 */
	public void readInFile(String fileName) {
		try {
			logger.log(Level.INFO, "从文件" + fileName + "中读入数据");
			generateFlightApp(fileName);
		} catch (FlightNameIllegalException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "航班名称要求2位大写字母和2-4位数字但却使用了非大写字母或小于2位或超过4位数字", e);
		} catch (DateInputIlleagalException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "输入日期格式不合法", e);
		} catch (LocationNameException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "位置名称不合法，出现了非字母", e);
		} catch (TimeInputIlleagalException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "输入的时间格式不合法", e);
		} catch (PlaneIDException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "输入的飞机ID第一位不为 N 或 B，或者后面不是四位数字", e);
		} catch (PlaneTypeException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "机型信息中含有除大小写字母和数字以外的其他字符", e);
		} catch (PlaneSeatNumberException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "飞机座位数范围为在[50,600]之外或者输入数据非整型数据", e);
		} catch (PlaneAgeException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "输入的机龄使用了超过1位的小数，或者范围不在[0, 30]内", e);
		} catch (SameLabelException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "存在多个航班计划项的“日期,航班号”信息完全一样的情况", e);
		} catch (FlightDateException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "第一行出现的航班日期与内部出现的起飞时间中的日期不一致", e);
		} catch (OverOneDayException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "降落时间中的日期与航班日期差距大于 1 天", e);
		} catch (FlightMessageException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "同一个航班号，虽然日期不同，但其出发或到达机场、 出发或到达时间有差异", e);
		} catch (PlaneMessageException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "在不同航班计划项中出现编号一样的飞机，但飞机的类型、座位数或机龄却不一致", e);
		} catch (ResourceConflictException e) {
			System.out.println("在读入第" + number + "组数据时发生错误！");
			e.printMessage();
			this.illegal = true;
			logger.log(Level.WARNING, "在为某计划项分配某资源的时候，分配后会导致与已有的其他计划项产生“资源独占冲突”", e);
		}
	}

	/**
	 * 设置logger的格式
	 */
	public static void setLog() {
		logger.setLevel(Level.INFO);
		logger.setUseParentHandlers(false);
		try {
			fileTxt = new FileHandler("FlightLogging.txt");
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
	 * 从文件中解析航班计划信息。
	 * 
	 * @param fileName 文件名（不带后缀）
	 * @return 航班计划项操作
	 */
	private void generateFlightApp(String fileName) throws FlightNameIllegalException, DateInputIlleagalException,
			LocationNameException, TimeInputIlleagalException, PlaneIDException, PlaneTypeException,
			PlaneSeatNumberException, PlaneAgeException, SameLabelException, FlightDateException, OverOneDayException,
			FlightMessageException, PlaneMessageException, ResourceConflictException {

		String filename = "src/data/txt/" + fileName + ".txt";
		Pattern flightDatePattern = Pattern.compile("^Flight:(\\d{4})-(\\d{2})-(\\d{2}),.*$");
		Pattern flightNamePattern = Pattern.compile("^Flight:.*,([A-Z]{2}\\d{2,4})$");
		Pattern departureAirportPattern = Pattern.compile("^DepartureAirport:([a-zA-Z]+)$");
		Pattern arrivalAirportPattern = Pattern.compile("^ArrivalAirport:([a-zA-Z]+)$");
		Pattern departureTimePattern = Pattern.compile("^DepatureTime:(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2})$");
		Pattern arrivalTimePattern = Pattern.compile("^ArrivalTime:(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2})$");
		Pattern planeIDPattern = Pattern.compile("^Plane:([N|B]\\d{4})$");
		Pattern planeTypePattern = Pattern.compile("^Type:([a-zA-Z\\d]+)$");
		Pattern planeSeatsPattern = Pattern.compile("^Seats:(([5-9]\\d)|([1-5]\\d{2})|(600))$");
		Pattern planeAgePattern = Pattern.compile("^Age:((([1-2]?[0-9])(\\.\\d)?)|(30|30.0))$");
		Matcher matcher;

		LocalDate presentDate = null;
		String flightName = null;
		Location departureAirport = null;
		Location arrivalAirport = null;
		LocalDateTime departureTime = null;
		LocalDateTime arrivalTime = null;
		Timeslot timeslot = null;
		String planeID = null;
		String planeType = null;
		int planeSeats = 0;
		float planeAge = 0;
		Plane plane;

		fso = new FlightScheduleOperation();
		number = 0;
		illegal = false;

		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filename)), "UTF-8"));
			String lineTxt;

			int lineNum = 0;

			while ((lineTxt = br.readLine()) != null) {

				if (lineTxt.length() == 0) {
					continue;
				}

				lineNum = lineNum % 13;

				switch (lineNum) {
				case 0:
					matcher = flightDatePattern.matcher(lineTxt);
					if (matcher.find() && matcher.groupCount() == 3) {
						int year = Integer.valueOf(matcher.group(1));
						int month = Integer.valueOf(matcher.group(2));
						int dayOfMonth = Integer.valueOf(matcher.group(3));
						presentDate = LocalDate.of(year, month, dayOfMonth);
					} else {
						throw new DateInputIlleagalException();
					}
					matcher = flightNamePattern.matcher(lineTxt);
					if (matcher.find()) {
						flightName = matcher.group(1);
					} else {
						throw new FlightNameIllegalException();
					}
					break;

				case 2:
					matcher = departureAirportPattern.matcher(lineTxt);
					if (matcher.find()) {
						departureAirport = new Location(matcher.group(1), true);
					} else {
						throw new LocationNameException();
					}
					break;

				case 3:
					matcher = arrivalAirportPattern.matcher(lineTxt);
					if (matcher.find()) {
						arrivalAirport = new Location(matcher.group(1), true);
					} else {
						throw new LocationNameException();
					}
					break;

				case 4:
					matcher = departureTimePattern.matcher(lineTxt);
					if (matcher.find() && matcher.groupCount() == 5) {
						int year = Integer.valueOf(matcher.group(1));
						int month = Integer.valueOf(matcher.group(2));
						int dayOfMonth = Integer.valueOf(matcher.group(3));
						int hour = Integer.valueOf(matcher.group(4));
						int minute = Integer.valueOf(matcher.group(5));
						departureTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
						if (!departureTime.toLocalDate().equals(presentDate)) {
							throw new FlightDateException();
						}
					} else {
						throw new TimeInputIlleagalException();
					}
					break;

				case 5:
					matcher = arrivalTimePattern.matcher(lineTxt);
					if (matcher.find() && matcher.groupCount() == 5) {
						int year = Integer.valueOf(matcher.group(1));
						int month = Integer.valueOf(matcher.group(2));
						int dayOfMonth = Integer.valueOf(matcher.group(3));
						int hour = Integer.valueOf(matcher.group(4));
						int minute = Integer.valueOf(matcher.group(5));
						arrivalTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
						Period period;
						period = Period.between(arrivalTime.toLocalDate(), presentDate);
						if (!arrivalTime.toLocalDate().equals(presentDate) && Math.abs(period.getDays()) > 1) {
							throw new OverOneDayException();
						}
					} else {
						throw new TimeInputIlleagalException();
					}
					break;

				case 6:
					matcher = planeIDPattern.matcher(lineTxt);
					if (matcher.find()) {
						planeID = matcher.group(1);
					} else {
						throw new PlaneIDException();
					}
					break;

				case 8:
					matcher = planeTypePattern.matcher(lineTxt);
					if (matcher.find()) {
						planeType = matcher.group(1);
					} else {
						throw new PlaneTypeException();
					}
					break;

				case 9:
					matcher = planeSeatsPattern.matcher(lineTxt);
					if (matcher.find()) {
						planeSeats = Integer.valueOf(matcher.group(1));
					} else {
						throw new PlaneSeatNumberException();
					}
					break;

				case 10:
					matcher = planeAgePattern.matcher(lineTxt);
					if (matcher.find()) {
						planeAge = Float.valueOf(matcher.group(1));
					} else {
						throw new PlaneAgeException();
					}
					break;

				}

				if (lineNum == 12) {
					number++;
					fso.addLocation(departureAirport);
					fso.addLocation(arrivalAirport);
					timeslot = new Timeslot(departureTime, arrivalTime);
					int flag = fso.checkIlleagalPlan(flightName, departureAirport, arrivalAirport, timeslot);
					if (flag == 1) {
						br.close();
						throw new FlightNameIllegalException();
					} else if (flag == 2) {
						br.close();
						throw new SameLabelException();
					} else if (flag == 3) {
						br.close();
						throw new FlightMessageException();
					}
					addPlan(flightName, departureAirport, arrivalAirport, timeslot);
					plane = new Plane(planeID, planeType, planeSeats, planeAge);
					for (Plane p : fso.getResourceSet()) {
						if (p.getPlaneID().equals(planeID)) {
							if (!p.equals(plane)) {
								br.close();
								throw new PlaneMessageException();
							}
						}
					}
					fso.addResource(plane);
					fso.distributeResource(flightName, timeslot, plane);
				}

				lineNum++;
			}

			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("没有该文件，重新输入文件名！");
			this.illegal = true;
			logger.log(Level.WARNING, "输入了不存在的文件名", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "读取文件出现IO错误", e);
		}
		System.out.println("共读入" + number + "个计划项！");

	}

	/**
	 * 获得名称为planName，起止时间为timeslot的计划项
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @return 如果存在该航班则返回该计划项，若不存在则返回{@code null}
	 */
	public FlightEntry getPlan(String planName, Timeslot timeslot) {
		return fso.getPlan(planName, timeslot);
	}

	/**
	 * 添加资源。
	 * 
	 * @param resource 要添加的资源
	 */
	public boolean addResource(Plane resource) {
		logger.log(Level.INFO, "添加了" + resource.getPlaneID() + "飞机");
		return fso.addResource(resource);
	}

	/**
	 * 删除资源。
	 * 
	 * @param resource 删除要添加的资源
	 */
	public boolean delResource(Plane resource) {
		try {
			logger.log(Level.INFO, "删除了" + resource.getPlaneID() + "飞机");
			return fso.delResource(resource);
		} catch (ResourceConflictException e) {
			e.printMessage();
			logger.log(Level.WARNING, "由于" + resource.getPlaneID() + "飞机所在的航班没有结束，删除失败", e);
		}
		return false;
	}

	/**
	 * 添加位置。
	 * 
	 * @param loc 要添加的位置
	 */
	public boolean addLocation(Location loc) {
		logger.log(Level.INFO, "添加了" + loc.getLocName() + "机场");
		return fso.addLocation(loc);
	}

	/**
	 * 删除位置。
	 * 
	 * @param loc 要删除的位置
	 */
	public boolean delLocation(Location loc) {
		try {
			logger.log(Level.INFO, "删除了" + loc.getLocName() + "机场");
			return fso.delLocation(loc);
		} catch (LocationConflictException e) {
			e.printMessage();
			logger.log(Level.WARNING, "由于有以" + loc.getLocName() + "机场为目的或终点的航班尚未结束，删除失败", e);
		}
		return false;
	}

	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param start    起始位置
	 * @param end      终止位置
	 * @param timeslot 起止时间
	 */
	public boolean addPlan(String planName, Location start, Location end, Timeslot timeslot) {
		logger.log(Level.INFO, "添加了" + planName + "航班");
		return fso.addPlan(planName, start, end, timeslot);
	}

	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void cancelPlan(String planName, Timeslot timeslot) {
		try {
			fso.cancelPlan(planName, timeslot);
			logger.log(Level.INFO, "取消了" + planName + "航班");
		} catch (EntryCancelException e) {
			e.printMessage();
			logger.log(Level.WARNING, planName + "航班正在运行中，无法取消", e);
		}
	}

	/**
	 * 为名称为planName，起止时间为timeslot的计划项分配资源。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @param resource 要添加的资源
	 */
	public boolean distributeResource(String planName, Timeslot timeslot, Plane resource) {
		try {
			logger.log(Level.INFO, "为" + planName + "航班分配了" + resource.getPlaneID() + "飞机");
			return fso.distributeResource(planName, timeslot, resource);
		} catch (ResourceConflictException e) {
			e.printMessage();
			logger.log(Level.WARNING, "由于" + resource.getPlaneID() + "在该航班时间有其他航班占用，分配失败", e);
		}
		return false;
	}

	/**
	 * 启动名称为planName，起止时间为timeslot的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void startPlan(String planName, Timeslot timeslot) {
		logger.log(Level.INFO, "启动了" + planName + "航班");
		fso.startPlan(planName, timeslot);
	}

	/**
	 * 结束名称为planName，起止时间为timeslot的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void endPlan(String planName, Timeslot timeslot) {
		fso.endPlan(planName, timeslot);
		logger.log(Level.INFO, "终止了" + planName + "航班");
	}

	/**
	 * 获得名称为planName，起止时间为timeslot的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName, Timeslot timeslot) {
		logger.log(Level.INFO, "获取了" + planName + "航班的状态信息");
		return fso.getState(planName, timeslot);
	}

	/**
	 * 检测是否有资源和位置的冲突。
	 * 
	 * @return {@code true}如果有资源或位置冲突；{@code false}如果没有资源或位置的冲突。
	 */
	public boolean checkLocationAndResourseConflict() {
		logger.log(Level.INFO, "检测了航班列表中是否有飞机和机场冲突");
		return fso.checkLocationAndResourseConflict();
	}

	/**
	 * 获得所有用到某一特定资源的计划项列表。
	 * 
	 * @param resource 选定的资源
	 * @return 使用这一特定资源的计划项列表
	 */
	public List<PlanningEntry<Plane>> getAllPlanWithResource(Plane resource) {
		logger.log(Level.INFO, "取得了" + resource.getPlaneID() + "飞机的全部航线列表");
		return fso.getAllPlanWithResource(resource);
	}

	/**
	 * 查看位置{@code locName}的看板。
	 * 
	 * @param locName 位置名称
	 */
	public void viewBoardOfLoc(String locName) {
		fso.viewBoardOfLoc(locName);
		logger.log(Level.INFO, "查看了" + locName + "机场一小时以内的起降航班列表");
	}

	/**
	 * 打印提示。
	 */
	public void help() {
		System.out.println("\n操作序号：");
		System.out.println("   1、增加飞机资源");
		System.out.println("   2、删除飞机资源");
		System.out.println("   3、增加位置");
		System.out.println("   4、删除位置");
		System.out.println("   5、增加新的航班");
		System.out.println("   6、取消航班");
		System.out.println("   7、为航班分配飞机");
		System.out.println("   8、启动航班");
		System.out.println("   9、结束航班");
		System.out.println("  10、查看航班状态");
		System.out.println("  11、检测航班列表中可能存在的位置和资源独占冲突");
		System.out.println("  12、列出使用某一飞机的所有计划项");
		System.out.println("  13、可视化展示当前时刻某一位置的信息板");
		System.out.println("  14、提示操作序号");
		System.out.println("  15、查询日志信息");
		System.out.println("  16、退出");
		logger.log(Level.INFO, "打印了操作列表");
	}

	public void searchLog(Scanner in) {

		String fileName = "FlightLogging.txt";
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
		FlightScheduleApp fsa;

		System.out.println("==========================");
		System.out.println(" 欢迎来到航班计划项模拟app！");
		System.out.println("==========================\n");

		while (true) {

			System.out.println("请选择数据来源：");
			System.out.println("  1、手动输入");
			System.out.println("  2、文件读入");

			choose = in.nextLine();

			if (choose.equals("1")) {
				fsa = new FlightScheduleApp();
				break;

			} else if (choose.equals("2")) {
				fsa = new FlightScheduleApp();
				while (true) {
					System.out.println("请输入文件名：");
					String filename = in.nextLine();
					fsa.readInFile(filename);
					System.out.println("文件读入中，请稍候……");
					if (!fsa.illegal) {
						System.out.println("文件读入成功！");
						break;
					} else {
						System.out.println("您应当输入一个合法的文件名！");
					}
				}
				break;

			} else {
				System.out.println("输入不正确，重新输入！");
			}
		}

		fsa.help();

		while (true) {
			System.out.println("\n请输入您的选择：");
			choose = in.nextLine();
			try {
				if (choose.equals("1")) {
					System.out.println("请输入要添加的飞机资源信息：飞机编号、飞机型号、座位数、机龄（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Plane plane = new Plane(messages[0], messages[1], Integer.valueOf(messages[2]),
							Float.valueOf(messages[3]));
					if (fsa.addResource(plane)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if (choose.equals("2")) {
					System.out.println("请输入要删除的飞机资源信息：飞机编号、飞机型号、座位数、机龄（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Plane plane = new Plane(messages[0], messages[1], Integer.valueOf(messages[2]),
							Float.valueOf(messages[3]));
					if (fsa.delResource(plane)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if (choose.equals("3")) {
					System.out.println("请输入要添加的位置名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, true);
					if (fsa.addLocation(location)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if (choose.equals("4")) {
					System.out.println("请输入要删除的位置名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, true);
					if (fsa.delLocation(location)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if (choose.equals("5")) {
					System.out.println("请输入要添加的新的航班信息：航班名称、起始位置、终点位置、起飞时间、降落时间（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					String[] startTime = messages[3].split("-");
					String[] endTime = messages[4].split("-");
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
					if (fsa.addPlan(messages[0], new Location(messages[1], true), new Location(messages[2], true),
							new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2))) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if (choose.equals("6")) {
					System.out.println("请输入要取消的航班信息：航班名称、起飞时间、降落时间（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					String[] startTime = messages[1].split("-");
					String[] endTime = messages[2].split("-");
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
					fsa.cancelPlan(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2));
					System.out.println("当前该航班状态为："
							+ fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if (choose.equals("7")) {
					System.out.println("请输入要分配的航班名称、起飞时间、降落时间、飞机编号、飞机型号、座位数、机龄（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					String[] startTime = messages[1].split("-");
					String[] endTime = messages[2].split("-");
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
					if (fsa.distributeResource(messages[0],
							new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2), new Plane(messages[3],
									messages[4], Integer.valueOf(messages[5]), Float.valueOf(messages[6])))) {
						System.out.println("分配成功！");
					} else {
						System.out.println("分配失败！");
					}
				} else if (choose.equals("8")) {
					System.out.println("请输入要启动的航班信息：航班名称、起飞时间、降落时间（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					String[] startTime = messages[1].split("-");
					String[] endTime = messages[2].split("-");
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
					fsa.startPlan(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2));
					System.out.println("当前该航班状态为："
							+ fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if (choose.equals("9")) {
					System.out.println("请输入要结束的航班信息：航班名称、起飞时间、降落时间（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					String[] startTime = messages[1].split("-");
					String[] endTime = messages[2].split("-");
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
					fsa.endPlan(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2));
					System.out.println("当前该航班状态为："
							+ fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if (choose.equals("10")) {
					System.out.println("请输入要查看状态的航班信息：航班名称、起飞时间、降落时间（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					String[] startTime = messages[1].split("-");
					String[] endTime = messages[2].split("-");
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
					System.out.println(
							fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if (choose.equals("11")) {
					if (fsa.checkLocationAndResourseConflict()) {
						System.out.println("存在冲突！");
					} else {
						System.out.println("不存在冲突！");
					}

				} else if (choose.equals("12")) {
					System.out.println("请输入要查找的飞机资源信息：飞机编号、飞机型号、座位数、机龄（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Plane plane = new Plane(messages[0], messages[1], Integer.valueOf(messages[2]),
							Float.valueOf(messages[3]));
					System.out.println("占用" + plane.getPlaneID() + "的航班有：");
					for (PlanningEntry<Plane> plan : fsa.getAllPlanWithResource(plane)) {
						System.out.println(plan.getPlanningName() + "\t" + plan.getPresentStateName());
					}
					System.out.println("是否继续查找某一航班的前序计划项？（y\\n）");
					messageLine = in.nextLine();
					if (messageLine.toLowerCase().equals("y")) {
						PlanningEntryAPIs<Plane> help = new PlanningEntryAPIs<>();
						System.out.println("请输入查找前序计划项的航班信息：航班名称、起飞时间、降落时间（不同信息用空格隔开，时间格式为：yyyy-MM-dd-hh-mm）");
						messageLine = in.nextLine();
						messages = messageLine.split(" ");
						String[] startTime = messages[1].split("-");
						String[] endTime = messages[2].split("-");
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
						FlightEntry fe = fsa.getPlan(messages[0],
								new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2));
						FlightEntry fe2 = (FlightEntry) help.findPreEntryPerResource(plane, fe,
								fsa.getAllPlanWithResource(plane));
						if (fe2 != null) {
							System.out.println("前序计划项为：" + fe2.getPlanningName());
						} else {
							System.out.println("未找到前序计划项！");
						}

					}
				} else if (choose.equals("13")) {
					System.out.println("请输入要查看看板的位置：");
					fsa.viewBoardOfLoc(in.nextLine());
				} else if (choose.equals("14")) {
					fsa.help();
				} else if (choose.equals("15")) {
					fsa.searchLog(in);
				} else if (choose.equals("16")) {
					System.out.println("感谢您的使用！");
					in.close();
					logger.log(Level.INFO, "退出程序");
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
