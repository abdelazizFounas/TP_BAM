package BAM.client;

public class Agent implements _Agent{
	
	private Route route;
	
	public Agent(){
	}

	protected _Action retour(){
		return null;
	}

	@Override
	public void run() {
		
	}

	@Override
	public void init(AgentServer agentServer, String serverName) {
		
	}

	@Override
	public void reInit(AgentServer server, String serverName) {
		
	}

	@Override
	public void addEtape(Etape etape) {
		route.add(etape);
	}

}
