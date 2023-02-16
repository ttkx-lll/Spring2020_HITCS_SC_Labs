package board;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import applicationEntry.CourseEntry;
import common.element.Location;

/**
 * CourseEntry计划项看板。
 * 
 * @author 梁书育
 */
public class CourseBoard extends JFrame {

	private static final long serialVersionUID = 1L;

	private Data<CourseEntry> coursedata;
	private LocalDateTime presentTime;
	private Location presentLoc;
	
	private String[][] data;
	private String[] head;
	
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPanel;
	private JLabel totalLabel;
	private JPanel totalPanel;
	private JPanel tablePanel;
	
	// Abstraction function:
	// 	  课程计划项看板
	
	// Representation invariant:
	//    无
	
	// Safety from rep exposure:
	//    没有对外暴露数据
	
	/**
	 * 通过课程数据以及位置创建看板。
	 * 
	 * @param coursedata 课程计划项列表
	 * @param presentLoc 当前位置
	 */
	public CourseBoard(Data<CourseEntry> coursedata, Location presentLoc) {
		
		this.coursedata = coursedata;
		this.presentLoc = presentLoc;
		this.presentTime = LocalDateTime.now();
		
		data = new String[0][4];
		head = new String[]{"上课时间", "课程", "教师", "状态"};
		
		model = new DefaultTableModel(data, head);
		table = new JTable(model);
		
		scrollPanel = new JScrollPane(table) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(450, 200);
			}
		};
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		totalLabel = new JLabel(df.format(presentTime) + ",  "
								+ presentLoc.getLocName() + "教室");
		
		totalPanel = new JPanel();
		tablePanel = new JPanel();
	}
	
	/**
	 * 设置总体框架
	 */
	public void setFrame() {
		
		this.setLayout(new FlowLayout());
		setTitle("Course Board");
		setBounds(520, 200, 500, 280);
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(totalPanel);
		add(tablePanel);
	}
	
	/**
	 * 设置面板
	 */
	public void setPanel() {
		
		totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		
		totalPanel.add(totalLabel);
		tablePanel.add(scrollPanel);
	}
	
	/**
	 * 设置表格格式
	 */
	public void setTableFormat() {
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
	}
	
	/**
	 * 通过时间更新各计划项状态
	 */
	public void updateState() {
		for(CourseEntry ce : coursedata) {
			if(ce.getStartTime().isAfter(presentTime)) { }
			else {
				ce.start();
				if(ce.getEndTime().isBefore(presentTime)) {
					ce.end();
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
		
		for(CourseEntry ce : coursedata) {
			
			if(ce.getLocation().equals(presentLoc)) {
				if(ce.getStartTime().toLocalDate().equals(presentTime.toLocalDate())) {
					Vector<String> row = new Vector<String>();
					row.add(df.format(ce.getStartTime()) + "-" + df.format(ce.getEndTime()));
					row.add(ce.getPlanningName());
					row.add(ce.getResource().getName());
					row.add(ce.getPresentStateName());
					model.addRow(row);
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
