package org.platanus.webboard.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("회원가입")
	void save() {
		// give
		User user = User.builder()
				.username("testuser")
				.password("password")
				.nickname("testnickname")
				.email("testemail@gmail.com")
				.role(UserRole.ROLE_USER)
				.deleted(false)
				.build();

		//when
		user = userRepository.save(user);

		//then
		Assertions.assertNotNull(user.getId());
	}

	@Test
	@DisplayName("회원정보변경")
	void update() {
		//give
		User user = User.builder()
				.username("testuser2")
				.password("password")
				.nickname("testnickname2")
				.email("testemail2@gmail.com")
				.role(UserRole.ROLE_USER)
				.deleted(false)
				.build();
		user = userRepository.save(user);

		//when
		String changed = "testmail22@gmail.com";
		user.setEmail(changed);

		//then
		User findByUsername = userRepository.findByUsername("testuser2").get();
		Assertions.assertEquals(changed, findByUsername.getEmail());
	}

	@Test
	void updateDeleteFlag() {
		//give
		User user = User.builder()
				.username("testuser3")
				.password("password")
				.nickname("testnickname3")
				.email("testemail3@gmail.com")
				.role(UserRole.ROLE_USER)
				.deleted(false)
				.build();
		user = userRepository.save(user);

		//when
		user.setDeleted(true);

		//then
		User findByUsername = userRepository.findByUsername("testuser3").get();
		Assertions.assertTrue(findByUsername.isDeleted());
	}
}