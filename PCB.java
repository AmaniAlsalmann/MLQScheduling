public class PCB {

	public int id;
	public int priority;
	public int arrivalTime;
	public int burstTime;
	public int startTime;
	public int terminateTime;
	public int turnAroundTime;
	public int waitTime;
	public int responseTime;
	public int cpuTime;

	public PCB(int id, int arrivalTime, int burstTime, int priority) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
		this.waitTime = 0;
		this.startTime = 0;
		this.terminateTime = 0;
		this.turnAroundTime = 0;
		this.responseTime = 0;
		this.cpuTime = 0;
	}

	@Override
	public String toString() {
		return "Process ID: P" + id + "\n" + "Priority: " + priority + "\n"
				+ "Burst Time: " + burstTime + "\n" + "Arrival Time: "
				+ arrivalTime + "\n" + "Start Time: " + startTime + "\n"
				+ "Termination Time: " + terminateTime + "\n"
				+ "Turn around Time: " + turnAroundTime + "\n"
				+ "Waiting Time: " + waitTime + "\n" + "Response Time: "
				+ responseTime;
	}
}