package P1;

import java.io.*;

public class MagicSquare {

	

	boolean isLegalMagicSquare(String fileName) {
		
		int i, j;
		
		String[] rows = new String[1000];
		String[][] magics = new String[1000][];
		int rowNumber = 0;
		int colNumber = 0;
		int[] colNumbers = new int[1000];
				
		try {
			String filename = "src/P1/txt/" + fileName;
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(
									new File(filename)), "UTF-8"));
			String lineTxt = null;
			i = 0;
			while((lineTxt = br.readLine()) != null) {
				rows[i++] = lineTxt;
			}
			rowNumber = i;
			br.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println(rowNumber);
//		for(i = 0; i < rowNumber; i++) {
//			System.out.println(rows[i]);
//		}
		
		for(i = 0; i < rowNumber; i++) {
			magics[i] = rows[i].split("\t");
			colNumbers[i] = magics[i].length;
		}
		
//		System.out.println(rowNumber);
//		for(i = 0; i < rowNumber; i++)
//			System.out.println(colNumbers[i]);
		
		
		colNumber = colNumbers[0];
		
		for(i = 1; i < rowNumber; i++) {
			if(colNumbers[i] == 1) {
				System.out.println("该矩阵并非由\t分割开!");
				return false;
			}
			if(colNumber != colNumbers[i]) {
				System.out.println("文件数据不是矩阵!");
				return false;
			}
		}
		
		if(colNumber!=rowNumber) {
			System.out.println("矩阵非方阵(行列数不相等)!");
			return false;
		}
		
		int rowValue[] = new int[rowNumber];
		int colValue[] = new int[colNumber];
		int diagValue[] = new int[2];
		
		for(i = 0; i < rowNumber; i++) {
			for(j = 0; j < colNumber; j++) {
				
				int value;
				
				try {
					value = Integer.valueOf(magics[i][j]);
				}
				catch(NumberFormatException e) {
					System.out.println("存在非法输入!");
					return false;
				}
				
				if(value < 0) {
					System.out.println("存在非正整数!");
					return false;
				}
				
				rowValue[i] += value;
				colValue[j] += value;
				if(i == j) diagValue[0] += value;
				if(i + j + 1 == colNumber) diagValue[1] += value;
			}
		}
		
		int value = rowValue[0];
		for(i = 0; i < rowNumber; i++) 
			if(rowValue[i] != value || colValue[i] != value) {
				System.out.println("该方阵不是MagicSquare!");
				return false;
			}
		for(i = 0; i < 2; i++)
			if(diagValue[i] != value) {
				System.out.println("该方阵不是MagicSquare!");
				return false;
			}
		return true;
	}
	
	public static boolean generateMagicSquare(int n) {
			
		int magic[][];
		int i, j;
		
		try {
			magic = new int[n][n];
			int row = 0, col = n / 2, square = n * n;	//计算填写幻方的起始位置以及幻方中的最大数
		
			for (i = 1; i <= square; i++) {
				magic[row][col] = i;					//将合适的数放到正确的行和列对应的位置上
				if (i % n == 0)							//如果要填的数为n的整数倍，则下一个位置列数不变行数加一
					row++;
				else {									//否则
					if (row == 0)						//如果行数为0，则将行坐标移到最下面一行
						row = n - 1;
					else								//行数不为0，则行坐标向上一行
						row--;
					if (col == (n - 1))					//如果列坐标为最右一列，则将列坐标移到第一列
						col = 0;
					else								//列坐标不是最右一列，则将列坐标向右一列
						col++;
				}
			}
			
		}
		catch(NegativeArraySizeException e) {			//对抛出的异常进行处理
			System.out.println("输入的n应为正整数!");
			return false;
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("输入的n应为奇数!");
			return false;
		}
		
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(
									new File("src/P1/txt/6.txt")),"UTF-8"));
			
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++)
					bw.write(magic[i][j] + "\t");
				bw.write("\n");
			}
			
			bw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		MagicSquare ms = new MagicSquare();
		for(int i = 1; i <= 6; i++) {
			System.out.println("src/P1/txt/"+ i +".txt:");
			if(ms.isLegalMagicSquare(String.valueOf(i) + ".txt")) {
				System.out.println("该方阵是一个MagicSquare!");
			} else {
//				System.out.println("false");
			}
			System.out.println("");
		}
		MagicSquare.generateMagicSquare(9);
	}
}
