package myMath;

import java.util.Scanner;
/**
 *scanner to polynom class
 * @author naomi&adi
 */
public class main {
		
		public static void main(String[] args) {

			Polynom_able p1;
			Polynom_able p2;
			System.out.println("Hi, Please choose 2 Polynoms:");
			Scanner sc = new Scanner(System.in);
			System.out.println("Polynom 1: ");
			String str1 = sc.nextLine();
			p1 = new Polynom(str1);
			System.out.println("Polynom 2: ");
			String str2 = sc.nextLine();
			p2 = new Polynom(str2);
			System.out.println("Choose what to do with p1 and p2?");
			String choice = "";
			while(!choice.equals("Exit")) {
				System.out.println("1 - add");
				System.out.println("2 - substract");
				System.out.println("3 - multiply");
				System.out.println("4 - isZero()");
				System.out.println("5 - root");
				System.out.println("6 - derivative()");
				System.out.println("7 - area");
				System.out.println("8 - draw a graph for polynom 1");
				System.out.println("Exit");
				System.out.println("Current P1: " + p1);
				System.out.println("Current P2: " + p2);
				choice = sc.nextLine();
				switch (choice) {
				case "1":
					System.out.println("Add");
					p1.add(p2);
					System.out.println("p1.add(p2):"+p1);
					System.out.println("");
					break;
				case "2":
					System.out.println("Substract");
					p1.substract(p2);
					System.out.println("p1.substract(p2):"+p1);
					System.out.println("");
					break;
				case "3":
					System.out.println("Multiply");
					p1.multiply(p2);
					System.out.println("p1.multiply(p2):"+p1);
					System.out.println("");
					break;

				case "4":
					System.out.println("isZero");
					System.out.println("p1.isZero:"+p1.isZero());
					System.out.println("");
					break;

				case "5":
					System.out.println("Root - Please choose 2 points for the range & eps");
					String range = sc.nextLine();
					String values[] = range.split(",");
					double[] value = {Double.parseDouble(values[0]),Double.parseDouble(values[1]),Double.parseDouble(values[2])};
					System.out.println("Root of p1:");
					System.out.println(p1.root(value[0], value[1], value[2]));
					System.out.println("");
					break;


				case "6":
					System.out.println("The derivative is:");
					System.out.println(p1.copy().derivative());
					System.out.println("");
					break;

				case "7":
					System.out.println("Area - Please choose 2 points for the range & eps:");
					String valuearea = sc.nextLine();
					String valuesArea[] = valuearea.split(",");
					double[] inputsarea = {Double.parseDouble(valuesArea[0]),Double.parseDouble(valuesArea[1]),Double.parseDouble(valuesArea[2])};
					System.out.println("Roots of p1:");
					System.out.println(p1.area(inputsarea[0], inputsarea[1], inputsarea[2]));
					System.out.println("");
					break;

				case "8":
					System.out.println("draw a praph - Please choose 2 points for the range:(write: num1,num2)");
					range = sc.nextLine();
					String arr[] = range.split(",");
					double[] values1 = {Double.parseDouble(arr[0]),Double.parseDouble(arr[1])};
					FunctionGraph frame = new FunctionGraph(p1,values1[0],values1[1]);
					frame.setVisible(true);
					break;

				case "Exit":
					choice = "Exit";
				default:
					break;
				}
			}
			System.out.println("Good Bye!");

			sc.close();
		}

	}


