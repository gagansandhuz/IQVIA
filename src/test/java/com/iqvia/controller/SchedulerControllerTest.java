package com.iqvia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.iqvia.model.JobStatus;
import com.iqvia.model.ScheduleData;
import com.iqvia.service.ScheduleDataService;
import com.iqvia.service.SchedulerService;

@RunWith(SpringJUnit4ClassRunner.class)
public class SchedulerControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private SchedulerController schedulerController;

	@Mock
	private ScheduleDataService scheduleDataService;

	@Mock
	private SchedulerService schedulerService;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(schedulerController).build();
	}

	@Test
	public void findAllMessagesTest() throws Exception {

		List<ScheduleData> scheduleDataList = new ArrayList<ScheduleData>();
		ScheduleData scheduleData = new ScheduleData("ff80808170a4a6e60170a4a76ae90000", new Date(), new Date(),
				"Message to print", JobStatus.CREATED.value);
		scheduleDataList.add(scheduleData);
		when(scheduleDataService.findAll()).thenReturn(scheduleDataList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/scheduler/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		verify(scheduleDataService).findAll();
	}

	@Test
	public void saveMessageTest() throws Exception {

		ScheduleData scheduleData = new ScheduleData("ff80808170a4a6e60170a4a76ae90000", new Date(), new Date(),
				"Message to print", JobStatus.CREATED.value);

		when(scheduleDataService.save(any(ScheduleData.class))).thenReturn(scheduleData);

		String jsonData = "{\"deliveryTime\":\"03-12-2020 04:31:38\", \"message\" : \"Message to print\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/scheduler/").contentType(MediaType.APPLICATION_JSON)
				.content(jsonData)).andExpect(status().isAccepted());

		verify(scheduleDataService).save(any(ScheduleData.class));
	}

}
