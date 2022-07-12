package handtalkproject.service;

import handtalkproject.domain.entity.User;
import handtalkproject.exception.DuplicatedEmailException;
import handtalkproject.exception.NoSuchUserException;
import handtalkproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private static final String DUPLICATED_EMAIL_MESSAGE = "이미 가입된 이메일 주소입니다. 다른 주소를 사용해주세요";

    private final UserRepository userRepository;

    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedEmailException(DUPLICATED_EMAIL_MESSAGE);
        }
        return userRepository.save(user);
    }

    public User login(User user) {
        return userRepository.findAll()
                      .stream()
                      .filter(u -> u.canLogin(user))
                      .findFirst()
                      .orElseThrow(NoSuchUserException::new);
    }
}