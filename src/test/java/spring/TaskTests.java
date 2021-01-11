package spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskTests {
	
	public static interface FakeRepo {
		public int getGeneratedInt();
	}
	
	@Mock
	private FakeRepo fakeRepo;
	private Task task;
	
	@Before
	public void initializeTask() {
		this.task = new Task();
	}
	
	@Test
	public void mkStringFromIntegerWithIntegerGeneratedFromRepoShouldReturnIntegerAsString() {
		when(fakeRepo.getGeneratedInt()).thenReturn(42);
		assertEquals("42", this.task.mkStringFromInteger(fakeRepo.getGeneratedInt()));
	}
}

class Task {
	
	public String mkStringFromInteger(int integer) {
		return String.valueOf(integer);
	}
}