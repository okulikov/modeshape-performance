/*
 * JBoss, Home of Professional Open Source
 * Copyright [2011], Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.modeshape;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.modeshape.jcr.JcrRepositoryFactory;
import org.modeshape.jcr.perftests.RunnerCfg;
import org.modeshape.jcr.perftests.SuiteRunner;
import org.modeshape.jcr.perftests.read.ConcurrentReadTestSuite;
import org.modeshape.jcr.perftests.report.TextFileReport;
import org.modeshape.jcr.perftests.write.ConcurrentReadWriteTestSuite;

/**
 * Runs the performance tests against a Modeshape 2.x repo.
 * 
 * @author Horia Chiorean
 */
public class ModeShape27PerformanceTest {

    private RunnerCfg runnerConfig;

    @Before
    public void before() {
        // TODO author=Horia Chiorean date=11/22/11 description=some tests excluded because of various problems
        runnerConfig = new RunnerCfg().addTestsToExclude(ConcurrentReadTestSuite.class.getSimpleName(),// deadlock
                                                         ConcurrentReadWriteTestSuite.class.getSimpleName());// deadlock
    }

    @Test
    public void testModeShapeInMemory() throws Exception {
        SuiteRunner performanceTestSuiteRunner = new SuiteRunner("ModeShape 2.7.0.Final InMemory", runnerConfig);
        Map<String, URL> parameters = new HashMap<String, URL>();
        parameters.put(JcrRepositoryFactory.URL, getClass().getClassLoader().getResource("configRepository.xml"));
        performanceTestSuiteRunner.runPerformanceTests(parameters, null);

        new TextFileReport(TimeUnit.SECONDS).generateReport(performanceTestSuiteRunner.getTestData());
    }

}
