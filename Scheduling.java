import java.util.ArrayList;

public class Scheduling {

	final public static int CPU_IDLE = 0;
	final public static int CPU_BUSY = 1;
	
	private int cpuStatus;
	private int cpuClock;
	private PCB runningProcess;

	private ArrayList<PCB> pQ1 = new ArrayList<PCB>();
	private ArrayList<PCB> pQ2 = new ArrayList<PCB>();
	private ArrayList<String> processChart = new ArrayList<String>();

	public Scheduling(ArrayList<PCB> pQ1, ArrayList<PCB> pQ2) {

		// Sort Q1 by burst time
		ArrayList<PCB> sorted = new ArrayList<PCB>();
		for (int i = 0; i < pQ1.size(); i++) {
			PCB p = pQ1.get(i);
			int j = 0;
			for (j = 0; j < sorted.size(); j++) {
				if (p.burstTime < sorted.get(j).burstTime)
					break;
			}
			sorted.add(j, p);
		}

		this.pQ1 = sorted;

		// Sort Q2 by arrival time
		ArrayList<PCB> sorted2 = new ArrayList<PCB>();
		for (int i = 0; i < pQ2.size(); i++) {
			PCB p = pQ2.get(i);
			int j = 0;
			for (j = 0; j < sorted2.size(); j++) {
				if (p.arrivalTime < sorted2.get(j).arrivalTime)
					break;
			}
			sorted2.add(j, p);
		}

		this.pQ2 = sorted2;
	}

	private int nextArrivedIndex(ArrayList<PCB> q, int time) {
		for (int i = 0; i < q.size(); i++) {
			if (q.get(i).arrivalTime <= time) {
				return i;
			}
		}
		return -1;
	}

	private PCB nextProcess(int time) {

		for (int i = 0; i < pQ1.size(); i++) {
			if (pQ1.get(i).arrivalTime <= time) {
				return pQ1.remove(i);
			}
		}

		for (int i = 0; i < pQ2.size(); i++) {
			if (pQ2.get(i).arrivalTime <= time) {
				return pQ2.remove(i);
			}
		}

		return null;
	}

	public void run() {
		cpuClock = 0;
		cpuStatus = CPU_IDLE;

		while (!pQ1.isEmpty() || !pQ2.isEmpty() || cpuStatus == CPU_BUSY) {

			if (cpuStatus == CPU_IDLE) {
				PCB next = nextProcess(cpuClock);
				if (next != null)
					cpuExcute(next);
			} else if (runningProcess.priority == 2 && !pQ1.isEmpty()) {
				int idx = nextArrivedIndex(pQ1, cpuClock);
				if (idx >= 0) {
					int i = 0;
					while (i < pQ2.size() && pQ2.get(i).arrivalTime <= cpuClock)
						i++;

					pQ2.add(i, runningProcess);
					cpuExcute(pQ1.remove(idx));
				}
			}

			cpuClock++;

			if (cpuStatus == CPU_BUSY) {
				runningProcess.cpuTime++;
				if (runningProcess.cpuTime == runningProcess.burstTime) {
					cpuTerminate(runningProcess);
				}
			}
		}
	}

	private void cpuExcute(PCB p) {
		if (p.cpuTime == 0)
			p.startTime = this.cpuClock;

		processChart.add("P" + p.id);
		
		cpuStatus = CPU_BUSY;
		runningProcess = p;
	}

	private void cpuTerminate(PCB p) {
		p.terminateTime = cpuClock;
		p.waitTime = p.terminateTime - p.arrivalTime - p.burstTime;
		p.responseTime = p.startTime - p.arrivalTime;
		p.turnAroundTime = p.terminateTime - p.arrivalTime;
		
		cpuStatus = CPU_IDLE;
		runningProcess = null;
	}

	public String getChartString() {
		String chart = "[";
		
		for (String p : processChart) {
			chart += " " + p + " |";
		}

		if (chart.length() > 1)
			chart = chart.substring(0, chart.length() - 1);
		
		chart += "]";
		
		return chart;
	}

}
