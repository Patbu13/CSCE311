import java.util.*;
public class RRScheduler extends BasicScheduler {

    private int quantum; // Sets quantuminterval for the scheduler
    private int currentPTick; // Keeps track of ticks the current process has had consecutively

    public RRScheduler(int RR) {
        this.readyQ = new LinkedList<BasicPCB>();
        quantum = RR;
    }

    public void dispatch()
    {
		if(readyQ.isEmpty())
		{
			runningProcess = null;
			return;
		}        
        
		runningProcess = readyQ.poll();
    }

    public void updateRunningProcess()
	{
        currentPTick++;
		if(runningProcess == null)
		{
            currentPTick = 0;
			dispatch();
			return;
		}
		runningProcess.nextLine();
		if(runningProcess.hasCompleted())
		{
			runningProcess.setCompletionTick(tickCount);
			waitingTimeSum += (runningProcess.getCompletionTick() - runningProcess.getArrivalTick());
			dispatch();
		}   else if (currentPTick == quantum) {
            addProcess(runningProcess);
            dispatch();
        }
	}

}
