public class StudyModules {

    public int shortestDuration(int m, int[][] dependencies, int[] duration) {

        if (m == 1) {
            return duration[0];
        }

        if (m == 2 && dependencies.length == 0) {
            return Math.max(duration[0], duration[1]);
        }

        Map<Integer, Integer> moduleDependencies = new HashMap<>();
        Map<Integer, List<Integer>> moduleMapping = new HashMap<>();
        Map<Integer, Integer> maxDuration = new HashMap<>();

        for (int i = 1; i <= m; i++) {
            moduleDependencies.put(i, 0);
            maxDuration.put(i, duration[i - 1]);
            moduleMapping.put(i, new ArrayList<>());
        }

        for (int[] dependency : dependencies) {
            int moduleA = dependency[0];
            int moduleB = dependency[1];

            moduleDependencies.computeIfPresent(moduleB, (k, val) -> val + 1);

            List<Integer> modulesList = moduleMapping.get(moduleA);
            modulesList.add(moduleB);
        }

        Queue<Integer> processingQueue = new LinkedList<>();

        for (int i = 1; i <= m; i++) {
            if (moduleDependencies.get(i) == 0) {
                processingQueue.offer(i);
            }
        }

        while (!processingQueue.isEmpty()) {
            int currentModule = processingQueue.poll();
            List<Integer> dependentModules = moduleMapping.get(currentModule);
            for (int dependentModule : dependentModules) {
                maxDuration.compute(dependentModule, (k, val) -> Math.max(maxDuration.get(currentModule) + duration[dependentModule - 1], val));
                moduleDependencies.compute(dependentModule, (k, val) -> val - 1);
                if (moduleDependencies.get(dependentModule) == 0) {
                    processingQueue.offer(dependentModule);
                }
            }
        }

        int maximumDuration = Integer.MIN_VALUE;
        for (Integer dur : maxDuration.values()) {
            maximumDuration = Math.max(maximumDuration, dur);
        }

        return maximumDuration;
    }
}
