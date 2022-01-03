package com.ondemandcarwash.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ondemandcarwash.models.Admin;
import com.ondemandcarwash.repository.AdminRepository;


@Service
public class AdminService implements UserDetailsService{
	
	@Autowired
	private AdminRepository adminRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		// find the user name
		Admin foundedUser = adminRepository.findByUsername(username);
		// if username is not there
		if (foundedUser == null)
			return null;

		String name = foundedUser.getUsername();
		String pwd = foundedUser.getPassword();
		return new User(name, pwd, new ArrayList<>());
	}


}
