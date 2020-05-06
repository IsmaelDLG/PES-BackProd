package com.pesados.purplepoint.api.model.user;

import com.pesados.purplepoint.api.model.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;



@Service
public class UserServiceImpl implements UserService {
	
	
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
	@Override
	public User saveUser(User newUser) {
		if (newUser.getProfilePic() == null) {
			Resource resource = new ClassPathResource("sample.jpg");
		    InputStream inputStream;
		    byte[] bdata = null;
			try {
				inputStream = resource.getInputStream();
			    bdata = FileCopyUtils.copyToByteArray(inputStream);	
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image defaultImage = new Image("sample.png","image/jpg", bdata);
			newUser.setProfilePic(defaultImage);
		}
		return userRepository.save(newUser);
	}
	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
    @Override
    public Optional<User> getUserByToken(String token) {
        return userRepository.findByToken(token);
    }
}