package com.zfgc.zfgbb.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import com.zfgc.zfgbb.mappers.UserAwardDboMapper;
import com.zfgc.zfgbb.mappers.UserBioInfoDboMapper;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class ProfileTest extends PostgresIntegrationTest {

	@Autowired
	private UserBioInfoDboMapper userBioInfoDboMapper;

	@Test
	void profileOwnerCanSaveTheirOwnProfile() throws Exception {
		String ownerName = "psave_owner_" + suffix;
		register(ownerName, "password123");
		String ownerToken = login(ownerName, "password123").get("accessToken").asString();
		int ownerId = userIdOf(ownerName);

		mockMvc.perform(put("/users/" + ownerId)
				.header("Authorization", "Bearer " + ownerToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"location\": \"Hyrule Field\"}"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void aDifferentNonAdminUserCannotSaveAnotherProfile() throws Exception {
		String ownerName = "psave_target_" + suffix;
		register(ownerName, "password123");
		int ownerId = userIdOf(ownerName);

		String intruderName = "psave_intruder_" + suffix;
		register(intruderName, "password123");
		String intruderToken = login(intruderName, "password123").get("accessToken").asString();

		mockMvc.perform(put("/users/" + ownerId)
				.header("Authorization", "Bearer " + intruderToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"location\": \"Gerudo Valley\"}"))
				.andExpect(status().isForbidden());
	}

	@Test
	void aSiteAdminCanSaveAnyProfileThroughTheProfileAdminRole() throws Exception {
		String ownerName = "psave_admin_target_" + suffix;
		register(ownerName, "password123");
		int ownerId = userIdOf(ownerName);

		String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

		mockMvc.perform(put("/users/" + ownerId)
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"location\": \"Death Mountain\"}"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void aPartialSettingsSaveKeepsTheFieldsItDidNotSend() throws Exception {
		String ownerName = "ppart_" + suffix;
		register(ownerName, "password123");
		String ownerToken = login(ownerName, "password123").get("accessToken").asString();
		int ownerId = userIdOf(ownerName);

		mockMvc.perform(post("/users/" + ownerId + "/settings")
				.header("Authorization", "Bearer " + ownerToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"theme": "GORON", "smileySet": "CLASSIC", "notifyAnnouncementsFlag": true,
						 "notifySendBodyFlag": true, "sendHappyBirthdayFlag": true}
						"""))
				.andExpect(status().isOk());

		mockMvc.perform(post("/users/" + ownerId + "/settings")
				.header("Authorization", "Bearer " + ownerToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"theme\": \"SHEIK\", \"smileySet\": \"NONE\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.theme").value("SHEIK"))
				.andExpect(jsonPath("$.smileySet").value("NONE"))
				.andExpect(jsonPath("$.notifyAnnouncementsFlag").value(true))
				.andExpect(jsonPath("$.notifySendBodyFlag").value(true))
				.andExpect(jsonPath("$.sendHappyBirthdayFlag").value(true));

		mockMvc.perform(post("/users/" + ownerId + "/settings")
				.header("Authorization", "Bearer " + ownerToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"notifySendBodyFlag\": false}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.notifySendBodyFlag").value(false))
				.andExpect(jsonPath("$.theme").value("SHEIK"))
				.andExpect(jsonPath("$.smileySet").value("NONE"));
	}

	@Test
	void settingsSaveRoundTrip() throws Exception {
		String ownerName = "pset_" + suffix;
		register(ownerName, "password123");
		String ownerToken = login(ownerName, "password123").get("accessToken").asString();
		int ownerId = userIdOf(ownerName);

		String settingsPayload = """
				{"theme": "GORON", "smileySet": "CLASSIC", "notifyAnnouncementsFlag": true,
				 "notifySendBodyFlag": false, "sendHappyBirthdayFlag": true}
				""";
		mockMvc.perform(post("/users/" + ownerId + "/settings")
				.header("Authorization", "Bearer " + ownerToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(settingsPayload))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.theme").value("GORON"))
				.andExpect(jsonPath("$.smileySet").value("CLASSIC"))
				.andExpect(jsonPath("$.notifyAnnouncementsFlag").value(true))
				.andExpect(jsonPath("$.notifySendBodyFlag").value(false))
				.andExpect(jsonPath("$.sendHappyBirthdayFlag").value(true));

		mockMvc.perform(get("/users/" + ownerId)
				.header("Authorization", "Bearer " + ownerToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.settings.theme").value("GORON"))
				.andExpect(jsonPath("$.settings.smileySet").value("CLASSIC"));

		MvcResult anonymousResult = mockMvc.perform(get("/users/" + ownerId))
				.andExpect(status().isOk())
				.andReturn();
		JsonNode anonymousSettings = json.readTree(anonymousResult.getResponse().getContentAsString())
				.get("settings");
		assertTrue(anonymousSettings == null || anonymousSettings.isNull(),
				"settings are private and must never leak on the public profile view");
	}

	@Test
	void bioPatchRoundTripSkipsAbsentFieldsAndStampsDbClock() throws Exception {
		TestUser owner = createUser("pbio_" + suffix);

		mockMvc.perform(put("/users/" + owner.id())
				.header("Authorization", "Bearer " + owner.token())
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"personalText": "P1", "location": "L1",
						 "websiteUrl": "https://a.example", "hideEmailFlag": true}
						"""))
				.andExpect(status().is2xxSuccessful());

		OffsetDateTime createdAtRegistration = userBioInfoDboMapper.selectByPrimaryKey(owner.id()).getCreatedTs();
		OffsetDateTime updatedByFirstPatch = userBioInfoDboMapper.selectByPrimaryKey(owner.id()).getUpdatedTs();
		assertNotNull(updatedByFirstPatch);
		assertTrue(updatedByFirstPatch.isAfter(createdAtRegistration),
				"the PATCH transaction stamps updated_ts fresh, strictly after the registration insert");

		mockMvc.perform(put("/users/" + owner.id())
				.header("Authorization", "Bearer " + owner.token())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"location\": \"L2\"}"))
				.andExpect(status().is2xxSuccessful());

		OffsetDateTime updatedBySecondPatch = userBioInfoDboMapper.selectByPrimaryKey(owner.id()).getUpdatedTs();
		assertTrue(updatedBySecondPatch.isAfter(updatedByFirstPatch),
				"each PATCH re-stamps updated_ts with a fresh DB clock in its own transaction");

		mockMvc.perform(get("/users/" + owner.id())
				.header("Authorization", "Bearer " + owner.token()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.bioInfo.personalText").value("P1"))
				.andExpect(jsonPath("$.bioInfo.location").value("L2"))
				.andExpect(jsonPath("$.bioInfo.websiteUrl").value("https://a.example"))
				.andExpect(jsonPath("$.bioInfo.hideEmailFlag").value(true));

		mockMvc.perform(put("/users/" + owner.id())
				.header("Authorization", "Bearer " + owner.token())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"personalText\": null}"))
				.andExpect(status().is2xxSuccessful());

		MvcResult afterNullResult = mockMvc.perform(get("/users/" + owner.id())
				.header("Authorization", "Bearer " + owner.token()))
				.andExpect(status().isOk())
				.andReturn();
		JsonNode bioAfterNull = json.readTree(afterNullResult.getResponse().getContentAsString()).get("bioInfo");
		JsonNode personalTextAfterNull = bioAfterNull.get("personalText");
		assertTrue(personalTextAfterNull == null || personalTextAfterNull.isNull(),
				"a present-null PATCH writes SQL null, distinct from an absent field being skipped");
		assertEquals("L2", bioAfterNull.get("location").asString(),
				"the null PATCH of personalText must not disturb the untouched location");
	}

	@Autowired
	private UserAwardDboMapper userAwardDboMapper;

	@Test
	void awardGrantByProfileAdminAndCatalog() throws Exception {
		String memberName = "paward_" + suffix;
		register(memberName, "password123");
		String memberToken = login(memberName, "password123").get("accessToken").asString();
		int memberId = userIdOf(memberName);
		String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

		MvcResult catalogResult = mockMvc.perform(get("/users/awards/catalog"))
				.andExpect(status().isOk())
				.andReturn();
		JsonNode catalog = json.readTree(catalogResult.getResponse().getContentAsString());
		Integer goodEggAwardId = null;
		for (JsonNode award : catalog)
			if ("GOOD_EGG".equals(award.get("code").asString()))
				goodEggAwardId = award.get("awardId").asInt();
		assertNotNull(goodEggAwardId, "the seeded award catalog must offer GOOD_EGG");

		String grantPayload = """
				{"awardId": %d, "reason": "Helpful beyond the call %s"}
				""".formatted(goodEggAwardId, suffix);
		mockMvc.perform(post("/users/" + memberId + "/awards")
				.header("Authorization", "Bearer " + memberToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(grantPayload))
				.andExpect(status().isForbidden());

		MvcResult grantResult = mockMvc.perform(post("/users/" + memberId + "/awards")
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(grantPayload))
				.andExpect(status().isOk())
				.andReturn();
		boolean granted = false;
		for (JsonNode award : json.readTree(grantResult.getResponse().getContentAsString()).get("awards"))
			if ("GOOD_EGG".equals(award.get("code").asString()))
				granted = true;
		assertTrue(granted, "the grant response must carry the freshly granted award");

		UserAwardDboExample ex = new UserAwardDboExample();
		ex.createCriteria().andUserIdEqualTo(memberId).andAwardIdEqualTo(goodEggAwardId);
		assertEquals(1, userAwardDboMapper.countByExample(ex));
	}
}
