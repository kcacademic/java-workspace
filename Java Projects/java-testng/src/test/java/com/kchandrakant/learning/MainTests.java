package com.kchandrakant.learning;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.kchandrakant.learning.Main;

public class MainTests {

	@Mock
	private Main mainMock;

	@Spy
	private Main mainSpy;

	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMainMock() {

		int actual = mainMock.getThreeDigitNumber();
		Assert.assertEquals(actual, 0);
	}

	@Test
	public void testMainMockWithStub() {

		int expected = 100;
		Mockito.when(mainMock.getThreeDigitNumber()).thenReturn(expected);

		int actual = mainMock.getThreeDigitNumber();
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void testMainSpy() {

		int actual = mainSpy.getThreeDigitNumber();
		Assert.assertNotNull(actual);
	}

	@Test
	public void testMainSpyWithStub() {

		int expected = 100;

		Mockito.doReturn(expected).when(mainSpy).getThreeDigitNumber();

		int actual = mainSpy.getThreeDigitNumber();
		Assert.assertNotNull(actual);
	}

}
