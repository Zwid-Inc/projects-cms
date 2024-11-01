package com.projectscms.server.users;

import com.projectscms.server.projects.Project;
import com.projectscms.server.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ProjectService projectService;

    @Transactional
    @Override
    public boolean createUser(User user) {
        LOG.debug(">>>createUser<<<");
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        LOG.debug(">>>getAllUsers<<<");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
        LOG.debug(">>>getUserById<<<");
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOG.debug(">>>getUserByEmail<<<");
        return userRepository.findByEmail(email);
    }



    @Transactional
    @Override
    public boolean updateUser(long id, User user) {
        LOG.debug(">>>updateUser<<<");
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    @Transactional
    @Override
    public boolean deleteUserById(long id) {
        LOG.debug(">>>deleteUserById<<<");

        if (userRepository.existsById(id)) {
            List<Project> projects = projectService.getAllProjects();

            // update projectMaintainers
            for (Project project : projects) {
                Set<User> updatedMaintainers = project.getProjectMaintainers().stream()
                        .filter(maintainer -> !maintainer.getUserId().equals(id))
                        .collect(Collectors.toSet());

                project.setProjectMaintainers(updatedMaintainers);
            }

            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}