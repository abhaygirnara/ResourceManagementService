package resource.management.service.configurations;

public class WorkerConfiguration {
    public int numMinThreads = 20;
    public int numMaxThreads = 50;
    public int idleTimeInSecs = 30;
    public int shutDownTimeInSecs = 30;
    public int queueCapacity = 10;
}
