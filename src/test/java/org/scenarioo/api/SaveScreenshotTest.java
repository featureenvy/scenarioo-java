package org.scenarioo.api;

import static org.scenarioo.api.TestConstants.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveScreenshotTest {

	private static final String pngFileName = "/scenarioo-icon-unittest.png";
	private static byte[] imageAsByteArray;

	private ScenarioDocuWriter writer;

	@BeforeClass
	public static void loadImage() {
		try {
			imageAsByteArray = FileUtils.readFileToByteArray(new File(SaveScreenshotTest.class.getResource(pngFileName)
					.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() {
		TEST_ROOT_DIRECTORY.mkdirs();
		writer = new ScenarioDocuWriter(TEST_ROOT_DIRECTORY, TEST_BRANCH_NAME, TEST_BUILD_NAME);
	}

	@Test
	public void savePngScreenshot() {
		writer.saveScreenshotAsPng(TEST_CASE_NAME, TEST_SCENARIO_NAME, TEST_STEP_INDEX, imageAsByteArray);
		writer.flush();

		assertStoredFileContentEqualsOriginalImage();
	}

	private void assertStoredFileContentEqualsOriginalImage() {
		File file = writer.getScreenshotFile(TEST_CASE_NAME, TEST_SCENARIO_NAME, TEST_STEP_INDEX);
		byte[] fileAsByteArray;

		try {
			fileAsByteArray = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			Assert.fail("Could not read file " + file);
			e.printStackTrace();
			return;
		}

		Assert.assertArrayEquals(imageAsByteArray, fileAsByteArray);
	}

}
