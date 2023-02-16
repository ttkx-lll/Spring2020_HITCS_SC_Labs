package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.FlightScheduleApp;
import applicationEntry.FlightEntry;
import common.element.Location;
import common.element.Timeslot;
import common.operation.PlanningEntry;
import common.operation.PlanningEntryAPIs;
import source.Plane;

/**
 * 航班计划项app
 * 
 * @author 梁书育
 *
 */
public class FlightScheduleApp {

	private boolean illegal = false;
	private FlightScheduleOperation fso;
	
	/**
	 * 从文件读入
	 * @param fileName
	 */
	public FlightScheduleApp(String fileName) {
//		fileName e.g. "FlightSchedule_5"
		this.fso = generateFlightApp(fileName);
		if(fso == null) {
			this.illegal = true;
		}
	}
	
	public FlightScheduleApp() {
		this.fso = new FlightScheduleOperation();
	}
	
	/**
	 * 从文件中解析航班计划信息。
	 * 
	 * @param fileName 文件名（不带后缀）
	 * @return 航班计划项操作
	 */
	private FlightScheduleOperation generateFlightApp(String fileName) {
		
		FlightScheduleOperation appOperation = new FlightScheduleOperation();
		String filename = "src/data/txt/" + fileName + ".txt";
		Pattern flightNamePattern = Pattern.compile("^Flight:(\\d{4})-(\\d{2})-(\\d{2}),([A-Z]{2}\\d{2,4})$");
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
		boolean illeagal = false;
		int number = 0;
		
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(
									new File(filename)), "UTF-8"));
			String lineTxt;
			
			int lineNum = 0;
			
			while((lineTxt = br.readLine()) != null) {
				
				if(lineTxt.length() == 0) {
					continue;
				}
				
				lineNum = lineNum % 13;
				
				switch(lineNum) {
					case 0:
						matcher = flightNamePattern.matcher(lineTxt);
						if(matcher.find() && matcher.groupCount() == 4) {
							int year = Integer.valueOf(matcher.group(1));
							int month = Integer.valueOf(matcher.group(2));
							int dayOfMonth = Integer.valueOf(matcher.group(3));
							presentDate = LocalDate.of(year, month, dayOfMonth);
							flightName = matcher.group(4);
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 2:
						matcher = departureAirportPattern.matcher(lineTxt);
						if(matcher.find()) {
							departureAirport = new Location(matcher.group(1), true);
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 3:
						matcher = arrivalAirportPattern.matcher(lineTxt);
						if(matcher.find()) {
							arrivalAirport = new Location(matcher.group(1), true);
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 4:
						matcher = departureTimePattern.matcher(lineTxt);
						if(matcher.find() && matcher.groupCount() == 5) {
							int year = Integer.valueOf(matcher.group(1));
							int month = Integer.valueOf(matcher.group(2));
							int dayOfMonth = Integer.valueOf(matcher.group(3));
							int hour = Integer.valueOf(matcher.group(4));
							int minute = Integer.valueOf(matcher.group(5));
							departureTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
							if(!departureTime.toLocalDate().equals(presentDate)) {
								illeagal = true;
								number++;
							}
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 5:
						matcher = arrivalTimePattern.matcher(lineTxt);
						if(matcher.find() && matcher.groupCount() == 5) {
							int year = Integer.valueOf(matcher.group(1));
							int month = Integer.valueOf(matcher.group(2));
							int dayOfMonth = Integer.valueOf(matcher.group(3));
							int hour = Integer.valueOf(matcher.group(4));
							int minute = Integer.valueOf(matcher.group(5));
							arrivalTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
							Period period;
							period = Period.between(arrivalTime.toLocalDate(), presentDate);
							if(!arrivalTime.toLocalDate().equals(presentDate) && Math.abs(period.getDays()) > 1) {
								illeagal = true;
								number++;
							}
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 6:
						matcher = planeIDPattern.matcher(lineTxt);
						if(matcher.find()) {
							planeID = matcher.group(1);
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 8:
						matcher = planeTypePattern.matcher(lineTxt);
						if(matcher.find()) {
							planeType = matcher.group(1);
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 9:
						matcher = planeSeatsPattern.matcher(lineTxt);
						if(matcher.find()) {
							planeSeats = Integer.valueOf(matcher.group(1));
						} else {
							illeagal = true;
							number++;
						}
						break;
						
					case 10:
						matcher = planeAgePattern.matcher(lineTxt);
						if(matcher.find()) {
							planeAge = Float.valueOf(matcher.group(1));
						} else {
							illeagal = true;
							number++;
						}
						break;
						
				}
				
				if(lineNum == 12) {
					number++;
					appOperation.addLocation(departureAirport);
					appOperation.addLocation(arrivalAirport);
					timeslot = new Timeslot(departureTime, arrivalTime);
					illeagal = appOperation.checkIlleagalPlan(flightName, departureAirport, arrivalAirport, timeslot);
					appOperation.addPlan(flightName, departureAirport, arrivalAirport, timeslot);
					plane = new Plane(planeID, planeType, planeSeats, planeAge);
					appOperation.addResource(plane);
					appOperation.distributeResource(flightName, timeslot, plane);
				}
				
				if(illeagal) {
					System.out.println("文件非法 !" + lineNum);
					System.out.println("在读入第" + number + "组数据时发生错误！");
					br.close();
					return null;
				}

				lineNum++;
			}
			
			br.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("没有该文件，重新输入文件名！");
			return null;
		} catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("共读入" + number + "个计划项！");
		return appOperation;
		
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
		return fso.addResource(resource);
	}
	
	/**
	 * 删除资源。
	 * 
	 * @param resource 删除要添加的资源
	 */
	public boolean delResource(Plane resource) {
		return fso.delResource(resource);
	}
	
	/**
	 * 添加位置。
	 * 
	 * @param loc 要添加的位置
	 */
	public boolean addLocation(Location loc) {
		return fso.addLocation(loc);
	}
	
	/**
	 * 删除位置。
	 * 
	 * @param loc 要删除的位置
	 */
	public boolean delLocation(Location loc) {
		return fso.delLocation(loc);
	}
	
	/**
	 * 添加计划项。
	 * 
	 * @param planName 计划项名称
	 * @param start 起始位置
	 * @param end 终止位置
	 * @param timeslot 起止时间
	 */
	public boolean addPlan(String planName, Location start, Location end, Timeslot timeslot) {
		return fso.addPlan(planName, start, end, timeslot);
	}
	
	/**
	 * 取消名称为planName的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void cancelPlan(String planName, Timeslot timeslot) {
		fso.cancelPlan(planName, timeslot);
	}
	
	/**
	 * 为名称为planName，起止时间为timeslot的计划项分配资源。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @param resource 要添加的资源
	 */
	public boolean distributeResource(String planName, Timeslot timeslot, Plane resource) {
		return fso.distributeResource(planName, timeslot, resource);
	}
	
	/**
	 * 启动名称为planName，起止时间为timeslot的计划项。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 */
	public void startPlan(String planName, Timeslot timeslot) {
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
	}
	
	/**
	 * 获得名称为planName，起止时间为timeslot的计划项的状态。
	 * 
	 * @param planName 计划项名称
	 * @param timeslot 起止时间
	 * @return 如存在该计划项则返回其状态，否则返回{@code null}
	 */
	public String getState(String planName, Timeslot timeslot) {
		return fso.getState(planName, timeslot);
	}
	
	/**
	 * 检测是否有资源和位置的冲突。
	 * 
	 * @return {@code true}如果有资源或位置冲突；{@code false}如果没有资源或位置的冲突。
	 */
	public boolean checkLocationAndResourseConflict() {
		return fso.checkLocationAndResourseConflict();
	}
	
	/**
	 * 获得所有用到某一特定资源的计划项列表。
	 * 
	 * @param resource 选定的资源
	 * @return 使用这一特定资源的计划项列表
	 */
	public List<PlanningEntry<Plane>> getAllPlanWithResource(Plane resource) {
		return fso.getAllPlanWithResource(resource);
	}
	
	/**
	 * 查看位置{@code locName}的看板。
	 * 
	 * @param locName 位置名称
	 */
	public void viewBoardOfLoc(String locName) {
		fso.viewBoardOfLoc(locName);
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
		System.out.println("  15、退出");
	}
	
	public static void main(String[] args) {
		
		String choose;
		Scanner in = new Scanner(System.in);
		FlightScheduleApp fsa;

		System.out.println("==========================");
		System.out.println(" 欢迎来到航班计划项模拟app！");
		System.out.println("==========================\n");
		
		while(true) {
			
			System.out.println("请选择数据来源：");
			System.out.println("  1、手动输入");
			System.out.println("  2、文件读入");
			
			choose = in.nextLine();
			
			if(choose.equals("1")) {
				fsa = new FlightScheduleApp();
				break;
				
			} else if(choose.equals("2")) {
				while(true) {
					System.out.println("请输入文件名：");
					String filename = in.nextLine();
					System.out.println("文件读入中，请稍候……");
					fsa = new FlightScheduleApp(filename);
					if(!fsa.illegal) {
						System.out.println("文件读入成功！");
						break;
					}
				}
				break;
				
			} else {
				System.out.println("输入不正确，重新输入！");
			}
		}
		
		fsa.help();
		
		while(true) {
			System.out.println("\n请输入您的选择：");
			choose = in.nextLine();
			try {
				if(choose.equals("1")) {
					System.out.println("请输入要添加的飞机资源信息：飞机编号、飞机型号、座位数、机龄（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Plane plane = new Plane(messages[0], messages[1], Integer.valueOf(messages[2]), Float.valueOf(messages[3]));
					if(fsa.addResource(plane)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("2")) {
					System.out.println("请输入要删除的飞机资源信息：飞机编号、飞机型号、座位数、机龄（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Plane plane = new Plane(messages[0], messages[1], Integer.valueOf(messages[2]), Float.valueOf(messages[3]));
					if(fsa.delResource(plane)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if(choose.equals("3")) {
					System.out.println("请输入要添加的位置名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, true);
					if(fsa.addLocation(location)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("4")) {
					System.out.println("请输入要删除的位置名称：");
					String messageLine = in.nextLine();
					Location location = new Location(messageLine, true);
					if(fsa.delLocation(location)) {
						System.out.println("删除成功！");
					} else {
						System.out.println("删除失败！");
					}
				} else if(choose.equals("5")) {
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
					if(fsa.addPlan(messages[0], new Location(messages[1], true), new Location(messages[2], true), 
							new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2))) {
						System.out.println("添加成功！");
					} else {
						System.out.println("添加失败！");
					}
				} else if(choose.equals("6")) {
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
					System.out.println("当前该航班状态为：" + fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if(choose.equals("7")) {
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
					if(fsa.distributeResource(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2), 
							new Plane(messages[3], messages[4], Integer.valueOf(messages[5]), Float.valueOf(messages[6])))) {
						System.out.println("分配成功！");
					} else {
						System.out.println("分配失败！");
					}
				} else if(choose.equals("8")) {
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
					System.out.println("当前该航班状态为：" + fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if(choose.equals("9")) {
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
					System.out.println("当前该航班状态为：" + fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if(choose.equals("10")) {
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
					System.out.println(fsa.getState(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2)));
				} else if(choose.equals("11")) {
					if(fsa.checkLocationAndResourseConflict()) {
						System.out.println("存在冲突！");
					} else {
						System.out.println("不存在冲突！");
					}
					
				} else if(choose.equals("12")) {
					System.out.println("请输入要查找的飞机资源信息：飞机编号、飞机型号、座位数、机龄（不同信息用空格分开）");
					String messageLine = in.nextLine();
					String[] messages = messageLine.split(" ");
					Plane plane = new Plane(messages[0], messages[1], Integer.valueOf(messages[2]), Float.valueOf(messages[3]));
					System.out.println("占用" + plane.getPlaneID() + "的航班有：");
					for(PlanningEntry<Plane> plan : fsa.getAllPlanWithResource(plane)) {
						System.out.println(plan.getPlanningName() + "\t" + plan.getPresentStateName());
					}
					System.out.println("是否继续查找某一航班的前序计划项？（y\\n）");
					messageLine = in.nextLine();
					if(messageLine.toLowerCase().equals("y")) {
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
						FlightEntry fe = fsa.getPlan(messages[0], new Timeslot(y1, mon1, d1, h1, min1, y2, mon2, d2, h2, min2));
						FlightEntry fe2 = (FlightEntry) help.findPreEntryPerResource(plane, fe, fsa.getAllPlanWithResource(plane));
						if(fe2 != null) {
							System.out.println("前序计划项为：" + fe2.getPlanningName());
						} else {
							System.out.println("未找到前序计划项！");
						}
						
					}
				} else if(choose.equals("13")) {
					System.out.println("请输入要查看看板的位置：");
					fsa.viewBoardOfLoc(in.nextLine());
				} else if(choose.equals("14")) {
					fsa.help();
				} else if(choose.equals("15")) {
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
