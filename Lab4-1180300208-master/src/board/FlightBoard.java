package board;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import applicationEntry.FlightEntry;
import common.element.Location;

/**
 * FlightEntry计划项看板。
 * 
 * @author 梁书育
 */
public class FlightBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Data<FlightEntry> flightdata;
	private LocalDateTime presentTime;
	private Location presentLoc;
	
	private String[][] data1;
	private String[][] data2;
	private String[] head1;
	private String[] head2;
	
	private DefaultTableModel model1;
	private DefaultTableModel model2;
	private JTable table1;
	private JTable table2;
	private JScrollPane panel1;
	private JScrollPane panel2;
	private JLabel totalLabel;
	private JLabel label1;
	private JLabel label2;
	private JPanel totalPanel;
	private JPanel arrivePanel;
	private JPanel leavePanel;
	
	// Abstraction function:
	// 	  航班计划项看板
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    没有对外暴露数据
	
	/**
	 * 通过航班数据以及位置创建看板。
	 * 
	 * @param flightdata 航班计划项列表
	 * @param presentLoc 当前位置
	 */
	public FlightBoard(Data<FlightEntry> flightdata, Location presentLoc) {
		
		this.flightdata = flightdata;
		this.presentLoc = presentLoc;
		this.presentTime = LocalDateTime.now();
		
		data1 = new String[0][4];
		data2 = new String[0][4];
		head1 = new String[]{"落地时间", "航班", "起始地-目的地", "状态"};
		head2 = new String[]{"起飞时间", "航班", "起始地-目的地", "状态"};
		
		model1 = new DefaultTableModel(data1, head1);
		model2 = new DefaultTableModel(data2, head2);
		table1 = new JTable(model1);
		table2 = new JTable(model2);
		
		panel1 = new JScrollPane(table1) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(450, 200);
			}
		};
		
		panel2 = new JScrollPane(table2) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(450, 200);
			}
		};
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		totalLabel = new JLabel(df.format(presentTime) + "(当前时间)  "
								+ presentLoc.getLocName() + "机场");
		label1 = new JLabel("抵达航班");
		label2 = new JLabel("出发航班");
		
		totalPanel = new JPanel();
		arrivePanel = new JPanel();
		leavePanel = new JPanel();
	}
	
	/**
	 * 设置总体框架
	 */
	public void setFrame() {

		this.setLayout(new FlowLayout());
		setTitle("Flight Board");
		setBounds(520, 200, 500, 520);
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(totalPanel);
		add(arrivePanel);
		add(leavePanel);
	}
	
	/**
	 * 设置面板
	 */
	public void setPanel() {
		
		totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
		arrivePanel.setLayout(new BoxLayout(arrivePanel, BoxLayout.Y_AXIS));
		leavePanel.setLayout(new BoxLayout(leavePanel, BoxLayout.Y_AXIS));
		
		totalPanel.add(totalLabel);
		arrivePanel.add(Box.createHorizontalStrut(83));
		arrivePanel.add(label1);
		arrivePanel.add(panel1);
		leavePanel.add(Box.createHorizontalStrut(83));
		leavePanel.add(label2);
		leavePanel.add(panel2);
	}
	
	/**
	 * 设置表格格式
	 */
	public void setTableFormat() {
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table1.setDefaultRenderer(Object.class, tcr);
		table2.setDefaultRenderer(Object.class, tcr);
	}
	
	/**
	 * 通过时间更新各计划项
	 */
	public void updateState() {
		for(FlightEntry fe : flightdata) {
			if(fe.getStartTime().isAfter(presentTime)) { }
			else {
				fe.start();
				if(fe.getEndTime().isBefore(presentTime)) {
					fe.end();
				}
			}
		}
	}
	
	/**
	 * 将数据整合进表格
	 */
	public void generateDataToTable() {
		
		updateState();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
		Duration duration;
		
		for(FlightEntry fe : flightdata) {
			
			if(fe.getEndLoc().equals(presentLoc)) {
				duration = Duration.between(fe.getEndTime(), presentTime);
				if(Math.abs(duration.toHours()) < 1) {
					Vector<String> row = new Vector<String>();
					row.add(df.format(fe.getEndTime()));
					row.add(fe.getPlanningName());
					row.add(fe.getStartLoc().getLocName() + "-" + fe.getEndLoc().getLocName());
					row.add(fe.getPresentStateName());
					model1.addRow(row);
				}
				
			}
			if(fe.getStartLoc().equals(presentLoc)) {
				duration = Duration.between(fe.getStartTime(), presentTime);
				if(Math.abs(duration.toHours()) < 1) {
					Vector<String> row = new Vector<String>();
					row.add(df.format(fe.getStartTime()));
					row.add(fe.getPlanningName());
					row.add(fe.getStartLoc().getLocName() + "-" + fe.getEndLoc().getLocName());
					row.add(fe.getPresentStateName());
					model2.addRow(row);
				}
				
			}
			
		}
	}
	
	/**
	 * 显示表格
	 */
	public void visualize() {
		setFrame();
		setPanel();
		setTableFormat();
		generateDataToTable();
	}

}
