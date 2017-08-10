package com.auto.testrun;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import com.restassured.steps.APICommonSteps;
import com.restassured.steps.APISteps;

public class RestAPIRunStories extends JUnitStories {

	 public RestAPIRunStories() {
		 configuredEmbedder()
	        .useMetaFilters(Arrays.asList("+Flow1"));
	        configuredEmbedder().embedderControls()
	        .doGenerateViewAfterStories(false)
	        .doIgnoreFailureInStories(false)
	       .useStoryTimeouts("30m")
	        .doVerboseFailures(true);// .useThreads(1);
	    }
	// Here we specify the configuration, starting from default
	// MostUsefulConfiguration, and changing only what xis needed
	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration()
		// where to find the stories				
				.useStoryControls(new StoryControls()
						.doResetStateBeforeStory(false)
						.doResetStateBeforeScenario(false)
						.useScenarioMetaPrefix("+Flow1")//Execution flow
						//.doSkipScenariosAfterFailure(true)//in case failure any steps it skips the run
						)
				.useStoryLoader(new LoadFromClasspath(this.getClass()))
				
				// CONSOLE and TXT reporting
				//.useStoryControls(new StoryControls().doIgnoreMetaFiltersIfGivenStory(true))
				.useStoryReporterBuilder(
						new StoryReporterBuilder().withDefaultFormats()
								.withFormats(Format.CONSOLE, Format.TXT)
								.withCodeLocation(CodeLocations.codeLocationFromPath("/build/jbehave")));
								//.withRelativeDirectory("/build/jbehave"));
	}
	 @Override
	 public InjectableStepsFactory stepsFactory() {
	      return new InstanceStepsFactory(configuration(),new APICommonSteps(), new APISteps());
	  }
	  
	 @Override
		protected List<String> storyPaths() {
			return new StoryFinder().findPaths(CodeLocations.codeLocationFromPath("src/test/resources"), 
					"**/RESTAPI*.story",
					"**/UI*.story");
		}
	    
}