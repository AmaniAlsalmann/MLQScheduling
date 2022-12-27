import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MLQScheduling {

	private static Scanner scr = null;
	private static PrintStream out1 = null;
	private static PrintStream out2 = null;

	private static ArrayList<PCB> processes = new ArrayList<PCB>();
	private static ArrayList<PCB> pQ1 = new ArrayList<PCB>();
	private static ArrayList<PCB> pQ2 = new ArrayList<PCB>();

	private static Scheduling scheduling;

	public static void runScheduling() {
		for (PCB p : processes) {
			p.startTime = 0;
			p.terminateTime = 0;
			p.waitTime = 0;
			p.cpuTime = 0;
		}

		scheduling = new Scheduling(pQ1, pQ2);
		scheduling.run();
	}

	public static void report1() {
		runScheduling();
		printReport1(System.out);
		printReport1(out1);
	}

	public static void report2() {
		runScheduling();
		printReport2(System.out);
		printReport2(out2);
	}

	public static void printReport1(PrintStream out) {
		out.println("Report detailed information about each process.");
		out.println("---------------");
		for (PCB p : processes) {
			out.println(p);
			out.println("");
		}
		out.println("---------------");
		out.println("Process order chart: " + scheduling.getChartString());
		out.println("---------------");
	}

	public static void printReport2(PrintStream out) {
		out.println("Report2.");
		out.println("---------------");

		int size = processes.size();
		double totalTurnAround = 0;
		double totalWait = 0;
		double totalResponse = 0;

		for (PCB p : processes) {
			totalWait += p.waitTime;
			totalTurnAround += p.turnAroundTime;
			totalResponse += p.responseTime;
		}

		out.println("Average turnaround time : " + totalTurnAround / size);
		out.println("Average waiting time    : " + totalWait / size);
		out.println("Average response time   : " + totalResponse / size);

		out.println("---------------");
	}

	private static void initializePCBs() {
		
		processes = new ArrayList<PCB>();
		pQ1 = new ArrayList<PCB>();
		pQ2 = new ArrayList<PCB>();

		System.out.print("Enter the number of processes: ");
		int num = scr.nextInt();

		System.out
				.println("Enter Arrival time, CPU burst and priority for each process:");
		for (int i = 0; i < num; i++) {
			System.out.println("Process P" + i + ":");

			System.out.print("\t Arrival Time: ");
			int arrivalTime = scr.nextInt();

			System.out.print("\t CPU burst: ");
			int burstTime = scr.nextInt();

			System.out.print("\t Priority : ");
			int priority = scr.nextInt();
			while (priority < 1 || priority > 2) {
				System.out.println("priority must be in range [1 - 2]");
				System.out.print("Priority : ");
				priority = scr.nextInt();
			}

			PCB pcb = new PCB(i, arrivalTime, burstTime, priority);
			processes.add(pcb);

			if (priority == 1)
				pQ1.add(pcb);
			else
				pQ2.add(pcb);
		}
	}

	public static void main(String[] args) {
		try {
			scr = new Scanner(System.in);
			out1 = new PrintStream("Report1.txt");
			out2 = new PrintStream("Report2.txt");

			char ch;
			do {

				System.out.println("Multilevel Queue Scheduling");
				System.out.println("---------------");
				System.out.println("1. Enter process information.");
				System.out
						.println("2. Report detailed information about each process.");
				System.out
						.println("3. Report the average turnaround time, waiting time, and response time.");
				System.out.println("4. Exit Program");
				System.out.println("---------------");
				System.out.print("Enter your choice: ");

				ch = scr.next().charAt(0);

				System.out.println();

				switch (ch) {
				case '1':
					initializePCBs();
					break;
				case '2':
					report1();
					break;
				case '3':
					report2();
					break;
				case '4':
					break;
				default:
					System.out.println("invalid choice, Try again");
				}

				System.out.println();

			} while (ch != '4');

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (scr != null)
			scr.close();

		if (out1 != null)
			out1.close();

		if (out2 != null)
			out2.close();
	}

}
