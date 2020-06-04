package com.pesados.purplepoint.api.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.pesados.purplepoint.api.model.image.Image;
import com.pesados.purplepoint.api.controller.ImageController;



@Transactional
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
			String pic = "default.png";
			Resource resource = new ClassPathResource(pic);
		    InputStream inputStream;
		    byte[] bdata = null;
			try {
				inputStream = resource.getInputStream();
			    bdata = FileCopyUtils.copyToByteArray(inputStream);	
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Comentado porque guardar imagenes tan grandes no es compatible con la BD.
			Image defaultImage = null;
			try {
				defaultImage = new Image(pic, "image/png",
						Base64.getEncoder().encodeToString(ImageController.cropAndScale("image/png", bdata)));
			} catch (IOException e) {
				e.printStackTrace();
			}
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