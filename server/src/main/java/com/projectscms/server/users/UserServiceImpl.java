package com.projectscms.server.users;

import com.projectscms.server.projects.Project;
import com.projectscms.server.projects.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

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
    public boolean deleteUserById(long userId) {
        LOG.debug(">>>deleteUserById<<<");

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {

            User user = userOptional.get();

            List<Project> projects = projectRepository.findByMaintainersContains(user.getUserId());


            for (Project project : projects) {
                project.removeMaintainerById(userId);
            }

            userRepository.delete(user);
            return true;
        }

        return false;
    }
}